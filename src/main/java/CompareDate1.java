import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompareDate1 {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2022-11-28 22:15:55");
//        Date date2 = sdf.parse("2022-02-30 10:01");
        
        Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int minutes = cal.get(Calendar.MINUTE);
		int hours = cal.get(Calendar.HOUR);
		System.out.println("Hours and Minutes is :"+hours+"::"+minutes);
		System.out.println("Before Date is : "+cal.getTime());
		
		String[] tempArr = "001.500".split("\\.");
		int doseMinutes = 0;
		int doseHours = 0;
		if(tempArr.length > 0) {
			doseHours = Integer.parseInt(tempArr[0]) ;
			doseMinutes = (((Integer.parseInt(tempArr[1])*6)/100));
		}
		System.out.println("doseMinutes are :"+doseMinutes);
		cal.add(Calendar.HOUR, doseHours);
		cal.add(Calendar.MINUTE, doseMinutes);
		 Date date2 = cal.getTime();
		System.out.println("After Date is : "+cal.getTime());

        System.out.println("date1 : " + sdf.format(date1));
        System.out.println("date2 : " + sdf.format(date2));

        int result = date1.compareTo(date2);
        System.out.println("result: " + result);

        if (result == 0) {
            System.out.println("Date1 is equal to Date2");
        } else if (result > 0) {
            System.out.println("Date1 is after Date2");
        } else if (result < 0) {
            System.out.println("Date1 is before Date2");
        } else {
            System.out.println("How to get here?");
        }

        Date d1 = date1;
		Date d2 = date2;
		
		// Calucalte time difference
		// in milliseconds
		long difference_In_Time = d1.getTime() - d2.getTime();

		// Calucalte time difference in
		// seconds, minutes, hours, years,
		// and days
		long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
		long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
		System.out.println(difference_In_Years + "\t"+difference_In_Days+"\t"+difference_In_Hours+"\t"+difference_In_Minutes);
    }
}