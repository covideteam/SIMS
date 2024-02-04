package com.covideinfo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "epk_subject_sample_separation_Timepoints_Data")
public class SubjectSampleSeparationTimePointsData extends CommonMaster {

	@Id
	@Column(name = "vialBarocde")
	private String vialBarocde;

	@ManyToOne
	@JoinColumn(name = "subjectSampleCollectionTimPoints")
	private SubjectSampleCollectionTimePoints subjectSampleCollectionTimPoints;
	private Date vacutinerScanTime;
	private Date vialScanTimetime;
	private int vialNo;
	private Date separationDate;
	private String diviation;
	@ManyToOne
	@JoinColumn(name = "separatedBy")
	private UserMaster separatedBy;
	@ManyToOne
	@JoinColumn(name = "centrifugationDataId")
	private CentrifugationData centrifugationData;
	
	public CentrifugationData getCentrifugationData() {
		return centrifugationData;
	}
	public void setCentrifugationData(CentrifugationData centrifugationData) {
		this.centrifugationData = centrifugationData;
	}
	public String getVialBarocde() {
		return vialBarocde;
	}
	public void setVialBarocde(String vialBarocde) {
		this.vialBarocde = vialBarocde;
	}
	public SubjectSampleCollectionTimePoints getSubjectSampleCollectionTimPoints() {
		return subjectSampleCollectionTimPoints;
	}
	public void setSubjectSampleCollectionTimPoints(SubjectSampleCollectionTimePoints subjectSampleCollectionTimPoints) {
		this.subjectSampleCollectionTimPoints = subjectSampleCollectionTimPoints;
	}
	public Date getVacutinerScanTime() {
		return vacutinerScanTime;
	}
	public void setVacutinerScanTime(Date vacutinerScanTime) {
		this.vacutinerScanTime = vacutinerScanTime;
	}
	public Date getVialScanTimetime() {
		return vialScanTimetime;
	}
	public void setVialScanTimetime(Date vialScanTimetime) {
		this.vialScanTimetime = vialScanTimetime;
	}
	public int getVialNo() {
		return vialNo;
	}
	public void setVialNo(int vialNo) {
		this.vialNo = vialNo;
	}

	public Date getSeparationDate() {
		return separationDate;
	}
	public void setSeparationDate(Date separationDate) {
		this.separationDate = separationDate;
	}
	public String getDiviation() {
		return diviation;
	}
	public void setDiviation(String diviation) {
		this.diviation = diviation;
	}
	public UserMaster getSeparatedBy() {
		return separatedBy;
	}
	public void setSeparatedBy(UserMaster separatedBy) {
		this.separatedBy = separatedBy;
	}
	

}
