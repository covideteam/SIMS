package com.covideinfo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "epk_volunteer")

public class Volunteer extends CommonMaster implements Comparable<Volunteer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178652216232938539L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_volunteer_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "volId")
	private long id;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	private String volunreerId;
	private String subjectNo;
	private int seqNo;
	private String volunteerType="Subject";
	@ManyToOne
	@JoinColumn(name = "enrollSatatus")
	private StatusMaster enrollSatatus;  // Selected, Enrolled, Replaced, Withdrawn 
	@ManyToOne
	@JoinColumn(name = "volunteerSatatus")
	private StatusMaster volunteerSatatus; // Enrolled, Withdrawn , Dropped;for period
	
	@ManyToOne
	@JoinColumn(name = "std_subjects")
	private StudySubjects studySubjects;  
	
	@ManyToMany(targetEntity = SubjectSampleCollectionTimePoints.class, cascade = { CascadeType.ALL })  
	@JoinTable(name = "epk_Subject_Sample_Collection_timepoints_volunteer",   
	            joinColumns = { @JoinColumn(name = "volunteerId") },   
	            inverseJoinColumns = { @JoinColumn(name = "sampleTimePoitnsId") })  
	private List<SubjectSampleCollectionTimePoints> sampleTimePoitns = new ArrayList<>();
	@Transient
	private String barcode="";
	public List<SubjectSampleCollectionTimePoints> getSampleTimePoitns() {
		return sampleTimePoitns;
	}

	public void setSampleTimePoitns(List<SubjectSampleCollectionTimePoints> sampleTimePoitns) {
		this.sampleTimePoitns = sampleTimePoitns;
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

	public String getVolunreerId() {
		return volunreerId;
	}

	public void setVolunreerId(String volunreerId) {
		this.volunreerId = volunreerId;
	}

	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public String getVolunteerType() {
		return volunteerType;
	}

	public void setVolunteerType(String volunteerType) {
		this.volunteerType = volunteerType;
	}

	public StatusMaster getEnrollSatatus() {
		return enrollSatatus;
	}

	public void setEnrollSatatus(StatusMaster enrollSatatus) {
		this.enrollSatatus = enrollSatatus;
	}

	public StatusMaster getVolunteerSatatus() {
		return volunteerSatatus;
	}

	public void setVolunteerSatatus(StatusMaster volunteerSatatus) {
		this.volunteerSatatus = volunteerSatatus;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	@Override
	public int compareTo(Volunteer o) {
		return (int) (this.getSeqNo()-o.getSeqNo());
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public StudySubjects getStudySubjects() {
		return studySubjects;
	}

	public void setStudySubjects(StudySubjects studySubjects) {
		this.studySubjects = studySubjects;
	}
	
}
