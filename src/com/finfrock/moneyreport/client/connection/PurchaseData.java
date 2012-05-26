package com.finfrock.moneyreport.client.connection;

import com.google.gwt.core.client.JavaScriptObject;

public class PurchaseData extends JavaScriptObject
{
   protected PurchaseData() 
   {
      
   }
   
   public final native int getKey()
   /*-{
      return this.key;
   }-*/;
   
   public final native String getStore()
   /*-{
      return this.store;
   }-*/;
   
   public final native double getCost()
   /*-{
      return this.cost;
   }-*/;
   
   public final native String getNotes()
   /*-{
      return this.notes;
   }-*/;
   
   public final native int getBillTypeKey()
   /*-{
      return this.billtypekey;
   }-*/;
   
   public final native int getDayOfMonth()
   /*-{
      return this.dayofmonth;
   }-*/;
   
   public final native int getMonth()
   /*-{
      return this.month;
   }-*/;
   
   public final native int getYear()
   /*-{
      return this.year;
   }-*/;
}
