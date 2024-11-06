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
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.ItemDefinition;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValuedDataObjectXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ValuedDataObjectXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link ValuedDataObjectXMLConverter#getBpmnElementType()}
   *   <li>{@link ValuedDataObjectXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test
   * {@link ValuedDataObjectXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link DataObject#getItemSubjectRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls getItemSubjectRef()")
  void testWriteAdditionalAttributes_thenCallsGetItemSubjectRef() throws Exception {
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
   * Test
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Element}.</li>
   *   <li>When {@link BooleanDataObject} (default constructor) Id is
   * {@code Element}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given 'Element'; when BooleanDataObject (default constructor) Id is 'Element'")
  void testWriteExtensionChildElements_givenElement_whenBooleanDataObjectIdIsElement() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId("Element");

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given empty string")
  void testWriteExtensionChildElements_givenEmptyString() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId("");

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link BooleanDataObject} (default constructor) Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given 'null'; when BooleanDataObject (default constructor) Id is 'null'")
  void testWriteExtensionChildElements_givenNull_whenBooleanDataObjectIdIsNull() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();

    BooleanDataObject element = new BooleanDataObject();
    element.setId(null);

    // Act and Assert
    assertFalse(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValuedDataObjectXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when BooleanDataObject (default constructor); then return 'true'")
  void testWriteExtensionChildElements_whenBooleanDataObject_thenReturnTrue() throws Exception {
    // Arrange
    ValuedDataObjectXMLConverter valuedDataObjectXMLConverter = new ValuedDataObjectXMLConverter();
    BooleanDataObject element = new BooleanDataObject();

    // Act and Assert
    assertTrue(
        valuedDataObjectXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test new {@link ValuedDataObjectXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ValuedDataObjectXMLConverter}
   */
  @Test
  @DisplayName("Test new ValuedDataObjectXMLConverter (default constructor)")
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
