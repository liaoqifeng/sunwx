package com.gson.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

import com.gson.WeChat;
import com.koch.entity.Member;
import com.koch.util.GlobalConstant;

public class WechatUtil {

	private static Date atLastTime = new Date();

	private static Date jsLastTime = new Date();

	private static String access_token = null;

	private static String js_ticket = null;

	public static String TIMESTAMP = "1430801695";

	public static String NONCESTR = "qwertyuiop";
	
	public static Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Member member = (Member) request.getSession().getAttribute(GlobalConstant.MEMBER_SESSION_USER);
			if (member != null) {
				return member;// this.memberDao.get(1);
			}
		}
		return null;// this.memberDao.get(1);
	}

	public static String getAuthUrl(String baseurl) {
		// baseurl = URLEncoder.encode(baseurl);
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ConfKit.get("AppId")
				+ "&redirect_uri=" + baseurl + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
		return url;
	}

	public static String getAuthUserInfoUrl() {
		// baseurl = URLEncoder.encode(baseurl);
		String baseurl = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunwx/weixin/regedit3.shtml";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ConfKit.get("AppId")
				+ "&redirect_uri=" + baseurl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		return url;
	}

	public static String getAccessToken() {
		try {
			if (access_token == null) {
				access_token = WeChat.getAccessToken();
				atLastTime = new Date();
			} else {
				Date d = new Date();
				long t = (d.getTime() - atLastTime.getTime()) / 1000;
				System.out.println(t);
				if (t > 7000) {
					access_token = WeChat.getAccessToken();
					atLastTime = d;
				}
			}
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static String getJsTicket() {
		try {
			if (js_ticket == null) {
				js_ticket = WeChat.getJsTicket(getAccessToken());
				jsLastTime = new Date();
			} else {
				Date d = new Date();
				long t = (d.getTime() - jsLastTime.getTime()) / 1000;
				System.out.println(t);
				if (t > 7000) {
					js_ticket = WeChat.getJsTicket(getAccessToken());
					jsLastTime = d;
				}
			}
			return js_ticket;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	// public static HttpURLConnection getMediaInputStream(String mediaId) {
	// String accessToken = WechatUtil.getAccessToken();
	// HttpURLConnection http = null;
	// String url =
	// "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" +
	// accessToken + "&media_id="
	// + mediaId;
	// try {
	// URL urlGet = new URL(url);
	// http = (HttpURLConnection) urlGet.openConnection();
	// http.setRequestMethod("GET"); // 必须是get方式请求
	// http.setRequestProperty("Content-Type",
	// "application/x-www-form-urlencoded");
	// http.setDoOutput(true);
	// http.setDoInput(true);
	// System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//
	// 连接超时30秒
	// System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //
	// 读取超时30秒
	// http.connect();
	// // 获取文件转化为byte流
	// // is = http.getInputStream();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return http;
	//
	// }

	public static HttpURLConnection getInputStream(String url) {

		HttpURLConnection http = null;
		// String url =
		// "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" +
		// accessToken + "&media_id="
		// + mediaId;
		try {
			URL urlGet = new URL(url);
			http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			// is = http.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return http;

	}

	/**
	 * 
	 * 获取下载图片信息（jpg）
	 * 
	 * 
	 * 
	 * @param mediaId
	 * 
	 *            文件的id
	 * 
	 * @throws Exception
	 */

	public static String saveMediaImageToDisk(String mediaId, String file) throws Exception {
		String accessToken = WechatUtil.getAccessToken();
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id="
				+ mediaId;
		return saveImageToDisk(url, file);
	}

	public static String saveImageToDisk(String url, String file) throws Exception {
		return saveImageToDisk(url, file, true);
	}

	public static String saveImageToDisk(String url, String file, boolean needSuffix) throws Exception {

		HttpURLConnection conn = getInputStream(url);
		String ds = conn.getHeaderField("Content-disposition");
		String suffix = "";
		if (ds != null) {
			String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
			String relName = fullName.substring(0, fullName.lastIndexOf("."));
			suffix = fullName.substring(relName.length() + 1);
		}
		InputStream inputStream = conn.getInputStream();
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			if (needSuffix) {
				fileOutputStream = new FileOutputStream(file + "." + suffix);
			} else {
				fileOutputStream = new FileOutputStream(file);
			}
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return suffix;
	}

	public static String getSignature(String url) throws AesException {
		String s = "jsapi_ticket=" + getJsTicket() + "&noncestr=" + NONCESTR + "&timestamp=" + TIMESTAMP + "&url="
				+ url;
		s = SHA1.getSHA1(s);
		return s;
	}

	public static String getCurrentTimestamp() {
		return String.valueOf(new Date().getTime() / 1000);
	}
	
	/**     
	 * * 发起https请求并获取结果     *      
	 * * @param requestUrl 请求地址    
	 *  * @param requestMethod 请求方式（GET、POST）    
	 *   * @param outputStr 提交的数据     
	 *   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)     
	*/
	public static Map long2Short(String accessToken, String jsonMsg) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WechatUtil.httpRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			return parserToMap(jsonObject.toString());
		}
		return null;
	}
	
	public static Map createQrcode(String accessToken, String jsonMsg) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WechatUtil.httpRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			return parserToMap(jsonObject.toString());
		}
		return null;
	}

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
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
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}
	
	 public static Map parserToMap(String s){
		Map map=new HashMap();
		JSONObject json=JSONObject.fromObject(s);
		Iterator keys=json.keys();
		while(keys.hasNext()){
			String key=(String) keys.next();
			String value=json.get(key).toString();
			if(value.startsWith("{")&&value.endsWith("}")){
				map.put(key, parserToMap(value));
			}else{
				map.put(key, value);
			}

		}
		return map;
	}
	 
	public static Map<String, String> getWxConfig(String url) throws AesException{
		String signature = getSignature(url);
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", ConfKit.get("AppId"));
		params.put("timestamp", WechatUtil.TIMESTAMP);
		params.put("nonceStr", WechatUtil.NONCESTR);
		params.put("signature", signature);
		return params;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getCurrentTimestamp());
		String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"http://www.baidu.com\"}";
		System.out.println(WechatUtil.getAccessToken());
		Map map = WechatUtil.long2Short(WeChat.getAccessToken(), jsonMsg);
		System.out.println(map);
		
	}

}
