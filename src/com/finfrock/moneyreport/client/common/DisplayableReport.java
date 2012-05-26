package com.finfrock.moneyreport.client.common;

import com.finfrock.moneyreport.client.DisplayPanelable;

public interface DisplayableReport
{
   String getName();
   void show(DisplayPanelable displayPanel);
}
