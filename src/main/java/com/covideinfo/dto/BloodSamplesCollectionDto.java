package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

@SuppressWarnings("serial")
public class BloodSamplesCollectionDto implements Serializable {
	
	private List<SampleTimePoints> samplesList;
	private List<SubjectSampleCollectionTimePointsData> sctpdList;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap; //subjectNo, periodId, treatement, SubjectDosePojo
	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getDosedMap() {
		return dosedMap;
	}
	public void setDosedMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap) {
		this.dosedMap = dosedMap;
	}
	public List<SampleTimePoints> getSamplesList() {
		return samplesList;
	}
	public List<SubjectSampleCollectionTimePointsData> getSctpdList() {
		return sctpdList;
	}
	public void setSamplesList(List<SampleTimePoints> samplesList) {
		this.samplesList = samplesList;
	}
	public void setSctpdList(List<SubjectSampleCollectionTimePointsData> sctpdList) {
		this.sctpdList = sctpdList;
	}
	

}
