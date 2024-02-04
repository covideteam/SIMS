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
@Table(name="study_subjects_periods")
public class StudySubjectPeriods implements Serializable {
	

	private static final long serialVersionUID = 7887747266528521622L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_subjects_periods_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="subject_id")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name="period_id")
	private StudyPeriodMaster periodId;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status; //New, Inprogress, Replace, Completed, Withdrawal, DropOut
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public StudyPeriodMaster getPeriodId() {
		return periodId;
	}
	public StatusMaster getStatus() {
		return status;
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
		public void setPeriodId(StudyPeriodMaster periodId) {
		this.periodId = periodId;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
}
