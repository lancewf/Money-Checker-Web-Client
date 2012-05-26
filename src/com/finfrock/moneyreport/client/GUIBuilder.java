package com.finfrock.moneyreport.client;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.finfrock.client.DataChangeListener;
import com.finfrock.client.FacebookConnectionStatus;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.DataBuilder;
import com.finfrock.moneyreport.client.connection.LoadManager;
import com.finfrock.moneyreport.client.connection.ReleaseServiceLocations;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.connection.TestServiceLocations;
import com.finfrock.moneyreport.client.view.MainTabPanel;
import com.finfrock.moneyreport.client.view.PortalPanel;
import com.finfrock.moneyreport.client.view.UserPanel;
import com.finfrock.moneyreport.client.view.ViewFactory;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GUIBuilder 
{
   // --------------------------------------------------------------------------
   // Private Class Data
   // --------------------------------------------------------------------------

   private static final String DIV_TAG = "main";
  
   private UserPanel userPanel;
   
   private FacebookConnectionStatus facebookConnectionStatus;
   
  // --------------------------------------------------------------------------
  // Public Members
  // --------------------------------------------------------------------------
 
   public void build()
   {
      facebookConnectionStatus = 
         new FacebookConnectionStatus();
      
      if (facebookConnectionStatus.isloggedin())
      {
         loadPanels();
      }
      else
      {  
         userPanel = new UserPanel(facebookConnectionStatus);
         
         RootPanel.get(DIV_TAG).add(userPanel);
         
         facebookConnectionStatus.addLoggedinListener(new DataChangeListener()
         {
            @Override
            public void onDataChange()
            {
               loadPanels();
            }
         });
      }
   }
  
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private void loadPanels()
   {
      final ServiceLocations serviceLocations = new ReleaseServiceLocations();

      //final ServiceLocations serviceLocations = new TestServiceLocations();
      
      final VerticalPanel loadingPanel = createLoadingPanel();

      RootPanel.get(DIV_TAG).add(loadingPanel);

      LoadManager loadManager = new LoadManager(serviceLocations);

      loadManager.startLoad(new Returnable<DataBuilder>()
      {
         @Override
         public void returned(DataBuilder dataBuilder)
         {
            RootPanel.get(DIV_TAG).remove(loadingPanel);
            
            ViewFactory viewFactory = new ViewFactory(
               dataBuilder.getBillTypeManager(), 
               serviceLocations, 
               dataBuilder.getStores());

            if(userPanel == null)
            {
               userPanel = new UserPanel(facebookConnectionStatus);
            }
            
            PortalPanel protal = new PortalPanel(viewFactory, userPanel);

            RootPanel.get(DIV_TAG).add(protal);
         }
      });
   }
   
   private VerticalPanel createLoadingPanel()
   {
      VerticalPanel loadingPanel = new VerticalPanel();
      Label label = new Label("Loading...");

      loadingPanel.add(label);

      return loadingPanel;
   }
}
