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
@Table(name="study_activity_data")
public class StudyActivityData implements Serializable {
	
	
	private static final long serialVersionUID = -7599479654754803915L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_activity_data_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="volunteer_id")
	private StudyVolunteerReporting volunteerId;
	@ManyToOne
	@JoinColumn(name="subject_id")
	private StudySubjects subjectId;
	@ManyToOne
	@JoinColumn(name="study_group_period")
	private StudyGroupPeriodMaster stdGoupPeriod;
	private String status="In-Review"; // start, sendcomment,approval
	private Long roleId;
	@ManyToOne
	@JoinColumn(name="study_activity")
	private StudyActivities sutydActivity;
	@Column(name="activity_start_date")
	private Date actvityStartDate;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="upddated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Column(name="reportStatus")
	private boolean reportStatus=false;// Only use for All subject activity
	
	
	public long getId() {
		return id;
	}
	public StudyVolunteerReporting getVolunteerId() {
		return volunteerId;
	}
	public StudySubjects getSubjectId() {
		return subjectId;
	}
	public StudyGroupPeriodMaster getStdGoupPeriod() {
		return stdGoupPeriod;
	}
	public String getStatus() {
		return status;
	}
	public StudyActivities getSutydActivity() {
		return sutydActivity;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setVolunteerId(StudyVolunteerReporting volunteerId) {
		this.volunteerId = volunteerId;
	}
	public void setSubjectId(StudySubjects subjectId) {
		this.subjectId = subjectId;
	}
	public void setStdGoupPeriod(StudyGroupPeriodMaster stdGoupPeriod) {
		this.stdGoupPeriod = stdGoupPeriod;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSutydActivity(StudyActivities sutydActivity) {
		this.sutydActivity = sutydActivity;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public boolean isReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(boolean reportStatus) {
		this.reportStatus = reportStatus;
	}
	public Date getActvityStartDate() {
		return actvityStartDate;
	}
	public void setActvityStartDate(Date actvityStartDate) {
		this.actvityStartDate = actvityStartDate;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	

}
