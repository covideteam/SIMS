package com.covideinfo.pdf.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.DataCrfDtoDetails;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserMaster;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooterForAdvCoverPage extends PdfPageEventHelper {
	
	private String imgLoc;
	private String studyName;
	private String periodNo;
	private UserMaster user;
	private String dateStr;
	private Font regular;
	private Font heading;
	private StudyVolunteerReporting svr;
	private StudySubjects subject;
	private MessageSource messageSource;
	private String dateStrWithTime;
	private Locale currentLocale;
	private Font maiHeading;
	
	public HeaderAndFooterForAdvCoverPage(String imagePath, String studyName, String periodNo, UserMaster user, String dateStr, Font regular, Font heading, StudyVolunteerReporting svr, MessageSource messageSource, Locale currentLocale, Font mainHeading,
			DataCrfDtoDetails dcdDto, String dateStrWithTime) {
		this.imgLoc = imagePath;
		this.studyName = studyName;
		this.periodNo = periodNo;
		this.user = user;
		this.dateStr = dateStr;
		this.regular = regular;
		this.heading = heading;
		this.messageSource = messageSource;
		this.currentLocale = currentLocale;
		this.maiHeading = mainHeading;
		this.dateStrWithTime = dateStrWithTime;
		
		this.svr = svr;
		if(dcdDto != null && dcdDto.getSubMap() != null && dcdDto.getSubMap().size() > 0)
			this.subject = dcdDto.getSubMap().get(svr.getId());
		
	}
	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) {
		try {
			//For A4 
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(40, 795); 
            img.scaleAbsolute(116, 30);
            document.add(img);
            
			//For A4 Rotation
			/*Image img2 = Image.getInstance(imgLoc);
			img2.setAbsolutePosition(200, 510); 
            img2.scaleAbsolute(100, 60);*/
			
			//Legal rotate
			/*Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(293, 530); 
            img.scaleAbsolute(100, 60);
            document.add(img);*/
            
            
            PdfPCell table_cell2 = null;
            
            PdfPTable sptable = new PdfPTable(1);
            sptable.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase(""));
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(40f);
            sptable.addCell(table_cell2);
            document.add(sptable);
            
            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(95f);
           // Font regular = new Font(FontFamily.HELVETICA, 12);
            Font bold = new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            String caseForm = messageSource.getMessage("label.avanHeader.caseForm", null,currentLocale);
            Phrase p = new Phrase(caseForm+" ", maiHeading);
            table_cell2= new PdfPCell(p);
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(table_cell2);
            document.add(table2);
             
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase(""));
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(40f);
            table.addCell(table_cell2);
            document.add(table);
      	}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
		try {
//			String confidentialStr =messageSource.getMessage("label.avanFooter.confidential", null,currentLocale);
			String genStr =messageSource.getMessage("label.avanFooter.genby", null,currentLocale);
//			String gendateStr = messageSource.getMessage("label.avanFooter.genDate", null,currentLocale);
			
			SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
			String date = sdf.format(new Date());
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(confidentialStr, regular), 40, 50, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(genStr+"   : "+user.getFullName() + " at " +date, regular), 40, 50, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("* This document is an electronically authenticated at IST (+5:30 GMT).", regular), 40, 30, 0);
		}catch(Exception e) {
			e.printStackTrace();
		}	
    }


}
