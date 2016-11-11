package com.wxapi.service;

import net.sf.json.JSONObject;

import com.wxapi.vo.MsgRequest;

/**
 * 
 */

public interface MyService {
	
	//消息处理
	public String processMsg(MsgRequest msgRequest);

	//消息处理
	public String processMsg(MsgRequest msgRequest,String appId, String appSecret);
	
	//发布菜单
	public JSONObject publishMenu(String gid,String appId, String appSecret);
	
	//删除菜单
	public JSONObject deleteMenu(String appId, String appSecret);
	
	//上传图文消息
	public JSONObject uploadMsgNews(String[] newIds,String appId,String appSecret);
	
	//群发消息
	public JSONObject sendMsgNewsAll(String[] newIds,String appId,String appSecret);
	
	
	
}
