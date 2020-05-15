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

import org.glassfish.webui.jsf.example.index.AppData;

/**
 * This class serves as a data wrapper for the Table component in the
 * Example Application Index page.
 */
public class TableData {  
   
    // Fill data for each example in an array object.            
    protected static final AppData[] data = {
        new AppData("index_buttonName","index_buttonConcepts", "showButton",
                new String[] {
                    "button/Button.jsf",
                    "button/ButtonResults.jsf",
                    "button/ButtonBackingBean.java"}
        ),
        new AppData("index_cbrbName","index_cbrbConcepts", "showCheckboxRadiobutton",
                new String[] {
                    "cbrb/checkboxRadiobutton.jsf",
                    "cbrb/checkboxRadiobuttonResults.jsf",
                    "cbrb/CheckboxRadiobuttonBackingBean.java"}
        ),
        new AppData("index_labelName","index_labelConcepts", "showLabel",
                new String[] {
                    "label/Label.jsf",
                    "label/LabelResults.jsf",
                    "label/Help.jsf",
                    "label/LabelBackingBean.java"}
        ),
        new AppData("index_alertName","index_alertConcepts", "showAlertIndex",
                new String[] {
                    "alert/Alert.jsf",
                    "alert/InlineAlert.jsf",
                    "alert/PageAlertExample.jsf",
                    "alert/PageAlert.jsf",
                    "alert/HelpAlert.jsf",
                    "alert/InlineAlertBackingBean.java",        
                    "alert/PageAlertBackingBean.java"}
        ),
        new AppData("index_textInputName", "index_textInputConcepts", "showTextInput", 
                new String[] {
                    "field/TextInput.jsf",
                    "field/TextInputResults.jsf",
                    "field/TextInputBackingBean.java"}
        ),
        new AppData("index_tableName", "index_tableConcepts", "showTableIndex", 
                new String[] {
                    "table/actions.jsf",
                    "table/actionsBottom.jsf",
                    "table/actionsTop.jsf",
                    "table/alarms.jsf",
                    "table/basicTable.jsf",
                    "table/customTitle.jsf",
                    "table/dynamicGroupTable.jsf",
                    "table/dynamicTable.jsf",
                    "table/embeddedActions.jsf",
                    "table/emptyCells.jsf",
                    "table/filter.jsf",
                    "table/filterPanel.jsf",
                    "table/groupTable.jsf",
                    "table/hiddenRowsActionsBottom.jsf",        
                    "table/hiddenRowsActionsTop.jsf",        
                    "table/hiddenSelectedRows.jsf",                    
                    "table/index.jsf",
                    "table/multipleHeadersFooters.jsf",
                    "table/liteTable.jsf",
                    "table/paginatedTable.jsf",
                    "table/preferences.jsf",
                    "table/preferencesPanel.jsf",
                    "table/selectMultipleRows.jsf",
                    "table/selectSingleRow.jsf",
                    "table/spacerColumn.jsf",
                    "table/sortableTable.jsf",
                    "table/table.jsf",
                    "table/TableBean.java",
                    "table/DynamicGroupTableBean.java",
                    "table/DynamicTableBean.java",
                    "table/util/Actions.java",
                    "table/util/Dynamic.java",
                    "table/util/Filter.java",
                    "table/util/Group.java",
                    "table/util/Messages.java",
                    "table/util/Name.java",
                    "table/util/Preferences.java",
                    "table/util/Select.java",
                    "table/js/actions.js",
                    "table/js/filter.js",
                    "table/js/preferences.js",
                    "table/js/select.js"}
        ),
        new AppData("index_addRemoveName", "index_addRemoveConcepts", "showAddRemove",
                new String[] {
                    "addremove/AddRemove.jsf",
                    "addremove/AddRemoveResults.jsf",
                    "addremove/AddRemoveBackingBean.java",
                    "common/UserData.java"}
        ),
        new AppData("index_orderableListName", "index_orderableListConcepts", "showOrderableList", 
                new String[] {
                    "orderablelist/OrderableList.jsf",
                    "orderablelist/OrderableListResults.jsf",
                    "orderablelist/OrderableListBackingBean.java",
                    "orderablelist/Flavor.java",
                    "common/UserData.java"}        
        ),
        new AppData("index_chooserUploader", "index_chooserUploaderConcepts", "showChooserUploader",
                new String[] {
                    "chooseruploader/fileUploader.jsf",
                    "chooseruploader/fileChooser.jsf", 
                    "chooseruploader/folderChooserPopup.jsf",
                    "chooseruploader/fileUploaderPopup.jsf",
                    "chooseruploader/folderChooser.jsf", 
                    "chooseruploader/fileChooserPopup.jsf",        
                    "chooseruploader/FileChooserBackingBean.java",  
                    "chooseruploader/FolderChooserBackingBean.java",
                    "chooseruploader/FileUploaderBackingBean.java",        
                    "chooseruploader/ChooserUploaderBackingBean.java",
                    "chooseruploader/ChooserUploaderValidator.java"}
        ),
        new AppData("index_menuListName", "index_menuListConcepts", "showMenuList", 
                new String[] {
                    "menu/MenuList.jsf",
                    "menu/MenuListResults.jsf",
                    "menu/MenuListBackingBean.java"}
        ),
        new AppData("index_mastheadName", "index_mastheadConcepts", "showMasthead",
                new String[] {
                    "masthead/Index.jsf",
                    "masthead/Masthead.jsf",
                    "masthead/MastheadFacets.jsf",
                    "masthead/Version.jsf",
                    "masthead/Popup.jsf",
                    "masthead/Images.jsf",
                    "masthead/ResultMasthead.jsf",
                    "masthead/ResultMastheadFacets.jsf",
                    "masthead/MastheadBackingBean.java"}
        ),
        new AppData("index_propertysheet", "index_propertySheetConcepts", "showPropertySheet",
                new String[] {
                    "propertysheet/PropertySheet.jsf",
                    "propertysheet/PropertySheetResult.jsf",         
                    "propertysheet/PropertySheetBackingBean.java"}
        ),
        new AppData("index_editablelist", "index_editableListConcepts", "showEditableList",
                new String[] {
                    "editablelist/editableList.jsf",
                    "editablelist/editableListResult.jsf",        
                    "editablelist/EditableListBackingBean.java"}
        ),
        new AppData("index_pagetitleName", "index_pagetitleConcepts", "showPagetitle",
                new String[] {
                    "pagetitle/Pagetitle.jsf",
                    "pagetitle/PagetitleBackingBean.java"}
        ),
        new AppData("index_hyperlink", "index_hyperlinkConcepts", "showHyperlink",
                new String[] {
                    "hyperlink/hyperLink.jsf",
                    "hyperlink/hyperLinkResult.jsf",        
                    "hyperlink/HyperlinkBackingBean.java"}
        ),
        new AppData("index_statictextName", "index_statictextConcepts", "showStaticText",
                new String[] {
                    "statictext/Statictext.jsf",
                    "statictext/StatictextBackingBean.java",
                    "statictext/Employee.java",
                    "statictext/EmployeeConverter.java"}
        ),         
        new AppData("index_commonTaskName", "index_commonTaskConcepts", "showCommonTask",
                new String[] {
                    "commontask/commonTasks.jsf",
                    "commontask/sample.jsf",
                    "commontask/commonTaskBean.java"}
        ),
        new AppData("index_treeName", "index_treeConcepts", "showTreeIndex",
                new String[] {
                    "tree/content.jsf",
                    "tree/dynamicTree.jsf",
                    "tree/header.jsf",
                    "tree/index.jsf",
                    "tree/navTree.jsf",
                    "tree/treeFrame.jsf",
		    "common/ClientSniffer.java",
		    "util/ExampleUtilities.java",
		    "tree/DynamicTreeBackingBean.java",
		    "tree/NavTreeBackingBean.java"}
        ),
        new AppData("index_progressBar", "index_progressBarConcepts", "showProgressBar",
                new String[] {
                    "progressbar/index.jsf",
                    "progressbar/determinate.jsf",
                    "progressbar/indeterminate.jsf",
                    "progressbar/busy.jsf",
                    "progressbar/ProgressBarBackingBean.java"}
        ),
        new AppData("index_wizardName","index_wizardConcepts", "showWizardIndex",
                new String[] {
                    "wizard/index.jsf",
                    "wizard/simpleWizard.jsf",
                    "wizard/SimpleWizardBackingBean.java"}
        )
    };    
    
    /** Default constructor */
    public TableData() { 		
    }
}
	
