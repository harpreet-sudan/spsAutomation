package utils;

import java.util.HashMap;
import java.util.Map;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigReader {
	
	public Map<String, String> inputParams = new HashMap<>();
	
	private static ConfigReader configReader;
    public Config config = ConfigFactory.parseResources("application.conf");
    
    private ConfigReader() {
    	inputParams.put("app_url", config.getString("conf.InputParams.app_url"));
    	inputParams.put("file_menu", config.getString("conf.InputParams.file_menu"));
    	inputParams.put("ImportPO_menuItem", config.getString("conf.InputParams.ImportPO_menuItem"));
    	inputParams.put("POOrderFilePath", config.getString("conf.InputParams.POOrderFilePath"));
    	inputParams.put("OpenButton", config.getString("conf.InputParams.OpenButton"));
    	inputParams.put("testdata", config.getString("conf.InputParams.testdata"));
    	inputParams.put("InformationDialogTitle", config.getString("conf.InputParams.InformationDialogTitle"));
    	inputParams.put("OkButton", config.getString("conf.InputParams.OkButton"));
    }
    
    public static ConfigReader getConfigReader() {
        if (configReader == null)
            configReader = new ConfigReader();
        return configReader;
    }

}
