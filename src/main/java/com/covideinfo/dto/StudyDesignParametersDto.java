package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StudyDesignParametersDto implements Serializable, Comparable<StudyDesignParametersDto> {
	
	private Long parameterId;
	private String parameterName;
	private Integer orderNo;
	private boolean mandatory;
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public int compareTo(StudyDesignParametersDto o) {
		return this.getOrderNo().compareTo(o.getOrderNo());
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	

}
