package com.covideinfo.model;

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
@Table(name = "epk_work_flow")
public class WorkFlow {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_work_flow_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="workFlowId")
	private long id;
	@Column(name="work_flow_name")
	private String name = "";
	@Column(name="work_flow_code")
	private String workFlowCodd;
	@ManyToOne
	@JoinColumn(name="activeStatus")
	private StatusMaster activeStatus;
	
	public String getWorkFlowCodd() {
		return workFlowCodd;
	}
	public void setWorkFlowCodd(String workFlowCodd) {
		this.workFlowCodd = workFlowCodd;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
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
	
}
