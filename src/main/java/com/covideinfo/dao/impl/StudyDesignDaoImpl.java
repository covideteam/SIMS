package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.internal.resources.Project;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.covide.dto.ProjectParametersDto;
import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyDesignDao;
import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.ProjectDetailsDto;
import com.covideinfo.dto.StudyDesignDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DiscrepancyModel;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.ProjectDetailsKeyVal;
import com.covideinfo.model.dummy.ProjectKeyVal;
import com.covideinfo.util.DateFormatUtil;

@Repository("studyDesignDao")
public class StudyDesignDaoImpl extends AbstractDao<Long, Projects> implements StudyDesignDao {

	@Override
	public boolean getAutoSaveData(Projects ps, List<ProjectsDetails> pdp) {
		boolean flag=false;
		try {
			getSession().save(ps);
			for(ProjectsDetails projectsDetail : pdp) {
				getSession().save(projectsDetail);
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	/**
	 * Returns project details along with discrepancies
	 * @param projectId
	 * @return project details dto
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ProjectDetailsDto getProjectsDetails(Long projectId, Long languageId,String format) {
		Projects project = (Projects)getSession().createCriteria(Projects.class).add(Restrictions.eq("id", projectId)).uniqueResult();
		
		List<ProjectsDetails> projectDetails = getSession().createCriteria(ProjectsDetails.class)
		        		 .add(Restrictions.eq("projectsId.projectId", projectId))
		        		 .add(Restrictions.eq("status", true))
		        		 .addOrder(Order.asc("type"))
						 .addOrder(Order.asc("rowNo"))
						 .addOrder(Order.asc("subRowNo"))
		        		 .addOrder(Order.asc("fieldOrder")).list();
		
		List<StudyDiscrepancy> studyDiscrepancies = getSession().createCriteria(StudyDiscrepancy.class)
			  		 .add(Restrictions.eq("project", project))
			  		 .add(Restrictions.ne("status", "deleted"))
			  		 .addOrder(Order.asc("createdOn")).list();
		
		List<Long> parameters = new ArrayList<>(); 
		for(ProjectsDetails projectDetail: projectDetails) {
			if(projectDetail.getStatus() && (projectDetail.getType().equals("DefaultActivity") || projectDetail.getType().equals("OtherActivity"))) {
				if(projectDetail.getFieldValue() != null && !projectDetail.getFieldValue().equals(""))
					parameters.add(Long.parseLong(projectDetail.getFieldValue()));
			}
		}
		ProjectDetailsDto projectDetailDto=new ProjectDetailsDto();
		if(parameters.size() > 0) {
			List<LanguageSpecificGlobalParameterDetails> globalParameters = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					 											.add(Restrictions.in("parameterId.id", parameters))
					 											.add(Restrictions.eq("inlagId.id", languageId)).list();
			
			List<ProjectParametersDto> projectParameters = new ArrayList<>();
			
			for(LanguageSpecificGlobalParameterDetails globalParameter:globalParameters) {
				projectParameters.add(new ProjectParametersDto(globalParameter));
			}
			projectDetailDto.setParameters(projectParameters);
		}
		
		projectDetailDto.setProjectDetails(projectDetails);
		
		List<CommentsDto> comments =new ArrayList<>();
		for(StudyDiscrepancy discrepancy:studyDiscrepancies) {
			CommentsDto comment =new CommentsDto();
			comment.setComment(discrepancy.getComments());
			comment.setCommentId(discrepancy.getId());
			comment.setCommentedBy(discrepancy.getCreatedBy());
			comment.setRespondedBy(discrepancy.getUpdateBy());
			comment.setResponse(discrepancy.getResponse());
			comment.setCommentedOn(DateFormatUtil.ConvertDate(discrepancy.getCreatedOn(), format));
			comment.setRespondedOn(DateFormatUtil.ConvertDate(discrepancy.getUpdateOn(), format));
			comments.add(comment);
		}
		
		projectDetailDto.setStudyDiscrepancies(comments);
		return projectDetailDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectsList(long roleid) {
		List<Projects> plist=null;
		plist=(List<Projects>) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("roleId",roleid)) //Approval Role
				.list();
       return plist;
	}

	@Override
	public Projects getProjectWithId(Long projectId) {
		Projects ppojo=null;
		ppojo=(Projects) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectId", projectId)).uniqueResult();
		
		return ppojo;
	}

	@Override
	public Projects getProjectsWithProjectNo(String prod1) {
		Projects ppojo=null;
		ppojo=(Projects) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectNo", prod1)).uniqueResult();
		
		return ppojo;
		}

	@Override
	public ProjectsDetails getProjectsDetails(Projects ppojo, String prod2, int prod4, int prod5,String type) {
		ProjectsDetails pdpojo=null;
		pdpojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId", ppojo))
				.add(Restrictions.eq("fieldName", prod2))
				.add(Restrictions.eq("rowNo", prod4))
				.add(Restrictions.eq("subRowNo", prod5))
				.add(Restrictions.eq("status", true))
				.add(Restrictions.eq("type", type)).uniqueResult();
		
		return pdpojo;
	}

	@Override
	public boolean getUpdateAutoSaveData(ProjectsDetails pd) {
		boolean flag=false;
		Projects project = null;
		try {
			getSession().update(pd);
			project = pd.getProjectsId();
			project.setUpdatedBy(pd.getCreatedBy());
			project.setUpdatedOn(new Date());
			getSession().merge(project);
			flag=true;
			
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean getNewAutoSaveData(ProjectsDetails pdp) {
		boolean flag=false;
		try {
			getSession().save(pdp);
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	@Override
	public ProjectsDetails getProjectsDetailsWithpdId(long id) {
		ProjectsDetails pdpojo=null;
		pdpojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectDetailsId", id)).uniqueResult();
		
		return pdpojo;
	}

	@Override
	public StudyDiscrepancy saveDiscrepancy(CommentsDto projectKeyValue,String username) {
		Projects project = getProjectWithId(projectKeyValue.getProjectId());
		StudyDiscrepancy studyDescrepancy=new StudyDiscrepancy();
		studyDescrepancy.setComments(projectKeyValue.getComment());
		studyDescrepancy.setProject(project);
		studyDescrepancy.setStatus("open");
		studyDescrepancy.setCreatedBy(username);
		studyDescrepancy.setCreatedOn(new Date());
		getSession().save(studyDescrepancy);
		return studyDescrepancy;
	}

	@Override
	public StudyDiscrepancy getStudyDiscrepancyWithId(long id) {
		StudyDiscrepancy sdpojo=null;
		sdpojo=(StudyDiscrepancy) getSession().createCriteria(StudyDiscrepancy.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		
		return sdpojo;
	}
	
	@Override
	public void deleteComment(long commentId, String username) {
		StudyDiscrepancy sdpojo=(StudyDiscrepancy) getSession().createCriteria(StudyDiscrepancy.class)
				.add(Restrictions.eq("id", commentId)).uniqueResult();
		if(sdpojo!=null) {
			sdpojo.setStatus("deleted");
			sdpojo.setUpdateBy(username);
			sdpojo.setUpdateOn(new Date());
			getSession().update(sdpojo);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DiscrepancyModel> getDescrepencies(ProjectKeyVal projectKeyValue, Environment environment) {
		List<StudyDiscrepancy> descrepancies = (List<StudyDiscrepancy>)getSession().createCriteria(StudyDiscrepancy.class)
												.add(Restrictions.eq("project.projectId", projectKeyValue.getProjectId())).list();
		List<DiscrepancyModel> discrepancyList=new ArrayList<>();
		for(StudyDiscrepancy discrepancy: descrepancies){
			discrepancyList.add(new DiscrepancyModel(discrepancy, environment));
		}
		
		return discrepancyList;
	}
	
	@Override
	public boolean updateDiscrepancy(StudyDiscrepancy sdpojo) {
		boolean flag=false;
		try {
			getSession().update(sdpojo);
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	@Override
	public DraftReviewStage getDraftReviewStageWithRoleidAndActionname(long roleid, String actionname) {
		DraftReviewStage dr=null;
		dr=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole.id", roleid))
				.add(Restrictions.eq("action", actionname))
				.add(Restrictions.eq("name", "StudyDesign"))
				.uniqueResult();
		
		return dr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Projects saveActivityDraftReviewAudit(ActivityDraftReviewAudit adra,Projects poject) {
		try {
			getSession().save(adra);
			getSession().update(poject);
			if(poject.getRoleId()==0) { //0 = Last level approval, RoleId= Approval Level
				poject = changeProjectsStatsu(poject, StudyStatus.APPROVED.toString());
			}
			
			
			List<StudyDiscrepancy> studyDiscrepancyList= getSession().createCriteria(StudyDiscrepancy.class)
															.add(Restrictions.eq("project.id", poject.getProjectId()))
															.add(Restrictions.eq("isResponseSubmitted", false)).list();
			
			for(StudyDiscrepancy studyDiscrepancy: studyDiscrepancyList) {
				if(studyDiscrepancy.getResponse() != null){
					studyDiscrepancy.setResponseSubmitted(true);
				}
			}
			return poject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poject;
	}

	
	private Projects changeProjectsStatsu(Projects project, String status) {
		if(status != null) {
			StatusMaster st = (StatusMaster) getSession().createCriteria(StatusMaster.class).add(Restrictions.eq("statusCode", status)).uniqueResult();
			project.setStatus(st);
		}
		getSession().merge(project);
		return project;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyDiscrepancy> getStudyDiscrepancyExitOrNot(Long projectId) {
		List<StudyDiscrepancy> sd=null;
		sd=(List<StudyDiscrepancy>) getSession().createCriteria(StudyDiscrepancy.class)
				.add(Restrictions.eq("project.projectId", projectId))
				.add(Restrictions.eq("status", "open")).list();
		
		return sd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getEligibleProjectList() {
		List<Projects> plist=null;
		plist=(List<Projects>) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("status", 12)).uniqueResult();
		
		return plist;
	}

	@Override
	public ProjectsDetails getProjectsDetails(long id, String prod2, int prod4, int prod5,String type) {
		ProjectsDetails pdpojo=null;
	try {
		pdpojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId.projectId", id))
				.add(Restrictions.eq("fieldName", prod2))
				.add(Restrictions.eq("rowNo", prod4))
				.add(Restrictions.eq("subRowNo", prod5))
				.add(Restrictions.eq("status", true))
				.add(Restrictions.eq("type",  type)).uniqueResult();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return pdpojo;
	}

	@Override
	public Projects getProjectsWithProjectId(long id) {
		// TODO Auto-generated method stub
		return (Projects) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectId", id)).uniqueResult();
	}

	@Override
	public StatusMaster getStatusMasterForSubmit(String string) {
		return (StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", string)).uniqueResult();
	}

	@Override
	public boolean getUpdateProjectData(Projects ppojo) {
        boolean flag=false;
        Projects project = null;
		try {
			getSession().update(ppojo);
			project = (Projects) getSession().createCriteria(Projects.class)
					.add(Restrictions.eq("projectId", ppojo.getProjectId())).uniqueResult();
			if(project != null) {
				project.setUpdatedBy(ppojo.getCreatedBy());
				project.setUpdatedOn(new Date());
				getSession().update(project);
				flag=true;
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectAllList() {
		List<Projects> list=null;
		list=(List<Projects>) getSession().createCriteria(Projects.class).list();
		return list;
	}

	@Override
	public boolean saveProjectClone(Projects ps, List<ProjectsDetails> pdList, String userName) {
		 boolean flag=false;
			try {
				getSession().save(ps);
				for (ProjectsDetails pdd : pdList) {
					if(pdd.getFieldName().equals("projectNumber")) {
						pdd.setFieldValue(ps.getProjectNo());
					}
					pdd.setProjectsId(ps);
					pdd.setCreatedOn(new Date());
					getSession().save(pdd);
				}
				flag=true;
			} catch (Exception e) {
				flag=false;
				e.printStackTrace();
			}
			return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deActivateProjectDetailsData(long projectId,int rowNumber,int subRowNumber, String type) {
		
		List<ProjectsDetails> pdlist=null;
		pdlist=(List<ProjectsDetails>)getSession().createCriteria(ProjectsDetails.class)
		        		 .add(Restrictions.eq("projectsId.projectId", projectId))
		        		 .add(Restrictions.eq("subRowNo", subRowNumber))
		        		 .add(Restrictions.eq("type", type)).list();
		
		for (ProjectsDetails projectsDetails : pdlist) {
			projectsDetails.setStatus(false);
			getSession().saveOrUpdate(projectsDetails);
		}
		
	}
	
	/**
	 * Creates new data if the activity parameter does not exists. Updates status to true if the parameter exists. 
	 * @param projectId
	 * @param activityId
	 * @param parameterId
	 * @param userName
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer addActivityParameter(long projectId, Long activityId, Long parameterId, String userName) {
		ProjectsDetails projectsDetails = (ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
		        		 .add(Restrictions.eq("projectsId.projectId", projectId))
		        		 .add(Restrictions.eq("displayValue", activityId.toString()))
		        		 .add(Restrictions.eq("fieldValue", parameterId.toString()))
		        		 .add(Restrictions.eq("type", "DefaultActivity"))
		        		 .uniqueResult();
		
		Integer count = 0;
		if(projectsDetails != null) {
			projectsDetails.setStatus(true);
			projectsDetails.setUpdatedBy(userName);
			projectsDetails.setUpdatedOn(new Date());
			getSession().saveOrUpdate(projectsDetails);
			count = projectsDetails.getRowNo();
		}
		else {
			List<ProjectsDetails> projectsDetailList = getSession().createCriteria(ProjectsDetails.class)
	        		 .add(Restrictions.eq("projectsId.projectId", projectId))
	        		 .add(Restrictions.eq("type", "DefaultActivity")).list();
			
			count = projectsDetailList.size() + 1;
			
			Projects project = (Projects)getSession().createCriteria(Projects.class).add(Restrictions.eq("projectId", projectId)).uniqueResult();
			projectsDetails = new ProjectsDetails();
			projectsDetails.setProjectsId(project);
			projectsDetails.setFieldName("DefaultParameter");
			projectsDetails.setFieldValue(parameterId.toString());
			projectsDetails.setRowNo(count);
			projectsDetails.setSubRowNo(count);
			projectsDetails.setType("DefaultActivity");
			projectsDetails.setStatusid(null);
			projectsDetails.setCreatedBy(userName);
			projectsDetails.setDisplayValue(activityId.toString());
			projectsDetails.setCreatedOn(new Date());
			projectsDetails.setTreatmentNo((long)1);
			getSession().save(projectsDetails);
		}
		return count;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getDraftReviewStageDraftStage() {
		List<Long> list=new ArrayList<>();
		
		List<DraftReviewStage> dft=getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("name", "StudyDesign"))
				.add(Restrictions.eq("action", "SUBMIT")).list();
		for(DraftReviewStage data:dft) {
			list.add(data.getFromRole().getId());
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectsListForDraft(List<Long> list) {
		List<Projects> lista=new ArrayList<>();
		lista=getSession().createCriteria(Projects.class)
				.add(Restrictions.in("roleId", list)).list();
		return lista;
	}

	@Override
	public RandomizationFileStatus getRandomizationFileStatusProWithId(Long projectNoVal) {
		RandomizationFileStatus rfs=null;
		rfs=(RandomizationFileStatus) getSession().createCriteria(RandomizationFileStatus.class)
				.add(Restrictions.eq("projectId", projectNoVal)).uniqueResult();
		
		return rfs;
	}
	
	@Override
	public void updateOtherActivityParameters(Long projectId,Long parameterId,Long activityId, boolean isDelete, String userName) {
		ProjectsDetails projectDetails = (ProjectsDetails)getSession().createCriteria(ProjectsDetails.class)
							.add(Restrictions.eq("fieldValue", parameterId))
							.add(Restrictions.eq("treatmentNo", activityId));
		
		if(projectDetails != null) {
			projectDetails.setStatus(false);
			getSession().save(projectDetails);
		}
		else {
			Projects project = (Projects)getSession().createCriteria(Projects.class)
					.add(Restrictions.eq("Id", projectId));
			
			projectDetails = new ProjectsDetails();
			projectDetails.setCreatedBy(userName);
			projectDetails.setCreatedOn(new Date());
			projectDetails.setFieldName("PARAMETER");
			projectDetails.setFieldOrder(1);
			projectDetails.setFieldValue(parameterId.toString());
			projectDetails.setTreatmentNo(activityId);
			projectDetails.setSubRowNo(0);	
			projectDetails.setProjectsId(project);
			projectDetails.setStatus(true);
			projectDetails.setRowNo(0);
			getSession().save(projectDetails);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyDesignDto getStudyDesignDtoDetails(ProjectDetailsKeyVal prod, String username,long res) {
		StudyDesignDto sdDto = null;
		Projects project = null;
		String noOfSubjects = null;
		String noofperiod = null;
		RandomizationFileStatus rpojo = null;
		List<ProjectsDetails> randomizationCode = null;
		List<ProjectDetailsRandomization> pdrlist = null;
		UserMaster checkLoginUser = null;
		DraftReviewStage drs = null;
		StatusMaster status = null;
		try {
			project = (Projects) getSession().createCriteria(Projects.class)
					.add(Restrictions.eq("projectId", prod.getProjectId())).uniqueResult();
			if(project != null) {
				noOfSubjects = (String) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("fieldName", "noOfSubjects"))
						.add(Restrictions.eq("projectsId", project))
						.setProjection(Projections.property("fieldValue")).uniqueResult();
				
				noofperiod = (String) getSession().createCriteria(ProjectsDetails.class)
							.add(Restrictions.eq("fieldName", "noOfPeriods"))
							.add(Restrictions.eq("projectsId", project))
							.setProjection(Projections.property("fieldValue")).uniqueResult();
				
				rpojo = (RandomizationFileStatus) getSession().createCriteria(RandomizationFileStatus.class)
						.add(Restrictions.eq("projectId", project.getProjectId())).uniqueResult();
				
				randomizationCode = getSession().createCriteria(ProjectsDetails.class)
								.add(Restrictions.eq("fieldName", "randomizationCode"))
								.add(Restrictions.eq("projectsId", project)).list();
				
				pdrlist = getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("projectNo", project.getProjectId())).list();
			}
			
			checkLoginUser = (UserMaster) getSession().createCriteria(UserMaster.class)
					.add(Restrictions.eq("username", username)).uniqueResult();
			if(checkLoginUser != null) {
				drs =(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
						.add(Restrictions.eq("fromRole", checkLoginUser.getRole()))
						.add(Restrictions.eq("action", StudyStatus.APPROVAL.toString()))
						.add(Restrictions.eq("name", "StudyDesign")).uniqueResult();
						/*.add(Restrictions.eq("id", res)).uniqueResult();*/
			}
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.INREVIEW.toString())).uniqueResult();
			
			sdDto = new StudyDesignDto();
			sdDto.setCheckLoginUser(checkLoginUser);
			sdDto.setDrs(drs);
			sdDto.setPoject(project);
			sdDto.setNoofperiod(noofperiod);
			sdDto.setNoOfSubjects(noOfSubjects);
			sdDto.setRpojo(rpojo);
			sdDto.setRandomizationCode(randomizationCode);
			sdDto.setPdrlist(pdrlist);
			sdDto.setSm(status);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityDraftReviewAudit> getActivityDraftReviewAuditListWithProjectId(long projectId) {
		List<ActivityDraftReviewAudit> list=null;
		list=getSession().createCriteria(ActivityDraftReviewAudit.class)
				.add(Restrictions.eq("projectId", projectId))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@Override
	public DraftReviewStage getDraftReviewStageWithRoleidAndActionname2(Long roleId, String string, long res) {
		DraftReviewStage dr=null;
		dr=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole.id", roleId))
				.add(Restrictions.eq("action", string))
				.add(Restrictions.eq("name", "StudyDesign"))
				//.add(Restrictions.eq("id", res))
				.uniqueResult();
		
		return dr;
	}

	@Override
	public CustomActivityParameters checkCustomActivityParameterExistsOrNot(Long activityId, Long parameterId) {
		return (CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
				.add(Restrictions.eq("parameter", parameterId))
				.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProjectDetailsDto getProjectsDetailsVal(long projectId, String format) {
		List<ProjectsDetails> projectDetails = getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId.projectId", projectId))
				.add(Restrictions.eq("status", true))
	       		.addOrder(Order.asc("type"))
				.addOrder(Order.asc("rowNo"))
				.addOrder(Order.asc("subRowNo"))
	       		.addOrder(Order.asc("fieldOrder")).list();

		List<StudyDiscrepancy> studyDiscrepancies = getSession().createCriteria(StudyDiscrepancy.class)
				.add(Restrictions.in("project", projectDetails)).addOrder(Order.asc("createdOn")).list();

		ProjectDetailsDto projectDetailDto = new ProjectDetailsDto();
		projectDetailDto.setProjectDetails(projectDetails);
		List<CommentsDto> comments =new ArrayList<>();
		for(StudyDiscrepancy discrepancy:studyDiscrepancies) {
			CommentsDto comment =new CommentsDto();
			comment.setComment(discrepancy.getComments());
			comment.setCommentId(discrepancy.getId());
			comment.setCommentedBy(discrepancy.getCreatedBy());
			comment.setRespondedBy(discrepancy.getUpdateBy());
			comment.setResponse(discrepancy.getResponse());
			comment.setCommentedOn(DateFormatUtil.ConvertDate(discrepancy.getCreatedOn(), format));
			comment.setCommentedOn(DateFormatUtil.ConvertDate(discrepancy.getUpdateOn(), format));
			comments.add(comment);
		}
		projectDetailDto.setStudyDiscrepancies(comments);
		return projectDetailDto;
	}

}
