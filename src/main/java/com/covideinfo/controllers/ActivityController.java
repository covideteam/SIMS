package com.covideinfo.controllers;
/*package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DynamicActivity;
import com.covideinfo.model.DynamicActivityDetails;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.service.ActivityService;
@Controller
@RequestMapping("/activity")
public class ActivityController {

	
	@Autowired
	ActivityService activityService;
	
	@RequestMapping(value="/getActivityList", method=RequestMethod.GET)
	public String getActivityList(ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		List<DynamicActivity> alist=activityService.getActivityList();
		model.addAttribute("alist", alist);
		return "activityList";
		
	}
	
	@RequestMapping(value="/getActivityForm", method=RequestMethod.GET)
	public String getActivityForm(ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		
		List<InternationalizaionLanguages> inlagList = activityService.getInternationalizaionLanguagesRecrdsList();
		model.addAttribute("inlagList", inlagList);
		
		DynamicActivity at=new DynamicActivity();
		model.addAttribute("at", at);
		return "activityForm";
		
	}
	
	@RequestMapping(value="/saveActivity",method=RequestMethod.POST)
	public String saveActivity(@Valid DynamicActivity at,@RequestParam ("countval") int countval,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		if(at!=null) {
			Activity atpojo=new Activity();
			atpojo.setActivityName(at.getActivityName());
			atpojo.setActivityDesc(at.getActivityDesc());
			atpojo.setStatus("ACTIVE");
			atpojo.setCreatedBy(username);
			atpojo.setCreatedOn(new Date());
			
			int size=countval;
			List<ActivityDetails> adlist=new ArrayList<>();
			for(int i=1;i<=size;i++){  
				ActivityDetails adva=new ActivityDetails();
				adva.setActivity(atpojo);
				adva.setParameterName(request.getParameter("name"+i));
				adva.setType(request.getParameter("typeval"+i));
				if(adva.getType().equals("Test")) {
				adva.setDataone(request.getParameter("dataone"+i));
				adva.setDatatwo(request.getParameter("datatwo"+i));
				}else {
				adva.setDataone(request.getParameter("dataone"+i));
				}
				adva.setCreatedBy(username);
				adva.setCreatedOn(new Date());
				adlist.add(adva);
		    } 
			boolean flag=activityService.saveActivityData(atpojo,adlist);
			if(flag) {
				redirectAttributes.addFlashAttribute("pageMessage", "Activity Successuflly");
			}else {
				redirectAttributes.addFlashAttribute("pageError", "Failed To Activity");
			}
			
		}else {
			redirectAttributes.addFlashAttribute("pageError", " Data Not Found...");
		}
		int size=countval;
		List<DynamicActivity> alist=new ArrayList<>();
		for(int i=1;i<=size;i++){  
			DynamicActivity atpojo=new DynamicActivity();
			atpojo.setLanguage(request.getParameter("lang"+i));
			atpojo.setActivityName(request.getParameter("name"+i));
			atpojo.setActivityDesc(request.getParameter("desc"+i));
			atpojo.setStatus("ACTIVE");
			atpojo.setCreatedBy(username);
			atpojo.setCreatedOn(new Date());
			
			alist.add(atpojo);
	    } 
		boolean flag=activityService.saveActivityData(alist);
		if(flag) {
			redirectAttributes.addFlashAttribute("pageMessage", "Dynamic Activity Successuflly");
		}else {
			redirectAttributes.addFlashAttribute("pageError", "Failed To Activity");
		}
		return "redirect:/activity/getActivityForm";
	}
	@RequestMapping(value="/changeStatus/{id}/{status}",method=RequestMethod.GET)
	public String changeStatus(@PathVariable ("id") long id,@PathVariable ("status") String status,ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		boolean flag=false;
		if(id>0) {
			if(status.equals("INACTIVE")) {
				DynamicActivity at=activityService.getActivityWithId(id);
				at.setStatus("ACTIVE");
				flag=activityService.updateStatus(at);
			}else {
				DynamicActivity at=activityService.getActivityWithId(id);
				at.setStatus("INACTIVE");
				flag=activityService.updateStatus(at);
			}
		
		}
		return "redirect:/activity/getActivityList";
		
	}
	
	
	
	@RequestMapping(value="/getActivityParameterList", method=RequestMethod.GET)
	public String getActivityParameterList(ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		List<DynamicActivityDetails> dadlist=activityService.getDynamicActivityDetailsList();
		model.addAttribute("dadlist", dadlist);
		return "activityParameterList";
		
	}
	@RequestMapping(value="/getActivityParameterForm/{id}", method=RequestMethod.GET)
	public String getActivityParameterForm(@PathVariable ("id") long id,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		DynamicActivity atpojo=activityService.getActivityWithId(id);
		model.addAttribute("atpojo", atpojo);
		return "activityParameterForm";
		
	}
	@RequestMapping(value="/saveActivityParameterForm",method=RequestMethod.POST)
	public String saveActivityParameterForm(@RequestParam ("countval") int countval,@RequestParam ("actid") long actid,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		    String username = request.getSession().getAttribute("userName").toString();
			if(actid>0 && countval>0) {
			int size=countval;
			List<DynamicActivityDetails> adlist=new ArrayList<>();
			for(int i=1;i<=size;i++){  
				DynamicActivity atpojo=activityService.getActivityWithId(actid);
				DynamicActivityDetails adva=new DynamicActivityDetails();
				adva.setActivity(atpojo);
				adva.setParameterName(request.getParameter("param"+i));
				adva.setType(request.getParameter("typeval"+i));
				if(adva.getType().equals("Test")) {
				adva.setDataone(request.getParameter("dataone"+i));
				adva.setDatatwo(request.getParameter("datatwo"+i));
				}else {
				adva.setDataone(request.getParameter("dataone"+i));
				}
				adva.setCreatedBy(username);
				adva.setCreatedOn(new Date());
				adlist.add(adva);
		    } 
			boolean flag=activityService.saveDynamicActivityDetails(adlist);
			if(flag) {
				redirectAttributes.addFlashAttribute("pageMessage", "Dynamic Activity Details Successuflly");
			}else {
				redirectAttributes.addFlashAttribute("pageError", "Failed To Activity");
			}
			}else {
				redirectAttributes.addFlashAttribute("pageError", "Data not found ...");
			}
		return "redirect:/activity/getActivityParameterList";
	}
	@RequestMapping(value="/dynamicActivityUpdate/{id}", method=RequestMethod.GET)
	public String dynamicActivityUpdate(@PathVariable ("id") long id,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		DynamicActivity atpojo=activityService.getActivityWithId(id);
		model.addAttribute("idval", atpojo.getId());
		model.addAttribute("atpojo", atpojo);
		return "activityUpdate.tiles";
		
	}
	@RequestMapping(value="/updateActivity",method=RequestMethod.POST)
	public String updateActivity(@Valid DynamicActivity atpojo,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		if(atpojo!=null) {
		DynamicActivity dac=activityService.getActivityWithId(atpojo.getId());
		
		dac.setActivityDesc(atpojo.getActivityDesc());
		dac.setUpdateBy(username);
		dac.setUpdateOn(new Date());
		boolean flag=activityService.updateDynamicActivity(dac);
		if(flag) {
			redirectAttributes.addFlashAttribute("pageMessage", dac.getActivityName()+": Dynamic Activity Update Successuflly");
		}else {
			redirectAttributes.addFlashAttribute("pageError", dac.getActivityName()+":Failed To Update Activity");
		}
		}else {
			redirectAttributes.addFlashAttribute("pageError", " Data not found...");
		}
		return "redirect:/activity/getActivityList";
		
	}
	@RequestMapping(value="/dynamicActivityDetailsUpdate/{id}", method=RequestMethod.GET)
	public String dynamicActivityDetailsUpdate(@PathVariable ("id") long id,ModelMap model,HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		DynamicActivityDetails dadpojo=activityService.getDynamicActivityDetailsWithId(id);
		model.addAttribute("idval", dadpojo.getId());
		model.addAttribute("dadpojo", dadpojo);
		return "activityDetailsUpdate.tiles";
		
	}
	
	
}
*/