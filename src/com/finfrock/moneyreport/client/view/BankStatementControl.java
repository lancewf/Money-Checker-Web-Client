package com.finfrock.moneyreport.client.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.MatchingPurchasesRetriever;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.Purchase;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;
import com.finfrock.moneyreport.client.view.report.view.PurchasesGridView;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BankStatementControl extends FormPanel
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private FormData formData;
   private DatePicker datePicker;
   private TextField<String> storeField;
   private NumberField costField;
   private MatchingItemsControl matchingField;
   private BankEntryControl bankEntryControl;
   private BillTypeManager billTypeManager;
   private ServiceLocations serviceLocations;
   private Widget loadingPanel;
   private Widget emptyPanel;
   private List<String> stores;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public BankStatementControl(BillTypeManager billTypeManager, 
                               ServiceLocations serviceLocations, 
                               List<String> stores)
   {
      this.billTypeManager = billTypeManager;
      this.serviceLocations = serviceLocations;
      this.stores = stores;
      initialize();
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public void setPurchases(List<Purchase> purchases)
   {
      bankEntryControl.setPurchases(purchases);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private void initialize()
   {
      formData = new FormData("-20");
      datePicker = new DatePicker();
      costField = createCostField();
      storeField = createTextField();
      matchingField = new MatchingItemsControl();
      loadingPanel = createLoadingPanel();
      emptyPanel = createEmptyPanel();
      
      bankEntryControl = new BankEntryControl(billTypeManager, serviceLocations, 
         stores, matchingField,
         new Returnable<Purchase>()
      {
         @Override
         public void returned(Purchase purchase)
         {
            setCurrentPurchase(purchase);
         }
      });
      
      add(datePicker, formData);
      add(costField, formData);
      add(storeField, formData);
      add(bankEntryControl, formData);
      add(matchingField, formData);
   }
   
   private void setCurrentPurchase(Purchase purchase)
   {
      matchingField.addDisplayableReportWidget(loadingPanel);
      
      MatchingPurchasesRetriever matchingPurchasesRetriever = 
         new MatchingPurchasesRetriever(billTypeManager, purchase.getCost(), 
            purchase.getStore(), purchase.getNote(), null, purchase.getDate(), 
            serviceLocations);
      
      matchingPurchasesRetriever.startLoad(new Returnable<Loadable>()
      {
         @Override
         public void returned(Loadable loadable)
         {
            MatchingPurchasesRetriever retriever = 
               (MatchingPurchasesRetriever)loadable;
            
            List<Purchase> purchases = retriever.getObject();
            
            if (purchases.size() > 0)
            {
               List<PurchaseItem> purchaseItems = createPurchaseItems(purchases);
               matchingField.addDisplayableReportWidget(new PurchasesGridView(
                  purchaseItems, "", serviceLocations, 
                  billTypeManager, stores));
            }
            else
            {
               matchingField.addDisplayableReportWidget(emptyPanel);
            }
         }
      });
      
      costField.setValue(purchase.getCost());
      datePicker.setValue(purchase.getDate());
      storeField.setValue(purchase.getStore());
   }

   private VerticalPanel createLoadingPanel()
   {
      VerticalPanel loadingPanel = new VerticalPanel();
      Label label = new Label("Loading...");

      loadingPanel.add(label);
      
      loadingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      return loadingPanel;
   }
   
   private VerticalPanel createEmptyPanel()
   {
      VerticalPanel loadingPanel = new VerticalPanel();
      Label label = new Label("No Matches Found");

      loadingPanel.add(label);
      
      loadingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      return loadingPanel;
   }
   
   private List<PurchaseItem> createPurchaseItems(List<Purchase> purchases)
   {
      List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
      
      for (Purchase purchase : purchases)
      {
         PurchaseItem purchaseItem = new PurchaseItem(purchase.getKey(),
            purchase.getStore(), purchase.getCost(), 
            purchase.getBillType(), 
            purchase.getNote(), purchase.getDate());

         purchaseItems.add(purchaseItem);
      }
      
      return purchaseItems;
   }
   
   private TextField<String> createTextField()
   {
      TextField<String> textField = new TextField<String>();
      textField.setFieldLabel("Store");
      
      return textField;
   }
   
   private NumberField createCostField()
   {
      NumberField costField = new NumberField();
      costField.setFieldLabel("Cost");
      
      return costField;
   }
}
