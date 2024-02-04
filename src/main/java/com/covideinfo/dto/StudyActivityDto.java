package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;

@SuppressWarnings("serial")
public class StudyActivityDto implements Serializable {
	
	private LanguageSpecificGlobalActivityDetails  lanSpecificAcitvity;
	private List<StudyActivities> saList = null;
	private Map<Long, List<LanguageSpecificGroupDetails>> lspgMap;
	private InternationalizaionLanguages inlg;
	private Map<Long, List<StudyActivityParameters>> sapMap;
	private Map<Long, LanguageSpecificGlobalParameterDetails> gpMap;
	private DefaultActivitys defaultActivity;
	private CustomActivityParameters custmActParm;
	private Map<Long,List<LanguageSpecificValueDetails>> languageValues;
	
	public LanguageSpecificGlobalActivityDetails getLanSpecificAcitvity() {
		return lanSpecificAcitvity;
	}
	public Map<Long, List<LanguageSpecificGroupDetails>> getLspgMap() {
		return lspgMap;
	}
	public InternationalizaionLanguages getInlg() {
		return inlg;
	}
	public Map<Long, List<StudyActivityParameters>> getSapMap() {
		return sapMap;
	}
	public void setLanSpecificAcitvity(LanguageSpecificGlobalActivityDetails lanSpecificAcitvity) {
		this.lanSpecificAcitvity = lanSpecificAcitvity;
	}
	public void setLspgMap(Map<Long, List<LanguageSpecificGroupDetails>> lspgMap) {
		this.lspgMap = lspgMap;
	}
	public void setInlg(InternationalizaionLanguages inlg) {
		this.inlg = inlg;
	}
	public void setSapMap(Map<Long, List<StudyActivityParameters>> sapMap) {
		this.sapMap = sapMap;
	}
	public List<StudyActivities> getSaList() {
		return saList;
	}
	public void setSaList(List<StudyActivities> saList) {
		this.saList = saList;
	}
	public Map<Long, LanguageSpecificGlobalParameterDetails> getGpMap() {
		return gpMap;
	}
	public void setGpMap(Map<Long, LanguageSpecificGlobalParameterDetails> gpMap) {
		this.gpMap = gpMap;
	}
	public DefaultActivitys getDefaultActivity() {
		return defaultActivity;
	}
	public void setDefaultActivity(DefaultActivitys defaultActivity) {
		this.defaultActivity = defaultActivity;
	}
	public CustomActivityParameters getCustmActParm() {
		return custmActParm;
	}
	public void setCustmActParm(CustomActivityParameters custmActParm) {
		this.custmActParm = custmActParm;
	}
	public Map<Long, List<LanguageSpecificValueDetails>> getLanguageValues() {
		return languageValues;
	}
	public void setLanguageValues(Map<Long, List<LanguageSpecificValueDetails>> languageValues) {
		this.languageValues = languageValues;
	}
	
	

	
	
	
	
	
	

}
