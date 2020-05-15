/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.field;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.context.FacesContext;

import org.glassfish.webui.jsf.model.Option;
import org.glassfish.webui.jsf.model.OptionTitle;

import org.glassfish.webui.jsf.component.DropDown;
import org.glassfish.webui.jsf.component.TextField;
import org.glassfish.webui.jsf.component.PasswordField;
import org.glassfish.webui.jsf.component.TextArea;

import org.glassfish.webui.jsf.example.index.IndexBackingBean;
import org.glassfish.webui.jsf.example.common.MessageUtil;

import java.io.Serializable;

/**
 * Backing bean for the Text Input example.
 *
 * Note that we must implement java.io.Serializable or
 * jakarta.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class TextInputBackingBean implements Serializable {
    
    // Text input default value.
    private static final String TEXTINPUT_DEFAULT_VALUE =
	    MessageUtil.getMessage("field_textFieldTitle");
 	     
    // Text area default value.
    private static final String TEXTAREA_DEFAULT_VALUE =
	    MessageUtil.getMessage("field_textAreaTitle");

    /** Holds value of property textFieldValue. */
    private String textFieldValue = TEXTINPUT_DEFAULT_VALUE;;
    
    /** Holds value of property passwordValue. */
    private String passwordValue = "";
       
    /** Holds value of property textAreaValue. */
    private String textAreaValue = TEXTAREA_DEFAULT_VALUE;        
    
    /** Holds value of property testCaseOptions. */
    private Option[] testCaseOptions = null;
    
    /** Holds value of property textFieldDisabled. */
    private boolean textFieldDisabled = false;      
    
    /** Holds value of property passwordDisabled. */
    private boolean passwordDisabled = false;      
    
    /** Holds value of property textAreaDisabled. */
    private boolean textAreaDisabled = false;  
    
    /** Default constructor */
    public TextInputBackingBean() {
        testCaseOptions = new Option[6];                              
	testCaseOptions[0] = new OptionTitle(
                 MessageUtil.getMessage("field_testCaseTitle"));
        testCaseOptions[1] = new Option("field_testCaseToggleTextFieldState", 
                 MessageUtil.getMessage("field_testCaseToggleTextFieldState")); 
        testCaseOptions[2] = new Option("field_testCaseTogglePasswordState", 
                 MessageUtil.getMessage("field_testCaseTogglePasswordState")); 
        testCaseOptions[3] = new Option("field_testCaseToggleTextAreaState", 
                 MessageUtil.getMessage("field_testCaseToggleTextAreaState")); 
        testCaseOptions[4] = new Option("field_testCaseDisableAll", 
                 MessageUtil.getMessage("field_testCaseDisableAll"));
        testCaseOptions[5] = new Option("field_testCaseEnableAll",  
                 MessageUtil.getMessage("field_testCaseEnableAll")); 
    }
    
    /** Get the value of the text field. */
    public String getTextFieldValue() {
        return textFieldValue;
    }
    
    /** Set the value of the text field. */
    public void setTextFieldValue(String textFieldValue) {
        this.textFieldValue = textFieldValue;
    }
    
    /** Get the value of the password field. */
    public String getPasswordValue() {
        return passwordValue;
    }
    
    /** Set the value of the password field. */
    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }
    
    /** Get the value of the text area. */
    public String getTextAreaValue() {
        return textAreaValue;
    }
    
    /** Set the value of the text area. */
    public void setTextAreaValue(String textAreaValue) {
        this.textAreaValue = textAreaValue;
    }
    
    /** Return the array of test case options */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }    
    
    /** Get the disabled state of the text field. */
    public boolean getTextFieldDisabled() {
        return textFieldDisabled;
    } 
    
    /** Set the disabled state of the text field. */
    public void setTextFieldDisabled(boolean textFieldDisabled) {
        this.textFieldDisabled = textFieldDisabled;
    }
    
    /** Get the disabled state of the password field. */
    public boolean getPasswordDisabled() {
        return passwordDisabled;
    } 

    /** Set the disabled state of the password field. */
    public void setPasswordDisabled(boolean passwordDisabled) {
        this.passwordDisabled = passwordDisabled;
    }
    
    /** Get the disabled state of the text area. */
    public boolean getTextAreaDisabled() {
        return textAreaDisabled;
    }
    
    /** Set the disabled state of the text area. */
    public void setTextAreaDisabled(boolean textAreaDisabled) {
        this.textAreaDisabled = textAreaDisabled;
    }
    
    /** Return the state result for text field. */
    public String getTextFieldResult() {                      
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_textFieldTitle");
        if (getTextFieldDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");    
        }        
        args[2] = getTextFieldValue();
        
        return MessageUtil.getMessage("field_fieldResult", args);       
    }
    
    /** Return the state result for password. */
    public String getPasswordResult() {                      
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_passwordTitle");
        if (getPasswordDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");    
        }        
        args[2] = getPasswordValue();
        
        return MessageUtil.getMessage("field_fieldResult", args);       
    }
    
    /** Return the state result for text area. */
    public String getTextAreaResult() {                      
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_textAreaTitle");
        if (getTextAreaDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");    
        }        
        args[2] = getTextAreaValue();
        
        return MessageUtil.getMessage("field_fieldResult", args);       
    }
            
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
        // Reset when leaving the example.
        textFieldValue = TEXTINPUT_DEFAULT_VALUE;
        passwordValue = "";
        textAreaValue = TEXTAREA_DEFAULT_VALUE;
        disable(false);
        
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Listeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Action listener for the test case dropdown menu. */
    public void processMenuSelection(ActionEvent e) {
        DropDown dropDown = (DropDown) e.getComponent();
        String selected = (String) dropDown.getSelected(); 
        FacesContext context = FacesContext.getCurrentInstance();        
        TextField textField = (TextField) context.getViewRoot().findComponent( 
                "form:textField"); 
        PasswordField password =  
                (PasswordField) context.getViewRoot().findComponent( 
                "form:password"); 
        TextArea textArea = (TextArea) context.getViewRoot().findComponent( 
                "form:textArea"); 

        // Since the action is immediate, the components won't
        // go through the Update Model phase.  So, we need to explicitly set the 
        // state of the components and update the model object for the given action selected.
        if (selected.equals("field_testCaseDisableAll")) { 
	    textField.setDisabled(true); 
            password.setDisabled(true); 
            textArea.setDisabled(true); 
            disable(true);
            
        } else if (selected.equals("field_testCaseEnableAll")) {                        
	    textField.setDisabled(false); 
            password.setDisabled(false); 
            textArea.setDisabled(false); 
            disable(false);            
	} else if (selected.equals("field_testCaseToggleTextFieldState")) {
             textField.setDisabled(!getTextFieldDisabled());
             setTextFieldDisabled(!getTextFieldDisabled());
        } else if (selected.equals("field_testCaseTogglePasswordState")) {             
             password.setDisabled(!getPasswordDisabled());
             setPasswordDisabled(!getPasswordDisabled());
        } else if (selected.equals("field_testCaseToggleTextAreaState")) {             
             textArea.setDisabled(!getTextAreaDisabled());
             setTextAreaDisabled(!getTextAreaDisabled());   
        }
    }
        
    /** Action listener for the preset button. */
    public void presetFields(ActionEvent e) {
        // Sine the action is immediate, the text input components won't
        // go through the Update Model phase. However, their submitted values
        // get set in the Apply Request Value phase and these values are retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.        
        FacesContext context = FacesContext.getCurrentInstance();  
        
        TextField textField = (TextField) context.getViewRoot().findComponent(
                "form:textField");
        textField.setSubmittedValue(null);
	textField.setDisabled(false); 
        textFieldValue = TEXTINPUT_DEFAULT_VALUE; 
        
        PasswordField password = 
            (PasswordField) context.getViewRoot().findComponent(
                "form:password");
        password.setSubmittedValue(null);  
	password.setDisabled(false);
        passwordValue = "";
        
        TextArea textArea = (TextArea) context.getViewRoot().findComponent(
                "form:textArea");
        textArea.setSubmittedValue(null);   
	textArea.setDisabled(false); 
        textAreaValue = TEXTAREA_DEFAULT_VALUE; 
        
        disable(false);        
    } 
            
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Helper method to set the disabled state of the input fields. */
    private void disable(boolean value) {
        setTextFieldDisabled(value);
        setTextAreaDisabled(value);
        setPasswordDisabled(value);
    }  
}
