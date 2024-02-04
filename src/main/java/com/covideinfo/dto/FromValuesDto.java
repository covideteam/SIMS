package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FromValuesDto implements Serializable {
	
	private Long valueId;
	private String valueName;
	private int order;
	public Long getValueId() {
		return valueId;
	}
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	

}
