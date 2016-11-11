package com.gson.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public final static String SMS_CORPID = "SMS_CORPID";

	public final static String SMS_USER = "SMS_USER";

	public final static String SMS_PASSWORD = "SMS_PASSWORD";

	public final static String SMS_CONTEXT = "SMS_CONTEXT";

	public final static String BASE_URL = "BASE_URL";

	public final static String REPAIR_PIC_DIR = "REPAIR_PIC_DIR";

	public final static String HEAD_PIC_DIR = "HEAD_PIC_DIR";

	public final static String NOTICE_PIC_DIR = "NOTICE_PIC_DIR";

	public final static String INSTITUTION_ID = "INSTITUTION_ID";

	public final static String REPAIR_WEBSERVICE_URL = "REPAIR_WEBSERVICE_URL";

	public final static String FTP_URL = "FTP_URL";
	public final static String FTP_USERNAME = "FTP_USERNAME";
	public final static String FTP_PASSWORD = "FTP_PASSWORD";
	public final static String FTP_DIR = "FTP_DIR";

	// public final static String PEDUNCLECODE= "peduncleCode";
	// public final static String DILATANTCODE= "dilatantCode";

	private static final Properties properties = new Properties();

	static {
		String filename = "/sunwx.properties";
		InputStream stream = PropertiesUtil.class.getResourceAsStream(filename);
		if (stream == null) {
			throw new RuntimeException("can not load " + filename);
		} else {
			try {
				properties.load(stream);
			} catch (Exception e) {
				throw new RuntimeException("can not load " + filename, e);
			}
		}
	}

	public static Properties getProperties() {
		Properties copy = new Properties();
		copy.putAll(properties);
		return copy;
	}

	public static String getProperty(String name) {
		return properties.getProperty(name);
	}

}
