package com.finfrock.moneyreport.client;

import java.util.List;

import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.view.report.DisplayableReportBuilder;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DisplayPanel extends VerticalPanel implements DisplayPanelable
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private List<DisplayableReport> displayableReports;
   private Widget selectedDisplay;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
  
   public DisplayPanel(DisplayableReportBuilder displayableReportBuilder)
   {
      this.displayableReports = displayableReportBuilder.buildAll();
      
      setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      
      createCurrentSelectionListBox();
      
      DisplayableReport displayableReport = displayableReports.get(0);
      
      showDisplayable(displayableReport);
   }
  
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public void addDisplayableReportWidget(Widget widget)
   {
      if(selectedDisplay != null)
      {
         remove(selectedDisplay);
      }
      selectedDisplay = widget;
      
      add(widget);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void createCurrentSelectionListBox()
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

            showDisplayable(displayableReport);
         }
      });

      add(listBox);
   }
   
   private void showDisplayable(DisplayableReport displayableReport)
   {
      if(displayableReport != null)
      {
         displayableReport.show(this);
      }
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
}
