/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.common;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;

import java.util.List;

/**
 * This class contains data provider.
 */
public class UserData {
    
    /** Data provider. */
    private TableDataProvider provider = null; 
    
    /** Default constructor. */
    public UserData() {
    }
    
    /** Construct an instance using given Object array. */
    public UserData(Object[] array) {        
        provider = new ObjectArrayDataProvider(array);
    }    
    
    /** Get data provider. */
    public TableDataProvider getDataProvider() {
        return provider;
    }
}
