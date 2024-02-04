package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
@SuppressWarnings("serial")
public class ActivityDataDetailsDto implements Serializable {
	
	private List<StudyCheckInActivityDataDetails> sachkinList;
	private List<StudyCheckOutActivityDataDetails> sachkoutList;
	private List<StudyExecutionActivityDataDetails> stdexeList;
	private List<StudyActivityDataDetailsDiscrepancy> stdActivityDiscrepancyList;
	private InternationalizaionLanguages inlg;
	public List<StudyCheckInActivityDataDetails> getSachkinList() {
		return sachkinList;
	}
	public List<StudyCheckOutActivityDataDetails> getSachkoutList() {
		return sachkoutList;
	}
	public List<StudyExecutionActivityDataDetails> getStdexeList() {
		return stdexeList;
	}
	public void setSachkinList(List<StudyCheckInActivityDataDetails> sachkinList) {
		this.sachkinList = sachkinList;
	}
	public void setSachkoutList(List<StudyCheckOutActivityDataDetails> sachkoutList) {
		this.sachkoutList = sachkoutList;
	}
	public void setStdexeList(List<StudyExecutionActivityDataDetails> stdexeList) {
		this.stdexeList = stdexeList;
	}
	public InternationalizaionLanguages getInlg() {
		return inlg;
	}
	public void setInlg(InternationalizaionLanguages inlg) {
		this.inlg = inlg;
	}
	public List<StudyActivityDataDetailsDiscrepancy> getStdActivityDiscrepancyList() {
		return stdActivityDiscrepancyList;
	}
	public void setStdActivityDiscrepancyList(List<StudyActivityDataDetailsDiscrepancy> stdActivityDiscrepancyList) {
		this.stdActivityDiscrepancyList = stdActivityDiscrepancyList;
	}
	
	
	
	
//	

}
