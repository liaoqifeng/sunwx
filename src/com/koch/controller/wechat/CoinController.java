package com.koch.controller.wechat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gson.util.AesException;
import com.gson.util.ConfKit;
import com.gson.util.MoneyUtils;
import com.gson.util.PropertiesUtil;
import com.gson.util.WechatUtil;
import com.koch.base.BaseController;
import com.koch.bean.Pager;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.DugCoin;
import com.koch.entity.DugCoinInfo;
import com.koch.entity.Member;
import com.koch.entity.Product;
import com.koch.entity.Receiver;
import com.koch.service.AreaService;
import com.koch.service.BonusInfoService;
import com.koch.service.BonusService;
import com.koch.service.DugCoinInfoService;
import com.koch.service.DugCoinService;
import com.koch.service.ExchangeInfoService;
import com.koch.service.MemberService;
import com.koch.service.ProductService;
import com.koch.service.ReceiverService;
import com.koch.util.DateUtil;
import com.koch.util.XmlConverUtil;

@Controller("wcCoinController")
@RequestMapping(value="weixin/member/coin")
public class CoinController extends BaseController{
	@Resource
	private MemberService memberService;
	@Resource
	private ProductService productService;
	@Resource
	private ReceiverService receiverService;
	@Resource
	private AreaService areaService;
	@Resource
	private ExchangeInfoService exchangeInfoService;
	@Resource
	private DugCoinService dugCoinService;
	@Resource
	private DugCoinInfoService dugCoinInfoService;
	@Resource
	private BonusService bonusService;
	@Resource
	private BonusInfoService bonusInfoService;
	
	@RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
	public String index(HttpServletRequest request,ModelMap model) throws AesException {
		model.addAttribute("member", memberService.get(memberService.getCurrent().getId()));
		List<Product> products = productService.findList(true,true,true,false,true,3);
		model.addAttribute("products", products);
		
		DugCoin dugCoin = dugCoinService.get(1);
		model.addAttribute("dugCoin", dugCoin);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = DateUtil.getCurrentDate();
		long second = -1;
		Boolean isHaveDug = false ,isOver = false;
		Date currentPoint = dugCoin.getCurrentPoint();
		if(currentPoint==null){
			isOver=true;
		}else{
			Calendar c = Calendar.getInstance();
			c.setTime(currentPoint);
			c.add(Calendar.MINUTE, dugCoin.getLastTime());
			if(date.after(currentPoint) && date.before(c.getTime())){
				isHaveDug = true;
				second = (c.getTime().getTime() - date.getTime())/1000;
			}else{
				second = (currentPoint.getTime() - date.getTime())/1000;
			}
		}
		List<DugCoinInfo> ds = null;
		if(isHaveDug){
			ds = dugCoinInfoService.findList(dugCoin, memberService.getCurrent());
		}
		Map dubMap = new HashMap();
		dubMap.put("isOver", isOver);
		dubMap.put("isHaveDug",isHaveDug);
		dubMap.put("isComplete", (ds != null && ds.size() > 0));
		dubMap.put("second", second);
		model.addAttribute("dubMap", dubMap);
		
		//Bonus bonus = bonusService.findActiveBonus();
		//model.addAttribute("bonus", bonus);
		
		String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
		String url = (basePath + "weixin/member/coin/index.shtml");
		//model.addAttribute("params", WechatUtil.getWxConfig(url));
		return "/weixin/coin/index";
	}
	
	private BigDecimal getRandomBonus(double iStart,double iEnd){ 
		iStart = iStart * 100;
		iEnd = iEnd * 100;
        int start = 0;
        int end = 0;
        if(iStart > iEnd){
            start = (int)Math.floor(iEnd);
            end = (int)Math.floor(iStart);
        } else {
            start = (int)Math.floor(iStart);
            end = (int)Math.floor(iEnd);
        }
        int s = new Random().nextInt(end - start + 1) + start;
        BigDecimal b = new BigDecimal(s).divide(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP);
        if(b.compareTo(new BigDecimal(iStart/100)) < 0) b = new BigDecimal(iStart/100);
        if(b.compareTo(new BigDecimal(iEnd/100)) > 0) b = new BigDecimal(iEnd/100);
        return b;
     }
	
	@RequestMapping(value = { "/sendBonus" })
	@ResponseBody
	public WeChatMessage sendBonus(HttpServletRequest request) {
		Bonus bonus = bonusService.findActiveBonus();
		BigDecimal b = getRandomBonus(bonus.getMinAmount().doubleValue(),bonus.getMaxAmount().doubleValue());
		if(bonus == null){
			return WeChatMessage.error("发放红包活动已过期");
		}
		if(bonus.totalSendAmount().compareTo(bonus.getTotalAmount()) >= 0 || bonus.totalSendAmount().add(b).compareTo(bonus.getTotalAmount()) >= 0){
			return WeChatMessage.warn("您来晚了,红包已经被抢光了");
		}
		if(bonus.totalSendCount().intValue() >= bonus.getCount()){
			return WeChatMessage.warn("您来晚了,红包已经被抢光了");
		}
		Member member = memberService.getCurrent();
		List<BonusInfo> infoList = bonusInfoService.findBonusInfo(member, bonus);
		if(CollectionUtils.isNotEmpty(infoList)){
			return WeChatMessage.error("您已经抢过红包了");
		}
		
		String orderNNo =  MoneyUtils.getOrderNo() ;
		
		BonusInfo bi = new BonusInfo();
		bi.setMember(member);
		bi.setBonus(bonus);
		bi.setMchBillno(orderNNo);
		bi.setAmount(b);
		Integer id = bonusInfoService.save(bi);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nonce_str", MoneyUtils.buildRandom());//随机字符串
		map.put("mch_billno", orderNNo);//商户订单
		map.put("mch_id", PropertiesUtil.getProperty("partnerId").trim());//商户号
		map.put("wxappid", PropertiesUtil.getProperty("AppId").trim());//商户appid
		map.put("nick_name", bonus.getNickName());//提供方名称
		map.put("send_name", bonus.getSendName());//用户名
		map.put("re_openid","ouI-8voT5s51r-sjOG2f1wVn1TNs");//用户openid     member.getWechatCode()
		map.put("total_amount", 100);//付款金额    b.multiply(new BigDecimal(100)).intValue()
		map.put("min_value", 100);//最小红包  b.multiply(new BigDecimal(100)).intValue()
		map.put("max_value", 100);//最大红包  b.multiply(new BigDecimal(100)).intValue()
		map.put("total_num", 1);//红包发送总人数
		map.put("wishing", bonus.getWishing());//红包祝福语
		map.put("client_ip", MoneyUtils.getIpAddr(request));//ip地址
		map.put("act_name", bonus.getActiveName());//活动名称
		map.put("remark", bonus.getRemark());//备注
		map.put("sign", MoneyUtils.createSign(map));//签名
		Map resultMap = null;
		try {
			String result = MoneyUtils.doSendMoney(MoneyUtils.url, MoneyUtils.createXML(map));
			resultMap = XmlConverUtil.xmltoMap(result);
		} catch (Exception e) {
			e.printStackTrace();
			bonusInfoService.delete(id);
			return WeChatMessage.error("抢红包人数过多,请稍微再试");
		}
		if("FAIL".equals(resultMap.get("return_code").toString())){
			bonusInfoService.delete(id);
			return WeChatMessage.error(resultMap.get("return_msg").toString());
		}
		bi.setSendListid(resultMap.get("send_listid").toString());
		bi.setSendTime(resultMap.get("send_time").toString());
		bonusInfoService.update(bi);
		return WeChatMessage.success("恭喜您获得一个红包");
	}
	
	@RequestMapping(value = { "/dug" }, method = { RequestMethod.GET })
	@ResponseBody
	public WeChatMessage dug() {
		DugCoin dugCoin = dugCoinService.get(1);
		List<DugCoinInfo> ds = dugCoinInfoService.findList(dugCoin, memberService.getCurrent());
		if(ds != null && ds.size() > 0){
			return WeChatMessage.error("这个时间段你已经挖过了");
		}
		int score = new Random().nextInt(dugCoin.getMaxScore() - dugCoin.getMinScore() + 1) + dugCoin.getMinScore();//new Random().nextInt(dugCoin.getScore()) + 1;
		DugCoinInfo d = new DugCoinInfo();
		d.setMember(memberService.getCurrent());
		d.setScore(score);
		dugCoinInfoService.save(d);
		
		Member member = memberService.get(memberService.getCurrent().getId());
		dugCoinService.dug(member, score);
		return WeChatMessage.success(score+"");
	}
	
	
	@RequestMapping(value = { "/gift" }, method = { RequestMethod.GET })
	public String gift(ModelMap model,Pager pager){
		//pager.setPageSize(10);
		//pager = productService.findByPage(pager);
		List<Product> products = productService.findList(true,true,null,false,true,100);
		model.addAttribute("products", products);
		//model.addAttribute("gifts", memberService.get(memberService.getCurrent().getId()).getExchangeInfos());
		model.addAttribute("member", memberService.get(memberService.getCurrent().getId()));
		return "/weixin/coin/gift";
	}
	
	@RequestMapping(value = { "/myGift" }, method = { RequestMethod.GET })
	public String myGift(ModelMap model,Pager pager){
		model.addAttribute("gifts", memberService.get(memberService.getCurrent().getId()).getExchangeInfos());
		model.addAttribute("member", memberService.get(memberService.getCurrent().getId()));
		return "/weixin/coin/myGift";
	}
	
	@RequestMapping(value = { "/exchange" }, method = { RequestMethod.GET })
	public String exchange(Integer id,ModelMap model){
		Product product = productService.get(id);
		model.addAttribute("product", product);
		//model.addAttribute("receivers", memberService.get(memberService.getCurrent().getId()).getReceivers());
		return "/weixin/coin/exchange";
	}
	
	
	@RequestMapping(value = { "/removeAddress" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage removeAddress(Integer receiverId){
		Member member = memberService.getCurrent();
		Receiver r = receiverService.get(receiverId);
		if(!r.getMember().getId().equals(member.getId())){
			return WeChatMessage.success("删除失败");
		}
		receiverService.delete(receiverId);
		return WeChatMessage.success("删除成功");
	}
	
	@RequestMapping(value = { "/doExchange" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage doExchange(Integer receiverId,Integer productId){
		if(receiverId == null || productId == null){
			return WeChatMessage.error("兑换失败");
		}
		Product product = productService.get(productId);
		if(!product.getIsPrize()){
			return WeChatMessage.error("礼物不能兑换");
		}
		if(product.getStock() < 1){
			return WeChatMessage.error("礼物已被全部兑换");
		}
		Member member = memberService.get(memberService.getCurrent().getId());
		if(product.getCoinPrice().intValue() > member.getScore()){
			return WeChatMessage.error("您的金币不足");
		}
		Receiver receiver = receiverService.get(receiverId);
		exchangeInfoService.exchange(product, receiver, member);
		return WeChatMessage.error("兑换成功");
	}
	
	@RequestMapping(value = { "/addAddress" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage addAddress(Receiver receiver,Integer areaId){
		Member member = memberService.get(memberService.getCurrent().getId());
		if(member.getReceivers().size() > Receiver.MAX_RECEIVER_COUNT){
			return WeChatMessage.error("最多只能增加"+Receiver.MAX_RECEIVER_COUNT+"个地址");
		}
		receiver.setArea(areaService.get(areaId));
		receiver.setMember(member);
		receiverService.save(receiver);
		return WeChatMessage.success("新增成功");
	}
	
}
