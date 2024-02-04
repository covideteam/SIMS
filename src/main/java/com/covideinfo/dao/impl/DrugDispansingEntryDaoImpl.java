package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DrugDispansingEntryDao;
import com.covideinfo.dto.DrugDispansingEntryDetailsDto;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@Repository("drugDispansingEntryDao")
public class DrugDispansingEntryDaoImpl extends AbstractDao<Long, TreatmentInfo> implements DrugDispansingEntryDao {

	@SuppressWarnings("unchecked")
	@Override
	public DrugDispansingEntryDetailsDto getDrugDispansingDetails() {
		DrugDispansingEntryDetailsDto ddedDto = null;
		List<StudyMaster> smList = null;
		try {
			smList = getSession().createCriteria(StudyMaster.class).list();
			
			ddedDto = new DrugDispansingEntryDetailsDto();
			ddedDto.setSmList(smList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddedDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DrugDispansingEntryDetailsDto getDrugDispansingInfoDetails(Long studyId) {
		DrugDispansingEntryDetailsDto ddeDto = null;
		List<DosingInfoDetails> dinfodList = null; 
		List<TreatmentInfo> tinfoList = null;
		long doseCount = 0;
		List<Long> subIds = null;
		try {
			dinfodList = getSession().createCriteria(DosingInfoDetails.class)
					          .add(Restrictions.eq("study.id", studyId)).list();
			
			tinfoList = getSession().createCriteria(TreatmentInfo.class)
					.add(Restrictions.eq("study.id", studyId)).list();
			
			subIds = getSession().createCriteria(StudySubjects.class)
					       .add(Restrictions.eq("study.id", studyId))
					       .setProjection(Projections.property("id")).list();
			if(subIds != null && subIds.size() > 0) {
				doseCount = (long) getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.in("studySubjects.id", subIds))
					       .setProjection(Projections.rowCount()).uniqueResult();
			}
			ddeDto = new DrugDispansingEntryDetailsDto();
			ddeDto.setTinfoList(tinfoList);
			ddeDto.setDifodList(dinfodList);
			ddeDto.setDosecount(doseCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddeDto;
	}

	@Override
	public DrugDispansingEntryDetailsDto getDosingInfoDetailsRecord(Long studyId, Long userId, Long treatment) {
		DrugDispansingEntryDetailsDto ddeDto = null;
		StudyMaster study = null;
		DosingInfoDetails didPojo = null;
		UserMaster user= null;
		TreatmentInfo tinf = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					     .add(Restrictions.eq("id", studyId)).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					 .add(Restrictions.eq("id", userId)).uniqueResult();
			
			tinf = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
					   .add(Restrictions.eq("id", treatment)).uniqueResult();
			if(study != null) {
				didPojo = (DosingInfoDetails) getSession().createCriteria(DosingInfoDetails.class)
						.add(Restrictions.eq("study", study))
						.add(Restrictions.eq("tinfo.id", treatment)).uniqueResult();
			}
			ddeDto = new DrugDispansingEntryDetailsDto();
			ddeDto.setDinfodetails(didPojo);
			ddeDto.setStudy(study);
			ddeDto.setUser(user);
			ddeDto.setTinf(tinf);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddeDto;
	}

	@Override
	public boolean updateDosingInfoDetails(DosingInfoDetails didPojo) {
		boolean flag = false;
		try {
			getSession().update(didPojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public boolean saveDosingInfoDetails(DosingInfoDetails didPojo) {
		boolean flag = false;
		long didNo = 0;
		try {
			didNo = (long) getSession().save(didPojo);
			if(didNo > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

}
