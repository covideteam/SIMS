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
@Table(name="epk_Subject_Replaced_Info")
public class SubjectReplacedInfo extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_Subject_Replaced_Info_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="subjectReplacedInfoId")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="volunteer_id")
	private Volunteer volunteer;
	private String subjectNo;
	private int seqNo;
	@ManyToOne
	@JoinColumn(name="replacedWith")
	private Volunteer replacedWith; // replaced with
	private String replacedWithBedNo;
	private int replacedWithSeqNo;
	@ManyToOne
	@JoinColumn(name="activeStatus")
	private StatusMaster activeStatus;
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
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public Volunteer getReplacedWith() {
		return replacedWith;
	}
	public void setReplacedWith(Volunteer replacedWith) {
		this.replacedWith = replacedWith;
	}
	public String getReplacedWithBedNo() {
		return replacedWithBedNo;
	}
	public void setReplacedWithBedNo(String replacedWithBedNo) {
		this.replacedWithBedNo = replacedWithBedNo;
	}
	public int getReplacedWithSeqNo() {
		return replacedWithSeqNo;
	}
	public void setReplacedWithSeqNo(int replacedWithSeqNo) {
		this.replacedWithSeqNo = replacedWithSeqNo;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
	}

	
}
