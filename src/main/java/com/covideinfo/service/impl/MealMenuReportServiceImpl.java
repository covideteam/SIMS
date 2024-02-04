package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.MealMenuReportDao;
import com.covideinfo.dto.MealDietPlanItemDto;
import com.covideinfo.dto.MealMenuReportsDetailsDto;
import com.covideinfo.dto.MealMenuReportsDto;
import com.covideinfo.dto.StudyLevelConfiguredMealsDietDetailsDto;
import com.covideinfo.model.UserMaster;
import com.covideinfo.pdf.report.HeaderAndFooterForMealMenu;
import com.covideinfo.pdf.report.MultipuleToSinglePdfConvertion;
import com.covideinfo.pdf.report.TextPdfWithFileLocation;
import com.covideinfo.service.MealMenuReportService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("mealMenuReportService")
public class MealMenuReportServiceImpl implements MealMenuReportService {
	
	@Autowired
	MealMenuReportDao mealMenuReportDao;

	@Override
	public MealMenuReportsDto getMealMenuReportDetails() {
		MealMenuReportsDto mmrDto = new MealMenuReportsDto();
		MealMenuReportsDetailsDto mmrdDto = null;
		try {
			mmrdDto = mealMenuReportDao.getMealMenuReportDetails();
			if(mmrdDto != null) {
				mmrDto.setSmList(mmrdDto.getSmList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mmrDto;
	}

	@Override
	public MealMenuReportsDto getStduyMealReportDetails(Long studyId) {
		MealMenuReportsDto mmrDto = new MealMenuReportsDto();
		MealMenuReportsDetailsDto mmrdDto = null;
		Map<Long, List<StudyLevelConfiguredMealsDietDetailsDto>> slcmdMap = new HashMap<>();
		List<Double> tpsList = new ArrayList<>();
		Map<Double, String> doubleTpStrMap = new HashMap<>();//timePoint, orderNo
		Map<Double, String> onlyTpStrMap = new HashMap<>();//timePointInDouble, timepointStr
		Map<String, String> tpOrderMap = new HashMap<>();//timePoint, orderNo
		Map<String, List<Long>> periodWiseTpsMap = new HashMap<>();//timepoint, periodsList
		try {
			mmrdDto = mealMenuReportDao.getStudyLevelMealReportDetails(studyId);
			if(mmrdDto != null) {
				mmrDto.setSpmList(mmrdDto.getSpmList());
				if(mmrdDto.getSlcmdList() != null && mmrdDto.getSlcmdList().size() > 0) {
					List<StudyLevelConfiguredMealsDietDetailsDto> slcmdList = null;
					List<Long> pidsList = null;
					for(StudyLevelConfiguredMealsDietDetailsDto slcmd : mmrdDto.getSlcmdList()) {
						if(periodWiseTpsMap.size() == 0 || (periodWiseTpsMap.size() > 0 && periodWiseTpsMap.get(slcmd.getTpSign()+slcmd.getTimePoint()) == null) ||(periodWiseTpsMap.get(slcmd.getTpSign()+slcmd.getTimePoint()) != null && !periodWiseTpsMap.get(slcmd.getTpSign()+slcmd.getTimePoint()).contains(slcmd.getPeriodId()))) {
							if(periodWiseTpsMap.containsKey(slcmd.getTpSign()+slcmd.getTimePoint())) {
								pidsList = periodWiseTpsMap.get(slcmd.getTpSign()+slcmd.getTimePoint());
								pidsList.add(slcmd.getPeriodId());
								periodWiseTpsMap.put(slcmd.getTpSign()+slcmd.getTimePoint(), pidsList);
							}else {
								pidsList = new ArrayList<>();
								pidsList.add(slcmd.getPeriodId());
								periodWiseTpsMap.put(slcmd.getTpSign()+slcmd.getTimePoint(), pidsList);
							}
							if(slcmdMap.containsKey(slcmd.getPeriodId())) {
								slcmdList = slcmdMap.get(slcmd.getPeriodId());
								slcmdList.add(slcmd);
								slcmdMap.put(slcmd.getPeriodId(), slcmdList);
							}else {
								slcmdList = new ArrayList<>();
								slcmdList.add(slcmd);
								slcmdMap.put(slcmd.getPeriodId(), slcmdList);
							}
							tpsList.add(Double.parseDouble(slcmd.getTpSign()+slcmd.getTimePoint()));
							onlyTpStrMap.put(Double.parseDouble(slcmd.getTpSign()+slcmd.getTimePoint()), slcmd.getTpSign()+slcmd.getTimePoint());
						
						}
					}
				}
			}
			if(tpsList.size() > 0) {
				Collections.sort(tpsList);
				int count =1;
				for(Double tp : tpsList) {
					String countNo = "";
					if(count < 9)
						countNo = "0"+count;
					else countNo = count+"";
					if(!doubleTpStrMap.containsKey(tp)) {
						doubleTpStrMap.put(tp, countNo);
						count++;
					}
				}
			}
			if(doubleTpStrMap.size() > 0) {
				for(Map.Entry<Double, String> tpStrMap : doubleTpStrMap.entrySet()) {
					tpOrderMap.put(onlyTpStrMap.get(tpStrMap.getKey()), tpStrMap.getValue());
				}
			}
				
			mmrDto.setSlcmdMap(slcmdMap);
			mmrDto.setTpOrderMap(tpOrderMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mmrDto;
	}

	@Override
	public String generateMealsMenuPdf(Long studyId, List<Long> dietIdsList, List<String> melsReportInfo,
			HttpServletRequest request, HttpServletResponse response, Locale currentLocale, Long langId, String dateStr,
			String dateStrWithTime, MessageSource messageSource, Long userId) {
		TreeMap<Integer, List<Long>> dietIdsOrderMap = new TreeMap<>();
		Map<Long, String> dietTpNoMap = new HashMap<>();
		Map<Long, String> dietPeriods = new HashMap<>();
		Map<Long, String> mealTypeMap = new HashMap<>();
		Map<Long, String> mealTitles = new HashMap<>();
		Map<Long, Long> mealDietPlanMap = new HashMap<>();
		List<Long> dietPlanIds = new ArrayList<>();
		Map<Long, List<MealDietPlanItemDto>> mealDietItemsMap = new HashMap<>();
		List<MealDietPlanItemDto> mealDietItemsList = null;
		MealMenuReportsDetailsDto mmrdDto = null;
		List<Long> tempList = null;
		String fileName = "";
		String studyName = null;
		UserMaster user = null;
		try {
			if(dietIdsList != null && dietIdsList.size() > 0) {
				if(melsReportInfo != null && melsReportInfo.size() > 0) {
					for(String st : melsReportInfo) {
						String[] temp = st.split("\\@@@");
						if(temp != null && temp.length > 0) {
							dietPeriods.put(Long.parseLong(temp[0]), temp[1]);
							dietTpNoMap.put(Long.parseLong(temp[0]), temp[2]);
							mealTypeMap.put(Long.parseLong(temp[0]), temp[3]);
							mealTitles.put(Long.parseLong(temp[0]), temp[4]);
							mealDietPlanMap.put(Long.parseLong(temp[0]), Long.parseLong(temp[5]));
							dietPlanIds.add(Long.parseLong(temp[5]));
							//Pdf order
							if(dietIdsOrderMap.containsKey(Integer.parseInt(temp[6]))) {
								tempList  = dietIdsOrderMap.get(Integer.parseInt(temp[6]));
								tempList.add(Long.parseLong(temp[0]));
								dietIdsOrderMap.put(Integer.parseInt(temp[6]), tempList);
							}else {
								tempList  = new ArrayList<>();
								tempList.add(Long.parseLong(temp[0]));
								dietIdsOrderMap.put(Integer.parseInt(temp[6]), tempList);
							}
						}
					}
				}
				if(dietIdsList.size() > 0) {
					mmrdDto = mealMenuReportDao.getMealMenuItemsRecordsList(dietPlanIds, userId, studyId);
					if(mmrdDto != null) {
						user = mmrdDto.getUser();
						studyName = mmrdDto.getStudyName();
						mealDietItemsList = mmrdDto.getMealDietItemsList();
						if(mealDietItemsList != null && mealDietItemsList.size() > 0) {
							List<MealDietPlanItemDto> mealItemsList = null;
							for(MealDietPlanItemDto mdpi : mealDietItemsList) {
								if(mealDietItemsMap.containsKey(mdpi.getMealDietplanId())) {
									mealItemsList = mealDietItemsMap.get(mdpi.getMealDietplanId());
									mealItemsList.add(mdpi);
									mealDietItemsMap.put(mdpi.getMealDietplanId(), mealItemsList);
								}else {
									mealItemsList = new ArrayList<>();
									mealItemsList.add(mdpi);
									mealDietItemsMap.put(mdpi.getMealDietplanId(), mealItemsList);
								}
							}
						}
					}
				}
				fileName = generateMealReportPdf(mealDietItemsMap, dietIdsOrderMap, mealTypeMap,mealTitles, mealDietPlanMap, request, response, dietTpNoMap, dateStrWithTime, 
						studyName, user, messageSource, currentLocale);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fileName.equals("")) {
			TextPdfWithFileLocation tpdf = new TextPdfWithFileLocation();
			fileName = tpdf.generateTextPdf("There is Some Internal Issue. Please Check And Try Again.", request, response, "Meal Menu", "MealMenuReports", "mealMenuReport");
		}
		return fileName;
	}

	

	private String generateMealReportPdf(Map<Long, List<MealDietPlanItemDto>> mealDietItemsMap,
			TreeMap<Integer, List<Long>> dietIdsOrderMap, Map<Long, String> mealTypeMap, Map<Long, String> mealTitles,
			Map<Long, Long> mealDietPlanMap, HttpServletRequest request, HttpServletResponse response, Map<Long, String> dietTpNoMap,
			String dateStrWithTime, String studyName, UserMaster user, MessageSource messageSource,
			Locale currentLocale) {
		List<String> filesList = new ArrayList<>();
		String finalFileName ="";
		try {
			if(dietIdsOrderMap.size() > 0) {
				for(Map.Entry<Integer, List<Long>> orderDietMap : dietIdsOrderMap.entrySet()) {
					for(Long dietId : orderDietMap.getValue()) {
						String fileName = generatePdf(mealDietItemsMap.get(mealDietPlanMap.get(dietId)), mealTypeMap.get(dietId), mealTitles.get(dietId), request, response, dietId, dietTpNoMap, dateStrWithTime, studyName, user);
						if(!fileName.equals("")) {
							filesList.add(fileName);
						}
					}
				}
			}
			if(filesList != null && filesList.size() > 0) {
				MultipuleToSinglePdfConvertion mtspc = new MultipuleToSinglePdfConvertion();
				String fname = mtspc.convertSinglePdf(request, response, filesList, messageSource, currentLocale, "FinalMealReports", "Final_MealMenuReport", "Document No: CL-TP039-01", "Meal Menu Pdf");
				if(!fname.equals(""))
					finalFileName = fname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalFileName;
	}

	@SuppressWarnings("unused")
	private String generatePdf(List<MealDietPlanItemDto> mealDietItemsList, String mealType, String mealTitle, HttpServletRequest request,
			HttpServletResponse response, Long dietId, Map<Long, String> dietTpNoMap, String dateStrWithTime, String studyName, UserMaster user) {
		String filepath = "";
		String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
		Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		Font regular = new Font(calibri.getFamily(), 9);
		Font heading = new Font(calibri.getFamily(), 9, Font.BOLD);
		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.BLACK);
		BaseColor bgColor = new BaseColor(211, 211, 211); //#D3D3D3
		String realPath = request.getSession().getServletContext().getRealPath("/");
		try {
			
			String path = realPath+"//MealMenuReports";
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/MealMenuReport"+dietId+"_.pdf";
			
			Document document = new Document();
//			    document.setPageSize(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.addTitle("Meal Menu");
			document.setPageSize(PageSize.A4);
//				document.setPageSize(PageSize.A4.rotate());
			document.setMargins(40, 40, 20, 80); //A4
//				document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
			document.setMarginMirroring(false);
			HeaderAndFooterForMealMenu headerEvent = new HeaderAndFooterForMealMenu(advimg, studyName, user, regular,  dateStrWithTime, dietTpNoMap.get(dietId));
			writer.setPageEvent(headerEvent);
			document.open();
			
			PdfPTable mainTab = new PdfPTable(4);
			mainTab.setWidthPercentage(95f);
			PdfPCell cell = null;
			
			
			cell = new PdfPCell(new Phrase("Meal Type : "+mealType, regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setColspan(3);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setNoWrap(false);
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Meal Reference ID : *"+mealTitle, regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setNoWrap(false);
			mainTab.addCell(cell);
				
			cell = new PdfPCell(new Phrase("Food Item", heading));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(2);
		    mainTab.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("Quantity \n (No./gm/ml)", heading));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("Energy \n (kcal)", heading));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    mainTab.getDefaultCell().setBackgroundColor(bgColor);
			
		    boolean flag = false;
		    Double totalCalories = 0.0;
		    if(mealDietItemsList != null && mealDietItemsList.size() > 0) {
		    	flag = true;
		    	for(MealDietPlanItemDto mdpi : mealDietItemsList) {
					cell = new PdfPCell(new Phrase(mdpi.getItemName(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setNoWrap(false);
					cell.setColspan(2);
					mainTab.addCell(cell);
					
					cell = new PdfPCell(new Phrase(mdpi.getQuantity()+" "+mdpi.getUnitsCode(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setNoWrap(false);
					mainTab.addCell(cell);
					
					cell = new PdfPCell(new Phrase(mdpi.getTotalCalries()+"", regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setNoWrap(false);
					mainTab.addCell(cell);
					
					totalCalories = totalCalories+mdpi.getTotalCalries();
				}
		    }
			if(flag) {
				cell = new PdfPCell(new Phrase("Total Energy(kcal)", regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setNoWrap(false);
				cell.setColspan(3);
				mainTab.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase(totalCalories+"", regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setNoWrap(false);
				mainTab.addCell(cell);
			}
			cell = new PdfPCell(new Phrase("Calorific Value Source: NIN(National Institute of Nutrition)", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setBorder(Rectangle.NO_BORDER);
			cell.setNoWrap(false);
			cell.setColspan(4);
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Note: Acceptance criteria for meal compliance is +/- 5% of the quantity in meal menu.", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setBorder(Rectangle.NO_BORDER);
			cell.setNoWrap(false);
			cell.setColspan(4);
			mainTab.addCell(cell);
			
			String str = "";
			char ch = mealTitle.charAt(0);
			if(ch == 'L')
				str = "*L : Lunch";
			else if(ch == 'S')
				str = "*S : Snaks";
			else if(ch == 'D')
				str = "*D : Dinner";
			else if(ch == 'B')
				str = "*B : Break Fast";
			
			if(!str.equals("")) {
				cell = new PdfPCell(new Phrase(str, regular));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setBorder(Rectangle.NO_BORDER);
				cell.setNoWrap(false);
				cell.setColspan(4);
				mainTab.addCell(cell);
			}
			
			cell = new PdfPCell(new Phrase("", heading));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(30f);
			cell.setNoWrap(false);
			cell.setColspan(4);
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase(" _________________________\n \n     Dietician(Sign. & Date)", regular));
		    cell.setBorder(Rectangle.NO_BORDER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setColspan(2);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
			    
		    cell = new PdfPCell(new Phrase(" _________________________\n \n    Approved by (Sign. & Date)", regular));
		    cell.setBorder(Rectangle.NO_BORDER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setColspan(2);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("", heading));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(15f);
			cell.setNoWrap(false);
			cell.setColspan(4);
			mainTab.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Assingned by PI/CI (Sign. & Date):  ", regular));
			cell.setBorder(Rectangle.NO_BORDER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setColspan(2);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase("_________________________", regular)); 
		    cell.setBorder(Rectangle.NO_BORDER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setColspan(2);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
			  
			mainTab.setHeaderRows(2);
			document.add(mainTab);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

}
