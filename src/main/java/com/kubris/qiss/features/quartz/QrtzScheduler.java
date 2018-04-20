package com.kubris.qiss.features.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.kubris.qiss.features.quartz.jobs.SampleJob;
import com.kubris.qiss.features.quartz.jobs.SampleJob1;

@Configuration
public class QrtzScheduler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Scheduler scheduler() throws SchedulerException, IOException {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
       
        return scheduler;
    }

   
    @Bean(name="jobDetail")
    public JobDetail jobDetail() {

        return newJob().ofType(SampleJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail")).withDescription("Invoke Sample Job service...").build();
    }
    
    
    @Bean(name="jobDetail1")
    public JobDetail jobDetail1() {

        return newJob().ofType(SampleJob1.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail")).withDescription("Invoke Sample Job service...").build();
    }

}
