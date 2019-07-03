package com.userPanel.schedular;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.jindal.auction.domain.xsd.CustomerServiceStatus;
import com.userPanel.log.ADBConnection;
import com.userPanel.log.HTTPRequestPoster;
import com.userPanel.log.PropUtil;
import com.userPanel.log.TestService;

public class ServiceActivator {

    // http://110.172.129.187:8080/TrWeb/rest/Roster/delete?username=u30
    ADBConnection adb = new ADBConnection();
    List<String> service;
    private ResourceBundle bundle = null;
    private static final Logger logger = Logger
            .getLogger(ServiceActivator.class);

    public ServiceActivator() {
        service = new ArrayList<String>();
        /*
         * service.add("Chat"); service.add("Vchat"); service.add("SMS");
         */

        setval();
        PropUtil prp = new PropUtil();
        prp.setprop();
    }

    public void setval() {
        Properties oReadProperties = new Properties();
        try {

            oReadProperties.load(getClass().getResourceAsStream(
                    "/serverConfig.properties"));

        } catch (FileNotFoundException ex) {
            if (logger.isDebugEnabled())
                logger.info("Exit: Class: ServiceActivator, method: setval() "
                        + ex.getMessage());

        } catch (IOException ex) {
            if (logger.isDebugEnabled())
                logger.info("Exit: Class: ServiceActivator, method: setval() "
                        + ex.getMessage());
        }
        // red fire server settings

        String protocol = oReadProperties.getProperty("services");
        String a[] = protocol.split(",");
        for (String s : a) {
            service.add(s);
        }
    }

    public ArrayList<String> setStatus() {
        ArrayList<String> reponseList = new ArrayList<String>();
        try {

            // System.out.println("Enter");
            Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();

            Date date = new Date();
            String lasmod = new SimpleDateFormat("yyyy-MM-dd").format(date);
            logger.info("Entry class: ServiceActivator, method: setStatus at "
                    + date.toString());

            TestService ts = new TestService();
            List<CustomerServiceStatus> list = ts.getCustomerServiceStatus("",
                    "", lasmod, "", "inactive");
            for (CustomerServiceStatus c : list) {
                if (c.getProductCode().equalsIgnoreCase(service.get(0))) {
                    logger.info("Entry class: ServiceActivator, Chat service status change enter");
                    try {
                        // 1. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "Chat", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");

                        ap.serviceConsumption(c.getCustomerId(),
                                c.getProductCode(), new Double(1), "Quantity",
                                new Date());
                    } catch (Exception e) {
                        logger.info("an exception is occured in Chat service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(1))) {
                    try {
                        logger.info("Entry class: ServiceActivator, VChat service status change enter");

                        // 1. calling service to set flag Y (pranav service)
                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "VChat", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };

                        String[] param1 = { "Chat", "userId" };
                        String[] pval1 = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };

                        String result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param1, pval1);

                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);

                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                        ap.serviceConsumption(c.getCustomerId(),
                                c.getProductCode(), new Double(1), "Quantity",
                                new Date());
                    } catch (Exception e) {
                        logger.info("an exception is occured in VChat service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(2))) {
                    logger.info("Entry class: ServiceActivator, SMS service status change enter");
                    String pswd = "";

                    try {
                        // 1. calling user creation using vikash servie
                        String result = HTTPRequestPoster.sendGetRequest(
                                PropUtil.smsusercreation, "?username="
                                        + c.getCustomerId().trim()
                                        + "&displayName="
                                        + c.getCustomerName().trim()
                                        + "&email=" + c.getCustomerId().trim());

                        pswd = getPswd(result);
                        logger.info("calling user creation using (vikash service) "
                                + pswd);
                    } catch (Exception e) {
                        logger.info("an exception is occured in SMS service user creation using (vikash service).Due to this exception : "
                                + e);
                    }
                    try {
                        // 2. calling value set service(pranav)
                        // String
                        // p1url="http://10.36.76.123:8081/sling/servlet/service/check.setCredential";
                        String[] param1 = { "userId", "msgUser", "msgPassword" };
                        String[] pval1 = {
                                c.getCustomerId().replace("@", "_").trim(),
                                c.getCustomerId().trim(), pswd };

                        String result = HTTPRequestPoster.callService(
                                PropUtil.smssetvalue, param1, pval1);

                        logger.info("calling calling value set service(pranav) "
                                + result);
                    } catch (Exception e) {
                        logger.info("an exception is occured in SMS service calling value set service(pranav).Due to this exception : "
                                + e);
                    }

                    try {
                        // 3. calling update no of sms requested

                        HTTPRequestPoster.sendGetRequest(PropUtil.smsupdatenos,
                                "?username=" + c.getCustomerId().trim()
                                        + "&creadiamt=" + c.getQuantity());
                    } catch (Exception e) {
                        logger.info("an exception is occured in SMS service calling update no of sms requested (vikash service).Due to this exception : "
                                + e);

                    }

                    try {
                        // 4. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "sms", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };

                        String result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y for SMS (pranav service) "
                                + result);

                        // 5. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in SMS service.Due to this exception : "
                                + e);

                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(3))) {
                    logger.info("Entry class: ServiceActivator, CAMP service status change enter");
                    try {
                        // 1. calling webserive to create user in Mass Mailer
                        // application

                        // String
                        // surl="http://10.36.68.199/webservice/createUserMassMailer.php?rquest=createUser";
                        String[] sparam = { "emails[]" };
                        String[] sval = { c.getCustomerId() };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.MassMailer, sparam, sval);

                        logger.info("calling service create user in Mass Mailer application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "camp", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Chat service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(4))) {
                    logger.info("Entry class: ServiceActivator, LIME service status change enter");
                    try {
                        // 1. calling webserive to create user in LIME server
                        // application

                        // String
                        // surl="http://10.36.68.199/webservice/createUser_LimeSurvey.php?rquest=createUser";
                        String[] sparam = { "emails[]", "fname[]", "lname[]",
                                "groups[]" };
                        String[] sval = { c.getCustomerId(),
                                c.getCustomerName(), " ", "visioninfocon" };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.LIMEservice, sparam, sval);
                        logger.info("calling service create user in LIME server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "lime", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in LIME service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().contains(service.get(5))) {
                    logger.info("Entry class: ServiceActivator, OPENX service status change enter");
                    try {
                        /**
                         * http://10.36.76.37/OPENDEVELOP/addNewAdvCampBann.php?
                         * advertiserName=siti&emailAddress=siti@socialmail.in&
                         * usrnm=siti&passwd=pranav&startDate=2013-04-14&
                         * "&endDate=''"+ zonename=socialmail.in-Home-Left&
                         * pricingModel=CPC/CPA/CPM/TANANCY&revenue=1
                         */
                        // 1. calling webserive to create user in OPENX server
                        // application
                        String cparam = "advertiserName=" + "Demo"
                                + c.getCustomerName().replace(' ', '_')
                                + "&emailAddress=" + c.getCustomerId().trim()
                                + "&usrnm="
                                + c.getCustomerId().replace("@", "_").trim()
                                + "&passwd=" + getDBPSWD(c.getCustomerId());

                        String result = HTTPRequestPoster.sendGetRequest(
                                PropUtil.OPENX, cparam);

                        logger.info("calling service create user in Openx server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "ox", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in OPENX service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(6))) {
                    logger.info("Entry class: ServiceActivator, Web Conference service status change enter");
                    try {
                        // 1. calling webserive to create user in Web Conference
                        // server application
                        String result = HTTPRequestPoster.sendGetRequest(
                                PropUtil.WebConf, "?usrnm="
                                        + c.getCustomerId().replace("@", "_")
                                                .trim() + "&passwd="
                                        + getDBPSWD(c.getCustomerId()));

                        logger.info("calling service create user in  Web Conference application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        // String
                        // purl="http://10.36.76.123:8081/sling/servlet/service/check.serviceCheck";
                        String[] param = { "wc", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Web Conference "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Web Conference service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(7))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {

                        // 1. calling service to set flag Y (pranav service)

                        String[] param = { "log", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) "
                                + result);

                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(8))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {
                        // 1. calling webserive to create user in deg360 server

                        String[] sparam = { "emails[]", "business[]",
                                "location[]" };
                        String[] sval = { c.getCustomerId(), "steel",
                                "visioninfocon" };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.deg360, sparam, sval);
                        logger.info("calling service create user in 360 Degree server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        String[] param = { "deg3", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Web Conference "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(9))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {
                        // 1. calling webserive to create user in Online Exam

                        String[] sparam = { "emails[]", "groups[]" };
                        String[] sval = { c.getCustomerId(), "visioninfocon" };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.onlineexam, sparam, sval);
                        logger.info("calling service create user in LIME server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        String[] param = { "exam", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Web Conference "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(10))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {
                        // 1. calling webserive to create user in Contact system
                        // server application

                        String[] sparam = { "emails[]" };
                        String[] sval = { c.getCustomerId() };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.cs, sparam, sval);
                        logger.info("calling service create user in Contact system  server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        String[] param = { "cs", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Cpntact system  "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(11))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {
                        // 1. calling webserive to create user in call asterisk
                        // system server application

                        String result = HTTPRequestPoster.sendGetRequest(
                                PropUtil.call + c.getCustomerId()
                                        + "&passwd=05GDO05VVO0", "");
                        logger.info("calling service create user in call asterisk system  server application "
                                + result);

                        // 2. calling service to set flag Y (pranav service)

                        String[] param = { "call", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Cpntact system  "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                }

                else if (c.getProductCode().equalsIgnoreCase(service.get(12))) {
                    logger.info("Entry class: ServiceActivator, Logs service status change enter");
                    try {
                        // 1. calling webserive to create user in call Mail
                        // Platform system server application

                        String[] sparam = { "emails[]" };
                        String[] sval = { c.getCustomerId() };
                        String result = HTTPRequestPoster.callService(
                                PropUtil.mailplatform, sparam, sval);
                        logger.info("calling service create user in Mail Platform server application "
                                + result);
                        // 2. calling service to set flag Y (pranav service)

                        String[] param = { "mp", "userId" };
                        String[] pval = { "Y",
                                c.getCustomerId().replace("@", "_").trim() };
                        result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        logger.info("calling service to set flag Y (pranav service) for  Mail Platform system  "
                                + result);

                        // 3. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Logs service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(13))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(14))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(15))) {
                    // For callMB Call me back
                    String[] param = { "callMB", "userId" };
                    String[] pval = { "Y",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(PropUtil.setflagY, param,
                            pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(16))) {
                    // For email
                    String[] param = { "email", "userId" };
                    String[] pval = { "Y",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(PropUtil.setflagY, param,
                            pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(17))) {
                    // For helpDesk
                    String[] sparam = { "displayname[]", "userId[]", "mail[]",
                            "firstname[]", "lastname[]", "location[]",
                            "contact_no[]", "phone[]", "title[]",
                            "department[]", "userPassword[]" };
                    String[] sval = { c.getCustomerId().split("@")[0],
                            c.getCustomerId(), c.getCustomerId(),
                            c.getCustomerName(), ".", "INDIA", "0000000000",
                            "0000000000", "Engineer", "Engineer", "password" };
                    HTTPRequestPoster.callService(PropUtil.helpdesk, sparam,
                            sval);
                    String[] param = { "helpdesk", "userId" };
                    String[] pval = { "Y",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(PropUtil.setflagY, param,
                            pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(18))) {
                    // For WebMail
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "active");
                } else {
                    bundle = ResourceBundle.getBundle("serverConfig");
                    String bannerService = bundle.getString("bannerServices");
                    String[] bannerListArray = bannerService.split(",");

                    ArrayList<String> bannerList = new ArrayList<String>(
                            Arrays.asList(bannerListArray));
                    System.out.println(bannerList);
                    if (bannerList.contains(c.getProductCode())) {
                        System.out.println("-->" + c.getProductCode());
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "active");
                    }
                }
                reponseList.add(c.getCustomerId() + "-->" + c.getProductCode()
                        + "--> Activated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * select (SELECT loginname FROM phplist_admin p where id=msg.owner) as
         * username,sent, (select name from phplist_custom_campaign where id =
         * (SELECT campaignid FROM custom_campaign_message c where messageid =
         * msg.id)) as messageid, count(um.viewed) as views, count(um.status) as
         * total,subject from phplist_usermessage um, phplist_message msg where
         * um.messageid = msg.id group by msg.id
         */
        return reponseList;
    }

    public String getPswd(String str) {

        JSONParser parser = new JSONParser();

        String s = str;
        Object obj = null;
        try {
            obj = parser.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray array = (JSONArray) obj;
        JSONObject obj2 = (JSONObject) array.get(0);
        return obj2.get("Password").toString();

    }

    @SuppressWarnings("unused")
    private String getNode(String jid) {

        String node = jid;
        int pos = node.indexOf("@");

        if (pos > -1)
            node = jid.substring(0, pos);

        return node;
    }

    public void Saveuser(CustomerServiceStatus c) {
        try {
            for (int i = 1; i < c.getQuantity(); i++) {
                adb.SaveUser(c.getCustomerId(), c.getShipmentId(), "", "", "",
                        i, "");
            }
        } catch (Exception e) {
            logger.info("an exception is occured in getDBPSWD .Due to this exception : "
                    + e);

        }
    }

    public String getDBPSWD(String userId) {
        try {
            String pswd = adb.Getpass(userId);

            return pswd;
        } catch (Exception e) {
            logger.info("an exception is occured in getDBPSWD .Due to this exception : "
                    + e);
            return "Password";
        }
    }

    public static void main(String[] args) {

    }
}
