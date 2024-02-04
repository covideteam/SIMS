package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;

@SuppressWarnings("serial")
public class RulesDetailsDto implements Serializable {
	
	private List<LanguageSpecificGlobalActivityDetails> actList;
	private List<InternationalizaionLanguages> inLagList;
	public List<LanguageSpecificGlobalActivityDetails> getActList() {
		return actList;
	}
	public void setActList(List<LanguageSpecificGlobalActivityDetails> actList) {
		this.actList = actList;
	}
	public List<InternationalizaionLanguages> getInLagList() {
		return inLagList;
	}
	public void setInLagList(List<InternationalizaionLanguages> inLagList) {
		this.inLagList = inLagList;
	}
	
	

}
