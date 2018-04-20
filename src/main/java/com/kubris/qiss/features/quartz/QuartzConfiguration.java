package com.kubris.qiss.features.quartz;

import static org.quartz.JobBuilder.newJob;

import java.io.IOException;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kubris.qiss.features.quartz.jobs.SampleJob1;
import com.kubris.qiss.features.quartz.jobs.SampleJob2;

@Configuration
public class QuartzConfiguration {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public Scheduler scheduler() throws SchedulerException, IOException {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
       
        return scheduler;
    }

    
    @Bean(name="jobDetail1")
    public JobDetail jobDetail1() {

        return newJob().ofType(SampleJob1.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail1")).withDescription("Invoke Sample Job service...").build();
    }
    
    
    @Bean(name="jobDetail2")
    public JobDetail jobDetail2() {

        return newJob().ofType(SampleJob2.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail2")).withDescription("Invoke Sample Job service...").build();
    }

}
