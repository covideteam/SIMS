package com.covideinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="epk_Subject_Period_Status")
public class SubjectPeriodStatus extends CommonMaster{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_Subject_Period_Status_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectPeriodStatusId")
	private long id;
	@ManyToOne
	@JoinColumn(name="studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="periodId")
	private StudyPeriodMaster period;
	private int periodNo;
	@ManyToOne
	@JoinColumn(name="volunteerId")
	private Volunteer volunteer;
	@ManyToOne
	@JoinColumn(name="studySubjectId")
	private StudySubjects studySubject;
	private String subjectNo;
	private int subjectSeqNo;
	
	@ManyToOne
	@JoinColumn(name="subjectStatus")
	private StatusMaster subjectStatus;  // selected, enrolled, 
	private boolean subjectDoseStatus;
	private boolean activeStatus = true;
	
	
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getSubjectSeqNo() {
		return subjectSeqNo;
	}
	public void setSubjectSeqNo(int subjectSeqNo) {
		this.subjectSeqNo = subjectSeqNo;
	}
	public StudySubjects getStudySubject() {
		return studySubject;
	}
	public void setStudySubject(StudySubjects studySubject) {
		this.studySubject = studySubject;
	}
	public boolean isSubjectDoseStatus() {
		return subjectDoseStatus;
	}
	public void setSubjectDoseStatus(boolean subjectDoseStatus) {
		this.subjectDoseStatus = subjectDoseStatus;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public StatusMaster getSubjectStatus() {
		return subjectStatus;
	}
	public void setSubjectStatus(StatusMaster subjectStatus) {
		this.subjectStatus = subjectStatus;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	
}
