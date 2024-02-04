package com.covideinfo.eform.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.covideinfo.model.CommonMaster;

@Entity
@Table(name = "EForm_GROUP")
public class EFormGroup extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_GROUP_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eformGroupId")
	private long id;
	
	@Column(columnDefinition="TEXT")
	private String name = "";
	@Column(columnDefinition="TEXT")
	private String title = "";
	@Column(columnDefinition="TEXT")
	private String hedding = "";
	@Column(columnDefinition="TEXT")
	private String subHedding = "";
	private int minRows;
	private int maxRows;
	private int maxColumns;
	private int sectionId;
	@Transient
	private EFormSection section;
	@Transient
	private int displayedRows;
	@Transient
	private EForm eform;
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="group")
	private List<EFormGroupElement> element;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHedding() {
		return hedding;
	}
	public void setHedding(String hedding) {
		this.hedding = hedding;
	}
	public String getSubHedding() {
		return subHedding;
	}
	public void setSubHedding(String subHedding) {
		this.subHedding = subHedding;
	}
	public int getMinRows() {
		return minRows;
	}
	public void setMinRows(int minRows) {
		this.minRows = minRows;
	}
	public int getMaxRows() {
		return maxRows;
	}
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}
	public int getMaxColumns() {
		return maxColumns;
	}
	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public EFormSection getSection() {
		return section;
	}
	public void setSection(EFormSection section) {
		this.section = section;
	}
	public int getDisplayedRows() {
		return displayedRows;
	}
	public void setDisplayedRows(int displayedRows) {
		this.displayedRows = displayedRows;
	}
	public EForm getEform() {
		return eform;
	}
	public void setEform(EForm eform) {
		this.eform = eform;
	}
	public List<EFormGroupElement> getElement() {
		return element;
	}
	public void setElement(List<EFormGroupElement> element) {
		this.element = element;
	}
		
}
