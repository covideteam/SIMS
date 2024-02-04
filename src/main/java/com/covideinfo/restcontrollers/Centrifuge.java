package com.covideinfo.restcontrollers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.dummy.TestJson;

@RestController
@RequestMapping("/sampleProcess")
@PropertySource(value = { "classpath:application.properties" })
public class Centrifuge {
	
	@RequestMapping(value = "/centrifugationStartSave")
	public @ResponseBody String centrifugationStartSave(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		System.out.println(request.getParameter("ranningTime"));
		System.out.println(request.getParameter("runningTimeWithSeconds"));
		return "study/clinical/ecgCollection";
	}
}
