package com.finfrock.moneyreport.client.view;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainTabPanel extends TabPanel
{
   
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private ViewFactory viewFactory;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public MainTabPanel(ViewFactory viewFactory)
   {
      setWidth(520);
      setAutoHeight(true);
      
      this.viewFactory = viewFactory;
      
      createTabs();
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private void createTabs()
   {
      TabItem tabItem1 = createAddItemTab();
      
      TabItem tabItem2 = createBankStatementTab();
      
      TabItem tabItem3 = createReportsTab();
      
      TabItem tabItem4 = createSearchTab();
                 
      add(tabItem1);
      add(tabItem2);
      add(tabItem3);
      add(tabItem4);
   }
   
   private TabItem createSearchTab()
   {
      TabItem tabItem = new TabItem("Search");
      
      
      return tabItem;
   }

   private TabItem createReportsTab()
   {
      TabItem tabItem = new TabItem("Reports");
      
      Widget reportControl = viewFactory.createReportControl();
      
      tabItem.add(reportControl);
      
      return tabItem;
   }
   
   private TabItem createBankStatementTab()
   {
      TabItem tabItem = new TabItem("Bank Statement");
      
      Widget bankStatementControl = viewFactory.createBankStatementControl();
      
      tabItem.add(bankStatementControl);
      
      return tabItem;
   }
   
   private TabItem createAddItemTab()
   {
      TabItem tabItem = new TabItem("Add Item");
      
      Widget addEntryControl = viewFactory.createAddEntry();
      
      tabItem.add(addEntryControl);
      
      return tabItem;
   }
}
