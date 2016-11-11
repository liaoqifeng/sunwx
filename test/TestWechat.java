import com.gson.WeChat;
import com.gson.oauth.User;


public class TestWechat {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		User user = new User();
		WeChat wc = new WeChat();
		user.getUserInfo(wc.getAccessToken(), "owV68vlN8mCqY_mlD9LJDXxSaE4c");

	}

}
