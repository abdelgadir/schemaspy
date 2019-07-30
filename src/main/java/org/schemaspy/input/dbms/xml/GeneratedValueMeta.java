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
public class GeneratedValueMeta {
    public enum GeneratedValueStrategy {TABLE, SEQUENCE}

    private final GeneratedValueStrategy strategy;
    private final String generator;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public GeneratedValueMeta(Node generatedValueNode) {
        NamedNodeMap attribs = generatedValueNode.getAttributes();
        Node node = attribs.getNamedItem("strategy");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'strategy' attribute");
        strategy = GeneratedValueStrategy.valueOf(node.getNodeValue());
        node = attribs.getNamedItem("generator");
        if (node == null)
            throw new IllegalStateException("XML generatedValue definition requires 'generator' attribute");
        generator = node.getNodeValue();

        LOGGER.debug("Found XML generatedValue metadata: strategy={}, generator={} ", strategy, generator);
    }

    public GeneratedValueStrategy getStrategy() {
        return strategy;
    }

    public String getGenerator() {
        return generator;
    }

    @Override
    public String toString() {
        return strategy.name() + '.' + generator;
    }
}