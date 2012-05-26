package com.finfrock.moneyreport.client.connection;

import com.google.gwt.core.client.JavaScriptObject;

public class AllottedAmountData extends JavaScriptObject
{
   protected AllottedAmountData() 
   {
      
   }
   
   public final native int getKey()
   /*-{
      return this.key;
   }-*/;
   
   public final native double getAmount()
   /*-{
      return this.amount;
   }-*/;
   
   public final native int getBillTypeKey()
   /*-{
      return this.billtypekey;
   }-*/;
   
   public final native int getStartDayOfMonth()
   /*-{
      return this.startdayofmonth;
   }-*/;
   
   public final native int getStartMonth()
   /*-{
      return this.startmonth;
   }-*/;
   
   public final native int getStartYear()
   /*-{
      return this.startyear;
   }-*/;
   
   public final native int getEndDayOfMonth()
   /*-{
      return this.enddayofmonth;
   }-*/;
   
   public final native int getEndMonth()
   /*-{
      return this.endmonth;
   }-*/;
   
   public final native int getEndYear()
   /*-{
      return this.endyear;
   }-*/;
}
