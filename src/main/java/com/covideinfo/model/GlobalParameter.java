package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="global_parameter")
public class GlobalParameter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -783917261285911669L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="global_parameter_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="parameter_name", length=10000)
	private String parameterName;
	@ManyToOne
	@JoinColumn(name="groups")
	private GlobalGroups groups;
	@ManyToOne
	@JoinColumn(name="content_type")
	private ControlType contentType; //RB, SB, TA, TB, DP,AC, CKE, CB, DPWTS
	@Column(name="bind_activity")
	private boolean bindActivity;
	@Column(name="order_no")
	private int orderNo;
	@ManyToOne
	@JoinColumn(name="activity")
	private GlobalActivity activity;
	@ManyToOne
	@JoinColumn(name="units_id")
	private UnitsMaster unitsId;
	@Column(name="parameter_phase")
	private String parameterPhase;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public Boolean allowDelete = true;
	public String parameterCode = "";
	
	@Column(name="control_name")
	private String controlName;
	@Column(name="mandatory", columnDefinition = "boolean default false")
	private boolean mandatory = false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public GlobalGroups getGroups() {
		return groups;
	}
	public void setGroups(GlobalGroups groups) {
		this.groups = groups;
	}
	
	public ControlType getContentType() {
		return contentType;
	}
	public void setContentType(ControlType contentType) {
		this.contentType = contentType;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
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
	public boolean isBindActivity() {
		return bindActivity;
	}
	public void setBindActivity(boolean bindActivity) {
		this.bindActivity = bindActivity;
	}
	public GlobalActivity getActivity() {
		return activity;
	}
	public void setActivity(GlobalActivity activity) {
		this.activity = activity;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getControlName() {
		return controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public UnitsMaster getUnitsId() {
		return unitsId;
	}
	public void setUnitsId(UnitsMaster unitsId) {
		this.unitsId = unitsId;
	}
	public String getParameterPhase() {
		return parameterPhase;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public void setParameterPhase(String parameterPhase) {
		this.parameterPhase = parameterPhase;
	}
	public Boolean getAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
}
