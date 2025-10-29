/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.bpmn.converter.export;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.FieldExtension;
import org.junit.jupiter.api.Test;

class FieldExtensionExportDiffblueTest {
  /**
   * Method under test:
   * {@link FieldExtensionExport#writeFieldExtensions(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFieldExtensions() throws Exception {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();

    // Act and Assert
    assertTrue(FieldExtensionExport.writeFieldExtensions(fieldExtensionList, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link FieldExtensionExport#writeFieldExtensions(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFieldExtensions2() throws Exception {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    // Act and Assert
    assertFalse(
        FieldExtensionExport.writeFieldExtensions(fieldExtensionList, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link FieldExtensionExport#writeFieldExtensions(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFieldExtensions3() throws Exception {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName("Field Extension List");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    // Act and Assert
    assertFalse(
        FieldExtensionExport.writeFieldExtensions(fieldExtensionList, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link FieldExtensionExport#writeFieldExtensions(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFieldExtensions4() throws Exception {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression(null);
    fieldExtension.setFieldName("");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    // Act and Assert
    assertFalse(
        FieldExtensionExport.writeFieldExtensions(fieldExtensionList, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link FieldExtensionExport#writeFieldExtensions(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFieldExtensions5() throws Exception {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    fieldExtensionList.add(new FieldExtension());

    // Act and Assert
    assertTrue(FieldExtensionExport.writeFieldExtensions(fieldExtensionList, true, new IndentingXMLStreamWriter(null)));
  }
}
