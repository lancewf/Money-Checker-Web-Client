package com.finfrock.moneyreport.client.data;

import java.util.Date;

import com.extjs.gxt.ui.client.store.Record;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;

public class PurchaseItemComparer
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private PurchaseItem purchaseItem;
   private Record record;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public PurchaseItemComparer(PurchaseItem purchaseItem, Record record)
   {
      this.purchaseItem = purchaseItem;
      this.record = record;
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public double getCost()
   {
      return get(PurchaseItem.COST_PROPERTY_NAME);
   }


   public int getKey()
   {
      return purchaseItem.getKey();
   }


   public String getStore()
   {
      return get(PurchaseItem.STORE_PROPERTY_NAME);
   }


   public String getNote()
   {
      String note = get(PurchaseItem.NOTE_PROPERTY_NAME);
      
      if(note == null)
      {
         note = "";
      }
      return note;
   }


   public BillType getBillType()
   {
      return get(PurchaseItem.BILL_TYPE_PROPERTY_NAME);
   }


   public Date getDate()
   {
      return get(PurchaseItem.DATE_PROPERTY_NAME);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private <X> X get(String property)
   {
      return purchaseItem.get(property);
   }
}
