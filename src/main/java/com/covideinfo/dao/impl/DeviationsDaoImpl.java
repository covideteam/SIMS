package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DeviationsDao;
import com.covideinfo.dto.DeviationsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjectDeviationReviewDetails;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.UserMaster;

@Repository("deviationsDao")
public class DeviationsDaoImpl extends AbstractDao<Long, StudyMaster> implements DeviationsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMastersList() {
		return getSession().createCriteria(StudyMaster.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviationsDto getgetDeviationRecords(Long projectId, Long roleId, String type) {
		List<StudySubjectDeviations> ssdList = null;
		List<Long> drsIds = null;
		StatusMaster status = null;
		StudyMaster study = null;
		DeviationsDto devDto = null;
		List<StudySubjectDeviationReviewDetails> ssdrdList = null;
		List<Long> ssdIds = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					    .add(Restrictions.eq("id", projectId)).uniqueResult();
			if(type.equals("Submit")) {
				status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						    .add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
				drsIds = getSession().createCriteria(DraftReviewStage.class)
						  .add(Restrictions.eq("name", "DiviationsReview"))
						  .add(Restrictions.eq("action", "SUBMIT"))
						  .add(Restrictions.isNull("fromRole"))
						  .setProjection(Projections.property("toRole.id")).list();
				if(drsIds != null && drsIds.size() > 0) {
					if(drsIds.contains(roleId)) {
						ssdList = getSession().createCriteria(StudySubjectDeviations.class)
								  .add(Restrictions.eq("status", status))
						          .add(Restrictions.eq("studyId", projectId)).list();
						ssdIds = getSession().createCriteria(StudySubjectDeviations.class)
								.add(Restrictions.eq("status", status))
						          .add(Restrictions.eq("studyId", projectId))
						          .setProjection(Projections.property("id")).list();
						if(ssdIds != null && ssdIds.size() > 0) {
							ssdrdList = getSession().createCriteria(StudySubjectDeviationReviewDetails.class)
									        .add(Restrictions.in("ssdevation.id", ssdIds)).list();
						}
					}
				}
			}else if(type.equals("Review")){
				status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					    .add(Restrictions.eq("statusCode", StudyStatus.INREVIEW.toString())).uniqueResult();
				drsIds = getSession().createCriteria(DraftReviewStage.class)
						  .add(Restrictions.eq("name", "DiviationsReview"))
						  .setProjection(Projections.property("fromRole.id")).list();
				if(drsIds != null && drsIds.size() > 0) {
					if(drsIds.contains(roleId)) {
						ssdList = getSession().createCriteria(StudySubjectDeviations.class)
								  .add(Restrictions.eq("status", status))
						          .add(Restrictions.eq("studyId", projectId)).list();
						ssdIds = getSession().createCriteria(StudySubjectDeviations.class)
								.add(Restrictions.eq("status", status))
						          .add(Restrictions.eq("studyId", projectId))
						          .setProjection(Projections.property("id")).list();
						if(ssdIds != null && ssdIds.size() > 0) {
							ssdrdList = getSession().createCriteria(StudySubjectDeviationReviewDetails.class)
									        .add(Restrictions.in("ssdevation.id", ssdIds)).list();
						}
					}
				}
			}
			devDto = new DeviationsDto();
			devDto.setStudy(study);
			devDto.setSubDevList(ssdList);
			devDto.setSsdrdList(ssdrdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devDto;
		
	}

	@Override
	public String saveDeviationRecords(Long deviationId, Long roleId, String type, String comments, Long userId, String submitType) {
		 	StudySubjectDeviationReviewDetails ssdrd = null;
			StudySubjectDeviations ssd = null;
			DraftReviewStage drs = null;
			String result ="Failed";
			UserMaster user = null;
			try {
				ssd = (StudySubjectDeviations) getSession().createCriteria(StudySubjectDeviations.class)
						.add(Restrictions.eq("id", deviationId)).uniqueResult();
				
				user = (UserMaster)getSession().createCriteria(UserMaster.class)
						.add(Restrictions.eq("id", userId)).uniqueResult();
				
				if(type.equals("Review") && submitType.equals("Approve")) {
					drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
								  .add(Restrictions.eq("name", "DiviationsReview"))
								  .add(Restrictions.eq("fromRole.id", roleId))
								  .add(Restrictions.eq("action", "APPROVAL")).uniqueResult();
					if(drs != null) 
						result = saveStudySubjectDeviationReviewDetailsRecord(ssdrd, user, comments,ssd, result, drs, "Approve");
					else result = "NeedWorkFlow";
				}else if(type.equals("Review") && submitType.equals("SentComments")){
					drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
							  .add(Restrictions.eq("name", "DiviationsReview"))
							  .add(Restrictions.eq("fromRole.id", roleId))
							  .add(Restrictions.eq("action", "SENDCOMMENTS")).uniqueResult();
					if(drs != null) 
						result = updateStudySubjectDeviationReviewDetailsRecord(ssdrd, user, comments,ssd, result, drs);
					else result = "NeedWorkFlow";
				}else if(type.equals("Submit") && submitType.equals("Approve")){
					drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
							  .add(Restrictions.eq("name", "DiviationsReview"))
							  .add(Restrictions.eq("action", "SUBMIT"))
							  .add(Restrictions.isNull("fromRole"))
							  .add(Restrictions.eq("toRole.id", roleId)).uniqueResult();
					if(drs != null) 
						result = saveStudySubjectDeviationReviewDetailsRecord(ssdrd, user, comments,ssd, result, drs, "Review");
					else result = "NeedWorkFlow";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
			return result;
	}

	private String updateStudySubjectDeviationReviewDetailsRecord(StudySubjectDeviationReviewDetails ssdrd,
			UserMaster user, String comments, StudySubjectDeviations ssd, String result, DraftReviewStage drs) {
		StatusMaster status = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					   .add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
			
			ssdrd = new StudySubjectDeviationReviewDetails();
			ssdrd.setCommentedBy(user);
			ssdrd.setCommentedOn(new Date());
			ssdrd.setComments(comments);
			ssdrd.setSsdevation(ssd);
			long ssdrdNo = (long) getSession().save(ssdrd);
			if(ssdrdNo > 0) {
				ssd.setUpdatedBy(user.getUsername());
				ssd.setUpdatedOn(new Date());
				ssd.setStatus(status);
				getSession().merge(ssd);
				result ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String saveStudySubjectDeviationReviewDetailsRecord(StudySubjectDeviationReviewDetails ssdrd,
			UserMaster user, String comments, StudySubjectDeviations ssd, String result, DraftReviewStage drs, String type) {
		StatusMaster status = null;
		try {
			ssdrd = new StudySubjectDeviationReviewDetails();
			ssdrd.setCommentedBy(user);
			ssdrd.setCommentedOn(new Date());
			ssdrd.setComments(comments);
			ssdrd.setSsdevation(ssd);
			long ssdrdNo = (long) getSession().save(ssdrd);
			if(ssdrdNo > 0) {
				ssd.setUpdatedBy(user.getUsername());
				ssd.setUpdatedOn(new Date());
				if(type.equals("Approve")) {
					if(drs.getFromRole() != null && drs.getToRole() == null) {
						status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								   .add(Restrictions.eq("statusCode", StudyStatus.APPROVED.toString())).uniqueResult();
						ssd.setStatus(status);
					}else if(drs.getFromRole() != null && drs.getToRole() != null) {
						status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								   .add(Restrictions.eq("statusCode", StudyStatus.INREVIEW.toString())).uniqueResult();
						ssd.setStatus(status);
					}
				}else {
//					if(drs.getFromRole() == null && drs.getToRole() != null) {
						status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								   .add(Restrictions.eq("statusCode", StudyStatus.INREVIEW.toString())).uniqueResult();
						ssd.setStatus(status);
//					}
				}
				
				getSession().merge(ssd);
				result ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
