package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

public class SampleCollectionDto implements Serializable {
	private static final long serialVersionUID = -1097290580334484289L;
	private GlobalparameterFromDto perameters;
	
	List<StudySubjects> subjects = new ArrayList<>();
	SortedMap<Integer, String> subjectBarocodes = new TreeMap<>();
	Map<String, String> replacedSubjects = new HashMap<>();
	Map<String, String> standBySubjectsActualSubject = new HashMap<>();
	List<String> droppedSubjects = new ArrayList<>();
	SortedMap<String, StudyPeriodMaster> subjectPerods = new TreeMap<>();
	SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes = new TreeMap<>();
	SortedMap<Long, StudyPeriodMaster> periods = new TreeMap<>();
	SortedMap<Long, SampleTimePoints> timPoints = new TreeMap<>();
	SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> collectedData = new TreeMap<>();
	Map<Long, SubjectSampleCollectionTimePointsData> timePointCollectedData = new HashMap<>();
	//deviations 
	List<DeviationMessage> deviations = new ArrayList<>();
	
	Map<String, SubjectSampleCollectionTimePointsData> subjectPeriodTimePointCollectionDetials = new HashMap<>();
	Set<Integer> timePointNos = new HashSet<>();
	public Map<String, SubjectSampleCollectionTimePointsData> getSubjectPeriodTimePointCollectionDetials() {
		return subjectPeriodTimePointCollectionDetials;
	}
	public void setSubjectPeriodTimePointCollectionDetials(
			Map<String, SubjectSampleCollectionTimePointsData> subjectPeriodTimePointCollectionDetials) {
		this.subjectPeriodTimePointCollectionDetials = subjectPeriodTimePointCollectionDetials;
	}
	public Map<String, String> getStandBySubjectsActualSubject() {
		return standBySubjectsActualSubject;
	}
	public void setStandBySubjectsActualSubject(Map<String, String> standBySubjectsActualSubject) {
		this.standBySubjectsActualSubject = standBySubjectsActualSubject;
	}
	public GlobalparameterFromDto getPerameters() {
		return perameters;
	}
	public void setPerameters(GlobalparameterFromDto perameters) {
		this.perameters = perameters;
	}
	
	public List<DeviationMessage> getDeviations() {
		return deviations;
	}
	public void setDeviations(List<DeviationMessage> deviations) {
		this.deviations = deviations;
	}
	public List<StudySubjects> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<StudySubjects> subjects) {
		this.subjects = subjects;
	}
	public SortedMap<String, SubjectDoseTimePoints> getSubjectDoseTimes() {
		return subjectDoseTimes;
	}
	public void setSubjectDoseTimes(SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes) {
		this.subjectDoseTimes = subjectDoseTimes;
	}
	public Map<Long, SubjectSampleCollectionTimePointsData> getTimePointCollectedData() {
		return timePointCollectedData;
	}
	public void setTimePointCollectedData(Map<Long, SubjectSampleCollectionTimePointsData> timePointCollectedData) {
		this.timePointCollectedData = timePointCollectedData;
	}

	public List<String> getDroppedSubjects() {
		return droppedSubjects;
	}
	public void setDroppedSubjects(List<String> droppedSubjects) {
		this.droppedSubjects = droppedSubjects;
	}
	public SortedMap<Integer, String> getSubjectBarocodes() {
		return subjectBarocodes;
	}
	public void setSubjectBarocodes(SortedMap<Integer, String> subjectBarocodes) {
		this.subjectBarocodes = subjectBarocodes;
	}
	public Map<String, String> getReplacedSubjects() {
		return replacedSubjects;
	}
	public void setReplacedSubjects(Map<String, String> replacedSubjects) {
		this.replacedSubjects = replacedSubjects;
	}
	public SortedMap<String, StudyPeriodMaster> getSubjectPerods() {
		return subjectPerods;
	}
	public void setSubjectPerods(SortedMap<String, StudyPeriodMaster> subjectPerods) {
		this.subjectPerods = subjectPerods;
	}
	
	public SortedMap<Long, StudyPeriodMaster> getPeriods() {
		return periods;
	}
	public void setPeriods(SortedMap<Long, StudyPeriodMaster> periods) {
		this.periods = periods;
	}
	public SortedMap<Long, SampleTimePoints> getTimPoints() {
		return timPoints;
	}
	public void setTimPoints(SortedMap<Long, SampleTimePoints> timPoints) {
		this.timPoints = timPoints;
	}
	public SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> getCollectedData() {
		return collectedData;
	}
	public void setCollectedData(
			SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> collectedData) {
		this.collectedData = collectedData;
	}
	public Set<Integer> getTimePointNos() {
		return timePointNos;
	}
	public void setTimePointNos(Set<Integer> timePointNos) {
		this.timePointNos = timePointNos;
	}


}
