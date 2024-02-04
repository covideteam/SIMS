package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.PeriodChekoutDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;

@Repository("periodChekoutDao")
public class PeriodChekoutDaoimpl extends AbstractDao<Long, StudyMaster> implements PeriodChekoutDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterList() {
		return getSession().createCriteria(StudyMaster.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStudySubjectsRecordsList(StudyMaster sm) {
		String result = "";
		List<StudySubjects> subList = null;
		List<StudyGroupPeriodMaster> sgpmList = null;
		StatusMaster status = null;
		List<StudyActivityData> sadList = null;
		StatusMaster compStatus = null;
		GlobalActivity ga = null;
		StudyActivities sa = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
			
			ga = (GlobalActivity)getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("activityCode", "BAGGAGERETURN")).uniqueResult();
			
			sa = (StudyActivities) getSession().createCriteria(StudyActivities.class)
					.add(Restrictions.eq("activityId", ga))
					.add(Restrictions.eq("sm", sm)).uniqueResult();
			
			sgpmList = getSession().createCriteria(StudyGroupPeriodMaster.class)
					.add(Restrictions.eq("studyId", sm.getId()))
					.add(Restrictions.eq("periodStatus", status)).list();
			
			List<StudyGroupPeriodMaster> updateSgpmList = new ArrayList<>();
			if(sgpmList != null && sgpmList.size() > 0) {
				for(StudyGroupPeriodMaster sgpm : sgpmList) {
					subList = getSession().createCriteria(StudySubjects.class)
							.add(Restrictions.ne("subjectStatus", "DropOut"))
							.add(Restrictions.eq("groupId", sgpm.getStudyGroup())).list();
					if(subList != null && subList.size() > 0) {
						List<Long> subIds = new ArrayList<>();
						for(StudySubjects ss : subList) {
							subIds.add(ss.getId());
						}
						if(subIds.size() > 0) {
							sadList = getSession().createCriteria(StudyActivityData.class)
									.add(Restrictions.eq("sutydActivity", sa))
									.add(Restrictions.eq("stdGoupPeriod", sgpm))
									.add(Restrictions.in("subjectId.id", subIds)).list();
							if(sadList != null && sadList.size() >0) {
								if(subIds.size() == sadList.size()) {
									compStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
											.add(Restrictions.eq("statusCode", "COMPLETED")).uniqueResult();
									sgpm.setPeriodStatus(compStatus);
									updateSgpmList.add(sgpm);
								}
							}
						}
					}
				}
				
			}
			if(updateSgpmList.size() > 0) {
				for(StudyGroupPeriodMaster sgp : updateSgpmList) {
					sgp.setUpdatedBy("System");
					sgp.setUpdatedOn(new Date());
					getSession().update(sgp);
					result ="Done";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String processToPeriodColose(StudyMaster sm) {
		String result = "";
		List<StudyGroupPeriodMaster> sgpmList = null;
		StatusMaster actStatus = null;
		SubjectDoseTimePoints sdtp = null;
		try {
			actStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
			
			sgpmList = getSession().createCriteria(StudyGroupPeriodMaster.class)
					.add(Restrictions.eq("studyId", sm.getId())).list();
			if(sgpmList.size() > 0) {
				List<StudyGroupPeriodMaster> updateSgpmList = new ArrayList<>();
				for(StudyGroupPeriodMaster sgpm : sgpmList) {
					if(sgpm.getPeriodStatus() != null && sgpm.getPeriodStatus().getId() == actStatus.getId()) {
						List<SubjectDoseTimePoints> sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
								.add(Restrictions.eq("study", sm))
								.add(Restrictions.eq("period", sgpm.getPeriod())).list();
						if(sdtpList.size() > 0) {
							sdtp = sdtpList.get(0);
							if(sdtp != null) {
//								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								if(sdtp.getActualTime() != null) {
									Date doseDate = sdtp.getActualTime();
									Calendar c = Calendar.getInstance();
									c.setTime(doseDate);
									c.add(Calendar.DAY_OF_YEAR, sm.getWashoutPeriod()); 
									Date nextDate = c.getTime(); // gets modified time
									Date currentDate = new Date();
									if(nextDate.compareTo(currentDate) > 0) {
										StatusMaster compStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
												.add(Restrictions.eq("statusCode", "COMPLETED")).uniqueResult();
										sgpm.setPeriodStatus(compStatus);
										updateSgpmList.add(sgpm);
									}
								}
								
							}
						}
					}
				}
				if(updateSgpmList.size() > 0) {
					for(StudyGroupPeriodMaster sgp : updateSgpmList) {
						sgp.setUpdatedBy("System");
						sgp.setUpdatedOn(new Date());
						getSession().update(sgp);
						result ="Done";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
