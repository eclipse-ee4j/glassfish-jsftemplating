/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.table.util;

// This class provides functionality for table preferences.
public class Preferences {
    private String preference = null; // Rows preference.
    private int rows = 5; // Rows per page.

    // Default constructor.
    public Preferences() {
    }

    // Table preferences event.
    public void applyPreferences() {
        try {
            int rows = Integer.parseInt(preference);
            if (rows > 0) {
                this.rows = rows;
            }
        } catch (NumberFormatException e) {}
    }

    // Get rows per page.
    public int getRows() {
        return rows;
    }

    // Get preference.
    public String getPreference() {
        return Integer.toString(rows);
    }

    // Set preference.
    public void setPreference(String value) {
        preference = value;
    }
}
