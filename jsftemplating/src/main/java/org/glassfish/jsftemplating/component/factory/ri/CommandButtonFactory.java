/*
 * Copyright (c) 2006, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jsftemplating.component.factory.ri;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;

import org.glassfish.jsftemplating.annotation.UIComponentFactory;
import org.glassfish.jsftemplating.component.factory.ComponentFactoryBase;
import org.glassfish.jsftemplating.layout.descriptors.LayoutComponent;


/**
 *  <p>	This factory is responsible for instantiating a <code>CommandButton
 *	UIComponent</code>.</p>
 *
 *  <p>	The {@link org.glassfish.jsftemplating.layout.descriptors.ComponentType}
 *	id for this factory is: "h:commandButton".</p>
 *
 *  @author Ken Paulsen	(ken.paulsen@sun.com)
 */
@UIComponentFactory("h:commandButton")
public class CommandButtonFactory extends ComponentFactoryBase {

    /**
     *	<p> This is the factory method responsible for creating the
     *	    <code>UIComponent</code>.</p>
     *
     *	@param	context	    The <code>FacesContext</code>
     *	@param	descriptor  The {@link LayoutComponent} descriptor associated
     *			    with the requested <code>UIComponent</code>.
     *	@param	parent	    The parent <code>UIComponent</code>
     *
     *	@return	The newly created <code>CommandButton</code>.
     */
    public UIComponent create(FacesContext context, LayoutComponent descriptor, UIComponent parent) {
	// Create the UIComponent
	UIComponent comp = createComponent(context, COMPONENT_TYPE, descriptor, parent);

	// Set all the attributes / properties
	setOptions(context, descriptor, comp);

	// Return the component
	return comp;
    }

    /**
     *	<p> The <code>UIComponent</code> type that must be registered in the
     *	    <code>faces-config.xml</code> file mapping to the UIComponent class
     *	    to use for this <code>UIComponent</code>.</p>
     */
    public static final String COMPONENT_TYPE	= "jakarta.faces.HtmlCommandButton";
}
