/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.hyperlink;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent; 
import jakarta.faces.context.FacesContext; 
import jakarta.faces.validator.Validator; 
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.event.ActionEvent;

import org.glassfish.webui.jsf.component.TextField;

import org.glassfish.webui.jsf.example.common.MessageUtil;
import org.glassfish.webui.jsf.example.index.IndexBackingBean;
import java.util.Date;
import java.io.*;

/**
 * Backing Bean for Hyperlink example.
 */
public class HyperlinkBackingBean implements Serializable {
    
    // Holds value for property userName.
    private String userName = null;
    
    // Holds value for property linkDisable.
    private boolean linkDisable = false;
    
    // Holds value for displayParam property.
    private boolean displayParam = false;
    
    // Holds value for property date.
    private String date = null;
    
    // Holds value for property linkOnoff.
    private boolean linkOnoff = false;
    
    // Holds value for property enable hyperlink.
    private boolean enableHyperlink = true;
    
    // Holds value for property disable hyperlink.
    private boolean disableHyperlink = false;
    
    // String constant for disable button id.
    private static String DISABLE_BUTTON_ID = 
            "hyperlinkForm:pageButtonsGroupBottom:disable";
    
    /** Creates a new instance of HyperlinkBackingBean */
    public HyperlinkBackingBean() {
    }
    
    /** Getter method for username property. */
    public String getUserName() {
        return userName;
    }

    /** Setter method for property userName */
    public void setUserName(String name) {
        this.userName = name;
    }
    
    /** 
     *  this method assigns value to disabled 
     *  property of hyperlink tag. 
     */
    public boolean getLinkOnoff() {
        return linkOnoff;
    }
    
    /**
     * this method returns current date.
     */
    public String getStartDate() {
        return (new Date()).toString();
    }
    
    /**
     * this method returns param value.
     */
    public String getParamValue() {
        date = (String) FacesContext.getCurrentInstance().
                      getExternalContext().getRequestParameterMap().
                      get("dateParam");
        return date;
    }
    
    /**
     * this method returns boolean for visible attribute of 
     * staticText and label tag.
     */
    public boolean getRenderParam() {
         date = (String) FacesContext.getCurrentInstance().
                      getExternalContext().getRequestParameterMap().
                      get("dateParam");
        
         if (date != null) {
            return  true;
         }
         return false;
    }
    
    /** Action listener for the immediate hyperlink. */
    public void immediateAction(ActionEvent ae) {
        // Since the action is immediate, the textfield component won't
        // go through the Update Model phase and the model object won't
        // get updated with the submitted value. So, we need to update the
        // model here so that it reflects the new value.
        FacesContext context = FacesContext.getCurrentInstance();
        TextField textField= (TextField) context.getViewRoot().findComponent(
                "hyperlinkForm:nameField");
        userName = (String) textField.getSubmittedValue();        
    } 

    /** Action handler. */
    public String nextPage() {
        return "resultHyperlink";
    }
    
    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("hyperlink_summary");
    }
    
    /**
     * getter method for enabling hyperlink.
     */
    public boolean getEnableHyperlink() {
        return enableHyperlink;
    }
    
    /**
     * getter method for disabling hyperlink.
     */
    public boolean getDisableHyperlink() {
        return disableHyperlink;
    }
    
    /**
     * Detail message for Validator exception.
     */
    public String getDetailMsg() {
        return MessageUtil.getMessage("hyperlink_detail");
    }
    /**
     * Checks for errors on page.
     */
    public boolean isErrorsOnPage() {
        
        FacesMessage.Severity severity =
                FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0) {
            return true;
        }
        return false;
    }
    
    /** Action listener for the disable/enable hyperlink button. */
    public void onOffbuttonAction(ActionEvent ae) {   
    
        FacesContext context = FacesContext.getCurrentInstance();
        String clientId = ae.getComponent().getClientId(context);
        if (clientId != null && clientId.equals(DISABLE_BUTTON_ID)) {
            linkOnoff = true;
            enableHyperlink = false;
            disableHyperlink = true;
        } else {
            linkOnoff = false;
            enableHyperlink = true;
            disableHyperlink = false;
        }
    
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        userName = null;
        linkOnoff = false;
        enableHyperlink = true;
        disableHyperlink = false;
        return IndexBackingBean.INDEX_ACTION;
    }
}
