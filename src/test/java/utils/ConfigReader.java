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
    	inputParams.put("ModifyImportPath", config.getString("conf.InputParams.ModifyImportPath"));
    	inputParams.put("ConfigMenu", config.getString("conf.InputParams.ConfigMenu"));
      	inputParams.put("ImportPathTableSubmenu", config.getString("conf.InputParams.ImportPathTableSubmenu"));
      	inputParams.put("PasswordDialog", config.getString("conf.InputParams.PasswordDialog"));
      	inputParams.put("F4Button", config.getString("conf.InputParams.F4Button"));
      	inputParams.put("PasswordDialogOkButton", config.getString("conf.InputParams.PasswordDialogOkButton"));
      	inputParams.put("ModifyImportPathButton", config.getString("conf.InputParams.ModifyImportPathButton"));
      	inputParams.put("FilePathTextbox", config.getString("conf.InputParams.FilePathTextbox"));
      	inputParams.put("SelectAllMenuOption", config.getString("conf.InputParams.SelectAllMenuOption"));
      	inputParams.put("BackSpaceKey", config.getString("conf.InputParams.BackSpaceKey"));
      	inputParams.put("UpdateButton_ModifyImportPath", config.getString("conf.InputParams.UpdateButton_ModifyImportPath"));
      	inputParams.put("ExitButton", config.getString("conf.InputParams.ExitButton"));
      	inputParams.put("PasswordTextbox", config.getString("conf.InputParams.PasswordTextbox"));
      	inputParams.put("ImportPathListDialogTitle", config.getString("conf.InputParams.ImportPathListDialogTitle"));
      	inputParams.put("ModifyImportPathDialogTitle", config.getString("conf.InputParams.ModifyImportPathDialogTitle"));
      	inputParams.put("DeleteImportPathButton", config.getString("conf.InputParams.DeleteImportPathButton"));
      	inputParams.put("ConfirmDialogTitle", config.getString("conf.InputParams.ConfirmDialogTitle"));
    	inputParams.put("ConfirmOkButton", config.getString("conf.InputParams.ConfirmOkButton"));
    	inputParams.put("SikuliImagePath", config.getString("conf.InputParams.SikuliImagePath"));
    	inputParams.put("ModifyOrderButton", config.getString("conf.InputParams.ModifyOrderButton"));
    	
    	inputParams.put("OrderDetailTitle", config.getString("conf.InputParams.OrderDetailTitle"));
    	
    	inputParams.put("ModifyOrderTitle", config.getString("conf.InputParams.ModifyOrderTitle"));
    	inputParams.put("ShiftDownKey", config.getString("conf.InputParams.ShiftDownKey"));
    	inputParams.put("CtrlDownKey", config.getString("conf.InputParams.CtrlDownKey"));
    	inputParams.put("restrictedbuttonImagePath", config.getString("conf.InputParams.restrictedbuttonImagePath"));
    	inputParams.put("UpKeys", config.getString("conf.InputParams.UpKeys"));
    	inputParams.put("StatusComboBox", config.getString("conf.InputParams.StatusComboBox"));
    	inputParams.put("NotAfterDateBox", config.getString("conf.InputParams.NotAfterDateBox"));
    	inputParams.put("ShipByDate", config.getString("conf.InputParams.ShipByDate"));
    	inputParams.put("UpdateButton", config.getString("conf.InputParams.UpdateButton"));
    	inputParams.put("BrandPartnerTitle", config.getString("conf.InputParams.BrandPartnerTitle"));
    	inputParams.put("PendingImagePath", config.getString("conf.InputParams.PendingImagePath"));
    	inputParams.put("ReadNotesButton", config.getString("conf.InputParams.ReadNotesButton"));
    	inputParams.put("OrderNoteTitle", config.getString("conf.InputParams.OrderNoteTitle"));
    	inputParams.put("ReadNotesImagePath", config.getString("conf.InputParams.ReadNotesImagePath"));
    	inputParams.put("SendPOAPath", config.getString("conf.InputParams.SendPOAPath"));
    	inputParams.put("SendPurchaseOrderTitle", config.getString("conf.InputParams.SendPurchaseOrderTitle"));
    	    	
    	inputParams.put("OutstandingImagePath", config.getString("conf.InputParams.OutstandingImagePath"));
    	inputParams.put("prePrintLabelPath", config.getString("conf.InputParams.prePrintLabelPath"));
    	inputParams.put("accentRatioPackImagePath", config.getString("conf.InputParams.accentRatioPackImagePath"));
    	inputParams.put("starTrackImagePath", config.getString("conf.InputParams.starTrackImagePath"));
    	inputParams.put("virtualPickImagePath", config.getString("conf.InputParams.virtualPickImagePath"));
    	inputParams.put("partiallyFilledMenuButtonPath", config.getString("conf.InputParams.partiallyFilledMenuButtonPath"));
    	inputParams.put("partiallyFilledOrderPath", config.getString("conf.InputParams.partiallyFilledOrderPath"));
    	inputParams.put("completeOrderMenuPath", config.getString("conf.InputParams.completeOrderMenuPath"));
    	inputParams.put("completeOrderMenuButtonPath", config.getString("conf.InputParams.completeOrderMenuButtonPath"));
    	inputParams.put("InvoiceNeedsTobeSentPath", config.getString("conf.InputParams.InvoiceNeedsTobeSentPath"));
    	inputParams.put("senInvoicePath", config.getString("conf.InputParams.senInvoicePath"));
    	inputParams.put("PrePrintSCMLabelsTitle", config.getString("conf.InputParams.PrePrintSCMLabelsTitle"));
    	inputParams.put("PickByProductButton", config.getString("conf.InputParams.PickByProductButton"));
    	inputParams.put("SelectRatioPackTitle", config.getString("conf.InputParams.SelectRatioPackTitle"));
    	inputParams.put("ShipmentDetailsTitle", config.getString("conf.InputParams.ShipmentDetailsTitle"));
    	inputParams.put("CarierDropdown", config.getString("conf.InputParams.CarierDropdown"));
    	inputParams.put("ConsignmentNoteNumber", config.getString("conf.InputParams.ConsignmentNoteNumber"));
    	inputParams.put("DocketNumber", config.getString("conf.InputParams.DocketNumber"));
    	inputParams.put("ContainerReferenceNumber", config.getString("conf.InputParams.ContainerReferenceNumber"));
    	inputParams.put("VirtualPickTitle", config.getString("conf.InputParams.VirtualPickTitle"));
    	inputParams.put("InformationOkButton", config.getString("conf.InputParams.InformationOkButton"));
    	inputParams.put("ConfirmTitle", config.getString("conf.InputParams.ConfirmTitle"));
    	inputParams.put("WaitingFunctionalAck", config.getString("conf.InputParams.WaitingFunctionalAck"));
    	inputParams.put("FAFilePath", config.getString("conf.InputParams.FAFilePath"));
    	inputParams.put("WaitingASNPath", config.getString("conf.InputParams.WaitingASNPath"));
    	inputParams.put("ASNFilePath", config.getString("conf.InputParams.ASNFilePath"));
    	inputParams.put("readnotesGrid", config.getString("conf.InputParams.readnotesGrid"));
     	inputParams.put("waitingInvoiceFuncAck", config.getString("conf.InputParams.waitingInvoiceFuncAck"));
      	inputParams.put("InvoiceFAFilePath", config.getString("conf.InputParams.InvoiceFAFilePath"));
    	inputParams.put("waitingForInvoiceAperak", config.getString("conf.InputParams.waitingForInvoiceAperak"));
      	inputParams.put("InvoiceAperakPath", config.getString("conf.InputParams.InvoiceAperakPath"));     	
      	inputParams.put("completeStatus", config.getString("conf.InputParams.completeStatus"));
      	inputParams.put("importDirectoryPath", config.getString("conf.InputParams.importDirectoryPath"));
       	inputParams.put("sendDirectoryPath", config.getString("conf.InputParams.sendDirectoryPath"));
    	inputParams.put("FADirectoryPath", config.getString("conf.InputParams.FADirectoryPath"));
    	inputParams.put("FAArchivePath", config.getString("conf.InputParams.FAArchivePath"));
    	inputParams.put("AperakArchivePath", config.getString("conf.InputParams.AperakArchivePath"));
       	inputParams.put("AperakDirectoryPath", config.getString("conf.InputParams.AperakDirectoryPath"));
    	inputParams.put("InvoiceFAArchivePath", config.getString("conf.InputParams.InvoiceFAArchivePath"));
    	inputParams.put("InvoiceFAPath", config.getString("conf.InputParams.InvoiceFAPath"));
    	inputParams.put("InvoiceAperakArchivePath", config.getString("conf.InputParams.InvoiceAperakArchivePath"));
    	inputParams.put("InvoiceAperakDirectoryPath", config.getString("conf.InputParams.InvoiceAperakDirectoryPath"));
    	inputParams.put("AperakArchivePath", config.getString("conf.InputParams.AperakArchivePath"));
     	
     	
     	
      
    }
    
    public static ConfigReader getConfigReader() {
        if (configReader == null)
            configReader = new ConfigReader();
        return configReader;
    }

}
