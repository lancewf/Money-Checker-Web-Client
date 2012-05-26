package com.finfrock.moneyreport.client.view.report.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.RowEditorEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.GroupSummaryView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.grid.SummaryColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.SummaryRenderer;
import com.extjs.gxt.ui.client.widget.grid.SummaryType;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.PurchaseDeleter;
import com.finfrock.moneyreport.client.connection.PurchaseModifier;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.PurchaseItemComparer;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;


public class BillTypePurchasePortlet extends Portlet 
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private List<PurchaseItem> purchaseItems;
   private String title;
   private ServiceLocations serviceLocations;
   private EditorGrid<PurchaseItem> grid;
   private RowEditor<PurchaseItem> rowEditor;
   private GroupingStore<PurchaseItem> store;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public BillTypePurchasePortlet(List<PurchaseItem> purchaseItems, String title, 
                               ServiceLocations serviceLocations)
   {
      this.purchaseItems = purchaseItems;
      this.title = title;
      this.serviceLocations = serviceLocations;
   }
   
   // --------------------------------------------------------------------------
   // Protected Members
   // --------------------------------------------------------------------------

   @Override
   protected void onRender(Element parent, int index) 
   {
      super.onRender(parent, index);
      setLayout(new FlowLayout(10));

      store = new GroupingStore<PurchaseItem>();
      store.groupBy("billType");
      store.add(purchaseItems);

      List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

      SummaryColumnConfig<Integer> note = new SummaryColumnConfig<Integer>(
         "note", "Note", 65);
      note.setEditor(new CellEditor(new TextField<String>()));
      
      SummaryColumnConfig<Integer> billtype = new SummaryColumnConfig<Integer>(
         "billType", "Bill Type", 0);
      billtype.setRenderer(new GridCellRenderer<PurchaseItem>()
      {
         public String render(PurchaseItem model,
                              String property,
                              ColumnData config,
                              int rowIndex,
                              int colIndex,
                              ListStore<PurchaseItem> store,
                              Grid<PurchaseItem> grid)
         {
            BillType billType = model.getBillType();
            
            if(billType != null)
            {
               return billType.getName();
            }
            else
            {
               return "-";
            }
         }
      });
      
      SummaryColumnConfig<Double> storeColumn = new SummaryColumnConfig<Double>(
            PurchaseItem.STORE_PROPERTY_NAME, "Store", 80);
      TextField<String> storeTextField = new TextField<String>();
      storeTextField.setAllowBlank(false);
      storeColumn.setEditor(new CellEditor(storeTextField));
      
      SummaryColumnConfig<Integer> date = new SummaryColumnConfig<Integer>(
         "date", "Date", 80);
      date.setSummaryType(SummaryType.COUNT);
      date.setSummaryRenderer(new SummaryRenderer()
      {
         public String render(Number value, Map<String, Number> data)
         {
            if(value == null)
            {
               return "";
            }
            if (value.intValue() > 1)
            {
               return "(" + value.intValue() + " Purchases)";
            }
            else
            {
               return "(1 Purchase)";
            }
         }
      });
      date.setRenderer(new GridCellRenderer<PurchaseItem>()
      {
         public String render(PurchaseItem model,
                              String property,
                              ColumnData config,
                              int rowIndex,
                              int colIndex,
                              ListStore<PurchaseItem> store,
                              Grid<PurchaseItem> grid)
         {
            return DateTimeFormat.getShortDateFormat().format(model.getDate());
         }
      });
      DateField dateField = new DateField();
      dateField.getPropertyEditor().setFormat(DateTimeFormat.getFormat("MM/dd/y"));
      date.setEditor(new CellEditor(dateField));
      
      NumberFormat format = NumberFormat.getFormat("$#,##0.00;($#,##0.00)");

      SummaryColumnConfig<Double> cost = new SummaryColumnConfig<Double>(
         "cost", "Cost", 80);
      cost.setNumberFormat(format);
//      cost.setSummaryFormat(format); // throws error
      cost.setSummaryType(SummaryType.SUM);
      cost.setAlignment(HorizontalAlignment.RIGHT);

      NumberField nf = new NumberField();
      nf.setAutoValidate(true);
      CellEditor ce = new CellEditor(nf);
      ce.setCancelOnEsc(true);
      cost.setEditor(ce);

      columns.add(billtype);
      columns.add(date);
      columns.add(storeColumn);
      columns.add(cost);
      columns.add(note);
      ColumnModel cm = new ColumnModel(columns);

      GroupSummaryView summary = new GroupSummaryView();
      summary.setForceFit(true);
      summary.setShowGroupedColumn(false);
      summary.setStartCollapsed(true);

      rowEditor = new RowEditor<PurchaseItem>();
      grid = new EditorGrid<PurchaseItem>(store, cm);
      grid.setBorders(true);
      grid.setAutoExpandColumn("note");
      grid.setView(summary);
      grid.getView().setShowDirtyCells(false);
      grid.addPlugin(rowEditor);
      grid.setSelectionModel(new GridSelectionModel<PurchaseItem>());
      
      grid.addListener(Events.AfterEdit, new Listener<GridEvent<PurchaseItem>>()
      {
         @Override
         public void handleEvent(GridEvent<PurchaseItem> gridEvent)
         {
            Record record = gridEvent.getRecord();

            saveRecord(record);
         }
      });

      rowEditor.addListener(Events.AfterEdit, new Listener<RowEditorEvent>()
      {
         @Override
         public void handleEvent(RowEditorEvent rowEditorEvent)
         {
            Record record = rowEditorEvent.getRecord();

            saveRecord(record);
         }
      });
      
      ToolBar toolBar = new ToolBar();
      Button add = new Button("Delete");
      add.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            deleteSelected();
         }
      });
      
      toolBar.add(add);
      
      setTopComponent(toolBar);
      setHeading(title);
      setCollapsible(false);
      setFrame(true);
      setSize(500, 450);
      setLayout(new FitLayout());
      add(grid);
   }
   
   private void deleteSelected()
   {
      final GridSelectionModel<PurchaseItem> selectionModel = grid
         .getSelectionModel();
      final PurchaseItem selectedPurchaseItem = selectionModel
         .getSelectedItem();

      if (selectedPurchaseItem != null)
      {
         Returnable<Boolean> reponseReturnable = new Returnable<Boolean>()
         {
            @Override
            public void returned(Boolean object)
            {
               rowEditor.stopEditing(false);

               store.remove(selectedPurchaseItem);

               if (store.getCount() > 0)
               {
                  selectionModel.select(0, false);
               }
            }
         };

         PurchaseDeleter purchaseDeleter = new PurchaseDeleter(
            selectedPurchaseItem.getKey(), serviceLocations);

         purchaseDeleter.addReturnableListener(reponseReturnable);
         
         purchaseDeleter.send();
      }
   }
   
   private void saveRecord(Record record)
   {
      if(record.isDirty())
      {
         Returnable<Boolean> reponseReturnable = new Returnable<Boolean>()
         {
            @Override
            public void returned(Boolean object)
            {
               if(object)
               {
                  Window.alert("success");
               }
               else
               {
                  Window.alert("failure");
               }
            }
         };
         
         PurchaseItem purchaseItem = (PurchaseItem)record.getModel();
         
         PurchaseItemComparer purchaseItemComparer = 
            new PurchaseItemComparer(purchaseItem, record);
         
         int key = purchaseItemComparer.getKey();
         double cost = purchaseItemComparer.getCost();
         String store = purchaseItemComparer.getStore();
         String note = purchaseItemComparer.getNote();
         BillType billType = purchaseItemComparer.getBillType();
         Date date = purchaseItemComparer.getDate();
         
         PurchaseModifier purchaseModifier = new PurchaseModifier(
            key, cost, store, note, billType, date, serviceLocations);
         
         purchaseModifier.addReturnableListener(reponseReturnable);
         
         purchaseModifier.send();
      }
   }
}

