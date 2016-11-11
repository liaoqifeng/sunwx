package com.wxapi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxapi.process.HttpMethod;
import com.wxapi.process.MsgType;
import com.wxapi.process.MsgXmlUtil;
import com.wxapi.process.WxAccountContext;
import com.wxapi.process.WxApi;
import com.wxapi.process.WxServiceProcess;
import com.wxapi.service.MyService;
import com.wxapi.vo.MsgRequest;
/**
 * 业务消息处理
 * 开发者根据自己的业务自行处理消息的接收与回复；
 */

@Service
public class MyServiceImpl implements MyService{

	
	private Integer msgCount = 5;//模式回复图文消息条数为5条
	
	
	/**
	 * 处理消息
	 * 开发者可以根据用户发送的消息和自己的业务，自行返回合适的消息；
	 * @param msgRequest : 接收到的消息
	 * @param Account ： 公众号对象
	 */
	public String processMsg(MsgRequest msgRequest){
		String appId = "";
		String appSecret = "";
		return this.processMsg(msgRequest,appId,appSecret);
	}
	
	
	/**
	 * 处理消息
	 * 开发者可以根据用户发送的消息和自己的业务，自行返回合适的消息；
	 * @param msgRequest : 接收到的消息
	 * @param appId ： appId
	 * @param appSecret : appSecret
	 */
	public String processMsg(MsgRequest msgRequest,String appId,String appSecret){
		String msgtype = msgRequest.getMsgType();//接收到的消息类型
		String respXml = null;//返回的内容；
		if(msgtype.equals(MsgType.Text.toString())){
			/**
			 * 文本消息，一般公众号接收到的都是此类型消息
			 */
			respXml = this.processTextMsg(msgRequest,appId,appSecret);
		}else if(msgtype.equals(MsgType.Event.toString())){//事件消息
			/**
			 * 用户订阅公众账号、点击菜单按钮的时候，会触发事件消息
			 */
			respXml = this.processEventMsg(msgRequest);
			
		//其他消息类型，开发者自行处理
		}else if(msgtype.equals(MsgType.Image.toString())){//图片消息
			
		}else if(msgtype.equals(MsgType.Location.toString())){//地理位置消息
			
		}
		
		//如果没有对应的消息，默认返回订阅消息；
		if(StringUtils.isEmpty(respXml)){
			respXml = MsgXmlUtil.textToXml(WxServiceProcess.getMsgResponseText(msgRequest, null));
		}
		return respXml;
	}
	
	//处理文本消息
	private String processTextMsg(MsgRequest msgRequest,String appId,String appSecret){
		String content = msgRequest.getContent();
		if(!StringUtils.isEmpty(content)){//文本消息
			String tmpContent = content.trim();
			return MsgXmlUtil.newsToXml(WxServiceProcess.getMsgResponseNews(msgRequest, null));
		}
		return null;
	}
	
	//处理事件消息
	private String processEventMsg(MsgRequest msgRequest){
		String key = msgRequest.getEventKey();
		if(MsgType.SUBSCRIBE.toString().equals(msgRequest.getEvent())){//订阅消息
			return MsgXmlUtil.textToXml(WxServiceProcess.getMsgResponseText(msgRequest, null));
		}else if(MsgType.UNSUBSCRIBE.toString().equals(msgRequest.getEvent())){//取消订阅消息
			return MsgXmlUtil.textToXml(WxServiceProcess.getMsgResponseText(msgRequest, null));
		}else{//点击事件消息
			if(!StringUtils.isEmpty(key)){
				/**
				 * 固定消息
				 * _fix_ ：在我们创建菜单的时候，做了限制，对应的event_key 加了 _fix_
				 * 
				 * 当然开发者也可以进行修改
				 */
				if(key.startsWith("_fix_")){
					String baseIds = key.substring("_fix_".length());
					if(!StringUtils.isEmpty(baseIds)){
						String[] idArr = baseIds.split(",");
						if(idArr.length > 1){//多条图文消息
							List msgNews = null;
							if(msgNews != null && msgNews.size() > 0){
								return MsgXmlUtil.newsToXml(WxServiceProcess.getMsgResponseNews(msgRequest, msgNews));
							}
						}else{//图文消息，或者文本消息
							return MsgXmlUtil.newsToXml(WxServiceProcess.getMsgResponseNews(msgRequest, null));
						}
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	//发布菜单
	public JSONObject publishMenu(String gid,String appId, String appSecret){
		JSONObject rstObj = WxServiceProcess.publishMenus(prepareMenus(null), appId, appSecret);
		return rstObj;
	}
	
	//删除菜单
	public JSONObject deleteMenu(String appId, String appSecret){
		JSONObject rstObj = WxServiceProcess.deleteMenu(appId,appSecret);
		
		return rstObj;
	}
	
	
	//上传图文消息
	public JSONObject uploadMsgNews(String[] newIds,String appId,String appSecret){
		List msgNewsList =null;
		return WxServiceProcess.uploadNews(msgNewsList, appId, appSecret);
	}
	
	//群发图文消息
	public JSONObject sendMsgNewsAll(String[] newIds,String appId,String appSecret){
		JSONObject rstObj = uploadMsgNews(newIds,appId,appSecret);
		if(rstObj.containsKey("media_id")){
			return WxServiceProcess.sendAll(rstObj.getString("media_id"), MsgType.MPNEWS.toString(), appId, appSecret);
		}else{
			return rstObj;
		}
	}

	//获取微信公众账号的菜单
	private String prepareMenus(List menus) {
		if(!CollectionUtils.isEmpty(menus)){
			/*List<AccountMenu> parentAM = new ArrayList<AccountMenu>();
			Map<Long,List<JSONObject>> subAm = new HashMap<Long,List<JSONObject>>();
			for(AccountMenu m : menus){
				if(m.getParentid() == 0L){//一级菜单
					parentAM.add(m);
				}else{//二级菜单
					if(subAm.get(m.getParentid()) == null){
						subAm.put(m.getParentid(), new ArrayList<JSONObject>());
					}
					List<JSONObject> tmpMenus = subAm.get(m.getParentid());
					tmpMenus.add(getMenuJSONObj(m));
					subAm.put(m.getParentid(), tmpMenus);
				}
			}
			JSONArray arr = new JSONArray();
			for(AccountMenu m : parentAM){
				if(subAm.get(m.getId()) != null){//有子菜单
					arr.add(getParentMenuJSONObj(m,subAm.get(m.getId())));
				}else{//没有子菜单
					arr.add(getMenuJSONObj(m));
				}
			}
			JSONObject root = new JSONObject();
			root.put("button", arr);
			return JSONObject.fromObject(root).toString();*/
		}
		return "error";
	}
	
	/**
	 * 此方法是构建菜单对象的；构建菜单时，对于  key 的值可以任意定义；
	 * 当用户点击菜单时，会把key传递回来；对已处理就可以了
	 * @param menu
	 * @return
	 */
	private JSONObject getMenuJSONObj(Object menu){
		JSONObject obj = new JSONObject();
		/*obj.put("name", menu.getName());
		obj.put("type", menu.getMtype());
		if("click".equals(menu.getMtype())){//事件菜单
			if("fix".equals(menu.getEventType())){//fix 消息
				obj.put("key", "_fix_" + menu.getMsgId());//以 _fix_ 开头
			}else{
				if(StringUtils.isEmpty(menu.getInputcode())){//如果inputcode 为空，默认设置为 subscribe，以免创建菜单失败
					obj.put("key", "subscribe");
				}else{
					obj.put("key", menu.getInputcode());
				}
			}
		}else{//链接菜单-view
			obj.put("url", menu.getUrl());
		}*/
		return obj;
	}
	
	private JSONObject getParentMenuJSONObj(Object menu,List<JSONObject> subMenu){
		JSONObject obj = new JSONObject();
		/*obj.put("name", menu.getName());
		obj.put("sub_button", subMenu);*/
		return obj;
	}
	
	
}


