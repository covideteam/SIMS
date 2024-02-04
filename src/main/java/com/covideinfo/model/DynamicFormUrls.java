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
@Table(name="dynamic_form_urls")
public class DynamicFormUrls implements Serializable {
	
	private static final long serialVersionUID = -91654758910847134L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="dynamic_form_urls_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@Column(name="activity_code")
	private String activityCode;
	@Column(name="get_url")
	private String getUrl;
	@Column(name="save_url")
	private String saveUrl;
	
	public DynamicFormUrls() {
		
	}
	public DynamicFormUrls(String activityCode, String getUrl, String saveUrl) {
		this.activityCode = activityCode;
		this.getUrl = getUrl;
		this.saveUrl = saveUrl;
	}
	public Long getId() {
		return id;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public String getSaveUrl() {
		return saveUrl;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}
	
	

}
