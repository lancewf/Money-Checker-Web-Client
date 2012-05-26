package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Retriever;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.view.report.item.CurrentViewItem;
import com.google.gwt.core.client.JsArray;

public class CurrentViewItemsRetriever extends Retriever<List<CurrentViewItem>>
{
   private BillTypeManager billTypeManager;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public CurrentViewItemsRetriever(ServiceLocations serviceLocations, 
                             BillTypeManager billTypeManager)
   {
      super(serviceLocations.getCurrentViewItemsAddress());
      
      this.billTypeManager = billTypeManager;
   }
   
   // --------------------------------------------------------------------------
   // Retriever Members
   // --------------------------------------------------------------------------
   
   @Override
   protected List<CurrentViewItem> parseText(String rawText)
   {
      JsArray<CurrentViewItemData> currentViewItemDatas = asArrayOfCurrentViewItemData(rawText);

      List<CurrentViewItem> currentViewItems = new ArrayList<CurrentViewItem>();
      for(int index = 0; index < currentViewItemDatas.length(); index++)
      {
         CurrentViewItemData currentViewItemData = currentViewItemDatas.get(index);
         
         BillType billType = billTypeManager.getBillType(currentViewItemData.getBillTypeKey());
         
         CurrentViewItem currentViewItem = new CurrentViewItem(billType.getName(),
            currentViewItemData.getAllotted(), currentViewItemData.getSpent(), 
            currentViewItemData.getAmountLeft(), currentViewItemData.getAverage(),
            currentViewItemData.getAmountLeftOfAverage());
         
         currentViewItems.add(currentViewItem);
      }
      
      return currentViewItems;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private final native JsArray<CurrentViewItemData> asArrayOfCurrentViewItemData(String json) 
   /*-{
      return eval(json);
   }-*/;
}