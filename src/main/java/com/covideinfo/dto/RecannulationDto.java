package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class RecannulationDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2517922739007130059L;
	private StudyMasterDto sm;
	private PeriodMasterDto spm;
	private List<SampleTpDto> samplesList;
	private SubjectsDto subject;
	private List<SubjectSamplesDto> subjectSamplesList;
	private List<RecannulationDataDto> recdDtoList;
	public StudyMasterDto getSm() {
		return sm;
	}
	public void setSm(StudyMasterDto sm) {
		this.sm = sm;
	}
	public PeriodMasterDto getSpm() {
		return spm;
	}
	public void setSpm(PeriodMasterDto spm) {
		this.spm = spm;
	}
	public List<SampleTpDto> getSamplesList() {
		return samplesList;
	}
	public void setSamplesList(List<SampleTpDto> samplesList) {
		this.samplesList = samplesList;
	}
	public SubjectsDto getSubject() {
		return subject;
	}
	public void setSubject(SubjectsDto subject) {
		this.subject = subject;
	}
	public List<SubjectSamplesDto> getSubjectSamplesList() {
		return subjectSamplesList;
	}
	public void setSubjectSamplesList(List<SubjectSamplesDto> subjectSamplesList) {
		this.subjectSamplesList = subjectSamplesList;
	}
	public List<RecannulationDataDto> getRecdDtoList() {
		return recdDtoList;
	}
	public void setRecdDtoList(List<RecannulationDataDto> recdDtoList) {
		this.recdDtoList = recdDtoList;
	}
	
}
