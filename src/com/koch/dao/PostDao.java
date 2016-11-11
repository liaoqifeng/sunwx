package com.koch.dao;

import com.koch.bean.Pager;
import com.koch.entity.Order;
import com.koch.entity.Post;

public interface PostDao extends BaseDao<Post>{
	public Pager<Post> findList(Integer postSectorId, Integer factionId, Integer memberId,Integer postType,Integer pageNumber);
}
