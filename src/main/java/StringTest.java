import java.util.Calendar;
import java.util.Date;

import javax.script.ScriptException;


public class StringTest {
	public static void main(String[] args) throws ScriptException {
		/*String s = "04a31a37a01a145n";
		s = s.replaceAll("a", "#");
		String ss[] = s.split("\\#");
		String sss[] = s.split("#");
		System.out.println(s);
		System.out.println(ss);
		System.out.println(sss);*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		System.out.println(cal.getTime());
		cal.add(Calendar.HOUR, -2);
		cal.add(Calendar.MINUTE, -34);
		System.out.println(cal.getTime());
		    
	}

}
