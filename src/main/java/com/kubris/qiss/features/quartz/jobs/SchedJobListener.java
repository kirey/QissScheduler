package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.utils.AppConstants;

@Service
public class SchedJobListener implements JobListener {

	@Autowired
	SchedulerExecutionLogDao schedulerExecutionLogDao;

	@Autowired
	SchedulersDao schedulersDao;

	public static final String LISTENER_NAME = "globalJobListener";

	@Override
	public String getName() {
		return LISTENER_NAME;
	}
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		SchedulerExecutionLog jobLog = schedulerExecutionLogDao
				.getLatestLogByJob(context.getJobDetail().getKey().getName());
		logger.info("Ülazi u jobWasExecuted");
		logger.info("FINISHING Log ID : " + jobLog.getId());
		if (jobLog.getStatus().equals(AppConstants.JOB_STATUS_FINISHED_FAILED)) {
			// ispravi
		} else if (jobLog.getStatus().equals(AppConstants.JOB_STATUS_STARTED)) {
			jobLog.setStatus(AppConstants.JOB_STATUS_FINISHED_SUCCESSFULL);
		} else if (jobLog.getStatus().equals(AppConstants.JOB_STATUS_INTERRUPT)) {
			// ispravi
		} else {
			System.out.println("Ulazi u job succeess");
			jobLog.setStatus(AppConstants.JOB_STATUS_FINISHED_SUCCESSFULL);
		}
		jobLog.setEndTimestamp(new Date());

		schedulerExecutionLogDao.merge(jobLog);

	}

}
