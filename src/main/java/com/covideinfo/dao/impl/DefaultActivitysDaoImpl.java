package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DefaultActivitysDao;
import com.covideinfo.dto.DefaultActivityDto;
import com.covideinfo.model.DefaultActivityParameters;
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

@Repository("defaultActivitysDao")
public class DefaultActivitysDaoImpl extends AbstractDao<Long, DefaultActivitys> implements DefaultActivitysDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivityList() {
		List<GlobalActivity> galist=null;
		galist=getSession().createCriteria(GlobalActivity.class)
		.addOrder(Order.asc("createdOn")).list();
		return galist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalParameterList() {
		List<GlobalParameter> gplist=null;
		gplist=getSession().createCriteria(GlobalParameter.class)
		.addOrder(Order.asc("createdOn")).list();
		return gplist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPhases> getStudyPhasesList() {
		List<StudyPhases> splist=null;
		splist=getSession().createCriteria(StudyPhases.class)
		.addOrder(Order.asc("createdOn")).list();
		return splist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultActivitys> getDefaultActivitysList() {
		List<DefaultActivitys> dalist=null;
		dalist=getSession().createCriteria(DefaultActivitys.class).list();
		return dalist;
	}

	@Override
	public DefaultActivitys getDefaultActivitysExit(long actid, List<Long> paramid, long phaseid) {
		DefaultActivitys dapojo=null;
		try {
			dapojo = (DefaultActivitys) getSession().createCriteria(DefaultActivityParameters.class)
					   .add(Restrictions.eq("activity.id", actid)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dapojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyActivityData> getStudyActivityDataList() {
		List<StudyActivityData> salist=null;
		salist=getSession().createCriteria(StudyActivityData.class).list();
		return salist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyCheckInActivityDataDetails> getStudyCheckInActivityDataDetailsWithIdList(long id) {
		List<StudyCheckInActivityDataDetails> datalist=null;
		datalist=getSession().createCriteria(StudyCheckInActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.list();
		return datalist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyCheckOutActivityDataDetails> getStudyCheckOutActivityDataDetailsWithIdList(long id) {
		List<StudyCheckOutActivityDataDetails> datalist=null;
		datalist=getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.list();
		return datalist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyExecutionActivityDataDetails> getStudyExecutionActivityDataDetailsWithIdList(long id) {
		List<StudyExecutionActivityDataDetails> datalist=null;
		datalist=getSession().createCriteria(StudyExecutionActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.list();
		return datalist;
	}

	@Override
	public StudyActivityData getStudyActivityDataWithId(long id) {
		StudyActivityData dpojo=null;
		dpojo=(StudyActivityData) getSession().createCriteria(StudyActivityData.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return dpojo;
	}

	@Override
	public StatusMaster getStatusMaster(long sid) {
		StatusMaster dpojo=null;
		dpojo=(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("id", sid)).uniqueResult();
		return dpojo;
	}

	@Override
	public boolean updateStudyActivityData(StudyActivityData saData) {
		boolean flag=false;
		try {
			getSession().update(saData);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public DraftReviewStage getDraftReviewStageWithFromRoleAndActionName(long id, String accept) {
		DraftReviewStage dpojo=null;
		dpojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole.id", id))
				.add(Restrictions.eq("action", accept)).uniqueResult();
		return dpojo;
	}

	@Override
	public boolean saveStudyActivityDataReviewAudit(StudyActivityDataReviewAudit sadra) {
		boolean flag=false;
		try {
			getSession().save(sadra);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public DefaultActivitys getPahsesWithSAD_Id(StudyActivityData saData) {
		DefaultActivitys dpojo=null;
		dpojo=(DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
				.add(Restrictions.eq("activity", saData.getSutydActivity().getActivityId())).uniqueResult();
		return dpojo;
	}

	@Override
	public StudyCheckInActivityDataDetails getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		StudyCheckInActivityDataDetails dpojo=null;
		dpojo=(StudyCheckInActivityDataDetails) getSession().createCriteria(StudyCheckInActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.add(Restrictions.eq("id", did)).uniqueResult();
		return dpojo;
	}

	@Override
	public StudyCheckOutActivityDataDetails getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		StudyCheckOutActivityDataDetails dpojo=null;
		dpojo=(StudyCheckOutActivityDataDetails) getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.add(Restrictions.eq("id", did)).uniqueResult();
		return dpojo;
	}

	@Override
	public StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(long id, long did) {
		StudyExecutionActivityDataDetails dpojo=null;
		dpojo=(StudyExecutionActivityDataDetails) getSession().createCriteria(StudyExecutionActivityDataDetails.class)
				.add(Restrictions.eq("saData.id", id))
				.add(Restrictions.eq("id", did)).uniqueResult();
		return dpojo;
	}

	@Override
	public boolean saveDiscrepancy(StudyActivityDataDetailsDiscrepancy sdpojo) {
		
		boolean flag=false;
		try {
			getSession().save(sdpojo);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public StudyActivityDataDetailsDiscrepancy getStudyActivityDataDetailsDiscrepancyWithId(long id) {
		StudyActivityDataDetailsDiscrepancy dpojo=null;
		dpojo=(StudyActivityDataDetailsDiscrepancy) getSession().createCriteria(StudyActivityDataDetailsDiscrepancy.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return dpojo;
	}

	@Override
	public boolean updateDiscrepancy(StudyActivityDataDetailsDiscrepancy saddd) {
		boolean flag=false;
		try {
			getSession().update(saddd);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean getStudyActivityDataDetailsDiscrepancyCheck(StudyActivityData saData) {
		boolean flag=false;
		List<StudyActivityDataDetailsDiscrepancy> datalist=null;
		datalist=getSession().createCriteria(StudyActivityDataDetailsDiscrepancy.class)
				.add(Restrictions.eq("studyActionData", saData))
				.list();
		if(datalist==null) {
			flag=true;
		}
		
		return flag;
	}

	@Override
	public InternationalizaionLanguages getInternationalizaionLanguagesWithCountryCode(String lang) {
		InternationalizaionLanguages dpojo=null;
		dpojo=(InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("countryCode", lang)).uniqueResult();
		return dpojo;
	}

	@Override
	public LanguageSpecificValueDetails getLanguageSpecificValueDetail(GlobalValues gvalue,
			InternationalizaionLanguages lanuage) {
		LanguageSpecificValueDetails dpojo=null;
		dpojo=(LanguageSpecificValueDetails) getSession().createCriteria(LanguageSpecificValueDetails.class)
				.add(Restrictions.eq("globalValId", gvalue))
				.add(Restrictions.eq("inlagId", lanuage)).uniqueResult();
		return dpojo;
	}

	@Override
	public LanguageSpecificGlobalParameterDetails getLanguageSpecificGlobalParameterDetailsWithLang(
			StudyActivityParameters saParameter, InternationalizaionLanguages lanuage) {
		LanguageSpecificGlobalParameterDetails dpojo=null;
		dpojo=(LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("parameterId", saParameter.getParameterId()))
				.add(Restrictions.eq("inlagId", lanuage)).uniqueResult();
		return dpojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterControlTypeValues> getParameterControlTypeValuesWithGparameter(GlobalParameter parameterId) {
		List<ParameterControlTypeValues> pctv=null;
		pctv= (List<ParameterControlTypeValues>) getSession().createCriteria(ParameterControlTypeValues.class)
				.add(Restrictions.eq("globalParameter",parameterId)).list();
		return pctv;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DefaultActivityDto getDefaultActivityDtoDetails() {
		DefaultActivityDto daDto = null;
		List<GlobalActivity> gaList = null;
		List<GlobalParameter> gpList = null;
		List<StudyPhases> phaseList = null;
		List<DefaultActivitys> daList = null;
		Map<Long, List<DefaultActivityParameters>> dapMap = new HashMap<>();
		try {
			gaList = getSession().createCriteria(GlobalActivity.class).list();
			gpList = getSession().createCriteria(GlobalParameter.class).list();
			phaseList= getSession().createCriteria(StudyPhases.class).list();
			daList = getSession().createCriteria(DefaultActivitys.class).list();
			if(daList != null) {
				for(DefaultActivitys da : daList) {
					List<DefaultActivityParameters> dapList = getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", da)).list();
					dapMap.put(da.getId(), dapList);
				}
			}
			
			
			daDto = new DefaultActivityDto();
			daDto.setDaList(daList);
			daDto.setGaList(gaList);
			daDto.setGpList(gpList);
			daDto.setPhaseList(phaseList);
			daDto.setDapMap(dapMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return daDto;
	}

	@Override
	public boolean saveDefaultActivity(long actid, List<Long> paramid, long phaseid, /*String tableName,*/ String subjectAllotment, String submitControlsString, String username,String getUrl, String saveUrl, String rejectUrl) {
		boolean flag = false;
		long dactNo = 0;
		GlobalParameter gp = null;
		GlobalActivity ga = null;
		StudyPhases sp = null;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", actid)).uniqueResult();
			
			sp = (StudyPhases) getSession().createCriteria(StudyPhases.class)
					.add(Restrictions.eq("id", phaseid)).uniqueResult();
			if(rejectUrl!="" && rejectUrl!=null) {
				ga.setRejectUrl(rejectUrl);
			}else {
				ga.setRejectUrl("0");
			}
			if(saveUrl!="" && saveUrl!=null) {
				ga.setSaveUrl(saveUrl);
			}else {
				ga.setSaveUrl("0");
			}
			if(getUrl!="" && getUrl!=null) {
				ga.setGetUrl(getUrl);
			}else {
				ga.setGetUrl("0");
			}
			
			DefaultActivitys dapojo = new DefaultActivitys();
			dapojo.setActivity(ga);
			dapojo.setCreatedBy(username);
			dapojo.setCreatedOn(new Date());
			dapojo.setStudyPhases(sp);
			dapojo.setSubjectAllotment(subjectAllotment);
			/*dapojo.setTableName(tableName);*/
			dapojo.setSubmitControls(submitControlsString);
			
			dactNo = (long) getSession().save(dapojo);
			if(dactNo > 0) {
				if(paramid.size() > 0) {
					for(Long pid : paramid) {
						gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
								.add(Restrictions.eq("id", pid)).uniqueResult();
						DefaultActivityParameters dap = new DefaultActivityParameters();
						dap.setCreatedBy(username);
						dap.setCreatedOn(new Date());
						dap.setDefalutActId(dapojo);
						dap.setParameter(gp);
						getSession().save(dap);
						flag = true;
					}
				}else flag = true;
			}
			if(flag) {
				getSession().update(ga);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public DefaultActivitys getDefaultActivitysWithGlobalActivity(long id) {
		DefaultActivitys pojo=null;
		pojo=(DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
				.add(Restrictions.eq("activity.id", id)).uniqueResult();
		return pojo;
	}




}
