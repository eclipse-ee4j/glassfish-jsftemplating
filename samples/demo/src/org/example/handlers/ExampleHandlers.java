/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.example.handlers;

import com.sun.jsftemplating.annotation.Handler;
import com.sun.jsftemplating.annotation.HandlerInput;
import com.sun.jsftemplating.annotation.HandlerOutput;
import com.sun.jsftemplating.layout.descriptors.handler.HandlerContext;


/**
 *  <p>	This class is written to demonstrate how to write a
 *	<code>Handler</code>.</p>
 */
public class ExampleHandlers {

    /**
     *	<p> This is a handler for the demo app.  It contains 1 input and 1
     *	    output, both of type String.  This handler will look at the input
     *	    and produce a response to that input.  It is looking for the input
     *	    to equal 'abc', it provides the user a hint if they type something
     *	    other than abc.  If no input is provided, it prompts the user for
     *	    input.</p>
     *
     *	<p> Because handlers are defined via @annotations, you can move them
     *	    to from one file or package to another without changing your pages
     *	    that use the handlers.  Also notice that this Handler has an "id"
     *	    that is different from the method name.  The "id" must be unique
     *	    within the application.  It is good practice to qualify all
     *	    handlers to avoid a naming conflict.</p>
     *
     *	@param  context	The <code>HandlerContext</code>.
     */
    @Handler(id="Test.getResponse",
	input={
	    @HandlerInput(name="userInput", type=String.class)
	},
	output={
	    @HandlerOutput(name="response", type=String.class)
	})
    public static void calculateResponse(HandlerContext context) {
	// Get the input.
	String in = (String) context.getInputValue("userInput");

	// Do business logic...
	String resp = "Type something and click 'Go!'";
	if (in != null) {
	    if (in.equals("abc")) {
		resp = "Congratulations, you did it!";
	    } else if (!in.equals("")) {
		resp = "You typed " + in + ", try typing 'abc'.";
	    }
	}

	// Set the output.
	context.setOutputValue("response", resp);
    }
}
