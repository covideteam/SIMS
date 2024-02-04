package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.covideinfo.dao.PDFReportDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.pdf.report.GenerateSiglePdfAction;
import com.covideinfo.service.PDFReportService;

@Controller
@RequestMapping("/pdfReport")
public class PDFReportController {
	
	@Autowired
	PDFReportService pdfReportService;
	@Autowired
	PDFReportDao pdfReportDao;
	
	@RequestMapping(value="/studyActivityPDFReport/{id}",method=RequestMethod.GET)
	public void studyActivityPDFReport(@PathVariable("id") long id,HttpServletRequest request,HttpServletResponse response ,
			ModelMap model) {
		GenerateSiglePdfAction gspa = new GenerateSiglePdfAction();
		try {
			if(id>0) {
				StudyActivityData sad=pdfReportService.getStudyActivityDataWithId(id);
				StudyActivities sa=sad.getSutydActivity();
				GlobalActivity ga=sa.getActivityId();
				String fileName=pdfReportService.generateCrfInPdfFormat(response, request,sad,ga,pdfReportService,pdfReportDao);
				if(!fileName.equals("")) {
					List<String> fileStrList = new ArrayList<>();
					fileStrList.add(fileName);
					gspa.mergePdf(request, response, fileStrList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
