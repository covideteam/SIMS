package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.FitForStudyDao;
import com.covideinfo.dto.ParameterModelDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.FitForStudyService;

@Service("fitForStudyService")
public class FitForStudyServiceImpl implements FitForStudyService {
	
	@Autowired
	FitForStudyDao fitForStudyDao;

	@Override
	public String inactiveVolunteerRecord(SubjectActivityData sad, String userName) {
		String finalResult ="Failed";
		List<ParameterModelDto> paramList = sad.getPmDto();
		List<Long> parmIdsList = new ArrayList<>();
		Map<Long, String> parmMapVals = new HashMap<>();
		List<GlobalParameter> gpList = null;
		try {
			if(paramList != null && paramList.size() > 0) {//List of Parameter and parameter Values
				for(ParameterModelDto pmd : paramList) {
					parmIdsList.add(pmd.getParameterId());
					parmMapVals.put(pmd.getParameterId(), pmd.getParameterValue());
				}
			}
			CustomActivityParameters cap = fitForStudyDao.getCustomActivityParametersRecord(sad.getActivityId());
			if(cap != null) {
				String parameterName = cap.getParameter().getParameterName();
			    if(parmIdsList.size() > 0) {
			    	gpList = fitForStudyDao.getGlobalparametersList(parmIdsList);
			    	if(gpList.size() > 0) {
			    		boolean flag = true;
			    		for(GlobalParameter gp : gpList) {
			    			if(parameterName.equals(gp.getParameterName())) {
			    				if(parmMapVals.get(gp.getId()).equals(cap.getParameterValue())) {
			    					finalResult = fitForStudyDao.inactiveVolunteerRecord(sad.getVolId());
			    					flag = false;
			    				}
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
