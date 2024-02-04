package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;

public class StorageVacutinerDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6503423698901611120L;
	private StudyMaster study;
	private List<StudyPeriodMaster> periods = new ArrayList<>();
	private List<SampleTimePoints> timePoitns = new ArrayList<>();
	private Map<Long, Instrument> deepfreezers =  new HashMap<>();
	private List<Integer> vialNos = new ArrayList<>();   //6
	private Map<String, Deepfreezer> racks =  new HashMap<>();  //7aPKn
	private Map<String, DeepfreezerShelf> shelfs =  new HashMap<>();  //9aPKn
	
	List<Instrument> instruments = new ArrayList<>();
	private List<CentrifugationDataMaster> centrifugationList = new ArrayList<>();
	private Map<Long, List<RackWithVials>> rackVials = new HashMap<>();
	private Map<Long, String> timePointsMap = new HashMap<>();
	private Map<Long, String> rackTimePointMap = new HashMap<>();
	
	List<SampleTimePoints> onlytimePoints = null;
	Map<String, List<Long>> timePointTimePointIdsMap = new HashMap<>();
	
	
	public List<SampleTimePoints> getOnlytimePoints() {
		return onlytimePoints;
	}
	public void setOnlytimePoints(List<SampleTimePoints> onlytimePoints) {
		this.onlytimePoints = onlytimePoints;
	}
	public Map<String, List<Long>> getTimePointTimePointIdsMap() {
		return timePointTimePointIdsMap;
	}
	public void setTimePointTimePointIdsMap(Map<String, List<Long>> timePointTimePointIdsMap) {
		this.timePointTimePointIdsMap = timePointTimePointIdsMap;
	}
	public Map<Long, String> getRackTimePointMap() {
		return rackTimePointMap;
	}
	public void setRackTimePointMap(Map<Long, String> rackTimePointMap) {
		this.rackTimePointMap = rackTimePointMap;
	}
	public Map<Long, String> getTimePointsMap() {
		return timePointsMap;
	}
	public void setTimePointsMap(Map<Long, String> timePointsMap) {
		this.timePointsMap = timePointsMap;
	}
	public Map<Long, List<RackWithVials>> getRackVials() {
		return rackVials;
	}
	public void setRackVials(Map<Long, List<RackWithVials>> rackVials) {
		this.rackVials = rackVials;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public List<StudyPeriodMaster> getPeriods() {
		return periods;
	}
	public void setPeriods(List<StudyPeriodMaster> periods) {
		this.periods = periods;
	}
	public List<SampleTimePoints> getTimePoitns() {
		return timePoitns;
	}
	public void setTimePoitns(List<SampleTimePoints> timePoitns) {
		this.timePoitns = timePoitns;
	}
	public Map<Long, Instrument> getDeepfreezers() {
		return deepfreezers;
	}
	public void setDeepfreezers(Map<Long, Instrument> deepfreezers) {
		this.deepfreezers = deepfreezers;
	}
	public List<Integer> getVialNos() {
		return vialNos;
	}
	public void setVialNos(List<Integer> vialNos) {
		this.vialNos = vialNos;
	}
	public Map<String, Deepfreezer> getRacks() {
		return racks;
	}
	public void setRacks(Map<String, Deepfreezer> racks) {
		this.racks = racks;
	}
	public Map<String, DeepfreezerShelf> getShelfs() {
		return shelfs;
	}
	public void setShelfs(Map<String, DeepfreezerShelf> shelfs) {
		this.shelfs = shelfs;
	}
	public List<Instrument> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}
	public List<CentrifugationDataMaster> getCentrifugationList() {
		return centrifugationList;
	}
	public void setCentrifugationList(List<CentrifugationDataMaster> centrifugationList) {
		this.centrifugationList = centrifugationList;
	}
	
	
	
	
}
