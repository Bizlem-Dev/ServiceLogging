/*package com.userPanel.log;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.simple.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestSampleRestInterface {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);


		//if timeouts needs to be configured then please add the following lines

		//Set the connection time out (milliseconds)
		client.setConnectTimeout(5000);

		//Set the read time out (in milliseconds)
		client.setReadTimeout(5000);


		if host has restricted access while connecting to the server by       weblogic basic authentication. Please add the following lines with proper credentials

//		String [] sparam = {"emailto","emailfrom","emailsub","emailmsg"};
//		String [] sval={"pranav.arya@visioninfocon.com,kamal.jain@visioninfocon.com","deepak.kumar@visioninfocon.com","Testing from kamal","Hello this is testing"};
		String [] emailto={"pranav.arya@visioninfocon.com","kamal.jain@visioninfocon.com"};
		String [] emailfrm={"deepak.kumar@visioninfocon.com"};

		WebResource service = client.resource(getBaseURI());
		JSONObject object = null;
		try {
			object = new JSONObject();
			object.put("emailto","pranav.arya@visioninfocon.com");
			object.put("emailfrom","kamal.jain@visioninfocon.com");
			object.put("emailsub","TM");
			object.put("emailmsg","TA");
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
		System.out.println("Response :"
				+ service.accept(MediaType.APPLICATION_JSON).entity(object).post(JSONObject.class,JSONObject.class));
				
				accept(
						MediaType.APPLICATION_JSON).post(JSONObject.class,
								object));  
		long endTime = System.currentTimeMillis();
		System.out.println(startTime-endTime);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://10.36.68.199/webservice/sendEmail.php?rquest=sendemailfromjames")
				.build();
	}
}*/