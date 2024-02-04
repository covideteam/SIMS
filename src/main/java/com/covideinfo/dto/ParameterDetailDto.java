package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class ParameterDetailDto implements Serializable {
	
	private static final long serialVersionUID = -2384131738390356614L;
	private String controlType;
	private List<GlobalValuesDtoDetails> gvdList;
	private Long lsvparamId;
	public String getControlType() {
		return controlType;
	}
	public List<GlobalValuesDtoDetails> getGvdList() {
		return gvdList;
	}
	public Long getLsvparamId() {
		return lsvparamId;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public void setGvdList(List<GlobalValuesDtoDetails> gvdList) {
		this.gvdList = gvdList;
	}
	public void setLsvparamId(Long lsvparamId) {
		this.lsvparamId = lsvparamId;
	}
		
	

}
