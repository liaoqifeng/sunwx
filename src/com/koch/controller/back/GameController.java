package com.koch.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koch.base.BaseController;
import com.koch.bean.CustomerData;
import com.koch.bean.Filter;
import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.entity.Game;
import com.koch.entity.GameItem;
import com.koch.entity.Product;
import com.koch.entity.Game.GameCycle;
import com.koch.entity.Game.GameStatus;
import com.koch.entity.GameItem.HortationType;
import com.koch.entity.Tag.Type;
import com.koch.service.GameService;
import com.koch.service.ProductService;
import com.koch.util.JsonUtil;
import com.koch.util.LotteryUtil;
/**
 * 游戏管理控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller
@RequestMapping(value="back/game")
public class GameController extends BaseController{
	
	@Resource
	private GameService gameService;
	@Resource
	private ProductService productService;
	
    @RequestMapping(value="list")
	public String list(ModelMap model){
		return "/back/content/game_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
   	public String pager(Pager<Game> pager){
    	pager = gameService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		result = JsonUtil.toJson(new CustomerData(pager.getList(), pager.getTotalCount()));
    	}
    	return result;
    }
    
    @RequestMapping(value="edit/{id}",method={RequestMethod.GET})
	public String get(@PathVariable Integer id,ModelMap model){
    	model.addAttribute("game", gameService.get(id));
    	model.addAttribute("status", GameStatus.values());
    	model.addAttribute("cycles", GameCycle.values());
    	model.addAttribute("hortationTypes", HortationType.values());
    	return "/back/content/game_edit";
    }
    @RequestMapping(value="edit",method={RequestMethod.POST})
    public String edit(Game game,String [] productNumbers,RedirectAttributes redirectAttributes) {
    	if(game == null || game.getGameItems().size() <= 0){
			return "redirect:/back/game/list.shtml";
    	}
    	Game old = gameService.get(game.getId());
    	old.getGameItems().clear();
    	gameService.update(old);
    	
    	Iterator<GameItem> gameItems = game.getGameItems().iterator();
    	int i = 0;
    	while(gameItems.hasNext()){
    		GameItem item = gameItems.next();
    		if(item == null || item.getHortationIndex() == null){
    			gameItems.remove();
    		}else{
    			if(item.getHortationType() == HortationType.physical){
        			Product product = productService.findByNumber(productNumbers[i]);
        			item.setProduct(product);
        		}
        		item.setGame(game);
        		i++;
    		}
    	}
    	gameService.update(game);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/game/list.shtml";
    }
    
    @RequestMapping(value="isExits")
    @ResponseBody
    public Boolean isExits(String productIds){
    	List<Filter> params = new ArrayList<Filter>();
    	params.add(new Filter("number", Operator.eq, productIds));
    	params.add(new Filter("isPrize", Operator.eq, true));
    	List<Product> products = productService.findList(1, params, null);
    	return products != null && products.size()>0;
    }
    
    @RequestMapping(value = { "/calculate" }, method = { RequestMethod.POST })
	@ResponseBody
	public List calculate(Integer id){
		Game game = gameService.get(id);
		List<GameItem> items = game.getGameItems();
		if(items != null && items.size() > 0){
			List<Double> orignalRates = new ArrayList<Double>(items.size());
	        for (GameItem item : items) {
	            double probability = item.getProbability();
	            if (probability < 0) {
	                probability = 0;
	            }
	            if (item.getCount() != null) {// 限数量抽奖
					Integer winningCount = item.getWinningCount() == null ? 0 : item.getWinningCount();
					if (winningCount >= item.getCount() && ((item.getScore()==null?0:item.getScore().intValue()) > 0 || item.getHortationType() == HortationType.physical)) {// 如果数量已经被抽完,则把概率设为0
						probability = 0;
					}
				}
				orignalRates.add(probability);
	        }
	        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
	        double num = 10000;
	        for (int i = 0; i < num; i++) {
	            int orignalIndex = LotteryUtil.lottery(orignalRates);

	            Integer value = count.get(orignalIndex);
	            count.put(orignalIndex, value == null ? 1 : value + 1);
	        }
	        List list = new ArrayList();
	        for(int i=0;i<items.size();i++){
	        	GameItem item = items.get(i);
	        	Integer c = count.get(i);
	        	if(c == null) c = 0;
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("hortationIndex", item.getHortationIndex());
	        	map.put("title", item.getTitle());
	        	map.put("count", (item.getCount()==null?0:item.getCount()) - item.getWinningCount());
	        	map.put("winning", c);
	        	map.put("probability", c / num);
	        	map.put("setProbability", item.getProbability());
	        	list.add(map);
	        }
	        return list;
		}
		return null;
	}
}
