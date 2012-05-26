package com.finfrock.moneyreport.client.data;

import java.util.Date;

public class Purchase
{
   private int key;
   private String store;
   private double cost;
   private BillType billType;
   private String note;
   private Date date;
   
   public Purchase(int key, String store, 
                   double cost, BillType billType, 
                   String note, Date date)
   {
      this.key = key;
      this.store = store;
      this.cost = cost;
      this.billType = billType;
      this.note = note;
      this.date = date;
   }
   
   public int getKey()
   {
      return key;
   }

   public void setKey(int key)
   {
      this.key = key;
   }

   public String getStore()
   {
      return store;
   }

   public void setStore(String store)
   {
      this.store = store;
   }

   public double getCost()
   {
      return cost;
   }

   public void setCost(double cost)
   {
      this.cost = cost;
   }

   public BillType getBillType()
   {
      return billType;
   }

   public void setBillType(BillType billType)
   {
      this.billType = billType;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote(String note)
   {
      this.note = note;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public Date getDate()
   {
      return date;
   }
}
