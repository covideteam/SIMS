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

@SuppressWarnings("serial")
@Entity
@Table(name = "static_activity_value_details")
public class StaticActivityValueDetails implements Serializable {

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="static_activity_value_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@Column(name="value_name")
	private String valueName;
	@ManyToOne
	@JoinColumn(name="static_activity_id")
	private StaticActivityDetails stActDetailsId;
	@Column(name="created_by")
    private String createdBy;
    @Column(name="created_on")
    private Date createdOn;
    public StaticActivityValueDetails() {
    	
    }
	public StaticActivityValueDetails(String valueName, StaticActivityDetails stActDetailsId, String createdBy,
			Date createdOn) {
		super();
		this.valueName = valueName;
		this.stActDetailsId = stActDetailsId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public StaticActivityDetails getStActDetailsId() {
		return stActDetailsId;
	}
	public void setStActDetailsId(StaticActivityDetails stActDetailsId) {
		this.stActDetailsId = stActDetailsId;
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
    
    
	
	
}
