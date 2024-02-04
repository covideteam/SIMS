package com.covideinfo.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

public class SegrigationDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7163558337306944987L;
	private Map<String, DeepfreezerShelf> shelfs = new HashMap<>();
	private Map<String, List<RackWithVials>> racks = new HashMap<>();  // key-period,aliquot value-list of RackWithVials
	private Map<String, StudySubjects> subjects = new HashMap<>();    // key=periodId,treatmentId,timePointId,vialNo,subjetNo value=studysubject
	private Map<Long, List<StudySubjects>> rackWiseSubects = new HashMap<>();
	private Map<String, SubjectSampleCollectionTimePointsData> vialdata = new HashMap<>(); // key - vial barocode, value - RackWithVials
	
	
	public Map<Long, List<StudySubjects>> getRackWiseSubects() {
		return rackWiseSubects;
	}
	public void setRackWiseSubects(Map<Long, List<StudySubjects>> rackWiseSubects) {
		this.rackWiseSubects = rackWiseSubects;
	}
	public Map<String, StudySubjects> getSubjects() {
		return subjects;
	}
	public void setSubjects(Map<String, StudySubjects> subjects) {
		this.subjects = subjects;
	}
	public Map<String, DeepfreezerShelf> getShelfs() {
		return shelfs;
	}
	public void setShelfs(Map<String, DeepfreezerShelf> shelfs) {
		this.shelfs = shelfs;
	}
	public Map<String, List<RackWithVials>> getRacks() {
		return racks;
	}
	public void setRacks(Map<String, List<RackWithVials>> racks) {
		this.racks = racks;
	}
	public Map<String, SubjectSampleCollectionTimePointsData> getVialdata() {
		return vialdata;
	}
	public void setVialdata(Map<String, SubjectSampleCollectionTimePointsData> vialdata) {
		this.vialdata = vialdata;
	}
	
	
	
}
