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
@Table(name="study_subject_re_cannulation_data")
public class StudySubjectReCannulationData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 923961990145137250L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_subject_re_cannulation_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster studyId;
	@ManyToOne
	@JoinColumn(name="sample_timepoint")
	private SampleTimePoints sampleTimePoint;
	@ManyToOne
	@JoinColumn(name="subject")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name="period")
	private StudyPeriodMaster period;
	@Column(name="re_cannulation")
	private boolean reCannulation;
	@Column(name="cannule_removed")
	private boolean cannuleRemoved;
	@Column(name="reason", length=10000)
	private String reason;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="updated_reason")
	private String updatedReason;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyMaster getStudyId() {
		return studyId;
	}
	public void setStudyId(StudyMaster studyId) {
		this.studyId = studyId;
	}
	public SampleTimePoints getSampleTimePoint() {
		return sampleTimePoint;
	}
	public void setSampleTimePoint(SampleTimePoints sampleTimePoint) {
		this.sampleTimePoint = sampleTimePoint;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public boolean isReCannulation() {
		return reCannulation;
	}
	public void setReCannulation(boolean reCannulation) {
		this.reCannulation = reCannulation;
	}
	public boolean isCannuleRemoved() {
		return cannuleRemoved;
	}
	public void setCannuleRemoved(boolean cannuleRemoved) {
		this.cannuleRemoved = cannuleRemoved;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public String getUpdatedReason() {
		return updatedReason;
	}
	public void setUpdatedReason(String updatedReason) {
		this.updatedReason = updatedReason;
	}
	

}
