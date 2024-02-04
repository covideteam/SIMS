package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class PdfGenerationDetailsDto implements Serializable {
	
	private List<LanguageSpecificGlobalActivityDetails> gaList;
	private List<LanguageSpecificGlobalParameterDetails> gpList;
	private List<LanguageSpecificValueDetails> lspvdList;
	private Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlMap;
	private InternationalizaionLanguages inlag;
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	public List<LanguageSpecificGlobalActivityDetails> getGaList() {
		return gaList;
	}
	public List<LanguageSpecificGlobalParameterDetails> getGpList() {
		return gpList;
	}
	public List<LanguageSpecificValueDetails> getLspvdList() {
		return lspvdList;
	}
	public void setGaList(List<LanguageSpecificGlobalActivityDetails> gaList) {
		this.gaList = gaList;
	}
	public void setGpList(List<LanguageSpecificGlobalParameterDetails> gpList) {
		this.gpList = gpList;
	}
	public void setLspvdList(List<LanguageSpecificValueDetails> lspvdList) {
		this.lspvdList = lspvdList;
	}
	public Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> getControlMap() {
		return controlMap;
	}
	public void setControlMap(Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlMap) {
		this.controlMap = controlMap;
	}
	
	
	
	

}
