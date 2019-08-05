/*
 * Copyright (C) 2019 AE Ibrahim
 *
 * This file is a part of the SchemaSpy project (http://schemaspy.org).
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.schemaspy.input.dbms.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.lang.invoke.MethodHandles;

/**
 * Additional metadata (as expressed in XML instead of from the database) about a column regarding how
 * its values are generated if applicable.
 */
public class TableGeneratorMeta {
    //this is the unique generator name
    private final String name;
    private final String tableName;
    private final String pkColumnName;
    private final String valueColumnName;
    private final String pkColumnValue;
    private final int startValue;
    private final int increment;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TableGeneratorMeta(Node generatedValueNode) {
        NamedNodeMap attribs = generatedValueNode.getAttributes();

        Node node = attribs.getNamedItem("name");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'name' attribute");
        name = node.getNodeValue();

        node = attribs.getNamedItem("tableName");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'tableName' attribute");
        tableName = node.getNodeValue();

        node = attribs.getNamedItem("pkColumnName");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'pkColumnName' attribute");
        pkColumnName = node.getNodeValue();

        node = attribs.getNamedItem("valueColumnName");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'valueColumnName' attribute");
        valueColumnName = node.getNodeValue();

        node = attribs.getNamedItem("pkColumnValue");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'pkColumnValue' attribute");
        pkColumnValue = node.getNodeValue();

        node = attribs.getNamedItem("startValue");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'startValue' attribute");
        startValue = Integer.valueOf(node.getNodeValue());

        node = attribs.getNamedItem("increment");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'increment' attribute");
        increment = Integer.valueOf(node.getNodeValue());

        LOGGER.debug("Found XML tableGenerator metadata: name={}, tableName={}, pkColumnName={}, valueColumnNam={}, " +
                "pkColumnValue={} ", name, tableName, pkColumnName, valueColumnName, pkColumnValue);
    }

    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public String getPkColumnValue() {
        return pkColumnValue;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getIncrement() {
        return increment;
    }

    @Override
    public String toString() {
        return name + '.' + tableName + '.' + pkColumnName + '.' + valueColumnName + '.' + pkColumnValue;
    }
}