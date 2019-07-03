package com.userPanel.dao;

import java.util.List;

import org.json.JSONException;

public interface UserPanelDAO {

	String personList() throws JSONException;

	String SaveUser(String userId, String assignedPerson, String productCode,String flag,String serviceId);

	List<String> assignedUserList(String userId,String productCode,String serviceId);
	
	void deleteUser(String userId, String serviceId);

	String getUserDetails(String userName, String productCode);
	String assignUserOpenfire(String assignedPerson);
	String getReportData(String userId,String productCode,String fromDate,String toDate) throws JSONException;
	public String getServiceLimitDetails(String userId, String productCode,String serviceId);
}
