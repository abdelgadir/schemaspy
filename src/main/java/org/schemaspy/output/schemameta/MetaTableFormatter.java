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

import static org.schemaspy.output.xml.dom.XmlConstants.TABLE;

import java.util.Collection;
import org.schemaspy.model.Table;
import org.schemaspy.output.xml.dom.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Formats {@link Table}s into an XML DOM tree comaptibel with schema meta.
 *
 * @author AE Ibrahim
 */
public class MetaTableFormatter {

  private final MetaColumnFormatter metaColumnFormatter = new MetaColumnFormatter();

  /**
   * Append the specified tables to the XML node
   *
   * @param schemaNode
   * @param tables
   */
  public void appendTables(Element schemaNode, Collection<Table> tables) {
    Document document = schemaNode.getOwnerDocument();
    Element tablesNode = document.createElement("tables");
    schemaNode.appendChild(tablesNode);
    tables.stream()
        .sorted((table1, table2) -> table1.getName().compareToIgnoreCase(table2.getName()))
        .distinct()
        .forEachOrdered(t -> appendTable(tablesNode, t));
  }

  /**
   * Append table details to the XML node
   *
   * @param tablesNode
   * @param table
   */
  private void appendTable(Element tablesNode, Table table) {
    Document document = tablesNode.getOwnerDocument();
    Element tableNode = document.createElement(TABLE);
    tablesNode.appendChild(tableNode);
    DOMUtil.appendAttribute(tableNode, "name", table.getName());
    metaColumnFormatter.appendColumns(tableNode, table);
  }
}