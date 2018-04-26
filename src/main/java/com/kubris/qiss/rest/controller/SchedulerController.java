package com.kubris.qiss.rest.controller;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.dto.ResponseDto;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.features.quartz.jobs.SampleJob1;
import com.kubris.qiss.features.quartz.jobs.SchedJobListener;
import com.kubris.qiss.utils.AppConstants;

@RestController
@RequestMapping(value = "rest/scheduler")
public class SchedulerController {

	@Autowired
	private SchedulersDao schedulersDao;

	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;

	@Autowired
	private Scheduler scheduler1;

	@Autowired
	private SchedJobListener schedJobListener;

	/*
	 * @Autowired
	 * 
	 * @Qualifier("jobDetail1") private JobDetail jobDetail1;
	 */

	/**
	 * get all jobs
	 * @return
	 */
	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Schedulers>> getAllSchedulers() {
		return new ResponseEntity<List<Schedulers>>(schedulersDao.findAll(), HttpStatus.OK);
	}

	/**
	 * get job details
	 * @param jobId
	 * @return
	 */
	@RequestMapping(value = "/job/{jobId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Schedulers> getJobById(@PathVariable int jobId) {
		Schedulers scheduler = schedulersDao.findById(jobId);
		return new ResponseEntity<Schedulers>(scheduler, HttpStatus.OK);
	}

	/**
	 * add new job
	 * @param scheduler
	 * @return
	 */
	@RequestMapping(value = "/addJob", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> addScheduler(@RequestBody Schedulers scheduler) {
		scheduler.setStatus(AppConstants.SCHEDULER_STATUS_INACTIVE);
		schedulersDao.persist(scheduler);
		return new ResponseEntity<ResponseDto>( new ResponseDto("successfully added"), HttpStatus.OK);
	}

	/**
	 * Edit job by id
	 * @param scheduler
	 * @return
	 */
	@RequestMapping(value = "/editJob", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> editScheduler(@RequestBody Schedulers scheduler) {
		schedulersDao.attachDirty(scheduler);
		return new ResponseEntity<ResponseDto>( new ResponseDto("successfully edited"), HttpStatus.OK);
	}

	/**
	 * delete specific job
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteJob/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deleteScheduler(@PathVariable int id) {
		schedulersDao.delete(schedulersDao.findById(id));
		return new ResponseEntity<ResponseDto>(new ResponseDto("successfully deleted"), HttpStatus.OK);
	}

	/**
	 * get scheduler log details
	 * 
	 * @param idLog
	 * @return
	 */
	@RequestMapping(value = "/jobLog/{idLog}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SchedulerExecutionLog> getJobLog(@PathVariable int idLog) {
		SchedulerExecutionLog sel = schedulerExecutionLogDao.findById(idLog);
		return new ResponseEntity<SchedulerExecutionLog>(sel, HttpStatus.OK);
	 }
	
	/**
	 * returns all logs for selected job
	 * @param idJob
	 * @return
	 */
	@RequestMapping(value = "/jobHistory/{idJob}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SchedulerExecutionLog>> getHistoryByJobId(@PathVariable int idJob) {
	  List<SchedulerExecutionLog> listHistoryByJobId = schedulerExecutionLogDao.getLogsByJobId(idJob);
	  return new ResponseEntity<List<SchedulerExecutionLog>>(listHistoryByJobId, HttpStatus.OK);
	 }

	/**
	 * Method for starting scheduler/job
	 * 
	 * @param id
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/startJob/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> startJob(@PathVariable int id) throws SchedulerException {
		
		Schedulers schedulerEnt = schedulersDao.findById(id);
		CronTrigger cronTrigger = newTrigger().withIdentity(TriggerKey.triggerKey(schedulerEnt.getJobName(), "group1"))
				.withSchedule(cronSchedule(schedulerEnt.getCronExpression())).build();

		JobDetail jobDetail1 = newJob().ofType(SampleJob1.class).storeDurably()
				.withIdentity(JobKey.jobKey(schedulerEnt.getJobName())).withDescription("Invoke Sample Job service...")
				.build();

		scheduler1.getListenerManager().addJobListener(schedJobListener);

		if (!scheduler1.checkExists(jobDetail1.getKey())) {
			scheduler1.scheduleJob(jobDetail1, cronTrigger);
			scheduler1.start();
		}		
		else {
			scheduler1.resumeJob(JobKey.jobKey(schedulerEnt.getJobName()));
		}

		// update status u scheduler
		schedulerEnt.setStatus(AppConstants.SCHEDULER_STATUS_ACTIVE);
		schedulersDao.merge(schedulerEnt);
		
		return new ResponseEntity<ResponseDto>( new ResponseDto("Job started"), HttpStatus.OK); 

	}

	@RequestMapping(value = "/stopJob/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> stopJob(@PathVariable int id) throws SchedulerException {
		
		Schedulers schedulerEnt = schedulersDao.findById(id);
		
		scheduler1.pauseJob(JobKey.jobKey(schedulerEnt.getJobName()));
		scheduler1.interrupt(JobKey.jobKey(schedulerEnt.getJobName()));
		
		schedulerEnt.setStatus(AppConstants.SCHEDULER_STATUS_INACTIVE);


		schedulersDao.attachDirty(schedulerEnt);
		
		SchedulerExecutionLog schedulerLogForUpdate = schedulerExecutionLogDao
				.getLatestLogByJob(schedulerEnt.getJobName());
		schedulerLogForUpdate.setStatus(AppConstants.JOB_STATUS_INTERRUPT);
		schedulerLogForUpdate.setEndTimestamp(new Date());
		
		schedulerExecutionLogDao.attachDirty(schedulerLogForUpdate);

		return new ResponseEntity<ResponseDto>( new ResponseDto("Job stopped"), HttpStatus.OK); 
	}

}
