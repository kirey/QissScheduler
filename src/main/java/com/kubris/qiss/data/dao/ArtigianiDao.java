package com.kubris.qiss.data.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kubris.qiss.data.entity.Artigiani;


/**
 * @author paunovicm
 *
 */

@Repository(value = "artigianiDao")
public class ArtigianiDao extends BaseDao{
	
	public ArtigianiDao() {
		log = LogFactory.getLog(ArtigianiDao.class);
		entityClass=Artigiani.class;
	}
	
	@Autowired
	private SessionFactory sessionFactory;

}
