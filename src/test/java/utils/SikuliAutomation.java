package utils;

import java.net.URISyntaxException;
import java.net.URL;

import org.sikuli.script.Screen;

public class SikuliAutomation {
	private Screen screen;
    public static String basePath;
    private static SikuliAutomation sikuliAutomation;
    
   
 
    public SikuliAutomation() throws Exception{
    	
        screen = new Screen();
        try {
        URL resourceFolderURL = this.getClass().getClassLoader().getResource("images");
        basePath = resourceFolderURL.toURI().getPath() + "/";
        }
        catch (Exception ex) {
        	System.out.println("URISyntaxException " +ex);
           	
        }
    }
    
    public static SikuliAutomation getSikuliObject() throws Exception {
    	 try {
        if (sikuliAutomation == null)
        	sikuliAutomation = new SikuliAutomation();
        return sikuliAutomation;
    }
    	 catch (Exception ex) {
         	System.out.println("URISyntaxException in get sikuli Object " +ex);   
         	return null;
         }
    }
}


