package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="global_groups")
public class GlobalGroups implements Serializable, Comparable<GlobalGroups> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670596539539838800L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="global_groups_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="type")
	private String type;
	@Column(name="order_no")
	private int orderNo;
	@Column(name="noofrows")
	private int noofrows;
	@Column(name="noofcolums")
	private int noofcolums;
	
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
	public String getType() {
		return type;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public int compareTo(GlobalGroups o) {
		return this.orderNo = o.orderNo;
	}
	public int getNoofrows() {
		return noofrows;
	}
	public void setNoofrows(int noofrows) {
		this.noofrows = noofrows;
	}
	public int getNoofcolums() {
		return noofcolums;
	}
	public void setNoofcolums(int noofcolums) {
		this.noofcolums = noofcolums;
	}
	
	

}
