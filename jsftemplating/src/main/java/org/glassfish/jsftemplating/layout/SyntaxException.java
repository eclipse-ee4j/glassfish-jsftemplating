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

package org.glassfish.jsftemplating.layout;

import org.glassfish.jsftemplating.TemplatingException;


/**
 *  <p>	This exception is thrown when a syntax error has occurred.  For i/o
 *	related exceptions, see {@link LayoutDefinitionException}.</p>
 */
public class SyntaxException extends TemplatingException {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public SyntaxException(String msg, Throwable ex) {
	super(msg, ex);
    }

    /**
     *
     */
    public SyntaxException() {
	super();
    }

    /**
     *
     */
    public SyntaxException(Throwable ex) {
	super(ex);
    }

    /**
     *
     */
    public SyntaxException(String msg) {
	super(msg);
    }
}
