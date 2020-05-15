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

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import org.glassfish.webui.jsf.component.Checkbox;
import org.glassfish.webui.jsf.component.TableRowGroup;

import java.util.List;

// This class contains data provider and util classes. Note that not all util
// classes are used for each example.
public class Group {
    private TableRowGroup tableRowGroup = null; // TableRowGroup component.
    private TableDataProvider provider = null; // Data provider.
    private Checkbox checkbox = null; // Checkbox component.
    private Preferences prefs = null; // Preferences util.
    private Messages messages = null; // Messages util.
    private Actions actions = null; // Actions util.
    private Filter filter = null; // Filter util.
    private Select select = null; // Select util.

    // Default constructor.
    public Group() {
        actions = new Actions(this);
        filter = new Filter(this);
        select = new Select(this);
        prefs = new Preferences();
        messages = new Messages();
    }

    // Construct an instance using given Object array.
    public Group(Object[] array) {
        this();
        provider = new ObjectArrayDataProvider(array);
    }

    // Construct an instance using given List.
    public Group(List list) {
        this();
        provider = new ObjectListDataProvider(list);
    }

    // Construct an instance using given List and a
    // flag indicating that selected objects should
    // not be cleared after the render response phase.        
    public Group(List list, boolean keepSelected) {
        actions = new Actions(this);        
        select = new Select(this, keepSelected);
        messages = new Messages();      
        provider = new ObjectListDataProvider(list);
    }
    
    // Get data provider.
    public TableDataProvider getNames() {
        return provider;
    }

    // Get Actions util.
    public Actions getActions() {
        return actions;
    }

    // Get Filter util.
    public Filter getFilter() {
        return filter;
    }

    // Get Messages util.
    public Messages getMessages() {
        return messages;
    }

    // Get Preferences util.
    public Preferences getPreferences() {
        return prefs;
    }

    // Get Select util.
    public Select getSelect() {
        return select;
    }

    // Get tableRowGroup component.
    public TableRowGroup getTableRowGroup() {
        return tableRowGroup;
    }

    // Set tableRowGroup component.
    public void setTableRowGroup(TableRowGroup tableRowGroup) {
        this.tableRowGroup = tableRowGroup;
    }

    // Get checkbox component.
    public Checkbox getCheckbox() {
        return checkbox;
    }

    // Set checkbox component.
    public void setCheckbox(Checkbox checkbox) {
        this.checkbox = checkbox;
    }
}
