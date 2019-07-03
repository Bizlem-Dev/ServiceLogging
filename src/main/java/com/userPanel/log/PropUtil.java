package com.userPanel.log;

import java.util.Properties;

public class PropUtil 
{

	public static String ofserverIP;

	public static String httpPort;
	
	public static String ravedbUrl;
	public static String ravedbUser;
	public static String ravedbPswd;
	
	public static String userCreateonRave;
	public static String ofPort;

	public static String setflagY;
	public static String smsusercreation;
	public static String smssetvalue;
	public static String smsupdatenos;
	public static String MassMailer;
	public static String LIMEservice;
	public static String OPENX;
	public static String WebConf;
	public static String onlineexam;
	public static String deg360;
	public static String cs;
	public static String call;
	public static String mailplatform;
	public static String helpdesk;
	
	

	public void setprop()
	{
		Properties prop = new Properties();
		try 
		{

			prop.load(getClass().getResourceAsStream("/serverConfig.properties"));

			ofserverIP=prop.getProperty("ofserverIP").trim();

			httpPort=prop.getProperty("httpPort").trim();
			
			ravedbUrl=prop.getProperty("ravedbUrl").trim();
			ravedbUser=prop.getProperty("ravedbuser").trim();
			ravedbPswd=prop.getProperty("ravedbpswd").trim();
			
			ofPort=prop.getProperty("ofPort").trim();

			setflagY=prop.getProperty("setflagY").trim();
			smsusercreation=prop.getProperty("smsusercreation").trim();
			smssetvalue=prop.getProperty("smssetvalue").trim();
			smsupdatenos=prop.getProperty("smsupdatenos").trim();
			MassMailer=prop.getProperty("MassMailer").trim();
			LIMEservice=prop.getProperty("LIMEservice").trim();
			OPENX=prop.getProperty("OPENX").trim();
			WebConf=prop.getProperty("WebConf").trim();
			onlineexam=prop.getProperty("onlineexam").trim();
			deg360=prop.getProperty("deg360").trim();
			cs=prop.getProperty("cs").trim();
			call=prop.getProperty("call").trim();
			mailplatform=prop.getProperty("mailplatform").trim();
			helpdesk=prop.getProperty("helpdesk").trim();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
