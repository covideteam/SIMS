package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class RulesValuesDto implements Serializable {
	
	private List<LanguageSpecificValueDetails> lspgvList;
	private LanguageSpecificGlobalParameterDetails gp;
	private InternationalizaionLanguages inlag;
	
	public List<LanguageSpecificValueDetails> getLspgvList() {
		return lspgvList;
	}
	public void setLspgvList(List<LanguageSpecificValueDetails> lspgvList) {
		this.lspgvList = lspgvList;
	}
	
	public LanguageSpecificGlobalParameterDetails getGp() {
		return gp;
	}
	public void setGp(LanguageSpecificGlobalParameterDetails gp) {
		this.gp = gp;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	
	

}
