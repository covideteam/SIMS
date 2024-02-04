package com.covideinfo.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.PDFReportDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.service.DefaultActivitysService;
import com.covideinfo.service.PDFReportService;
import com.itextpdf.text.DocumentException;

@Service("pdfReportService")
public class PDFReportServiceImpl implements PDFReportService{
	
    @Autowired
    PDFReportDao  pdfReportDao;
    @Autowired
    DefaultActivitysService  defaultActivitysService;
	@Override
	public StudyActivityData getStudyActivityDataWithId(long id) {
		StudyActivityData saddata=pdfReportDao.getStudyActivityDataWithId(id);
		return saddata;
	}
	@Override
	public String generateCrfInPdfFormat(HttpServletResponse response, HttpServletRequest request,
			StudyActivityData sad, GlobalActivity ga, PDFReportService pdfReportService, PDFReportDao pdfReportDao) throws DocumentException, MalformedURLException, IOException {
		/*GenerateReportPdf grp = new GenerateReportPdf();
		List<StudyActivityDataDetailsDto> studydata = new ArrayList<>();
		List<StudyCheckInActivityDataDetails> studyCheck = null;
		List<StudyCheckOutActivityDataDetails> studyCheckout = null;
		List<StudyExecutionActivityDataDetails> studyExecution = null;
		studyCheck = defaultActivitysService.getStudyCheckInActivityDataDetailsWithIdList(sad.getId());
		StudyActivityDataDetailsDto saddd;
		for (StudyCheckInActivityDataDetails dda1 : studyCheck) {
			LanguageSpecificValueDetails langSpecific=null;
			LanguageSpecificGlobalParameterDetails langParameter=null;
			List<ParameterControlTypeValues> pctv=null;
       	     Locale locale = LocaleContextHolder.getLocale();
  	         String lang=locale.getCountry();
  	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
  	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(dda1.getGlobalValues(),lanuage);
  	         langParameter=defaultActivitysService.getLanguageSpecificGlobalParameterDetailsWithLang(dda1.getSaParameter(),lanuage);
  	         pctv=defaultActivitysService.getParameterControlTypeValuesWithGparameter(dda1.getSaParameter().getParameterId());
  	         saddd = new StudyActivityDataDetailsDto();
			saddd.setComments(dda1.getComments());
			saddd.setCreatedBy(dda1.getCreatedBy());
			saddd.setCreatedOn(dda1.getCreatedOn());
			saddd.setDeviation(dda1.isDeviation());
			saddd.setId(dda1.getId());
			saddd.setSaData(dda1.getSaData());
			saddd.setSaParameter(dda1.getSaParameter());
			saddd.setValue(dda1.getValue());
			saddd.setGlobalValues(dda1.getGlobalValues());
			saddd.setGlobalName(langSpecific.getName());
			saddd.setParamName(langParameter.getName());
			
			String data="";
			for(ParameterControlTypeValues pc:pctv) {
				if(data=="") {
					data=pc.getGlobalValues().getName();
				}else {
					data=data+"##"+pc.getGlobalValues().getName();
				}
			}
			saddd.setControleParametrData(data);
			
			studydata.add(saddd);
		}

		if (studyCheck == null) {
			studyCheckout = defaultActivitysService.getStudyCheckOutActivityDataDetailsWithIdList(sad.getId());
			for (StudyCheckOutActivityDataDetails dda1 : studyCheckout) {
				LanguageSpecificValueDetails langSpecific=null;
				LanguageSpecificGlobalParameterDetails langParameter=null;
				List<ParameterControlTypeValues> pctv=null;
	        	 Locale locale = LocaleContextHolder.getLocale();
	   	         String lang=locale.getCountry();
	   	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
	   	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(dda1.getGlobalValues(),lanuage);
	   	         langParameter=defaultActivitysService.getLanguageSpecificGlobalParameterDetailsWithLang(dda1.getSaParameter(),lanuage);
	   	        pctv=defaultActivitysService.getParameterControlTypeValuesWithGparameter(dda1.getSaParameter().getParameterId());
				saddd = new StudyActivityDataDetailsDto();
				saddd.setComments(dda1.getComments());
				saddd.setCreatedBy(dda1.getCreatedBy());
				saddd.setCreatedOn(dda1.getCreatedOn());
				saddd.setDeviation(dda1.isDeviation());
				saddd.setId(dda1.getId());
				saddd.setSaData(dda1.getSaData());
				saddd.setSaParameter(dda1.getSaParameter());
				saddd.setValue(dda1.getValue());
				saddd.setGlobalValues(dda1.getGlobalValues());
				saddd.setGlobalName(langSpecific.getName());
				saddd.setParamName(langParameter.getName());
				String data="";
				for(ParameterControlTypeValues pc:pctv) {
					if(data!="") {
						data=pc.getGlobalValues().getName();
					}else {
						data=data+"##"+pc.getGlobalValues().getName();
					}
				}
				saddd.setControleParametrData(data);
				studydata.add(saddd);
			}
		}
		if (studyCheckout == null) {
			studyExecution = defaultActivitysService.getStudyExecutionActivityDataDetailsWithIdList(sad.getId());
			for (StudyExecutionActivityDataDetails dda1 : studyExecution) {
				LanguageSpecificValueDetails langSpecific=null;
				LanguageSpecificGlobalParameterDetails langParameter=null;
				List<ParameterControlTypeValues> pctv=null;
		        	 Locale locale = LocaleContextHolder.getLocale();
		   	         String lang=locale.getCountry();
		   	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
		   	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(dda1.getGlobalValues(),lanuage);
		   	      langParameter=defaultActivitysService.getLanguageSpecificGlobalParameterDetailsWithLang(dda1.getSaParameter(),lanuage);
		   	    pctv=defaultActivitysService.getParameterControlTypeValuesWithGparameter(dda1.getSaParameter().getParameterId());
				saddd = new StudyActivityDataDetailsDto();
				saddd.setComments(dda1.getComments());
				saddd.setCreatedBy(dda1.getCreatedBy());
				saddd.setCreatedOn(dda1.getCreatedOn());
				saddd.setDeviation(dda1.isDeviation());
				saddd.setId(dda1.getId());
				saddd.setSaData(dda1.getSaData());
				saddd.setSaParameter(dda1.getSaParameter());
				saddd.setValue(dda1.getValue());
				saddd.setGlobalValues(dda1.getGlobalValues());
				saddd.setGlobalName(langSpecific.getName());
				saddd.setParamName(langParameter.getName());
				String data="";
				for(ParameterControlTypeValues pc:pctv) {
					if(data!="") {
						data=pc.getGlobalValues().getName();
					}else {
						data=data+"##"+pc.getGlobalValues().getName();
					}
				}
				saddd.setControleParametrData(data);
				studydata.add(saddd);
			}
		}
		
		

		String fileName = "";
		fileName = grp.generateReport(response, request, sad, ga, studydata,
				pdfReportService, pdfReportDao);
		return fileName;
*/	
     return "";
}
}
