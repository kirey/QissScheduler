package com.kubris.qiss.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;

@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {

	@Autowired
	private SchedulersDao schedulersDao;

	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Schedulers> getAllSchedulers() {
		List<Schedulers> listSchedulers = schedulersDao.findAll();
		return listSchedulers;
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
		Schedulers scheduler = schedulersDao.findById(id);
		schedulersDao.delete(scheduler);
		return "ok";
	}
	
}
