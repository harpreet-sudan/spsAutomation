package spsAutomation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;
import utils.BaseClass;
import utils.ConfigReader;
import utils.SikuliAutomation;
import utils.TestDataManager;
import utils.*;

public class ImportPoTest extends BaseClass {

	// static Logger log = Logger.getLogger(notepad.class.getName());

//	 AutoItX autoitObj ;
	static Logger log = Logger.getLogger(ImportPoTest.class.getName());
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	private String app_url = configReader.inputParams.get("app_url");
	TestDataManager testdata = new TestDataManager();
//	private Screen screen;
	public SikuliAutomation sikuliInstance;
//	private String basePath;
	SimpleDateFormat dnt = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();
	public static String Status = "";
	RecentFileRetrieval FileDataUpdate = new RecentFileRetrieval();
//	ABSHelper helper = new ABSHelper();
	public Helper helper = new Helper();

	/**
	 * i Returns if the JVM is 32 or 64 bit version
	 */

	@BeforeTest
	public void setupBefore() {

		try {

			Calendar calobj = Calendar.getInstance();
			calobj.add(Calendar.DAY_OF_MONTH, 30);
			date = calobj.getTime();
			System.out.println("date is++++++++++++" + dnt.format(date));

		} catch (Exception ex) {
			System.out.println("URISyntaxException in get sikuli Object " + ex);

		}

	}

	@Test()
	public void openABS() throws InterruptedException, FindFailed {
//		log.info("opening abspick");
//		log.info("this is app url"+app_url);
//		System.out.println("testdata"+testdata.read_property("CreateAssortment", "plan", "beginMonth"));

		autoitObj.run(app_url, "", AutoItX.SW_MAXIMIZE);

	}

	@Test()
	public void maximizeWindow() throws InterruptedException {

		autoitObj.winWaitActive("Unpicked Orders");

		if (autoitObj.winExists("Unpicked Orders")) {
			autoitObj.winSetState("Unpicked Orders", "", AutoItX.SW_MAXIMIZE);
		}

		Assert.assertEquals(autoitObj.winGetState("[CLASS:TOrderListForm]", ""), 47);
	}

	// @Test(dependsOnMethods = "openABS")
	@Test
	public void importOrder() throws InterruptedException, IOException {
		autoitObj.winWaitActive("Unpicked Orders");

//Updating last imported file.
//		Path importFile = Paths.get(configReader.inputParams.get("POOrderFilePath"));
		if (Paths.get(configReader.inputParams.get("POOrderFilePath")).toFile().exists()) {
			// Update the filename if the file exists in import path

			System.out.println("the file exists");
			FileDataUpdate.updatePOFile();
			autoitObj.send(configReader.inputParams.get("file_menu"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("POOrderFilePath"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("OpenButton"), false);

		} else {
			if (Paths.get(configReader.inputParams.get("ImportArchivePath")).toFile().isDirectory()) {
				if (new File(configReader.inputParams.get("ImportArchivePath")).list().length > 0) {
					// copy from archive to import path

					System.out.println("inside archive");

					// Provide the third parameter same as initials to the PO file
					FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("ImportArchivePath"),
							configReader.inputParams.get("ImportDirectoryPath"), "Acc");
					// update the name inside the PO file
					FileDataUpdate.updatePOFile();
					autoitObj.send(configReader.inputParams.get("file_menu"), false);
					Thread.sleep(2000);
					autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
					Thread.sleep(2000);
					autoitObj.send(PoOrderFilename.toString(), false);
					Thread.sleep(2000);
					autoitObj.send(configReader.inputParams.get("OpenButton"), false);
					Thread.sleep(2000);
				} else {
					System.out.println("There is no file in import archive folder");
				}

			} else {
				System.out.println("There is no archive directory");
			}

			System.out.println("There is no File in Import Path" + configReader.inputParams.get("POOrderFilePath"));

		}

		Thread.sleep(1000);
		// Assert.assertTrue(autoitObj.winExists("[CLASS:DirectUIHWND]"));

		// Assert.assertTrue(autoitObj.winExists(configReader.inputParams.get("InformationDialogTitle")));
		// Boolean flag1 =autoitObj.controlCommandIsVisible("Information", "", "[NAME:1
		// Order imported.]");
		// Assert.assertTrue(autoitObj.winExists("Importing Order Amendments"));
		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
				configReader.inputParams.get("OkButton"));

	}

	@Test()
	public void clickTheGrid() throws FindFailed, InterruptedException {
		Thread.sleep(3000);

		Pattern cancelledStatus = new Pattern(basePath + configReader.inputParams.get("cancelledImagePath"));
		Pattern pendingStatus = new Pattern(basePath + configReader.inputParams.get("PendingImagePath"));
		// change below image to outstanding
		Pattern outsandingStatus = new Pattern(configReader.inputParams.get("OutstandingImagePath"));
		//
		if (screen.exists(cancelledStatus) != null) {
			// change this to single click
			Status = "CANCELLED";
			screen.wait(cancelledStatus.similar((float) 0.90), 2).doubleClick();

		} else if (screen.exists(pendingStatus) != null) {
			// change this to single click
			Status = "PENDING";
			screen.wait(pendingStatus.similar((float) 0.90), 2).click();

		} else if (screen.exists(outsandingStatus) != null) {
			// change this to single click
			Status = "OUTSTANDING";
			screen.wait(outsandingStatus.similar((float) 0.90), 2).click();

		}
		Thread.sleep(2000);
		// Assert.assertTrue(autoitObj.winExists("BRAND PARTNER - Purchase Order
		// Details"));

	}

	// check the status and change to pending from cancelled.
	// @Test(dependsOnMethods = "clickTheGrid")
	@Test
	public void changeStatusToPending() throws FindFailed, InterruptedException {
		Thread.sleep(3000);
		if (Status.contentEquals("CANCELLED")) {
			autoitObj.controlClick(configReader.inputParams.get("OrderDetailTitle"), "",
					configReader.inputParams.get("ModifyOrderButton"));
			Thread.sleep(2000);

			autoitObj.winWaitActive(configReader.inputParams.get("ModifyOrderTitle"));
			autoitObj.send(configReader.inputParams.get("ShiftDownKey"), false);
			autoitObj.send(configReader.inputParams.get("CtrlDownKey"), false);

			Pattern restrictedbuttonImage = new Pattern(configReader.inputParams.get("restrictedbuttonImagePath"));

			try {
				screen.wait(restrictedbuttonImage.similar((float) 0.90), 5).click();
			} catch (FindFailed e) {
				e.printStackTrace();
			}

			autoitObj.send(configReader.inputParams.get("UpKeys"), false);
			autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "",
					configReader.inputParams.get("PasswordTextbox"));
			Thread.sleep(2000);

			autoitObj.send(configReader.inputParams.get("F4Button"), false);
			Thread.sleep(2000);

			autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "",
					configReader.inputParams.get("PasswordDialogOkButton"));
			Thread.sleep(2000);

//********Verify if status combo box is visible
			Boolean flag = autoitObj.controlCommandIsVisible(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("StatusComboBox"));
			Assert.assertTrue(flag);
			Thread.sleep(2000);

//********Edit the dates		
			autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("NotAfterDateBox"));
			Thread.sleep(2000);

			autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("NotAfterDateBox"), "right", 1);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("SelectAllMenuOption"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("BackSpaceKey"), false);
			Thread.sleep(2000);

			autoitObj.send(dnt.format(date), false);
			Thread.sleep(2000);
			autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("ShipByDate"));
			Thread.sleep(2000);
			autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("ShipByDate"), "right", 1);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("SelectAllMenuOption"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("BackSpaceKey"), false);
			Thread.sleep(2000);
			autoitObj.send(dnt.format(date), false);
			Thread.sleep(2000);

//Click update after changing dates 		
			autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "",
					configReader.inputParams.get("UpdateButton"));

//Assert that status changes to pending after changing dates
			Assert.assertTrue((screen.exists(basePath + configReader.inputParams.get("PendingImagePath")) != null));
//Change status to PENDING
			Status = "PENDING";
			helper.readNotes();
		}

	}

	// @Test(dependsOnMethods = "changeStatusToPending")
	@Test
	public void sendPOAAndChangeToOutstanding() throws FindFailed, InterruptedException {

		// there will be outstanding
		Pattern pendingStatus = new Pattern(basePath + configReader.inputParams.get("PendingImagePath"));
		if (screen.exists(pendingStatus) != null) {

			if (screen.exists(configReader.inputParams.get("W_Status_A")) != null) {
				Pattern pendingImage = new Pattern(basePath + configReader.inputParams.get("PendingImagePath"));
				screen.wait(pendingImage.similar((float) 0.90), 5).rightClick();

				// Send POA
				Pattern sendPOA = new Pattern(configReader.inputParams.get("SendPOAPath"));
				screen.wait(sendPOA.similar((float) 0.90), 5).click();
				Thread.sleep(2000);

				autoitObj.controlClick(configReader.inputParams.get("SendPurchaseOrderTitle"), "",
						configReader.inputParams.get("UpdateButton"));
				Thread.sleep(2000);

				Status = "OUTSANDING";
			}
		} else {
			System.out.println("Status is not PENDING to send POA" + Status);
		}
	}

//	@Test(dependsOnMethods = "sendPOAAndChangeToOutstanding")
	@Test
	public void stepsAfterOutstanding() throws FindFailed, InterruptedException {
		helper.printSCMLabels(configReader.inputParams.get("accentRatioPackImagePath"));
		helper.virtualPick();
	}

	@Test
	public void partShipOrder() throws Exception {

		Pattern PartShipOrder = new Pattern(basePath + configReader.inputParams.get("PartShipOrder"));
		if (screen.exists(PartShipOrder) != null) {

			screen.wait(PartShipOrder.similar((float) 0.99), 3).click();
			Thread.sleep(3000);

			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Thread.sleep(3000);

			helper.modifyOrderLevelFour();
			helper.discardOrder();
			// call waitingForFunctionalAck() this method after this

		}
	}

//	@Test(dependsOnMethods = "stepsAfterOutstanding")
	@Test
	public void rePickOrder() throws FindFailed, InterruptedException {

		if (screen.exists(configReader.inputParams.get("RePickOrder")) != null) {
			Pattern RePickOrder = new Pattern(configReader.inputParams.get("RePickOrder"));
			screen.wait(RePickOrder.similar((float) 0.90), 5).click();
			Thread.sleep(3000);

			autoitObj.controlClick(configReader.inputParams.get("RepickOptionsWindowTitle"), "",
					configReader.inputParams.get("ClearOrderClearCartonRepick"));
			Thread.sleep(3000);

			autoitObj.controlClick(configReader.inputParams.get("ConfirmTitle"), "",
					configReader.inputParams.get("ConfirmOkButton"));
			Thread.sleep(3000);

			autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "",
					configReader.inputParams.get("PasswordTextbox"));
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("F4Button"), false);
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "",
					configReader.inputParams.get("PasswordDialogOkButton"));
			Thread.sleep(3000);

			helper.printSCMLabels(configReader.inputParams.get("accentRatioPackImagePath"));
			helper.virtualPick();

			// call complete order() after this function.

		}
	}

	/// code for part ship will depend on virtual pick partially filled

//	@Test(dependsOnMethods = "rePickOrder")
	@Test
	public void completeOrder() throws FindFailed, InterruptedException {

		Pattern completeOrderMenu = new Pattern(configReader.inputParams.get("completeOrderMenuPath"));
		screen.wait(completeOrderMenu.similar((float) 0.90), 5).click();
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("ConfirmTitle"), "",
				configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
				configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
	}

	@Test
	public void waitingForFunctionalAck() throws FindFailed, InterruptedException, IOException {

		// Click the complete order button from top menu and
		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
		screen.wait(completeOrderMenuButton.similar((float) 0.99), 5).click();
		Thread.sleep(3000);

		// Check the status in complete order tab
		if (screen.exists(configReader.inputParams.get("WaitingFunctionalAck")) != null) {

			// Replace and Update Files
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("FAArchivePath"),
					configReader.inputParams.get("FADirectoryPath"), "con");
			// code for opening FA acknowledgement file will go here
			FileDataUpdate.updateASNAndInvoiceFAFileData(configReader.inputParams.get("sendDirectoryPath"),
					configReader.inputParams.get("FAFilePath"), "DSA");

			// steps of FA acknowledgement
			Pattern waitingFA = new Pattern(configReader.inputParams.get("WaitingFunctionalAck"));
			screen.wait(waitingFA.similar((float) 0.90), 5).click();
			Thread.sleep(2000);

			// Import FA from designed path
			autoitObj.send(configReader.inputParams.get("file_menu"), false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("FAFilePath"), false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("OpenButton"), false);
			Thread.sleep(3000);

			// click OK on information dialog
			autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
					configReader.inputParams.get("InformationOkButton"));
			Thread.sleep(3000);

			// keep the file from archive back to original folder with updated filename
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("FAArchivePath"),
					configReader.inputParams.get("FADirectoryPath"), "con");
		}

	}

	// @Test(dependsOnMethods = "waitingForFunctionalAck")
	@Test
	public void waitingForASNAperak() throws FindFailed, InterruptedException, IOException {

//				Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
//				screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
//				Thread.sleep(3000);

		// Check the status in complete order tab
		if (screen.exists(configReader.inputParams.get("WaitingASNPath")) != null) {

			// Replace and update file
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("AperakArchivePath"),
					configReader.inputParams.get("AperakDirectoryPath"), "APE");
			// Call the function to update ASN FA file
			FileDataUpdate.updateASNAperakFileData(configReader.inputParams.get("sendDirectoryPath"),
					configReader.inputParams.get("ASNFilePath"), "DSA");

			// assert that status changes to waiting for ASN aperak
			Assert.assertTrue((screen.exists(configReader.inputParams.get("WaitingASNPath")) != null));
			Thread.sleep(3000);
			// Import ASN aperak from designed path
			autoitObj.send(configReader.inputParams.get("file_menu"), false);
			// x.controlSend(title, text, control, string)
			// x.winMenuSelectItem("[CLASS:TOrderListForm]", "", "File", "Exit");
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ASNFilePath"), false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("OpenButton"), false);

			Thread.sleep(3000);
			//
			// click OK on information dialog
			autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
					configReader.inputParams.get("InformationOkButton"));
			Thread.sleep(3000);

			helper.readNotes();

			// keep the file from archive back to original folder with updated filename
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("AperakArchivePath"),
					configReader.inputParams.get("AperakDirectoryPath"), "APE");

		}
	}

	// @Test(dependsOnMethods = "waitingForASNAperak")
	@Test
	public void sendInvoice() throws FindFailed, InterruptedException {
		if (screen.exists(configReader.inputParams.get("InvoiceNeedsTobeSentPath")) != null) {
			// Send Invoice
			Thread.sleep(3000);
			Pattern InvoiceNeedsTobeSent = new Pattern(configReader.inputParams.get("InvoiceNeedsTobeSentPath"));
			screen.wait(InvoiceNeedsTobeSent.similar((float) 0.90), 5).rightClick();
			Thread.sleep(3000);

			Pattern senInvoice = new Pattern(configReader.inputParams.get("senInvoicePath"));
			screen.wait(senInvoice.similar((float) 0.90), 5).click();
			Thread.sleep(3000);

			// click Ok information dialog
			autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
					configReader.inputParams.get("InformationOkButton"));
			Thread.sleep(3000);
		}

	}

	// @Test(dependsOnMethods = "sendInvoice")
	@Test
	public void waitingInvoiceFunctionalAck() throws FindFailed, InterruptedException, IOException {
//				Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
//				screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
//				Thread.sleep(3000);
		// Assert that Waiting Invoice Functional Ac exists
		Assert.assertTrue((screen.exists(configReader.inputParams.get("waitingInvoiceFuncAck")) != null));
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceFAArchivePath"),
				configReader.inputParams.get("InvoiceFAPath"), "con");
		// CAll the function to update file data in invoice FA file
		FileDataUpdate.updateASNAndInvoiceFAFileData(configReader.inputParams.get("sendDirectoryPath"),
				configReader.inputParams.get("InvoiceFAFilePath"), "INV");

		// Steps for invoice functional ack

		// Import invoice FA from designed path
		autoitObj.send(configReader.inputParams.get("file_menu"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("InvoiceFAFilePath"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("OpenButton"), false);
		Thread.sleep(3000);

		// click OK on information dialog
		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
				configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);

		// keep the file from archive back to original folder with updated filename
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceFAArchivePath"),
				configReader.inputParams.get("InvoiceFAPath"), "con");

	}

	// @Test(dependsOnMethods = "waitingInvoiceFunctionalAck")
	@Test
	public void invoiceAperak() throws FindFailed, InterruptedException, IOException {
//				Pattern completeOrderMenu = new Pattern("C:\\Users\\Harpreet.Sudan\\Eclipse-workspace-Trial\\spsAutomation\\src\\test\\resources\\images\\completeOrderMenuButton.PNG");
//				screen.wait(completeOrderMenuButton.similar((float)0.90), 5).click();
//				Thread.sleep(3000);
		// Assert that Waiting Invoice Functional Ac exists
		Assert.assertTrue((screen.exists(configReader.inputParams.get("waitingForInvoiceAperak")) != null));
		//// CAll the function to update file data in invoice FA file
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceAperakArchivePath"),
				configReader.inputParams.get("InvoiceAperakDirectoryPath"), "INV_A");
		FileDataUpdate.updateInvoiceAperakFileData(configReader.inputParams.get("sendDirectoryPath"),
				configReader.inputParams.get("InvoiceAperakPath"), "INV");

		// Import invoice FA from designed path
		autoitObj.send(configReader.inputParams.get("file_menu"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("InvoiceAperakPath"), false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("OpenButton"), false);
		Thread.sleep(3000);
		// click OK on information dialog
		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
				configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);

		// keep the file from archive back to original folder with updated filename
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceAperakArchivePath"),
				configReader.inputParams.get("InvoiceAperakDirectoryPath"), "INV_A");

		helper.readNotes();
	}

	// @Test (dependsOnMethods = "invoiceAperak")
	@Test
	public void completeStatusVerify() throws FindFailed, InterruptedException {
		Assert.assertTrue((screen.exists(configReader.inputParams.get("completeStatus")) != null));
	}

	@AfterTest
	public void emptyBin() throws Exception {
		Pattern completeOrderMenu = new Pattern(configReader.inputParams.get("completeOrderMenuPath"));
		screen.wait(completeOrderMenu.similar((float) 0.90), 5).click();
		Thread.sleep(3000);
		Assert.assertTrue((screen.exists(configReader.inputParams.get("completeStatus")) != null));
		helper.modifyOrderLevelFour();
		helper.discardOrder();

		autoitObj.winClose("[CLASS:TOrderListForm]");
	}

}
