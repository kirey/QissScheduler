package com.kubris.qiss.features.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SampleJob1 implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        logger.info("-----------------------------------------------------------------------------------------------------");
        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }
}
