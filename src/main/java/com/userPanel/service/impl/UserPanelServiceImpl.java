package com.userPanel.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;

import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
//import com.user.validate.UserValidationStub.RaveUserExistence;
import com.userPanel.dao.UserPanelDAO;
import com.userPanel.dao.impl.UserPanelDAOImpl;
import com.userPanel.log.HTTPRequestPoster;
import com.userPanel.log.PropUtil;
import com.userPanel.service.UserPanelService;
//import com.user.validate.UserValidationStub;
//import com.user.validate.UserValidationStub.RaveUserExistence;
//import org.springframework.beans.factory.annotation.Autowired;
import com.userPanel.service.ActiveMQProducer;
import com.userPanel.service.impl.DefaultActiveMQProducer;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//import org.springframework.stereotype.Service;



public class UserPanelServiceImpl implements UserPanelService {

    UserPanelDAO userPanelDAO = null;
    Auctions_WSDLPortTypeProxy ap = null;
    ResourceBundle bundle = null;
    DefaultActiveMQProducer activeMQ = null;
//    @Autowired
//	private ActiveMQProducer activeMQ;
//    
//    public void setActiveMQ(ActiveMQProducer activeMQ){
//    	this.activeMQ=activeMQ;
//    }
    
    public String personList() throws JSONException {
        userPanelDAO = new UserPanelDAOImpl();
        return userPanelDAO.personList();
    }

    
    public String compSling(String companyName, String website,String email,String number ,String userId) {
    	try{
    		InputStream inputXml1 = null;
    		while(companyName.indexOf(" ") != -1){
    			companyName = companyName.replace(" ", "%20");
    		}
    		bundle = ResourceBundle.getBundle("serverConfig");
    		  String serviceUrl = bundle.getString("mdmServiceUrl");
    	String	url1 = serviceUrl+"/services/MDMServiceWeb/setngetCompany?companyName="+companyName+"&website="+website+"&emailId="+email+"&contactNumber="+number+"&userId="+userId;
//			customerDeatilList = new ArrayList<HashMap<String, String>>();
			inputXml1 = new URL(url1).openConnection().getInputStream();

			DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
			Document doc1 = dBuilder1.parse(inputXml1);
			doc1.getDocumentElement().normalize();
			NodeList nList1 = doc1.getElementsByTagName("ns:setngetCompanyResponse");
	        org.w3c.dom.Node nNode = nList1.item(0);
	       Element eElement = (Element) nNode;
//	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
	String compSlingPath = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
	if(compSlingPath != ""){
		return compSlingPath;
	}else{
		return "no response";
	}

    	}catch(Exception e){
    		return e.getMessage();
    	}
    }
    
    public String compMdmIntoDatabase(String companyName, String website,String email,String number,String compSlingPath,String productId,String userId,String serviceId){
    	int i = 0;
    	Statement stmt = null;
    	String data = "0";
    	try{
    		bundle = ResourceBundle.getBundle("serverConfig");
    		Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerserviceuser"),
					bundle.getString("customerservicepswd"));
			 stmt = (Statement) con.createStatement();
			String sql = "INSERT INTO `mdmcompany`(`name`, `website`, `emailId`, `number`, `company_slingpath`, `productId`, `customerId`,`serviceId`) VALUES ('"+companyName+"','"+website+"','"+email+"','"+number+"','"+compSlingPath+"','"+productId+"','"+userId+"',"+serviceId+")";
			 i = stmt.executeUpdate(sql);
	            //  Long l = stmt.getLastInsertID();
	            ResultSet keys = stmt.executeQuery("SELECT LAST_INSERT_ID()");

	            keys.next();
	            i = keys.getInt(1);

	             data = String.valueOf(i);
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return data;
    }
   
    public String mdmServiceUser(String personList, String nameList, String numberList) {
    	try{
    		String[] names = personList.split(",");
			  String[] nameLists = nameList.split(",");
			  String[] numLists = numberList.split(",");
			 for (int i = 0; i < names.length; i++) {
      	String name = names[i].toString();
			String fullname = nameLists[i].toString();
			String number = numLists[i].toString();
          ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
          String serviceUrl = bundle.getString("mdmServiceUrl");
  		String url = "";
  		InputStream inputXml = null;
			
  		url = serviceUrl+"/services/MDMServiceWeb/raveUserExistence?userId="+name+"&name="+fullname+"&mobileNumber="+number;
//			customerDeatilList = new ArrayList<HashMap<String, String>>();
			inputXml = new URL(url).openConnection().getInputStream();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXml);
			doc.getDocumentElement().normalize();
//			NodeList nList = doc.getElementsByTagName("ns:return");
//			String userresult = nList.toString();
			NodeList nList1 = doc.getElementsByTagName("ns:raveUserExistenceResponse");
	        org.w3c.dom.Node nNode = nList1.item(0);
	        Element eElement = (Element) nNode;
//	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
	        String userresult = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
			}
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return "true";
    }
    
    public String userMdmIntoDatabase(String personList, String nameList, String numberList,String adminList,String productCode,String customerId,String serviceId) {
    	int k = 0;
    	Statement stmt = null;
    	try{
    		bundle = ResourceBundle.getBundle("serverConfig");
    		String[] names = personList.split(",");
			  String[] nameLists = nameList.split(",");
			  String[] numLists = numberList.split(",");
			  String[] adminLists = adminList.split(",");
			  Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(
						bundle.getString("customerserviceUrl"),
						bundle.getString("customerserviceuser"),
						bundle.getString("customerservicepswd"));
			 for (int i = 0; i < names.length; i++) {
      	String name = names[i].toString();
			String fullname = nameLists[i].toString();
			String number = numLists[i].toString();
			String admin = adminLists[i].toString();
  		stmt = (Statement) con.createStatement();
		String query = "select emailId from mdmperson where serviceId = "+serviceId+" and customerId = '"+customerId+"' and emailId = '"+name+"' and flag = 0 " ;
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()){
		
		}else{
  		
		String nameSling = name.replace("@","_");
		String sql = "INSERT INTO `mdmperson`(`name`, `emailId`, `number`, `admin`, `person_slingpath`, `productId`, `customerId`, `serviceId`, `flag`,`assignpercent`,`assigntype`,`assigncatlevel1`,`assigncatlevel2`,`assigncatlevel3`,`assigncatlevel4`) VALUES ('"+fullname+"','"+name+"','"+number+"',"+admin+",'/content/user/"+nameSling+"','"+productCode+"','"+customerId+"','"+serviceId+"',0,'','','','','','')";
		 k = stmt.executeUpdate(sql);
if(k > 0){
//	return "true";
}else{
	//return "false";
}
		}	 
			 }
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return "true";
    }
    
    public String userGetService(String userId) {
    	String result = "0";
    	Statement stmt = null;
    	try{
    		bundle = ResourceBundle.getBundle("serverConfig");
    		  Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(
						bundle.getString("customerserviceUrl"),
						bundle.getString("customerserviceuser"),
						bundle.getString("customerservicepswd"));
		
		stmt = (Statement) con.createStatement();
		String query = "select serviceId from mdmperson where emailId = '"+userId+"' and flag = 0 " ;
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()){
			result = String.valueOf(rs.getInt("serviceId"));
		}else{
			result = "false";
		}
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return result;
    }

    
    public String userMdmUpdateDatabase(String idList, String adminList,String customerId,String serviceId) {
    	int k = 0;
    	Statement stmt = null;
    	Statement stmtU = null;
    	try{
    		String[] names = idList.split(",");
			  String[] adminLists = adminList.split(",");
			  bundle = ResourceBundle.getBundle("serverConfig");
			  Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(
						bundle.getString("customerserviceUrl"),
						bundle.getString("customerserviceuser"),
						bundle.getString("customerservicepswd"));
				Connection conU = (Connection) DriverManager.getConnection(
						bundle.getString("customerserviceUrl"),
						bundle.getString("customerserviceuser"),
						bundle.getString("customerservicepswd"));
				stmt = (Statement) con.createStatement();
				stmtU = (Statement) conU.createStatement();
				String query = "select mdmPersonId,emailId from mdmperson where serviceId = "+serviceId+" and customerId = '"+customerId+"' and flag = 0 " ;
				ResultSet rs = stmt.executeQuery(query);
				int j = 0;
				while(rs.next()){
					String name = names[j].toString();
					String admin = adminLists[j].toString();
					if(String.valueOf(rs.getInt("mdmPersonId")).equals(name)){
					String sql = "update mdmperson set admin = "+admin+" where mdmPersonId = "+rs.getInt("mdmPersonId")+" ";
					 k = stmtU.executeUpdate(sql);
			if(k > 0){
//				return "true";
			}else{
				//return "false";
			}
			j++;
		  		}else{
					String sql = "update mdmperson set flag = 1,admin=0 where mdmPersonId = "+rs.getInt("mdmPersonId")+" ";
							 k = stmtU.executeUpdate(sql);
					if(k > 0){
//						return "true";
					}else{
						//return "false";
					}
				  		
						}
					
				}
			
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return "true";
    }
    
    public String userUpdateMdmPerson(String id, String email,String userPercent,String userPercentType,String customerId,String serviceId) {
    	int k = 0;
    	Statement stmt = null;
    	String result = "false";
    	try{
    		  bundle = ResourceBundle.getBundle("serverConfig");
			  Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(
						bundle.getString("customerserviceUrl"),
						bundle.getString("customerserviceuser"),
						bundle.getString("customerservicepswd"));
				stmt = (Statement) con.createStatement();
					String sql = "update mdmperson set assignpercent = '"+userPercent+"',assigntype = '"+userPercentType+"' where mdmPersonId = "+id+" ";
					 k = stmt.executeUpdate(sql);
					 if(k > 0){
						 result = "true";
					 }
    	}catch(Exception e){
    		return e.getMessage();
    	}
    	return result;
    }

    
	public String assignedMDMUserList(String userId,String serviceId) {
		//List<String> list = new ArrayList<String>();
		Statement statement = null;
		JSONArray jarray = new JSONArray();
		JSONObject mainJson = new JSONObject();
		String query = "";
		try {
			bundle = ResourceBundle.getBundle("serverConfig");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerserviceuser"),
					bundle.getString("customerservicepswd"));
			query = "select * from mdmperson where serviceId = "+serviceId+" and customerId = '"+userId+"' and flag =0 " ;
			
			//	conn = ConnectionImpl.getConnection();
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			JSONObject json = null;
			while (rs.next()) {

				json = new JSONObject();
				json.put("id",rs.getInt("mdmPersonId"));
				json.put("name",rs.getString("name"));
				json.put("email",rs.getString("emailId"));
				json.put("number",rs.getString("number"));
				json.put("isadmin",rs.getInt("admin"));
				json.put("person_slingpath",rs.getString("person_slingpath"));
				jarray.put(json);
			}
			if(jarray.length() > 0){
			mainJson.put("mdminfo",jarray);
			}
			con.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.getMessage();

		}

		return mainJson.toString();
		//return query;
	}
	
	public String assignedMDMCompany(String userId,String serviceId) {
		//List<String> list = new ArrayList<String>();
		Statement statement = null;
		JSONArray jarray = new JSONArray();
		JSONObject mainJson = new JSONObject();
		String query = "";
		try {
			bundle = ResourceBundle.getBundle("serverConfig");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection(
					bundle.getString("customerserviceUrl"),
					bundle.getString("customerserviceuser"),
					bundle.getString("customerservicepswd"));
			query = "select * from mdmcompany where serviceId = "+serviceId+" and customerId = '"+userId+"' " ;
			
			//	conn = ConnectionImpl.getConnection();
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			JSONObject json = null;
			while (rs.next()) {

				json = new JSONObject();
				json.put("compid",rs.getInt("mdmCompanyId"));
				json.put("compname",rs.getString("name"));
				json.put("compweb",rs.getString("website"));
				json.put("compemail",rs.getString("emailId"));
				json.put("compnumber",rs.getString("number"));
				json.put("company_slingpath",rs.getString("company_slingpath"));
				jarray.put(json);
			}
			if(jarray.length() > 0){
			mainJson.put("mdmcompinfo",jarray);
			}
			con.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.getMessage();

		}

		return mainJson.toString();
		//return query;
	}
    
    public String splitUser(String personList, String productCode, String userId,String serviceId) {
    	String result = "";
    	JSONObject strResult = new JSONObject();
//    	String userserviceres = "";
    	try {
            bundle = ResourceBundle.getBundle("serverConfig");
            String[] names = personList.split(",");
//            String[] names1 = new String[names.length];
            ArrayList names1 = new ArrayList();
            ap = new Auctions_WSDLPortTypeProxy();
            double assignedQuanity = (double) names.length;
            String smsPassowrd = "";
//            String result = "";
            JSONObject json = null;
            
            JSONArray emailAlreadyCnfig = new JSONArray();
            JSONArray mainArr = new JSONArray();
            if (productCode.equalsIgnoreCase("email")
                    || productCode.equalsIgnoreCase("sms") 
                    || productCode.equalsIgnoreCase("call")) {
                if (productCode.equalsIgnoreCase("sms")) {
                    String response = HTTPRequestPoster.sendGetRequest(
                            bundle.getString("smsusercreation"), "?username="
                                    + userId + "&displayName=&email=" + userId);
                    JSONArray jarray = new JSONArray(response);
                    json = jarray.getJSONObject(0);
                    if (json.getString("Status").equalsIgnoreCase("exists")) {

                    }
                    smsPassowrd = json.getString("Password");
                }else if(productCode.equalsIgnoreCase("call")){
                	for(int i = 0; i < names.length; i++){
                    	String serviceUrl1 = bundle.getString("userServiceUrl");
                		String url1 = "";
                		InputStream inputXml1 = null;
                		url1 = serviceUrl1+"/services/UserValidation/serviceCheckUser?userId="+names[i]+"&productCode="+productCode;
//            			customerDeatilList = new ArrayList<HashMap<String, String>>();
            			inputXml1 = new URL(url1).openConnection().getInputStream();

            			DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory
            					.newInstance();
            			DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
            			Document doc1 = dBuilder1.parse(inputXml1);
            			doc1.getDocumentElement().normalize();
            			NodeList nList1 = doc1.getElementsByTagName("ns:serviceCheckUserResponse");
            	        org.w3c.dom.Node nNode = nList1.item(0);
            	       Element eElement = (Element) nNode;
//            	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
            	String userserviceres = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
            			if(userserviceres.equals("true")){
        				names1.add(names[i]);
            			}else if(userserviceres.equals("false")){
//            				names1[i] = "";	
            				emailAlreadyCnfig.put(names[i]);
            			}
                    	}
                		strResult.put("accessFlag","true");
                    	strResult.put("emailid",emailAlreadyCnfig);
                    	double assignedQuanityCall = (double) names1.size();
            			result = setConsumption(userId, productCode, assignedQuanityCall,serviceId);

                }
//                result = "{\"accessFlag\":\"true\"}";
            } else {
            	String url1 = "";
            	for(int i = 0; i < names.length; i++){
            	String serviceUrl1 = bundle.getString("userServiceUrl");
        		
        		InputStream inputXml1 = null;
        		url1 = serviceUrl1+"/services/UserValidation/serviceCheckUser?userId="+names[i]+"&productCode="+productCode;
//    			customerDeatilList = new ArrayList<HashMap<String, String>>();
    			inputXml1 = new URL(url1).openConnection().getInputStream();

    			DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory
    					.newInstance();
    			DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
    			Document doc1 = dBuilder1.parse(inputXml1);
    			doc1.getDocumentElement().normalize();
    			NodeList nList1 = doc1.getElementsByTagName("ns:serviceCheckUserResponse");
    	        org.w3c.dom.Node nNode = nList1.item(0);
    	       Element eElement = (Element) nNode;
//    	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
    	String userserviceres = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
    			//userserviceres = nList1.toString();
    			if(userserviceres.equals("true")){
				names1.add(names[i]);
    			}else if(userserviceres.equals("false")){
//    				names1[i] = "";	
    				emailAlreadyCnfig.put(names[i]);
    			}
            	}
            	strResult.put("accessFlag","true");
            	strResult.put("emailid",emailAlreadyCnfig);
            	
            	double assignedQuanityChat = (double) names1.size();
    			result = setConsumption(userId, productCode, assignedQuanityChat,serviceId);
            }
            try {
                json = new JSONObject(result);
                if (json.getBoolean("accessFlag")) {
                    String flag = "query";
                    
                    for (int i = 0; i < names1.size(); i++) {
                    	String name = names1.get(i).toString();
                        if (productCode.equalsIgnoreCase("sms")) {
                            String[] smsParam = { "userId", "msgUser",
                                    "msgPassword" };
                            String[] smsVal = {
                            		name.replace("@", "_").trim(), userId,
                                    smsPassowrd };
                            HTTPRequestPoster.callService(
                                    bundle.getString("smssetvalue"), smsParam,
                                    smsVal);
                        }
//                        UserValidationStub objUserValidationStub = new UserValidationStub();
//                        RaveUserExistence raveUserExistence = new RaveUserExistence();
//            			raveUserExistence.setUserId(names[i]);
//            			String userresult =objUserValidationStub.raveUserExistence(raveUserExistence).get_return();
                        ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
                        String serviceUrl = bundle.getString("userServiceUrl");
                		String url = "";
                		InputStream inputXml = null;
                		url = serviceUrl+"/services/UserValidation/raveUserExistence?userId="+name;
//            			customerDeatilList = new ArrayList<HashMap<String, String>>();
            			inputXml = new URL(url).openConnection().getInputStream();

            			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
            					.newInstance();
            			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            			Document doc = dBuilder.parse(inputXml);
            			doc.getDocumentElement().normalize();
//            			NodeList nList = doc.getElementsByTagName("ns:return");
//            			String userresult = nList.toString();
            			NodeList nList1 = doc.getElementsByTagName("ns:raveUserExistenceResponse");
            	        org.w3c.dom.Node nNode = nList1.item(0);
            	       Element eElement = (Element) nNode;
//            	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
            	String userresult = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
//            			org.w3c.dom.Node nNode = nList.item(0);
//            			String userresult="";
//        				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
////        					map = new HashMap<String, String>();
//        					Element eElement = (Element) nNode;
//        					userresult=eElement.getElementsByTagName("ax21:orderId").item(0).getTextContent();
//        				}
//            			if(userresult!=null && userresult.equals("true")){

                        SaveUser(userId, name, productCode, flag,serviceId);
                        Map<String, String> propertyMap = new HashMap<String, String>();
                		propertyMap.put("userId", name);
                		propertyMap.put("emailId", userId);
                		propertyMap.put("name", productCode);
                		propertyMap.put("password", "");
                		propertyMap.put("mobileNumber", "");
                		propertyMap.put("extension", "");
                		propertyMap.put("entityId", "");
                		activeMQ = new DefaultActiveMQProducer();
                		if(activeMQ!=null){
                			System.out.print("sssssss");
                			activeMQ.producerCall("UserServiceConfiguration", propertyMap, "");
                		}else{
                			System.out.print("nulll  hai");
                		}
            			
//            			}else if(userresult!=null && userresult.equals("false")){
//            				SaveUser(userId, names[i], productCode, flag);
//            			}
                    }
                }else{
                	return result;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//                result=result+e.getMessage();
                return "false";
//                return e.getMessage()+"first ex";
            }
        } catch (Exception e) {

            e.printStackTrace();
//            result=result+e.getMessage();
            return "false";
        }
        return strResult.toString();
    }

    public String splitUserLiveService(String personList, String productCode, String userId,String serviceId, int queueID) {
    	String result = "";
    	JSONObject strResult = new JSONObject();
//    	String userserviceres = "";
    	try {
            bundle = ResourceBundle.getBundle("serverConfig");
            String[] names = personList.split(",");
//            String[] names1 = new String[names.length];
            ArrayList names1 = new ArrayList();
            ap = new Auctions_WSDLPortTypeProxy();
            double assignedQuanity = (double) names.length;
            String smsPassowrd = "";
//            String result = "";
            JSONObject json = null;
            
            JSONArray emailAlreadyCnfig = new JSONArray();
            JSONArray mainArr = new JSONArray();
            if (productCode.equalsIgnoreCase("live_chat")){
            	String url1 = "";
            	for(int i = 0; i < names.length; i++){
            	String serviceUrl1 = bundle.getString("userServiceUrl");
        		
        		InputStream inputXml1 = null;
        		url1 = serviceUrl1+"/services/UserValidation/serviceCheckUser?userId="+names[i]+"&productCode="+productCode;
//    			customerDeatilList = new ArrayList<HashMap<String, String>>();
    			inputXml1 = new URL(url1).openConnection().getInputStream();

    			DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory
    					.newInstance();
    			DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
    			Document doc1 = dBuilder1.parse(inputXml1);
    			doc1.getDocumentElement().normalize();
    			NodeList nList1 = doc1.getElementsByTagName("ns:serviceCheckUserResponse");
    	        org.w3c.dom.Node nNode = nList1.item(0);
    	       Element eElement = (Element) nNode;
//    	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
    	String userserviceres = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
    			//userserviceres = nList1.toString();
    			if(userserviceres.equals("true")){
				names1.add(names[i]);
    			}else if(userserviceres.equals("false")){
//    				names1[i] = "";	
    				emailAlreadyCnfig.put(names[i]);
    			}
    			
    			
            	}
            	strResult.put("accessFlag","true");
            	strResult.put("emailid",emailAlreadyCnfig);
            	
            	double assignedQuanityChat = (double) names1.size();
    			result = setConsumption(userId, productCode, assignedQuanityChat,serviceId);
    			
            }

            
            try {
                json = new JSONObject(result);
                if (json.getBoolean("accessFlag")) {
                    String flag = "query";
                    
                    for (int i = 0; i < names1.size(); i++) {
                    	String name = names1.get(i).toString();
                        
                        ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
                        String serviceUrl = bundle.getString("userServiceUrl");
                		String url = "";
                		InputStream inputXml = null;
                		url = serviceUrl+"/services/UserValidation/raveUserExistence?userId="+name;
//            			customerDeatilList = new ArrayList<HashMap<String, String>>();
            			inputXml = new URL(url).openConnection().getInputStream();

            			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
            					.newInstance();
            			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            			Document doc = dBuilder.parse(inputXml);
            			doc.getDocumentElement().normalize();
//            			NodeList nList = doc.getElementsByTagName("ns:return");
//            			String userresult = nList.toString();
            			NodeList nList1 = doc.getElementsByTagName("ns:raveUserExistenceResponse");
            	        org.w3c.dom.Node nNode = nList1.item(0);
            	       Element eElement = (Element) nNode;
//            	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
            	String userresult = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
//            			org.w3c.dom.Node nNode = nList.item(0);
//            			String userresult="";
//        				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
////        					map = new HashMap<String, String>();
//        					Element eElement = (Element) nNode;
//        					userresult=eElement.getElementsByTagName("ax21:orderId").item(0).getTextContent();
//        				}
//            			if(userresult!=null && userresult.equals("true")){

            	userPanelDAO.SaveUser(userId, name, productCode, flag,serviceId);
                        if (productCode.equalsIgnoreCase("live_chat")){
                        	String url2="http://35.236.154.164:8180/UserValidation/services/UserValidation/addAgentInQueue?queueID="+queueID+"&assignedAgent="+name;
                          	 inputXml = new URL(url2).openConnection().getInputStream();
                  			DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
                  			DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
                  			Document doc2 = dBuilder2.parse(inputXml);
                  			doc2.getDocumentElement().normalize();	
                  			//NodeList nList = doc.getElementsByTagName("ns:return");
                  		       String  agentAddstatus =doc2.getElementsByTagName("ns:return").item(0).getTextContent();
                        }
                        
                        Map<String, String> propertyMap = new HashMap<String, String>();
                		propertyMap.put("userId", name);
                		propertyMap.put("emailId", userId);
                		propertyMap.put("name", productCode);
                		propertyMap.put("password", "");
                		propertyMap.put("mobileNumber", "");
                		propertyMap.put("extension", "");
                		propertyMap.put("entityId", "");
                		activeMQ = new DefaultActiveMQProducer();
                		if(activeMQ!=null){
                			System.out.print("sssssss");
                			activeMQ.producerCall("UserServiceConfiguration", propertyMap, "");
                		}else{
                			System.out.print("nulll  hai");
                		}
            			
//            			}else if(userresult!=null && userresult.equals("false")){
//            				SaveUser(userId, names[i], productCode, flag);
//            			}
                    }
                }else{
                	return result;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//                result=result+e.getMessage();
                return "false";
//                return e.getMessage()+"first ex";
            }
        } catch (Exception e) {

            e.printStackTrace();
//            result=result+e.getMessage();
            return "false";
        }
        return strResult.toString();
    }
    
    public String SaveUser(String userId, String assignedPerson,
            String productCode, String flag,String serviceId) {
        bundle = ResourceBundle.getBundle("serverConfig");
        userPanelDAO = new UserPanelDAOImpl();
        if (productCode.equalsIgnoreCase("VChat")) {
            String[] param1 = { "Chat", "userId" };
            String[] pval1 = { "Y", assignedPerson.replace("@", "_").trim() };
            HTTPRequestPoster.callService(bundle.getString("setflagY"), param1,
                    pval1);
        }
        String[] param = { productCode, "userId" };
        String[] pval = { "Y", assignedPerson.replace("@", "_").trim() };
        String r=HTTPRequestPoster
                .callService(bundle.getString("setflagY"), param, pval);
        if(r.equalsIgnoreCase("success") && productCode.equalsIgnoreCase("Chat")){
        	try{
//        	userPanelDAO.assignUserOpenfire(assignedPerson);
        	ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
            String serviceUrl = bundle.getString("userServiceUrl");
    		String url = "";
    		InputStream inputXml = null;
    		url = serviceUrl+"/services/UserValidation/assignUserOpenfire?assignedPerson="+assignedPerson;
//			customerDeatilList = new ArrayList<HashMap<String, String>>();
			inputXml = new URL(url).openConnection().getInputStream();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXml);
			doc.getDocumentElement().normalize();
			NodeList nList1 = doc.getElementsByTagName("ns:assignUserOpenfireResponse");
	        org.w3c.dom.Node nNode = nList1.item(0);
	       Element eElement = (Element) nNode;
//	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
	String openfireresult = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
        	}catch(Exception e){
        		
        	}
        }
        return userPanelDAO.SaveUser(userId, assignedPerson, productCode, flag,serviceId);
    }

    public List<String> assignedUserList(String userId, String productCode,String serviceId) {
        userPanelDAO = new UserPanelDAOImpl();
        return userPanelDAO.assignedUserList(userId, productCode, serviceId);
    }

    public String setConsumption(String userId, String productCode,
            double assignedQuanity,String serviceId) {
        JSONObject json = new JSONObject();
        ap = new Auctions_WSDLPortTypeProxy();
        try {
            if(productCode.equalsIgnoreCase("sms")){
                userId = getUserDetails(userId, "sms");
            }
            System.out.println(userId+"------"+productCode+"******"+assignedQuanity);
            String result = ap.serviceConsumption(userId, serviceId,
                    assignedQuanity, "Quanity", new Date());
            if (result.equals("saved")) {
                json.accumulate("accessFlag", "true");
            } else {
                json.accumulate("accessFlag", "false");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"accessFlag\":\"false\"}";
        }
        return json.toString();
    }

    public void deActivateFlag(final String productCode, final String userId,final int iserviceId) {
        Runnable task = new Runnable() {

         
            public void run() {
                try {
                    userPanelDAO = new UserPanelDAOImpl();
                    String strServiceId = Integer.toString(iserviceId);
                    List<String> assignedUserList = userPanelDAO
                            .assignedUserList(userId, productCode,strServiceId);
                    assignedUserList.add(userId.replace("@", "_").trim());
                    for (String assignedUser : assignedUserList) {
                        bundle = ResourceBundle.getBundle("serverConfig");
                        String[] param = { productCode, "userId" };
                        String[] pval = { "N",
                                assignedUser.replace("@", "_").trim() };
                        HTTPRequestPoster.callService(
                                bundle.getString("setflagY"), param, pval);
                        if (productCode.equals("VChat")) {
                            String[] param1 = { "Chat", "userId" };
                            String[] pval1 = { "N",
                                    assignedUser.replace("@", "_").trim() };
                            HTTPRequestPoster
                                    .callService(bundle.getString("setflagY"),
                                            param1, pval1);
                        }
                    }
                    userPanelDAO.deleteUser(userId, productCode);
                } catch (Exception ex) {
                    // handle error which cannot be thrown back
                    ex.printStackTrace();
                }
            }
        };
        new Thread(task, userId + "DeActivationThread").start();

    }

    public String getLimitService(String userId, String productCode) {
        Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();
        JSONObject json = new JSONObject();
        try {
            json.accumulate("limit",
                    ap.getNoOfserviceAvailable(userId, productCode));
            json.accumulate("userId", userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"limit\":\"expired\"}";
        }

        return json.toString();
    }
    
    public String getLimitServiceCustom(String userId, String productCode,String serviceId) {
    	  userPanelDAO = new UserPanelDAOImpl();
        JSONObject json = new JSONObject();
        try {
            json.accumulate("limit",
            		userPanelDAO.getServiceLimitDetails( userId,  productCode, serviceId));
            json.accumulate("userId", userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"limit\":\"expired\"}";
        }

        return json.toString();
    }

    public void deleteUser(String userId, String serviceId) {
        userPanelDAO = new UserPanelDAOImpl();
        userPanelDAO.deleteUser(userId, serviceId);
    }

    public String getUserDetails(String userName, String productCode) {
        userPanelDAO = new UserPanelDAOImpl();
        return userPanelDAO.getUserDetails(userName, productCode);
    }
    
    /////////////// Vivek 
    public String getReport(String userId, String productCode,String fromDate,String toDate) throws JSONException {
    	   userPanelDAO = new UserPanelDAOImpl();
        return userPanelDAO.getReportData(userId, productCode,fromDate,toDate);
    }
    
    public String callGetService(String urlStr, String[] paramName,
			String[] paramValue) {
		URL url;
		StringBuilder requestString = new StringBuilder(urlStr);
		if (paramName != null && paramName.length > 0) {
			requestString.append("?");
			for (int i = 0; i < paramName.length; i++) {
				requestString.append(paramName[i]);
				requestString.append("=");
				requestString.append(paramValue[i]);
				requestString.append("&");
			}
		}
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(requestString.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println(line);
			}
			br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return sb.toString();
	}

}
