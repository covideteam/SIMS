package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ParameterFormDataDto implements Serializable, Comparable<ParameterFormDataDto> {
	
	private String parameterName;
	private Long parameterId;
	private FormGroupsDto group;
	private Long groupId;
	private FromControlDto controlType;
	private Integer orderNo;
	private boolean mandatory;
	public String getParameterName() {// RB.SB,CB,
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public FormGroupsDto getGroup() {
		return group;
	}
	public void setGroup(FormGroupsDto group) {
		this.group = group;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public FromControlDto getControlType() {
		return controlType;
	}
	public void setControlType(FromControlDto controlType) {
		this.controlType = controlType;
	}
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public int compareTo(ParameterFormDataDto o) {
		return Integer.compare(this.orderNo, o.orderNo);
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	
	
	

}
