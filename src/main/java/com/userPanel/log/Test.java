/*package com.userPanel.log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post;

			post = new HttpPost("http://10.36.68.199/webservice/sendEmail.php?rquest=sendemailfromjames");

			String emailto="pranav.arya@visioninfocon.com,kamal.jain@visioninfocon.com";
//			String [] emailfrm={"deepak.kumar@visioninfocon.com"};

			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("emailto", emailto)); 
			nameValuePairs.add(new BasicNameValuePair("emailfrom","kamal.jain@visioninfocon.com")); 
			nameValuePairs.add(new BasicNameValuePair("emailsub","TM")); 
			nameValuePairs.add(new BasicNameValuePair("emailmsg","TA")); 
			
			object.put("emailto","pranav.arya@visioninfocon.com");
			object.put("emailfrom","kamal.jain@visioninfocon.com");
			object.put("emailsub","TM");
			object.put("emailmsg","TA");
			
			//you can as many name value pair as you want in the list.
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/