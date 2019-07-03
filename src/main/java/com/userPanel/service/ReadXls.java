package com.userPanel.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import jxl.read.biff.BiffException;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


// TODO: Auto-generated Javadoc
/**
 * The Interface UserPanelService.
 */
public interface ReadXls {

	/**
	 * This method is used for fetching all user list for autocompleter.
	 * 
	 * @return the string
	 * @throws JSONException
	 *             the jSON exception
	 */
	public  String insertCompany(String compid,DB objDB,String userid,String compname,InputStream inputStream) throws IOException, BiffException;
	public  String insertCompanyByform(String tabtype,String compid,DB objDB,String userid,String compname,String cname,String conperson,String email, String website,String contact,String type );
	public  ArrayList insertProduct(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException;
	 public  ArrayList insertAddress(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException;
	 public  ArrayList insertCustomerVendor(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException;
	 public  ArrayList insertVendorItem(String serviceId,DB objDB,String userId,String quantity,String productCode,InputStream inputStream) throws IOException, BiffException;
	 public DB getMongoDb();
	 public String checkcontact(String compid,String email);
	 public String changeFlag(String compid,String email);
	 public JSONObject  getInvitedCompany(String compid); 
	 public String getProcessLength(DB db,String serviceId);
	 public String getProduct(DB db,String serviceId,String userId);
	 public String getAssignByCat(DB db,String serviceId,String userId);
	 public int getProductApprovedLength(DB db,String serviceId,String userId);
	 public String assignSmeMongo(DB db,String serviceId,String userId,String email,String percent,String percentType);
	 public String getAssignedLength(DB db,String serviceId);
	 public String unAssignSmeMongo(DB db,String serviceId,String customerId,String email,String strTtype);
	  public String previewAssignPercent(DB db,String serviceId,String userId,String email,String percent,String percentType);
	  public String getSMEUserProcessLength(DB db,String serviceId,String userId);
	  public String apprProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String prodimage,
	    		String prodid,String prodname,String prodmodelnumb,String manufacturer,String serviceId,String userId);
	  public String rejectProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String prodimage,
	    		String prodid,String prodname,String prodmodelnumb,String manufacturer,String serviceId,String userId);
	  public String reRunProduct(DB db,String pos,String category,String prodcode,String prodshortdesc,String addnMetdata,String serviceId,String userId);
	  public int getAddressApprovedLength(DB db,String serviceId,String userId);
	  public String apprAddress(DB db,String pos,String streetaddress,String country,String region,String locality,String postalcode,String addrid,String serviceId,String userId);
	  public String rejectAddress(DB db,String pos,String streetaddress,String country,String region,String locality,String postalcode,String addrid,String serviceId,String userId);
	  public String getVendor(DB db,String serviceId,String userId);
	  public String getSearchProductView(DB db,String serviceId,String searchText);
	  public String  getInvitedPCcontact(String compid, String user);
}
