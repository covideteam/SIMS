package com.covideinfo.dto;

import java.io.Serializable;

public class GlobalValuesDtoDetails implements Serializable {

	private static final long serialVersionUID = -8454086023526958394L;
	private String valueName;
	private long valueId;
	public String getValueName() {
		return valueName;
	}
	public long getValueId() {
		return valueId;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public void setValueId(long valueId) {
		this.valueId = valueId;
	}
	
	
}
