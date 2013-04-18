package com.finfrock.moneyreport.client.view;

import com.finfrock.client.FacebookConnectionStatus;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserPanel extends VerticalPanel {
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public UserPanel(FacebookConnectionStatus facebookConnectionStatus)
   {
      addStyleName("logInPanel");
      setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
   }
}
