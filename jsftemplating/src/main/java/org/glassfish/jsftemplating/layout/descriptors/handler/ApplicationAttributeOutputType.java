/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jsftemplating.layout.descriptors.handler;


/**
 *  <p>	This class implements the OutputType interface to provide a way to
 *	get/set Output values from the Application Attribute Map.</p>
 *
 *  @author Ken Paulsen	(ken.paulsen@sun.com)
 */
public class ApplicationAttributeOutputType implements OutputType {

    /**
     *	<p> This method is responsible for retrieving the value of the Output
     *	    from a Application attribute.  'key' may be null, if this occurs, a
     *	    default name will be provided.  That name will follow the
     *	    following format:</p>
     *
     *	<p> [handler-id]:[output-name]</p>
     *
     *	@param	context	    The HandlerContext
     *
     *	@param	outDesc	    The IODescriptor for this Output value in
     *			    which to obtain the value
     *
     *	@param	key	    The optional 'key' to use when retrieving the
     *			    value from the Application attribute Map.
     *
     *	@return The requested value.
     */
    public Object getValue(HandlerContext context, IODescriptor outDesc, String key) {
	if (key == null) {
	    // Provide a reasonably unique default
	    key = context.getHandlerDefinition().getId()
		+ ':' + outDesc.getName();
	}

	// Get it from the Application attribute map
	return context.getFacesContext().getExternalContext().
	    getApplicationMap().get(key);
    }

    /**
     *	<p> This method is responsible for setting the value of the Output to
     *	    a Application attribute.  'key' may be null, in this case, a
     *	    default name will be provided.  That name will follow the
     *	    following format:</p>
     *
     *	<p> [handler-id]:[output-name]</p>
     *
     *	@param	context	    The HandlerContext
     *
     *	@param	outDesc	    The IODescriptor for this Output value in
     *			    which to obtain the value
     *
     *	@param	key	    The optional 'key' to use when setting the
     *			    value into the Application attribute Map
     *
     *	@param	value	    The value to set
     */
    public void setValue(HandlerContext context, IODescriptor outDesc, String key, Object value) {
	if (key == null) {
	    // Provide a reasonably unique default
	    key = context.getHandlerDefinition().getId()
		+ ':' + outDesc.getName();
	}

	// Get it from the Application attribute map
	context.getFacesContext().getExternalContext().
	    getApplicationMap().put(key, value);
    }
}
