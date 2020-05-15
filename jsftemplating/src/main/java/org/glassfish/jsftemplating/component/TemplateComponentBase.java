/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jsftemplating.component;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;

import org.glassfish.jsftemplating.layout.LayoutDefinitionException;
import org.glassfish.jsftemplating.layout.LayoutDefinitionManager;
import org.glassfish.jsftemplating.layout.descriptors.LayoutComponent;
import org.glassfish.jsftemplating.layout.descriptors.LayoutDefinition;
import org.glassfish.jsftemplating.layout.descriptors.LayoutElement;


/**
 *  <p>	This abstract class provides base functionality for components that
 *	work in conjunction with the
 *	{@link org.glassfish.jsftemplating.renderer.TemplateRenderer} that do not
 *	need any "special" component features (such as provided by
 *	<code>UIInput</code>, or an <code>ActionSource</code> implementation).
 *	This class is a suitable class for "container" types of components, or
 *	components that simply agregate other components together.  It
 *	provides a default implementation of the
 *	{@link org.glassfish.jsftemplating.component.TemplateComponent}
 *	interface.</p>
 *
 *  @see    org.glassfish.jsftemplating.renderer.TemplateRenderer
 *  @see    org.glassfish.jsftemplating.component.TemplateComponent
 *
 *  @author Ken Paulsen	(ken.paulsen@sun.com)
 */
public abstract class TemplateComponentBase extends UIComponentBase implements TemplateComponent {

    /**
     *	<p> This method will find the request child <code>UIComponent</code>
     *	    by id.  If it is not found, it will attempt to create it if it can
     *	    find a {@link LayoutElement} describing it.</p>
     *
     *	@param	context	    The <code>FacesContext</code>.
     *	@param	id	    The <code>UIComponent</code> id to find.
     *
     *	@return	The requested <code>UIComponent</code>.
     */
    public UIComponent getChild(FacesContext context, String id) {
	return getHelper().getChild(this, context, id);
    }


    /**
     *	<p> This method will find the request child <code>UIComponent</code>
     *	    by id.  If it is not found, it will use the given
     *	    {@link LayoutComponent} to create it.</p>
     *
     *	@param	context	    The <code>FacesContext</code>.
     *	@param	descriptor  The <code>UIComponent</code> id to find.
     *
     *	@return	The requested <code>UIComponent</code>.
     */
    public UIComponent getChild(FacesContext context, LayoutComponent descriptor) {
	return getHelper().getChild(this, context, descriptor);
    }

    /**
     *	<p> This method returns the {@link LayoutDefinition} associated with
     *	    this component.</p>
     *
     *	@param	context	The <code>FacesContext</code>.
     *
     *	@return	{@link LayoutDefinition} associated with this component.
     */
    public LayoutDefinition getLayoutDefinition(FacesContext context) {
	return getHelper().getLayoutDefinition(context);
    }

    /**
     *	<p> This method saves the state for this component.  It relies on the
     *	    superclass to save its own sate, this method will invoke
     *	    super.saveState().</p>
     *
     *	@param	context	The <code>FacesContext</code>.
     *
     *	@return The serialized state.
     */
    public Object saveState(FacesContext context) {
	return getHelper().saveState(context, super.saveState(context));
    }

    /**
     *	<p> This method restores the state for this component.  It will invoke
     *	    the superclass to restore its state.</p>
     *
     *	@param	context	The <code>FacesContext</code>.
     *	@param	state	The serialized state.
     */
    public void restoreState(FacesContext context, Object state) {
	super.restoreState(context, getHelper().restoreState(context, state));
    }

    /**
     *	<p> This method returns the {@link LayoutDefinition} key for this
     *	    component.</p>
     *
     *	@return	The key to use in the {@link LayoutDefinitionManager}.
     */
    public String getLayoutDefinitionKey() {
	return getHelper().getLayoutDefinitionKey();
    }


    /**
     *	<p> This method sets the {@link LayoutDefinition} key for this
     *	    component.</p>
     *
     *	@param	key The key to use in the {@link LayoutDefinitionManager}.
     */
    public void setLayoutDefinitionKey(String key) {
	getHelper().setLayoutDefinitionKey(key);
    }

    public <V> V getPropertyValue(V field, String attributeName, V defaultValue) {
        return getHelper().getAttributeValue(this, field, attributeName, defaultValue);
    }

    /**
     *	<p> This method retrieves the {@link TemplateComponentHelper} used by
     *	    this class to help implement the {@link TemplateComponent}
     *	    interface.</p>
     *
     *	@return	The {@link TemplateComponentHelper} for this component.
     */
    protected TemplateComponentHelper getHelper() {
	if (_helper == null) {
	    _helper = new TemplateComponentHelper();
	}
	return _helper;
    }

    /**
     *	<p> Our <code>TemplateComponentHelper</code>.  We initialize it on
     *	    access b/c we want to ensure it exists, if it is serialized it
     *	    won't exist if we init it here or in the constructor.</p>
     */
    private transient TemplateComponentHelper _helper = null;
}
