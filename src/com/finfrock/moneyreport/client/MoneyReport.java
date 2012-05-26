package com.finfrock.moneyreport.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MoneyReport implements EntryPoint
{
  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {    
     GUIBuilder guiBuilder = new GUIBuilder();
     
     guiBuilder.build();
  }
}