package com.covideinfo.dto;

import java.util.List;

public class StudyActivityDataReviewDto {
	private Long studyActivityDataId;
	private List<CommentsDto> comments;
	public Long getStudyActivityDataId() {
		return studyActivityDataId;
	}
	public void setStudyActivityDataId(Long studyActivityDataId) {
		this.studyActivityDataId = studyActivityDataId;
	}
	public List<CommentsDto> getComments() {
		return comments;
	}
	public void setComments(List<CommentsDto> comments) {
		this.comments = comments;
	}
}
