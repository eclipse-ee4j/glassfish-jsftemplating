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

package com.sun.jsftemplating.layout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jsftemplating.el.PageSessionResolver;
import com.sun.jsftemplating.layout.descriptors.LayoutComponent;
import com.sun.jsftemplating.layout.descriptors.LayoutComposition;
import com.sun.jsftemplating.layout.descriptors.LayoutDefinition;
import com.sun.jsftemplating.layout.descriptors.LayoutElement;
import com.sun.jsftemplating.layout.descriptors.LayoutFacet;
import com.sun.jsftemplating.layout.descriptors.LayoutInsert;
import com.sun.jsftemplating.layout.descriptors.Resource;
import com.sun.jsftemplating.util.LayoutElementUtil;
import com.sun.jsftemplating.util.LogUtil;
import com.sun.jsftemplating.util.SimplePatternMatcher;
import com.sun.jsftemplating.util.TypeConversion;
import com.sun.jsftemplating.util.TypeConverter;
import com.sun.jsftemplating.util.UIComponentTypeConversion;
import com.sun.jsftemplating.util.fileStreamer.Context;
import com.sun.jsftemplating.util.fileStreamer.FacesStreamerContext;
import com.sun.jsftemplating.util.fileStreamer.FileStreamer;


// FIXME: Things to consider:
// FIXME:   - What is necessary to support Portlets...
// FIXME:   - Should I attempt to clean up old unused UIComponents?
// FIXME:   - f:view supported setting locale, I should too...

/**
 *  <p>	This class provides a custom <code>ViewHandler</code> that is able to
 *	create and populate a <code>UIViewRoot</code> from a
 *	{@link LayoutDefinition}.  This is often defined by an XML document,
 *	the default implementation's DTD is defined in
 *	<code>layout.dtd</code>.</p>
 *
 *  <p>	Besides the default <code>ViewHandler</code> behavior, this class is
 *	responsible for using the given <code>viewId</code> as the
 *	{@link LayoutDefinition} key and setting it on the UIViewRoot that is
 *	created.  It will obtain the {@link LayoutDefinition}, initialize the
 *	declared {@link Resource}s, and instantiate <code>UIComponent</code>
 *	tree using the {@link LayoutDefinition}'s declared
 *	{@link LayoutComponent} structure.  During rendering, it delegates to
 *	the {@link LayoutDefinition}.</p>
 *
 *  @author Ken Paulsen (ken.paulsen@sun.com)
 */
public class LayoutViewHandler extends ViewHandler {

    /**
     *	<p> Constructor.</p>
     *
     *	@param	oldViewHandler	The old <code>ViewHandler</code>.
     */
    public LayoutViewHandler(ViewHandler oldViewHandler) {
	_oldViewHandler = oldViewHandler;

// FIXME: Fire an initializtion event, work out how to listen for this event

	// This is added here to ensure that if the ViewHandler is reloaded in
	// a running application, that handlers, ct's, and resources will get
	// re-read.  Ryan added a feature which may introduce this code path.
	LayoutDefinitionManager.clearGlobalComponentTypes(null);
	LayoutDefinitionManager.clearGlobalHandlerDefinitions(null);
	LayoutDefinitionManager.clearGlobalResources(null);
    }

    /**
     *	<p> Initialize the view for the request processing lifecycle.  It is
     *	    called at the beginning of the Restore View phase.</p>
    public void initView(FacesContext context) throws FacesException {
	// Not used yet... I left this here as a reminder that it is here
    }
     */

    /**
     *	<p> This method is invoked when restoreView does not yield a
     *	    <code>UIViewRoot</code> (initial requests and new pages).</p>
     *
     *	<p> This implementation should work with both
     *	    {@link LayoutDefinition}-based pages as well as traditional
     *	    JSP pages (or other frameworks).</p>
     */
    public UIViewRoot createView(FacesContext context, String viewId) {
//_time = new java.util.Date();
	// Check to see if this is a resource request
	String path = getResourcePath(viewId);
	if (path != null) {
	    // Serve Resource
	    return serveResource(context, path);
	}

	// Check to see if jsftemplating should create the view
	if(!this.isMappedView(viewId) || (viewId == null)) {
	    UIViewRoot viewRoot = _oldViewHandler.createView(context, viewId);
	    return viewRoot;
	}

	Locale locale = null;
	String renderKitId = null;

	// use the locale from the previous view if is was one which will be
	// the case if this is called from NavigationHandler. There wouldn't be
	// one for the initial case.
	if (context.getViewRoot() != null) {
	    UIViewRoot oldViewRoot = context.getViewRoot();
	    LayoutDefinition oldLD = ViewRootUtil.getLayoutDefinition(oldViewRoot);
	    if ((oldLD != null) && oldViewRoot.getViewId().equals(viewId)) {
		// If you navigate to the page you are already on, JSF will
		// re-create the UIViewRoot of the current page.  The initPage
		// event needs to be reset so that it will re-execute itself.
		oldLD.setInitPageExecuted(context, Boolean.FALSE);
	    }
	    locale = context.getViewRoot().getLocale();
	    renderKitId = context.getViewRoot().getRenderKitId();
	}

	// Create the ViewRoot
	UIViewRoot viewRoot = _oldViewHandler.createView(context, viewId);
	viewRoot.setViewId(viewId);
	ViewRootUtil.setLayoutDefinitionKey(viewRoot, viewId);

	// if there was no locale from the previous view, calculate the locale
	// for this view.
	if (locale == null) {
	    locale = calculateLocale(context);
	}
	viewRoot.setLocale(locale);

	// set the renderkit
	if (renderKitId == null) {
	    renderKitId = calculateRenderKitId(context);
	}
	viewRoot.setRenderKitId(renderKitId);

	// Save the current viewRoot, temporarily set the new UIViewRoot so
	// beforeCreate, afterCreate will function correctly
	UIViewRoot currentViewRoot = context.getViewRoot(); 

	// Set the View Root to the new viewRoot
	// NOTE: This must happen after return _oldViewHandler.createView(...)
	// NOTE2: However, we really want the UIViewRoot available during
	//	  initPage events which are fired during
	//	  getLayoutDefinition()... so we need to set this, then unset
	//	  it if we go through _oldViewHandler.createView(...)
	context.setViewRoot(viewRoot);

	// Initialize Resources / Create Tree
	LayoutDefinition def = null;
	try {
	    def = ViewRootUtil.getLayoutDefinition(viewRoot);
	} catch (LayoutDefinitionException ex) {
	    if (LogUtil.configEnabled()) {
		LogUtil.config("JSFT0005", (Object) viewId);
		if (LogUtil.finestEnabled()) {
		    LogUtil.finest(
			"File (" + viewId + ") not found!", ex);
		}
	    }

	    // Restore original ViewRoot, we set it prematurely
	    if (currentViewRoot != null) {
// FIXME: Talk to Ryan about restoring the ViewRoot to null!!
		context.setViewRoot(currentViewRoot);
	    }

// FIXME: Provide better feedback when no .jsf & no .jsp
// FIXME: Difficult to tell at this stage if no .jsp is present

	    // Not found, delegate to old ViewHandler
	    return _oldViewHandler.createView(context, viewId);
	} catch (RuntimeException ex) {
	    // Restore original ViewRoot, we set it prematurely
	    if (currentViewRoot != null) {
// FIXME: Talk to Ryan about restoring the ViewRoot to null!!
		context.setViewRoot(currentViewRoot);
	    }

	    // Allow error to be thrown (this isn't the normal code path)
	    throw ex;
	}

	// We need to do this again b/c an initPage handler may have changed
	// the viewRoot
	viewRoot = context.getViewRoot();

	// Check to make sure we found a LD and that the response isn't
	// already finished (initPage could complete the response...
	// i.e. during a redirect).
	if ((def != null) && !context.getResponseComplete()) {
	    // Ensure that our Resources are available
	    Iterator<Resource> it = def.getResources().iterator();
	    Resource resource = null;
	    while (it.hasNext()) {
		resource = it.next();
		// Just calling getResource() puts it in the Request scope
		resource.getFactory().getResource(context, resource);
	    }

	    // Get the Tree and pre-walk it
	    if (LayoutDefinitionManager.isDebug(context)) {
		// Make sure to reset all the client ids we're about to check
		getClientIdMap(context).clear();
	    }
	    buildUIComponentTree(context, viewRoot, def);
	}

	// Restore the current UIViewRoot
	if (currentViewRoot != null) {
	    context.setViewRoot(currentViewRoot);
	}

	// Return the populated UIViewRoot
	return viewRoot;
    }

    /**
     *	<p> Tests if the provided <code>viewId</code> matches one of the
     *	    configured view-mappings.  If no view-mappings are defined, all
     *	    <code>viewId</code>s will match.</p>
     *
     *	@param	viewId	The viewId to be tested.
     *
     *	@return	true	If the viewId matched or no view-mappings are defined, 
     *			false otherwise.
     *	@since	1.2
     */
    private boolean isMappedView(String viewId) {
	if (this._viewMappings == null) {
	    String initParam = (String) FacesContext.getCurrentInstance().
		getExternalContext().getInitParameterMap().get(VIEW_MAPPINGS);
	    this._viewMappings = SimplePatternMatcher.
	    parseMultiPatternString(initParam, ";");
	}
	if (this._viewMappings.isEmpty()) {
	    return true;
	}
	for (SimplePatternMatcher mapping : this._viewMappings) {
	    if (mapping.matches(viewId)) {
		return true;
	    }
	}
	return false;
    }

    /**
     *	<p> If this is a resource request, this method will handle the
     *	    request.</p>
     */
    public static UIViewRoot serveResource(FacesContext context, String path) {
	// Mark the response complete so no more processing occurs
	context.responseComplete();

	// Create dummy UIViewRoot
	UIViewRoot root = new UIViewRoot();
	root.setRenderKitId("dummy");

	// Setup the FacesStreamerContext
	Context fsContext = new FacesStreamerContext(context);
	fsContext.setAttribute(Context.FILE_PATH, path);

	// Get the HttpServletResponse
	Object obj = context.getExternalContext().getResponse();
	HttpServletResponse resp = null;
	if (obj instanceof HttpServletResponse) {
	    resp = (HttpServletResponse) obj;

	    // We have an HttpServlet response, do some extra stuff...
	    // Check the last modified time to see if we need to serve the resource
	    long mod = fsContext.getContentSource().getLastModified(fsContext);
	    if (mod != -1) {
		long ifModifiedSince = ((HttpServletRequest)
			context.getExternalContext().getRequest()).
			getDateHeader("If-Modified-Since");
		// Round down to the nearest second for a proper compare
		if (ifModifiedSince < (mod / 1000 * 1000)) {
		    // A ifModifiedSince of -1 will always be less
		    resp.setDateHeader("Last-Modified", mod);
		} else {
		    // Set not modified header and complete response
		    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		    return root;
		}
	    }
	}

	// Stream the content
	try {
	    FileStreamer.getFileStreamer(context).streamContent(fsContext);
	} catch (FileNotFoundException ex) {
	    if (LogUtil.infoEnabled()) {
		LogUtil.info("JSFT0004", (Object) path);
	    }
	    if (resp != null) {
		try {
		    ((HttpServletResponse) resp).sendError(
			   HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException ioEx) {
		    // Ignore
		}
	    }
	} catch (IOException ex) {
	    if (LogUtil.infoEnabled()) {
		LogUtil.info("JSFT0004", (Object) path);
		if (LogUtil.fineEnabled()) {
		    LogUtil.fine(
			"Resource (" + path + ") not available!", ex);
		}
	    }
// FIXME: send 404?
	}

	// Return dummy UIViewRoot to avoid NPE
	return root;
    }

    /**
     *	<p> Returns the current encoding type.</p>
     *
     *	@param	ctx The <code>FacesContext</code>.
     */
    public static String getEncoding(FacesContext ctx) {
	// Sanity check
	if (ctx == null) {
	    return null;
	}

	String encType = null;
	UIViewRoot root = ctx.getViewRoot();
	Map<String, Serializable> map = PageSessionResolver.getPageSession(ctx, root);
	if (map != null) {
	    //check for page session
	    encType = (String) map.get(ENCODING_TYPE);
	}
	if ((encType == null) || encType.equals("")) {
	    //check for application level
	    encType = ctx.getExternalContext().getInitParameter(ENCODING_TYPE);
	}
	if ((encType == null) || encType.equals("")) {
	    ExternalContext extCtx = ctx.getExternalContext();
	    try {
		ServletRequest request = (ServletRequest) extCtx.getRequest();
		encType = request.getCharacterEncoding();
	    } catch (Exception ex) {
		// FIXME: Portlet?
	    }
	    if ((encType == null) || encType.equals("")) {
		//default encoding type
		encType="UTF-8";
	    }	
	}
	return encType;
    }

    /**
     *	<p> This method checks the given viewId and returns a the path to the
     *	    requested resource if it refers to a resource.  Resources are
     *	    things like JavaScript files, images, etc.  Basically anything that
     *	    is not a JSF page that you'd like to serve up via the FacesServlet.
     *	    Serving resources this way allows you to bundle the resources in a
     *	    jar file, this is useful if you want to package up part of an app
     *	    (or a JSF component) in a single file.</p>
     *
     *	<p> A request for a resource must be prefixed by the resource prefix,
     *	    see @{link #getResourcePrefixes}.  This prefix must also be mapped to
     *	    the <code>FacesServlet</code> in order for this class to handle the
     *	    request.</p>
     */
    public String getResourcePath(String viewId) {
	ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
// FIXME: Portlet!
	String servletPath = extCtx.getRequestServletPath();
	Iterator<String> it = getResourcePrefixes().iterator();
	while (it.hasNext()) {
	    if (servletPath.equals(it.next())) {
		return extCtx.getRequestPathInfo();
	    }
	}
	return null;
    }

    /**
     *	<p> This method returns the prefix that a URL must contain in order to
     *	    retrieve a "resource" through this <code>ViewHandler</code>.</p>
     *
     *	<p> The prefix itself does not manifest itself in the file system /
     *	    classpath.</p>
     *
     *	<p> If the prefix is not set, then an init parameter (see
     *	    {@link #RESOURCE_PREFIX}) will be checked.  If that is still not
     *	    specified, then the {@link #DEFAULT_RESOURCE_PREFIX} will be
     *	    used.</p>
     */
    public Set<String> getResourcePrefixes() {
	if (_resourcePrefix == null) {
	    HashSet<String> set = new HashSet<String>();
	    // Check to see if it's specified by a context param
	    // Get context parameter map (initParams in JSF are context params)
	    String initParam = (String) FacesContext.getCurrentInstance().
		getExternalContext().getInitParameterMap().get(RESOURCE_PREFIX);
	    if (initParam != null) {
		for(String token: initParam.split(",")) {
		    set.add(token.trim());
		}
	    }
	    // Add default...
	    set.add(DEFAULT_RESOURCE_PREFIX);
	    _resourcePrefix = set;
	}
	return _resourcePrefix;
    }

    /**
     *	<p> This method allows a user to set the resource prefix which will be
     *	    checked to obtain a resource via this <code>Viewhandler</code>.
     *	    Currently, only 1 prefix is supported.  The prefix itself does not
     *	    manifest itself in the file system / classpath.</p>
     */
    public void setResourcePrefixes(Set<String> prefix) {
	_resourcePrefix = prefix;
    }

    /**
     *	<p> This method iterates over the child {@link LayoutElement}s of the
     *	    given <code>elt</code> to create <code>UIComponent</code>s for each
     *	    {@link LayoutComponent}.</p>
     *
     *	@param	context	The <code>FacesContext</code>.
     *	@param	parent	The parent <code>UIComponent</code> of the
     *			<code>UIComponent</code> to be found or created.
     *	@param	elt	The <code>LayoutElement</code> driving everything.
     */
    public static void buildUIComponentTree(FacesContext context, UIComponent parent, LayoutElement elt) {
// FIXME: Consider processing *ALL* LayoutElements so that <if> and others
// FIXME: have meaning when inside other components.
	Iterator<LayoutElement> it = elt.getChildLayoutElements().iterator();
	LayoutElement childElt;
	UIComponent child = null;
	while (it.hasNext()) {
	    childElt = it.next();
	    if (childElt instanceof LayoutFacet) {
		if (!((LayoutFacet) childElt).isRendered()) {
		    // The contents of this should be a single UIComponent
		    buildUIComponentTree(context, parent, childElt);
		}
		// NOTE: LayoutFacets that aren't JSF facets aren't
		// NOTE: meaningful in this context
	    } else if (childElt instanceof LayoutComposition) {
		LayoutComposition compo = ((LayoutComposition) childElt);
		String template = compo.getTemplate();
		if (template != null) {
		    // Add LayoutComposition to the stack
		    LayoutComposition.push(context, childElt);

		    try {
			// Add the template here.
			buildUIComponentTree(context, parent, LayoutDefinitionManager.getLayoutDefinition(
				context, template));
		    } catch (LayoutDefinitionException ex) {
			if (((LayoutComposition) childElt).isRequired()) {
			    throw ex;
			}
		    }

		    // Remove the LayoutComposition from the stack
		    LayoutComposition.pop(context);
		} else {
		    // In this case we don't have a template, so instead we
		    // render the body
		    buildUIComponentTree(context, parent, childElt);
		}
	    } else if (childElt instanceof LayoutInsert) {
		Stack<LayoutElement> stack =
		    LayoutComposition.getCompositionStack(context);
		if (stack.empty()) {
		    // No template-client found...
		    // Is this supposed to do nothing?  Or throw an exception?
		    throw new IllegalArgumentException(
			    "'ui:insert' encountered, however, no "
			    + "'ui:composition' was used!");
		}

		// Get associated UIComposition
		String insertName = ((LayoutInsert) childElt).getName();
		if (insertName == null) {
		    // include everything
		    buildUIComponentTree(context, parent, stack.get(0));
		} else {
		    // First resolve any EL in the insertName
		    insertName = "" + ((LayoutInsert) childElt).resolveValue(
			    context, parent, insertName);

		    // Search for specific LayoutDefine
		    LayoutElement def = LayoutInsert.findLayoutDefine(
			    context, parent, stack, insertName);
		    if (def == null) {
			// Not found include the body-content of the insert
			buildUIComponentTree(context, parent, childElt);
		    } else {
			// Found, include the ui:define content
			buildUIComponentTree(context, parent, def);
		    }
		}
	    } else if (childElt instanceof LayoutComponent) {
		// Calling getChild will add the child UIComponent to tree
		child = ((LayoutComponent) childElt).
			getChild(context, parent);

		if (LayoutDefinitionManager.isDebug(context)) {
		    // To help developer avoid duplicate ids, we'll check the
		    // ids here.
		    Map idMap = getClientIdMap(context);
		    String id = child.getClientId(context);
		    if (idMap.containsKey(id)) {
			if (!((LayoutComponent) childElt).containsOption(LayoutComponent.SKIP_ID_CHECK) && LogUtil.warningEnabled()) {
			    LogUtil.warning("JSFT0011", (Object) id);
			}

			// Clear the map as a way to prevent this message from
			// being shown tons of times as may occur in some
			// valid use cases.  Remember this is just a debug-time
			// only helpful message anyway...
			idMap.clear();
		    }
		    idMap.put(id, id);
		}

		// Check for events
		// NOTE: For now I am only supporting "action" and
		// NOTE: "actionListener" event types.  In the future it
		// NOTE: may be desirable to support beforeEncode /
		// NOTE: afterEncode as well.  At this time, those events
		// NOTE: are supported by the "Event" UIComponent.  That
		// NOTE: component can wrap non-layout-based components to
		// NOTE: achieve this functionality (supporting that
		// NOTE: functionality here will simply do the same thing
		// NOTE: automatically).

		// Recurse
		buildUIComponentTree(context, child, childElt);
	    } else {
		buildUIComponentTree(context, parent, childElt);
	    }
	}
    }

    /**
     *	<p> This method provides access to a <code>Map</code> of clientIds
     *	    that have been used in this page.</p>
     */
    private static Map<String, String> getClientIdMap(FacesContext context) {
	Map<String, Object> reqMap =
		context.getExternalContext().getRequestMap();
	Map<String, String> idMap = (Map<String, String>)
		reqMap.get("__debugIdMap");
	if (idMap == null) {
	    idMap = new HashMap<String, String>();
	    reqMap.put("__debugIdMap", idMap);
	}
	return idMap;
    }

    /**
     *	<p> This implementation relies on the default behavior to reconstruct
     *	    the UIViewRoot.</p>
     *
     *	<p> ...</p>
     */
    public UIViewRoot restoreView(FacesContext context, String viewId) {
//_time = new java.util.Date();
	Map<String, Object> map = context.getExternalContext().getRequestMap();
	if (map.get(RESTORE_VIEW_ID) == null) {
	    map.put(RESTORE_VIEW_ID, viewId);
	} else {
	    // This request has already been processed, it must be a forward()
	    return createView(context, viewId);
	}

	// Perform default behavior...
	UIViewRoot root = _oldViewHandler.restoreView(context, viewId);

	// We can check for JSFT UIViewRoots by calling
	// getLayoutDefinitionKey(root) as this will return null if not JSFT
	if (root != null) {
	    String key = ViewRootUtil.getLayoutDefinitionKey(root);
	    if (key != null) {
		// Set the View Root to the new viewRoot (needed for initPage)
		// NOTE: See createView note about saving / restoring the
		// NOTE: original UIViewRoot and issue with setting it to
		// NOTE: (null). restoreView is less important b/c it is not
		// NOTE: normally called by a developer or framework as
		// NOTE: navigation rules will call createView.  For this
		// NOTE: reason, I am not resetting the UIViewRoot for now.
		context.setViewRoot(root);

		// Call getLayoutDefinition() to ensure initPage events are
		// fired, only do this for JSFT ViewRoots.  Its good to call
		// this after restoreView as we need the UIViewRoot available
		// during initPage events.  Formerly this was done during the
		// ApplyRequestValuesPhase, however, I no longer have a custom
		// UIViewRoot to use for this purpose, so I will do it here,
		// which should be just as good.
		LayoutDefinition def = ViewRootUtil.getLayoutDefinition(key);

		// While we're at it, we should call the LD decode() event so
		// we can provide a page-level decode() functionality.  This
		// won't effect components in the page, or JSFT-based
		// components.
		def.decode(context, root);
	    }
	}

	// Return the UIViewRoot
	return root;
    }

    /**
     *
     */
    public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException {
	// Make sure we have a def
	LayoutDefinition def = ViewRootUtil.getLayoutDefinition(viewToRender);
	if (def == null) {
	    // PartialRequest or No def, fall back to default behavior
	    _oldViewHandler.renderView(context, viewToRender);
	} else {
	    // Start document
	    if (!context.getPartialViewContext().isPartialRequest() || context.getPartialViewContext().isRenderAll()) {
		ResponseWriter writer = setupResponseWriter(context);
		writer.startDocument();

		// Render content
		def.encode(context, viewToRender);

		// End document
		writer.endDocument();
	    } else {
		// NOTE: This "if" branch has been added to avoid the
		// NOTE: start/endDocument calls being called 2x on PartialView
		// NOTE: requests.  JSF Issue #1307 has been filed to resolve
		// NOTE: this correctly (assuming checking here is not
		// NOTE: correct... which I do not feel that it is).
		//
		// Render content
		def.encode(context, viewToRender);
	    }
	}
//System.out.println("PROCESSING TIME: " + (new java.util.Date().getTime() - _time.getTime()));
    }

    private static void renderComponent(FacesContext context, UIComponent comp) throws IOException {
	if (!comp.isRendered()) {
	    return;
	}

	comp.encodeBegin(context);
	if (comp.getRendersChildren()) {
	    comp.encodeChildren(context);
	} else {
	    UIComponent child = null;
	    Iterator<UIComponent> it = comp.getChildren().iterator();
	    while (it.hasNext()) {
		child = it.next();
		renderComponent(context, child);
	    }
	}
	comp.encodeEnd(context);
    }

    /**
     *
     */
    private ResponseWriter setupResponseWriter(FacesContext context) throws IOException {
	ResponseWriter writer = context.getResponseWriter();
	if (writer != null) {
	    // It is already setup
	    return writer;
	}

	ExternalContext extCtx = context.getExternalContext();
// FIXME: Portlet?
	ServletResponse response = (ServletResponse) extCtx.getResponse();

	RenderKitFactory renderFactory = (RenderKitFactory)
	    FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
	RenderKit renderKit =
	    renderFactory.getRenderKit(context,
		 context.getViewRoot().getRenderKitId());

	// See if the user (page author) specified a ContentType...
	String contentTypeList = null;
// FIXME: Provide a way for the user to specify this...
// FIXME: Test multiple browsers against this code!!
	String userContentType = "text/html";
	if((userContentType != null) && (userContentType.length() > 0)) {
		// User picked this, use it...
		response.setContentType(userContentType);
	}
	else {
		// No explicit Content-type, find best match...
		contentTypeList = (String) extCtx.getRequestHeaderMap().get("Accept");
		if (contentTypeList == null) {
			contentTypeList = "text/html;q=1.0";
		}
	}
	String encType = getEncoding(context);
	// Object encValue = extCtx.getSessionMap().get(
	//				ViewHandler.CHARACTER_ENCODING_KEY);
	
	extCtx.getSessionMap().put(ViewHandler.CHARACTER_ENCODING_KEY,
						encType);
// FIXME: use the external context to set the character encoding, it is supported
	response.setCharacterEncoding(encType);

// FIXME: Portlet?
	writer =
	    renderKit.createResponseWriter(
		new OutputStreamWriter(response.getOutputStream(), encType),
		contentTypeList, encType);
	context.setResponseWriter(writer);
// Not setting the contentType here results in XHTML which formats differently
// than text/html in Mozilla.. even though the documentation claims this
// works, it doesn't (try viewing the Tree)
//        response.setContentType("text/html");

	// As far as I can tell JSF doesn't ever set the Content-type that it
	// works so hard to calculate...  This is the code we should be
	// calling, however we can't do this yet
	response.setContentType(writer.getContentType());

	return writer;
    }

    /**
     *	<p> Take any appropriate action to either immediately write out the
     *	    current state information (by calling
     *	    <code>StateManager.writeState</code>, or noting where state
     *	    information should later be written.</p>
     *
     *	@param context <code>FacesContext</code> for the current request
     *
     *	@exception IOException if an input/output error occurs
     */
    public void writeState(FacesContext context) throws IOException {
	// Check to see if we should delegate back to the legacy ViewHandler
	UIViewRoot root = context.getViewRoot();
// FIXME: For now I am treating "@all" Ajax requests as normal requests...
// FIXME: Otherwise the view state is not written.
	if ((root == null) || (context.getPartialViewContext().isPartialRequest() && !context.getPartialViewContext().isRenderAll())
		|| (ViewRootUtil.getLayoutDefinition(root) == null)) {
	    // Use old behavior...
	    _oldViewHandler.writeState(context);
	} else {
	    // b/c we pre-processed the ViewTree, we can just add it...
	    StateManager stateManager =
		context.getApplication().getStateManager();
	    SerializedView view = stateManager.saveSerializedView(context);

	    // New versions of JSF 1.2 changed the contract so that state is
	    // always written (client and server state saving)
	    stateManager.writeState(context, view);
	}
    }

    /**
     *	<p> Return a URL suitable for rendering (after optional encoding
     *	    performed by the <code>encodeResourceURL()</code> method of
     *	    <code>ExternalContext<code> that selects the specified web
     *	    application resource.  If the specified path starts with a slash,
     *	    it must be treated as context relative; otherwise, it must be
     *	    treated as relative to the action URL of the current view.</p>
     *
     *	@param context	<code>FacesContext</code> for the current request
     *	@param path	Resource path to convert to a URL
     *
     *	@exception  IllegalArgumentException	If <code>viewId</code> is not
     *	    valid for this <code>ViewHandler</code>.
     */
    public String getResourceURL(FacesContext context, String path) {
	return _oldViewHandler.getResourceURL(context, path);
    }

    /**
     *	<p> Return a URL suitable for rendering (after optional encoding
     *	    performed by the <code>encodeActionURL()</code> method of
     *	    <code>ExternalContext</code> that selects the specified view
     *	    identifier.</p>
     *
     *	@param	context	<code>FacesContext</code> for this request
     *	@param	viewId	View identifier of the desired view
     *
     *	@exception  IllegalArgumentException	If <code>viewId</code> is not
     *		valid for this <code>ViewHandler</code>.
     */
    public String getActionURL(FacesContext context, String viewId) {
	return _oldViewHandler.getActionURL(context, viewId);
    }

    /**
     *	<p> Returns an appropriate <code>Locale</code> to use for this and
     * subsequent requests for the current client.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     *
     * @exception NullPointerException if <code>context</code> is
     *  <code>null</code>
     */
     public Locale calculateLocale(FacesContext context) {
	 return _oldViewHandler.calculateLocale(context);
     }

    /**
     * <p>Return an appropriate <code>renderKitId</code> for this
     * and subsequent requests from the current client.</p>
     *
     * <p>The default return value is
     * <code>javax.faces.render.RenderKitFactory.HTML_BASIC_RENDER_KIT</code>.
     * </p>
     *
     * @param	context	<code>FacesContext</code> for the current request.
     */
    public String calculateRenderKitId(FacesContext context) {
	return _oldViewHandler.calculateRenderKitId(context);
    }

    /**
     *	<p> This is the key that may be used to identify the clientId of the
     *	    UIComponent that is to be updated via an Ajax request.</p>
     */
    public static final String AJAX_REQ_KEY		= "ajaxReq";

    public static final String RESTORE_VIEW_ID		= "_resViewID";

    /**
     *	<p> This is the default prefix that must be included on all requests
     *	    for resources.</p>
     */
    public static final String DEFAULT_RESOURCE_PREFIX	= "/resource";

    /**
     *	<p> The name of the <code>context-param</code> to set the resource
     *	    prefix.</p>
     */
    public static final String RESOURCE_PREFIX		=
	"com.sun.jsftemplating.RESOURCE_PREFIX";

    private Set<String> _resourcePrefix = null;
//private transient java.util.Date _time = null;

    private ViewHandler _oldViewHandler			= null;
    static final String AJAX_REQ_TARGET_KEY		= "_ajaxReqTarget";

    /**
     * <p> The name of the <code>context-param</code> to set the view mappings</p>
     */
    // TODO: should these keys be added to a new Class f.e. com.sun.jsftemplating.Keys?
    private static final String VIEW_MAPPINGS		= 
	"com.sun.jsftemplating.VIEW_MAPPINGS";

    /**
     *	
     */
    private static final TypeConversion UICOMPONENT_TYPE_CONVERSION =
	new UIComponentTypeConversion();

    /*
     *	<p> This is intended to initialize additional type conversions
     *	    for the {@link TypeConverter}.  These additional type conversions
     *	    are typically specific to JSF or JSFTemplating.  If you are
     *	    reading this and want additional custom type conversions, bug
     *	    Ken Paulsen to add an init event which will allow you to easily
     *	    initialize your own type conversions.</p>
     */
    static {
	// Add type conversions by class
	TypeConverter.registerTypeConversion(null, UIComponent.class, UICOMPONENT_TYPE_CONVERSION);
	// Add type conversions by class name
	TypeConverter.registerTypeConversion(null, UIComponent.class.getName(), UICOMPONENT_TYPE_CONVERSION);
    }


    private Collection<SimplePatternMatcher> _viewMappings = null;

    /**
     *	<p> This key can be used to override the encoding type used in your
     *	    application.</p>
     */
    public static final String ENCODING_TYPE="com.sun.jsftemplating.ENCODING";
}
