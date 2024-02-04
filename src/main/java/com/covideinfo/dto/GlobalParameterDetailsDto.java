package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class GlobalParameterDetailsDto implements Serializable {
	
	private Long studyAtivityId;
	private String activityName;
	private Long activityId;
	private String activityCode;
	private boolean showSubjectField;
	private String getUrl;
	private String postUrl;
	private List<ParameterFormDataDto> parameterDto;
	private Long customParameterId;
	private RulesDetails rulesDetails;
	private Long periodId;
	private Long treatmentId;
	private List<StudyActivityTimePointsDto> satpDtoList;
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public List<ParameterFormDataDto> getParameterDto() {
		return parameterDto;
	}
	public void setParameterDto(List<ParameterFormDataDto> parameterDto) {
		this.parameterDto = parameterDto;
	}
	public Long getStudyAtivityId() {
		return studyAtivityId;
	}
	public void setStudyAtivityId(Long studyAtivityId) {
		this.studyAtivityId = studyAtivityId;
	}
	public RulesDetails getRulesDetails() {
		return rulesDetails;
	}
	public void setRulesDetails(RulesDetails rulesDetails) {
		this.rulesDetails = rulesDetails;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public Long getCustomParameterId() {
		return customParameterId;
	}
	public void setCustomParameterId(Long customParameterId) {
		this.customParameterId = customParameterId;
	}
	public List<StudyActivityTimePointsDto> getSatpDtoList() {
		return satpDtoList;
	}
	public void setSatpDtoList(List<StudyActivityTimePointsDto> satpDtoList) {
		this.satpDtoList = satpDtoList;
	}
	public boolean isShowSubjectField() {
		return showSubjectField;
	}
	public void setShowSubjectField(boolean showSubjectField) {
		this.showSubjectField = showSubjectField;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	
	
	
	

}
