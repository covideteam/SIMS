package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.internal.resources.Project;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.RandomizationDao;
import com.covideinfo.dto.RandomizationViewDto;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;

@Repository("randomizationDao")
public class RandomizationDaoImpl extends AbstractDao<Long, ProjectDetailsRandomization> implements RandomizationDao {

	@Override
	public String saveUploadRandomization(List<ProjectDetailsRandomization> pdrdataFinal,RandomizationReviewAudit rra, Projects poject,RandomizationFileStatus rfs) {
		String flag="error";
		try {
			for(ProjectDetailsRandomization add: pdrdataFinal) {
				getSession().save(add);
			}
			getSession().save(rra);
			getSession().update(poject);
			getSession().save(rfs);
			
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
			flag="error";
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getAllProjectsList() {
		List<Projects> prolist=null;
		prolist=getSession().createCriteria(Projects.class)
				.addOrder(Order.asc("createdOn")).list();
		return prolist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetailsRandomization> getRandomExitOrNot(Long projectNoVal) {
		List<ProjectDetailsRandomization> prolist=null;
		prolist=getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("projectNo", projectNoVal)).list();
		return prolist;
	}

	@Override
	public boolean updateStatusRandomization(List<ProjectDetailsRandomization> randomList, String username) {
		boolean flag=false;
		try {
			for(ProjectDetailsRandomization upd: randomList) {
				upd.setUpdatedBy(username);
				upd.setUpdatedOn(new Date());
				upd.setStatus("Inactive");
				getSession().update(upd);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public ProjectDetailsRandomization getProjectDetailsRandomizationWithSubjectAndPeriod(
			ProjectDetailsRandomization ch) {
		ProjectDetailsRandomization pdr=null;
		pdr=(ProjectDetailsRandomization) getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("projectNo", ch.getProjectNo()))
				.add(Restrictions.eq("subjectNo", ch.getSubjectNo()))
				.add(Restrictions.eq("period", ch.getPeriod())).uniqueResult();
		return pdr;
	}

	@Override
	public String updateUploadRandomization(List<ProjectDetailsRandomization> addData,
			List<ProjectDetailsRandomization> updateData,RandomizationReviewAudit rra,Projects poject,RandomizationFileStatus rfsupdate) {
		String flag="error";
		try {
			for(ProjectDetailsRandomization upd: updateData) {
				getSession().update(upd);
			}
			for(ProjectDetailsRandomization add: addData) {
				getSession().save(add);
			}
			getSession().save(rra);
			getSession().update(poject);
			getSession().update(rfsupdate);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
			flag="error";
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getAllStudyList() {
		List<StudyMaster> studylist=null;
		studylist=getSession().createCriteria(StudyMaster.class).list();
		return studylist;
	}

	@Override
	public StudyMaster getStudyMasterWithId(Long projectNoVal) {
		StudyMaster sm=null;
		sm=(StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("id", projectNoVal)).uniqueResult();
		return sm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreatmentInfo> getTreatmentInfoWithStudyId(StudyMaster sm) {
		List<TreatmentInfo> ti=null;
		ti=getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", sm)).list();
		return ti;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithStudyId(Long id) {
		List<ProjectDetailsRandomization> pd=null;
		pd=getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("projectNo", id))
				 .addOrder(Order.asc("id")).list();
		return pd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DraftReviewStage getRandomizationReviewStage(RoleMaster role) {
		DraftReviewStage roled=null;
		roled=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole", role))
				.add(Restrictions.eq("name", "Randomization")).uniqueResult();
		return roled;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RandomizationReviewAudit> getRandomizationReviewAuditWithStudyId(Long id) {
		List<RandomizationReviewAudit> list=null;
		list=getSession().createCriteria(RandomizationReviewAudit.class)
				.add(Restrictions.eq("studyId", id))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithreviewState(long reviewState) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("id", reviewState)).uniqueResult();
		return pojo;
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithTypeFromRole(RoleMaster fromRole,
			String approvalType,long l) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole", fromRole))
				.add(Restrictions.eq("action", approvalType))
				.add(Restrictions.eq("name", "Randomization")).uniqueResult();
				/*.add(Restrictions.eq("id", l)).uniqueResult();*/
		return pojo;
	}

	@Override
	public boolean saveRandomizationApproval(RandomizationReviewAudit rra, Projects poject, RandomizationFileStatus rfsupdate) {
		boolean flag=false;
		try {
			getSession().save(rra);
			getSession().update(poject);
			getSession().update(rfsupdate);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public StudyPeriodMaster getStudyPeriodMasterWithStudyAndPeriodName(Long studyid, String period) {
		StudyPeriodMaster pojo=null;
		pojo=(StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", studyid))
				.add(Restrictions.eq("periodName", period)).uniqueResult();
		return pojo;
	}

	@Override
	public TreatmentInfo getTreatmentInfoRamdomizationCode(String radamizationCode, Long studyid) {
		TreatmentInfo pojo=null;
		pojo=(TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study.id", studyid))
				.add(Restrictions.eq("randamizationCode", radamizationCode)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveRandomizationApprovalFinal(RandomizationReviewAudit rra,
			List<SubjectRandamization> subjectRamList,List<SubjectRandamization> subjectRamUpdate,Projects poject,long studyid) {
		boolean flag=false;
		try {
			getSession().save(rra);
			getSession().update(poject);
			
			List<StudyTreatmentWiseSubjects> stwsList =  getSession().createCriteria(StudyTreatmentWiseSubjects.class)
					.add(Restrictions.eq("sm.id", studyid)).list();
			for(StudyTreatmentWiseSubjects list:stwsList) {
				list.setSubjects("");
				getSession().update(list);
			}
			for(SubjectRandamization upp:subjectRamUpdate) {
				getSession().update(upp);
				StudyTreatmentWiseSubjects stwsPojo = (StudyTreatmentWiseSubjects) getSession().createCriteria(StudyTreatmentWiseSubjects.class)
						.add(Restrictions.eq("treatment", upp.getTreatmentInfo()))
						.add(Restrictions.eq("period", upp.getPeriod())).uniqueResult();
				if(stwsPojo != null) {
				String subNos = stwsPojo.getSubjects();
				if(subNos=="") {
					stwsPojo.setSubjects(upp.getSubjectNo());
					getSession().merge(stwsPojo);
				}else {
					stwsPojo.setSubjects(subNos+","+upp.getSubjectNo());
					getSession().merge(stwsPojo);
				}
				}
			}
			for(SubjectRandamization add:subjectRamList) { 
				getSession().save(add);
				//StudyTeatmentWiseSubjects
				StudyTreatmentWiseSubjects stwsPojo = (StudyTreatmentWiseSubjects) getSession().createCriteria(StudyTreatmentWiseSubjects.class)
						.add(Restrictions.eq("treatment", add.getTreatmentInfo()))
						.add(Restrictions.eq("period", add.getPeriod())).uniqueResult();
				if(stwsPojo == null) {
					StudyTreatmentWiseSubjects stws = new StudyTreatmentWiseSubjects();
					stws.setCreatedBy(add.getCretedBy());
					stws.setCreatedOn(add.getCreatedOn());
					stws.setPeriod(add.getPeriod());
					stws.setSm(add.getPeriod().getStudy());
					stws.setTreatment(add.getTreatmentInfo());
					stws.setSubjects(add.getSubjectNo());
					getSession().save(stws);
				}else {
					String subNos = stwsPojo.getSubjects();
                    stwsPojo.setSubjects(subNos+","+add.getSubjectNo());
                    getSession().merge(stwsPojo);
				}
			}
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public SubjectRandamization getSubjectRandamizationExitCheck(StudyPeriodMaster pd,
			String subjectNo,TreatmentInfo ti) {
		SubjectRandamization pojo=null;
		pojo=(SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("period", pd))
				.add(Restrictions.eq("subjectNo", subjectNo))
				.add(Restrictions.eq("treatmentInfo", ti)).uniqueResult();
		return pojo;
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageFirsyRow(Long rid) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("action", "SUBMIT"))
				.add(Restrictions.eq("name", "Randomization"))
				.add(Restrictions.eq("fromRole.id", rid)).uniqueResult();
		return pojo;
	}

	@Override
	public Projects getProjectsWithProjectNo(String projectNo) {
		Projects pojo=null;
		pojo=(Projects) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectNo", projectNo)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterForRandomization(List<String>projectNo) {
		List<StudyMaster> pojol=null;
		pojol=getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.in("projectNo", projectNo)).list();
		return pojol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectForRandomization() {
		List<Projects> pojol=null;
		pojol=getSession().createCriteria(Projects.class)
				.add(Restrictions.isNotNull("randamizationRole")).list();
		return pojol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects() {
		List<Projects> returnList=new ArrayList<>();
		List<Projects> pplist=new ArrayList<>();
		pplist=getSession().createCriteria(Projects.class).list();
		
		for(Projects pp:pplist) {
				ProjectsDetails noofsub=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("fieldName", "noOfSubjects"))
						.add(Restrictions.eq("projectsId", pp)).uniqueResult();
				ProjectsDetails noofperiod=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("fieldName", "noOfPeriods"))
						.add(Restrictions.eq("projectsId", pp)).uniqueResult();
				List<ProjectsDetails> tretm=(List<ProjectsDetails>) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("fieldName", "randomizationCode"))
						.add(Restrictions.eq("projectsId", pp)).list();
				if(noofsub!=null &&noofperiod!=null &&tretm.size()>0 ) {
					returnList.add(pp);
				}
				
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithProjectId(Long id) {
		List<ProjectDetailsRandomization> list=null;
		list=getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("projectNo", id))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RandomizationReviewAudit> getRandomizationReviewAuditWithProjectId(Long id) {
		List<RandomizationReviewAudit> list=null;
		list=getSession().createCriteria(RandomizationReviewAudit.class)
				.add(Restrictions.eq("projectId", id))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@Override
	public Projects getProjectsWithProjectId(Long projectNoVal) {
		Projects pojo=null;
		pojo=(Projects) getSession().createCriteria(Projects.class).add(Restrictions.eq("projectId", projectNoVal)).uniqueResult();
		return pojo;
	}

	@Override
	public ProjectsDetails getProjectsDetailsForNoofSubjectWithProject(Projects poject) {
		ProjectsDetails pojo=null;
		pojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId", poject))
				.add(Restrictions.eq("fieldName", "noOfSubjects")).uniqueResult();
		return pojo;
	}

	@Override
	public ProjectsDetails getProjectsDetailsForNoofPeriodWithProject(Projects poject) {
		ProjectsDetails pojo=null;
		pojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId", poject))
				.add(Restrictions.eq("fieldName", "noOfPeriods")).uniqueResult();
		return pojo;
	}

	@Override
	public StudyMaster getStudyMasterWithProjectNo(String projectNo) {
		StudyMaster pojo=null;
		pojo=(StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("projectNo", projectNo)).uniqueResult();
		return pojo;
	}

	@Override
	public RandomizationFileStatus getRandomizationFileStatusWithProjectId(long projectId) {
		RandomizationFileStatus pojo=null;
		pojo=(RandomizationFileStatus) getSession().createCriteria(RandomizationFileStatus.class)
				.add(Restrictions.eq("projectId", projectId)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectsDetails> getProjectsDetailsRandomizationCode(Projects poject) {
		List<ProjectsDetails> tretm=(List<ProjectsDetails>) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("fieldName", "randomizationCode"))
				.add(Restrictions.eq("projectsId", poject)).list();
		return tretm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationForSplit(RandomizationViewDto data) {
		List<ProjectDetailsRandomization> list=null;
		list=getSession().createCriteria(ProjectDetailsRandomization.class)
				.add(Restrictions.eq("subjectNo", data.getSubjectNo()))
			    .add(Restrictions.eq("projectNo", data.getProjectNo())).list();
		return list;
	}

	@Override
	public ProjectsDetails getProjectsDetailsWithNoofPeriods(Long id) {
		ProjectsDetails pojo=null;
		pojo=(ProjectsDetails) getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId.id", id))
				.add(Restrictions.eq("fieldName", "noOfPeriods")).uniqueResult();
		return pojo;
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType) {
		DraftReviewStage pojo=null;
		pojo=(DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("fromRole", toRole))
				.add(Restrictions.eq("action", approvalType))
				.add(Restrictions.eq("name", "Randomization")).uniqueResult();
				/*.add(Restrictions.eq("id", l)).uniqueResult();*/
		return pojo;
	}

	

	
}
