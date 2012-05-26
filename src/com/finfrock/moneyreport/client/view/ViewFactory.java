package com.finfrock.moneyreport.client.view;

import java.util.List;

import com.finfrock.moneyreport.client.DisplayPanel;
import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.connection.BankStatementLoader;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.view.report.DisplayableReportBuilder;
import com.google.gwt.user.client.ui.Widget;

public class ViewFactory
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private BillTypeManager billTypeManager;
   private ServiceLocations serviceLocations;
   private List<String> stores;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public ViewFactory(BillTypeManager billTypeManager, 
                      ServiceLocations serviceLocations, 
                      List<String> stores)
   {
      this.billTypeManager = billTypeManager;
      this.serviceLocations = serviceLocations;
      this.stores = stores;
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public Widget createAddEntry()
   {
      return new AddEntryControl(
         billTypeManager, serviceLocations, stores);
   }

   public BankStatementControl createBankStatementControl()
   {
      BankStatementControl bankStatementControl = 
         new BankStatementControl(billTypeManager, serviceLocations, 
            stores);
      
      return bankStatementControl;
   }

   public Widget createReportControl()
   {
      DisplayableReportBuilder displayableReportBuilder = 
         createDisplayableReportBuilder();
      
      DisplayPanel displayPanel = new DisplayPanel(displayableReportBuilder);
      
      return displayPanel;
   }
   
   public DisplayableReportBuilder createDisplayableReportBuilder()
   {
      DisplayableReportBuilder displayableReportBuilder = 
         new DisplayableReportBuilder(billTypeManager, 
            serviceLocations);
      
      return displayableReportBuilder;
   }
   
   public List<DisplayableReport> createDisplayableReports()
   {
      DisplayableReportBuilder displayableReportBuilder = 
         createDisplayableReportBuilder();
      
      return displayableReportBuilder.buildAll();
   }

   public BankStatementLoader createBankStatementLoader()
   {
      return new BankStatementLoader(serviceLocations);
   }

}
