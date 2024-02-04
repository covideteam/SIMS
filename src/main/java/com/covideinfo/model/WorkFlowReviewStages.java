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
@Table(name = "epk_work_flow_Review_Stages")
public class WorkFlowReviewStages extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_work_flow_Review_Stages_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="workFlowReviewStagesId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "fromRole_id")
	private RoleMaster fromRole;
	@ManyToOne
	@JoinColumn(name = "toRole_id")
	private RoleMaster toRole;
	@ManyToOne
	@JoinColumn(name = "workFlow_id")
	private WorkFlow workFlow;
	@Column(name="action_perform")
	private String action = "ACCEPT";
	@Column(name="active_status")
	private boolean activeStatus = true;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public RoleMaster getFromRole() {
		return fromRole;
	}
	public void setFromRole(RoleMaster fromRole) {
		this.fromRole = fromRole;
	}
	public RoleMaster getToRole() {
		return toRole;
	}
	public void setToRole(RoleMaster toRole) {
		this.toRole = toRole;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public WorkFlow getWorkFlow() {
		return workFlow;
	}
	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	
}
