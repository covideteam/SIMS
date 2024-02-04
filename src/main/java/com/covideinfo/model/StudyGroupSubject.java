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
@Table(name="epk_Study_Group_Subject")
public class StudyGroupSubject extends CommonMaster{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1591705710949346593L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_Study_Group_Subject_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "studyGroupSubjectId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "studyGroupId")
	private StudyGroup studyGroup;
	@ManyToOne
	@JoinColumn(name = "volunteerId")
	private Volunteer volunteer;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyGroup getStudyGroup() {
		return studyGroup;
	}
	public void setStudyGroup(StudyGroup studyGroup) {
		this.studyGroup = studyGroup;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	
}
