package com.userPanel.log;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.jindal.auction.domain.xsd.CustomerServiceStatus;
import com.userPanel.schedular.ServiceDeActivator;

public class TestServiceDea 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		try {
			
			ServiceDeActivator sa= new ServiceDeActivator();
			sa.setStatus();
			
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
