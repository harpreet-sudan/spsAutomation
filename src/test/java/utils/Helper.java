package utils;

import java.io.File;

import org.apache.commons.lang3.RandomStringUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;

public class Helper extends BaseClass {
	private AutoItX autoHelper;

	private final ConfigReader configReader = ConfigReader.getConfigReader();
	private final Screen screen = new Screen();

	public void readNotes() throws FindFailed, InterruptedException {
		// Read Notes
		
		Thread.sleep(3000);
		System.out.println("inside helper base path is " + basePath);
		Pattern pendingStatus = new Pattern(basePath + configReader.inputParams.get("PendingImagePath"));
//		Pattern cancelledStatus = new Pattern(configReader.inputParams.get("cancelledImagePath"));
		if (screen.exists(pendingStatus) != null) {
			// Pattern pendingImage = new
			// Pattern(basePath+configReader.inputParams.get("PendingImagePath"));
//				autoHelper.controlClick("Modify Order", "", "[CLASS:TButton; INSTANCE:1]");	
			screen.wait(pendingStatus.similar((float) 0.90), 5).doubleClick();
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "",
					configReader.inputParams.get("ReadNotesButton"));
			Thread.sleep(2000);
			autoitObj.controlClick(configReader.inputParams.get("OrderNoteTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Thread.sleep(2000);
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Assert.assertTrue((screen.exists(configReader.inputParams.get("ReadNotesImagePath")) != null));

			// Status = "OUTSTANDING";

		} else {

			Assert.assertTrue((screen.exists(configReader.inputParams.get("readnotesGrid")) != null));
			// read notes again
			Pattern readnotesgrid = new Pattern(configReader.inputParams.get("readnotesGrid"));
			screen.wait(readnotesgrid.similar((float) 0.90), 5).doubleClick();
			Thread.sleep(3000);

			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "",
					configReader.inputParams.get("ReadNotesButton"));
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("OrderNoteTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Thread.sleep(3000);
			// Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Assert.assertTrue((screen.exists(configReader.inputParams.get("ReadNotesImagePath")) != null));

		}

	}

	@Test
	public void modifyOrderLevelFour() throws Exception {
		Pattern UnpickedOrderTab = new Pattern(basePath + configReader.inputParams.get("UnpickedOrderTab"));
		screen.wait(UnpickedOrderTab.similar((float) 0.99), 5).click();
		Thread.sleep(3000);

		if (screen.exists(configReader.inputParams.get("OutstandingImagePath")) != null) {

			Pattern outstanding = new Pattern(configReader.inputParams.get("OutstandingImagePath"));
			screen.wait(outstanding.similar((float) 0.90), 5).doubleClick();
			Thread.sleep(3000);

		} else if (screen.exists(configReader.inputParams.get("completeOrderMenuPath")) != null) {

			Pattern outstanding = new Pattern(configReader.inputParams.get("completeOrderMenuPath"));
			screen.wait(outstanding.similar((float) 0.90), 5).doubleClick();
			Thread.sleep(3000);
		}

		autoHelper.controlClick(configReader.inputParams.get("OrderDetailTitle"), "",
				configReader.inputParams.get("ModifyOrderButton"));
		Thread.sleep(3000);

		autoHelper.winWaitActive(configReader.inputParams.get("ModifyOrderTitle"));
		autoHelper.send(configReader.inputParams.get("ShiftDownKey"), false);
		autoHelper.send(configReader.inputParams.get("CtrlDownKey"), false);
		// autoHelper.controlSend("Modify Order", "", "[CLASS:TButton;
		// INSTANCE:1]","{SHIFTDOWN}{CTRLDOWN}",true);
		Pattern restrictedbuttonImage = new Pattern(configReader.inputParams.get("restrictedbuttonImagePath"));
//		autoHelper.controlClick("Modify Order", "", "[CLASS:TButton; INSTANCE:1]");	
		try {
			screen.wait(restrictedbuttonImage.similar((float) 0.90), 5).click();
		} catch (FindFailed e) {
			e.printStackTrace();
		}
		autoHelper.send(configReader.inputParams.get("UpKeys"), false);
		autoHelper.controlClick(configReader.inputParams.get("PasswordDialog"), "",
				configReader.inputParams.get("PasswordTextbox"));
		Thread.sleep(3000);
		autoHelper.send(configReader.inputParams.get("F4Button"), false);
		Thread.sleep(3000);
		autoHelper.controlClick(configReader.inputParams.get("PasswordDialog"), "",
				configReader.inputParams.get("PasswordDialogOkButton"));
		Thread.sleep(3000);

//********Verify if status combo box is visible
		Boolean flag = autoHelper.controlCommandIsVisible(configReader.inputParams.get("ModifyOrderTitle"), "",
				configReader.inputParams.get("StatusComboBox"));
		Assert.assertTrue(flag);
		Thread.sleep(3000);
	}

	@Test
	public void discardOrder() throws FindFailed, InterruptedException {

		autoHelper.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "",
				configReader.inputParams.get("StatusComboBox"));
		screen.click(configReader.inputParams.get("DiscardedOrderStatus"));
		// discard the order

	}

//	@Test(dependsOnMethods = "sendPOAAndChangeToOutstanding")
	@Test
	public void printSCMLabels(String ratioPackPath) throws FindFailed, InterruptedException {
		System.out.println("inside helper base path is " + basePath);

		Pattern OrderBeingPickedTab = new Pattern(basePath + configReader.inputParams.get("OrdersBeingPicked"));
		screen.wait(OrderBeingPickedTab.similar((float) 0.99), 5).click();
		Thread.sleep(3000);

		Pattern UnpickedOrderTab = new Pattern(basePath + configReader.inputParams.get("UnpickedOrderTab"));

		screen.wait(UnpickedOrderTab.similar((float) 0.99), 5).click();
		Thread.sleep(2000);

//Assert that status changes to outstanding 
		Thread.sleep(2000);
		Assert.assertTrue((screen.exists(configReader.inputParams.get("OutstandingImagePath")) != null));

//click on outstanding image		
		Pattern outstanding = new Pattern(configReader.inputParams.get("OutstandingImagePath"));
		screen.wait(outstanding.similar((float) 0.90), 5).rightClick();
		Thread.sleep(3000);
//PrePrint SCM Labels		
		Pattern prePrintLabel = new Pattern(configReader.inputParams.get("prePrintLabelPath"));
		screen.wait(prePrintLabel.similar((float) 0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "",
				configReader.inputParams.get("PickByProductButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "",
				configReader.inputParams.get("PickByProductButton"));

//Select Ratio Pack based on your requirement. 
//Accent Ratio Pack is for partially filled order and
// FF Ratio Pack is for fully filled order		

		Pattern accentRatioPack = new Pattern(ratioPackPath);
		// Pattern accentRatioPack = new
		// Pattern(configReader.inputParams.get(str1+str2));
		screen.wait(accentRatioPack.similar((float) 0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("SelectRatioPackTitle"), "",
				configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "",
				configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		Boolean flag = autoitObj.controlCommandIsVisible(configReader.inputParams.get("ShipmentDetailsTitle"), "",
				configReader.inputParams.get("CarierDropdown"));
		System.out.println("Flag is" + flag);
		if (flag == true) {

			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("CarierDropdown"));
			Thread.sleep(3000);
			Pattern starTrack = new Pattern(configReader.inputParams.get("starTrackImagePath"));
			screen.wait(starTrack.similar((float) 0.90), 5).click();
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("ConsignmentNoteNumber"));
			Thread.sleep(3000);
			autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("ConsignmentNoteNumber"), "abc123");

			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("DocketNumber"));
			Thread.sleep(3000);
			autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("DocketNumber"), "abc123");

			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("ContainerReferenceNumber"));
			Thread.sleep(3000);
			autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("ContainerReferenceNumber"), "abc123");

			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",
					configReader.inputParams.get("UpdateButton"));
			Thread.sleep(3000);
		}

		autoitObj.send("printFile" + RandomStringUtils.randomAlphanumeric(4), false);
		Thread.sleep(3000);
		autoitObj.send("{Enter}", false);
		Thread.sleep(3000);

	}

//	@Test(dependsOnMethods = "printSCMLabels")
	public void virtualPick() throws FindFailed, InterruptedException {
		// click unpicked order tab
		if (screen.exists(configReader.inputParams.get("UnpickedOrderTabClicked")) != null) {
			Pattern UnpickedOrderTabClicked = new Pattern(
					basePath + configReader.inputParams.get("UnpickedOrderTabClicked"));
			screen.wait(UnpickedOrderTabClicked.similar((float) 0.99), 5).click();
		} else if (screen.exists(configReader.inputParams.get("UnpickedOrderTab")) != null) {

			Pattern UnpickedOrderTab = new Pattern(basePath + configReader.inputParams.get("UnpickedOrderTab"));
			screen.wait(UnpickedOrderTab.similar((float) 0.99), 5).click();
		}

//		Virtual Pick		
		Pattern outstanding = new Pattern(configReader.inputParams.get("OutstandingImagePath"));
		screen.wait(outstanding.similar((float) 0.90), 5).rightClick();
		Thread.sleep(3000);
		Pattern virtualPick = new Pattern(configReader.inputParams.get("virtualPickImagePath"));
		screen.wait(virtualPick.similar((float) 0.90), 5).click();
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("VirtualPickTitle"), "",
				configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);

		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "",
				configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);

		// check if the order is in partially filled tab or fully filled tab
		// check if the order is in partially filled
		Pattern partiallyFilledMenuButton = new Pattern(configReader.inputParams.get("partiallyFilledMenuButtonPath"));
		screen.wait(partiallyFilledMenuButton.similar((float) 0.99), 5).click();
		Thread.sleep(3000);

		// Check if status is partially filled
		if (screen.exists(configReader.inputParams.get("partiallyFilledOrderPath")) != null) {

			Pattern partiallyFilledOrder = new Pattern(configReader.inputParams.get("partiallyFilledOrderPath"));
			screen.wait(partiallyFilledOrder.similar((float) 0.90), 5).rightClick();
			Thread.sleep(3000);
		} else {
			// Check if status is fully filled
			Pattern FullyFilledTabButton = new Pattern(configReader.inputParams.get("FullyFilledTabButton"));
			screen.wait(FullyFilledTabButton.similar((float) 0.99), 5).click();
			Thread.sleep(3000);
			if (screen.exists(configReader.inputParams.get("FullyFilledStatus")) != null) {
				Pattern FullyFilledStatus = new Pattern(configReader.inputParams.get("FullyFilledStatus"));
				screen.wait(FullyFilledStatus.similar((float) 0.90), 5).rightClick();
				Thread.sleep(3000);

			}

			// Check if status is filled

		}
	}

}
