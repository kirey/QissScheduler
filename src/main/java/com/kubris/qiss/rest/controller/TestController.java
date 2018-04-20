package com.kubris.qiss.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.ArtigianiDao;
import com.kubris.qiss.data.entity.Artigiani;

@RestController
@RequestMapping("/rest")
public class TestController {

	
	@Autowired
	private ArtigianiDao artigianiDao;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value = "/testSecurity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testSecurity() {
		return "test";
	}
	
	@RequestMapping(value = "/artigiani", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Artigiani> getAllArtigiani() {
		List<Artigiani> listArtigiani = artigianiDao.findAll();
		return listArtigiani;
	}

}
