package com.koch.bean;


public class WeChatMessage {
	public enum Type{
		success, warn, error;
	}
	public WeChatMessage(Type type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	
	public static WeChatMessage success(String content) {
		return new WeChatMessage(Type.success, content);
	}

	public static WeChatMessage warn(String content) {
		return new WeChatMessage(Type.warn, content);
	}

	public static WeChatMessage error(String content) {
		return new WeChatMessage(Type.error, content);
	}

	private Type type;
	private String content;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
