package com.covideinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "epk_centrifugation")
public class Centrifugation extends CommonMaster {
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_centrifugation_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "centrifugationId")
	private Long id;
	
	@Column(name = "centrifugationBarcode")
	private String centrifugationBarcode;
	private String name = "";
	private String code = "";
	@Column(name = "insturmentDesc", length = 10000)
	private String insturmentDesc = "";
	private boolean activeStatus = true;

	public Centrifugation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Centrifugation(String name, String code, String insturmentDesc, boolean activeStatus) {
		super();
		this.name = name;
		this.code = code;
		this.insturmentDesc = insturmentDesc;
		this.activeStatus = activeStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCentrifugationBarcode() {
		return centrifugationBarcode;
	}

	public void setCentrifugationBarcode(String centrifugationBarcode) {
		this.centrifugationBarcode = centrifugationBarcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInsturmentDesc() {
		return insturmentDesc;
	}

	public void setInsturmentDesc(String insturmentDesc) {
		this.insturmentDesc = insturmentDesc;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

}
