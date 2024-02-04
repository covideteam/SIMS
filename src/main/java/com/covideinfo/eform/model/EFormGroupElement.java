package com.covideinfo.eform.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.covideinfo.model.CommonMaster;


@Entity
@Table(name = "EForm_GROUP_ELEMENT")
public class EFormGroupElement extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_GROUP_ELEMENT_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eformGroupElementId")
	private long id;
	
	@Column(columnDefinition="TEXT")
	private String sno = "";
	@Column(name="ele_name")
	private String name;
	@Column(name="ele_title")
	private String title;
	@Column(name="ele_rowNo")
	private int rowNo;
	@Column(name="ele_columnNo")
	private int columnNo;
	@Column(name="ele_type")
	private String type= "";// text/textArea/radio/select/checkBox/date/dateAndTime/non
	private String dataType = "String"; // Number/String
	private String responceType;
	@Column(name="ele_display")
	private String display="horizantal";// vertical
	@Column(name="ele_values")
	private String values;
	@Column(name="ele_pattren")
	private String pattren;
	@Column(name="ele_required")
	private boolean required;
	private boolean discrepancy;
	private boolean commentRequired;
	private String commentValue = "";
	private int targetField = 0;
	private String typeOfTime = "";
	private boolean visiblity = false;
	private String roles = "";
	@Transient
	private EForm eform;
	@Transient
	private EFormSection section;
	@ManyToOne
	@JoinColumn(name="eform_group")
	private EFormGroup group;
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="groupElement")
	private List<EFormGroupElementValue> elementValues;
	@Transient
	private boolean seclectionStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public int getColumnNo() {
		return columnNo;
	}
	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getResponceType() {
		return responceType;
	}
	public void setResponceType(String responceType) {
		this.responceType = responceType;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String getPattren() {
		return pattren;
	}
	public void setPattren(String pattren) {
		this.pattren = pattren;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isDiscrepancy() {
		return discrepancy;
	}
	public void setDiscrepancy(boolean discrepancy) {
		this.discrepancy = discrepancy;
	}
	public boolean isCommentRequired() {
		return commentRequired;
	}
	public void setCommentRequired(boolean commentRequired) {
		this.commentRequired = commentRequired;
	}
	public String getCommentValue() {
		return commentValue;
	}
	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}
	public int getTargetField() {
		return targetField;
	}
	public void setTargetField(int targetField) {
		this.targetField = targetField;
	}
	public String getTypeOfTime() {
		return typeOfTime;
	}
	public void setTypeOfTime(String typeOfTime) {
		this.typeOfTime = typeOfTime;
	}
	public boolean isVisiblity() {
		return visiblity;
	}
	public void setVisiblity(boolean visiblity) {
		this.visiblity = visiblity;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public EForm getEform() {
		return eform;
	}
	public void setEform(EForm eform) {
		this.eform = eform;
	}
	public EFormSection getSection() {
		return section;
	}
	public void setSection(EFormSection section) {
		this.section = section;
	}
	public EFormGroup getGroup() {
		return group;
	}
	public void setGroup(EFormGroup group) {
		this.group = group;
	}
	public List<EFormGroupElementValue> getElementValues() {
		return elementValues;
	}
	public void setElementValues(List<EFormGroupElementValue> elementValues) {
		this.elementValues = elementValues;
	}
	public boolean isSeclectionStatus() {
		return seclectionStatus;
	}
	public void setSeclectionStatus(boolean seclectionStatus) {
		this.seclectionStatus = seclectionStatus;
	}
		
}
