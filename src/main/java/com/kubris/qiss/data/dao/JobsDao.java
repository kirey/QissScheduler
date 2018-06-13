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
import com.kubris.qiss.data.entity.Jobs;

@Repository(value = "jobsDao")
public class JobsDao extends BaseDao{
	
	public JobsDao() {
		log = LogFactory.getLog(JobsDao.class);
		entityClass=Jobs.class;
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Jobs findByJobName(String jobName) {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Jobs> query = builder.createQuery(Jobs.class);
        
        Root<Jobs> root = query.from(Jobs.class);
        query.select(root).where(builder.equal(root.get("jobName"), jobName));
        Jobs scheduler = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
       		
		return scheduler;
		
	}

}
