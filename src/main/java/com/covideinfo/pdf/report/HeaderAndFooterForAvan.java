package com.covideinfo.pdf.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.Projects;
import com.covideinfo.model.UserMaster;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooterForAvan extends PdfPageEventHelper {
	private String imgLoc;
	private String studyName;
	private String sponserCode;
	private UserMaster user;
	private String dateStr;
	private Font regular;
//	private Font heading;
	private Projects project;
	private ActivityDraftReviewAudit arAduit;
	private Font maiHeading;
	private Font subHeading;
	private String periodNo;
	private MessageSource messageSource;
	private Locale currentLocale;
	private String dateStrWithTime;
	
	public HeaderAndFooterForAvan(String imagePath, String studyName, String sponserCode, BlankPdfDto bpDto, String dateStr, Font regular, Font heading, Font mainHeading, Font subHeading, String periodNo, MessageSource messageSource, Locale currentLocale, String dateStrWithTime) {
		this.imgLoc = imagePath;
		this.studyName = studyName;
		this.sponserCode = sponserCode;
		this.user = bpDto.getUser();
		this.dateStr = dateStr;
		this.regular = regular;
//		this.heading = heading;
		this.project = bpDto.getProject();
		this.arAduit = bpDto.getAdrAudit();
		this.subHeading = subHeading;
		this.maiHeading = mainHeading;
		this.periodNo = periodNo;
		this.messageSource= messageSource;
		this.currentLocale = currentLocale;
		this.dateStrWithTime = dateStrWithTime;
	}
	
	 private PdfNumber rotation = PdfPage.PORTRAIT;
     public void setRotation(PdfNumber rotation) {
         this.rotation = rotation;
     }
	
	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
		try {
			//For A4 
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(40, 795); 
            img.scaleAbsolute(120, 26);
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
            
            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(95f);
           // Font regular = new Font(FontFamily.HELVETICA, 12);
//            Font bold = new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            String caseForm = messageSource.getMessage("label.avanHeader.caseForm", null,currentLocale);
            Phrase p = new Phrase(caseForm+" ", maiHeading);
            table_cell2= new PdfPCell(p);
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            table2.addCell(table_cell2);
            document.add(table2);
             
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase(""));
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            table_cell2.setFixedHeight(20f);
            table.addCell(table_cell2);
            document.add(table);
            
            String  headStr = "";
            PdfPCell cell = null;
    		PdfPTable hstable = new PdfPTable(2);
    		hstable.setWidthPercentage(100);
    		headStr =messageSource.getMessage("label.avanHeader.proNo", null,currentLocale);
    		headStr = headStr+" : " +studyName;
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//    		cell.setPadding(7f);
    		hstable.addCell(cell);
    		
    		headStr =messageSource.getMessage("label.avanHeader.subNo", null,currentLocale);
    		headStr = headStr+" : ";
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//    		cell.setPadding(7f);
    		hstable.addCell(cell);
    		
    		headStr =messageSource.getMessage("label.avanHeader.sponser", null,currentLocale);
    		headStr = headStr+" : ";
    		cell = new PdfPCell(new Phrase(headStr.trim()+" "+sponserCode, subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//    		cell.setPadding(7f);
    		hstable.addCell(cell);
    		
    		String date = "";
    		String versionheadStr =messageSource.getMessage("label.avanHeader.version", null,currentLocale);
			String periodNoheadStr =messageSource.getMessage("label.avanHeader.period", null,currentLocale);
			String dateHeadStr = messageSource.getMessage("label.avanHeader.date", null,currentLocale);
			String lastUpdateStr = messageSource.getMessage("label.avanHeader.lastudate", null,currentLocale);
    		if(!periodNo.equals("H")) {
    			if(project.getStatus().getStatusCode().equals("APPROVED")) {
    				if(arAduit != null)
        				date = sdf.format(arAduit.getDateOfActivity());
        			/*headStr = "Version : 01, Period No. : "+periodNo+" \nDate : "+date;*/
        			headStr = versionheadStr+" : 01, "+periodNoheadStr+" : "+periodNo+" \n"+dateHeadStr+" : "+date;
        		}else {
        			if(project.getUpdatedOn() != null)
        				date = sdf.format(project.getUpdatedOn());
        			/*headStr = "Version : 01, Period No. : "+periodNo+" \nLast updated on : "+date;*/
        			headStr = versionheadStr+" : 01, "+periodNoheadStr+" : "+periodNo+" \n"+lastUpdateStr+" : "+date;
        		}
    		}else {
    			if(project.getStatus().getStatusCode().equals("APPROVED")) {
        			if(arAduit != null)
        				date = sdf.format(arAduit.getDateOfActivity());
        			/*headStr = "Version : 01, \nDate : "+date;*/
        			headStr = versionheadStr+" : 01, "+" \n"+dateHeadStr+" : "+date;
        		}else {
        			if(project.getUpdatedOn() != null)
        				date = sdf.format(project.getUpdatedOn());
//        			headStr = "Version : 01, \nLast updated on : "+date;
        			headStr = versionheadStr+" : 01, "+" \n"+lastUpdateStr+" : "+date;
        		}
    		}
    		
    		
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//    		cell.setPadding(7f);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		hstable.addCell(cell);
    		document.add(hstable);
    		
    		 PdfPTable space = new PdfPTable(1);
    		 space.setWidthPercentage(95f);
             cell = new PdfPCell(new Phrase(""));
             cell.setBorder(Rectangle.NO_BORDER);
             cell.setFixedHeight(10f);
             space.addCell(cell);
             document.add(space);
      
      	}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
		try {
			String confidentialStr =messageSource.getMessage("label.avanFooter.confidential", null,currentLocale);
			String genStr =messageSource.getMessage("label.avanFooter.genby", null,currentLocale);
			String gendateStr = messageSource.getMessage("label.avanFooter.genDate", null,currentLocale);
			
//			Phrase phrase = new Phrase("Draft", new Font( FontFamily.TIMES_ROMAN, 110, Font.ITALIC, new GrayColor(0.85f)));
			String date = sdf.format(new Date());
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(confidentialStr, regular), 40, 50, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(genStr+"   : "+user.getFullName(), regular), 40, 30, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(gendateStr+" : "+date, regular), 250, 30, 0);
			writer.addPageDictEntry(PdfName.ROTATE, rotation);
			/* ColumnText.showTextAligned(writer.getDirectContentUnder(),
                      Element.ALIGN_CENTER, //Keep waterMark center aligned
                      phrase, 300f, 500f, 50f);// 45f is the rotation angle
*/		}catch(Exception e) {
			e.printStackTrace();
		}
  	
    }
	
}

