package com.finfrock.moneyreport.client.data;

import java.util.Date;

public class AllottedAmount
{
   private int key;
   private double amount;
   private BillType billType;
   private Date startDate;
   private Date endDate;
   
   public AllottedAmount(int key, double amount, BillType billType, 
                         Date startDate, Date endDate)
   {
      this.key = key;
      this.amount = amount;
      this.billType = billType;
      this.startDate = startDate;
      this.endDate = endDate;
   }
   
   public int getKey()
   {
      return key;
   }
   
   public double getAmount()
   {
      return amount;
   }
   
   public BillType getBillType()
   {
      return billType;
   }
   
   public Date getStartDate()
   {
      return startDate;
   }
   
   public Date getEndDate()
   {
      return endDate;
   }
}
