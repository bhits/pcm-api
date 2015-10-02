package gov.samhsa.spirit.pep.wsclient;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckTime {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		System.out.println(currentTimestamp);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		  //2014-08-11T09:36:34.388Z
		System.out.println(sdf.format(new Date()));
	}

}
