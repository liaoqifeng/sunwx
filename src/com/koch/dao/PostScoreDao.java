package com.koch.dao;

import java.util.List;
import java.util.Map;

import com.koch.entity.Member;
import com.koch.entity.Order;
import com.koch.entity.Post;
import com.koch.entity.PostJoin;
import com.koch.entity.PostPraise;
import com.koch.entity.PostScore;
import com.koch.entity.PostSector;

public interface PostScoreDao extends BaseDao<PostScore> {
	public PostScore getPostScore(Member member);
	

}
