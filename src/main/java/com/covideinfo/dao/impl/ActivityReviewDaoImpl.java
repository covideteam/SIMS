package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ActivityReviewDao;
import com.covideinfo.dto.ActivityDataDetailsDto;
import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.RequestStatusDto;
import com.covideinfo.dto.ResultDto;
import com.covideinfo.dto.StudyActivityDataReviewDto;
import com.covideinfo.enums.WorkFlowReviewStatus;
import com.covideinfo.enums.WorkFlowTypes;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyActivityDataReviewAudit;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DefaultActivitysService;
import com.covideinfo.service.UserService;
import com.covideinfo.service.WorkflowService;

@Repository("activityReviewDao")
public class ActivityReviewDaoImpl extends AbstractDao<Long, GlobalActivity> implements ActivityReviewDao {
	@Autowired
	DefaultActivitysService defaultActivitysService;
	
	@Autowired
	WorkflowService workFlowService;
	
	@Autowired
	UserService userService;
	
	@Autowired  
	MessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyActivityData> getStudyActivityDataRecordsList(Long activityId) {
		List<Long> saIdsList = null;
		List<StudyActivityData> sadList = null;
		try {
			saIdsList =  getSession().createCriteria(StudyActivities.class)
					.add(Restrictions.eq("activityId.id", activityId))
					.setProjection(Projections.property("id")).list();
			if(saIdsList != null && saIdsList.size() > 0) {
				sadList = getSession().createCriteria(StudyActivityData.class)
						.add(Restrictions.in("sutydActivity.id", saIdsList))
						.add(Restrictions.eq("status", "In-Review")).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sadList;
	}
	
	@SuppressWarnings("unchecked")
	/** 
     * This method returns the study activity data pending for review or update.
     * @param studyId.
     * @param activityId.
     * @param roleId role id of the loin user.
     * @return List<StudyActivityData> This returns the study activity data list
     */ 
	@Override
	public List<StudyActivityData> getStudyActivityDataForReview(Long studyId, Long activityId, Long roleId) {
		List<Long> saIdsList = null;
		List<StudyActivityData> sadList = null;
		try {
			saIdsList =  getSession().createCriteria(StudyActivities.class)
					.add(Restrictions.eq("activityId.id", activityId))
					.add(Restrictions.eq("sm.id", studyId))
					.setProjection(Projections.property("id")).list();
			if(saIdsList != null && saIdsList.size() > 0) {
				sadList = getSession().createCriteria(StudyActivityData.class)
						.add(Restrictions.in("sutydActivity.id", saIdsList))
						.add(Restrictions.eq("roleId", roleId))
						.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sadList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityDataDetailsDto getActivityDataDetails(Long activityDataId, Locale currentLocale) {
		List<StudyCheckInActivityDataDetails> sachkinList= null;
		List<StudyCheckOutActivityDataDetails> sachkoutList = null;
		List<StudyExecutionActivityDataDetails> stdexeList = null;
		List<StudyActivityDataDetailsDiscrepancy> stdActivityDiscrepancyList = null;
		ActivityDataDetailsDto actddto = null;
		String country = currentLocale.getCountry();
		InternationalizaionLanguages inlg = null;
		try {
			inlg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("countryCode", country)).uniqueResult();
		
			sachkinList = getSession().createCriteria(StudyCheckInActivityDataDetails.class)
					.add(Restrictions.eq("saData.id", activityDataId)).list();
			if(sachkinList == null) {
				sachkoutList =  getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
						.add(Restrictions.eq("saData.id", activityDataId)).list();
				if(sachkoutList == null) {
					stdexeList =  getSession().createCriteria(StudyExecutionActivityDataDetails.class)
							.add(Restrictions.eq("saData.id", activityDataId)).list();
				}
			}
			
			stdActivityDiscrepancyList =  getSession().createCriteria(StudyActivityDataDetailsDiscrepancy.class)
													  .add(Restrictions.eq("studyActionData.id", activityDataId)).list();
			
			actddto = new ActivityDataDetailsDto();
			actddto.setSachkinList(sachkinList);
			actddto.setSachkoutList(sachkoutList);
			actddto.setStdexeList(stdexeList);
			actddto.setStdActivityDiscrepancyList(stdActivityDiscrepancyList);
			
			actddto.setInlg(inlg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actddto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalparameterDetails(List<Long> parmIds, InternationalizaionLanguages inalg) {
		return getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("inlagId", inalg))
				.add(Restrictions.in("parameterId.id", parmIds)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getLanguageSpecificValuesDetails(long paramId, long controlId,
			InternationalizaionLanguages inalg) {
		List<LanguageSpecificValueDetails> lsvdList = null;
		List<Long> gvIds = null;
		try {
			gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("globalParameter.id", paramId))
					.add(Restrictions.eq("controlType.id", controlId))
					.setProjection(Projections.property("globalValues.id")).list();
			
			lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.in("globalValId.id", gvIds))
					.add(Restrictions.eq("inlagId", inalg)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsvdList;
	}

	@Override
	public RequestStatusDto acceptStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale) {
		RequestStatusDto requestStatus = new RequestStatusDto();
		UserMaster uspojo = userService.findByUsername(userName);
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(studyActivityData.getStudyActivityDataId());
		boolean exit=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyCheck(saData);
		if(!exit) {
			DraftReviewStage drs = workFlowService.GetWorkflow(WorkFlowTypes.StudyDataReview, WorkFlowReviewStatus.APPROVAL, uspojo.getRole().getId());
			if(drs!=null) {
				if(drs.getToRole() == null) {
					saData.setStatus("approval");
				}
				else {
					saData.setRoleId(drs.getToRole().getId());
				}
								
				StudyActivityDataReviewAudit sadra=new StudyActivityDataReviewAudit();
				sadra.setReviewState(drs.getId());
				sadra.setRole(uspojo.getRole());
				sadra.setDateOfActivity(new Date());
				sadra.setComment("");
				sadra.setStudyActivityDataId(saData);
				sadra.setUser(uspojo);
				SaveDiscrepancy(sadra,studyActivityData,userName);
				requestStatus.setResult(true);
				requestStatus.setMessage(messageSource.getMessage("label.acceptsmessage", null, locale));
			}
			else {
				requestStatus.setResult(false);
				requestStatus.setMessage(messageSource.getMessage("label.failedacceptsmessage", null, locale));
			}
		}
		else {
			requestStatus.setResult(false);
			requestStatus.setMessage(messageSource.getMessage("label.failedacceptsmessage", null, locale));
		}
		return requestStatus;
	}

	@Override
	public RequestStatusDto sendCommentsStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale) {
		RequestStatusDto requestStatus = new RequestStatusDto();
		UserMaster uspojo = userService.findByUsername(userName);
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(studyActivityData.getStudyActivityDataId());
		boolean exit=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyCheck(saData);
		if(!exit) {
			DraftReviewStage drs = workFlowService.GetWorkflow(WorkFlowTypes.StudyDataReview, WorkFlowReviewStatus.SENDCOMMENTS, uspojo.getRole().getId());
			if(drs!=null) {
				saData.setStatus("sendcomment");
				saData.setRoleId(drs.getToRole().getId());
				
				StudyActivityDataReviewAudit sadra=new StudyActivityDataReviewAudit();
				sadra.setReviewState(drs.getId());
				sadra.setRole(uspojo.getRole());
				sadra.setDateOfActivity(new Date());
				sadra.setComment("");
				sadra.setStudyActivityDataId(saData);
				sadra.setUser(uspojo);
				SaveDiscrepancy(sadra,studyActivityData,userName);
				requestStatus.setResult(true);
				requestStatus.setMessage(messageSource.getMessage("label.sendcommentsmessage", null, locale));
			}
			else {
				requestStatus.setResult(false);
				requestStatus.setMessage(messageSource.getMessage("label.failedsendcommentsmessage", null, locale));
			}
		}
		else {
			requestStatus.setResult(false);
			requestStatus.setMessage(messageSource.getMessage("label.failedsendcommentsmessage", null, locale));
		}
		return requestStatus;
	}
	
	@SuppressWarnings("unchecked")
	private void SaveDiscrepancy(StudyActivityDataReviewAudit studyActivityDataReviewAudit,StudyActivityDataReviewDto studyActivityDataDto, String userName) {
		List<CommentsDto> comments = studyActivityDataDto.getComments();
		Date currentDate = new Date();
		List<StudyActivityDataDetailsDiscrepancy> discrepancies = new ArrayList<>();
		
		StudyActivityData studyActivityData = (StudyActivityData)getSession().createCriteria(StudyActivityData.class).add(Restrictions.eq("id", studyActivityDataDto.getStudyActivityDataId())).uniqueResult();
		
		List<StudyCheckInActivityDataDetails> checkInSad = getSession().createCriteria(StudyCheckInActivityDataDetails.class)
				.add(Restrictions.eq("saData", studyActivityData)).list();
		
		if(checkInSad != null && checkInSad.size() > 0) {
			for(CommentsDto comment : comments) {
				for(StudyCheckInActivityDataDetails data: checkInSad) {
					if(data.getId() == comment.getParameterId()) {
						StudyActivityDataDetailsDiscrepancy sdpojo=new StudyActivityDataDetailsDiscrepancy();
						sdpojo.setComments(comment.getComment());
						sdpojo.setStudyActionData(data.getSaData());
						sdpojo.setStudyCheckInActivityData(data);
						sdpojo.setResponse("");
						sdpojo.setStatus("open");	
						sdpojo.setCreatedBy(userName);
						sdpojo.setCreatedOn(currentDate);
						discrepancies.add(sdpojo);
					}
				}
			}
		}
		
		if(discrepancies.size() == 0) {
			List<StudyCheckOutActivityDataDetails> checkOutSad = getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
					.add(Restrictions.eq("saData", studyActivityData)).list();
			
			if(checkOutSad != null && checkOutSad.size() > 0) {
				for(CommentsDto comment : comments) {
					for(StudyCheckOutActivityDataDetails data: checkOutSad) {
						if(data.getId() == comment.getParameterId()) {
							StudyActivityDataDetailsDiscrepancy sdpojo=new StudyActivityDataDetailsDiscrepancy();
							sdpojo.setComments(comment.getComment());
							sdpojo.setStudyActionData(data.getSaData());
							sdpojo.setStudyCheckOutActivityData(data);
							sdpojo.setResponse("");
							sdpojo.setStatus("open");	
							sdpojo.setCreatedBy(userName);
							sdpojo.setCreatedOn(currentDate);
							discrepancies.add(sdpojo);
						}
					}
				}
			}
		}
		
		if(discrepancies.size() == 0) {
			List<StudyExecutionActivityDataDetails> executionSad = getSession().createCriteria(StudyExecutionActivityDataDetails.class)
					.add(Restrictions.eq("saData", studyActivityData)).list();
			if(executionSad != null && executionSad.size() > 0) {
				for(CommentsDto comment : comments) {
					for(StudyExecutionActivityDataDetails data: executionSad) {
						if(data.getId() == comment.getParameterId()) {
							StudyActivityDataDetailsDiscrepancy sdpojo=new StudyActivityDataDetailsDiscrepancy();
							sdpojo.setComments(comment.getComment());
							sdpojo.setStudyActionData(data.getSaData());
							sdpojo.setStudyExecutionActivityData(data);
							sdpojo.setResponse("");
							sdpojo.setStatus("open");	
							sdpojo.setCreatedBy(userName);
							sdpojo.setCreatedOn(currentDate);
							discrepancies.add(sdpojo);
						}
					}
				}
			}
		}
		
		if(discrepancies.size() > 0) {
			getSession().save(studyActivityDataReviewAudit);	
			
			for(StudyActivityDataDetailsDiscrepancy dis: discrepancies) {
				getSession().save(dis);	
			}
		}
	}
}
