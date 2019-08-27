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
package org.schemaspy.output.schemameta;

import static org.schemaspy.output.xml.dom.XmlConstants.COLUMN;

import java.sql.JDBCType;
import java.util.Arrays;
import java.util.List;
import org.schemaspy.model.Table;
import org.schemaspy.model.TableColumn;
import org.schemaspy.output.xml.dom.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Formats {@link TableColumn}s into an XML DOM tree compatible with schema meta.
 *
 * @author Abdelgadir Ibrahim
 */
public class MetaColumnFormatter {

  private static final String PLACE_HOLDER = "";
  private static List<JDBCType> TEXTUAL_TYPES = Arrays
      .asList(JDBCType.CHAR, JDBCType.VARCHAR, JDBCType.LONGVARCHAR, JDBCType.NCHAR,
          JDBCType.NVARCHAR, JDBCType.LONGNVARCHAR);

  /**
   * Append all columns in the table to the XML node
   *
   * @param tableNode
   * @param table
   */
  public void appendColumns(Element tableNode, Table table) {
    for (TableColumn column : table.getColumns()) {
      appendColumn(tableNode, column);
    }
  }

  /**
   * Append column details to the XML node
   *
   * @param tableNode
   * @param column
   * @return
   */
  public Node appendColumn(Node tableNode, TableColumn column) {
    Document document = tableNode.getOwnerDocument();
    Node columnNode = document.createElement(COLUMN);
    tableNode.appendChild(columnNode);

    DOMUtil.appendAttribute(columnNode, "name", column.getName());

    //a pk join column is most likely derived hence no need to have them as generatedValue.
    //auto updated keys are handled separately hence again not assigned a generatedValue.
    if (hasSimplePK(column.getTable()) && column.isPrimary() && !(
        column.isForeignKey() || column
            .isAutoUpdated())) {
      appendGeneratedValue(columnNode);
    }
    appendAnnotations(columnNode, column);
    return columnNode;
  }

  private static void appendGeneratedValue(Node columnNode) {
    Document document = columnNode.getOwnerDocument();
    Node generatedValueNode = document.createElement("generatedValue");
    columnNode.appendChild(generatedValueNode);
    DOMUtil.appendAttribute(generatedValueNode, "strategy", PLACE_HOLDER);
    DOMUtil.appendAttribute(generatedValueNode, "generator", PLACE_HOLDER);
  }

  private static void appendAnnotations(Node columnNode, TableColumn column) {
    Document document = columnNode.getOwnerDocument();
    Node annotations = document.createElement("annotations");
    columnNode.appendChild(annotations);

    Element beanValidation = document.createElement("beanValidation");
    beanValidation.setTextContent(column.isNullable() ? "@NotNull" : PLACE_HOLDER);
    annotations.appendChild(beanValidation);

    if (isTextualType(column)) {
      Element beanValidation2 = document.createElement("beanValidation");
      beanValidation2.setTextContent("@Size(max=" + column.getLength() + ")");
      annotations.appendChild(beanValidation2);
    }
  }

  private boolean hasSimplePK(Table table) {
    return table.getPrimaryColumns().size() == 1;
  }

  private static boolean isTextualType(TableColumn column) {
    return TEXTUAL_TYPES.contains(JDBCType.valueOf(column.getType()));
  }
}
