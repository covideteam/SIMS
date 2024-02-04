package com.covide.sorting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class MethodExecutionTimeings {
	public void generateExecutionFile(String type, String collectionType, String subjNo, String timePoint, String period, String realPath) {
		String fileName = "";
		String filePath = "";
		if(collectionType.equals("SampleCollection")) 
			fileName = realPath+"\\ExecutionTestingFiles";
		else if(collectionType.equals("VitalCollection")) 
			fileName = realPath+"\\ExecutionTestingFiles";
		else if(collectionType.equals("MealsCollection")) 
			fileName = realPath+"\\ExecutionTestingFiles";
		
        File file = new File(fileName);
        if(!file.exists()) 
        	file.mkdirs();
        if(collectionType.equals("SampleCollection")) 
        	filePath = fileName+"\\SampleCollection.txt";
        else if(collectionType.equals("VitalCollection")) 
        	filePath = fileName+"\\VitalCollection.txt";
        else if(collectionType.equals("MealsCollection")) 
        	filePath = fileName+"\\MealsCollection.txt";
        File file2 = new File(filePath);
		try {
			    if(!file2.exists()) {
			    	if(type.equals("start")) {
			    		FileWriter myWriter = new FileWriter(filePath);
			            myWriter.write("Execution Starts For Subject : "+subjNo+"  Period ID : "+period+"  Time Point ID : "+timePoint+"  Time :"+new Date());
			            myWriter.close();
			    	}else {
			    		 String fileContent = "\nExecution End For Subject : "+subjNo+"  Period ID : "+period+"  Time Point ID: "+timePoint+"  Time :"+new Date();
			        	 BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
			             out.write(fileContent);
			             out.close();
			    	}
		        	
		        }else {
		        	if(type.equals("start")) {
		        		FileWriter myWriter = new FileWriter(filePath);
			            myWriter.write("Execution Starts For Subject : "+subjNo+"  Period ID : "+period+"  Time Point ID : "+timePoint+"  Time :"+new Date());
			            myWriter.close();
		        	}else {
		        		 String fileContent = "\nExecution End For Subject : "+subjNo+"  Period ID : "+period+"  Time Point ID : "+timePoint+"  Time :"+new Date();
			        	 BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
			             out.write(fileContent);
			             out.close();
		        	}
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
