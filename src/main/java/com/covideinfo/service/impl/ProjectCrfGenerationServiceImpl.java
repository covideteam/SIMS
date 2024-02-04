package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.dao.ProjectCrfGenerationDao;
import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.dto.DataCrfDtoDetails;
import com.covideinfo.dto.InclusionAndExclusionDto;
import com.covideinfo.dto.InclusionDto;
import com.covideinfo.dto.PdfGenerationDetailsDto;
import com.covideinfo.dto.ProjectDetailsPdfGenerationDto;
import com.covideinfo.dto.SampleInfoDto;
import com.covideinfo.dto.StudyActivityTimpointsSavingDto;
import com.covideinfo.dto.StudyProjectDetailDto;
import com.covideinfo.dummy.ClinicalInfomation;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.pdf.report.HeaderAndFooter;
import com.covideinfo.pdf.report.HeaderAndFooterForAdvFirstCoverPage;
import com.covideinfo.pdf.report.HeaderAndFooterForAvan;
import com.covideinfo.service.ProjectCrfGenerationService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Service("projectCrfGenerationService")
public class ProjectCrfGenerationServiceImpl extends PdfPageEventHelper implements ProjectCrfGenerationService {
	
	@Autowired
	ProjectCrfGenerationDao projectCrfGenerationDao;

	@Override
	public List<Projects> getProjectsRecords() {
		return projectCrfGenerationDao.getProjectsRecords();
	}
	

	private void dosingRecord(List<DoseTimePoints> doseTimePoints, Map<Integer, List<Long>> nonTimePointDoseParamMap, StudyMaster study, Document document, Font regular,
			Image unchkImage, Image unchkradioimg, Font heading, Locale currentLocale, MessageSource messageSource, InternationalizaionLanguages inlag, List<TreatmentInfo> treatmentList, 
			LanguageSpecificGlobalActivityDetails lsga, ApplicationConfiguration apConfig, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont)
			throws MalformedURLException, IOException, DocumentException {
		Map<Integer, TreatmentInfo> treatMents = new HashMap<>();
		Map<Integer, List<DoseTimePoints>> treatDoseTimePointsMap = new HashMap<>();
		for(DoseTimePoints dtp : doseTimePoints) {
			List<DoseTimePoints> dtps = treatDoseTimePointsMap.get(Integer.parseInt(dtp.getTreatmentInfo().getTreatmentNo()));
			if (dtps == null)
				dtps = new ArrayList<>();
			dtps.add(dtp);
			treatDoseTimePointsMap.put(Integer.parseInt(dtp.getTreatmentInfo().getTreatmentNo()), dtps);
			if (!treatMents.containsKey(Integer.parseInt(dtp.getTreatmentInfo().getTreatmentNo()))) {
				treatMents.put(Integer.parseInt(dtp.getTreatmentInfo().getTreatmentNo()), dtp.getTreatmentInfo());
			}
		}
		int count =1;
		if(!apConfig.getConfigCode().equals("ADV")) {
			for(TreatmentInfo treatment : treatmentList) {
				count++;
				generateDoseFormAvan(treatDoseTimePointsMap,  study, document, regular,
						unchkImage, unchkradioimg, heading, currentLocale, messageSource, inlag, treatment, count, lsga, nonTimePointDoseParamMap, bgColor, mainHeading, subHeading, actHeadFont);
				
			}
		}else {
			for(TreatmentInfo treatment : treatmentList) {
			 if(count ==1) { //No neeed all treatments
				count++;
				generateDoseFrom(treatDoseTimePointsMap, nonTimePointDoseParamMap, study, document, regular,
					unchkImage, unchkradioimg, heading, currentLocale, messageSource, inlag, lsga, treatment, count, bgColor, mainHeading, subHeading, actHeadFont);
			  }
			}
		}
}

	


	private void generateDoseFormAvan(Map<Integer, List<DoseTimePoints>> dtMap, StudyMaster study,
			Document document, Font regular, Image unchkImage, Image unchkradioimg, Font heading, Locale currentLocale, MessageSource messageSource, InternationalizaionLanguages inlag,
			TreatmentInfo tin, int count, LanguageSpecificGlobalActivityDetails lsga, Map<Integer, List<Long>> nonTimePointDoseParamMap, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont)  throws DocumentException {
		
	PdfPCell cell = null;
	String labelStr = null;
	
	PdfPTable hstable = new PdfPTable(5);
	hstable.setWidthPercentage(100f);
	List<DoseTimePoints> dtpList = dtMap.get(Integer.parseInt(tin.getTreatmentNo()));
	for(DoseTimePoints dtp : dtpList) {
		if(count != 1) {
			PdfPTable space = new PdfPTable(1);
			space.setWidthPercentage(100f);
			
			cell = new PdfPCell(new Phrase(""));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(7f);
			space.addCell(cell);
			document.add(space);
		}
		if(lsga.getGlobalActivity().isHeadding()) {
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(i==0)
			     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
			    builder.append(st);
			}
			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#776858
		    cell.setColspan(5);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
		    cell.setNoWrap(false);
			hstable.addCell(cell);
			hstable.getDefaultCell().setBackgroundColor(bgColor);
		}
		    
		//First Row
		labelStr =  messageSource.getMessage("crf.date", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr+":    ", heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setColspan(5);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		//Second Row
		labelStr =  messageSource.getMessage("label.dosing.Pf", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase(tin.getRandamizationCode()+"- "+tin.getTreatmentName(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setColspan(4);
		hstable.addCell(cell);
		
		/*labelStr =  messageSource.getMessage("label.dosing.Drug", null,currentLocale);
			cell = new PdfPCell(new Phrase(labelStr, heading));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
			
			
			cell = new PdfPCell(new Phrase("    ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);*/
		
		//Third Row
		labelStr =  messageSource.getMessage("label.dosing.Strength", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(tin.getStreanth(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setColspan(2);
		hstable.addCell(cell);
		
		labelStr =  messageSource.getMessage("label.dosing.dose", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(tin.getDose(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		labelStr =  messageSource.getMessage("label.dosing.Admintime", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr, heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dtp.getTimePoint(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setColspan(2);
		hstable.addCell(cell);
		
		labelStr =  messageSource.getMessage("label.dosing.awc", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr, heading));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dtp.getTreatmentInfo().getMountOfWaterConsumedWithTheDose(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);

	    if(nonTimePointDoseParamMap.size() > 0) {
	    	List<Long> paramIds = new ArrayList<>();
	    	if(nonTimePointDoseParamMap.containsKey(0)) {
	    		List<Long> parList = nonTimePointDoseParamMap.get(0);
	    		paramIds.addAll(parList);
	    	}
	    	List<Long> pIds = nonTimePointDoseParamMap.get(Integer.parseInt(tin.getTreatmentNo()));
	    	if(pIds != null && pIds.size() > 0) {
	    		paramIds.addAll(pIds);
	    	}
	    	if(paramIds != null && paramIds.size() > 0) {
	    		List<LanguageSpecificGlobalParameterDetails> lsgpList = projectCrfGenerationDao.getLanguageSpecificParameters(paramIds, inlag);
	    		if(lsgpList !=null && lsgpList.size() > 0) {
	    			for(LanguageSpecificGlobalParameterDetails lsgp : lsgpList) {
	    				labelStr =  messageSource.getMessage("label.dosing.parm", null,currentLocale);
	    				cell = new PdfPCell(new Phrase(labelStr, heading));
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);;
	    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	    				cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//	    				cell.setPadding(7f);
	    			    cell.setNoWrap(false);
	    				cell.setColspan(4);
	    				hstable.addCell(cell);
	    				
	    				List<LanguageSpecificValueDetails> lspgvList = null;
	    				String type = lsgp.getParameterId().getContentType().getCode();
	    				if(type.equals("CB") || type.equals("RB") || type.equals("SB")) {
	    					lspgvList = projectCrfGenerationDao.getLanguageSpecificGlobalValusesList(lsgp.getParameterId(), inlag);
	    				}else {
	    					cell = new PdfPCell((new Phrase("", regular)));
		    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    				cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//		    				cell.setPadding(7f);
		    			    cell.setNoWrap(false);
		    			    hstable.addCell(cell);
	    				}
	    				if(lspgvList != null && lspgvList.size() > 0) {
	    					PdfPTable radTab = new PdfPTable(lspgvList.size());
		    				List<String> selectBoxList = new ArrayList<>();
		    				Chunk selectChunk = null;
	    					for(LanguageSpecificValueDetails lsgv : lspgvList) {
	    						Phrase pp = new Phrase(100);
			    				Paragraph p = new Paragraph();
	    						if(type.equals("RB")) {
		    	    				Chunk chunkCheckBoxYes = new Chunk(unchkradioimg, 0, 0, false);
		    	    				p.add(chunkCheckBoxYes);
		    	    				p.add(" ");
		    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
		    	    				pg.setAlignment(Element.ALIGN_LEFT);
		    	    				pp.add(pg);
		    	    				p.add(pp);
		    	    				p.setAlignment(Element.ALIGN_LEFT);
		    	    				
		    	    				cell = new PdfPCell();
		    	    				cell.addElement(p);
		    	    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    	    				cell.setBorder(Rectangle.NO_BORDER);
		    	    				cell.setPaddingTop(3f);
		    	    			    cell.setPaddingBottom(7f);
		    	    			    cell.setPaddingLeft(7f);
		    	    			    cell.setPaddingRight(7f);
//		    	    				cell.setPadding(7f);
		    	    			    cell.setNoWrap(false);
		    	    				radTab.addCell(cell);
	    						}else if(type.equals("CB")) {
	    							Chunk chunkCheckBoxYes = new Chunk(unchkImage, 0, 0, false);
		    	    				p.add(chunkCheckBoxYes);
		    	    				p.add(" ");
		    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
		    	    				pg.setAlignment(Element.WRITABLE_DIRECT);
		    	    				pp.add(pg);
		    	    				p.add(pp);
		    	    				p.setAlignment(Element.WRITABLE_DIRECT);
		    	    				
		    	    				cell = new PdfPCell();
		    	    				cell.addElement(p);
		    	    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    	    				cell.setBorder(Rectangle.NO_BORDER);
		    	    				cell.setPaddingTop(3f);
		    	    			    cell.setPaddingBottom(7f);
		    	    			    cell.setPaddingLeft(7f);
		    	    			    cell.setPaddingRight(7f);
//		    	    				cell.setPadding(7f);
		    	    			    cell.setNoWrap(false);
		    	    				radTab.addCell(cell);
	    						}else
	    							selectBoxList.add(lsgv.getName());
	    					}
	    					if(type.equals("CB") || type.equals("RB")) {
	    						cell = new PdfPCell(radTab);
			    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    				cell.setVerticalAlignment(Element.ALIGN_CENTER);
			    				cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//			    				cell.setPadding(7f);
			    			    cell.setNoWrap(false);
			    			    hstable.addCell(cell);
	    					}else {
	    						selectChunk = new Chunk();
	    						for(String st : selectBoxList) {
	    							selectChunk.append(st);
	    							selectChunk.append("\n");
	    							selectChunk.setFont(regular);
	    						}
	    						cell = new PdfPCell();
	    						cell.addElement(selectChunk);
			    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    				cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//			    				cell.setPadding(7f);
			    			    cell.setNoWrap(false);
			    			    hstable.addCell(cell);
	    							
	    					}
	    					
	    				}
	    			}
	    		}
	    	}
	    }
	    labelStr =  messageSource.getMessage("label.dosing.Describe", null,currentLocale);
		cell = new PdfPCell(new Phrase(labelStr, heading));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//		cell.setPadding(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//		cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setColspan(5);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("", regular));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setFixedHeight(10f);
		cell.setColspan(5);
		hstable.addCell(cell);
		}
		hstable.setHeaderRows(1);
		document.add(hstable);
}


	private void generateDoseFrom(Map<Integer, List<DoseTimePoints>> dtMap,
			Map<Integer, List<Long>> nonTimePointDoseParamMap, StudyMaster study, Document document, Font regular,
			Image unchkImage, Image unchkradioimg, Font heading, Locale currentLocale,
			MessageSource messageSource, InternationalizaionLanguages inlag,
			LanguageSpecificGlobalActivityDetails lsga, TreatmentInfo treatment, int count2, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont) throws DocumentException {
		PdfPCell cell = null;
		PdfPTable hstable = null;
		String headStr = null;
		hstable = new PdfPTable(4);
		hstable.setWidthPercentage(100f);
		
		/*if(count2 != 1) {
			PdfPTable tab = new PdfPTable(1);
			tab.setWidthPercentage(100f);
			cell = new PdfPCell(new Phrase(""));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(3f);
			tab.addCell(cell);
			document.add(tab);
		}*/
		if(lsga.getGlobalActivity().isHeadding()) {
			@SuppressWarnings("unused")
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(i==0)
			     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
			    builder.append(st);
			}
			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#776858
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setColspan(4);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    hstable.getDefaultCell().setBackgroundColor(bgColor);
		}
		
		
		headStr = messageSource.getMessage("label.advDose.doseCal", null,currentLocale);
		headStr = headStr+" " + study.getDosageFrom().getDoseForm();
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(4);
		hstable.addCell(cell);
		
		headStr = messageSource.getMessage("crf.date", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
		hstable.addCell(cell);
		
		
		headStr = messageSource.getMessage("label.advDose.sdt", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
		hstable.addCell(cell);
		
		
		headStr = messageSource.getMessage("label.advDose.adt", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
		hstable.addCell(cell);
		
		headStr = messageSource.getMessage("label.advDose.vu", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
		hstable.addCell(cell);
		
		
		String randomizationCode = "";
		List<DoseTimePoints> dtpList = dtMap.get(Integer.parseInt(treatment.getTreatmentNo()));
		if(dtpList != null) {
			for(DoseTimePoints dtp : dtpList) {
				if(randomizationCode.equals(""))
					randomizationCode = dtp.getTreatmentInfo().getRandamizationCode();
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setVerticalAlignment(Element.ALIGN_CENTER);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
//				headStr = dtp.getTimePoint();
				cell = new PdfPCell(new Phrase("", regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);

			    headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = treatment.getDose();
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
			    
			    
			}
		}
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(30f);
		table.setWidths(new int[]{5, 10, 5});
		
		cell = new PdfPCell(new Phrase("Name of IP : ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setColspan(2);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("No. of Units : ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    PdfPTable table2 = new PdfPTable(1);
	    table2.setWidthPercentage(30f);
//	    table2.setWidths(new int[]{20});
	    
	    Font blodText = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD);
 	    cell = new PdfPCell(new Phrase(randomizationCode, blodText));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setBorderWidth(2f);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(12f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setRowspan(3);
	    table2.addCell(cell);
	    
	    cell = new PdfPCell();
	    cell.addElement(table2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(15f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setRowspan(3);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("Batch No : ", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("Expiry Date : ", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(7f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    table.addCell(cell);
	    
	    
	    cell = new PdfPCell(table);
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(10f);
	    cell.setPaddingBottom(10f);
	    cell.setPaddingLeft(90f);
	    cell.setPaddingRight(90f);
	    cell.setColspan(5);
	    hstable.addCell(cell);
	    
		document.add(new Paragraph("\n"));
		@SuppressWarnings("unused")
		int incNo = 1;
		if(nonTimePointDoseParamMap != null) {
			List<Long> paramIds = new ArrayList<>();
	    	if(nonTimePointDoseParamMap.containsKey(0)) {
	    		List<Long> parList = nonTimePointDoseParamMap.get(0);
	    		paramIds.addAll(parList);
	    	}
	    	List<Long> pIds = nonTimePointDoseParamMap.get(Integer.parseInt(treatment.getTreatmentNo()));
	    	if(pIds != null && pIds.size() > 0) {
	    		paramIds.addAll(pIds);
	    	}
			if(paramIds != null && paramIds.size() > 0) {
				for(Long param : paramIds) {
					LanguageSpecificGlobalParameterDetails lspgp = projectCrfGenerationDao.getlanguageSpecificGlobalParameterDetails(param, inlag);
//					headStr = incNo + ". \t " + lspgp.getName();
					headStr = lspgp.getName();
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
					cell.setColspan(3);
					hstable.addCell(cell);
					
					String contrlType = lspgp.getParameterId().getContentType().getCode();
					if(contrlType.equals("RB") || contrlType.equals("CB") || contrlType.equals("SB")) {
						List<LanguageSpecificValueDetails> lsvdList = projectCrfGenerationDao.getLanguageSpecificGlobalValusesList(lspgp.getParameterId(), inlag);
						PdfPTable conTab = new PdfPTable(1);
						conTab.setWidthPercentage(100f);
						Paragraph phra = new Paragraph();
						if(contrlType.equals("RB")) {
							for(LanguageSpecificValueDetails lsvd : lsvdList) {
								Phrase phrase = new Phrase(100);
//								p.add(new Chunk(unchkradioimg, 0, -2));
//								p.setAlignment(Element.ALIGN_TOP);
								Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
								phra.add(radoButton);
								phra.add(" ");
								phra.setAlignment(Element.WRITABLE_DIRECT);
								Paragraph pgPhara = new Paragraph(lsvd.getName()+" ", regular);
								pgPhara.setAlignment(Element.WRITABLE_DIRECT);
								phrase.add(pgPhara);
								phra.add(phrase);
								
							}
						}else if(contrlType.equals("CB")) {
							for(LanguageSpecificValueDetails lsvd : lsvdList) {
								Phrase ppPhrara = new Phrase(100);
//								p.add(new Chunk(unchkImage, 0, -2));
//								p.setAlignment(Element.ALIGN_TOP);
								Chunk chunkCheckBoxNoImage = new Chunk(unchkImage, 0, 0, false);
								phra.add(chunkCheckBoxNoImage);
								phra.add(" ");
								Paragraph pgPhra = new Paragraph(lsvd.getName()+" ", regular);
								pgPhra.setAlignment(Element.WRITABLE_DIRECT);
								ppPhrara.add(pgPhra);
								phra.add(ppPhrara);
								phra.setAlignment(Element.WRITABLE_DIRECT);
								
							}
						}else if(contrlType.equals("SB")) {
							PdfPTable gvTab = new PdfPTable(1);
							for(LanguageSpecificValueDetails lsvd : lsvdList) {
								cell = new PdfPCell(new Phrase(lsvd.getName(), regular));
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
							    cell.setNoWrap(false);
								gvTab.addCell(cell);
							}
							cell = new PdfPCell(gvTab);
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
							conTab.addCell(cell);
							
							cell = new PdfPCell(conTab);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setPadding(7f);
						    cell.setNoWrap(false);
						    hstable.addCell(cell);
						}
						cell = new PdfPCell();
						cell.addElement(phra);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
						
						if(contrlType.equals("CB"))
							cell.setColspan(2);
						hstable.addCell(cell);
					}else {
						cell = new PdfPCell(new Phrase("  "));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					}
					incNo++;
				}
				
			}
		}

		headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim()+":", regular));
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//		cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(4);
	    hstable.addCell(cell);
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setFixedHeight(10f);
	    cell.setColspan(4);
	    hstable.addCell(cell);
	    
	    PdfPTable stable = new PdfPTable(6);
	    stable.setWidthPercentage(100f);
	    
	    cell = new PdfPCell(new Phrase("Dosed by: "+"\n                _________________________\n                          (Sign & Date)", regular));
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(2);
	    stable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("Supervised by: _________________________\n                                (Sign & Date)", regular));
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(2);
	    stable.addCell(cell);
	    
	    
	    cell = new PdfPCell(new Phrase("Reviewed by (PI/Designee): _________________________\n                                                      (Sign & Date)", regular));
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(2);
	    stable.addCell(cell);
	    
	    cell = new PdfPCell(stable);
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setColspan(4);
	    hstable.addCell(cell);
		
		hstable.setHeaderRows(1);
		document.add(hstable);
	}



	

		

	private ProjectDetailsPdfGenerationDto getProjectDetailsPdfGenerationDetails(Map<Integer, List<ProjectsDetails>> restrictionComplains,
			long langId, Map<Long, List<Long>> defaultMap) {
		ProjectDetailsPdfGenerationDto pdpgDto = null;
		List<Long> avIds = new ArrayList<>();
		List<Long> parmIds = new ArrayList<>();
		PdfGenerationDetailsDto pgdDto = null;
		List<LanguageSpecificGlobalActivityDetails> gaList = null;
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		Map<Long, LanguageSpecificGlobalActivityDetails> gaMap = new HashMap<>();
		Map<Long, LanguageSpecificGlobalParameterDetails> gpMap = new HashMap<>();
		Map<Long, LanguageSpecificGlobalActivityDetails> gaFinalMap = new HashMap<>();
		Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpFinalMap = new HashMap<>();
		InternationalizaionLanguages inlag = null;
		Map<Long, List<Long>> resParamMap = new HashMap<>();
		try {
			if(restrictionComplains != null && restrictionComplains.size() > 0) {
				for(Map.Entry<Integer, List<ProjectsDetails>> resProMap : restrictionComplains.entrySet()) {
					List<ProjectsDetails> proList = resProMap.getValue();
					if(proList != null && proList.size() > 0) {
						String parameterId = "";
				    	String activityId = "";
					    for(ProjectsDetails pd : proList) {
					    	if (pd.getFieldName().equalsIgnoreCase(StudyDesign.RESTRICTIONACTIVITY.toString())) {
					    		activityId = pd.getFieldValue();
					    	}else if (pd.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
					    		parameterId = pd.getFieldValue();
					 		}
					    }
					    List<Long> tempParIds = null;
				    	if(!parameterId.equals("") && !activityId.equals("")) {
				    		if (!avIds.contains(Long.parseLong(activityId)))
				    			avIds.add(Long.parseLong(activityId));
				    		if (!parmIds.contains(Long.parseLong(parameterId)))
								parmIds.add(Long.parseLong(parameterId));
				    		if(resParamMap.containsKey(Long.parseLong(activityId))) {
				    			tempParIds = resParamMap.get(Long.parseLong(activityId));
				    			tempParIds.add(Long.parseLong(parameterId));
				    			resParamMap.put(Long.parseLong(activityId), tempParIds);
				    		}else {
				    			tempParIds = new ArrayList<>();
				    			tempParIds.add(Long.parseLong(parameterId));
				    			resParamMap.put(Long.parseLong(activityId), tempParIds);
				    		}
				    		parameterId ="";
				    		activityId="";
				    	}
					}
				}
			}
			//DefaultActivities
			if(defaultMap != null && defaultMap.size() > 0) {
				for(Map.Entry<Long, List<Long>> dact : defaultMap.entrySet()) {
					if(!avIds.contains(dact.getKey())) {
						avIds.add(dact.getKey());
						parmIds.addAll(dact.getValue());
					}
				}
			}
			pgdDto = projectCrfGenerationDao.getPdfGenerationDetailsDtoDetails(avIds, parmIds, langId);
			if (pgdDto != null) {
				gaList = pgdDto.getGaList();
				gpList = pgdDto.getGpList();
				inlag = pgdDto.getInlag();
				if(gaList != null) {
					for(LanguageSpecificGlobalActivityDetails lsa : gaList) {
						gaMap.put(lsa.getGlobalActivity().getId(), lsa);
					}
				}
				if (gpList != null) {
					for (LanguageSpecificGlobalParameterDetails lsgp : gpList) {
						gpMap.put(lsgp.getParameterId().getId(), lsgp);
					}
				}
				boolean defalultFlag = false;
				if (resParamMap.size() > 0) {
					for (Map.Entry<Long, List<Long>> rescMap : resParamMap.entrySet()) {
						Long actId = rescMap.getKey();
//						defaultActIds.add(actId);
						//Activities
						LanguageSpecificGlobalActivityDetails lsga = gaMap.get(actId);
						if(lsga != null)
							gaFinalMap.put(actId, lsga);
							
						//Parameters
						List<Long> pdIdsList = rescMap.getValue();
						if (pdIdsList != null) {
							for(Long pid : pdIdsList) {
								List<LanguageSpecificGlobalParameterDetails> lsList = null;
								if (gpFinalMap.containsKey(actId)) {
									lsList = gpFinalMap.get(actId);
									lsList.add(gpMap.get(pid));
									gpFinalMap.put(actId, lsList);
								} else {
									lsList = new ArrayList<>();
									lsList.add(gpMap.get(pid));
									gpFinalMap.put(actId, lsList);
								}
							}
						}
					}
					defalultFlag = true;
				}else defalultFlag = true;
				if(defalultFlag) {
					List<LanguageSpecificGlobalParameterDetails> templist = null;
					if(defaultMap != null && defaultMap.size() > 0) {
						for(Map.Entry<Long, List<Long>> dact : defaultMap.entrySet()) {
							List<Long> paramids = dact.getValue();
							if(paramids != null && paramids.size() > 0) {
								for(Long pid : paramids) {
									if(!gaFinalMap.containsKey(dact.getKey())) {
										gaFinalMap.put(dact.getKey(), gaMap.get(dact.getKey()));
										if(gpFinalMap.containsKey(dact.getKey())) {
											templist = gpFinalMap.get(dact.getKey());
											templist.add(gpMap.get(pid));
											gpFinalMap.put(dact.getKey(), templist);
										}else {
											templist = new ArrayList<>();
											templist.add(gpMap.get(pid));
											gpFinalMap.put(dact.getKey(), templist);
										}
									}else {
										if(gpFinalMap.containsKey(dact.getKey())) {
											templist = gpFinalMap.get(dact.getKey());
											templist.add(gpMap.get(pid));
											gpFinalMap.put(dact.getKey(), templist);
										}else {
											templist = new ArrayList<>();
											templist.add(gpMap.get(pid));
											gpFinalMap.put(dact.getKey(), templist);
										}
									}
								}
							}
						}
					}
				}
				pdpgDto = new ProjectDetailsPdfGenerationDto();
				pdpgDto.setGaFinalMap(gaFinalMap);
				pdpgDto.setGpFinalMap(gpFinalMap);
				pdpgDto.setInalg(inlag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdpgDto;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public List<String> generateBlankCrf(HttpServletRequest request, Locale currentLocale,
			StudyCreationBo studyCreationBo, int periodNo, MessageSource messageSource, Long noOfPeriods, BlankPdfDto bpDto, 
			String dateStr, Long langId, String dateStrWithTime)throws Exception {
		List<String> filesList = new ArrayList<>();
		Map<Long, List<LanguageSpecificGlobalParameterDetails>> finalgpMap = new HashMap<>();
		StudyProjectDetailDto spDto = studyCreationBo.mapProjectDetailsToStudyMaster(bpDto.getProject());
		List<DoseTimePoints> doseTimePoints = spDto.getDoseTimePoints();
		Map<Integer, List<Long>> nonTimePointDoseParamMap = null;
		ClinicalInfomation clinalInfo = null;
		ProjectDetailsPdfGenerationDto pdpgDto = null;
		InclusionAndExclusionDto inaeDto = null;
		InclusionAndExclusionDto excluDto = null;
		Map<Integer, List<SampleTimePoints>> twSamplesMap = null;
		Map<Integer, List<MealsTimePoints>> twMealsMap = null;
		
		//Pdf Code Starts
		String period = "";
		if(periodNo < 10)
			period = "0"+periodNo;
		else period = periodNo+"";
		
		//Pdf Generation Code
		String unchkrdPath = request.getServletContext().getRealPath("/static/images/radioUncheck.png");
//		String chkrdPath = request.getServletContext().getRealPath("/static/images/radiobtnChecked8.png");
		String unchkcbPath = request.getServletContext().getRealPath("/static/images/uncheckedCB.png");
//		String chkcbPath = request.getServletContext().getRealPath("/static/images/checkedCB.png");
		String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
//		String img = request.getServletContext().getRealPath("/static/images/AS-LOGO-GRIS-.png");
		String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
		Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		Font regular = new Font(calibri.getFamily(), 9);
		Font heading = new Font(calibri.getFamily(), 9, Font.BOLD);
//		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, new BaseColor(255,255,255));
//		BaseColor bgColor = new BaseColor(52,152,219);// #e6e6e6
		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.BLACK);
		BaseColor bgColor = new BaseColor(211, 211, 211); //#D3D3D3
		
		
		//Checkbox image
		Image unchkImage = Image.getInstance(unchkcbPath);
		unchkImage.setAlignment(Element.ALIGN_CENTER);
		unchkImage.scaleAbsolute(8, 8);
//		unchkImage.scaleToFit(7, 7);
		//Radio Image
		Image unchkradioimg = Image.getInstance(unchkrdPath);
		unchkradioimg.scaleAbsolute(8, 8);
//		unchkradioimg.scaleToFit(7, 7);
		
		String path = realPath+"//BlankCrfGeneration";
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		String filepath = path+"/FinalBlankPdf_"+periodNo+".pdf";
		Document document = new Document();
//	    document.setPageSize(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
		document.addTitle("FinalBlankPdf");
		document.setPageSize(PageSize.A4);
//		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(40, 40, 20, 80); //A4
//		document.setMargins(10f, 10f, 100f, 80f);//A4 Rotation
//		document.setMarginMirroring(false);
//		ApplicationConfiguration apConfig = projectCrfGenerationDao.getApplicationConfigurationRecord("Pdf Header");
		ApplicationConfiguration appConfig = bpDto.getAppConfig();
		HeaderAndFooter advHeader = null;
		HeaderAndFooterForAvan avanHeader = null;
		if(appConfig != null) {
			if(appConfig.getConfigCode().equals("ADV")) {
				advHeader = new HeaderAndFooter(advimg, spDto.getStudyMaster().getProjectNo(), period, bpDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
				writer.setPageEvent(advHeader);
			}else {
				avanHeader = new HeaderAndFooterForAvan(img, spDto.getStudyMaster().getProjectNo(), spDto.getStudyMaster().getSponsorCode(), bpDto, dateStr, regular, heading, mainHeading, subHeading, period, messageSource, currentLocale, dateStrWithTime);
				writer.setPageEvent(avanHeader);
			}
		}else {
			advHeader = new HeaderAndFooter(img, spDto.getStudyMaster().getProjectNo(), period, bpDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
			writer.setPageEvent(advHeader);
		}
		document.open();
		PdfPTable mainTab = new PdfPTable(2);
		mainTab.setWidthPercentage(100f);
		PdfPCell cell = null;
//		if(doseTimePoints != null && doseTimePoints.size() > 0) {
			
			nonTimePointDoseParamMap = spDto.getNonTimePointDosingParamMap();
			clinalInfo = spDto.getClinicalInfomation();
			
			pdpgDto = getProjectDetailsPdfGenerationDetails(spDto.getRestrictionComplains(),langId, spDto.getDefaultActMap());
			inaeDto = getInclusionExclusionData(spDto.getInclusionCriteria(), currentLocale, "inclusion", langId);
			excluDto = getInclusionExclusionData(spDto.getExclusionCriteria(), currentLocale, "exclusion", langId);
			twSamplesMap = getTreatMentWiseSampleTimePoints(clinalInfo.getSampleTimePoints());
			twMealsMap = getTreatmentwiseMealsTimePoints(clinalInfo.getMealsTimePoints());
			//Ordering Global Activities
			List<LanguageSpecificGlobalActivityDetails> finalActList = new ArrayList<>();
			List<Long> actIds = new ArrayList<>();
			if(pdpgDto != null && pdpgDto.getGaFinalMap().size() > 0) {
				Map<Long, LanguageSpecificGlobalActivityDetails> gaMap = pdpgDto.getGaFinalMap();
				if(gaMap.size() > 0) {
					for(Map.Entry<Long, LanguageSpecificGlobalActivityDetails> ga : gaMap.entrySet()) {
						if(!actIds.contains(ga.getValue().getGlobalActivity().getId())) {
							finalActList.add(ga.getValue());
							finalgpMap.putAll(pdpgDto.getGpFinalMap());
							actIds.add(ga.getValue().getGlobalActivity().getId());
						}
					}
				}
			}
			if(inaeDto != null && inaeDto.getGa() != null) {
				if(!actIds.contains(inaeDto.getGa().getGlobalActivity().getId()))
					finalActList.add(inaeDto.getGa());
				finalgpMap.putAll(inaeDto.getGpMap());
			}
			
			if(excluDto != null && excluDto.getGa() != null) {
				if(!actIds.contains(excluDto.getGa().getGlobalActivity().getId()))
					finalActList.add(inaeDto.getGa());
				finalgpMap.putAll(excluDto.getGpMap());
			}
			List<LanguageSpecificGlobalActivityDetails> lsgaList = projectCrfGenerationDao.getLanguageSpecificGlobalActivitiesBasedOnCode(langId);
			if(lsgaList != null)
				finalActList.addAll(lsgaList);
			//Default Activities for Phases
			List<DefaultActivitys> dfalultActList = projectCrfGenerationDao.getDefaultActivitiesList();
			Map<Long, DefaultActivitys> dfactMap = new HashMap<>();
			if(dfalultActList != null) {
				for(DefaultActivitys dfact : dfalultActList) {
					dfactMap.put(dfact.getActivity().getId(), dfact);
				}
			}
			
			
			if(appConfig != null) {
				if(!appConfig.getConfigCode().equals("ADV")) {
					//Reporting Date and time and Check-in time and date
					String lableStr =messageSource.getMessage("label.avanPdf.redt", null,currentLocale);
					cell = new PdfPCell(new Phrase(lableStr+" :", subHeading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setNoWrap(false);
					mainTab.addCell(cell);
					
					cell = new PdfPCell(new Phrase("  ", subHeading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
				    cell.setNoWrap(false);
					mainTab.addCell(cell);
					
					
					String lableStr2 =messageSource.getMessage("label.avanChicken.text", null,currentLocale);
					cell = new PdfPCell(new Phrase(lableStr2+" :", subHeading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
					mainTab.addCell(cell);
					
					cell = new PdfPCell(new Phrase("  ", subHeading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
				    cell.setNoWrap(false);
					mainTab.addCell(cell);
					document.add(mainTab);
					
					PdfPTable spTab = new PdfPTable(1);
					spTab.setWidthPercentage(100f);
					cell = new PdfPCell(new Phrase(""));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(10f);
				    cell.setNoWrap(false);
					spTab.addCell(cell);
					document.add(spTab);
				}
			}
			int count =0;
			//Restrictions Complains And Default Activities
			if(finalActList != null && finalActList.size() > 0) {
				Collections.sort(finalActList);
				SortedMap<Integer, LanguageSpecificGlobalActivityDetails> actMap = getSortedActivitiesDetails(finalActList, dfactMap);
				SortedMap<Integer, LanguageSpecificGlobalActivityDetails> stdActMap = getSortedStudyLevelActivitiesDetails(finalActList, dfactMap);
				SortedMap[] mapArray = new SortedMap[2];
				mapArray[0] = actMap;
				mapArray[1] = stdActMap;
				if(mapArray.length > 0) {
					int mapArrSize = 1;
					if(noOfPeriods == periodNo)
						mapArrSize = mapArray.length;
					for(int i=0; i< mapArrSize; i++) {
						@SuppressWarnings("unchecked")
						SortedMap<Integer, LanguageSpecificGlobalActivityDetails> map = mapArray[i];
						if(map != null && map.size() > 0) {
							Set<Integer> keySet = new TreeSet<>(map.keySet());
							for (Integer key : keySet) {
								LanguageSpecificGlobalActivityDetails lsga = map.get(key);
								//if show pdf yes
								if(lsga.getGlobalActivity().isShowInPDF()) {
									//Checking activity Phase
									DefaultActivitys dac = dfactMap.get(lsga.getGlobalActivity().getId());
									String phase = "";
									if(dac != null)
									   phase = dac.getStudyPhases().getCode();
									boolean phaseFlag = false;
									if(phase != null && (phase.equals("P"+periodNo+"O") || phase.equals("SCI") || phase.equals("SCO") || phase.equals("SE"))) {
										phaseFlag = true;
									}else phaseFlag = true;
									if(phaseFlag) {
										boolean flag  = true;
										if((lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("InclusionCriteria") ||
												lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("ExclusionCriteria")) && periodNo > 1) {
											flag = false;
										}
										if(flag) {
											if(lsga.getGlobalActivity().getId() == 28L)
											if(count !=0) {
												PdfPTable spaceTab = new PdfPTable(1);
												spaceTab.setWidthPercentage(100f);
												cell = new PdfPCell(new Phrase(""));
												cell.setBorder(Rectangle.NO_BORDER);
												cell.setPaddingTop(3f);
											    cell.setPaddingBottom(7f);
											    cell.setPaddingLeft(7f);
											    cell.setPaddingRight(7f);
											    cell.setNoWrap(false);
											    cell.setFixedHeight(7f);
												spaceTab.addCell(cell);
												document.add(spaceTab);
												count++;
											}
											if(count == 0)
												count++;
											List<LanguageSpecificGlobalParameterDetails> gpList = finalgpMap.get(lsga.getGlobalActivity().getId());
											if(gpList != null && gpList.size() > 0) {
												mainTab = new PdfPTable(2);
												mainTab.setWidthPercentage(100f);
												if(gpList.size() == 1) {
													LanguageSpecificGlobalParameterDetails lsgp = gpList.get(0);
												    if(!lsgp.getParameterId().getContentType().getCode().equals("ET") && !lsgp.getParameterId().getContentType().getCode().equals("ST") && !lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinSensivity.toString())
																&& !lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinAdhesion.toString())
																&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("DosingCollection")
																&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("MealsCollection")
																&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SampleCollection")
																&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("StudyExecutionVitals")
																&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase(StudyActivities.StudyWithDraw.toString())) {
												    	if(document.getPageSize().getRotation() == 90) {
															document.setPageSize(PageSize.A4);
															document.newPage();
															advHeader.setRotation(PdfPage.PORTRAIT);
														}
												    	generateActivityPdf(lsga, gpList, spDto.getStudyMaster().getProjectNo(), pdpgDto,  pdpgDto.getInalg(),  document,unchkImage, unchkradioimg, regular, heading, bgColor, mainHeading, subHeading, actHeadFont, periodNo, bpDto.getPhasesMap());
												    	if(document.getPageSize().getRotation() == 90)
												    		advHeader.setRotation(PdfPage.PORTRAIT);
													}
												}else {
													if(!lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinSensivity.toString())
															&& !lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinAdhesion.toString())
															&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("DosingCollection")
															&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("MealsCollection")
															&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SampleCollection")
															&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("StudyExecutionVitals")
															&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase(StudyActivities.StudyWithDraw.toString())) {
														if(document.getPageSize().getRotation() == 90) {
															document.setPageSize(PageSize.A4);
															document.newPage();
															advHeader.setRotation(PdfPage.PORTRAIT);
														}
													    generateActivityPdf(lsga, gpList, spDto.getStudyMaster().getProjectNo(),pdpgDto, pdpgDto.getInalg(), document,unchkImage, unchkradioimg, regular, heading, bgColor, mainHeading, subHeading, actHeadFont, periodNo, bpDto.getPhasesMap());
													    if(document.getPageSize().getRotation() == 90)
												    		advHeader.setRotation(PdfPage.PORTRAIT);
													}
												}
											}
											if(lsga.getGlobalActivity().getActivityCode().equals(StudyActivities.StudyWithDraw.toString())) {
												if(document.getPageSize().getRotation() == 90) {
													document.setPageSize(PageSize.A4);
													document.newPage();
													advHeader.setRotation(PdfPage.PORTRAIT);
												}
												generateStaticActivityPdf(lsga, bpDto, pdpgDto.getInalg(), document,unchkImage, unchkradioimg, regular, heading, bgColor, mainHeading, subHeading, actHeadFont, periodNo);
												if(document.getPageSize().getRotation() == 90)
										    		advHeader.setRotation(PdfPage.PORTRAIT);
											}
											//DosingTime Points
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("DosingCollection")) { 
												document.setPageSize(PageSize.A4.rotate());
												document.newPage();
												advHeader.setRotation(PdfPage.LANDSCAPE);
												dosingRecord(doseTimePoints, nonTimePointDoseParamMap, spDto.getStudyMaster(), document, regular, unchkImage, unchkradioimg, heading, currentLocale, messageSource, pdpgDto.getInalg(), spDto.getTreatmentInfoList(), lsga, appConfig, bgColor, mainHeading, subHeading, actHeadFont);
												activitySeparation(document);
//												advHeader.setRotation(PdfPage.LANDSCAPE);
												document = placeLogoForModifiedDocument(document, advimg);
											}
											//MealsRecords 
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("MealsCollection")) {
												if(twMealsMap.size() > 0) {
													document.setPageSize(PageSize.A4.rotate());
													document.newPage();
													advHeader.setRotation(PdfPage.LANDSCAPE);
													for(Map.Entry<Integer, List<MealsTimePoints>> mealMap : twMealsMap.entrySet()) {
													   generateMealRecordPdf(mealMap.getValue(), document, regular, unchkImage, unchkradioimg, heading, currentLocale, messageSource, lsga, bgColor, appConfig, mainHeading, subHeading, actHeadFont);
//														activitySeparation(document);
													}
													document = placeLogoForModifiedDocument(document, advimg);
													advHeader.setRotation(PdfPage.LANDSCAPE);
												}
											}
											//Blood Samples Or Sample Points
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SampleCollection")) {
												if(twSamplesMap.size() > 0) {
													document.setPageSize(PageSize.A4.rotate());
													document.newPage();
													advHeader.setRotation(PdfPage.LANDSCAPE);
													for(Map.Entry<Integer, List<SampleTimePoints>> sample : twSamplesMap.entrySet()) {
//														generateBloodSheetPdf(sample.getValue(), document, regular, unchkImage, unchkradioimg, heading, bold, messageSource, currentLocale);
														Map<Integer, SampleInfoDto> sampInfMap  = null;
														/*if(spDto != null && spDto.getTreatmentNoMap() != null)
															sampInfMap = getSampleInfoDetails(spDto.getTreatmentNoMap(), bpDto.getSampleProList());*/
														generateBloodSheetAvanPdf(sample.getValue(), document, regular, unchkImage, unchkradioimg, heading, messageSource, currentLocale, bgColor, lsga, mainHeading, subHeading, actHeadFont, sampInfMap);
														activitySeparation(document);
													}
//													advHeader.setRotation(PdfPage.LANDSCAPE);
													document = placeLogoForModifiedDocument(document, advimg);
												}
											}
											//Vitals
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("StudyExecutionVitals")) {
												Map<Integer, List<VitalTimePoints>> twVitalsMap = getTreatmentWiseVitalsDetails(clinalInfo.getVitalTimePoints());
												if(twVitalsMap.size() > 0) {
													document.setPageSize(PageSize.A4.rotate());
													document.newPage();
													advHeader.setRotation(PdfPage.LANDSCAPE);
													for(Map.Entry<Integer, List<VitalTimePoints>> vitalMap : twVitalsMap.entrySet()) {
														generateVitalPdf(vitalMap.getValue(), document, regular, unchkImage, unchkradioimg, heading,pdpgDto.getInalg(), studyCreationBo, messageSource, currentLocale, bpDto.getProject().getProjectId(), lsga, bgColor, mainHeading, subHeading, actHeadFont);
														activitySeparation(document);
													}
//													advHeader.setRotation(PdfPage.LANDSCAPE);
													document = placeLogoForModifiedDocument(document, advimg);
												}
											}
											//SkinAdhection
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SkinAdhesion")) {
												Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhMap = getTreatmentWiseTimePoints(clinalInfo.getSkinAdhFinalMap());
												if(skinAdhMap.size() > 0) {
													document.setPageSize(PageSize.A4);
													for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> skAdhMap : skinAdhMap.entrySet()) {
														generateSkinAdhectionPdf(skAdhMap.getValue(), document, regular, unchkImage, unchkradioimg, heading,pdpgDto.getInalg(), studyCreationBo, "SkingAdhection", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
														activitySeparation(document);
													}
													advHeader.setRotation(PdfPage.PORTRAIT);
												}
											}
											//SkinSensitivity
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SkinSensivity")) {
												Map<Integer, List<StudyActivityTimpointsSavingDto>> skinSenMap = getTreatmentWiseTimePoints(clinalInfo.getSkinSenFinalMap());
												if(skinSenMap.size() > 0) {
													document.setPageSize(PageSize.A4);
													for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> skinSen : skinSenMap.entrySet()) {
														generateSkinAdhectionPdf(skinSen.getValue(), document, regular, unchkImage, unchkradioimg, heading, pdpgDto.getInalg(), studyCreationBo, "SkinSensitivity", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
														activitySeparation(document);
													}
													advHeader.setRotation(PdfPage.PORTRAIT);
													
												}
											}
											//Ecg TimePoints
											if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("EcgTimePoints")) {
												Map<Integer, List<StudyActivityTimpointsSavingDto>> ecgMap = getTreatmentWiseTimePoints(clinalInfo.getTwSatEcgMap());
												if(ecgMap.size() > 0) {
													document.setPageSize(PageSize.A4);
													for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> ecg : ecgMap.entrySet()) {
														generateSkinAdhectionPdf(ecg.getValue(), document, regular, unchkImage, unchkradioimg, heading, pdpgDto.getInalg(), studyCreationBo, "EcgCollection", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
														activitySeparation(document);
													}
													advHeader.setRotation(PdfPage.PORTRAIT);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}else {
				Font red = new Font(FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.RED);
				String lableStr2 =messageSource.getMessage("label.nodataAvl", null,currentLocale);
				cell = new PdfPCell(new Phrase(lableStr2, red));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				mainTab.addCell(cell);
				document.add(mainTab);
			}
		/*}else {
			Font red = new Font(FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.RED);
			String lableStr2 =messageSource.getMessage("label.nodataAvl", null,currentLocale);
			cell = new PdfPCell(new Phrase(lableStr2, red));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			mainTab.addCell(cell);
			document.add(mainTab);
		}*/
		document.close();
		filesList.add(filepath);
		return filesList;
	}
 
	private Document placeLogoForModifiedDocument(Document document, String imgLoc) {
		try {
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(40, 550); 
		    img.scaleAbsolute(80, 30);
		    document.add(img);
		} catch (Exception e) {
			e.printStackTrace();
			return document;
		}
		return document;
		
	}

	private void activitySeparation(Document document) {
		PdfPCell cell = null;
		try {
			PdfPTable spTab2 = new PdfPTable(1);
		    spTab2.setWidthPercentage(100f);
			cell = new PdfPCell(new Phrase(""));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(7f);
		    cell.setNoWrap(false);
		    spTab2.addCell(cell);
			document.add(spTab2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void generateStaticActivityPdf(LanguageSpecificGlobalActivityDetails lsga, BlankPdfDto bpDto,
			InternationalizaionLanguages inalg, Document document, Image unchkImage, Image unchkradioimg, Font regular,
			Font heading, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont, int periodNo) throws DocumentException {
		List<StaticActivityDetails> staticActList =  bpDto.getStaticActList();
		List<StaticActivityValueDetails> savdList = bpDto.getStaticActValList();
		Map<Long, List<StaticActivityValueDetails>> saddMap = new HashMap<>();
		Map<String, List<StaticActivityDetails>> saticActMap = new HashMap<>();
		List<StaticActivityValueDetails> tempSavdList = null;
		if(savdList != null && savdList.size() > 0) {
			for(StaticActivityValueDetails savd : savdList) {
				if(saddMap.containsKey(savd.getStActDetailsId().getId())) {
					tempSavdList = saddMap.get(savd.getStActDetailsId().getId());
					tempSavdList.add(savd);
					saddMap.put(savd.getStActDetailsId().getId(), tempSavdList);
				}else {
					tempSavdList = new ArrayList<>();
					tempSavdList.add(savd);
					saddMap.put(savd.getStActDetailsId().getId(), tempSavdList);
				}
			}
		}
		List<StaticActivityDetails> tempActList = null;
		if(staticActList != null && staticActList.size() > 0) {
			for(StaticActivityDetails sad : staticActList) {
				if(saticActMap.containsKey(sad.getActivityType())) {
					tempActList = saticActMap.get(sad.getActivityType());
					tempActList.add(sad);
					saticActMap.put(sad.getActivityType(), tempActList);
				}else {
					tempActList = new ArrayList<>();
					tempActList.add(sad);
					saticActMap.put(sad.getActivityType(), tempActList);
				}
			}
		}
		if(saticActMap.size() > 0) {
			List<String> strList = new ArrayList<>();
			strList.add("DroupOut");
			strList.add("Withdrawal");
			for(String str : strList) {
				List<StaticActivityDetails> sadList = saticActMap.get(str);
				if(sadList != null) {
					generateSActPdf(lsga, sadList, saddMap,document, unchkImage, unchkradioimg, regular,
							heading, bgColor, mainHeading, subHeading, actHeadFont, periodNo);
				}
			}
		}
	}


	private void generateSActPdf(LanguageSpecificGlobalActivityDetails lsga, List<StaticActivityDetails> sadList,
			Map<Long, List<StaticActivityValueDetails>> saddMap, Document document, Image unchkImage,
			Image unchkradioimg, Font regular, Font heading, BaseColor bgColor, Font mainHeading, Font subHeading,
			Font actHeadFont, int periodNo) throws DocumentException {
		PdfPTable mainTab = new PdfPTable(2);
		mainTab.setWidthPercentage(100f);
		PdfPCell cell = null;
		if(sadList != null && sadList.size() > 0) {
			if(lsga.getGlobalActivity().isHeadding()) {
				String[] words = lsga.getName().split(" ");
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < words.length; i++) {
					String st = "";
					if(!words[i].trim().equals("")) {
						if(i==0)
						     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
							else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
						    builder.append(st);
					}
				}
				cell = new PdfPCell(new Phrase(lsga.getName()+"", actHeadFont));
//							cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setBackgroundColor(bgColor);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    
//						    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    mainTab.addCell(cell);
			    mainTab.getDefaultCell().setBackgroundColor(bgColor);
			}
			for(StaticActivityDetails sad : sadList) {
				Paragraph p = new Paragraph();
				List<StaticActivityValueDetails> savdList = saddMap.get(sad.getId());
				if(savdList != null && savdList.size() > 0) {
					PdfPTable table = new PdfPTable(savdList.size());
					table.setWidthPercentage(100f);
					
					cell = new PdfPCell(new Phrase(sad.getQueryName(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					mainTab.addCell(cell);
					for(StaticActivityValueDetails savd : savdList) {
						Phrase pp = new Phrase(100);
						Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
						p.add(radoButton);
						
						p.add(" ");
						p.setAlignment(Element.WRITABLE_DIRECT);
						Paragraph pg = new Paragraph(savd.getValueName()+" ", regular);
//								pg.setAlignment(Element.ALIGN_CENTER);
						pg.setAlignment(Element.WRITABLE_DIRECT);
						pp.add(pg);
						p.add(pp);
					}
					cell = new PdfPCell();
					cell.addElement(p);
					cell.setNoWrap(false);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					mainTab.addCell(cell);
				}else {
					Phrase pp = new Phrase(100);
					Chunk radoButton = new Chunk(unchkImage, 0, 0, true);
					p.add(radoButton);
					
					p.add(" ");
					p.setAlignment(Element.WRITABLE_DIRECT);
					Paragraph pg = new Paragraph(sad.getQueryName()+" ", regular);
//							pg.setAlignment(Element.ALIGN_CENTER);
					pg.setAlignment(Element.WRITABLE_DIRECT);
					pp.add(pg);
					p.add(pp);
					cell = new PdfPCell();
					cell.addElement(p);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setColspan(2);
					mainTab.addCell(cell);
				}
			}
			cell = new PdfPCell(new Phrase("Comments :", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Performed By : ", regular));
//			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		
		    cell = new PdfPCell(new Phrase("Performed Date : ", regular));
//		    cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    document.add(mainTab);
		    activitySeparation(document);
		}
			
		
		mainTab.setHeaderRows(1);
		document.add(mainTab);
		
	}


	private SortedMap<Integer, LanguageSpecificGlobalActivityDetails> getSortedStudyLevelActivitiesDetails(
			List<LanguageSpecificGlobalActivityDetails> finalActList, Map<Long, DefaultActivitys> dfactMap) {
		SortedMap<Integer, LanguageSpecificGlobalActivityDetails> actMap = new TreeMap<>();
		try {
			for(LanguageSpecificGlobalActivityDetails lsgad : finalActList) {
				System.out.println(lsgad.getName());
				DefaultActivitys dac = dfactMap.get(lsgad.getGlobalActivity().getId());
				String phase = "";
				if(dac != null) {
				   phase = dac.getStudyPhases().getCode();
				   if(phase.equals("STDL"))
					   actMap.put(lsgad.getGlobalActivity().getOrderNo(), lsgad);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actMap;
	}


	private Map<Integer, List<MealsTimePoints>> getTreatmentwiseMealsTimePoints(List<MealsTimePoints> mealsTimePoints) {
		Map<Integer, List<MealsTimePoints>> mealsTpMap = new HashMap<>();
		List<String> timePoints = new ArrayList<>();
		List<MealsTimePoints> mealTpList = null;
		//Separate Distinct timepoints
		if(mealsTimePoints != null && mealsTimePoints.size() > 0) {
			for(MealsTimePoints mtp : mealsTimePoints) {
				if(!timePoints.contains(mtp.getSign()+mtp.getTimePoint())) {
					timePoints.add(mtp.getSign()+mtp.getTimePoint());
					if(mealsTpMap.containsKey(Integer.parseInt(mtp.getTreatmentInfo().getTreatmentNo()))) {
						mealTpList = mealsTpMap.get(Integer.parseInt(mtp.getTreatmentInfo().getTreatmentNo()));
						mealTpList.add(mtp);
						mealsTpMap.put(Integer.parseInt(mtp.getTreatmentInfo().getTreatmentNo()), mealTpList);
					}else {
						mealTpList = new ArrayList<>();
						mealTpList.add(mtp);
						mealsTpMap.put(Integer.parseInt(mtp.getTreatmentInfo().getTreatmentNo()), mealTpList);
					}
				}
			}
		}
		return mealsTpMap;
	}


	@SuppressWarnings("unused")
	private void generateBloodSheetAvanPdf(List<SampleTimePoints> sampleTimePoints, Document document, Font regular,
			Image unchkImage, Image unchkradioimg, Font heading, MessageSource messageSource,
			Locale currentLocale, BaseColor bgColor, LanguageSpecificGlobalActivityDetails lsga, Font mainHeading, 
			Font subHeading, Font actHeadFont, Map<Integer, SampleInfoDto> sampInfMap) throws DocumentException {
		PdfPCell cell = null;
		PdfPTable hstable = null;
		String headStr = null;

		hstable = new PdfPTable(11);
		hstable.setWidthPercentage(100);
		
		if(lsga.getGlobalActivity().isHeadding()) {
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(i==0)
			     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
			    builder.append(st);
			}
			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#776858
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(11);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//			cell.setPadding(7f);
		    cell.setNoWrap(false);
			hstable.addCell(cell);
			hstable.getDefaultCell().setBackgroundColor(bgColor);
		}
		cell = new PdfPCell(new Phrase("Dosing Date: ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(3);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Dosing Time: ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(3);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Light Condition:  ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(5);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Pre and Post Dose Blood Volume: ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(3);
		hstable.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Type of Vacutainer Used: ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(5);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Ice bath required: ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(3);
		hstable.addCell(cell);
		
		for(int i=1; i<=2; i++) {
			if(i==1) {
				headStr =messageSource.getMessage("label.date", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			    
			    cell = new PdfPCell(new Phrase("Time Point \n (Hours)", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			    
				cell = new PdfPCell(new Phrase("Scheduled time", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			    
			    cell = new PdfPCell(new Phrase("I.D check", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    cell.setRowspan(1);
			    hstable.addCell(cell);
			    
				cell = new PdfPCell(new Phrase("Sample Collected on Scheduled Time", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(1);
			    cell.setColspan(2);
			    hstable.addCell(cell);
			    
				cell = new PdfPCell(new Phrase("If no record the Actual time", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			    
			    cell = new PdfPCell(new Phrase("#Code for Deviation Comments", heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			    
			    
			    String headStr1 =messageSource.getMessage("label.performedBy", null,currentLocale);
			    headStr =messageSource.getMessage("label.performedOn", null,currentLocale);
			    
				cell = new PdfPCell(new Phrase(headStr1+"\n & \n"+headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    cell.setRowspan(2);
			    hstable.addCell(cell);
			}
			if(i==2) {
		    	for(int j=1; j<=11; j++) {
		    		if(j==4) {
	    			  	cell = new PdfPCell(new Phrase("Sub ID", heading));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    			    cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//	    			    cell.setPadding(7f);
	    			    cell.setNoWrap(false);
	    			    hstable.addCell(cell);	
	    			}
		    		if(j==5) {
		    			cell = new PdfPCell(new Phrase("Tube ID", heading));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    			    cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//	    			    cell.setPadding(7f);
	    			    cell.setNoWrap(false);
	    			    hstable.addCell(cell);
		    		}
		    		
		    		if(j==6) {
		    			headStr =messageSource.getMessage("label.gaYes", null,currentLocale);
	    				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    			    cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//	    			    cell.setPadding(7f);
	    			    cell.setNoWrap(false);
	    			    hstable.addCell(cell);	
	    		   }
		    	   if(j==7) {
		    		   	headStr =messageSource.getMessage("label.gaNo", null,currentLocale);
	    				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    			    cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//	    			    cell.setPadding(7f);
	    			    cell.setNoWrap(false);
	    			    hstable.addCell(cell);
		    	   }
		    	}
		    }
		}
		hstable.getDefaultCell().setBackgroundColor(new BaseColor(184, 207, 241));
		Collections.sort(sampleTimePoints);
		int count =1;
		int tpCount =0;
		Map<String, String> tolarenceTpsMap = new HashMap<>();
		List<String> stpStrSignsList = new ArrayList<>();
		for (SampleTimePoints stp : sampleTimePoints) {
			headStr = "";
			cell = new PdfPCell(new Phrase(headStr, regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase(stp.getSign()+stp.getTimePoint(), regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    String stpStr = stp.getWindowPeriodSign();
		    if(stpStr.equals("MINUS")) {
		    	stpStr = "-";
		    	if(!stpStrSignsList.contains("-"))
					stpStrSignsList.add("-");
		    }if(stpStr.equals("PLUS")) {
		    	stpStr = "+";
		    if(!stpStrSignsList.contains("+"))
				stpStrSignsList.add("+");
		    }
		    if(stpStr.equals("PLUSANDMINUS")) {
		    	stpStr = "+/-";
		    	if(!stpStrSignsList.contains("+/-"))
					stpStrSignsList.add("+/-");
		    }
		    if(tolarenceTpsMap.containsKey(stpStr+" "+stp.getWindowPeriod()+"")) {
		    	String tpStr = tolarenceTpsMap.get(stpStr+" "+stp.getWindowPeriod()+"");
		    	if(tpCount == 16) {
		    		tpStr = tpStr.trim()+", \n"+stp.getSign().trim()+stp.getTimePoint();
		    		tpCount = 0;
		    	}else tpStr = tpStr.trim()+","+stp.getSign().trim()+stp.getTimePoint();
		    	tolarenceTpsMap.put(stpStr+" "+stp.getWindowPeriod()+"", tpStr);
		    }else {
		    	String tpStr = stp.getSign().trim()+stp.getTimePoint();
		    	tolarenceTpsMap.put(stpStr+" "+stp.getWindowPeriod()+"", tpStr);
		    }
		    tpCount++;
		    
		    cell = new PdfPCell(unchkImage);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(4f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(unchkImage);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(4f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(2);
		    hstable.addCell(cell);
		    count++;
		}
		cell = new PdfPCell(new Phrase("#Please mention reasons for sample collection deviations (DV-Difficulty with Veins, CB- Cannula Blocked, SRL-Subject Reported Late and AOR-Any Other Reason), CR- Cannula Removed, RCD-Re-cannulation Done, SNR- Subject Not Reported, TFP-Taken by fresh prick.", regular));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(11);
	    hstable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("Comments : ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(2);
	    hstable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(" ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//	    cell.setPadding(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(9);
	    hstable.addCell(cell);
		if(tolarenceTpsMap.size() > 0) {
			if(tolarenceTpsMap.size() ==1) {
				for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
					headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), heading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(9);
				    hstable.addCell(cell);
				}
			}else {
				headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(11);
			    hstable.addCell(cell);
			    for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
			    	cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase(trMap.getValue(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(10);
				    hstable.addCell(cell);
			    }
		    }
	    }
		if(stpStrSignsList.size() > 0) {
			for(String st : stpStrSignsList) {
				if(st.equals("+"))
					headStr =messageSource.getMessage("label.sbCol.plus", null,currentLocale);
				else if(st.equals("-"))
					headStr =messageSource.getMessage("label.sbCol.minus", null,currentLocale);
				else if(st.equals("+/-"))
					headStr =messageSource.getMessage("label.sbCol.plusorminus", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, regular));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setBorder(Rectangle.NO_BORDER);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setColspan(11);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
			}
			
		}
		cell = new PdfPCell(new Phrase("", regular));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(11);
		cell.setFixedHeight(35f);
		hstable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("      Reviewed by:      _____________________________ \n  (PI/PIC/Designee)                (Sign & Date) ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(11);
	    hstable.addCell(cell);
		hstable.setHeaderRows(5);
		document.add(hstable);
	    
}


	private SortedMap<Integer, LanguageSpecificGlobalActivityDetails> getSortedActivitiesDetails(
			List<LanguageSpecificGlobalActivityDetails> finalActList, Map<Long, DefaultActivitys> dfactMap) {
		SortedMap<Integer, LanguageSpecificGlobalActivityDetails> actMap = new TreeMap<>();
		try {
			for(LanguageSpecificGlobalActivityDetails lsgad : finalActList) {
				DefaultActivitys dac = dfactMap.get(lsgad.getGlobalActivity().getId());
				String phase = "";
				if(dac != null) 
				   phase = dac.getStudyPhases().getCode();
			   if(!phase.equals("STDL"))
				   actMap.put(lsgad.getGlobalActivity().getOrderNo(), lsgad);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actMap;
	}


	private Map<Integer, List<StudyActivityTimpointsSavingDto>> getTreatmentWiseTimePoints(
			Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhFinalMap) {
		Map<Integer, List<StudyActivityTimpointsSavingDto>> map = new HashMap<>();
		List<String> timePoints = new ArrayList<>();
		List<StudyActivityTimpointsSavingDto> tempList = null;
		//Separate Distinct Timepoints
		if(skinAdhFinalMap.size() > 0) {
			for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> skMap : skinAdhFinalMap.entrySet()) {
				List<StudyActivityTimpointsSavingDto> satsList = skMap.getValue();
				if(satsList != null && satsList.size() > 0) {
					for(StudyActivityTimpointsSavingDto sat : satsList) {
						if(!timePoints.contains(sat.getTimePoint())) {
							timePoints.add(sat.getTimePoint());
							if(map.containsKey(skMap.getKey())) {
								tempList = map.get(skMap.getKey());
								tempList.add(sat);
								map.put(skMap.getKey(), tempList);
							}else {
								tempList = new ArrayList<>();
								tempList.add(sat);
								map.put(skMap.getKey(), tempList);
							}
						}
					}
				}
			}
		}
		return map;
	}


	@SuppressWarnings("unused")
	private void generateSkinAdhectionPdf(List<StudyActivityTimpointsSavingDto> satsList, Document document, Font regular,
			Image unchkImage, Image unchkradioimg, Font heading, InternationalizaionLanguages inalg,
			StudyCreationBo studyCreationBo, String activityName, MessageSource messageSource, Locale currentLocale, LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont) throws DocumentException {
		PdfPTable satsTab = null;
		PdfPCell cell = null;
		String headStr = "";
		satsTab = new PdfPTable(4);
		satsTab.setWidthPercentage(100f);
		if(satsList != null && satsList.size() > 0) {
			if(lsga.getGlobalActivity().isHeadding()) {
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
				String[] words = lsga.getName().split(" ");
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < words.length; i++) {
					String st = "";
					if(i==0)
				     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
					else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				    builder.append(st);
				}
				cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setBackgroundColor(bgColor);//#776858
			    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			    cell.setPadding(8f);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(4);
			    satsTab.addCell(cell);
			    satsTab.getDefaultCell().setBackgroundColor(bgColor);
			}
			
			for(StudyActivityTimpointsSavingDto sat : satsList) {
				
				headStr =messageSource.getMessage("crf.dateWithColun", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    satsTab.addCell(cell);
			    
			    
			    headStr =messageSource.getMessage("crf.timePoint", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr+": "+sat.getTimePoint(), regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    satsTab.addCell(cell);
			    
			    
			    headStr =messageSource.getMessage("label.vital.windowPeriod", null,currentLocale);
			    String sign = sat.getSign();
			    if(sign == null || sign.equals("") )
			    	sign = "+";
				cell = new PdfPCell(new Phrase(headStr+": "+sign+sat.getWindowPeriod()+" "+sat.getWindowPeriodType().substring(0,1).toUpperCase()+sat.getWindowPeriodType().substring(1).toLowerCase(), regular));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cell.setVerticalAlignment(Element.ALIGN_CENTER);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    satsTab.addCell(cell);
			    
			    List<Long> parmList = new ArrayList<>();
			    String[] tempArr = sat.getParameters().split("\\,");
			    if(tempArr.length > 0) {
			    	for(String st : tempArr) {
			    		if(st != null && !st.equals(""))
			    			parmList.add(Long.parseLong(st));
			    	}
			    }
			    List<LanguageSpecificGlobalParameterDetails> gpList = null;
			    if(parmList.size() > 0)
			    	gpList = studyCreationBo.getLanguageSpecificParmetersList(inalg, parmList);
			    if(gpList != null && gpList.size() > 0) {
			    	int size = gpList.size();
			    	String type = "Even";
			    	if(size % 2 == 0) {
			    		type = "Even";
//			    		satsTab = new PdfPTable(gpList.size());
			    	}else {
//			    		satsTab = new PdfPTable(gpList.size()+1);
			    		type = "Odd";
			    	}
//			    	satsTab.setWidthPercentage(100f);
			    	String groupName ="";
			    	for(LanguageSpecificGlobalParameterDetails lsgpd : gpList) {
			    		if(lsgpd.getParameterId().getGroups() != null) {
							boolean flag = false;
							if(groupName.equals("")) {
								groupName = lsgpd.getParameterId().getGroups().getName();
								flag = true;
							}else {
								if(!groupName.equals(lsgpd.getParameterId().getGroups().getName())) {
									groupName = lsgpd.getParameterId().getGroups().getName();
									flag = true;
								}
							}
							if(flag) {
								cell = new PdfPCell(new Phrase(groupName, heading));
							    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    cell.setBackgroundColor(new BaseColor(179, 226, 231));//#B3E2E7
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							    cell.setVerticalAlignment(Element.ALIGN_CENTER);
							    cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(4);
							    satsTab.addCell(cell);
							}
						}
						String contrlType = lsgpd.getParameterId().getContentType().getCode();
//						if(!contrlType.equals("CB")) {
						String paramName = lsgpd.getName();
						if(paramName.contains("&#8805;")) {
							 paramName =  paramName.replaceAll("&#8805;", ">=");
						 }
						 if(paramName.contains("&#8804; "))
							 paramName = paramName.replaceAll("&#8804;", "<=");
							cell = new PdfPCell(new Phrase(paramName, regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
							satsTab.addCell(cell);
//						}
						
						if(contrlType.equals("RB") || contrlType.equals("CB") || contrlType.equals("SB")) {
							List<LanguageSpecificValueDetails> lsvdList = projectCrfGenerationDao.getLanguageSpecificGlobalValusesList(lsgpd.getParameterId(), inalg);
							PdfPTable conTab = new PdfPTable(lsvdList.size());
							conTab.setWidthPercentage(100f);
							Paragraph p = new Paragraph();
							if(contrlType.equals("RB")) {
								for(LanguageSpecificValueDetails lsvd : lsvdList) {
									Phrase pp = new Phrase(100);
									Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
									p.add(radoButton);
									p.add(" ");
									p.setAlignment(Element.WRITABLE_DIRECT);
									Paragraph pgPhara = new Paragraph(lsvd.getName()+" ", regular);
									pgPhara.setAlignment(Element.WRITABLE_DIRECT);
									pp.add(pgPhara);
									p.add(pp);
									
								}
							}else if(contrlType.equals("CB")) {
								for(LanguageSpecificValueDetails lsvd : lsvdList) {
									Phrase pp = new Phrase(100);
									Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
									p.add(radoButton);
									p.add(" ");
									Paragraph pgPhara = new Paragraph(lsvd.getName()+" ", regular);
									pgPhara.setAlignment(Element.ALIGN_MIDDLE);
									pp.add(pgPhara);
									p.add(pp);
									
								}
							}else if(contrlType.equals("SB")) {
								PdfPTable gvTab = new PdfPTable(1);
								for(LanguageSpecificValueDetails lsvd : lsvdList) {
									cell = new PdfPCell(new Phrase(lsvd.getName(), regular));
									cell.setBorder(Rectangle.NO_BORDER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setPaddingTop(3f);
				    			    cell.setPaddingBottom(7f);
				    			    cell.setPaddingLeft(7f);
				    			    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
								    cell.setNoWrap(false);
									gvTab.addCell(cell);
								}
								cell = new PdfPCell(gvTab);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
							    cell.setNoWrap(false);
								conTab.addCell(cell);
								
								cell = new PdfPCell(conTab);
								cell.setNoWrap(false);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
							    cell.setNoWrap(false);
								satsTab.addCell(cell);
							}
							cell = new PdfPCell();
							cell.addElement(p);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
							
							if(contrlType.equals("CB"))
								cell.setColspan(2);
							satsTab.addCell(cell);
						}else {
							cell = new PdfPCell(new Phrase(" "));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
							satsTab.addCell(cell);
						}
					}
			    	if(type.equals("Odd")) {
						cell = new PdfPCell(new Phrase(""));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setPaddingTop(3f);
	    			    cell.setPaddingBottom(7f);
	    			    cell.setPaddingLeft(7f);
	    			    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(2);
						satsTab.addCell(cell);
					}
			    	 //For Equalize to upper Table Columns
			    	cell = new PdfPCell(new Phrase(""));
			    	cell.setBorder(Rectangle.NO_BORDER);
				    cell.setFixedHeight(10f);
				    cell.setColspan(4);
				    satsTab.addCell(cell);
				
			    	
			    	/*headStr =messageSource.getMessage("crf.startTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr+": ", regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    cell.setNoWrap(false);
				    cell.setFixedHeight(25f);
				    satsTab.addCell(cell);
				    
				    
				    headStr =messageSource.getMessage("crf.endTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr+" : ", regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    cell.setNoWrap(false);
				    cell.setFixedHeight(25f);
				    satsTab.addCell(cell);
				    
				    
				    headStr =messageSource.getMessage("label.vital.donBy", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr+": ", regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    cell.setNoWrap(false);
				    cell.setFixedHeight(25f);
				    satsTab.addCell(cell);
				    document.add(satsTab);*/
			    }
			}
			satsTab.setHeaderRows(1);
	    	document.add(satsTab);
		}
	}


	private void generateVitalPdf(List<VitalTimePoints> vitalList, Document document, Font regular, Image unchkImage,
			Image unchkradioimg, Font heading, InternationalizaionLanguages inalg, StudyCreationBo studyCreationBo, MessageSource messageSource, Locale currentLocale, long projectId, 
			LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont) throws DocumentException {
		PdfPTable mainTab = new PdfPTable(1);
		mainTab.setWidthPercentage(100f);
		PdfPCell cell = null;
		String headStr = "";
		int columns =7;
		Map<String, List<VitalTimePoints>> positionVptMap = new HashMap<>();
		List<VitalTimePoints> tempVtpList = null;
		if(vitalList != null && vitalList.size() > 0) {
			for(VitalTimePoints vtp : vitalList) {
				Set<String> positionList = new HashSet<>();
				if(vtp.isOrthostatic()) {
					if(vtp.getOrthostaticPosition() != null) { 
						positionList.add(vtp.getOrthostaticPosition().getFieldValue());
					}
					positionList.add(vtp.getVitalPosition().getFieldValue());
				}else {
					positionList.add(vtp.getVitalPosition().getFieldValue());
				}
				if(positionList.size() > 0) {
					for(String st : positionList) {
						if(positionVptMap.containsKey(st)) {
							tempVtpList = positionVptMap.get(st);
							tempVtpList.add(vtp);
							positionVptMap.put(st, tempVtpList);
						}else {
							tempVtpList = new ArrayList<>();
							tempVtpList.add(vtp);
							positionVptMap.put(st, tempVtpList);
						}
					}
				}
			}
		}
		if(positionVptMap.size() > 0) {
				for(Map.Entry<String, List<VitalTimePoints>> vtpMap : positionVptMap.entrySet()) {
					int defalultRows = 2;
					columns =7;
					int maxParamNo =0;
					List<VitalTimePoints> vptList =vtpMap.getValue();
					List<LanguageSpecificGlobalParameterDetails> lsgpList = projectCrfGenerationDao.getLanguageSpecificGlobalParametersFromStudyActivitParameters(projectId, inalg);
					PdfPTable vitalTab = null;
					if(lsgpList != null && lsgpList.size() > 0) {
						maxParamNo = lsgpList.size();
						vitalTab = new PdfPTable(columns+lsgpList.size());
						vitalTab.setWidthPercentage(100f);
						columns = columns+lsgpList.size();
				    }else {
				    	vitalTab = new PdfPTable(columns);
						vitalTab.setWidthPercentage(100f);
				    }
					if(lsga.getGlobalActivity().isHeadding()) {
						@SuppressWarnings("unused")
						Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
						String[] words = lsga.getName().split(" ");
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < words.length; i++) {
							String st = "";
							if(i==0)
						     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
							else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
						    builder.append(st);
						}
						cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setBackgroundColor(bgColor);//#776858
					    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(columns);
					    vitalTab.addCell(cell);
					    vitalTab.getDefaultCell().setBackgroundColor(bgColor);
					}
				    if(positionVptMap.size() > 1) {
						cell = new PdfPCell(new Phrase("Position :"+vtpMap.getKey(), actHeadFont));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
//						    cell.setBackgroundColor(bgColor);//#776858
					    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(columns);
					    vitalTab.addCell(cell);
//						    vitalTab.getDefaultCell();
					    defalultRows =3;
					}
				    
				    headStr =messageSource.getMessage("label.vitalDate", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
					headStr =messageSource.getMessage("label.vitalSamplingTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
				    headStr =messageSource.getMessage("label.vitalScheduleTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
				    headStr =messageSource.getMessage("label.vitalStartTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
				    if(lsgpList != null && lsgpList.size() > 0) {
				    	for(LanguageSpecificGlobalParameterDetails lsgp : lsgpList) {
				    		headStr =lsgp.getName();
				    		String[] words = headStr.split(" ");
							StringBuilder builder2 = new StringBuilder();
							for (int i = 0; i < words.length; i++) {
								String str = "";
								if(!words[i].trim().equals("")) {
									if(i==0)
									     str = Character.toUpperCase(words[i].trim().charAt(0)) + words[i].trim().substring(1, words[i].trim().length());
										else str = " "+Character.toUpperCase(words[i].trim().charAt(0)) + words[i].trim().substring(1, words[i].trim().length());
									    builder2.append(str);
								}
							}
				    		cell = new PdfPCell(new Phrase(builder2+"", heading));
				    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
				    	}
				    }
				    
				    headStr =messageSource.getMessage("label.vitalEndTime", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
				    String headStr1 =messageSource.getMessage("label.performedBy", null,currentLocale);
					headStr =messageSource.getMessage("label.performedOn", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr1+"\n & \n"+headStr, heading));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setColspan(2);
				    cell.setNoWrap(false);
				    vitalTab.addCell(cell);
				    
//					    vitalTab.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
				    vitalTab.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
				    List<String> vitalSignList = new ArrayList<>();
				    Map<String, String> tolarenceTpsMap = new HashMap<>();
				    int tpCount = 1;
				    for(VitalTimePoints vtp : vptList) {
				    	if(vtp.getSign().equals("")) {
				    		if(!vitalSignList.contains("+"))
				    			vitalSignList.add("+");
				    	}else {
				    		if(!vitalSignList.contains(vtp.getSign()))
				    			vitalSignList.add(vtp.getSign());
			    		}
				    	
				    	cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
				    	
				    	cell = new PdfPCell(new Phrase(vtp.getSign()+vtp.getTimePoint(), regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					     if(lsgpList != null && lsgpList.size() > 0) {
					    	for(int j=0; j<lsgpList.size(); j++) {
					    		LanguageSpecificGlobalParameterDetails lsgp = lsgpList.get(j);
				    			String unitsStr = "";
				    			if(lsgp.getParameterId().getUnitsId() != null)
					    			unitsStr = lsgp.getParameterId().getUnitsId().getUnitsCode();
					    		cell = new PdfPCell(new Phrase("      "+unitsStr, regular));
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
			    			    cell.setPaddingBottom(7f);
			    			    cell.setPaddingLeft(7f);
			    			    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
							    cell.setNoWrap(false);
							    vitalTab.addCell(cell);
					    	}
					    }
					    
					    cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(2);
					    vitalTab.addCell(cell);
					    
					    
					    String windowPeriodSign = "";
					    if(vtp.getWindowPeriodSign().equals("MINUS"))
					    	windowPeriodSign = "-";
					    else if(vtp.getWindowPeriodSign().equals("PLUS"))
					    	windowPeriodSign = "+";
					    else windowPeriodSign = "+/-";
					    
					    if(tolarenceTpsMap.containsKey(windowPeriodSign+" "+vtp.getWindowPeriod()+"")) {
					    	String tpStr = tolarenceTpsMap.get(windowPeriodSign+" "+vtp.getWindowPeriod()+"");
					    	if(tpCount == 16) {
					    		tpStr = tpStr.trim()+", \n"+vtp.getSign().trim()+vtp.getTimePoint();
					    		tpCount = 0;
					    	}else tpStr = tpStr.trim()+","+vtp.getSign().trim()+vtp.getTimePoint();
					    	tolarenceTpsMap.put(windowPeriodSign+" "+vtp.getWindowPeriod()+"", tpStr);
					    }else {
					    	String tpStr = vtp.getSign().trim()+vtp.getTimePoint();
					    	tolarenceTpsMap.put(windowPeriodSign+" "+vtp.getWindowPeriod()+"", tpStr);
					    }
					    
				    }
				   /* if(vitalSignList.size() > 0) {
						for(String st : vitalSignList) {
							if(st.equals("+"))
								headStr =messageSource.getMessage("label.sbCol.plus", null,currentLocale);
							else if(st.equals("-"))
								headStr =messageSource.getMessage("label.sbCol.minus", null,currentLocale);
							else if(st.equals("+/-"))
								headStr =messageSource.getMessage("label.sbCol.plusorminus", null,currentLocale);
							cell = new PdfPCell(new Phrase(headStr, regular));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setBorder(Rectangle.NO_BORDER);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    cell.setColspan(columns);
//							    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
						}
					}*/
				    if(tolarenceTpsMap.size() > 0) {
						int colNo = columns+maxParamNo;
						int labelColNo = 0;
						if(tolarenceTpsMap.size() ==1) {
							if(maxParamNo > 0) 
								labelColNo = colNo-2;
							for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
								headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
								cell = new PdfPCell(new Phrase(headStr.trim(), heading));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    if(labelColNo > 0)
							    	cell.setColspan(colNo-labelColNo);
							    else cell.setColspan(2);
							    vitalTab.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    if(labelColNo > 0)
							    	cell.setColspan(labelColNo);
							    else cell.setColspan(colNo-2);
							    vitalTab.addCell(cell);
							}
						}else {
							if(maxParamNo > 0) 
								labelColNo = colNo-1;
							headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
							cell = new PdfPCell(new Phrase(headStr.trim(), heading));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    if(labelColNo > 0)
						    	cell.setColspan(colNo-labelColNo);
						    else 
						    	cell.setColspan(1);
						    
						    vitalTab.addCell(cell);
						    
						    for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
						    	cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    if(labelColNo > 0)
							    	cell.setColspan(colNo-labelColNo);
							    else 
							    	cell.setColspan(1);
							    vitalTab.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase(trMap.getValue(), regular));
								cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(colNo-1);
							    vitalTab.addCell(cell);
						    }
					    }
				    }
				    cell = new PdfPCell(new Phrase("", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setFixedHeight(10f);
				    cell.setColspan(columns);
				    vitalTab.addCell(cell);
				    
				    
				    cell = new PdfPCell(new Phrase("Reviewed by (PI/Designee): _________________________\n                                                      (Sign & Date)", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(columns);
				    vitalTab.addCell(cell);
				    
				    vitalTab.setHeaderRows(defalultRows);
				    cell = new PdfPCell();
				    cell.addElement(vitalTab);
				    cell.setBorder(Rectangle.NO_BORDER);
				    mainTab.addCell(cell);
				}
				document.add(mainTab);
			}
	}

	private Map<Integer, List<VitalTimePoints>> getTreatmentWiseVitalsDetails(List<VitalTimePoints> vitalsList) {
		Map<Integer, List<VitalTimePoints>> vitalMap = new HashMap<>();
		List<VitalTimePoints> vtpTempList = null;
		List<String> timePoints = new ArrayList<>();
		//Separate Distinct Time Points
		if(vitalsList != null && vitalsList.size() > 0) {
			for(VitalTimePoints vtp : vitalsList) {
				if(!timePoints.contains(vtp.getSign()+vtp.getTimePoint())) {
					timePoints.add(vtp.getSign()+vtp.getTimePoint());
					if(vitalMap.containsKey(Integer.parseInt(vtp.getTreatmentInfo().getTreatmentNo()))) {
						vtpTempList = vitalMap.get(Integer.parseInt(vtp.getTreatmentInfo().getTreatmentNo()));
						vtpTempList.add(vtp);
						vitalMap.put(Integer.parseInt(vtp.getTreatmentInfo().getTreatmentNo()), vtpTempList);
					}else {
						vtpTempList = new ArrayList<>();
						vtpTempList.add(vtp);
						vitalMap.put(Integer.parseInt(vtp.getTreatmentInfo().getTreatmentNo()), vtpTempList);
					}
				}
			}
		}
		return vitalMap;
	}


	private Map<Integer, List<SampleTimePoints>> getTreatMentWiseSampleTimePoints(
			List<SampleTimePoints> sampleTimePoints) {
		Map<Integer, List<SampleTimePoints>> sampleMap = new HashMap<>();
		List<String> timePoints = new ArrayList<>();
		List<SampleTimePoints> sampleTempList = null;
		//Separate Distinct timepoints
		if(sampleTimePoints != null && sampleTimePoints.size() > 0) {
			for(SampleTimePoints stp : sampleTimePoints) {
				if(!timePoints.contains(stp.getSign()+stp.getTimePoint())) {
					timePoints.add(stp.getSign()+stp.getTimePoint());
					if(sampleMap.containsKey(Integer.parseInt(stp.getTreatmentInfo().getTreatmentNo()))) {
						sampleTempList = sampleMap.get(Integer.parseInt(stp.getTreatmentInfo().getTreatmentNo()));
						sampleTempList.add(stp);
						sampleMap.put(Integer.parseInt(stp.getTreatmentInfo().getTreatmentNo()), sampleTempList);
					}else {
						sampleTempList = new ArrayList<>();
						sampleTempList.add(stp);
						sampleMap.put(Integer.parseInt(stp.getTreatmentInfo().getTreatmentNo()), sampleTempList);
					}
				}
			}
		}
		return sampleMap;
	}


	/*private Map<Integer, List<LanguageSpecificGlobalActivityDetails>> getOrderGlobalActivityDetails(LanguageSpecificGlobalActivityDetails ga,
			Map<Integer, List<LanguageSpecificGlobalActivityDetails>> finalOrderActMap) {
		List<LanguageSpecificGlobalActivityDetails> tempActList = null;
		if(ga != null) {
			if(finalOrderActMap.containsKey(ga.getGlobalActivity().getOrderNo())) {
				tempActList = finalOrderActMap.get(ga.getOrderNo());
				tempActList.add(ga);
				finalOrderActMap.put(ga.getGlobalActivity().getOrderNo(), tempActList);
			}else {
				tempActList = new ArrayList<>();
				tempActList.add(ga);
				finalOrderActMap.put(ga.getGlobalActivity().getOrderNo(), tempActList);
			}
		}
		return finalOrderActMap;
	}*/

	@SuppressWarnings("unused")
	private void generateActivityPdf(LanguageSpecificGlobalActivityDetails lsga,
			List<LanguageSpecificGlobalParameterDetails> lsgpdList, String projectNo,
			ProjectDetailsPdfGenerationDto pdpgDto, InternationalizaionLanguages inalg, Document document, Image unchkImage, Image unchkradioimg,Font regular, Font heading, BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont, int periodNo, Map<Long, StudyPhases> phasesMap) throws DocumentException {
		PdfPTable mainTab = new PdfPTable(2);
		mainTab.setWidthPercentage(100f);
		PdfPCell cell = null;
		boolean finalFlag = false;
		List<Long> gropIds = new ArrayList<>();
		Map<Integer, List<LanguageSpecificGlobalParameterDetails>> parametersMap = getGroupAndNonGroupParameters(lsgpdList);
		
		if(parametersMap.size() > 0) {
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
			if(lsga.getGlobalActivity().isHeadding()) {
				String[] words = lsga.getName().split(" ");
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < words.length; i++) {
					String st = "";
					if(i==0)
				     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
					else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				    builder.append(st);
				}
				cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setBackgroundColor(bgColor);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    mainTab.addCell(cell);
			    mainTab.getDefaultCell().setBackgroundColor(bgColor);
			}
			for(Map.Entry<Integer, List<LanguageSpecificGlobalParameterDetails>> parmMap : parametersMap.entrySet()) {
				List<LanguageSpecificGlobalParameterDetails> lsgpList = parmMap.getValue();
				Collections.sort(lsgpList);
				Map<Long, List<LanguageSpecificGlobalParameterDetails>> groupParmMap = new HashMap<>();
				List<LanguageSpecificGlobalParameterDetails> paramList = parmMap.getValue();
				Collections.sort(paramList);
				List<LanguageSpecificGlobalParameterDetails> tempList = null;
				for(LanguageSpecificGlobalParameterDetails lsgp : paramList) {
					if(lsgp.getParameterId().getGroups() != null) {
						if(groupParmMap.containsKey(lsgp.getParameterId().getGroups().getId())) {
							tempList = groupParmMap.get(lsgp.getParameterId().getGroups().getId());
							tempList.add(lsgp);
							Collections.sort(tempList);
							groupParmMap.put(lsgp.getParameterId().getGroups().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgp);
							Collections.sort(tempList);
							groupParmMap.put(lsgp.getParameterId().getGroups().getId(), tempList);
						}
					}else {
						if(groupParmMap.containsKey(0L)) {
							tempList = groupParmMap.get(0L);
							tempList.add(lsgp);
							Collections.sort(tempList);
							groupParmMap.put(0L, tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgp);
							Collections.sort(tempList);
							groupParmMap.put(0L, tempList);
						}
					}
				}

				String groupName ="";
				for(Map.Entry<Long, List<LanguageSpecificGlobalParameterDetails>> pmap : groupParmMap.entrySet()) {
					List<LanguageSpecificGlobalParameterDetails> lsgplist = pmap.getValue();
					if(lsgpdList != null) {
						for(LanguageSpecificGlobalParameterDetails lsgpd : lsgplist) {
							String phases = lsgpd.getParameterId().getParameterPhase();
							boolean phaseFlag = false;
							if(phases != null && !phases.equals("")) {
								String[] tempArr = phases.split("\\,");
								if(tempArr.length > 0) {
									for(String str : tempArr) {
										if(periodNo == 1) {
											if(phasesMap.get(Long.parseLong(str)).getCode().equals("P"+periodNo+"O")
												|| com.covideinfo.enums.StudyPhases.SCI.toString().equals(phasesMap.get(Long.parseLong(str)).getCode())) {
												phaseFlag = true;
											}
										}else {
											if(com.covideinfo.enums.StudyPhases.SE.toString().equals(phasesMap.get(Long.parseLong(str)).getCode())
													|| com.covideinfo.enums.StudyPhases.SCI.toString().equals(phasesMap.get(Long.parseLong(str)).getCode())
													|| com.covideinfo.enums.StudyPhases.SCO.toString().equals(phasesMap.get(Long.parseLong(str)).getCode())) {
												phaseFlag = true;
											}else {
												if(phasesMap.get(Long.parseLong(str)).getCode().equals("P"+periodNo+"O")) {
													phaseFlag = true;
												}
											}
										}
									}
								}
							}
							if(phaseFlag) {
								finalFlag = true;
								if(lsgpd.getParameterId().getGroups() != null) {
									boolean flag = false;
									if(groupName.equals("")) {
										groupName = projectCrfGenerationDao.getLanguageSpecificGroupName(lsgpd.getParameterId().getGroups(), inalg);
										gropIds.add(lsgpd.getParameterId().getGroups().getId());
										flag = true;
									}else {
										if(!gropIds.contains(lsgpd.getParameterId().getGroups().getId())) {
											groupName = projectCrfGenerationDao.getLanguageSpecificGroupName(lsgpd.getParameterId().getGroups(), inalg);
											gropIds.add(lsgpd.getParameterId().getGroups().getId());
											flag = true;
										}else flag = false;
									}
									if(flag) {
										cell = new PdfPCell(new Phrase(groupName, subHeading));
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell.setVerticalAlignment(Element.ALIGN_CENTER);
									    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setBackgroundColor(bgColor);//#B3E2E7
									    cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
									    cell.setNoWrap(false);
									    cell.setColspan(2);
									    mainTab.addCell(cell);
									}
								}
								String contrlType = lsgpd.getParameterId().getContentType().getCode();
								 String paramName = lsgpd.getName();
								 if(paramName.contains("&#8805;")) {
									 paramName =  paramName.replaceAll("&#8805;", ">=");
								 }
								 if(paramName.contains("&#8804; "))
									 paramName = paramName.replaceAll("&#8804;", "<=");
								
								cell = new PdfPCell(new Phrase(paramName, regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setNoWrap(false);
								if(lsgpd.getParameterId().getContentType().getCode().equals("LABEL")) 
									cell.setColspan(2);
								mainTab.addCell(cell);
								
								if(contrlType.equals("RB") || contrlType.equals("CB") || contrlType.equals("SB")) {
									List<LanguageSpecificValueDetails> lsvdList = projectCrfGenerationDao.getLanguageSpecificGlobalValusesList(lsgpd.getParameterId(), inalg);
									TreeMap<Integer, LanguageSpecificValueDetails> valuesMap = new TreeMap<>();
									if(lsvdList != null && lsvdList.size() > 0) {
										for(LanguageSpecificValueDetails lsvd : lsvdList) {
											valuesMap.put(lsvd.getGlobalValId().getOrderNo(), lsvd);
										}
									}
									PdfPTable conTab = new PdfPTable(1);
									conTab.setWidthPercentage(100f);
									Paragraph p = new Paragraph();
									if(contrlType.equals("RB")) {
										for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
											LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
											Phrase pp = new Phrase(100);
//											p.add(new Chunk(unchkradioimg, 0, -2));
//											p.setAlignment(Element.ALIGN_TOP);
											Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
											p.add(radoButton);
											p.add(" ");
											p.setAlignment(Element.WRITABLE_DIRECT);
											Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
//											pg.setAlignment(Element.ALIGN_CENTER);
											pg.setAlignment(Element.WRITABLE_DIRECT);
											pp.add(pg);
											p.add(pp);
											
										}
									}else if(contrlType.equals("CB")) {
										for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
											LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
											Phrase pp = new Phrase(100);
//											p.add(new Chunk(unchkImage, 0, -2));
//											p.setAlignment(Element.ALIGN_TOP);
											Chunk chunkCheckBoxNo = new Chunk(unchkImage, 0, 0, false);
											p.add(chunkCheckBoxNo);
											p.add(" ");
											p.setAlignment(Element.WRITABLE_DIRECT);
											Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
											pg.setAlignment(Element.WRITABLE_DIRECT);
											pp.add(pg);
											p.add(pp);
											
										}
									}else if(contrlType.equals("SB")) {
										PdfPTable gvTab = new PdfPTable(1);
										for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
											LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
											cell = new PdfPCell(new Phrase(lsvd.getName(), regular));
											cell.setBorder(Rectangle.NO_BORDER);
											cell.setNoWrap(false);
											cell.setPaddingTop(3f);
										    cell.setPaddingBottom(7f);
										    cell.setPaddingLeft(7f);
										    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
										    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											gvTab.addCell(cell);
										}
										cell = new PdfPCell(gvTab);
										cell.setBorder(Rectangle.NO_BORDER);
										cell.setNoWrap(false);
										cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
									    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										conTab.addCell(cell);
										
										cell = new PdfPCell(conTab);
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										cell.setPadding(7f);
									    cell.setNoWrap(false);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
										mainTab.addCell(cell);
									}
									cell = new PdfPCell();
									cell.addElement(p);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setNoWrap(false);
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
									if(contrlType.equals("CB"))
										cell.setColspan(2);
									mainTab.addCell(cell);
								}else {
									if(!lsgpd.getParameterId().getContentType().getCode().equals("LABEL")) { 
										cell = new PdfPCell(new Phrase("   "));
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
										cell.setNoWrap(false);
										cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
			//							cell.setPadding(7f);
										mainTab.addCell(cell);
									}
								}
							}
						}
					}
				}
			}
			cell = new PdfPCell(new Phrase("Performed By : ", regular));
//			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		
		    cell = new PdfPCell(new Phrase("Performed Date : ", regular));
//		    cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    activitySeparation(document);
		}
		mainTab.setHeaderRows(1);
		if(finalFlag)
			document.add(mainTab);

		
	}


	private Map<Integer, List<LanguageSpecificGlobalParameterDetails>> getGroupAndNonGroupParameters(
			List<LanguageSpecificGlobalParameterDetails> lsgpdList) {
		Map<Integer, List<LanguageSpecificGlobalParameterDetails>> map = new HashMap<>();
		List<LanguageSpecificGlobalParameterDetails> tempList = null;
		try {
			if(lsgpdList != null && lsgpdList.size() > 0) {
				for(LanguageSpecificGlobalParameterDetails lsgpd : lsgpdList) {
					if(lsgpd.getParameterId().getGroups() != null) {
						if(map.containsKey(1)) {
							tempList = map.get(1);
							tempList.add(lsgpd);
							map.put(1, tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgpd);
							map.put(1, tempList);
						}
					}else {
						if(map.containsKey(2)) {
							tempList = map.get(2);
							tempList.add(lsgpd);
							map.put(2, tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgpd);
							map.put(2, tempList);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	private void generateMealRecordPdf(List<MealsTimePoints> mealsTimePoints, Document document, Font regular,
		Image unchkImage, Image unchkradioimg, Font heading, Locale currentLocale, MessageSource messageSource, LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor, ApplicationConfiguration appConfig, Font mainHeading, Font subHeading, Font actHeadFont) throws DocumentException {
		PdfPCell cell = null;
		PdfPTable hstable = null;
		String headStr = null;
		int columns = 0;
		if(appConfig != null) {
			if(appConfig.getConfigCode().equals("ADV")) { 
				hstable = new PdfPTable(9);
				columns = 9;
			}else {
				hstable = new PdfPTable(9);
				columns = 9;
			}
		}
		hstable.setWidthPercentage(100f);
		
		if(lsga.getGlobalActivity().isHeadding()) {
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(i==0)
			     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
			    builder.append(st);
			}
			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#776858
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(columns);
		    hstable.addCell(cell);
		    hstable.getDefaultCell().setBackgroundColor(bgColor);
		}
		for(int i=1; i<=2; i++) {
			if(i == 1) {
				headStr =messageSource.getMessage("label.date", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr =messageSource.getMessage("crf.mealType", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr =messageSource.getMessage("label.meals.scheduleTime", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				headStr =messageSource.getMessage("crf.actualtime", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, heading));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setColspan(2);
				hstable.addCell(cell);
				
				String consumed = messageSource.getMessage("label.meals.consume", null,currentLocale);
				String asperStr = messageSource.getMessage("label.meals.aspermeal", null,currentLocale);
				String menu = messageSource.getMessage("label.meals.menu", null,currentLocale);
				String yesStr = messageSource.getMessage("label.gaYes", null,currentLocale);
				String noStr = messageSource.getMessage("label.gaNo", null,currentLocale);
				headStr = consumed+"\n "+asperStr+" "+ menu+"\n ("+yesStr+"/*"+noStr+")";
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				String headStr1 = messageSource.getMessage("label.performedBy", null,currentLocale);
				headStr = messageSource.getMessage("label.performedOn", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr1+"\n & \n "+headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
				cell.setRowspan(2);
				hstable.addCell(cell);
			}else {
				for(int j=1; j<=8; j++) {
					if(j==4) {
						headStr = messageSource.getMessage("crf.startTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					}
					if(j==5) {
						headStr = messageSource.getMessage("crf.endTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					}
				}
			}
		}
		hstable.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
		if(mealsTimePoints != null && mealsTimePoints.size() > 0) {
			for (MealsTimePoints meal : mealsTimePoints) {
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				String sign = meal.getSign();
				if(sign == null || sign.equals(""))
					sign = "+";
				headStr = meal.getMealsType().getFieldValue() + "\n(" + sign + meal.getTimePoint() + " hr)";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
			    
			    headStr = "";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    hstable.addCell(cell);
			}
		}
		headStr = messageSource.getMessage("label.meals.col1", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//		cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(9);
		hstable.addCell(cell);
		
		headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim()+": ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setColspan(8);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
	    
		cell = new PdfPCell(new Phrase("      Reviewed by:      _____________________________ \n  (PI/PIC/Designee)                (Sign & Date) ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(9);
	    hstable.addCell(cell);
		
		hstable.setHeaderRows(3);
		document.add(hstable);
}

	
	private InclusionAndExclusionDto getInclusionExclusionData(Map<Integer, List<ProjectsDetails>> inclusionCriteria,
			Locale currentLocale, String actrString, Long langId) {
		InclusionAndExclusionDto inaeDto = null;
		LanguageSpecificGlobalActivityDetails ga = null;
		Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpMap = new HashMap<>();
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		List<Long> parmIds = new ArrayList<>();
		if(inclusionCriteria != null && inclusionCriteria.size() > 0) {
			for(Map.Entry<Integer, List<ProjectsDetails>> incMap : inclusionCriteria.entrySet()) {
				if(incMap.getValue() != null && incMap.getValue().size() > 0) {
					for (ProjectsDetails p : incMap.getValue()) {
						if (!p.getFieldName().equalsIgnoreCase(StudyDesign.GENDER.toString())) {
							if (p.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
								if (!parmIds.contains(Long.parseLong(p.getFieldValue())))
									parmIds.add(Long.parseLong(p.getFieldValue()));
							}
						}
					}
				}
			}
			String actrCode = "";
			if(actrString.equals("inclusion"))
				actrCode = StudyActivities.InclusionCriteria.toString();
			else if(actrString.equals("exclusion"))
				actrCode = StudyActivities.ExclusionCriteria.toString();
			InclusionDto indto = projectCrfGenerationDao.getGlobalParametersList(parmIds, langId, actrCode);
			
			if(indto != null) {
				ga = indto.getLsga();
				gpList = indto.getLspgpList();
				gpMap.put(indto.getLsga().getGlobalActivity().getId(), gpList);
			}
		}
		
		inaeDto = new InclusionAndExclusionDto();
		inaeDto.setGa(ga);
		inaeDto.setGpMap(gpMap);
		return inaeDto;
	}

@Override
public BlankPdfDto getBlankPdfDtoDetails(Long projectNo, String userName) {
	return projectCrfGenerationDao.getBlankPdfDtoDetails(projectNo, userName);
}


@Override
public Map<String, String> getProjectTitleFromPojectDetails(Long projectNo) {
	return projectCrfGenerationDao.getProjectTitleFromPojectDetails(projectNo) ;
}


@Override
public ApplicationConfiguration getApplicationConfigurationRecord(String string) {
	return projectCrfGenerationDao.getApplicationConfigurationRecord(string);
}


@SuppressWarnings("unused")
@Override
public List<String> generateCoverPages(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Long projectNo, Long langId, BlankPdfDto bpDto, String dateStr, String dateStrWithTime) {
	List<String> fileList = new ArrayList<>();
		Map<String, String> proMap = null;
	String fileName = "";
	String fname = "";
	/*Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
	Font heading = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);*/
	int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
	Font calibri = FontFactory.getFont("Calibri");
	Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
	Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//	Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
	Font regular = new Font(calibri.getFamily(), 10);
	Font heading = new Font(calibri.getFamily(), 10, Font.BOLD);
	try {
		proMap = projectCrfGenerationDao.getProjectTitleFromPojectDetails(projectNo);
		ApplicationConfiguration appConfig = bpDto.getAppConfig();
		if(appConfig != null) {
			if(appConfig.getConfigCode().equals("ADV")) {
				fileName = generateFirstCoverPageAdv(request, currentLocale, messageSource, proMap, langId, bpDto, dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
//				fname = generateSecondCoverPageAdv(request, currentLocale, messageSource, proMap, langId, user, appConfig, dateStr);
			}else {
				fileName = generateFirstCoverPage(request, currentLocale, messageSource, proMap, langId, bpDto, dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
				fname = generateSecondCoverPage(request, currentLocale, messageSource, proMap, langId, bpDto, dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
			}
		}
		if(fileName != null && !fileName.equals(""))
			fileList.add(fileName);
		if(fname != null && !fname.equals(""))
			fileList.add(fname);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return fileList;
}
@SuppressWarnings("unused")
private String generateFirstCoverPageAdv(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Map<String, String> proMap, Long langId, BlankPdfDto bpDto, String dateStr, Font regular, Font heading, Font mainHeading, Font subHeading, String dateStrWithTime) throws FileNotFoundException, DocumentException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//BlankCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalBlankPdf_CoverPage1.pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate());
	document.setMargins(40, 40, 20, 170); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(false);
	document.open();
	
	PdfPTable footTab = new PdfPTable(4);
	footTab.setWidthPercentage(100f);
	footTab.setTotalWidth(500f);
	PdfPCell cell = null;
	String lableStr ="";
	
	
	cell = new PdfPCell(new Phrase("", heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr =messageSource.getMessage("label.advCoverName", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr =messageSource.getMessage("label.advCoverJobTitle", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
    lableStr =messageSource.getMessage("label.advCoverSignedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
    //First Row
    
    lableStr =messageSource.getMessage("label.advCoverPreferedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr ="";
   cell = new PdfPCell(new Phrase(lableStr, regular));
   cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
   cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
   cell.setPaddingTop(3f);
   cell.setPaddingBottom(7f);
   cell.setPaddingLeft(7f);
   cell.setPaddingRight(7f);
//	cell.setPadding(7f);
   cell.setNoWrap(false);
   footTab.addCell(cell);
	
    lableStr = "";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr = "";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
	
	//Second Row
    lableStr =messageSource.getMessage("label.advCoverApproveBy", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr ="";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
    lableStr ="";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
    lableStr ="";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(proMap.size() > 0) {
		projectNumber = proMap.get("projectNumber");
		projectTitle = proMap.get("projectTitle");
		sponsorCode = proMap.get("sponsorCode");
	}
	StudyVolunteerReporting svr = null;
	DataCrfDtoDetails dcDto = null;
	HeaderAndFooterForAdvFirstCoverPage event1 = new HeaderAndFooterForAdvFirstCoverPage(advimg, projectNumber, "", bpDto.getUser(), dateStr, regular, heading, svr, messageSource, currentLocale, mainHeading, dcDto, dateStrWithTime, footTab);
	writer.setPageEvent(event1);
	document.open();
	
//	PdfPCell cell = null;
	PdfPTable table = null;
//	String lableStr = "";
	Chunk chunk = null;
	
	table = new PdfPTable(4);
	table.setWidthPercentage(100f);
	
	lableStr =messageSource.getMessage("label.advCover.proNo", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr+" "+projectNumber, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBorder(Rectangle.NO_BORDER);
	/*cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);*/
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	cell.setFixedHeight(20f);
	table.addCell(cell);
	
	//First Row
	lableStr =messageSource.getMessage("label.advCoverStudyTitle", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setColspan(4);
	table.addCell(cell);
	
	
	cell = new PdfPCell(new Phrase(projectTitle, regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(7f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	document.add(table);
	document.close();
	return filepath;
}


@SuppressWarnings("unused")
private String generateSecondCoverPage(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Map<String, String> proMap, Long langId, BlankPdfDto bpDto, String dateStr, Font regular, Font heading, Font mainHeading, Font subHeading, String dateStrWithTime) throws DocumentException, FileNotFoundException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//BlankCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalBlankPdf_CoverPage2.pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate());
	document.setMargins(40, 40, 20, 90); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(false);
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(proMap.size() > 0) {
		projectNumber = proMap.get("projectNumber");
		projectTitle = proMap.get("projectTitle");
		sponsorCode = proMap.get("sponsorCode");
	}
	ApplicationConfiguration appConfig = bpDto.getAppConfig();
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) {
			HeaderAndFooter event1 = new HeaderAndFooter(advimg, projectNumber, "", bpDto.getUser(), dateStr, regular, heading,mainHeading, subHeading, dateStrWithTime );
			writer.setPageEvent(event1);
		}else {
			HeaderAndFooterForAvan event1 = new HeaderAndFooterForAvan(img, projectNumber, sponsorCode, bpDto, dateStr, regular, heading, mainHeading, subHeading, "H", messageSource, currentLocale, dateStrWithTime);
			writer.setPageEvent(event1);
		}
	}else {
		HeaderAndFooter event1 = new HeaderAndFooter(img, projectNumber, "", bpDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
		writer.setPageEvent(event1);
	}
	document.open();
	
	PdfPCell cell = null;
	PdfPTable table = null;
	String lableStr = "";
	
	table = new PdfPTable(4);
	table.setWidthPercentage(90f);
	table.setWidths(new int[]{10,40,10,40});
	
	lableStr =messageSource.getMessage("label.coverPage2.title", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//FirstRow
	lableStr =messageSource.getMessage("label.coverPage2.Abbreviations", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//SecondRow
	lableStr =messageSource.getMessage("label.coverPage2.bmi", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.bmiAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.m", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Third Row
	lableStr =messageSource.getMessage("label.coverPage2.bpm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.bpmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.min", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.minAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Fourth Row
	lableStr =messageSource.getMessage("label.coverPage2.cm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.cmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mL", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mLAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	//Fifth Row
	lableStr =messageSource.getMessage("label.coverPage2.ECG", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.ECGAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mmHg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mmHgAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Sixth Row
	lableStr =messageSource.getMessage("label.coverPage2.h", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.hAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	Paragraph p = new Paragraph();
	p.add("m");
	Chunk superScript = new Chunk("2");
	superScript.setFont(heading);
	superScript.setTextRise(6f);
	p.add(superScript);
	cell = new PdfPCell();
	cell.addElement(p);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.m2Abr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Seventh Row
	lableStr =messageSource.getMessage("label.cvoerPage2.kg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.kgAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.N/A", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.N/AAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	//Eight Row
	lableStr =messageSource.getMessage("label.coverPage2.brpm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPge2.brpmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	Chunk dchunk = new Chunk("o");
	dchunk.setFont(heading);
	dchunk.setTextRise(6f);
	Paragraph p1 = new Paragraph();
	p1.add(dchunk);
	p1.add("C");
	p1.setFont(heading);
	cell = new PdfPCell();
	cell.addElement(p1);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.degreeAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	
	document.add(table);
	document.close();
	return filepath;
}


private String generateFirstCoverPage(HttpServletRequest request, Locale currentLocale,
		MessageSource messageSource, Map<String, String> proMap, Long langId, BlankPdfDto bpDto, String dateStr, Font regular, Font heading, Font mainHeading, Font subHeading, String dateStrWithTime) throws FileNotFoundException, DocumentException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//BlankCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalBlankPdf_CoverPage1.pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate());
	document.setMargins(40, 40, 20, 90); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(false);
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(proMap.size() > 0) {
		projectNumber = proMap.get("projectNumber");
		projectTitle = proMap.get("projectTitle");
		sponsorCode = proMap.get("sponsorCode");
	}
	ApplicationConfiguration appConfig = bpDto.getAppConfig();
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) {
			HeaderAndFooter event1 = new HeaderAndFooter(advimg, projectNumber, "", bpDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
			writer.setPageEvent(event1);
		}else {
			HeaderAndFooterForAvan event1 = new HeaderAndFooterForAvan(img, projectNumber, sponsorCode, bpDto, dateStr, regular, heading, mainHeading, subHeading, "H", messageSource, currentLocale, dateStrWithTime);
			writer.setPageEvent(event1);
		}
	}else {
		HeaderAndFooter event1 = new HeaderAndFooter(img, projectNumber, "", bpDto.getUser(),  dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
		writer.setPageEvent(event1);
	}
	document.open();
	
	PdfPCell cell = null;
	PdfPTable table = null;
	String lableStr = "";
	
	table = new PdfPTable(4);
	table.setWidthPercentage(100f);
	
	lableStr =messageSource.getMessage("label.coverPage.title", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//First Row
	lableStr =messageSource.getMessage("label.coverPage.date", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setColspan(3);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Second Row
	lableStr =messageSource.getMessage("label.coverPage.proTitle", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase(projectTitle, regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setColspan(3);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Third Row
	lableStr =messageSource.getMessage("label.coverPage.subReg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.subin", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setFixedHeight(25f);
	table.addCell(cell);
	
	//Forth Row
	
	PdfPTable tempTab = new PdfPTable(4);
	tempTab.setWidthPercentage(100f);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	tempTab.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.name", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	tempTab.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.singedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	tempTab.addCell(cell);
	
	cell = new PdfPCell(tempTab);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setColspan(4);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Fifth Row
	lableStr =messageSource.getMessage("label.coverPage.preparedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Sixth Row
	lableStr =messageSource.getMessage("label.coverPage.pireviewed", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Seventh Row
	lableStr =messageSource.getMessage("label.coverPage.qareviewedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	document.add(table);
	document.close();
	return filepath;
}
	

}
