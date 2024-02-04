package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

@SuppressWarnings("serial")
public class StudyDesingDto implements Serializable {
	
	private List<GlobalParameter> gpList;
	private GlobalActivity activity;
	private  List<LanguageSpecificGlobalParameterDetails> lsgpdList;
	public List<GlobalParameter> getGpList() {
		return gpList;
	}
	public void setGpList(List<GlobalParameter> gpList) {
		this.gpList = gpList;
	}
	public GlobalActivity getActivity() {
		return activity;
	}
	public void setActivity(GlobalActivity activity) {
		this.activity = activity;
	}
	public List<LanguageSpecificGlobalParameterDetails> getLsgpdList() {
		return lsgpdList;
	}
	public void setLsgpdList(List<LanguageSpecificGlobalParameterDetails> lsgpdList) {
		this.lsgpdList = lsgpdList;
	}
	
	

}
