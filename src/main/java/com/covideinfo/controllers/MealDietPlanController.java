package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.MealDietPlanService;

@Controller
@RequestMapping("/mealDiet")
public class MealDietPlanController {
	
	@Autowired
	MealDietPlanService mealDietPlanService;
	@Autowired  
	MessageSource messageSource;
	
	/** 
     * This method Meal Diet Plan List
     * @return Meal Diet Plan List
     */
	@RequestMapping(value="/mealDietPlanList",method=RequestMethod.GET)
	private String mealDietPlanList(ModelMap model,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		
		List<MealDietPlan> list=mealDietPlanService.getMealDietPlanList();
		model.addAttribute("list", list);
		
		return "mealDietList";
	}
	/** 
     * This method Meal Diet Form
     * @return Meal Diet Form
     */
	@RequestMapping(value="/getMealDietForm",method=RequestMethod.GET)
	private String getMealDietForm(ModelMap model,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		MealDietPlan mdplan=new MealDietPlan();
		List<FromStaticData> fsdata=mealDietPlanService.getFromStaticDataForMealsTypeOnly();
		List<UnitsMaster> units=mealDietPlanService.getUnitsMasterData();
		model.addAttribute("mdplan", mdplan);
		model.addAttribute("fsdata", fsdata);
		model.addAttribute("units", units);
		return "pages/mealDietPlan/mealDietPlanForm";
	}
	/** 
     * This method Save Meal Diet Plan
     * mdplan--> MealDietPlan Pojo
     * iteamValuesId --> Iteams
     * quntityValuesId --> Quntity
     * totalcalValuesId --> Total Calories
     * unitsValuesId --> Units
     * @return Meal Diet List
     */
	@RequestMapping(value = "/saveMealDietPlan", method = RequestMethod.POST)
	public String saveMealDietPlan(@Valid MealDietPlan mdplan, ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam("iteamValuesId") String[] iteamValuesId,@RequestParam("quntityValuesId") String[] quntityValuesId,
			@RequestParam("totalcalValuesId") String[] totalcalValuesId,@RequestParam("unitsValuesId") String[] unitsValuesId) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String user = request.getSession().getAttribute("userName").toString();
		boolean flag=false;
		if(mdplan!=null) {
		
		 flag=mealDietPlanService.saveMealDietPlan(mdplan,iteamValuesId,quntityValuesId,totalcalValuesId,user,unitsValuesId);
		}
		if(flag) {
			String gvsuccessMsg =messageSource.getMessage("label.mealDiet.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
		}else {
			String gvFailMsg =messageSource.getMessage("label.mealDiet.failed", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

		}
		return "redirect:/mealDiet/mealDietPlanList";
	}
	/** 
     * This method Meal Diet View
     * mdplan--> MealDietPlan Pojo
     * id --> MealDietPlan Id
     * @return Meal Diet Plan View
     */
	@RequestMapping(value="/mealDietView/{id}",method=RequestMethod.GET)
	private String mealDietView(ModelMap model,HttpServletRequest request,@PathVariable ("id") long id,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		MealDietPlan mdplan=null;
		List<MealDietPlanItem> mdplanList=new ArrayList<>();
		mdplan=mealDietPlanService.getMealDietPlanWithId(id);
		mdplanList=mealDietPlanService.getMealDietPlanItemWithMapId(mdplan);
		model.addAttribute("mdplan", mdplan);
		model.addAttribute("mdplanList", mdplanList);
		return "pages/mealDietPlan/mealDietPlanView";
	}
	/** 
     * This method Check Meal Title Exit Or Not 
     * value--> Meal Title
     * @return Yes/No
     */
	@RequestMapping(value="/checkmealTitleExit/{value}",method=RequestMethod.GET)
	public @ResponseBody String  checkmealTitleExit(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("value") String mealTitle) {
		String validate="No";
		  if(mealTitle!="" && mealTitle!=null)  {
			  MealDietPlan stugs=mealDietPlanService.getMealDietPlanWithMealTitle(mealTitle);
			  try {
				  if(stugs==null) {
					  validate="Yes";
				  }
			} catch (Exception e) {
				return validate;
			}
		  }
		return validate;
	}
	
	
	
	

}
