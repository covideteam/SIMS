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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="study_treatment_wise_subjects")
public class StudyTreatmentWiseSubjects implements Serializable {
	
	
	private static final long serialVersionUID = 6700477101589270091L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_treatment_wise_subjects_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="subjects")
	private String subjects;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster sm;
	@ManyToOne
	@JoinColumn(name="treatment")
	private TreatmentInfo treatment;
	@ManyToOne
	@JoinColumn(name="period")
	private StudyPeriodMaster period;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="update_reason")
	public String updateReason;
	
	public long getId() {
		return id;
	}
	public String getSubjects() {
		return subjects;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	

}
