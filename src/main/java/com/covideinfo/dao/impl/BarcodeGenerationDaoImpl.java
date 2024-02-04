package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.BarcodeGenerationDao;
import com.covideinfo.dto.BarcodeDetailsDto;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectRandamization;

@Repository("barcodeGenerationDao")
public class BarcodeGenerationDaoImpl extends AbstractDao<Long, StudyMaster> implements BarcodeGenerationDao {

	@SuppressWarnings("unchecked")
	@Override
	public BarcodeDetailsDto getBarcodesDtoDetails(Long studyId, String barcodeType) {
		BarcodeDetailsDto bcDto = null;
		List<StudyPeriodMaster> spmList = null;
		StudyMaster sm = null;
		List<SampleTimePoints> sampleList = null;
		List<Integer> studyGroupStanbys = null;
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					  .add(Restrictions.eq("id", studyId)).uniqueResult();
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study", sm)).list();
			
			if(barcodeType.equals("VACUTAINER")) {
				sampleList = getSession().createCriteria(SampleTimePoints.class)
						 .add(Restrictions.eq("study", sm)).list();
			}
			studyGroupStanbys = getSession().createCriteria(StudyGroups.class)
					             .add(Restrictions.eq("study.id", studyId))
					             .setProjection(Projections.property("noofStandbys")).list();
			 bcDto = new BarcodeDetailsDto();
			 bcDto.setSpmList(spmList);
			 bcDto.setStudy(sm);
			 bcDto.setStpList(sampleList);
			 bcDto.setStudyGroupStanbys(studyGroupStanbys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bcDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BarcodeDetailsDto getBarcodesDtoDetailsForBarcodesPrint(Long studyId, Long periodId, String type) {
		BarcodeDetailsDto bcDto = null;
		List<StudyPeriodMaster> spmList = null;
		StudyMaster sm = null;
		List<DoseTimePoints> dosetpList = null;
		List<SubjectRandamization> srzList = null;
		List<SampleTimePoints> sampleList = null;
		List<Integer> studyGroupStanbys = null;
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					  .add(Restrictions.eq("id", studyId)).uniqueResult();
			if(type.equals("Subject")) {
				spmList = getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study", sm)).list();
			
			}else if(type.equals("Sachet") || type.equals("Vacutainer")) {
				if(periodId != 0) {
					StudyPeriodMaster spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
			                 .add(Restrictions.eq("id", periodId)).uniqueResult();
					if(spm != null) {
						sm = spm.getStudy();
						spmList = new ArrayList<>();
						spmList.add(spm);
						srzList = getSession().createCriteria(SubjectRandamization.class)
						          .add(Restrictions.eq("period", spm)).list();
					}
				}else {
					spmList = getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study", sm)).list();
					
					List<Long> spmIds = getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study", sm))
							.setProjection(Projections.property("id")).list();
					
					if(spmIds != null && spmIds.size() > 0) {
						srzList = getSession().createCriteria(SubjectRandamization.class)
						          .add(Restrictions.in("period.id", spmIds)).list();
					}
					
				}
				if(type.equals("Sachet")) {
					dosetpList = getSession().createCriteria(DoseTimePoints.class)
					        .add(Restrictions.eq("study", sm)).list();
				}
				if(type.equals("Vacutainer")) {
					sampleList = getSession().createCriteria(SampleTimePoints.class)
							 .add(Restrictions.eq("study", sm)).list();
				}
				
			}
			studyGroupStanbys = getSession().createCriteria(StudyGroups.class)
					                .add(Restrictions.eq("study.id", studyId))
					                .setProjection(Projections.property("noofStandbys")).list();
					
			
			 bcDto = new BarcodeDetailsDto();
			 bcDto.setSpmList(spmList);
			 bcDto.setStudy(sm);
			 bcDto.setDosetpList(dosetpList);
			 bcDto.setSrzList(srzList);
			 bcDto.setStpList(sampleList);
			 bcDto.setStudyGroupStanbys(studyGroupStanbys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bcDto;
	}

}
