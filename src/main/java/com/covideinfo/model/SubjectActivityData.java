package com.covideinfo.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.covideinfo.dto.ParameterModelDto;

public class SubjectActivityData {
	private Long studyId;
	private Long activityId;
	private Long studyActivityId;
//	private String parameterValues;
	private List<ParameterModelDto> pmDto;
	private Map<Long, List<String>> parameterValuesMap;  // GlobalParameter-key , value
	private Long volId;
	private String type;
	private String saveTable;
	private Date activitySartDate;
	private String subjectDiscountinue;
	private String subjectReplace;
	private Long timePointId;

	public Long getStudyId() {
		return studyId;
	}

	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public List<ParameterModelDto> getPmDto() {
		return pmDto;
	}

	public void setPmDto(List<ParameterModelDto> pmDto) {
		this.pmDto = pmDto;
	}

	public Long getVolId() {
		return volId;
	}

	public void setVolId(Long volId) {
		this.volId = volId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSaveTable() {
		return saveTable;
	}

	public void setSaveTable(String saveTable) {
		this.saveTable = saveTable;
	}

	public Date getActivitySartDate() {
		return activitySartDate;
	}

	public void setActivitySartDate(Date activitySartDate) {
		this.activitySartDate = activitySartDate;
	}

	public Long getStudyActivityId() {
		return studyActivityId;
	}

	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}

	public String getSubjectDiscountinue() {
		return subjectDiscountinue;
	}

	public void setSubjectDiscountinue(String subjectDiscountinue) {
		this.subjectDiscountinue = subjectDiscountinue;
	}

	public String getSubjectReplace() {
		return subjectReplace;
	}

	public void setSubjectReplace(String subjectReplace) {
		this.subjectReplace = subjectReplace;
	}

	public Long getTimePointId() {
		return timePointId;
	}

	public void setTimePointId(Long timePointId) {
		this.timePointId = timePointId;
	}

	public Map<Long, List<String>> getParameterValuesMap() {
		return parameterValuesMap;
	}

	public void setParameterValuesMap(Map<Long, List<String>> parameterValuesMap) {
		this.parameterValuesMap = parameterValuesMap;
	}

}
