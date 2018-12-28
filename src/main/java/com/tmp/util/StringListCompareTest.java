package com.tmp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StringListCompareTest {

	
	public static void main(String args[]) {
		
		ArrayList<String> strList = new ArrayList<String>();
		
		strList.add(".net");
		strList.add("java");
		
		String obj = ".Net Developer";
		
		for(String strObj : strList) {
			
			//if(obj.toLowerCase().contains(strObj.toLowerCase()) || strObj.toLowerCase().contains(obj.toLowerCase())) {
				if(obj.contains(strObj) || strObj.contains(obj)) {
			//if(obj.equalsIgnoreCase(strObj)) {
				
				System.out.println("obj Value "+obj);
				
			}
			
		}
		
		String test = getRandomString("","100","Ford Direct.com", "Ford Direct","SS" );
		
		System.out.println("Testing "+test);
		
		
	}
	
	private static String getShortName(String name) {
		
		String shortName = "";
		String[] words = name.split(" ");
		if(words.length == 1) { // ONE WORD ONLY
			shortName = words[0].substring(0, 2);
		}else{
			shortName = words[0].substring(0, 1)+words[1].substring(0, 1);
		}
		return shortName.toUpperCase();
	}
	
	private static String getRandomString(String dbDate, String incrementor, String accountName, String projectName, String userName) {

		DateFormat df = new SimpleDateFormat("yyyy_MM");
		Date today = Calendar.getInstance().getTime(); 
		String currDate = df.format(today);
		String defaultIncrementor="01";
		String reqRandomNo =null;
		String shortNames = "_"+getShortName(accountName)+"_"+getShortName(projectName)+"_"+getShortName(userName)+"_";
		try {
			
			
			if(dbDate == null){
				reqRandomNo = currDate+shortNames+defaultIncrementor;
			}else{
				int reqDateIncr= Integer.parseInt(incrementor)+1;
				if(reqDateIncr<=9){
					reqRandomNo = currDate+shortNames+0+reqDateIncr;
				}else{
					reqRandomNo = currDate+shortNames+reqDateIncr;
				}
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return reqRandomNo;
	}
	
}
