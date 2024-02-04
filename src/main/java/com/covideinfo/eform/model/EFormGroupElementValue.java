package com.covideinfo.eform.model;

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
@Table(name = "EForm_GROUP_ELEMENT_VALUE")
public class EFormGroupElementValue extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_GROUP_ELEMENT_VALUE_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eformGroupElementValueID")
	private long id;
	
	private String value = "";
	@ManyToOne
	@JoinColumn(name="groupElement")
	private EFormGroupElement groupElement;
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
	public EFormGroupElement getGroupElement() {
		return groupElement;
	}
	public void setGroupElement(EFormGroupElement groupElement) {
		this.groupElement = groupElement;
	}

	
}
