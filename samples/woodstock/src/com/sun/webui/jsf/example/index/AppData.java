/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.index;

import com.sun.webui.jsf.example.index.AppAction;

/**
 * A convenient class to wrap data about a given example.
 */
public class AppData {    
    
    /** The example app name */
    private String name;

    /** The concepts illustrated by this example */
    private String concepts;
    
    /** The example app action */
    private String appAction;

    /** 
     * Array of file names that will have source code
     * links for this example
     */
    private String[] files;               
    
    /**
     * Accepts info necessary to describe the given
     * example.
     *
     * @param name The name of the example
     * @param concepts The concepts illustrated by this example
     * @param appAction The example app action
     * @param files Array of file names for this example
     */
    public AppData(String name, String concepts, String appAction, String[] files) {
        this.name = name;
	this.concepts = concepts;
        this.appAction = appAction;
	this.files = files;
    }    
    
    /**
     * Get the name of the example
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the concepts illustrated by this example
     */
    public String getConcepts() {
        return concepts;
    }         
    
    /**
     * Get AppAction.
     */
    public AppAction getAppAction() {               
        return new AppAction(appAction);                
    }
    
    /**
     * Get array of files for this example
     */
    public String[] getFiles() {
        return files;
    }      
}
