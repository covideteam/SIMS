package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DrugReceptionDao;
import com.covideinfo.model.DocumentryRequirements;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.DrugDispensingContainerBarcodes;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.DrugReceptionContainerBarcodes;
import com.covideinfo.model.DrugReceptionReviewAudit;
import com.covideinfo.model.Projects;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.Regulatories;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.model.UserMaster;

@Repository("/drugReceptionDao")
public class DrugReceptionDaoImpl extends AbstractDao<Long,DrugReception> implements DrugReceptionDao  {

	@Override
	public boolean saveDrugReception(DrugReception drung,List<DrugReceptionContainerBarcodes> barList,DocumentryRequirements document,DrugReceptionReviewAudit drrpojo) {
		boolean flag=false;
		try {
			getSession().save(drung);
			for(DrugReceptionContainerBarcodes add:barList) {
				getSession().save(add);
			}
			getSession().save(document);
			if(drrpojo!=null) {
			drrpojo.setDrugid(drung.getId());	
			getSession().save(drrpojo);
			}
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getEligibleProjectList() {
		List<Projects> list=null;
		list=getSession().createCriteria(Projects.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Regulatories> getRegulatoriesList() {
		List<Regulatories> list=null;
		list=getSession().createCriteria(Regulatories.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitsMaster> getUnitsMasterList() {
		List<UnitsMaster> list=null;
		list=getSession().createCriteria(UnitsMaster.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReception> getDrugReceptionList() {
		List<DrugReception> lista=null;
		lista=getSession().createCriteria(DrugReception.class).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingList() {
		List<StudyDrugDispensing> lista=null;
		lista=getSession().createCriteria(StudyDrugDispensing.class).list();
		return lista;
	}

	@Override
	public boolean saveStudyDrugDispensing(StudyDrugDispensing dispensing) {
		boolean flag=false;
		try {
			getSession().save(dispensing);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public StudyDrugDispensing getStudyDrugDispensingDataWithId(Long id) {
		StudyDrugDispensing pojo=null;
		pojo=(StudyDrugDispensing) getSession().createCriteria(StudyDrugDispensing.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public boolean updateStudyDrugDispensingData(StudyDrugDispensing dispensing,List<DrugDispensingContainerBarcodes> barList) {
		boolean flag=false;
		try {
			getSession().update(dispensing);
			for(DrugDispensingContainerBarcodes add:barList) {
				getSession().save(add);
			}
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingInApprovalList() {
		List<StudyDrugDispensing> lista=null;
		lista=getSession().createCriteria(StudyDrugDispensing.class).
				add(Restrictions.eq("status", "InApproval")).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReception> getDrugReceptionWithProjectIdAndRandomizationCode(Projects projectId,
			String randamizationCode) {
		List<DrugReception> list=null;
		list=(List<DrugReception>) getSession().createCriteria(DrugReception.class)
				.add(Restrictions.eq("projectId", projectId))
				.add(Restrictions.eq("randamizationCode", randamizationCode))
                .addOrder(Order.asc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingApprovalAndNotUpdatedList() {
		List<StudyDrugDispensing> lista=null;
		lista=getSession().createCriteria(StudyDrugDispensing.class)
				.add(Restrictions.eq("status", "Approval"))
				//.add(Restrictions.isNull("updatedBy"))
				.list();
		return lista;
	}

	@Override
	public DrugReception getDrugReceptionWithId(Long id) {
		DrugReception pojo=null;
		pojo=(DrugReception) getSession().createCriteria(DrugReception.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public DocumentryRequirements getDocumentryRequirementsWithDrugId(Long id) {
		DocumentryRequirements pojo=null;
		pojo=(DocumentryRequirements) getSession().createCriteria(DocumentryRequirements.class)
				.add(Restrictions.eq("drugReceptionId.id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public boolean updateDrugReception(DrugReception drung, DocumentryRequirements document,DrugReceptionReviewAudit drrpojo) {
		boolean flag=false;
		try {
			document.setDrugReceptionId(drung);
			getSession().update(drung);
			getSession().update(document);
			if(drrpojo!=null) {
			getSession().save(drrpojo);
			}
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReception> getDrugReceptionOnlyCompletedList() {
		List<DrugReception> lista=null;
		lista=getSession().createCriteria(DrugReception.class)
				.add(Restrictions.eq("status","Completed")).list();
		return lista;
	}

	@Override
	public boolean drugReceptionApprovalSave(DrugReception drugData) {
		boolean flag=false;
		try {
			getSession().update(drugData);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReception> getDrugReceptionListOnlyApproval() {
		List<DrugReception> lista=null;
		lista=getSession().createCriteria(DrugReception.class)
				.add(Restrictions.eq("reviewerStatus","Approval")).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getEligibleProjectListForDrugDispensing(List<Long> ids,StatusMaster approved) {
		List<Projects> lista=null;
		if(ids.size()>0) {
			lista=getSession().createCriteria(Projects.class)
					.add(Restrictions.in("id",ids))	
					.add(Restrictions.eq("status", approved))
					.list();
		}
		
		return lista;
	}

	@Override
	public StatusMaster statusMaster(String string) {
		StatusMaster pojo=null;
		pojo=(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", string)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getDrugReceptionSubmitList() {
        List<Long> list=new ArrayList<>();
		List<DraftReviewStage> dft=getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("name", "DrugReception"))
				.add(Restrictions.eq("action", "SUBMIT")).list();
		for(DraftReviewStage data:dft) {
			list.add(data.getFromRole().getId());
		}
		return list;
	}

	@Override
	public UserMaster findByUsername(String username) {
		UserMaster um=null;
		um=(UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
		return um;
	}

	@Override
	public DraftReviewStage getDrugReceptionStage(RoleMaster role) {
		DraftReviewStage roled=null;
		roled=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole", role))
				.add(Restrictions.eq("name", "DrugReception")).uniqueResult();
		return roled;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugReceptionReviewAudit> getDrugReceptionReviewAuditWithDrugReceptionId(Long id) {
		List<DrugReceptionReviewAudit> list=null;
		list=getSession().createCriteria(DrugReceptionReviewAudit.class)
				.add(Restrictions.eq("drugid", id))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@Override
	public DraftReviewStage getDrugReceptionReviewStageFirsyRow(Long rid) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("action", "SUBMIT"))
				.add(Restrictions.eq("name", "DrugReception"))
				.add(Restrictions.eq("fromRole.id", rid)).uniqueResult();
		return pojo;
	}

	@Override
	public DraftReviewStage getDrugReceptionReviewStageWithreviewState(long reviewState) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("id", reviewState)).uniqueResult();
		return pojo;
	}

	@Override
	public DraftReviewStage getDrugReceptionReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole", toRole))
				.add(Restrictions.eq("action", approvalType))
				.add(Restrictions.eq("name", "DrugReception")).uniqueResult();
				/*.add(Restrictions.eq("id", l)).uniqueResult();*/
		return pojo;
	}
	

}
