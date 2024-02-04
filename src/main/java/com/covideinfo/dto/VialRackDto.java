package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class VialRackDto implements Serializable{
	private static final long serialVersionUID = 1463870941513480869L;
	private long id;
	private Long studyId;
	private Long rackId;
	private String rackScanTime;
	private String timePoint;
	private int aliqut;
	private List<String> vialDataList;
	
	public int getAliqut() {
		return aliqut;
	}
	public void setAliqut(int aliqut) {
		this.aliqut = aliqut;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public Long getRackId() {
		return rackId;
	}
	public void setRackId(Long rackId) {
		this.rackId = rackId;
	}
	 
	public String getRackScanTime() {
		return rackScanTime;
	}
	public void setRackScanTime(String rackScanTime) {
		this.rackScanTime = rackScanTime;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public List<String> getVialDataList() {
		return vialDataList;
	}
	public void setVialDataList(List<String> vialDataList) {
		this.vialDataList = vialDataList;
	} 

	
}
