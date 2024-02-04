package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.SubjectDiscountinueDao;
import com.covideinfo.dto.ParameterModelDto;
import com.covideinfo.dto.StudyWitdrawalSavingDto;
import com.covideinfo.dto.StudyWithDrawDetailsDto;
import com.covideinfo.dto.StudyWithDrawDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.SubjectDiscontinueService;

@Service("subjectDiscontinueService")
public class SubjectDiscontinueServiceImpl implements SubjectDiscontinueService {
	
	@Autowired
	SubjectDiscountinueDao subjectdiscDao;

	@Override
	public String updateStudySubjectRecords(SubjectActivityData sad, String userName) {
		String finalResult ="Failed";
		List<ParameterModelDto> paramList = sad.getPmDto();
		List<Long> parmIdsList = new ArrayList<>();
		Map<Long, String> parmMapVals = new HashMap<>();
		List<GlobalParameter> gpList = null;
		String subjectDiscountinue = sad.getSubjectDiscountinue();
		try {
			if(paramList != null && paramList.size() > 0) { //ParamList contains parmeter and that parmeter value
				for(ParameterModelDto pmd : paramList) {
					parmIdsList.add(pmd.getParameterId());
					parmMapVals.put(pmd.getParameterId(), pmd.getParameterValue());
				}
				
			}
			//Dummy withdraw parmeter Name value is No
			CustomActivityParameters cap = subjectdiscDao.getCustomActivityParametersRecord(sad.getActivityId());
			if(cap != null) {
				String parameterName = cap.getParameter().getParameterName();
			    if(parmIdsList.size() > 0) {
			    	gpList = subjectdiscDao.getGlobalparametersList(parmIdsList);
			    	if(gpList.size() > 0) {
			    		if(subjectDiscountinue != null && !subjectDiscountinue.equals("") && subjectDiscountinue.equals("Yes")) {
			    			StudySubjects ssub = subjectdiscDao.getStudySubjectRecord(sad.getVolId());
			    			if(ssub != null) {
			    				if(ssub.getSubjectDiscontinue().equalsIgnoreCase(sad.getSubjectDiscountinue())) {
			    					ssub.setSubjectReplace(sad.getSubjectReplace());
				    				ssub.setSubjectDiscontinue(sad.getSubjectDiscountinue());
				    				ssub.setUpatedBy(userName);
				    				ssub.setUpdatedOn(new Date());
				    				ssub.setSubjectStatus("DropOut");
				    				finalResult = subjectdiscDao.updateStudySubjectRecord(ssub);
			    				}
			    			}
			    		}else {
			    			StudySubjects ssub = subjectdiscDao.getStudySubjectRecord(sad.getVolId());
			    			if(ssub != null) {
			    				if(!ssub.getSubjectDiscontinue().equalsIgnoreCase(sad.getSubjectDiscountinue()) || !ssub.getSubjectDiscontinue().equalsIgnoreCase(sad.getSubjectDiscountinue())) {
			    					ssub.setSubjectReplace(sad.getSubjectReplace());
				    				ssub.setSubjectDiscontinue(sad.getSubjectDiscountinue());
				    				ssub.setUpatedBy(userName);
				    				ssub.setSubjectStatus("DropOut");
				    				ssub.setUpdatedOn(new Date());
				    				finalResult = subjectdiscDao.updateStudySubjectRecord(ssub);
			    				}
			    			}
			    		}
			    	}
			    }
			}else finalResult = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
	}

	@Override
	public StudyWithDrawDto getStudyWithDrawDtoDetails(Long studyId) {
		StudyWithDrawDto swdDto = null;
		Map<String, List<StaticActivityDetails>> actMap = new HashMap<>();
		Map<Long, List<StaticActivityValueDetails>> actValMap = new HashMap<>();
		StudyWithDrawDetailsDto swddDto = null;
		List<StaticActivityDetails> actList = null;
		List<StaticActivityValueDetails> actValList = null;
		List<StaticActivityDetails> tempActList = null;
		List<StaticActivityValueDetails> tempActValList = null;
		try {
			swddDto = subjectdiscDao.getStudyWithDrawDetailsDtoDetails(studyId);
			 if(swddDto != null) {
				 actList = swddDto.getActList();
				 actValList = swddDto.getActValList();
				 if(actList != null && actList.size() > 0) {
					 for(StaticActivityDetails sad : actList) {
						 if(actMap.containsKey(sad.getActivityType())) {
							 tempActList = actMap.get(sad.getActivityType());
							 tempActList.add(sad);
							 actMap.put(sad.getActivityType(), tempActList);
						 }else {
							 tempActList = new ArrayList<>();
							 tempActList.add(sad);
							 actMap.put(sad.getActivityType(), tempActList);
						 }
					 }
				 }
				 if(actValList != null && actValList.size() > 0) {
					 for(StaticActivityValueDetails savd : actValList) {
						 if(actValMap.containsKey(savd.getStActDetailsId().getId())) {
							 tempActValList = actValMap.get(savd.getStActDetailsId().getId());
							 tempActValList.add(savd);
							 actValMap.put(savd.getStActDetailsId().getId(), tempActValList);
						 }else {
							 tempActValList = new ArrayList<>();
							 tempActValList.add(savd);
							 actValMap.put(savd.getStActDetailsId().getId(), tempActValList);
						 }
					 }
				 }
			 }
			 swdDto = new StudyWithDrawDto();
			 swdDto.setActMap(actMap);
			 swdDto.setActValMap(actValMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return swdDto;
	}

	@Override
	public String saveStaticActivityDataDetails(StudyWitdrawalSavingDto swsDto, Long userId, String dateFormat) {
		String result = "Failed";
		try {
			result = subjectdiscDao.saveStaticActivityDataDetails(swsDto, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
