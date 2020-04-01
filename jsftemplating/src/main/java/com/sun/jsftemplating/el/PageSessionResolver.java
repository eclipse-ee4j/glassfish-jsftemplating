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

package com.sun.jsftemplating.el;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.el.EvaluationException;
import jakarta.faces.el.VariableResolver;


/**
 *  <p>	This <code>VariableResolver</code> exists to resolve "page session"
 *	attributes.  This concept, borrowed from NetDynamics / JATO, stores
 *	data w/ the page so that it is available throughout the life of the
 *	page.  This is longer than request scope, but usually shorter than
 *	session.  This implementation stores the attributes on the
 *	<code>UIViewRoot</code>.</p>
 *
 *  @author Ken Paulsen (ken.paulsen@sun.com)
 */
public class PageSessionResolver extends VariableResolver {

    /**
     *	<p> Constructor.</p>
     */
    public PageSessionResolver(VariableResolver orig) {
	super();
	_origVariableResolver = orig;
    }

    /**
     *	<p> This first delegates to the original <code>VariableResolver</code>,
     *	    it then checks "page session" to see if the value exists.</p>
     */
    public Object resolveVariable(FacesContext context, String name) throws EvaluationException {
	Object result = null;
	// Check to see if expression explicitly asks for PAGE_SESSION
	if (name.equals(PAGE_SESSION)) {
	    // It does, return the Map
	    UIViewRoot root = context.getViewRoot();
	    result = getPageSession(context, root);
	    if (result == null) {
		// No Map!  That's ok, create one...
		result = createPageSession(context, root);
	    }
	} else {
	    if (_origVariableResolver != null) {
		// Not explicit, let original resolver do its thing first...
		result = _origVariableResolver.resolveVariable(context, name);
	    }

	    if (result == null) {
		// Original resolver couldn't find anything, check page session
		Map<String, Serializable> map =
		    getPageSession(context, (UIViewRoot) null);
		if (map != null) {
		    result = map.get(name);
		}
	    }
	}
	return result;
    }

    /**
     *	<p> This method provides access to the "page session"
     *	    <code>Map</code>.  If it doesn't exist, it returns
     *	    <code>null</code>.  If the given <code>UIViewRoot</code> is null,
     *	    then the current <code>UIViewRoot</code> will be used.</p>
     */
    public static Map<String, Serializable> getPageSession(FacesContext ctx, UIViewRoot root) {
	if (root == null) {
	    root = ctx.getViewRoot();
	}
	return (Map<String, Serializable>)
	    root.getAttributes().get(PAGE_SESSION_KEY);
    }

    /**
     *	<p> This method will create a new "page session" <code>Map</code>.  It
     *	    will overwrite any existing "page session" <code>Map</code>, so be
     *	    careful.</p>
     */
    public static Map<String, Serializable> createPageSession(FacesContext ctx, UIViewRoot root) {
	if (root == null) {
	    root = ctx.getViewRoot();
	}
	// Create it...
	Map<String, Serializable> map = new HashMap<String, Serializable>(4);

	// Store it...
	root.getAttributes().put(PAGE_SESSION_KEY, map);

	// Return it...
	return map;
    }

    /**
     *	<p> The original <code>VariableResolver</code>.</p>
     */
    private VariableResolver _origVariableResolver = null;

    /**
     *	<p> The attribute key in which to store the "page" session Map.</p>
     */
    private static final String PAGE_SESSION_KEY	= "_ps";

    /**
     *	<p> The name an expression must use when it explicitly specifies page
     *	    session. ("pageSession")</p>
     */
    public static final String PAGE_SESSION		= "pageSession";
}
