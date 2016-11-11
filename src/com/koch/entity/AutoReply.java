package com.koch.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="auto_reply")
public class AutoReply extends BaseEntity{
	private static final long serialVersionUID = -5636102881436964174L;
	
	private String keywords;
	private String content;
	
	@Column
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
