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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="userwise_assignstudies_master")
public class UserWiseStudiesAsignMaster implements Serializable, Comparable<UserWiseStudiesAsignMaster>{

	
	private static final long serialVersionUID = 2425511857404364563L;

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="userwise_assignstudies_master_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="userWiseStudiesAsignMasterId")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="userId")
	private UserMaster userId;
	@Column(name="study_roles")
	private String studyRoles;
	private boolean status = true;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="update_reason")
	public String updateReason;
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


	@Column(name="updated_on")
	private Date updatedOn;
	@Transient
	private List<Long> rolesList;
	
	
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


	public UserMaster getUserId() {
		return userId;
	}


	public void setUserId(UserMaster userId) {
		this.userId = userId;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public int compareTo(UserWiseStudiesAsignMaster o) {
		// TODO Auto-generated method stub
		return (int) (this.getId()-o.getId());
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


	public String getStudyRoles() {
		return studyRoles;
	}


	public void setStudyRoles(String studyRoles) {
		this.studyRoles = studyRoles;
	}


	public List<Long> getRolesList() {
		return rolesList;
	}


	public void setRolesList(List<Long> rolesList) {
		this.rolesList = rolesList;
	}


	public String getUpdateReason() {
		return updateReason;
	}


	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
}