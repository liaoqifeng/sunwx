package com.koch.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;

import com.gson.util.PropertiesUtil;

public class SmsUtil {

	private static String url = "http://sms3.mobset.com/SDK/Sms_Send.asp";

	public static int SMSsend(String context, String send_no) {

		String CorpID = PropertiesUtil.getProperty(PropertiesUtil.SMS_CORPID);
		String LoginName = PropertiesUtil.getProperty(PropertiesUtil.SMS_USER);
		String passwd = PropertiesUtil.getProperty(PropertiesUtil.SMS_PASSWORD);
		String urls = "", txt = "";
		urls = url + "?CorpID=300230" + "&LoginName=" + LoginName + "&passwd=" + passwd + "&send_no=" + send_no
				+ "&Timer=&msg=" + context + "";
		txt = send(urls);
		System.out.println(txt);
		String[] ts = txt.split(",");
		int result = Integer.parseInt(ts[0]);
		return result;
	}
	
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return "";
	}

	private static String send(String url) {
		String result = "";
		try {
			URL U = new URL(url);
			URLConnection connection = U.openConnection();
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {
			System.out.println("发送短信失败" + e);
			// result = "0";
		}
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		//String context = PropertiesUtil.getProperty(PropertiesUtil.SMS_CONTEXT);
		//context = context.replaceAll("!code!", "1111");
		int r = SMSsend("您正在注册阳光汇会员，验证码：1111，如非本人操作，无需担心，对方无法注册", "13585883231");
		//System.out.println(context);
		System.out.println(r);
	}
}