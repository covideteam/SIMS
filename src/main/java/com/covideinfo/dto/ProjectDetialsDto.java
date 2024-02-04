package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.Projects;
import com.covideinfo.model.StatusMaster;

@SuppressWarnings("serial")
public class ProjectDetialsDto implements Serializable, Comparable<ProjectDetialsDto> {
	
	private String fieldName;
    private String fieldValue;
	private String type;     // study, period, dosing, dosingParameters, sampleTimePoins,vitalTimePoints,mealsTimePoints
	private boolean status = true;
	private Integer fieldOrder = 0;
	private Long treatmentNo;
	private Projects projectsId;
	private StatusMaster statusid;
	private Integer subRowNo = 0;
	public String getFieldName() {
		return fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public String getType() {
		return type;
	}
	public boolean isStatus() {
		return status;
	}
	public Integer getFieldOrder() {
		return fieldOrder;
	}
	public Long getTreatmentNo() {
		return treatmentNo;
	}
	public Projects getProjectsId() {
		return projectsId;
	}
	public StatusMaster getStatusid() {
		return statusid;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
	public void setTreatmentNo(Long treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	public void setProjectsId(Projects projectsId) {
		this.projectsId = projectsId;
	}
	public void setStatusid(StatusMaster statusid) {
		this.statusid = statusid;
	}
	@Override
	public int compareTo(ProjectDetialsDto o) {
		return Long.compare(this.treatmentNo, o.treatmentNo);
	}
	public Integer getSubRowNo() {
		return subRowNo;
	}
	public void setSubRowNo(Integer subRowNo) {
		this.subRowNo = subRowNo;
	}
	
	
	
	

}
