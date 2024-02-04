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
@Table(name="study_volunteer_reporting")
public class StudyVolunteerReporting implements Serializable {
	
	
	private static final long serialVersionUID = 5284385029940416637L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_volunteer_reporting_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster sm;
	@Column(name="volunteer_id")
	private String volunteerId;
	@ManyToOne
	@JoinColumn(name="gender_id")
	private LanguageSpecificValueDetails genderId;
    @Column(name="subject_no")
	private String subjectNo;
	@ManyToOne
	@JoinColumn(name="period")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name="statusId")
	private StatusMaster status;
	@Column(name="comments")
	private String comments;
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
	public StudyMaster getSm() {
		return sm;
	}
	public String getVolunteerId() {
		return volunteerId;
	}
	
	public String getComments() {
		return comments;
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
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}
	
	public LanguageSpecificValueDetails getGenderId() {
		return genderId;
	}
	public void setGenderId(LanguageSpecificValueDetails genderId) {
		this.genderId = genderId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	
	
}
