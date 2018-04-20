package com.kubris.qiss.features.quartz;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
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
import static org.quartz.CronScheduleBuilder.*;
	
@Component
public class ActivateScheduler {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Scheduler scheduler1;
	
	@Autowired
	private Scheduler scheduler2;	
	
	@Autowired 
	@Qualifier("jobDetail1")
	private JobDetail jobDetail1;
	
	@Autowired 
	@Qualifier("jobDetail2")
	private JobDetail jobDetail2;

	
	
	@PostConstruct
	public void startMe(){
		logger.info("------------------------------------------------- Activate scheduler");
		
		Trigger 	trigger1 = newTrigger().withIdentity("trigger1", "group1").withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
		CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1").withSchedule(cronSchedule("0/5 * * * * ?")).build();

		
		try {
			scheduler1.scheduleJob(jobDetail1, trigger1);
			scheduler1.start();
			
			scheduler2.scheduleJob(jobDetail2, trigger2);
			scheduler2.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}		
		
	}
}
