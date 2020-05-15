/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.index;

import org.glassfish.webui.jsf.example.index.TableData;
import org.glassfish.webui.jsf.example.index.AppData;
import org.glassfish.webui.jsf.example.common.MessageUtil;

import org.glassfish.webui.jsf.component.Hyperlink;
import org.glassfish.webui.jsf.component.TableColumn;
import org.glassfish.webui.jsf.component.Markup;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;

import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIParameter;

import java.util.ArrayList;
import java.io.Serializable;

import org.glassfish.webui.jsf.example.util.ExampleUtilities;


/**
 * This bean class provides data to the index page table
 * in the example app.
 */
public class IndexBackingBean implements Serializable {
    
    // Hyperlink id
    private static final String HYPERLINK_ID = "link";
    
    // The outcome strings used in the faces config file
    private static final String SHOWCODE_ACTION = "showCode";
    public static final String INDEX_ACTION = "showIndex";
    
    // TableColumn component.
    private TableColumn tableColumn = null; 
    
    // Table data
    private TableData tableData = null; 
    
    // Table provider
    private TableDataProvider provider = null;
    
    /** Default constructor. */
    public IndexBackingBean() {           
        tableData = new TableData(); 
    }        
    
    /**
     * Get table data provider
     */
    public TableDataProvider getDataProvider() {
        if (provider == null) {
            provider = new ObjectArrayDataProvider(getTableData());
        }        
        return provider;
    }  
    
    /**
     * Get table data
     */
    public AppData[] getTableData() {               
        return tableData.data;        
    }
      
    /**
     * Get tableColumn component
     */
    public TableColumn getTableColumn() {        
        if (tableColumn == null) {
            String id; 
            int maxLinks = 0;
            int numFiles = 0;
            
            // create tableColumn
            tableColumn = createTableColumn("col3", "index_filesHeader");                                                                                                             
            
            // Find the maximum number of hyperlinks that should be created
            // by counting the number of files in the AppData array object. 
            AppData[] tableData = getTableData();                        
            for (int k = 0; k < tableData.length; k++) {
                 numFiles = tableData[k].getFiles().length;
                 if (numFiles > maxLinks) {
                     maxLinks = numFiles;
                 }
            }
                                               
            // Create hyperlinks up to the maximum number of files
            // in the AppData object. Use the "rendered" attribute 
            // of the hyperlink component to hide it if there are
            // less links to show.
            if (maxLinks > 0) {                    
                for (int i = 0; i < maxLinks; i++) {                
                    id = HYPERLINK_ID + "_" + i;               
                    
                    // Create hyperlinks for each file in an example app
                    // to show the source code
                    Hyperlink hyperlink = createHyperlink(id,
                            "#{data.value.files["+ i + "]}",
                            "#{IndexBean.action}",
                            "#{data.value.files[" + i + "]}");  
                    
                    // The hyperlink is an ActionSource component                    
                    // which should be marked immediate by default. 
                    hyperlink.setImmediate(true);
                    
                    // Don't display the link if no file name exists.
                    ExampleUtilities.setValueExpression(hyperlink, "rendered",
                            "#{data.value.files["+ i + "] != null}");                                                 
                    
                    // Add new lines between the hyperlinks.                    
                    Markup markup = createMarkup("br", true);                    
                    ExampleUtilities.setValueExpression(markup, "rendered",
                            "#{data.value.files["+ i + "] != null}");                    
                    
                    tableColumn.getChildren().add(hyperlink);
                    tableColumn.getChildren().add(markup);            
                }
            }
        }        
        return tableColumn;
    }
    
    /**
     * Set tableColumn component.
     *
     * @param tableColumn The TableColumn component
     */
    public void setTableColumn(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    /** 
     * Return the string used for hyperlink action.
     */
    public String action() {
        return new String(SHOWCODE_ACTION);       
    }
    
    /**
     * Return the string used for breadcrumbs action.
     */
    public String showIndex() {
        return new String(INDEX_ACTION);
    }
        
    /**
     * Helper method to create table column
     *    
     * @param id The component id.
     * @param header The component header text.
     */
    private TableColumn createTableColumn(String id, String header) {
        TableColumn col = new TableColumn();        
        col.setId(id);
        col.setHeaderText(MessageUtil.getMessage(header));        
        return col;        
    }  
    
    /**
     * Helper method to create hyperlink.
     *
     * @param id The component id.
     * @param text Value binding expression for text.
     * @param action Method binding expression for action.
     * @param parameter Value binding expression for parameter.
     */
    private Hyperlink createHyperlink(String id, String text, String action,
            String parameter) {
        // Get hyperlink.
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setId(id); // Set id.
        ExampleUtilities.setValueExpression(hyperlink,
		"text", text); // Set text.
        ExampleUtilities.setMethodExpression(hyperlink,
		"actionExpression", action); // Set action.

        // Create paramerter.
        UIParameter param = new UIParameter();
        param.setId(id + "_param");
        param.setName("param");
        ExampleUtilities.setValueExpression(param,
		"value", parameter); // Set parameter.
        hyperlink.getChildren().add(param);

        return hyperlink;
    }
       
    /**
     * Helper method to create markup
     */
    private Markup createMarkup(String tag, boolean singleton) {
        // Get markup.
        Markup markup = new Markup();
        markup.setTag(tag);
        markup.setSingleton(singleton);        
        return markup;
    }
    
}
