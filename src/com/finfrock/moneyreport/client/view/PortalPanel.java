package com.finfrock.moneyreport.client.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.CheckChangedEvent;
import com.extjs.gxt.ui.client.event.CheckChangedListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.DisplayPanelable;
import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.connection.BankStatementLoader;
import com.finfrock.moneyreport.client.data.Purchase;
import com.finfrock.moneyreport.client.view.report.DisplayableReportBuilder;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class PortalPanel extends LayoutContainer  implements DisplayPanelable
{

   private ViewFactory viewFactory;
   private List<DisplayableReport> displayableReports;
   private Portal portal;
   private UserPanel userPanel;
   
   public PortalPanel(ViewFactory viewFactory, UserPanel userPanel)
   {
      this.viewFactory = viewFactory;
      
      displayableReports = viewFactory.createDisplayableReports();
      this.userPanel = userPanel;
   }
   
   @Override
   protected void onRender(Element parent, int index) {
     super.onRender(parent, index);
     setLayout(new BorderLayout());

     ContentPanel west = new ContentPanel();
     west.setBodyBorder(false);
     west.setHeading("");
     west.setLayout(new AccordionLayout());

     ContentPanel nav = new ContentPanel();
     nav.setHeading("Reports");
     nav.setBorders(false);

     nav.setHeight(540);
     nav.setBodyStyle("fontSize: 12px; padding: 6px; text-align: left");
     
     nav.add(createTree());
     west.add(nav);

     ContentPanel settings = new ContentPanel();
     settings.setHeading("Other");
     settings.setBorders(false);
     settings.setBodyStyle("fontSize: 12px; padding: 6px; text-align: left");
     settings.setHeight(200);

     Button addEntryButton = new Button("Add Entry");
     addEntryButton.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
           @Override
           public void componentSelected(ButtonEvent ce)
           {
              Widget addEntryControl = viewFactory.createAddEntry();
              
              Portlet portlet = new Portlet();
              portlet.setHeading("Add Entry");
              configPanel(portlet);
              portlet.setLayout(new FitLayout());
              portlet.add(addEntryControl);
              portlet.setHeight(520);
              portal.add(portlet, 0);
           }
        });
     
     settings.add(userPanel);
     settings.add(addEntryButton);
     settings.add(createBankLoader());
     settings.add(createReportControl());
     west.add(settings);

     BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200, 100, 300);
     westData.setMargins(new Margins(5, 0, 5, 5));
     westData.setCollapsible(true);

     portal = new Portal(2);
     portal.setBorders(true);
     portal.setStyleAttribute("backgroundColor", "white");
     portal.setColumnWidth(0, .40);
     portal.setColumnWidth(1, .60);

     BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
     centerData.setMargins(new Margins(5));

     add(west, westData);
     add(portal, centerData);
   }
   
   private Widget createReportControl()
   {
      ListBox listBox = new ListBox();
      
      for(DisplayableReport displayableReport : displayableReports)
      {
         listBox.addItem(displayableReport.getName());
      }
      
      listBox.addChangeHandler(new ChangeHandler()
      {
         @Override
         public void onChange(ChangeEvent event)
         {
            ListBox listBox = (ListBox)event.getSource();
            
            String selectedText = listBox.getItemText(
               listBox.getSelectedIndex());
            
            DisplayableReport displayableReport = 
               findDisplayableReport(selectedText);

            if(displayableReport != null)
            {
               displayableReport.show(PortalPanel.this);
            }
         }
      });
      
      return listBox;
   }

   private Widget createBankLoader()
   {
      BankStatementLoader bankStatementLoader = viewFactory.createBankStatementLoader();
      
      bankStatementLoader.setReturnable(new Returnable<List<Purchase>>()
      {
            @Override
            public void returned(List<Purchase> purchases)
            {
               BankStatementControl bankStatementControl = 
                  viewFactory.createBankStatementControl();
               
               bankStatementControl.setPurchases(purchases);
               
               Portlet portlet = new Portlet();
               portlet.setHeading("Bank Statement");
               configPanel(portlet);
               portlet.setLayout(new FitLayout());
               portlet.add(bankStatementControl);
               portlet.setHeight(650);
               portal.add(portlet, 1);
            }
      });
      
      return bankStatementLoader;
   }

   private Widget createTree()
   {
      Folder model = getTreeModel();

      TreeStore<ModelData> store = new TreeStore<ModelData>();
      store.add(model.getChildren(), true);

      TreePanel<ModelData> tree = new TreePanel<ModelData>(store);
      tree.setHeight(510);
      tree.setDisplayProperty("name");
      //tree.getStyle().setLeafIcon(Resources.ICONS.music());
      
      tree.getSelectionModel().addSelectionChangedListener(
         new SelectionChangedListener<ModelData>(){

         @Override
         public void selectionChanged(SelectionChangedEvent<ModelData> se)
         {
            ModelData modelData = se.getSelectedItem();
            
            if(modelData instanceof DisplayableReportTreeModel)
            {
               DisplayableReportTreeModel selectedReportTreeModel = 
                  (DisplayableReportTreeModel) modelData;
               
               DisplayableReport displayableReport = selectedReportTreeModel.getDisplayableReport();

               displayableReport.show(PortalPanel.this);
            }
         }
      });

      return tree;
    }
    
    public Folder getTreeModel() 
    {
       DisplayableReportBuilder builder = viewFactory.createDisplayableReportBuilder();
       Folder root = new Folder("root");
       
       Folder currentView = new Folder("Current View");
       
       for(DisplayableReport report : builder.buildCurrentView())
       {
          DisplayableReportTreeModel treeModel = new DisplayableReportTreeModel(report);
          
          currentView.add(treeModel);
       }
       
       root.add(currentView);
       
       Folder monthView = new Folder("Month View");
       
       for(DisplayableReport report : builder.buildMonthDisplayReports())
       {
          DisplayableReportTreeModel treeModel = new DisplayableReportTreeModel(report);
          
          monthView.add(treeModel);
       }
  
       root.add(monthView);
       
       Folder billTypeView = new Folder("Bill Type View");
       
       for(DisplayableReport report : 
          builder.buildBillTypePurchasesDisplayReports())
       {
          DisplayableReportTreeModel treeModel = 
             new DisplayableReportTreeModel(report);
          
          billTypeView.add(treeModel);
       }
  
       root.add(billTypeView);

       return root;
     }

   private DisplayableReport findDisplayableReport(String name)
   {
      for(DisplayableReport displayableReport : displayableReports)
      {
         if(displayableReport.getName().equals(name))
         {
            return displayableReport;
         }
      }
      
      return null;
   }

   private String getBogusText() 
   {
     return "<div class=text style='padding: 5px'> Lance Test</div>";
   }

   private void configPanel(final ContentPanel panel) {
     panel.setCollapsible(true);
     panel.setAnimCollapse(false);
     panel.getHeader().addTool(new ToolButton("x-tool-gear"));
     panel.getHeader().addTool(
         new ToolButton("x-tool-close", new SelectionListener<IconButtonEvent>() {

           @Override
           public void componentSelected(IconButtonEvent ce) {
             panel.removeFromParent();
           }

         }));
   }

   @Override
   public void addDisplayableReportWidget(Widget widget)
   {
      Portlet portlet = (Portlet)widget;
      configPanel(portlet);
      portlet.setLayout(new FitLayout());
      portal.add((Portlet)widget, 1);
   }
}
