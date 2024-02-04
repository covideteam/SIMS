import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.aspectj.apache.bcel.classfile.Field;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdfDocument {

    public static void main(String... args) throws DocumentException, MalformedURLException, IOException {
    	File f = new File("C:\\Users\\durgarao.mendi.ADVITYGLOBAL\\Desktop\\HeaderFooter.pdf");
    	FileOutputStream fout = new FileOutputStream(f);
    	
        // create document
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 90, 36);
        PdfWriter writer = PdfWriter.getInstance(document, fout);
        String imgLocation = "C:\\Users\\durgarao.mendi.ADVITYGLOBAL\\Desktop\\company.PNG";
        // add header and footer
//        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
//        writer.setPageEvent(event);

        // write to document
        document.open();
        Image img = Image.getInstance(imgLocation);
		img.setAbsolutePosition(40, 540); 
	    img.scaleAbsolute(80, 30);
	    document.add(img);
        document.add(new Paragraph("Adding a header to PDF Document using iText."));
        document.newPage();
        document.add(new Paragraph("Adding a footer to PDF Document using iText."));
        document.close();
        System.out.println(f.getAbsolutePath());
    }
}