package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finfrock.client.Retriever;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.Purchase;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

public class MatchingPurchasesRetriever extends Retriever<List<Purchase>>
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private double cost;
   private String store;
   private String note;
   private BillType billType;
   private Date date;
   private BillTypeManager billTypeManager;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public MatchingPurchasesRetriever(BillTypeManager billTypeManager, 
                                     double cost, 
                                     String store, 
                                     String note, 
                                     BillType billType, 
                                     Date date, 
                                     ServiceLocations serviceLocations)
   {
      super(serviceLocations.getMatchingPurchaseAddress());
      
      this.cost = cost;
      this.store = store;
      this.note = note;
      this.billType = billType;
      this.date = date;
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
      
      requestData += "store=" + store + "&";
      requestData += "cost=" + cost + "&";
      requestData += "month=" + monthFormater.format(date) + "&";
      requestData += "dayOfMonth=" + dayFormater.format(date) + "&";
      requestData += "year=" + yearFormater.format(date) + "&";
      requestData += "note=" + note;
      
      if(billType != null)
      {
         requestData += "&billTypeKey=" + billType.getKey();
      }
      
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
            new Date(purchaseData.getYear()-1900, purchaseData.getMonth() - 1, 
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
