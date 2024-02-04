package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

@SuppressWarnings("serial")
public class InclusionDto implements Serializable {
	private List<LanguageSpecificGlobalParameterDetails> lspgpList;
	private LanguageSpecificGlobalActivityDetails lsga;
	private InternationalizaionLanguages inlag;
	public List<LanguageSpecificGlobalParameterDetails> getLspgpList() {
		return lspgpList;
	}
	public LanguageSpecificGlobalActivityDetails getLsga() {
		return lsga;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setLspgpList(List<LanguageSpecificGlobalParameterDetails> lspgpList) {
		this.lspgpList = lspgpList;
	}
	public void setLsga(LanguageSpecificGlobalActivityDetails lsga) {
		this.lsga = lsga;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	

}
