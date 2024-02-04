package com.covideinfo.aspect.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.Projects;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.StudyActivityService;

//@BuildStudy(operation = "approved")
@Aspect
@Component
@PropertySource(value = { "classpath:application.properties" })
public class StudyDesingAscpect {
	@Autowired
	private Environment environment;

	@Autowired
	StudyCreationBo studyCreationBo;

	@Autowired
	private HttpSession session;
	
	@Autowired
	StudyActivityService studyActivityService;

	@AfterReturning(pointcut = "execution(* com.covideinfo.security.CustomUserDetailsService.loadUserByUsername(..))", returning = "userObj")
	public void afterLoadUserByUsername(Object userObj) throws Throwable {
		System.out.println("****afterLoadUserByUsername()  start--");
		UserDetails user = (UserDetails) userObj;
		System.out.println("****afterLoadUserByUsername()  end--");
	}

	//@AfterReturning(pointcut = "execution(* com.covideinfo.service.impl.StudyDesignServiceImpl.testSaveStudyInfo(..))", returning = "projectObj")
//	@AfterReturning(pointcut = "execution(* com.covideinfo.service.impl.StudyDesignServiceImpl.saveActivityDraftReviewAudit(..))", returning = "projectObj")
	public void aftersaveActivityDraftReviewAudit(Object projectObj) throws Throwable {
		System.out.println("****aftersaveActivityDraftReviewAudit()  start--");
		Projects project = (Projects) projectObj;
		if (project.getStatus() != null && project.getStatus().getStatusCode().equals(StudyStatus.APPROVED.toString())) {
			try {
				StatusMaster stm = studyCreationBo.statusMaster(StudyDesign.STUDINFORMATION.toString());
				StudyMaster study = studyCreationBo.createStudyMaster(project, studyActivityService);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
