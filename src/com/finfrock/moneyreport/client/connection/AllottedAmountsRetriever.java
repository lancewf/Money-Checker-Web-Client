package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Retriever;
import com.google.gwt.core.client.JsArray;

public class AllottedAmountsRetriever extends Retriever<List<AllottedAmountData>>
{
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public AllottedAmountsRetriever(ServiceLocations serviceLocations)
   {
      super(serviceLocations.getAllottedAmountAddress());
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
   
   @Override
   protected List<AllottedAmountData> parseText(String rawText)
   {
      JsArray<AllottedAmountData> allottedAmountDataJsArray = 
         asArrayOfAllottedAmountData(rawText);

      List<AllottedAmountData> allottedAmountDataList = 
         new ArrayList<AllottedAmountData>();
      
      for(int index = 0; index < allottedAmountDataJsArray.length(); index++)
      {
         AllottedAmountData allottedAmountData = 
            allottedAmountDataJsArray.get(index);

         allottedAmountDataList.add(allottedAmountData);
      }
      
      return allottedAmountDataList;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private final native JsArray<AllottedAmountData> 
      asArrayOfAllottedAmountData(String json) 
   /*-{
      return eval(json);
   }-*/;
}