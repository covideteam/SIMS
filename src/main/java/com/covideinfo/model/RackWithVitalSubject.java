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

@Entity
@Table(name = "Rack_With_Vials_Subject")
public class RackWithVitalSubject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4339713787045323295L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "Rack_With_Vials_subjec__id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "rackWithVitalSubjectId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "rackWithVialsId")
	private RackWithVials rackWithVials;
	@ManyToOne
	@JoinColumn(name = "studySubjectsId")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name = "timePointId")
	private SampleTimePoints timePoint;
	private String vialBarcode;
	private Date scanTime;
	
	
	public SampleTimePoints getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(SampleTimePoints timePoint) {
		this.timePoint = timePoint;
	}
	public String getVialBarcode() {
		return vialBarcode;
	}
	public void setVialBarcode(String vialBarcode) {
		this.vialBarcode = vialBarcode;
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
	public RackWithVials getRackWithVials() {
		return rackWithVials;
	}
	public void setRackWithVials(RackWithVials rackWithVials) {
		this.rackWithVials = rackWithVials;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
	
}
