package com.finfrock.moneyreport.client.connection;

import com.google.gwt.core.client.JavaScriptObject;

public class CurrentViewItemData extends JavaScriptObject
{
   protected CurrentViewItemData() 
   {
      
   }
   
   public final native int getBillTypeKey()
   /*-{
      return this.billType;
   }-*/;
   
   public final native double getAllotted()
   /*-{
      return this.allotted;
   }-*/;
   
   public final native double getSpent()
   /*-{
      return this.spent;
   }-*/;
   
   public final native double getAmountLeft()
   /*-{
      return this.amountLeft;
   }-*/;
   
   public final native double getAverage()
   /*-{
      return this.average;
   }-*/;
   
   public final native double getAmountLeftOfAverage()
   /*-{
      return this.amountLeftOfAverage;
   }-*/;
}
