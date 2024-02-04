package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class MissedTimePointsDto implements Serializable {
	
	private List<StudyMasterDto> smList;
	private List<StudyPeriodsDto> spmList;
	private List<ActivityDetailsDto> actList;
	private Map<Long, String> priodNamesMap;
	private Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> mtpMap;//periodId, subject, timepoint, scheduledTimes
	private Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> dosetpMap; //periodId, subject, timepoint, scheduledTimes
	private Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> stpMap; //periodId, subject, timepoint, scheduledTimes
	private Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> vtpMap; //periodId, subject, timepoint, scheduledTimes
	
	public List<StudyMasterDto> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMasterDto> smList) {
		this.smList = smList;
	}
	public List<StudyPeriodsDto> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodsDto> spmList) {
		this.spmList = spmList;
	}
	public List<ActivityDetailsDto> getActList() {
		return actList;
	}
	public void setActList(List<ActivityDetailsDto> actList) {
		this.actList = actList;
	}
	public Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> getMtpMap() {
		return mtpMap;
	}
	public void setMtpMap(Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> mtpMap) {
		this.mtpMap = mtpMap;
	}
	public Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> getDosetpMap() {
		return dosetpMap;
	}
	public void setDosetpMap(Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> dosetpMap) {
		this.dosetpMap = dosetpMap;
	}
	public Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> getStpMap() {
		return stpMap;
	}
	public void setStpMap(Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> stpMap) {
		this.stpMap = stpMap;
	}
	public Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> getVtpMap() {
		return vtpMap;
	}
	public void setVtpMap(Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> vtpMap) {
		this.vtpMap = vtpMap;
	}
	public Map<Long, String> getPriodNamesMap() {
		return priodNamesMap;
	}
	public void setPriodNamesMap(Map<Long, String> priodNamesMap) {
		this.priodNamesMap = priodNamesMap;
	}
	
	
	

}
