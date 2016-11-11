package com.koch.dao;

import java.util.List;
import java.util.Map;

import com.koch.entity.Member;
import com.koch.entity.PostCommentPraise;

public interface PostCommentPraiseDao extends BaseDao<PostCommentPraise> {

	public List<Map> getCount(List<Integer> postIds);
	
	public List<Integer> getPraises(List<Integer> postIds,Member member);
}
