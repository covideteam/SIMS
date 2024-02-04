package com.covideinfo.dao.impl;

import java.util.List;

import javax.servlet.RequestDispatcher;

import org.eclipse.core.internal.resources.Project;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.PharmacyDao;
import com.covideinfo.model.DispensedIPHandover;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.IPDiscard;
import com.covideinfo.model.IPRetention;
import com.covideinfo.model.LineClearanceActivity;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusOfUnusedDispensed;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyPeriodMaster;

@Repository("pharmacyDao")
public class PharmacyDaoImpl extends AbstractDao<Long, LineClearanceActivity> implements PharmacyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<LineClearanceActivity> getLineClearanceActivityList() {
		List<LineClearanceActivity> list=null;
		list=getSession().createCriteria(LineClearanceActivity.class).list();
		return list;
	}

	@Override
	public boolean saveLineClearanceActivity(LineClearanceActivity lineform) {
		boolean flag=false;
		try {
			getSession().save(lineform);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DispensedIPHandover> getDispensedIPHandoverList() {
		List<DispensedIPHandover> list=null;
		list=getSession().createCriteria(DispensedIPHandover.class).list();
		return list;
	}

	@Override
	public boolean saveDispensedIPHandover(DispensedIPHandover ipHandform) {
		boolean flag=false;
		try {
			getSession().save(ipHandform);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IPRetention> getipRetentionList() {
		List<IPRetention> list=null;
		list=getSession().createCriteria(IPRetention.class).list();
		return list;
	}

	@Override
	public boolean saveIPRetention(IPRetention ippojo) {
		boolean flag=false;
		try {
			getSession().save(ippojo);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatusOfUnusedDispensed> getStatusOfUnusedDispensedList() {
		List<StatusOfUnusedDispensed> list=null;
		list=getSession().createCriteria(StatusOfUnusedDispensed.class).list();
		return list;
	}

	@Override
	public boolean saveStatusOfUnusedDispensed(StatusOfUnusedDispensed soupojo) {
		boolean flag=false;
		try {
			getSession().save(soupojo);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IPDiscard> getIPDiscardList() {
		List<IPDiscard> list=null;
		list=getSession().createCriteria(IPDiscard.class).list();
		return list;
	}

	@Override
	public boolean saveIPDiscard(IPDiscard ippojo) {
		boolean flag=false;
		try {
			getSession().save(ippojo);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public ProjectsDetails getSponsorCodeWithProjectId(Long proid) {
		Projects ppojo=(Projects) getSession().createCriteria(Projects.class).add(Restrictions.eq("projectId", proid)).uniqueResult();
		
		ProjectsDetails pojo=null;
		if(ppojo!=null) {
		pojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId", ppojo))
				.add(Restrictions.eq("fieldName","sponsorCode"))
				.uniqueResult();
		}
		return pojo;
	}

	@Override
	public StudyDrugDispensing getStudyDrugDispensingWithId(Long id) {
		StudyDrugDispensing pojo=null;
		pojo=(StudyDrugDispensing) getSession().createCriteria(StudyDrugDispensing.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("status", "InApproval")).uniqueResult();
		return pojo;
	}

	@Override
	public boolean updateStudyDrugDispensingApproval(StudyDrugDispensing sdd) {
		boolean flag=false;
		try {
			getSession().update(sdd);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> getPeriodDataBasedonProjectId(long id) {
		List<StudyPeriodMaster> list=null;
		list=getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", id)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReception> getDrugReceptionBasedonProjectId(long id) {
		List<DrugReception> list=null;
		list=getSession().createCriteria(DrugReception.class)
				.add(Restrictions.eq("projectId.id", id)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectsDetails> getRandamizationCodeWithProjectId(long id) {
		List<ProjectsDetails> list=null;
		list=getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId.id", id))
				.add(Restrictions.eq("fieldName", "randomizationCode")).list();
		return list;
	}

}
