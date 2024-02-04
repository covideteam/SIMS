package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.StudySampleCentrifugation;

public class CetrifugationPageDataDto implements Serializable{

	SeparationVacutinerDto separationVacutinerDto;
	StudySampleCentrifugation studySampleCentrifugation;
	StorageVacutinerDto storageVacutinerDto;
	CentrificationDto centrificationDto;
	
	public SeparationVacutinerDto getSeparationVacutinerDto() {
		return separationVacutinerDto;
	}
	public void setSeparationVacutinerDto(SeparationVacutinerDto separationVacutinerDto) {
		this.separationVacutinerDto = separationVacutinerDto;
	}
	public StudySampleCentrifugation getStudySampleCentrifugation() {
		return studySampleCentrifugation;
	}
	public void setStudySampleCentrifugation(StudySampleCentrifugation studySampleCentrifugation) {
		this.studySampleCentrifugation = studySampleCentrifugation;
	}
	public StorageVacutinerDto getStorageVacutinerDto() {
		return storageVacutinerDto;
	}
	public void setStorageVacutinerDto(StorageVacutinerDto storageVacutinerDto) {
		this.storageVacutinerDto = storageVacutinerDto;
	}
	public CentrificationDto getCentrificationDto() {
		return centrificationDto;
	}
	public void setCentrificationDto(CentrificationDto centrificationDto) {
		this.centrificationDto = centrificationDto;
	}
	
}
