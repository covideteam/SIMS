package com.covideinfo.barcode;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
public class PrintBarcodeFile {
//	public static final String serviceName = "ZDesigner GX430t";
//	public static final String serviceName = "ZDesigner GC420t"; // 200DBI or
//	public static final String serviceName = "ZDesigner GC420t (EPL)"; // 200DBI
//	public static final String serviceName = "ZDesigner GC420t (EPL) (Copy 1)"; // 200DBI
	public static String serviceName= "SATO SA412 SZPL";
	
	public void printBarcode(String fileName){
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(fileName));
//			InputStream is = new BufferedInputStream(new FileInputStream("D:\\2d_Barcode.prn"));
			// Find the default service
//			DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST;
			DocFlavor flavor = new DocFlavor("application/octet-stream","java.io.InputStream");

			PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
			System.out.println("default printer : " + service1);
			System.out.println(service1.getName());
			PrintService service =  getPrintService(serviceName);
			System.out.println(serviceName);
			if(!serviceName.equals(service.getName())){
				System.out.println("'"+serviceName+"' print not avilabel");
				service = service1;
			}
			System.out.println(service.getName());
			// Create the print job
			DocPrintJob job = service.createPrintJob();

			Doc nic = new SimpleDoc(is, flavor, null);

			// Print it
			job.print(nic, null);

			// It is now safe to close the input stream
			is.close();
			
		} catch (PrintException e) {
			e.printStackTrace();
			System.out.println("'"+serviceName+"' print not avilabel Failed");
		}catch (IOException e) {
			e.printStackTrace();	
			
			System.out.println("Failed : file not found");
		}
	}
	public PrintService getPrintService(String printername) {
	    
	    if (printername == null) {
	        return PrintServiceLookup.lookupDefaultPrintService();       
	    } else {
	        
	        if ("(Show dialog)".equals(printername)) {
	            return null; // null means "you have to show the print dialog"
	        } else if ("(Default)".equals(printername)) {
	            return PrintServiceLookup.lookupDefaultPrintService(); 
	        } else {
	            PrintService[] pservices = 
	                    PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE , null);
	            for (PrintService s : pservices) {    
	                if (printername.equals(s.getName())) {
	                    return s;
	                }
	            }
	            return PrintServiceLookup.lookupDefaultPrintService();       
	        }                
	    }                 
	}
}
