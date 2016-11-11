package com.gson.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class ContantValue {

	public static final String PAYMENT_TYPE_NEEDPLUS = "05";
	
	public static final String PAYMENT_STATUS_NOPAY = "01";

	public static final String PAYMENT_STATUS_CLEARING_FAIL = "02";
	
	public static final String PAYMENT_STATUS_PAYED = "00";
	
	public static final String STATUS_INUSE = "00";

	public static final String SESSION_USER = "session_user";

	public static final String SESSION_OPENID = "session_openid";

	public static final String test_cid = "1";

	public static final String MENU_ARTICLE = "article";
	
	public static final String EVENT_SUBSCRIBE = "subscribe";
	
	public static final String EVENT_CLICK = "CLICK";

	
	public static final String UPLOAD_PIC_DIR =  "uploadPic";
	
	public static final String ROLE_TYPE_HZ = "00";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static Map<String, String> initCardTypeMap()  {
		Map<String, String> cardTypeMap = new LinkedHashMap<String, String>();
		cardTypeMap.put("10", "个人借记");
//		cardTypeMap.put("20", "个人贷记");
		return cardTypeMap;
	}

	public static Map<String, String> initIdentificationTypeMap() {
		Map<String, String> cardTypeMap = new LinkedHashMap<String, String>();
		cardTypeMap.put("0", "身份证");
		cardTypeMap.put("1", "户口薄");
		cardTypeMap.put("2", "护照");
		cardTypeMap.put("3", "军官证");
		cardTypeMap.put("4", "士兵证");
		cardTypeMap.put("5", "港澳居民来往内地通行证");
		cardTypeMap.put("6", "台湾同胞来往内地通行证");
		cardTypeMap.put("7", "临时身份证");
		cardTypeMap.put("8", "外国人居留证");
		cardTypeMap.put("9", "警官证");
		cardTypeMap.put("X", "其他证件");
		return cardTypeMap;
	}

}
