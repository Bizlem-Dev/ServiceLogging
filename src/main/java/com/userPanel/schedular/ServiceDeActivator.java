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
import org.jivesoftware.smack.XMPPException;

import com.jindal.auction.auctionServies.Auctions_WSDLPortTypeProxy;
import com.jindal.auction.domain.xsd.CustomerServiceStatus;
import com.userPanel.log.ADBConnection;
import com.userPanel.log.HTTPRequestPoster;
import com.userPanel.log.PropUtil;
import com.userPanel.log.TestService;
import com.userPanel.log.XMppConnection;
import com.userPanel.service.UserPanelService;
import com.userPanel.service.impl.UserPanelServiceImpl;

public class ServiceDeActivator {

    // http://110.172.129.187:8080/TrWeb/rest/Roster/delete?username=u30

    List<String> service;
    List<String> user = new ArrayList<String>();

    ADBConnection adb = new ADBConnection();
    XMppConnection xmpp = new XMppConnection();
    private ResourceBundle bundle = null;
    private static final Logger logger = Logger
            .getLogger(ServiceDeActivator.class);

    public ServiceDeActivator() {
        service = new ArrayList<String>();
        /*
         * service.add("Chat"); service.add("Vchat"); service.add("SMS");
         */

        setval();
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

    @SuppressWarnings("unused")
    public ArrayList<String> setStatus() {
        ArrayList<String> reponseList = new ArrayList<String>();
        try {
            Auctions_WSDLPortTypeProxy ap = new Auctions_WSDLPortTypeProxy();
            UserPanelService userPanel = null;
            Date date = new Date();
            String lasmod = new SimpleDateFormat("yyyy-MM-dd").format(date);
            logger.info("Entry class: ServiceActivator, method: setStatus at "
                    + date.toString());

            TestService ts = new TestService();
            List<CustomerServiceStatus> list = ts.getCustomerServiceStatus("",
                    "", "", lasmod, "active");

            for (CustomerServiceStatus c : list) {
                if (c.getProductCode().equalsIgnoreCase(service.get(0))) {
                    logger.info("Entry class: ServiceActivator, Chat service status change enter");
                    try {
                        userPanel = new UserPanelServiceImpl();
                        deleteRoster(c);
                        userPanel.deActivateFlag(c.getProductCode(),
                                c.getCustomerId(),c.getserviceId());
                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "inactive");
                    } catch (Exception e) {
                        logger.info("an exception is occured in Chat service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(1))) {
                    try {
                        logger.info("Entry class: ServiceActivator, VChat service status change enter");
                        userPanel = new UserPanelServiceImpl();
                        deleteRoster(c);
                        userPanel.deActivateFlag(c.getProductCode(),
                                c.getCustomerId(),c.getserviceId());
                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "inactive");
                    } catch (Exception e) {
                        logger.info("an exception is occured in VChat service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(2))) {
                    try {
                        logger.info("Entry class: ServiceActivator, SMS service status change enter");
                        userPanel = new UserPanelServiceImpl();
                        String[] param = { "sms", "userId" };
                        String[] pval = { "N",
                                c.getCustomerId().replace("@", "_").trim() };

                        String result = HTTPRequestPoster.callService(
                                PropUtil.setflagY, param, pval);
                        // 2. calling service to activate finally
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "inactive");
                        userPanel.deActivateFlag(c.getProductCode(), c.getCustomerId(),c.getserviceId());
                    } catch (Exception e) {
                        logger.info("an exception is occured in SMS service status change.Due to this exception : "
                                + e);
                    }
                } else if (c.getProductCode().equalsIgnoreCase(service.get(3))) {
                    String[] param = { "camp", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(4))) {
                    String[] param = { "lime", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(5))) {
                    String[] param = { "ox", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(6))) {
                    String[] param = { "wc", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(7))) {
                    String[] param = { "log", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(8))) {
                    String[] param = { "deg3", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(9))) {
                    String[] param = { "exam", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(10))) {
                    String[] param = { "cs", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(11))) {
                	userPanel = new UserPanelServiceImpl();
                    String[] param = { "call", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);                   
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                    userPanel.deActivateFlag("call", c.getCustomerId(),c.getserviceId());
                } else if (c.getProductCode().equalsIgnoreCase(service.get(12))) {
                    String[] param = { "mp", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    String result = HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(13))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(14))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } /*else if (c.getProductCode().equalsIgnoreCase(service.get(15))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(16))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(17))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                }*/ else if (c.getProductCode().equalsIgnoreCase(service.get(15))) {
                    String[] param = { "callMB", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(PropUtil.setflagY, param,
                            pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else if (c.getProductCode().equalsIgnoreCase(service.get(16))) {
                    userPanel = new UserPanelServiceImpl();
                    String[] param = { "email", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                    userPanel.deActivateFlag(c.getProductCode(), c.getCustomerId(),c.getserviceId());
                } else if (c.getProductCode().equalsIgnoreCase(service.get(17))) {
                    String[] param = { "helpdesk", "userId" };
                    String[] pval = { "N",
                            c.getCustomerId().replace("@", "_").trim() };
                    HTTPRequestPoster.callService(
                            PropUtil.setflagY, param, pval);
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                }  else if (c.getProductCode().equalsIgnoreCase(service.get(18))) {
                    ap.changeServiceStatus(c.getCustomerId(),
                            c.getProductCode(), "inactive");
                } else {
                    bundle = ResourceBundle.getBundle("serverConfig");
                    String bannerService = bundle.getString("bannerServices");
                    String[] bannerListArray = bannerService.split(",");
                    ArrayList<String> bannerList = new ArrayList<String>(
                            Arrays.asList(bannerListArray));
                    if(bannerList.contains(c.getProductCode())){
                        ap.changeServiceStatus(c.getCustomerId(),
                                c.getProductCode(), "inactive");
                    }
                }
                reponseList.add(c.getCustomerId() + "-->" + c.getProductCode()
                        + "-->DeActivated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponseList;

    }

    public void deleteRoster(CustomerServiceStatus c) {
        try {
            user = adb.GetUidpass(c.getCustomerId());
            xmpp.login("u" + user.get(0), user.get(1));
            xmpp.displayBuddyList();
            xmpp.disconnect();
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServiceDeActivator obj = new ServiceDeActivator();
        obj.setStatus();
    }
}
