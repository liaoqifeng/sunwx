package com.koch.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.koch.bean.OrderBy;
import com.koch.bean.OrderBy.OrderType;
import com.koch.dao.AreaDao;
import com.koch.entity.Area;
import com.koch.service.AreaService;

@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService{
	@Resource
	private AreaDao areaDao;
	@Resource
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}
	
	@Cacheable(cacheName="area")
	public List<Area> findAll(){
		return areaDao.getAll(new OrderBy("orderList", OrderType.asc));
	}
	
	@Cacheable(cacheName="areaTree")
	public String getTreeJson(){
		List<Area> all = findAll();
		List<Area> roots = findRoots();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0;i<roots.size();i++){
			Area root = roots.get(i);
			if(i != 0){ sb.append(","); }
			sb.append("{");
			sb.append("\"id\":" + root.getId() + ",");
			sb.append("\"name\":\"" + root.getName() + "\",");
			List<Area> fc = getList(all, root.getId());
			if(fc != null && fc.size() > 0){
				sb.append("\"child\":[");
				for(int j=0;j<fc.size();j++){
					Area fa = fc.get(j);
					if(j != 0){ sb.append(","); }
					sb.append("{");
					sb.append("\"id\":" + fa.getId() + ",");
					sb.append("\"name\":\"" + fa.getName() + "\",");
					List<Area> wc = getList(all, fa.getId());
					if(wc != null && wc.size() > 0){
						sb.append("\"child\":[");
						for(int m=0;m<wc.size();m++){
							Area wa = wc.get(m);
							if(m != 0){ sb.append(","); }
							sb.append("{");
							sb.append("\"id\":" + wa.getId() + ",");
							sb.append("\"name\":\"" + wa.getName() + "\"");
							sb.append("}");
						}
						sb.append("]");
					}else{
						sb.append("\"child\":[]");
					}
					sb.append("}");
				}
				sb.append("]");
			}else{
				sb.append("\"child\":[]");
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Transactional
	@TriggersRemove(cacheName={"area","areaTree"},removeAll=true)
	public Integer save(Area t) {
		return super.save(t);
	}
	
	@Transactional
	@TriggersRemove(cacheName={"area","areaTree"},removeAll=true)
	public Area update(Area t) {
		return super.update(t);
	}
	
	@Transactional
	@TriggersRemove(cacheName={"area","areaTree"},removeAll=true)
	public void delete(String id) {
		super.delete(id);
	}
	
	public List<Area> getList(Integer parentId) {
		return getList(findAll(),parentId);
	}
	
	public List<Area> findRoots(){
		return findRoots(null);
	}
	
	public List<Area> findRoots(Integer count){
		return this.areaDao.findRoots(count);
	}
	
	public List<Area> getList(List<Area> list, Integer parentId) {
		List<Area> areas = new ArrayList<Area>();
		if(list != null && list.size()>0){
			for(Area area : list){
				if(parentId == null && area.getParent() == null){
					areas.add(area);
				}else if(parentId != null && area.getParent() != null && parentId.equals(area.getParent().getId())){
					areas.add(area);
				}
			}
		}
		return areas;
	}
	
}
