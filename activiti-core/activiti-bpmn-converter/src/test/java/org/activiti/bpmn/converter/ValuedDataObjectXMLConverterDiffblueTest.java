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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ItemDefinition;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.Test;

class ValuedDataObjectXMLConverterDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ValuedDataObjectXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link ValuedDataObjectXMLConverter#getBpmnElementType()}
   *   <li>{@link ValuedDataObjectXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();

    // Act
    valuedDataObjectXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = valuedDataObjectXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("dataObject", valuedDataObjectXMLConverter.getXMLElementName());
    Class<ValuedDataObject> expectedBpmnElementType = ValuedDataObject.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();
    BooleanDataObject element = mock(BooleanDataObject.class);
    when(element.getItemSubjectRef()).thenReturn(new ItemDefinition());
    BpmnModel model = new BpmnModel();

    // Act
    valuedDataObjectXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(element, atLeast(1)).getItemSubjectRef();
  }

  /**
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId(null);

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements2() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId("Element");

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements3() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();
    BooleanDataObject element = new BooleanDataObject();

    // Act and Assert
    assertTrue(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements4() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId("");

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ValuedDataObjectXMLConverter}
   */
  @Test
  void testNewValuedDataObjectXMLConverter() {
    // Arrange and Act
    ValuedDataObjectXMLConverter actualValuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    // Assert
    assertEquals("dataObject", actualValuedDataObjectXMLConverter.getXMLElementName());
    assertFalse(actualValuedDataObjectXMLConverter.didWriteExtensionStartElement);
    Class<ValuedDataObject> expectedBpmnElementType = ValuedDataObject.class;
    assertEquals(expectedBpmnElementType, actualValuedDataObjectXMLConverter.getBpmnElementType());
  }
}
