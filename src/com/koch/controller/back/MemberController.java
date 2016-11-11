package com.koch.controller.back;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koch.base.BaseController;
import com.koch.bean.CustomerData;
import com.koch.bean.Filter;
import com.koch.bean.JsonMessage;
import com.koch.bean.Message;
import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.bean.Setting;
import com.koch.bean.Filter.Operator;
import com.koch.bean.OrderBy.OrderType;
import com.koch.entity.Admin;
import com.koch.entity.Grade;
import com.koch.entity.Member;
import com.koch.entity.Question;
import com.koch.entity.Member.MemberStatus;
import com.koch.service.AdminService;
import com.koch.service.AreaService;
import com.koch.service.FactionService;
import com.koch.service.GradeService;
import com.koch.service.JobService;
import com.koch.service.MemberService;
import com.koch.service.QuestionService;
import com.koch.util.JavaMD5;
import com.koch.util.JsonUtil;
import com.koch.util.SettingUtils;
/**
 * 会员管理控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller
@RequestMapping(value="back/member")
public class MemberController extends BaseController{
	@Resource
	private MemberService memberService;
	@Resource
	private QuestionService questionService;
	@Resource
	private AreaService areaService;
	@Resource
	private GradeService gradeService;
	@Resource
	private AdminService adminService;
	@Resource
	private FactionService factionService;
	@Resource
	private JobService jobService;
	
    @RequestMapping(value="list")
	public String list(ModelMap model){
    	List<Grade> list = gradeService.getAll();
    	model.addAttribute("grades",list);
    	model.addAttribute("statusArray", MemberStatus.values());
    	return "/back/member/member_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
	public String pager(Integer gradeId,String username,MemberStatus status,String phone,String realname, Pager<Member> pager){
    	if(gradeId != null){
    		pager.getFilters().add(Filter.eq("grade", gradeService.get(gradeId)));
    	}
    	if(StringUtils.isNotEmpty(username)){
    		pager.getFilters().add(Filter.eq("username", username));
    	}
    	if(status != null){
    		pager.getFilters().add(Filter.eq("status", status));
    	}
    	if(StringUtils.isNotEmpty(phone)){
    		pager.getFilters().add(Filter.eq("phone", phone));
    	}
    	if(StringUtils.isNotEmpty(realname)){
    		pager.getFilters().add(Filter.like("realname", realname));
    	}
    	pager = memberService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		try {
    			String [] propertys = {"id","username","grade","faction","job","deposit","score","amount","email","registerIp","statusName","createDate"};
    			Map<String, String[]> params = new HashMap<String, String[]>();
    			params.put("member", propertys);
    			params.put("faction", new String[]{"id","name"});
    			params.put("job", new String[]{"id","name"});
    			result = JsonUtil.toJsonIncludeProperties(new CustomerData(pager.getList(), pager.getTotalCount()), params);
    		} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }
    
    @RequestMapping(value="checkUserName")
    @ResponseBody
    public Boolean checkUserName(String username){
    	if (StringUtils.isEmpty(username))
    		return false;
    	return !this.memberService.usernameExists(username);
    }
    
    @RequestMapping(value="checkEmail")
    @ResponseBody
    public Boolean checkEmail(String previousEmail, String email){
		if (StringUtils.isEmpty(email))
			return false;
		return this.memberService.emailUnique(previousEmail, email);
    }
    
    public void initModelMap(ModelMap model){
    	List<Question> list = questionService.getList("isPublish", true,new OrderBy("orderList",OrderBy.OrderType.asc));
		model.addAttribute("questions", list);
    	model.addAttribute("statusArray", MemberStatus.values());
    	model.addAttribute("grades",gradeService.getAll());
    }
    
	@RequestMapping(value="view")
    public String view(ModelMap model){
		initModelMap(model);
		return "/back/member/member_add";
    }
	
	@RequestMapping(value="add", method={RequestMethod.POST})
    public String add(Member member,Integer areaId,ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Setting setting = SettingUtils.get();
		if (member.getUsername().length() < setting.getUsernameMinLength().intValue() || member.getUsername().length() > setting.getUsernameMaxLength().intValue()){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.user.userInputIsNotCorrect");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/member_add";
		}
		if (member.getPassword().length() < setting.getPasswordMinLength().intValue() || member.getPassword().length() > setting.getPasswordMaxLength().intValue()){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.user.passwordInputIsNotCorrect");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/member_add";
		}
			
		if (this.memberService.usernameExists(member.getUsername())){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.user.exitUserName");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/member_add";
		}
			
		if (!setting.getIsDuplicateEmail().booleanValue() && this.memberService.emailExists(member.getEmail())){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.user.exitEmail");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/member_add";
		}
		if(areaId != null){
			member.setArea(areaService.get(areaId));
		}
		member.setPassword(JavaMD5.getMD5ofStr(member.getPassword()));
		member.setAmount(new BigDecimal(0));
		member.setExp(0D);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setCart(null);
		member.setOrders(null);
		member.setDepositInfos(null);
		member.setPaymentInfos(null);
		member.setCouponInfos(null);
		
		Admin operator = new Admin();//暂无登录用户
		memberService.save(member, operator);
		setRedirectAttributes(redirectAttributes, "Common.save.success");
    	return "redirect:/back/member/list.shtml";
    }
    
    @RequestMapping(value="edit/{id}")
	public String get(@PathVariable Integer id,ModelMap model){
    	Member member = memberService.get(id);
    	List<Question> list = questionService.getList("isPublish", true,new OrderBy("orderList",OrderBy.OrderType.asc));
    	model.addAttribute("member", member);
    	model.addAttribute("questions", list);
    	model.addAttribute("statusArray", MemberStatus.values());
    	model.addAttribute("grades",gradeService.getAll());
    	model.addAttribute("factions", factionService.getAll(new OrderBy("indexs", OrderType.asc)));
    	model.addAttribute("jobs", jobService.getAll(new OrderBy("indexs", OrderType.asc)));
    	return "/back/member/member_edit";
    }
    
    public void initRedirectAttributes(RedirectAttributes redirectAttributes){
    	List<Question> list = questionService.getList("isPublish", true,new OrderBy("orderList",OrderBy.OrderType.asc));
    	redirectAttributes.addFlashAttribute("questions", list);
    	redirectAttributes.addFlashAttribute("statusArray", MemberStatus.values());
    	redirectAttributes.addFlashAttribute("grades",gradeService.getAll());
    }
    
	@RequestMapping(value="edit", method={RequestMethod.POST})
    public String edit(Member member,Integer areaId, BigDecimal modifyDeposit, String modifyDepositRemark, Integer modifyScore, RedirectAttributes redirectAttributes){
		Setting setting = SettingUtils.get();
		if(StringUtils.isNotEmpty(member.getPassword()) && (member.getPassword().length() < setting.getPasswordMinLength() || member.getPassword().length() > setting.getPasswordMaxLength())){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.user.userInputIsNotCorrect");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/edit/"+member.getId()+".shtml";
		}
		
		Member item = memberService.get(member.getId());
		if(item == null){
			initRedirectAttributes(redirectAttributes);
			setRedirectAttributes(redirectAttributes, "call.update.error");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/edit/"+member.getId()+".shtml";
		}
		if(!setting.getIsDuplicateEmail() && !memberService.emailUnique(item.getEmail(), member.getEmail())){
			setRedirectAttributes(redirectAttributes, "call.user.exitEmail");
			redirectAttributes.addFlashAttribute("member", member);
			return "redirect:/back/member/edit/"+member.getId()+".shtml";
		}
		
		item.setGrade(gradeService.get(member.getGrade().getId()));
		
		if(areaId != null){
			member.setArea(areaService.get(areaId));
		}
		if (StringUtils.isEmpty(member.getPassword()))
			member.setPassword(item.getPassword());
		else
			member.setPassword(JavaMD5.getMD5ofStr(member.getPassword()));
		
		if (item.getStatus() == MemberStatus.lock && member.getStatus() == MemberStatus.active) {
			member.setLoginFailureCount(0);
			member.setLockedDate(null);
		} else {
			member.setStatus(item.getStatus());
			member.setLoginFailureCount(item.getLoginFailureCount());
			member.setLockedDate(item.getLockedDate());
		}
		
		BeanUtils.copyProperties(member, item, new String[]{"id","username","score","deposit","amount","exp","createDate","registerIp","profileImage","cart","orders","paymentInfos","depositInfos","favoriteProducts","couponInfos"});
		
		Admin operator = adminService.getCurrent();
		item.setQuestion(null);//取消问题保护
		memberService.update(item, modifyDeposit, modifyDepositRemark, modifyScore, operator);
		setRedirectAttributes(redirectAttributes, "Common.update.success");
		return "redirect:/back/member/list.shtml";
    }
	
	@RequestMapping(value="delete", method={RequestMethod.POST})
	@ResponseBody
    public JsonMessage delete(Integer [] id){
		if (id != null) {
			for (Integer i : id) {
				Member member = this.memberService.get(i);
				if (member != null && member.getDeposit().compareTo(new BigDecimal(0)) > 0) {
					return JsonMessage.error("call.member.deleteExistDepositNotAllowed", new Object[] { member.getUsername() });
				}
			}
			this.memberService.delete(id);
		}
		return delete_success;
    }
}
