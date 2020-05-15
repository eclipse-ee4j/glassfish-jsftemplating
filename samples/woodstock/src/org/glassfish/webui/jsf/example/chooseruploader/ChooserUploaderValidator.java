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

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import org.glassfish.webui.jsf.example.common.MessageUtil;

import java.io.File;

/**
 * Validator class for Chooser/Uploader example.
 */
public class ChooserUploaderValidator implements Validator {
    
    /** Creates a new instance of ChooserUploaderValidator */
    public ChooserUploaderValidator() {
    }
    
    /**
     * This method throws validator exception if specified directory is 
     * invalid or do not have write permission.
     */
    public void validate(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        
        if (value != null) {
            String dirPath = (String) value;
            File tempDir = new File(dirPath);
            
            if (!tempDir.isDirectory()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_invalidDir");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
                
            } else if (!tempDir.canWrite()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_permissionDenied");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } 
        }
    }
}
