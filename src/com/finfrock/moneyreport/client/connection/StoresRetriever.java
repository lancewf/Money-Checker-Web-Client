package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Retriever;

public class StoresRetriever extends Retriever<List<String>>
{
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public StoresRetriever(ServiceLocations serviceLocations)
   {
      super(serviceLocations.getStoresAddress());
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
   
   @Override
   protected List<String> parseText(String rawText)
   {
      String[] stores = rawText.split(";");

      List<String> storeList = new ArrayList<String>();
      for(String store : stores)
      {
         storeList.add(store);
      }
      
      return storeList;
   }
}
