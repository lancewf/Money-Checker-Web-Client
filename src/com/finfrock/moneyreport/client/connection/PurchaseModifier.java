package com.finfrock.moneyreport.client.connection;

import java.util.Date;

import com.finfrock.client.Sender;
import com.finfrock.moneyreport.client.data.BillType;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PurchaseModifier extends Sender
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private int key;
   private double cost;
   private String store;
   private String note;
   private BillType billType;
   private Date date;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public PurchaseModifier(int key, double cost, String store, String note, BillType billType, 
                         Date date, ServiceLocations serviceLocations)
   {
      super(serviceLocations.getPurchaseModifierAddress());
      
      this.key = key;
      this.cost = cost;
      this.store = store;
      this.note = note;
      this.billType = billType;
      this.date = date;
   }
   
   // --------------------------------------------------------------------------
   // Protected Members
   // --------------------------------------------------------------------------

   @Override
   protected String getData()
   {
      String requestData = "";
      
      DateTimeFormat monthFormater = DateTimeFormat.getFormat("M");
      DateTimeFormat dayFormater = DateTimeFormat.getFormat("d");
      DateTimeFormat yearFormater = DateTimeFormat.getFormat("y");
      
      requestData += "purchaseKey=" + key + "&";
      requestData += "store=" + store + "&";
      requestData += "cost=" + cost + "&";
      requestData += "month=" + monthFormater.format(date) + "&";
      requestData += "dayOfMonth=" + dayFormater.format(date) + "&";
      requestData += "year=" + yearFormater.format(date) + "&";
      requestData += "note=" + note + "&";
      requestData += "billTypeKey=" + billType.getKey();
      
      return requestData;
   }
}
