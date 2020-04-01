/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.index;

import com.sun.jsftemplating.util.FileUtil;

import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.JavaHtmlConverter;

import java.util.Map;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Serializable;

import jakarta.servlet.ServletContext;

/**
 * Backing bean for the show code page.
 */
public class ShowCodeBackingBean implements Serializable {    
    
    // name of the file to display its content
    private String fileName; 
    
    // relative path to java and properties resources
    private static final String RELATIVE_PATH = "com/sun/webui/jsf/example/";
    
    /** Default constructor */
    public ShowCodeBackingBean() {       
    }
  
    /**
     * Get file name.
     */
    public String getFileName() {
        // Get hyperlink parameter      
        Map map = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap();
        String param = (String) map.get("param");
        this.fileName = (param != null) ? param : 
                MessageUtil.getMessage("index_noFileName");
        
        return this.fileName;
    }
        
    /** 
     * Get the source code in the form of html
     *    
     */
    public String getSourceCode() {
        try {
	    boolean isJavaCode = false;
	    String sourceName = this.fileName;

	    if (sourceName.endsWith(".java")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = true;
	    } else if (sourceName.endsWith(".properties")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = false;
	    } else if (sourceName.endsWith(".jsp")
                    || sourceName.endsWith(".js")
                    || sourceName.endsWith(".jsf")
		    || sourceName.endsWith(".xml")) {		
		isJavaCode = false;
	    } else {
		throw new Exception("Unknown file type");
	    }

	    // Get the source file input stream
	    InputStream is = FileUtil.searchForFile(sourceName, null).openStream();
	    if (is == null) {
		throw new Exception("Resource not found: " + sourceName);
	    }

	    InputStreamReader reader = new InputStreamReader(is);
	    StringWriter writer = new StringWriter();

	    // It turns out that the Java->HTML converter does a decent
	    // job on the JSPs as well; we just want to tell it not to
	    // highlight keywords
	    JavaHtmlConverter.convert(reader, writer, true, isJavaCode);
            
	    return writer.getBuffer().toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Exception: " + e.toString();
	}        
    }    
}
