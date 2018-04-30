package com.kubris.qiss.data.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	@Transactional
	public Schedulers findByJobName(String jobName) {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Schedulers> query = builder.createQuery(Schedulers.class);
        
        Root<Schedulers> root = query.from(Schedulers.class);
        query.select(root).where(builder.equal(root.get("jobName"), jobName));
        Schedulers scheduler = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
       		
		return scheduler;
		
	}

}
