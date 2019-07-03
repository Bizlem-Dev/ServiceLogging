package com.userPanel.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
//import javax.jcr.Session;
//import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.apache.felix.scr.annotations.Reference;
//import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.jindal.auction.domain.xsd.CustomerServiceStatus;
import com.userPanel.log.HTTPRequestPoster;
import com.userPanel.log.TestService;
import com.userPanel.service.JamesAccountCreationService;
import com.userPanel.service.UserPanelService;
import com.userPanel.service.impl.DefaultActiveMQProducer;
import com.userPanel.service.impl.JamesAccountCreationServiceImpl;
import com.userPanel.service.impl.UserPanelServiceImpl;
import com.userPanel.service.impl.ReadXlsImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class ServiceDetailServlet extends HttpServlet {
	
	static final String DB_URL_1 = "jdbc:mysql://localhost:3306/rave2";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";
	
	//private SlingRepository repos;
    UserPanelService userPanelService = new UserPanelServiceImpl();
    HTTPRequestPoster poster=new HTTPRequestPoster();
    JamesAccountCreationService service = new JamesAccountCreationServiceImpl();
    Auctions_WSDLPortTypeProxy ap = null;
    private static final long serialVersionUID = 1L;
    ReadXlsImpl objReadXls = new ReadXlsImpl();
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("type").equals("callService")) {
            TestService ts = new TestService();
            List<CustomerServiceStatus> list = ts.getCustomerServiceStatus(
                    request.getRemoteUser(), "", "", "", "");
            request.setAttribute("serviceList", list);
            request.getRequestDispatcher("userPanelView/serviceDetails.jsp")
                    .forward(request, response);
        }else if (request.getParameter("type").equals("getviewdata")) {
        	DB objDB = objReadXls.getMongoDb();
        	String strRes = objReadXls.getSearchProductView(objDB,request.getParameter("serviceId"),request.getParameter("result"));
        	//strRes = "{\"data\":[{\"level1\":\"Row 1\",\"level2\":\"Row 2\",\"level3\":\"Row 3\",\"itemName\":\"Data 1\"},{\"level1\":\"Row 1\",\"level2\":\"Row 2\",\"level3\":\"Row 3\",\"itemName\":\"Data 2\"},{\"level1\":\"Row 1\",\"level2\":\"Row 2\",\"level3\":\"Row 3\",\"itemName\":\"Data 3\"}]}";
        	response.getOutputStream().print(strRes);
        	//response.getOutputStream().print(strRes);
        }else if (request.getParameter("type").equals("mdmservice")) {
           // TestService ts = new TestService();
           // List<CustomerServiceStatus> list = ts.getCustomerServiceStatus(
                //    request.getRemoteUser(), "", "", "", "");
           // request.setAttribute("serviceList", list);
        //	response.getOutputStream().print(a);
        	String jsonUserRes = userPanelService.assignedMDMUserList(request.getRemoteUser(),request.getParameter("serviceId"));
        	String jsonCompRes = userPanelService.assignedMDMCompany(request.getRemoteUser(),request.getParameter("serviceId"));
        	// response.getOutputStream().print(jsonRes+request.getRemoteUser()+request.getParameter("serviceId"));
        	request.setAttribute("userList", jsonUserRes);
        	request.setAttribute("companyData", jsonCompRes);
        	 DB objDB = objReadXls.getMongoDb();
        	//String jsonProdRes = objReadXls.getProduct(objDB,request.getParameter("serviceId"),request.getRemoteUser());
        	//request.setAttribute("productData", jsonProdRes);
        	String jsonCatRes = objReadXls.getAssignByCat(objDB,request.getParameter("serviceId"),request.getRemoteUser());
        	request.setAttribute("categoryData", jsonCatRes);
        	request.getRequestDispatcher("userPanelView/mdmManagement.jsp").forward(request, response);
        }else if (request.getParameter("type").equals("userbasedserviceid")) {
         	String jsonUserRes = userPanelService.userGetService(request.getParameter("userId"));
         	response.getOutputStream().print(jsonUserRes);
         }else if (request.getParameter("type").equals("approvematchproduct")) {
        	 String pos = request.getParameter("pos");
        	 String category = request.getParameter("category");
        	 String prodcode = request.getParameter("prodcode");
        	String prodshortdesc = request.getParameter("prodshortdesc");
        	String prodimage= request.getParameter("prodimage");
        	 String prodid = request.getParameter("prodid");
        	String prodname = request.getParameter("prodname");
        	String prodmodelnumb = request.getParameter("prodmodelnumb");
        	String manufacturer = request.getParameter("manufacturer");
        	 String serviceId = request.getParameter("serviceId");
        	 String userId = request.getParameter("userId");
        	 DB objDB = objReadXls.getMongoDb();
          	String strRes = objReadXls.apprProduct(objDB,pos,category,prodcode,prodshortdesc,prodimage,prodid,prodname,prodmodelnumb,manufacturer,serviceId,userId);
          	response.getOutputStream().print(strRes);
          }else if (request.getParameter("type").equals("rejectmatchproduct")) {
         	 String pos = request.getParameter("pos");
         	 String category = request.getParameter("category");
         	 String prodcode = request.getParameter("prodcode");
         	String prodshortdesc = request.getParameter("prodshortdesc");
         	String prodimage= request.getParameter("prodimage");
         	 String prodid = request.getParameter("prodid");
         	String prodname = request.getParameter("prodname");
         	String prodmodelnumb = request.getParameter("prodmodelnumb");
         	String manufacturer = request.getParameter("manufacturer");
         	 String serviceId = request.getParameter("serviceId");
         	 String userId = request.getParameter("userId");
         	 DB objDB = objReadXls.getMongoDb();
           	String strRes = objReadXls.rejectProduct(objDB,pos,category,prodcode,prodshortdesc,prodimage,prodid,prodname,prodmodelnumb,manufacturer,serviceId,userId);
           	response.getOutputStream().print(strRes);
           }else if (request.getParameter("type").equals("rerunproduct")) {
         	 String pos = request.getParameter("pos");
         	 String category = request.getParameter("category");
         	 String prodcode = request.getParameter("prodcode");
         	String prodshortdesc = request.getParameter("prodshortdesc");
         	String addnmetdata= request.getParameter("addnmetdata");
         	 String serviceId = request.getParameter("serviceId");
         	 String userId = request.getParameter("userId");
         	 DB objDB = objReadXls.getMongoDb();
           	String strRes = objReadXls.reRunProduct(objDB,pos,category,prodcode,prodshortdesc,addnmetdata,serviceId,userId);
           	response.getOutputStream().print(strRes);
           }else if (request.getParameter("type").equals("approvematchaddress")) {
          	 String pos = request.getParameter("pos");
          	 String streetaddress = request.getParameter("streetaddress");
          	 String country = request.getParameter("country");
          	String region = request.getParameter("region");
          	String locality= request.getParameter("locality");
          	 String postalcode = request.getParameter("postalcode");
          	String addrId = request.getParameter("addrId");
          	 String serviceId = request.getParameter("serviceId");
          	 String userId = request.getParameter("userId");
          	 DB objDB = objReadXls.getMongoDb();
            	String strRes = objReadXls.apprAddress(objDB,pos,streetaddress,country,region,locality,postalcode,addrId,serviceId,userId);
            	response.getOutputStream().print(strRes);
            }else if (request.getParameter("type").equals("rejectmatchaddress")) {
            	String pos = request.getParameter("pos");
             	 String streetaddress = request.getParameter("streetaddress");
             	 String country = request.getParameter("country");
             	String region = request.getParameter("region");
             	String locality= request.getParameter("locality");
             	 String postalcode = request.getParameter("postalcode");
             	String addrId = request.getParameter("addrId");
             	 String serviceId = request.getParameter("serviceId");
             	 String userId = request.getParameter("userId");
             	 DB objDB = objReadXls.getMongoDb();
               	String strRes = objReadXls.rejectAddress(objDB,pos,streetaddress,country,region,locality,postalcode,addrId,serviceId,userId);
               	response.getOutputStream().print(strRes);
               }else if (request.getParameter("type").equals("rerunaddress")) {
             	 String pos = request.getParameter("pos");
             	String streetaddress = request.getParameter("streetaddress");
            	 String country = request.getParameter("country");
            	 String addnmetdata= request.getParameter("addnmetdata");
             	 String serviceId = request.getParameter("serviceId");
             	 String userId = request.getParameter("userId");
             	 DB objDB = objReadXls.getMongoDb();
               	String strRes = objReadXls.reRunAddress(objDB,pos,streetaddress,country,addnmetdata,serviceId,userId);
               	response.getOutputStream().print(strRes);
               }else if (request.getParameter("type").equals("approvematchvendor")) {
                	 String pos = request.getParameter("pos");
                  	 String streetaddress = request.getParameter("streetaddress");
                  	 String vendorname = request.getParameter("vendorname");
                  	 String website = request.getParameter("website");
                  	 String country = request.getParameter("country");
                  	 String region = request.getParameter("region");
                  	 String locality= request.getParameter("locality");
                  	 String postalcode = request.getParameter("postalcode");
                  	 String vendorId = request.getParameter("vendorId");
                  	 String serviceId = request.getParameter("serviceId");
                  	 String userId = request.getParameter("userId");
                  	 DB objDB = objReadXls.getMongoDb();
                    	String strRes = objReadXls.apprVendor(objDB,pos,vendorname,website,streetaddress,country,region,locality,postalcode,vendorId,serviceId,userId);
                    	response.getOutputStream().print(strRes);
                    }else if (request.getParameter("type").equals("rejectmatchvendor")) {
                    	String pos = request.getParameter("pos");
                     	 String streetaddress = request.getParameter("streetaddress");
                     	 String vendorname = request.getParameter("vendorname");
                     	 String website = request.getParameter("website");
                     	 String country = request.getParameter("country");
                     	 String region = request.getParameter("region");
                     	 String locality= request.getParameter("locality");
                     	 String postalcode = request.getParameter("postalcode");
                     	 String vendorId = request.getParameter("vendorId");
                     	 String serviceId = request.getParameter("serviceId");
                     	 String userId = request.getParameter("userId");
                     	 DB objDB = objReadXls.getMongoDb();
                       	String strRes = objReadXls.rejectVendor(objDB,pos,vendorname,website,streetaddress,country,region,locality,postalcode,vendorId,serviceId,userId);
                       	response.getOutputStream().print(strRes);
                       }else if (request.getParameter("type").equals("rerunvendor")) {
                     	 String pos = request.getParameter("pos");
                     	String vendorname = request.getParameter("vendorname");
                    	 String country = request.getParameter("country");
                    	 String addnmetdata= request.getParameter("addnmetdata");
                     	 String serviceId = request.getParameter("serviceId");
                     	 String userId = request.getParameter("userId");
                     	 DB objDB = objReadXls.getMongoDb();
                       	String strRes = objReadXls.reRunVendor(objDB,pos,vendorname,country,addnmetdata,serviceId,userId);
                       	response.getOutputStream().print(strRes);
                       }else if (request.getParameter("type").equals("mdmserviceproductview")) {
            DB objDB = objReadXls.getMongoDb();
         	String jsonProdRes = objReadXls.getProduct(objDB,request.getParameter("serviceId"),request.getRemoteUser());
         	request.setAttribute("productData", jsonProdRes);
         	String jsonAddrRes = objReadXls.getAddress(objDB,request.getParameter("serviceId"),request.getRemoteUser());
         	request.setAttribute("addressData", jsonAddrRes);
         	String jsonVendorRes = objReadXls.getVendor(objDB,request.getParameter("serviceId"),request.getRemoteUser());
         	request.setAttribute("vendorData", jsonVendorRes);
         	request.getRequestDispatcher("userPanelView/mdmProcessView.jsp").forward(request, response);
         }else if (request.getParameter("type").equals("mdmservicesavecompany")) {
        	if (request.getParameter("comp_name") != ""
                    && request.getParameter("comp_name") != null) {
            	String a=userPanelService.compSling(request.getParameter("comp_name"),
                        request.getParameter("comp_website"),request.getParameter("comp_email"),
                        request.getParameter("comp_numb"),request.getRemoteUser());
            	String b=userPanelService.compMdmIntoDatabase(request.getParameter("comp_name"),
                        request.getParameter("comp_website"),request.getParameter("comp_email"),
                        request.getParameter("comp_numb"),a,"MDM_Service",request.getRemoteUser(),request.getParameter("serviceId"));
                response.getOutputStream().print(a+"~"+b);
            }
        	}else if (request.getParameter("type").equals("mdmservicesaveperson")) {
            	if (request.getParameter("person") != ""
                        && request.getParameter("person") != null) {
            		String a=userPanelService.mdmServiceUser(request.getParameter("person"),
                            request.getParameter("name"),request.getParameter("number"));
                	String b=userPanelService.userMdmIntoDatabase(request.getParameter("person"),
                            request.getParameter("name"),request.getParameter("number"),
                            request.getParameter("admin"),"MDM_Service",request.getRemoteUser(),request.getParameter("serviceId"));
                    response.getOutputStream().print(a+"~"+b);
                }
            	}else if (request.getParameter("type").equals("mdmserviceupdateperson")) {
                	if (request.getParameter("userid") != ""
                            && request.getParameter("userid") != null) {
                		String b=userPanelService.userMdmUpdateDatabase(request.getParameter("userid"),request.getParameter("admin"),request.getRemoteUser(),request.getParameter("serviceId"));
                        response.getOutputStream().print(b);
                    }
                	} else if (request.getParameter("type").equals("callAuto")) {
            String personList = "";
            try {
                personList = userPanelService.personList();
                request.setAttribute("list", personList);
                List<String> assignedUserList = userPanelService
                        .assignedUserList(request.getRemoteUser(),
                                request.getParameter("productCode"),request.getParameter("serviceId"));
                request.setAttribute("assignedUserList", assignedUserList);
                request.getRequestDispatcher(
                        "userPanelView/assignedServices.jsp").forward(request,
                        response);
            } catch (JSONException e) {

               
            }
            }else if (request.getParameter("type").equals("CallMe Back")) {
                String personList = "";
                try {
                    personList = userPanelService.personList();
                    request.setAttribute("list", personList);
                    List<String> assignedUserList = userPanelService
                            .assignedUserList(request.getRemoteUser(),
                                    request.getParameter("productCode"),request.getParameter("serviceId"));
                    request.setAttribute("assignedUserList", assignedUserList);
                    
                    String[] checkparamKey = {"userId","serviceId"};
                    String[] checkparamValue = {request.getRemoteUser(),request.getParameter("serviceId")};
                    									
            		String res = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/company/show.comp", checkparamKey, checkparamValue);
             		request.setAttribute("companylist", res);

                    request.getRequestDispatcher(
                            "userPanelView/callMeBack.jsp").forward(request,
                            response);
                } catch (JSONException e) {

                   
                }
            }else if (request.getParameter("type").equals("livechat")) {
            	
            	String personList = "";
            	try{
            		
				personList = userPanelService.personList();
                 request.setAttribute("list", personList);
                 List<String> assignedUserList = userPanelService
                         .assignedUserList(request.getRemoteUser(),
                                 request.getParameter("productCode"),request.getParameter("serviceId"));
                 request.setAttribute("assignedUserList", assignedUserList);
             
            	// request.getRequestDispatcher(
                 //        "userPanel.servlet/AssignedLivechatServiceServlet.live").forward(request,
                 //        response);
                 String[] checkparamKey = {"userId","serviceId"};
                 String[] checkparamValue = {request.getRemoteUser(),request.getParameter("serviceId")};
                 									
         		String res = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/company/show.comp", checkparamKey, checkparamValue);
         	
         		request.setAttribute("companylist", res);
         		request.getRequestDispatcher(
                         "userPanelView/assignedLivechatService.jsp").forward(request,
                         response);
            	}catch(Exception e){ e.printStackTrace();}
        }else if (request.getParameter("type").equals("personalcontact")) {
        	String user =request.getParameter("user");
            String compid =request.getParameter("compid");
            
            //JSONObject send = new JSONObject();
            String send=objReadXls.getInvitedPCcontact(compid,user);
             response.getWriter().print(send.toString());
       
        }else if (request.getParameter("type").equals("assignsmepercenttype")) {
        	if (request.getParameter("userPercent") != ""
                    && request.getParameter("userPercent") != null) {
        	//	String b=userPanelService.userUpdateMdmPerson(request.getParameter("userId"),
              //          request.getParameter("userEmail"),request.getParameter("userPercent"),
                //        request.getParameter("userPercentType"),request.getRemoteUser(),request.getParameter("serviceId"));
        		//if(b.equals("true")){
        			DB objDB = objReadXls.getMongoDb();
                	String Res = objReadXls.assignSmeMongo(objDB,request.getParameter("serviceId"),request.getRemoteUser(),
                            request.getParameter("userEmail"),request.getParameter("userPercent"),
                            request.getParameter("userPercentType"));	
                response.getOutputStream().print(Res);
        		//}
            }
        }else if (request.getParameter("type").equals("assignsmepercenttypepreview")) {
        	if (request.getParameter("userPercent") != ""
                    && request.getParameter("userPercent") != null) {
        	//	String b=userPanelService.userUpdateMdmPerson(request.getParameter("userId"),
              //          request.getParameter("userEmail"),request.getParameter("userPercent"),
                //        request.getParameter("userPercentType"),request.getRemoteUser(),request.getParameter("serviceId"));
        		//if(b.equals("true")){
        			DB objDB = objReadXls.getMongoDb();
                	String Res = objReadXls.previewAssignPercent(objDB,request.getParameter("serviceId"),request.getRemoteUser(),
                            request.getParameter("userEmail"),request.getParameter("userPercent"),
                            request.getParameter("userPercentType"));	
                response.getOutputStream().print(Res);
        		//}
            }else{
            	response.getOutputStream().print("false");
            }
        }
        else if (request.getParameter("type").equals("assignsmecategory")) {
        	if ((!request.getParameter("assignCat4").equals("")
                    && request.getParameter("assignCat4") != null) || (!request.getParameter("assignCat3").equals("")
                            && request.getParameter("assignCat3") != null) || (!request.getParameter("assignCat2").equals("")
                                    && request.getParameter("assignCat2") != null) || (!request.getParameter("assignCat1").equals("")
                                            && request.getParameter("assignCat1") != null)) {
        			DB objDB = objReadXls.getMongoDb();
                	String Res = objReadXls.assignCatSmeMongo(objDB,request.getParameter("serviceId"),request.getRemoteUser(),
                            request.getParameter("userEmail"),request.getParameter("assignCat1"),
                            request.getParameter("assignCat2"),request.getParameter("assignCat3"),request.getParameter("assignCat4"));	
                response.getOutputStream().print(Res);
        		
            }else{
            	response.getOutputStream().print("else");
            }
        }else if (request.getParameter("type").equals("assignsmecategorypreview")) {
        	if ((!request.getParameter("assignCat4").equals("")
                    && request.getParameter("assignCat4") != null) || (!request.getParameter("assignCat3").equals("")
                            && request.getParameter("assignCat3") != null) || (!request.getParameter("assignCat2").equals("")
                                    && request.getParameter("assignCat2") != null) || (!request.getParameter("assignCat1").equals("")
                                            && request.getParameter("assignCat1") != null)) {
        			DB objDB = objReadXls.getMongoDb();
                	String Res = objReadXls.previewassignCatSmeMongo(objDB,request.getParameter("serviceId"),request.getRemoteUser(),
                            request.getParameter("userEmail"),request.getParameter("assignCat1"),
                            request.getParameter("assignCat2"),request.getParameter("assignCat3"),request.getParameter("assignCat4"));	
                response.getOutputStream().print(Res);
        		
            }else{
            	response.getOutputStream().print("false");
            }
        }else if (request.getParameter("type").equals("unassignsmeuser")) {
        	if (request.getParameter("smeuser") != ""
                    && request.getParameter("smeuser") != null) {
        		String user = request.getParameter("smeuser");
        		String type = request.getParameter("smetype");
        			DB objDB = objReadXls.getMongoDb();
                	String Res = objReadXls.unAssignSmeMongo(objDB,request.getParameter("serviceId"),request.getRemoteUser(),
                            user,type);	
                response.getOutputStream().print(Res);
        		
            }
        }
        else if (request.getParameter("type").equals("createAccount")) {

            List<String> assignedUserList = userPanelService.assignedUserList(
                    request.getRemoteUser(),
                    request.getParameter("productCode"),request.getParameter("serviceId"));
            request.setAttribute("assignedUserList", assignedUserList);

            request.getRequestDispatcher("userPanelView/createJamesAccount.jsp")
                    .forward(request, response);
        } else if (request.getParameter("type").equals("assignSmsEmail")) {
            String personList = "";
            try {
                personList = userPanelService.personList();
                request.setAttribute("list", personList);
                List<String> assignedUserList = userPanelService
                        .assignedUserList(request.getRemoteUser(),
                                request.getParameter("productCode"),request.getParameter("serviceId"));
                request.setAttribute("assignedUserList", assignedUserList);

                request.getRequestDispatcher(
                        "userPanelView/assignSms&Email.jsp").forward(request,
                        response);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (request.getParameter("type").equals("saveAccount")) {

            String[] userName = request.getParameter("person").split(",");
            String[] password = request.getParameter("password").split(",");
            String productCode = request.getParameter("productCode");
            String serviceId = request.getParameter("serviceId");
            String flag = "accountQuery";
            double assignedQuanity = (double) userName.length;
            String result = userPanelService.setConsumption(
                    request.getRemoteUser(), productCode, assignedQuanity,serviceId);
            try {
                JSONObject json = new JSONObject(result);
                if (json.getBoolean("accessFlag")) {
                    for (int i = 0; i < userName.length; i++) {

                        Boolean user_status = service.addUser(userName[i],
                                password[i]);
                        if (user_status == true) {
                            userPanelService.SaveUser(request.getRemoteUser(),
                                    userName[i], productCode, flag,serviceId);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (request.getParameter("type").equals("verify")) {

            boolean exist = service
                    .verifyUser(request.getParameter("userName"));
            // response.getOutputStream().print(exist);
            if (exist == true) {
                response.getOutputStream().print(
                        "User Name already exist Please change");
            } else {
                response.getOutputStream().print(true);
            }
        }

        else if (request.getParameter("type").equals("save")) {

            if (request.getParameter("person") != ""
                    && request.getParameter("person") != null) {
            	String a=userPanelService.splitUser(request.getParameter("person"),
                        request.getParameter("productCode"),
                        request.getRemoteUser(),request.getParameter("serviceId"));
                response.getOutputStream().print(a);
            }
        } else if (request.getParameter("type").equals("saveCallMB")) {
            InputStream inputXml = null;
            
            try{
                   if (request.getParameter("person") != ""
                           && request.getParameter("person") != null) {
                    
                    String companyname=request.getParameter("companyname");
                    String companyId=request.getParameter("companyId");
                    String serviceId=request.getParameter("serviceId");
                    
                    
                    String a=userPanelService.splitUser(request.getParameter("person"),
                               request.getParameter("productCode"),
                               request.getRemoteUser(),request.getParameter("serviceId"));
                 
                    // code to get extension of user
                    
                    String[] user = {"users"};
                    String[] uservalue = {request.getParameter("person")};
                             
                    String userext = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/company/show.getExtension", user, uservalue);
                    
                    // code to create queue for company in php
                    
                    String[] queuekey = {"name","ext"};
                    String[] queuevalue = {companyname,userext};
                             
                    String resQueue = userPanelService.callGetService("http://34.198.153.157/webservice/queueCreation.php", queuekey, queuevalue);
              
                    
                   //call code to add in sling which is written in another sling servlet check how we call in livechat in this servlet    
                     
                       
                       String[] checkparamKey = {"serviceId","productCode","companyId","ext_queue"};
                       String[] checkparamValue = {serviceId, request.getParameter("productCode"),companyId,resQueue};
                                
                 String res = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/company/show.addcallMBToSling", checkparamKey, checkparamValue);
                 //request.setAttribute("companylist", res);
                 //request.getRequestDispatcher(
                         //      "userPanelView/assignedLivechatService.jsp").forward(request,
                           //    response);
                   response.getOutputStream().print(a);
                   }
                   
            }catch(Exception e){}   

           }else if (request.getParameter("type").equals("savelivechat")) {
        	InputStream inputXml = null;
        	
        	try{
                if (request.getParameter("person") != ""
                        && request.getParameter("person") != null) {
                	
                	String companyname=request.getParameter("companyname");
                	String companyId=request.getParameter("companyId");
                	String serviceId=request.getParameter("serviceId");
                	
                	 String url="http://35.236.154.164:8180/UserValidation/services/UserValidation/addWorkgroup?jid="+companyname+"&description="+"";
                	 inputXml = new URL(url).openConnection().getInputStream();
        			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        			Document doc = dBuilder.parse(inputXml);
        			doc.getDocumentElement().normalize();	
        			//NodeList nList = doc.getElementsByTagName("ns:return");
        		String wgID =doc.getElementsByTagName("ns:return").item(0).getTextContent();
        		String workgroupDisplayName= companyname+"@workgroup.socialmail.in";
        		int workgroupID = Integer.parseInt(wgID);
                	
        		String url1="http://35.236.154.164:8180/UserValidation/services/UserValidation/addQueue?workgroupID="+workgroupID;
           	 inputXml = new URL(url1).openConnection().getInputStream();
   			DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
   			DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
   			Document doc1 = dBuilder1.parse(inputXml);
   			doc1.getDocumentElement().normalize();	
   			//NodeList nList = doc.getElementsByTagName("ns:return");
   		       String  qID=doc1.getElementsByTagName("ns:return").item(0).getTextContent();
   		       int queueID = Integer.parseInt(qID);
               
   		       String a=userPanelService.splitUserLiveService(request.getParameter("person"),
                            request.getParameter("productCode"),
                            request.getRemoteUser(),request.getParameter("serviceId"),queueID);
   		    

              
                //call code to add in sling which is written in another sling servlet check how we call in livechat in this servlet    
                  
                    
                    String[] checkparamKey = {"serviceId","workgroupID","workgroupDisplayName","productCode","companyId"};
                    String[] checkparamValue = {serviceId,wgID,workgroupDisplayName, request.getParameter("productCode"),companyId};
                    									
            		String res = userPanelService.callGetService("http://35.236.154.164:8082/portal/servlet/company/show.addSling", checkparamKey, checkparamValue);
            		//request.setAttribute("companylist", res);
            		//request.getRequestDispatcher(
                      //      "userPanelView/assignedLivechatService.jsp").forward(request,
                        //    response);
            		  response.getOutputStream().print(a);
                }
                
        	}catch(Exception e){} 
            
        
        	
        	
        	
        	
        	
        	
            
        } else if (request.getParameter("type").equals("setConsumption")) {
            double quanity = (double) Integer.parseInt(request
                    .getParameter("quantity"));
            response.getOutputStream().print(
                    userPanelService.setConsumption(
                            request.getParameter("userId"),
                            request.getParameter("productCode"), quanity,request.getParameter("serviceId")));

        } else if (request.getParameter("type").equals("getConsumption")) {
            response.getOutputStream().print(
                    userPanelService.getLimitService(
                            request.getParameter("userId"),
                            request.getParameter("productCode")));
        }

        else if (request.getParameter("type").equals("userDetails")) {

            request.getRequestDispatcher("userPanelView/userDetails.jsp")
                    .forward(request, response);
        }

        else if (request.getParameter("type").equals("userId")) {
            String Id = "";
            String productCode = request.getParameter("productCode");
            String userName = request.getParameter("userName");

            Id = userPanelService.getUserDetails(userName, productCode);

            if (Id != "" && Id != null) {
                response.getOutputStream().print(Id);
            } else {
                response.getOutputStream().print("No Record Exist");
            }
        }else if (request.getParameter("type").equals("invitecompany")) {
        	
        	
        	request.getRequestDispatcher(
                    "userPanelView/invitecompany.jsp").forward(request,
                    response);
        }else if (request.getParameter("type").equals("changeFlag")) {
        	String compid=request.getParameter("compid");
        	String email=request.getParameter("email");
        	
        	String status=objReadXls.changeFlag( compid, email);
        	PrintWriter out=response.getWriter();
        	if(status.equals("true")){
        	out.print("Your Acceptance to the request is Recorded Successfully...");
        	}else if(status.equals("false")){      
        		out.print("some error occurred please try again later...");

        	}else if(status.equals("false")){
        		out.print("some error occurred please try again later...");

        	}
        	//request.getRequestDispatcher( "userPanelView/invitecompany.jsp").forward(request,response);
        }
        else if(request.getParameter("type").equals("Doctiger")){
        	        	String personList = "";
        	try{
        		
			personList = userPanelService.personList();
             request.setAttribute("list", personList);
             List<String> assignedUserList = userPanelService
                     .assignedUserList(request.getRemoteUser(),
                             request.getParameter("productCode"),request.getParameter("serviceId"));
             request.setAttribute("assignedUserList", assignedUserList);
         
        	// request.getRequestDispatcher(
             //        "userPanel.servlet/AssignedLivechatServiceServlet.live").forward(request,
             //        response);
             	request.getRequestDispatcher(
                    "doctigerui/index.jsp").forward(request,
                    response);

        	}catch(Exception e){ e.printStackTrace();}
    
        }
        
    }
 
    

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out=response.getWriter();
    if(request.getParameter("type").equals("form")){
    	String compid=request.getParameter("compid");
    	String compname=request.getParameter("compname");
    	String userid=request.getParameter("userid");
    	String cname=request.getParameter("cname");
    	String conperson=request.getParameter("conperson");
    	String website=request.getParameter("website");
    	String email=request.getParameter("email");
    	String contact=request.getParameter("contact");
    	String type=request.getParameter("type");
    	
    	String status="";
    	String htmlText="";
    	String password="";
    	String hiturl="";
    	 DB objDB = objReadXls.getMongoDbGeneric("invite");
         if (objDB!=null){
        	password=objReadXls.checkcontact(compid,email);
        	
        	status =  objReadXls.insertCompanyByform("form",compid,objDB,userid,compname,cname,conperson,email,website,contact,type);
        
        out.print(status);
        if(status.equals("true")){
        	
       	htmlText  ="<html><body><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border-radius:0px '0px  10px 10px; border:1px solid #4096EE; background-color:#ffffff;'><tr><td align='center' valign='top'><table width='100%' border='0' align='center' cellpadding='5' cellspacing='5' style='border-top:6px solid #4096EE;'><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px; padding-right:10px;'><div style='font-size:24px; color:#4096EE;'>Dear "+email+ ", </div><p>You have recieved a Invition from "+userid+" to join as"+type+" with "+compname+"</p><br/><p>Your Account login cradential at www.bizlem.com is:</p></br><p>User id: "+email+"</p></br></p>Password:"+password+"<p>If you are agree to join click the below link:</p><br/><p> <a href='http://35.236.154.164:8078/ServiceLogging/userPanel?type=changeFlag&compid="+compid+"&email="+email+"'</a><Strong>Agree</strong></p><tr><td align='left' valign='iddle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px;'></tr><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#333; font-size:13px;'><span style='color:#333; font-size:12px; font-family:Arial, Helvetica, sans-serif;'>With Regards,<br /><strong>Administrator</strong></span></tr></table></td></tr></table></body></html>";
 
       	String subject = "Invition to join www.bizlem.com";
		String[] checkparamKey = { "email", "msg", "subject" };
		String[] checkparamValue = { email, htmlText, subject };
		String res = poster.callService(
				"http://35.236.154.164:8082/portal/servlet/service/productselection.sendMailRfqSeller",
				checkparamKey, checkparamValue);	
       	out.print(res);
        }else{
        	out.print("Due to some Technical Reason Can't Access your Request Please Try After Some Time...");

        }
       
       
         }else {
        	out.print("Due to some Technical Reason Can't Access your Request Please Try After Some Time...");
        }
    	
      response.sendRedirect("http://35.236.154.164:8078/ServiceLogging/userPanel?type=invitecompany&compname="+compname+"&compid="+compid);
  
    }else if(request.getParameter("type").equals("Addcontactmongo")){
    	
    	String compid=request.getParameter("compid");
    	out.print(compid);
    	String userid=request.getParameter("userid");
    	out.print(userid);
    	String compname=request.getParameter("compname");
    	out.println(compname);
    	String[] chk=request.getParameterValues("select");
    	String[] cname=request.getParameterValues("comp_name");
    	String[] conperson=request.getParameterValues("contperson_nameId");
    	String[] website=request.getParameterValues("comp_website");
    	String[] email =request.getParameterValues("comp_email");
    	String[] contact=request.getParameterValues("comp_numb");
    	//String[] type=request.getParameterValues("comp_type");
    	String type=request.getParameter("comp_type");
    	
//    	int j = 0;
//    	for(int i=0;i<chk.length;i++){
//    		out.println(chk[i]);
//    		out.println(cname[Integer.parseInt(chk[i])]);
//    		out.println(conperson[Integer.parseInt(chk[i])]);
//    		out.println(website[Integer.parseInt(chk[i])]);
//    		out.println(email[Integer.parseInt(chk[i])]);
//    		out.println(contact[Integer.parseInt(chk[i])]);
//    		out.println(type[Integer.parseInt(chk[i])]);
//    		out.println(creationdate[Integer.parseInt(chk[i])]);
//    	}
    	String status="";
    	String htmlText="";
    	String password="";
    	
    	
    	
    	
    	try{
    	for(int i=0; i<chk.length;i++){
    		password="";
    		htmlText="";
    		 DB objDB = objReadXls.getMongoDbGeneric("invite");
             if (objDB!=null){
            	password=objReadXls.checkcontact(compid,email[Integer.parseInt(chk[i])]);
            	
            	status =  objReadXls.insertCompanyByform("personalcontact",compid,objDB,userid,compname,cname[Integer.parseInt(chk[i])],conperson[Integer.parseInt(chk[i])],email[Integer.parseInt(chk[i])],website[Integer.parseInt(chk[i])],contact[Integer.parseInt(chk[i])],type);
            
            out.print(status);
            if(status.equals("true")){
            	
           	htmlText  ="<html><body><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border-radius:0px '0px  10px 10px; border:1px solid #4096EE; background-color:#ffffff;'><tr><td align='center' valign='top'><table width='100%' border='0' align='center' cellpadding='5' cellspacing='5' style='border-top:6px solid #4096EE;'><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px; padding-right:10px;'><div style='font-size:24px; color:#4096EE;'>Dear "+email[Integer.parseInt(chk[i])]+ ", </div><p>You have recieved a Invition from "+userid+" to join as"+type+" with "+compname+"</p><br/><p>Your Account login cradential at www.bizlem.com is:</p></br><p>User id: "+email[Integer.parseInt(chk[i])]+"</p></br></p>Password:"+password+"<p>If you are agree to join click the below link:</p><br/><p> <a href='http://35.236.154.164:8078/ServiceLogging/userPanel?type=changeFlag&compid="+compid+"&email="+email[Integer.parseInt(chk[i])]+"'</a><Strong>Agree</strong></p><tr><td align='left' valign='iddle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px;'></tr><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#333; font-size:13px;'><span style='color:#333; font-size:12px; font-family:Arial, Helvetica, sans-serif;'>With Regards,<br /><strong>Administrator</strong></span></tr></table></td></tr></table></body></html>";
     
           	String subject = "Invition to join www.bizlem.com";
    		String[] checkparamKey = { "email", "msg", "subject" };
    		String[] checkparamValue = { email[Integer.parseInt(chk[i])], htmlText, subject };
    		String res = poster.callService(
    				"http://35.236.154.164:8082/portal/servlet/service/productselection.sendMailRfqSeller",
    				checkparamKey, checkparamValue);	
           	out.print(res);
            }
           
             }
    	}
    		}catch(Exception e){}
        response.sendRedirect("http://35.236.154.164:8078/ServiceLogging/userPanel?type=invitecompany&compname="+compname+"&compid="+compid+"&maintab=1");

    }else if(request.getParameter("type").equals("sendreminder")){
    	String compid=request.getParameter("compid");
    	out.print(compid);
    	String userid=request.getParameter("userid");
    	out.print(userid);
    	String compname=request.getParameter("compname");
    	out.println(compname);
    	String[] chk=request.getParameterValues("select");
    	String[] cname=request.getParameterValues("comp_name");
    	String[] conperson=request.getParameterValues("contperson_nameId");
    	String[] website=request.getParameterValues("comp_website");
    	String[] email =request.getParameterValues("comp_email");
    	String[] creationdate =request.getParameterValues("comp_date");
    	String[] contact=request.getParameterValues("comp_numb");
    	String[] type=request.getParameterValues("comp_type");
    	String[] flag=request.getParameterValues("comp_flag");
    	
    	String status="";
    	String htmlText="";
    	String password="";
    	
    	for(int i=0;i<chk.length;i++){
    		out.println(chk[i]);
    		out.println(cname[Integer.parseInt(chk[i])]);
    		out.println(conperson[Integer.parseInt(chk[i])]);
    		out.println(website[Integer.parseInt(chk[i])]);
    		out.println(email[Integer.parseInt(chk[i])]);
    		out.println(contact[Integer.parseInt(chk[i])]);
    		out.println(type[Integer.parseInt(chk[i])]);
    		out.println(creationdate[Integer.parseInt(chk[i])]);
    		out.println(flag[Integer.parseInt(chk[i])]);
    	}
    	
    	try{
        	for(int i=0; i<chk.length;i++){
        		password="";
        		htmlText="";
        		 DB objDB = objReadXls.getMongoDbGeneric("invite");
                 if (objDB!=null){
                	password=objReadXls.checkcontact(compid,email[Integer.parseInt(chk[i])]);
                	out.print(password);
               	htmlText  ="<html><body><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border-radius:0px '0px  10px 10px; border:1px solid #4096EE; background-color:#ffffff;'><tr><td align='center' valign='top'><table width='100%' border='0' align='center' cellpadding='5' cellspacing='5' style='border-top:6px solid #4096EE;'><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px; padding-right:10px;'><div style='font-size:24px; color:#4096EE;'>Dear "+email[Integer.parseInt(chk[i])]+ ", </div><p>You have recieved a Invition from "+userid+" to join as"+type[Integer.parseInt(chk[i])]+" with "+compname+"</p><br/><p>Your Account login cradential at www.bizlem.com is:</p></br><p>User id: "+email[Integer.parseInt(chk[i])]+"</p></br></p>Password:"+password+"<p>If you are agree to join click the below link:</p><br/><p> <a href='http://35.236.154.164:8078/ServiceLogging/userPanel?type=changeFlag&compid="+compid+"&email="+email[Integer.parseInt(chk[i])]+"'</a><Strong>Agree</strong></p><tr><td align='left' valign='iddle' style='font-family:Arial, Helvetica, sans-serif; color:#4e4e4e; font-size:13px;'></tr><tr><td align='left' valign='middle' style='font-family:Arial, Helvetica, sans-serif; color:#333; font-size:13px;'><span style='color:#333; font-size:12px; font-family:Arial, Helvetica, sans-serif;'>With Regards,<br /><strong>Administrator</strong></span></tr></table></td></tr></table></body></html>";
         
               	String subject = "Reminder to join www.bizlem.com";
        		String[] checkparamKey = { "email", "msg", "subject" };
        		String[] checkparamValue = { email[Integer.parseInt(chk[i])], htmlText, subject };
        		String res = poster.callService(
        				"http://35.236.154.164:8082/portal/servlet/service/productselection.sendMailRfqSeller",
        				checkparamKey, checkparamValue);	
               	out.print(res);
          
                 }
        	}               
        	response.sendRedirect("http://35.236.154.164:8078/ServiceLogging/userPanel?type=invitecompany&compname="+compname+"&compid="+compid+"&maintab=3");

        		}catch(Exception e){}
    	
    }

}}
