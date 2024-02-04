package com.covideinfo.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

/**
 * @author swami.tammireddi
 *
 */
public class ResultDto implements Serializable{
	private String success;
	private String message;
	private String timePointId;
	private Map<String, SubjectMealsTimePointsData> mealSubjectData = new HashMap<>();
	
	private SubjectSampleCollectionTimePointsData timepoint;
	private String subjectPeriodTimePointCollectionDetialsKey;
	private String endTime;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimePointId() {
		return timePointId;
	}
	public void setTimePointId(String timePointId) {
		this.timePointId = timePointId;
	}
	public Map<String, SubjectMealsTimePointsData> getMealSubjectData() {
		return mealSubjectData;
	}
	public void setMealSubjectData(Map<String, SubjectMealsTimePointsData> mealSubjectData) {
		this.mealSubjectData = mealSubjectData;
	}
	public SubjectSampleCollectionTimePointsData getTimepoint() {
		return timepoint;
	}
	public void setTimepoint(SubjectSampleCollectionTimePointsData timepoint) {
		this.timepoint = timepoint;
	}
	public String getSubjectPeriodTimePointCollectionDetialsKey() {
		return subjectPeriodTimePointCollectionDetialsKey;
	}
	public void setSubjectPeriodTimePointCollectionDetialsKey(String subjectPeriodTimePointCollectionDetialsKey) {
		this.subjectPeriodTimePointCollectionDetialsKey = subjectPeriodTimePointCollectionDetialsKey;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}