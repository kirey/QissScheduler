package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kubris.qiss.data.dao.JobExecutionLogDao;
import com.kubris.qiss.data.dao.JobsDao;
import com.kubris.qiss.data.entity.JobExecutionLog;
import com.kubris.qiss.data.entity.Jobs;
import com.kubris.qiss.utils.AppConstants;

@Component
@DisallowConcurrentExecution
public class SampleJob2 implements InterruptableJob {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobExecutionLogDao jobExecutionLogDao;

	@Autowired
	private JobsDao schedulersDao;

	private boolean loopControl = false;

	private JobExecutionLog jobLogLatest = null;

	private JobExecutionContext jobExecutionContext = null;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			jobExecutionContext = context;

			String jobName = context.getJobDetail().getKey().getName();

			Jobs job = schedulersDao.findByJobName(jobName);

			JobExecutionLog jobLog = new JobExecutionLog();
			jobLog.setStartTimestamp(new Date());
			jobLog.setStatus(AppConstants.JOB_STATUS_STARTED);
			jobLog.setJobName(context.getJobDetail().getKey().getName());
			jobLog.setJob(job);
			jobExecutionLogDao.persist(jobLog);

			jobLogLatest = jobExecutionLogDao.getLatestLogByJob(context.getJobDetail().getKey().getName());
			
			logger.info("execute()::***EXECUTING SAMPLE JOB 2:" + context.getJobDetail().getKey().getName() + "WITH LOG ID: "
					+ jobLogLatest.getId());

			loopControl = true;
			for (int i = 0; i < 10 && loopControl; i++) {
				Thread.currentThread().sleep(1000);
				logger.info("execute::EXECUTING:" + context.getJobDetail().getKey().getName() + "WITH LOG ID: "
					+ jobLogLatest.getId());
			}

			if (loopControl) {
				context.getJobDetail().getJobDataMap().put("status", AppConstants.JOB_STATUS_FINISHED_SUCCESSFULL);
			} else
				context.getJobDetail().getJobDataMap().put("status", AppConstants.JOB_STATUS_INTERRUPT);

		} catch (Exception e) {

			context.getJobDetail().getJobDataMap().put("status", AppConstants.JOB_STATUS_FINISHED_FAILED);

		} finally {
			JobExecutionException jobExecutionException = new JobExecutionException();
			jobExecutionException.setUnscheduleAllTriggers(true);

		}

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		logger.info("interrupt");
		loopControl = false;
	}
}
