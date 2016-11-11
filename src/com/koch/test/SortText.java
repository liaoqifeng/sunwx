package com.koch.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.koch.util.DateUtil;

public class SortText {

	public static void main(String[] args) {
		List<String> ts = new ArrayList<String>();
		ts.add("5:0:0");
		ts.add("1:0:0");
		ts.add("6:0:0");
		for (String string : ts) {
			System.out.println("begin:"+string);
		}
		Collections.sort(ts, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Calendar c = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = DateUtil.parseDate(df.format(new Date())+" "+o1, "yyyy-MM-dd HH:mm:ss");
				Date date2 = DateUtil.parseDate(df.format(new Date())+" "+o2, "yyyy-MM-dd HH:mm:ss");
				if(date1.after(date2)){
					return 1;
				}
				return -1;
			}
		});
		for (String string : ts) {
			System.out.println("end:"+string);
		}
	}

}
