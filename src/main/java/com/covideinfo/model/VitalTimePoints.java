package com.covideinfo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "study_vital_time_points")
public class VitalTimePoints  implements Serializable, Comparable<VitalTimePoints>{
	
	private static final long serialVersionUID = 1259790261772021966L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_vital_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "vitalTimePointsId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "vital_position")
	private FromStaticData vitalPosition;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	private String timePoint = "";
	@ManyToOne
	@JoinColumn(name = "timePointType")
	private FromStaticData timePointType;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	private int timePointNo;
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "vitalTimePointId")
	private List<TimePointVitalTests> test = new ArrayList<TimePointVitalTests>();*/
	private boolean orthostatic;
	@ManyToOne
	@JoinColumn(name = "orthostaticPosition")
	private FromStaticData orthostaticPosition;
	@Column(name="parameter_ids")
	private String parameterIds;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	private String updateReason;
	@Transient
	private Double vtpVal;
	@Transient
	private String tpWithScheduleTime;

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

	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}

	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

	
	public FromStaticData getTimePointType() {
		return timePointType;
	}

	public void setTimePointType(FromStaticData timePointType) {
		this.timePointType = timePointType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}

	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
	}

	public int getWindowPeriod() {
		return windowPeriod;
	}

	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}

	

	public FromStaticData getWindowPeriodType() {
		return windowPeriodType;
	}

	public int getTimePointNo() {
		return timePointNo;
	}

	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}

	/*public List<TimePointVitalTests> getTest() {
		return test;
	}

	public void setTest(List<TimePointVitalTests> test) {
		this.test = test;
	}
*/
	public boolean isOrthostatic() {
		return orthostatic;
	}

	public void setOrthostatic(boolean orthostatic) {
		this.orthostatic = orthostatic;
	}

	public String getParameterIds() {
		return parameterIds;
	}

	public void setParameterIds(String parameterIds) {
		this.parameterIds = parameterIds;
	}

	public FromStaticData getOrthostaticPosition() {
		return orthostaticPosition;
	}

	public void setOrthostaticPosition(FromStaticData orthostaticPosition) {
		this.orthostaticPosition = orthostaticPosition;
	}

	public void setWindowPeriodType(FromStaticData windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}

	@Override
	public int compareTo(VitalTimePoints o) {
		// TODO Auto-generated method stub
		return this.getTimePointNo() - o.getTimePointNo();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public FromStaticData getVitalPosition() {
		return vitalPosition;
	}

	public void setVitalPosition(FromStaticData vitalPosition) {
		this.vitalPosition = vitalPosition;
	}

	public Double getVtpVal() {
		return vtpVal;
	}

	public void setVtpVal(Double vtpVal) {
		this.vtpVal = vtpVal;
	}

	public String getTpWithScheduleTime() {
		return tpWithScheduleTime;
	}

	public void setTpWithScheduleTime(String tpWithScheduleTime) {
		this.tpWithScheduleTime = tpWithScheduleTime;
	}
}
