package com.covideinfo.dto;

import java.util.Map;

import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

public class SampleCollectionDtoDetails {
	
	private CommonTimePointDto ctpDto;
	private Map<Long, Map<Long, SampleTimePoints>> twSamplesMap;//treatment, sampleId, SampleTimePoints
	private Map<String, Map<Long, Map<Long, Map<Long, RealTimeCommunicationDto>>>> sampColDataMap; //SubjectNo, periodId, treatmentId, sampleId, SamplePojo
	private Map<Long, Integer> samplesOrderMap;//sampleId, orderNo
	private Map<Integer, Long> orderSampleIdsMap; //OrederNo, SampleId
	private Map<Long, SampleTimePoints> samplesMap; //sampleId, SampleTimePointPojo
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap; //subjectNo, periodId, treatement, SubjectDosePojo
	private Map<String, String> subCannulaInfoMap; // SubjectNo, Done/NotDone
	private boolean msgFlag = false;
	private String mealsMsg;
	private PlannedTimeDetailsDto ptdDto;
	public CommonTimePointDto getCtpDto() {
		return ctpDto;
	}

	public void setCtpDto(CommonTimePointDto ctpDto) {
		this.ctpDto = ctpDto;
	}

	public Map<Long, Map<Long, SampleTimePoints>> getTwSamplesMap() {
		return twSamplesMap;
	}

	public Map<String, Map<Long, Map<Long, Map<Long, RealTimeCommunicationDto>>>> getSampColDataMap() {
		return sampColDataMap;
	}

	public void setTwSamplesMap(Map<Long, Map<Long, SampleTimePoints>> twSamplesMap) {
		this.twSamplesMap = twSamplesMap;
	}

	public void setSampColDataMap(
			Map<String, Map<Long, Map<Long, Map<Long, RealTimeCommunicationDto>>>> sampColDataMap) {
		this.sampColDataMap = sampColDataMap;
	}

	public Map<Long, Integer> getSamplesOrderMap() {
		return samplesOrderMap;
	}

	public Map<Long, SampleTimePoints> getSamplesMap() {
		return samplesMap;
	}

	public void setSamplesOrderMap(Map<Long, Integer> samplesOrderMap) {
		this.samplesOrderMap = samplesOrderMap;
	}

	public void setSamplesMap(Map<Long, SampleTimePoints> samplesMap) {
		this.samplesMap = samplesMap;
	}

	public Map<Integer, Long> getOrderSampleIdsMap() {
		return orderSampleIdsMap;
	}

	public void setOrderSampleIdsMap(Map<Integer, Long> orderSampleIdsMap) {
		this.orderSampleIdsMap = orderSampleIdsMap;
	}

	public boolean isMsgFlag() {
		return msgFlag;
	}

	public String getMealsMsg() {
		return mealsMsg;
	}

	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	public void setMealsMsg(String mealsMsg) {
		this.mealsMsg = mealsMsg;
	}

	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getDosedMap() {
		return dosedMap;
	}

	public void setDosedMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap) {
		this.dosedMap = dosedMap;
	}

	public PlannedTimeDetailsDto getPtdDto() {
		return ptdDto;
	}

	public void setPtdDto(PlannedTimeDetailsDto ptdDto) {
		this.ptdDto = ptdDto;
	}

	public Map<String, String> getSubCannulaInfoMap() {
		return subCannulaInfoMap;
	}

	public void setSubCannulaInfoMap(Map<String, String> subCannulaInfoMap) {
		this.subCannulaInfoMap = subCannulaInfoMap;
	}
	

}
