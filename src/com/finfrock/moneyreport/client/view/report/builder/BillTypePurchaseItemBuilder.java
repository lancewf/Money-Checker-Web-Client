package com.finfrock.moneyreport.client.view.report.builder;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.DisplayPanelable;
import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.BillTypePurchasesRetriever;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.Purchase;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;
import com.finfrock.moneyreport.client.view.report.view.BillTypePurchaseGrid;
import com.finfrock.moneyreport.client.view.report.view.BillTypePurchasePortlet;

public class BillTypePurchaseItemBuilder implements DisplayableReport
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private BillTypePurchasesRetriever retriever;
   private String name;
   private ServiceLocations serviceLocations;

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public BillTypePurchaseItemBuilder(BillTypeManager billTypeManager,
         ServiceLocations serviceLocations, BillType billType)
   {
      this.name = billType.getName();
      this.serviceLocations = serviceLocations;
      retriever = new BillTypePurchasesRetriever(serviceLocations, billTypeManager, 
         billType);
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   @Override
   public String getName()
   {
      return name;
   }

   @Override
   public void show(final DisplayPanelable displayPanel)
   {
      retriever.startLoad(new Returnable<Loadable>()
      {
         @Override
         public void returned(Loadable loadable)
         {
            BillTypePurchasesRetriever purchasesRetriever = 
               (BillTypePurchasesRetriever) loadable;

            List<Purchase> purchases = purchasesRetriever.getObject();
            List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();

            for (Purchase purchase : purchases)
            {
               PurchaseItem purchaseItem = new PurchaseItem(purchase.getKey(),
                  purchase.getStore(), purchase.getCost(), purchase.getBillType(), 
                  purchase.getNote(), purchase.getDate());

               purchaseItems.add(purchaseItem);
            }

            BillTypePurchasePortlet grid = new BillTypePurchasePortlet(purchaseItems, 
               name, serviceLocations);

            displayPanel.addDisplayableReportWidget(grid);
         }
      });
   }
}
