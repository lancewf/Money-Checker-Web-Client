package com.finfrock.moneyreport.client.view.report.item;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class CurrentViewItem extends BaseModelData
{
   public CurrentViewItem(String billType, double allotted, double spent, 
                          double amountLeft, double average, 
                          double amountLeftOfAverage)
   {
      set("billType", billType);
      set("allotted", allotted);
      set("spent", spent);
      set("amountLeft", amountLeft);
      set("average", average);
      set("amountLeftOfAverage", amountLeftOfAverage);
   }
}
