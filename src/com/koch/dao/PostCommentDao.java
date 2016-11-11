package com.koch.dao;

import java.util.List;
import java.util.Map;

import com.koch.entity.Order;
import com.koch.entity.Post;
import com.koch.entity.PostComment;
import com.koch.entity.PostSector;

public interface PostCommentDao extends BaseDao<PostComment>{
	
	public List<Map> getCount(List<Integer> postIds);
	
	
	public void updateBadeg(Integer memberId);
	
	public void updateBadeg(Integer memberId,Integer id);
	
	public Long getBadgeCount(Integer memberId);
	
	public Integer getMaxId(Integer memberId);
	
	public List getBadgeCounts(List<Integer> postIds);
	
	public void updateBadegByPost(Integer postId);
}
