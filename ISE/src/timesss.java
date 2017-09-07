import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timesss 
{
    public static void main( String[] args )
    {
    	System.out.println(getCurrentTime());
    }
    
    public static String getCurrentTime(){
        //Date object
	 Date date= new Date();
        //getTime() returns current time in milliseconds
	 long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class 
	 Timestamp ts = new Timestamp(time);
	 //System.out.println("Current Time Stamp: "+ts);
	 // Current Time Stamp: 2017-07-02 12:18:42.396

	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 String dateStr = sdf.format(ts);
	 
	// System.out.println("Current Time Stamp: "+ts +"   -"+dateStr);
	 return dateStr;
    }
    
    public static String generateRandomString(int length) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}