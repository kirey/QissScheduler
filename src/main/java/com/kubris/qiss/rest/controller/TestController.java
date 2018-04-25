package com.kubris.qiss.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.Schedulers;

@RestController
@RequestMapping("/rest")
public class TestController {

	
	@Autowired
	private SchedulersDao schedulersDao;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value = "/testSecurity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testSecurity() {
		return "test";
	}
	
	@RequestMapping(value = "/schedulers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Schedulers> getAllSchedulers() {
		List<Schedulers> listSchedulers = schedulersDao.findAll();
		return listSchedulers;
	}

}
