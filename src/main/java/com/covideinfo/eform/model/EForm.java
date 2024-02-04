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

import org.springframework.web.multipart.MultipartFile;

import com.covideinfo.eform.service.EFormExcelSheet;
import com.covideinfo.model.CommonMaster;
import com.covideinfo.model.StudyMaster;

@Entity
@Table(name = "eform")
public class EForm extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eform_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eformId")
	private long id;
	
	@Column(columnDefinition="TEXT")
	private String name = "";
	@Column(columnDefinition="TEXT")
	private String title = "";
	private String type = ""; //clinical bio screen all
	private String gender = ""; //MALE FEMALE 
	private String version = "";
	@Column(name = "is_active")	
	private boolean active = true;//status
	@OneToMany(cascade=CascadeType.PERSIST,  mappedBy="eform")
	private List<EFormSection> sections;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster study;
	@Column(name="eform_key")
	private String eformKey;
	@Transient
	private DataSet d;
	@Transient
	private boolean periodEFormstatus;
	@Transient
	private boolean exitEForm;
	@Transient
	private MultipartFile file;
	@Transient
	private boolean flag;
	@Transient
	private String message = "";
	
	@Transient
	private boolean configure = false;
	@Transient
	private EFormExcelSheet exclData;
	
	public boolean isPeriodEFormstatus() {
		return periodEFormstatus;
	}
	public void setPeriodEFormstatus(boolean periodEFormstatus) {
		this.periodEFormstatus = periodEFormstatus;
	}
	public boolean isExitEForm() {
		return exitEForm;
	}
	public void setExitEForm(boolean exitEForm) {
		this.exitEForm = exitEForm;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<EFormSection> getSections() {
		return sections;
	}
	public void setSections(List<EFormSection> sections) {
		this.sections = sections;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public String getEformKey() {
		return eformKey;
	}
	public void setEformKey(String eformKey) {
		this.eformKey = eformKey;
	}
	public DataSet getD() {
		return d;
	}
	public void setD(DataSet d) {
		this.d = d;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isConfigure() {
		return configure;
	}
	public void setConfigure(boolean configure) {
		this.configure = configure;
	}
	public EFormExcelSheet getExclData() {
		return exclData;
	}
	public void setExclData(EFormExcelSheet exclData) {
		this.exclData = exclData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
		
}
