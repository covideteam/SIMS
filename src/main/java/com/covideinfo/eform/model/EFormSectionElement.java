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
@Table(name = "EForm_SECTION_ELEMENT")
public class EFormSectionElement extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_SECTION_ELEMENT_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eformSectionElementId")
	private long id;
	@Column(columnDefinition="TEXT")
	private String sno = "";
	
	@Column(columnDefinition="TEXT")
	private String name = "";
	@Column(columnDefinition="TEXT")
	private String leftDesc = "";
	@Column(columnDefinition="TEXT")
	private String rigtDesc = "";
	@Column(columnDefinition="TEXT")
	private String middeDesc = "";
	@Column(columnDefinition="TEXT")
	private String totalDesc = ""; //  left+middle+right
	@Column(columnDefinition="TEXT")
	private String topDesc = "";
	@Column(columnDefinition="TEXT")
	private String bottemDesc = "";
	private int rowNo;
	private int columnNo;
	@Column(name="ele_type")
	private String type= "non";// text/textArea/radio/select/checkBox/date/dateAndTime/non/selectTable
	private String dataType = "String"; // Number/String
	private String responceType = "";
	@Column(name="ele_display")
	private String display="horizantal";// vertical
	@Column(name="ele_values")
	private String values = "";
	private String pattren = "";
	private boolean required;
	private boolean discrepancy;
	private boolean commentRequired;
	private String commentValue = "";
	private int targetField = 0;
	private String typeOfTime = "";
	private boolean visibility = false;
	private boolean showHide = false;
	private String roles = "";
	private String controllerWidth;
	private String lableWidth;
	private String columnWidth;
	private String pdfControllerWidth;
	private String pdflLableWidth;
	private String pdfColumnWidth;
	
	
	
	@Transient
	private EForm eform;
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="sectionElement")
	private List<EFormSectionElementValue> elementValues; 
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="sectionId")
	private EFormSection section;
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
	public String getLeftDesc() {
		return leftDesc;
	}
	public void setLeftDesc(String leftDesc) {
		this.leftDesc = leftDesc;
	}
	public String getRigtDesc() {
		return rigtDesc;
	}
	public void setRigtDesc(String rigtDesc) {
		this.rigtDesc = rigtDesc;
	}
	public String getMiddeDesc() {
		return middeDesc;
	}
	public void setMiddeDesc(String middeDesc) {
		this.middeDesc = middeDesc;
	}
	public String getTotalDesc() {
		return totalDesc;
	}
	public void setTotalDesc(String totalDesc) {
		this.totalDesc = totalDesc;
	}
	public String getTopDesc() {
		return topDesc;
	}
	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
	}
	public String getBottemDesc() {
		return bottemDesc;
	}
	public void setBottemDesc(String bottemDesc) {
		this.bottemDesc = bottemDesc;
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
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public boolean isShowHide() {
		return showHide;
	}
	public void setShowHide(boolean showHide) {
		this.showHide = showHide;
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
	
	
	public List<EFormSectionElementValue> getElementValues() {
		return elementValues;
	}
	public void setElementValues(List<EFormSectionElementValue> elementValues) {
		this.elementValues = elementValues;
	}
	public EFormSection getSection() {
		return section;
	}
	public void setSection(EFormSection section) {
		this.section = section;
	}
	public boolean isSeclectionStatus() {
		return seclectionStatus;
	}
	public void setSeclectionStatus(boolean seclectionStatus) {
		this.seclectionStatus = seclectionStatus;
	}
	public String getControllerWidth() {
		return controllerWidth;
	}
	public void setControllerWidth(String controllerWidth) {
		this.controllerWidth = controllerWidth;
	}
	public String getLableWidth() {
		return lableWidth;
	}
	public void setLableWidth(String lableWidth) {
		this.lableWidth = lableWidth;
	}
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	public String getPdfControllerWidth() {
		return pdfControllerWidth;
	}
	public void setPdfControllerWidth(String pdfControllerWidth) {
		this.pdfControllerWidth = pdfControllerWidth;
	}
	public String getPdflLableWidth() {
		return pdflLableWidth;
	}
	public void setPdflLableWidth(String pdflLableWidth) {
		this.pdflLableWidth = pdflLableWidth;
	}
	public String getPdfColumnWidth() {
		return pdfColumnWidth;
	}
	public void setPdfColumnWidth(String pdfColumnWidth) {
		this.pdfColumnWidth = pdfColumnWidth;
	}
	
	
}
