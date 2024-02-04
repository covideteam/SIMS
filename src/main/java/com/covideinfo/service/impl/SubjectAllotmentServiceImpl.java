package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.SubjectAllotmentDao;
import com.covideinfo.dto.ParameterModelDto;
import com.covideinfo.dto.SubjectAllotmentDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.SubjectAllotmentService;

@Service("inclusionExclusionService")
public class SubjectAllotmentServiceImpl implements SubjectAllotmentService {
	
	@Autowired
	SubjectAllotmentDao subjectAllotmentDao;
	
	@Override
	public List<String> generateSubjectNo(Long studyId) {
		SubjectAllotmentDto sadto = null;
		List<String> subjList = null;
		int totalSubjects = 0;
		List<String> finalSubList = new ArrayList<>();
		try {
			sadto = subjectAllotmentDao.getSubjectAllotmentDtoDetails(studyId);
			if(sadto != null) {
				subjList = sadto.getSubjList();
				totalSubjects = sadto.getTotalSubjects();
				for(int i=1; i<=totalSubjects; i++) {
					String subNo = i+"";
					if(subNo.length()== 1)
						subNo = "0"+subNo;
					if(!subjList.contains(subNo))
						finalSubList.add(subNo);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalSubList;
	}

	@Override
	public String studySubjectAllotment(SubjectActivityData sad, String userName) {
		String finalResult ="Failed";
		List<ParameterModelDto> paramList = sad.getPmDto();
		List<Long> parmIdsList = new ArrayList<>();
		Map<Long, String> parmMapVals = new HashMap<>();
		List<GlobalParameter> gpList = null;
		try {
			if(paramList != null && paramList.size() > 0) { //ParamList contains parmeter and that parmeter value
				for(ParameterModelDto pmd : paramList) {
					parmIdsList.add(pmd.getParameterId());
					parmMapVals.put(pmd.getParameterId(), pmd.getParameterValue());
				}
				
			}
			
			//Dummy Subject Number parmeter Name value
			CustomActivityParameters cap = subjectAllotmentDao.getCustomActivityParametersRecord(sad.getActivityId());
			if(cap != null) {
				String parameterName = cap.getParameter().getParameterName();
			    if(parmIdsList.size() > 0) {
			    	gpList = subjectAllotmentDao.getGlobalparametersList(parmIdsList);
			    	if(gpList.size() > 0) {
			    		boolean flag = true;
			    		for(GlobalParameter gp : gpList) {
			    			if(parameterName.equals(gp.getParameterName())) {
			    				String subjectNo = parmMapVals.get(gp.getId());
			    				finalResult = subjectAllotmentDao.subjectAllotment(sad, userName, subjectNo);
			    				flag = false;
			    			}
			    		}
			    		if(flag)
			    			finalResult = "success";
			    	}
			    }
			}else finalResult = "success";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;

	}
	
}
