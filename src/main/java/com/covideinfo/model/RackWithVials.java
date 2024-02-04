package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name = "Rack_With_Vials")
public class RackWithVials implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8507919953711215L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "Rack_With_Vials_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "rackWithVialsId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	private String sampleTimePoint;
	@ManyToOne
	@JoinColumn(name = "reackId")
	private Deepfreezer reack;
	private int vialNo;
	private Date rackScanTime;
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private UserMaster createdBy;
	private Date createdOn;
	@Transient
	private List<RackWithVitalSubject> subjects;

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
	public String getSampleTimePoint() {
		return sampleTimePoint;
	}
	public void setSampleTimePoint(String sampleTimePoint) {
		this.sampleTimePoint = sampleTimePoint;
	}
	public Deepfreezer getReack() {
		return reack;
	}
	public void setReack(Deepfreezer reack) {
		this.reack = reack;
	}
	public int getVialNo() {
		return vialNo;
	}
	public void setVialNo(int vialNo) {
		this.vialNo = vialNo;
	}
	
	public Date getRackScanTime() {
		return rackScanTime;
	}
	public void setRackScanTime(Date rackScanTime) {
		this.rackScanTime = rackScanTime;
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
	public List<RackWithVitalSubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<RackWithVitalSubject> subjects) {
		this.subjects = subjects;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	

	
	
}
