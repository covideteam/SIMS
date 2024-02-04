package com.covideinfo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="study_subject_replaced_info")
public class StudySubjectReplacedInfo extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_subject_replaced_info_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long studySubjectReplacedInfoId;
	@ManyToOne
	@JoinColumn(name="subjectId")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name="replaceWithSubjectId")
	private StudySubjects replaceWithSubject;
	private String activity = "Dosing"; //Dosing;
	
	public Long getStudySubjectReplacedInfoId() {
		return studySubjectReplacedInfoId;
	}
	public void setStudySubjectReplacedInfoId(Long studySubjectReplacedInfoId) {
		this.studySubjectReplacedInfoId = studySubjectReplacedInfoId;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public StudySubjects getReplaceWithSubject() {
		return replaceWithSubject;
	}
	public void setReplaceWithSubject(StudySubjects replaceWithSubject) {
		this.replaceWithSubject = replaceWithSubject;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
}
