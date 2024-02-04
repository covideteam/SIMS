package com.covideinfo.dummy;

import java.util.List;
import java.util.Map;

import com.covideinfo.dto.OtherActivitiesDto;
import com.covideinfo.dto.StudyActivityTimpointsSavingDto;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.VitalTimePoints;

public class ClinicalInfomation {

	private List<SampleTimePoints> sampleTimePoints;
	private List<MealsTimePoints> mealsTimePoints;
	private List<VitalTimePoints> vitalTimePoints;
	private Map<Integer, List<StudyActivityTimpointsSavingDto>> twSatEcgMap;
	private Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhFinalMap;
	private Map<Integer, List<StudyActivityTimpointsSavingDto>> skinSenFinalMap;
	private List<OtherActivitiesDto> otherActWithTimePointParamList;
	private List<OtherActivitiesDto> otherActWithoutTimePointParamList;
    public List<SampleTimePoints> getSampleTimePoints() {
		return sampleTimePoints;
	}
	public void setSampleTimePoints(List<SampleTimePoints> sampleTimePoints) {
		this.sampleTimePoints = sampleTimePoints;
	}
	public List<MealsTimePoints> getMealsTimePoints() {
		return mealsTimePoints;
	}
	public void setMealsTimePoints(List<MealsTimePoints> mealsTimePoints) {
		this.mealsTimePoints = mealsTimePoints;
	}
	public List<VitalTimePoints> getVitalTimePoints() {
		return vitalTimePoints;
	}
	public void setVitalTimePoints(List<VitalTimePoints> vitalTimePoints) {
		this.vitalTimePoints = vitalTimePoints;
	}
	public Map<Integer, List<StudyActivityTimpointsSavingDto>> getTwSatEcgMap() {
		return twSatEcgMap;
	}
	public Map<Integer, List<StudyActivityTimpointsSavingDto>> getSkinAdhFinalMap() {
		return skinAdhFinalMap;
	}
	public Map<Integer, List<StudyActivityTimpointsSavingDto>> getSkinSenFinalMap() {
		return skinSenFinalMap;
	}
	public void setTwSatEcgMap(Map<Integer, List<StudyActivityTimpointsSavingDto>> twSatEcgMap) {
		this.twSatEcgMap = twSatEcgMap;
	}
	public void setSkinAdhFinalMap(Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhFinalMap) {
		this.skinAdhFinalMap = skinAdhFinalMap;
	}
	public void setSkinSenFinalMap(Map<Integer, List<StudyActivityTimpointsSavingDto>> skinSenFinalMap) {
		this.skinSenFinalMap = skinSenFinalMap;
	}
	public List<OtherActivitiesDto> getOtherActWithTimePointParamList() {
		return otherActWithTimePointParamList;
	}
	public List<OtherActivitiesDto> getOtherActWithoutTimePointParamList() {
		return otherActWithoutTimePointParamList;
	}
	public void setOtherActWithTimePointParamList(List<OtherActivitiesDto> otherActWithTimePointParamList) {
		this.otherActWithTimePointParamList = otherActWithTimePointParamList;
	}
	public void setOtherActWithoutTimePointParamList(List<OtherActivitiesDto> otherActWithoutTimePointParamList) {
		this.otherActWithoutTimePointParamList = otherActWithoutTimePointParamList;
	}
	

}
