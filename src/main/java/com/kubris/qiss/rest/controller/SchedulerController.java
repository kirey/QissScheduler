package com.kubris.qiss.rest.controller;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.SchedulerExecutionLogDao;
import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.dto.ResponseDto;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.rest.service.JobService;
import com.kubris.qiss.utils.AppConstants;

@RestController
@RequestMapping(value = "rest/scheduler")
public class SchedulerController {

	@Autowired
	private SchedulersDao schedulersDao;

	@Autowired
	private SchedulerExecutionLogDao schedulerExecutionLogDao;

	@Autowired
	JobService jobService;

	/**
	 * get all jobs
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Schedulers>> getAllSchedulers() {
		return new ResponseEntity<List<Schedulers>>(schedulersDao.findAll(), HttpStatus.OK);
	}

	/**
	 * get job details
	 * 
	 * @param jobId
	 * @return
	 */
	@RequestMapping(value = "/job/{jobId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Schedulers> getJobById(@PathVariable int jobId) {
		Schedulers scheduler = schedulersDao.findById(jobId);
		return new ResponseEntity<Schedulers>(scheduler, HttpStatus.OK);
	}

	/**
	 * add new job and return updated list of schedulers
	 * 
	 * @param scheduler
	 * @return
	 */
	@RequestMapping(value = "/addJob", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Schedulers>> addScheduler(@RequestBody Schedulers scheduler) {
		scheduler.setStatus(AppConstants.SCHEDULER_STATUS_INACTIVE);
		schedulersDao.persist(scheduler);
		return new ResponseEntity<List<Schedulers>>(schedulersDao.findAll(), HttpStatus.OK);
	}

	/**
	 * Edit job by id and return updated list of schedulers
	 * 
	 * @param scheduler
	 * @return
	 */
	@RequestMapping(value = "/editJob", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Schedulers>> editScheduler(@RequestBody Schedulers scheduler) {
		schedulersDao.attachDirty(scheduler);
		return new ResponseEntity<List<Schedulers>>(schedulersDao.findAll(), HttpStatus.OK);
	}

	/**
	 * delete specific job and return updated list of schedulers
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteJob/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Schedulers>> deleteScheduler(@PathVariable int id) {
		schedulersDao.delete(schedulersDao.findById(id));
		return new ResponseEntity<List<Schedulers>>(schedulersDao.findAll(), HttpStatus.OK);
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
	 * 
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
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/startJob/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> startJob(@PathVariable int id) throws ClassNotFoundException {

		try {
			jobService.startJob(id);
			return new ResponseEntity<ResponseDto>(new ResponseDto("Job started"), HttpStatus.OK);
		} catch (SchedulerException e) {
			return new ResponseEntity<ResponseDto>(new ResponseDto("Job starting failed"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * MEthod for stopping job with given id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stopJob/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> stopJob(@PathVariable Integer id) {

		try {
			jobService.stopJob(id);
			return new ResponseEntity<ResponseDto>(new ResponseDto("Job stopped sucesffully"), HttpStatus.OK);
		} catch (SchedulerException e) {
			return new ResponseEntity<ResponseDto>(new ResponseDto("Job starting failed: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
