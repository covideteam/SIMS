package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DrugDispensingPdfDao;
import com.covideinfo.dto.DrugDispancingPdfDto;
import com.covideinfo.dto.PeriodMasterDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.dto.SubjectRandomizationDto;
import com.covideinfo.dto.TreatmentsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@Repository("drugDispensingPdfDao")
public class DrugDispensingPdfDaoImpl extends AbstractDao<Long, StudyMaster> implements DrugDispensingPdfDao {

	
	
	@SuppressWarnings("unchecked")
	@Override
	public DrugDispancingPdfDto DrugDispancingPdfDtoDetailsForDownlodPdf() {
		DrugDispancingPdfDto ddpDto = null;
		List<StudyMasterDto> smList = null;
		List<PeriodMasterDto> spmList = null;
		StatusMaster status = null;
		List<Long> studyIds = new ArrayList<>();
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				      .add(Restrictions.eq("statusCode", StudyStatus.APPROVED.toString())).uniqueResult();
			if(status != null) {
				Criteria studyCri = getSession().createCriteria(StudyMaster.class)
						.add(Restrictions.eq("studyStatus.id", status.getId()))
						.setProjection(Projections.projectionList()
								.add(Projections.property("id"),"id")
								.add(Projections.property("projectNo"), "projectNo"))
						.setResultTransformer(Transformers.aliasToBean(StudyMasterDto.class));
				smList = studyCri.list();
				
				if(smList != null && smList.size() > 0) {
					for(StudyMasterDto sm : smList) {
						studyIds.add(sm.getId());
					}
				}
			}
			if(studyIds.size() > 0) {
				Criteria periodCri = getSession().createCriteria(StudyPeriodMaster.class)
						.add(Restrictions.in("study.id", studyIds))
						.setProjection(Projections.projectionList()
								.add(Projections.property("id"),"id")
								.add(Projections.property("periodName"), "periodName")
								.add(Projections.property("study.id"), "studyId")
								.add(Projections.property("periodNo"), "periodNo"))
						
						.setResultTransformer(Transformers.aliasToBean(PeriodMasterDto.class));
				
				spmList = periodCri.list();
			}
			
			ddpDto = new DrugDispancingPdfDto();
			ddpDto.setSmList(smList);
			ddpDto.setSpmList(spmList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddpDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DrugDispancingPdfDto getDrugDispancingPdfDetails(Long studyId, Long userId, Long periodId) {
		DrugDispancingPdfDto ddispDto = null;
		List<DosingInfoDetails> dinfList = null;
		UserMaster user = null;
		PeriodMasterDto period = null;
		List<TreatmentsDto> treatmentsList = null;
		List<SubjectRandomizationDto> subRazList = null;
		try {
			dinfList = getSession().createCriteria(DosingInfoDetails.class)
			        .add(Restrictions.eq("study.id", studyId)).list();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					   .add(Restrictions.eq("id", userId)).uniqueResult();
			
			Criteria periodCri = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("id", periodId))
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"),"id")
							.add(Projections.property("periodName"), "periodName")
							.add(Projections.property("study.id"), "studyId")
							.add(Projections.property("periodNo"), "periodNo"))
					
					.setResultTransformer(Transformers.aliasToBean(PeriodMasterDto.class));
			
			period = (PeriodMasterDto) periodCri.uniqueResult();
			
			Criteria treatmentCri = getSession().createCriteria(TreatmentInfo.class)
					.add(Restrictions.eq("study.id", studyId))
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"),"id")
							.add(Projections.property("treatmentName"), "treatmentName")
							.add(Projections.property("randamizationCode"), "randomizationCode"))
					
					.setResultTransformer(Transformers.aliasToBean(TreatmentsDto.class));
			treatmentsList = treatmentCri.list();
			
			Criteria subRazCri = getSession().createCriteria(SubjectRandamization.class)
					.add(Restrictions.eq("period.id", periodId))
					.createAlias("treatmentInfo", "treatment")
 					.createAlias("period", "sp")
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"),"id")
							.add(Projections.property("radamizationCode"), "randomizationCode")
							.add(Projections.property("subjectNo"), "subjectNo")
							.add(Projections.property("sp.periodName"), "periodName")
							.add(Projections.property("sp.periodNo"), "periodNo")
							.add(Projections.property("sp.id"), "periodId")
							.add(Projections.property("treatment.id"), "treatmentId"))
					.setResultTransformer(Transformers.aliasToBean(SubjectRandomizationDto.class));
			
			subRazList = subRazCri.list();
			
			ddispDto = new DrugDispancingPdfDto();
			ddispDto.setDinfdList(dinfList);
			ddispDto.setUser(user);
			ddispDto.setPeriod(period);
			ddispDto.setTreatmentsList(treatmentsList);
			ddispDto.setSubRazList(subRazList);
		} catch (Exception e) {
			e.printStackTrace();
			return ddispDto;
		}
		return ddispDto;
	}
	

}
