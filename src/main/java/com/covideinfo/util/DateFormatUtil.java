package com.covideinfo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	public static String ConvertDate(Date date,String format) {
		if(date == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(format);
		return  df.format(date);
	}
}
