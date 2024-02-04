package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DrugDispensingPdfDao;
import com.covideinfo.dto.DrugDispancingPdfDto;
import com.covideinfo.dto.SubjectRandomizationDto;
import com.covideinfo.dto.TreatmentsDto;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.UserMaster;
import com.covideinfo.pdf.report.HeaderFooterForDrugDispansingPdf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@Service("drugDispensingPdfService")
public class DrugDispensingPdfService implements com.covideinfo.service.DrugDispensingPdfService {
	
	@Autowired
	DrugDispensingPdfDao drugDispPdfDao;

	/**
	 * This method is used to getting study and periods information form database and placing into DrugDispancingPdfDto and this DTO is return to controller
	 */
	@Override
	public DrugDispancingPdfDto DrugDispancingPdfDtoDetails() {
		return drugDispPdfDao.DrugDispancingPdfDtoDetailsForDownlodPdf();
	}

	/**
	 * This method is getting information by using provided study and period details and getting total drug dispensing pdf generating required data and the drug dispensing pdf is generated
	 * by using this method we can download pdf
	 */
	@Override
	public void downloadDrugDispansingPdf(HttpServletRequest request, HttpServletResponse response, Long studyId, Long periodId, Long userId, String dateStrWithTime, String dateStr) {
		String fileName = "";
		DrugDispancingPdfDto drugDispDto = null;
		List<TreatmentsDto> treatmentList = null;
		Map<String, Long> treatmentMap = new HashMap<>();
        List<SubjectRandomizationDto> subRazList = null;
        List<SubjectRandomizationDto> finalsubRazList = new ArrayList<>();
        List<DosingInfoDetails> dinfdList = null;
        Map<String, DosingInfoDetails> dinfMap = new HashMap<>();
        UserMaster user = null;
       try {
			drugDispDto = drugDispPdfDao.getDrugDispancingPdfDetails(studyId, userId, periodId);
			if(drugDispDto != null) {
				treatmentList = drugDispDto.getTreatmentsList();
				subRazList = drugDispDto.getSubRazList();
				dinfdList = drugDispDto.getDinfdList();
				user = drugDispDto.getUser();
			}
			if(treatmentList != null && treatmentList.size() > 0) {
				for(TreatmentsDto tinf : treatmentList) {
					treatmentMap.put(tinf.getRandomizationCode(), tinf.getId());
				}
			} 
			if(subRazList != null && subRazList.size() > 0) {
				for(SubjectRandomizationDto srz : subRazList) {
					srz.setSubjNo(Integer.parseInt(srz.getSubjectNo()));
					finalsubRazList.add(srz);
				}
			}
			if(dinfdList != null && dinfdList.size() > 0) {
				for(DosingInfoDetails dinfd : dinfdList) {
					dinfMap.put(dinfd.getTinfo().getRandamizationCode(), dinfd);
				}
			}
			fileName = generateDownloadDrugDispansingPdf(request, response, studyId, periodId, userId, dateStrWithTime, dateStr, finalsubRazList, dinfMap, treatmentMap, user);
			if(!fileName.equals("")) {
				List<String> fileNames = new ArrayList<>();
				fileNames.add(fileName);
				String finalFile = generatePageNumbersToFile(request, response, fileNames);
				if(!finalFile.equals("")) {
					//File Downloading 
					 File downloadFile = new File(finalFile);
				     FileInputStream inStream = new FileInputStream(downloadFile);
				  // modifies response
			        response.setContentType("application/pdf");
			        response.setContentLength((int) downloadFile.length());
			        
			        // forces download
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			         
			        // obtains response's output stream
			        OutputStream outStream = response.getOutputStream();
			         
			        byte[] buffer = new byte[4096];
			        int bytesRead = -1;
			         
			        while ((bytesRead = inStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			         
			        inStream.close();
			        outStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unused")
	private String generateDownloadDrugDispansingPdf(HttpServletRequest request, HttpServletResponse response,
			Long studyId, Long userId, Long periodId, String dateStrWithTime, String dateStr, List<SubjectRandomizationDto> finalsubRazList, Map<String, DosingInfoDetails> dinfMap, Map<String, Long> treatmentMap, UserMaster user) {
		String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
		String realPath = request.getSession().getServletContext().getRealPath("/");
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
//		Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		Font regular = new Font(calibri.getFamily(), 9);
		Font heading = new Font(calibri.getFamily(), 9, Font.BOLD);
//		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.BLACK);
		Font subHeadingRed = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.RED);
//		BaseColor bgColor = new BaseColor(211, 211, 211); //#D3D3D3
		String filepath = "";
		PdfPTable table = null;
		PdfPCell cell = null;
		String path = "";
		File file = null;
		Document document = null;
		PdfWriter writer = null;
		HeaderFooterForDrugDispansingPdf hfdrugdp = null;
		try {
			path = realPath+"//DrugDispansePdf";
			file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/drugDispansingPdf.pdf";
			document = new Document();
			writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.addTitle("Drugn Dispansing PDF");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 70); //A4
			document.setMarginMirroring(false);
			hfdrugdp = new HeaderFooterForDrugDispansingPdf(advimg,user, regular, mainHeading, dateStrWithTime);
			writer.setPageEvent(hfdrugdp);
			document.open();
			Collections.sort(finalsubRazList);
			if(finalsubRazList != null && finalsubRazList.size() > 0) {
				if(dinfMap.size() > 0) {
					table = new PdfPTable(2);
					table.setWidthPercentage(100f);
					for(SubjectRandomizationDto srz : finalsubRazList) {
						DosingInfoDetails dinfd = dinfMap.get(srz.getRandomizationCode());
						if(dinfd != null) {
							for(int i=1; i<=2; i++) {
								PdfPTable table2 = new PdfPTable(3);
								table2.setWidthPercentage(100f);
								table2.setWidths(new int[]{30, 40, 30});
								
								cell = new PdfPCell(new Phrase("For Clinical Research Use Only", regular));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(3);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Study No:", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
								
							    if(dinfd != null)
							    	cell = new PdfPCell(new Phrase(dinfd.getStudy().getProjectNo(), heading));
							    else cell = new PdfPCell(new Phrase("", heading));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    PdfPTable subTab = new PdfPTable(1);
							    subTab.setWidthPercentage(100f);
							    
							    if(dinfd != null)
							    	cell = new PdfPCell(new Phrase(dinfd.getTinfo().getRandamizationCode(), mainHeading));
							    else cell = new PdfPCell(new Phrase("", heading));
							    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderWidth(3f);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(7f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    subTab.addCell(cell);
							    
							    cell = new PdfPCell(subTab);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(15f);
							    cell.setPaddingBottom(15f);
							    cell.setPaddingLeft(15f);
							    cell.setPaddingRight(15f);
							    cell.setNoWrap(false);
							    cell.setRowspan(4);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Period No :", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    int periodNo = 0;
							    if(dinfd != null)
							    	periodNo = srz.getPeriodNo();
							    String periodNoStr = "";
							    if(periodNo != 0 && periodNo < 10)
							    	periodNoStr = "0"+periodNo;
							    else periodNoStr = periodNo+"";
							    cell = new PdfPCell(new Phrase(periodNoStr, regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Subject No :", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase(srz.getSubjectNo(), heading));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("No. of Units:", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    if(dinfd != null)
							    	cell = new PdfPCell(new Phrase(dinfd.getNoOfUnits(), regular));
							    else cell = new PdfPCell(new Phrase("", heading));
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Name of IP:", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    if(dinfd != null)
							    	cell = new PdfPCell(new Phrase(dinfd.getNameOfIp(), regular));
							    else cell = new PdfPCell(new Phrase("", heading));
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(2);
							    table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Batch No:", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    if(dinfd != null)
							    	cell = new PdfPCell(new Phrase(dinfd.getBatchNo(), regular));
							    else cell = new PdfPCell(new Phrase("", heading));
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(2);
								table2.addCell(cell);
							    
							    cell = new PdfPCell(new Phrase("Expiry Date:", regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table2.addCell(cell);
							    
							    String expDate = "";
							    if(dinfd != null) {
							    	SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
							    	if(dinfd.getExpDate() != null)
							    		expDate = sdf.format(dinfd.getExpDate());
							    }
							    cell = new PdfPCell(new Phrase(expDate, regular));
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(2);
								table2.addCell(cell);
								
								cell = new PdfPCell(table2);
							    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setNoWrap(false);
							    table.addCell(cell);
							}
						}
					}
				}else {
					table = new PdfPTable(1);
					table.setWidthPercentage(100f);
					
					cell = new PdfPCell(new Phrase("Drug Dispansing Data Not Avaliable.", subHeadingRed));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setNoWrap(false);
				    table.addCell(cell);
			
				 }
			}else {
				table = new PdfPTable(1);
				table.setWidthPercentage(100f);
				 
			    cell = new PdfPCell(new Phrase("Subjects Randomization Not Done Yet.", subHeadingRed));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setNoWrap(false);
			    table.addCell(cell);
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generatePageNumbersToFile(HttpServletRequest request, HttpServletResponse response, List<String> fileNames)
			throws IOException, DocumentException {
		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		String filepath = "";
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String path = realPath+"//FinalDispancingPdf";
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/DispansingLabelsPdf.pdf";
//			response.setContentType("application/pdf");
//		    OutputStream out = response.getOutputStream();
		    Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	        document.open();
	        int totalPages = 0;
	        PdfContentByte cb = writer.getDirectContent();
			List<InputStream> inslist = new ArrayList<InputStream>();
			for (String st : fileNames) {
				InputStream stream = new FileInputStream(new File(st));
				inslist.add(stream);
				//For totalNo of pages
				PdfReader reader = new PdfReader(st);
				totalPages = totalPages + reader.getNumberOfPages();

			}
			
			int pageCount=1;
			if (inslist.size() > 0) {
				for (InputStream in : inslist) {
		            PdfReader reader = new PdfReader(in);
		            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		            	document.newPage();
		            	PdfNumber rotation = PdfPage.PORTRAIT;
	                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                int pageOrientation = reader.getPageRotation(i);
		                if ((pageOrientation != 0)) {
		                	rotation = PdfPage.LANDSCAPE;
		                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 550, 725, 90);
		        			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Page"+" " + pageCount + " "+"Of"+" " + totalPages, regular), 568, 753, 90);
		                }else {
		                	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 483, 50, 0);
		        			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Page"+" " + pageCount + " "+"Of"+" " + totalPages, regular), 510, 30,0);
		                }
		                //import the page from source pdf
		                PdfImportedPage page = writer.getImportedPage(reader, i);
		                cb.addTemplate(page, 0, 0);
		               pageCount++;
		            }
		        }
		        
//		        out.flush();
		        document.close();
//		        out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

}
