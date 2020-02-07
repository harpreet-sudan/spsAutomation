package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {
	FileInputStream file;
	
	public Properties prop;
	String propFileName = "config.properties";

	
	public void getPropValues() throws IOException {
		try {
			prop = new Properties();
			file= new FileInputStream(propFileName);
	
			if (file != null) {
				prop.load(file);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		}catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			file.close();
		}
	
	}
	
	
	public static void main(String[] args) throws InterruptedException {

	}
	
	

}
