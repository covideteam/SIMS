package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class MealsRealTimeCommunicationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8081424615005229557L;
	
	private List<RealTimeCommunicationDto> rtcDtoList;

	public List<RealTimeCommunicationDto> getRtcDtoList() {
		return rtcDtoList;
	}

	public void setRtcDtoList(List<RealTimeCommunicationDto> rtcDtoList) {
		this.rtcDtoList = rtcDtoList;
	}
	
	
	

}
