package com.covideinfo.dto;

import java.io.Serializable;

public class SampleTimepointDto implements Serializable, Comparable<SampleTimepointDto>{
	private int index;
	private Long id;
	private String barcode;
	private String projectNo;
	private String subjectNo;
	
	private String periodNo;
	private String timePoint;
	private String vacutinaerNo;
	private String treatment;
	private int subjectOrder;
	private int timePointNo;
	private boolean collectionStatus;
	private String seqNo;
	private String actualTp;
	private int subNo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isCollectionStatus() {
		return collectionStatus;
	}
	public void setCollectionStatus(boolean collectionStatus) {
		this.collectionStatus = collectionStatus;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(String periodNo) {
		this.periodNo = periodNo;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getVacutinaerNo() {
		return vacutinaerNo;
	}
	public void setVacutinaerNo(String vacutinaerNo) {
		this.vacutinaerNo = vacutinaerNo;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
		public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
		
		
		public int getSubjectOrder() {
			return subjectOrder;
		}
		public void setSubjectOrder(int subjectOrder) {
			this.subjectOrder = subjectOrder;
		}
		public int getTimePointNo() {
			return timePointNo;
		}
		public void setTimePointNo(int timePointNo) {
			this.timePointNo = timePointNo;
		}
		public String getActualTp() {
			return actualTp;
		}
		public void setActualTp(String actualTp) {
			this.actualTp = actualTp;
		}
		public SampleTimepointDto(int index, String barcode, String projectNo, String subjectNo, String periodNo,
				String timePoint, String vacutinaerNo, String treatment, int subjectOrder, int timePointNo,
				String seqNo, String actualTp, int subNo) {
			super();
			this.index = index;
			this.barcode = barcode;
			this.projectNo = projectNo;
			this.subjectNo = subjectNo;
			this.periodNo = periodNo;
			this.timePoint = timePoint;
			this.vacutinaerNo = vacutinaerNo;
			this.treatment = treatment;
			this.subjectOrder = subjectOrder;
			this.timePointNo = timePointNo;
			this.seqNo = seqNo;
			this.actualTp = actualTp;
			this.subNo = subNo;
		}
		
		public SampleTimepointDto(int index, String barcode, String projectNo, String subjectNo, String periodNo,
				String timePoint, String vacutinaerNo, String treatment, int subjectOrder, int timePointNo,
				String seqNo, String actualTp) {
			super();
			this.index = index;
			this.barcode = barcode;
			this.projectNo = projectNo;
			this.subjectNo = subjectNo;
			this.periodNo = periodNo;
			this.timePoint = timePoint;
			this.vacutinaerNo = vacutinaerNo;
			this.treatment = treatment;
			this.subjectOrder = subjectOrder;
			this.timePointNo = timePointNo;
			this.seqNo = seqNo;
			this.actualTp = actualTp;
			
		}
		
		public SampleTimepointDto(Long id, String timePoint) {
			super();
			this.id = id;
			this.timePoint = timePoint;
		}
		@Override
		public int compareTo(SampleTimepointDto o) {
			return Integer.compare(this.subNo, o.subNo);
		}
		public int getSubNo() {
			return subNo;
		}
		public void setSubNo(int subNo) {
			this.subNo = subNo;
		}
		
		
}
