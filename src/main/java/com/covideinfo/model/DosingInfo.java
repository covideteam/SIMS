package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "dosing_Info")
public class DosingInfo  implements Serializable {

	private static final long serialVersionUID = -2451398185189312131L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "dosing_Info_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "dosingInfoId")
	private long id;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projects;
	
	@Column(name="dosingDate")
	private Date dosingDate;

	@Column(name="dsdifferenceBetweenSubjects")
	private int dsdifferenceBetweenSubjects;
	@Column(name="no_of_stations")
	private int noOfStations;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	
	@Transient
	private String doseDateStr;
	
	@Transient
	private String doseTimeStr;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDosingDate() {
		return dosingDate;
	}
	public void setDosingDate(Date dosingDate) {
		this.dosingDate = dosingDate;
	}
	
	public int getDsdifferenceBetweenSubjects() {
		return dsdifferenceBetweenSubjects;
	}
	public void setDsdifferenceBetweenSubjects(int dsdifferenceBetweenSubjects) {
		this.dsdifferenceBetweenSubjects = dsdifferenceBetweenSubjects;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Projects getProjects() {
		return projects;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
	
	public int getNoOfStations() {
		return noOfStations;
	}
	public void setNoOfStations(int noOfStations) {
		this.noOfStations = noOfStations;
	}
	public String getDoseDateStr() {
		return doseDateStr;
	}
	public String getDoseTimeStr() {
		return doseTimeStr;
	}
	public void setDoseDateStr(String doseDateStr) {
		this.doseDateStr = doseDateStr;
	}
	public void setDoseTimeStr(String doseTimeStr) {
		this.doseTimeStr = doseTimeStr;
	}
	
	
	
}
