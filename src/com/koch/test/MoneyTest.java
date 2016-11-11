package com.koch.test;

import java.util.HashMap;
import java.util.Map;

import com.gson.util.MoneyUtils;
import com.koch.util.XmlConverUtil;

public class MoneyTest {
	
	public static void main(String[] args) {
		String orderNNo =  MoneyUtils.getOrderNo() ; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nonce_str", MoneyUtils.buildRandom());//随机字符串
		map.put("mch_billno", orderNNo);//商户订单
		map.put("mch_id", "1251637201");//商户号
		map.put("wxappid", "wx0daa0d7188858bb0");//商户appid
		map.put("nick_name", "六里阳光");//提供方名称
		map.put("send_name", "六里阳光");//用户名
		map.put("re_openid", "ouI-8voT5s51r-sjOG2f1wVn1TNs");//用户openid
		map.put("total_amount", 440);//付款金额
		map.put("min_value", 440);//最小红包
		map.put("max_value", 440);//最大红包
		map.put("total_num", 1);//红包发送总人数
		map.put("wishing", "新年快乐");//红包祝福语
		map.put("client_ip", "127.0.0.1");//ip地址
		map.put("act_name", "过年红包");//活动名称
		map.put("remark", "新年新气象");//备注
		map.put("sign", MoneyUtils.createSign(map));//签名
		
		Map resultMap = null;
		try {
			String result = MoneyUtils.doSendMoney(MoneyUtils.url, MoneyUtils.createXML(map));
			resultMap = XmlConverUtil.xmltoMap(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("result:"+resultMap);
	}
}
