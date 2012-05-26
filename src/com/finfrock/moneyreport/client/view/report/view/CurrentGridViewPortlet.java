package com.finfrock.moneyreport.client.view.report.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.finfrock.moneyreport.client.view.report.item.CurrentViewItem;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class CurrentGridViewPortlet extends Portlet 
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private List<CurrentViewItem> currentViewItems;
 
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public CurrentGridViewPortlet(List<CurrentViewItem> currentViewItems)
   {
      this.currentViewItems = currentViewItems;
   }
   
   // --------------------------------------------------------------------------
   // Overridden Members
   // --------------------------------------------------------------------------
   
   @Override
   protected void onRender(Element parent, int index) {
     super.onRender(parent, index);
     setLayout(new FlowLayout(10));

     ListStore<CurrentViewItem> store = new ListStore<CurrentViewItem>();
     store.add(currentViewItems);

     List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

     ColumnConfig billType = new ColumnConfig("billType", "Type", 65);

     NumberFormat format = NumberFormat.getFormat("$#,##0.00;($#,##0.00)");
     
     ColumnConfig allotted = createCurrentyColumn("allotted", "Allotted", format);
     ColumnConfig spent = createCurrentyColumn("spent", "Spent", format);
     ColumnConfig amountLeft = createCurrentyColumn("amountLeft", "Amount Left", format);
     ColumnConfig average = createCurrentyColumn("average", "Average", format);
     ColumnConfig amountLeftOfAverage = createCurrentyColumn("amountLeftOfAverage", "Average Left", format);
     
     columns.add(billType);
     columns.add(allotted);
     columns.add(spent);
     columns.add(amountLeft);
     columns.add(average);
     columns.add(amountLeftOfAverage);
     ColumnModel cm = new ColumnModel(columns);

     Grid<CurrentViewItem> grid = new Grid<CurrentViewItem>(store, cm);
     grid.setStyleAttribute("borderTop", "none");
     grid.setAutoExpandColumn("amountLeftOfAverage");
     grid.setBorders(true);
     grid.setStripeRows(true);

     setHeading("Current View");
     setCollapsible(true);
     setFrame(true);
     setSize(500, 450);
     setLayout(new FitLayout());
     add(grid);
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private ColumnConfig createCurrentyColumn(String bindingName, 
      String columnName, NumberFormat format)
   {
      ColumnConfig conlumnConfig = new ColumnConfig(bindingName, columnName, 80);
      conlumnConfig.setNumberFormat(format);
      conlumnConfig.setAlignment(HorizontalAlignment.RIGHT);

      NumberField nf = new NumberField();
      nf.setAutoValidate(true);
      CellEditor ce = new CellEditor(nf);
      ce.setCancelOnEsc(true);
      conlumnConfig.setEditor(ce);

      return conlumnConfig;
   }
}
