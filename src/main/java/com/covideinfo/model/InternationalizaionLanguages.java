package com.covideinfo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="internationalizaion_languages")
public class InternationalizaionLanguages implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="internationalizaion_languages_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="language")
	private String language;
	@Column(name="lang_code")
	private String langCode;
	@Column(name="country_code")
	private String countryCode;
	@Column(name="country")
	private String country;
	
	
	public InternationalizaionLanguages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InternationalizaionLanguages(String language, String langCode, String countryCode, String country) {
		super();
		this.language = language;
		this.langCode = langCode;
		this.countryCode = countryCode;
		this.country = country;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
