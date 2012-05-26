package com.finfrock.moneyreport.client;

import com.googlecode.gchart.client.GChart;

public class BarDisplayControl extends GChart
{
   final String[] groupLabels =
   { "<html>2007<br><small><small>O Seven",
         "<html>2008<br><small><small>Owe Ate",
         "<html>2009<br><i><small><small>Oh Nein!" };

   final String[] barLabels =
   { "Q1", "Q2", "Q3", "Q4" };

   final String[] barColors =
   { "red", "blue", "green", "silver" };

   final int MAX_REVENUE = 1000;

   final int WIDTH = 300;

   final int HEIGHT = 200;

   public BarDisplayControl()
   {
      setChartSize(WIDTH, HEIGHT);

      for (int iCurve = 0; iCurve < barLabels.length; iCurve++)
      {
         addCurve(); // one curve per quarter
         getCurve().getSymbol().setSymbolType(SymbolType.VBAR_SOUTHWEST);
         getCurve().getSymbol().setBackgroundColor(barColors[iCurve]);
         getCurve().setLegendLabel(barLabels[iCurve]);
         getCurve().getSymbol().setHovertextTemplate(GChart
            .formatAsHovertext(barLabels[iCurve] + " revenue=${y}"));
         getCurve().getSymbol().setModelWidth(1.0);
         getCurve().getSymbol().setBorderColor("black");
         getCurve().getSymbol().setBorderWidth(1);
         for (int jGroup = 0; jGroup < groupLabels.length; jGroup++)
         {
            // the '+1' creates a bar-sized gap between groups
            getCurve().addPoint(1 + iCurve + jGroup * (barLabels.length + 1),
               Math.random() * MAX_REVENUE);
            getCurve().getPoint().setAnnotationText(barLabels[iCurve]);
            getCurve().getPoint()
               .setAnnotationLocation(AnnotationLocation.NORTH);
         }
      }

      for (int i = 0; i < groupLabels.length; i++)
      {
         // formula centers tick-label horizontally on each group
         getXAxis().addTick(barLabels.length / 2. + i * (barLabels.length + 1),
            groupLabels[i]);
      }
      getXAxis().setTickLabelFontSize(20);
      getXAxis().setTickLabelThickness(40);
      getXAxis().setTickLength(6); // small tick-like gap...
      getXAxis().setTickThickness(0); // but with invisible ticks
      getXAxis().setAxisMin(0); // keeps first bar on chart

      getYAxis().setAxisMin(0); // Based on sim revenue range
      getYAxis().setAxisMax(MAX_REVENUE); // of 0 to MAX_REVENUE.
      getYAxis().setTickCount(11);
      getYAxis().setHasGridlines(true);
      getYAxis().setTickLabelFormat("$#,###");
      
      update();
   }

}