package com.koch.controller.back;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import com.koch.bean.JsonMessage;
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.entity.Admin;
import com.koch.entity.DugCoin;
import com.koch.entity.Role;
import com.koch.service.AdminService;
import com.koch.service.DugCoinService;
import com.koch.service.NodesService;
import com.koch.service.RoleService;
import com.koch.util.DateUtil;
import com.koch.util.JavaMD5;
import com.koch.util.JsonUtil;
/**
 * 管理员管理控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller
@RequestMapping(value="back/dugCoin")
public class DugCoinController extends BaseController{
	@Resource
	private DugCoinService dugCoinService;
	
	@RequestMapping(value="edit/{id}")
	public String get(@PathVariable Integer id,ModelMap modelMap){
		modelMap.addAttribute("dugCoin", dugCoinService.get(id));
		return "/back/content/dugCoin_edit";
    }
	
	@RequestMapping(value="edit",method={RequestMethod.POST})
	public String edit(DugCoin dugCoin,String timers,RedirectAttributes redirectAttributes){
		if(StringUtils.isEmpty(timers)){
			setRedirectAttributes(redirectAttributes, "Common.update.error");
		}else{
			List<String> ts = JsonUtil.toObject(timers, ArrayList.class);
			Collections.sort(ts, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					Calendar c = Calendar.getInstance();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = DateUtil.parseDate(df.format(new Date())+" "+o1, "yyyy-MM-dd HH:mm:ss");
					Date date2 = DateUtil.parseDate(df.format(new Date())+" "+o2, "yyyy-MM-dd HH:mm:ss");
					if(date1.after(date2)){
						return 1;
					}
					return -1;
				}
			});
			dugCoin.setTimeList(JsonUtil.toJson(ts));
			setRedirectAttributes(redirectAttributes, "Common.update.success");
		}
		dugCoinService.update(dugCoin);
		return "redirect:/back/dugCoin/edit/"+dugCoin.getId()+".shtml";
	}
	
	
}
