import java.io.UnsupportedEncodingException;

public class T {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("中文");

		System.out.println("中文".getBytes());

		System.out.println("中文".getBytes("GB2312"));

		System.out.println("中文".getBytes("ISO8859_1"));

		System.out.println(new String("中文".getBytes()));

		System.out.println(new String("中文".getBytes(), "GB2312"));

		System.out.println(new String("中文".getBytes(), "ISO8859_1"));

		System.out.println(new String("中文".getBytes("GB2312")));

		System.out.println(new String("中文".getBytes("GB2312"), "GB2312"));

		System.out.println(new String("中文".getBytes("GB2312"), "ISO8859_1"));

		System.out.println(new String("中文".getBytes("ISO8859_1")));

		System.out.println(new String("中文".getBytes("ISO8859_1"), "GB2312"));

		System.out.println(new String("中文".getBytes("ISO8859_1"), "ISO8859_1"));

	}

}
