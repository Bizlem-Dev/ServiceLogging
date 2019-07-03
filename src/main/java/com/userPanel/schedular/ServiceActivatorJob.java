package com.userPanel.schedular;




import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;




public class ServiceActivatorJob  implements Job {

	@SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ServiceActivatorJob.class);
	/*
	 * this method is very important, which have assigned a task to do.
	 */

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("Entry class: CallingScheduledJob, method: execute");
		/*
		 * here we assigned a job.we are calling a web service here.
		 */		

		System.out.println("Enter in scheduling");
		ServiceActivator obj = new ServiceActivator();
		obj.setStatus();


		/*SecheduleAction obj =new SecheduleAction();
		obj.fetchAndUpdateOrderDetails();
		obj.fetchAndUpdateOrderShipmentDetails();
		obj.fetchAndUpdateProductConfigDetails();
		obj.updateCustomerserviceStatus();*/

		logger.info("Exit class: CallingScheduledJob, method: execute");
	}

}
