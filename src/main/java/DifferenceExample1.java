import java.text.SimpleDateFormat;  
import java.text.ParseException;   
import java.util.Date;
import java.util.HashMap;
import java.util.Map;   

class DateDifferences {
	
	public DateDifferences() {
		
	}
	
	// Create function for finding difference 
	Map<String, Long> find(String dateStr1, String dateStr2)   
    {   
		Map<String, Long> datesMap = new HashMap<>();
        // Create an instance of the SimpleDateFormat class  
        SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        // In the try block, we will try to find the difference  
        try {   
            // Use parse method to get date object of both dates  
            Date date1 = obj.parse(dateStr1);   
            Date date2 = obj.parse(dateStr2);   
            // Calucalte time difference in milliseconds   
            Long time_difference = date2.getTime() - date1.getTime();  
            // Calucalte time difference in days  
            Long days_difference = (time_difference / (1000*60*60*24)) % 365;   
            // Calucalte time difference in years  
            Long years_difference = (time_difference / (1000l*60*60*24*365));   
            // Calucalte time difference in seconds  
            Long seconds_difference = (time_difference / 1000)% 60;   
            // Calucalte time difference in minutes  
            Long minutes_difference = (time_difference / (1000*60)) % 60;   
              
            // Calucalte time difference in hours  
            Long hours_difference = (time_difference / (1000*60*60)) % 24;   
            // Show difference in years, in days, hours, minutes, and seconds   
            datesMap.put("Hours", hours_difference);
            datesMap.put("Minutes", minutes_difference);
            datesMap.put("Seconds", seconds_difference);
            datesMap.put("Days", days_difference);
        }   
        // Catch parse exception   
        catch (Exception e) {   
            e.printStackTrace();   
        } 
        return datesMap;
    } 
}
class DifferenceExample1 {     
	
      
    // Main class  
    public static void main(String[] args)   
    {   
        // Set values for both dates  
    	String dateStr1 = "2023-06-20 16:38:44";
    	String dateStr2 = "2023-06-21 11:44:59";  
        // Calling find() method for getting difference bwtween dates 
    	DateDifferences ddf = new DateDifferences();
    	Map<String, Long> datesMap = ddf.find(dateStr1, dateStr2); 
    	if(datesMap.size() > 0) {
    		for(Map.Entry<String, Long> st : datesMap.entrySet()) {
    			System.out.println("Key :"+st.getKey()+" \t Value is :"+st.getValue());
    		}
    	}
    }   
}  