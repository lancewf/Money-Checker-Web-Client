package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.data.Purchase;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BankStatementLoader extends FormPanel
{
   public BankStatementLoader(ServiceLocations serviceLocations)
   {
      setAction(serviceLocations.getProcessBankStatementAddress());
      setEncoding(FormPanel.ENCODING_MULTIPART);
      setMethod(FormPanel.METHOD_POST);

      VerticalPanel holder = new VerticalPanel();

      FileUploadField upload = new FileUploadField();
      upload.setName("userfile");
      Button bankButton = new Button();
      bankButton.setText("Read Bankstatement");
      holder.add(upload);
      holder.add(bankButton);
      
      bankButton.addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            submit();
         }
      });

      add(holder);
   }
   
   public void setReturnable(final Returnable<List<Purchase>> returnable)
   {
      addSubmitCompleteHandler(new SubmitCompleteHandler()
      {
         @Override
         public void onSubmitComplete(SubmitCompleteEvent event)
         {
            List<Purchase> purchases = parseText(event.getResults());
            
            returnable.returned(purchases);
         }
      });
   }
   
   private List<Purchase> parseText(String rawText)
   {
      JsArray<PurchaseData> purchaseDatas = asArrayOfPurchaseData(rawText);

      List<Purchase> purchases = new ArrayList<Purchase>();
      for(int index = 0; index < purchaseDatas.length(); index++)
      {
         PurchaseData purchaseData = purchaseDatas.get(index);
         
         Date date = 
            new Date(purchaseData.getYear()-1900, purchaseData.getMonth() - 1, 
               purchaseData.getDayOfMonth());
         
         Purchase purchase = new Purchase(purchaseData.getKey(), purchaseData.getStore(), 
            purchaseData.getCost(),
            null, purchaseData.getNotes(), date);
         
         
         purchases.add(purchase);
      }
      
      return purchases;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private final native JsArray<PurchaseData> asArrayOfPurchaseData(String json) 
   /*-{
      return eval(json);
   }-*/;
}
