package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.utils.AppConstants;

@Component
public class SampleJob1 implements InterruptableJob {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;

	@Autowired
	private SchedulersDao schedulersDao;

	public void execute(JobExecutionContext context) {
		try {
			System.out.println("EXECUTING:" + context.getJobDetail().getKey().getName());

			logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
			logger.info(
					"-----------------------------------------------------------------------------------------------------");
			logger.info("Next job scheduled @ {}", context.getNextFireTime());

			//calling method for exception
			//dangerousMethod();

		} catch (Exception e) {

			SchedulerExecutionLog jobLog = schedulerExecutionLogDao
					.getLatestLogByJob(context.getJobDetail().getKey().getName());

			jobLog.setStatus(AppConstants.JOB_STATUS_FINISHED_FAILED);
			jobLog.setEndTimestamp(new Date());

			schedulerExecutionLogDao.merge(jobLog);
		}

	}

	//method to test exception
//	private void dangerousMethod() throws JobExecutionException {
//		throw new JobExecutionException();
//	}


	@Override
	public void interrupt() throws UnableToInterruptJobException {

	}
}
