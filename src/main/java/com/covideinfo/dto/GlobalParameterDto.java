package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.ControlType;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.UnitsMaster;

@SuppressWarnings("serial")
public class GlobalParameterDto implements Serializable {
	
	private  List<InternationalizaionLanguages> inlList;
	private Map<Long, Map<String, String>> lanMap;
	private Map<String, String> outlanMap;
	private List<LanguageSpecificGlobalParameterDetails> lspdList;
	private List<GlobalGroups> ggList;
	private List<ControlType> controlType;
	private List<GlobalValues> valsList;
	private Map<Long, List<LanguageSpecificGlobalParameterDetails>> lsMap;
	private Map<Long, GlobalParameter> ggMap;
	private List<GlobalActivity> gaList;
	private List<ParameterControlTypeValues> pctvList;
	private Map<Long, Map<Long, List<GlobalValues>>> pctvMap;
	private List<UnitsMaster> umList;
	private InternationalizaionLanguages inlag = null;
	private GlobalGroups group = null;
	private ControlType ctPojo = null;
	private List<GlobalValues> gvList = null;
	private GlobalActivity gaPojo = null;
	private UnitsMaster unitsPojo = null;
	private List<StudyPhases> phaseList;
	
	public List<InternationalizaionLanguages> getInlList() {
		return inlList;
	}
	public void setInlList(List<InternationalizaionLanguages> inlList) {
		this.inlList = inlList;
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
	public List<LanguageSpecificGlobalParameterDetails> getLspdList() {
		return lspdList;
	}
	public void setLspdList(List<LanguageSpecificGlobalParameterDetails> lspdList) {
		this.lspdList = lspdList;
	}
	public List<GlobalGroups> getGgList() {
		return ggList;
	}
	public void setGgList(List<GlobalGroups> ggList) {
		this.ggList = ggList;
	}
	public List<ControlType> getControlType() {
		return controlType;
	}
	public void setControlType(List<ControlType> controlType) {
		this.controlType = controlType;
	}
	public Map<Long, List<LanguageSpecificGlobalParameterDetails>> getLsMap() {
		return lsMap;
	}
	public void setLsMap(Map<Long, List<LanguageSpecificGlobalParameterDetails>> lsMap) {
		this.lsMap = lsMap;
	}
	public Map<Long, GlobalParameter> getGgMap() {
		return ggMap;
	}
	public void setGgMap(Map<Long, GlobalParameter> ggMap) {
		this.ggMap = ggMap;
	}
	public List<GlobalValues> getValsList() {
		return valsList;
	}
	public void setValsList(List<GlobalValues> valsList) {
		this.valsList = valsList;
	}
	public List<GlobalActivity> getGaList() {
		return gaList;
	}
	public void setGaList(List<GlobalActivity> gaList) {
		this.gaList = gaList;
	}
	public GlobalGroups getGroup() {
		return group;
	}
	public void setGroup(GlobalGroups group) {
		this.group = group;
	}
	public ControlType getCtPojo() {
		return ctPojo;
	}
	public void setCtPojo(ControlType ctPojo) {
		this.ctPojo = ctPojo;
	}
	public List<ParameterControlTypeValues> getPctvList() {
		return pctvList;
	}
	public void setPctvList(List<ParameterControlTypeValues> pctvList) {
		this.pctvList = pctvList;
	}
	public List<GlobalValues> getGvList() {
		return gvList;
	}
	
	public Map<Long, Map<Long, List<GlobalValues>>> getPctvMap() {
		return pctvMap;
	}
	public List<StudyPhases> getPhaseList() {
		return phaseList;
	}
	public void setPhaseList(List<StudyPhases> phaseList) {
		this.phaseList = phaseList;
	}
	public void setPctvMap(Map<Long, Map<Long, List<GlobalValues>>> pctvMap) {
		this.pctvMap = pctvMap;
	}
	public UnitsMaster getUnitsPojo() {
		return unitsPojo;
	}
	public void setUnitsPojo(UnitsMaster unitsPojo) {
		this.unitsPojo = unitsPojo;
	}
	public void setGvList(List<GlobalValues> gvList) {
		this.gvList = gvList;
	}
	public GlobalActivity getGaPojo() {
		return gaPojo;
	}
	public void setGaPojo(GlobalActivity gaPojo) {
		this.gaPojo = gaPojo;
	}
	public List<UnitsMaster> getUmList() {
		return umList;
	}
	public void setUmList(List<UnitsMaster> umList) {
		this.umList = umList;
	}
	public InternationalizaionLanguages getInlag() {
		return inlag;
	}
	public void setInlag(InternationalizaionLanguages inlag) {
		this.inlag = inlag;
	}
	
	
	
	

}
