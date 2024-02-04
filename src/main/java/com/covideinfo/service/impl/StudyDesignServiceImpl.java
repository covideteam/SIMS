package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dao.StudyDesignDao;
import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.ProjectDetailsDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyDesignDto;
import com.covideinfo.dto.StudyProjectDetailDto;
import com.covideinfo.enums.ActionTypes;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DiscrepancyModel;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.ProjectDetailsKeyVal;
import com.covideinfo.model.dummy.ProjectKeyVal;
import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.StudyDesignService;

@Service("studyDesignService")
@PropertySource(value = { "classpath:application.properties" })
public class StudyDesignServiceImpl  implements StudyDesignService {
	public static String projectNo = "";
	@Autowired
	StudyDesignDao studyDesignDao;

	@Autowired
	StudyCreationBo studyCreationBo;
	@Autowired
	private StudyCreationDao studyCreationDao;
	
	@Autowired
	StudyActivityService studyActivityService;
	
	@Override
	public boolean getAutoSaveData(Projects ps, List<ProjectsDetails> pdp) {

		return studyDesignDao.getAutoSaveData(ps, pdp);
	}

	@Override
	public ProjectDetailsDto getProjectsDetails(Long projectId, Long languageId, String format) {
		return studyDesignDao.getProjectsDetails(projectId, languageId, format);
	}

	@Override
	public List<Projects> getProjectsList(Long roleid) {
		return studyDesignDao.getProjectsList(roleid);
	}

	@Override
	public Projects getProjectWithId(Long projectId) {
		return studyDesignDao.getProjectWithId(projectId);
	}

	@Override
	public Projects getProjectsWithProjectNo(String prod1) {
		return studyDesignDao.getProjectsWithProjectNo(prod1);
	}

	@Override
	public ProjectsDetails getProjectsDetails(Projects ppojo, String prod2, int prod4, int prod5,String type) {
		return studyDesignDao.getProjectsDetails(ppojo, prod2,
				prod4, prod5,type);
	}

	@Override
	public boolean getUpdateAutoSaveData(ProjectsDetails pd) {
		return studyDesignDao.getUpdateAutoSaveData(pd);
	}

	@Override
	public boolean getNewAutoSaveData(ProjectsDetails pdp) {
		return studyDesignDao.getNewAutoSaveData(pdp);
	}

	@Override
	public ProjectsDetails getProjectsDetailsWithpdId(long id) {
		return studyDesignDao.getProjectsDetailsWithpdId(id);
	}

	@Override
	public StudyDiscrepancy saveDiscrepancy(	CommentsDto projectKeyValue,String username) {
		StudyDiscrepancy studyDiscrepancy = new StudyDiscrepancy();
		try {
			return studyDesignDao.saveDiscrepancy(projectKeyValue, username);	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return studyDiscrepancy;
	}

	@Override
	public StudyDiscrepancy getStudyDiscrepancyWithId(long id) {
		return studyDesignDao.getStudyDiscrepancyWithId(id);
	}
	
	@Override
	public void deleteComment(long commentId, String username) {
		studyDesignDao.deleteComment(commentId, username);
	}
	
	@Override
	public List<DiscrepancyModel> getDescrepencies(ProjectKeyVal keyValue, Environment enviroment) throws Exception {
		return studyDesignDao.getDescrepencies(keyValue, enviroment);
	}

	@Override
	public boolean updateDiscrepancy(StudyDiscrepancy sdpojo) {
		return studyDesignDao.updateDiscrepancy(sdpojo);
	}

	@Override
	public DraftReviewStage getDraftReviewStageWithRoleidAndActionname(long roleid, String actionname) {
		return studyDesignDao.getDraftReviewStageWithRoleidAndActionname(roleid, actionname);
	}

	@Override
	public Projects saveActivityDraftReviewAudit(Long projectId, Long roleId, ActionTypes actionType, UserMaster checkLoginUser) {
		Projects project  = null;
		long res=0;
     	DraftReviewStage drs= null;
     	StatusMaster sm= null;
		try {
			project  = studyDesignDao.getProjectWithId(projectId);
			drs=studyDesignDao.getDraftReviewStageWithRoleidAndActionname2(roleId, actionType.toString(),res);
			sm=studyDesignDao.getStatusMasterForSubmit(StudyStatus.DRAFT.toString());
			project.setStatus(sm);
			if(drs.getToRole()==null) {
				project.setRoleId(0);
			}else {
				project.setRoleId(drs.getToRole().getId());
			}
			ActivityDraftReviewAudit adra=new ActivityDraftReviewAudit();
			//adra.setProjectId(project.getProjectId());
			adra.setDateOfActivity(new Date());
			adra.setReviewState(drs.getId());
			adra.setProjectId(projectId);
			adra.setStatus("SENDCOMMENTS");
			adra.setRole(checkLoginUser.getRole());
			adra.setUser(checkLoginUser);
			//adra.setRole(checkLoginUser.getRole());
			//adra.setUser(checkLoginUser);
			project = studyDesignDao.saveActivityDraftReviewAudit(adra,project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}
	
	@Override
	public Projects saveActivityDraftReviewAudit(ActivityDraftReviewAudit adra,Projects poject){
		return studyDesignDao.saveActivityDraftReviewAudit(adra,poject);
	}

	@Override
	public List<StudyDiscrepancy> getStudyDiscrepancyExitOrNot(Long projectId) {
		return studyDesignDao.getStudyDiscrepancyExitOrNot(projectId);
	}

	@Override
	public List<Projects> getEligibleProjectList() {
		return studyDesignDao.getEligibleProjectList();
	}

	@Override
	public ProjectsDetails getProjectsDetails(long id, String prod2, int prod4, int prod5,String type) {
		return studyDesignDao.getProjectsDetails(id,prod2,prod4,prod5,type);
	}

	@Override
	public Projects getProjectsWithProjectId(long id) {
		return studyDesignDao.getProjectsWithProjectId(id);
	}

	@Override
	public StatusMaster getStatusMasterForSubmit(String string) {
		return studyDesignDao.getStatusMasterForSubmit(string);
	}

	public Projects testSaveStudyInfo(int testNo, UserMaster user) {
		StatusMaster approved = studyCreationDao.statusMaster(StudyStatus.APPROVED.toString());
		int maxId = studyCreationDao.projectMaxId();
		maxId++;
		Projects p = new Projects();
		p.setStatus(approved);
		List<ProjectsDetails> pdls = new ArrayList<>();
		pdls.addAll(studyFrom(maxId, p));
		pdls.addAll(treatmentFrom(maxId, p, testNo));
		pdls.addAll(doseTimePointsFrom(maxId, p, testNo));
		pdls.addAll(dosePerametersFrom(maxId, p, testNo));
		pdls.addAll(sampleTimePoints(maxId, p, testNo));
		pdls.addAll(mealsTimePoints(maxId, p, testNo));
		pdls.addAll(vitalTimePoints(maxId, p, testNo));
		pdls.addAll(vitalTestRanges(maxId, p, testNo));
		pdls.addAll(ecgTimePoints(maxId, p, testNo));
		pdls.addAll(aditionalSaftyAssessment(maxId, p, testNo));
		pdls.addAll(sampleProcessingAndStorage(maxId, p));
		pdls.addAll(restrictionCompliance(maxId, p));
		pdls.addAll(inclustione(maxId, p));
		pdls.addAll(exclution(maxId, p));
		p.setProjectNo(StudyDesignServiceImpl.projectNo);
		studyCreationDao.saveTestProject(p, pdls);
		p.setLoginUser(user);
		return p;
	}

	private Collection<? extends ProjectsDetails> vitalTestRanges(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTNAME.toString());
		pd.setFieldValue(StaticData.BP.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
		pd.setFieldValue("90/60");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGETO.toString());
		pd.setFieldValue("139/89");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTNAME.toString());
		pd.setFieldValue(StaticData.PULSERATE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
		pd.setFieldValue("40");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGETO.toString());
		pd.setFieldValue("170");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTNAME.toString());
		pd.setFieldValue(StaticData.TEMPERATURE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
		pd.setFieldValue("95");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGETO.toString());
		pd.setFieldValue("99");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTNAME.toString());
		pd.setFieldValue(StaticData.RESPIRATORYRATE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
		pd.setFieldValue("12");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTestPerameterRange");
		pd.setFieldName(StudyDesign.TESTRANGETO.toString());
		pd.setFieldValue("16");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		
		//---------------------------------------------------------------------------------
		if(testNo == 1) {
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTNAME.toString());
			pd.setFieldValue(StaticData.BP.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
			pd.setFieldValue("90/60");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGETO.toString());
			pd.setFieldValue("139/89");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTNAME.toString());
			pd.setFieldValue(StaticData.PULSERATE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
			pd.setFieldValue("40");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGETO.toString());
			pd.setFieldValue("170");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTNAME.toString());
			pd.setFieldValue(StaticData.TEMPERATURE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
			pd.setFieldValue("95");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGETO.toString());
			pd.setFieldValue("99");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTNAME.toString());
			pd.setFieldValue(StaticData.RESPIRATORYRATE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGEFROM.toString());
			pd.setFieldValue("12");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTestPerameterRange");
			pd.setFieldName(StudyDesign.TESTRANGETO.toString());
			pd.setFieldValue("16");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
		}
		return pdls;
	}

	private Collection<? extends ProjectsDetails> exclution(int maxId, Projects p) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("exclution");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Healthy human subjects between 18-45 years of age (including both)");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("exclution");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Subjects able to communicate effectively.");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("exclution");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Truly voluntarily participation");
		pd.setRowNo(3);
		pdls.add(pd);
		return pdls;
	}

	private Collection<? extends ProjectsDetails> inclustione(int maxId, Projects p) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("inclustion");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Healthy human subjects between 18-45 years of age (including both)");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("inclustion");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Subjects able to communicate effectively.");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("inclustion");
		pd.setFieldName(StudyDesign.PARAMETER.toString());
		pd.setFieldValue("Truly voluntarily participation");
		pd.setRowNo(3);
		pdls.add(pd);
		return pdls;
	}

	private Collection<? extends ProjectsDetails> restrictionCompliance(int maxId, Projects p) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKIN.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("At least 11.00 hours in house stay maintained before dosing?");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKIN.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("At least 10 hours fasting condition maintained prior to IP administration?");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKIN.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("01 hour pre-dose waster restriction was maintained before IP administration?");
		pd.setRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKIN.toString());
		pd.setRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("Subject was instructed not to disturb the injection for at least 15 minutes from the time of injection?");
		pd.setRowNo(3);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKOUT.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("01 hour post-dose waster restriction was maintained after IP administration? ");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKOUT.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("At least 10 hours fasting condition maintained prior to IP administration?");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKOUT.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("01 hour pre-dose waster restriction was maintained before IP administration?");
		pd.setRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue(StaticData.CHECKOUT.toString());
		pd.setRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("restrictionCompliance");
		pd.setFieldName(StudyDesign.RESTRICTIONSFOR.toString());
		pd.setFieldValue("Subject was instructed not to disturb the injection for at least 15 minutes from the time of injection?");
		pd.setRowNo(3);
		pdls.add(pd);
		return pdls;
	}

	private Collection<? extends ProjectsDetails> sampleProcessingAndStorage(int maxId, Projects p) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.CENTRIFUGATIONAPPLICABLE.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString());
		pd.setFieldValue(StaticData.ALL.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.ALLOWEDTIME.toString());
		pd.setFieldValue("45");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.CENTRIFUGATIONCONDITION.toString());
		pd.setFieldValue(StaticData.BUFFER.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.SPEED.toString());
		pd.setFieldValue("1300");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.PROCESSTIME.toString());
		pd.setFieldValue("10");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.TEMPERATURE.toString());
		pd.setFieldValue("34");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.TEMPERATUREWINDOW.toString());
		pd.setFieldValue("2");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.TEMPERATUREWINDOWSIGN.toString());
		pd.setFieldValue("+/-");
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.PROCESSCONDITION.toString());
		pd.setFieldValue(StaticData.BUFFER.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.PROCESSMATRIX.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.PROCESSALLOWEDTIME.toString());
		pd.setFieldValue("45");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.PROCESSALLOWEDTIMEFROM.toString());
		pd.setFieldValue(StaticData.STARTTIME.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.ALIQUOTS1VOLUME.toString());
		pd.setFieldValue("Approx 1.5 ml");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.ALIQUOTS1MATRIX.toString());
		pd.setFieldValue(StaticData.SERUM.toString());
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.ISSTORAGEDIFF.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.STORAGECONDITION.toString());
		pd.setFieldValue(StaticData.CONDITION1.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.STORAGEALLOWEDTIME.toString());
		pd.setFieldValue("15");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.STORATETEMPERATURE.toString());
		pd.setFieldValue("-75");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleProcessing");
		pd.setFieldName(StudyDesign.STORAGE.toString());
		pd.setFieldValue(StaticData.TIMEPOINTWISE.toString());
		pdls.add(pd);
		return pdls;
	}

	private Collection<? extends ProjectsDetails> aditionalSaftyAssessment(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.TITLE.toString());
		pd.setFieldValue("aditionalSaftyAssessment title 1");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.PREDOSE.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("02.00,01.00");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("additionalAssesment");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		
		if(testNo == 1) {
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.TITLE.toString());
			pd.setFieldValue("aditionalSaftyAssessment title 1");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.POSTDOSE.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("30.00,20.00");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("additionalAssesment");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pdls.add(pd);
		}

		return pdls;
	}

	private Collection<? extends ProjectsDetails> ecgTimePoints(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.ECGAPPLICABLE.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TREATMENTWISEECGTIMEPOITNS.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.NOOFECGTIMEPOINTS.toString());
		pd.setFieldValue("7");
		pd.setRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.PREDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.ECGPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("04.00,01.00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);

		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.POSTDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.ECGPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("04.00,10.00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.AMBULATORY.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.ECGPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("47.00");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("ecgTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		
		if(testNo == 1) {
			//---------------------------
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.NOOFECGTIMEPOINTS.toString());
			pd.setFieldValue("7");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.PREDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.ECGPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("04.00,01.00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);

			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.POSTDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.ECGPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("04.00,10.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.AMBULATORY.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.ECGPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("47.00");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("ecgTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
		}

		return pdls;
	}

	private Collection<? extends ProjectsDetails> vitalTimePoints(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TREATMENTWISEVITALTIMEPOINTS.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.VITALPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TEMPERATURE.toString());
		pd.setFieldValue(StaticData.FOREHEAD.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TOTALTIMEPOINTS.toString());
		pd.setFieldValue("7");
		pd.setRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.PREDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("03.00,01.00,00.00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TESTNAMES.toString());
		pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("11");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.VITALPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.POSTDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("01.00,02.00,06.00,10.00,24.00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TESTNAMES.toString());
		pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("12");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.VITALPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.AMBULATORY.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("48.00,50.00");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.TESTNAMES.toString());
		pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("13");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("vitalTimePoins");
		pd.setFieldName(StudyDesign.VITALPOSITION.toString());
		pd.setFieldValue(StaticData.PRONE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		
		if(testNo == 1) {
			//-------------------------------------------
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.VITALPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TEMPERATURE.toString());
			pd.setFieldValue(StaticData.FOREHEAD.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TOTALTIMEPOINTS.toString());
			pd.setFieldValue("7");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.PREDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("03.00,01.00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("03.00,01.00,00.00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TESTNAMES.toString());
			pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("21");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.VITALPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.POSTDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("01.00,02.00,06.00,10.00,24.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("03.00,01.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TESTNAMES.toString());
			pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("-");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("22");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.VITALPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.AMBULATORY.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("48.00,50.00");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("03.00,01.00");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.TESTNAMES.toString());
			pd.setFieldValue(StaticData.BP.toString()+","+StaticData.WELLBEING.toString()+","+StaticData.PULSERATE.toString()+","+StaticData.TEMPERATURE.toString()+","+StaticData.RESPIRATORYRATE.toString() );
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("23");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.ORTHOSTATIC.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("vitalTimePoins");
			pd.setFieldName(StudyDesign.VITALPOSITION.toString());
			pd.setFieldValue(StaticData.PRONE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
		}

		return pdls;
	}

	private Collection<? extends ProjectsDetails> mealsTimePoints(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TREATMENTWISEMEALSTIMEPOINTS.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TOTALTIMEPOINTS.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
		pd.setFieldValue(StaticData.DINNER.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("-11.00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
		pd.setFieldValue("15");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
		pd.setFieldValue(StaticData.LUNCH.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("05.00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
		pd.setFieldValue("15");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
		pd.setFieldValue(StaticData.SNACKS.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("08.00");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
		pd.setFieldValue(StaticData.DINNER.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("12.00");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
		pd.setFieldValue(StaticData.NORMALBREAKFAST.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("-01.00");
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("mealsTimePoints");
		pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
		pd.setFieldValue("15");
		pd.setRowNo(1);
		pd.setSubRowNo(5);
		pdls.add(pd);
		
		
		if(testNo == 1) {
			/////////////////////////treatment
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TOTALTIMEPOINTS.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
			pd.setFieldValue(StaticData.DINNER.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("-11.00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
			pd.setFieldValue("15");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			
			
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
			pd.setFieldValue(StaticData.LUNCH.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("05.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
			pd.setFieldValue("15");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
			pd.setFieldValue(StaticData.SNACKS.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("08.00");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
			pd.setFieldValue(StaticData.DINNER.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("12.00");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.MEALSTTYPE.toString());
			pd.setFieldValue(StaticData.NORMALBREAKFAST.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("-01.00");
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTYPE.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("mealsTimePoints");
			pd.setFieldName(StudyDesign.COMPLETIONTIME.toString());
			pd.setFieldValue("15");
			pd.setRowNo(2);
			pd.setSubRowNo(5);
			pdls.add(pd);
			
		}

		return pdls;
	}

	private Collection<? extends ProjectsDetails> sampleTimePoints(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TREATMENTSPECIFICSAMPLETIMEPOINTS.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.LIGHTCONDITION.toString());
		pd.setFieldValue(StaticData.NORMAL.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.VOLUMNOFBLOOD.toString());
		pd.setFieldValue("5");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TYPEOFVACUTAINERUSED.toString());
		pd.setFieldValue(StaticData.K2EDTA.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.BUFFER.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.NOOFVAILSFORSAMPLESEPARATION.toString());
		pd.setFieldValue("2");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TOTALSAMPLETIMEPOINTS.toString());
		pd.setFieldValue("20");
		pd.setRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.PREDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("02.00,01.00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		
		pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
		if(testNo == 1 || testNo==2)
			pd.setFieldValue("2");
		else
			pd.setFieldValue("1");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("10");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.PREDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("00.00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
		if(testNo == 1 || testNo==2)
			pd.setFieldValue("2");
		else
			pd.setFieldValue("1");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("-");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("50");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.POSTDOSE.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("00.15,00.50,01.00,01.50,02.00,02.50,03.00,04.00,04.50,05.00,06.00,07.00,09.00,12.00,16.00,20.00,24.00");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
		if(testNo == 1 || testNo==2)
			pd.setFieldValue("2");
		else
			pd.setFieldValue("1");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("2");
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(3);
		pdls.add(pd);
		
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
		pd.setFieldValue(StaticData.AMBULATORY.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.TIMEPOINT.toString());
		pd.setFieldValue("48.00,50.00");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
		pd.setFieldValue("1");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+/-");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("120");
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("sampleInformation");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(4);
		pdls.add(pd);
		
		//---------------------------------------------------------------
		if(testNo == 1) {
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.LIGHTCONDITION.toString());
			pd.setFieldValue(StaticData.NORMAL.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.VOLUMNOFBLOOD.toString());
			pd.setFieldValue("5");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TYPEOFVACUTAINERUSED.toString());
			pd.setFieldValue(StaticData.K2EDTA.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.BUFFER.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVAILSFORSAMPLESEPARATION.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TOTALSAMPLETIMEPOINTS.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TREATMENTWISEDOSEPERAMETER.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.LIGHTCONDITION.toString());
			pd.setFieldValue(StaticData.NORMAL.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.VOLUMNOFBLOOD.toString());
			pd.setFieldValue("5");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TYPEOFVACUTAINERUSED.toString());
			pd.setFieldValue(StaticData.K2EDTA.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TYPEOFVACUTAINERUSED.toString());
			pd.setFieldValue(StaticData.K2EDTA.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.LIGHTCONDITION.toString());
			pd.setFieldValue(StaticData.NORMAL.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.BUFFER.toString());
			pd.setFieldValue(StaticData.YES.toString());
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVAILSFORSAMPLESEPARATION.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TOTALSAMPLETIMEPOINTS.toString());
			pd.setFieldValue("20");
			pd.setRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.PREDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("02.50,01.50");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("10");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.PREDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("00.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("-");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("60");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.POSTDOSE.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("00.50,01.00,01.50,02.00,02.50,03.00,04.00,04.50,05.00,06.00,07.00,09.00,12.00,16.00,20.00,24.00");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(3);
			pdls.add(pd);
			
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINTTYPE.toString());
			pd.setFieldValue(StaticData.AMBULATORY.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.TIMEPOINT.toString());
			pd.setFieldValue("48.00,50.00");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.NOOFVACUTAINERS.toString());
			pd.setFieldValue("1");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+/-");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("120");
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("sampleInformation");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(4);
			pdls.add(pd);
			
			
		}

		return pdls;
	}

	private Collection<? extends ProjectsDetails> dosePerametersFrom(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.TREATMENTWISEDOSEPERAMETER.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);

		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.TREATMENT.toString());
		pd.setFieldValue("TREATMENTNAME A");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.PARAMETERDESC.toString());
		pd.setFieldValue("TREATMENTNAME A PARAMETERDESC1 PARAMETERDESC1 PARAMETERDESC1");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.CONTROLTYPE.toString());
		pd.setFieldValue(StaticData.RADIO.toString());
		pd.setRowNo(1);
		pdls.add(pd);

		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.TREATMENT.toString());
		pd.setFieldValue("TREATMENTNAME A");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.PARAMETERDESC.toString());
		pd.setFieldValue("TREATMENTNAME A PARAMETERDESC2 PARAMETERDESC2 PARAMETERDESC2");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingParameters");
		pd.setFieldName(StudyDesign.CONTROLTYPE.toString());
		pd.setFieldValue(StaticData.RADIO.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		
		if(testNo == 1) {
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.TREATMENT.toString());
			pd.setFieldValue("TREATMENTNAME B");
			pd.setRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.PARAMETERDESC.toString());
			pd.setFieldValue("TREATMENTNAME B PARAMETERDESC1 PARAMETERDESC1 PARAMETERDESC1");
			pd.setRowNo(3);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.CONTROLTYPE.toString());
			pd.setFieldValue(StaticData.RADIO.toString());
			pd.setRowNo(3);
			pdls.add(pd);		

			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.TREATMENT.toString());
			pd.setFieldValue("TREATMENTNAME B");
			pd.setRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.PARAMETERDESC.toString());
			pd.setFieldValue("TREATMENTNAME B PARAMETERDESC2 PARAMETERDESC2 PARAMETERDESC2");
			pd.setRowNo(4);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingParameters");
			pd.setFieldName(StudyDesign.CONTROLTYPE.toString());
			pd.setFieldValue(StaticData.RADIO.toString());
			pd.setRowNo(4);
			pdls.add(pd);		
		}
		return pdls;
	}

	private Collection<? extends ProjectsDetails> doseTimePointsFrom(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		// DOSE TIME POINTS
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.TREATMENTWISENOOFDOSING.toString());
		if(testNo == 1)
			pd.setFieldValue(StaticData.YES.toString());
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue(StaticData.NO.toString());
		pdls.add(pd);

		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.DOSETIMEPOINT.toString());
		pd.setFieldValue("00.00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FASTINGCRITERIA.toString());
		pd.setFieldValue("10:00");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FASTINGCRITERIATYPE.toString());
		pd.setFieldValue(StaticData.HOURS.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FEDCRITERIA.toString());
		pd.setFieldValue("00:30");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FEDCRITERIATYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("2");
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(1);
		pdls.add(pd);

		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.DOSETIMEPOINT.toString());
		pd.setFieldValue("12.00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FASTINGCRITERIA.toString());
		pd.setFieldValue("10:00");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FASTINGCRITERIATYPE.toString());
		pd.setFieldValue(StaticData.HOURS.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FEDCRITERIA.toString());
		pd.setFieldValue("00:30");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.FEDCRITERIATYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
		pd.setFieldValue("+");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
		pd.setFieldValue("2");
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("DosingTimepoints");
		pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
		pd.setFieldValue(StaticData.MINUTES.toString());
		pd.setRowNo(1);
		pd.setSubRowNo(2);
		pdls.add(pd);

		if(testNo == 1) {
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.DOSETIMEPOINT.toString());
			pd.setFieldValue("00.00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FASTINGCRITERIA.toString());
			pd.setFieldValue("10:00");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FASTINGCRITERIATYPE.toString());
			pd.setFieldValue(StaticData.HOURS.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FEDCRITERIA.toString());
			pd.setFieldValue("00:30");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FEDCRITERIATYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(1);
			pdls.add(pd);

			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.DOSETIMEPOINT.toString());
			pd.setFieldValue("11.00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FASTINGCRITERIA.toString());
			pd.setFieldValue("10:00");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FASTINGCRITERIATYPE.toString());
			pd.setFieldValue(StaticData.HOURS.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FEDCRITERIA.toString());
			pd.setFieldValue("00:30");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.FEDCRITERIATYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODSIGN.toString());
			pd.setFieldValue("+");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIOD.toString());
			pd.setFieldValue("2");
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
			pd = new ProjectsDetails(); pd.setProjectsId(p);
			pd.setType("DosingTimepoints");
			pd.setFieldName(StudyDesign.WINDOWPERIODTYPE.toString());
			pd.setFieldValue(StaticData.MINUTES.toString());
			pd.setRowNo(2);
			pd.setSubRowNo(2);
			pdls.add(pd);
		}
		
		return pdls;
	}

	private Collection<? extends ProjectsDetails> treatmentFrom(int maxId, Projects p, int testNo) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTNO.toString());
		pd.setFieldValue("1");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTNAME.toString());
		pd.setFieldValue("TREATMENTNAME A");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.RANDAMIZATIONCODE.toString());
		pd.setFieldValue("T");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.STREANGTH.toString());
		pd.setFieldValue("2 mg+1 mg");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.DOSE.toString());
		pd.setFieldValue("2 mg+1 mg");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.AMOUNTOFWATERCONSUMEDWITHTHEDOSE.toString());
		pd.setFieldValue("200 ml");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTTYPE.toString());
		pd.setFieldValue(StaticData.FAST.toString());
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.NOOFSACHETLABLES.toString());
		if(testNo == 1)
			pd.setFieldValue("2");
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue("1");
		pd.setRowNo(1);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.NOOFDOSINGS.toString());
		if(testNo == 1)
			pd.setFieldValue("2");
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue("1");
		pd.setRowNo(1);
		pdls.add(pd);

		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTNO.toString());
		pd.setFieldValue("2");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTNAME.toString());
		pd.setFieldValue("TREATMENTNAME B");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.RANDAMIZATIONCODE.toString());
		pd.setFieldValue("R");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.STREANGTH.toString());
		pd.setFieldValue("2 mg+1 mg");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.DOSE.toString());
		pd.setFieldValue("2 mg+1 mg");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.AMOUNTOFWATERCONSUMEDWITHTHEDOSE.toString());
		pd.setFieldValue("200 ml");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.TREATMENTTYPE.toString());
		pd.setFieldValue(StaticData.FAST.toString());
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.NOOFSACHETLABLES.toString());
		if(testNo == 1)
			pd.setFieldValue("2");
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue("1");
		pd.setRowNo(2);
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("TreatmentWiseInformation");
		pd.setFieldName(StudyDesign.NOOFDOSINGS.toString());
		if(testNo == 1)
			pd.setFieldValue("2");
		else if(testNo == 2 || testNo == 3)
			pd.setFieldValue("1");
		pd.setRowNo(2);
		pdls.add(pd);
		return pdls;
	}

	private Collection<? extends ProjectsDetails> studyFrom(int maxId, Projects p) {
		List<ProjectsDetails> pdls = new ArrayList<>();
		ProjectsDetails pd = new ProjectsDetails();
		pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.PROJECTNO.toString());
		pd.setFieldValue("Project " + maxId);
		projectNo = "Project " + maxId;
		pdls.add(pd);

		pd = new ProjectsDetails();
		pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.SPONSORCODE.toString());
		pd.setFieldValue("YYYYYYYYYYY");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.STUDYCATEGORY.toString());
		pd.setFieldValue(StaticData.PILOT.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.STUDYDESIGN.toString());
		pd.setFieldValue(StaticData.FAST.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.MASKING.toString());
		pd.setFieldValue(StaticData.OPENLABEL.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.STUDYTITLE.toString());
		pd.setFieldValue("STUDYTITLE");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.NOOFPERIODS.toString());
		pd.setFieldValue(3 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.WASHOUTPERIOD.toString());
		pd.setFieldValue(7 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.DOSAGEFORM.toString());
		pd.setFieldValue(StaticData.INJECTION.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.TREATMENTCODE.toString());
		pd.setFieldValue(StaticData.SHOW.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.NOOFSUBJECTS.toString());
		pd.setFieldValue(30 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.NOOFSTANDBYSUBJECTS.toString());
		pd.setFieldValue(5 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.DOSETYPE.toString());
		pd.setFieldValue(StaticData.MULTIPLE.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.NOOFTREATMENTS.toString());
		pd.setFieldValue(2 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.PREDOSEHR.toString());
		pd.setFieldValue(40 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.POSTDOSEHR.toString());
		pd.setFieldValue(40 + "");
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.TREATMENTSPECIFICSAMPLETIMEPOINTS.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);
		pd = new ProjectsDetails(); pd.setProjectsId(p);
		pd.setType("StudyInformation");
		pd.setFieldName(StudyDesign.TREATMENTWISEDOSEPERAMETER.toString());
		pd.setFieldValue(StaticData.YES.toString());
		pdls.add(pd);

		return pdls;
	}

	@Override
	public boolean getUpdateProjectData(Projects ppojo) {
		// TODO Auto-generated method stub
		return studyDesignDao.getUpdateProjectData(ppojo);
	}

	@Override
	public List<Projects> getProjectAllList() {
		return studyDesignDao.getProjectAllList();
	}

	@Override
	public boolean saveProjectClone(Projects ps, List<ProjectsDetails> pdList,String userName) {
		return studyDesignDao.saveProjectClone(ps,pdList,userName);
	}

	@Override
	public void deActivateProjectDetailsData(long projectId,int rowNumber,int subRowNumber, String type) {
		studyDesignDao.deActivateProjectDetailsData(projectId, rowNumber, subRowNumber, type);
	}
	
	@Override
	public List<Long> getDraftReviewStageDraftStage() {
		return studyDesignDao.getDraftReviewStageDraftStage();
	}

	@Override
	public List<Projects> getProjectsListForDraft(List<Long> list) {
		
		return studyDesignDao.getProjectsListForDraft(list);
	}

	@SuppressWarnings("unused")
	@Override
	public String approveProject(ProjectDetailsKeyVal prod, String username, MessageSource messageSource, Locale currentLocale) {
		
		String result = messageSource.getMessage("label.project.Fail", null,currentLocale);
		StudyDesignDto sdDto = null;
		Projects poject= null;
		String noofsub = null;
		String noofperiod = null;
		RandomizationFileStatus rpojo = null;
		List<ProjectsDetails> randomizationCode = null;
		List<ProjectDetailsRandomization> pdrlist = null;
		StatusMaster sm = null;
		DraftReviewStage drs = null;
		UserMaster checkLoginUser = null;
		StudyProjectDetailDto spDto = null;
		StudyActivitiesSavingDto sasDto = null;
		boolean finalFlag = false;
		String studyResult = "Failed";
		Projects finalPoject = null; 
		List<ActivityDraftReviewAudit>acAudit=studyDesignDao.getActivityDraftReviewAuditListWithProjectId(prod.getProjectId());
		String finalResult = "Failed";
		//Not need now
		long res=0;
		/*ActivityDraftReviewAudit pojo=null;
		int size=acAudit.size();
		pojo=acAudit.get(size-1);
		
		if(size>0) {
			if(!pojo.getStatus().equals("IN APPROVAL")) {
			if(size>=2) {
				pojo=acAudit.get(size-1);
				res=pojo.getReviewState()+2;
			}else {
					pojo=acAudit.get(size-1);
					res=pojo.getReviewState()+1;
			}
			}else {
				pojo=acAudit.get(size-1);
				res=pojo.getReviewState()+1;
			}
		}*/
		try {
			sdDto = studyDesignDao.getStudyDesignDtoDetails(prod, username,res);
			if(sdDto != null) {
				poject = sdDto.getPoject();
				noofsub = sdDto.getNoOfSubjects();
				noofperiod = sdDto.getNoofperiod();	
				rpojo = sdDto.getRpojo();
				randomizationCode = sdDto.getRandomizationCode();
				pdrlist= sdDto.getPdrlist();
				sm = sdDto.getSm();
				drs = sdDto.getDrs();
				checkLoginUser = sdDto.getCheckLoginUser();
				if(drs!=null) {
					List<String> codeType=new ArrayList<>();
					for(ProjectsDetails comet:randomizationCode) {
						codeType.add(comet.getFieldValue());
					}
					List<String> code=new ArrayList<>();
					if(pdrlist != null && pdrlist.size() > 0) {
						for(ProjectDetailsRandomization ord:pdrlist) {
			    			String[] tempArr = ord.getRandomizationCode().split(",");
			    			if(!code.contains(tempArr[0])) {
			    				code.add(tempArr[0]);
			    			}
			    		}
					}
		    		boolean codeval=true;
		    		for(String ccc:code) {
		    			if(codeType.contains(ccc)) {
		    			}else {
		    				codeval=false;
		    			}
		    		}
		    		boolean radamizationFlag = false;
		    		if(rpojo != null) {
		    			if(noofsub.equals(rpojo.getNoofSubject()) && noofperiod.equals(rpojo.getNoofPeriod()) && codeval) {	
		    				radamizationFlag = true;
		    			}
		    		}else radamizationFlag = true;
					if(radamizationFlag) {	
						ActivityDraftReviewAudit adra=new ActivityDraftReviewAudit();
						adra.setDateOfActivity(new Date());
						adra.setReviewState(drs.getId());
						adra.setRole(checkLoginUser.getRole());
						adra.setUser(checkLoginUser);
						adra.setProjectId(prod.getProjectId());
						adra.setStatus("APPROVAL");
						
						
						poject.setStatus(sm);
						if(drs.getToRole() == null) {
							poject.setRoleId(0);
							finalFlag = true;
						}else {
							poject.setRoleId(drs.getToRole().getId());
						}
						//Collectiing all StudyDesing Data into single Object and Saving study Information and StudyActivities
						if(finalFlag) {
							spDto = studyCreationBo.mapProjectDetailsToStudyMaster(poject);
							finalResult = studyCreationDao.saveStudyCompleteInfomation(spDto, adra, poject);
						}
						if(finalResult.equals("success")) 
							result =messageSource.getMessage("label.project.stucces", null,currentLocale);
						else result = messageSource.getMessage("label.project.Fail", null,currentLocale);
					}
				}else{
					result = messageSource.getMessage("label.projet.reviewStatus", null,currentLocale);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Projects> getProjectWithProjectNo(String prono) {
		return studyCreationDao.getProjectWithProjectNo(prono);
	}
	
	@Override
	public void updateOtherActivityParameters(Long projectId,Long parameterId,Long activityId, boolean isDelete, String userName) {
		studyDesignDao.updateOtherActivityParameters(projectId, parameterId, activityId, isDelete, userName);
	}

	@Override
	public boolean checkCustomActivityParameterExistsOrNot(Long activityId, Long parameterId) {
		CustomActivityParameters cap = null;
		boolean flag = false;
		try {
			cap = studyDesignDao.checkCustomActivityParameterExistsOrNot(activityId, parameterId);
			if(cap != null) 
				flag = true; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public Integer addActivityParameter(long projectId, Long activityId, Long parameterId, String userName) {
		return studyDesignDao.addActivityParameter(projectId, activityId, parameterId, userName);
	}

	@Override
	public ProjectDetailsDto getProjectsDetailsVal(long projectId, String format) {
		return studyDesignDao.getProjectsDetailsVal(projectId, format);
	}
}
