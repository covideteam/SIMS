package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyMealTimePointDiet;
import com.covideinfo.model.StudyPeriodMaster;


/** 
 * This Class Fetching the information from database and stores those information into StudyMealsDietConfiguraionDetailsDto. 
 * @param smList This will having all active studies Information
 * @param spmList This will having all active study periods information 
 * @return mealstpList This will having active study Meals information
 */ 
@SuppressWarnings("serial")
public class StudyMealsDietConfiguraionDetailsDto implements Serializable {

	private List<StudyMaster> smList;
	private List<StudyPeriodMaster> spmList;
	private List<MealsTimePoints> mealstpList;
	private List<MealDietPlan> mdplanList;
	private List<StudyMealTimePointDiet> smtpDietList;
	private Long treatmentId;
	
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public List<MealsTimePoints> getMealstpList() {
		return mealstpList;
	}
	public void setMealstpList(List<MealsTimePoints> mealstpList) {
		this.mealstpList = mealstpList;
	}
	public List<MealDietPlan> getMdplanList() {
		return mdplanList;
	}
	public void setMdplanList(List<MealDietPlan> mdplanList) {
		this.mdplanList = mdplanList;
	}
	public List<StudyMealTimePointDiet> getSmtpDietList() {
		return smtpDietList;
	}
	public void setSmtpDietList(List<StudyMealTimePointDiet> smtpDietList) {
		this.smtpDietList = smtpDietList;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	
	
	
}
