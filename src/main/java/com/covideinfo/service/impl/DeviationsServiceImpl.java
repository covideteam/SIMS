package com.covideinfo.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DeviationsDao;
import com.covideinfo.dto.DeviationsDto;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.DeviationsService;

@Service("deviationsService")
public class DeviationsServiceImpl implements DeviationsService {
	
	@Autowired
	private DeviationsDao deviationsDao;
	
	
	@Override
	public List<StudyMaster> getStudyMastersList() {
		return deviationsDao.getStudyMastersList();
	}

	@Override
	public DeviationsDto getDeviationRecords(Long projectId, Long roleId, String type) {
		DeviationsDto devDto = null;
		try {
			devDto = deviationsDao.getgetDeviationRecords(projectId, roleId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devDto;
	}

	@Override
	public String saveDeviationRecords(Long deviationId, Long roleId, String type, String comments, Long userId, MessageSource messageSource, 
			Locale currentLocale, String submitType) {
		String result ="";
		String resultMsg = "Success";
		try {
			result = deviationsDao.saveDeviationRecords(deviationId, roleId, type, comments, userId, submitType);
			if(result.equals("NeedWorkFlow"))
				resultMsg = messageSource.getMessage("label.deviationNeedWorkFlow", null, currentLocale);
			else if(result.equals("Failed"))
				resultMsg = messageSource.getMessage("label.deviationReviewDataFailed", null, currentLocale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMsg;
	}

	

}
