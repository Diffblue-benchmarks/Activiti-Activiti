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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTaskXMLConverterDiffblueTest {
  /**
   * Test
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) SimpleValue is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given CustomProperty (default constructor) SimpleValue is empty string")
  void testWriteExtensionChildElements_givenCustomPropertySimpleValueIsEmptyString() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);
    fieldExtension.setStringValue(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertFalse(
        serviceTaskXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); then return 'false'")
  void testWriteExtensionChildElements_thenReturnFalse() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);
    fieldExtension.setStringValue(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertFalse(
        serviceTaskXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); then return 'true'")
  void testWriteExtensionChildElements_thenReturnTrue() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);
    fieldExtension.setStringValue(null);
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertTrue(serviceTaskXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when empty string; then return 'null'")
  void testParseOperationRef_whenEmptyString_thenReturnNull() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertNull(serviceTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   * <ul>
   *   <li>When {@code Operation Ref}.</li>
   *   <li>Then return {@code null:Operation Ref}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when 'Operation Ref'; then return 'null:Operation Ref'")
  void testParseOperationRef_whenOperationRef_thenReturnNullOperationRef() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertEquals("null:Operation Ref", serviceTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ServiceTaskXMLConverter}
   *   <li>
   * {@link ServiceTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link ServiceTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link ServiceTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    ServiceTaskXMLConverter actualServiceTaskXMLConverter = new ServiceTaskXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualServiceTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualServiceTaskXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("serviceTask", actualServiceTaskXMLConverter.getXMLElementName());
    Class<ServiceTask> expectedBpmnElementType = ServiceTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
