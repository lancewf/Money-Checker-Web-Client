package com.finfrock.moneyreport.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class MoneyMenu extends MenuBar
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
  
   private MenuBar fileMenuBar;
   private MenuBar toolsMenuBar;
   private MenuBar displayMenuBar;
  
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
  
   public MoneyMenu()
   {
      fileMenuBar = createFileMenuBar();
      toolsMenuBar = createToolsMenuBar();
      displayMenuBar = createDisplayMenuBar();

      addItem("File", fileMenuBar);
      addItem("Tools", toolsMenuBar);
      addItem("Display", displayMenuBar);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
  
   private MenuBar createFileMenuBar()
   {
      MenuBar menuBar = new MenuBar();
      
      menuBar.addItem("Log Out", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      menuBar.addItem("Add Entry", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      return menuBar;
   }

   private MenuBar createDisplayMenuBar()
   {
      MenuBar menuBar = new MenuBar();
      
      menuBar.addItem("Pie Chart", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      menuBar.addItem("Bar Chart", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      menuBar.addItem("Report", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      return menuBar;
   }

   private MenuBar createToolsMenuBar()
   {
      MenuBar menuBar = new MenuBar();
      
      menuBar.addItem("Lookup Entries", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      menuBar.addItem("Edit Bill Types", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      menuBar.addItem("Read Bank Statement", new Command()
      {
         @Override
         public void execute()
         {
            Window.alert("Clicked");
         }
      });
      
      return menuBar;
   }
}
