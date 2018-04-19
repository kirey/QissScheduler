package com.kubris.documentManagement.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "artigiani_documents")
public class ArtigianiDocuments implements Serializable {

	private static final long serialVersionUID = 8777344080170557890L;
	
	private Integer id;
	private DicArtigianiDocumentTypes documentType;
	private Artigiani artigiani;
	byte[] documentFile;
	
	@Id
	@SequenceGenerator(name = "seq_artigiani_documents_gen", sequenceName = "seq_artigiani_documents", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_artigiani_documents_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "document_type")
	public DicArtigianiDocumentTypes getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DicArtigianiDocumentTypes documentType) {
		this.documentType = documentType;
	}
	
	@ManyToOne
	@JoinColumn(name = "artigiani")
	public Artigiani getArtigiani() {
		return artigiani;
	}
	public void setArtigiani(Artigiani artigiani) {
		this.artigiani = artigiani;
	}
	
	//@Lob // in case of use postgres database remove @Lob annotation
	@Column(name = "document_file", nullable = false)
	public byte[] getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(byte[] documentFile) {
		this.documentFile = documentFile;
	}
	
	
	
	

}
