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
@Table(name = "StudyActivityData_ReviewAudit")
public class StudyActivityDataReviewAudit implements Serializable{
	
	
	private static final long serialVersionUID = -8163057407136449756L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="ACTIVITY_DRAFT_REVIEW_AUDIT_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyActivityDataId")
	private StudyActivityData studyActivityDataId;
/*	@ManyToOne
	@JoinColumn(name = "workFlowReviewStagesId")
	private WorkFlowReviewStages workFlowReviewStages;*/
	private long reviewState;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleMaster role;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserMaster user;
	@Column(name="date_of_activity")
	private Date dateOfActivity;
	private String comment;
	private String status = "";
	@Column(name="in_the_flow")
	private boolean inTheFlow = true;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	public boolean isInTheFlow() {
		return inTheFlow;
	}
	public void setInTheFlow(boolean inTheFlow) {
		this.inTheFlow = inTheFlow;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public StudyActivityData getStudyActivityDataId() {
		return studyActivityDataId;
	}
	public void setStudyActivityDataId(StudyActivityData studyActivityDataId) {
		this.studyActivityDataId = studyActivityDataId;
	}
	/*public WorkFlowReviewStages getWorkFlowReviewStages() {
		return workFlowReviewStages;
	}
	public void setWorkFlowReviewStages(WorkFlowReviewStages workFlowReviewStages) {
		this.workFlowReviewStages = workFlowReviewStages;
	}*/
	public long getReviewState() {
		return reviewState;
	}
	public void setReviewState(long reviewState) {
		this.reviewState = reviewState;
	}
	public RoleMaster getRole() {
		return role;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	
	public Date getDateOfActivity() {
		return dateOfActivity;
	}
	public void setDateOfActivity(Date dateOfActivity) {
		this.dateOfActivity = dateOfActivity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	
}
