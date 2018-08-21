/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.table;

import java.io.Serializable;

import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableRowGroup;

import com.sun.webui.jsf.example.table.util.Dynamic;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/** Backing bean for dynamic table examples.
 *
 * Note: To simplify the example, this bean is used only to create the table 
 * layout. The resulting table will use methods already defined in TableBean.
 *
 * Note that we must implement java.io.Serializable or
 * javax.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class DynamicTableBean implements Serializable {
    private Dynamic dynamic = null; // Dynamic util.
    private Table table = null; // Table component.

    // Default constructor.
    public DynamicTableBean() {
        dynamic = new Dynamic();
    }

    // Get Table component.
    public Table getTable() {
        if (table == null) {
            // Get table row group.
            TableRowGroup rowGroup1 = dynamic.getTableRowGroup("rowGroup1",
                "#{TableBean.groupB.names}",
                "#{TableBean.groupB.select.selectedState}", null);

            // Set table row group properties.
            dynamic.setTableRowGroupChildren(rowGroup1,
                "#{TableBean.groupB.select.selectedState}",
                "#{TableBean.groupB.select.selected}",
                "#{TableBean.groupB.select.selectedValue}",
                "#{TableBean.groupB.actions.action}", true);

            // Get table.
            table = dynamic.getTable("table1", MessageUtil.getMessage("table_dynamicTitle"));
            table.getChildren().add(rowGroup1);
        }
        return table;
    }

    // Set Table component.
    //
    // @param table The Table component.
    public void setTable(Table table) {
        this.table = table;
    }
    
    // Action handler when navigating to the table index.
    public String showTableIndex() {
        reset();
        return TableBean.SHOW_TABLE_INDEX;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Reset values so next visit starts fresh.
    private void reset() {
        table = null;
        dynamic = new Dynamic();
    }
}
