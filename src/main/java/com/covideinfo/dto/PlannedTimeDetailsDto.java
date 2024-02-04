package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PlannedTimeDetailsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8138047525461664897L;
	private int washoutDays;
	private Date plannedDate;
	private Map<Long, Date> repotingDateMap; //periodId, Date
	private Map<Long, String> priodNamesMap;
	private Map<String, Long> priodIdsMap;
	private Map<Long, Date> periodWiseDoseMap;
	private Map<Long, Integer> periodNoIdsMap;
	private Map<Integer, Long> periodNoMap;
	private Map<String, Map<Long, Date>> subjectDoseMap;
	
	public int getWashoutDays() {
		return washoutDays;
	}
	public void setWashoutDays(int washoutDays) {
		this.washoutDays = washoutDays;
	}
	public Date getPlannedDate() {
		return plannedDate;
	}
	public void setPlannedDate(Date plannedDate) {
		this.plannedDate = plannedDate;
	}
	public Map<Long, Date> getRepotingDateMap() {
		return repotingDateMap;
	}
	public void setRepotingDateMap(Map<Long, Date> repotingDateMap) {
		this.repotingDateMap = repotingDateMap;
	}
	public Map<Long, String> getPriodNamesMap() {
		return priodNamesMap;
	}
	public void setPriodNamesMap(Map<Long, String> priodNamesMap) {
		this.priodNamesMap = priodNamesMap;
	}
	public Map<String, Long> getPriodIdsMap() {
		return priodIdsMap;
	}
	public void setPriodIdsMap(Map<String, Long> priodIdsMap) {
		this.priodIdsMap = priodIdsMap;
	}
	public Map<Long, Date> getPeriodWiseDoseMap() {
		return periodWiseDoseMap;
	}
	public void setPeriodWiseDoseMap(Map<Long, Date> periodWiseDoseMap) {
		this.periodWiseDoseMap = periodWiseDoseMap;
	}
	public Map<Long, Integer> getPeriodNoIdsMap() {
		return periodNoIdsMap;
	}
	public void setPeriodNoIdsMap(Map<Long, Integer> periodNoIdsMap) {
		this.periodNoIdsMap = periodNoIdsMap;
	}
	public Map<Integer, Long> getPeriodNoMap() {
		return periodNoMap;
	}
	public void setPeriodNoMap(Map<Integer, Long> periodNoMap) {
		this.periodNoMap = periodNoMap;
	}
	public Map<String, Map<Long, Date>> getSubjectDoseMap() {
		return subjectDoseMap;
	}
	public void setSubjectDoseMap(Map<String, Map<Long, Date>> subjectDoseMap) {
		this.subjectDoseMap = subjectDoseMap;
	}
	
	

}
