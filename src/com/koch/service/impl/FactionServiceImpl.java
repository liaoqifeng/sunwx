package com.koch.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.bean.OrderBy;
import com.koch.dao.FactionDao;
import com.koch.entity.Faction;
import com.koch.entity.PostSector;
import com.koch.service.FactionService;
import com.koch.util.PostConstant;

@Service
public class FactionServiceImpl extends BaseServiceImpl<Faction> implements FactionService{

	@Override
	public List<Faction> getAlls() {
		List<Faction> all = getAll(new OrderBy("index"));
		Faction p= new Faction();
		p.setId(-1);
		p.setName("全部帮派");
		all.add(0,p);
		return all;
	}
	
	
	@Resource
	public void setBaseDao(FactionDao factionDao) {
		super.setBaseDao(factionDao);
	}
	
	@Override
	public String getName(Integer id) {
		String name = null;
		if (id == null || id ==-1) {
			name = "全部帮派";
		} else {
			Faction p = get(id);
			if (p != null) {
				name = p.getName();
			}
		}
		return name;
	}
	
	
	@Override
	public Map<Integer,String>  getFactionMap() {
		List<Faction> all = getAll(new OrderBy("index"));
		Map<Integer,String> r = new LinkedHashMap<Integer, String>();
		r.put(PostConstant.FACTION_ALL_KEY, PostConstant.FACTION_ALL_VALUE);
		for (int i = 0; i < all.size(); i++) {
			Faction p = all.get(i);
			r.put(p.getId(), p.getName());
		}
		return r;
	}
	
}
