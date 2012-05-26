package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finfrock.client.Loadable;
import com.finfrock.moneyreport.client.data.AllottedAmount;
import com.finfrock.moneyreport.client.data.BillType;

public class DataBuilder
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private BillTypeRetriever billTypeRetriever;
   
   private BillTypeManager billTypeManager;
   
   private AllottedAmountsRetriever allottedAmountsRetriever;
   
   private StoresRetriever storesRetriever;
   
   // --------------------------------------------------------------------------
   // Constructor Members
   // --------------------------------------------------------------------------

   public DataBuilder()
   {
      //
      // Do nothing
      //
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public void setLoadable(Loadable loadable)
   {
      if (loadable instanceof BillTypeRetriever)
      {
         billTypeRetriever = (BillTypeRetriever) loadable;
      }
      else if (loadable instanceof AllottedAmountsRetriever)
      {
         allottedAmountsRetriever = (AllottedAmountsRetriever) loadable;
      }
      else if (loadable instanceof StoresRetriever)
      {
         storesRetriever = (StoresRetriever) loadable;
      }
   }
   
   public void build()
   {
      List<BillTypeData> billTypeDatas = billTypeRetriever.getObject();
      
      List<BillType> billTypes = new ArrayList<BillType>();
      for(BillTypeData billTypeData : billTypeDatas)
      {
         BillType billType = new BillType(billTypeData.getKey(), 
            billTypeData.getName(), billTypeData.getDescription());
         
         billTypes.add(billType);
      }
      
      billTypeManager = new BillTypeManager(billTypes);
      
      
      List<AllottedAmountData> allottedAmountDatas = 
         allottedAmountsRetriever.getObject();
      
      for(AllottedAmountData allottedAmountData : allottedAmountDatas)
      {
         BillType billType = billTypeManager.getBillType(
            allottedAmountData.getBillTypeKey());
         int key = allottedAmountData.getKey();
         double amount = allottedAmountData.getAmount();
         Date startDate = 
            new Date(allottedAmountData.getStartYear(), allottedAmountData.getStartMonth() - 1, 
               allottedAmountData.getStartDayOfMonth());
         Date endDate = 
            new Date(allottedAmountData.getEndYear(), allottedAmountData.getEndMonth() - 1, 
               allottedAmountData.getEndDayOfMonth());
         
         
         AllottedAmount allottedAmount = new AllottedAmount(
            key, amount, billType, startDate, endDate);
         
         billType.addAllottedAmount(allottedAmount);
      }
   }

   public BillTypeManager getBillTypeManager()
   {
      return billTypeManager;
   }
   
   public List<String> getStores()
   {
      return storesRetriever.getObject();
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
}
