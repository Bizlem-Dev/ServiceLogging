package com.userPanel.service.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public interface UserPanelActivator {
    ArrayList<String> activateService(String userId, String serviceId,
            String quantity, String name);

    void activateChat(String userId, String serviceId, String quantity,
            String name);

    void activateVideoChat(String userId, String serviceId, String quantity,
            String name);

    void activateCallMBFlag(String userId, String serviceId, String quantity,
            String name);

    void activateEmailFlag(String userId, String serviceId, String quantity,
            String name);

    void activateSMSFlag(String userId, String serviceId, String quantity,
            String name);

    void activateMassMailer(String userId, String serviceId, String quantity,
            String name);

    void activateLimeSurvey(String userId, String serviceId, String quantity,
            String name);

    void activateOpenX(String userId, String serviceId, String quantity,
            String name);

    void activateWebConference(String userId, String serviceId,
            String quantity, String name);

    void activateLogRecord(String userId, String serviceId, String quantity,
            String name);

    void activate360Degree(String userId, String serviceId, String quantity,
            String name);

    void activateOnlineExam(String userId, String serviceId, String quantity,
            String name);

    void activateContactSystem(String userId, String serviceId,
            String quantity, String name);

    void activateCall(String userId, String serviceId, String quantity,
            String name);

    void activateMailPlatform(String userId, String serviceId, String quantity,
            String name);

    void activateHelpDesk(String userId, String serviceId, String quantity,
            String name);

    void activateBanner(String userId, String serviceId, String quantity,
            String name, ResourceBundle bundle);
    String commitInDataBase(String userId,String name, String serviceId, String quantity,String months,String accounts,String sdate,String edate,String records,String response);

	String commitOrderToMainDataBase(String userId, String name,
			String serviceId, String productDescription, String quantity,
			String uom, java.sql.Date service_start_date, java.sql.Date service_end_date,
			String consumptionQuantity, java.sql.Date  lastConsumptionDate,
			String likeDislike, String comment, String rating, String share,
			String rfq, String blog, String status, String uom_description,
			String config_id, String shipment_id, String order_id,
			java.sql.Date lastInsertedDate);
	boolean getService(String customerId, String productCode);
	
}
