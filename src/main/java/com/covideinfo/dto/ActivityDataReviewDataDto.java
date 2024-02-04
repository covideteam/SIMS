package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ActivityDataReviewDataDto implements Serializable {
	
	private FormGroupsDto group;
	private ParameterDto parameters;
	private FromControlDto controlType;
	private List<CommentsDto> comments;
	
	public FormGroupsDto getGroup() {
		return group;
	}
	public ParameterDto getParameters() {
		return parameters;
	}
	public FromControlDto getControlType() {
		return controlType;
	}
	public void setGroup(FormGroupsDto group) {
		this.group = group;
	}
	public void setParameters(ParameterDto parameters) {
		this.parameters = parameters;
	}
	public void setControlType(FromControlDto controlType) {
		this.controlType = controlType;
	}
	public List<CommentsDto> getComments() {
		return comments;
	}
	public void setComments(List<CommentsDto> comments) {
		this.comments = comments;
	}
}
