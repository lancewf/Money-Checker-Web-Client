package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finfrock.client.Retriever;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.Purchase;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PurchasesRetriever extends Retriever<List<Purchase>>
{
   private BillTypeManager billTypeManager;
   private Date startDate;
   private Date endDate;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public PurchasesRetriever(ServiceLocations serviceLocations, 
                             BillTypeManager billTypeManager, Date startDate, 
                             Date endDate)
   {
      super(serviceLocations.getPurchaseAddress());
      
      this.startDate = startDate;
      this.endDate = endDate;
      this.billTypeManager = billTypeManager;
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
  
   @Override
   protected String getRequestData()
   {
      String requestData = "";
      
      DateTimeFormat monthFormater = DateTimeFormat.getFormat("M");
      DateTimeFormat dayFormater = DateTimeFormat.getFormat("d");
      DateTimeFormat yearFormater = DateTimeFormat.getFormat("y");
      
      requestData += "startmonth=" + monthFormater.format(startDate) + "&";
      requestData += "startdaymonth=" + dayFormater.format(startDate) + "&";
      requestData += "startyear=" + yearFormater.format(startDate) + "&";
      requestData += "endmonth=" + monthFormater.format(endDate) + "&";
      requestData += "enddaymonth=" + dayFormater.format(endDate) + "&";
      requestData += "endyear=" + yearFormater.format(endDate);
      
      return requestData;
   }
  
   @Override
   protected List<Purchase> parseText(String rawText)
   {
      JsArray<PurchaseData> purchaseDatas = asArrayOfPurchaseData(rawText);

      List<Purchase> purchases = new ArrayList<Purchase>();
      for(int index = 0; index < purchaseDatas.length(); index++)
      {
         PurchaseData purchaseData = purchaseDatas.get(index);
         
         Date date = 
            new Date(purchaseData.getYear() -1900, purchaseData.getMonth() - 1, 
               purchaseData.getDayOfMonth());
         
         BillType billType = billTypeManager.getBillType(purchaseData.getBillTypeKey());
         
         Purchase purchase = new Purchase(purchaseData.getKey(), purchaseData.getStore(), 
            purchaseData.getCost(),
            billType, purchaseData.getNotes(), date);
         
         
         purchases.add(purchase);
      }
      
      return purchases;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private final native JsArray<PurchaseData> asArrayOfPurchaseData(String json) 
   /*-{
      return eval(json);
   }-*/;
}