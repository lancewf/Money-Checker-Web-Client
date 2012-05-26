package com.finfrock.moneyreport.client.connection;

public interface ServiceLocations
{
   String getPurchaseAddress();

   String getBillTypeAddress();

   String getAllottedAmountAddress();

   String getCurrentViewItemsAddress();

   String getBillTypePurchaseAddress();

   String getPurchaseSenderAddress();

   String getStoresAddress();

   String getMatchingPurchaseAddress();

   String getProcessBankStatementAddress();

   String getPurchaseDeleterAddress();

   String getPurchaseModifierAddress();
}
