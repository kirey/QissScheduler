package com.kubris.qiss.data.dao;


import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kubris.qiss.data.entity.SchedulerExecutionLog;
import com.kubris.qiss.data.entity.Schedulers;;

@Repository(value = "schedulerExecutionLogDao")
public class SchedulerExecutionLogDao extends BaseDao{
	
	
	public SchedulerExecutionLogDao() {
		log = LogFactory.getLog(SchedulerExecutionLog.class);
		entityClass=SchedulerExecutionLog.class;
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Transactional
	public List<SchedulerExecutionLog> getLogsByJobId(int idJob){
	    
		String hql = "from SchedulerExecutionLog sel where sel.scheduler.id = :idJob";
		
		List<SchedulerExecutionLog> lista = sessionFactory.getCurrentSession().createQuery(hql).setParameter("idJob", idJob).list();
		return lista;	
	}
	

	

}
