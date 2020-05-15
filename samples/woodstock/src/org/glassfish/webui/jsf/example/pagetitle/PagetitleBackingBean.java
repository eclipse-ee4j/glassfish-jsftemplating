/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.pagetitle;

import java.beans.*;
import java.io.Serializable;
import org.glassfish.webui.jsf.component.DropDown;
import org.glassfish.webui.jsf.component.TextField;
import org.glassfish.webui.jsf.model.Option;
import jakarta.faces.component.UIComponent;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import org.glassfish.webui.jsf.example.common.MessageUtil;

/**
 * Backing bean for Content Page Title example.
 */

public class PagetitleBackingBean implements Serializable {
    
    // Holds the text to be displayed in the alert boxes.
    private String message = null;
    private String detail = null;
    
    // Holds the texts to be displayed in the text fields.
    private String text1 = null;
    private String text2 = null;
        
    // Holds the PageViews
    private Option[] views = null;
    
    // Holds the selected item of PageView
    private String selectedItem = "View1";
    
    // Initial value for renedering the Alert box.
    private boolean isRendered = false;
    
    /** Creates a new instance of PagetitleBackingBean. */
    public PagetitleBackingBean() {
        views = new Option[3];
        views[0] = new Option("View1", MessageUtil.getMessage("pagetitle_view1"));
        views[1] = new Option("View2", MessageUtil.getMessage("pagetitle_view2"));
        views[2] = new Option("View3", MessageUtil.getMessage("pagetitle_view3"));
    }
    
    /** 
     * Returns value that decides whether the alertbox should be rendered or 
     * not.  
     */
    public boolean getIsRendered() {
        return isRendered;
    }
        
    /** Return message to be displayed in alert box. */
    public String getMessage() {
        return message;
    }
    
    /** Returns detail to be displayed in alert box. */
    public String getDetail() {
        return detail;
    }
        
    /** Returns options to be displayed in PageView dropdown menu. */
    public Option[] getViews() {
        isRendered = false;
        return views;
    }
    
    /** Return text to be displayed in textfield. */
    public String getText1() {
         return text1;
    }
    
    /** Sets text to be displayed in textfield. */
    public void setText1(String text1) {
        this.text1 = text1;
    }
    
    /** Return text to be displayed in textfield. */
    public String getText2() {
        return text2;
    }
    
    /** Sets text to be displayed in textfield. */
    public void setText2(String text2) {
        this.text2 = text2;
    }
            
    /** Returns the selected item in the PageView. */
    public String getSelectedItem() {
        return selectedItem;
    }
    
    /** Sets the selected item in the PageView. */
    public void setSelectedItem(String s) {
        selectedItem = s;
    }
    
    /** Checks if there is any error on the page. */  
    public boolean isErrorsOnPage() {
        message = MessageUtil.getMessage("pagetitle_error");
        detail = MessageUtil.getMessage("pagetitle_detail");     
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
         
    /** Message to be displayed when Save Button is clicked. */
    public void saveClicked() {
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_saveClicked");        
    }
    
    /** Message to be displayed when Reset Button is clicked. */
    public void resetClicked(ActionEvent ae) throws NullPointerException {
        FacesContext context = FacesContext.getCurrentInstance();
        TextField tf1 = (TextField) context.getViewRoot().findComponent("form1:pagetitle:text1");
        TextField tf2 = (TextField) context.getViewRoot().findComponent("form1:pagetitle:text2");                
        tf1.setSubmittedValue(null);
        tf2.setSubmittedValue(null);
        tf2.setValue(null);
        text1 = null;
        text2 = null;
        isRendered = true;        
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_resetClicked");
    }
    
    /** Message to be displayed when the PageViews are clicked. */
    public void menuChanged(ActionEvent e) throws AbortProcessingException {
        UIComponent c = e.getComponent();        
        DropDown menu = (DropDown) c;
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_viewClicked") + " " + 
                 menu.getValue();
    }
    
    /** Resets page defaults while navigating to the Index Page. */
    public String showIndex() {
        isRendered = false;
        text1 = null;
        text2 = null;
        selectedItem = "View1";
        return "showIndex";
    }        
}
