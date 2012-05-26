package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Retriever;
import com.google.gwt.core.client.JsArray;

public class BillTypeRetriever extends Retriever<List<BillTypeData>>
{
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public BillTypeRetriever(ServiceLocations serviceLocations)
   {
      super(serviceLocations.getBillTypeAddress());
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
   
   @Override
   protected List<BillTypeData> parseText(String rawText)
   {
      JsArray<BillTypeData> billTypeDatas = asArrayOfBillTypeData(rawText);

      List<BillTypeData> billTypes = new ArrayList<BillTypeData>();
      for(int index = 0; index < billTypeDatas.length(); index++)
      {
         BillTypeData billTypeData = billTypeDatas.get(index);
         
         billTypes.add(billTypeData);
      }
      
      return billTypes;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private final native JsArray<BillTypeData> asArrayOfBillTypeData(String json) 
   /*-{
      return eval(json);
   }-*/;
}