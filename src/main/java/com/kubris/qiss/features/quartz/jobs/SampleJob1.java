package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
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

	private boolean loopControl = false;


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("EXECUTING:" + context.getJobDetail().getKey().getName());

			logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
			logger.info(
					"-----------------------------------------------------------------------------------------------------");
			logger.info("Next job scheduled @ {}", context.getNextFireTime());

			SchedulerExecutionLog jobLog = schedulerExecutionLogDao
					.getLatestLogByJob(context.getJobDetail().getKey().getName());
			loopControl = true;
			System.out.println("");
			for (int i = 0; i < 20 && loopControl; i++) {
				Thread.currentThread().sleep(1000);
				System.out.print(".");
			}

		} catch (Exception e) {

			SchedulerExecutionLog jobLog = schedulerExecutionLogDao
					.getLatestLogByJob(context.getJobDetail().getKey().getName());

			jobLog.setStatus(AppConstants.JOB_STATUS_FINISHED_FAILED);
			jobLog.setEndTimestamp(new Date());

			schedulerExecutionLogDao.merge(jobLog);
		} finally {
			JobExecutionException jobExecutionException = new JobExecutionException();
			jobExecutionException.setUnscheduleAllTriggers(true);

		}

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Ulazi u interrupt");
		loopControl = false;
	}
}
