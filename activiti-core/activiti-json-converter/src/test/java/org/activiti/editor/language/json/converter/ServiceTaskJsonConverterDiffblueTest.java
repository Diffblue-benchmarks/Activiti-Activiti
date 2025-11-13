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
package org.activiti.editor.language.json.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ServiceTaskJsonConverterDiffblueTest {
  /**
   * Test {@link ServiceTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ServiceTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ServiceTaskJsonConverter> expectedGetResult = ServiceTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ServiceTask"));
  }

  /**
   * Test {@link ServiceTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ServiceTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    // Act and Assert
    assertEquals("ServiceTask", serviceTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ServiceTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ServiceTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode elementNode = new ArrayNode(nf);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        serviceTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ServiceTask);
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementation());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementationType());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getOperationRef());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getResultVariableName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getType());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ServiceTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getFieldExtensions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link ServiceTask}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return ServiceTask")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ServiceTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnServiceTask() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        serviceTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ServiceTask);
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementation());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementationType());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getOperationRef());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getResultVariableName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getType());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ServiceTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getFieldExtensions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();
    ServiceTask task = new ServiceTask();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode2() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask task = new ServiceTask();
    task.setFieldExtensions(fieldExtensions);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode3() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Field Name");
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask task = new ServiceTask();
    task.setFieldExtensions(fieldExtensions);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode4() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Field Name");
    fieldExtension.setStringValue("");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask task = new ServiceTask();
    task.setFieldExtensions(fieldExtensions);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode5() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Field Name");
    fieldExtension.setStringValue(null);
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask task = new ServiceTask();
    task.setFieldExtensions(fieldExtensions);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String, ServiceTask,
   * ObjectNode)} with {@code propertyName}, {@code fieldName}, {@code task}, {@code
   * propertiesNode}.
   *
   * <p>Method under test: {@link ServiceTaskJsonConverter#setPropertyFieldValue(String, String,
   * ServiceTask, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyFieldValue(String, String, ServiceTask, ObjectNode) with 'propertyName', 'fieldName', 'task', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskJsonConverter.setPropertyFieldValue(String, String, ServiceTask, ObjectNode)"
  })
  void testSetPropertyFieldValueWithPropertyNameFieldNameTaskPropertiesNode6() {
    // Arrange
    ServiceTaskJsonConverter serviceTaskJsonConverter = new ServiceTaskJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Field Name");
    fieldExtension.setStringValue("");
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask task = new ServiceTask();
    task.setFieldExtensions(fieldExtensions);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    serviceTaskJsonConverter.setPropertyFieldValue(
        "Property Name", "Field Name", task, propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test new {@link ServiceTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ServiceTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new ServiceTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskJsonConverter.<init>()"})
  void testNewServiceTaskJsonConverter() {
    // Arrange and Act
    ServiceTaskJsonConverter actualServiceTaskJsonConverter = new ServiceTaskJsonConverter();

    // Assert
    assertNull(actualServiceTaskJsonConverter.shapesArrayNode);
    assertNull(actualServiceTaskJsonConverter.flowElementNode);
    assertNull(actualServiceTaskJsonConverter.decisionTableKeyMap);
    assertNull(actualServiceTaskJsonConverter.model);
    assertNull(actualServiceTaskJsonConverter.processor);
    assertEquals(0.0d, actualServiceTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualServiceTaskJsonConverter.subProcessY);
  }
}
