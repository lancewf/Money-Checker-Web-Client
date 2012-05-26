package com.finfrock.moneyreport.client.view.report.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.RowEditorEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.PurchaseDeleter;
import com.finfrock.moneyreport.client.connection.PurchaseModifier;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.data.BillType;
import com.finfrock.moneyreport.client.data.PurchaseItemComparer;
import com.finfrock.moneyreport.client.view.BillTypeModel;
import com.finfrock.moneyreport.client.view.report.item.PurchaseItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;

public class SplitPurchaseGrid extends LayoutContainer 
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private RowEditor<PurchaseItem> rowEditor;
   private EditorGrid<PurchaseItem> grid;
   private ListStore<PurchaseItem> store;
   private BillTypeManager billTypeManager;
   private double totalCost;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public SplitPurchaseGrid(BillTypeManager billTypeManager)
   {
      this.billTypeManager = billTypeManager;
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public List<PurchaseItem> getNewPurcahses()
   {
      return store.getModels();
   }
   
   public void setTotalCost(double totalCost)
   {
      this.totalCost = totalCost;
      
      if(store != null && store.getModels().size() > 0)
      {
         setLastRecordsCost();
      }
   }
   
   // --------------------------------------------------------------------------
   // Overridden Members
   // --------------------------------------------------------------------------
   
   @Override
   protected void onRender(Element parent, int index)
   {
      super.onRender(parent, index);
      setLayout(new FlowLayout(10));
      
      store = new ListStore<PurchaseItem>();
      
      store.add(new PurchaseItem(-1, "", 0, null, "", new Date()));
      store.add(new PurchaseItem(-1, "", totalCost, null, "", new Date()));

      NumberFormat format = NumberFormat.getFormat("$#,##0.00;($#,##0.00)");

      ColumnConfig note = new ColumnConfig("note", "Note", 15);
      note.setEditor(new CellEditor(new TextField<String>()));
      
      ColumnConfig billtype = new ColumnConfig("billType", "Bill Type", 80);
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
      
      List<BillType> billTypes = billTypeManager.getBillTypes();
      
      Collections.sort(billTypes, new Comparator<BillType>()
      {
         @Override
         public int compare(BillType billType1, BillType billType2)
         {
            return billType1.getName().compareToIgnoreCase(billType2.getName());
         }
      });
      
      final List<BillTypeModel> billTypeModels = new ArrayList<BillTypeModel>();
      
      for(BillType billType : billTypes)
      {
         BillTypeModel billTypeModel = new BillTypeModel(billType);
         billTypeModels.add(billTypeModel);
      }
      
      ListStore<BillTypeModel> billTypeListBox = new ListStore<BillTypeModel>();
      billTypeListBox.add(billTypeModels);
      
      final ComboBox<BillTypeModel> billTypeComboBox = new ComboBox<BillTypeModel>();
      billTypeComboBox.setFieldLabel("Bill Type");
      billTypeComboBox.setDisplayField("name");
      billTypeComboBox.setTriggerAction(TriggerAction.ALL);
      billTypeComboBox.setStore(billTypeListBox);
      billTypeComboBox.setAllowBlank(false);
      billTypeComboBox.setForceSelection(true);

      CellEditor editor = new CellEditor(billTypeComboBox){
        @Override
        public Object preProcessValue(Object value) 
        {
          if (value == null) {
            return value;
          }
          String name = value.toString();
          for(BillTypeModel billTypeModel : billTypeModels)
          {
             if(name.equals(billTypeModel.getName()))
             {
                return billTypeModel;
             }
          }
          
          return null;
        }

        @Override
        public Object postProcessValue(Object value) 
        {
          if (value == null) {
            return value;
          }
          return ((BillTypeModel) value).getBillType();
        }
      };
      
      billtype.setEditor(editor);
      
      ColumnConfig cost = new ColumnConfig("cost", "Cost", 55);
      cost.setNumberFormat(format);
      cost.setAlignment(HorizontalAlignment.RIGHT);
      cost.setEditor(new CellEditor(new NumberField()));

      List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
      columns.add(billtype);
      columns.add(cost);
      columns.add(note);

      ColumnModel cm = new ColumnModel(columns);

      rowEditor = new RowEditor<PurchaseItem>();
      grid = new EditorGrid<PurchaseItem>(store,
         cm);
      grid.setStyleAttribute("borderTop", "none");
      grid.setAutoExpandColumn("note");
      grid.setBorders(false);
      grid.setStripeRows(true);
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
      Button addButton = new Button("Add");
      addButton.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            addPurchase();
         }
      });
      
      toolBar.add(addButton);
      
      Button delete = new Button("Delete");
      delete.addSelectionListener(new SelectionListener<ButtonEvent>()
      {
         @Override
         public void componentSelected(ButtonEvent ce)
         {
            deleteSelected();
         }
      });
      
      toolBar.add(delete);

      ContentPanel panel = new ContentPanel();
      panel.setTopComponent(toolBar);
      panel.setHeading("");
      panel.setCollapsible(false);
      panel.setFrame(false);
      panel.setSize(300, 200);
      panel.setLayout(new FitLayout());
      panel.setBodyBorder(false);
      panel.setHeaderVisible(false);
      panel.add(grid);
      add(panel);
   }
   
   private void addPurchase()
   {
      rowEditor.stopEditing(false);
      
      PurchaseItem purchaseItem = 
         new PurchaseItem(-1, "", 0, null, "", new Date());
      
      store.insert(purchaseItem, 0);
      
      rowEditor.startEditing(0 , true);
   }
   
   private void saveRecord(Record record)
   {
      PurchaseItem lastPurchaseItem = getLastPurchaseItem();
      
      if(record.getModel() != lastPurchaseItem)
      {
         setLastRecordsCost();
      }
   }
   
   private PurchaseItem getLastPurchaseItem()
   {
      PurchaseItem lastPurchaseItem = 
         store.getModels().get(store.getModels().size() - 1);
      
      return lastPurchaseItem;
   }
   
   private void setLastRecordsCost()
   {
      List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>(store
         .getModels());

      PurchaseItem lastPurchaseItem = getLastPurchaseItem();

      purchaseItems.remove(lastPurchaseItem);

      double leftOver = totalCost;

      for (PurchaseItem purchaseItem : purchaseItems)
      {
         leftOver -= purchaseItem.getCost();
      }

      Record lastPurchaseItemRecord = store.getRecord(lastPurchaseItem);

      if(leftOver > 0)
      {
         lastPurchaseItemRecord.set(PurchaseItem.COST_PROPERTY_NAME, leftOver);
      }
      else
      {
         lastPurchaseItemRecord.set(PurchaseItem.COST_PROPERTY_NAME, 0);
      }
   }
   
   private void deleteSelected()
   {
      final GridSelectionModel<PurchaseItem> selectionModel = grid
         .getSelectionModel();
      final PurchaseItem selectedPurchaseItem = selectionModel
         .getSelectedItem();

      if (selectedPurchaseItem != null)
      {
         rowEditor.stopEditing(false);

         store.remove(selectedPurchaseItem);

         if (store.getCount() > 0)
         {
            selectionModel.select(0, false);
         }
      }
   }
}
