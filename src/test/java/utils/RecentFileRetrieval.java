package utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Optional;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.Test;



public class RecentFileRetrieval {
	public static File mostRecent;
	public String targetFileContents = "";
	
	public File TempMostFile;
	
	
	public static void fileRetrieval(String sourceFilePath,String targetCode) throws IOException {
	
	 Path sourDirPath = Paths.get(sourceFilePath);
		 
	 // Create a FilenameFilter 
     FilenameFilter filter = new FilenameFilter() { 

         public boolean accept(File f, String name) 
         { 
             return name.startsWith(targetCode); 
         } 
     }; 
     
     String[] files = sourDirPath.toFile().list(filter); 
     
 //    System.out.println("Files are:"); 

     // Display the names of the files 
     for (int i = 0; i < files.length; i++) { 
         System.out.println(files[i]); 
     } 
 //Below code finds the latest file with targetCode initials	 
		Optional<File> mostRecentFile =
	    Arrays
	        .stream(sourDirPath.toFile().listFiles(filter))
	        .filter(file -> file.isFile())
	        .max(
	            (f1, f2) -> Long.compare(f1.lastModified(),
	                f2.lastModified()));
		
		if (mostRecentFile.isPresent()) {
		    mostRecent = mostRecentFile.get();
		    System.out.println("most recent is " + mostRecent.getPath());
		} else {
		    System.out.println("folder is empty!");
		}
		
	}
	
	
	public void updateASNAndInvoiceFAFileData(String sourceFilePath,String targetFilePath,String targetCode) throws IOException 
	{
		
		RecentFileRetrieval.fileRetrieval(sourceFilePath,targetCode);
		
// Reading file into String in one line in JDK 7 
		String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath()))); 
		System.out.println("Contents (Java 7) : " + contents);
		//contents.subSequence(70, 75);
		String ValueToBeUpdated = contents.substring(70,75);
		System.out.println("Value retrieved  : " + ValueToBeUpdated);
		
//Update the target file		
		 Path pathOfTargetFile = Paths.get(targetFilePath);
	//	 String targetFileContents;
		 if (pathOfTargetFile==null) 
		 {
			    System.out.println("target File is empty .Name of the file is " +pathOfTargetFile.toString());
			}
		
		 else 
		 {
			 targetFileContents = new String(Files.readAllBytes(pathOfTargetFile)); 
			 System.out.println("targetFileContents : " + targetFileContents);
		
			 targetFileContents =  targetFileContents.substring(0, 105) + ValueToBeUpdated + targetFileContents.substring(110);
		
			 Files.write(pathOfTargetFile, targetFileContents.getBytes());
			
			////contents of target file
			System.out.println("contents of target file");
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		 }		
	}
	
	
	public void updateASNAperakFileData(String sourceFilePath,String targetFilePath,String targetCode) throws IOException 
	{
		RecentFileRetrieval.fileRetrieval(sourceFilePath,targetCode);
		
		String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath()))); 
//		System.out.println("Contents Of most recent file : " + contents);
		String ValueToBeUpdated = contents.substring(118,122);
		System.out.println("Value retrieved  : " + ValueToBeUpdated);
		
//Update the target file		
		 Path pathOfTargetFile = Paths.get(targetFilePath);
		 if (pathOfTargetFile==null) 
		 {
			    System.out.println("target File is empty .Name of the file is " +pathOfTargetFile.toString());
			}
		
		 else 
		 {
			String targetFileContents = new String(Files.readAllBytes(pathOfTargetFile)); 
//			System.out.println("targetFileContents original contents : " + targetFileContents);
			targetFileContents =  targetFileContents.substring(0, 191)
		            + ValueToBeUpdated
		            + targetFileContents.substring(195);
		
			Files.write(pathOfTargetFile, targetFileContents.getBytes());
			
			////contents of target file
			System.out.println("contents of target file after update");
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		 }
	}
	

	public void updateInvoiceAperakFileData(String sourceFilePath,String targetFilePath,String targetCode) throws IOException 
	{		
			RecentFileRetrieval.fileRetrieval(sourceFilePath,targetCode);
		
	//Reading file into String  
			String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath()))); 
	//		System.out.println("Contents Of most recent file : " + contents);
		
			String ValueToBeUpdated = contents.substring(132,134);
			System.out.println("Value retrieved  : " + ValueToBeUpdated);
			
	//Update the target file		
			// Path pathOfTargetFile = Paths.get(targetFilePath);
			 Path pathOfTargetFile = Paths.get(targetFilePath);
			 if (pathOfTargetFile==null) 
			 {
				    System.out.println("target File is empty .Name of the file is " +pathOfTargetFile.toString());
				}
			
			 else 
			 {
			String targetFileContents = new String(Files.readAllBytes(pathOfTargetFile)); 
	//		System.out.println("targetFileContents original contents : " + targetFileContents);
			targetFileContents =  targetFileContents.substring(0, 188)
		            + ValueToBeUpdated
		            + targetFileContents.substring(190);
		
			Files.write(pathOfTargetFile, targetFileContents.getBytes());
			
			////contents of target file
			System.out.println("contents of target file after update");
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		 }
	}

	@Test
	public void replaceAllFileAtSource(String sourcePath, String destinationPath, String fileInitials) throws IOException
	{
		RecentFileRetrieval.fileRetrieval(sourcePath,fileInitials);
		String updatedFilename = "";
//		Path sourcePath      = Paths.get("data/source.txt");
		if (mostRecent.exists()) {
			
			//Getting file extention and filename to update.
			 updatedFilename = mostRecent.getName();
			String ext = updatedFilename.substring(updatedFilename.lastIndexOf('.') + 1);
			//Remove tilde
			String extWithoutTilde = ext.substring(1);
		    String ediExt =extWithoutTilde.substring(0,3);
		    updatedFilename = updatedFilename.substring(0,updatedFilename.lastIndexOf('.'))+"."+ediExt;
		    System.out.println("temp file name "+updatedFilename);
		   
		} else {
		    System.out.println("There is no most recent file in the folder!");
		}
		
		Path destinationPathWithFilename = Paths.get(destinationPath +"\\"+ updatedFilename);
	//	System.out.println("Most recent filename is" +RecentFileRetrieval.mostRecent.toPath().getFileName());
	//	System.out.println(destinationPath);
		Files.copy(RecentFileRetrieval.mostRecent.toPath(),destinationPathWithFilename,StandardCopyOption.REPLACE_EXISTING);
	
		
		
	}
	
	}
