package com.finfrock.moneyreport.client.connection;

import com.google.gwt.core.client.GWT;

public class TestServiceLocations implements ServiceLocations
{

   @Override
   public String getPurchaseAddress()
   {
      return getServiceBase() + "getPurchases";
   }

   @Override
   public String getBillTypeAddress()
   {
      return GWT.getHostPageBaseURL() + "billtypes.php";
   }

   @Override
   public String getAllottedAmountAddress()
   {
      return GWT.getHostPageBaseURL() + "allottedamounts.php";
   }

   @Override
   public String getCurrentViewItemsAddress()
   {
      return GWT.getHostPageBaseURL() + "currentviewitems.php";
   }

   @Override
   public String getBillTypePurchaseAddress()
   {
      return GWT.getHostPageBaseURL() + "billtypepurchases.php";
   }

   @Override
   public String getPurchaseSenderAddress()
   {
      return GWT.getHostPageBaseURL() + "purchasesender.php";
   }

   @Override
   public String getStoresAddress()
   {
      return GWT.getHostPageBaseURL() + "getStores.php";
   }

   @Override
   public String getMatchingPurchaseAddress()
   {
      return GWT.getHostPageBaseURL() + "getMatchingPuchases.php";
   }

   @Override
   public String getProcessBankStatementAddress()
   {
      return GWT.getHostPageBaseURL() + "processbankstatement.php";
   }

   @Override
   public String getPurchaseDeleterAddress()
   {
      return GWT.getHostPageBaseURL() + "deletePurchase.php";
   }

   @Override
   public String getPurchaseModifierAddress()
   {
      return GWT.getHostPageBaseURL() + "modifyPurchase.php";
   }
   
   private String getServiceBase()
   {
//      return GWT.getHostPageBaseURL();
      return "http://localhost/moneyreport/index.php/services/";
   }

}
