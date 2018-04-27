package com.kubris.qiss.rest.service;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;

public interface JobService {

	public CronTrigger createTrigger(String cronExpresion, String jobName, String groupName);
	public JobDetail createJob(String jobName, String groupName);
	public void startJob(Integer id) throws SchedulerException;
	public void stopJob(Integer id) throws SchedulerException;
	public void deleteJob(String jobName);
	public void pauseJob(String jobName);
	public void resumeJob(String jobName);
	public String getJobState(String jobName, String groupName) throws SchedulerException;
	public boolean isJobWithNamePresent(String jobName, String groupName) throws SchedulerException;
	public void updateCronExpForJob(String cronExpresion, String jobName, String groupName) throws SchedulerException;
	public boolean isJobCurrentllyRunning(String jobName, String groupName) throws SchedulerException;
}
