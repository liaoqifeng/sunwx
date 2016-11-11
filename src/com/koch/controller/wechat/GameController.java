package com.koch.controller.wechat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koch.base.BaseController;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.GameItem;
import com.koch.entity.Member;
import com.koch.entity.Game.GameStatus;
import com.koch.entity.Game.GameType;
import com.koch.entity.GameItem.HortationType;
import com.koch.service.GameInfoService;
import com.koch.service.GameItemService;
import com.koch.service.GameService;
import com.koch.service.MemberService;
import com.koch.util.JsonUtil;

@Controller("scGameCoinController")
@RequestMapping(value="weixin/member/game")
public class GameController extends BaseController{
	
	private static Integer GAME_SUDOKU_ID = 3;
	private static Integer GAME_ROTATE_ID = 2;
	private static Integer GAME_GUAGUALE_ID = 1;
	private static Integer GAME_SLOT_ID = 4;
	
	@Resource
	private GameService gameService;
	@Resource
	private GameItemService gameItemService;
	@Resource
	private MemberService memberService;
	@Resource
	private GameInfoService gameInfoService;
	
	@RequestMapping(value = { "/guaguale" }, method = { RequestMethod.GET })
	public String guaguale(ModelMap model) {
		model.addAttribute("game", gameService.get(GAME_GUAGUALE_ID));
		return "/weixin/game/guaguale";
	}
	
	@RequestMapping(value = { "/rotate" }, method = { RequestMethod.GET })
	public String rotate(ModelMap model){
		model.addAttribute("game", gameService.get(GAME_ROTATE_ID));
		return "/weixin/game/rotate";
	}
	
	@RequestMapping(value = { "/sudoku" }, method = { RequestMethod.GET })
	public String sudoku(ModelMap model){
		model.addAttribute("game", gameService.get(GAME_SUDOKU_ID));
		model.addAttribute("member", memberService.get(memberService.getCurrent().getId()));
		return "/weixin/game/sudoku";
	}
	
	@RequestMapping(value = { "/slot" }, method = { RequestMethod.GET })
	public String slot(ModelMap model){
		model.addAttribute("game", gameService.get(GAME_SLOT_ID));
		model.addAttribute("member", memberService.get(memberService.getCurrent().getId()));
		return "/weixin/game/slot";
	}
	
	@RequestMapping(value = { "/lottery" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage lottery(Integer id){
		Game game = gameService.get(id);
		if(game.getStatus() == GameStatus.maintain){
			return WeChatMessage.error("抽奖正在维修中...");
		}
		if(game.getStatus() == GameStatus.close){
			return WeChatMessage.error("抽奖已经关闭...");
		}
		Calendar c = Calendar.getInstance();
		Date beginDate = null, endDate = null;
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		if(game.getBeginTime() != null){
			if(hour < game.getBeginTime()){
				return WeChatMessage.error("抽奖失败,请查看规则说明");
			}
			c.set(Calendar.HOUR_OF_DAY, game.getBeginTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			beginDate = c.getTime();
		}
		if(game.getEndTime() != null){
			if(hour > game.getEndTime()){
				return WeChatMessage.error("抽奖失败,请查看规则说明");
			}
			c.set(Calendar.HOUR_OF_DAY, game.getEndTime());
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			endDate = c.getTime();
		}
		Member member = memberService.get(memberService.getCurrent().getId());//memberService.getCurrent();
		List<GameInfo> infos = gameInfoService.findList(beginDate, endDate, game, member);
		if(game.getCount() != null){
			if(infos.size() >= game.getCount()){
				return WeChatMessage.error("每天只能抽奖"+game.getCount()+"次");
			}
		}
		if(StringUtils.isNotEmpty(game.getExpression())){
			if(member.getScore().intValue() < game.getScore(infos.size())){
				return WeChatMessage.error("您的金币不足");
			}
		}
		
		GameItem item = gameService.lottery(game, member, infos);
		String result = JsonUtil.toJsonIncludeProperties(item, "gameItem", new String[]{"hortationIndex","image","title","hortationType","score"});
		System.out.println("result:"+result);
		List<GameItem> items = game.getGameItems();
		Map<String,Object> map = new HashMap<String, Object>();
		Integer [] indexs = new Integer[3];
		String message = "";
		if(item.getHortationType() == HortationType.virtual && (item.getScore() == null || item.getScore().intValue() == 0)){
			if(game.getType() == GameType.slot){
				Random r = new Random();
				int r1 = r.nextInt(items.size());
				int r2 = r.nextInt(items.size());
				indexs[0]=r1;
				indexs[1]=r2;
				if(r1 == r2){
					int r3 = 0;
					do {
						r3 = r.nextInt(items.size());
					} while (r3 != r2);
					indexs[2]=r3;
				}else{
					indexs[2]=r.nextInt(items.size());
				}
			}
			message = "您未抽中奖品哟,继续努力吧!";
		}else{
			if(item.getHortationType() == HortationType.virtual){
				message = "恭喜,您抽中了"+item.getTitle()+"";
			}else{
				message = "恭喜,您抽中了"+item.getTitle()+",稍候工作人员会与您取得联系";
			}
			indexs = new Integer[]{item.getHortationIndex(),item.getHortationIndex(),item.getHortationIndex()};
		}
		//Member m = memberService.get(member.getId());
		if(game.getType() == GameType.rotate){
			map.put("angle", 360 / items.size() * item.getHortationIndex());
		}
		if(game.getType() == GameType.slot){
			map.put("indexs",indexs);
		}
		map.put("score", member.getScore());
		map.put("message", message);
		map.put("result", result);
		return WeChatMessage.success(JsonUtil.toJson(map));
	}
}
