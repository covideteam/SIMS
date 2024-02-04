package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DefaultActivitysDao;
import com.covideinfo.dto.DefaultActivityDto;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyActivityDataReviewAudit;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.service.DefaultActivitysService;

@Service("defaultActivitysService")
public class DefaultActivitysServiceImpl implements DefaultActivitysService {
	
	@Autowired
	DefaultActivitysDao defaultActivitysDao;

	@Override
	public List<GlobalActivity> getGlobalActivityList() {
		return defaultActivitysDao.getGlobalActivityList();
	}

	@Override
	public List<GlobalParameter> getGlobalParameterList() {
		return defaultActivitysDao.getGlobalParameterList();
	}

	@Override
	public List<StudyPhases> getStudyPhasesList() {
		return defaultActivitysDao.getStudyPhasesList();
	}

	@Override
	public List<DefaultActivitys> getDefaultActivitysList() {
		return defaultActivitysDao.getDefaultActivitysList();
	}

	@Override
	public DefaultActivitys getDefaultActivitysExit(long actid, List<Long> paramid, long phaseid) {
		return defaultActivitysDao.getDefaultActivitysExit(actid,paramid,phaseid);
	}

	@Override
	public List<StudyActivityData> getStudyActivityDataList() {
		return defaultActivitysDao.getStudyActivityDataList();
	}

	@Override
	public List<StudyCheckInActivityDataDetails> getStudyCheckInActivityDataDetailsWithIdList(long id) {
		return defaultActivitysDao.getStudyCheckInActivityDataDetailsWithIdList(id);
	}

	@Override
	public List<StudyCheckOutActivityDataDetails> getStudyCheckOutActivityDataDetailsWithIdList(long id) {
		return defaultActivitysDao.getStudyCheckOutActivityDataDetailsWithIdList(id);
	}

	@Override
	public List<StudyExecutionActivityDataDetails> getStudyExecutionActivityDataDetailsWithIdList(long id) {
		return defaultActivitysDao.getStudyExecutionActivityDataDetailsWithIdList(id);
	}

	@Override
	public StudyActivityData getStudyActivityDataWithId(long id) {
		return defaultActivitysDao.getStudyActivityDataWithId(id);
	}

	@Override
	public StatusMaster getStatusMaster(long sid) {
		return defaultActivitysDao.getStatusMaster(sid);
	}

	@Override
	public boolean updateStudyActivityData(StudyActivityData saData) {
		return defaultActivitysDao.updateStudyActivityData(saData);
	}

	@Override
	public DraftReviewStage getDraftReviewStageWithFromRoleAndActionName(long id, String accept) {
		return defaultActivitysDao.getDraftReviewStageWithFromRoleAndActionName(id,accept);
	}

	@Override
	public boolean saveStudyActivityDataReviewAudit(StudyActivityDataReviewAudit sadra) {
		return defaultActivitysDao.saveStudyActivityDataReviewAudit(sadra);
	}

	@Override
	public DefaultActivitys getPahsesWithSAD_Id(StudyActivityData saData) {
		return defaultActivitysDao.getPahsesWithSAD_Id(saData);
	}

	@Override
	public StudyCheckInActivityDataDetails getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		return defaultActivitysDao.getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(id,did);
	}

	@Override
	public StudyCheckOutActivityDataDetails getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		return defaultActivitysDao.getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(id,did);
	}

	@Override
	public StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		return defaultActivitysDao.getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(id,did);
	}

	@Override
	public boolean saveDiscrepancy(StudyActivityDataDetailsDiscrepancy sdpojo) {
		return defaultActivitysDao.saveDiscrepancy(sdpojo);
	}

	@Override
	public StudyActivityDataDetailsDiscrepancy getStudyActivityDataDetailsDiscrepancyWithId(long id) {
		return defaultActivitysDao.getStudyActivityDataDetailsDiscrepancyWithId(id);
	}

	@Override
	public boolean updateDiscrepancy(StudyActivityDataDetailsDiscrepancy saddd) {
		return defaultActivitysDao.updateDiscrepancy(saddd);
	}

	@Override
	public boolean getStudyActivityDataDetailsDiscrepancyCheck(StudyActivityData saData) {
		return defaultActivitysDao.getStudyActivityDataDetailsDiscrepancyCheck(saData);
	}

	@Override
	public InternationalizaionLanguages getInternationalizaionLanguagesWithCountryCode(String lang) {
		return defaultActivitysDao.getInternationalizaionLanguagesWithCountryCode(lang);
	}

	@Override
	public LanguageSpecificValueDetails getLanguageSpecificValueDetail(GlobalValues gvalue,
			InternationalizaionLanguages lanuage) {
		return defaultActivitysDao.getLanguageSpecificValueDetail(gvalue,lanuage);
	}

	@Override
	public LanguageSpecificGlobalParameterDetails getLanguageSpecificGlobalParameterDetailsWithLang(
			StudyActivityParameters saParameter, InternationalizaionLanguages lanuage) {
		return defaultActivitysDao.getLanguageSpecificGlobalParameterDetailsWithLang(saParameter,lanuage);
	}

	@Override
	public List<ParameterControlTypeValues> getParameterControlTypeValuesWithGparameter(GlobalParameter parameterId) {
		return defaultActivitysDao.getParameterControlTypeValuesWithGparameter(parameterId);
	}
	@Override
	public DefaultActivityDto getDefaultActivityDtoDetails() {
		return defaultActivitysDao.getDefaultActivityDtoDetails();
	}

	@Override
	public boolean saveDefaultActivity(long actid, List<Long> paramid, long phaseid,  /*String tableName,*/ String subjectAllotment,String submitControlsString ,String username,String getUrl, String saveUrl, String rejectUrl) {
		boolean flag = false;
		try {
			flag = defaultActivitysDao.saveDefaultActivity(actid, paramid, phaseid,/*tableName,*/ subjectAllotment, submitControlsString, username,getUrl,saveUrl,rejectUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public DefaultActivitys getDefaultActivitysWithGlobalActivity(long id) {
		// TODO Auto-generated method stub
		return defaultActivitysDao.getDefaultActivitysWithGlobalActivity(id);
	}
	
	
	
	

}
