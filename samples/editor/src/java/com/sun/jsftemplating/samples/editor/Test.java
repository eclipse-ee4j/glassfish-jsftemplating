/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.jsftemplating.samples.editor;

import java.util.*;
import org.glassfish.jsftemplating.layout.descriptors.LayoutComponent;
import org.glassfish.jsftemplating.layout.descriptors.LayoutDefinition;
import org.glassfish.jsftemplating.layout.descriptors.LayoutElement;
import org.glassfish.jsftemplating.layout.LayoutDefinitionManager;

public class Test {
    
    /** Creates a new instance of Test */
    public Test() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	LayoutDefinition ld = LayoutDefinitionManager.getLayoutDefinition(null, "propSheet.jsf");

	List compList = getList(ld, "", new ArrayList());

	for (int i=0; i < compList.size(); i++) {
		System.out.println(compList.get(i));
	}
        
    }


    private static List<String> getList(LayoutElement le, String indent, List newList) {
	List list = le.getChildLayoutElements();
	
	for (int i=0; i < list.size(); i++) {
		LayoutElement lel = (LayoutElement)list.get(i);
		if (lel instanceof LayoutComponent) {
			newList.add(indent + lel.getUnevaluatedId());
			getList(lel, indent + " ", newList);
			
		}	
	}
	
	return newList;
    }
    
}

