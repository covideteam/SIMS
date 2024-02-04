package com.covideinfo.model;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects")
public class Projects {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="projects_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long projectId;
	@Column(name = "projectNo", unique = true, nullable = false)
	private String projectNo;
	@ManyToOne
	@JoinColumn(name="project_status")
	private StatusMaster status; 
	@ManyToOne
	@JoinColumn(name="buildStudyStatus")
	private StatusMaster buildStudyStatus; 
	@Column(name="roleId")
	private long roleId=0;//Approval Role Study (Current Approval Role)
	@ManyToOne
	@JoinColumn(name="randamizaion_status")
	private StatusMaster randamizationStatus;
	@Column(name="randamization_role")
	private Long randamizationRole;
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Transient
	private UserMaster loginUser;
	
	public UserMaster getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(UserMaster loginUser) {
		this.loginUser = loginUser;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
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
	public StatusMaster getBuildStudyStatus() {
		return buildStudyStatus;
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
	public void setBuildStudyStatus(StatusMaster buildStudyStatus) {
		this.buildStudyStatus = buildStudyStatus;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public StatusMaster getRandamizationStatus() {
		return randamizationStatus;
	}
	public Long getRandamizationRole() {
		return randamizationRole;
	}
	public void setRandamizationStatus(StatusMaster randamizationStatus) {
		this.randamizationStatus = randamizationStatus;
	}
	public void setRandamizationRole(Long randamizationRole) {
		this.randamizationRole = randamizationRole;
	}
	
	
	
	
}
