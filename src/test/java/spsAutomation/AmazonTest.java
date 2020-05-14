package spsAutomation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autoitx4java.AutoItX;
import utils.BaseClass;
import utils.ConfigReader;
import utils.Helper;
import utils.RecentFileRetrieval;

@Test(groups = "AmazonTest")
public class AmazonTest extends BaseClass {
	private final ConfigReader configReader = ConfigReader.getConfigReader();

	SimpleDateFormat dnt = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();
	public static String Status = "";
	RecentFileRetrieval FileDataUpdate = new RecentFileRetrieval();
	public Helper helper = new Helper();

	@BeforeClass
	public void setupBefore() {

		try {

			Calendar calobj = Calendar.getInstance();
			calobj.add(Calendar.DAY_OF_MONTH, 30);
			date = calobj.getTime();
			System.out.println("date is++++++++++++" + dnt.format(date));
			Client = "AMAZON";

		} catch (Exception ex) {
			System.out.println("Exception is " + ex);

		}

	}

	@Test()
	public void openABSForAmazon() throws Exception {
		// Thread.sleep(4000);
		autoitObj.run(configReader.inputParams.get("app_url"), "", AutoItX.SW_MAXIMIZE);
		autoitObj.winWaitActive("Select Alias");
		autoitObj.controlClick("Select Alias", "", "TComboBox1");
		autoitObj.controlSend("Select Alias", "", "TComboBox1", "[DRAKES]");
		Thread.sleep(2000);
		autoitObj.controlClick("Select Alias", "", "TBitBtn2");
		Thread.sleep(3000);

	}

	@Test
	public void importAmazonOrder() throws InterruptedException, IOException {
		autoitObj.winWaitActive("Unpicked Orders");

//Updating last imported file.
//		Path importFile = Paths.get(configReader.inputParams.get("POOrderFilePath"));
		if (Paths.get(configReader.amazonInputParams.get("AmazonPOFilePath")).toFile().exists()) {
			// Update the filename if the file exists in import path

			System.out.println("the file exists");
			FileDataUpdate.updatePOFile();
			autoitObj.send(configReader.inputParams.get("file_menu"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.amazonInputParams.get("AmazonPOFilePath"), false);
			Thread.sleep(2000);
			autoitObj.send(configReader.inputParams.get("OpenButton"), false);

		} else {
			if (Paths.get(configReader.inputParams.get("ImportArchivePath")).toFile().isDirectory()) {
				if (new File(configReader.inputParams.get("ImportArchivePath")).list().length > 0) {
					// copy from archive to import path

					System.out.println("inside archive");

					// Provide the third parameter same as initials to the PO file
					FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("ImportArchivePath"),
							configReader.inputParams.get("ImportDirectoryPath"), "Ama");
					// update the name inside the PO file
					FileDataUpdate.updatePOFile();
					autoitObj.send(configReader.inputParams.get("file_menu"), false);
					Thread.sleep(2000);
					autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"), false);
					Thread.sleep(2000);
					autoitObj.send(PoOrderFilename.toString(), false);
					Thread.sleep(2000);
					autoitObj.send(configReader.inputParams.get("OpenButton"), false);
					Thread.sleep(3000);
				} else {
					System.out.println("There is no file in import archive folder");
				}

			} else {
				System.out.println("There is no archive directory");
			}

			System.out.println("There is no File in Import Path" + configReader.inputParams.get("POOrderFilePath"));

		}
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
				configReader.inputParams.get("OkButton"));
	}

	@Test()
	public void clickInsideAmazonGrid() throws FindFailed, InterruptedException {
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

	@Test
	public void changeStatusToPendingForAmazon() throws FindFailed, InterruptedException {
		Thread.sleep(3000);
		if (Status.contentEquals("CANCELLED")) {
			autoitObj.controlClick(configReader.amazonInputParams.get("OrderWindowTitleForAmazon"), "",
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
			autoitObj.controlClick(configReader.amazonInputParams.get("OrderWindowTitleForAmazon"), "",
					configReader.inputParams.get("UpdateButton"));

//Assert that status changes to pending after changing dates
			Assert.assertTrue((screen.exists(basePath + configReader.inputParams.get("PendingImagePath")) != null));
//Change status to PENDING
			Status = "PENDING";
			helper.readNotes(configReader.amazonInputParams.get("OrderWindowTitleForAmazon"));
		}

	}

	// @Test(dependsOnMethods = "changeStatusToPending")
	@Test
	public void sendPOAAndChangeToOutstandingForAmazon() throws FindFailed, InterruptedException {

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
				Assert.assertTrue((screen.exists(configReader.inputParams.get("OutstandingImagePath")) != null));

				Status = "OUTSANDING";
			}
		} else {
			System.out.println("Status is not PENDING to send POA" + Status);
		}
	}

//		@Test(dependsOnMethods = "sendPOAAndChangeToOutstanding")
	@Test
	public void stepsAfterOutstandingForAmazon() throws FindFailed, InterruptedException {
		helper.printSCMLabels(basePath + configReader.amazonInputParams.get("AmazonRp"),
				basePath + configReader.amazonInputParams.get("ShipmentOption"));
		helper.virtualPick();
	}

	@Test
	public void completeOrderForAmazon() throws FindFailed, InterruptedException {

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

	// @Test(dependsOnMethods = "waitingForASNAperak")
	@Test
	public void sendInvoiceForAmazon() throws FindFailed, InterruptedException {

		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
		screen.wait(completeOrderMenuButton.similar((float) 0.99), 5).click();
		Thread.sleep(3000);
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

	// @Test (dependsOnMethods = "invoiceAperak")
	@Test
	public void completeStatusVerifyForAmazon() throws FindFailed, InterruptedException {
		Assert.assertTrue((screen.exists(configReader.inputParams.get("completeStatus")) != null));
	}

	@AfterClass
	public void emptyBinForAmazon() throws Exception {
//		helper.modifyOrderLevelFour();
//		helper.discardOrder();
//
//		Thread.sleep(3000);

		if (autoitObj.winExists("[CLASS:TOrderListForm]")) {

			String handle = autoitObj.winGetHandle("[CLASS:TOrderListForm]");
			System.out.println("inside if");
			autoitObj.winClose(handle, "");
			autoitObj.winKill("[CLASS:TOrderListForm]");
			Thread.sleep(3000);
		}

	}

}
