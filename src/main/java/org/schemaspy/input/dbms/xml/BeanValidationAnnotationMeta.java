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


import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * Additional metadata about bean validations - applies to columns or tables.
 * @see {@link AnnotationsMeta}
 *
 * @author AE Ibrahim
 */
public class BeanValidationAnnotationMeta {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MethodHandles.lookup().lookupClass());
  private final String content;

  public BeanValidationAnnotationMeta(Node bvAnnotationNode) {
    content = bvAnnotationNode.getTextContent();
    LOGGER.debug("Found XML beanValidation metadata: content={} ", content);
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "beanValidation." + content;
  }
}