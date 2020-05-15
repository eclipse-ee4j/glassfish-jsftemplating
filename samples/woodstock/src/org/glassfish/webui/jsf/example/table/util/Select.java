/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.table.util;

import org.glassfish.webui.jsf.example.util.ExampleUtilities;

import com.sun.data.provider.FieldKey;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.TableDataProvider;
import org.glassfish.webui.jsf.event.TableSelectPhaseListener;

import jakarta.faces.context.FacesContext;
import jakarta.el.ValueExpression;

// This class provides functionality for select tables.
//
// Note: UI guidelines recomend that rows should be unselected when no longer in
// view. For example, when a user selects rows of the table and navigates to
// another page. Or, when a user applies a filter or sort that may hide
// previously selected rows from view. If a user invokes an action to delete
// the currently selected rows, they may inadvertently remove rows not
// displayed on the current page. Using TableSelectPhaseListener ensures
// that invalid row selections are not rendered by clearing selected state
// after the render response phase.
public class Select {
    private TableSelectPhaseListener tspl = null; // Phase listener.
    private Group group = null; // Group util.

    // Default constructor.
    public Select(Group group) {
        this.group = group;
        tspl = new TableSelectPhaseListener();
    }

    // Construct an instance. The given flag indicates that selected
    // objects should not be cleared after the render response phase.
    public Select(Group group, boolean keepSelected) {
        this.group = group;
        tspl = new TableSelectPhaseListener(keepSelected);          
    }

    // Clear selected state from phase listener (e.g., when deleting rows).
    public void clear() {
        tspl.clear();
    }

    // Test flag indicating that selected objects should not be cleared.
    public boolean isKeepSelected() {
        return tspl.isKeepSelected();
    }

    // Set flag indicating that selected objects should not be cleared.
    public void keepSelected(boolean keepSelected) {
        tspl.keepSelected(keepSelected);
    }

    // Get selected property.
    public Object getSelected() {
	return tspl.getSelected(getTableRow());
    }

    // Set selected property.
    public void setSelected(Object object) {
        RowKey rowKey = getTableRow();
        if (rowKey != null) {
            tspl.setSelected(rowKey, object);
        }
    }

    // Get selected value property.
    public Object getSelectedValue() {
        RowKey rowKey = getTableRow();
        return (rowKey != null) ? rowKey.getRowId() : null;
    }

    // Get the selected state -- Sort on checked state only.
    public boolean getSelectedState() {
        // Typically, selected state is tested by comparing the selected and 
        // selectedValue properties. In this example, however, the phase 
        // listener value is not null when selected.
        return getSelectedState(getTableRow());
    }

    // Get the selected state.
    public boolean getSelectedState(RowKey rowKey) {
        return tspl.isSelected(rowKey);
    }

    // Get current table row.
    //
    // Note: To obtain a RowKey for the current table row, the use the same 
    // sourceVar property given to the TableRowGroup component. For example, if 
    // sourceVar="name", use "#{name.tableRow}" as the expression string.
    private RowKey getTableRow() {
        FacesContext context = FacesContext.getCurrentInstance();
        ValueExpression ve = ExampleUtilities.createValueExpression(context,
		"#{name.tableRow}", Object.class);
        return (RowKey) ve.getValue(context.getELContext());
    }
}
