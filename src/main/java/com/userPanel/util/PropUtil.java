package com.userPanel.util;

import java.util.Properties;

public class PropUtil 
{

	public static String ravedbUrl;
	public static String ravedbUser;
	public static String ravedbPswd;

	public static String userCreateonRave;
	public static String uparam1;
	public static String uparam2;

	public void setprop()
	{
		Properties prop = new Properties();
		try 
		{

			prop.load(getClass().getResourceAsStream("/serverConfig.properties"));

			ravedbUrl=prop.getProperty("ravedbUrl").trim();
			ravedbUser=prop.getProperty("ravedbuser").trim();
			ravedbPswd=prop.getProperty("ravedbpswd").trim();

		/*	userCreateonRave=prop.getProperty("userCreateonRave").trim();
			uparam1=prop.getProperty("uparam1").trim();
			uparam2=prop.getProperty("uparam2").trim();*/
			


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
