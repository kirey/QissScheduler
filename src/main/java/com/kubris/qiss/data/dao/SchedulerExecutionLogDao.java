package com.kubris.qiss.data.dao;


import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kubris.qiss.data.entity.SchedulerExecutionLog;;

@Repository(value = "schedulerExecutionLogDao")
public class SchedulerExecutionLogDao extends BaseDao{
	
	
	public SchedulerExecutionLogDao() {
		log = LogFactory.getLog(SchedulerExecutionLog.class);
		entityClass=SchedulerExecutionLog.class;
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	

}
