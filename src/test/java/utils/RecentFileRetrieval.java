package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.Test;

public class RecentFileRetrieval extends BaseClass {
	public static File mostRecent;
	public String targetFileContents = "";
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	public File TempMostFile;
	IndexExtraction IndexExt = new IndexExtraction();

	public static void fileRetrieval(String sourceFilePath, String targetCode) throws IOException {

		Path sourDirPath = Paths.get(sourceFilePath);

		// Create a FilenameFilter
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File f, String name) {
				return name.startsWith(targetCode);
			}
		};

		String[] files = sourDirPath.toFile().list(filter);

		// System.out.println("Files are:");

		// Display the names of the files
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
		}
		// Below code finds the latest file with targetCode initials
		Optional<File> mostRecentFile = Arrays.stream(sourDirPath.toFile().listFiles(filter))
				.filter(file -> file.isFile()).max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

		if (mostRecentFile.isPresent()) {
			mostRecent = mostRecentFile.get();
			System.out.println("most recent is " + mostRecent.getPath());
		} else {
			System.out.println("folder is empty!");
		}

	}

	public void updateASNAndInvoiceFAFileData(String sourceFilePath, String targetFilePath, String targetCode)
			throws IOException

	{
		RecentFileRetrieval.fileRetrieval(sourceFilePath, targetCode);
		IndexExt.setTheIndexes(RecentFileRetrieval.mostRecent.getPath());

		// Reading file into String in one line in JDK 7
		String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath())));
		// System.out.println("Contents (Java 7) : " + contents);

		String ValueToBeUpdated = contents.substring(IndexExt.indexofPlusBeforeFourPlusInDSA + 1,
				IndexExt.indexOfFourPlusInDSA);
		System.out.println("Value retrieved  : " + ValueToBeUpdated);

		// Update the target file
		Path pathOfTargetFile = Paths.get(targetFilePath);
		// String targetFileContents;
		if (pathOfTargetFile == null) {
			System.out.println("target File is empty .Name of the file is ");
		}

		else {
			targetFileContents = new String(Files.readAllBytes(pathOfTargetFile));
			System.out.println("targetFileContents : " + targetFileContents);
			IndexExt.setTheIndexes(targetFilePath);
			String FirstPartOfTargetFile = targetFileContents.substring(0,
					IndexExt.indexOfFirstPlusAfterSearchString + 1);
			String SecondPartOfTargetFile = targetFileContents.substring(IndexExt.indexOfSecondPlusAfterSearchString);

			targetFileContents = FirstPartOfTargetFile + ValueToBeUpdated + SecondPartOfTargetFile;

			Files.write(pathOfTargetFile, targetFileContents.getBytes());

			//// contents of target file
			System.out.println("contents of target file" + targetFileContents);
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		}
	}

	public void updateASNAperakFileData(String sourceFilePath, String targetFilePath, String targetCode)
			throws IOException {
		RecentFileRetrieval.fileRetrieval(sourceFilePath, targetCode);
		IndexExt.setTheIndexes(RecentFileRetrieval.mostRecent.getPath());

		String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath())));
		// System.out.println("Contents Of most recent file : " + contents);
		String ValueToBeUpdated = contents.substring(IndexExt.indexOfSecondPlusAfterSearchString + 1,
				IndexExt.indexOfThirdplusAfterSearchString);
		System.out.println("Value retrieved  : " + ValueToBeUpdated);

		// Update the target file
		Path pathOfTargetFile = Paths.get(targetFilePath);
		if (pathOfTargetFile == null) {
			System.out.println("target File is empty .Name of the file is ");
		}

		else {
			String targetFileContents = new String(Files.readAllBytes(pathOfTargetFile));
			// System.out.println("targetFileContents original contents : " +
			// targetFileContents);
			IndexExt.setTheIndexes(targetFilePath);
			String FirstPartOfTargetFile = targetFileContents.substring(0,
					IndexExt.indexOfFirstColonAfterSearchString + 1);
			String SecondPartOfTargetFile = targetFileContents
					.substring(IndexExt.indexOfFirstQuotationAfterSearchString);

			targetFileContents = FirstPartOfTargetFile + ValueToBeUpdated + SecondPartOfTargetFile;

			Files.write(pathOfTargetFile, targetFileContents.getBytes());

			//// contents of target file
			System.out.println("contents of target file after update");
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		}
	}

	public void updateInvoiceAperakFileData(String sourceFilePath, String targetFilePath, String targetCode)
			throws IOException {

		RecentFileRetrieval.fileRetrieval(sourceFilePath, targetCode);
		IndexExt.setTheIndexes(RecentFileRetrieval.mostRecent.getPath());
		// Reading file into String
		String contents = new String(Files.readAllBytes(Paths.get(mostRecent.getPath())));
		// System.out.println("Contents Of most recent file : " + contents);

		String ValueToBeUpdated = contents.substring(IndexExt.indexofFirstPlusInINV + 1,
				IndexExt.indexofSecondPlusInINV);
		System.out.println("Value retrieved  : " + ValueToBeUpdated);

		// Update the target file
		// Path pathOfTargetFile = Paths.get(targetFilePath);
		Path pathOfTargetFile = Paths.get(targetFilePath);
		if (pathOfTargetFile == null) {
			System.out.println("target File is empty .Name of the file is ");
		}

		else {
			String targetFileContents = new String(Files.readAllBytes(pathOfTargetFile));
			IndexExt.setTheIndexes(targetFilePath);
			String FirstPartOfTargetFile = targetFileContents.substring(0,
					IndexExt.indexOfFirstColonAfterSearchString + 1);
			String SecondPartOfTargetFile = targetFileContents.substring(IndexExt.indexOfSecondColonAfterSearchString);

			targetFileContents = FirstPartOfTargetFile + ValueToBeUpdated + SecondPartOfTargetFile;

			// System.out.println("targetFileContents original contents : " +
			// targetFileContents);
//			targetFileContents = targetFileContents.substring(0, 188) + ValueToBeUpdated
//					+ targetFileContents.substring(190);

			Files.write(pathOfTargetFile, targetFileContents.getBytes());

			//// contents of target file
			System.out.println("contents of target file after update");
			Files.lines(pathOfTargetFile, StandardCharsets.UTF_8).forEach(System.out::println);
		}
	}

	public void replaceAllFileAtSource(String sourcePath, String destinationPath, String fileInitials)
			throws IOException {
		RecentFileRetrieval.fileRetrieval(sourcePath, fileInitials);
		String updatedFilename = "";
		// Path sourcePath = Paths.get("data/source.txt");
		if (mostRecent.exists()) {
			System.out.println("replaceAllFileAtSource+++++++++++++++++++++++ ");
			// Getting file extention and filename to update.
			updatedFilename = mostRecent.getName();
			String ext = updatedFilename.substring(updatedFilename.lastIndexOf('.') + 1);
			// Remove tilde
			String extWithoutTilde = ext.substring(1);
			String ediExt = extWithoutTilde.substring(0, 3);
			updatedFilename = updatedFilename.substring(0, updatedFilename.lastIndexOf('.')) + "." + ediExt;
			System.out.println("temp file name " + updatedFilename);

			System.out.println("replaceAllFileAtSource------------------------- ");

		} else {
			System.out.println("There is no most recent file in the folder!");
		}

		Path destinationPathWithFilename = Paths.get(destinationPath + "\\" + updatedFilename);
		PoOrderFilename = destinationPathWithFilename;
		System.out.println("Most recent filename is" + PoOrderFilename);
		// +RecentFileRetrieval.mostRecent.toPath().getFileName());
		// System.out.println(destinationPath);
		Files.copy(RecentFileRetrieval.mostRecent.toPath(), destinationPathWithFilename,
				StandardCopyOption.REPLACE_EXISTING);
		System.out.println("destinationPathWithFilename" + destinationPathWithFilename);

	}

	public void updatePOFile() throws IOException {
		// RecentFileRetrieval.fileRetrieval(sourcePath,fileInitials);
		Path PoOrderFilePath;
		if (Paths.get(configReader.inputParams.get("POOrderFilePath")).toFile().exists()) {
			IndexExt.setTheIndexes(configReader.inputParams.get("POOrderFilePath"));
			PoOrderFilePath = Paths.get(configReader.inputParams.get("POOrderFilePath"));
		} else {
			// this means file is not in import path and we need the file name from archive
			// if it exists.
			// Reading PO file into String

			IndexExt.setTheIndexes(PoOrderFilename.toString());
			PoOrderFilePath = PoOrderFilename;
		}

		String contents = new String(Files.readAllBytes(Paths.get(PoOrderFilePath.toFile().getPath())));

		String filename = contents.substring(IndexExt.indexOfSecondPlusAfterSearchString + 1,
				IndexExt.indexOfThirdplusAfterSearchString);

		// search if the filename has any numeric value in it. For example filename =
		// "abc123". below code should return 123
		String patternStr = "[0-9]";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {

//			System.out.println("index of first digit" + matcher.start());// this will give you index

//			System.out.println("filename having only numbers" + filename.substring(matcher.start()));
			String onlynumberfromfilename = filename.substring(matcher.start());

			if (onlynumberfromfilename != null) {
				int valueToIncrement = Integer.parseInt(onlynumberfromfilename);
				valueToIncrement = valueToIncrement + 1;
				String charSubstringOfFilename = filename.substring(0, matcher.start());
				filename = charSubstringOfFilename + Integer.toString(valueToIncrement);
//				System.out.println("filename updated" + filename);// this will give you index
			}
		} else {
//			System.out.println("No numbers in the string");
			filename = filename + "01";
			// System.out.println("filename length" +filename.length() + "index of
			// filenema.lenght is "+(indexOfSecondplusAfterBGM+1+filename.length()));
		}

		// update the contents of PO with updated filename
		String FirstPartOfContents = contents.substring(0, IndexExt.indexOfSecondPlusAfterSearchString + 1);
		String LastPartOfContents = contents.substring(IndexExt.indexOfThirdplusAfterSearchString);
		contents = FirstPartOfContents + filename + LastPartOfContents;

		Files.write(PoOrderFilePath, contents.getBytes());

		// Update the target file
		// Path pathOfTargetFile = Paths.get(targetFilePath);

	}

}
