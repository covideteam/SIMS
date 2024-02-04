package com.covideinfo.dto;
import java.util.List;

import com.covide.dto.ProjectParametersDto;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StudyDiscrepancy;

public class ProjectDetailsDto {
	private List<ProjectsDetails> projectDetails;
	private List<CommentsDto> studyDiscrepancies;
	private List<ProjectParametersDto> parameters;
	
	public List<ProjectParametersDto> getParameters() {
		return parameters;
	}
	public void setParameters(List<ProjectParametersDto> parameters) {
		this.parameters = parameters;
	}
	public List<ProjectsDetails> getProjectDetails() {
		return projectDetails;
	}
	public void setProjectDetails(List<ProjectsDetails> projectDetails) {
		this.projectDetails = projectDetails;
	}
	public List<CommentsDto> getStudyDiscrepancies() {
		return studyDiscrepancies;
	}
	public void setStudyDiscrepancies(List<CommentsDto> studyDiscrepancies) {
		this.studyDiscrepancies = studyDiscrepancies;
	}
}
