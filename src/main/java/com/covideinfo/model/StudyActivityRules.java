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
@Table(name = "study_activity_rules")
public class StudyActivityRules implements Serializable {

	
	private static final long serialVersionUID = -7822174835775474206L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_activity_rules_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name = "rule_type")
	private String ruleType;
	@Column(name = "condition")
	private String condition;
	@Column(name = "con_firstval")
	private String conFirstVal;
	@Column(name = "con_secondval")
	private String conSecondVal;
	@ManyToOne
	@JoinColumn(name = "source_activity")
	private GlobalActivity sourceActivity;
	@ManyToOne
	@JoinColumn(name = "source_parameter")
	private GlobalParameter sourceParameter;
	@ManyToOne
	@JoinColumn(name = "dest_activity")
	private GlobalActivity destinationActivity;
	@Column(name="prameter_global_lsp_id")
	private Long parameterGlobalLspId;
	@Column(name="dest_parameter_global_val")
	private String destparameterGloablVal;
	@Column(name="dest_prameter_global_lsp_id")
	private Long destparameterGlobalLspId;
	@Column(name="parameter_action")
	private String parameterAction;
	@Column(name="multi_param")
	private String multiParam;
	@Column(name="from_range")
	private String fromRange;
	@Column(name="to_range")
	private String toRange;
	@Column(name="tb_condition")
	private String tbCondition;
	@Column(name="dependent_apply_for")
	private String dependentapplyFor;
	@Column(name="deptbvalue")
	private String deptbvalue;
	@Column(name="depndent_activities")
	private String dependentActivities;
	@ManyToOne
	@JoinColumn(name = "dest_parameter")
	private GlobalParameter destParameter;
	@Column(name="dest_parm_condition")
	private String destParamCodntiion;
	@ManyToOne
	@JoinColumn(name="global_val_id")
	private GlobalValues globalValId;
	@Column(name="key_name")
	private String keyName;
	@Column(name="key_value")
	private String keyValue;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_on")
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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public GlobalActivity getSourceActivity() {
		return sourceActivity;
	}

	public void setSourceActivity(GlobalActivity sourceActivity) {
		this.sourceActivity = sourceActivity;
	}

	public GlobalParameter getSourceParameter() {
		return sourceParameter;
	}

	public void setSourceParameter(GlobalParameter sourceParameter) {
		this.sourceParameter = sourceParameter;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConFirstVal() {
		return conFirstVal;
	}

	public void setConFirstVal(String conFirstVal) {
		this.conFirstVal = conFirstVal;
	}

	public String getConSecondVal() {
		return conSecondVal;
	}

	public void setConSecondVal(String conSecondVal) {
		this.conSecondVal = conSecondVal;
	}

	public GlobalActivity getDestinationActivity() {
		return destinationActivity;
	}

	public void setDestinationActivity(GlobalActivity destinationActivity) {
		this.destinationActivity = destinationActivity;
	}

	
	public Long getParameterGlobalLspId() {
		return parameterGlobalLspId;
	}

	public void setParameterGlobalLspId(Long parameterGlobalLspId) {
		this.parameterGlobalLspId = parameterGlobalLspId;
	}

	public String getParameterAction() {
		return parameterAction;
	}

	public void setParameterAction(String parameterAction) {
		this.parameterAction = parameterAction;
	}

	public String getMultiParam() {
		return multiParam;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public GlobalValues getGlobalValId() {
		return globalValId;
	}

	public void setGlobalValId(GlobalValues globalValId) {
		this.globalValId = globalValId;
	}

	public String getDependentActivities() {
		return dependentActivities;
	}

	public void setDependentActivities(String dependentActivities) {
		this.dependentActivities = dependentActivities;
	}

	public GlobalParameter getDestParameter() {
		return destParameter;
	}

	public void setDestParameter(GlobalParameter destParameter) {
		this.destParameter = destParameter;
	}

	public void setMultiParam(String multiParam) {
		this.multiParam = multiParam;
	}

	public String getFromRange() {
		return fromRange;
	}

	public void setFromRange(String fromRange) {
		this.fromRange = fromRange;
	}

	public String getToRange() {
		return toRange;
	}

	public void setToRange(String toRange) {
		this.toRange = toRange;
	}

	public String getDestparameterGloablVal() {
		return destparameterGloablVal;
	}

	public Long getDestparameterGlobalLspId() {
		return destparameterGlobalLspId;
	}

	public void setDestparameterGloablVal(String destparameterGloablVal) {
		this.destparameterGloablVal = destparameterGloablVal;
	}

	public void setDestparameterGlobalLspId(Long destparameterGlobalLspId) {
		this.destparameterGlobalLspId = destparameterGlobalLspId;
	}

	public String getDestParamCodntiion() {
		return destParamCodntiion;
	}

	public void setDestParamCodntiion(String destParamCodntiion) {
		this.destParamCodntiion = destParamCodntiion;
	}

	public String getDeptbvalue() {
		return deptbvalue;
	}

	public void setDeptbvalue(String deptbvalue) {
		this.deptbvalue = deptbvalue;
	}

	public String getTbCondition() {
		return tbCondition;
	}

	public void setTbCondition(String tbCondition) {
		this.tbCondition = tbCondition;
	}

	public String getDependentapplyFor() {
		return dependentapplyFor;
	}

	public void setDependentapplyFor(String dependentapplyFor) {
		this.dependentapplyFor = dependentapplyFor;
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
