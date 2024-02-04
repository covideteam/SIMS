package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class VitalParameterDto implements Serializable{
	
	private Map<String, Integer> positionMaxParamMap;
	private Map<Integer, Set<Long>> vtalParamIdsMap;
	public Map<String, Integer> getPositionMaxParamMap() {
		return positionMaxParamMap;
	}
	public void setPositionMaxParamMap(Map<String, Integer> positionMaxParamMap) {
		this.positionMaxParamMap = positionMaxParamMap;
	}
	public Map<Integer, Set<Long>> getVtalParamIdsMap() {
		return vtalParamIdsMap;
	}
	public void setVtalParamIdsMap(Map<Integer, Set<Long>> vtalParamIdsMap) {
		this.vtalParamIdsMap = vtalParamIdsMap;
	}
	
	

}
