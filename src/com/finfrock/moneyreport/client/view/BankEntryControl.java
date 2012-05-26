package com.finfrock.moneyreport.client.view;

import java.util.List;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.Purchase;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class BankEntryControl extends HorizontalPanel
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private List<Purchase> purchases;
   private int currentIndex;
   private Label countLabel;
   private Returnable<Purchase> listener;
   private AddEntryControl addEntryControl;
   private MatchingItemsControl matchingField;
   private Button leftButton;
   private Button rightButton;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public BankEntryControl(BillTypeManager billTypeManager, 
                           ServiceLocations serviceLocations, 
                           List<String> stores, 
                           MatchingItemsControl matchingField,
                           Returnable<Purchase> listener)
   {         
      this.listener = listener;
      this.matchingField = matchingField;
      this.addEntryControl = new AddEntryControl(
         billTypeManager, serviceLocations, stores);
      
      initialize();
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public void setPurchases(List<Purchase> purchases)
   {
      this.purchases = purchases;
      currentIndex = 0;
      indexChanged();
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private Purchase getCurrentPurchase()
   {
      return purchases.get(currentIndex);
   }
   
   private void initialize()
   {
      leftButton = new Button("<");
      
      leftButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            decreaseIndex();
         }
      });
      
      rightButton = new Button(">");
      
      rightButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            increaseIndex();
         }
      });
      
      Button addButton = new Button("Add");
      
      addButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            addEntryControl.clear();
            
            Purchase currentPurchase = getCurrentPurchase();
            
            addEntryControl.setCost(currentPurchase.getCost());
            addEntryControl.setStore(currentPurchase.getStore());
            addEntryControl.setDate(currentPurchase.getDate());
            
            matchingField.addDisplayableReportWidget(addEntryControl);
            
            addEntryControl.addFinishListener(new SelectionListener()
            {
               @Override
               public void componentSelected(ComponentEvent ce)
               {
                  matchingField.addDisplayableReportWidget(null);
                  indexChanged();
                  rightButton.focus();
               }
            });
            
            addEntryControl.setFocus();
         }
      });
      
      countLabel = new Label("");
      
      add(countLabel);
      add(leftButton);
      add(rightButton);
      add(addButton);
   }
   
   private void decreaseIndex()
   {
      if(currentIndex <= 0)
      {
         if(getTotalAmount() > 0)
         {
            currentIndex = getTotalAmount() - 1;
         }
         else
         {
            currentIndex = 0;
         }
      }
      else
      {
         currentIndex--;
      }
      
      indexChanged();
   }
   
   private void increaseIndex()
   {
      if(currentIndex >= getTotalAmount())
      {
         currentIndex = 0;
      }
      else
      {
         if(getTotalAmount() > 0)
         {
            currentIndex++;
         }
         else
         {
            currentIndex = 0;
         }
      }
      
      indexChanged();
   }
   
   private void indexChanged()
   {
      if(getTotalAmount() > 0)
      {
         countLabel.setText((1 + currentIndex) +" of " + getTotalAmount());
      }
      else
      {
         countLabel.setText("0 of 0");
      }
      
      listener.returned(purchases.get(currentIndex));
   }

   private int getTotalAmount()
   {
      return purchases.size();
   }
}
