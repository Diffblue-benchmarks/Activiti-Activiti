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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ServiceTaskXMLConverterDiffblueTest {
  /**
   * Test {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ServiceTaskXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_thenReturnFalse() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("not empty");
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertFalse(
        serviceTaskXMLConverter.writeExtensionChildElements(
            element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ServiceTaskXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_thenReturnFalse2() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(new CustomProperty());
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("not empty");
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertFalse(
        serviceTaskXMLConverter.writeExtensionChildElements(
            element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ServiceTaskXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_whenTrue_thenReturnTrue() throws Exception {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("not empty");
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask element = new ServiceTask();
    element.setCustomProperties(customProperties);
    element.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertTrue(
        serviceTaskXMLConverter.writeExtensionChildElements(
            element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when empty string; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ServiceTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenEmptyString_thenReturnNull() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertNull(serviceTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code http://activiti.org/bpmn}.
   *   <li>Then return {@code null://activiti.org/bpmn}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseOperationRef(String, BpmnModel); when 'http://activiti.org/bpmn'; then return 'null://activiti.org/bpmn'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ServiceTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenHttpActivitiOrgBpmn_thenReturnNullActivitiOrgBpmn() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertEquals(
        "null://activiti.org/bpmn",
        serviceTaskXMLConverter.parseOperationRef("http://activiti.org/bpmn", new BpmnModel()));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ServiceTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenNull_thenReturnNull() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertNull(serviceTaskXMLConverter.parseOperationRef(null, new BpmnModel()));
  }

  /**
   * Test {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code Operation Ref}.
   *   <li>Then return {@code null:Operation Ref}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseOperationRef(String, BpmnModel); when 'Operation Ref'; then return 'null:Operation Ref'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ServiceTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenOperationRef_thenReturnNullOperationRef() {
    // Arrange
    ServiceTaskXMLConverter serviceTaskXMLConverter = new ServiceTaskXMLConverter();

    // Act and Assert
    assertEquals(
        "null:Operation Ref",
        serviceTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ServiceTaskXMLConverter}
   *   <li>{@link ServiceTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link ServiceTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link ServiceTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskXMLConverter.<init>()",
    "Class ServiceTaskXMLConverter.getBpmnElementType()",
    "String ServiceTaskXMLConverter.getXMLElementName()",
    "void ServiceTaskXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    ServiceTaskXMLConverter actualServiceTaskXMLConverter = new ServiceTaskXMLConverter();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message element =
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    BpmnModel model = new BpmnModel();
    actualServiceTaskXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType =
        actualServiceTaskXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("serviceTask", actualServiceTaskXMLConverter.getXMLElementName());
    Class<ServiceTask> expectedBpmnElementType = ServiceTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
