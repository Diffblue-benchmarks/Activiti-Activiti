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
import org.junit.jupiter.api.Test;

class ServiceTaskXMLConverterDiffblueTest {
  /**
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements() throws Exception {
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
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements2() throws Exception {
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
   * Method under test:
   * {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements3() throws Exception {
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

  /**
   * Method under test:
   * {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  void testParseOperationRef() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertEquals("null:Operation Ref", serviceTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Method under test:
   * {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  void testParseOperationRef2() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertNull(serviceTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }
}
