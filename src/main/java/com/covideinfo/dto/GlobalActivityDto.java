package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.RoleMaster;

@SuppressWarnings("serial")
public class GlobalActivityDto implements Serializable {
	
	private List<InternationalizaionLanguages> inlList;
	private List<LanguageSpecificGlobalActivityDetails> lsgList;
	private Map<Long, Map<String, String>> lanMap;
	private Map<String, String> outlanMap;
	private Map<Long, GlobalActivity> ggMap;
	private Map<Long, List<LanguageSpecificGlobalActivityDetails>> lsMap;
	private List<RoleMaster> rolesList;
	private Map<Long, String> roleMap = null;
	private Map<Long, List<Long>> gauserIdMap;
	private List<LanguageSpecificGroupDetails> gbList;
	private InternationalizaionLanguages inlag;
	public List<InternationalizaionLanguages> getInlList() {
		return inlList;
	}
	public void setInlList(List<InternationalizaionLanguages> inlList) {
		this.inlList = inlList;
	}
	public List<LanguageSpecificGlobalActivityDetails> getLsgList() {
		return lsgList;
	}
	public void setLsgList(List<LanguageSpecificGlobalActivityDetails> lsgList) {
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
	public Map<Long, GlobalActivity> getGgMap() {
		return ggMap;
	}
	public void setGgMap(Map<Long, GlobalActivity> ggMap) {
		this.ggMap = ggMap;
	}
	public Map<Long, List<LanguageSpecificGlobalActivityDetails>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificGlobalActivityDetails>> lsMap) {
		this.lsMap = lsMap;
	}
	public List<RoleMaster> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<RoleMaster> rolesList) {
		this.rolesList = rolesList;
	}
	public Map<Long, String> getRoleMap() {
		return roleMap;
	}
	public void setRoleMap(Map<Long, String> roleMap) {
		this.roleMap = roleMap;
	}
	public Map<Long, List<Long>> getGauserIdMap() {
		return gauserIdMap;
	}
	public void setGauserIdMap(Map<Long, List<Long>> gauserIdMap) {
		this.gauserIdMap = gauserIdMap;
	}
	public List<LanguageSpecificGroupDetails> getGbList() {
		return gbList;
	}
	public void setGbList(List<LanguageSpecificGroupDetails> gbList) {
		this.gbList = gbList;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	
	

}
