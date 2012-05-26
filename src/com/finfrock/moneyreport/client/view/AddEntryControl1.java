package com.finfrock.moneyreport.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.MatchingPurchasesRetriever;
import com.finfrock.moneyreport.client.connection.PurchaseSender;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.Purchase;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;
import com.finfrock.moneyreport.client.view.report.view.PurchasesGridView;
import com.finfrock.moneyreport.client.view.report.view.SplitPurchaseGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AddEntryControl1 extends VerticalPanel
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private ComboBox<StoreModel> storeComboBox;
   
   private ComboBox<BillTypeModel> billTypeComboBox;
   
   private NumberField costField;
   
   private DateField dateField;
   
   private TextArea noteField;
   
   private ServiceLocations serviceLocations;
   
   private List<String> stores;
   
   private BillTypeManager billTypeManager;
   
   private MatchingItemsControl matchingField;
   
   private Button submitWithMatchesButton;
   
   private SplitPurchaseGrid splitPurchaseGrid;
   
   private List<SelectionListener> selectionListeners = 
      new ArrayList<SelectionListener>();
   
   private boolean isSplit = false;
   
   private Grid fieldPanel;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public AddEntryControl1(BillTypeManager billTypeManager, 
                          ServiceLocations serviceLocations, 
                          List<String> stores)
   {
      this.serviceLocations = serviceLocations;
      this.stores = stores;
      this.billTypeManager = billTypeManager;
      
      initialize(billTypeManager, serviceLocations);
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public boolean isValid()
   {
      if (storeComboBox.isValid() && 
          billTypeComboBox.isValid() && 
          costField.isValid() && 
          dateField.isValid() && 
          noteField.isValid())
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   public String getNote()
   {
      String note = noteField.getValue();
      
      if(note == null)
      {
         note = "";
      }

      return note;
   }
   
   public Date getDate()
   {
      Date date = dateField.getValue();

      return date;
   }
   
   public void setDate(Date date)
   {
      dateField.setValue(date);
   }
   
   public double getCost()
   {
      double value = 0.0;
      Number number = costField.getValue();
      if(number != null)
      {
         value = number.doubleValue();
      }
      
      return value;
   }
   
   public void setCost(double cost)
   {
      costField.setValue(cost);
   }
   
   public String getStore()
   {
      String store = storeComboBox.getRawValue();
      
      return store;
   }
   
   public void setStore(String store)
   {
      storeComboBox.setRawValue(store);
   }
   
   public BillType getBillType()
   {
      return billTypeComboBox.getValue().getBillType();
   }
   
   public void setFocus()
   {
      storeComboBox.focus();
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void initialize(BillTypeManager billTypeManager,
                           ServiceLocations serviceLocations)
   {
      FormData formData = new FormData("-20");
      
      matchingField = new MatchingItemsControl();
      Widget storeField = createStoreField();
      Widget billTypeField = createBillTypeField(billTypeManager);
      Widget costField = createCostField();
      Widget notesField = createNotesField();
      Widget dateField = createDateField();
      
      Button okButton = new Button("OK");
      
      okButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            if(isSplit)
            {
               submitSplitPurchase();
            }
            else
            {
               if (isValid())
               {
                  submitPurchase();
               }
               else
               {
                  Window.alert("Not valid");
               }
            }
         }
      });
      
      Button cancelButton = new Button("Cancel");
      
      cancelButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {  
            finishPurchase();
         }
      });
      
      submitWithMatchesButton = new Button("Submit With Matches");
      
      submitWithMatchesButton.addSelectionListener(
         new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            sendPurchase();
         }
      });
      
      submitWithMatchesButton.setVisible(false);
      
      Button splitButton = new Button("Split");
      splitButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            toggleSplit();
         }
      });
      
      VerticalPanel labelPanel = new VerticalPanel();
      VerticalPanel controlPanel = new VerticalPanel();
      labelPanel.add(new Label("Store"));
      labelPanel.add(new Label("Cost"));
      labelPanel.add(new Label("Date"));
      labelPanel.add(new Label("Bill Type"));
      labelPanel.add(new Label("Note"));
      controlPanel.add(storeField);
      controlPanel.add(costField);
      controlPanel.add(dateField);
      controlPanel.add(billTypeField);
      controlPanel.add(notesField);
      
      fieldPanel = new Grid(5, 2);
      
      fieldPanel.setText(0, 0, "Store:");
      fieldPanel.setWidget(0, 1, storeField);
      
      fieldPanel.setText(1, 0, "Cost:");
      fieldPanel.setWidget(1, 1, costField);
      
      fieldPanel.setText(2, 0, "Date:");
      fieldPanel.setWidget(2, 1, dateField);
      
      fieldPanel.setText(3, 0, "Bill Type:");
      fieldPanel.setWidget(3, 1, billTypeField);
      
      fieldPanel.setText(4, 0, "Note:");
      fieldPanel.setWidget(4, 1, notesField);
      
      HorizontalPanel buttonPanel = new HorizontalPanel();
      
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);
      
      add(splitButton);
      add(fieldPanel);
      add(matchingField);
      add(submitWithMatchesButton);
      add(buttonPanel);
   }
   
   private void toggleSplit()
   {
      isSplit = !isSplit;
      if(isSplit)
      {
         splitPurchaseGrid = 
            new SplitPurchaseGrid(billTypeManager);
         
         splitPurchaseGrid.setTotalCost(getCost());
         
         costField.setFieldLabel("Total Cost");
         
         fieldPanel.removeRow(3);
         fieldPanel.removeRow(3);
         
         matchingField.addDisplayableReportWidget(splitPurchaseGrid);
      }
      else
      {
         costField.setFieldLabel("Cost");
         
         fieldPanel.insertRow(3);
         
         fieldPanel.setText(2, 0, "Bill Type:");
         fieldPanel.setWidget(2, 1, billTypeComboBox);

         fieldPanel.insertRow(4);
         fieldPanel.setText(3, 0, "Note:");
         fieldPanel.setWidget(3, 1, noteField);
         
         matchingField.addDisplayableReportWidget(null);
      }
   }
   
   private void submitSplitPurchase()
   {
      String store = getStore();
      Date date = getDate();
      
      List<PurchaseItem> purchaseItems = splitPurchaseGrid.getNewPurcahses();
      
      double totalCost = sumPurchaseItemCost(purchaseItems);
      PurchaseItem highestCostPurchaseItem = 
         findHighestCostPurchaseItem(purchaseItems);
      
      BillType highestCostbillType = highestCostPurchaseItem.getBillType();
      
      sendPurchase(totalCost, highestCostbillType, 
         highestCostPurchaseItem.getNote(), store, date);
      
      purchaseItems.remove(highestCostPurchaseItem);
      
      for(PurchaseItem purchaseItem : purchaseItems)
      {
         double cost = purchaseItem.getCost();
         BillType billType = purchaseItem.getBillType();
         String note = purchaseItem.getNote();
         
         sendPurchase(((-1)*cost), highestCostbillType, 
            "Subtracting other purchases", store, date);
         
         sendPurchase(cost, billType, note, store, date);
      }
      
      finishPurchase();
   }
   
   private double sumPurchaseItemCost(List<PurchaseItem> purchaseItems)
   {
      double totalCost = 0;
      
      for(PurchaseItem purchaseItem : purchaseItems)
      {
         totalCost += purchaseItem.getCost();
      }
      
      return totalCost;
   }

   private void sendPurchase(double cost, BillType billType, String note, 
                             String store, Date date)
   {
      PurchaseSender purchaseSender = new PurchaseSender(cost, store,
         note, billType, date, serviceLocations);

      purchaseSender.addReturnableListener(new Returnable<Boolean>()
      {
         @Override
         public void returned(Boolean object)
         {
            Window.alert("Submitted " + object);
         }
      });
      
      purchaseSender.send();
   }
   
   private PurchaseItem findHighestCostPurchaseItem(List<PurchaseItem> purchaseItems)
   {
      PurchaseItem highestCostPurchaseItem = null;
      
      for(PurchaseItem purchaseItem : purchaseItems)
      {
         if(highestCostPurchaseItem == null || 
               highestCostPurchaseItem.getCost() < purchaseItem.getCost())
         {
            highestCostPurchaseItem = purchaseItem;
         }
      }
      
      return highestCostPurchaseItem;
   }

   private void submitPurchase()
   {
      double cost = getCost();
      String store = getStore();
      String note = getNote();
      BillType billType = getBillType();
      Date date = getDate();
    
      MatchingPurchasesRetriever matchingPurchasesRetriever = 
         new MatchingPurchasesRetriever(billTypeManager, 
            cost, store,
            note, billType, 
            date, serviceLocations);
      
      matchingPurchasesRetriever.startLoad(new Returnable<Loadable>()
      {
         @Override
         public void returned(Loadable object)
         {
            MatchingPurchasesRetriever matchingPurchasesRetriever = 
               (MatchingPurchasesRetriever)object;
            
            List<Purchase> purchases = 
               matchingPurchasesRetriever.getObject();
            
            if(purchases.size() > 0)
            {
               List<PurchaseItem> purchaseItems = createPurchaseItems(purchases);
               
               matchingField.addDisplayableReportWidget(
                  new PurchasesGridView(purchaseItems, "", serviceLocations, 
                     billTypeManager, stores));
               
               submitWithMatchesButton.setVisible(true);
            }
            else
            {
               sendPurchase();
            }
         }
     });
   }
   
   private void sendPurchase()
   {
      double cost = getCost();
      String store = getStore();
      String note = getNote();
      BillType billType = getBillType();
      Date date = getDate();
      
      PurchaseSender purchaseSender = new PurchaseSender(cost, store,
         note, billType, date, AddEntryControl1.this.serviceLocations);

      purchaseSender.send();
      
      finishPurchase();
   }
   
   private void finishPurchase()
   {
      if(isSplit)
      {
         toggleSplit();
      }
      
      clear();
      
      storeComboBox.focus();
      
      for(SelectionListener listener : selectionListeners)
      {
         listener.componentSelected(null);
      }
   }
   
   public void addFinishListener(SelectionListener listener)
   {
      selectionListeners.add(listener);
   }
   
   public void removeFinishListener(SelectionListener listener)
   {
      selectionListeners.remove(listener);
   }
   
   public void clear()
   {
      super.clear();
      
      matchingField.addDisplayableReportWidget(null);
      submitWithMatchesButton.setVisible(false);
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
   
   private Widget createDateField()
   {
      dateField = new DateField();
      dateField.setFieldLabel("Date");
      dateField.setAllowBlank(false);
      
      return dateField;
   }
   
   private Widget createNotesField()
   {
      noteField = new TextArea();
      noteField.setPreventScrollbars(true);
      noteField.setFieldLabel("Notes");
      
      return noteField;
   }
   
   private Widget createCostField()
   {
      costField = new NumberField()
      {
         @Override
         public boolean validate() 
         {
            if (splitPurchaseGrid != null)
            {
               splitPurchaseGrid.setTotalCost(getCost());
            }
            
            return super.validate();
         }   
      };
      costField.setFieldLabel("Cost");
      costField.setAllowDecimals(true);
      costField.setAllowNegative(true);
      costField.setAllowBlank(false);
      
      return costField;
   }
   
   private Widget createBillTypeField(BillTypeManager billTypeManager)
   {      
      List<BillType> billTypes = billTypeManager.getBillTypes();
      
      Collections.sort(billTypes, new Comparator<BillType>()
      {
         @Override
         public int compare(BillType billType1, BillType billType2)
         {
            return billType1.getName().compareToIgnoreCase(billType2.getName());
         }
      });
      
      List<BillTypeModel> billTypeModels = new ArrayList<BillTypeModel>();
      
      for(BillType billType : billTypes)
      {
         BillTypeModel billTypeModel = new BillTypeModel(billType);
         billTypeModels.add(billTypeModel);
      }
      
      ListStore<BillTypeModel> billTypeListBox = new ListStore<BillTypeModel>();
      billTypeListBox.add(billTypeModels);
      
      billTypeComboBox = new ComboBox<BillTypeModel>();
      billTypeComboBox.setFieldLabel("Bill Type");
      billTypeComboBox.setDisplayField("name");
      billTypeComboBox.setTriggerAction(TriggerAction.ALL);
      billTypeComboBox.setStore(billTypeListBox);
      billTypeComboBox.setAllowBlank(false);
      billTypeComboBox.setForceSelection(true);
      
      return billTypeComboBox;
   }
   
   private Widget createStoreField()
   {
      ListStore<StoreModel> storeNameListStore = new ListStore<StoreModel>();
      
      for(String store : stores)
      {
         storeNameListStore.add(new StoreModel(store));
      }
      
      storeComboBox = new ComboBox<StoreModel>();
      storeComboBox.setFieldLabel("Store");
      storeComboBox.setDisplayField("name");
      storeComboBox.setTriggerAction(TriggerAction.ALL);
      storeComboBox.setStore(storeNameListStore);
      storeComboBox.setAllowBlank(false);
      
      return storeComboBox;
   }
   
   // --------------------------------------------------------------------------
   // Private Classes
   // --------------------------------------------------------------------------
   
   private class StoreModel extends BaseModel
   {
      public StoreModel(String name)
      {
         set("name", name);
      }
      
      public String getName()
      {
         return get("name");
      }
   }
}
