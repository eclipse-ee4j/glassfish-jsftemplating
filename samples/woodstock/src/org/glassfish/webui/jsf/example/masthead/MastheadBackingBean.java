/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.masthead;

import org.glassfish.webui.jsf.component.Hyperlink;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import jakarta.faces.event.ValueChangeEvent;

import org.glassfish.webui.jsf.example.common.MessageUtil;

/**
 * Backing bean for Image and Masthead example.
 */

public class MastheadBackingBean implements Serializable {
    
    // Holds the server and user names.
    private String server = "test_server";
    private String user = "test_user";
    
    // Holds the text to be displayed in the alert boxes.
    private String message = null;
    private String detail = null;
    
    // Holds the severity of Alarms clicked.
    private String severity = null;
    
    // The outcome strings used in the faces config file
    private static final String MASTHEAD_INDEX = "showMasthead";
    private static final String MASTHEAD1 = "showMasthead1";
    private static final String MASTHEAD2 = "showMasthead2";
    private static final String MASTHEAD3 = "showMasthead3";
    private static final String RESULT_MASTHEAD = "showResultMasthead";
    private static final String RESULT_MASTHEAD_FACETS = "showResultMastheadFacets";    
    
    // Initial value for renedering the alert boxes.
    private boolean isRendered1 = false;
    private boolean isRendered2 = false;
    private boolean isRendered3 = false;
    private boolean isRendered4 = false;
    
    // Value for renedering the Back button.
    private boolean buttonDisplayed = false;
            
    // Default selection value for the "status area facet" checkbox.
    private boolean cb1Selected = true;
    private boolean cb2Selected = true;
    private boolean cb3Selected = true;
    private boolean cb4Selected = true;
    
    // Holds the Utility Links.
    private Hyperlink[] links = new Hyperlink[2];
    
    // Holds the Alarms.
    private int[] alarms = new int[] {1, 2, 3, 4};
    
    /** Creates a new instance of MastheadBackingBean. */
    public MastheadBackingBean() {
        
        links[0] = new Hyperlink();
        links[0].setText(MessageUtil.getMessage("masthead_utilityLink1Text"));
        links[0].setUrl("http://www.sun.com");
        links[0].setToolTip
                (MessageUtil.getMessage("masthead_utilityLink1ToolTip"));
        links[0].setTarget("_blank");
        
        links[1] = new Hyperlink();
        links[1].setText(MessageUtil.getMessage("masthead_utilityLink2Text"));
        links[1].setUrl("http://developers.sun.com/");
        links[1].setToolTip
                (MessageUtil.getMessage("masthead_utilityLink2ToolTip"));       
    }
    
    /** Returns the server name. */
    public String getServer() {
        return server;
    }
    
    /** Returns the user name. */
    public String getUser() {
        return user;
    }
    
    /** Returns message to be displayed in alert box. */
    public String getMessage() {
        return message;
    }
    
    /** Returns detail to be displayed in alert box. */
    public String getDetail() {
        return detail;
    }
    
    /** 
     * Returns value that decides whether the alertbox should be rendered or 
     * not in Masthead with Attributes Page. 
     */
    public boolean getIsRendered1() {
        return isRendered1;
    }
    
    /** 
     * Returns value that decides whether the alertbox should be rendered or 
     * not in Masthead with Facets Page. 
     */
    public boolean getIsRendered2() {
        return isRendered2;
    }
    
    /** 
     * Return value that decides whether the alertbox should be rendered or 
     * not in Results Page of Masthead with Attributes. 
     */
    public boolean getisRendered3() {
        return isRendered3;
    }
    
    /** 
     * Returns value that decides whether the alertbox should be rendered or 
     * not in Results Page of Masthead with Facets. 
     */
    public boolean getisRendered4() {
        return isRendered4;
    }
    
    /** 
     * Returns value that decides whether the Back button should be rendered or 
     * not in Results Page of Masthead with Facets. 
     */
    public boolean getbuttonDisplayed() {
        return buttonDisplayed;
    }
        
    /** Returns Utility Links. */
    public Hyperlink[] getLinks() {
        return links;
    }
    
    /** Set alarms in the masthead. */
    public void setAlarms(int[] alarms) {
        this.alarms = alarms;
    }
    
    /** Returns the alarms. */
    public int[] getAlarms() {
        return alarms;
    }
    
    /** Returns the enable/disable state of the status area facet checkbox. */
    public boolean getCb1Selected() {
        return cb1Selected;
    }
    
    /** Sets the enable/disable state of the status area facet checkbox. */
    public void setCb1Selected(boolean b) {
        cb1Selected = b;
    }
    
    /** Returns the enable/disable state of the status area facet checkbox. */
    public boolean getCb2Selected() {
        return cb2Selected;
    }
    
    /** Sets the enable/disable state of the status area facet checkbox. */
    public void setCb2Selected(boolean b) {
        cb2Selected = b;
    }
    
    /** Returns the enable/disable state of the status area facet checkbox. */
    public boolean getCb3Selected() {
        return cb3Selected;
    }
    
    /** Sets the enable/disable state of the status area facet checkbox. */
    public void setCb3Selected(boolean b) {
        cb3Selected = b;
    }
    
    /** Returns the enable/disable state of the status area facet checkbox. */
    public boolean getCb4Selected() {
        return cb4Selected;
    }
    
    /** Sets the enable/disable state of the status area facet checkbox. */
    public void setCb4Selected(boolean b) {
        cb4Selected = b;
    }
    
    /** Removes displayed alert box when Update Masthead is clicked. */   
    public void buttonClicked() {
        isRendered2 = false;
        return;
    }
    
    /** Message to be displayed when HelpLink is clicked. */
    public void helpPage1Clicked() {
        isRendered1 = true;
        message = MessageUtil.getMessage("masthead_helpLinkClicked");
        return;
    }
    
    /** Message to be displayed when LogoutLink is clicked. */
    public String logoutPage1Clicked() {
        isRendered1 = false;
        isRendered3 = true;
        message = MessageUtil.getMessage("masthead_logoutLinkClicked");
        return new String(RESULT_MASTHEAD);
    }

    /** Message to be displayed when VersionLink is clicked. */
    public void versionPage1Clicked() {
        isRendered1 = true;
        message = MessageUtil.getMessage("masthead_versionLinkClicked");
        return;
    }
    
    /** Message to be displayed when ConsoleLink is clicked. */
    public void consolePage1Clicked() {
        isRendered1 = true;        
        message = MessageUtil.getMessage("masthead_consoleLinkClicked");
        return;
    }
    
    /** Message to be displayed when HelpLink is clicked. */
    public void helpPage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_helpLinkClicked");
        detail = "";
        return;
    }
    
    /** Message to be displayed when LogoutLink is clicked. */
    public String logoutPage2Clicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = false;
        message = MessageUtil.getMessage("masthead_logoutLinkClicked");
        detail = "";
        return new String(RESULT_MASTHEAD_FACETS);
    }

    /** Message to be displayed when VersionLink is clicked. */
    public void versionPage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_versionLinkClicked");
        detail = "";
        return;
    }
    
    /** Message to be displayed when ConsoleLink is clicked. */
    public void consolePage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_consoleLinkClicked");
        detail = "";
        return;
    }
        
    /** Message to be displayed when Notification Phrase is clicked. */
    public String notificationClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_notificationClicked");
        detail = "";
        return new String(RESULT_MASTHEAD_FACETS);
    } 
    
    /** Message to be displayed when Job Status is clicked. */
    public String jobstatusClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_jobStatusClicked");
        detail = "";
        return new String(RESULT_MASTHEAD_FACETS);
    }
    
    /** Message to be displayed when Alarms are clicked. */
    public String alarmClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_alarmClicked");
        
        severity = (String) FacesContext.getCurrentInstance().
                            getExternalContext().getRequestParameterMap().
                            get("severity");
        
        if (severity.equals("critical")) 
        {
            detail = MessageUtil.getMessage("masthead_criticalalarmClicked");
        } else if (severity.equals("major"))
        {
            detail = MessageUtil.getMessage("masthead_majoralarmClicked");
        } else if (severity.equals("minor"))
        {
            detail = MessageUtil.getMessage("masthead_minoralarmClicked");
        }
        return new String(RESULT_MASTHEAD_FACETS);
    } 
    
    /** Page Navigation for breadcrumb. */
    public String goToMastheadIndex() {      
        return new String(MASTHEAD_INDEX);       
    }

    /** Page Navigation for Masthead with Atributes Page. */
    public String goToPage1() {
        isRendered1 = false;
        isRendered2 = false;
        return new String(MASTHEAD1);       
    }
    
    /** Page Navigation for Masthead with Facets Page. */
    public String goToPage2() {
        isRendered1 = false;
        isRendered2 = false;
        return new String(MASTHEAD2);       
    }
    
    /** Page Navigation for Images and imageHyperlink Page. */
    public String goToPage3() {
        isRendered1 = false;
        isRendered2 = false;
        return new String(MASTHEAD3);       
    }
    
    /** ValueChangelistener for checkbox that shows Notification Info. */
    public void listener1 (ValueChangeEvent event) {        
         cb1Selected = ((Boolean) event.getNewValue()).booleanValue();         
         return;        
    }
    
    /** ValueChangelistener for checkbox that shows Jobs Running Info. */
    public void listener2 (ValueChangeEvent event) {        
         cb2Selected = ((Boolean) event.getNewValue()).booleanValue();
         return;        
    }
    
    /** ValueChangelistener for checkbox that shows Time Stamp Info. */
    public void listener3 (ValueChangeEvent event) {        
         cb3Selected = ((Boolean) event.getNewValue()).booleanValue();
         return;        
    }
    
    /** ValueChangelistener for checkbox that shows Current Alarms Info. */
    public void listener4 (ValueChangeEvent event) {        
         cb4Selected = ((Boolean) event.getNewValue()).booleanValue();
         return;        
    }
}
