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
@Table(name = "ACTIVITY_DRAFT_REVIEW_AUDIT")
public class ActivityDraftReviewAudit implements Serializable {
	
	private static final long serialVersionUID = 6455265942770808097L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="ACTIVITY_DRAFT_REVIEW_AUDIT_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	/*@ManyToOne
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
	@Column(name="projectId")
	private long projectId;
	@Column(name="in_the_flow")
	private boolean inTheFlow = true;
	@ManyToOne
    @JoinColumn(name="created_by")
    private UserMaster createdBy;
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
	
	public long getReviewState() {
		return reviewState;
	}
	public RoleMaster getRole() {
		return role;
	}
	public UserMaster getUser() {
		return user;
	}
	public Date getDateOfActivity() {
		return dateOfActivity;
	}
	public String getComment() {
		return comment;
	}
	public String getStatus() {
		return status;
	}
	public boolean isInTheFlow() {
		return inTheFlow;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void setReviewState(long reviewState) {
		this.reviewState = reviewState;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public void setDateOfActivity(Date dateOfActivity) {
		this.dateOfActivity = dateOfActivity;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setInTheFlow(boolean inTheFlow) {
		this.inTheFlow = inTheFlow;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
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
