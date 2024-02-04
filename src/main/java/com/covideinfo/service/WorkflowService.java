package com.covideinfo.service;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.enums.WorkFlowReviewStatus;
import com.covideinfo.enums.WorkFlowTypes;
import com.covideinfo.model.DraftReviewStage;

@Repository("WorkflowService")
public class WorkflowService extends AbstractDao<Long, DraftReviewStage> {
	/** 
     * This method returns the workflow based on login user role, workflow type and review status.
     * @param workFlowType this is for filtering data based on workflow type ex. Study Design, Randamization etc.
     * @param workFlowReviewStatus this is for filtering data based on workflow type ex. Accept, Sendcomments etc.
     * @param roleId role id of the loin user.
     * @return DraftReviewStage This returns the workflow 
     */  
	public DraftReviewStage GetWorkflow(WorkFlowTypes workFlowType, WorkFlowReviewStatus workFlowReviewStatus, Long roleId) {
		DraftReviewStage draftReviewStage =(DraftReviewStage)getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("name", workFlowType.toString()))
				.add(Restrictions.eq("fromRole.id", roleId))
				.add(Restrictions.eq("action", workFlowReviewStatus.toString())).uniqueResult();
		return draftReviewStage;
	}
	
	/** 
     * This method returns the first workflow where from role is null. 
     * @param workFlowType this is for filtering data based on workflow type ex. Study Design, Randamization etc.
     * @return DraftReviewStage This returns the workflow 
     */  
	@SuppressWarnings("unchecked")
	public DraftReviewStage GetFirstWorkflow(WorkFlowTypes workFlowType) {
		List<DraftReviewStage> draftReviewStages = getSession().createCriteria(DraftReviewStage.class)
				.add(Restrictions.eq("name", workFlowType.toString()))
				.add(Restrictions.isNull("fromRole"))
				.add(Restrictions.eq("action", WorkFlowReviewStatus.SUBMIT.toString())).list();
		
		if(draftReviewStages.size() > 0) {
			return draftReviewStages.get(0);
		}
		else {
			return null;
		}
	}
}
