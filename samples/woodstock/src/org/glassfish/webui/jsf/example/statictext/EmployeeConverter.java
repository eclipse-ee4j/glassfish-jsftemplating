/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.webui.jsf.example.statictext;

import java.beans.*;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.application.FacesMessage;

import org.glassfish.webui.jsf.example.common.MessageUtil;

/**
 * Employee Converter Class
 */
public class EmployeeConverter implements Converter {
    
    /** Creates an instance of EmployeeConverter. */
    public EmployeeConverter() {    
    } 
    
    /** Converts an object into a string */
    public String getAsString(FacesContext context, 
                              UIComponent component, 
                              Object value) throws ConverterException {
        if (value instanceof Employee) {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(((Employee)value).getFirstName());
            strbuf.append(" "); 
            strbuf.append(((Employee)value).getLastName());
            strbuf.append("-"); 
            strbuf.append(((Employee)value).getDesignation());
            return strbuf.toString();
        }
        throw new ConverterException(MessageUtil.getMessage("statictext_errorMessage1") + value.toString());
    }            
    
    /** Converts a string into an object */
    public Object getAsObject(FacesContext context, 
                              UIComponent component, 
                              String value) throws ConverterException {
        try { 
            String[] names = value.split(" ");
            Employee emp = new Employee(names[0], names[1], names[2]); 
            return emp;
        } 
        catch (Exception ex) { 
            String message = MessageUtil.getMessage("statictext_errorMessage2");              
            throw new ConverterException(new FacesMessage(message));
        } 
        
    }
}
