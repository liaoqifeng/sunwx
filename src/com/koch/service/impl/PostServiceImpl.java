package com.koch.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gson.util.WechatUtil;
import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.dao.MemberDao;
import com.koch.dao.PostBadgeDao;
import com.koch.dao.PostCommentDao;
import com.koch.dao.PostDao;
import com.koch.dao.PostPraiseDao;
import com.koch.dao.PostScoreDao;
import com.koch.dao.PostSectorDao;
import com.koch.dao.TaskDao;
import com.koch.dao.TaskInfoDao;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostBadge;
import com.koch.entity.PostImage;
import com.koch.entity.PostScore;
import com.koch.entity.PostSector;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;
import com.koch.exception.SunwxException;
import com.koch.service.PostService;
import com.koch.util.PostConstant.PostStatus;
import com.koch.util.PropertiesUtil;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {
	@Value("${image.project}")
	private String imageProject;

	@Value("${system.project}")
	private String project;

	@Resource
	private PostDao postDao;

	@Resource
	private PostPraiseDao postPraiseDao;

	@Resource
	private PostSectorDao postSectorDao;

	@Resource
	private PostCommentDao postCommentDao;

	@Resource
	private PostScoreDao postScoreDao;

	@Resource
	private PostBadgeDao postBadgeDao;

	@Resource
	private MemberDao memberDao;

	@Resource
	private TaskInfoDao taskInfoDao;

	@Resource
	public TaskDao taskDao;

	@Override
	@Transactional
	public Pager<Post> findFirstPosts(Integer postSectorId, Integer factionId, Integer memberId, Integer postType,
			Integer minRow, Member currentMember) {
		PostBadge postBadge = postBadgeDao.get("member", currentMember);
		if (postBadge != null) {
			postCommentDao.updateBadeg(currentMember.getId(), postBadge.getPostCommentId());
			postPraiseDao.updateBadeg(currentMember.getId(), postBadge.getPostPraiseId());
		}
		if (memberId != null && memberId.equals(currentMember.getId())) {
			saveBadge(currentMember);
		}
		return findPosts(postSectorId, factionId, memberId, postType, minRow, currentMember);
	}

	@Override
	@Transactional
	public void saveBadge(Member currentMember) {
		PostBadge postBadge = postBadgeDao.get("member", currentMember);
		if (postBadge == null) {
			postBadge = new PostBadge();
			postBadge.setMember(currentMember);
		}
		Integer commentId = postCommentDao.getMaxId(currentMember.getId());
		Integer praiseId = postPraiseDao.getMaxId(currentMember.getId());
		postBadge.setPostCommentId(commentId);
		postBadge.setPostPraiseId(praiseId);
		postBadgeDao.save(postBadge);

	}

	@Override
	public Pager<Post> findPosts(Integer postSectorId, Integer factionId, Integer memberId, Integer postType,
			Integer minRow, Member currentMember) {

		Pager<Post> pager = postDao.findList(postSectorId, factionId, memberId, postType, minRow);

		List<Integer> postIds = new ArrayList<Integer>();
		if (pager.getList() != null && pager.getList().size() > 0) {
			for (int i = 0; i < pager.getList().size(); i++) {
				Post p = (Post) pager.getList().get(i);
				if (p.getContent() != null && p.getContent().length() > 100) {
					p.setContent(p.getContent().substring(0, 99) + "...");
				}
				postIds.add(p.getId());

			}

			List<PostSector> all = postSectorDao.getAll(new OrderBy("index"));
			Map<Integer, String> r = new HashMap<Integer, String>();
			for (int i = 0; i < all.size(); i++) {
				PostSector p = all.get(i);
				r.put(p.getId(), p.getName());
			}

			List l2 = postCommentDao.getCount(postIds);
			Map<Integer, Long> r2 = new HashMap<Integer, Long>();
			for (int i = 0; i < l2.size(); i++) {
				Object[] m = (Object[]) l2.get(i);
				r2.put((Integer) m[0], (Long) m[1]);
			}
			List l1 = postPraiseDao.getCount(postIds);
			Map<Integer, Long> r1 = new HashMap<Integer, Long>();
			for (int i = 0; i < l1.size(); i++) {
				Object[] m = (Object[]) l1.get(i);
				r1.put((Integer) m[0], (Long) m[1]);
			}

			List<Integer> l3 = postPraiseDao.getPraises(postIds, currentMember);

			Map<Integer, Long> r4 = null;
			Map<Integer, Long> r5 = null;
			if (memberId != null && memberId.equals(currentMember.getId())) {
				List l4 = postCommentDao.getBadgeCounts(postIds);
				r4 = new HashMap<Integer, Long>();
				for (int i = 0; i < l4.size(); i++) {
					Object[] m = (Object[]) l4.get(i);
					r4.put((Integer) m[0], (Long) m[1]);
				}

				List l5 = postPraiseDao.getBadgeCounts(postIds);
				r5 = new HashMap<Integer, Long>();
				for (int i = 0; i < l5.size(); i++) {
					Object[] m = (Object[]) l5.get(i);
					r5.put((Integer) m[0], (Long) m[1]);
				}

			}

			for (int i = 0; i < pager.getList().size(); i++) {
				Post p = (Post) pager.getList().get(i);
				if (p.getPostSector() != null && p.getPostSector().getId() != null) {
					p.setPostSectorName(r.get(p.getPostSector().getId()));
				}
				Long count2 = r2.get(p.getId());
				if (count2 == null) {
					count2 = 0L;
				}
				p.setPostCommentCount(count2);

				Long count1 = r1.get(p.getId());
				if (count1 == null) {
					count1 = 0L;
				}
				p.setPostPraiseCount(count1);

				if (l3.contains(p.getId())) {
					p.setPraised(true);
				}
				if (r4 != null) {
					Long count4 = r4.get(p.getId());
					if (count4 != null) {
						p.setBadge(p.getBadge() + count4);
					}
				}

				if (r5 != null) {
					Long count5 = r5.get(p.getId());
					if (count5 != null) {
						p.setBadge(p.getBadge() + count5);
						// System.out.println(p.getId() + "   " + p.getBadge());
					}
				}
			}
		}

		return pager;
	}

	@Resource
	public void setBaseDao(PostDao postDao) {
		super.setBaseDao(postDao);
	}

	@Override
	@Transactional
	public Post deletePost(Integer postId) {
		Post p = get(postId);
		p.setModifyDate(new Date());
		p.setStatus(PostStatus.disable);
		p = getBaseDao().update(p);
		return p;
	}

	@Override
	@Transactional
	public Integer addPost(Post post, String ps, String path) {
		try {

			// String allPath = "";
			List<PostImage> pis = new ArrayList<PostImage>();
			if (ps != null && !"".equals(ps)) {
				String[] pics = ps.split(";");
				String dir = path;
				dir = dir.replaceAll(project, imageProject);
				File dirf = new File(dir);
				if (!dirf.exists()) {
					dirf.mkdirs();
				}
				for (int i = 0; i < pics.length; i++) {
					PostImage pi = new PostImage();
					String fpath = dir + File.separator + pics[i];
					String su = WechatUtil.saveMediaImageToDisk(pics[i], fpath);
					pi.setIndex(i);
					pi.setCreateDate(new Date());
					pi.setModifyDate(new Date());
					pi.setName(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/" + imageProject
							+ "/upload/post/" + pics[i] + "." + su);
					pis.add(pi);
					// if (i == 0) {
					// allPath += pics[i] + "." + su;
					// } else {
					// allPath += ";" + pics[i] + "." + su;
					// }
				}

			}
			post.setPostImages(pis);
			Integer postId = getBaseDao().save(post);
			if (postId > 0) {
				PostScore postScore = postScoreDao.getPostScore(post.getMember());
				int cj = Integer.parseInt(PropertiesUtil.getProperty(PropertiesUtil.POST_ALL_SCORE))
						- postScore.getScore();
				int add = Integer.parseInt(PropertiesUtil.getProperty(PropertiesUtil.POST_ADD_SCORE));
				if (add > cj) {
					add = cj;
				}
				Member m = memberDao.get(post.getMember().getId());
				if (add > 0) {
					
					m.setScore(m.getScore() + add);
					postScore.setScore(postScore.getScore() + add);
					memberDao.save(m);
					postScoreDao.save(postScore);
//					if (cj == add) {
//						
//					}
				}
				completeTask(m, 3);
			}
			return postId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SunwxException("保存失败");
		}
	}

	private void completeTask(Member member, Integer taskId) {
		boolean flag = true;
		Task task = taskDao.get(taskId);
		Calendar c = Calendar.getInstance();
		Date beginDate = null, endDate = null;
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		if (task.getBeginTime() != null) {
			if (hour < task.getBeginTime()) {
				flag = false;
			}
			c.set(Calendar.HOUR_OF_DAY, task.getBeginTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			beginDate = c.getTime();
		}
		if (flag) {
			if (task.getEndTime() != null) {
				if (hour > task.getEndTime()) {
					flag = false;
				}
				c.set(Calendar.HOUR_OF_DAY, task.getEndTime());
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				endDate = c.getTime();
			}
		}
		if (flag) {

			List<TaskInfo> taskInfos = taskInfoDao.findList(beginDate, endDate, task, member);
			if (task.getCount() != null) {
				if (taskInfos.size() >= task.getCount()) {
					flag = false;
				}
			}
		}
		if (flag) {
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setScore(member.getScore() + task.getScore());
			memberDao.update(member);

			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setMember(member);
			taskInfo.setTask(task);
			taskInfoDao.save(taskInfo);
		}

	}

	@Override
	@Transactional
	public void updateBadeg(Integer memberId) {
		postCommentDao.updateBadeg(memberId);
		postPraiseDao.updateBadeg(memberId);

	}

	@Override
	@Transactional
	public void updateBadegByPost(Integer postId) {
		postCommentDao.updateBadegByPost(postId);
		postPraiseDao.updateBadegByPost(postId);

	}

	@Override
	public Long getBadgeCount(Integer memberId) {
		long c1 = postCommentDao.getBadgeCount(memberId);
		long c2 = postPraiseDao.getBadgeCount(memberId);
		long c = c1 + c2;
		// postCommentDao.updateBadeg(memberId);
		return c;
	}
}
