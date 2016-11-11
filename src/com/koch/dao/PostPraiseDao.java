package com.koch.dao;

import java.util.List;
import java.util.Map;

import com.koch.entity.Member;
import com.koch.entity.PostPraise;

public interface PostPraiseDao extends BaseDao<PostPraise> {

	public List<Map> getCount(List<Integer> postIds);

	public List<Integer> getPraises(List<Integer> postIds, Member member);

	public void updateBadeg(Integer memberId);

	public Long getBadgeCount(Integer memberId);

	public List getBadgeCounts(List<Integer> postIds);

	public void updateBadeg(Integer memberId, Integer id);

	public Integer getMaxId(Integer memberId);
	
	public void updateBadegByPost(Integer postId);

}
