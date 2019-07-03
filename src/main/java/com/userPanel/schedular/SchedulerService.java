package com.userPanel.schedular;

/*
 * @author chirag.sharma
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/*
 * this class is used to call the job scheduler which is triggered at the specific time for a specific
 *  job.
 */
public class SchedulerService implements ServletContextListener {
    private static final Logger logger = Logger
            .getLogger(SchedulerService.class);
    /*
     * this method is not called, currently. It is the destroy method.
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    int hour, min, deActivatorMinute, deActivatorHour;

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

        String protocol = oReadProperties.getProperty("hour");
        if (protocol != null)
            hour = Integer.parseInt(protocol);
        else
            hour = 21;

        protocol = oReadProperties.getProperty("minute");
        if (protocol != null)
            min = Integer.parseInt(protocol);
        else
            min = 0;
        protocol = oReadProperties.getProperty("deActivatorHour");
        if (protocol != null)
            deActivatorHour = Integer.parseInt(protocol);
        else
            deActivatorHour = 23;

        protocol = oReadProperties.getProperty("deActivatorMinute");
        if (protocol != null)
            deActivatorMinute = Integer.parseInt(protocol);
        else
            deActivatorMinute = 0;

    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    /*
     * this method is called as the application is loaded. This is the
     * initialization of the process.
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {

        try {
            setval();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("ServiceActivatorStatusTrigger",
                            "ServiceActivator")
                    .withSchedule(
                            CronScheduleBuilder.dailyAtHourAndMinute(hour, min))
                    .build();
            Trigger trigger2 = TriggerBuilder
                    .newTrigger()
                    .withIdentity("ServiceDeActivatorTrigger",
                            "ServiceDeActivator")
                    .withSchedule(
                            CronScheduleBuilder.dailyAtHourAndMinute(
                                    deActivatorHour, deActivatorMinute))
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(
                    JobBuilder
                            .newJob(ServiceActivatorJob.class)
                            .withIdentity("ServiceActivatorJob",
                                    "ServiceActivator").build(), trigger);
            scheduler.scheduleJob(
                    JobBuilder
                            .newJob(ServiceDeActivatorJob.class)
                            .withIdentity("ServiceDeactivatorJob",
                                    "ServiceDeActivator").build(), trigger2);

        } catch (SchedulerException e) {
            logger.info("an exception is occured in CronTriggerScheduler.Due to this exception : "
                    + e);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}