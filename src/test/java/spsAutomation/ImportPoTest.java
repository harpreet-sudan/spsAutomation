package spsAutomation;


import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;
import utils.BaseClass;
import utils.ConfigReader;
import utils.SikuliAutomation;
import utils.TestDataManager;
import utils.*;

public class ImportPoTest extends BaseClass{
	
	//static  Logger log = Logger.getLogger(notepad.class.getName());  
	   
	AutoItX autoitObj;
	static  Logger log = Logger.getLogger(ImportPoTest.class.getName()); 
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	private String app_url = configReader.inputParams.get("app_url");
	TestDataManager testdata=new TestDataManager();
	private Screen screen;
	public SikuliAutomation sikuliInstance;
	private String basePath;
	SimpleDateFormat dnt = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    RecentFileRetrieval FileDataUpdate = new RecentFileRetrieval();
	/**
	 *i
	 * Returns if the JVM is 32 or 64 bit version
	 */

	public static String jvmBitVersion() {
		return System.getProperty("sun.arch.data.model");
	}

	
	@BeforeTest
	public void setup() {
		String jacobDllVersionToUse;
		if (jvmBitVersion().contains("32")) {
			jacobDllVersionToUse = "jacob-1.19-x86.dll";
		} else {
			jacobDllVersionToUse = "jacob-1.19-x64.dll";
		}

		File file = new File("lib", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		autoitObj = new AutoItX();
		
		screen = new Screen();
		Calendar calobj = Calendar.getInstance();
		calobj.add(Calendar.DAY_OF_MONTH,30);
		date = calobj.getTime();
		
		System.out.println("date is++++++++++++"+dnt.format(date));
		
		try {
			
		        URL resourceFolderURL = this.getClass().getClassLoader().getResource("images");
		        System.out.println("url is-------" +resourceFolderURL);
		        basePath = resourceFolderURL.toURI().getPath() + "/";
		        
		        System.out.println("basePath is**********" +basePath);
		   
		}
		catch (Exception ex) {
         	System.out.println("URISyntaxException in get sikuli Object " +ex);   
         	
         }
		
		
	}

	@Test()
	public void openSps() throws InterruptedException {
//		log.info("opening abspick");
//		log.info("this is app url"+app_url);
//		System.out.println("testdata"+testdata.read_property("CreateAssortment", "plan", "beginMonth"));
		
	//	autoitObj.run(app_url);
		autoitObj.run(app_url,"",AutoItX.SW_MAXIMIZE);
		

//		if(autoitObj.winExists("Unpicked Orders"))
//				{
//			int winState = autoitObj.winGetState("[CLASS:TOrderListForm]","");	
//			System.out.println("state is" +winState);
//		}
//		String handle =  autoitObj.winGetHandle("[CLASS:TOrderListForm]");
	}

	
	@Test()
	public void maximizeWindow() throws InterruptedException {
	
		autoitObj.winWaitActive("Unpicked Orders");
		
		if(autoitObj.winExists("Unpicked Orders"))
				{
			autoitObj.winSetState("Unpicked Orders", "",  AutoItX.SW_MAXIMIZE);
			
		//	System.out.println("state is" +winState);
		}
		
		Assert.assertEquals(autoitObj.winGetState("[CLASS:TOrderListForm]",""), 47);
	}
	
	@Test
	public void modifyImportPath() throws InterruptedException {
		
		autoitObj.send(configReader.inputParams.get("ConfigMenu"),false);
		autoitObj.send(configReader.inputParams.get("ImportPathTableSubmenu"),false);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordTextbox"));
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("F4Button"),false);
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordDialogOkButton"));
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("ImportPathListDialogTitle"), "", configReader.inputParams.get("ModifyImportPathButton"));
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("ModifyImportPathDialogTitle"), "", configReader.inputParams.get("FilePathTextbox"));
		Thread.sleep(5000);
	//	autoitObj.mouseClick("right", 1, 5);
		autoitObj.controlClick(configReader.inputParams.get("ModifyImportPathDialogTitle"), "", configReader.inputParams.get("FilePathTextbox"),"right",1);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("SelectAllMenuOption"),false);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("BackSpaceKey"),false);
		Thread.sleep(5000);
//		String s = configReader.inputParams.get("ModifyImportPath");
//		System.out.println("path is  +++++"+s);
		autoitObj.send(configReader.inputParams.get("ModifyImportPath"),false);
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ModifyImportPathDialogTitle"), "", configReader.inputParams.get("UpdateButton_ModifyImportPath"));
	//	Thread.sleep(3000);
//		autoitObj.controlClick(configReader.inputParams.get("ImportPathListDialogTitle"), "", configReader.inputParams.get("ExitButton"));
		
		Assert.assertTrue(autoitObj.controlGetHandle(configReader.inputParams.get("ImportPathListDialogTitle"), "", configReader.inputParams.get("ExitButton"))!=null);
		autoitObj.controlClick(configReader.inputParams.get("ImportPathListDialogTitle"), "", configReader.inputParams.get("ExitButton"));
			
	}
	
	@Test
	public void deleteImportPath() throws InterruptedException {
		
		autoitObj.send(configReader.inputParams.get("ConfigMenu"),false);
		autoitObj.send(configReader.inputParams.get("ImportPathTableSubmenu"),false);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordTextbox"));
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("F4Button"),false);
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordDialogOkButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ImportPathListDialogTitle"), "", configReader.inputParams.get("DeleteImportPathButton"));
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("ConfirmDialogTitle"), "", configReader.inputParams.get("ConfirmOkButton"));
		Thread.sleep(5000);
	
	}
	
	
	
	
	
	@Test()
	public void importOrder() throws InterruptedException {
		autoitObj.winWaitActive("Unpicked Orders");
		autoitObj.send(configReader.inputParams.get("file_menu"),false);
	//	x.controlSend(title, text, control, string)
	//	x.winMenuSelectItem("[CLASS:TOrderListForm]", "", "File", "Exit");
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"),false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("POOrderFilePath"),false);
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("OpenButton"),false);
		
		
		Thread.sleep(3000);
	//	Assert.assertTrue(autoitObj.winExists("[CLASS:DirectUIHWND]"));
	
	//	Assert.assertTrue(autoitObj.winExists(configReader.inputParams.get("InformationDialogTitle")));
	//	Boolean flag1 =autoitObj.controlCommandIsVisible("Information", "", "[NAME:1 Order imported.]");
	//	Assert.assertTrue(autoitObj.winExists("Importing Order Amendments"));
	  autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("OkButton"));

			
	}
	
	@Test()
	public void clickTheGrid() throws FindFailed, InterruptedException  {
		Thread.sleep(5000);
		
		 Pattern capturedImage = new Pattern("C:\\Users\\Harpreet.Sudan\\Eclipse-workspace-Trial\\spsAutomation\\src\\test\\resources\\images\\cancelled.PNG");
	
		try{
			screen.wait(capturedImage.similar((float)0.90), 5).doubleClick();
			}
		catch(FindFailed e)
		{
            e.printStackTrace();
        }
		
		Assert.assertTrue(autoitObj.winExists("BRAND PARTNER - Purchase Order Details"));
		//}
//		ImagePath.setBundlePath(configReader.inputParams.get("SikuliImagePath"));
//		screen.findText("AccentPO539");
//		screen.click("newshipbyDate.png");
		}
	
	@Test()
	public void basicWorkflow() throws FindFailed, InterruptedException  {
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("OrderDetailTitle"), "", configReader.inputParams.get("ModifyOrderButton"));
		Thread.sleep(5000);
		
		autoitObj.winWaitActive(configReader.inputParams.get("ModifyOrderTitle"));
		autoitObj.send(configReader.inputParams.get("ShiftDownKey"),false);
		autoitObj.send(configReader.inputParams.get("CtrlDownKey"),false);
	//	autoitObj.controlSend("Modify Order", "", "[CLASS:TButton; INSTANCE:1]","{SHIFTDOWN}{CTRLDOWN}",true);
		Pattern restrictedbuttonImage = new Pattern(configReader.inputParams.get("restrictedbuttonImagePath"));
//		autoitObj.controlClick("Modify Order", "", "[CLASS:TButton; INSTANCE:1]");	
		try{
			screen.wait(restrictedbuttonImage.similar((float)0.90), 5).click();
			}
		catch(FindFailed e)
		{
            e.printStackTrace();
        }
		autoitObj.send(configReader.inputParams.get("UpKeys"),false);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordTextbox"));
		Thread.sleep(3000);
		autoitObj.send(configReader.inputParams.get("F4Button"),false);
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PasswordDialog"), "", configReader.inputParams.get("PasswordDialogOkButton"));
		Thread.sleep(3000);
		
//********Verify if status combo box is visible
		Boolean flag =autoitObj.controlCommandIsVisible(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("StatusComboBox"));
		Assert.assertTrue(flag);	
		Thread.sleep(5000);
		
//********Edit the dates		
		autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("NotAfterDateBox"));
		Thread.sleep(5000);

		autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("NotAfterDateBox"),"right",1);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("SelectAllMenuOption"),false);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("BackSpaceKey"),false);
		Thread.sleep(5000);
		
		autoitObj.send(dnt.format(date),false);
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("ShipByDate"));
		Thread.sleep(5000);
		autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("ShipByDate"),"right",1);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("SelectAllMenuOption"),false);
		Thread.sleep(5000);
		autoitObj.send(configReader.inputParams.get("BackSpaceKey"),false);
		Thread.sleep(5000);
		autoitObj.send(dnt.format(date),false);
		Thread.sleep(3000);
		
//Click update after changin dates 		
		autoitObj.controlClick(configReader.inputParams.get("ModifyOrderTitle"), "", configReader.inputParams.get("UpdateButton"));
		autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("UpdateButton"));
		
//Assert that status changes to pending after changing dates
		Assert.assertTrue((screen.exists(configReader.inputParams.get("PendingImagePath"))!=null));
		Pattern pendingImage = new Pattern(configReader.inputParams.get("PendingImagePath"));
//		autoitObj.controlClick("Modify Order", "", "[CLASS:TButton; INSTANCE:1]");	
		screen.wait(pendingImage.similar((float)0.90), 5).doubleClick();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("ReadNotesButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("OrderNoteTitle"), "", configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("UpdateButton"));
		Assert.assertTrue((screen.exists(configReader.inputParams.get("ReadNotesImagePath"))!=null));
		screen.wait(pendingImage.similar((float)0.90), 5).rightClick();
		Pattern sendPOA = new Pattern(configReader.inputParams.get("SendPOAPath"));
		screen.wait(sendPOA.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("SendPurchaseOrderTitle"), "", configReader.inputParams.get("UpdateButton"));
	
		Thread.sleep(3000);
		
	}
	
	@Test()
	public void outstandingAhead() throws FindFailed, InterruptedException  {
//Assert that status changes to outstanding 
		Assert.assertTrue((screen.exists(configReader.inputParams.get("OutstandingImagePath"))!=null));	
		
//click on outstanding image		
		Pattern outstanding = new Pattern(configReader.inputParams.get("OutstandingImagePath"));
		screen.wait(outstanding.similar((float)0.90), 5).rightClick();
		Thread.sleep(3000);
		Pattern prePrintLabel = new Pattern(configReader.inputParams.get("prePrintLabelPath"));
		screen.wait(prePrintLabel.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "", configReader.inputParams.get("PickByProductButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "", configReader.inputParams.get("PickByProductButton"));
		Pattern accentRatioPack = new Pattern(configReader.inputParams.get("accentRatioPackImagePath"));
		screen.wait(accentRatioPack.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("SelectRatioPackTitle"), "", configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("PrePrintSCMLabelsTitle"), "", configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "", configReader.inputParams.get("CarierDropdown"));
		Thread.sleep(3000);
		Pattern starTrack = new Pattern(configReader.inputParams.get("starTrackImagePath"));
		screen.wait(starTrack.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "", configReader.inputParams.get("ConsignmentNoteNumber"));
		Thread.sleep(3000);
		autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"),"", configReader.inputParams.get("ConsignmentNoteNumber"), "abc123");
	
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "", configReader.inputParams.get("DocketNumber"));
		Thread.sleep(3000);
		autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"),"", configReader.inputParams.get("DocketNumber"), "abc123");
	
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "", configReader.inputParams.get("ContainerReferenceNumber"));
		Thread.sleep(3000);
		autoitObj.controlSend(configReader.inputParams.get("ShipmentDetailsTitle"),"", configReader.inputParams.get("ContainerReferenceNumber"), "abc123");
	
		Thread.sleep(3000);
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "", configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		
		autoitObj.send("printFile"+RandomStringUtils.randomAlphanumeric(4),false);
		Thread.sleep(3000);
		autoitObj.send("{Enter}",false);
		Thread.sleep(3000);
		screen.wait(outstanding.similar((float)0.90), 5).rightClick();
		Thread.sleep(3000);

		
//Virtual Pick		
		Pattern virtualPick = new Pattern(configReader.inputParams.get("virtualPickImagePath"));
		screen.wait(virtualPick.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		
		autoitObj.controlClick(configReader.inputParams.get("VirtualPickTitle"), "", configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		
		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);
		
	}
	
	@Test
	public void partiallFilledAhead() throws FindFailed, InterruptedException  {
	
//Go for partially filled order and then complete the order		
		Pattern partiallyFilledMenuButton = new Pattern(configReader.inputParams.get("partiallyFilledMenuButtonPath"));
		screen.wait(partiallyFilledMenuButton.similar((float)0.99), 5).click();
		Thread.sleep(3000);
		
		Pattern partiallyFilledOrder = new Pattern(configReader.inputParams.get("partiallyFilledOrderPath"));
		screen.wait(partiallyFilledOrder.similar((float)0.90), 5).rightClick();
		Thread.sleep(3000);
		
		Pattern completeOrderMenu = new Pattern(configReader.inputParams.get("completeOrderMenuPath"));
		screen.wait(completeOrderMenu.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		
		autoitObj.controlClick(configReader.inputParams.get("ConfirmTitle"), "", configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);
		
		autoitObj.controlClick(configReader.inputParams.get("ShipmentDetailsTitle"), "",configReader.inputParams.get("UpdateButton"));
		Thread.sleep(3000);
		
		
	}
	
	@Test
	public void completeOrderAhead() throws FindFailed, InterruptedException, IOException   
	{
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("FAArchivePath"), configReader.inputParams.get("FADirectoryPath"), "con");
//code for opening FA acknowledgement file will go here
		FileDataUpdate.updateASNAndInvoiceFAFileData(configReader.inputParams.get("sendDirectoryPath"), configReader.inputParams.get("FAFilePath"), "DSA");
		
//Click the complete order button from top menu and 		
		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
		screen.wait(completeOrderMenuButton.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		
//steps of FA acknowledgement
		Pattern waitingFA = new Pattern(configReader.inputParams.get("WaitingFunctionalAck"));
		screen.wait(completeOrderMenuButton.similar((float)0.90), 5).rightClick();
		Thread.sleep(3000);
	//Import FA from designed path	
		    autoitObj.send(configReader.inputParams.get("file_menu"),false);
		//	x.controlSend(title, text, control, string)
		//	x.winMenuSelectItem("[CLASS:TOrderListForm]", "", "File", "Exit");
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"),false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("FAFilePath"),false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("OpenButton"),false);
			Thread.sleep(3000);
	//click OK on information dialog		
			autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
			Thread.sleep(3000);
			
			// keep the file from archive back to original folder with updated filename
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("FAArchivePath"), configReader.inputParams.get("FADirectoryPath"), "con");	
			
	}
	
	@Test
	public void waitingASNAhead() throws FindFailed, InterruptedException, IOException   
	{
//		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
//		screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
//		Thread.sleep(3000);
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("AperakArchivePath"), configReader.inputParams.get("AperakDirectoryPath"), "APE");
		//Call the function to update ASN FA file
		FileDataUpdate.updateASNAperakFileData(configReader.inputParams.get("sendDirectoryPath"), configReader.inputParams.get("ASNFilePath"), "DSA");
		
		
	//assert that status changes to waiting for ASN aperak
			Assert.assertTrue((screen.exists(configReader.inputParams.get("WaitingASNPath"))!=null));
			Thread.sleep(3000);
			//Import ASN aperak from designed path	
		    autoitObj.send(configReader.inputParams.get("file_menu"),false);
		//	x.controlSend(title, text, control, string)
		//	x.winMenuSelectItem("[CLASS:TOrderListForm]", "", "File", "Exit");
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"),false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("ASNFilePath"),false);
			Thread.sleep(3000);
			autoitObj.send(configReader.inputParams.get("OpenButton"),false);
			
			Thread.sleep(3000);
		//
	//click OK on information dialog		
			autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
			Thread.sleep(3000);
			
		// keep the file from archive back to original folder with updated filename
			FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("AperakArchivePath"), configReader.inputParams.get("AperakDirectoryPath"), "APE");
			
	}
	
	@Test
	public void readNotesGridAhead() throws FindFailed, InterruptedException   
	{
//		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
//		screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
////		screen.click(completeOrderMenuButton);
		Thread.sleep(3000);	
	//assert that status changes to waiting for ASN aperak
			Assert.assertTrue((screen.exists(configReader.inputParams.get("readnotesGrid"))!=null));
	
//			read notes again
			Pattern readnotesgrid = new Pattern(configReader.inputParams.get("readnotesGrid"));
			screen.wait(readnotesgrid.similar((float)0.90), 5).doubleClick();
			Thread.sleep(3000);
			
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("ReadNotesButton"));
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("OrderNoteTitle"), "", configReader.inputParams.get("UpdateButton"));
			Thread.sleep(3000);
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("UpdateButton"));
			Assert.assertTrue((screen.exists(configReader.inputParams.get("ReadNotesImagePath"))!=null));

	}
	
	@Test
	public void sendInvoice( ) throws FindFailed, InterruptedException   
	{
			
			
	//Send Invoice	
			Thread.sleep(3000);
		Pattern InvoiceNeedsTobeSent = new Pattern(configReader.inputParams.get("InvoiceNeedsTobeSentPath"));
		screen.wait(InvoiceNeedsTobeSent.similar((float)0.90), 5).rightClick();
		Thread.sleep(3000);
		
		Pattern senInvoice = new Pattern(configReader.inputParams.get("senInvoicePath"));
		screen.wait(senInvoice.similar((float)0.90), 5).click();
		Thread.sleep(3000);
		
		//click Ok information dialog
		autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
		Thread.sleep(3000);
		
	// send invoice file opening code will go here	
		
//		Pattern completeOrderMenu = new Pattern("C:\\Users\\Harpreet.Sudan\\Eclipse-workspace-Trial\\spsAutomation\\src\\test\\resources\\images\\completeOrderMenuButton.PNG");
//		screen.wait(completeOrderMenuButton.similar((float)0.90), 5).click();
//		Thread.sleep(3000);
		
		
	
		
		
//		autoitObj.controlSend("Modify Order", "", "[CLASS:TButton; INSTANCE:1]","{SHIFTUP}{CTRLUP}",true);
//		autoitObj.sen
	}
	
	@Test
	public void invoiceFunctionalAck() throws FindFailed, InterruptedException, IOException   
	{
		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
		screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
		Thread.sleep(3000);
//Assert that Waiting Invoice Functional Ac exists
		Assert.assertTrue((screen.exists(configReader.inputParams.get("waitingInvoiceFuncAck"))!=null));
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceFAArchivePath"), configReader.inputParams.get("InvoiceFAPath"), "con");
//CAll the function to update file data in invoice FA file
		FileDataUpdate.updateASNAndInvoiceFAFileData(configReader.inputParams.get("sendDirectoryPath"), configReader.inputParams.get("InvoiceFAFilePath"), "INV");
		
//Steps for invoice functional ack
	
			//Import invoice FA from designed path	
				    autoitObj.send(configReader.inputParams.get("file_menu"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("InvoiceFAFilePath"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("OpenButton"),false);
					Thread.sleep(3000);
			//click OK on information dialog		
					autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
					Thread.sleep(3000);
					
			// keep the file from archive back to original folder with updated filename
					FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceFAArchivePath"), configReader.inputParams.get("InvoiceFAPath"), "con");
				
	
	}
	
	@Test
	public void invoiceAperak() throws FindFailed, InterruptedException, IOException   
	{
//		Pattern completeOrderMenu = new Pattern("C:\\Users\\Harpreet.Sudan\\Eclipse-workspace-Trial\\spsAutomation\\src\\test\\resources\\images\\completeOrderMenuButton.PNG");
//		screen.wait(completeOrderMenuButton.similar((float)0.90), 5).click();
//		Thread.sleep(3000);
//Assert that Waiting Invoice Functional Ac exists
		Assert.assertTrue((screen.exists(configReader.inputParams.get("waitingForInvoiceAperak"))!=null));
////CAll the function to update file data in invoice FA file
		FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceAperakArchivePath"), configReader.inputParams.get("InvoiceAperakDirectoryPath"), "INV_A");
		FileDataUpdate.updateInvoiceAperakFileData(configReader.inputParams.get("sendDirectoryPath"), configReader.inputParams.get("InvoiceAperakPath"), "INV");

	
			//Import invoice FA from designed path	
				    autoitObj.send(configReader.inputParams.get("file_menu"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("ImportPO_menuItem"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("InvoiceAperakPath"),false);
					Thread.sleep(3000);
					autoitObj.send(configReader.inputParams.get("OpenButton"),false);
					Thread.sleep(3000);
			//click OK on information dialog		
					autoitObj.controlClick(configReader.inputParams.get("InformationDialogTitle"), "", configReader.inputParams.get("InformationOkButton"));
					Thread.sleep(3000);
					
			// keep the file from archive back to original folder with updated filename
					FileDataUpdate.replaceAllFileAtSource(configReader.inputParams.get("InvoiceAperakArchivePath"), configReader.inputParams.get("InvoiceAperakDirectoryPath"), "INV_A");
					
	
	}
	
	public void completeStatusVerify() throws FindFailed, InterruptedException   
	{
		Assert.assertTrue((screen.exists(configReader.inputParams.get("completeStatus"))!=null));
	}
	
	@Test
	public void readNotesGridAheadAgain() throws FindFailed, InterruptedException   
	{
//		Pattern completeOrderMenuButton = new Pattern(configReader.inputParams.get("completeOrderMenuButtonPath"));
//		screen.wait(completeOrderMenuButton.similar((float)0.99), 5).click();
////		screen.click(completeOrderMenuButton);
		Thread.sleep(3000);	
	//assert that status changes to waiting for ASN aperak
			Assert.assertTrue((screen.exists(configReader.inputParams.get("readnotesGrid"))!=null));
	
//			read notes again
			Pattern readnotesgrid = new Pattern(configReader.inputParams.get("readnotesGrid"));
			screen.wait(readnotesgrid.similar((float)0.90), 5).doubleClick();
			Thread.sleep(3000);
			
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("ReadNotesButton"));
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("OrderNoteTitle"), "", configReader.inputParams.get("UpdateButton"));
			Thread.sleep(3000);
			Thread.sleep(3000);
			autoitObj.controlClick(configReader.inputParams.get("BrandPartnerTitle"), "", configReader.inputParams.get("UpdateButton"));
			Assert.assertTrue((screen.exists(configReader.inputParams.get("ReadNotesImagePath"))!=null));

	}
	
	
	
//	@AfterTest
//	public void closenb() throws InterruptedException {
//		x.winClose("Untitled - Notepad");
//	}

	

}
