package com.kubris.documentManagement.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

@Entity
@Table(name = "artigiani")
public class Artigiani implements Serializable {

	private static final long serialVersionUID = -4143838677168131768L;
	
	private Integer id;
	private String firstName;
	private String lastName;
	
	private List<ArtigianiDocuments> artigianiDocumentses = new ArrayList<ArtigianiDocuments>();

	@Id
	@SequenceGenerator(name = "seq_artigiani_gen", sequenceName = "seq_artigiani", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_artigiani_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@OneToMany(mappedBy = "artigiani")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Fetch(FetchMode.SUBSELECT)
	public List<ArtigianiDocuments> getArtigianiDocumentses() {
		return artigianiDocumentses;
	}

	public void setArtigianiDocumentses(List<ArtigianiDocuments> artigianiDocumentses) {
		this.artigianiDocumentses = artigianiDocumentses;
	}
	
	
	
	

}
