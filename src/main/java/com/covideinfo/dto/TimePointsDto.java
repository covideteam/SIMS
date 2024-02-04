package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TimePointsDto implements Serializable, Comparable<TimePointsDto> {
	
	private Long dataInfoId;
	private String tpVal;
	private Double timePoint;
	private String treatmentName;
	private String treatmentCode;
	private int subRowNo;
	private String typeStr;


	private int intravelBetweenSubjecs;
	
	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public Double getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Double timePoint) {
		this.timePoint = timePoint;
	}

	@Override
	public int compareTo(TimePointsDto o) {
		return Double.compare(this.timePoint, o.timePoint);
	}

	public int getIntravelBetweenSubjecs() {
		return intravelBetweenSubjecs;
	}

	public void setIntravelBetweenSubjecs(int intravelBetweenSubjecs) {
		this.intravelBetweenSubjecs = intravelBetweenSubjecs;
	}

	public String getTpVal() {
		return tpVal;
	}

	public void setTpVal(String tpVal) {
		this.tpVal = tpVal;
	}

	public int getSubRowNo() {
		return subRowNo;
	}

	public void setSubRowNo(int subRowNo) {
		this.subRowNo = subRowNo;
	}

	public Long getDataInfoId() {
		return dataInfoId;
	}

	public void setDataInfoId(Long dataInfoId) {
		this.dataInfoId = dataInfoId;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getTreatmentCode() {
		return treatmentCode;
	}

	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}
	

}
