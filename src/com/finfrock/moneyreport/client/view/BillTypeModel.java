package com.finfrock.moneyreport.client.view;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.finfrock.moneyreport.client.data.BillType;

public class BillTypeModel extends BaseModel
{

   private BillType billType;
   
   public BillTypeModel(BillType billType)
   {
      this.billType = billType;
      set("name", billType.getName());
      set("allottedAmounts", billType.getAllottedAmounts());
      set("description", billType.getDescription());
      set("key", billType.getKey());
   }

   public BillType getBillType()
   {
      return billType;
   }

   public String getName()
   {
      return get("name");
   }
   
}
