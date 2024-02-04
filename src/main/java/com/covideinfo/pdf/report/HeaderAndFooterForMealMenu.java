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
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooterForMealMenu extends PdfPageEventHelper {
	private String imgLoc;
	private String studyName;
	private UserMaster user;
	private String dateStrWithTime;
	private Font regular;
	private String mealTpNo;
	
	private PdfNumber rotation = PdfPage.PORTRAIT;
	
	public HeaderAndFooterForMealMenu(String imagePath, String studyName, UserMaster user,  Font regular, String dateStrWithTime, String mealTpNo) {
		this.imgLoc = imagePath;
		this.studyName = studyName;
		this.user = user;
		this.regular = regular;
		this.dateStrWithTime = dateStrWithTime;
		this.mealTpNo = mealTpNo;
	
	}
	public PdfNumber getRotation() {
		return rotation;
	}
	public void setRotation(PdfNumber rotation) {
		this.rotation = rotation;
	}
	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) {
		 PdfPCell table_cell2 = null;
		try {
			//For A4 
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(40, 795); 
            img.scaleAbsolute(80, 30);
            document.add(img);
            
            PdfPTable space2 = new PdfPTable(1);
            space2.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase(""));
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(50f);
            space2.addCell(table_cell2);
            document.add(space2);
           
            
            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(95f);
             
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase("Template Title : Meal Menu", regular));
//            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
            table_cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_cell2.setFixedHeight(20f);
            table_cell2.setFixedHeight(20f);
            table_cell2.setPaddingTop(3f);
            table_cell2.setPaddingBottom(7f);
            table_cell2.setPaddingLeft(7f);
            table_cell2.setPaddingRight(7f);
            table.addCell(table_cell2);
            
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase("SOP Ref. No.: CL-023", regular));
//            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
            table_cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_cell2.setFixedHeight(20f);
            table_cell2.setFixedHeight(20f);
            table_cell2.setPaddingTop(3f);
            table_cell2.setPaddingBottom(7f);
            table_cell2.setPaddingLeft(7f);
            table_cell2.setPaddingRight(7f);
            table.addCell(table_cell2);
            
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase("Meal assigned for Study No.: "+studyName, regular));
            table_cell2.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
            table_cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(20f);
            table_cell2.setFixedHeight(20f);
            table_cell2.setPaddingTop(3f);
            table_cell2.setPaddingBottom(7f);
            table_cell2.setPaddingLeft(7f);
            table_cell2.setPaddingRight(7f);
            table.addCell(table_cell2);
            
            table.setWidthPercentage(95f);
            table_cell2 = new PdfPCell(new Phrase("Study Meal ID : "+mealTpNo, regular));
            table_cell2.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
            table_cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setFixedHeight(20f);
            table_cell2.setPaddingTop(3f);
            table_cell2.setPaddingBottom(7f);
            table_cell2.setPaddingLeft(7f);
            table_cell2.setPaddingRight(7f);
            table.addCell(table_cell2);
            document.add(table);
            
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
			/*PdfContentByte canvas = writer.getDirectContent();
			CMYKColor magentaColor = new CMYKColor(0.f, 1.f, 0.f, 0.f);
	        canvas.setColorStroke(magentaColor);
			canvas.moveTo(50960, 10);
			canvas.lineTo(0, 260);
		    canvas.closePathStroke();*/
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 40, 70, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Generated By : "+user.getFullName() + " at "+date, regular), 40, 50, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("* This document is an electronically authenticated at IST (+5:30 GMT).", regular), 40, 30, 0);
			writer.addPageDictEntry(PdfName.ROTATE, rotation);
		}catch(Exception e) {
			e.printStackTrace();
		}	
    }

}
