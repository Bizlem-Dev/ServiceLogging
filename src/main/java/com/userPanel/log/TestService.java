package com.userPanel.log;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.jindal.auction.domain.xsd.CustomerServiceStatus;
import com.userPanel.schedular.ServiceActivator;

public class TestService 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		try {

			
		/*	//String surl="http://10.36.76.15/webservice/OnlineExamActiveSummary.php?rquest=summaryReport";
			String surl="http://10.36.76.15/webservice/OnlineExamLogDetails.php?rquest=detailReport";
			String [] sparam = {"emails[]","group[]","date1[]","date2[]"};
			String [] sval={"nik.garg89@gmail.com","Jindalsteel","2013-06-06 12:00:00","2013-07-30 23:59:00"};

			String result = HTTPRequestPoster.callService(surl, sparam, sval);
			System.out.println(result);*/
			
			
			ServiceActivator sa= new ServiceActivator();
			sa.setStatus();

			//String res = HTTPRequestPoster.sendGetRequest("http://10.36.76.184/newServices/provisionForConference.php",
			//		"?uname=kamal&passwd=pranav");

			//System.out.println(res);

			/*
			 *  /// for sending email (Sitikant sir service)
			 * 			String surl="http://10.36.68.199/webservice/sendEmail.php?rquest=sendemailfromjames";
			String [] sparam = {"emailto[]","emailfrom[]","emailsub[]","emailmsg[]"};
			String [] sval={"pranav.arya@visioninfocon.com,kamal.jain@visioninfocon.com","deepak.kumar@visioninfocon.com","Testing from kamal","Hello this is testing"};

			String result = HTTPRequestPoster.callService(surl, sparam, sval);
			System.out.println(result);
			 */

			/*			String surl="http://pariharinfotech.com/inst/api/registeruser.php";
			String [] sparam = {"type","email","password","firstname","lastname"};
			String [] sval={"UPDATE","kamaljain1008@gmail.com","123456","kem","jain"};

			 *//**
			 * //POST params :
//
//type=REGISTER      (required)// UPDATE Forupdate user info
//email   (required)
//password   (required)
//firstname  (optional)
//lastname (optional)
//dob   (optional)       // [yyyy-mm-dd]
//gender   (optional)
//phoneno   (optional)
//device  (optional)   //device token
//image   (optional)
//

			  *//*
			String result = HTTPRequestPoster.callService(surl, sparam, sval);
			System.out.println(result);
			   */
			//	Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();

			//ap.serviceConsumption("", "", quantity, uom, lastConsumptionDate)

			//String status = ap.changeServiceStatus("pranavarya99@gmail.com", "VChat-10000", "active");
			//System.out.println(ap.getNoOfserviceAvailable("pranavarya99@gmail.com", "SMS"));

			//			System.out.println(ap.serviceConsumption("pranavarya99@gmail.com", "SMS", 1.0, "quantity", new Date()));
			//System.out.println(status);
			/*CustomerServiceStatus [] cs =ap.getCustomerServiceStatus("10000", "10000", "", "", "");

			List<CustomerServiceStatus> gg = new ArrayList<CustomerServiceStatus>(Arrays.asList(cs));
			ProductConfigDetails[]	ps = gg.get(0).getProductConfigList();
			List <ProductConfigDetails>  pp = new ArrayList<ProductConfigDetails>(Arrays.asList(ps));

			System.out.println(pp.get(0).getProductName());
			System.out.println(pp.get(1).getProductName());*/




		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/*	try {
			Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();

			double d = ap.getNoOfserviceAvailable("10000", "10011");
			System.out.println(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}


	public List<CustomerServiceStatus> getCustomerServiceStatus(java.lang.String customerId, java.lang.String productId, java.lang.String fromDate, java.lang.String toDate, java.lang.String status)
	{
		try 
		{
			Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();

			CustomerServiceStatus [] cs =ap.getCustomerServiceStatus(customerId, productId, fromDate, toDate, status);

			List<CustomerServiceStatus> gg = new ArrayList<CustomerServiceStatus>();
			if(cs!=null){
				gg = new ArrayList<CustomerServiceStatus>(Arrays.asList(cs));
			}



			//System.out.println(gg);
			return gg;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
