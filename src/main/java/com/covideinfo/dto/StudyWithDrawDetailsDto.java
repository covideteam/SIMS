package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;

@SuppressWarnings("serial")
public class StudyWithDrawDetailsDto implements Serializable {
	
	private List<StaticActivityDetails> actList;
	private List<StaticActivityValueDetails> actValList;
	public List<StaticActivityDetails> getActList() {
		return actList;
	}
	public void setActList(List<StaticActivityDetails> actList) {
		this.actList = actList;
	}
	public List<StaticActivityValueDetails> getActValList() {
		return actValList;
	}
	public void setActValList(List<StaticActivityValueDetails> actValList) {
		this.actValList = actValList;
	}
	
	
	
	
	

}
