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
@Table(name = "form_static_data")
public class FromStaticData implements Serializable {
	private static final long serialVersionUID = -481875310213795511L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "form_static_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "formstaticDataId")
	private long id;
	private String fieldName;
	private String fieldValue;
	private String code;
	
	public FromStaticData(String fieldName, String fieldValue, String code) {
		
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.code = code;
	}

	public FromStaticData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
