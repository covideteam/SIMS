package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class GlobalActivityParameterDetailsDto implements Serializable {
	
	private Map<Long, Long> volPeriodMap;//volunteerId, periodId
	private Map<Long, Map<Long, GlobalParameterDetailsDto>> actParamsMap;//periodId, treatmentId, parameterdetails
	private Map<Long, Long> volTreatmentsMap;//volId, treatmentId
	private Long treatmentOneId;
	public Map<Long, Long> getVolPeriodMap() {
		return volPeriodMap;
	}
	public Map<Long, Map<Long, GlobalParameterDetailsDto>> getActParamsMap() {
		return actParamsMap;
	}
	public Map<Long, Long> getVolTreatmentsMap() {
		return volTreatmentsMap;
	}
	public Long getTreatmentOneId() {
		return treatmentOneId;
	}
	public void setVolPeriodMap(Map<Long, Long> volPeriodMap) {
		this.volPeriodMap = volPeriodMap;
	}
	public void setActParamsMap(Map<Long, Map<Long, GlobalParameterDetailsDto>> actParamsMap) {
		this.actParamsMap = actParamsMap;
	}
	public void setVolTreatmentsMap(Map<Long, Long> volTreatmentsMap) {
		this.volTreatmentsMap = volTreatmentsMap;
	}
	public void setTreatmentOneId(Long treatmentOneId) {
		this.treatmentOneId = treatmentOneId;
	}
	
	

}
