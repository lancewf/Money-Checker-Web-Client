package com.finfrock.moneyreport.client.connection;

import java.util.Date;

import com.finfrock.client.Sender;
import com.finfrock.moneyreport.client.data.BillType;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PurchaseSender extends Sender
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private double cost;
   private String store;
   private String note;
   private BillType billType;
   private Date date;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public PurchaseSender(double cost, String store, String note, BillType billType, 
                         Date date, ServiceLocations serviceLocations)
   {
      super(serviceLocations.getPurchaseSenderAddress());
      
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
