package com.covideinfo.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DobConversion {
	public int calculateDateofBirth(String dt) throws ParseException {
	 
      /*//This Code Works on jdk8
       int age =0;
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  Date d = sdf.parse(dt);
	  Calendar c = Calendar.getInstance();
	  c.setTime(d);
	  int year = c.get(Calendar.YEAR);
	  int month = c.get(Calendar.MONTH) + 1;
	  int date = c.get(Calendar.DATE);
	  LocalDate l1 = LocalDate.of(year, month, date);
	  LocalDate now1 = LocalDate.now();
	  Period diff1 = Period.between(l1, now1);
	  System.out.println("age:" + diff1.getYears() + "years");
	  age = diff1.getYears();*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar today = Calendar.getInstance();	 
		Calendar birthDate = Calendar.getInstance();

	    int age = 0;
        Date date = sdf.parse(dt);
	    birthDate.setTime(date);
	   /* if (birthDate.after(today)) {
	        throw new IllegalArgumentException("Can't be born in the future");
	    }
*/
	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
	    if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
	            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	        age--;

	     // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
	              (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
	        age--;
	    }

		
	  return age;
	  
	 }
	} 
