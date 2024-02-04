package com.covideinfo.dummy;

import java.util.HashMap;
import java.util.Map;

public class TempCentrifuge {
	private Long studyId;
	private Long periodId;
	private Long timePotnId;
	private Map<String, String> vacutainer = new HashMap<>();
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public Long getTimePotnId() {
		return timePotnId;
	}
	public void setTimePotnId(Long timePotnId) {
		this.timePotnId = timePotnId;
	}
	public Map<String, String> getVacutainer() {
		return vacutainer;
	}
	public void setVacutainer(Map<String, String> vacutainer) {
		this.vacutainer = vacutainer;
	}
	public TempCentrifuge(Long studyId, Long periodId, Long timePotnId, Map<String, String> vacutainer) {
		super();
		this.studyId = studyId;
		this.periodId = periodId;
		this.timePotnId = timePotnId;
		this.vacutainer = vacutainer;
	}
	public TempCentrifuge() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
