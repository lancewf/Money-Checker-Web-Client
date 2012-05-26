package com.finfrock.moneyreport.client.connection;

import com.google.gwt.core.client.JavaScriptObject;

public class BillTypeData extends JavaScriptObject
{
   protected BillTypeData() 
   {
      
   }
   
   public final native int getKey()
   /*-{
      return this.key;
   }-*/;
   
   public final native String getName()
   /*-{
      return this.name;
   }-*/;
   
   public final native String getDescription()
   /*-{
      return this.description;
   }-*/;
}
