package com.covideinfo.dummy;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.dto.StudySampleCentrifugationDetailsDto;
import com.covideinfo.dto.StudySampleCentrifugationDto;
import com.covideinfo.dto.StudySampleProcessingDetailsDto;
import com.covideinfo.dto.StudySampleProcessingDto;
import com.covideinfo.dto.StudySampleStorageDetailsDto;
import com.covideinfo.dto.StudySampleStorageDto;

public class SampleProcessingAndStorage implements Serializable{
	
	private static final long serialVersionUID = -7206805563639758751L;
	private StudySampleCentrifugationDto sscDto;
	private List<StudySampleCentrifugationDetailsDto> sscdDtoList;
	private StudySampleProcessingDto sspDto;
	private List<StudySampleProcessingDetailsDto> sspDtoList;
	private StudySampleStorageDto sssDto;
	private List<StudySampleStorageDetailsDto> sssdDtoList;
	public StudySampleCentrifugationDto getSscDto() {
		return sscDto;
	}
	public List<StudySampleCentrifugationDetailsDto> getSscdDtoList() {
		return sscdDtoList;
	}
	public StudySampleProcessingDto getSspDto() {
		return sspDto;
	}
	public List<StudySampleProcessingDetailsDto> getSspDtoList() {
		return sspDtoList;
	}
	public StudySampleStorageDto getSssDto() {
		return sssDto;
	}
	public List<StudySampleStorageDetailsDto> getSssdDtoList() {
		return sssdDtoList;
	}
	public void setSscDto(StudySampleCentrifugationDto sscDto) {
		this.sscDto = sscDto;
	}
	public void setSscdDtoList(List<StudySampleCentrifugationDetailsDto> sscdDtoList) {
		this.sscdDtoList = sscdDtoList;
	}
	public void setSspDto(StudySampleProcessingDto sspDto) {
		this.sspDto = sspDto;
	}
	public void setSspDtoList(List<StudySampleProcessingDetailsDto> sspDtoList) {
		this.sspDtoList = sspDtoList;
	}
	public void setSssDto(StudySampleStorageDto sssDto) {
		this.sssDto = sssDto;
	}
	public void setSssdDtoList(List<StudySampleStorageDetailsDto> sssdDtoList) {
		this.sssdDtoList = sssdDtoList;
	}
	
	
	   
	
	
	
}
