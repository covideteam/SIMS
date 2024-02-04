package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SampleInfoDto implements Serializable {
	
	private String lightCondition;
	private String bloodVolume;
	private String typeOfVacutainer;
	private String treatment;
	public String getLightCondition() {
		return lightCondition;
	}
	public void setLightCondition(String lightCondition) {
		this.lightCondition = lightCondition;
	}
	public String getBloodVolume() {
		return bloodVolume;
	}
	public void setBloodVolume(String bloodVolume) {
		this.bloodVolume = bloodVolume;
	}
	public String getTypeOfVacutainer() {
		return typeOfVacutainer;
	}
	public void setTypeOfVacutainer(String typeOfVacutainer) {
		this.typeOfVacutainer = typeOfVacutainer;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	

}
