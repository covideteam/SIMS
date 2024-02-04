package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

@SuppressWarnings("serial")
public class ProjectDetailsPdfGenerationDto implements Serializable {
	
	private Map<Long, LanguageSpecificGlobalActivityDetails> gaFinalMap;// key-idOfLanguageSpecificGlobalActivityDetails and value = LanguageSpecificGlobalActivityDetails
    private Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpFinalMap; //activityId, Listparameter
	private InternationalizaionLanguages inalg;
	public Map<Long, LanguageSpecificGlobalActivityDetails> getGaFinalMap() {
		return gaFinalMap;
	}
	public Map<Long, List<LanguageSpecificGlobalParameterDetails>> getGpFinalMap() {
		return gpFinalMap;
	}
	public InternationalizaionLanguages getInalg() {
		return inalg;
	}
	public void setGaFinalMap(Map<Long, LanguageSpecificGlobalActivityDetails> gaFinalMap) {
		this.gaFinalMap = gaFinalMap;
	}
	public void setGpFinalMap(Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpFinalMap) {
		this.gpFinalMap = gpFinalMap;
	}
	public void setInalg(InternationalizaionLanguages inalg) {
		this.inalg = inalg;
	}
	
	
	
}
