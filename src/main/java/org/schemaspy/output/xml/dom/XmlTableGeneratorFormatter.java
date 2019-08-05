/*
 * Copyright (C) 2019 AE Ibrahim
 *
 * This file is part of SchemaSpy.
 *
 * SchemaSpy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SchemaSpy. If not, see <http://www.gnu.org/licenses/>.
 */
package org.schemaspy.output.xml.dom;

import org.schemaspy.input.dbms.xml.TableGeneratorMeta;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class XmlTableGeneratorFormatter {

    public void appendTableGenerators(Element schemaNode, Collection<TableGeneratorMeta> tableGenMetas) {
        LinkedList<TableGeneratorMeta> generatorsMetaList = new LinkedList<>(tableGenMetas);
        //possibly for consistency in the output otherwise every run will produce different output in terms of order.
        Collections.sort(generatorsMetaList, Comparator.comparing(TableGeneratorMeta::getName));
        if (!generatorsMetaList.isEmpty()) {
            Element generatorsElement = schemaNode.getOwnerDocument().createElement("tableGenerators");
            schemaNode.appendChild(generatorsElement);
            for (TableGeneratorMeta tgm : generatorsMetaList) {
                appendTableGenerator(generatorsElement, tgm);
            }
        }
    }

    private static void appendTableGenerator(Element generatorsElement, TableGeneratorMeta tableGenMeta) {
        Element generatorElement = generatorsElement.getOwnerDocument().createElement("tableGenerator");
        generatorsElement.appendChild(generatorElement);
        DOMUtil.appendAttribute(generatorElement, "name", tableGenMeta.getName());
        DOMUtil.appendAttribute(generatorElement, "tableName", tableGenMeta.getTableName());
        DOMUtil.appendAttribute(generatorElement, "pkColumnName", tableGenMeta.getPkColumnName());
        DOMUtil.appendAttribute(generatorElement, "valueColumnName", tableGenMeta.getValueColumnName());
        DOMUtil.appendAttribute(generatorElement, "pkColumnValue", tableGenMeta.getPkColumnValue());
        DOMUtil.appendAttribute(generatorElement, "startValue", String.valueOf(tableGenMeta.getStartValue()));
        DOMUtil.appendAttribute(generatorElement, "increment", String.valueOf(tableGenMeta.getIncrement()));
    }
}
