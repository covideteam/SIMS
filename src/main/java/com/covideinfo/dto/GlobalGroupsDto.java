package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGroupDetails;

@SuppressWarnings("serial")
public class GlobalGroupsDto implements Serializable {
	
	private List<InternationalizaionLanguages> inlList;
	private List<LanguageSpecificGroupDetails> lsgList;
	private Map<Long, Map<String, String>> lanMap;
	private Map<String, String> outlanMap;
	private Map<Long, GlobalGroups> ggMap;
	private Map<Long, List<LanguageSpecificGroupDetails>> lsMap;
	private InternationalizaionLanguages inlag;
	public List<InternationalizaionLanguages> getInlList() {
		return inlList;
	}
	public void setInlList(List<InternationalizaionLanguages> inlList) {
		this.inlList = inlList;
	}
	public List<LanguageSpecificGroupDetails> getLsgList() {
		return lsgList;
	}
	public void setLsgList(List<LanguageSpecificGroupDetails> lsgList) {
		this.lsgList = lsgList;
	}
	public Map<Long, Map<String, String>> getLanMap() {
		return lanMap;
	}
	public void setLanMap(Map<Long, Map<String, String>> lanMap) {
		this.lanMap = lanMap;
	}
	public Map<String, String> getOutlanMap() {
		return outlanMap;
	}
	public void setOutlanMap(Map<String, String> outlanMap) {
		this.outlanMap = outlanMap;
	}
	public Map<Long, GlobalGroups> getGgMap() {
		return ggMap;
	}
	public void setGgMap(Map<Long, GlobalGroups> ggMap) {
		this.ggMap = ggMap;
	}
	public Map<Long, List<LanguageSpecificGroupDetails>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificGroupDetails>> lsMap) {
		this.lsMap = lsMap;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	
	
	
	

}
