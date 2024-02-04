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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="study_activites_parameters")
public class StudyActivityParameters implements Serializable {
	
	
	
	private static final long serialVersionUID = -910481308628543675L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_activity_parameters_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="study_activity")
	private StudyActivities studyActivity;
	@ManyToOne
	@JoinColumn(name="parameter_id")
	private GlobalParameter parameterId;
	@ManyToOne
	@JoinColumn(name="study_activity_timepoint")
	private StudyActivityTimePoints studyActivityTimePoint;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyActivities getStudyActivity() {
		return studyActivity;
	}
	public void setStudyActivity(StudyActivities studyActivity) {
		this.studyActivity = studyActivity;
	}
	public GlobalParameter getParameterId() {
		return parameterId;
	}
	public void setParameterId(GlobalParameter parameterId) {
		this.parameterId = parameterId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public StudyActivityTimePoints getStudyActivityTimePoint() {
		return studyActivityTimePoint;
	}
	public void setStudyActivityTimePoint(StudyActivityTimePoints studyActivityTimePoint) {
		this.studyActivityTimePoint = studyActivityTimePoint;
	}
	
	

}
