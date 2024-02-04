package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.RecannulaDao;
import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.RecannulationDto;
import com.covideinfo.dto.SubjectSamplesDto;
import com.covideinfo.service.RecannulaService;

@Service("recannulaService")
public class RecannulaServiceImpl implements RecannulaService {
	
	@Autowired
	RecannulaDao recannulaDao;

	/**
	 * This method gets the data from database by using given input studyId and subject number related to recannulation
	 * And what is the data we fetched from database that data customize as per our requirement and and returns to controller
	 */
	@Override
	public RecannulationDto getRecannulationDtoDetails(Long studyId, String subjNo) {
		RecannulationDto rcDto = null;
		List<SubjectSamplesDto> finalSubSampleDtoList = new ArrayList<>();
		List<RecannulationDataDto> recdDtoList = null;
		Map<Long, Map<Long, List<Long>>> mealIdsMap = new HashMap<>(); //subjectId, periodId, sampletpId, List of sampletps
		Map<Long, List<Long>> periodMap = null;
		List<Long> tempList = null;
		List<SubjectSamplesDto> ssDtoList = null;
		try {
			rcDto = recannulaDao.getRecannulationDtoDetails(studyId, subjNo);
			if(rcDto != null) {
				recdDtoList = rcDto.getRecdDtoList();
				ssDtoList = rcDto.getSubjectSamplesList();
			}
			if(recdDtoList != null && recdDtoList.size() > 0) {
				for(RecannulationDataDto rcd : recdDtoList) {
					if(rcd.getSampleId() != null) {
						if(mealIdsMap.containsKey(rcd.getSubjectId())) {
							periodMap = mealIdsMap.get(rcd.getSubjectId());
							if(periodMap.containsKey(rcd.getPeriodId())){
								tempList = periodMap.get(rcd.getPeriodId());
								if(tempList == null)
									tempList = new ArrayList<>();
								if(!tempList.contains(rcd.getSampleId())) 
									tempList.add(rcd.getSampleId());
								periodMap.put(rcd.getPeriodId(), tempList);
								mealIdsMap.put(rcd.getSubjectId(), periodMap);
							}
						}else {
							tempList = new ArrayList<>();
							periodMap = new HashMap<>();
							tempList.add(rcd.getSampleId());
							periodMap.put(rcd.getPeriodId(), tempList);
							mealIdsMap.put(rcd.getSubjectId(), periodMap);
						}
					}
				}
			}
			if(ssDtoList != null && ssDtoList.size() > 0) {
				for(SubjectSamplesDto ssDto : ssDtoList) {
					if(mealIdsMap.containsKey(ssDto.getSubjectId())) {
						if(mealIdsMap.get(ssDto.getSubjectId()).containsKey(ssDto.getPeriodId())) {
							if(!mealIdsMap.get(ssDto.getSubjectId()).get(ssDto.getPeriodId()).contains(ssDto.getSampleTpId())) {
								finalSubSampleDtoList.add(ssDto);
							}
						}else finalSubSampleDtoList.add(ssDto);
					}else finalSubSampleDtoList.add(ssDto);
				}
			}
			rcDto.setSubjectSamplesList(finalSubSampleDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rcDto;
	}

	@Override
	/**
	 * This method is passing data to database for saving the recannulation data  with user Information
	 */
	public String saveRecannulationData(RecannulationDataDto rcDto, Long userId) {
		return recannulaDao.saveRecannulationData(rcDto, userId);
	}
	


}
