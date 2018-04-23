package com.kubris.qiss.rest.controller;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;

@RestController
@RequestMapping(value = "rest/scheduler")
public class SchedulerController {

	@Autowired
	private SchedulersDao schedulersDao;
	
	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;

	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Schedulers> getAllSchedulers() {
		return schedulersDao.findAll();
	}
	
	@RequestMapping(value = "/job/{jobId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Schedulers getJobById(@PathVariable int jobId) {
		return schedulersDao.findById(jobId);
	}

	@RequestMapping(value = "/addJob", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addScheduler(@RequestBody Schedulers scheduler) {
		schedulersDao.persist(scheduler);
		return "ok";
	}

	@RequestMapping(value = "/editJob", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String editScheduler(@RequestBody Schedulers scheduler) {
		schedulersDao.attachDirty(scheduler);
		return "ok";
	}
	
	@RequestMapping(value = "/deleteJob/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteScheduler(@PathVariable int id) {
		schedulersDao.delete(schedulersDao.findById(id));
		return "ok";
	}
	
	//SchedulerLog by log id
	@RequestMapping(value = "/jobLog/{idLog}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public SchedulerExecutionLog getJobLog(@PathVariable int idLog) {
		return schedulerExecutionLogDao.findById(idLog);
	 }
	
	
	@RequestMapping(value = "/jobHistory/{idJob}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public List<SchedulerExecutionLog> getHistoryByJobId(@PathVariable int idJob) {
	  List<SchedulerExecutionLog> listHistoryByJobId = schedulerExecutionLogDao.getLogsByJobId(idJob);
	  
	  return listHistoryByJobId;
	 }
	
	
	
}
