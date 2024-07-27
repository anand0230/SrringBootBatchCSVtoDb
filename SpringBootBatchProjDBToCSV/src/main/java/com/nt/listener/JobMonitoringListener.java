package com.nt.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobMonitoringListener implements JobExecutionListener{
	long starttime,endtime;

	@Override
	public void beforeJob(JobExecution arg0) {
		
		System.out.println("Job Starting Time: "+new Date());
		starttime=System.currentTimeMillis();
		System.out.println("Status : "+arg0.getStatus());
		
		
		
	}
	

	@Override
	public void afterJob(JobExecution arg0) {
		System.out.println("Job Ending Time: "+new Date());
		endtime=System.currentTimeMillis();
		System.out.println("Status : "+arg0.getStatus());
		System.out.println("Total Time Taken By Job: "+(endtime-starttime));
	}


}
