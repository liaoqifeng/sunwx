package com.koch.controller.wechat;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gson.util.AesException;
import com.gson.util.ConfKit;
import com.gson.util.WechatUtil;
import com.koch.base.BaseController;
import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Faction;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostComment;
import com.koch.entity.PostJoin;
import com.koch.entity.PostNotice;
import com.koch.entity.PostPraise;
import com.koch.entity.PostSector;
import com.koch.interceptor.WechatLoginInterceptor;
import com.koch.service.FactionService;
import com.koch.service.MemberService;
import com.koch.service.PostCommentPraiseService;
import com.koch.service.PostCommentService;
import com.koch.service.PostJoinService;
import com.koch.service.PostNoticeService;
import com.koch.service.PostPraiseService;
import com.koch.service.PostSectorService;
import com.koch.service.PostService;
import com.koch.util.JsonUtil;
import com.koch.util.PostConstant;
import com.koch.util.PropertiesUtil;

/**
 * Back Order Controller
 * 
 * @author koch
 * @date 2015-03-12
 */
@Controller
@RequestMapping(value = "weixin/post")
public class PostController extends BaseController {

	private static Logger logger = Logger.getLogger(PostController.class);

	@Resource
	private PostService postService;
	@Resource
	private PostSectorService postSectorService;
	@Resource
	private FactionService factionService;
	@Resource
	private PostCommentService postCommentService;
	@Resource
	private PostPraiseService postPraiseService;

	@Resource
	private PostJoinService postJoinService;

	@Resource
	private MemberService memberService;

	@Resource
	private PostNoticeService postNoticeService;

	@Resource
	private PostCommentPraiseService postCommentPraiseService;

	private void initWechatSign(ModelMap model, HttpServletRequest request) throws AesException {
		String url = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + request.getRequestURI();
		if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
			url += "?" + request.getQueryString();
		}
		String signature = WechatUtil.getSignature(url);
		model.addAttribute("appId", ConfKit.get("AppId"));
		model.addAttribute("timestamp", WechatUtil.TIMESTAMP);
		model.addAttribute("nonceStr", WechatUtil.NONCESTR);
		model.addAttribute("signature", signature);
	}

	@RequestMapping(value = "add")
	public String add(ModelMap model, HttpServletRequest request) throws AesException {
		initWechatSign(model, request);
		// List<PostSector> postSectors = postSectorService.getAlls("发布类型");
		List<PostSector> postSectors = postSectorService.getAll(new OrderBy("index"));
		model.addAttribute("postSectors", postSectors);

		return "/weixin/post/add";
	}

	@RequestMapping(value = "addActivity")
	public String addActivity(ModelMap model, HttpServletRequest request) throws AesException {
		initWechatSign(model, request);
		return "/weixin/post/addActivity";
	}

	@RequestMapping(value = "deletePost")
	public String deletePost(Integer postId) {
		postService.deletePost(postId);
		return "redirect:/weixin/post/main.shtml";
	}

	@RequestMapping(value = "addActivitySuccess")
	public String addActivitySuccess(String title, String beginTime, String endTime, String area, String content,
			String pics, ModelMap model, HttpServletRequest request) {
		logger.info("title = " + title + " ; content = " + content + " ; area = " + area + " ; pics = " + pics
				+ " ; beginTime = " + beginTime + " ; endTime = " + endTime);
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		// PostSector postSector = new PostSector();
		// postSector.setId(sector);
		// post.setPostSector(postSector);
		Date bd = com.koch.util.DateUtil.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		if (endTime != null) {
			Date ed = com.koch.util.DateUtil.parseDate(endTime, "yyyy-MM-dd HH:mm");
			post.setEndDate(ed);
		}
		post.setBeginDate(bd);
		post.setStatus(PostConstant.PostStatus.active);

		post.setArea(area);
		post.setCreateDate(new Date());
		post.setModifyDate(new Date());
		Member member = memberService.getCurrent();
		// member.setId();
		post.setMember(member);
		post.setType(2);
		// Integer result = postService.save(post);
		String path = request.getServletContext().getRealPath("upload/post");

		System.out.println(path);
		Integer result = postService.addPost(post, pics, path);
		if (result > 0) {
			System.out.println("success");
		}

		return "redirect:/weixin/post/main.shtml";
	}

	@RequestMapping(value = "addSuccess", method = { RequestMethod.POST })
	public String addSuccess(String title, String content, Integer sector, String pics, ModelMap model,
			HttpServletRequest request) {
		logger.info("title = " + title + " ; content = " + content + " ; sector = " + sector + " ; pics = " + pics);
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		PostSector postSector = new PostSector();
		postSector.setId(sector);
		post.setPostSector(postSector);
		post.setCreateDate(new Date());
		post.setModifyDate(new Date());
		post.setStatus(PostConstant.PostStatus.active);
		Member member = memberService.getCurrent();
		// member.setId(TestValue.TestUser);
		post.setMember(member);
		post.setType(1);
		String path = request.getServletContext().getRealPath("upload/post");
		// System.out.println(path);
		Integer result = postService.addPost(post, pics, path);
		if (result > 0) {
			System.out.println("success");
		}
		// return "redirect:/weixin/post/view/" + result + ".shtml";
		return "redirect:/weixin/post/main.shtml";
	}

	@RequestMapping(value = "faction")
	public String faction(Integer factionId, ModelMap model) {
		Faction faction = factionService.get(factionId);
		Long score = memberService.getScoreByFaction(factionId);
		List<Map<String, Object>> members = memberService.findListByFaction(factionId);
		model.addAttribute("faction", faction);
		model.addAttribute("members", members);
		model.addAttribute("score", score);
		return "/weixin/post/faction";
	}

	private void clearSession(HttpSession session) {
		session.removeAttribute(PostConstant.SESSION_POST_FACTION_ID);
		session.removeAttribute(PostConstant.SESSION_POST_SECTOR_ID);
		session.removeAttribute(PostConstant.SESSION_POST_MEMBER_ID);
	}

	@RequestMapping(value = "main")
	public String main(Integer type, Integer factionId, Integer sectorId, Integer memberId, ModelMap model,
			HttpServletRequest request) throws AesException {
		initWechatSign(model, request);

		// String removeBadgeFlag = (String)
		// request.getSession().getAttribute("removeBadge");
		// if (removeBadgeFlag != null) {
		// postService.updateBadeg(memberService.getCurrent().getId());
		// }

		Map<Integer, String> postSectors = postSectorService.getSectorMap();
		Map<Integer, String> factions = factionService.getFactionMap();

		model.addAttribute("postSectors", postSectors);
		model.addAttribute("factions", factions);

		// postCommentService.updateBadeg(memberService.getCurrent().getId());
		// if (factionId != null) {
		// session.setAttribute(PostConstant.SESSION_POST_FACTION_ID,
		// factionId);
		// }
		// if (sectorId != null) {
		// session.setAttribute(PostConstant.SESSION_POST_SECTOR_ID, sectorId);
		// }
		// if (memberId != null) {
		// session.setAttribute(PostConstant.SESSION_POST_MEMBER_ID, memberId);
		// }

		if (factionId == null) {
			factionId = PostConstant.FACTION_ALL_KEY;
		}
		if (sectorId == null) {
			sectorId = PostConstant.SECTOR_ALL_KEY;
		}
		if (type == null) {
			type = PostConstant.VIEW_POST_TYPE_ALL;
		}

		model.addAttribute("selectPostSectorId", sectorId);
		model.addAttribute("selectFactionId", factionId);
		model.addAttribute("type", type);

		model.addAttribute("memberId", memberId);
		// boolean removeBadge = false;
		Pager<Post> pager = null;
		Integer minRow = null;
		if (type == PostConstant.VIEW_POST_TYPE_MEMBER) {
			if (memberId == null) {
				memberId = memberService.getCurrent().getId();
			}
			Member member = memberService.get(memberId);
			model.addAttribute("member", member);
			pager = postService.findPosts(null, null, memberId, null, null, memberService.getCurrent());
		} else {
			PostNotice postNotice = postNoticeService.getPostNotice();
			if (postNotice != null) {
				List scoreList = memberService.getScoreGroupByFaction();
				model.addAttribute("postNotice", postNotice);
				model.addAttribute("scoreList", scoreList);
			}
			if (sectorId == PostConstant.SECTOR_ACTIVITY_KEY) {
				pager = postService.findPosts(null, factionId, null, 2, null, memberService.getCurrent());
			} else {
				pager = postService.findPosts(sectorId, factionId, null, null, null, memberService.getCurrent());
			}
		}

		List<Integer> postIds = new ArrayList<Integer>();
		if (pager.getList() != null && pager.getList().size() > 0) {
			// for (int i = 0; i < pager.getList().size(); i++) {
			// Post p = (Post) pager.getList().get(i);
			// if (p.getContent() != null && p.getContent().length() > 100) {
			// p.setContent(p.getContent().substring(0, 99) + "...");
			// }
			// postIds.add(p.getId());
			//
			// }
			Post last = (Post) pager.getList().get(pager.getList().size() - 1);
			minRow = last.getId();
		}

		// Map<Integer, Integer> postPraiseCount =
		// postPraiseService.getPraiseCount(postIds);

		// model.addAttribute("removeBadge", removeBadge);
		model.addAttribute("type", type);
		model.addAttribute("minRow", minRow);
		// model.addAttribute("postPraiseCount", postPraiseCount);
		// List<Post> posts = postService.getAll();
		model.addAttribute("posts", pager.getList());

		long badgeCount = 0;
		// String removingBadge = (String)
		// request.getSession().getAttribute("removingBadge");
		// if (memberId == null ||
		// !memberId.equals(memberService.getCurrent().getId())) {
		badgeCount = postService.getBadgeCount(memberService.getCurrent().getId());
		// }
		// System.out.println("badgeCount==================="+badgeCount);
		model.addAttribute("badgeCount", badgeCount);

		return "/weixin/post/main";
	}

	@RequestMapping(value = "removeBadge")
	@ResponseBody
	public WeChatMessage removeBadge(HttpServletRequest request) {
		request.getSession().setAttribute("removingBadge", "");
		System.out.println("===================removeBadge=================");
		postService.updateBadeg(memberService.getCurrent().getId());
		request.getSession().removeAttribute("removingBadge");
		return WeChatMessage.success("");
	}

	@RequestMapping(value = "praise")
	@ResponseBody
	public WeChatMessage praise(Integer postId, ModelMap model) {
		return postPraiseService.doPraise(memberService.getCurrent().getId(), postId);
	}

	@RequestMapping(value = "commentPraise")
	@ResponseBody
	public WeChatMessage commentPraise(Integer postCommentId, ModelMap model) {
		return postCommentPraiseService.doPraise(memberService.getCurrent(), postCommentId);
	}

	@RequestMapping(value = "cancelPraise")
	@ResponseBody
	public WeChatMessage cancelPraise(Integer postId, ModelMap model) {
		return postPraiseService.cancelPraise(memberService.getCurrent().getId(), postId);
	}

	@RequestMapping(value = "join")
	@ResponseBody
	public WeChatMessage join(Integer postId, ModelMap model) {
		return postJoinService.doJoin(memberService.getCurrent().getId(), postId);
	}

	@RequestMapping(value = "cancelJoin")
	@ResponseBody
	public WeChatMessage cancelJoin(Integer postId, ModelMap model) {
		return postJoinService.cancelJoin(memberService.getCurrent().getId(), postId);
	}

	@RequestMapping(value = "cancelCommont")
	@ResponseBody
	public WeChatMessage cancelCommont(Integer commentId, ModelMap model) {
		return postCommentService.cancelComment(commentId);
	}

	@RequestMapping(value = "commentBak")
	public String commentBak(Integer postId, ModelMap model) {
		// List<PostSector> postSectors = postSectorService.getAlls("发布类型");
		// model.addAttribute("postSectors", postSectors);
		model.addAttribute("postId", postId);
		return "/weixin/post/comment";
	}

	@RequestMapping(value = "getData")
	public String getData(Integer type, Integer factionId, Integer sectorId, Integer memberId, Integer minRow,
			HttpServletResponse response) {
		logger.info("factionId = " + factionId + " ; sectorId = " + sectorId + " ; memberId = " + memberId
				+ " ; type = " + type + " ; minRow = " + minRow);
		Pager<Post> pager = null;
		if (type == PostConstant.VIEW_POST_TYPE_MEMBER) {
			if (memberId == null) {
				memberId = memberService.getCurrent().getId();
			}
			pager = postService.findPosts(null, null, memberId, null, minRow, memberService.getCurrent());
		} else {
			if (sectorId == PostConstant.SECTOR_ACTIVITY_KEY) {
				pager = postService.findPosts(null, factionId, null, 2, minRow, memberService.getCurrent());
			} else {
				pager = postService.findPosts(sectorId, factionId, null, null, minRow, memberService.getCurrent());
			}
		}

		Map<String, String[]> map = new HashMap<String, String[]>();

		String[] postF = { "title", "id", "content", "member", "postSector", "postImages", "postPraises",
				"beginDateStr", "endDateStr", "type", "area", "postSectorName", "postPraiseCount", "showDate",
				"postCommentCount", "postImagesStr", "praised", "badge" };
		map.put("post", postF);
		// String[] postCommentF = { "content", "member", "showDate" };
		// map.put("post_comment", postCommentF);
		String[] memberF = { "realname", "profileImage", "faction", "id" };
		map.put("member", memberF);
		String[] factionF = { "name" };
		map.put("faction", factionF);
		String[] postSectorF = { "name" };
		map.put("postSector", postSectorF);
		String[] postImagesF = { "name", "path" };
		map.put("post_image", postImagesF);
		String[] postPraisesF = { "postId" };
		map.put("post_praise", postPraisesF);

		String content = JsonUtil.toJsonIncludeProperties(pager, map);
		return ajaxJson(content, response);
	}

	@RequestMapping(value = "comment")
	@ResponseBody
	public WeChatMessage comment(Integer postId, String content, boolean needResult, ModelMap model) {
		return postCommentService.doComment(memberService.getCurrent().getId(), postId, content);
	}

	@RequestMapping(value = "reply")
	@ResponseBody
	public WeChatMessage reply(Integer postId, Integer replyCommentId, String content, ModelMap model) {
		return postCommentService.doComment(memberService.getCurrent().getId(), postId, replyCommentId, content);
	}

	@RequestMapping(value = "getComments")
	public String getComments(Integer postId, HttpServletResponse response) {
		List<PostComment> ps = postCommentService.getComments(postId, memberService.getCurrent());
		for (int i = 0; i < ps.size(); i++) {
			PostComment pc = ps.get(i);
			if (memberService.getCurrent() != null && pc.getMember().getId().equals(memberService.getCurrent().getId())) {
				pc.setBelong(true);
			}
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] postCommentF = { "content", "member", "showDate", "belong", "id", "praised", "postId", "replyMember" };
		map.put("post_comment", postCommentF);
		String[] memberF = { "realname", "profileImage", "faction", "id" };
		map.put("member", memberF);
		String[] factionF = { "name" };
		map.put("faction", factionF);
		String content = JsonUtil.toJsonIncludeProperties(ps, map);
		return ajaxJson(content, response);
	}

	@RequestMapping(value = "getPraises")
	public String getPraises(Integer postId, HttpServletResponse response) {
		List<PostPraise> ps = postPraiseService.getPraises(postId);
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] postPraisesF = { "postId", "member" };
		map.put("post_praise", postPraisesF);
		String[] memberF = { "realname", "profileImage", "faction", "id" };
		map.put("member", memberF);
		String[] factionF = { "name" };
		map.put("faction", factionF);
		String content = JsonUtil.toJsonIncludeProperties(ps, map);
		return ajaxJson(content, response);
	}

	@RequestMapping(value = "getJoins")
	public String getJoins(Integer postId, HttpServletResponse response) {
		List<PostJoin> ps = postJoinService.getJoins(postId);
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] postPraisesF = { "postId", "member" };
		map.put("post_join", postPraisesF);
		String[] memberF = { "realname", "profileImage", "faction", "id" };
		map.put("member", memberF);
		String[] factionF = { "name" };
		map.put("faction", factionF);
		String content = JsonUtil.toJsonIncludeProperties(ps, map);
		return ajaxJson(content, response);
	}

	// @RequestMapping(value = "commentSuccess", method = { RequestMethod.POST
	// })
	// public String commentSuccess(Integer postId, String content, ModelMap
	// model) {
	// logger.info(" content = " + content + " ;");
	// // Post post = new Post();
	// // post.setId(postId);
	// PostComment postComment = new PostComment();
	// postComment.setPostId(postId);
	// postComment.setStatus(PostConstant.PostStatus.active);
	// Member member = new Member();
	// member.setId(TestValue.TestUser);
	// postComment.setMember(member);
	// postComment.setContent(content);
	// postComment.setCreateDate(new Date());
	// postComment.setModifyDate(new Date());
	// Integer result = postCommentService.save(postComment);
	// if (result > 0) {
	// System.out.println("success");
	// }
	// return "redirect:/weixin/post/view/" + postId + ".shtml";
	// }

	@RequestMapping(value = "view")
	public String view(Integer postId, ModelMap model, HttpServletRequest request) throws AesException {
		initWechatSign(model, request);
		Post post = postService.get(postId);
		Member member = memberService.getCurrent();
		postService.updateBadegByPost(postId);
		String baseUrl = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + request.getRequestURI() + "?postId="
				+ postId;
		String shareUrl = WechatUtil.getAuthUrl(URLEncoder.encode(baseUrl));
		// System.out.println(shareUrl);
		model.addAttribute("shareUrl", shareUrl);
		if (member == null) {
			model.addAttribute("isLogin", true);
			model.addAttribute("isPraise", false);
			model.addAttribute("isNotPraise", false);
			model.addAttribute("isJoin", false);
			model.addAttribute("isNotJoin", false);
			model.addAttribute("isDelete", false);
			model.addAttribute("isComment", false);

			String redirectUrl = "?" + "redirectUrl" + "=" + URLEncoder.encode(baseUrl);
			redirectUrl = request.getContextPath() + WechatLoginInterceptor.loginPage + redirectUrl;
			// System.out.println(redirectUrl);
			model.addAttribute("redirectUrl", redirectUrl);

		} else {

			boolean isDelete = false;
			boolean isPraise = !postPraiseService.isPraise(memberService.getCurrent().getId(), postId);
			boolean isNotPraise = !isPraise;
			boolean isJoin = false;
			boolean isNotJoin = false;
			if (post.getType() == 2) {
				isJoin = !postJoinService.isJoin(member.getId(), postId);
				isNotJoin = !isJoin;
			}

			if (post.getMember().getId().equals(memberService.getCurrent().getId())) {
				isDelete = true;
			}
			model.addAttribute("isLogin", false);
			model.addAttribute("isPraise", isPraise);
			model.addAttribute("isNotPraise", isNotPraise);
			model.addAttribute("isJoin", isJoin);
			model.addAttribute("isNotJoin", isNotJoin);
			model.addAttribute("isDelete", isDelete);
			model.addAttribute("isComment", true);
		}
		model.addAttribute("post", post);
		// if (post.getType() == 1) {
		return "/weixin/post/view";
		// } else {
		// return "/weixin/post/viewActivity";
		// }
	}

	@RequestMapping(value = "notice")
	public String notice(Integer id, ModelMap model) throws AesException {
		PostNotice postNotice = postNoticeService.get(id);
		model.put("postNotice", postNotice);
		// if (post.getType() == 1) {
		return "/weixin/post/notice";
		// } else {
		// return "/weixin/post/viewActivity";
		// }
	}

	// public String ajaxPost(Object obj, HttpServletResponse response) {
	// try {
	// response.setContentType("text/html" + ";charset=UTF-8");
	// response.setHeader("Pragma", "No-cache");
	// response.setHeader("Cache-Control", "no-cache");
	// response.setDateHeader("Expires", 0);
	//
	//
	// // ObjectMapper mapper = new ObjectMapper();
	// // mapper.writeValue(response.getOutputStream(), list);
	// response.getWriter().write(content);
	// response.getWriter().flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// // JsonConfig jsonConfig = new JsonConfig();
	// // jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	// // JSONArray jsonArray = JSONArray.fromObject(list);
	//
	// }
}
