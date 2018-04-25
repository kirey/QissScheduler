package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.utils.AppConstants;

@Service
public class SchedJobListener implements JobListener{

	@Autowired
	SchedulerExecutionLogDao schedulerExecutionLogDao;
	
	@Autowired
	SchedulersDao schedulersDao;
	
	public static final String LISTENER_NAME = "globalJobListener";
	
	@Override
	public String getName() {
		return LISTENER_NAME;
	}
	
	

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		///add log to db when job is about to execute
		
		String jobName = context.getJobDetail().getKey().getName();
		System.out.println("Ime posla: " + jobName);
        
        Schedulers scheduler = schedulersDao.findByJobName(jobName);
		
        SchedulerExecutionLog jobLog = new SchedulerExecutionLog();
        jobLog.setStartTimestamp(new Date());
        jobLog.setStatus(AppConstants.JOB_STATUS_STARTED);
        jobLog.setJobName(context.getJobDetail().getKey().getName());
        jobLog.setScheduler(scheduler);
        
        schedulerExecutionLogDao.persist(jobLog);   
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
				
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		//add to log in db after executed successfully
		
		SchedulerExecutionLog  jobLog = schedulerExecutionLogDao.getLatestLogByJob(context.getJobDetail().getKey().getName());
		System.out.println("Ülazi u jobWasExecuted");
		System.out.println("FINISHING Log ID : " + jobLog.getId());
		if(jobLog.getStatus().equals(AppConstants.JOB_STATUS_FINISHED_FAILED)) {
			
		}else {
			jobLog.setStatus(AppConstants.JOB_STATUS_FINISHED_SUCCESSFULL);			
		}
		jobLog.setEndTimestamp(new Date());
		
		schedulerExecutionLogDao.merge(jobLog);
				
	}

}
