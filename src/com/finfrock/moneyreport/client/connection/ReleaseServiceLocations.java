package com.finfrock.moneyreport.client.connection;

public class ReleaseServiceLocations implements ServiceLocations
{

   @Override
   public String getBillTypeAddress()
   {
      return getBaseServicesUrl() + "getBillTypes";
   }

   @Override
   public String getPurchaseAddress()
   {
      return getBaseServicesUrl() + "getPurchases";
   }

   public static native String getBaseServicesUrl() 
   /*-{
      return $wnd.base_services_url;
   }-*/;

   @Override
   public String getAllottedAmountAddress()
   {
      return getBaseServicesUrl() + "getAllocatedAmounts";
   }

   @Override
   public String getCurrentViewItemsAddress()
   {
      return getBaseServicesUrl() + "getCurrentViewItems";
   }

   @Override
   public String getBillTypePurchaseAddress()
   {
      return getBaseServicesUrl() + "getBillTypePurchases";
   }

   @Override
   public String getPurchaseSenderAddress()
   {
      return getBaseServicesUrl() + "addPurchase";
   }

   @Override
   public String getStoresAddress()
   {
      return getBaseServicesUrl() + "getStores";
   }

   @Override
   public String getMatchingPurchaseAddress()
   {
      return getBaseServicesUrl() + "getMatchingPuchases";
   }

   @Override
   public String getProcessBankStatementAddress()
   {
      return getBaseServicesUrl() + "processBankStatement";
   }

   @Override
   public String getPurchaseDeleterAddress()
   {
      return getBaseServicesUrl() + "deletePurchase";
   }

   @Override
   public String getPurchaseModifierAddress()
   {
      return getBaseServicesUrl() + "modifyPurchase";
   }
}
