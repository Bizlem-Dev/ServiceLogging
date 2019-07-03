package com.userPanel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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


public class Invitecompany extends HttpServlet {

    
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
        String compid = "";
        String compname = "";
        String userid = request.getRemoteUser();
        List al = new ArrayList();
        String result="no result";
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
            DB objDB = objReadXls.getMongoDbGeneric("invite");
            if(objDB==null){
            	out.print("Due to some Technical Reason Can't Access your Request Please Try After Some Time...");
               response.sendRedirect("http://35.236.154.164:8082/portal/servlet/company/show.invite?compname="+compname+"&compid="+compid);
	
            }else{
             for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = item.getName();
                        String[] file = name.split("\\.");
                        System.out.println(file[0] + "==" +file[1]);
                        if(file[1].equals("xls")){
                            
                        if(file[0].equalsIgnoreCase("invitecompany")){
                           // ReadXls objProduct = new ReadXls();
                        	if(compid != ""){
                        		result=  objReadXls.insertCompany(compid,objDB,userid,compname,item.getInputStream());
                        	}
                        }
                        
                        }
                    } else {
                    	if (item.getFieldName().equals("compid")) {
                            //out.print(item.getString());
                    		compid = item.getString();
                        }
                    	if (item.getFieldName().equals("userid")) {
                            //out.print(item.getString());
                    		userid = item.getString();
                        }
                    	if (item.getFieldName().equals("compname")) {
                            //out.print(item.getString());
                    		compname = item.getString();
                        }
                    	
                    }
                }
            }
            }
            //out.println(result);
            
            
            HttpSession session=request.getSession();  
            session.setAttribute("result",result); 
            
            
           // request.getRequestDispatcher("userPanel?type=invitecompany&compname="+compname+"&compid="+compid).forward(request,
             //       response);
         response.sendRedirect("http://35.236.154.164:8078/ServiceLogging/userPanel?type=invitecompany&compname="+compname+"&compid="+compid+"&maintab=2&childtab=1");
        }catch(Exception e){
            out.print(e.getMessage());
        }finally {
            out.close();
        }
        
    }

}
