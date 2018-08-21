/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.webui.jsf.example.statictext;

import java.beans.*;
import java.util.Date;
import java.io.Serializable;

import com.sun.webui.jsf.example.common.MessageUtil;
import javax.faces.convert.Converter;

/**
 * Backing bean for Static Text example.
 */

public class StatictextBackingBean implements Serializable {
    
    // Holds the Date.
    Date date = null;
    
    // Holds the Employee object.
    Employee emp = null;

    // Converter for the Employee object.
    EmployeeConverter empConverter = new EmployeeConverter(); 
                       
    /** Creates a new instance of StatictextBackingBean. */
    public StatictextBackingBean() {
        date = new Date();
        emp = new Employee(MessageUtil.getMessage("statictext_firstname"), 
                MessageUtil.getMessage("statictext_lastname"), 
                MessageUtil.getMessage("statictext_designation"));
    }
   
    /** Returns the date. */
    public Date getDate() {
        return date;
    }
    
    /** Sets the date. */
    public void setDate(Date date) {
        this.date = date;
    }
           
    /** Returns employee object. */
    public Employee getEmp() {
        return emp;
    }
    
    /** Sets employee object. */
    public void setEmp(Employee emp) {
        this.emp = emp;
    } 

    /**
     * Get the converter.
     *
     * @return converter for the Employee object.
     */	
    public Converter getEmpConverter() {
        return empConverter;
    }
}
