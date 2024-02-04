package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="useractivity")
public class UserActivity implements Serializable{
	
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "useractivity_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long sno;
	@Column(name="userName")
	private String userName;
	@Column(name="userid")
	private long userid;
	@Column(name="login_date")
	private Date loginDate;
	@Column(name="logout_date")
	private Date logoutDate;
	@Column(name="user_role")
	private String userRole;

	public UserActivity() {
	}

	public UserActivity(String userName, Date loginDate, String userRole) {
		this.userName = userName;
		this.loginDate = loginDate;
		this.userRole = userRole;
	}

	public UserActivity(String userName, Date loginDate, Date logoutDate,
			String userRole) {
		this.userName = userName;
		this.loginDate = loginDate;
		this.logoutDate = logoutDate;
		this.userRole = userRole;
	}

	public Long getSno() {
		return sno;
	}

	public void setSno(Long sno) {
		this.sno = sno;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setSno(long sno) {
		this.sno = sno;
	}
	
	
	
}
