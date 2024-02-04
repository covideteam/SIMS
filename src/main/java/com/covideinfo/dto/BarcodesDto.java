package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class BarcodesDto implements Serializable {
	
	private StudyMaster study;
	private List<StudyPeriodMaster> spmList;
	private Map<String, StudySubjects> subMap;
	private Map<Long, StudyPeriodMaster> spmMap;
	private TreeMap<Long, List<DoseTimePoints>> doseTpMap;
	private Map<String, TreatmentInfo> subTrMap;
	private Map<String, SubjectRandamization> subRzMap;
	private Map<Long, List<SampleTimePoints>> sampTpMap;
	private List<SampleTimePoints> sampTpList;
	private int studyGroupStanbysCount;
	
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public Map<String, StudySubjects> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<String, StudySubjects> subMap) {
		this.subMap = subMap;
	}
	public Map<Long, StudyPeriodMaster> getSpmMap() {
		return spmMap;
	}
	public void setSpmMap(Map<Long, StudyPeriodMaster> spmMap) {
		this.spmMap = spmMap;
	}
	public Map<String, TreatmentInfo> getSubTrMap() {
		return subTrMap;
	}
	public void setSubTrMap(Map<String, TreatmentInfo> subTrMap) {
		this.subTrMap = subTrMap;
	}
	public TreeMap<Long, List<DoseTimePoints>> getDoseTpMap() {
		return doseTpMap;
	}
	public void setDoseTpMap(TreeMap<Long, List<DoseTimePoints>> doseTpMap) {
		this.doseTpMap = doseTpMap;
	}
	public Map<String, SubjectRandamization> getSubRzMap() {
		return subRzMap;
	}
	public void setSubRzMap(Map<String, SubjectRandamization> subRzMap) {
		this.subRzMap = subRzMap;
	}
	public Map<Long, List<SampleTimePoints>> getSampTpMap() {
		return sampTpMap;
	}
	public void setSampTpMap(Map<Long, List<SampleTimePoints>> sampTpMap) {
		this.sampTpMap = sampTpMap;
	}
	public List<SampleTimePoints> getSampTpList() {
		return sampTpList;
	}
	public void setSampTpList(List<SampleTimePoints> sampTpList) {
		this.sampTpList = sampTpList;
	}
	public int getStudyGroupStanbysCount() {
		return studyGroupStanbysCount;
	}
	public void setStudyGroupStanbysCount(int studyGroupStanbysCount) {
		this.studyGroupStanbysCount = studyGroupStanbysCount;
	}
	
	
	
	
	
	
	
	

}
