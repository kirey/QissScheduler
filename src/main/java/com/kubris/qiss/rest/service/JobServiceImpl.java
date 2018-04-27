package com.kubris.qiss.rest.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;

import org.quartz.CronTrigger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.features.quartz.jobs.SampleJob1;
import com.kubris.qiss.features.quartz.jobs.SchedJobListener;
import com.kubris.qiss.utils.AppConstants;

@Service
public class JobServiceImpl implements JobService{

	@Autowired
	private Scheduler scheduler1;
	
	@Autowired
	private SchedJobListener schedJobListener;
	
	@Autowired
	private SchedulersDao schedulersDao;
	
	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public CronTrigger createTrigger(String cronExpresion, String jobName, String groupName) {
				
		CronTrigger trigger = newTrigger().withIdentity(TriggerKey.triggerKey(jobName, groupName))
		.withSchedule(cronSchedule(cronExpresion)).build();		
		return trigger;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public JobDetail createJob(String jobName, String groupName) throws ClassNotFoundException {
		
		Job jobClass = (Job) applicationContext.getBean("sampleJob1");
		
		JobDetail jobDetail = newJob().ofType(jobClass.getClass()).storeDurably()
				.withIdentity(JobKey.jobKey(jobName,AppConstants.GROUP_NAME)).withDescription("Invoke Sample Job service...")
				.build();
				
		return jobDetail;
	}

	@Override
	public void startJob(Integer id) throws SchedulerException, ClassNotFoundException {
		
		Schedulers schedulerEnt = schedulersDao.findById(id);
		
		CronTrigger cronTrigger = createTrigger(schedulerEnt.getCronExpression(), schedulerEnt.getJobName(), AppConstants.GROUP_NAME);
		JobDetail jobDetail = createJob(schedulerEnt.getJobName(), AppConstants.GROUP_NAME);		
				
		scheduler1.getListenerManager().addJobListener(schedJobListener);
		if(!scheduler1.checkExists(jobDetail.getKey()))
			scheduler1.scheduleJob(jobDetail, cronTrigger);
		
	    scheduler1.start();
		
		schedulerEnt.setStatus(AppConstants.SCHEDULER_STATUS_ACTIVE);
		schedulersDao.merge(schedulerEnt);
		
	}

	@Override
	public void stopJob(Integer id) throws SchedulerException {
		
		Schedulers schedulerEnt = schedulersDao.findById(id);
		
		scheduler1.interrupt(JobKey.jobKey(schedulerEnt.getJobName(), AppConstants.GROUP_NAME));
		scheduler1.deleteJob(JobKey.jobKey(schedulerEnt.getJobName(), AppConstants.GROUP_NAME));
		
		schedulerEnt.setStatus(AppConstants.SCHEDULER_STATUS_INACTIVE);
		schedulersDao.attachDirty(schedulerEnt);
		
		/*SchedulerExecutionLog schedulerLogForUpdate = schedulerExecutionLogDao
				.getLatestLogByJob(schedulerEnt.getJobName());
		schedulerLogForUpdate.setStatus(AppConstants.JOB_STATUS_INTERRUPT);
		schedulerLogForUpdate.setEndTimestamp(new Date());
		
		schedulerExecutionLogDao.attachDirty(schedulerLogForUpdate); */
		
	}


	@Override
	public void deleteJob(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pauseJob(Integer id) {
		
	}


	@Override
	public void resumeJob(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getJobState(String jobName, String groupName) throws SchedulerException {
		
		JobKey jobKey = new JobKey(jobName, groupName);

		JobDetail jobDetail = scheduler1.getJobDetail(jobKey);

		List<? extends Trigger> triggers = scheduler1.getTriggersOfJob(jobDetail.getKey());
		if(triggers != null && triggers.size() > 0){
			for (Trigger trigger : triggers) {
					TriggerState triggerState = scheduler1.getTriggerState(trigger.getKey());

					if (TriggerState.PAUSED.equals(triggerState)) {
						return "PAUSED";
					}else if (TriggerState.BLOCKED.equals(triggerState)) {
						return "BLOCKED";
					}else if (TriggerState.COMPLETE.equals(triggerState)) {
						return "COMPLETE";
					}else if (TriggerState.ERROR.equals(triggerState)) {
						return "ERROR";
					}else if (TriggerState.NONE.equals(triggerState)) {
						return "NONE";
					}else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";
					}
				}
			}
	
		 //add this to response when err catch in controller "SchedulerException while checking job with name and group exist:"+e.getMessage()
		return null;
	}


	@Override
	public boolean isJobWithNamePresent(String jobName, String groupName) throws SchedulerException {
	
		JobKey jobKey = new JobKey(jobName, groupName);
			
		if (scheduler1.checkExists(jobKey)){
			return true;
		}
				
	    //add this to response when err catch in controller "SchedulerException while checking job with name and group exist:"+e.getMessage()
		return false;
	}

	
	@Override
	public void updateCronExpForJob(String cronExpresion, String jobName, String groupName) throws SchedulerException {
	
		CronTrigger newTrigger = createTrigger(cronExpresion,jobName,groupName);
		scheduler1.rescheduleJob(TriggerKey.triggerKey(jobName), newTrigger);
		
		//add this to response when err catch in controller "SchedulerException while updating job key :"+jobKey+ " is running. error message :"+e.getMessage()
	}


	@Override
	public boolean isJobCurrentllyRunning(String jobName, String groupName) throws SchedulerException {
		
		List<JobExecutionContext> currentJobs = scheduler1.getCurrentlyExecutingJobs();
		if(currentJobs !=null) {
			for (JobExecutionContext jobCtx: currentJobs){
			    String jobN = jobCtx.getJobDetail().getKey().getName();
			    String groupN = jobCtx.getJobDetail().getKey().getGroup();
			    if (jobN.equalsIgnoreCase(jobName)&& groupN.equals(groupName)) {
			        return true;
			    }               
			}
		}
		
		//add this to response when err catch in controller "SchedulerException while checking job with key :"+jobKey+ " is running. error message :"+e.getMessage()
		return false;
	}
	
}
