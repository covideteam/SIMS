package com.covideinfo.general;

import java.util.Date;
import java.text.SimpleDateFormat;
public class DatesDifference{
   public int calculateDiffBetweenTwoDays(String startDate, String endDate){
	 SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	 int days = 0;
	 try {
	       Date dateBefore = myFormat.parse(endDate); //Previous Date
	       Date dateAfter = myFormat.parse(startDate);//present Date
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
           days = Math.round(daysBetween);
	      // System.out.println("Number of Days between dates: "+days);
	 } catch (Exception e) {
	       e.printStackTrace();
	 }
	 return days;
   }
  
}