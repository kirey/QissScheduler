package com.kubris.qiss.data.entity;

import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "schedulers")
public class Schedulers implements Serializable {

	
	private static final long serialVersionUID = 1703338942103035773L;

	private int id;

	private String jobName;

	private String cronExpression;

	private String status;

	@JsonBackReference
	List<SchedulerExecutionLog> schedulerExecutionLogs = new ArrayList<>();

	@Id
	@SequenceGenerator(name = "seq_schedulers_gen", sequenceName = "seq_schedulers", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedulers_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "job_name", nullable = false)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "cron_expression", nullable = false)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@OneToMany(mappedBy = "scheduler",cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Fetch(FetchMode.SUBSELECT)
	public List<SchedulerExecutionLog> getSchedulerExecutionLogs() {
		return schedulerExecutionLogs;
	}

	public void setSchedulerExecutionLogs(List<SchedulerExecutionLog> schedulerExecutionLogs) {
		this.schedulerExecutionLogs = schedulerExecutionLogs;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
