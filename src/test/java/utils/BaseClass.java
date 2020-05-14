package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;

import org.sikuli.script.Screen;
import org.testng.*;
import org.testng.annotations.*;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;

public class BaseClass {
	FileInputStream file;

	public Properties prop;
	public static String Client = "";
	String propFileName = "config.properties";
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	public static AutoItX autoitObj;
	public static String basePath;
	public static Screen screen;
	public static Path PoOrderFilename;

	public static String jvmBitVersion() {
		return System.getProperty("sun.arch.data.model");
	}

	@BeforeSuite
	public void setup() throws Exception {

		System.out.println("Inside before suite");
		String jacobDllVersionToUse;
		if (jvmBitVersion().contains("32")) {
			jacobDllVersionToUse = "jacob-1.19-x86.dll";
		} else {
			jacobDllVersionToUse = "jacob-1.19-x64.dll";
		}

		File file = new File("lib", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

		System.out.println("Inside before suite at autoit");
		autoitObj = new AutoItX();

		// set base path
		URL resourceFolderURL = this.getClass().getClassLoader().getResource("images");
		System.out.println("url is-------" + resourceFolderURL);
		basePath = resourceFolderURL.toURI().getPath() + "/";

	//	System.out.println("basePath is**********" + (basePath + configReader.inputParams.get("cancelledImagePath")));

		screen = new Screen();

	}

	public void getPropValues() throws IOException {
		try {
			prop = new Properties();
			file = new FileInputStream(propFileName);

			if (file != null) {
				prop.load(file);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			file.close();
		}

	}

	public static void main(String[] args) throws InterruptedException {

	}

}
