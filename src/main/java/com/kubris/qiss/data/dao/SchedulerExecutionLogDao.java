package com.kubris.qiss.data.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kubris.qiss.data.entity.SchedulerExecutionLog;

@Repository(value = "schedulerExecutionLogDao")
public class SchedulerExecutionLogDao extends BaseDao {

	public SchedulerExecutionLogDao() {
		log = LogFactory.getLog(SchedulerExecutionLog.class);
		entityClass = SchedulerExecutionLog.class;
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<SchedulerExecutionLog> getLogsByJobId(int idJob) {

		String hql = "from SchedulerExecutionLog sel where sel.scheduler.id = :idJob";

		List<SchedulerExecutionLog> lista = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("idJob", idJob).list();
		return lista;
	}

	@Transactional
	public SchedulerExecutionLog getLatestLogByJob(String jobName) {

		String hql = "from SchedulerExecutionLog s " + " where s.startTimestamp in ( "
				+ "    select max(b.startTimestamp) " + "    from SchedulerExecutionLog b "
				+ "    where b.jobName= :jobName" + ")";

		return (SchedulerExecutionLog) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("jobName", jobName).uniqueResult();
	}

	public SchedulerExecutionLog getLogByName(String nameJob) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<SchedulerExecutionLog> query = builder.createQuery(SchedulerExecutionLog.class);

		Root<SchedulerExecutionLog> root = query.from(SchedulerExecutionLog.class);
		query.select(root).where(builder.equal(root.get("jobName"), nameJob));
		SchedulerExecutionLog log = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();

		return log;
	}

}
