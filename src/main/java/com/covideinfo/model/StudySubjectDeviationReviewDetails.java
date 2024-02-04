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

@SuppressWarnings("serial")
@Entity
@Table(name="study_subject_deviation_review_details")
public class StudySubjectDeviationReviewDetails implements Serializable {
	
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_subject_deviation_review_details_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name="comments", length=10000)
	private String comments;
	@ManyToOne
	@JoinColumn(name="ssdevation")
	private StudySubjectDeviations ssdevation;
	@ManyToOne
	@JoinColumn(name="commented_by")
	private UserMaster commentedBy;
	@Column(name="commented_on")
	private Date commentedOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public StudySubjectDeviations getSsdevation() {
		return ssdevation;
	}
	public void setSsdevation(StudySubjectDeviations ssdevation) {
		this.ssdevation = ssdevation;
	}
	
	public UserMaster getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(UserMaster commentedBy) {
		this.commentedBy = commentedBy;
	}
	public Date getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}
	
	

}
