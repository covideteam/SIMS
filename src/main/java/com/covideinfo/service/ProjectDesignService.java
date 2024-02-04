package com.covideinfo.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.model.ProjectsDetails;

@Repository("projectDesignService")
public class ProjectDesignService extends  AbstractDao<Long, ProjectsDetails> {
	
	/**
	 * Get all project details by type. 
	 * @param projectId
	 * @param type
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectsDetails> getProjectDetailsByType(Long projectId, String type) {
		return	getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectId))
					.add(Restrictions.eq("type", type)).list();
	}
	
	/**
	 * Deactivate all project details with same type. 
	 * @param projectId
	 * @param userName
	 * @param type
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void deactiavateProjectDetailsByType(Long projectId, String type, String userName) {
		List<ProjectsDetails> projectDetails =	getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectId))
					.add(Restrictions.eq("type", type))
					.add(Restrictions.eq("status", true)).list();
		for(ProjectsDetails pd: projectDetails)
		{
			pd.setStatus(false);
			pd.setUpdatedBy(userName);
			pd.setUpdatedOn(new Date());
			getSession().saveOrUpdate(pd);
		}
	}
	
	/**
	 * Deactivate all project details by treatment. 
	 * @param projectId
	 * @param userName
	 * @param treatment
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void deActivateTreatment(long projectId, long treatment, String userName) {
		List<ProjectsDetails> pdlist=null;
		pdlist=(List<ProjectsDetails>)getSession().createCriteria(ProjectsDetails.class)
		        		 .add(Restrictions.eq("projectsId.projectId", projectId))
		        		 .add(Restrictions.eq("treatmentNo", treatment)).list();
		
		for (ProjectsDetails projectsDetails : pdlist) {
			projectsDetails.setStatus(false);
			projectsDetails.setUpdatedBy(userName);
			projectsDetails.setUpdatedOn(new Date());
			getSession().saveOrUpdate(projectsDetails);
		}
		
	}
}
