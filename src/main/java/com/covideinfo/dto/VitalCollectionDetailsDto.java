package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.VitalTimePoints;

@SuppressWarnings("serial")
public class VitalCollectionDetailsDto implements Serializable {
	
	private CommonTimePointDto ctpDto;
	private Map<Long, List<VitalTimePoints>> preDoseVtpMap; //treatment, List of Vitals
	private Map<Long, List<VitalTimePoints>> postDoseVtpMap; //treatment, List of Vitals
	private Map<Long, VitalTimePoints> vitalMap; //vitalTpId, vitalTimePoint
	private Map<String, Map<Long, Map<Long, Map<Long, List<RealTimeCommunicationDto>>>>> svtpData; //SubjectNo, periodId, treatmentId, vitalId, subjectVitaltimePointData
	private Map<Long, GlobalparameterFromDto> parameterMap; ////vitaltpId, parameters
	private Map<Long, Integer> previtalOrderMap;//VitalId, orderNo
	private Map<Long, Integer> postvitalOrderMap;//VitalId, orderNo
	private Map<Long, VitalTimePoints> preVitalsMap; //vitalId, VitalPojo
	private Map<Long, VitalTimePoints> postVitalsMap; //vitalId, VitalPojo
	private Map<Integer, Long> preorderVitalIdsMap; //orderNo, SampleId
	private Map<Integer, Long> postorderVitalIdsMap; //orderNo, SampleId
	private PlannedTimeDetailsDto ptdDto;
	public CommonTimePointDto getCtpDto() {
		return ctpDto;
	}
	public Map<Long, VitalTimePoints> getVitalMap() {
		return vitalMap;
	}
	public Map<String, Map<Long, Map<Long, Map<Long, List<RealTimeCommunicationDto>>>>> getSvtpData() {
		return svtpData;
	}
	public Map<Long, GlobalparameterFromDto> getParameterMap() {
		return parameterMap;
	}
	public void setCtpDto(CommonTimePointDto ctpDto) {
		this.ctpDto = ctpDto;
	}
	public void setVitalMap(Map<Long, VitalTimePoints> vitalMap) {
		this.vitalMap = vitalMap;
	}
	public void setSvtpData(Map<String, Map<Long, Map<Long, Map<Long, List<RealTimeCommunicationDto>>>>> svtpData) {
		this.svtpData = svtpData;
	}
	public void setParameterMap(Map<Long, GlobalparameterFromDto> parameterMap) {
		this.parameterMap = parameterMap;
	}
	public Map<Long, List<VitalTimePoints>> getPreDoseVtpMap() {
		return preDoseVtpMap;
	}
	public Map<Long, List<VitalTimePoints>> getPostDoseVtpMap() {
		return postDoseVtpMap;
	}
	public void setPreDoseVtpMap(Map<Long, List<VitalTimePoints>> preDoseVtpMap) {
		this.preDoseVtpMap = preDoseVtpMap;
	}
	public void setPostDoseVtpMap(Map<Long, List<VitalTimePoints>> postDoseVtpMap) {
		this.postDoseVtpMap = postDoseVtpMap;
	}
	public Map<Long, VitalTimePoints> getPreVitalsMap() {
		return preVitalsMap;
	}
	public Map<Long, VitalTimePoints> getPostVitalsMap() {
		return postVitalsMap;
	}
	public void setPreVitalsMap(Map<Long, VitalTimePoints> preVitalsMap) {
		this.preVitalsMap = preVitalsMap;
	}
	public void setPostVitalsMap(Map<Long, VitalTimePoints> postVitalsMap) {
		this.postVitalsMap = postVitalsMap;
	}
	public Map<Long, Integer> getPrevitalOrderMap() {
		return previtalOrderMap;
	}
	public Map<Long, Integer> getPostvitalOrderMap() {
		return postvitalOrderMap;
	}
	public Map<Integer, Long> getPreorderVitalIdsMap() {
		return preorderVitalIdsMap;
	}
	public Map<Integer, Long> getPostorderVitalIdsMap() {
		return postorderVitalIdsMap;
	}
	public void setPrevitalOrderMap(Map<Long, Integer> previtalOrderMap) {
		this.previtalOrderMap = previtalOrderMap;
	}
	public void setPostvitalOrderMap(Map<Long, Integer> postvitalOrderMap) {
		this.postvitalOrderMap = postvitalOrderMap;
	}
	public void setPreorderVitalIdsMap(Map<Integer, Long> preorderVitalIdsMap) {
		this.preorderVitalIdsMap = preorderVitalIdsMap;
	}
	public void setPostorderVitalIdsMap(Map<Integer, Long> postorderVitalIdsMap) {
		this.postorderVitalIdsMap = postorderVitalIdsMap;
	}
	public PlannedTimeDetailsDto getPtdDto() {
		return ptdDto;
	}
	public void setPtdDto(PlannedTimeDetailsDto ptdDto) {
		this.ptdDto = ptdDto;
	}
	
	
	

}
