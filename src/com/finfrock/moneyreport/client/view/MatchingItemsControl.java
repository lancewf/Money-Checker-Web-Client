package com.finfrock.moneyreport.client.view;

import com.finfrock.moneyreport.client.DisplayPanelable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MatchingItemsControl extends VerticalPanel 
   implements DisplayPanelable
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private Widget selectedDisplay;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
  
   public MatchingItemsControl()
   {
      setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
   }
   
   // --------------------------------------------------------------------------
   // Overridden Members
   // --------------------------------------------------------------------------
   
   @Override
   public void addDisplayableReportWidget(Widget widget)
   {
      if(selectedDisplay != null)
      {
         remove(selectedDisplay);
      }
      selectedDisplay = widget;
      
      
      if(widget != null)
      {
         add(widget);
      }
   }

}
