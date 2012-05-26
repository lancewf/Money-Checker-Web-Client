package com.finfrock.moneyreport.client.data;

import java.util.ArrayList;
import java.util.List;

public class BillType
{
   private String name;
   private String description;
   private int key;
   private List<AllottedAmount> allottedAmounts = new ArrayList<AllottedAmount>();
   
   public BillType(int key, String name, String description)
   {
      this.key = key;
      this.name = name;
      this.description = description;
   }
   
   public void addAllottedAmount(AllottedAmount allottedAmount)
   {
      allottedAmounts.add(allottedAmount);
   }
   
   public List<AllottedAmount> getAllottedAmounts()
   {
      return allottedAmounts;
   }
   
   public String getName()
   {
      return name;
   }
   
   public String getDescription()
   {
      return description;
   }
   
   public String toString()
   {
      return name;
   }

   public int getKey()
   {
      return key;
   }
}
