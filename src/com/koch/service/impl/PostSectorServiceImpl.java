package com.koch.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.bean.OrderBy;
import com.koch.dao.PostSectorDao;
import com.koch.entity.PostSector;
import com.koch.service.PostSectorService;
import com.koch.util.PostConstant;

@Service
public class PostSectorServiceImpl extends BaseServiceImpl<PostSector> implements PostSectorService {

	@Override
	public String getName(Integer id) {
		String name = null;
		if (id == null || id ==-1) {
			name = "全部板块";
		} else {
			PostSector p = get(id);
			if (p != null) {
				name = p.getName();
			}
		}
		return name;
	}

	@Resource
	public void setBaseDao(PostSectorDao postSectorDao) {
		super.setBaseDao(postSectorDao);
	}

	@Override
	public List<PostSector> getAlls(String name) {
		List<PostSector> all = getAll(new OrderBy("index"));
		PostSector p = new PostSector();
		p.setId(-1);
		p.setName(name);
		all.add(0,p);
		return all;
	}
	
	@Override
	public Map<Integer,String>  getSectorMap() {
		List<PostSector> all = getAll(new OrderBy("index"));
		Map<Integer,String> r = new LinkedHashMap<Integer, String>();
		r.put(PostConstant.SECTOR_ALL_KEY, PostConstant.SECTOR_ALL_VALUE);
		for (int i = 0; i < all.size(); i++) {
			PostSector p = all.get(i);
			r.put(p.getId(), p.getName());
		}
		r.put(PostConstant.SECTOR_ACTIVITY_KEY, PostConstant.SECTOR_ACTIVITY_VALUE);
//		System.out.println(r);
		return r;
	}

}
