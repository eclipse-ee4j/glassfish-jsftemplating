/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.index;

/**
 * This class is used to get the action value of an example app. 
 */
public class AppAction {

    private String action;    
      
    /**
     * Construct a new instance of AppAction.
     *
     * @param action The example app action
     */
    public AppAction(String action) {
        this.action = action;
    }
    
    /**
     * Return the action value
     */
    public String action() {
        return action;        
    }
}
