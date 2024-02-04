package com.covideinfo.eform.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.covideinfo.model.CommonMaster;

@Entity
@Table(name = "EFORM_SECTION_ELEMENT_VALUE")
public class EFormSectionElementValue extends CommonMaster {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EFORM_SECTION_ELEMENT_VALUE_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eFormSectionElementValueId")
	private long id;
	
	
	private String value = "";
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="sectionElement")
	private EFormSectionElement sectionElement;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public EFormSectionElement getSectionElement() {
		return sectionElement;
	}
	public void setSectionElement(EFormSectionElement sectionElement) {
		this.sectionElement = sectionElement;
	}
	
	
	
}
