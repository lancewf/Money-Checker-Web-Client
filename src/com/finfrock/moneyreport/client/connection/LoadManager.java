package com.finfrock.moneyreport.client.connection;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;

public class LoadManager implements Returnable<Loadable>
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private Returnable<DataBuilder> returnable;

   private List<Loadable> loadables;
   
   private DataBuilder dataBuilder;

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public LoadManager(ServiceLocations serviceLocations)
   {
      dataBuilder = new DataBuilder();
      
      createLoadables(serviceLocations);
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public void startLoad(Returnable<DataBuilder> returnable)
   {
      this.returnable = returnable;

      for (Loadable loadable : loadables)
      {
         loadable.startLoad(this);
      }
   }

   @Override
   public void returned(Loadable loadable)
   {
      loadables.remove(loadable);

      dataBuilder.setLoadable(loadable);
      
      if (loadables.size() == 0)
      {
         dataBuilder.build();
         
         returnable.returned(dataBuilder);
      }
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private void createLoadables(ServiceLocations serviceLocations)
   {
      loadables = new ArrayList<Loadable>();

      loadables.add(new BillTypeRetriever(serviceLocations));
      loadables.add(new AllottedAmountsRetriever(serviceLocations));
      loadables.add(new StoresRetriever(serviceLocations));
   }
}
