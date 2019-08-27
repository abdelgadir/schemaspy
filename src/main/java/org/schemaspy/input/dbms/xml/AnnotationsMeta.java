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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Additional metadata about a table or a column annotations.
 *
 * @author AE Ibrahim
 */
public class AnnotationsMeta {

  private List<BeanValidationAnnotationMeta> beanValidations = new ArrayList<>();

  AnnotationsMeta(Node annotationsNode) {
    NodeList bvNodes = ((Element) annotationsNode.getChildNodes())
        .getElementsByTagName("beanValidation");

    for (int i = 0; i < bvNodes.getLength(); ++i) {
      Node bvNode = bvNodes.item(i);
      beanValidations.add(new BeanValidationAnnotationMeta(bvNode));
    }

    //filter out empty validation nodes
    beanValidations = getBeanValidations().stream()
        .filter(bv -> !StringUtils.isEmpty(bv.getContent())).collect(
            Collectors.toList());
  }

  public List<BeanValidationAnnotationMeta> getBeanValidations() {
    return beanValidations;
  }
}