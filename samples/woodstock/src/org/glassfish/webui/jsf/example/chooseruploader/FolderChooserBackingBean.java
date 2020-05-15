/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.chooseruploader;

import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import org.glassfish.webui.jsf.model.UploadedFile;
import org.glassfish.webui.jsf.example.index.IndexBackingBean;
import org.glassfish.webui.jsf.example.common.MessageUtil;

import java.io.*;
import java.util.List;


/**
 * Backing Bean for Folder Chooser example.
 */
public class FolderChooserBackingBean implements Serializable {
    
    // String constant.
    public static final String WINDOWS_OS = "WINDOW";
    
    // Holds value of lookin property.
    private File lookin = null;
    
    // Holds value of selected property.
    private File selected = null;
    
    /** Creates a new instance of FileChooserBackingBean */
    public FolderChooserBackingBean() {
        String osName = System.getProperty("os.name").toUpperCase();
        if (osName.startsWith(WINDOWS_OS)) {
            lookin = new File("c:\\\\windows");
        } else {
            lookin = new File("/usr");
        }
    }
    
    /**
     *Getter for property lookin
     *@return Value of property lookin.
     */
    public File getLookin() {
        return lookin;
    }
    
    /**
     *returns absolute path for selected objects.
     */
    private String valueString(Object object) {
        
        if (object instanceof List) {
            List files = (List)object;
            Object obj = files.get(0);
            if (obj instanceof File) {
                object = files.toArray(new File[files.size()]);
            } else
                if (obj instanceof String) {
                object = files.toArray(new String[files.size()]);
                } else {
                String value = files.get(0).toString();
                for (int i = 1; i < files.size(); ++i) {
                    value = value + ", " + files.get(i).toString();
                }
                return value;
                }
        }
        
        String value = null;
        if (object instanceof File[]) {
            File[] files = (File[])object;
            value = files[0].getAbsolutePath();
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i].getAbsolutePath();
            }
        } else
            if (object instanceof File) {
            File file = (File)object;
            value = file.getAbsolutePath();
            } else
                if (object instanceof String[]) {
            String[] files = (String[])object;
            value = files[0];
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i];
            }
                } else
                    if (object instanceof String) {
            String file = (String)object;
                  value = file;
                }
        return value;
    }
    
    /**
     *Getter method for selected.
     */
    public File getSelected() {
        return selected;
    }
    
    /**
     *Setter method for selected.
     */
    public void setSelected(File selected) {
        this.selected = selected;
    }
    
    /**
     *Getter method for folderName.
     */
    public String getFolderName() {
        
        if (selected != null) {
            return valueString(selected);
        } else return null;
        
    }
    
    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {           
           return MessageUtil.getMessage("chooseruploader_summary");
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
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        selected = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        
        selected = null;
        return "showChooserUploader";
    }
}

