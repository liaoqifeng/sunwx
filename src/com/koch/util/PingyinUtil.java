package com.koch.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PingyinUtil {

	public static String getFirstLetter(String s) {
		if (s != null && s.length() > 0) {
			char a = s.charAt(0);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(a);
			if (pinyinArray != null && pinyinArray.length > 0) {
				return new Character(pinyinArray[0].charAt(0)).toString().toUpperCase();
			} else {
				if (!(a >= 'A' && a <= 'Z') && !(a >= 'a' && a <= 'z')) {
					a = '！';
				}

				return new Character(a).toString().toUpperCase();
			}

		}
		return "";

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getFirstLetter("y"));

	}

}
