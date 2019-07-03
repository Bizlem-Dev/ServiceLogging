package com.userPanel.service.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.mysql.jdbc.PreparedStatement;
import com.userPanel.log.HTTPRequestPoster;
import com.userPanel.log.PropUtil;
import com.userPanel.schedular.ServiceActivator;

public class UserPanelActivatorImpl implements UserPanelActivator {
	Auctions_WSDLPortTypeProxy ap = null;

	ResourceBundle bundle = null;

	PropUtil prp = new PropUtil();

	public ArrayList<String> activateService(String userId, String serviceId,
			String quantity, String name) {
		bundle = ResourceBundle.getBundle("serverConfig");
		ap = new Auctions_WSDLPortTypeProxy();
		prp.setprop();
		String protocol = bundle.getString("services");
		String a[] = protocol.split(",");
		List<String> service = new ArrayList<String>();
		for (String s : a) {
			service.add(s);
		}
		ArrayList<String> reponseList = new ArrayList<String>();
		try {
			if (serviceId.equalsIgnoreCase(service.get(0))) {
				activateChat(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(1))) {
				activateVideoChat(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(2))) {
				activateSMSFlag(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			}

			else if (serviceId.equalsIgnoreCase(service.get(3))) {
				activateMassMailer(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");

			} else if (serviceId.equalsIgnoreCase(service.get(4))) {
				activateLimeSurvey(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.contains(service.get(5))) {
				activateLimeSurvey(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(6))) {
				activateWebConference(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(7))) {
				activateLogRecord(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(8))) {
				activate360Degree(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(9))) {
				activateOnlineExam(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(10))) {
				activateContactSystem(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(11))) {
				activateCall(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(12))) {
				activateMailPlatform(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");

			} else if (serviceId.equalsIgnoreCase(service.get(13))) {
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(14))) {
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(15))) {
				activateCallMBFlag(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(16))) {
				activateEmailFlag(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(17))) {
				activateHelpDesk(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(18))) {
				ap.changeServiceStatus(userId, serviceId, "active");
			}else if (serviceId.equalsIgnoreCase(service.get(19))) {
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(20))) {
				ap.changeServiceStatus(userId, serviceId, "active");
			} else if (serviceId.equalsIgnoreCase(service.get(21))) {
				activateDoctiger(userId, serviceId, quantity, name);
				ap.changeServiceStatus(userId, serviceId, "active");
			} 
			
			
			//21 activateDoctiger
			else {
				activateBanner(userId, serviceId, quantity, name, bundle);
			}
			reponseList.add(userId + "-->" + serviceId + "--> Activated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reponseList;
	}
	public void activateDoctiger(String userId, String serviceId, String quantity,
			String name) {
		String[] param = { "Doctiger", "userId" };
		String[] pval = { "Y", userId.replace("@", "_").trim() };
		String result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
				pval);
		ap = new Auctions_WSDLPortTypeProxy();
		try {
			ap.serviceConsumption(userId, serviceId, new Double(1), "Quantity",
					new Date());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void activateChat(String userId, String serviceId, String quantity,
			String name) {
		String[] param = { "Chat", "userId" };
		String[] pval = { "Y", userId.replace("@", "_").trim() };
		String result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
				pval);
		ap = new Auctions_WSDLPortTypeProxy();
		try {
			ap.serviceConsumption(userId, serviceId, new Double(1), "Quantity",
					new Date());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void activateVideoChat(String userId, String serviceId,
			String quantity, String name) {
		try {
			String[] param = { "VChat", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };

			String[] param1 = { "Chat", "userId" };
			String[] pval1 = { "Y", userId.replace("@", "_").trim() };

			String result = HTTPRequestPoster.callService(PropUtil.setflagY,
					param1, pval1);

			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);
			ap = new Auctions_WSDLPortTypeProxy();
			ap.serviceConsumption(userId, serviceId, new Double(1), "Quantity",
					new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateCallMBFlag(String userId, String serviceId,
			String quantity, String name) {
		String[] param = { "callMB", "userId" };
		String[] pval = { "Y", userId.replace("@", "_").trim() };
		HTTPRequestPoster.callService(PropUtil.setflagY, param, pval);
	}

	public void activateEmailFlag(String userId, String serviceId,
			String quantity, String name) {
		String[] param = { "email", "userId" };
		String[] pval = { "Y", userId.replace("@", "_").trim() };
		HTTPRequestPoster.callService(PropUtil.setflagY, param, pval);

	}

	public void activateSMSFlag(String userId, String serviceId,
			String quantity, String name) {
		String pswd = "";

		try {
			String result = HTTPRequestPoster.sendGetRequest(
					PropUtil.smsusercreation, "?username=" + userId.trim()
							+ "&displayName=" + name.trim() + "&email="
							+ userId.trim());

			// pswd = getPswd(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String[] param1 = { "userId", "msgUser", "msgPassword" };
			String[] pval1 = { userId.replace("@", "_").trim(), userId.trim(),
					pswd };

			String result = HTTPRequestPoster.callService(PropUtil.smssetvalue,
					param1, pval1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HTTPRequestPoster.sendGetRequest(PropUtil.smsupdatenos,
					"?username=" + userId.trim() + "&creadiamt=" + quantity);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			String[] param = { "sms", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };

			String result = HTTPRequestPoster.callService(PropUtil.setflagY,
					param, pval);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void activateMassMailer(String userId, String serviceId,
			String quantity, String name) {
		try {

			String[] sparam = { "emails[]" };
			String[] sval = { userId };
			String result = HTTPRequestPoster.callService(PropUtil.MassMailer,
					sparam, sval);

			String[] param = { "camp", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateLimeSurvey(String userId, String serviceId,
			String quantity, String name) {
		try {
			String[] sparam = { "emails[]", "fname[]", "lname[]", "groups[]" };
			String[] sval = { userId, name, " ", "visioninfocon" };
			String result = HTTPRequestPoster.callService(PropUtil.LIMEservice,
					sparam, sval);

			String[] param = { "lime", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateOpenX(String userId, String serviceId, String quantity,
			String name) {
		ServiceActivator serviceActivator = new ServiceActivator();
		try {

			String cparam = "advertiserName=" + "Demo" + name.replace(' ', '_')
					+ "&emailAddress=" + userId.trim() + "&usrnm="
					+ userId.replace("@", "_").trim() + "&passwd="
					+ serviceActivator.getDBPSWD(userId);

			String result = HTTPRequestPoster.sendGetRequest(PropUtil.OPENX,
					cparam);

			String[] param = { "ox", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateWebConference(String userId, String serviceId,
			String quantity, String name) {
		ServiceActivator serviceActivator = new ServiceActivator();
		try {

			String result = HTTPRequestPoster.sendGetRequest(PropUtil.WebConf,
					"?usrnm=" + userId.replace("@", "_").trim() + "&passwd="
							+ serviceActivator.getDBPSWD(userId));

			String[] param = { "wc", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateLogRecord(String userId, String serviceId,
			String quantity, String name) {
		try {

			// 1. calling service to set flag Y (pranav service)

			String[] param = { "log", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			String result = HTTPRequestPoster.callService(PropUtil.setflagY,
					param, pval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activate360Degree(String userId, String serviceId,
			String quantity, String name) {
		try {
			String[] sparam = { "emails[]", "business[]", "location[]" };
			String[] sval = { userId, "steel", "visioninfocon" };
			String result = HTTPRequestPoster.callService(PropUtil.deg360,
					sparam, sval);

			String[] param = { "deg3", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateOnlineExam(String userId, String serviceId,
			String quantity, String name) {
		try {
			// 1. calling webserive to create user in Online Exam

			String[] sparam = { "emails[]", "groups[]" };
			String[] sval = { userId, "visioninfocon" };
			String result = HTTPRequestPoster.callService(PropUtil.onlineexam,
					sparam, sval);

			String[] param = { "exam", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateContactSystem(String userId, String serviceId,
			String quantity, String name) {
		try {
			String[] sparam = { "emails[]" };
			String[] sval = { userId };
			String result = HTTPRequestPoster.callService(PropUtil.cs, sparam,
					sval);

			String[] param = { "cs", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateCall(String userId, String serviceId, String quantity,
			String name) {
		try {
			// 1. calling webserive to create user in call asterisk
			// system server application

			String result = HTTPRequestPoster.sendGetRequest(PropUtil.call
					+ userId + "&passwd=05GDO05VVO0", "");

			String[] param = { "call", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateMailPlatform(String userId, String serviceId,
			String quantity, String name) {
		try {
			String[] sparam = { "emails[]" };
			String[] sval = { userId };
			String result = HTTPRequestPoster.callService(
					PropUtil.mailplatform, sparam, sval);

			String[] param = { "mp", "userId" };
			String[] pval = { "Y", userId.replace("@", "_").trim() };
			result = HTTPRequestPoster.callService(PropUtil.setflagY, param,
					pval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activateHelpDesk(String userId, String serviceId,
			String quantity, String name) {
		String[] sparam = { "displayname[]", "userId[]", "mail[]",
				"firstname[]", "lastname[]", "location[]", "contact_no[]",
				"phone[]", "title[]", "department[]", "userPassword[]" };
		String[] sval = { userId.split("@")[0], userId, userId, name, ".",
				"INDIA", "0000000000", "0000000000", "Engineer", "Engineer",
				"password" };
		HTTPRequestPoster.callService(PropUtil.helpdesk, sparam, sval);
		String[] param = { "helpdesk", "userId" };
		String[] pval = { "Y", userId.replace("@", "_").trim() };
		HTTPRequestPoster.callService(PropUtil.setflagY, param, pval);
	}

	public void activateBanner(String userId, String serviceId,
			String quantity, String name, ResourceBundle bundle) {
		ap = new Auctions_WSDLPortTypeProxy();
		String bannerService = bundle.getString("bannerServices");
		String[] bannerListArray = bannerService.split(",");

		ArrayList<String> bannerList = new ArrayList<String>(
				Arrays.asList(bannerListArray));
		System.out.println(bannerList);
		if (bannerList.contains(serviceId)) {
			try {
				ap.changeServiceStatus(userId, serviceId, "active");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String commitInDataBase(String userId, String serviceId,
			String quantity, String name,String months,String accounts,String sdate,String edate,String records,String response) {
		String commitRespoce = "false";
		try {
			bundle = ResourceBundle.getBundle("serverConfig");
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			prp.setprop();
			String protocol = bundle.getString("services");
			String a[] = protocol.split(",");
			List<String> service = new ArrayList<String>();
			for (String s : a) {
				service.add(s);
			}
			 SimpleDateFormat sd =null;
			 java.sql.Date startDate=null;
			 java.sql.Date endDate=null;
			 java.sql.Date consuptionDate=null;
			 java.sql.Date insertedDate=null;
			 Date date = new Date();
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        String uom="";
		        String uomdesc="";
			if(!accounts.equals("0")){
				 cal.add(Calendar.MONTH, 0);				      
			     startDate=new java.sql.Date(cal.getTimeInMillis());
			     consuptionDate=new java.sql.Date(cal.getTimeInMillis());
			     insertedDate=new java.sql.Date(cal.getTimeInMillis());				     
			     cal.add(Calendar.MONTH, Integer.parseInt(months));
			     endDate=new java.sql.Date(cal.getTimeInMillis());
			       quantity=accounts;
			       uom="OTH_USER";
			       uomdesc="Month";
			}else if(!quantity.equals("0")){
				cal.add(Calendar.MONTH, 0);				      
			     startDate=new java.sql.Date(cal.getTimeInMillis());
			     consuptionDate=new java.sql.Date(cal.getTimeInMillis());
			     insertedDate=new java.sql.Date(cal.getTimeInMillis());				     
//			     cal.add(Calendar.MONTH, 50);
			     if(months.equals("0")){
			         cal.add(Calendar.MONTH, 50);  
			        }else{
			         cal.add(Calendar.MONTH, Integer.parseInt(months));
			        }
			     endDate=new java.sql.Date(cal.getTimeInMillis());
			     uom="OTH_USER";
			       uomdesc="Month";
			}else
				if(!response.equals("0")){
					cal.add(Calendar.MONTH, 0);				      
				     startDate=new java.sql.Date(cal.getTimeInMillis());
				     consuptionDate=new java.sql.Date(cal.getTimeInMillis());
				     insertedDate=new java.sql.Date(cal.getTimeInMillis());				     
				     if(months.equals("0")){
				         cal.add(Calendar.MONTH, 50);  
				        }else{
				         cal.add(Calendar.MONTH, Integer.parseInt(months));
				        }
				     endDate=new java.sql.Date(cal.getTimeInMillis());
				     uom="OTH_USER";
				       uomdesc="Month";
				       quantity=response;
				}else
				if(!months.equals("0")){
					cal.add(Calendar.MONTH, 0);				      
				     startDate=new java.sql.Date(cal.getTimeInMillis());
				     consuptionDate=new java.sql.Date(cal.getTimeInMillis());
				     insertedDate=new java.sql.Date(cal.getTimeInMillis());				     
				     cal.add(Calendar.MONTH, Integer.parseInt(months));
				     endDate=new java.sql.Date(cal.getTimeInMillis());
				     uom="OTH_USER";
				       uomdesc="Month";
				       quantity="1";
				}else
					if(!records.equals("0")){
						cal.add(Calendar.MONTH, 0);				      
					     startDate=new java.sql.Date(cal.getTimeInMillis());
					     consuptionDate=new java.sql.Date(cal.getTimeInMillis());
					     insertedDate=new java.sql.Date(cal.getTimeInMillis());				     
					     if(months.equals("0")){
					         cal.add(Calendar.MONTH, 50);  
					        }else{
					         cal.add(Calendar.MONTH, Integer.parseInt(months));
					        }
					     endDate=new java.sql.Date(cal.getTimeInMillis());
					     uom="OTH_USER";
					       uomdesc="Month";
					       quantity=records;
					}
			
			
			ArrayList<String> reponseList = new ArrayList<String>();
			Date d = new Date();
			
			if (serviceId.equalsIgnoreCase(service.get(0))) {
				// activateChat(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(0), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(1))) {
				activateVideoChat(userId, serviceId, quantity, name);
				// ap.changeServiceStatus(userId, serviceId, "active");
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(1), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(2))) {
				// / activateSMSFlag(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(2), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			}

			else if (serviceId.equalsIgnoreCase(service.get(3))) {
				// /activateMassMailer(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(3), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(4))) {
				// activateLimeSurvey(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(4), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);

			} else if (serviceId.contains(service.get(5))) {
				// activateLimeSurvey(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(5), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);

			} else if (serviceId.equalsIgnoreCase(service.get(6))) {
				// activateWebConference(userId, serviceId, quantity, name);

				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(6), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(7))) {
				// activateLogRecord(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(7), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(8))) {
				// activate360Degree(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(8), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(9))) {
				// activateOnlineExam(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(9), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(10))) {
				// activateContactSystem(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(10), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(11))) {
				// activateCall(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						serviceId, "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(12))) {
				// activateMailPlatform(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(12), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(13))) {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(13), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(14))) {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(14), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(15))) {
				// activateCallMBFlag(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(15), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(16))) {
				// activateEmailFlag(userId, serviceId, quantity, name);
				
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(16), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);

			} else if (serviceId.equalsIgnoreCase(service.get(17))) {
				// activateHelpDesk(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(17), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(18))) {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(18), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else if (serviceId.equalsIgnoreCase(service.get(19))) {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(19), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			}else if (serviceId.equalsIgnoreCase(service.get(20))) {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(20), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			}else if (serviceId.equalsIgnoreCase(service.get(21))) {
			//	activateDoctiger(userId, serviceId, quantity, name);
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
						service.get(21), "", quantity, uom, startDate,
						endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
						"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
			} else {
				commitRespoce = this.commitOrderToMainDataBase(userId, name,
					serviceId, "", quantity, uom, startDate,
					endDate, "0", consuptionDate, "Y", "Y", "N", "Y",
					"Y", "Y", "active", uomdesc, "", "", "", insertedDate);
				// activateBanner(userId, serviceId, quantity, name, bundle);
			}
			this.activateService(userId, serviceId, quantity, name);
		} catch (Exception e) {
			commitRespoce = "false";
		}
		return commitRespoce;
	}

	

	public boolean getService(String customerId, String productCode) {
		boolean serviceExistStatus = false;
		try {
			bundle = ResourceBundle.getBundle("serverConfig");
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerservicebuser"),
					bundle.getString("customerservicepswd"));

			String sql = "SELECT COUNT(*) AS rowcount from customer_service_status where customerId=? and productcode=?";
			java.sql.PreparedStatement pmt = con.prepareStatement(sql);
			pmt.setString(0, customerId);
			pmt.setString(1, productCode);
			ResultSet rs = pmt.executeQuery();
			if(rs!=null){
				int count=rs.getInt("rowcount");
				if(count>0){
					serviceExistStatus=true;
				}
			}
			if(con!=null){
				con.close();
				if(rs !=null){
					rs .close();
					}
				if(pmt !=null){
					pmt .close();
					}
				}

		} catch (Exception e) {
			serviceExistStatus = false;
		}

		return serviceExistStatus;
	}
	public boolean updateService(String customerId, String productCode) {
		boolean serviceExistStatus = false;
		try {
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerservicebuser"),
					bundle.getString("customerservicepswd"));

			String sql = "Update customer_service_status set  where customerId=? and productcode=?";
			java.sql.PreparedStatement pmt = con.prepareStatement(sql);
			pmt.setString(0, customerId);
			pmt.setString(1, productCode);
			ResultSet rs = pmt.executeQuery();
			if(rs!=null){
				int count=rs.getInt("rowcount");
				if(count>0){
					serviceExistStatus=true;
				}
			}
			//serviceExistStatus = true;

		} catch (Exception e) {
			serviceExistStatus = false;
		}

		return serviceExistStatus;
	}

	
	
	public String commitOrderToMainDataBase(String userId, String name,
			String serviceId, String productDescription, String quantity,
			String uom, java.sql.Date service_start_date,
			java.sql.Date service_end_date, String consumptionQuantity,
			java.sql.Date lastConsumptionDate, String likeDislike,
			String comment, String rating, String share, String rfq,
			String blog, String status, String uom_description,
			String config_id, String shipment_id, String order_id,
			java.sql.Date lastInsertedDate) {
		String commitData = "false";
		try {
			bundle = ResourceBundle.getBundle("serverConfig");
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerserviceuser"),
					bundle.getString("customerservicepswd"));

			String sql = "Insert into customer_service_status (customerId,customerName,productCode,productDescription,quantity," +
							"uom,service_start_date,service_end_date,consumptionQuantity,lastConsumptionDate,likeDislike,comment," +
							"rating,share,rfq,blog,status,uom_description,config_id,shipment_id,order_id,lastInsertedDate )"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			 Date date = new Date();
		       
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        cal.add(Calendar.MONTH, 4);		      
		    
			java.sql.PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, userId);
			psmt.setString(2, name);
			psmt.setString(3, serviceId);
			psmt.setString(4, productDescription);
			psmt.setDouble(5, Double.valueOf(quantity));
			psmt.setString(6, uom);
			psmt.setTimestamp(7, new java.sql.Timestamp(service_start_date.getTime()));
			psmt.setTimestamp(8,  new java.sql.Timestamp(service_end_date.getTime()));
			psmt.setDouble(9, Double.valueOf(consumptionQuantity));
			psmt.setTimestamp(10, new java.sql.Timestamp(lastConsumptionDate.getTime()));
			psmt.setString(11, likeDislike);
			psmt.setString(12, comment);
			psmt.setString(13, rating);
			psmt.setString(14, share);
			psmt.setString(15, rfq);
			psmt.setString(16, blog);
			psmt.setString(17, status);
			psmt.setString(18, uom_description);
			psmt.setString(19, config_id);
			psmt.setString(20, shipment_id);
			psmt.setString(21, order_id);
			psmt.setTimestamp(22, new java.sql.Timestamp(lastInsertedDate.getTime()));
			
			psmt.execute();
			commitData = "true";
			if(con!=null){
			con.close();
			if(psmt!=null){
				psmt.close();
				}
			}
		} catch (Exception e) {
			commitData = "false"+e.getMessage();
		}
		return commitData;
	}

}
