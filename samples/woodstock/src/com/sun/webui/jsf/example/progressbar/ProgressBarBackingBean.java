/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.progressbar;

import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * Backing bean for ProgressBar example.
 */
public class ProgressBarBackingBean {
    
    // holds progress value.
    private int progressRate = 0;
    
    // holds status string.
    private String status = null;
    
    // Outcome strings used in the faces config.
    private final static String SHOW_PROGRESSBAR_INDEX  = "showProgressBar";
    
    /** Creates a new instance of ProgressBean */
    public ProgressBarBackingBean() {
        
    }
    
    /** This method generates the progress value. */
    public int getProgressRate() {
        
        String task = "";
        if (getComponentInstance() != null)
            task = getComponentInstance().getTaskState();
        
        if (task != null) {
            if (task.equals(ProgressBar.TASK_PAUSED)) {
                status = MessageUtil.
                            getMessage("progressbar_pausedText");
                return progressRate;
                
            }
            if (task.equals(ProgressBar.TASK_CANCELED)) {
                status = MessageUtil.
                            getMessage("progressbar_canceledText");
                return progressRate;
            }
        }
        
        progressRate = progressRate + 3;
        status = progressRate + MessageUtil.
                            getMessage("progressbar_percentText");
        
        if (progressRate > 99) {
            progressRate = 100;
        }
        
        if (progressRate == 100) {
            
            getComponentInstance().setTaskState(ProgressBar.TASK_COMPLETED);
            status = MessageUtil.
                            getMessage("progressbar_completedText");
        }
        
        return progressRate;
    }
    
    /** Get the status string for ProgressBar */    
    public String getStatus() {
        
        return status;
    }
    
    /**
     * Method to get the ProgressBar instance.
     */
    public ProgressBar getComponentInstance() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent comp = context.getViewRoot().findComponent("form1:progressBarContentPage:pb1");
        ProgressBar pb = (ProgressBar) comp;
        
        return pb;
    }
    
    /**
     * Action handler when navigating to the progressbar example index.
     */    
    public String showProgressBarIndex() {
        getComponentInstance().setTaskState(ProgressBar.TASK_NOT_STARTED);
        progressRate = 0;
        status = "";
        return SHOW_PROGRESSBAR_INDEX;
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        getComponentInstance().setTaskState(ProgressBar.TASK_NOT_STARTED);
        progressRate = 0;
        status = "";
        return IndexBackingBean.INDEX_ACTION;
    }
    
}
