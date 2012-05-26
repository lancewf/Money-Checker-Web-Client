package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finfrock.client.Retriever;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.Purchase;
import com.google.gwt.core.client.JsArray;

public class BillTypePurchasesRetriever extends Retriever<List<Purchase>>
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
  
   private BillTypeManager billTypeManager;
   private BillType billType;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public BillTypePurchasesRetriever(ServiceLocations serviceLocations, 
                             BillTypeManager billTypeManager, BillType billType)
   {
      super(serviceLocations.getBillTypePurchaseAddress());
      
      this.billTypeManager = billTypeManager;
      this.billType = billType;
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
  
   @Override
   protected String getRequestData()
   {
      String requestData = "";

      requestData += "billtypekey=" + billType.getKey();
      
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
            new Date(purchaseData.getYear() - 1900, purchaseData.getMonth() - 1, 
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