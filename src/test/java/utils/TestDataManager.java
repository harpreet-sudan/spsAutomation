package utils;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class TestDataManager {
//	public static ConfigFileManager configFileManager;
	File fXmlFile;
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document doc;
	private final ConfigReader configReader = ConfigReader.getConfigReader();
	
	public TestDataManager() 
	{
		try {
			
	//	configFileManager = ConfigFileManager.getInstance();
	//	if (configFileManager.getProperty("testdata") != null && configFileManager.getProperty("testdata") != "")
		if (configReader.inputParams.get("testdata") != null)
        {
			fXmlFile = new File(configReader.inputParams.get("testdata"));
        }
		else
		{
			throw new FileNotFoundException("Please set testdata path in config file (ex - TESTDATA=<path of the file>.xml)");
		}
		
		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		    }
	}
	public String read_property (String tag,String type,String value)
	{
		String attrvalue="";
		NodeList nList = doc.getElementsByTagName(tag);
		for (int temp = 0; temp < nList.getLength(); temp++) 
		{
			Node nNode = nList.item(temp);
			if (nNode.getNodeName() == tag) 
			{
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("type").equalsIgnoreCase(type))
				{
					attrvalue=eElement.getElementsByTagName(value).item(0).getTextContent();
				}
			}
		}
		return attrvalue;
	}

}
