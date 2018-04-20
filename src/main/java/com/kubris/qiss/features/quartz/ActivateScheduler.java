package com.kubris.qiss.features.quartz;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ActivateScheduler {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Scheduler scheduler;
	
	@Autowired 
	@Qualifier("jobDetail1")
	private JobDetail jobDetail;
	
	
	@PostConstruct
	public void startMe(){
		logger.info("------------------------------------------------- Activate scheduler");
		
		Trigger trigger = newTrigger().forJob(jobDetail).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Sample trigger").withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}		
		
	}
}
