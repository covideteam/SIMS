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
@Table(name="global_activity")
public class GlobalActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2333419482907894535L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="global_activity_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="actvity_code")
	private String activityCode;
	@Column(name="allowMultiple")
	private String allowMultiple;
	@ManyToOne
	@JoinColumn(name="group_id")
	private GlobalGroups groupId;
	@Column(name="saveUrl")
	private String saveUrl="0";
	@Column(name="role_ids")
	private String roleIds;
	@Column(name="orderNo")
	private int orderNo;
	@Column(name="headding")
	private boolean headding;
	
	@Column(name="showInPDF")
	private boolean showInPDF;
	
	@Column(name="subjectsInput")
	private boolean subjectsInput;
	
	
	@Column(name="getUrl")
	private String getUrl="0";
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="rejectUrl")
	private String rejectUrl="0";
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	@Column(columnDefinition="BOOLEAN DEFAULT false")
	private boolean eligibleForNextProcess;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAllowMultiple() {
		return allowMultiple;
	}
	public String getSaveUrl() {
		return saveUrl;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public void setAllowMultiple(String allowMultiple) {
		this.allowMultiple = allowMultiple;
	}
	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public GlobalGroups getGroupId() {
		return groupId;
	}
	public void setGroupId(GlobalGroups groupId) {
		this.groupId = groupId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public boolean isHeadding() {
		return headding;
	}
	public void setHeadding(boolean headding) {
		this.headding = headding;
	}
	public String getRejectUrl() {
		return rejectUrl;
	}
	public void setRejectUrl(String rejectUrl) {
		this.rejectUrl = rejectUrl;
	}
	
	public boolean isShowInPDF() {
		return showInPDF;
	}
	public void setShowInPDF(boolean showInPDFVal) {
		this.showInPDF = showInPDFVal;
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
	public boolean isSubjectsInput() {
		return subjectsInput;
	}
	public void setSubjectsInput(boolean subjectsInput) {
		this.subjectsInput = subjectsInput;
	}
	public boolean isEligibleForNextProcess() {
		return eligibleForNextProcess;
	}
	public void setEligibleForNextProcess(boolean eligibleForNextProcess) {
		this.eligibleForNextProcess = eligibleForNextProcess;
	}
	
	
}
