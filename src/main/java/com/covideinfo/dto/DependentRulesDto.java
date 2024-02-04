package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DependentRulesDto implements Serializable {
	
	private String sourceActivityName;
	private Long sourceActivityId;
	private String ruleType;
	private String dependentApplyFor;
	private String destinationActivityName;
	private Long destinationActivityId;
	private String sourceParameterName;
	private Long sourceParameterId;
	private String controlType;
	private String controlTypeVal;
	private Long controlTypeLspId;
	private String destinationcontrolType;
	private String destinationcontrolTypeVal;
	private Long destinationcontrolTypeLspId;
	private String condition;
	private String paramterAction;
	private Long destinationParameterId;
	private String destinationParameterName;
	private String enableOrDiableParameterIds;
	private String enableOrDiableParameterNames;
	private String fromRange;
	private String toRange;
	private String conditionValue;
	private String destParameterCondition;
	private String destParamTbVal;
	private String keyName;
	private String keyValue;
	public String getSourceActivityName() {
		return sourceActivityName;
	}
	public void setSourceActivityName(String sourceActivityName) {
		this.sourceActivityName = sourceActivityName;
	}
	public Long getSourceActivityId() {
		return sourceActivityId;
	}
	public void setSourceActivityId(Long sourceActivityId) {
		this.sourceActivityId = sourceActivityId;
	}
	public String getDestinationActivityName() {
		return destinationActivityName;
	}
	public void setDestinationActivityName(String destinationActivityName) {
		this.destinationActivityName = destinationActivityName;
	}
	public Long getDestinationActivityId() {
		return destinationActivityId;
	}
	public void setDestinationActivityId(Long destinationActivityId) {
		this.destinationActivityId = destinationActivityId;
	}
	public String getSourceParameterName() {
		return sourceParameterName;
	}
	public void setSourceParameterName(String sourceParameterName) {
		this.sourceParameterName = sourceParameterName;
	}
	public Long getSourceParameterId() {
		return sourceParameterId;
	}
	public void setSourceParameterId(Long sourceParameterId) {
		this.sourceParameterId = sourceParameterId;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getControlTypeVal() {
		return controlTypeVal;
	}
	public void setControlTypeVal(String controlTypeVal) {
		this.controlTypeVal = controlTypeVal;
	}
	public String getCondition() {
		return condition;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Long getControlTypeLspId() {
		return controlTypeLspId;
	}
	public void setControlTypeLspId(Long controlTypeLspId) {
		this.controlTypeLspId = controlTypeLspId;
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
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	public Long getDestinationParameterId() {
		return destinationParameterId;
	}
	public void setDestinationParameterId(Long destinationParameterId) {
		this.destinationParameterId = destinationParameterId;
	}
	public String getDestinationParameterName() {
		return destinationParameterName;
	}
	public void setDestinationParameterName(String destinationParameterName) {
		this.destinationParameterName = destinationParameterName;
	}
	public String getEnableOrDiableParameterIds() {
		return enableOrDiableParameterIds;
	}
	public void setEnableOrDiableParameterIds(String enableOrDiableParameterIds) {
		this.enableOrDiableParameterIds = enableOrDiableParameterIds;
	}
	public String getParamterAction() {
		return paramterAction;
	}
	public void setParamterAction(String paramterAction) {
		this.paramterAction = paramterAction;
	}
	public String getEnableOrDiableParameterNames() {
		return enableOrDiableParameterNames;
	}
	public void setEnableOrDiableParameterNames(String enableOrDiableParameterNames) {
		this.enableOrDiableParameterNames = enableOrDiableParameterNames;
	}
	public String getDestinationcontrolTypeVal() {
		return destinationcontrolTypeVal;
	}
	public Long getDestinationcontrolTypeLspId() {
		return destinationcontrolTypeLspId;
	}
	public void setDestinationcontrolTypeVal(String destinationcontrolTypeVal) {
		this.destinationcontrolTypeVal = destinationcontrolTypeVal;
	}
	public void setDestinationcontrolTypeLspId(Long destinationcontrolTypeLspId) {
		this.destinationcontrolTypeLspId = destinationcontrolTypeLspId;
	}
	public String getDestinationcontrolType() {
		return destinationcontrolType;
	}
	public void setDestinationcontrolType(String destinationcontrolType) {
		this.destinationcontrolType = destinationcontrolType;
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
	public String getDestParameterCondition() {
		return destParameterCondition;
	}
	public void setDestParameterCondition(String destParameterCondition) {
		this.destParameterCondition = destParameterCondition;
	}
	public String getDependentApplyFor() {
		return dependentApplyFor;
	}
	public void setDependentApplyFor(String dependentApplyFor) {
		this.dependentApplyFor = dependentApplyFor;
	}
	public String getDestParamTbVal() {
		return destParamTbVal;
	}
	public void setDestParamTbVal(String destParamTbVal) {
		this.destParamTbVal = destParamTbVal;
	}
	
	
	
	
	
	
	
	
	
	

}
