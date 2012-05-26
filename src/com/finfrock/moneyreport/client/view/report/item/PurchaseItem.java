package com.finfrock.moneyreport.client.view.report.item;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.finfrock.moneyreport.client.data.BillType;

public class PurchaseItem extends BaseModelData
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   public static final String COST_PROPERTY_NAME = "cost";
   public static final String KEY_PROPERTY_NAME = "key";
   public static final String STORE_PROPERTY_NAME = "storename";
   public static final String BILL_TYPE_PROPERTY_NAME = "billType";
   public static final String NOTE_PROPERTY_NAME = "note";
   public static final String DATE_PROPERTY_NAME = "date";
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public PurchaseItem(int key, String store, 
                   double cost, BillType billType, 
                   String note, Date date)
   {
      set(KEY_PROPERTY_NAME, key);
      set(STORE_PROPERTY_NAME, store);
      set(COST_PROPERTY_NAME, cost);
      set(BILL_TYPE_PROPERTY_NAME, billType);
      set(NOTE_PROPERTY_NAME, note);
      set(DATE_PROPERTY_NAME, date);
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public Date getDate()
   {
      return get(DATE_PROPERTY_NAME);
   }

   public String getStore()
   {
      return get(STORE_PROPERTY_NAME);
   }

   public int getKey()
   {
      return get(KEY_PROPERTY_NAME);
   }

   public BillType getBillType()
   {
      return get(BILL_TYPE_PROPERTY_NAME);
   }

   public double getCost()
   {
      return get(COST_PROPERTY_NAME);
   }
   
   public void setCost(double cost)
   {
      set(COST_PROPERTY_NAME, cost);
   }

   public String getNote()
   {
      String note = get(NOTE_PROPERTY_NAME);
      
      if(note == null)
      {
         note = "";
      }
      return note;
   }
}
