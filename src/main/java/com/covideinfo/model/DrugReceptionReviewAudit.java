package com.covideinfo.model;

import java.util.Date;

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
@Table(name = "DrugReception_ReviewAudit")
public class DrugReceptionReviewAudit {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="DrugReception_ReviewAudit_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	private long reviewState;
	private long drugid;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleMaster role;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserMaster user;
	@Column(name="date")
	private Date date;
	private String comment;
	private String status = "";
	@Column(name="in_the_flow")
	private boolean inTheFlow = true;
	
	public boolean isInTheFlow() {
		return inTheFlow;
	}
	public void setInTheFlow(boolean inTheFlow) {
		this.inTheFlow = inTheFlow;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getReviewState() {
		return reviewState;
	}
	public void setReviewState(long reviewState) {
		this.reviewState = reviewState;
	}
	public RoleMaster getRole() {
		return role;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	/*public long getStudyId() {
		return studyId;
	}
	public void setStudyId(long studyId) {
		this.studyId = studyId;
	}*/
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getDrugid() {
		return drugid;
	}
	public void setDrugid(long drugid) {
		this.drugid = drugid;
	}
	
	
	
}
