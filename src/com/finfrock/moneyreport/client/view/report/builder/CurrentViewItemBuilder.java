package com.finfrock.moneyreport.client.view.report.builder;

import java.util.List;

import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;
import com.finfrock.moneyreport.client.DisplayPanelable;
import com.finfrock.moneyreport.client.common.DisplayableReport;
import com.finfrock.moneyreport.client.connection.BillTypeManager;
import com.finfrock.moneyreport.client.connection.CurrentViewItemsRetriever;
import com.finfrock.moneyreport.client.connection.ServiceLocations;
import com.finfrock.moneyreport.client.view.report.item.CurrentViewItem;
import com.finfrock.moneyreport.client.view.report.view.CurrentGridView;
import com.finfrock.moneyreport.client.view.report.view.CurrentGridViewPortlet;

public class CurrentViewItemBuilder implements DisplayableReport
{
   private CurrentViewItemsRetriever retriever;

   public CurrentViewItemBuilder(BillTypeManager billTypeManger,
         ServiceLocations serviceLocations)
   {
      this.retriever = new CurrentViewItemsRetriever(serviceLocations,
         billTypeManger);
   }

   @Override
   public String getName()
   {
      return "Current View";
   }

   @Override
   public void show(final DisplayPanelable displayPanel)
   {
      retriever.startLoad(new Returnable<Loadable>()
      {
         @Override
         public void returned(Loadable object)
         {
            List<CurrentViewItem> currentViewItems = retriever.getObject();

            CurrentGridViewPortlet grid = new CurrentGridViewPortlet(currentViewItems);

            displayPanel.addDisplayableReportWidget(grid);
         }
      });
   }
}
