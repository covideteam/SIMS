package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class GlobalValuesDto implements Serializable {
	
	private Map<String, String> outLanMap;
	private Map<Long, Map<String, String>> lanMap;
	private Map<Long, GlobalValues> gvMap;
	private Map<Long, List<LanguageSpecificValueDetails>> lsMap;
	private String result="Failed";
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
	public Map<Long, GlobalValues> getGvMap() {
		return gvMap;
	}
	public void setGvMap(Map<Long, GlobalValues> gvMap) {
		this.gvMap = gvMap;
	}
	public Map<Long, List<LanguageSpecificValueDetails>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificValueDetails>> lsMap) {
		this.lsMap = lsMap;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	

}
