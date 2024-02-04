package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class MealMenuReportsDto implements Serializable {
	
	private List<StudyMasterDto> smList;
	private List<StudyPeriodsDto> spmList;
	private Map<Long, List<StudyLevelConfiguredMealsDietDetailsDto>> slcmdMap;
	private Map<String, String> tpOrderMap;
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
	public Map<Long, List<StudyLevelConfiguredMealsDietDetailsDto>> getSlcmdMap() {
		return slcmdMap;
	}
	public void setSlcmdMap(Map<Long, List<StudyLevelConfiguredMealsDietDetailsDto>> slcmdMap) {
		this.slcmdMap = slcmdMap;
	}
	public Map<String, String> getTpOrderMap() {
		return tpOrderMap;
	}
	public void setTpOrderMap(Map<String, String> tpOrderMap) {
		this.tpOrderMap = tpOrderMap;
	}
	
	
	
	
	
	
	
	
	

}
