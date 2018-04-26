package com.kubris.qiss.features.quartz.jobs;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
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
@DisallowConcurrentExecution
public class SampleJob1 implements InterruptableJob {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;

	@Autowired
	private SchedulersDao schedulersDao;

	private boolean loopControl = false;

	private SchedulerExecutionLog jobLogLatest = null;
	
	private JobExecutionContext jobExecutionContext = null;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			jobExecutionContext = context;
			
			String jobName = context.getJobDetail().getKey().getName();
			logger.info("Upisivanje posla u bazu sa imenom : " + jobName);

			Schedulers scheduler = schedulersDao.findByJobName(jobName);

			SchedulerExecutionLog jobLog = new SchedulerExecutionLog();
			jobLog.setStartTimestamp(new Date());
			jobLog.setStatus(AppConstants.JOB_STATUS_STARTED);
			jobLog.setJobName(context.getJobDetail().getKey().getName());
			jobLog.setScheduler(scheduler);
			schedulerExecutionLogDao.persist(jobLog);
			
			jobLogLatest = schedulerExecutionLogDao.getLatestLogByJob(context.getJobDetail().getKey().getName());

			loopControl = true;
			System.out.println("");
			for (int i = 0; i < 20 && loopControl; i++) {
				Thread.currentThread().sleep(1000);
				logger.info(
						"EXECUTING:" + context.getJobDetail().getKey().getName() + "WITH LOG ID: " + jobLogLatest.getId());
			}
			logger.info("Zavrsena petlja");

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
		logger.info("Ulazi u interrupt " + jobLogLatest.getJobName());
		jobLogLatest.setStatus(AppConstants.JOB_STATUS_INTERRUPT);
		jobLogLatest.setEndTimestamp(new Date());
		loopControl = false;
		//TriggerKey tkey = new TriggerKey(jobLogLatest.getJobName());
		/*try {
			//jobExecutionContext.getScheduler().pauseTrigger(TriggerKey.triggerKey(jobLogLatest.getJobName(), "group1"));
			jobExecutionContext.getScheduler().deleteJob(JobKey.jobKey(jobLogLatest.getJobName()));
		} catch (SchedulerException e) {
			
		}*/
	}
}
