package com.userPanel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.userPanel.dao.UserPanelDAO;
import com.userPanel.util.ConnectionImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserPanelDAOImpl implements UserPanelDAO {

	private Connection conn = null;
	private Statement statement;
	private PreparedStatement preparedStatement;

	@SuppressWarnings("unused")
	public String personList() throws JSONException {
		JSONArray jarray = new JSONArray();
		List<String> keys = new ArrayList<String>();
		try {

			String query = "SELECT username FROM person";
			conn = ConnectionImpl.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			JSONObject json = new JSONObject();
			while (rs.next()) {

				json = new JSONObject();

				jarray.put(rs.getString(1));
			}
			conn.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.printStackTrace();

		}

		return jarray.toString();
	}
	
	public String getReportData(String userId,String productCode,String fromDate,String toDate) throws JSONException {
		JSONArray jarray = new JSONArray();
		List<String> keys = new ArrayList<String>();
		String a = "";
		String query ="";
		try {

//			String query = "SELECT username FROM person";
			if(productCode.equals("0")){
			 query = "SELECT  b.productId as 'Service Name',a.customerName,a.quantity,b.consumedQuantity,b.uom,b.consumedDate,a.status FROM customer_service_status a LEFT JOIN service_consumtion_history b ON a.customerId = b.customerId where b.customerId='"+userId+"' and (b.consumedDate BETWEEN '"+fromDate+"' AND '"+toDate+"')";
			}else{
				 //query = "SELECT  b.productId as 'Service Name',a.customerName,a.quantity,b.consumedQuantity,b.uom,b.consumedDate,a.status FROM customer_service_status a LEFT JOIN service_consumtion_history b ON a.customerId = b.customerId where b.customerId='"+userId+"' and b.productId='"+productCode+"' and (b.consumedDate BETWEEN '"+fromDate+"' AND '"+toDate+"')";
				 query = "SELECT b.productId as 'Service Name',a.customerName,a.quantity,b.consumedQuantity,b.uom,b.consumedDate,a.status FROM customer_service_status a inner JOIN service_consumtion_history b ON a.customerId = b.customerId where b.customerId=a.customerId  and a.productcode=b.productId and a.customerId='"+userId+"' and b.productId='"+productCode+"' and (b.consumedDate BETWEEN '"+fromDate+"' AND '"+toDate+"')";
				 
			}
		
			conn = ConnectionImpl.getConnectionServiceDb();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			JSONObject json = new JSONObject();
			if(rs != null){
			while (rs.next()) {

				json = new JSONObject();
//				a = rs.getString("Service Name");
				json.put("service",rs.getString("Service Name"));
				json.put("customer",rs.getString("customerName"));
				json.put("quantity",rs.getString("quantity"));
				json.put("conquantity",rs.getString("consumedQuantity"));
				json.put("uom",rs.getString("uom"));
				json.put("cndate",rs.getString("consumedDate"));
				jarray.put(json);
			}
			}
			conn.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			return e.getMessage();
//			e.printStackTrace();

		}

		return jarray.toString();
//		return a;
	}


	public String SaveUser(String userId, String assignedPerson,
			String productCode,String flag,String serviceId) {
		try {
			String query="";
			if(flag.equals("query"))
			{
			query = "INSERT INTO adminpanel(UserId, serviceId, username, email, isAdmin, serviceName, Sno, openid) " +
					"VALUES (?,?,?,?,?,?,?,(select CONCAT('u',a.entity_id) from person a where a.username='"
					+ assignedPerson + "'))";
			}
			else
			{
				query = "INSERT INTO adminpanel(UserId, serviceId, username, email, isAdmin, serviceName, Sno, openid) " +
						"VALUES (?,?,?,?,?,?,?,?)";	
			}
			conn = ConnectionImpl.getConnection();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, serviceId);
			preparedStatement.setString(3, assignedPerson);
			preparedStatement.setString(4, assignedPerson);
			preparedStatement.setString(5, "");
			preparedStatement.setString(6, productCode);
			preparedStatement.setInt(7, 1);
			if(!flag.equals("query"))
			{
				preparedStatement.setString(8, "");
				}
			preparedStatement.executeUpdate();
			conn.close();
			preparedStatement.close();	

		} catch (Exception e) {
			e.printStackTrace();

		}

		return "insert";
	}
	
	//// entry in openfire to assign user to group
	
	public String assignUserOpenfire(String assignedPerson) {
		try {
//			Connection raveConn = null;
//			raveConn = ConnectionImpl.getConnection();
			String query="";
			
			query = "INSERT INTO ofGroupUser(groupName, username, administrator) " +
					"VALUES (?,(select CONCAT('u',a.entity_id) from person a where a.username='"
					+ assignedPerson + "'),?)";
			
			conn = ConnectionImpl.getConnectionOpenfire();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, "People");
//			preparedStatement.setString(2, "");
			preparedStatement.setByte(3, (byte)0);
			preparedStatement.executeUpdate();
			conn.close();
			preparedStatement.close();	

		} catch (Exception e) {
			e.printStackTrace();

		}

		return "insert";
	}

	public List<String> assignedUserList(String userId, String productCode,String serviceId) {
		List<String> list = new ArrayList<String>();
		try {
//			int serviceId = Integer.
//			int serviceId = Integer.parseInt(productCode);
			String query = "SELECT username FROM adminpanel where UserId='"
					+ userId + "' and serviceId=" + serviceId + " ";
			conn = ConnectionImpl.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {

				list.add(rs.getString(1));
			}

			conn.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.printStackTrace();

		}

		return list;
	}

	public void deleteUser(String userId, String serviceId) {
		try {
			String query = "delete from adminpanel where UserId = '" + userId
					+ "' and serviceId='" + serviceId + "';";
			conn = ConnectionImpl.getConnection();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.executeUpdate();
			conn.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public String getUserDetails(String userName, String productCode) {
		String userId="";
		try {
			String query = "SELECT UserId FROM adminpanel where (username='"
					+ userName + "' or UserId='"+userName +"') and serviceName='" + productCode + "' ";
			conn = ConnectionImpl.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {

			userId=	rs.getString(1);
			
			}

			conn.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.printStackTrace();

		}

		return userId;
	}

	/*public static void main(String[] args) {
		UserPanelDAOImpl p = new UserPanelDAOImpl();
		p.SaveUser("pran3bull@gmail.com", "ankita.mishra.ap@gmail.com", "Chat");
	}*/
	
	public String getServiceLimitDetails(String userId, String productCode,String serviceId) {
		String remainingquantity="";
		try {
//			String query = "SELECT UserId FROM adminpanel where (username='"
//					+ userName + "' or UserId='"+userName +"') and serviceName='" + productCode + "' ";
			String query ="select quantity-consumptionQuantity as remainingquantity from customer_service_status  where customerId='"+userId+"' and productCode = '"+productCode+"' and serviceId = '"+serviceId+"'";
			conn = ConnectionImpl.getConnectionServiceDb();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {

				remainingquantity=	rs.getString("remainingquantity");
			}

			conn.close();
			statement.close();
			rs.close();

		}

		catch (Exception e) {
			e.printStackTrace();

		}

		return remainingquantity;
	}
}
