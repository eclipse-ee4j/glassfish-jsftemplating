/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.editablelist;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent; 
import jakarta.faces.context.FacesContext; 
import jakarta.faces.validator.Validator; 
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.event.ValueChangeEvent;

import org.glassfish.webui.jsf.example.common.MessageUtil;
import org.glassfish.webui.jsf.example.index.IndexBackingBean;
import java.io.Serializable;

/**
 * Backing Bean for editable list example.
 */
public class EditableListBackingBean implements Serializable {
    
    // Holds value for property roles.
    private String[] roles;
    
    // Holds value for listTopChkBox property.
    private boolean listTopChkBox = true;
    
    // Holds value for sortedChkBox property.
    private boolean sortedChkBox = true;
    
    // Holds value for listTopBottom.
    private boolean listTopBottom = true;
    
    // Holds value for sortedList.
    private boolean sortedList = true;
    
    
    
    /** Creates a new instance of EfitableListBackingBean */
    public EditableListBackingBean() {
        roles = new String[5];
        
        roles[0] = "Cron Management";
        roles[1] = "DHCP Management";
        roles[2] = "Log Management";
        roles[3] = "Mail Management";
        roles[4] = "Network Management";
        
    }
 
    /** 
     *  this method assigns value to listonTop 
     *  property of editable list tag. 
     */
    public boolean getListTopBottom() {
        
        return listTopBottom;
        
    }
    
    /** 
     *  this method assigns value to sorted 
     *  property of editablelist tag. 
     */
    public boolean getSortedList() {
        
        return sortedList;
    }
    
    /** valueChangelistener for checkbox that sets list on top property. */
    public void listOnToplistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            listTopBottom = true;
        } else {
            listTopBottom = false;
        }
        
    }
    
    /** valueChangelistener for checkbox that sets sorted property. */
    public void sortedlistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            sortedList = true;
        } else {
            sortedList = false;
        }
        
    }
    
    /** getter method for property roles. */
    public String[] getRoles() {
        
        return this.roles;
    }
        
    /** Setter method for property roles. */
    public void setRoles(String[] roles) {
        
        this.roles = roles;
    } 
    
    /** Getter method for listTopChkBox property. */
    public boolean getListTopChkBox() {
             return listTopChkBox;    
    }
    
    /** Getter method for sortedChkBox property. */
    public boolean getSortedChkBox() {
           return sortedChkBox;    
    }
    
    /** Setter method for listTopChkBox property. */
    public void setListTopChkBox(boolean listTopChkBox) {
           this.listTopChkBox = listTopChkBox;    
    }
    
    /** Setter method for sortedChkBox property. */
    public void setSortedChkBox(boolean sortedChkBox) {
           this.sortedChkBox = sortedChkBox;    
    }
    
    /** validate method for role validation. */
    public void validate(FacesContext context, UIComponent component,
	Object value) throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        String string = value.toString(); 
              
            char[] characters = string.toCharArray(); 
            for (int counter = 0; counter < characters.length; ++counter) { 
                if (!Character.isLetter(characters[counter]) && 
                                        characters[counter] != ' ') { 
                    msgString = MessageUtil.
                                            getMessage("editablelist_string");
                          msg = new FacesMessage(msgString); 
                    throw new ValidatorException(msg); 
                } 
            } 

    }
        
    /** list validator that checks for number of list items. */
    public void validateList(FacesContext context, UIComponent component,
	                     Object value) throws ValidatorException {
        
          if (value instanceof String[]) {
              String[] list = (String[]) value;
              if (list.length < 5) {
                  String msgString = MessageUtil.
                                     getMessage("editablelist_listvalidate");
                  FacesMessage msg = new FacesMessage(msgString);
                  throw new ValidatorException(msg);
              }
          }
    }
    
    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("editablelist_summary");
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
    
    /**
     * method to get edited roles for resultpage.
     **/
    public String getEditedRoles() {
        
        if (roles == null) 
            return ""; 
        int i = 0;
        StringBuffer editedRole = new StringBuffer(); 
        for (i = 0; i < roles.length - 1; ++i) { 
            editedRole.append(roles[i]); 
            editedRole.append(", "); 
        } 
        editedRole.append(roles[i]); 
        return editedRole.toString();
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        listTopChkBox = true;
        sortedChkBox = true;
        listTopBottom = true;
        sortedList = true;
        return IndexBackingBean.INDEX_ACTION;
    }
}

