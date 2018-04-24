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
				
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
				
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		
		SchedulerExecutionLog  log = schedulerExecutionLogDao.findById(context.getJobDetail().getJobDataMap().getInt("jobId"));
		
		//set scheduler inactive
		Schedulers scheduler = schedulersDao.findByJobName(context.getJobDetail().getKey().getName());
		scheduler.setStatus(AppConstants.SCHEDULER_STATUS_FINISHED);
		schedulersDao.attachDirty(scheduler);
		
		if(jobException==null)
		{			
			log.setStatus(AppConstants.JOB_STATUS_FINISHED_SUCCESSFULL);			
		}
		else {
			log.setStatus(AppConstants.JOB_STATUS_FINISHED_FAILED);
		} 
		
		log.setEndTimestamp(new Date());
		schedulerExecutionLogDao.attachDirty(log);
		
		
	}

}
