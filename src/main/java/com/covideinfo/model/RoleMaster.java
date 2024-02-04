package com.covideinfo.model;

import java.io.Serializable;
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "epk_role_master")
public class RoleMaster extends CommonMaster {
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_role_master_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "roleId")
	private long id;

	@Column(unique = true, nullable = false)
	private String role;
	private String roleDesc;
	@ManyToOne
	@JoinColumn(name = "roleStatus")
	private StatusMaster roleStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public StatusMaster getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(StatusMaster roleStatus) {
		this.roleStatus = roleStatus;
	}

}
