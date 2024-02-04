package com.covideinfo.pdf.report;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class HeaderAndFooter extends PdfPageEventHelper {
	private String imgLoc;
	private String studyName;
	private String periodNo;
	private UserMaster user;
	private String dateStr;
	private Font regular;
//	private Font heading;
	private Font mainHeading;
	private Font subHeading;
	private String dateStrWithTime;
	private PdfNumber rotation = PdfPage.PORTRAIT;
	
	public HeaderAndFooter(String imagePath, String studyName, String periodNo, UserMaster user, String dateStr, Font regular, Font heading, Font mainHeading, Font subHeading, String dateStrWithTime) {
		this.imgLoc = imagePath;
		this.studyName = studyName;
		this.periodNo = periodNo;
		this.user = user;
		this.dateStr = dateStr;
		this.regular = regular;
//		this.heading = heading;
		this.mainHeading = mainHeading;
		this.subHeading = subHeading;
		this.dateStrWithTime = dateStrWithTime;
	}
	 public PdfNumber getRotation() {
		return rotation;
	}
	public void setRotation(PdfNumber rotation) {
         this.rotation = rotation;
         
     }
	
	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) {
		try {
			//For A4 
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(40, 795); 
            img.scaleAbsolute(80, 30);
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
            Phrase p = new Phrase("CASE REPORT FORM ", mainHeading);
            table_cell2= new PdfPCell(p);
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(table_cell2);
            document.add(table2);
             
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase(""));
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(20f);
            table.addCell(table_cell2);
            document.add(table);
            
            PdfPCell cell = null;
    		PdfPTable hstable = new PdfPTable(4);
    		hstable.setWidthPercentage(100f);
    		String headStr = "Study No.: " +studyName;
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setPadding(7f);
    		cell.setNoWrap(false);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		hstable.addCell(cell);
    		
    		headStr = "Registration No. : ";
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setPadding(7f);
    		cell.setNoWrap(false);
    		hstable.addCell(cell);
    		
    		headStr = "Subject No. : ";
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setPadding(7f);
    		cell.setNoWrap(false);
    		hstable.addCell(cell);
    		
    		headStr = "Period. : "+periodNo;
    		cell = new PdfPCell(new Phrase(headStr.trim(), subHeading));
    		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		cell.setPadding(7f);
    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    		cell.setNoWrap(false);
    		hstable.addCell(cell);
    		document.add(hstable);
    		
    		 PdfPTable space = new PdfPTable(1);
    		 space.setWidthPercentage(95f);
             table_cell2 = new PdfPCell(new Phrase(""));
             table_cell2.setBorder(Rectangle.NO_BORDER);
             table_cell2.setFixedHeight(20f);
             space.addCell(table_cell2);
             document.add(space);
      
      	}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
			String date = sdf.format(new Date());
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 40, 70, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Generated By : "+user.getFullName() + " at "+date, regular), 40, 50, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("* This document is an electronically authenticated at IST (+5:30 GMT).", regular), 40, 30, 0);
			writer.addPageDictEntry(PdfName.ROTATE, rotation);
		}catch(Exception e) {
			e.printStackTrace();
		}	
    }
	
}

