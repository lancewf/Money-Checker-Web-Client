package com.finfrock.moneyreport.client.view.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.view.report.builder.BillTypePurchaseItemBuilder;
import com.finfrock.moneyreport.client.view.report.builder.CurrentViewItemBuilder;
import com.finfrock.moneyreport.client.view.report.builder.PurchaseBillTypeItemBuilder;
import com.google.gwt.i18n.client.DateTimeFormat;

public class DisplayableReportBuilder
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
  
   private BillTypeManager billTypeManager;
   private ServiceLocations serviceLocations;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public DisplayableReportBuilder(BillTypeManager billTypeManager, 
                                   ServiceLocations serviceLocations)
   {
      this.billTypeManager = billTypeManager;
      this.serviceLocations = serviceLocations;
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public List<DisplayableReport> buildAll()
   {
      List<DisplayableReport> displayableReports = 
         new ArrayList<DisplayableReport>();
      
      displayableReports.addAll(buildCurrentView());
      
      displayableReports.addAll(buildMonthDisplayReports());
      
      displayableReports.addAll(buildBillTypePurchasesDisplayReports());
      
      return displayableReports;
   }
   
   public List<DisplayableReport> buildCurrentView()
   {
      List<DisplayableReport> displayableReports = 
         new ArrayList<DisplayableReport>();

      CurrentViewItemBuilder currentView = new CurrentViewItemBuilder(
         billTypeManager, serviceLocations);
      
      displayableReports.add(currentView);
      
      return displayableReports;
   }
   
   public List<DisplayableReport> buildBillTypePurchasesDisplayReports()
   {
      List<DisplayableReport> displayableReports = 
         new ArrayList<DisplayableReport>();
      
      for(BillType billType : billTypeManager.getBillTypes())
      {
         BillTypePurchaseItemBuilder billTypePurchaseItemBuilder = 
            new BillTypePurchaseItemBuilder(billTypeManager, 
               serviceLocations, billType);

         displayableReports.add(billTypePurchaseItemBuilder);
      }
      
      return displayableReports;
   }
   
   public List<DisplayableReport> buildMonthDisplayReports()
   {
      List<DisplayableReport> displayableReports = 
         new ArrayList<DisplayableReport>();
      
      DateTimeFormat monthFormater = DateTimeFormat.getFormat("MMMM yyyy");
      
      Date currentMonth = getStartOfNextMonth();
      
      for (int index = 0; index < 4; index++)
      {
         Date previousMonth = getStartOfPreviousMonth(currentMonth);

         PurchaseBillTypeItemBuilder purchaseItemBuilder = new PurchaseBillTypeItemBuilder(
            billTypeManager, serviceLocations, previousMonth, currentMonth,
            monthFormater.format(previousMonth));

         displayableReports.add(purchaseItemBuilder);
         
         currentMonth = previousMonth;
      }
      
      return displayableReports;
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private Date getStartOfPreviousMonth(Date currentMonth)
   {
      DateTimeFormat monthFormater = DateTimeFormat.getFormat("M");
      DateTimeFormat yearFormater = DateTimeFormat.getFormat("y");
      
      int month = Integer.parseInt(monthFormater.format(currentMonth));
      int year = Integer.parseInt(yearFormater.format(currentMonth));
      
      int nextMonth = 0;
      int nextYear = 0;
      
      if(month <= 1)
      {
         nextMonth = 12;
         nextYear = year - 1;
      }
      else
      {
         nextMonth = month - 1;
         nextYear = year;
      }
      
      Date previousMonth = new Date(nextYear - 1900, nextMonth - 1, 1);
      
      return previousMonth;
   }

   private Date getStartOfNextMonth()
   {
      Date today = new Date();
      
      DateTimeFormat monthFormater = DateTimeFormat.getFormat("M");
      DateTimeFormat yearFormater = DateTimeFormat.getFormat("y");
      
      int month = Integer.parseInt(monthFormater.format(today));
      int year = Integer.parseInt(yearFormater.format(today));
      
      if(month == 12)
      {
         year +=1;
         month = 1;
      }
      else
      {
         month++;
      }
      
      Date startOfMonth = new Date(year - 1900, month - 1, 1);
      
      return startOfMonth;
   }
}
