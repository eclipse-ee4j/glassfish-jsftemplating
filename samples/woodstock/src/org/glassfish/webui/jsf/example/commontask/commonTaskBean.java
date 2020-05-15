/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.commontask;

import org.glassfish.webui.jsf.example.index.IndexBackingBean;

public class commonTaskBean {
    
    /** Creates a new instance of commonTaskBean */
    public commonTaskBean() {
    }
    
    public String taskAction() {
        return "task";
    }
    
    public String showExampleIndex() {
        return IndexBackingBean.INDEX_ACTION;
    }
}
