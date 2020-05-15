/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.orderablelist;

/**
 * This class represents a flavor.
 */
public class Flavor {
    
    /** Name of a flavor. */
    private String name;
    
    /** Creates a new instance of Flavor. */
    public Flavor() {
    }
 
    /** Creates a new instance of Flavor. */
    public Flavor(String name) {
        this.name = name;
    }
    
    /** Get the name of the flavor. */
    public String getName() {
        return this.name;
    }
    
    /** Set the name of the flavor. */
    public void setName(String name) {
        this.name = name;
    }
 
    /** Returns a string representation of the object. */
    public String toString() { 
        return name;
    }
}
