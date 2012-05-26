package com.finfrock.moneyreport.client.view;

import com.finfrock.client.DataChangeListener;
import com.finfrock.client.FacebookConnectionStatus;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserPanel extends VerticalPanel
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private Widget facebookButton;
   private Widget profilePicture;
   private FacebookConnectionStatus facebookConnectionStatus;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public UserPanel(FacebookConnectionStatus facebookConnectionStatus)
   {
      addStyleName("logInPanel");
      setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      
      this.facebookConnectionStatus = facebookConnectionStatus;
      
      initialize();
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private void initialize()
   {
      facebookButton = createPushButton();
      
      facebookConnectionStatus.addLoggedinListener(new DataChangeListener()
      {
         @Override
         public void onDataChange()
         {
            if(facebookConnectionStatus.isloggedin())
            {
               remove(facebookButton);
               profilePicture = createProfilePic();
               add(profilePicture); 
            }
            else
            {
               remove(profilePicture);
               add(facebookButton);
            }
         }
      });
      
      if(!facebookConnectionStatus.isloggedin())
      {
         add(facebookButton);
      }
      else
      {
         profilePicture = createProfilePic();
         add(profilePicture);
      }
   }

   private Widget createPushButton()
   {
      Button facebookButton = facebookConnectionStatus.createLogginButton();
          
      facebookButton.addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            facebookConnectionStatus.connectToFacebook();
         }
      });
      
      return facebookButton;
   }

   private Widget createProfilePic()
   {
      HorizontalPanel panel = new HorizontalPanel();
      
      Anchor logOutLink = new Anchor("Logout");
      
      //logOutLink.addStyleName("textWithSpace");
      
      logOutLink.addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            facebookConnectionStatus.logoutOfFacebook();
         }
      });
      
      Widget profilePic = facebookConnectionStatus.createProfilePicture();
      
      //profilePic.addStyleName("textWithSpace");
      
      panel.add(logOutLink);
      panel.add(profilePic);
      
      return panel;
   }
}
