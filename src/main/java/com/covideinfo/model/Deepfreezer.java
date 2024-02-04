package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="DEEPFREEZER")
public class Deepfreezer implements Serializable {
	
	private static final long serialVersionUID = -148062389524429980L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Deepfreezer_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="instrumentId")
	private Instrument instrument;
	private int rackNo;
	private String rackDesc;
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public int getRackNo() {
		return rackNo;
	}
	public void setRackNo(int rackNo) {
		this.rackNo = rackNo;
	}
	public String getRackDesc() {
		return rackDesc;
	}
	public void setRackDesc(String rackDesc) {
		this.rackDesc = rackDesc;
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
