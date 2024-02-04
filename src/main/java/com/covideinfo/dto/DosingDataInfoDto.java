package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.DosingInfoData;
import com.covideinfo.model.Projects;

@SuppressWarnings("serial")
public class DosingDataInfoDto implements Serializable {
	
	private Projects porject;
	private DosingInfo dosingInfo;
	private List<DosingInfoData> dosinDataList;
	public DosingInfo getDosingInfo() {
		return dosingInfo;
	}
	public List<DosingInfoData> getDosinDataList() {
		return dosinDataList;
	}
	public void setDosingInfo(DosingInfo dosingInfo) {
		this.dosingInfo = dosingInfo;
	}
	public void setDosinDataList(List<DosingInfoData> dosinDataList) {
		this.dosinDataList = dosinDataList;
	}
	public Projects getPorject() {
		return porject;
	}
	public void setPorject(Projects porject) {
		this.porject = porject;
	}

}
