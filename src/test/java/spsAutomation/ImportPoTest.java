package spsAutomation;


import java.io.File;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;
import utils.BaseClass;
import utils.ConfigReader;
import utils.TestDataManager;

public class ImportPoTest extends BaseClass{
	
	//static  Logger log = Logger.getLogger(notepad.class.getName());  
	   
	AutoItX autoitObj;
	static  Logger log = Logger.getLogger(ImportPoTest.class.getName()); 
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	private String app_url = configReader.inputParams.get("app_url");
	TestDataManager testdata=new TestDataManager();

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
	}

	@Test(priority = 1)
	public void openSps() throws InterruptedException {
//		log.info("opening abspick");
//		log.info("this is app url"+app_url);
//		System.out.println("testdata"+testdata.read_property("CreateAssortment", "plan", "beginMonth"));
		
		autoitObj.run(app_url);
	//	autoitObj.run(app_url,"",AutoItX.SW_MAXIMIZE);
		autoitObj.winActivate("Unpicked Orders");
	
		//x.winMenuSelectItem("[CLASS:Notepad]", "", "&File", "Save");
	}

	
	@Test(priority = 2)
	public void maximizeWindow() throws InterruptedException {
		autoitObj.winActivate("Unpicked Orders");
		autoitObj.winSetState("[CLASS:TJMcDDrawGrid]", "",  AutoItX.SW_MAXIMIZE);
	
//		int i = autoitObj.winGetState("Unpicked Orders");
//		System.out.println("state is "+i);
//		
		}
	
	
	
	@Test(priority = 3)
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
	//    autoitObj.controlClick("Importing Order Amendments", "", "[CLASS:TBitBtn]");
	//	System.out.println(" this is falg  " +flag1);
			
	}

	
	
	
	
//	@AfterTest
//	public void closenb() throws InterruptedException {
//		x.winClose("Untitled - Notepad");
//	}

	

}
