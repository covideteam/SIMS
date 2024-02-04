package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name="Sample_Container")
public class SampleContainer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8249751436840056128L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Sample_Container_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "studySubjectsId")
	private StudySubjects subject;
	private int vialNo;
	private String cleanArea;
	private String dataLogger;
	private String aliquot;
	private String allSubject;
	private String subjects;
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private UserMaster createdBy;
	private Date createdOn;
	@Transient
	private List<SampleContainerVials> vialsList;
	
	public List<SampleContainerVials> getVialsList() {
		return vialsList;
	}
	public void setVialsList(List<SampleContainerVials> vialsList) {
		this.vialsList = vialsList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public int getVialNo() {
		return vialNo;
	}
	public void setVialNo(int vialNo) {
		this.vialNo = vialNo;
	}
	public String getCleanArea() {
		return cleanArea;
	}
	public void setCleanArea(String cleanArea) {
		this.cleanArea = cleanArea;
	}
	public String getDataLogger() {
		return dataLogger;
	}
	public void setDataLogger(String dataLogger) {
		this.dataLogger = dataLogger;
	}
	public String getAliquot() {
		return aliquot;
	}
	public void setAliquot(String aliquot) {
		this.aliquot = aliquot;
	}
	
	public String getAllSubject() {
		return allSubject;
	}
	public void setAllSubject(String allSubject) {
		this.allSubject = allSubject;
	}
	public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
}
