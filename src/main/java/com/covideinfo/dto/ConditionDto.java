package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;
import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class ConditionDto implements Serializable {
	
	private Map<String, String> outLanMap;
	private Map<Long, Map<String, String>> lanMap;
	private Map<Long, ConditionParameter> swaMap;
	private Map<Long, List<LanguageSpecificCondition>> lsMap;
	private String result="Failed";
	private InternationalizaionLanguages inlag;
	public Map<String, String> getOutLanMap() {
		return outLanMap;
	}
	public void setOutLanMap(Map<String, String> outLanMap) {
		this.outLanMap = outLanMap;
	}
	public Map<Long, Map<String, String>> getLanMap() {
		return lanMap;
	}
	public void setLanMap(Map<Long, Map<String, String>> lanMap) {
		this.lanMap = lanMap;
	}
	
	public Map<Long, ConditionParameter> getSwaMap() {
		return swaMap;
	}
	public void setSwaMap(Map<Long, ConditionParameter> swaMap) {
		this.swaMap = swaMap;
	}
	
	public Map<Long, List<LanguageSpecificCondition>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificCondition>> lsMap) {
		this.lsMap = lsMap;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	
	

}
