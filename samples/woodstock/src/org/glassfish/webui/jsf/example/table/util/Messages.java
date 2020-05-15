/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.table.util;

public class Messages {
    private String message = null; // JSF messages.

    // Default constructor.
    public Messages() {
    }

    // Get message.
    public String getMessage() {
        return message;
    }

    // Set message.
    public void setMessage(String message) {
        this.message = message;
    }
}
