package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.moneyreport.client.data.BillType;

public class BillTypeManager
{
   private List<BillType> billTypes = new ArrayList<BillType>();
   
   public BillTypeManager(List<BillType> billTypes)
   {
      this.billTypes = billTypes;
   }
   
   public BillType getBillType(int billTypeKey)
   {
      for(BillType billType : billTypes)
      {
         if(billType.getKey() == billTypeKey)
         {
            return billType;
         }
      }
      
      return null;
   }

   public List<BillType> getBillTypes()
   {
      return new ArrayList<BillType>(billTypes);
   }

}
