/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

//<!--

// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment cuases compile errors because it terminates the outer comment.

// Use this function to initialize all rows displayed in the table when the
// state of selected components change (i.e., checkboxes or radiobuttons used to
// de/select rows of the table). This functionality requires the selectId 
// property of the tableColumn component to be set.
// 
// Note: Use setTimeout when invoking this function. This will ensure that 
// checkboxes and radiobutton are selected immediately, instead of waiting for 
// the onClick event to complete. For example: 
//
// onClick="setTimeout('initAllRows(); disableActions()', 0)"
function initAllRows() {
    // Disable table actions by default.
    var table = document.getElementById("form1:table1");
    table.initAllRows();
}

//-->
