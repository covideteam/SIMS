package com.covideinfo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "deviation_message")
public class DeviationMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2797935940217739848L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "deviation_message_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "deviationMessageId")
	private long id;

	private String collectionType = ""; //DOSING, ALL, MEALS, SAMPLES, VITALS
	private String code;
	private String developerCode;
	private String message;
	private boolean activeStatus = true;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getDeveloperCode() {
		return developerCode;
	}
	public void setDeveloperCode(String developerCode) {
		this.developerCode = developerCode;
	}
	
	
}
