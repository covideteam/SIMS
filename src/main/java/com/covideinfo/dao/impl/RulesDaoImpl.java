package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.RulesDao;
import com.covideinfo.dto.GlobalValuesDtoDetails;
import com.covideinfo.dto.ParameterDetailDto;
import com.covideinfo.dto.RulesDetailsDto;
import com.covideinfo.dto.RulesDto;
import com.covideinfo.dto.RulesValuesDto;
import com.covideinfo.dto.StudyActivityRulesDetailsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StudyActivityRuleMessages;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyMaster;

@Repository("rulesDao")
public class RulesDaoImpl extends AbstractDao<Long, StudyMaster> implements RulesDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public RulesDetailsDto getRulesDetailsDtoDetails(Long langId) {
		List<LanguageSpecificGlobalActivityDetails> lsgaList = null;
		List<InternationalizaionLanguages> inLagList = null;
		InternationalizaionLanguages inlag = null;
		RulesDetailsDto rdDto = null;
		try {
			inLagList = getSession().createCriteria(InternationalizaionLanguages.class).list();
					
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			
			lsgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("inlagId", inlag)).list();
			
			rdDto = new RulesDetailsDto();
			rdDto.setActList(lsgaList);
			rdDto.setInLagList(inLagList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getActivityParametersList(Long activityId, Long langId) {
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		InternationalizaionLanguages inlag = null;
		List<Long> gpIds = null;
		List<Long> gpNIds = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			
			gpIds = getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("activity.id", activityId))
					.setProjection(Projections.property("id")).list();
			
			/*gpNIds = getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.isNull("activity"))
					.setProjection(Projections.property("id")).list();
			if(gpNIds != null && gpNIds.size() > 0) {
				if(gpIds == null)
					gpIds = new ArrayList<>();
				gpIds.addAll(gpNIds);
			}*/
			if(gpIds.size() > 0) {
				lspgpdList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.in("parameterId.id", gpIds))
						.add(Restrictions.eq("inlagId", inlag)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgpdList;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public RulesValuesDto getRulesValuesDtoDetails(Long parameterId, Locale currentLocale, Long controlId) {
		RulesValuesDto rvDto = null;
		List<LanguageSpecificValueDetails> lspgvList = new ArrayList<>();
		InternationalizaionLanguages inlag = null;
		List<ParameterControlTypeValues> pcTypeValsList = null;
		LanguageSpecificGlobalParameterDetails lspgp = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
						.add(Restrictions.eq("countryCode", currentLocale.getCountry())).uniqueResult();
			lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
		    		.add(Restrictions.eq("parameterId.id", parameterId))
		    		.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
		    
		    if(lspgp != null) {
		    	pcTypeValsList =  getSession().createCriteria(ParameterControlTypeValues.class)
		    			.add(Restrictions.eq("globalParameter", lspgp.getParameterId()))
		    			.add(Restrictions.eq("controlType.id", controlId)).list();
		    	List<Long> gvIds = new ArrayList<>();
		    	if(pcTypeValsList != null && pcTypeValsList.size() > 0) {
		    		for(ParameterControlTypeValues pctv : pcTypeValsList) {
		    			 if(!gvIds.contains(pctv.getGlobalValues().getId()))
		    				 gvIds.add(pctv.getGlobalValues().getId());
		    		}
		    	}
		    	if(gvIds.size() > 0) {
	    			lspgvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
		    				.add(Restrictions.in("globalValId.id", gvIds))
		    				.add(Restrictions.eq("inlagId", inlag)).list();
	    		}
		    }
			rvDto = new RulesValuesDto();
			rvDto.setInlag(inlag);
			rvDto.setGp(lspgp);
			rvDto.setLspgvList(lspgvList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rvDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RulesDto getRulesDtoDetails(Long sourceActivity, Long sourcePramId, List<Long> lagList) {
		RulesDto rulesDto = null;
		GlobalActivity sourceAct = null;
		GlobalParameter sourceParameter = null;
		List<InternationalizaionLanguages> inlagList = null;
		try {
			sourceAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", sourceActivity)).uniqueResult();
			
			sourceParameter = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", sourcePramId)).uniqueResult();
			
			inlagList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			
			rulesDto = new RulesDto();
			rulesDto.setInlagList(inlagList);
			rulesDto.setSourceActivity(sourceAct);
			rulesDto.setSourceParameter(sourceParameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rulesDto;
	}

	@Override
	public boolean saveRulesData(StudyActivityRules sarule, List<StudyActivityRuleMessages> sarmList) {
		boolean flag = false;
		long saruleNo = 0;
		boolean sarmFlag = false;
		try {
			saruleNo = (long) getSession().save(sarule);
			if(saruleNo > 0) {
				if(sarmList.size() > 0) {
					for(StudyActivityRuleMessages sarm : sarmList) {
						sarm.setRule(sarule);
						getSession().save(sarm);
						sarmFlag = true;
					}
				}else sarmFlag = true;
			}
			if(sarmFlag && saruleNo > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public long saveRulesConditionData(StudyActivityRules sarule) {
		return (long) getSession().save(sarule);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RulesDto getRulesDtoDetailsForDependent(Long sourceActivity, Long sourcePramId, Long destActivity,
			Long destRuleParam, List<Long> multiParam) {
		RulesDto rulesDto = null;
		GlobalActivity sourceAct = null;
		GlobalParameter sourceParameter = null;
		GlobalActivity destAct = null;
		List<GlobalParameter> gpList = null;
		GlobalParameter destParm = null;
		try {
			sourceAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", sourceActivity)).uniqueResult();
			
			sourceParameter = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", sourcePramId)).uniqueResult();
			
			destAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", destActivity)).uniqueResult();
			
			gpList = getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.in("id", multiParam)).list();
			
			destParm = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", destRuleParam)).uniqueResult();
			
			rulesDto = new RulesDto();
			rulesDto.setDestActivity(destAct);
			rulesDto.setGpList(gpList);
			rulesDto.setSourceActivity(sourceAct);
			rulesDto.setSourceParameter(sourceParameter);
			rulesDto.setDestParm(destParm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rulesDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyActivityRulesDetailsDto getStudyActivityRulesDetailsDto(Long activityId, Long langId) {
		StudyActivityRulesDetailsDto sardDto = null;
		List<StudyActivityRules> stdactRList = null;
		List<StudyActivityRuleMessages> stdactRMList = null;
		InternationalizaionLanguages inlag = null;
		List<Long> sarIds = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
		
			stdactRList = getSession().createCriteria(StudyActivityRules.class)
					.add(Restrictions.eq("sourceActivity.id", activityId)).list();
			
			sarIds = getSession().createCriteria(StudyActivityRules.class)
					.add(Restrictions.eq("sourceActivity.id", activityId))
					.setProjection(Projections.property("id")).list();
			if(sarIds != null && sarIds.size() > 0) {
				stdactRMList = getSession().createCriteria(StudyActivityRuleMessages.class)
						.add(Restrictions.in("rule.id", sarIds))
						.add(Restrictions.eq("lang", inlag)).list();
			}
			sardDto = new StudyActivityRulesDetailsDto();
			sardDto.setStdactRList(stdactRList);
			sardDto.setStdactRMList(stdactRMList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sardDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getParameterListBasedOnIdsList(String multiParam, Long langId) {
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		List<Long> parmIds = new ArrayList<>();
		try {
			if(multiParam != null && !multiParam.equals("")) {
			     String[] tempArr = multiParam.split("\\,");
			     if(tempArr.length > 0) {
			    	 for(String st : tempArr) {
			    		 Long id = Long.parseLong(st);
			    		 if(!parmIds.contains(id)) {
			    			 parmIds.add(id);
			    		 }
			    	 }
			     }
			}
			if(parmIds.size() > 0) {
				gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.in("parameterId.id", parmIds))
						.add(Restrictions.eq("inlagId.id", langId)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivityList(Long acivityId) {
		return getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.ne("id", acivityId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public RulesDto getRulesDtoForDepedentActivities(Long sourceActivity, List<Long> destMultList) {
		GlobalActivity ga = null;
		List<GlobalActivity> gaList = null;
		RulesDto ruldto = null;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", sourceActivity)).uniqueResult();
			gaList = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("id", destMultList)).list();
			ruldto = new RulesDto();
			ruldto.setSourceActivity(ga);
			ruldto.setGaList(gaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruldto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivitiesListBasedOnIds(List<Long> depIds) {
		return getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.in("id", depIds)).list();
	}

	@Override
	public LanguageSpecificValueDetails getLanguageSpecificValueDetailsWithId(Long checkedLspId) {
		LanguageSpecificValueDetails lg=null;
		lg=(LanguageSpecificValueDetails) getSession().createCriteria(LanguageSpecificValueDetails.class)
				.add(Restrictions.eq("id",checkedLspId)).uniqueResult();
		return lg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParameterDetailDto getParameterType(Long parameterId, Long langId) {
		String controlType ="";
		GlobalParameter gp = null;
		ParameterDetailDto pdto = null;
		List<Long> gvIdsList = null;
		List<LanguageSpecificValueDetails> lsgvList = null;
		List<GlobalValuesDtoDetails> gvdList = new ArrayList<>();
		Long lsgpId = null;
		
		try {
			gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", parameterId)).uniqueResult();
			
			if(gp != null) {
				controlType = gp.getContentType().getCode();
				
				lsgpId = (Long) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.eq("parameterId", gp))
						.add(Restrictions.eq("inlagId.id", langId))
						.setProjection(Projections.property("id")).uniqueResult();
				if(controlType.equals("SB") || controlType.equals("RB") || controlType.equals("CB")) {
					gvIdsList = getSession().createCriteria(ParameterControlTypeValues.class)
							.add(Restrictions.eq("globalParameter", gp))
							.add(Restrictions.eq("controlType", gp.getContentType()))
							.setProjection(Projections.property("globalValues.id")).list();
					if(gvIdsList != null && gvIdsList.size() > 0) {
						lsgvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
								.add(Restrictions.in("globalValId.id", gvIdsList))
								.add(Restrictions.eq("inlagId.id", langId)).list();
						if(lsgvList != null && lsgvList.size() > 0) {
							for(LanguageSpecificValueDetails lsgvd : lsgvList) {
								GlobalValuesDtoDetails gvd = new GlobalValuesDtoDetails();
								gvd.setValueId(lsgvd.getGlobalValId().getId());
								gvd.setValueName(lsgvd.getName());
								gvdList.add(gvd);
							}
						}
					}
				}
			}
			pdto = new ParameterDetailDto();
			pdto.setControlType(controlType);
			pdto.setGvdList(gvdList);
			pdto.setLsvparamId(lsgpId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdto;
	}

	@Override
	public GlobalValues getglobalValueRecord(Long checkedLspId) {
		return (GlobalValues) getSession().createCriteria(GlobalValues.class)
				.add(Restrictions.eq("id", checkedLspId)).uniqueResult();
	}

	@Override
	public LanguageSpecificValueDetails getlsgvdetails(Long globalValId, Long langId) {
		InternationalizaionLanguages inlag = null;
		LanguageSpecificValueDetails lsgvd = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			lsgvd = (LanguageSpecificValueDetails) getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.eq("globalValId.id", globalValId))
					.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsgvd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivitiesList(List<Long> depIds,
			Long langId) {
		InternationalizaionLanguages inlag = null;
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			lspgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.in("globalActivity.id", depIds))
					.add(Restrictions.eq("inlagId", inlag)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RulesDto getRulesDtoDetailsForDependentActivities(Long sourceActivity, Long sourcePramId, List<Long> lagList,
			Long depParameter, List<Long> multiParam, Long destActivity, Long destRuleParam) {
		RulesDto rulesDto = null;
		GlobalActivity sourceAct = null;
		GlobalParameter sourceParameter = null;
		GlobalParameter destParameter = null;
		List<GlobalParameter> multiParameters = new ArrayList<>();
		GlobalActivity destinationActivity = null;
		GlobalParameter destRuleParameter = null;
		try {
			sourceAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", sourceActivity)).uniqueResult();
			
			destinationActivity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", destActivity)).uniqueResult();
			
			sourceParameter = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", sourcePramId)).uniqueResult();
			
			destRuleParameter = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", destRuleParam)).uniqueResult();
			
			if(sourceParameter != null)
				multiParameters.add(sourceParameter);
			destParameter = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", depParameter)).uniqueResult();
			if(destParameter != null)
				multiParameters.add(destParameter);
			
			if(destRuleParameter != null)
				multiParameters.add(destRuleParameter);
			
			if(multiParam != null && multiParam.size() > 0) {
				List<GlobalParameter> gpList = getSession().createCriteria(GlobalParameter.class)
						.add(Restrictions.in("id", multiParam)).list();
				if(gpList.size() > 0)
					multiParameters.addAll(gpList);
			}
			rulesDto = new RulesDto();
			rulesDto.setSourceActivity(sourceAct);
			rulesDto.setGpList(multiParameters);
			rulesDto.setDestActivity(destinationActivity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rulesDto;
	}

	@Override
	public LanguageSpecificGlobalParameterDetails getlanguageSpecificGlobalParameterDetails(
			GlobalParameter destParameter, Long langId) {
		return (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("parameterId", destParameter))
				.add(Restrictions.eq("inlagId.id", langId)).uniqueResult();
	}

	@Override
	public LanguageSpecificGlobalActivityDetails getlanguageSpecificGlobalActivitiesDetailsRecord(
			GlobalActivity destinationActivity, Long langId) {
		return (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
				.add(Restrictions.eq("globalActivity", destinationActivity))
				.add(Restrictions.eq("inlagId.id", langId)).uniqueResult();
	}

	

}
