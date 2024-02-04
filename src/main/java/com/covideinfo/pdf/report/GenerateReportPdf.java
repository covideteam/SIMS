package com.covideinfo.pdf.report;
/*package com.covideinfo.pdf.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.covideinfo.dao.PDFReportDao;
import com.covideinfo.dto.StudyActivityDataDetailsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.service.DefaultActivitysService;
import com.covideinfo.service.PDFReportService;

public class GenerateReportPdf {
	@Autowired
	DefaultActivitysService defaultActivitysService;

	@SuppressWarnings("unlikely-arg-type")
	public String generateReport(HttpServletResponse response, HttpServletRequest request, StudyActivityData sad,
			GlobalActivity ga, List<StudyActivityDataDetailsDto> studydata,
			PDFReportService pdfReportService, PDFReportDao pdfReportDao) throws DocumentException, MalformedURLException, IOException {
		
		String unchkrdPath = request.getServletContext().getRealPath("/static/images/radioUncheck.png");
//		String chkrdPath = request.getServletContext().getRealPath("/static/images/radiochecked.png");
		String chkrdPath = request.getServletContext().getRealPath("/static/images/radiobtnChecked8.png");
		String unchkcbPath = request.getServletContext().getRealPath("/static/images/uncheckedCB.png");
		String chkcbPath = request.getServletContext().getRealPath("/static/images/checkedCB.png");
		String img = request.getServletContext().getRealPath("/static/images/Logo.png");
		response.setContentType("application/pdf");
		// OutputStream out = response.getOutputStream();
		String realPath = request.getServletContext().getRealPath("/");
		String path = realPath + "/PdfFiles/SinglePdf/";
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		String fileName = path + sad.getId() + "_" + sad.getId() + ".pdf";
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
		document.addTitle("Activity Data");
		document.setPageSize(PageSize.A4);
		document.setMargins(10, 80, 50, 80);
		document.setMarginMirroring(false);
		HeaderAndFooter event1 = new HeaderAndFooter(img,sad.getSutydActivity().getSm().getProjectNo(),"priodNo");
		writer.setPageEvent(event1);
		document.open();
		Font bold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		Font heading = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
		PdfPCell cell = null;
		PdfPTable hstable = new PdfPTable(1);
		hstable.setWidthPercentage(95);
		String headStr = "Activity Head";
		

		cell = new PdfPCell(new Phrase(headStr.trim(), heading));
//		cell.setBorder(Rectangle.NO_BORDER);
		hstable.addCell(cell);
		
		document.add(hstable);
		
		for(StudyActivityDataDetailsDto data: studydata) {
			
			LanguageSpecificValueDetails langSpecific=null;
	        if(data.getGlobalValues()!=null) {
	        	 Locale locale = LocaleContextHolder.getLocale();
	   	         String lang=locale.getCountry();
	   	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
	   	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(data.getGlobalValues(),lanuage);
	        }

			if(data.getSaParameter().getParameterId().getGroups()!=null) {
				
				 
				 if (data.getSaParameter().getParameterId().getContentType().getCode().equals("RB")) {
					Phrase label = new Phrase(data.getParamName(), regular);
					 
					PdfPTable radTab = new PdfPTable(2);
					radTab.setWidthPercentage(95);
					PdfPCell radioCell = null;
					radioCell = new PdfPCell(label);
					radioCell.setBorder(Rectangle.BOX);
					radioCell.setNoWrap(false);
					radTab.addCell(radioCell);
					
				     String valLabel=data.getControleParametrData();
				     String val=data.getValue();
				     String temp[] = valLabel.split("##");
				     PdfPTable desc = new PdfPTable(2);
				     for (String s : temp) {
				     if (!val.trim().equals("") && val.trim().equals(s.trim())) {
				    	 
							Image radioimg = Image.getInstance(chkrdPath);
							// radioimg.setAbsolutePosition(10, 10);
							radioimg.scaleAbsolute(8, 8);
							Phrase labelv = new Phrase(s, regular);

							radioCell = new PdfPCell(radioimg);
							radioCell.setNoWrap(false);
							radioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							radioCell.setBorder(Rectangle.NO_BORDER);
							desc.addCell(radioCell);

							radioCell = new PdfPCell(labelv);
							radioCell.setNoWrap(false);
							radioCell.setBorder(Rectangle.NO_BORDER);
							//radioCell.setColspan(2);
							desc.addCell(radioCell);
						} else {
							Image radioimg = Image.getInstance(unchkrdPath);
							// radioimg.setAbsolutePosition(10, 10);
							radioimg.scaleAbsolute(12, 12);
							Phrase labelv = new Phrase(s, regular);

							radioCell = new PdfPCell(radioimg);
							radioCell.setNoWrap(false);
							radioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							radioCell.setBorder(Rectangle.NO_BORDER);
							desc.addCell(radioCell);

							radioCell = new PdfPCell(labelv);
							radioCell.setNoWrap(false);
							radioCell.setBorder(Rectangle.NO_BORDER);
							//radioCell.setColspan(2);
							desc.addCell(radioCell);
						}
				       
				     }
				     cell = new PdfPCell(desc);
						cell.setBorder(Rectangle.BOX);
						radTab.addCell(cell);
						document.add(radTab);
				 }
				 if (data.getSaParameter().getParameterId().getContentType().getCode().equals("CB")) {
						Phrase label = new Phrase(data.getParamName(), regular);
						 
						PdfPTable radTab = new PdfPTable(2);
						radTab.setWidthPercentage(95);
						PdfPCell radioCell = null;
						radioCell = new PdfPCell(label);
						radioCell.setBorder(Rectangle.BOX);
						radioCell.setNoWrap(false);
						radTab.addCell(radioCell);
						
					     String valLabel=data.getControleParametrData();
					     String val=data.getValue();
					     String temp[] = valLabel.split("##");
					     PdfPTable desc = new PdfPTable(2);
					     for (String s : temp) {
					     if (!val.trim().equals("") && val.trim().contains(s.trim())) {
					    	 
								Image radioimg = Image.getInstance(chkcbPath);
								// radioimg.setAbsolutePosition(10, 10);
								radioimg.scaleAbsolute(10, 10);
								Phrase labelv = new Phrase(s, regular);

								radioCell = new PdfPCell(radioimg);
								radioCell.setNoWrap(false);
								radioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								radioCell.setBorder(Rectangle.NO_BORDER);
								desc.addCell(radioCell);

								radioCell = new PdfPCell(labelv);
								radioCell.setNoWrap(false);
								radioCell.setBorder(Rectangle.NO_BORDER);
								//radioCell.setColspan(2);
								desc.addCell(radioCell);
							} else {
								Image radioimg = Image.getInstance(unchkcbPath);
								// radioimg.setAbsolutePosition(10, 10);
								radioimg.scaleAbsolute(10, 10);
								Phrase labelv = new Phrase(s, regular);

								radioCell = new PdfPCell(radioimg);
								radioCell.setNoWrap(false);
								radioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								radioCell.setBorder(Rectangle.NO_BORDER);
								desc.addCell(radioCell);

								radioCell = new PdfPCell(labelv);
								radioCell.setNoWrap(false);
								radioCell.setBorder(Rectangle.NO_BORDER);
								//radioCell.setColspan(2);
								desc.addCell(radioCell);
							}
					       
					     }
					     cell = new PdfPCell(desc);
							cell.setBorder(Rectangle.BOX);
							radTab.addCell(cell);
							document.add(radTab);
					 }
			}else {
				 Phrase label = new Phrase(data.getParamName(), regular);
			        Phrase value = new Phrase(data.getValue(), regular);
			        Phrase value2 = new Phrase("", regular);
					
					PdfPTable radTab = new PdfPTable(2);
					radTab.setWidthPercentage(95);
					PdfPCell radioCell = null;
					radioCell = new PdfPCell(label);
					radioCell.setNoWrap(false);
					radioCell.setBorder(Rectangle.BOX);
					radTab.addCell(radioCell);

					radioCell = new PdfPCell(value);
					radioCell.setNoWrap(false);
					radioCell.setBorder(Rectangle.BOX);
					radTab.addCell(radioCell);
					radioCell = new PdfPCell(value2);
					radioCell.setNoWrap(false);
					radioCell.setBorder(Rectangle.BOX);
					//radioCell.setColspan(2);
					radTab.addCell(radioCell);
					
					document.add(radTab);
			}
	       
		}
		
		document.close();
		return fileName;
	}
	
	
	

}
*/