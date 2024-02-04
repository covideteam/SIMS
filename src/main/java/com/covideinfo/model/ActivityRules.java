package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="activity_rules")
public class ActivityRules implements Serializable {

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="activity_rules_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="rule_type")
	private String ruleType;
	@Column(name="source_activity")
	private Long sourceActivity;
	@Column(name="dest_activity")
	private Long destActivity;
	@Column(name="rule")
	private String rule;
	@Column(name="value")
	private String value;
	@Column(name="rule_message")
	private String ruleMessage;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date cratedOn;
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
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public Long getSourceActivity() {
		return sourceActivity;
	}
	public void setSourceActivity(Long sourceActivity) {
		this.sourceActivity = sourceActivity;
	}
	public Long getDestActivity() {
		return destActivity;
	}
	public void setDestActivity(Long destActivity) {
		this.destActivity = destActivity;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRuleMessage() {
		return ruleMessage;
	}
	public void setRuleMessage(String ruleMessage) {
		this.ruleMessage = ruleMessage;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCratedOn() {
		return cratedOn;
	}
	public void setCratedOn(Date cratedOn) {
		this.cratedOn = cratedOn;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
