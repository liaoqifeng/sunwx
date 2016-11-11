package com.koch.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

public class DateUtil {
	public DateUtil() {
	}

	public static String getShowDate(Date date) {
		String r = null;
		Date d = new Date();
		Long t = d.getTime() - date.getTime();
		if (t < 1000 * 60) {
			r = "刚刚";
		} else if (t < 1000 * 60 * 60) {
			r = t / (1000 * 60) + "分钟前";
		} else if (t < 1000 * 60 * 60 * 24) {
			r = t / (1000 * 60 * 60) + "小时前";
		} else {
			r = parseDate(date, "yyyy-MM-dd HH:mm");
		}
		return r;
	}

	public static long getCurrentTime() {
		// ��ȡ��ǰʱ��
		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();
		long currentTimeLong = currentTime.getTime();
		return currentTimeLong;
	}

	// public static String getCurrentTimeStr() {
	// // ��ȡ��ǰʱ��
	// Calendar cal = Calendar.getInstance();
	// Date currentTime = cal.getTime();
	// String s = parseDate(currentTime, "yyyy-MM-dd ")
	// return currentTimeLong;
	// }

	public static Date getCurrentDate() {
		// ��ȡ��ǰʱ��
		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();
		return currentTime;
	}

	public static Date getCurrentDateStr() {
		// ��ȡ��ǰʱ��
		Calendar cal = Calendar.getInstance();
		Date d = DateUtil.parseDate(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DATE));
		return d;
	}

	// ��ݸ������ؾ������ȱʡ�����ڣ�
	public static String getDefaultTime(long days) {
		long muchTime = days * 24 * 60 * 60 * 1000;
		Date current = getCurrentDate();
		long data1 = current.getTime();
		String rtnString = "";
		if (days == 0)
			rtnString = parseDate(current);
		else {
			Date targetDate = new Date(data1 + muchTime);
			rtnString = parseDate(targetDate);
		}
		return rtnString;
	}

	// ���ַ����͵�����ת��Ϊ������
	public static java.util.Date toDate(String sDate) {
		java.sql.Timestamp stm = toTimestamp(sDate);
		return new Date(stm.getTime());
	}

	// ���ַ����͵�����ת��ΪTimeStamp��
	public static java.sql.Timestamp toTimestamp(String sDate) {
		// //System.out.println("input value = " + sDate + "---");
		// added by wzhou for null value
		if (sDate == null || sDate.length() == 0) {
			return null;
		}
		sDate = sDate.replace('/', '-');
		// sValue'fomat is: "2001-06-14 17:29:57,459".
		if (sDate.indexOf(":") < 0) {
			sDate += " 00:00:00.000";
		} else if (sDate.indexOf("-") < 0) {
			sDate = "1990-07-01 " + sDate.trim();
		}
		if (sDate.length() == 16) {
			sDate += ":00.000";
		}
		java.sql.Timestamp oRet = null;
		if (!sDate.trim().equals("")) {
			sDate = sDate.replace(',', '.');
			// //System.out.println("converted value = " + sDate + "---");
			oRet = java.sql.Timestamp.valueOf(sDate);
		}
		return oRet;
	}

	// ����ָ���ĸ�ʽ�������ڵ��ַ���ʽ
	public static String parseDate(Date date, String ptn) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(ptn);
		return sdf.format(date);
	}

	// ���չ̶��ĸ�ʽ�������ڵ��ַ���ʽ
	public static String parseDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.format(date);
	}

	// ���չ̶��ĸ�ʽ�������ڵ��ַ���ʽ
	public static Date parseDate(String dataStr) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.parse(dataStr, new ParsePosition(0));
	}

	public static Date getMonthEndDate(int year, int month) {
		int day = getDaysOfMonth(year, month);
		Date date = parseDate(year + "-" + month + "-" + day);
		return date;
	}

	public static Date parseDate(String dataStr, String ptn) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(ptn);
		return sdf.parse(dataStr, new ParsePosition(0));
	}

	public static String parseString(Calendar calendar) {
		return parseDate(calendar.getTime());
	}

	public static Calendar parseCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Calendar parseCalendar(String string) {
		Date date = parseDate(string);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date getNextDate(Date date) {
		Calendar cal = parseCalendar(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date getNextMonthDate(Date date) {
		Calendar cal = parseCalendar(date);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}

	public static Date getEndDate(Date date) {
		Calendar cal = parseCalendar(date);
		int days = getDaysOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static Date getEndDateByYear(Date date) {
		Calendar cal = parseCalendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, 11);
		return cal.getTime();
	}

	public static Date getBeginDate(Date date) {
		Calendar cal = parseCalendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}

	public static Date getPreDate(Date date) {
		Calendar cal = parseCalendar(date);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static long compareDate(Date date1, Date date2) {
		// System.out.println(date1+"   "+date2);
		return (date1.getTime() - date2.getTime());
	}

	// ȡ��2����������������2��Ϊͬһ�죬��ֵΪ0
	public static int getDays(Date beginDate, Date endDate) {
		long times = endDate.getTime() - beginDate.getTime();
		long t = Math.round((times * 1.0 / (1000 * 3600 * 24)));
		return (int) t;
	}

	public static boolean betweenDate(Date date, Date date1, Date date2) {
		return (date.getTime() - date1.getTime() >= 0) && (date.getTime() - date2.getTime() <= 0);
	}

	public static void main(String[] args) {
		Date d1 = parseDate("2009-8-1");
		Date d2 = parseDate("2009-8-31");
		System.out.println(parseDate(getNextMonthDate(d2)));
		System.out.println(getCurrentDateStr());
	}

	public static boolean isDate(String year, String month, String day) {
		boolean flag = false;
		try {
			if (isYear(year) && isMonth(month) && DateUtil.isInteger(day)) {
				int dayTmp = getDaysOfMonth(Integer.parseInt(year), Integer.parseInt(month));
				if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= dayTmp) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean isYear(String year) {
		boolean flag = false;
		try {
			if (DateUtil.isInteger(year) && year.length() == 4)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean isMonth(String month) {
		boolean flag = false;
		try {
			if (DateUtil.isInteger(month) && Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * �ж�һ���ַ��ǲ������� ���ַ�س�һ����byte����ASCII����֤������0��9ΪASCII��48��57
	 * 
	 * @param integer
	 *            �жϵ��ַ�
	 * @return ��Ϊ�����򷵻�true������false
	 * @since 1.0
	 */
	public static boolean isInteger(String integer) throws Exception {
		int integerLength = integer.length();
		// ����ѭ���ַ�����ַ�
		for (int i = 0; i < integerLength; i++)
			// �ж��Ƿ���ASCII������ַ�Χ��
			if ((byte) integer.charAt(i) < 48 || (byte) integer.charAt(i) > 57)
				return false;

		return true;
	}

	/**
	 * ����ꡢ��ȡ�õ��µ�����
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return �·ݰ������
	 */
	public static int getDaysOfMonth(int year, int month) {
		int days = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			days = 31;
		else if (month == 4 || month == 6 || month == 9 || month == 11)
			days = 30;
		else if (month == 2 && ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)))
			days = 29;
		else
			days = 28;

		return days;
	}

	public static Date getMinDate(Date d1, Date d2) {
		if (compareDate(d1, d2) <= 0) {
			return d1;
		} else {
			return d2;
		}
	}

	public static Date addDays(Date d, int days) {
		Calendar cal = parseCalendar(d);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date addMonths(Date d, int months) {
		Calendar cal = parseCalendar(d);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public static Date getMaxDate(Date d1, Date d2) {
		if (compareDate(d1, d2) >= 0) {
			return d1;
		} else {
			return d2;
		}
	}

	public static boolean isCompleteMonth(Date begin, Date end) {
		if (begin.getDate() != 1)
			return false;
		// if (end.getMonth() != begin.getMonth()
		// || end.getYear() != begin.getYear())
		// return false;
		int days = getDaysOfMonth(end.getYear(), end.getMonth() + 1);
		if (begin.getDate() + days - 1 != end.getDate())
			return false;
		else
			return true;

	}
}
