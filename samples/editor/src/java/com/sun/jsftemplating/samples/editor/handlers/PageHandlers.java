/*
 * Copyright (c) 2006, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * PageHandlers.java
 *
 * Created on June 9, 2006, 1:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.glassfish.jsftemplating.samples.editor.handlers;

import org.glassfish.jsftemplating.annotation.Handler;
import org.glassfish.jsftemplating.annotation.HandlerInput;
import org.glassfish.jsftemplating.annotation.HandlerOutput;
import org.glassfish.jsftemplating.layout.LayoutDefinitionException;
import org.glassfish.jsftemplating.layout.LayoutDefinitionManager;
import org.glassfish.jsftemplating.layout.descriptors.ComponentType;
import org.glassfish.jsftemplating.layout.descriptors.LayoutComponent;
import org.glassfish.jsftemplating.layout.descriptors.LayoutDefinition;
import org.glassfish.jsftemplating.layout.descriptors.LayoutElement;
import org.glassfish.jsftemplating.layout.descriptors.handler.HandlerContext;
import org.glassfish.jsftemplating.layout.template.TemplateWriter;
import org.glassfish.jsftemplating.util.LayoutElementUtil;
import org.glassfish.jsftemplating.util.FileUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.faces.context.FacesContext;


/**
 *
 * @author Administrator
 */
public class PageHandlers {

    /**
     * Creates a new instance of PageHandlers
     */
    public PageHandlers() {
    }

    /**
     *	<p>
     *	</p>
     *
     *	<ul><li>pageName --</li>
     *	    <li>parent --</li>
     *	    <li>type --<li>
     *	    <li>id --<li>
     *	    <li>label --<li>
     *	    <li>text --<li>
     *	    <li>nvps --<li></ul>
     *
     */
    @Handler(id="addLayoutComponentToPage",
	    input={
		@HandlerInput(name="pageName", type=String.class, required=true),
		@HandlerInput(name="parent", type=String.class, required=true),
		@HandlerInput(name="type", type=String.class, required=true),
		@HandlerInput(name="id", type=String.class, required=false),
		@HandlerInput(name="label", type=String.class, required=false),
		@HandlerInput(name="text", type=String.class, required=false),
		@HandlerInput(name="nvps", type=Iterator.class, required=false)
	    },
	    output={
		@HandlerOutput(name="newComponent", type=LayoutComponent.class)
	    })
    public static void addLayoutComponentToPage(HandlerContext context) {
	FacesContext facesCtx = context.getFacesContext();
	// Get Page
	String pageName = (String) context.getInputValue("pageName");
        LayoutDefinition ld = LayoutDefinitionManager.getLayoutDefinition(
		facesCtx, pageName);

	// Walk the LD
	LayoutElement parent = ld;
	String id = (String) context.getInputValue("parent");
	if (id == null) {
// FIXME: report an error?
	    return;
	}
	StringTokenizer tok = new StringTokenizer(id, ":");
	while (tok.hasMoreTokens()) {
	    id = tok.nextToken();
	    parent = parent.getChildLayoutElement(id);
	    if (parent == null) {
		throw new IllegalArgumentException("LayoutElement '" + id
			+ "' not found when attempting to resolve "
			+ "LayoutElement identified by '" + parent + "'");
	    }
	}

	// Create the new LayoutComponent
	ComponentType type = LayoutDefinitionManager.getGlobalComponentType(
		facesCtx, (String) context.getInputValue("type"));
	if (type == null) {
	    throw new IllegalArgumentException("Unkown ComponentType: "
		    + context.getInputValue("type"));
	}
	id = (String) context.getInputValue("id");
	if (id == null) {
	    id = LayoutElementUtil.getGeneratedId(null);
	}
	LayoutComponent comp = new LayoutComponent(parent, id, type);
	comp.setNested(true);
	//checkForFacetChild

	// Add properties to it....
	String value = (String) context.getInputValue("label");
	if (value != null) {
	    comp.addOption("label", value);
	}
	value = (String) context.getInputValue("text");
	if (value != null) {
	    comp.addOption("text", value);
	}
	int idx = 0;
	String name = null;
	Iterator<String> it = (Iterator<String>) context.getInputValue("nvps");
	if (it != null) {
	    while (it.hasNext()) {
		value = it.next();
		idx = value.indexOf('=');
		if (idx == -1) {
		    throw new IllegalArgumentException("Invalid NVP: '" + value
			    + "'.  It must be of the form 'name=value'!");
		}
		name = value.substring(0, idx).trim();
		value = value.substring(idx + 1).trim();
		comp.addOption(name, value);
	    }
	}

	// Add the component to the parent...
	parent.addChildLayoutElement(comp);

	// Write out the page w/ the changes...
	writePage(facesCtx.getExternalContext().getContext(), pageName, ld);

	// Return the new component
	context.setOutputValue("newComponent", comp);
    }

    /**
     *	<p> This handler returns a list of display names and the corresponding
     *      list of qualified names of all the LayoutComponents in a given page.</p>
     *
     *	<p> Input value: "pageName" -- Type: <code>java.lang.String</code></p>
     *
     *	<p> Output value: "displayNames" -- Type: <code>java.util.List</code></p>
     *
     *  <p> Output value: "qualifiedNames" -- Type: <code>java.util.List</code></p>
     *	@param	context	The HandlerContext.
     */
    @Handler(id="getPageComponentNames",
	input={
	    @HandlerInput(name="pageName", type=String.class, required=true)},
	output={
	    @HandlerOutput(name="displayNames", type=List.class),
            @HandlerOutput(name="qualifiedNames", type=List.class)})
    public static void getPageComponentNames(HandlerContext context) {
	String pageName = (String) context.getInputValue("pageName");
        LayoutDefinition ld = null;
	FacesContext facesCtx = context.getFacesContext();
	try {
	    ld = LayoutDefinitionManager.getLayoutDefinition(
		facesCtx, pageName);
	} catch (LayoutDefinitionException ex) {
	    // New page
	    ld = LayoutDefinitionManager.getLayoutDefinition(
		facesCtx, "template.jsf");
// FIXME... must *clone* this tree... or read it directly from the Reader instead of through the Manager
	    LayoutDefinitionManager.putCachedLayoutDefinition(facesCtx, pageName, ld);
	    // Write it to disk...
	    writePage(facesCtx.getExternalContext().getContext(), pageName, ld);
	}
        List<String> displayNames = getDisplayList(ld, new ArrayList(), "");
        List<String> qualifiedNames = getQualifiedList(ld, new ArrayList(), "");
	context.setOutputValue("displayNames", displayNames);
        context.setOutputValue("qualifiedNames", qualifiedNames);
    }

    /**
     *	<p> This method takes in the name of a page and writes the given
     *	    <code>LayoutDefinition</code> to the docroot.  The
     *	    <code>context</code> passed in must be a
     *	    <code>ServletContext</code> or <code>PortletContext</code>.</p>
     */
    private static void writePage(Object context, String pageName, LayoutDefinition ld) {
	String path = FileUtil.getRealPath(context, pageName);
	if (path == null) {
	    throw new IllegalArgumentException(
		"Unable to determine where to write '" + pageName
		+ "'!  Is this application is directory deployed?");
	}
	try {
	    OutputStream os = new FileOutputStream(path);
	    new TemplateWriter(os).write(ld);
	    os.close();
	} catch (FileNotFoundException ex2) {
	    throw new IllegalArgumentException("Unable to write '" + path
		+ "'!  Is this application is directory deployed and "
		+ "the directory is writable?", ex2);
	} catch (IOException ex2) {
	    throw new RuntimeException(ex2);
	}
    }

    private static List<String> getDisplayList(LayoutElement le, List displayList, String indent) {
	Formatter printf = null;
	List list = le.getChildLayoutElements();
	for (int i=0; i < list.size(); i++) {
	    LayoutElement lel = (LayoutElement)list.get(i);
	    if (lel instanceof LayoutComponent) {
		printf = new Formatter();
		printf.format("%-25s",
		    ((LayoutComponent) lel).getType().getId());
		displayList.add(printf.toString().replaceAll(" ", "&nbsp;") + indent + lel.getUnevaluatedId());
		getDisplayList(lel, displayList, indent + "&nbsp;");
	    }	
	}

	return displayList;
    }

    private static List<String> getQualifiedList(LayoutElement le, List qualifiedList, String longName) {
	List list = le.getChildLayoutElements();
	
	for (int i=0; i < list.size(); i++) {
	    LayoutElement lel = (LayoutElement)list.get(i);
	    if (lel instanceof LayoutComponent) {
		qualifiedList.add(longName + lel.getUnevaluatedId());
		getQualifiedList(lel, qualifiedList, longName + lel.getUnevaluatedId() + ":");
	    }	
	}

	return qualifiedList;
    }


}
