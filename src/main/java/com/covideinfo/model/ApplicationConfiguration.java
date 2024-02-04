package com.covideinfo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="application_configuration")
public class ApplicationConfiguration implements Serializable {
	
	private static final long serialVersionUID = -6622820783530420878L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "application_configuration_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name="configuration")
	private String configuration;
	@Column(name="config_code")
	private String configCode;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status;
	
	public ApplicationConfiguration() {
		
	}
	
	
	public ApplicationConfiguration(String configuration, String configCode, StatusMaster status) {
		super();
		this.configuration = configuration;
		this.configCode = configCode;
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public String getConfiguration() {
		return configuration;
	}
	public String getConfigCode() {
		return configCode;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}

}
