package com.userPanel.service;

import java.util.List;

import org.json.JSONException;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserPanelService.
 */
public interface UserPanelService {

	/**
	 * This method is used for fetching all user list for autocompleter.
	 * 
	 * @return the string
	 * @throws JSONException
	 *             the jSON exception
	 */
	String personList() throws JSONException;

	/**
	 * This method is used for allotting,deallotting web services i.e
	 * call,webmail,chat,vchat etc to the users.
	 * 
	 * @param userId
	 *            here userId is cas loginId.
	 * @param assignedPerson
	 *             here assignedPerson is assigned user.
	 * @param productCode
	 *             here productCode is name of web service.
	 * @param flag
	 *            the flag
	 * @return the string
	 */
	String SaveUser(String userId, String assignedPerson, String productCode,
			String flag,String serviceId);

	/**
	 * This method is used for fetching list of already assigned user of
	 * corresponding to productCode and userId.
	 * 
	 * @param userId
	 *            here userId is cas loginId.
	 * @param productCode
	 *             here productCode is name of web service.
	 * @return the list
	 */
	List<String> assignedUserList(String userId, String productCode,String serviceId);
	String getReport(String userId, String productCode,String fromDate,String toDate) throws JSONException;

	/**
	 * This method is used for set value of consumed quantity of different services like sms,vchat,chat.
	 * 
	 * @param userId
	 *            here userId is cas loginId.
	 * @param productCode
	 *             here productCode is name of web service.
	 * @param assignedQuanity
	 *            here assignedQuanity is consumed quanity.
	 * @return the string
	 */
	String setConsumption(String userId, String productCode,
			double assignedQuanity,String serviceId);

	/**
	 * This method is used for  deActivate particular service of user.
	 * 
	 * @param productCode
	 *            here productCode is name of web service.
	 * @param userId
	 *             here userId is cas loginId.
	 */
	void deActivateFlag(String productCode, String userId,int iserviceId);

	/**
	 * This method is used for set value of assigned quantity of different services like sms,vchat,chat.
	 * 
	 * @param userId
	 *             here userId is cas loginId.
	 * @param productCode
	 *             here productCode is name of web service.
	 * @return the limit service
	 */
	String getLimitService(String userId, String productCode);

	/**
	 * This method is used for removing userdetails corresponding to productcode and userId.
	 * 
	 * @param userId
	 *             here userId is cas loginId.
	 * @param serviceId
	 *            the service id
	 */
	void deleteUser(String userId, String serviceId);

	/**
	 * This method is used for fetching userdetails corresponding to productcode and userId.
	 * 
	 * @param userName
	 *           here userName is cas loginId
	 * @param productCode
	 *             here productCode is name of web service.
	 * @return the user details
	 */
	String getUserDetails(String userName, String productCode);

	/**
	 * This method is used for splitting userdetails.
	 * 
	 * @param personList
	 *            here personList is assigned user List.
	 * @param productCode
	 *             here productCode is name of web service.
	 * @param userId
	 *             here userId is cas loginId.
	 * @return the string
	 */
	String splitUser(String personList, String productCode, String userId,String serviceId);
	
	public String compSling(String companyName, String website,String email,String number ,String userId);
	public String compMdmIntoDatabase(String companyName, String website,String email,String number,String compSlingPath,String productId,String userId,String serviceId);
	 public String mdmServiceUser(String personList, String nameList, String numberList);
	 public String userMdmIntoDatabase(String personList, String nameList, String numberList,String adminList,String productCode,String customerId,String serviceId);
	
	 public String assignedMDMUserList(String userId,String serviceId);
	 public String assignedMDMCompany(String userId,String serviceId);
	 public String userMdmUpdateDatabase(String idList, String adminList,String customerId,String serviceId);
	 public String userUpdateMdmPerson(String id, String email,String userPercent,String userPercentType,String customerId,String serviceId);
	 public String userGetService(String userId);
	 String splitUserLiveService(String personList, String productCode, String userId,String serviceId, int queueID) ;
	 public String callGetService(String urlStr, String[] paramName,
				String[] paramValue);
	 public String getLimitServiceCustom(String userId, String productCode,String serviceId);
}
