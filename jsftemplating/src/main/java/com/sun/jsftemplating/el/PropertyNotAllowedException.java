/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

import jakarta.el.ELException;

/**
 * Thrown when a property could not be accessed while evaluating a
 * {@link jakarta.el.ValueExpression ValueExpression} or
 * {@link jakarta.el.MethodExpression MethodExpression}.
 *
 * @author avpinchuk
 */
public class PropertyNotAllowedException extends ELException {

    private static final long serialVersionUID = -1L;

    /**
     * Creates a {@link PropertyNotAllowedException} with no detail message.
     */
    public PropertyNotAllowedException() {
        super();
    }

    /**
     * Creates a {@link PropertyNotAllowedException} with the given detail message.
     *
     * @param message the detail message
     */
    public PropertyNotAllowedException(String message) {
        super(message);
    }

    /**
     * Creates a {@link PropertyNotAllowedException} with the given root cause.
     *
     * @param cause the originating cause of this exception
     */
    public PropertyNotAllowedException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@link PropertyNotAllowedException} with the given detail message
     * and root cause.
     *
     * @param message the detail message
     * @param cause the originating cause of this exception
     */
    public PropertyNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
