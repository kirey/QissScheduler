package com.kubris.qiss.data.dao;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kubris.qiss.data.entity.Schedulers;

@Repository(value = "schedulersDao")
public class SchedulersDao extends BaseDao{
	
	public SchedulersDao() {
		log = LogFactory.getLog(SchedulersDao.class);
		entityClass=Schedulers.class;
	}
	
	@Autowired
	private SessionFactory sessionFactory;

}
