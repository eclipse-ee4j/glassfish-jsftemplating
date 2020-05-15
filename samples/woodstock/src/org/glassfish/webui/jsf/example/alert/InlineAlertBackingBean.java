/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.alert;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.application.FacesMessage;

import org.glassfish.webui.jsf.example.common.MessageUtil;
import org.glassfish.webui.jsf.example.index.IndexBackingBean;

import java.util.Random;
import java.io.Serializable;

/**
 * Backing bean for Inline Alert example.
 */
public class InlineAlertBackingBean implements Serializable { 
    
    // Outcome strings used in the faces config. 
    private final static String SHOW_INLINE_ALERT = "showInlineAlert";
    private final static String SHOW_ALERT_INDEX  = "showAlertIndex";
    
    // Holds value of property fieldValue. 
    private Integer fieldValue;    
    
    // Holds value of property disabled. 
    private boolean disabled = false;
        
    // Holds value of property alertSummary. 
    private String alertSummary = null;
    
    // Holds value of property alertDetail. 
    private String alertDetail = null;
    
    // Holds value of property alertType. 
    private String alertType = null;
    
    // Holds value of property alertRendered. 
    private boolean alertRendered = false;
     
    // Holds value of property linkRendered. 
    private boolean linkRendered = false;    
    
    // Random number 
    private int randomNumber = 0;    
    
    // Number of attempts 
    private int attempts = 0;    
    
    // User guess 
    private int guess = 0;                 
    
    /** Default constructor */
    public InlineAlertBackingBean() {                   
    }
            
    /** Set the value of property fieldValue. */    
    public void setFieldValue(Integer fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    /** Get the value of property fieldValue. */     
    public Integer getFieldValue() {
        return fieldValue;
    }
    
    /** Get the value of property disabled. */
    public boolean getDisabled() {
        return disabled;
    }
    
    /** Get the value of property alertSummary. */
    public String getAlertSummary() {
        if (isTextInvalid()) {
            alertSummary = MessageUtil.getMessage("alert_sumException"); 
        } 
        return alertSummary;
    }
    
    /** Get the value of property alertDetail. */
    public String getAlertDetail() {
        if (isTextInvalid()) {
            alertDetail = null;
        }
        return alertDetail;
    }
    
    /** Get the value of property alertType. */
    public String getAlertType() {
        if (isTextInvalid()) {
            alertType = "error"; 
        } 
        return alertType;
    }        
        
    /** Get the value of property alertRendered. */
    public boolean getAlertRendered() { 
        if (isTextInvalid()) {
            alertRendered = true; 
        }         
        return alertRendered;
    }             

    /** Get the value of property linkRendered. */
    public boolean getLinkRendered() {        
        if (isTextInvalid()) {
            linkRendered = false;
        }
        return linkRendered;
    }        
        
    /** 
     * Text field validator. This will ONLY be called if a value
     * was specified. 
     */
    public void validateFieldEntry(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {   
        
        int num = ((Integer) value).intValue();       
        
        // Number must be between 1 and 10.
        if (num < 1 || num > 10) {            
            setAlertInfo("error", "alert_sumException", null, null, false);
            FacesMessage message = new FacesMessage();
            message.setDetail(MessageUtil.getMessage("alert_sumException"));
	    message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);                                         
        }                
    }    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Action handler for the Enter button. */
    public String handleAction() {
        // Return null to cause the page to re-render.
        return null;
    }

    /** Action handler for the restart button. */
    public String restart() {
        resetAll();                
        // Return null to cause the page to re-render.
        return null;
    }

    /** Action handler when navigating to the inline alert example. */
    public String showInlineAlert() {        
        return SHOW_INLINE_ALERT;
    }

    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {        
        resetAll();
        return IndexBackingBean.INDEX_ACTION;
    }
         
    /** Action handler when navigating to the alert example index. */
    public String showAlertIndex() {
        fieldValue = null;        
        disabled = false;       
        alertRendered = false;        
        return SHOW_ALERT_INDEX;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Listeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            
    /** Action listener for the Enter button. */
    public void processButtonAction(ActionEvent ae) {   
        
        // Get the random number
	if (randomNumber == 0) {
            Random r = new Random();
	    randomNumber = r.nextInt(10) + 1;    
        }
                
	try {                        
	    // Get the value entered.	    
            guess = fieldValue.intValue();
            attempts++;
            
	    // Test guess with random number and display the appropriate
            // message.
	    if (guess == randomNumber) {
		setAlertInfo("success", "alert_sumCongrats",
                        "alert_number", String.valueOf(randomNumber), false);                                                  
                // Disable the button.            
                disabled = true;                                 
                reset();
	    } else if (attempts >= 3) {
		setAlertInfo("information", "alert_sumWrong", "alert_number",
                        String.valueOf(randomNumber), false);                                                
                // Disable the button.            
                disabled = true;
                reset();                
	    } else if (attempts == 2) {
		if (guess < randomNumber) {
		    setAlertInfo("warning", "alert_sumLastChance", 
                        "alert_detHigher", null, true);
		} else
		    setAlertInfo("warning", "alert_sumLastChance",
			"alert_detLower", null, true);
	    } else {
		if (guess < randomNumber) {
		    setAlertInfo("warning", "alert_sumTryAgain",
			"alert_detHigher", null, true);
		} else 
		    setAlertInfo("warning", "alert_sumTryAgain",
			"alert_detLower",  null, true);
	    }            
	} catch (Exception e) {
	    setAlertInfo("error", "alert_sumException", null, null, false);
	}
        
        // Reset the field value so that the old guess doesn't
        // remain in the field.
        fieldValue = null;
    }     

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** 
     * Set the alert properties that will be used by that component.
     *
     * @param type The alert type.     
     * @param summary The alert summary message key.
     * @param detail The alert detail message key.
     * @param detailArg The alert detail message arguments.
     * @param rendered The rendered value for the alert link.
     */
    private void setAlertInfo(String type, String summary, 
	    String detail, String detailArg, boolean rendered) {
        
        String[] args = {detailArg};
        if (detailArg != null) {
            alertDetail = MessageUtil.getMessage(detail, args);
        } else {
            alertDetail = MessageUtil.getMessage(detail);    
        }
        
        alertSummary = MessageUtil.getMessage(summary);
        alertType = type;                
        linkRendered = rendered;        
        
        // Set the alertRendered to true so that when the page is
        // re-rendered, it shows the alert message.
        alertRendered = true;       
    }    
    
    /** Check to see if text field is invalid. */
    private boolean isTextInvalid() {     
        FacesMessage.Severity severity = 
            FacesContext.getCurrentInstance().getMaximumSeverity();
        if(severity != null &&                 
                severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) { 
            return true;
        }
        return false;
    }
    
    /** Reset to initial values. */
    private void reset() {          
        guess = 0;
	attempts = 0;	    
	randomNumber = 0;                                                      
    }    
    
    /** Reset all to their initial values. */
    private void resetAll() {
        disabled = false;       
        alertRendered = false; 
        fieldValue = null; 
        reset();
    }
}
