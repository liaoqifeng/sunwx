package com.koch.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.koch.bean.Pager.OrderType;
import com.koch.entity.Member;
import com.koch.entity.ScoreLog;
import com.koch.service.MemberService;
import com.koch.service.ScoreLogService;
import com.koch.util.JsonUtil;

/**
 * 职位控制器
 * 
 * @author koch
 * @date 2015-06-12
 */
@Controller
@RequestMapping(value = "back/scoreLog")
public class ScoreLogController extends BaseController {
	@Resource
	private ScoreLogService scoreLogService;

	@Resource
	private MemberService memberService;

	@RequestMapping(value = "list")
	public String list() {
		return "/back/member/scoreLog_list";
	}

	@RequestMapping(value = "findUsers")
	@ResponseBody
	public String findUsers(String realname) {

		List<Member> members = memberService.findListByRealName(realname);

		String result = "[]";
		if (members != null && members.size() > 0) {
			Map<String, String[]> params = new HashMap<String, String[]>();

			// params.put("score_log", new String[] { "id", "score", "remark",
			// "createDate", "member" });
			params.put("member", new String[] { "id", "realname", "score" });
			result = JsonUtil.toJsonIncludeProperties(members, params);
		}
		return result;
	}

	@RequestMapping(value = "list/pager")
	@ResponseBody
	public String pager(String name, Pager pager) {
		
		if(name != null){
			List<Member> list = memberService.findListByRealName(name);
    		pager.getFilters().add(Filter.in("member", list));
    	}
		pager.setOrderBy("createDate");
		pager.setOrderType(OrderType.desc);
		pager = scoreLogService.findByPage(pager);
		String result = "[]";
		if (pager.getList() != null && pager.getList().size() > 0) {
			Map<String, String[]> params = new HashMap<String, String[]>();

			params.put("score_log", new String[] { "id", "score", "remark", "createDate", "member" });
			params.put("member", new String[] { "id", "realname" });
			result = JsonUtil.toJsonIncludeProperties(new CustomerData(pager.getList(), pager.getTotalCount()), params);
		}
		return result;
	}

	@RequestMapping(value = "view")
	public String view() {
		return "/back/member/scoreLog_add";
	}

	@RequestMapping(value = "add", method = { RequestMethod.POST })
	public String add(ScoreLog scoreLog, RedirectAttributes redirectAttributes) {
		scoreLog.setType(0);

		scoreLogService.updateScore(scoreLog);
		setRedirectAttributes(redirectAttributes, "Common.save.success");
		return "redirect:/back/scoreLog/list.shtml";
	}

	@RequestMapping(value = "edit/{id}")
	public String get(@PathVariable Integer id, ModelMap modelMap) {
		ScoreLog scoreLog = scoreLogService.get(id);
		modelMap.addAttribute("sector", scoreLog);
		return "/back/member/scoreLog_edit";
	}

	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public String edit(ScoreLog scoreLog, RedirectAttributes redirectAttributes) {
		scoreLogService.update(scoreLog);
		setRedirectAttributes(redirectAttributes, "Common.update.success");
		return "redirect:/back/scoreLog/list.shtml";
	}

	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	@ResponseBody
	public JsonMessage delete(Integer[] id) {
		if (id != null) {
			this.scoreLogService.delete(id);
		}
		return delete_success;
	}
}
