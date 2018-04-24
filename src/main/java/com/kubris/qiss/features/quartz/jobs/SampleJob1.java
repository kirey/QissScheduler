package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.utils.AppConstants;

@Component
public class SampleJob1 implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SchedulerExecutionLogDao schedulerExecutionLogDao;
    
    @Autowired
    private SchedulersDao schedulersDao;

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    	System.out.println("EXECUTING: ontext.getJobDetail().getKey().getName()");
    	
        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        logger.info("-----------------------------------------------------------------------------------------------------");
        logger.info("Next job scheduled @ {}", context.getNextFireTime());
        
        String jobName = context.getJobDetail().getKey().getName();
        
        Schedulers scheduler = schedulersDao.findByJobName(jobName);
        
        ///add log to db
        SchedulerExecutionLog jobLog = new SchedulerExecutionLog();
        jobLog.setStartTimestamp(new Date());
        jobLog.setStatus(AppConstants.JOB_STATUS_STARTED);
        jobLog.setJobName(context.getJobDetail().getKey().getName());
        jobLog.setScheduler(scheduler);
        
        schedulerExecutionLogDao.attachDirty(jobLog);   

        context.getJobDetail().getJobDataMap().put("jobId", jobLog.getId());  
        System.out.println("Log ID created: " + jobLog.getId());
    }

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		
	}
}
