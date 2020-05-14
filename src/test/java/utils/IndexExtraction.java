package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

public class IndexExtraction extends BaseClass {

	private final ConfigReader configReader = ConfigReader.getConfigReader();
	public int indexOfSearchString;
	public int indexOfSecondPlusAfterSearchString;
	public int indexOfThirdplusAfterSearchString;
	public int indexOfFirstPlusAfterSearchString;
	public int indexOfFirstColonAfterSearchString;
	public int indexOfFirstQuotationAfterSearchString;
	public int indexOfSecondColonAfterSearchString;
	public int indexOfFourPlusInDSA;
	public int indexofPlusBeforeFourPlusInDSA;
	public int indexOfSearchStringInFA;
	public int indexOfSearchStringInAperak;
	public int indexOfInvoicePlusInINV;
	public int indexofFirstPlusInINV;
	public int indexofSecondPlusInINV;
	//AMAZON
	public int indexOfsecondstarAfterSearchString;
	public int indexOfthirdstarAfterSearchString;
	public int indexOffourthstarAfterSearchString;

	@Test
	public void setTheIndexes(String sourceFile) throws IOException {

		if(Client.equals("AMAZON")) {
			Path sourceFilePath = Paths.get(sourceFile);
			String contentsOfSourceFile = new String(Files.readAllBytes(Paths.get(sourceFilePath.toFile().getPath())));
			// Below three indexes are for PO
			indexOfSearchString = contentsOfSourceFile.indexOf("BEG*");
			System.out.println("Index of B in BEG*"+indexOfSearchString);
			indexOfsecondstarAfterSearchString = contentsOfSourceFile.indexOf("*", indexOfSearchString + 4);
			System.out.println("Index of Second star after BEG*"+indexOfsecondstarAfterSearchString);
			indexOfthirdstarAfterSearchString = contentsOfSourceFile.indexOf("*", indexOfsecondstarAfterSearchString + 3);
			System.out.println("Index of Third star after Second star" + indexOfthirdstarAfterSearchString);
			indexOffourthstarAfterSearchString = contentsOfSourceFile.indexOf("*", indexOfthirdstarAfterSearchString + 3);
			System.out.println("Index of fourth star after third star" + indexOffourthstarAfterSearchString);

		}
		else if (Client.equals("ACCENT"))
		{
		if (sourceFile == configReader.inputParams.get("POOrderFilePath") || sourceFile.contains("DSA")
				|| (sourceFile.contains("INV") && !sourceFile.contains("APERAK"))
				|| sourceFile == PoOrderFilename.toString()) {
			Path sourceFilePath = Paths.get(sourceFile);
			String contentsOfSourceFile = new String(Files.readAllBytes(Paths.get(sourceFilePath.toFile().getPath())));
			// Below three indexes are for PO
			indexOfSearchString = contentsOfSourceFile.indexOf("BGM+");
			indexOfSecondPlusAfterSearchString = contentsOfSourceFile.indexOf("+", indexOfSearchString + 4);		
			indexOfThirdplusAfterSearchString = contentsOfSourceFile.indexOf("+",
					indexOfSecondPlusAfterSearchString + 1);
						
			// Below three indexes are from DSA
			indexOfFourPlusInDSA = contentsOfSourceFile.indexOf("++++");
			// System.out.println("indexOfFourPlusInDSA in DSA is "+indexOfFourPlusInDSA);
			indexofPlusBeforeFourPlusInDSA = contentsOfSourceFile.lastIndexOf("+", indexOfFourPlusInDSA - 1);
//			System.out.println("indexofPlusBeforeFourPlusInDSA in DSA is   "+indexofPlusBeforeFourPlusInDSA);

			// Below three indexes are from INV file
			indexOfInvoicePlusInINV = contentsOfSourceFile.indexOf("INVOICE+");
//			System.out.println("indexOfInvoicePlusInINV in INV is   "+indexOfInvoicePlusInINV);
			indexofFirstPlusInINV = contentsOfSourceFile.indexOf("+", indexOfInvoicePlusInINV);
			// System.out.println("indexofFirstPlusInINV in INV is "+indexofFirstPlusInINV);
			indexofSecondPlusInINV = contentsOfSourceFile.indexOf("+", indexofFirstPlusInINV + 1);
//			System.out.println("indexofSecondPlusInINV in INV is   "+indexofSecondPlusInINV);

		} else if ((sourceFile == configReader.inputParams.get("FAFilePath"))
				|| (sourceFile == configReader.inputParams.get("InvoiceFAFilePath"))) {
			Path sourceFilePath = Paths.get(sourceFile);
			String contentsOfSourceFile = new String(Files.readAllBytes(Paths.get(sourceFilePath.toFile().getPath())));
			indexOfSearchStringInFA = contentsOfSourceFile.indexOf("UCI+");
			// System.out.println("indexOfSearchStringInFA in DSA is
			// "+indexOfSearchStringInFA);
			indexOfFirstPlusAfterSearchString = contentsOfSourceFile.indexOf("+", indexOfSearchStringInFA);
//			System.out.println("indexOfFirstPlusAfterSearchString in FA is   "+indexOfFirstPlusAfterSearchString);
			indexOfSecondPlusAfterSearchString = contentsOfSourceFile.indexOf("+",
					indexOfFirstPlusAfterSearchString + 1);
			// System.out.println("indexOfSecondPlusAfterSearchString in FA is
			// "+indexOfSecondPlusAfterSearchString);
		} else if (sourceFile == configReader.inputParams.get("ASNFilePath")) {
			Path sourceFilePath = Paths.get(sourceFile);
			String contentsOfSourceFile = new String(Files.readAllBytes(Paths.get(sourceFilePath.toFile().getPath())));
			// System.out.println("contents of aperak file " +contentsOfSourceFile);
			indexOfSearchString = contentsOfSourceFile.indexOf("+AAK");
			// System.out.println("indexOfSearchString in aperak AAk+ is
			// "+indexOfSearchString);
			indexOfFirstColonAfterSearchString = contentsOfSourceFile.indexOf(":", indexOfSearchString);
			// System.out.println("indexOfFirstColonAfterSearchString in aperak AAk+ is
			// "+indexOfFirstColonAfterSearchString);
			indexOfFirstQuotationAfterSearchString = contentsOfSourceFile.indexOf("'",
					indexOfFirstColonAfterSearchString);
			// System.out.println("indexOfFirstQuotationAfterSearchString in aperak AAk+ is
			// "+indexOfFirstQuotationAfterSearchString);

		} else if (sourceFile == configReader.inputParams.get("InvoiceAperakPath")) {
			Path sourceFilePath = Paths.get(sourceFile);
			String contentsOfSourceFile = new String(Files.readAllBytes(Paths.get(sourceFilePath.toFile().getPath())));
			indexOfSearchString = contentsOfSourceFile.indexOf("+IV");
//			System.out.println("indexOfSearchString in aperak AAk+ is   "+indexOfSearchString);
			indexOfFirstColonAfterSearchString = contentsOfSourceFile.indexOf(":", indexOfSearchString + 1);
//			System.out.println("indexOfFirstColonAfterSearchString in aperak IV+ is   "+indexOfFirstColonAfterSearchString);
			indexOfSecondColonAfterSearchString = contentsOfSourceFile.indexOf(":",
					indexOfFirstColonAfterSearchString + 1);
//			System.out.println("indexOfSecondColonAfterSearchString in aperak IV+ is   "+indexOfSecondColonAfterSearchString);

		}

	}
	}
}
