/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * ButtonBackingBean.java
 *
 * Created on January 2, 2006, 5:18 PM
 */

package org.glassfish.webui.jsf.example.button;

import java.io.Serializable;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.context.FacesContext;

import org.glassfish.webui.jsf.model.Option;
import org.glassfish.webui.jsf.model.OptionTitle;
import org.glassfish.webui.jsf.example.common.MessageUtil;
import org.glassfish.webui.jsf.example.index.IndexBackingBean;

/**
 * Backing bean for Button example.
 *
 * Note that we must implement java.io.Serializable or
 * jakarta.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class ButtonBackingBean implements Serializable{
    
    private Option[] testCaseOptions = null;
    private String summary = null;
    private String detail = null;
    private boolean primaryDisabled = false;
    private boolean primaryMiniDisabled = false;
    private boolean secondaryDisabled = false;
    private boolean secondaryMiniDisabled = false;
    
    /** Creates a new instance of ButtonBackingBean */
    public ButtonBackingBean() {
        testCaseOptions = new Option[3];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("button_testCase_title"));
        testCaseOptions[1] = new Option("button_testCase_disableAll",
                MessageUtil.getMessage("button_testCase_disableAll"));
        testCaseOptions[2] = new Option("button_testCase_enableAll", 
                MessageUtil.getMessage("button_testCase_enableAll"));
    }
    
    /** Return the array of test case options */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }
    
    /** Action listener for icon button. */
    public void iconActionListener(ActionEvent e) {
        setAlertInfo(e, null);
    }
    
    /** Action listener for primary button. */
    public void primaryActionListener(ActionEvent e) {
        setAlertInfo(e, "button_primaryButtonText");
    }
    
    /** Action listener for primary mini button. */
    public void primaryMiniActionListener(ActionEvent e) {
        setAlertInfo(e, "button_primaryMiniButtonText");
    }
    
    /** Action listener for secondary button. */
    public void secondaryActionListener(ActionEvent e) {
        setAlertInfo(e, "button_secondaryButtonText");
    }
    
    /** Action listener for secondary mini button. */
    public void secondaryMiniActionListener(ActionEvent e) {
        setAlertInfo(e, "button_secondaryMiniButtonText");
    }
    
    /**
     * Set the alert summary and detail messages based on the component
     * associated with the event.  The valueKey is the message key
     * for the detail message, or null if no detail is required.
     */
    private void setAlertInfo(ActionEvent e, String valueKey) {
        FacesContext context = FacesContext.getCurrentInstance();
        String clientID = e.getComponent().getClientId(context);
        
        // We only want the last part of the fully-qualified clientID
        int n = clientID.lastIndexOf(':');
        if (n >= 0) {
            clientID = clientID.substring(n + 1);
        }
        
        Object[] args = null;
        String detailKey = null;
        if (valueKey != null) {
            args = new Object[2];
            args[1] = MessageUtil.getMessage(valueKey);
            detailKey = "button_alertElementDetail";
        } else {
            args = new Object[1];
            detailKey = "button_alertElementDetailNoValue";
        }
        args[0] = clientID;
        summary = MessageUtil.getMessage("button_alertElement");
        detail = MessageUtil.getMessage(detailKey, args);
    }
    
    /** Action handler for all buttons. */
    public String actionHandler() {
        // Returning null causes page to re-render.
        return null;
    }
    
    /** Action handler for the test case dropdown menu. */
    public String testCaseActionHandler() {
        // Disable the alert by having no summary and detail.
        summary = null;
        detail = null;
        
        return null;
    }
    
    /** Return the enable/disable state of the primary button. */
    public boolean getPrimaryDisabled() {
        return primaryDisabled;
    }

    /** Set the enable/disable state of the primary button. */
    public void setPrimaryDisabled(boolean b) {
        primaryDisabled = b;
    }
    
    /** Return the enable/disable state of the primary mini button. */
    public boolean getPrimaryMiniDisabled() {
        return primaryMiniDisabled;
    }

    /** Set the enable/disable state of the primary mini button. */
    public void setPrimaryMiniDisabled(boolean b) {
        primaryMiniDisabled = b;
    }
    
    /** Return the enable/disable state of the secondary button. */
    public boolean getSecondaryDisabled() {
        return secondaryDisabled;
    }

    /** Set the enable/disable state of the secondary button. */
    public void setSecondaryDisabled(boolean b) {
        secondaryDisabled = b;
    }
    
    /** Return the enable/disable state of the secondary mini button. */
    public boolean getSecondaryMiniDisabled() {
        return secondaryMiniDisabled;
    }

    /** Set the enable/disable state of the secondary mini button. */
    public void setSecondaryMiniDisabled(boolean b) {
        secondaryMiniDisabled = b;
    }
    
    /** Return the alert summary message. */
    public String getAlertSummary() {
        return summary;
    }
    
    /** Return the alert detail message. */
    public String getAlertDetail() {
        return detail;
    }
    
    /** Return the render state of the alert. */
    public boolean getAlertRendered() {
        if (summary == null)
            return false;
        
        return true;
    }

    /** Return the selected state of the primary button's checkbox. */
    public boolean getPrimaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getPrimaryDisabled();
    }
    
    /** Set the selected state of the primary button's checkbox. */
    public void setPrimaryCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setPrimaryDisabled(!b);
    }
    
    /** Return the selected state of the primary mini button's checkbox. */
    public boolean getPrimaryMiniCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getPrimaryMiniDisabled();
    }
    
    /** Set the selected state of the primary mini button's checkbox. */
    public void setPrimaryMiniCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setPrimaryMiniDisabled(!b);
    }
    
    /** Return the selected state of the secondary button's checkbox. */
    public boolean getSecondaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getSecondaryDisabled();
    }
    
    /** Set the selected state of the secondary button's checkbox. */
    public void setSecondaryCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setSecondaryDisabled(!b);
    }
    
    /** Return the selected state of the secondary mini button's checkbox. */
    public boolean getSecondaryMiniCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getSecondaryMiniDisabled();
    }
    
    /** Set the selected state of the secondary mini button's checkbox. */
    public void setSecondaryMiniCBSelected(boolean b) {
        // Checkbox state is inverse of button's disabled state.
        setSecondaryMiniDisabled(!b);
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    private void reset() {
        summary = null;
        detail = null;
        primaryDisabled = false;
        primaryMiniDisabled = false;
        secondaryDisabled = false;
        secondaryMiniDisabled = false;        
    }
    
    /** Return the state result for the primary button */
    public String getPrimaryResult() {
        String buttonLabel = MessageUtil.getMessage("button_primaryButtonText");
        if (getPrimaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
    
    /** Return the state result for the primary-mini button */
    public String getPrimaryMiniResult() {
        String buttonLabel = MessageUtil.getMessage("button_primaryMiniButtonText");
        if (getPrimaryMiniDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
    
    /** Return the state result for the secondary button */
    public String getSecondaryResult() {
        String buttonLabel = MessageUtil.getMessage("button_secondaryButtonText");
        if (getSecondaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
    
    /** Return the state result for the secondary-mini button */
    public String getSecondaryMiniResult() {
        String buttonLabel = MessageUtil.getMessage("button_secondaryMiniButtonText");
        if (getSecondaryMiniDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
}
