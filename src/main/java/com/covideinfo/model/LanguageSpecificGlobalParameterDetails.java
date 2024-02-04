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
@Table(name="language_specific_global_parameter_details")
public class LanguageSpecificGlobalParameterDetails implements Serializable, Comparable<LanguageSpecificGlobalParameterDetails> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6289661494026248228L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="language_specific_global_parameter_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="name", length=10000)
	private String name;
	@ManyToOne
	@JoinColumn(name="lang_id")
	private InternationalizaionLanguages inlagId;
	@ManyToOne
	@JoinColumn(name="parameter_id")
	private GlobalParameter parameterId;
	@Column(name="order_no")
	private int orderNo;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InternationalizaionLanguages getInlagId() {
		return inlagId;
	}
	public void setInlagId(InternationalizaionLanguages inlagId) {
		this.inlagId = inlagId;
	}
	public GlobalParameter getParameterId() {
		return parameterId;
	}
	public void setParameterId(GlobalParameter parameterId) {
		this.parameterId = parameterId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreatedOn() {
		return createdOn;
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
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public int compareTo(LanguageSpecificGlobalParameterDetails o) {
		return Integer.compare(this.getOrderNo(), o.getOrderNo());
	}


}
