import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddingDays {

	public static void main(String[] args) throws ParseException {


	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String date = "2020-07-11";
	Date givenDate = sdf.parse(date);
	Calendar c = Calendar.getInstance();
	c.setTime(givenDate); // Now use today date.
	c.add(Calendar.DATE, 28); // Adding 5 days
	String output = sdf.format(c.getTime());
	System.out.println(output);



	}

}
