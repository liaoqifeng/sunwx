package com.koch.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.koch.util.DateUtil;
import com.koch.util.JsonUtil;


@Entity
@Table(name="dug_coin")
public class DugCoin extends BaseEntity{ 
	private static final long serialVersionUID = 7488597919885721459L;
	
	private Integer minScore;
	private Integer maxScore;
	private Integer lastTime;
	private String timeList;
	
	private List<String> timers = new ArrayList<String>();
	
	@Column
	public Integer getMinScore() {
		return minScore;
	}
	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}
	@Column
	public Integer getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	@Column
	public Integer getLastTime() {
		return lastTime;
	}
	public void setLastTime(Integer lastTime) {
		this.lastTime = lastTime;
	}
	@Column
	public String getTimeList() {
		return timeList;
	}
	public void setTimeList(String timeList) {
		this.timeList = timeList;
	}
	
	@Transient
	public List<String> getTimers() {
		if(StringUtils.isNotEmpty(getTimeList())){
			return JsonUtil.toObject(getTimeList(), ArrayList.class);
		}
		return timers;
	}
	public void setTimers(List<String> timers) {
		this.timers = timers;
	}
	@Transient
	public Date getCurrentPoint(){
		if(getTimers() != null && getTimers().size()>0){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = DateUtil.getCurrentDate();
			/*for (String t : getTimers()) {
				Date date1 = DateUtil.parseDate(df.format(new Date())+" "+t, "yyyy-MM-dd HH:mm:ss");
				Calendar c = Calendar.getInstance();
				c.setTime(date1);
				c.add(Calendar.MINUTE, getScore());
				Date date2 = c.getTime();
				if(date.after(date1) && date.before(date2)){
					return date1;
				}
			}*/
			for (String t : getTimers()) {
				Date date1 = DateUtil.parseDate(df.format(new Date())+" "+t, "yyyy-MM-dd HH:mm:ss");
				Calendar c = Calendar.getInstance();
				c.setTime(date1);
				c.add(Calendar.MINUTE, getLastTime());
				if(!c.getTime().after(date)){
					continue;
				}
				return date1;
			}
		}
		return null;
	}
}
