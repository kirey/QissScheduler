package com.kubris.qiss.features.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kubris.qiss.features.quartz.jobs.SampleJob1;
import com.kubris.qiss.features.quartz.jobs.SampleJob2;
	
@Component
@EnableScheduling
public class ActivateScheduler {
	Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private Scheduler scheduler1;
//	
//	@Autowired
//	private Scheduler scheduler2;
	
	
	private Scheduler scheduler;
	private JobKey jobKey1;
	private JobDetail job1;

	private JobKey jobKey2;
	private JobDetail job2;
	
	private Trigger trigger1;

	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired 
	@Qualifier("jobDetail1")
	private JobDetail jobDetail1;
	
	@Autowired 
	@Qualifier("jobDetail2")
	private JobDetail jobDetail2;

	
	
	//@PostConstruct
//	public void startMe1(){
//		logger.info("------------------------------------------------- Activate scheduler");
//		
//		JobDetail jobDetail1 = (JobDetail) applicationContext.getBean("jobDetail1");
//		JobDetail jobDetail2 = (JobDetail) applicationContext.getBean("jobDetail2");
//		
//		
//		
//		
//		Trigger 	trigger1 = newTrigger().withIdentity("trigger1", "group1").withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
//		CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1").withSchedule(cronSchedule("0/5 * * * * ?")).build();
//
//		
//		try {
//			scheduler1.scheduleJob(jobDetail1, trigger1);
//			scheduler1.start();
//			
//			scheduler2.scheduleJob(jobDetail2, trigger2);
//			scheduler2.start();
//
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}		
//		
//	}
	
	@PostConstruct
	public void startMe2(){
		logger.info("------------------------------------------------- Activate scheduler");
		
		Job sampleJob1 = (Job)applicationContext.getBean("sampleJob1");
		
		
		jobKey1 = new JobKey("job1", "group1");
    	job1 = JobBuilder.newJob(sampleJob1.getClass()).withIdentity(jobKey1).build();

    	jobKey2 = new JobKey("job2", "group1");
    	job2 = JobBuilder.newJob(SampleJob2.class).withIdentity(jobKey2).build();

    	Trigger trigger1 = TriggerBuilder
		.newTrigger()
		.withIdentity("triggerName1", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
    	
    	Trigger trigger2 = TriggerBuilder
		.newTrigger()
		.withIdentity("triggerName2", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
    	
    	
    	
    	
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
	    	scheduler.start();
	    	scheduler.scheduleJob(job1, trigger1);
	    	scheduler.scheduleJob(job2, trigger2);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Scheduled(fixedDelay = 40000)
	public void changeTriggerJobDetail1(){
		
		logger.info(".................................................................... Job1 rescheduled");
		JobDetail jobDetail1 = (JobDetail) applicationContext.getBean("jobDetail1");
		Trigger 	trigger3 = newTrigger().withIdentity("triggerName2", "group1").withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
		
		try {
			scheduler.rescheduleJob(new TriggerKey("triggerName1", "group1"), trigger3);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}
