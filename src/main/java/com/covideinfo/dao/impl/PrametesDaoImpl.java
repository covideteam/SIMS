package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.PrametesDao;
import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalParameterDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.model.ControlType;
import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.UnitsMaster;

@Repository("prametesDao")
public class PrametesDaoImpl extends AbstractDao<Long, GlobalValues> implements PrametesDao {

	@SuppressWarnings("unchecked")
	@Override
	public GlobalParameterDto getGlobalParameterDtoDetails(Long langId) {
		GlobalParameterDto gpdto = null;
		List<InternationalizaionLanguages> inlList = null;
		List<LanguageSpecificGlobalParameterDetails> lspdList = null;
		List<GlobalGroups> ggList = null;
		List<ControlType> controlType = null;
		List<GlobalValues> valsList = null;
		List<GlobalActivity> gaList = null;
		List<ParameterControlTypeValues> fpctvList = new ArrayList<>();
		List<UnitsMaster> umList = null;
		InternationalizaionLanguages inlag = null;
		List<StudyPhases> phaseList= null;
		try {
			inlList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			lspdList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					.add(Restrictions.eq("inlagId.id", langId)).list();
			ggList = getSession().createCriteria(GlobalGroups.class).list();
			controlType = getSession().createCriteria(ControlType.class).list();
			valsList = getSession().createCriteria(GlobalValues.class).list();
			gaList = getSession().createCriteria(GlobalActivity.class).list();
			phaseList= getSession().createCriteria(StudyPhases.class).list();
			
			for(LanguageSpecificGlobalParameterDetails lsgp : lspdList) {
				List<ParameterControlTypeValues> pctvList =  getSession().createCriteria(ParameterControlTypeValues.class)
						.add(Restrictions.eq("globalParameter", lsgp.getParameterId())).list();
				if(pctvList != null) {
					fpctvList.addAll(pctvList);
				}
			}
			umList = getSession().createCriteria(UnitsMaster.class).list();
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			
			gpdto = new GlobalParameterDto();
			gpdto.setInlList(inlList);
			gpdto.setLspdList(lspdList);
			gpdto.setControlType(controlType);
			gpdto.setGgList(ggList);
			gpdto.setValsList(valsList);
			gpdto.setGaList(gaList);
			gpdto.setPctvList(fpctvList);
			gpdto.setUmList(umList);
			gpdto.setInlag(inlag);
			gpdto.setPhaseList(phaseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gpdto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalParameterDto getGlobalParameterDetails(Long groupId, String controlId, List<Long> valIds,
			Long activityId, String bindVal, Long unitsId) {
		GlobalParameterDto gpDto = null;
		GlobalGroups group = null;
		ControlType ctPojo = null;
		List<GlobalValues> gvList = null;
		GlobalActivity gaPojo = null;
		UnitsMaster units = null;
		try {
			group = (GlobalGroups) getSession().createCriteria(GlobalGroups.class)
					.add(Restrictions.eq("id", groupId)).uniqueResult();
			
			ctPojo = (ControlType) getSession().createCriteria(ControlType.class)
					.add(Restrictions.eq("code", controlId)).uniqueResult();
			
			gvList = getSession().createCriteria(GlobalValues.class)
					.add(Restrictions.in("id", valIds)).list();
			
			gaPojo = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			units = (UnitsMaster)getSession().createCriteria(UnitsMaster.class)
					.add(Restrictions.eq("id", unitsId)).uniqueResult();
			
			gpDto = new GlobalParameterDto();
			gpDto.setGroup(group);
			gpDto.setCtPojo(ctPojo);
			gpDto.setGvList(gvList);
			gpDto.setGaPojo(gaPojo);
			gpDto.setUnitsPojo(units);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDto;
	}

	@Override
	public String saveGlobalParameterRecords(GlobalParameter gp, List<LanguageSpecificGlobalParameterDetails> lsgpdList,
			List<ParameterControlTypeValues> pctvList) {
		String result = "Failed";
		long gpNo = 0;
		boolean isgpFlag = false;
		boolean pctvFlag = false;
		try {
			gpNo = (long) getSession().save(gp);
			if(gpNo > 0) {
				if(lsgpdList.size() > 0) {
					for(LanguageSpecificGlobalParameterDetails lsgpd : lsgpdList) {
						lsgpd.setParameterId(gp);
						getSession().save(lsgpd);
						isgpFlag = true;
					}
				}else isgpFlag = true;
				if(pctvList.size() > 0) {
					for(ParameterControlTypeValues pctv : pctvList) {
						pctv.setGlobalParameter(gp);
						getSession().save(pctv);
						pctvFlag = true;
					}
				}else pctvFlag = true;
			}
			if(isgpFlag && pctvFlag)
				result = "success";
				
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	
	/*
	 * Gets language specific activity parameters 
	 * @param activityId - activity id 
	 * @param langId - language id
	 * 
	 * @List<ParameterDto> - list of language specific activity parameters
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterDto> getActivityDefaultParameters(Long activityId,Long langId) {
		DefaultActivitys defaultActivity = (DefaultActivitys)getSession().createCriteria(DefaultActivitys.class).add(Restrictions.eq("activity.id", activityId)).uniqueResult();
		List<Long> parameterIds = getSession().createCriteria(DefaultActivityParameters.class).
							add(Restrictions.eq("defalutActId.id", defaultActivity.getId())).
							setProjection(Projections.property("parameter.id")).list();
		
		List<LanguageSpecificGlobalParameterDetails> parameters = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					.add(Restrictions.in("parameterId.id", parameterIds))
					.add(Restrictions.eq("inlagId.id", langId)).list();
		
		List<ParameterDto> parameterList = new ArrayList<>();
		for(LanguageSpecificGlobalParameterDetails parameter : parameters) {
			ParameterDto parameterDto = new ParameterDto();
			parameterDto.setParameterId(parameter.getParameterId().getId());
			parameterDto.setParameterName(parameter.getParameterId().getParameterName());
			parameterList.add(parameterDto);
		}
		return parameterList;
	}
	
	/*
	 * Gets language specific multiple activity parameters 
	 * @param activityIds - activity id 
	 * @param langId - language id
	 * 
	 * @List<ParameterDto> - list of language specific activity parameters
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterDto> getActivityDefaultParameters(Long[] activityIds, Long langId) {
		List<ParameterDto> pdtoList = new ArrayList<>();
		List<Long> daIds = null;
		List<DefaultActivityParameters> defaultActivityParameters = null;
		List<Long> parameterIds = new ArrayList<>();
		List<LanguageSpecificGlobalParameterDetails> parameters = null;
		try {
			daIds = getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.in("activity.id", activityIds))
					.setProjection(Projections.property("id")).list();
			if(daIds != null && daIds.size() > 0) {
				defaultActivityParameters = getSession().createCriteria(DefaultActivityParameters.class).
						add(Restrictions.in("defalutActId.id",daIds)).list();
				if(defaultActivityParameters != null && defaultActivityParameters.size() > 0) {
					for(DefaultActivityParameters dap : defaultActivityParameters) {
						if(!parameterIds.contains(dap.getParameter().getId()))
							parameterIds.add(dap.getParameter().getId());
					}
				}
			}
			if(parameterIds != null && parameterIds.size() > 0) {
				parameters = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.in("parameterId.id", parameterIds))
						.add(Restrictions.eq("inlagId.id", langId)).list();
				if(parameters != null && parameters.size() > 0) {
					for(LanguageSpecificGlobalParameterDetails lsgp : parameters) {
						ParameterDto parameterDto = new ParameterDto();
						parameterDto.setParameterId(lsgp.getParameterId().getId());
						parameterDto.setParameterName(lsgp.getParameterId().getParameterName());
						
						for(DefaultActivityParameters dap : defaultActivityParameters) {
							if(lsgp.getParameterId().getId() == dap.getParameter().getId()) {
								parameterDto.setActivityId(dap.getDefalutActId().getActivity().getId());
								break;
							}
						}
						pdtoList.add(parameterDto);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdtoList;
	}

	@Override
	public GlobalParameter parameterNameCheckExitOrNot(String value) {
		GlobalParameter pojo=null;
		pojo=(GlobalParameter) getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.eq("parameterName", value)).uniqueResult();
		return pojo;
	}
}
