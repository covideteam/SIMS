package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dto.PeriodMasterDto;
import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.RecannulationDto;
import com.covideinfo.dto.SampleTpDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.dto.SubjectSamplesDto;
import com.covideinfo.dto.SubjectsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjectReCannulationData;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@Repository("recannulaDao")
public class RecannulaDao extends AbstractDao<Long, StudySubjectReCannulationData> implements com.covideinfo.dao.RecannulaDao {

	@SuppressWarnings("unchecked")
	@Override
	public RecannulationDto getRecannulationDtoDetails(Long studyId, String subjNo) {
		RecannulationDto rcDto = new RecannulationDto();
		 StudyMasterDto sm = null;
		 PeriodMasterDto spm = null;
		 List<SampleTpDto> samplesList = null;
		 SubjectsDto subject = null;
		 List<SubjectSamplesDto> subjectSamplesList = null;
		 TreatmentInfo treatment = null;
		 DeviationMessage devMsg = null;
		 StatusMaster activeStatus = null;
		 List<RecannulationDataDto> recdDtoList = null;
		try {
			activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					           .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			 sm = (StudyMasterDto) getSession().createCriteria(StudyMaster.class)
					    .add(Restrictions.eq("id", studyId))
					    .setProjection(Projections.projectionList()
					    		.add(Projections.property("id"), "id")
					    		.add(Projections.property("projectNo"), "projectNo"))
						.setResultTransformer(Transformers.aliasToBean(StudyMasterDto.class)).uniqueResult();
		      
		      subject = (SubjectsDto) getSession().createCriteria(StudySubjects.class)
		    		         .add(Restrictions.eq("study.id", studyId))
		    		         .add(Restrictions.eq("subjectNo", subjNo))
		    		         .setProjection(Projections.projectionList()
		    		        	.add(Projections.property("id"), "subjectId")
		    		        	.add(Projections.property("subjectNo"), "subjectNo"))
		    		         .setResultTransformer(Transformers.aliasToBean(SubjectsDto.class)).uniqueResult();
		      
		      if(subject != null) {
		    	 Criteria spmCriteria = getSession().createCriteria(StudySubjectPeriods.class)
		    			      .add(Restrictions.eq("subject.id", subject.getSubjectId()))
		    			      .add(Restrictions.eq("status.id", activeStatus.getId()))
		    			      .createAlias("periodId", "period")
		    			      .createAlias("periodId.study", "pstudy");
		    			      
		    	         spm =  (PeriodMasterDto) spmCriteria.setProjection(Projections.projectionList()
		    			    	.add(Projections.property("period.id"), "id")
		    			    	.add(Projections.property("period.periodName"), "periodName")
		    			    	.add(Projections.property("period.periodNo"), "periodNo")
		    			    	.add(Projections.property("pstudy.id"), "studyId"))
		    	               .setResultTransformer(Transformers.aliasToBean(PeriodMasterDto.class)).uniqueResult();
		    	     devMsg = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
		    	    		            .add(Restrictions.eq("code", "CB")).uniqueResult();
		    	     
		    	  Criteria sstpdCriteria = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
		    			                   .add(Restrictions.eq("subject.id", subject.getSubjectId()))
		    			                   .add(Restrictions.eq("studyPeriodMaster.id", spm.getId()))
		    			                   .add(Restrictions.eq("sampleDeviationId.id", devMsg.getId()))
		    			                   .add(Restrictions.eq("modeOfCollection", "TFP"))
		    			                   .createAlias("sampleTimePoint", "stp")
		    			                   .createAlias("subject", "sub")
		    			                   .createAlias("studyPeriodMaster", "sp")
		    			                   .setProjection(Projections.projectionList()
		    			                		  .add(Projections.property("id"), "id")
		    			                		  .add(Projections.property("stp.id"), "sampleTpId")
		    			                		  .add(Projections.property("stp.sign"), "sign")
		    			                		  .add(Projections.property("stp.timePoint"), "timePoint")
		    			                		  .add(Projections.property("sub.id"), "subjectId")
		    			                		  .add(Projections.property("sub.subjectNo"), "subjectNo")
		    			                		  .add(Projections.property("sp.id"), "periodId")
		    			                		  .add(Projections.property("sp.periodName"), "periodName"))
		    			                      .setResultTransformer(Transformers.aliasToBean(SubjectSamplesDto.class));
		    	  subjectSamplesList = sstpdCriteria.list();
		    	  if(subjectSamplesList != null && subjectSamplesList.size() > 0) {
		    		  rcDto.setSubjectSamplesList(subjectSamplesList);
			      }
//		    	  else {
		    		  treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
   	        		       .add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
   	        		       .add(Restrictions.eq("period.id", spm.getId()))
   	        		       .setProjection(Projections.property("treatmentInfo")).uniqueResult();
		    		  if(treatment == null) {
		    			  treatment = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
		    					           .add(Restrictions.eq("study.id", studyId))
		    					           .add(Restrictions.eq("treatmentNo", "1")).uniqueResult();
		    		  }
		    		  
		    		  Criteria samleCri = getSession().createCriteria(SampleTimePoints.class)
		    				           .add(Restrictions.eq("study.id", studyId))
		    				           .add(Restrictions.eq("treatmentInfo.id", treatment.getId()))
		    				           .createAlias("treatmentInfo", "tinf")
		    				           .setProjection(Projections.projectionList()
		    				        		  .add(Projections.property("id"), "tpId")
		    				        		  .add(Projections.property("sign"), "sing")
		    				        		  .add(Projections.property("timePoint"), "timePoint")
		    				        		  .add(Projections.property("tinf.id"), "treatmentId")
		    				        		  .add(Projections.property("tinf.treatmentName"), "treatmentName"))
		    				                  .setResultTransformer(Transformers.aliasToBean(SampleTpDto.class));
		    		  samplesList = samleCri.list();
		    		  
		    		  rcDto.setSamplesList(samplesList);
//		    	  }
		    	  recdDtoList = getSession().createCriteria(StudySubjectReCannulationData.class)
				          .add(Restrictions.eq("studyId.id", studyId))
				          .add(Restrictions.eq("reCannulation", true))
				          .createAlias("sampleTimePoint", "tp", JoinType.LEFT_OUTER_JOIN)
				          .createAlias("studyId", "std")
				          .createAlias("subject", "sub")
				          .createAlias("period", "spm")
				          .setProjection(Projections.projectionList()
				        		 .add(Projections.property("std.id"), "studyId")
				        		 .add(Projections.property("spm.id"), "periodId")
				        		 .add(Projections.property("sub.id"), "subjectId")
				        		 .add(Projections.property("tp.id"), "sampleId")
				        		 .add(Projections.property("reason"), "reason")
				        		 .add(Projections.property("createdBy"), "doneBy")
				        		 .add(Projections.property("createdOn"), "recannulationDate")
				        		 .add(Projections.property("reCannulation"), "recannula")
				        		 .add(Projections.property("cannuleRemoved"), "cannulaRemoved"))
				          .setResultTransformer(Transformers.aliasToBean(RecannulationDataDto.class)).list();
		    	  rcDto.setSpm(spm);
		    	  rcDto.setSubject(subject);
		    	  rcDto.setSm(sm);
		    	  rcDto.setRecdDtoList(recdDtoList);
		      }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rcDto;
	}

	@Override
	public String saveRecannulationData(RecannulationDataDto rcDto, Long userId) {
		String result ="Failed";
		StudySubjectReCannulationData ssrdata = null;
		StudySubjectReCannulationData ssrdataPojo = null;
		UserMaster user = null;
		StudyPeriodMaster spm = null;
		SampleTimePoints stp = null;
		StudyMaster sm = null;
		StudySubjects subject = null;
		long ssrdNo = 0;
		try {
		  	ssrdataPojo = (StudySubjectReCannulationData) getSession().createCriteria(StudySubjectReCannulationData.class)
					          .add(Restrictions.eq("studyId.id", rcDto.getStudyId()))
					          .add(Restrictions.eq("sampleTimePoint.id", rcDto.getSampleId()))
					          .add(Restrictions.eq("subject.id", rcDto.getSubjectId()))
					          .add(Restrictions.eq("period.id", rcDto.getPeriodId())).uniqueResult();
			if(ssrdataPojo == null) { 
				user = (UserMaster) getSession().createCriteria(UserMaster.class)
					     .add(Restrictions.eq("id", userId)).uniqueResult();
			
				if(rcDto.getPeriodId() != null)
					spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
					              .add(Restrictions.eq("id", rcDto.getPeriodId())).uniqueResult();
				if(rcDto.getSampleId() != null)
					stp = (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
					           .add(Restrictions.eq("id", rcDto.getSampleId())).uniqueResult();
				
				if(rcDto.getStudyId() != null)
					sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					              .add(Restrictions.eq("id", rcDto.getStudyId())).uniqueResult();
				
				if(rcDto.getSubjectId() != null)
					subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					             .add(Restrictions.eq("id", rcDto.getSubjectId())).uniqueResult();
				ssrdata = new StudySubjectReCannulationData();
				ssrdata.setCannuleRemoved(rcDto.isCannulaRemoved());
				ssrdata.setReCannulation(rcDto.isRecannula());
				if(user != null)
					ssrdata.setCreatedBy(user.getFullName());
				ssrdata.setCreatedOn(new Date());
				ssrdata.setPeriod(spm);
				ssrdata.setReason(rcDto.getReason());
				ssrdata.setSampleTimePoint(stp);
				ssrdata.setStudyId(sm);
				ssrdata.setSubject(subject);
				ssrdNo = (long) getSession().save(ssrdata);
				if(ssrdNo > 0)
					result ="success";
			}else result ="exists";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	
}
