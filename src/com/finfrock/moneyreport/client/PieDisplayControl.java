package com.finfrock.moneyreport.client;

import com.googlecode.gchart.client.GChart;
import com.googlecode.gchart.client.GChart.AnnotationLocation;
import com.googlecode.gchart.client.GChart.SymbolType;

public class PieDisplayControl extends GChart
{
   public PieDisplayControl()
   {
      double[] pieMarketShare =
      { 0.65, 0.20, 0.10, 0.05 };
      String[] pieTypes =
      { "Apple", "Cherry", "Pecan", "Bannana" };
      String[] pieColors =
      { "green", "red", "maroon", "yellow" };

      this.setChartSize(300, 200);
      this.setLegendVisible(false);
      getXAxis().setAxisVisible(false);
      getYAxis().setAxisVisible(false);
      getXAxis().setAxisMin(0);
      getXAxis().setAxisMax(10);
      getXAxis().setTickCount(0);
      getYAxis().setAxisMin(0);
      getYAxis().setAxisMax(10);
      getYAxis().setTickCount(0);
      // this line orients the center of the first slice (apple) due east
      setInitialPieSliceOrientation(0.75 - pieMarketShare[0] / 2);
      for (int i = 0; i < pieMarketShare.length; i++)
      {
         addCurve();
         getCurve().addPoint(5, 5);
         getCurve().getSymbol()
            .setSymbolType(SymbolType.PIE_SLICE_OPTIMAL_SHADING);
         getCurve().getSymbol().setBorderColor("white");
         getCurve().getSymbol().setBackgroundColor(pieColors[i]);
         // next two lines define pie diameter in x-axis model units
         getCurve().getSymbol().setModelWidth(6);
         getCurve().getSymbol().setHeight(0);
         getCurve().getSymbol().setFillSpacing(3);
         getCurve().getSymbol().setFillThickness(3);
         getCurve().getSymbol().setHovertextTemplate(GChart
            .formatAsHovertext(pieTypes[i] + ", "
                  + Math.round(100 * pieMarketShare[i]) + "%"));
         getCurve().getSymbol().setPieSliceSize(pieMarketShare[i]);
         getCurve().getPoint().setAnnotationText(pieTypes[i]);
         getCurve().getPoint()
            .setAnnotationLocation(AnnotationLocation.OUTSIDE_PIE_ARC);
      }
      
      update();
   }
}