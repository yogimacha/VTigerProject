package com.GenericUtilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author : M.Yogesh
 */
public class Java_Utility {
	public int getRandomNumber()
	{
		Random r=new Random();
		int Rno1=r.nextInt(2000);
		int Rno2=r.nextInt(6000);
		int sum=Rno1+Rno2;
		return sum;
		
	}
	
	public String getCurrentDate() {
		Date dobj=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String currentdata=sim.format(dobj);
		return currentdata;
	}
	public String getDateAftergivenDays(int days) {
		Date dobj=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		sim.format(dobj);
		Calendar cal=sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		String dateaftergivendays =sim.format(cal.getTime());
		return dateaftergivendays;
	}
	public static String getCurrentTimestamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(dtf);
	}
}
