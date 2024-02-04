package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dao.ActivityReviewDao;
import com.covideinfo.dto.ActivityDataDetailsDto;
import com.covideinfo.dto.ActivityDataReviewDataDto;
import com.covideinfo.dto.ActivityDataReviewDto;
import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.dto.RequestStatusDto;
import com.covideinfo.dto.ResultDto;
import com.covideinfo.dto.StudyActivityDataReviewDto;
import com.covideinfo.enums.ActivityDataTables;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.service.ActivityDataReviewService;
import com.covideinfo.util.DateFormatUtil;

@Service("activityDataReviewService")
public class ActivityDataReviewServiceImpl implements ActivityDataReviewService {
	@Autowired
	ActivityReviewDao activityReviewDao;

	@Override
	public List<ActivityDataReviewDto> getActivityDataReviewVolounteerList(Long studyId, Long activityId, String dateFormat) {
		List<ActivityDataReviewDto> actdRList = new ArrayList<>();
		List<StudyActivityData> stadList = null;
		try {
			stadList = activityReviewDao.getStudyActivityDataRecordsList(activityId);
			if(stadList != null) {
				for(StudyActivityData sa : stadList) {
					if(sa.getSutydActivity().getSm().getId() == studyId) {
						ActivityDataReviewDto acdr =  new ActivityDataReviewDto();
						acdr.setActivityDataId(sa.getId());
						acdr.setCreatedBy(sa.getCreatedBy());
						acdr.setCreatedOn(DateFormatUtil.ConvertDate(sa.getCreatedOn(),dateFormat));
						acdr.setRegistrationNumber(sa.getVolunteerId().getVolunteerId());
						
						if(sa.getVolunteerId() != null && sa.getSubjectId() == null) {
							acdr.setType("Reporting");
						}
						else {
							acdr.setType("StudySubject");
							acdr.setSubjectNumber(sa.getSubjectId().getSubjectNo());
						}
						actdRList.add(acdr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actdRList;
	}
	
	@Override
	public List<ActivityDataReviewDto> getStudyActivityDataForReview(Long studyId, Long activityId, Long roleId, String dateFormat) {
		List<ActivityDataReviewDto> actdRList = new ArrayList<>();
		List<StudyActivityData> stadList = null;
		try {
			stadList = activityReviewDao.getStudyActivityDataForReview(studyId, activityId, roleId);
			if(stadList != null) {
				for(StudyActivityData sa : stadList) {
					ActivityDataReviewDto acdr =  new ActivityDataReviewDto();
					acdr.setActivityDataId(sa.getId());
					acdr.setCreatedBy(sa.getCreatedBy());
					acdr.setCreatedOn(DateFormatUtil.ConvertDate(sa.getCreatedOn(),dateFormat));
					acdr.setRegistrationNumber(sa.getVolunteerId().getVolunteerId());
					
					if(sa.getVolunteerId() != null && sa.getSubjectId() == null) {
						acdr.setType("Reporting");
					}
					else {
						acdr.setType("StudySubject");
						acdr.setSubjectNumber(sa.getSubjectId().getSubjectNo());
					}
					actdRList.add(acdr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actdRList;
	}
	
	@Override
	public List<ActivityDataReviewDataDto> getActivityDataDetails(Long activityDataId, Locale currentLocale, String dateFormat) {
		List<ActivityDataReviewDataDto> adrdtoList = null;
		ActivityDataDetailsDto addDto = null;
		List<StudyCheckInActivityDataDetails> sachinList = null;
		List<StudyCheckOutActivityDataDetails> sachoutList = null;
		List<StudyExecutionActivityDataDetails> stdexeList = null;
		InternationalizaionLanguages inalg = null;
		Map<Long, String> dataMap = new HashMap<>();
		Map<Long, Long> parameterDataMap = new HashMap<>();
		List<Long> parmIds = new ArrayList<>();
		try {
			addDto = activityReviewDao.getActivityDataDetails(activityDataId, currentLocale);
			if(addDto != null) {
				sachinList = addDto.getSachkinList();
				sachoutList = addDto.getSachkoutList();
				stdexeList = addDto.getStdexeList();
				inalg = addDto.getInlg();
				List<LanguageSpecificGlobalParameterDetails> lspgpList = null;
				if(sachinList != null && sachinList.size() > 0) {
					for(StudyCheckInActivityDataDetails scadd : sachinList) {
						parmIds.add(scadd.getSaParameter().getParameterId().getId());
						if(scadd.getGlobalValues() == null) {
							dataMap.put(scadd.getSaParameter().getParameterId().getId(), scadd.getValue());	
						}
						else {
							dataMap.put(scadd.getSaParameter().getParameterId().getId(), String.valueOf(scadd.getGlobalValues().getId()));
						}
						parameterDataMap.put(scadd.getSaParameter().getParameterId().getId(),scadd.getId());
					}
				}
				if(sachoutList != null && sachoutList.size() > 0) {
					for(StudyCheckOutActivityDataDetails stdcod : sachoutList) {
						parmIds.add(stdcod.getSaParameter().getParameterId().getId());
						if(stdcod.getGlobalValues() == null) {
							dataMap.put(stdcod.getSaParameter().getParameterId().getId(), stdcod.getValue());	
						}
						else {
							dataMap.put(stdcod.getSaParameter().getParameterId().getId(), String.valueOf(stdcod.getGlobalValues().getId()));
						}
						parameterDataMap.put(stdcod.getSaParameter().getParameterId().getId(), stdcod.getId());
					}
				}
				if(stdexeList != null && stdexeList.size() > 0) {
					for(StudyExecutionActivityDataDetails stdexe : stdexeList) {
						parmIds.add(stdexe.getSaParameter().getParameterId().getId());
						if(stdexe.getGlobalValues() == null) {
							dataMap.put(stdexe.getSaParameter().getParameterId().getId(), stdexe.getValue());	
						}
						else {
							dataMap.put(stdexe.getSaParameter().getParameterId().getId(), String.valueOf(stdexe.getGlobalValues().getId()));
						}
						parameterDataMap.put(stdexe.getSaParameter().getParameterId().getId(),stdexe.getId());
					}
				}
				if(parmIds.size() > 0)
					lspgpList = activityReviewDao.getLanguageSpecificGlobalparameterDetails(parmIds, inalg);
				if(lspgpList != null) {
					adrdtoList = getActivityDataReviewDataDtoDetails(lspgpList, dataMap, inalg,addDto.getStdActivityDiscrepancyList(), dateFormat,parameterDataMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adrdtoList;
	}

	private List<ActivityDataReviewDataDto> getActivityDataReviewDataDtoDetails(
			List<LanguageSpecificGlobalParameterDetails> lspgpList, Map<Long, String> dataMap, 
			InternationalizaionLanguages inalg, List<StudyActivityDataDetailsDiscrepancy> discrepancyList, 
			String dateFormat,Map<Long, Long> parameterDataMap) {
		List<ActivityDataReviewDataDto> adrdtoList = new ArrayList<>();
		List<CommentsDto> commentsList=new ArrayList<>();
		try {
			for(LanguageSpecificGlobalParameterDetails lsgp : lspgpList) {
				commentsList=new ArrayList<>();
				ActivityDataReviewDataDto adrdto = new ActivityDataReviewDataDto();
				//Group
				FormGroupsDto fgdto = null;
				if(lsgp.getParameterId().getGroups() != null) {
					fgdto = new FormGroupsDto();
					fgdto.setGroupId(lsgp.getParameterId().getGroups().getId());
					fgdto.setGroupName(lsgp.getParameterId().getGroups().getName());
					fgdto.setOrderNo(lsgp.getParameterId().getGroups().getOrderNo());
				}
				adrdto.setGroup(fgdto);
				//Parameter
				ParameterDto pardto = new ParameterDto();
				pardto.setParameterId(lsgp.getParameterId().getId());
				pardto.setParameterName(lsgp.getName());
				pardto.setParamOrder(lsgp.getParameterId().getOrderNo());
				pardto.setValue(dataMap.get(lsgp.getParameterId().getId()));
				pardto.setStudyActivityDataParameterId(parameterDataMap.get(lsgp.getParameterId().getId()));
				adrdto.setParameters(pardto);
				//Control Type
				FromControlDto control = new FromControlDto();
				List<FromValuesDto> valuesDto = new ArrayList<>();
				String controlType = lsgp.getParameterId().getContentType().getCode();
				control.setContentCode(controlType);
				if(controlType.equals("SB") || controlType.equals("RB") || controlType.equals("CB")) {
					List<LanguageSpecificValueDetails> lsvdList = activityReviewDao.getLanguageSpecificValuesDetails(lsgp.getParameterId().getId(), lsgp.getParameterId().getContentType().getId(), inalg);
					for(LanguageSpecificValueDetails lspvd : lsvdList) {
						FromValuesDto fvdto = new FromValuesDto();
						fvdto.setValueId(lspvd.getGlobalValId().getId());
						fvdto.setValueName(lspvd.getName());
						valuesDto.add(fvdto);
					}
				}
				
				for(StudyActivityDataDetailsDiscrepancy discrepancy : discrepancyList) {
					if(discrepancy.getStudyCheckInActivityData() != null && discrepancy.getStudyCheckInActivityData().getSaParameter().getParameterId().getId() == lsgp.getParameterId().getId()) {
						commentsList.add(Comment(discrepancy, ActivityDataTables.CheckIn, dateFormat, discrepancy.getStudyCheckInActivityData().getId()));
					}
					else if(discrepancy.getStudyCheckOutActivityData() != null && discrepancy.getStudyCheckOutActivityData().getSaParameter().getParameterId().getId() == lsgp.getParameterId().getId()) {
						commentsList.add(Comment(discrepancy, ActivityDataTables.CheckOut, dateFormat, discrepancy.getStudyCheckOutActivityData().getId()));
					}
					else if(discrepancy.getStudyExecutionActivityData() != null && discrepancy.getStudyExecutionActivityData().getSaParameter().getParameterId().getId() == lsgp.getParameterId().getId()) {
						commentsList.add(Comment(discrepancy, ActivityDataTables.Execution, dateFormat, discrepancy.getStudyExecutionActivityData().getId()));
					}
				}
				control.setValuesDto(valuesDto);
				adrdto.setControlType(control);
				adrdto.setComments(commentsList);
				adrdtoList.add(adrdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adrdtoList;
	}
	
	private CommentsDto Comment(StudyActivityDataDetailsDiscrepancy discrepancy, ActivityDataTables activityTable, String dateFormat,Long id) {
		CommentsDto comment = new CommentsDto();
		comment.setCommentId(discrepancy.getId());
		comment.setComment(discrepancy.getComments());
		comment.setCommentedBy(discrepancy.getCreatedBy());
		comment.setCommentedOn(DateFormatUtil.ConvertDate(discrepancy.getCreatedOn(), dateFormat));
		if(discrepancy.getResponse()==null) {
			comment.setResponse(discrepancy.getResponse());	
		}
		else {
			comment.setResponse("");
		}
		comment.setRespondedBy(discrepancy.getUpdateBy());
		if(discrepancy.getUpdateOn()!=null) {
			comment.setRespondedOn(DateFormatUtil.ConvertDate(discrepancy.getUpdateOn(), dateFormat));	
		}
		comment.setParameterId(id);
		comment.setActivityTableName(activityTable.toString());
		return comment;
	}


	public RequestStatusDto acceptStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale) {
		return activityReviewDao.acceptStudyActivityData(studyActivityData, userName, locale);
	}
	
	public RequestStatusDto sendCommentsStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale) {
		return activityReviewDao.sendCommentsStudyActivityData(studyActivityData, userName, locale);
	}
}
