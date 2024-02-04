package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ActivityDto implements Serializable {
	
	private List<ActvitityDetailsDto> actdList;

	public List<ActvitityDetailsDto> getActdList() {
		return actdList;
	}

	public void setActdList(List<ActvitityDetailsDto> actdList) {
		this.actdList = actdList;
	}
	
	

}
