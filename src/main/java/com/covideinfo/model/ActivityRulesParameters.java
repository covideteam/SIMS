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
@Table(name="activity_rules_parameters")
public class ActivityRulesParameters implements Serializable {
	
	
	private static final long serialVersionUID = -6035688257759427327L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="activity_rules_parameters_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="activity_rule")
	private ActivityRules activityRule;
	@ManyToOne
	@JoinColumn(name="soucre_parameter")
	private GlobalParameter sourceParamter;
	@ManyToOne
	@JoinColumn(name="dest_parameter")
	private GlobalParameter destParameter;
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
	public void setId(long id) {
		this.id = id;
	}
	public ActivityRules getActivityRule() {
		return activityRule;
	}
	public void setActivityRule(ActivityRules activityRule) {
		this.activityRule = activityRule;
	}
	public GlobalParameter getSourceParamter() {
		return sourceParamter;
	}
	public void setSourceParamter(GlobalParameter sourceParamter) {
		this.sourceParamter = sourceParamter;
	}
	public GlobalParameter getDestParameter() {
		return destParameter;
	}
	public void setDestParameter(GlobalParameter destParameter) {
		this.destParameter = destParameter;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	
	
	
	
}
