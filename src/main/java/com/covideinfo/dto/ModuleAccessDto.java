package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.LanguageSpecificSidemenu;


@SuppressWarnings("serial")
public class ModuleAccessDto implements Serializable {
	
	private Map<String, String> outLanMap;
	private Map<Long, Map<String, String>> lanMap;
	private Map<Long, ApplictionSideMenus> gvMap;
	private Map<Long, List<LanguageSpecificSidemenu>> lsMap;
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
	public Map<Long, ApplictionSideMenus> getGvMap() {
		return gvMap;
	}
	public void setGvMap(Map<Long, ApplictionSideMenus> gvMap) {
		this.gvMap = gvMap;
	}
	public Map<Long, List<LanguageSpecificSidemenu>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificSidemenu>> lsMap) {
		this.lsMap = lsMap;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
