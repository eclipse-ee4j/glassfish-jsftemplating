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

package org.glassfish.jsftemplating.layout.event;

import jakarta.faces.component.UIComponent;


/**
 *  <p>	This interface defines a method for obtaining a
 *	<code>UIComponent</code>.  This is used by various
 *	<code>EventObject<code> implementations which hold
 *	<code>UIComponent</code>.  This allows event handling code to access
 *	the <code>UIComponent</code> related to the event.</p>
 *
 *  @author Ken Paulsen	(ken.paulsen@sun.com)
 */
public interface UIComponentHolder {

    /**
     *	<p> This method returns the <code>UIComponent</code> held by the
     *	    <code>Object</code> implementing this interface.</p>
     *
     *	@return The <code>UIComponent</code>.
     */
    public UIComponent getUIComponent();
}
