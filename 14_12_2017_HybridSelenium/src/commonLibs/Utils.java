package commonLibs;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;

public class Utils {
	
	public static void waitForSeconds(long seconds){
		try {
			
			Thread.sleep(seconds*1000L);
		} catch (Throwable t) {
			
			t.printStackTrace();
		}
	}
	
	
	public static By getLocatorBy(String locatorString){//id:=email
		try{
			
			String[] locator;
			
			locatorString = locatorString.trim();
			if(locatorString.isEmpty() || ! locatorString.contains(":=")){
				
				System.out.println(" locator is empty");
			} 
			
			locator = locatorString.split(":=");
			
			if(locator[0].equalsIgnoreCase("id")){//id.equalsIgnoreCase("id")
				return By.id(locator[1]);//By.id("email")
			}
			
			if(locator[0].equalsIgnoreCase("Class")){
				return By.className(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("xPath")){
				return By.xpath(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("css")){
				return By.cssSelector(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("link")){
				return By.linkText(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("partialLink")){
				return By.partialLinkText(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("name")){
				return By.name(locator[1]);
			}
			
			if(locator[0].equalsIgnoreCase("tagname")){
				return By.tagName(locator[1]);
			}
			
			throw new Exception("Invalid locator String...");
			
		} catch(Throwable t){
			System.err.println(t.getMessage());
			return null;
		}
	}
	//----------------------------------------------------------------
	public static Properties getProperties(String propertiesFilePath){
		
		try {
			InputStream fileReader;
			Properties property;
			
			fileReader = new FileInputStream(propertiesFilePath);
			property = new Properties();
			
			property.load(fileReader);
			
			return property;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
		
		
	}
	
	//---------------------------------------------------------------
	
	public static String getDateTimeStamp(){

		Date date;
		String[] datePart;
		String dateStamp;
		
		
		date = new Date();
		System.out.println(date.toString());
		//Mon Sep 07 17:28:42 IST 2015

		datePart = date.toString().split(" ");
		
		dateStamp = datePart[5] + "_" +
				datePart[1] + "_" +
				datePart[2] + "_" +
				datePart[3] ;
		
		dateStamp = dateStamp.replace(":",  "_");
		System.out.println(dateStamp);
		return dateStamp;
	}

}
