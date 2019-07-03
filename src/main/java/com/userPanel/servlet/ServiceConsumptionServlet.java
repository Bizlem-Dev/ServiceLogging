package com.userPanel.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.userPanel.schedular.ServiceActivator;
import com.userPanel.schedular.ServiceDeActivator;
import com.userPanel.service.UserPanelService;
import com.userPanel.service.impl.UserPanelActivator;
import com.userPanel.service.impl.UserPanelActivatorImpl;
import com.userPanel.service.impl.UserPanelServiceImpl;

/**
 * <class>ServiceConsumptionServlet</class> contains all request and response
 * related to set or get consumption service.
 */
public class ServiceConsumptionServlet extends HttpServlet {

    UserPanelService userPanelService = new UserPanelServiceImpl();

    Auctions_WSDLPortTypeProxy ap = null;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    ResourceBundle bundle = null;
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("type").equals("setConsumption")) {
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
        }else if (request.getParameter("type").equals("getConsumptionCustom")) {
            response.getOutputStream().print(
                    userPanelService.getLimitServiceCustom(
                            request.getParameter("userId"),
                            request.getParameter("productCode"),request.getParameter("serviceId")));
        } else if (request.getParameter("type").equals("activate")) {
            ServiceActivator obj = new ServiceActivator();
            response.getOutputStream().print(obj.setStatus().toString());
        } else if (request.getParameter("type").equals("deactivate")) {
            ServiceDeActivator obj = new ServiceDeActivator();
            response.getOutputStream().print(obj.setStatus().toString());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	JSONObject json = null;
    	bundle = ResourceBundle.getBundle("serverConfig");
        if (request.getParameter("type").equals("activator")) {
            UserPanelActivator service_activator = new UserPanelActivatorImpl();
            service_activator.activateService(request.getParameter("userId"),
                    request.getParameter("serviceId"),
                    request.getParameter("quantity"),
                    request.getParameter("name"));
        }
        else if (request.getParameter("type").equals("commitOrder")) {
            UserPanelActivator service_activator = new UserPanelActivatorImpl();
            json = new JSONObject();
            String value="";
           boolean action= service_activator.getService(request.getParameter("userId"),
        		   
                    request.getParameter("serviceId"));
            value=service_activator.commitInDataBase(request.getParameter("userId"),
                    request.getParameter("serviceId"),
                    request.getParameter("quantity"),
                    request.getParameter("name"),request.getParameter("months"),request.getParameter("accounts"),request.getParameter("sdate"),request.getParameter("edate"),request.getParameter("records"),request.getParameter("response"));
           //userId --mailId,serviceId --800, 
            //request.getParameter("productCode")
            //userId
            try {
				json.put("userId",request.getParameter("userId"));
				json.put("productCode",request.getParameter("productCode"));
				json.put("serviceId",request.getParameter("serviceId"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            response.getWriter().print(value);
            if(value=="true")
            this.uploadToServer(bundle.getString("orderCommitServletUrl"), json);
        }
    }

    private String uploadToServer(String urlstr, JSONObject json) {
		StringBuilder response = null;
		URL url = null;
		HttpURLConnection con = null;
		DataOutputStream wr = null;
		BufferedReader in = null;
		// .replaceAll("[╔,û#á]", "")
		// BufferedWriter writer = null;
		try {
			// out.println("Json passed is "+json);
			url = new URL(urlstr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			// writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
			// writer.write(json.toString());
			wr.writeBytes(json.toString());
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = null;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// System.out.println(response.toString());

		} catch (Exception e) {
			response = null;

		} finally {

			if (null != con) {
				con.disconnect();
				con = null;
			}
			if (null != wr) {
				try {
					wr.flush();
					wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				wr = null;
			}
			/*
			 * if(null != writer){ writer.flush(); writer.close(); writer = null; }
			 */
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = null;
			}
		}
		return (response == null ? "" : response.toString());

	}
}
