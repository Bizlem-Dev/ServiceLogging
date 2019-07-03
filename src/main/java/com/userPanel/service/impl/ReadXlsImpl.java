package com.userPanel.service.impl;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ResourceBundle;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;


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
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import com.userPanel.service.ReadXls;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.springframework.stereotype.Service;



public class ReadXlsImpl implements ReadXls {
	UserPanelService userPanelService = new UserPanelServiceImpl();
    HTTPRequestPoster poster=new HTTPRequestPoster();
    static final String DB_URL_1 = "jdbc:mysql://localhost:3306/rave2";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    
    
    
    public String checkcontact( String compid, String email){
    	String password="";
    	try{
           
           
            if(compid != ""){
            	ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
                String serviceUrl = bundle.getString("userServiceUrl");
        		String url = "";
        		InputStream inputXml = null;
        		url = serviceUrl+"/services/UserValidation/raveUserExistence?userId="+email;
//    			customerDeatilList = new ArrayList<HashMap<String, String>>();
    			inputXml = new URL(url).openConnection().getInputStream();

    			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
    					.newInstance();
    			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    			Document doc = dBuilder.parse(inputXml);
    			doc.getDocumentElement().normalize();
//    			NodeList nList = doc.getElementsByTagName("ns:return");
//    			String userresult = nList.toString();
    			NodeList nList1 = doc.getElementsByTagName("ns:raveUserExistenceResponse");
    	        org.w3c.dom.Node nNode = nList1.item(0);
    	       Element eElement = (Element) nNode;
//    	        System.out.println(""+eElement.getElementsByTagName("ns:return").item(0).getTextContent());
    	String userresult = eElement.getElementsByTagName("ns:return").item(0).getTextContent();
         	
            	
            		try{
            			Class.forName("com.mysql.jdbc.Driver");
            		Connection conn = null;
            		conn = (Connection) DriverManager.getConnection(DB_URL_1, USER, PASS);           
           		//oraganization  details 	  
           	       //String getOrganizationSql = "select * from organization where flag = 0 limit "+count;
           	       String getOrganizationSql = "select password from person where email = '"+email+"'";
           	       Statement stmtOrganization=conn.createStatement();
           	       ResultSet rs=stmtOrganization.executeQuery(getOrganizationSql);
           	       while(rs.next()){
           	    	   password=rs.getString("password");  
           	       }
            		
            		conn.close();
            		rs.close();
            	}catch(Exception e){e.getMessage();}
    	return password;
            
           }}catch(Exception e){}
    	return password;
           }
    
    	
	 public  String  insertCompany(String compid,DB objDB,String userid,String compname,InputStream inputStream) throws IOException, BiffException {
	        ArrayList al = new ArrayList();
	        Workbook wb = Workbook.getWorkbook(inputStream);
	        String userresult="";
	        jxl.Sheet sheet = wb.getSheet(0);
	        String strSerialNumber = "";
	        String strcompanyname = "";
	        String strcontactperson = "";
	        String stremailid = "";
	        String strwebsite = "";
	        String strcontactdetail = "";
	        String strtype = "";
	        String strflage = "0";
	        String tabtype="";
	        String password="";
	        int count=0;
	        
	        int Successfulrecord=0;
	        int unsuccessfulrecord=0;
	        List companyArray = new ArrayList();
	        List errorArray =new ArrayList();
	        JSONObject errordatajson = new JSONObject();
	        JSONObject insidejson = null;
	        JSONArray jarray = new JSONArray();
	        
	        //BasicDBObject productArray = new BasicDBObject();
	        BasicDBObject singleObj = new BasicDBObject();
	        BasicDBObject compObj = null;
	        
	        BasicDBObject upProdObj = null;
	        BasicDBObject whereQuery = new BasicDBObject();
	        BasicDBObject updateCommand = new BasicDBObject();
         DBCollection coll = objDB.getCollection("companyinvite");//change collection name
         int k = 0;
         whereQuery.put("compid", compid);
         DBCursor cursor = coll.find(whereQuery);
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();
         String strDate = dateFormat.format(date);
        // System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
       try{
         if(cursor.hasNext()) {
       
         	for (int i = 0; i < sheet.getRows(); i++) {
         	  	Cell c[]=sheet.getRow(i);
         	  	count=0;
         	  	if(c[0].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[1].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[2].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[3].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[4].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[5].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[6].getContents() == ""){
         	  		count++;
         	  	}
//         	  	for(int j=0; j<c.length; j++){
//         	  		
//         	  		if(c[j].getContents()!="" && c[j].getContents()!=null){
//         	  			count++;
//         	  			}
//         	  	}
         	  	
 	            if (i > 1) {
 	            	if(count < 7){
 	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(3, i).getContents() != "" &&  sheet.getCell(6, i).getContents() != "") {
 	                	//mendiatory feilds are not give in excel go to else ad store that record in arraylist
 	                	
 	                	strSerialNumber = sheet.getCell(0, i).getContents();
 	                	strcompanyname = sheet.getCell(1, i).getContents();
 	                	strcontactperson = sheet.getCell(2, i).getContents();
 	                	stremailid = sheet.getCell(3, i).getContents();
 	                	strwebsite = sheet.getCell(4, i).getContents();
 	                	strcontactdetail = sheet.getCell(5, i).getContents();
 	                	strtype = sheet.getCell(6, i).getContents();
 	                	compObj = new BasicDBObject();
 	                	compObj.put("serialnumber", strSerialNumber);
 	                	compObj.put("strcompanyname", strcompanyname);
 	                	compObj.put("strcontactperson", strcontactperson);
 	                	compObj.put("stremailid", stremailid);
 	                	compObj.put("strwebsite", strwebsite);
 	                	compObj.put("strcontactdetail", strcontactdetail);
 	                	compObj.put("strtype", strtype);
 	                	compObj.put("strflage", strflage);
 	                	compObj.put("createdby", userid);
 	                	compObj.put("createddate", strDate);
 	                	compObj.put("tabtype", "Excel");
 	                password =	checkcontact( compid,stremailid);
	                    	//productArray.put(prodObj);
	                    	updateCommand.put("$push", new BasicDBObject("data", compObj));
	                        coll.update(whereQuery, updateCommand, true, true );
 	                
	                       	String subject = "Invition to join www.bizlem.com";
	                String htmlText  ="<html><body><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border-radius:0px '0px  10px 10px; border:1px solid #4096EE; background-color:#ffffff;'><tr><td align='center' valign='top'><table width='100%' border='0' align='center' cellpadding='5' cellspacing='5' style='border-top:6px solid #4096EE;'><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px; padding-right:10px;'><div style='font-size:24px; color:#4096EE;'>Dear "+stremailid+ ", </div><p>You have recieved a Invition from "+compname+" to join as"+strtype+" with "+compname+"</p><br/><p>Your Account login cradential at www.bizlem.com is:</p></br><p>User id: "+stremailid+"</p></br></p>Password:"+password+"<p>If you are agree to join click the below link:</p><br/><p> <a href='http://35.236.154.164:8078/ServiceLogging/userPanel?type=changeFlag&compid="+compid+"&email="+stremailid+"'</a><Strong>Agree</strong></p><tr><td align='left' valign='iddle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px;'></tr><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#333; font-size:13px;'><span style='color:#333; font-size:12px; font-family:Arial, Helvetica, sans-serif;'>With Regards,<br /><strong>Administrator</strong></span></tr></table></td></tr></table></body></html>";
	                		String[] checkparamKey = { "email", "msg", "subject" };
	                		String[] checkparamValue = { stremailid, htmlText, subject };
	                		String res = poster.callService("http://35.236.154.164:8082/portal/servlet/service/productselection.sendMailRfqSeller",checkparamKey, checkparamValue);	
	                		al.add(res);
	                		Successfulrecord++;
 	                }else{
 	                	strSerialNumber = sheet.getCell(0, i).getContents();
 	                	strcompanyname = sheet.getCell(1, i).getContents();
 	                	strcontactperson = sheet.getCell(2, i).getContents();
 	                	stremailid = sheet.getCell(3, i).getContents();
 	                	strwebsite = sheet.getCell(4, i).getContents();
 	                	strcontactdetail = sheet.getCell(5, i).getContents();
 	                	strtype = sheet.getCell(6, i).getContents();
 	                	
 	                	insidejson = new  JSONObject();
 	                	insidejson.put("serialnumber", strSerialNumber);
 	                	insidejson.put("strcompanyname", strcompanyname);
 	                	insidejson.put("strcontactperson", strcontactperson);
 	                	insidejson.put("stremailid", stremailid);
 	                	insidejson.put("strwebsite", strwebsite);
 	                	insidejson.put("strcontactdetail", strcontactdetail);
 	                	insidejson.put("strtype", strtype);
 	                	insidejson.put("strflage", strflage);
 	                	insidejson.put("createdby", userid);
 	                	insidejson.put("createddate", strDate);
 	                	jarray.put(insidejson);
 	                	unsuccessfulrecord++;
 	                }
 	                }else{
 	                   
 	                }
 	   			 
 	            	
         }
         	}
         	
         	errordatajson.put("data", jarray);
         	k++;
         }else{
         	
         	for (int i = 0; i < sheet.getRows(); i++) {
         		Cell c[]=sheet.getRow(i);
         	  	count=0;
         	  	if(c[0].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[1].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[2].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[3].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[4].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[5].getContents() == ""){
         	  		count++;
         	  	}
         	  	if(c[6].getContents() == ""){
         	  		count++;
         	  	}
//         	  	for(int j=0; j<c.length; j++){
//         	  		
//         	  		if(c[j].getContents()!="" && c[j].getContents()!=null){
//         	  			count++;
//         	  			}
//         	  	}
 	            if (i > 0) {
 	            	if(count < 7){
 	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(6, i).getContents() != "") {
 	                	strSerialNumber = sheet.getCell(0, i).getContents();
 	                	strcompanyname = sheet.getCell(1, i).getContents();
 	                	strcontactperson = sheet.getCell(2, i).getContents();
 	                	stremailid = sheet.getCell(3, i).getContents();
 	                	strwebsite = sheet.getCell(4, i).getContents();
 	                	strcontactdetail = sheet.getCell(5, i).getContents();
 	                	strtype = sheet.getCell(6, i).getContents();
 	                	
 	                	singleObj.put("compid", compid);
	                    singleObj.put("compname", compname);
	                    singleObj.put("userid", userid);
	                    	//singleObj.put("productCode", productCode);
	                    	//singleObj.put("consumequantity", "0");
	                    	compObj = new BasicDBObject();
	                    	compObj.put("serialnumber", strSerialNumber);
	 	                	compObj.put("strcompanyname", strcompanyname);
	 	                	compObj.put("strcontactperson", strcontactperson);
	 	                	compObj.put("stremailid", stremailid);
	 	                	compObj.put("strwebsite", strwebsite);
	 	                	compObj.put("strcontactdetail", strcontactdetail);
	 	                	compObj.put("strtype", strtype);
	 	                	compObj.put("strflage", strflage);
	 	                	compObj.put("createdby", userid);
	 	                	compObj.put("createddate", strDate);
	 	                	compObj.put("tabtype", "Excel");
	 	                	companyArray.add(compObj);
	 	                	Successfulrecord++;
 	                }else{
 	                	strSerialNumber = sheet.getCell(0, i).getContents();
 	 	             	strcompanyname = sheet.getCell(1, i).getContents();
 	 	             	strcontactperson = sheet.getCell(2, i).getContents();
 	 	             	stremailid = sheet.getCell(3, i).getContents();
 	 	             	strwebsite = sheet.getCell(4, i).getContents();
 	 	             	strcontactdetail = sheet.getCell(5, i).getContents();
 	 	             	strtype = sheet.getCell(6, i).getContents();
 	 	             	
 	 	             	insidejson = new  JSONObject();
 	 	             	insidejson.put("serialnumber", strSerialNumber);
 	 	             	insidejson.put("strcompanyname", strcompanyname);
 	 	             	insidejson.put("strcontactperson", strcontactperson);
 	 	             	insidejson.put("stremailid", stremailid);
 	 	             	insidejson.put("strwebsite", strwebsite);
 	 	             	insidejson.put("strcontactdetail", strcontactdetail);
 	 	             	insidejson.put("strtype", strtype);
 	 	             	insidejson.put("strflage", strflage);
 	 	             	insidejson.put("createdby", userid);
 	 	             	insidejson.put("createddate", strDate);
 	 	             	jarray.put(insidejson);
 	 	             	unsuccessfulrecord++;
 	                }
 	            }else{
 	               
 	             	
 	             }
 	            
 	            
 	            
 	            }
         	
         }errordatajson.put("data", jarray);
         }
         
         
	        if(k == 0){
	        	
	        	  password =	checkcontact( compid,stremailid);
	        singleObj.put("data", companyArray);
     	coll.insert(singleObj);
     	
        String htmlText  ="<html><body><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border-radius:0px '0px  10px 10px; border:1px solid #4096EE; background-color:#ffffff;'><tr><td align='center' valign='top'><table width='100%' border='0' align='center' cellpadding='5' cellspacing='5' style='border-top:6px solid #4096EE;'><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px; padding-right:10px;'><div style='font-size:24px; color:#4096EE;'>Dear "+stremailid+ ", </div><p>You have recieved a Invition from "+compname+" to join as"+strtype+" with "+compname+"</p><br/><p>Your Account login cradential at www.bizlem.com is:</p></br><p>User id: "+stremailid+"</p></br></p>Password:"+password+"<p>If you are agree to join click the below link:</p><br/><p> <a href='http://35.236.154.164:8078/ServiceLogging/userPanel?type=changeFlag&compid="+compid+"&email="+stremailid+"'</a><Strong>Agree</strong></p><tr><td align='left' valign='iddle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px;'></tr><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#333; font-size:13px;'><span style='color:#333; font-size:12px; font-family:Arial, Helvetica, sans-serif;'>With Regards,<br /><strong>Administrator</strong></span></tr></table></td></tr></table></body></html>";
       	String subject = "Invition to join www.bizlem.com";
		String[] checkparamKey = { "email", "msg", "subject" };
		String[] checkparamValue = { stremailid, htmlText, subject };
		String res = poster.callService(
				"http://35.236.154.164:8082/portal/servlet/service/productselection.sendMailRfqSeller",
				checkparamKey, checkparamValue);	
 al.add(res);
	        }else{
//        	BasicDBObject updateCommand = new BasicDBObject();
//             updateCommand.put("$push", new BasicDBObject("products", productArray));
//             coll.update(whereQuery, updateCommand, true, true );
         }}catch(Exception e){}
	        return Successfulrecord+"~"+unsuccessfulrecord+"~"+errordatajson.toString();
	    }

	
		public  String insertCompanyByform(String tabtype,String compid,DB objDB,String userid,String compname,String cname,String conperson,String email, String website,String contact,String type)
		{
			 ArrayList al = new ArrayList();
		        List companyArray = new ArrayList();
		        
		        String flag="0";
		        
		        BasicDBObject singleObj = new BasicDBObject();
		        BasicDBObject compObj = null;
		        BasicDBObject upProdObj = null;
		        BasicDBObject whereQuery = new BasicDBObject();
		        BasicDBObject updateCommand = new BasicDBObject();
	         DBCollection coll = objDB.getCollection("companyinvite");//change collection name
	         int k = 0;
	         whereQuery.put("compid", compid);
	         DBCursor cursor = coll.find(whereQuery);
	         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	         Date date = new Date();
	         String strDate = dateFormat.format(date);
	         if(cursor.hasNext()) {
	        	 
	        	 
	        	 compObj = new BasicDBObject();
              	compObj.put("serialnumber", "");
              	compObj.put("strcompanyname", cname);
              	compObj.put("strcontactperson", conperson);
              	compObj.put("stremailid", email);
              	compObj.put("strwebsite", website);
              	compObj.put("strcontactdetail", contact);
              	compObj.put("strtype", type);
              	compObj.put("strflage", flag);
              	compObj.put("createdby", userid);
              	compObj.put("createddate", strDate);
              	compObj.put("tabtype", tabtype);
              	
                 	//productArray.put(prodObj);
                 	updateCommand.put("$push", new BasicDBObject("data", compObj));
                     coll.update(whereQuery, updateCommand, true, true );
                     k++;
                     return "true";
	        	 
	         }else {
              	singleObj.put("compid", compid);
                singleObj.put("compname", compname);
                singleObj.put("userid", userid);
                	//singleObj.put("productCode", productCode);
                	//singleObj.put("consumequantity", "0");
                	compObj = new BasicDBObject();
                	compObj.put("serialnumber", "");
	                	compObj.put("strcompanyname", cname);
	                	compObj.put("strcontactperson", conperson);
	                	compObj.put("stremailid", email);
	                	compObj.put("strwebsite", website);
	                	compObj.put("strcontactdetail", contact);
	                	compObj.put("strtype", type);
	                	compObj.put("strflage", flag);
	                	compObj.put("createdby", userid);
	                	compObj.put("createddate", strDate);
	                	compObj.put("tabtype", tabtype);
	                	companyArray.add(compObj);
	         }
	         
	         if(k == 0){
	 	        singleObj.put("data", companyArray);
	      	coll.insert(singleObj);
	      	return "true";
	 	        }else{
	 	        	return "false";
//	 	        	BasicDBObject updateCommand = new BasicDBObject();
//	              updateCommand.put("$push", new BasicDBObject("products", productArray));
//	              coll.update(whereQuery, updateCommand, true, true );
	          }
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	   public  ArrayList insertProduct(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException {
	        ArrayList al = new ArrayList();
	        Workbook wb = Workbook.getWorkbook(inputStream);

	        jxl.Sheet sheet = wb.getSheet(0);
	        String strSerialNumber = "";
	        String strLevel1Code = "";
	        String strLevel1Desc = "";
	        String strLevel2Code = "";
	        String strLevel2Desc = "";
	        String strLevel3Code = "";
	        String strLevel3Desc = "";
	        String strLevel4Code = "";
	        String strLevel4Desc = "";
	        String strItemCode = "";
	        String strShortDesc = "";
	        String strLongDesc = "";
	        String strPoText = "";
	        String strOtherInfo1 = "";
	        String strOtherInfo2 = "";
	        String strOtherInfo3 = "";
	        String strOtherInfo4 = "";
	        List productArray = new ArrayList();
	        //BasicDBObject productArray = new BasicDBObject();
	        BasicDBObject singleObj = new BasicDBObject();
	        BasicDBObject prodObj = null;
	        BasicDBObject upProdObj = null;
	        BasicDBObject whereQuery = new BasicDBObject();
	        BasicDBObject updateCommand = new BasicDBObject();
            DBCollection coll = objDB.getCollection("mdmcoll");
            int k = 0;
            whereQuery.put("serviceId", serviceId);
            DBCursor cursor = coll.find(whereQuery);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String strDate = dateFormat.format(date);
           // System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
            if(cursor.hasNext()) {
            	
            	for (int i = 0; i < sheet.getRows(); i++) {
    	            if (i > 7) {
    	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(10, i).getContents() != "") {
    	                	strSerialNumber = sheet.getCell(0, i).getContents();
    	                	strLevel1Code = sheet.getCell(1, i).getContents();
    	                	strLevel1Desc = sheet.getCell(2, i).getContents();
    	                	strLevel2Code = sheet.getCell(3, i).getContents();
    	                	strLevel2Desc = sheet.getCell(4, i).getContents();
    	                	strLevel3Code = sheet.getCell(5, i).getContents();
    	                	strLevel3Desc = sheet.getCell(6, i).getContents();
    	                	strLevel4Code = sheet.getCell(7, i).getContents();
    	                	strLevel4Desc = sheet.getCell(8, i).getContents();
    	                	strItemCode = sheet.getCell(9, i).getContents();
    	                	strShortDesc = sheet.getCell(10, i).getContents();
    	                	strLongDesc = sheet.getCell(11, i).getContents();
    	                	strPoText = sheet.getCell(12, i).getContents();
    	                	strOtherInfo1 = sheet.getCell(13, i).getContents();
    	                	strOtherInfo2 = sheet.getCell(14, i).getContents();
    	                	strOtherInfo3 = sheet.getCell(15, i).getContents();
    	                	strOtherInfo4 = sheet.getCell(16, i).getContents();
    	                	prodObj = new BasicDBObject();
	                    	prodObj.put("serialnumber", strSerialNumber);
	                    	prodObj.put("level1code", strLevel1Code);
	                    	prodObj.put("level1desc", strLevel1Desc);
	                    	prodObj.put("level2code", strLevel2Code);
	                    	prodObj.put("level2desc", strLevel2Desc);
	                    	prodObj.put("level3code", strLevel3Code);
	                    	prodObj.put("level3desc", strLevel3Desc);
	                    	prodObj.put("level4code", strLevel4Code);
	                    	prodObj.put("level4desc", strLevel4Desc);
	                    	prodObj.put("itemcode", strItemCode);
	                    	prodObj.put("shortdesc", strShortDesc);
	                    	prodObj.put("longdesc", strLongDesc);
	                    	prodObj.put("potext", strPoText);
	                    	prodObj.put("otherinfo1", strOtherInfo1);
	                    	prodObj.put("otherinfo2", strOtherInfo2);
	                    	prodObj.put("otherinfo3", strOtherInfo3);
	                    	prodObj.put("otherinfo4", strOtherInfo4);
	                    	prodObj.put("createdby", userId);
	                    	prodObj.put("createddate", strDate);
	                    	prodObj.put("approvedby", "");
	                    	prodObj.put("approveddate", "");
	                    	prodObj.put("smeuser", "");
	                    	prodObj.put("assigneddate", "");
	                    	//productArray.put(prodObj);
	                    	updateCommand.put("$push", new BasicDBObject("products", prodObj));
	                        coll.update(whereQuery, updateCommand, true, true );
    	                }
    	            }
            	}
            	
            	k++;
            }else{
            	
            	for (int i = 0; i < sheet.getRows(); i++) {
    	            if (i > 7) {
    	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(10, i).getContents() != "") {
    	                	strSerialNumber = sheet.getCell(0, i).getContents();
    	                	strLevel1Code = sheet.getCell(1, i).getContents();
    	                	strLevel1Desc = sheet.getCell(2, i).getContents();
    	                	strLevel2Code = sheet.getCell(3, i).getContents();
    	                	strLevel2Desc = sheet.getCell(4, i).getContents();
    	                	strLevel3Code = sheet.getCell(5, i).getContents();
    	                	strLevel3Desc = sheet.getCell(6, i).getContents();
    	                	strLevel4Code = sheet.getCell(7, i).getContents();
    	                	strLevel4Desc = sheet.getCell(8, i).getContents();
    	                	strItemCode = sheet.getCell(9, i).getContents();
    	                	strShortDesc = sheet.getCell(10, i).getContents();
    	                	strLongDesc = sheet.getCell(11, i).getContents();
    	                	strPoText = sheet.getCell(12, i).getContents();
    	                	strOtherInfo1 = sheet.getCell(13, i).getContents();
    	                	strOtherInfo2 = sheet.getCell(14, i).getContents();
    	                	strOtherInfo3 = sheet.getCell(15, i).getContents();
    	                	strOtherInfo4 = sheet.getCell(16, i).getContents();
    	                	singleObj.put("serviceId", serviceId);
	                    	//singleObj.put("customerId", userId);
	                    	singleObj.put("quantity", quantity);
	                    	singleObj.put("productCode", productCode);
	                    	singleObj.put("consumequantity", "0");
	                    	prodObj = new BasicDBObject();
	                    	prodObj.put("serialnumber", strSerialNumber);
	                    	prodObj.put("level1code", strLevel1Code);
	                    	prodObj.put("level1desc", strLevel1Desc);
	                    	prodObj.put("level2code", strLevel2Code);
	                    	prodObj.put("level2desc", strLevel2Desc);
	                    	prodObj.put("level3code", strLevel3Code);
	                    	prodObj.put("level3desc", strLevel3Desc);
	                    	prodObj.put("level4code", strLevel4Code);
	                    	prodObj.put("level4desc", strLevel4Desc);
	                    	prodObj.put("itemcode", strItemCode);
	                    	prodObj.put("shortdesc", strShortDesc);
	                    	prodObj.put("longdesc", strLongDesc);
	                    	prodObj.put("potext", strPoText);
	                    	prodObj.put("otherinfo1", strOtherInfo1);
	                    	prodObj.put("otherinfo2", strOtherInfo2);
	                    	prodObj.put("otherinfo3", strOtherInfo3);
	                    	prodObj.put("otherinfo4", strOtherInfo4);
	                    	prodObj.put("createdby", userId);
	                    	prodObj.put("createddate", strDate);
	                    	prodObj.put("approvedby", "");
	                    	prodObj.put("approveddate", "");
	                    	prodObj.put("smeuser", "");
	                    	prodObj.put("assigneddate", "");
	                    	productArray.add(prodObj);
    	                }else{
    	                    System.out.println("svs");
    	                }
    	            }
            	}
            	
            }
	        
	        if(k == 0){
	        singleObj.put("products", productArray);
        	coll.insert(singleObj);
	        }else{
//	        	BasicDBObject updateCommand = new BasicDBObject();
//                updateCommand.put("$push", new BasicDBObject("products", productArray));
//                coll.update(whereQuery, updateCommand, true, true );
            }
	        return al;
	    }

	    public  ArrayList insertAddress(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException {
	        ArrayList al = new ArrayList();
	        Workbook wb = Workbook.getWorkbook(inputStream);

	        jxl.Sheet sheet = wb.getSheet(0);
	        String strSerialNumber = "";
	        String strStreetAddress1 = "";
	        String strStreetAddress2 = "";
	        String strTownCity = "";
	        String strRegionState = "";
	        String strCountry = "";
	        String strZip = "";
	        String strEmailId = "";
	        String strTelMob1 = "";
	        String strTelMob2 = "";
	        String strTelMob3 = "";
	        List addrArray = new ArrayList();
	        BasicDBObject singleObj = new BasicDBObject();
	        BasicDBObject addrObj = null;
	        BasicDBObject upaddrObj = null;
	        BasicDBObject whereQuery = new BasicDBObject();
	        BasicDBObject updateCommand = new BasicDBObject();
            DBCollection coll = objDB.getCollection("mdmcoll");
            int k = 0;
            whereQuery.put("serviceId", serviceId);
            DBCursor cursor = coll.find(whereQuery);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String strDate = dateFormat.format(date);
            if(cursor.hasNext()) {
            	
            	for (int i = 0; i < sheet.getRows(); i++) {
    	            if (i > 7) {
    	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(3, i).getContents() != "" && sheet.getCell(5, i).getContents() != "") {
    	                	strSerialNumber = sheet.getCell(0, i).getContents();
    	                	strStreetAddress1 = sheet.getCell(1, i).getContents();
    	                	strStreetAddress2 = sheet.getCell(2, i).getContents();
    	                	strTownCity = sheet.getCell(3, i).getContents();
    	                	strRegionState = sheet.getCell(4, i).getContents();
    	                	strCountry = sheet.getCell(5, i).getContents();
    	                	strZip = sheet.getCell(6, i).getContents();
    	                	strEmailId = sheet.getCell(7, i).getContents();
    	                	strTelMob1 = sheet.getCell(8, i).getContents();
    	                	strTelMob2 = sheet.getCell(9, i).getContents();
    	                	strTelMob3 = sheet.getCell(10, i).getContents();
    	                	addrObj = new BasicDBObject();
	                    	addrObj.put("serialnumber", strSerialNumber);
	                    	addrObj.put("streetaddress1", strStreetAddress1);
	                    	addrObj.put("streetaddress2", strStreetAddress2);
	                    	addrObj.put("towncity", strTownCity);
	                    	addrObj.put("regionstate", strRegionState);
	                    	addrObj.put("country", strCountry);
	                    	addrObj.put("pincode", strZip);
	                    	addrObj.put("email", strEmailId);
	                    	addrObj.put("telmob1", strTelMob1);
	                    	addrObj.put("telmob2", strTelMob2);
	                    	addrObj.put("telmob3", strTelMob3);
	                    	addrObj.put("createdby", userId);
	                    	addrObj.put("createddate", strDate);
	                    	addrObj.put("approvedby", "");
	                    	addrObj.put("approveddate", "");
	                    	addrObj.put("smeuser", "");
	                    	addrObj.put("assigneddate", "");
	                    	//addrArray.add(addrObj);
	                    	updateCommand.put("$push", new BasicDBObject("address", addrObj));
	                        coll.update(whereQuery, updateCommand, true, true );
    	                }
    	            }
            	}
            	
            	k++;
            }else{
            	
            	for (int i = 0; i < sheet.getRows(); i++) {
    	            if (i > 7) {
    	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(3, i).getContents() != "" && sheet.getCell(5, i).getContents() != "") {
    	                	strSerialNumber = sheet.getCell(0, i).getContents();
    	                	strStreetAddress1 = sheet.getCell(1, i).getContents();
    	                	strStreetAddress2 = sheet.getCell(2, i).getContents();
    	                	strTownCity = sheet.getCell(3, i).getContents();
    	                	strRegionState = sheet.getCell(4, i).getContents();
    	                	strCountry = sheet.getCell(5, i).getContents();
    	                	strZip = sheet.getCell(6, i).getContents();
    	                	strEmailId = sheet.getCell(7, i).getContents();
    	                	strTelMob1 = sheet.getCell(8, i).getContents();
    	                	strTelMob2 = sheet.getCell(9, i).getContents();
    	                	strTelMob3 = sheet.getCell(10, i).getContents();
    	                	singleObj.put("serviceId", serviceId);
	                    	singleObj.put("customerId", userId);
	                    	singleObj.put("quantity", quantity);
	                    	singleObj.put("productCode", productCode);
	                    	singleObj.put("consumequantity", "0");
	                    	addrObj = new BasicDBObject();
	                    	addrObj.put("serialnumber", strSerialNumber);
	                    	addrObj.put("streetaddress1", strStreetAddress1);
	                    	addrObj.put("streetaddress2", strStreetAddress2);
	                    	addrObj.put("towncity", strTownCity);
	                    	addrObj.put("regionstate", strRegionState);
	                    	addrObj.put("country", strCountry);
	                    	addrObj.put("pincode", strZip);
	                    	addrObj.put("email", strEmailId);
	                    	addrObj.put("telmob1", strTelMob1);
	                    	addrObj.put("telmob2", strTelMob2);
	                    	addrObj.put("telmob3", strTelMob3);
	                    	addrObj.put("createdby", userId);
	                    	addrObj.put("createddate", strDate);
	                    	addrObj.put("approvedby", "");
	                    	addrObj.put("approveddate", "");
	                    	addrObj.put("smeuser", "");
	                    	addrObj.put("assigneddate", "");
	                    	addrArray.add(addrObj);
    	                }else{
    	                    System.out.println("svs");
    	                }
    	            }
            	}
            	
            }
	        
	        if(k == 0){
	        singleObj.put("address", addrArray);
        	coll.insert(singleObj);
	        }else{
//	        	BasicDBObject updateCommand = new BasicDBObject();
//                updateCommand.put("$push", new BasicDBObject("address", addrArray));
//                coll.update(whereQuery, updateCommand, true, true );
            }
	        return al;
	    }

	    public  ArrayList insertCustomerVendor(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException {
	        ArrayList al = new ArrayList();
	        Workbook wb = Workbook.getWorkbook(inputStream);

	        jxl.Sheet sheet = wb.getSheet(0);

	        String strSerialNumber = "";
			 String strVendorCode = "";
			 String strVendorName = "";
	        String strStreetAddress1 = "";
	        String strStreetAddress2 = "";
	        String strTownCity = "";
	        String strRegionState = "";
	        String strCountry = "";
	        String strZip = "";
			String strWebsite = "";
	        String strEmailId = "";
	        String strTelMob1 = "";
	        String strTelMob2 = "";
	        String strTelMob3 = "";
			String strFax = "";
			String strContactPerson = "";
			String strContactPersonEmailId = "";
			String strContactPersonTel = "";
	        List vendorArray = new ArrayList();
	        BasicDBObject singleObj = new BasicDBObject();
	        BasicDBObject vendorObj = null;
	        BasicDBObject upvendorObj = null;
	        BasicDBObject whereQuery = new BasicDBObject();
	        BasicDBObject updateCommand = new BasicDBObject();
           DBCollection coll = objDB.getCollection("mdmcoll");
           int k = 0;
           whereQuery.put("serviceId", serviceId);
           DBCursor cursor = coll.find(whereQuery);
           DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           Date date = new Date();
           String strDate = dateFormat.format(date);
           if(cursor.hasNext()) {
           	
           	for (int i = 0; i < sheet.getRows(); i++) {
   	            if (i > 7) {
   	                if (sheet.getCell(2, i).getContents() != "" && sheet.getCell(5, i).getContents() != "" && sheet.getCell(7, i).getContents() != "") {
   	                	strSerialNumber = sheet.getCell(0, i).getContents();
							strVendorCode = sheet.getCell(1, i).getContents();
							strVendorName = sheet.getCell(2, i).getContents();
   	                	strStreetAddress1 = sheet.getCell(3, i).getContents();
   	                	strStreetAddress2 = sheet.getCell(4, i).getContents();
   	                	strTownCity = sheet.getCell(5, i).getContents();
   	                	strRegionState = sheet.getCell(6, i).getContents();
   	                	strCountry = sheet.getCell(7, i).getContents();
   	                	strZip = sheet.getCell(8, i).getContents();
							strWebsite = sheet.getCell(9, i).getContents();
   	                	strEmailId = sheet.getCell(10, i).getContents();
   	                	strTelMob1 = sheet.getCell(11, i).getContents();
   	                	strTelMob2 = sheet.getCell(12, i).getContents();
   	                	strTelMob3 = sheet.getCell(13, i).getContents();
							strFax = sheet.getCell(14, i).getContents();
							strContactPerson = sheet.getCell(15, i).getContents();
							strContactPersonEmailId = sheet.getCell(16, i).getContents();
							strContactPersonTel = sheet.getCell(17, i).getContents();
   	                	vendorObj = new BasicDBObject();
	                    	vendorObj.put("serialnumber", strSerialNumber);
							vendorObj.put("vendorcode", strVendorCode);
							vendorObj.put("vendorname", strVendorName);
	                    	vendorObj.put("streetaddress1", strStreetAddress1);
	                    	vendorObj.put("streetaddress2", strStreetAddress2);
	                    	vendorObj.put("towncity", strTownCity);
	                    	vendorObj.put("regionstate", strRegionState);
	                    	vendorObj.put("country", strCountry);
	                    	vendorObj.put("pincode", strZip);
							vendorObj.put("strWebsite", strWebsite);
	                    	vendorObj.put("email", strEmailId);
	                    	vendorObj.put("telmob1", strTelMob1);
	                    	vendorObj.put("telmob2", strTelMob2);
	                    	vendorObj.put("telmob3", strTelMob3);
							vendorObj.put("fax", strFax);
							vendorObj.put("contactperson", strContactPerson);
							vendorObj.put("contactpersonemailid", strContactPersonEmailId);
							vendorObj.put("contactpersontel", strContactPersonTel);
							vendorObj.put("createdby", userId);
							vendorObj.put("createddate", strDate);
							vendorObj.put("approvedby", "");
							vendorObj.put("approveddate", "");
							vendorObj.put("smeuser", "");
							vendorObj.put("assigneddate", "");
	                    	//vendorArray.add(vendorObj);
							
				               updateCommand.put("$push", new BasicDBObject("customervendor", vendorObj));
				               coll.update(whereQuery, updateCommand, true, true );
   	                }
   	            }
           	}
           	
           	k++;
           }else{
           	
           	for (int i = 0; i < sheet.getRows(); i++) {
   	            if (i > 7) {
   	                if (sheet.getCell(2, i).getContents() != "" && sheet.getCell(5, i).getContents() != "" && sheet.getCell(7, i).getContents() != "") {
   	                	strSerialNumber = sheet.getCell(0, i).getContents();
							strVendorCode = sheet.getCell(1, i).getContents();
							strVendorName = sheet.getCell(2, i).getContents();
   	                	strStreetAddress1 = sheet.getCell(3, i).getContents();
   	                	strStreetAddress2 = sheet.getCell(4, i).getContents();
   	                	strTownCity = sheet.getCell(5, i).getContents();
   	                	strRegionState = sheet.getCell(6, i).getContents();
   	                	strCountry = sheet.getCell(7, i).getContents();
   	                	strZip = sheet.getCell(8, i).getContents();
							strWebsite = sheet.getCell(9, i).getContents();
   	                	strEmailId = sheet.getCell(10, i).getContents();
   	                	strTelMob1 = sheet.getCell(11, i).getContents();
   	                	strTelMob2 = sheet.getCell(12, i).getContents();
   	                	strTelMob3 = sheet.getCell(13, i).getContents();
							strFax = sheet.getCell(14, i).getContents();
							strContactPerson = sheet.getCell(15, i).getContents();
							strContactPersonEmailId = sheet.getCell(16, i).getContents();
							strContactPersonTel = sheet.getCell(17, i).getContents();
   	                	singleObj.put("serviceId", serviceId);
	                    	singleObj.put("customerId", userId);
	                    	singleObj.put("quantity", quantity);
	                    	singleObj.put("productCode", productCode);
	                    	singleObj.put("consumequantity", "0");
	                    	vendorObj = new BasicDBObject();
	                    	vendorObj.put("serialnumber", strSerialNumber);
							vendorObj.put("vendorcode", strVendorCode);
							vendorObj.put("vendorname", strVendorName);
	                    	vendorObj.put("streetaddress1", strStreetAddress1);
	                    	vendorObj.put("streetaddress2", strStreetAddress2);
	                    	vendorObj.put("towncity", strTownCity);
	                    	vendorObj.put("regionstate", strRegionState);
	                    	vendorObj.put("country", strCountry);
	                    	vendorObj.put("pincode", strZip);
							vendorObj.put("strWebsite", strWebsite);
	                    	vendorObj.put("email", strEmailId);
	                    	vendorObj.put("telmob1", strTelMob1);
	                    	vendorObj.put("telmob2", strTelMob2);
	                    	vendorObj.put("telmob3", strTelMob3);
							vendorObj.put("fax", strFax);
							vendorObj.put("contactperson", strContactPerson);
							vendorObj.put("contactpersonemailid", strContactPersonEmailId);
							vendorObj.put("contactpersontel", strContactPersonTel);
							vendorObj.put("createdby", userId);
							vendorObj.put("createddate", strDate);
							vendorObj.put("approvedby", "");
							vendorObj.put("approveddate", "");
							vendorObj.put("smeuser", "");
							vendorObj.put("assigneddate", "");
	                    	vendorArray.add(vendorObj);
   	                }else{
   	                    System.out.println("svs");
   	                }
   	            }
           	}
           	
           }
	        
	        if(k == 0){
	        singleObj.put("customervendor", vendorArray);
       	coll.insert(singleObj);
	        }else{
//	        	BasicDBObject updateCommand = new BasicDBObject();
//               updateCommand.put("$push", new BasicDBObject("customervendor", vendorArray));
//               coll.update(whereQuery, updateCommand, true, true );
           }
	        
	        return al;
	    }

	    public  ArrayList insertVendorItem(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException {
	        ArrayList al = new ArrayList();
	        Workbook wb = Workbook.getWorkbook(inputStream);

	        jxl.Sheet sheet = wb.getSheet(0);
	        
			 String strSerialNumber = "";
			 String strVendorCode = "";
			 String strItemCode = "";
	        String strStatus = "";
	        String strDate = "";
	        List vendorArray = new ArrayList();
	        BasicDBObject singleObj = new BasicDBObject();
	        BasicDBObject vendorObj = null;
	        BasicDBObject upvendorObj = null;
	        BasicDBObject whereQuery = new BasicDBObject();

        	BasicDBObject updateCommand = new BasicDBObject();
           DBCollection coll = objDB.getCollection("mdmcoll");
           int k = 0;
           whereQuery.put("serviceId", serviceId);
           DBCursor cursor = coll.find(whereQuery);
           DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           Date date = new Date();
           String strDate1 = dateFormat.format(date);
           if(cursor.hasNext()) {
           	
           	for (int i = 0; i < sheet.getRows(); i++) {
   	            if (i > 7) {
   	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(2, i).getContents() != "") {
   	                	strSerialNumber = sheet.getCell(0, i).getContents();
							strVendorCode = sheet.getCell(1, i).getContents();
							strItemCode = sheet.getCell(2, i).getContents();
   	                	strStatus = sheet.getCell(3, i).getContents();
   	                	strDate = sheet.getCell(4, i).getContents();
   	                	vendorObj = new BasicDBObject();
	                    	vendorObj.put("serialnumber", strSerialNumber);
							vendorObj.put("vendorcode", strVendorCode);
							vendorObj.put("itemcode", strItemCode);
	                    	vendorObj.put("status", strStatus);
	                    	vendorObj.put("date", strDate);
	                    	vendorObj.put("createdby", userId);
	                    	vendorObj.put("createddate", strDate1);
	                    	vendorObj.put("approvedby", "");
	                    	vendorObj.put("approveddate", "");
	                    	vendorObj.put("smeuser", "");
							vendorObj.put("assigneddate", "");
	                    	//vendorArray.add(vendorObj);
	                        updateCommand.put("$push", new BasicDBObject("vendormapping", vendorObj));
	                        coll.update(whereQuery, updateCommand, true, true );
   	                }
   	            }
           	}
           	
           	k++;
           }else{
           	
           	for (int i = 0; i < sheet.getRows(); i++) {
   	            if (i > 7) {
   	                if (sheet.getCell(1, i).getContents() != "" && sheet.getCell(2, i).getContents() != "") {
   	                	strSerialNumber = sheet.getCell(0, i).getContents();
							strVendorCode = sheet.getCell(1, i).getContents();
							strItemCode = sheet.getCell(2, i).getContents();
   	                	strStatus = sheet.getCell(3, i).getContents();
   	                	strDate = sheet.getCell(4, i).getContents();
   	                	singleObj.put("serviceId", serviceId);
	                    	singleObj.put("customerId", userId);
	                    	singleObj.put("quantity", quantity);
	                    	singleObj.put("productCode", productCode);
	                    	singleObj.put("consumequantity", "0");
	                    	vendorObj = new BasicDBObject();
	                    	vendorObj.put("serialnumber", strSerialNumber);
							vendorObj.put("vendorcode", strVendorCode);
							vendorObj.put("itemcode", strItemCode);
	                    	vendorObj.put("status", strStatus);
	                    	vendorObj.put("date", strDate);
	                    	vendorObj.put("createdby", userId);
	                    	vendorObj.put("createddate", strDate1);
	                    	vendorObj.put("approvedby", "");
	                    	vendorObj.put("approveddate", "");
	                    	vendorObj.put("smeuser", "");
							vendorObj.put("assigneddate", "");
	                    	vendorArray.add(vendorObj);
   	                }else{
   	                    System.out.println("svs");
   	                }
   	            }
           	}
           	
           }
	        
	        if(k == 0){
	        singleObj.put("vendormapping", vendorArray);
       	coll.insert(singleObj);
	        }else{
//	        	BasicDBObject updateCommand = new BasicDBObject();
//               updateCommand.put("$push", new BasicDBObject("vendormapping", vendorArray));
//               coll.update(whereQuery, updateCommand, true, true );
           }
	        
	        return al;
	    }
	    
	    public DB getMongoDb() {
	        DB db = null;
	        try {
	            // To connect to mongodb server
	            MongoClient mongoClient = new MongoClient("localhost", 27017);
	            db = mongoClient.getDB("mdmservice");
	        } catch (Exception e) {

	        }
	        return db;
	    }
	    
	    public DB getMongoDbGeneric(String databaseName) {
	        DB db = null;
	        try {
	            // To connect to mongodb server
	            MongoClient mongoClient = new MongoClient("localhost", 27017);
	            db = mongoClient.getDB(databaseName);
	        } catch (Exception e) {

	        }
	        return db;
	    }
	    
	    public String getProcessLength(DB db,String serviceId) {
	    	String result = "";
	    	int vendorItemCount = 0;
	    	int prodCount = 0;
	    	int cvCount = 0;
	    	int addrCount = 0;
	        try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("products") != null){
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
	            	 prodCount = objProductArray.size();
	            	}
	            	if(objDBObject.get("address") != null){
	            	ArrayList objAddrArray = (ArrayList) objDBObject.get("address");
	            	 addrCount = objAddrArray.size();
	            	}
	            	if(objDBObject.get("customervendor") != null){
	            	ArrayList objCVArray = (ArrayList) objDBObject.get("customervendor");
	            	 cvCount = objCVArray.size();
	            	
	            	}
	            	if(objDBObject.get("vendormapping") != null){
	            	ArrayList objVIArray = (ArrayList) objDBObject.get("vendormapping");
	            	 vendorItemCount = objVIArray.size();
	            	}
	            	
	            	
	            
		             //result = prodCount + addrCount + cvCount + vendorItemCount;
		             result = prodCount + "," + addrCount +"," + cvCount +","+ vendorItemCount;
	            }else{
	            	 result = prodCount + "," + addrCount +"," + cvCount +","+ vendorItemCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }
	    public JSONObject  getInvitedCompany(String compid){
	    	
	    	String cname =null;
	    	String pname=null;
	    	String cwebsite =null;
	    	String cemail =null;
	    	String ccontat =null;
	    	String category =null;
	    	 String cdate= null;
	    	 String ctype= null;
	    	 String cflage= null;
	    	 String tabtype= "";
	    	ArrayList objProductArray = null;
	    	
	    	
	    	JSONObject mainJson = new JSONObject();
	    	JSONArray jarray = new JSONArray();
	    	JSONObject obj = null;
	    	try {
	    	DB objDB = getMongoDbGeneric("invite");
	        DBCollection coll = objDB.getCollection("companyinvite");
	        BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("compid", compid);
            DBCursor cursor = coll.find(whereQuery);
            if(cursor.hasNext()) {
            	DBObject objDBObject = cursor.next();
           	 objProductArray = (ArrayList) objDBObject.get("data");
    		for(int i=0;i<objProductArray.size();i++){
    			obj = new JSONObject();
    			String record = objProductArray.get(i).toString();
        		JSONObject objMongo = new JSONObject(record);
        		cname = objMongo.get("strcompanyname").toString();
        		pname = objMongo.get("strcontactperson").toString();
    			cwebsite = objMongo.get("strwebsite").toString();
    			cemail = objMongo.get("stremailid").toString();
    			ccontat = objMongo.get("strcontactdetail").toString();
    			cdate=objMongo.get("createddate").toString();
    			ctype=objMongo.get("strtype").toString();
    			cflage=objMongo.get("strflage").toString();
                if(!objMongo.isNull("tabtype")){
                	tabtype=objMongo.get("tabtype").toString();
    			}
    			obj.put("compname", cname);
    			obj.put("contperson", pname);
    			obj.put("website", cwebsite);
    			obj.put("email", cemail);
    			obj.put("contactnumber", ccontat);
    			obj.put("createddate", cdate);
    			obj.put("type", ctype);
    			obj.put("flag", cflage);
    			obj.put("tabtype", tabtype);
    			
    			//category = cname+","+cwebsite+","+cemail+","+ccontat+","+cdate+","+ctype+","+cflage;
    			jarray.put(obj);
        		
    		}
    		mainJson.put("data", jarray);
            	
            }}catch(Exception e){}
	        return mainJson;
	    }

	    
	    public String getProduct(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	ArrayList objProductArray = null;
	    	String prodUser = "";
	    	String serialnumber = "";
	    	String level1desc = "";
	    	String level2desc = "";
	    	String level3desc = "";
	    	String level4desc = "";
	    	String shortdesc = "";
	    	String category = "";
	    	String appr  = "";
	    	String solr_result  = "";
	    	JSONObject mainJson = new JSONObject();
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	 objProductArray = (ArrayList) objDBObject.get("products");
	            	 JSONObject jsonMat = null;
	            	 JSONObject jsonPaMat = null;
	            	 JSONObject jsonPaMatIn = null;
	            	 JSONObject jsonUnMat = null;
	            	 JSONArray jarrayMat = new JSONArray();
	            	 JSONArray jarrayPaMat = new JSONArray();
	            	 JSONArray jarrayPaMatIn = null;
	            	 JSONArray jarrayUnMat = new JSONArray();
	         		
	            	for(int i=0;i<objProductArray.size();i++){
	            		jarrayPaMatIn = new JSONArray();
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		 //prodUser = objMongo.get("createdby").toString();
	            		prodUser = objMongo.get("smeuser").toString();
	            		 appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(appr.equals("")){
	            			serialnumber = objMongo.get("serialnumber").toString();
	            			level1desc = objMongo.get("level1desc").toString();
	            			level2desc = objMongo.get("level2desc").toString();
	            			level3desc = objMongo.get("level3desc").toString();
	            			level4desc = objMongo.get("level4desc").toString();
	            			shortdesc = objMongo.get("shortdesc").toString();
	            			category = level1desc+","+level2desc+","+level3desc+","+level4desc;
	            			/*if(!objMongo.isNull("solr_result")){
	            				solr_result = objMongo.get("solr_result").toString();
	            				if(solr_result.equals("matched")){
	            					String v_modelnumber = objMongo.get("temp_modelnumber").toString();
            	            		String v_image = objMongo.get("temp_image").toString();
            	            		String v_productname = objMongo.get("temp_productname").toString();
            	            		String v_companyname = objMongo.get("temp_companyname").toString();
            	            		String prodId = objMongo.get("temp_prodid").toString();
	            					jsonMat = new JSONObject();
            	            		jsonMat.put("prodpos",i);
            	            		jsonMat.put("prodid",prodId);
            	            		jsonMat.put("id",serialnumber);
            	    				jsonMat.put("category",category);
            	    				jsonMat.put("shortdesc",shortdesc);
            	    				jsonMat.put("modelnumber",v_modelnumber);
            	    				jsonMat.put("image",v_image);
            	    				jsonMat.put("productname",v_productname);
            	    				jsonMat.put("companyname",v_companyname);
            	    				jarrayMat.put(jsonMat);
	            				}else if(solr_result.equals("partmatched")){
	            					String v_modelnumber = objMongo.get("temp_modelnumber").toString();
            	            		String v_image = objMongo.get("temp_image").toString();
            	            		String v_productname = objMongo.get("temp_productname").toString();
            	            		String v_companyname = objMongo.get("temp_companyname").toString();
            	            		String prodId = objMongo.get("temp_prodid").toString();
            	            		jsonPaMat = new JSONObject();
            	            		jsonPaMat.put("prodpos",i);
            	            		jsonPaMat.put("prodid",prodId);
            	            		jsonPaMat.put("id",serialnumber);
            	    				jsonPaMat.put("category",category);
            	    				jsonPaMat.put("shortdesc",shortdesc);
            	    				jsonPaMat.put("modelnumber",v_modelnumber);
            	    				jsonPaMat.put("image",v_image);
            	    				jsonPaMat.put("productname",v_productname);
            	    				jsonPaMat.put("companyname",v_companyname);
            	    				jsonPaMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
            	    				jarrayPaMat.put(jsonPaMat);
	            				}
	            			}else{*/
	            				String query = "";
	            				float max = 5.0f;
	            				float min = 3.0f;
	            				//Float min = 5.0;
	            				if(objMongo.isNull("reject")){
	            				if(objMongo.isNull("additionalmetadata")){	
	            			        query = "v_modelnumber:"+serialnumber+" OR v_shortdesc:"+shortdesc+" AND v_category:"+category;
	            			        max = 5.0f;
	            			        min = 3.0f;
	            				}else{
	            				     query = objMongo.get("additionalmetadata").toString();
	            				     max = 3.0f;
		            			        min = 1.0f;
	            				}
	            	        while(query.indexOf(":") != -1){
	            	           query = query.replaceAll(":", "%3A");
	            	        }
	            	        while(query.indexOf(" ") != -1){
	            	           query = query.replaceAll(" ", "+");
	            	        }
	            	        while(query.indexOf(",") != -1){
	            	           query = query.replaceAll(",", "%2C");
	            	        }
	            	        String searchcore = "product_mdm";
	            	        String url1 = "http://34.193.219.25:8983/solr/" + searchcore + "/select?q=" + query + "&fl=id,v_modelnumber,v_productname,v_image,v_companyname,score&wt=json&indent=true&rows=" + 500;
	            	        StringBuffer sb = new StringBuffer();
	            	        try{
	            	           URL url = new URL(url1);

	            	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            	            BufferedReader br = new BufferedReader(new InputStreamReader(
	            	                    (conn.getInputStream())));

	            	            String line;

	            	            boolean s = false;
	            	            while ((line = br.readLine()) != null) {
	            	                sb.append(line);
//	            	                System.out.println(line);
	            	             //   sb.append(System.lineSeparator());
//	            	                al.add(line);
	            	            }
	            	            br.close();
	            	            conn.disconnect();
	            	            JSONObject obj = new JSONObject(sb.toString());
	            	            JSONObject obj1 = new JSONObject(obj.get("response").toString());
	            	            System.out.println(obj1.toString());
	            	           // return obj1.toString();
	            	            JSONArray jar = new JSONArray(obj1.get("docs").toString());
	            	            if(jar.length()>0){
	            	            	String score = jar.getJSONObject(0).getString("score");
	            	            	if(Math.ceil(Float.parseFloat(score)) >= max){
	            	            		String v_modelnumber = jar.getJSONObject(0).getString("v_modelnumber");
	            	            		String v_image = jar.getJSONObject(0).getString("v_image");
	            	            		String v_productname = jar.getJSONObject(0).getString("v_productname");
	            	            		String v_companyname = jar.getJSONObject(0).getString("v_companyname");
	            	            		String prodId = jar.getJSONObject(0).getString("id");
	            	            		jsonMat = new JSONObject();
	            	            		jsonMat.put("prodpos",i);
	            	            		jsonMat.put("prodid",prodId);
	            	            		jsonMat.put("id",serialnumber);
	            	    				jsonMat.put("category",category);
	            	    				jsonMat.put("shortdesc",shortdesc);
	            	    				jsonMat.put("modelnumber",v_modelnumber);
	            	    				jsonMat.put("image",v_image);
	            	    				jsonMat.put("productname",v_productname);
	            	    				jsonMat.put("companyname",v_companyname);
	            	    				jarrayMat.put(jsonMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < max && Math.ceil(Float.parseFloat(score)) >= min){
	            	            		/*String v_modelnumber = jar.getJSONObject(0).getString("v_modelnumber");
	            	            		String v_image = jar.getJSONObject(0).getString("v_image");
	            	            		String v_productname = jar.getJSONObject(0).getString("v_productname");
	            	            		String v_companyname = jar.getJSONObject(0).getString("v_companyname");
	            	            		String prodId = jar.getJSONObject(0).getString("id");
	            	            		jsonPaMat = new JSONObject();
	            	            		jsonPaMat.put("prodpos",i);
	            	            		jsonPaMat.put("prodid",prodId);
	            	            		jsonPaMat.put("id",serialnumber);
	            	    				jsonPaMat.put("category",category);
	            	    				jsonPaMat.put("shortdesc",shortdesc);
	            	    				jsonPaMat.put("modelnumber",v_modelnumber);
	            	    				jsonPaMat.put("image",v_image);
	            	    				jsonPaMat.put("productname",v_productname);
	            	    				jsonPaMat.put("companyname",v_companyname);
	            	    				jsonPaMat.put("additionalmetadata","");
	            	    				jarrayPaMat.put(jsonPaMat);*/
	            	            		jsonPaMat = new JSONObject();
	            	            		jsonPaMat.put("prodpos",i);
	            	            		jsonPaMat.put("id",serialnumber);
	            	    				jsonPaMat.put("category",category);
	            	    				jsonPaMat.put("shortdesc",shortdesc);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    					jsonPaMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
		            	    				}else{
		            	    				 //jsonUnMat.put("additionalmetadata","");	
		            	    			}
	            	            		if(jar.length() > 5){
	            	            			for(int recordI = 0;recordI < 5;recordI++){
	            	            				String v_modelnumber = jar.getJSONObject(recordI).getString("v_modelnumber");
	            	            				String v_image = jar.getJSONObject(recordI).getString("v_image");
	    	            	            		String v_productname = jar.getJSONObject(recordI).getString("v_productname");
	    	            	            		String v_companyname = jar.getJSONObject(recordI).getString("v_companyname");
	    	            	            		String prodId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("prodid",prodId);
	    	            	            		jsonPaMatIn.put("modelnumber",v_modelnumber);
	    	            	            		jsonPaMatIn.put("image",v_image);
	    	            	            		jsonPaMatIn.put("productname",v_productname);
	    	            	            		jsonPaMatIn.put("companyname",v_companyname);
	    	            	    				//jsonPaMatIn.put("additionalmetadata","");
	    	            	    				//jsonPaMat.put("solr_result","");
	    	            	    				jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}else{
	            	            			for(int recordI = 0;recordI < jar.length();recordI++){
	            	            				String v_modelnumber = jar.getJSONObject(recordI).getString("v_modelnumber");
	            	            				String v_image = jar.getJSONObject(recordI).getString("v_image");
	    	            	            		String v_productname = jar.getJSONObject(recordI).getString("v_productname");
	    	            	            		String v_companyname = jar.getJSONObject(recordI).getString("v_companyname");
	    	            	            		String prodId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("prodid",prodId);
	    	            	            		jsonPaMatIn.put("modelnumber",v_modelnumber);
	    	            	            		jsonPaMatIn.put("image",v_image);
	    	            	            		jsonPaMatIn.put("productname",v_productname);
	    	            	            		jsonPaMatIn.put("companyname",v_companyname);
	    	            	    				//jsonPaMatIn.put("additionalmetadata","");
	    	            	    				//jsonPaMat.put("solr_result","");
	    	            	    				jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}
	            	            		jsonPaMat.put("dataarray",jarrayPaMatIn);
	            	            		jarrayPaMat.put(jsonPaMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < min){
	            	            		jsonUnMat = new JSONObject();
	            	            		jsonUnMat.put("prodpos",i);
	            	            		jsonUnMat.put("id",serialnumber);
	            	    				jsonUnMat.put("category",category);
	            	    				jsonUnMat.put("shortdesc",shortdesc);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 //jsonUnMat.put("additionalmetadata","");	
	            	    				}
	            	    				jarrayUnMat.put(jsonUnMat);
	            	            	}
	            	            }else{
	            	            	jsonUnMat = new JSONObject();
	            	            	jsonUnMat.put("prodpos",i);
	            	            	jsonUnMat.put("id",serialnumber);
            	    				jsonUnMat.put("category",category);
            	    				jsonUnMat.put("shortdesc",shortdesc);
            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 jsonUnMat.put("additionalmetadata","");	
	            	    				}
            	    				jarrayUnMat.put(jsonUnMat);
	            	            }
	            	        
	            	        }catch(Exception e){
	            	            System.out.println(e.getMessage());
	            	        }
	            	        
	            				}
	            	        
	            			}
	            			
	            		//}
	            		}
	            	}
	          	if(jarrayMat.length() > 0){
	        			mainJson.put("matchedinfo",jarrayMat);
	        			}
	            	if(jarrayUnMat.length() > 0){
	        			mainJson.put("unmatchedinfo",jarrayUnMat);
	        			}
	            	if(jarrayPaMat.length() > 0){
	        			mainJson.put("partmatchedinfo",jarrayPaMat);
	        			}
	           	
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        return mainJson.toString();
}
	    
	    

	    public String getAddress(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	ArrayList objProductArray = null;
	    	String prodUser = "";
	    	String appr  = "";
	    	String strStreetAddr = "";
	    	String strCountryName = "";
	    	JSONObject mainJson = new JSONObject();
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	 objProductArray = (ArrayList) objDBObject.get("address");
	            	 JSONObject jsonMat = null;
	            	 JSONObject jsonPaMat = null;
	            	 JSONObject jsonPaMatIn = null;
	            	 JSONObject jsonUnMat = null;
	            	 JSONArray jarrayMat = new JSONArray();
	            	 JSONArray jarrayPaMat = new JSONArray();
	            	 JSONArray jarrayPaMatIn = null;
	            	 JSONArray jarrayUnMat = new JSONArray();
	         		
	            	for(int i=0;i<objProductArray.size();i++){
	            		jarrayPaMatIn = new JSONArray();
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		 //prodUser = objMongo.get("createdby").toString();
	            		prodUser = objMongo.get("smeuser").toString();
	            		 appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(appr.equals("")){
	            		    strStreetAddr = objMongo.get("streetaddress1").toString();
	            		    strCountryName = objMongo.get("country").toString();
	            			//String query = "streetaddress:"+strStreetAddr+" OR countryname:"+strCountryName;
	            		    String query = "";
            				float max = 5.0f;
            				float min = 3.0f;
            				//Float min = 5.0;
            				if(objMongo.isNull("reject")){
            				if(objMongo.isNull("additionalmetadata")){	
            			        query = "streetaddress:"+strStreetAddr+" OR countryname:"+strCountryName;
            			        max = 4.0f;
            			        min = 2.0f;
            				}else{
            				     query = objMongo.get("additionalmetadata").toString();
            				        max = 2.0f;
	            			        min = 1.0f;
            				}
	            	        while(query.indexOf(":") != -1){
	            	           query = query.replaceAll(":", "%3A");
	            	        }
	            	        while(query.indexOf(" ") != -1){
	            	           query = query.replaceAll(" ", "+");
	            	        }
	            	        while(query.indexOf(",") != -1){
	            	           query = query.replaceAll(",", "%2C");
	            	        }
	            	        String searchcore = "address_mdm";
	            	        String url1 = "http://34.193.219.25:8983/solr/" + searchcore + "/select?q=" + query + "&fl=streetaddress,countryname,region,locality,postalcode,id,score&wt=json&indent=true&rows=" + 500;
	            	        StringBuffer sb = new StringBuffer();
	            	        try{
	            	           URL url = new URL(url1);

	            	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            	            BufferedReader br = new BufferedReader(new InputStreamReader(
	            	                    (conn.getInputStream())));

	            	            String line;

	            	            boolean s = false;
	            	            while ((line = br.readLine()) != null) {
	            	                sb.append(line);
//	            	                System.out.println(line);
	            	             //   sb.append(System.lineSeparator());
//	            	                al.add(line);
	            	            }
	            	            br.close();
	            	            conn.disconnect();
	            	            JSONObject obj = new JSONObject(sb.toString());
	            	            JSONObject obj1 = new JSONObject(obj.get("response").toString());
	            	            System.out.println(obj1.toString());
	            	           // return obj1.toString();
	            	            JSONArray jar = new JSONArray(obj1.get("docs").toString());
	            	            if(jar.length()>0){
	            	            	String score = jar.getJSONObject(0).getString("score");
	            	            	if(Math.ceil(Float.parseFloat(score)) >= max){
	            	            	//	streetaddress,countryname,region,locality,postalcode,score
	            	            		String strRegion = jar.getJSONObject(0).getString("region");
	            	            		String strLocality = jar.getJSONObject(0).getString("locality");
	            	            		String strPostalCode = jar.getJSONObject(0).getString("postalcode");
	            	            		//String v_companyname = jar.getJSONObject(0).getString("v_companyname");
	            	            		String strId = jar.getJSONObject(0).getString("id");
	            	            		jsonMat = new JSONObject();
	            	            		jsonMat.put("addrpos",i);
	            	            		jsonMat.put("addrid",strId);
	            	            		jsonMat.put("streetaddress",strStreetAddr);
	            	    				jsonMat.put("region",strRegion);
	            	    				jsonMat.put("locality",strLocality);
	            	    				jsonMat.put("country",strCountryName);
	            	    				jsonMat.put("postalcode",strPostalCode);
	            	    				jarrayMat.put(jsonMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < max && Math.ceil(Float.parseFloat(score)) >= min){
	            	            		/*String v_modelnumber = jar.getJSONObject(0).getString("v_modelnumber");
	            	            		String v_image = jar.getJSONObject(0).getString("v_image");
	            	            		String v_productname = jar.getJSONObject(0).getString("v_productname");
	            	            		String v_companyname = jar.getJSONObject(0).getString("v_companyname");
	            	            		String prodId = jar.getJSONObject(0).getString("id");
	            	            		jsonPaMat = new JSONObject();
	            	            		jsonPaMat.put("prodpos",i);
	            	            		jsonPaMat.put("prodid",prodId);
	            	            		jsonPaMat.put("id",serialnumber);
	            	    				jsonPaMat.put("category",category);
	            	    				jsonPaMat.put("shortdesc",shortdesc);
	            	    				jsonPaMat.put("modelnumber",v_modelnumber);
	            	    				jsonPaMat.put("image",v_image);
	            	    				jsonPaMat.put("productname",v_productname);
	            	    				jsonPaMat.put("companyname",v_companyname);
	            	    				jsonPaMat.put("additionalmetadata","");
	            	    				jarrayPaMat.put(jsonPaMat);*/
	            	            		jsonPaMat = new JSONObject();
	            	            		jsonPaMat.put("addrpos",i);
	            	            		jsonPaMat.put("streetaddress",strStreetAddr);
	            	    				jsonPaMat.put("country",strCountryName);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    					jsonPaMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
		            	    				}else{
		            	    				 //jsonUnMat.put("additionalmetadata","");	
		            	    			}
	            	            		if(jar.length() > 5){
	            	            			for(int recordI = 0;recordI < 5;recordI++){
	            	            				String strRegion = jar.getJSONObject(recordI).getString("region");
	    	            	            		String strLocality = jar.getJSONObject(recordI).getString("locality");
	    	            	            		String strPostalCode = jar.getJSONObject(recordI).getString("postalcode");
	    	            	            		String strId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("addrid",strId);
	    	            	            		jsonPaMatIn.put("region",strRegion);
	    	            	            		jsonPaMatIn.put("locality",strLocality);
	    	            	            		jsonPaMatIn.put("postalcode",strPostalCode);
	    	            	    				//jsonPaMatIn.put("additionalmetadata","");
	    	            	    				//jsonPaMat.put("solr_result","");
	    	            	    				jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}else{
	            	            			for(int recordI = 0;recordI < jar.length();recordI++){
	            	            				String strRegion = jar.getJSONObject(recordI).getString("region");
	    	            	            		String strLocality = jar.getJSONObject(recordI).getString("locality");
	    	            	            		String strPostalCode = jar.getJSONObject(recordI).getString("postalcode");
	    	            	            		String strId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("addrid",strId);
	    	            	            		jsonPaMatIn.put("region",strRegion);
	    	            	            		jsonPaMatIn.put("locality",strLocality);
	    	            	            		jsonPaMatIn.put("postalcode",strPostalCode);
	    	            	    				//jsonPaMatIn.put("additionalmetadata","");
	    	            	    				//jsonPaMat.put("solr_result","");
	    	            	    				jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}
	            	            		jsonPaMat.put("dataarray",jarrayPaMatIn);
	            	            		jarrayPaMat.put(jsonPaMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < min){
	            	            		jsonUnMat = new JSONObject();
	            	            		jsonUnMat.put("addrpos",i);
	            	            		jsonUnMat.put("streetaddress",strStreetAddr);
	            	            		jsonUnMat.put("country",strCountryName);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 //jsonUnMat.put("additionalmetadata","");	
	            	    				}
	            	    				jarrayUnMat.put(jsonUnMat);
	            	            	}
	            	            }else{
	            	            	jsonUnMat = new JSONObject();
	            	            	jsonUnMat.put("addrpos",i);
            	            		jsonUnMat.put("streetaddress",strStreetAddr);
            	            		jsonUnMat.put("country",strCountryName);
            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 jsonUnMat.put("additionalmetadata","");	
	            	    				}
            	    				jarrayUnMat.put(jsonUnMat);
	            	            }
	            	        }catch(Exception e){
	            	            System.out.println(e.getMessage());
	            	        }
            				} 
	            		}
	            		}
	            	}
	          	if(jarrayMat.length() > 0){
	        			mainJson.put("matchedinfo",jarrayMat);
	        			}
	            	if(jarrayUnMat.length() > 0){
	        			mainJson.put("unmatchedinfo",jarrayUnMat);
	        			}
	            	if(jarrayPaMat.length() > 0){
	        			mainJson.put("partmatchedinfo",jarrayPaMat);
	        			}
	           	
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        return mainJson.toString();
}
	    
	    public String getVendor(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	ArrayList objProductArray = null;
	    	String prodUser = "";
	    	String appr  = "";
	    	String strVendorName = "";
	    	String strCountryName = "";
	    	JSONObject mainJson = new JSONObject();
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	 objProductArray = (ArrayList) objDBObject.get("customervendor");
	            	 JSONObject jsonMat = null;
	            	 JSONObject jsonPaMat = null;
	            	 JSONObject jsonPaMatIn = null;
	            	 JSONObject jsonUnMat = null;
	            	 JSONArray jarrayMat = new JSONArray();
	            	 JSONArray jarrayPaMat = new JSONArray();
	            	 JSONArray jarrayPaMatIn = null;
	            	 JSONArray jarrayUnMat = new JSONArray();
	         		
	            	for(int i=0;i<objProductArray.size();i++){
	            		jarrayPaMatIn = new JSONArray();
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		 //prodUser = objMongo.get("createdby").toString();
	            		prodUser = objMongo.get("smeuser").toString();
	            		 appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(appr.equals("")){
	            			strVendorName = objMongo.get("vendorname").toString();
	            		    strCountryName = objMongo.get("country").toString();
	            			//String query = "streetaddress:"+strStreetAddr+" OR countryname:"+strCountryName;
	            		    String query = "";
            				float max = 5.0f;
            				float min = 3.0f;
            				//Float min = 5.0;
            				if(objMongo.isNull("reject")){
            				if(objMongo.isNull("additionalmetadata")){	
            			        query = "vendor_name:"+strVendorName+" OR country:"+strCountryName;
            			        max = 4.0f;
            			        min = 2.0f;
            				}else{
            				     query = objMongo.get("additionalmetadata").toString();
            				        max = 2.0f;
	            			        min = 1.0f;
            				}
	            	        while(query.indexOf(":") != -1){
	            	           query = query.replaceAll(":", "%3A");
	            	        }
	            	        while(query.indexOf(" ") != -1){
	            	           query = query.replaceAll(" ", "+");
	            	        }
	            	        while(query.indexOf(",") != -1){
	            	           query = query.replaceAll(",", "%2C");
	            	        }
	            	        String searchcore = "vendor_mdm1";
	            	        String url1 = "http://34.193.219.25:8983/solr/" + searchcore + "/select?q=" + query + "&fl=id,postalcode,streetaddress,website,locality,vendor_name,country,city,score&wt=json&indent=true&rows=" + 500;
	            	        StringBuffer sb = new StringBuffer();
	            	        try{
	            	           URL url = new URL(url1);

	            	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            	            BufferedReader br = new BufferedReader(new InputStreamReader(
	            	                    (conn.getInputStream())));

	            	            String line;

	            	            boolean s = false;
	            	            while ((line = br.readLine()) != null) {
	            	                sb.append(line);	            	            }
	            	            br.close();
	            	            conn.disconnect();
	            	            JSONObject obj = new JSONObject(sb.toString());
	            	            JSONObject obj1 = new JSONObject(obj.get("response").toString());
	            	            System.out.println(obj1.toString());
	            	           // return obj1.toString();
	            	            JSONArray jar = new JSONArray(obj1.get("docs").toString());
	            	            if(jar.length()>0){
	            	            	String score = jar.getJSONObject(0).getString("score");
	            	            	if(Math.ceil(Float.parseFloat(score)) >= max){
	            	            	//	streetaddress,countryname,region,locality,postalcode,score
	            	            		String strRegion = jar.getJSONObject(0).getString("city");
	            	            		String strLocality = jar.getJSONObject(0).getString("locality");
	            	            		String strPostalCode = jar.getJSONObject(0).getString("postalcode");
	            	            		String strAddress = jar.getJSONObject(0).getString("streetaddress");
	            	            		String strWebsite = jar.getJSONObject(0).getString("website");
	            	            		String strId = jar.getJSONObject(0).getString("id");
	            	            		jsonMat = new JSONObject();
	            	            		jsonMat.put("vendorpos",i);
	            	            		jsonMat.put("vendorid",strId);
	            	            		jsonMat.put("vendorname",strVendorName);
	            	            		jsonMat.put("website",strWebsite);
	            	            		jsonMat.put("streetaddress",strAddress);
	            	    				jsonMat.put("region",strRegion);
	            	    				jsonMat.put("locality",strLocality);
	            	    				jsonMat.put("country",strCountryName);
	            	    				jsonMat.put("postalcode",strPostalCode);
	            	    				jarrayMat.put(jsonMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < max && Math.ceil(Float.parseFloat(score)) >= min){
	            	            		jsonPaMat = new JSONObject();
	            	            		jsonPaMat.put("vendorpos",i);
	            	            		jsonPaMat.put("vendorname",strVendorName);
	            	            		jsonPaMat.put("country",strCountryName);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    					jsonPaMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
		            	    				}else{
		            	    				 //jsonUnMat.put("additionalmetadata","");	
		            	    			}
	            	            		if(jar.length() > 5){
	            	            			for(int recordI = 0;recordI < 5;recordI++){
	            	            				String strRegion = jar.getJSONObject(recordI).getString("city");
	    	            	            		String strLocality = jar.getJSONObject(recordI).getString("locality");
	    	            	            		String strPostalCode = jar.getJSONObject(recordI).getString("postalcode");
	    	            	            		String strAddress = jar.getJSONObject(recordI).getString("streetaddress");
	    	            	            		String strWebsite = jar.getJSONObject(recordI).getString("website");
	    	            	            		String strId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("vendorid",strId);
	    	            	            		jsonPaMatIn.put("website",strWebsite);
	    	            	            		jsonPaMatIn.put("streetaddress",strAddress);
	    	            	            		jsonPaMatIn.put("region",strRegion);
	    	            	            		jsonPaMatIn.put("locality",strLocality);
	    	            	            		jsonPaMatIn.put("postalcode",strPostalCode);
	    	            	    				jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}else{
	            	            			for(int recordI = 0;recordI < jar.length();recordI++){
	            	            				String strRegion = jar.getJSONObject(recordI).getString("city");
	    	            	            		String strLocality = jar.getJSONObject(recordI).getString("locality");
	    	            	            		String strPostalCode = jar.getJSONObject(recordI).getString("postalcode");
	    	            	            		String strAddress = jar.getJSONObject(recordI).getString("streetaddress");
	    	            	            		String strWebsite = jar.getJSONObject(recordI).getString("website");
	    	            	            		String strId = jar.getJSONObject(recordI).getString("id");
	    	            	            		jsonPaMatIn = new JSONObject();
	    	            	            		jsonPaMatIn.put("vendorid",strId);
	    	            	            		jsonPaMatIn.put("website",strWebsite);
	    	            	            		jsonPaMatIn.put("streetaddress",strAddress);
	    	            	            		jsonPaMatIn.put("region",strRegion);
	    	            	            		jsonPaMatIn.put("locality",strLocality);
	    	            	            		jsonPaMatIn.put("postalcode",strPostalCode);
	    	            	            		jarrayPaMatIn.put(jsonPaMatIn);
	            	            			}
	            	            		}
	            	            		jsonPaMat.put("dataarray",jarrayPaMatIn);
	            	            		jarrayPaMat.put(jsonPaMat);
	            	            	}else if(Math.ceil(Float.parseFloat(score)) < min){
	            	            		jsonUnMat = new JSONObject();
	            	            		jsonUnMat.put("vendorpos",i);
	            	            		jsonUnMat.put("vendorname",strVendorName);
	            	            		jsonUnMat.put("country",strCountryName);
	            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 //jsonUnMat.put("additionalmetadata","");	
	            	    				}
	            	    				jarrayUnMat.put(jsonUnMat);
	            	            	}
	            	            }else{
	            	            	jsonUnMat = new JSONObject();
	            	            	jsonUnMat.put("vendorpos",i);
            	            		jsonUnMat.put("vendorname",strVendorName);
            	            		jsonUnMat.put("country",strCountryName);
            	    				if(!objMongo.isNull("additionalmetadata")){
	            	    			     jsonUnMat.put("additionalmetadata",objMongo.get("additionalmetadata").toString());
	            	    				}else{
	            	    				 jsonUnMat.put("additionalmetadata","");	
	            	    				}
            	    				jarrayUnMat.put(jsonUnMat);
	            	            }
	            	        }catch(Exception e){
	            	            System.out.println(e.getMessage());
	            	        }
            				} 
	            		}
	            		}
	            	}
	          	if(jarrayMat.length() > 0){
	        			mainJson.put("matchedinfo",jarrayMat);
	        			}
	            	if(jarrayUnMat.length() > 0){
	        			mainJson.put("unmatchedinfo",jarrayUnMat);
	        			}
	            	if(jarrayPaMat.length() > 0){
	        			mainJson.put("partmatchedinfo",jarrayPaMat);
	        			}
	           	
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        return mainJson.toString();
}


	    
	    
	    public String getAssignByCat(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	ArrayList objProductArray = null;
	    	String prodUser = "";
	    	String serialnumber = "";
	    	String level1desc = "";
	    	String level2desc = "";
	    	String level3desc = "";
	    	String level4desc = "";
	    	String shortdesc = "";
	    	String category = "";
	    	String appr  = "";
	    	JSONObject mainJson = new JSONObject();
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	 objProductArray = (ArrayList) objDBObject.get("products");
	            	 JSONObject jsonlevel1 = null;
	            	 JSONObject jsonlevel2 = null;
	            	 JSONObject jsonlevel3 = null;
	            	 JSONObject jsonlevel4 = null;
	            	 JSONArray jarraylevel1 = new JSONArray();
	            	 JSONArray jarraylevel2 = new JSONArray();
	            	 JSONArray jarraylevel3 = new JSONArray();
	            	 JSONArray jarraylevel4 = new JSONArray();
	         		
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		 prodUser = objMongo.get("createdby").toString();
	            		 appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            			//	if(appr != ""){
	            			level1desc = objMongo.get("level1desc").toString();
	            			level2desc = objMongo.get("level2desc").toString();
	            			level3desc = objMongo.get("level3desc").toString();
	            			level4desc = objMongo.get("level4desc").toString();
	            				        if(!level1desc.equals("")){
	            						jsonlevel1 = new JSONObject();
	            				        jsonlevel1.put("level1desc",level1desc);
	            				        jarraylevel1.put(jsonlevel1);
	            				        }
	            				        if(!level2desc.equals("")){
	            				        jsonlevel2 = new JSONObject();
	            				        jsonlevel2.put("level2desc",level2desc);
	            				        jarraylevel2.put(jsonlevel2);
	            				        }
	            				        if(!level3desc.equals("")){
	            				        jsonlevel3 = new JSONObject();
	            				        jsonlevel3.put("level3desc",level3desc);
	            				        jarraylevel3.put(jsonlevel3);
	            				        }
	            				        if(!level4desc.equals("")){
	            				        jsonlevel4 = new JSONObject();
	            				        jsonlevel4.put("level4desc",level4desc);
	            	    				jarraylevel4.put(jsonlevel4);
	            				        }
	            	    //}
	            		}
	            	}
	          	if(jarraylevel1.length() > 0){
	        			mainJson.put("level1",jarraylevel1);
	        			}
	            	if(jarraylevel2.length() > 0){
	        			mainJson.put("level2",jarraylevel2);
	        			}
	            	if(jarraylevel3.length() > 0){
	        			mainJson.put("level3",jarraylevel3);
	        			}
	            	if(jarraylevel4.length() > 0){
	        			mainJson.put("level4",jarraylevel4);
	        			}
	           	
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        return mainJson.toString();
}
	    
	    public int getProductApprovedLength(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	int approvedCount = 0;
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("products") != null){
	            		
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		String prodUser = objMongo.get("createdby").toString();
	            		String appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(!appr.equals("")){
	            					approvedCount++;
	            				}
	            			}
	            	}
	            	//prodCountApprd = objProductArray.size();
	            	}
	            	
		             result = approvedCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }
	    
	    public int getAddressApprovedLength(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	int approvedCount = 0;
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("address") != null){
	            		
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("address");
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		String prodUser = objMongo.get("createdby").toString();
	            		String appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(!appr.equals("")){
	            					approvedCount++;
	            				}
	            			}
	            	}
	            	//prodCountApprd = objProductArray.size();
	            	}
	            	
		             result = approvedCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }

	    public int getVendorApprovedLength(DB db,String serviceId,String userId) {
	    	int result = 0;
	    	int approvedCount = 0;
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("customervendor") != null){
	            		
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("customervendor");
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		String prodUser = objMongo.get("createdby").toString();
	            		String appr = objMongo.get("approvedby").toString();
	            			if(prodUser.equals(userId)){
	            				if(!appr.equals("")){
	            					approvedCount++;
	            				}
	            			}
	            	}
	            	//prodCountApprd = objProductArray.size();
	            	}
	            	
		             result = approvedCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }

	    public String changeFlag(String compid,String email) {
	    	int result = 0;
	    	//int approvedCount = 0;
	    	try {
	    		 DB db = getMongoDbGeneric("invite");
	            DBCollection coll = db.getCollection("companyinvite");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("compid", compid);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
		    				if(objDBObject.get("data") != null){
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("data");
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String stremailid = objMongo.get("stremailid").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(stremailid.equals(email)){
		    	            							updateCommand.put("$set", new BasicDBObject("data"+"."+i+"."+"strflage", "1"));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            				}
		    	            	}
		    	          }
		    			}
	            return "true";	
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        
	    }
	   
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public String assignSmeMongo(DB db,String serviceId,String userId,String email,String percent,String percentType) {
	    	int result = 0;
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	String [] typelist = percentType.split(",");
		    		for(int list = 0;list<typelist.length;list++){
		    			if(typelist[list].trim().equals("product")){
		    				if(objDBObject.get("products") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    			}else if(typelist[list].trim().equals("address")){
		    				
		    				if(objDBObject.get("address") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("address");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							updateCommand.put("$set", new BasicDBObject("address"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    				
		    			}else if(typelist[list].trim().equals("customer/vendor")){
		    				//"vendormapping""customervendor"
		    				if(objDBObject.get("customervendor") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("customervendor");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							updateCommand.put("$set", new BasicDBObject("customervendor"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    				
		    			}else if(typelist[list].trim().equals("vendoritem")){
		    				if(objDBObject.get("vendormapping") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("vendormapping");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							updateCommand.put("$set", new BasicDBObject("vendormapping"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }	
		    			}
		    		}
	            	
	                 result = 1;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return "true";

	    }
	    
	    // preview percent assign
	    public String previewAssignPercent(DB db,String serviceId,String userId,String email,String percent,String percentType) {
	    	int result = 0;
	    	//int approvedCount = 0;
	    	int productAssignCount = 0;
	    	int addressAssignCount = 0;
	    	int customerAssignCount = 0;
	    	int vendorAssignCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	String [] typelist = percentType.split(",");
		    		for(int list = 0;list<typelist.length;list++){
		    			if(typelist[list].trim().equals("product")){
		    				if(objDBObject.get("products") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							//updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        //coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            							productAssignCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    			}else if(typelist[list].trim().equals("address")){
		    				
		    				if(objDBObject.get("address") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("address");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							//updateCommand.put("$set", new BasicDBObject("address"+"."+i+"."+"smeuser", email));
		    	            	                        //coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            							addressAssignCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    				
		    			}else if(typelist[list].trim().equals("customer/vendor")){
		    				//"vendormapping""customervendor"
		    				if(objDBObject.get("customervendor") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("customervendor");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							//updateCommand.put("$set", new BasicDBObject("customervendor"+"."+i+"."+"smeuser", email));
		    	            	                        //coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            							customerAssignCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    				
		    			}else if(typelist[list].trim().equals("vendoritem")){
		    				if(objDBObject.get("vendormapping") != null){
		    					int prodCount = 0;
		    	            	ArrayList objProductArray = (ArrayList) objDBObject.get("vendormapping");
		    	            	float newFloat = (float) objProductArray.size();
		    	            	float percentVal = Float.parseFloat(percent);
		    	            	int productSme = (int) (newFloat * percentVal / 100f);
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(productSme > prodCount){
		    	            							
		    	            							//updateCommand.put("$set", new BasicDBObject("vendormapping"+"."+i+"."+"smeuser", email));
		    	            	                       // coll.update(whereQuery, updateCommand, true, true );
		    	            							//db.mdmcoll.update({"serviceId":"754"}, { "$set":{"products.2.level1code":"99"} } )
		    	            							prodCount++;
		    	            							vendorAssignCount++;
		    	            						}
		    	            					}
		    	            				}
		    	            			//}
		    	            	}
		    	            	//prodCountApprd = objProductArray.size();
		    	          }	
		    			}
		    		}
	            	
	                 result = 1;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return email + "~" +productAssignCount + "~" + addressAssignCount + "~" + customerAssignCount + "~" +vendorAssignCount;

	    }

	    
	    
	    /// category assign
	    
	    public String assignCatSmeMongo(DB db,String serviceId,String userId,String email,String assignCat4,String assignCat3,String assignCat2,String assignCat1) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	           
	            if(cursor.hasNext()) {
	            	
	            	DBObject objDBObject = cursor.next();
	            	
	            	String [] typelist = null;
	            	if(!assignCat4.equals("")){
	            		typelist = assignCat4.split(",");
	            	}else if(!assignCat3.equals("")){
	            		typelist = assignCat3.split(",");
	            	}else if(!assignCat2.equals("")){
	            		typelist = assignCat2.split(",");
	            	}else if(!assignCat1.equals("")){
	            		typelist = assignCat1.split(",");
	            	}
	            	
		    		for(int list = 0;list<typelist.length;list++){
		    			//if(typelist[list].equals("product")){
		    				if(objDBObject.get("products") != null){
		    					result = "1";
		    					ArrayList objProductArray = (ArrayList) objDBObject.get("products");
		    	            	
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            		String level1desc = objMongo.get("level1desc").toString();
		    	            		String level2desc = objMongo.get("level2desc").toString();
		    	            		String level3desc = objMongo.get("level3desc").toString();
		    	            		String level4desc = objMongo.get("level4desc").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            		//result = "2";
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(typelist[list].trim().equals(level1desc)){
		    	            							updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "3";
		    	            						}else if(typelist[list].trim().equals(level2desc)){
		    	            							updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "4";
		    	            						}else if(typelist[list].trim().equals(level3desc)){
		    	            							updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "5";
		    	            						}else if(typelist[list].trim().equals(level4desc)){
		    	            							updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "6";
		    	            						} 
		    	            					}
		    	            				}
		    	            				
		    	            	}
		    	            //	result = "7";
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    			//}
		    		}
	            	
	                 //result = 1;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
 /// category preview assign
	    
	    public String previewassignCatSmeMongo(DB db,String serviceId,String userId,String email,String assignCat4,String assignCat3,String assignCat2,String assignCat1) {
	    	String result = "0";
	    	int prodCountCat = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	           
	            if(cursor.hasNext()) {
	            	
	            	DBObject objDBObject = cursor.next();
	            	
	            	String [] typelist = null;
	            	if(!assignCat4.equals("")){
	            		typelist = assignCat4.split(",");
	            	}else if(!assignCat3.equals("")){
	            		typelist = assignCat3.split(",");
	            	}else if(!assignCat2.equals("")){
	            		typelist = assignCat2.split(",");
	            	}else if(!assignCat1.equals("")){
	            		typelist = assignCat1.split(",");
	            	}
	            	
		    		for(int list = 0;list<typelist.length;list++){
		    			//if(typelist[list].equals("product")){
		    				if(objDBObject.get("products") != null){
		    					result = "1";
		    					ArrayList objProductArray = (ArrayList) objDBObject.get("products");
		    	            	
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            		String level1desc = objMongo.get("level1desc").toString();
		    	            		String level2desc = objMongo.get("level2desc").toString();
		    	            		String level3desc = objMongo.get("level3desc").toString();
		    	            		String level4desc = objMongo.get("level4desc").toString();
		    	            			//if(prodUser.equals(userId)){
		    	            		//result = "2";
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals("")){
		    	            						if(typelist[list].trim().equals(level1desc)){
		    	            						//	updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                     //   coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "3";
		    	            	                        prodCountCat++;
		    	            						}else if(typelist[list].trim().equals(level2desc)){
		    	            						//	updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                     //   coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "4";
		    	            	                        prodCountCat++;
		    	            						}else if(typelist[list].trim().equals(level3desc)){
		    	            						//	updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                     //   coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "5";
		    	            	                        prodCountCat++;
		    	            						}else if(typelist[list].trim().equals(level4desc)){
		    	            						//	updateCommand.put("$set", new BasicDBObject("products"+"."+i+"."+"smeuser", email));
		    	            	                     //   coll.update(whereQuery, updateCommand, true, true );
		    	            	                        result = "6";
		    	            	                        prodCountCat++;
		    	            						} 
		    	            					}
		    	            				}
		    	            				
		    	            	}
		    	            //	result = "7";
		    	            	//prodCountApprd = objProductArray.size();
		    	          }
		    			//}
		    		}
	            	
	                 //result = 1;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return email + "~" + prodCountCat;

	    }


	    
/// sme unassign
	    
	    public String unAssignSmeMongo(DB db,String serviceId,String customerId,String email,String strTtype) {
	    	int result = 0;
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	           
	            if(cursor.hasNext()) {
	            	
	            	DBObject objDBObject = cursor.next();
	            	
	            	if(objDBObject.get(strTtype) != null){
		    					
		    					ArrayList objProductArray = (ArrayList) objDBObject.get(strTtype);
		    	            	
		    	            	for(int i=0;i<objProductArray.size();i++){
		    	            		String record = objProductArray.get(i).toString();
		    	            		JSONObject objMongo = new JSONObject(record);
		    	            		String prodUser = objMongo.get("createdby").toString();
		    	            		String appr = objMongo.get("approvedby").toString();
		    	            		String smeuser = objMongo.get("smeuser").toString();
		    	            				if(appr.equals("")){
		    	            					if(smeuser.equals(email)){
		    	            							updateCommand.put("$set", new BasicDBObject(strTtype+"."+i+"."+"smeuser", ""));
		    	            	                        coll.update(whereQuery, updateCommand, true, true );
		    	            					}
		    	            				}
		    	            	}
	            	}
	            	
	                 result = 1;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return "true";

	    }


	    
	    public String getAssignedLength(DB db,String serviceId) {
	    	String result = "0";
	    	int assignedCount = 0;
	    	int notassignedCount = 0;
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("products") != null){
	            		
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		String prodUser = objMongo.get("createdby").toString();
	            		String appr = objMongo.get("approvedby").toString();
	            		String smeuser = objMongo.get("smeuser").toString();
	            			//if(prodUser.equals(userId)){
	            				if(!smeuser.equals("")){
	            					assignedCount++;
	            				}else{
	            					notassignedCount++;
	            				}
	            			//}
	            	}
	            	//prodCountApprd = objProductArray.size();
	            	}
	            	if(objDBObject.get("address") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("address");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            			//if(prodUser.equals(userId)){
		            				if(!smeuser.equals("")){
		            					assignedCount++;
		            				}else{
		            					notassignedCount++;
		            				}
		            			//}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	if(objDBObject.get("customervendor") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("customervendor");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            			//if(prodUser.equals(userId)){
		            				if(!smeuser.equals("")){
		            					assignedCount++;
		            				}else{
		            					notassignedCount++;
		            				}
		            			//}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	if(objDBObject.get("vendormapping") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("vendormapping");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            			//if(prodUser.equals(userId)){
		            				if(!smeuser.equals("")){
		            					assignedCount++;
		            				}else{
		            					notassignedCount++;
		            				}
		            			//}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	
		             result = assignedCount + "~" + notassignedCount;
	            }else{
	            	result = assignedCount + "~" + notassignedCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }
	    
	    public String getAssignedSummary(DB db,String serviceId) {
	    	ResourceBundle bundle = null;
	    	Statement statement = null;
			JSONArray jarray = new JSONArray();
			JSONObject mainJson = new JSONObject();
			JSONObject objJson = null;
			String query = "";
			int productAppr = 0;
			int productAssign = 0;
			int addressAppr = 0;
			int addressAssign = 0;
			int customerAppr = 0;
			int customerAssign = 0;
			int vendorItemAppr = 0;
			int vendorItemAssign = 0;
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	
	            		bundle = ResourceBundle.getBundle("serverConfig");
	        			Class.forName("com.mysql.jdbc.Driver");
	        			Connection con = (Connection) DriverManager.getConnection(
	        					bundle.getString("customerserviceUrl"),
	        					bundle.getString("customerserviceuser"),
	        					bundle.getString("customerservicepswd"));
	        			query = "select * from mdmperson where serviceId = "+serviceId+" and flag =0 " ;
	        			statement = con.createStatement();
	        			ResultSet rs = statement.executeQuery(query);
	        			DBObject objDBObject = cursor.next();
	            	while(rs.next()){
	            		 productAppr = 0;
	        			 productAssign = 0;
	        			 addressAppr = 0;
	        			 addressAssign = 0;
	        			 customerAppr = 0;
	        			 customerAssign = 0;
	        			 vendorItemAppr = 0;
	        			 vendorItemAssign = 0;
	            		objJson = new JSONObject();	
	            	if(objDBObject.get("products") != null){
	            		
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		String prodUser = objMongo.get("createdby").toString();
	            		String appr = objMongo.get("approvedby").toString();
	            		String smeuser = objMongo.get("smeuser").toString();
	            			if(appr.equals(rs.getString("emailId"))){
	            				productAppr++;
	            			 }else if(smeuser.equals(rs.getString("emailId"))){
	            					productAssign++;
	            				}

	            	}
	            	//prodCountApprd = objProductArray.size();
	            	}
	            	if(objDBObject.get("address") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("address");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            		if(appr.equals(rs.getString("emailId"))){
	            				addressAppr++;
	            			 }else if(smeuser.equals(rs.getString("emailId"))){
	            				 addressAssign++;
	            				}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	if(objDBObject.get("customervendor") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("customervendor");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            		if(appr.equals(rs.getString("emailId"))){
	            				customerAppr++;
	            			 }else if(smeuser.equals(rs.getString("emailId"))){
	            				 customerAssign++;
	            				}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	if(objDBObject.get("vendormapping") != null){
	            		
		            	ArrayList objProductArray = (ArrayList) objDBObject.get("vendormapping");
		            	for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("createdby").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            		String smeuser = objMongo.get("smeuser").toString();
		            		if(appr.equals(rs.getString("emailId"))){
	            				vendorItemAppr++;
	            			 }else if(smeuser.equals(rs.getString("emailId"))){
	            				 vendorItemAssign++;
	            				}
		            	}
		            	//prodCountApprd = objProductArray.size();
		            	}
	            	objJson.put("userId",rs.getString("emailId"));
	            	objJson.put("productappr",productAppr);
	            	objJson.put("productassign",productAssign);
	            	objJson.put("addressappr",addressAppr);
	            	objJson.put("addressassign",addressAssign);
	            	objJson.put("customerappr",customerAppr);
	            	objJson.put("customerassign",customerAssign);
	            	objJson.put("vendoritemappr",vendorItemAppr);
	            	objJson.put("vendoritemassign",vendorItemAssign);
	            	jarray.put(objJson);
	            	
	            	}
	            	mainJson.put("users",jarray);
	            	
		             //result = assignedCount + "~" + notassignedCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false   ::"+e.getMessage();
	        }
	        return mainJson.toString();

	    }
	    
	    public String getSMEUserProcessLength(DB db,String serviceId,String userId) {
	    	String result = "";
	    	int vendorItemCount = 0;
	    	int prodCount = 0;
	    	int cvCount = 0;
	    	int addrCount = 0;
	        try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	if(objDBObject.get("products") != null){
	            	ArrayList objProductArray = (ArrayList) objDBObject.get("products");
	            	 //prodCount = objProductArray.size();
	            	 for(int i=0;i<objProductArray.size();i++){
		            		String record = objProductArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("smeuser").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            			if(prodUser.equals(userId)){
		            				//if(!appr.equals("")){
		            					prodCount++;
		            				//}
		            			}
		            	}
	            	}
	            	if(objDBObject.get("address") != null){
	            	ArrayList objAddrArray = (ArrayList) objDBObject.get("address");
	            	// addrCount = objAddrArray.size();
	            	 for(int i=0;i<objAddrArray.size();i++){
		            		String record = objAddrArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("smeuser").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            			if(prodUser.equals(userId)){
		            				//if(!appr.equals("")){
		            					addrCount++;
		            				//}
		            			}
		            	}
	            	}
	            	if(objDBObject.get("customervendor") != null){
	            	ArrayList objCVArray = (ArrayList) objDBObject.get("customervendor");
	            	// cvCount = objCVArray.size();
	            	 for(int i=0;i<objCVArray.size();i++){
		            		String record = objCVArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("smeuser").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            			if(prodUser.equals(userId)){
		            				//if(!appr.equals("")){
		            					cvCount++;
		            				//}
		            			}
		            	}
	            	}
	            	if(objDBObject.get("vendormapping") != null){
	            	ArrayList objVIArray = (ArrayList) objDBObject.get("vendormapping");
	            	 //vendorItemCount = objVIArray.size();
	            	 for(int i=0;i<objVIArray.size();i++){
		            		String record = objVIArray.get(i).toString();
		            		JSONObject objMongo = new JSONObject(record);
		            		String prodUser = objMongo.get("smeuser").toString();
		            		String appr = objMongo.get("approvedby").toString();
		            			if(prodUser.equals(userId)){
		            				//if(!appr.equals("")){
		            					vendorItemCount++;
		            				//}
		            			}
		            	}
	            	}
	            	
	            	
	            
		             //result = prodCount + addrCount + cvCount + vendorItemCount;
		             result = prodCount + "," + addrCount +"," + cvCount +","+ vendorItemCount;
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return result;

	    }
	    
	    /// approve product
	    
	    public String apprProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String prodimage,
	    		String prodid,String prodname,String prodmodelnumb,String manufacturer,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            Date date = new Date();
	            String strDate = dateFormat.format(date);
	           // prodObj = new BasicDBObject();
	           // prodObj.put()
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"approvedby", userId));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"approveddate", strDate));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"productimage", prodimage));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"productname", prodname));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"manufacturer", manufacturer));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"producturl", prodid));
                coll.update(whereQuery, updateCommand, true, true );
	            //DBCursor cursor = coll.find(whereQuery);
	            result = "true";
		    	
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
	    /// approve address
	    
	    public String apprAddress(DB db,String pos,String streetaddress,String country,
	    		String region,String locality,String postalcode,
	    		String addrid,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            Date date = new Date();
	            String strDate = dateFormat.format(date);
	           // prodObj = new BasicDBObject();
	           // prodObj.put()
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"approvedby", userId));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"approveddate", strDate));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"region", region));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"locality", locality));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"postalcode", postalcode));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"addressid", addrid));
                coll.update(whereQuery, updateCommand, true, true );
	            //DBCursor cursor = coll.find(whereQuery);
	            result = "true";
		    	
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
	    
	    /// rerun product
	    
	    public String reRunProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String addnMetdata,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"additionalmetadata", addnMetdata));
	            coll.update(whereQuery, updateCommand, true, true );
	            
	          /*  String query = addnMetdata;
    	        while(query.indexOf(":") != -1){
    	           query = query.replaceAll(":", "%3A");
    	        }
    	        while(query.indexOf(" ") != -1){
    	           query = query.replaceAll(" ", "+");
    	        }
    	        while(query.indexOf(",") != -1){
    	           query = query.replaceAll(",", "%2C");
    	        }
    	        String searchcore = "product_mdm";
    	        String url1 = "http://34.193.219.25:8983/solr/" + searchcore + "/select?q=" + query + "&fl=id,v_modelnumber,v_productname,v_image,v_companyname,score&wt=json&indent=true&rows=" + 500;
    	        StringBuffer sb = new StringBuffer();
    	      //  try{
    	           URL url = new URL(url1);

    	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    	            BufferedReader br = new BufferedReader(new InputStreamReader(
    	                    (conn.getInputStream())));

    	            String line;

    	            boolean s = false;
    	            while ((line = br.readLine()) != null) {
    	                sb.append(line);
//    	                System.out.println(line);
    	             //   sb.append(System.lineSeparator());
//    	                al.add(line);
    	            }
    	            br.close();
    	            conn.disconnect();
    	            JSONObject obj = new JSONObject(sb.toString());
    	            JSONObject obj1 = new JSONObject(obj.get("response").toString());
    	            System.out.println(obj1.toString());
    	           // return obj1.toString();
    	            JSONArray jar = new JSONArray(obj1.get("docs").toString());
    	            if(jar.length()>0){
    	            	String score = jar.getJSONObject(0).getString("score");
    	            	if(Math.ceil(Float.parseFloat(score)) >= 2.0){
    	            		String v_modelnumber = jar.getJSONObject(0).getString("v_modelnumber");
    	            		String v_image = jar.getJSONObject(0).getString("v_image");
    	            		String v_productname = jar.getJSONObject(0).getString("v_productname");
    	            		String v_companyname = jar.getJSONObject(0).getString("v_companyname");
    	            		String prodId = jar.getJSONObject(0).getString("id");
    	            		updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_modelnumber", v_modelnumber));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_image", v_image));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_productname", v_productname));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_companyname", v_companyname));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_prodid", prodId));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"solr_result", "matched"));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	            	}else if(Math.ceil(Float.parseFloat(score)) < 2.0 && Math.ceil(Float.parseFloat(score)) >= 1.0){
    	            		String v_modelnumber = jar.getJSONObject(0).getString("v_modelnumber");
    	            		String v_image = jar.getJSONObject(0).getString("v_image");
    	            		String v_productname = jar.getJSONObject(0).getString("v_productname");
    	            		String v_companyname = jar.getJSONObject(0).getString("v_companyname");
    	            		String prodId = jar.getJSONObject(0).getString("id");
    	            		updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_modelnumber", v_modelnumber));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_image", v_image));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_productname", v_productname));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_companyname", v_companyname));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_prodid", prodId));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	    	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"solr_result", "partmatched"));
    	    	            coll.update(whereQuery, updateCommand, true, true );
    	            	}else if(Math.ceil(Float.parseFloat(score)) < 1.0){
    	            	
    	            	}
    	            }else{
    	            	
    	            }*/
    	            result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }

	    
/// rerun address
	    
	    public String reRunAddress(DB db,String pos,String streetaddress,String country,String addnMetdata,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"additionalmetadata", addnMetdata));
	            coll.update(whereQuery, updateCommand, true, true );
	            

    	        result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }

	    
	    /// reject product
	    
	    public String rejectProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String prodimage,
	    		String prodid,String prodname,String prodmodelnumb,String manufacturer,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"reject", "1"));
	            coll.update(whereQuery, updateCommand, true, true );
	            
	            /*updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_modelnumber", prodmodelnumb));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_image", prodimage));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_productname", prodname));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_companyname", manufacturer));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"temp_prodid", prodid));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("products"+"."+iPos+"."+"solr_result", "partmatched"));
	            coll.update(whereQuery, updateCommand, true, true );*/
	            
	            result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
	    
	    /// reject address
	    
	    public String rejectAddress(DB db,String pos,String streetaddress,String country,
	    		String region,String locality,String postalcode,
	    		String addrid,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("address"+"."+iPos+"."+"reject", "1"));
	            coll.update(whereQuery, updateCommand, true, true );
	            
	            result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
	    
	    /// reject vendor
	    
	    public String rejectVendor(DB db,String pos,String vendorname,String website,String streetaddress,String country,
	    		String region,String locality,String postalcode,
	    		String addrid,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"reject", "1"));
	            coll.update(whereQuery, updateCommand, true, true );
	            
	            result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
/// rerun vendor
	    
	    public String reRunVendor(DB db,String pos,String vendorname,String country,String addnMetdata,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	           
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"additionalmetadata", addnMetdata));
	            coll.update(whereQuery, updateCommand, true, true );
	            

    	        result = "true";
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }
	    
/// approve vendor
	    
	    public String apprVendor(DB db,String pos,String vendorname,String website,String streetaddress,String country,
	    		String region,String locality,String postalcode,
	    		String addrid,String serviceId,String userId) {
	    	String result = "0";
	    	//int approvedCount = 0;
	    	try {
	    		
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            BasicDBObject updateCommand = new BasicDBObject();
	            BasicDBObject prodObj = null;
	            whereQuery.put("serviceId", serviceId);
	            int iPos = Integer.parseInt(pos);
	            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            Date date = new Date();
	            String strDate = dateFormat.format(date);
	           // prodObj = new BasicDBObject();
	           // prodObj.put()
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"approvedby", userId));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"approveddate", strDate));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"solrstaddress", streetaddress));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"region", region));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"locality", locality));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"postalcode", postalcode));
	            coll.update(whereQuery, updateCommand, true, true );
	            updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"addressid", addrid));
                coll.update(whereQuery, updateCommand, true, true );
                updateCommand.put("$set", new BasicDBObject("customervendor"+"."+iPos+"."+"website", website));
                coll.update(whereQuery, updateCommand, true, true );
	            //DBCursor cursor = coll.find(whereQuery);
	            result = "true";
		    	
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return "false"+e.getMessage();
	        }
	        return result;

	    }

	    public String getSearchProductView(DB db,String serviceId,String searchText) {
	    	int result = 0;
	    	ArrayList objProductArray = null;
	    	String level1desc = "";
	    	String level2desc = "";
	    	String level3desc = "";
	    	String level4desc = "";
	    	String strProductName = "";
	    	String strProductUrl = "";
	    	String strManufacturer = "";
	    	String appr  = "";
	    	JSONObject mainJson = new JSONObject();
	    	try {
	        	//DB db = this.getMongoDb();
	            DBCollection coll = db.getCollection("mdmcoll");
	            BasicDBObject whereQuery = new BasicDBObject();
	            whereQuery.put("serviceId", serviceId);
	            DBCursor cursor = coll.find(whereQuery);
	            if(cursor.hasNext()) {
	            	DBObject objDBObject = cursor.next();
	            	
	            	 objProductArray = (ArrayList) objDBObject.get("products");
	            	 JSONObject jsonMat = null;
	            	 JSONArray jarrayMat = new JSONArray();
	         		
	            	for(int i=0;i<objProductArray.size();i++){
	            		String record = objProductArray.get(i).toString();
	            		JSONObject objMongo = new JSONObject(record);
	            		 appr = objMongo.get("approvedby").toString();
	            		 if(!objMongo.isNull("productname")){	
	            		 if(!appr.equals("")){
	            			if(objMongo.isNull("reject")){
	            				
	            				strProductName = objMongo.get("productname").toString();
	            				if(strProductName.indexOf("::;;") != -1){
	            				strProductUrl = objMongo.get("producturl").toString();
		            			strManufacturer = objMongo.get("manufacturer").toString();
		            			String [] product = strProductName.split("::;;");
	            				String [] producturl = strProductUrl.split("::;;");
	            				String [] manufacArray = strManufacturer.split("::;;");
	            				for(int ipro=0;ipro<product.length;ipro++){
	            					//strProductName = product[ipro];	
	            				if(product[ipro].toLowerCase().contains(searchText.toLowerCase())){
	            					level1desc = objMongo.get("level1desc").toString();
	    	            			level2desc = objMongo.get("level2desc").toString();
	    	            			level3desc = objMongo.get("level3desc").toString();
	    	            			level4desc = objMongo.get("level4desc").toString();
	    	            			jsonMat = new JSONObject();
									jsonMat.put("level1",level1desc);
	            	            	jsonMat.put("level2",level2desc);
	            	            	jsonMat.put("level3",level3desc);
	            	    			jsonMat.put("level4",level4desc);
	            	    			jsonMat.put("itemName",product[ipro]);
	            	    			jsonMat.put("itemUrl",producturl[ipro]);
	            	    			jsonMat.put("companyName",manufacArray[ipro]);
	            	    			jarrayMat.put(jsonMat);
	            				}
	            				}
	            			   }else{
	            				   if(strProductName.toLowerCase().contains(searchText.toLowerCase())){
		            					level1desc = objMongo.get("level1desc").toString();
		    	            			level2desc = objMongo.get("level2desc").toString();
		    	            			level3desc = objMongo.get("level3desc").toString();
		    	            			level4desc = objMongo.get("level4desc").toString();
		    	            			strProductUrl = objMongo.get("producturl").toString();
			            				strManufacturer = objMongo.get("manufacturer").toString();
			            				jsonMat = new JSONObject();
										jsonMat.put("level1",level1desc);
		            	            	jsonMat.put("level2",level2desc);
		            	            	jsonMat.put("level3",level3desc);
		            	    			jsonMat.put("level4",level4desc);
		            	    			jsonMat.put("itemName",strProductName);
		            	    			jsonMat.put("itemUrl",strProductUrl);
		            	    			jsonMat.put("companyName",strManufacturer);
		            	    			jarrayMat.put(jsonMat);
		            				}   
	            			   }
	            		    }
	                     }
	            	   }
	            	}
	          	if(jarrayMat.length() > 0){
	        			mainJson.put("data",jarrayMat);
	        	}
	            	
	            }
	           
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        return mainJson.toString();
}

	    public String  getInvitedPCcontact(String compid, String user){
	        
	        String pcemail="";
	           String compname="";
	           String contactpersonname="";
	           String email="";
	           String website="";
	           String contact="";
	           String cdate="";
	           String type="";
	           String flag="";
	           String tabtype="";
	           
	               String[] checkparamKey = {"user"};
	                  String[] checkparamValue = {user};
	                           
	            String res = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/service/event.personalcontact", checkparamKey, checkparamValue);
	            JSONObject send = new JSONObject();
	         JSONArray sendarray = new JSONArray();
	            JSONObject sendsubjson =null;
	            JSONObject pc = null;
	            JSONArray pcarray=new JSONArray();
	            JSONObject alldatajson = new JSONObject();
	            JSONObject alldatasubjson =new JSONObject();
	            JSONArray alldataarray=new JSONArray();
	            try {
	             pc= new JSONObject(res);
	             pcarray = pc.getJSONArray("data");
	             alldatajson = getInvitedCompany(compid);
	             
	            
	             //ifalldatajson!=null
	             alldataarray = alldatajson.getJSONArray("data");
	            
	       for(int j=0;j<pcarray.length(); j++){
	    	  
	       for(int i=0; i<alldataarray.length() ; i++){
	       
	        alldatasubjson=alldataarray.getJSONObject(i);       
	            if(!alldatasubjson.isNull("tabtype")){
	             tabtype=alldatasubjson.getString("tabtype");
	            
	             if(tabtype.equals("personalcontact")){
	              email=alldatasubjson.getString("email");
	              if(pcarray.getJSONObject(j).getString("email").equals(email)){
	               sendsubjson = new JSONObject();
	               compname=alldatasubjson.getString("compname");
	                     contactpersonname=alldatasubjson.getString("contperson");
	                     email=alldatasubjson.getString("email");
	                     website=alldatasubjson.getString("website");
	                     contact=alldatasubjson.getString("contactnumber");
	                     cdate=alldatasubjson.getString("createddate");
	                        type=alldatasubjson.getString("type");
	                        flag=alldatasubjson.getString("flag");
	                     tabtype=alldatasubjson.getString("tabtype");
	                     sendsubjson.put("compname", compname);
	                     sendsubjson.put("contactpersonname", contactpersonname);
	                     sendsubjson.put("email", email);
	                     sendsubjson.put("website", website);
	                     sendsubjson.put("contact", contact);
	                     sendsubjson.put("cdate", cdate);
	                     sendsubjson.put("type", type);
	                     sendsubjson.put("flag", flag);
	                     sendsubjson.put("tabtype", tabtype);
	                     sendarray.put(sendsubjson); 
	                     break;
	             }else{
	              if(i == alldataarray.length()-1){
	              sendsubjson = new JSONObject();
	              sendsubjson.put("email", pcarray.getJSONObject(j).getString("email"));
	              sendsubjson.put("compname", "");
	                       sendsubjson.put("contactpersonname", "");
	                       sendsubjson.put("website", "");
	                       sendsubjson.put("contact", "");
	                       sendsubjson.put("cdate", "");
	                       sendsubjson.put("type", "");
	                       sendsubjson.put("flag", "");
	                       
	                        sendsubjson.put("tabtype", "personalcontact");
	                     sendarray.put(sendsubjson); 
	              }
	              }
	             }else{
		              if(i == alldataarray.length()-1){
			              sendsubjson = new JSONObject();
			              sendsubjson.put("email", pcarray.getJSONObject(j).getString("email"));
			              sendsubjson.put("compname", "");
			                       sendsubjson.put("contactpersonname", "");
			                       sendsubjson.put("website", "");
			                       sendsubjson.put("contact", "");
			                       sendsubjson.put("cdate", "");
			                       sendsubjson.put("type", "");
			                       sendsubjson.put("flag", "");
			                        sendsubjson.put("tabtype", "personalcontact");
			                     sendarray.put(sendsubjson); 
			              }
			              }
	            }
	            }
	            
	            
	    	   
	       
	       
	       }
	             send.put("data",sendarray); 
	            // return send;
	      } catch (Exception e) {
	       return e.getMessage();
	       
	      }
	            return send.toString();
	          //  return pc.toString() + "middleeeeeeeeeeeee" + alldatajson.toString();
	       }

}
