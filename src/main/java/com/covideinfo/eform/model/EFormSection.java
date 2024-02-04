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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.covideinfo.model.CommonMaster;


@Entity
@Table(name = "EForm_Section")
public class EFormSection extends CommonMaster implements Comparable<EFormSection>  {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_Section_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="efromSectionId")
	private long id;
	
	@Column(columnDefinition="TEXT")
	private String name = "";
	@Column(columnDefinition="TEXT")
	private String title = "";
	@Column(columnDefinition="TEXT")
	private String hedding = "";
	@Column(columnDefinition="TEXT")
	private String subHedding = "";
	private int maxRows;
	private int maxColumns;
	@Column(name="sec_order")
	private int order;
	private boolean containsGroup;
	@OneToOne(cascade=CascadeType.ALL)
	private EFormGroup group;
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="section")
	private List<EFormSectionElement> element;
	private String gender = "ALL"; //MALE FEMALE 
	@Column(name = "is_active")	
	private boolean active = true;//status
	private String roles = "ALL";
	@ManyToOne
	@JoinColumn(name="eformId")
	private EForm eform;
	@Transient
	private boolean allowDataEntry;
	
	@Override
	public int compareTo(EFormSection o) {
		return ((Integer)this.getOrder()).compareTo((Integer)o.getOrder());
	}
	
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public boolean isContainsGroup() {
		return containsGroup;
	}
	public void setContainsGroup(boolean containsGroup) {
		this.containsGroup = containsGroup;
	}
	public EFormGroup getGroup() {
		return group;
	}
	public void setGroup(EFormGroup group) {
		this.group = group;
	}
	public List<EFormSectionElement> getElement() {
		return element;
	}
	public void setElement(List<EFormSectionElement> element) {
		this.element = element;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public boolean isAllowDataEntry() {
		return allowDataEntry;
	}
	public void setAllowDataEntry(boolean allowDataEntry) {
		this.allowDataEntry = allowDataEntry;
	}
	
	
	
}
