package com.covideinfo.util;
import java.text.SimpleDateFormat;

import com.covideinfo.model.Projects;
import com.itextpdf.text.Chunk;
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

public class RedBorder extends PdfPageEventHelper {
	private String imgLoc;

	private Projects project;
	public RedBorder(String imagePath, Projects project) {
		this.imgLoc = imagePath;
		this.project = project;
	}
	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) {

		try {
			Image img = Image.getInstance(imgLoc);
			img.setAbsolutePosition(10, 760); 
            img.scaleAbsolute(100, 60);
            /*PdfPTable table = new PdfPTable(1);
            PdfPCell table_cell = new PdfPCell();
            table_cell.addElement(img);
            table_cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(table_cell);*/
            document.add(img);
            document.add( Chunk.NEWLINE );
            PdfPTable table2 = new PdfPTable(1);
            PdfPCell table_cell2 = null;
           // Font regular = new Font(FontFamily.HELVETICA, 12);
            Font bold = new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            Phrase p = new Phrase("CASE REPORT FORM ", bold);
            table_cell2= new PdfPCell(p);
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(table_cell2);
            document.add(table2);
            document.add( Chunk.NEWLINE );
            
            PdfPTable table3 = new PdfPTable(1);
            Font regular = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Phrase p1 = new Phrase("Project No. : "+project.getProjectNo(), regular);
            table_cell2 = new PdfPCell(p1);
            table_cell2.setBorder(Rectangle.NO_BORDER);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(table_cell2);
            document.add(table3);
            
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
		/*  595.0x842.0 (rot: 0 degrees)  -- A4  width and higtht in pixels
			1684.0x2384.0 (rot: 0 degrees)-- A1
		   842.0 x 1191  --> A3
		   1008.0x612.0 (rot: 180 degrees)  -> legal 180 degress(landscape) 
		 float  width = document.getPageSize().getWidth();
	     float  height = document.getPageSize().getHeight();
	     int x=0;
	     int y=0;
	     if(width == 1008.0 && height == 612.0) {
	    	   x=50; y=550; 
	       }else if( width == 595.0 && height == 842.0) {
	    	   x=50; y=770; 
	       }else {
	    	   x=25; y=2250; 
	       }
		
        PdfContentByte canvas = writer.getDirectContentUnder();
      //  Rectangle rect =  new Rectangle(577,825,18,15);
        Rectangle rect =  new Rectangle(565,730,30,25);//Right, Top, left, Bottom
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
//        Rectangle rect = document.getPageSize();
        rect.setBorder(Rectangle.BOX); // left, right, top, bottom border
        rect.setBorderWidth(5); // a width of 5 user units
        rect.setBorderColor(BaseColor.BLACK); // a red border
        rect.setUseVariableBorders(true); // the full width will be visible
        canvas.rectangle(rect);*/
		try {
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 110, 30, 0);
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 550, 30, 0);
		}catch(Exception e) {
			e.printStackTrace();
		}
       /* ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("http://www.xxxx-your_example.com/"), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);*/
		
		
    }

}