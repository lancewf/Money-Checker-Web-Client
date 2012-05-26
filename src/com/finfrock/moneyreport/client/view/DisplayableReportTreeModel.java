package com.finfrock.moneyreport.client.view;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.finfrock.moneyreport.client.common.DisplayableReport;

public class DisplayableReportTreeModel extends BaseTreeModel {

   private DisplayableReport report;
   
   public DisplayableReportTreeModel(DisplayableReport report) {
      this.report = report;
     set("name", report.getName());
   }

   public String getName() {
     return (String) get("name");
   }

   public String toString() {
     return getName();
   }

   public DisplayableReport getDisplayableReport()
   {
      return report;
   }
}
