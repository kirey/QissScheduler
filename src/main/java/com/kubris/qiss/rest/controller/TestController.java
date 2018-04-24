package com.kubris.qiss.rest.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubris.qiss.data.dao.SchedulersDao;
import com.kubris.qiss.data.entity.Schedulers;
import com.kubris.qiss.soap.wsdl.EngineServices;
import com.kubris.qiss.soap.wsdl.EngineServicesService;
import com.kubris.qiss.soap.wsdl.ReqPlanDescriptionForTestQuery;
import com.kubris.qiss.soap.wsdl.RspReqPlanDescriptionForTestQuery;

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
	
	@RequestMapping(value = "/soap", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testSoap() {
		this.reqExecTestQuery();
		return "SOAP call performed";
	}

	private void reqExecTestQuery() {
		EngineServicesService esService = new EngineServicesService();
		EngineServices engineServices = esService.getEngineServicesPort();
		
		BindingProvider bindingProvider = (BindingProvider) engineServices;
		URL url = null;
		try {
			url = new URL("file:///c:/fielqengine.wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "file:/C:/fielqengine.wsdl");
		
		ReqPlanDescriptionForTestQuery reqPlanDTQuery = new ReqPlanDescriptionForTestQuery();
		reqPlanDTQuery.setArg0("select 1");
		
		RspReqPlanDescriptionForTestQuery rspPlanDTQuery = engineServices.reqPlanDescriptionForTestQuery(reqPlanDTQuery.getArg0());
		
		System.out.println(rspPlanDTQuery.getPlanDebugLog());
		System.out.println(rspPlanDTQuery.getPlanDescription());
		System.out.println(rspPlanDTQuery.getReplyCode());
		System.out.println(rspPlanDTQuery.getReplyDescription());
	}

}
