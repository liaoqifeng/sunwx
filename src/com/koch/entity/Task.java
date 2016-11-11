package com.koch.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="task")
public class Task extends BaseEntity{
	private static final long serialVersionUID = 6663742106859112647L;
	
	public enum TaskType{
		scan, forward, send;
	}
	public enum TaskCycle{
		everyDay,everyWeek,everyMonth,everyYear;
	}
	
	private String title;
	private String content;
	private String url;
	private String imgUrl;
	private TaskType type;
	private TaskCycle cycle;
	private Integer count;
	private Integer score;
	private String rules;
	private String require;
	private String image;
	private Integer beginTime;
	private Integer endTime;
	private String description;
	private String action;
	private String orderList;
	
	private Boolean isComplete;
	
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Enumerated
	@Column
	public TaskType getType() {
		return type;
	}
	public void setType(TaskType type) {
		this.type = type;
	}
	@Enumerated
	@Column
	public TaskCycle getCycle() {
		return cycle;
	}
	public void setCycle(TaskCycle cycle) {
		this.cycle = cycle;
	}
	@Column
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Column
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	@Column
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
	}
	@Column
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column
	public Integer getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
	}
	@Column
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	
	@Transient
	public Boolean getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	
}
