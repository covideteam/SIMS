import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdfTable {

	public static void main(String[] args) {
		try {
			//get images
			String unchkrdPath = "C:\\Users\\durgarao.mendi\\Desktop\\uncheckedCB.png";
			//get images
//		    Image imgChecked = Image.GetInstance(unchkrdPath);
		    Image imgUnchecked = Image.getInstance(unchkrdPath);
//		    imgUnchecked.scaleAbsolute(9, 9);
		    imgUnchecked.scaleToFit(7, 7);
		    Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
			
		    String path ="C:\\Users\\durgarao.mendi\\Desktop\\RadioButtonChecking.pdf";
		   
		    Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		    document.open();
		    Paragraph p = new Paragraph();
		    Chunk chunkCheckBoxNo = new Chunk(imgUnchecked, 0, 0, true);
		    p.add(chunkCheckBoxNo); //add to paragraph

		    //add text - 'No'
		    /*Chunk textChk = new Chunk("No", regular);
		    p.add(textChk);
		    p.set(Element.ALIGN_TOP);
		    p.setLeading(0, 5);*/
		    p.add("No");
		    p.setFont(regular);
		    p.setAlignment(Element.ALIGN_TOP);
		    
		    
		    PdfPTable table = new PdfPTable(2);
		    table.setWidthPercentage(100f);
		    PdfPCell cell = null;
		    
		    cell = new PdfPCell(new Phrase("Dou you Continue", regular));
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    table.addCell(cell);
		    
		    cell = new PdfPCell();
		    cell.addElement(p);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setFixedHeight(25f);
		    table.addCell(cell);
		    document.add(table);
		    document.close();
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	


}
