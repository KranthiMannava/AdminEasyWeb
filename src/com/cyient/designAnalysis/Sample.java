package com.cyient.designAnalysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample {

	
	public static void main(String args[]) throws ParseException{
		
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
	    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
	    Date date = format1.parse("05/01/1999");
	    
	    String reportDate = format2.format(date);
	    System.out.println(reportDate);
	}
}
