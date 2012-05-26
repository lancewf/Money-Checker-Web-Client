package com.finfrock.moneyreport.client.connection;

import com.finfrock.client.Sender;

public class PurchaseDeleter extends Sender
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private int purchaseKey;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public PurchaseDeleter(int purchaseKey, ServiceLocations serviceLocations)
   {
      super(serviceLocations.getPurchaseDeleterAddress());

      this.purchaseKey = purchaseKey;
   }

   // --------------------------------------------------------------------------
   // Protected Members
   // --------------------------------------------------------------------------

   @Override
   protected String getData()
   {
      String requestData = "";
      
      requestData += "purchaseKey=" + purchaseKey;
      
      return requestData;
   }
}
