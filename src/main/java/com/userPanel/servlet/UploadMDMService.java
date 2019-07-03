package com.userPanel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.userPanel.service.impl.ReadXlsImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class UploadMDMService extends HttpServlet {

    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
       
    }
ReadXlsImpl objReadXls = new ReadXlsImpl();
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        boolean isFileUploaded = false;
        String serviceId = "";
        String userId = "";
        String quantity = "";
        String productCode = "";
        try {
            if (isMultipart) {

            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();
//            Float f = Float.parseFloat(matchScore);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> multiparts = upload.parseRequest(request);
            //out.print(multiparts.size());
//            for(int i=0;i<multiparts.size();i++){
//                out.println(multiparts.get(i).getSize());
//                if(multiparts.get(i).toString().indexOf("xls") != -1){
//                out.println("if");
////                product p = new product();
////                p.insertDataIntoNewClassTable(multiparts.get(i).getInputStream());
//                }else{
//                    out.println("outer else");
//                }
//            }
            
            //out.print("call");
            DB objDB = objReadXls.getMongoDb();
             for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = item.getName();
                        String[] file = name.split("\\.");
                        System.out.println(file[0] + "==" +file[1]);
                        if(file[1].equals("xls")){
                            
                        if(file[0].equalsIgnoreCase("product")){
                           // ReadXls objProduct = new ReadXls();
                        	if(serviceId != ""){
                            objReadXls.insertProduct(serviceId,objDB,userId,quantity,productCode,item.getInputStream());
                        	}
                        }else if(file[0].equalsIgnoreCase("address")){
                          // Address objAddress = new Address();
                        	if(serviceId != ""){
                           objReadXls.insertAddress(serviceId,objDB,userId,quantity,productCode,item.getInputStream());
                        	}
                        }else if(file[0].equalsIgnoreCase("customervendor")){
                        	if(serviceId != ""){
                        	objReadXls.insertCustomerVendor(serviceId,objDB,userId,quantity,productCode,item.getInputStream());
                        	}
                        }else if(file[0].equalsIgnoreCase("vendoritem")){
                        	if(serviceId != ""){
                        	objReadXls.insertVendorItem(serviceId,objDB,userId,quantity,productCode,item.getInputStream());
                        	}
                        }
                        
                        }
                    } else {
                    	if (item.getFieldName().equals("serviceId1")) {
                            //out.print(item.getString());
                    		serviceId = item.getString();
                        }
                    	if (item.getFieldName().equals("userId1")) {
                            //out.print(item.getString());
                    		userId = item.getString();
                        }
                    	if (item.getFieldName().equals("quantity1")) {
                            //out.print(item.getString());
                    		quantity = item.getString();
                        }
                    	if (item.getFieldName().equals("productCode1")) {
                            //out.print(item.getString());
                    		productCode = item.getString();
                        }
                    }
                }
             
            }
            response.sendRedirect("http://35.236.154.164:8078/ServiceLogging//userPanel?type=mdmservice"+"&val="+quantity+"&productCode="+productCode+"&serviceId="+serviceId+"&tab=2");
        }catch(Exception e){
            e.getMessage();
        }finally {
            out.close();
        }
        
    }

}
