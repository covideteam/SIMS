package com.covideinfo.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.SampleStorageData;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.TreatmentInfo;

public class VialRackCollectionDto {
	private List<StudySubjects> subjects = new ArrayList<>();
	private SortedMap<Integer, String> subjectBarocodes = new TreeMap<>();  // key-Order , value=subjectno
	private Map<String, String> subjectTypes = new HashMap<>();			// key-subjecno, value = standby/not
	private Map<String, String> replacedSubjects = new HashMap<>();			// key-barcodeSubjectNo, value=finalReplacedSujectNo if any replacement happend
	private List<String> droppedSubjects = new ArrayList<>();				// drouped subject no's
	
	private SortedMap<String, StudyPeriodMaster> subjectPerods = new TreeMap<>();	// key-subject no, value=periodPojo
	private SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes = new TreeMap<>();	// key-subject no, value=subjectDoseObject
	private SortedMap<Integer, StudyPeriodMaster> periods = new TreeMap<>();	// key-periodno, value = priodPojo
	private SortedMap<Long, DoseTimePoints> timPoints = new TreeMap<>();		// key-timePointId(PK) , value = doseTimePointObject
	private SortedMap<Long, TreatmentInfo> treatment = new TreeMap<>();		// key-treatmentId(PK) , value = treatmentId
	private GlobalparameterFromDto perameters;
	
	private List<VitalTimePointsDto> vitalTimPoints = new ArrayList<>();	
	private Map<Long, VitalTimePointsDto> vitalTimePointsMap = new HashMap<>();		// key-timePointId(PK) , value = vitalObject
	private List<SubjectVitalTimePointsData> timePointCollectedData = new ArrayList<>();	// key-subjectMealId , value = subjectVitalObject
	private Map<String, SubjectVitalTimePointsData> collectedData = new HashMap<>();
	
	private List<SampleTimePoints> sampleTimePointCollectedData = new ArrayList<>();	// key-id , value = sampleTime
	private Map<String, SampleTimePoints> samplecollectedData = new HashMap<>();        // key-id ,value =SampleTimePoints
	private Map<Long, String> timePointsMap = new HashMap<>();
	private Map<Long, Map<Long, Map<Long, SampleStorageData>>> storageMap;//periodId, rackId, vitalId, SampleStorageData

	
	public Map<Long, String> getTimePointsMap() {
		return timePointsMap;
	}
	public void setTimePointsMap(Map<Long, String> timePointsMap) {
		this.timePointsMap = timePointsMap;
	}
	public Map<String, SubjectVitalTimePointsData> getCollectedData() {
		return collectedData;
	}
	public void setCollectedData(Map<String, SubjectVitalTimePointsData> collectedData) {
		this.collectedData = collectedData;
	}
	public List<StudySubjects> getSubjects() {
		
		return subjects;
	}
	public void setSubjects(List<StudySubjects> subjects) {
		this.subjects = subjects;
	}
	public SortedMap<Integer, String> getSubjectBarocodes() {
		return subjectBarocodes;
	}
	public void setSubjectBarocodes(SortedMap<Integer, String> subjectBarocodes) {
		this.subjectBarocodes = subjectBarocodes;
	}
	public Map<String, String> getSubjectTypes() {
		return subjectTypes;
	}
	public void setSubjectTypes(Map<String, String> subjectTypes) {
		this.subjectTypes = subjectTypes;
	}
	public Map<String, String> getReplacedSubjects() {
		return replacedSubjects;
	}
	public void setReplacedSubjects(Map<String, String> replacedSubjects) {
		this.replacedSubjects = replacedSubjects;
	}
	public List<String> getDroppedSubjects() {
		return droppedSubjects;
	}
	public void setDroppedSubjects(List<String> droppedSubjects) {
		this.droppedSubjects = droppedSubjects;
	}
	public SortedMap<String, StudyPeriodMaster> getSubjectPerods() {
		return subjectPerods;
	}
	public void setSubjectPerods(SortedMap<String, StudyPeriodMaster> subjectPerods) {
		this.subjectPerods = subjectPerods;
	}
	public SortedMap<String, SubjectDoseTimePoints> getSubjectDoseTimes() {
		return subjectDoseTimes;
	}
	public void setSubjectDoseTimes(SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes) {
		this.subjectDoseTimes = subjectDoseTimes;
	}
	public SortedMap<Integer, StudyPeriodMaster> getPeriods() {
		return periods;
	}
	public void setPeriods(SortedMap<Integer, StudyPeriodMaster> periods) {
		this.periods = periods;
	}
	public SortedMap<Long, DoseTimePoints> getTimPoints() {
		return timPoints;
	}
	public void setTimPoints(SortedMap<Long, DoseTimePoints> timPoints) {
		this.timPoints = timPoints;
	}
	public SortedMap<Long, TreatmentInfo> getTreatment() {
		return treatment;
	}
	public void setTreatment(SortedMap<Long, TreatmentInfo> treatment) {
		this.treatment = treatment;
	}
	public GlobalparameterFromDto getPerameters() {
		return perameters;
	}
	public void setPerameters(GlobalparameterFromDto perameters) {
		this.perameters = perameters;
	}
	
	public List<SubjectVitalTimePointsData> getTimePointCollectedData() {
		return timePointCollectedData;
	}
	public void setTimePointCollectedData(List<SubjectVitalTimePointsData> timePointCollectedData) {
		this.timePointCollectedData = timePointCollectedData;
	}
	public List<VitalTimePointsDto> getVitalTimPoints() {
		return vitalTimPoints;
	}
	public void setVitalTimPoints(List<VitalTimePointsDto> vitalTimPoints) {
		this.vitalTimPoints = vitalTimPoints;
	}
	public Map<Long, VitalTimePointsDto> getVitalTimePointsMap() {
		return vitalTimePointsMap;
	}
	public void setVitalTimePointsMap(Map<Long, VitalTimePointsDto> vitalTimePointsMap) {
		this.vitalTimePointsMap = vitalTimePointsMap;
	}
	public List<SampleTimePoints> getSampleTimePointCollectedData() {
		return sampleTimePointCollectedData;
	}
	public void setSampleTimePointCollectedData(List<SampleTimePoints> sampleTimePointCollectedData) {
		this.sampleTimePointCollectedData = sampleTimePointCollectedData;
	}
	public Map<String, SampleTimePoints> getSamplecollectedData() {
		return samplecollectedData;
	}
	public void setSamplecollectedData(Map<String, SampleTimePoints> samplecollectedData) {
		this.samplecollectedData = samplecollectedData;
	}
	public Map<Long, Map<Long, Map<Long, SampleStorageData>>> getStorageMap() {
		return storageMap;
	}
	public void setStorageMap(Map<Long, Map<Long, Map<Long, SampleStorageData>>> storageMap) {
		this.storageMap = storageMap;
	}
	
	
}
