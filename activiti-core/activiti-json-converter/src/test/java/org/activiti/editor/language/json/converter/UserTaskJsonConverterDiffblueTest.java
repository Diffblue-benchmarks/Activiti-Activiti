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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskJsonConverterDiffblueTest {
  /**
   * Test {@link UserTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    UserTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<UserTaskJsonConverter> expectedGetResult = UserTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("UserTask"));
  }

  /**
   * Test {@link UserTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UserTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    // Act and Assert
    assertEquals("UserTask", userTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link UserTaskJsonConverter#getExtensionElementValue(String, UserTask)}.
   *
   * <ul>
   *   <li>When {@link UserTask} (default constructor).
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#getExtensionElementValue(String, UserTask)}
   */
  @Test
  @DisplayName(
      "Test getExtensionElementValue(String, UserTask); when UserTask (default constructor); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UserTaskJsonConverter.getExtensionElementValue(String, UserTask)"})
  void testGetExtensionElementValue_whenUserTask_thenReturnEmptyString() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    // Act and Assert
    assertEquals("", userTaskJsonConverter.getExtensionElementValue("Name", new UserTask()));
  }

  /**
   * Test {@link UserTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link UserTask}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return UserTask")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement UserTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnUserTask() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        userTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof UserTask);
    assertNull(((UserTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getAssignee());
    assertNull(((UserTask) actualConvertJsonToElementResult).getBusinessCalendarName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getCategory());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDueDate());
    assertNull(((UserTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFormKey());
    assertNull(((UserTask) actualConvertJsonToElementResult).getOwner());
    assertNull(((UserTask) actualConvertJsonToElementResult).getPriority());
    assertNull(((UserTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((UserTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((UserTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((UserTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateGroups().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateUsers().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getTaskListeners().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(
        ((UserTask) actualConvertJsonToElementResult).getCustomGroupIdentityLinks().isEmpty());
    assertTrue(
        ((UserTask) actualConvertJsonToElementResult).getCustomUserIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillAssigneeInfo(JsonNode, JsonNode, UserTask); when BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillAssigneeInfo(JsonNode, JsonNode, UserTask)"})
  void testFillAssigneeInfo_whenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    BinaryNode canCompleteTaskNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.FALSE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then {@link UserTask} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillAssigneeInfo(JsonNode, JsonNode, UserTask); when Instance; then UserTask (default constructor) ExtensionElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillAssigneeInfo(JsonNode, JsonNode, UserTask)"})
  void testFillAssigneeInfo_whenInstance_thenUserTaskExtensionElementsSizeIsOne() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.FALSE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then {@link UserTask} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillAssigneeInfo(JsonNode, JsonNode, UserTask); when Instance; then UserTask (default constructor) ExtensionElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillAssigneeInfo(JsonNode, JsonNode, UserTask)"})
  void testFillAssigneeInfo_whenInstance_thenUserTaskExtensionElementsSizeIsOne2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    NullNode canCompleteTaskNode = NullNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.FALSE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link UserTask} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillAssigneeInfo(JsonNode, JsonNode, UserTask); when 'null'; then UserTask (default constructor) ExtensionElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillAssigneeInfo(JsonNode, JsonNode, UserTask)"})
  void testFillAssigneeInfo_whenNull_thenUserTaskExtensionElementsSizeIsOne() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, null, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.FALSE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@link UserTask} (default constructor).
   *   <li>Then {@link UserTask} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillAssigneeInfo(JsonNode, JsonNode, UserTask); when UserTask (default constructor); then UserTask (default constructor) ExtensionElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillAssigneeInfo(JsonNode, JsonNode, UserTask)"})
  void testFillAssigneeInfo_whenUserTask_thenUserTaskExtensionElementsSizeIsOne() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.FALSE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateUsers(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('10.0', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-10.0").size());
    assertEquals(1, extensionElements.get("user-info-externalid-10.0").size());
    assertEquals(1, extensionElements.get("user-info-firstname-10.0").size());
    assertEquals(1, extensionElements.get("user-info-lastname-10.0").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateUsers(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateUsers(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers3() throws UnsupportedEncodingException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('QVhBWEFYQVg=', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-QVhBWEFYQVg=").size());
    assertEquals(1, extensionElements.get("user-info-externalid-QVhBWEFYQVg=").size());
    assertEquals(1, extensionElements.get("user-info-firstname-QVhBWEFYQVg=").size());
    assertEquals(1, extensionElements.get("user-info-lastname-QVhBWEFYQVg=").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateUsers(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-As Text").size());
    assertEquals(1, extensionElements.get("user-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("user-info-firstname-As Text").size());
    assertEquals(1, extensionElements.get("user-info-lastname-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then {@link UserTask} (default constructor) CandidateUsers Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayList() add 'null'; then UserTask (default constructor) CandidateUsers Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayListAddNull_thenUserTaskCandidateUsersEmpty() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(null);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayList() add valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayListAddValueOfTen() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>When {@link ArrayNode}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayNode asText() return 'null'; when ArrayNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayNodeAsTextReturnNull_whenArrayNode() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn(null);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    userTaskJsonConverter.fillCandidateUsers(
        idmDefNode, mock(ArrayNode.class), mock(UserTask.class));

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayNodeIsNullReturnTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayNodeIsNullReturnTrue2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return zero.
   *   <li>Then calls {@link UserTask#addExtensionElement(ExtensionElement)}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayNode size() return zero; then calls addExtensionElement(ExtensionElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayNodeSizeReturnZero_thenCallsAddExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(0);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");

    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateUsers(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateUsers(isA(List.class));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode idmDefNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given 'false'; when ArrayNode isNull() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenFalse_whenArrayNodeIsNullReturnFalse() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-As Text").size());
    assertEquals(1, extensionElements.get("user-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("user-info-firstname-As Text").size());
    assertEquals(1, extensionElements.get("user-info-lastname-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given 'true'; when ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenTrue_whenArrayNodeIsNullReturnTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-As Text").size());
    assertEquals(1, extensionElements.get("user-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("user-info-firstname-As Text").size());
    assertEquals(1, extensionElements.get("user-info-lastname-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Then calls {@link UserTask#addExtensionElement(ExtensionElement)}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); then calls addExtensionElement(ExtensionElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_thenCallsAddExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");

    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateUsers(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateUsers(isA(List.class));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); when BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_whenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BinaryNode canCompleteTaskNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-As Text").size());
    assertEquals(1, extensionElements.get("user-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("user-info-firstname-As Text").size());
    assertEquals(1, extensionElements.get("user-info-lastname-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateUsers(JsonNode, JsonNode, UserTask); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_whenNull() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, null, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}",
        candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    assertEquals(1, extensionElements.get("user-info-email-As Text").size());
    assertEquals(1, extensionElements.get("user-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("user-info-firstname-As Text").size());
    assertEquals(1, extensionElements.get("user-info-lastname-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-user"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link UserTask} (default constructor) CandidateUsers Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); when valueOf ten; then UserTask (default constructor) CandidateUsers Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_whenValueOfTen_thenUserTaskCandidateUsersEmpty() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link UserTask} (default constructor) CandidateUsers Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateUsers(JsonNode, JsonNode, UserTask); when valueOf ten; then UserTask (default constructor) CandidateUsers Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.fillCandidateUsers(JsonNode, JsonNode, UserTask)"})
  void testFillCandidateUsers_whenValueOfTen_thenUserTaskCandidateUsersEmpty2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateGroups(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('10.0', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-10.0").size());
    assertEquals(1, extensionElements.get("group-info-name-10.0").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateGroups(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateGroups(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups3() throws UnsupportedEncodingException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('QVhBWEFYQVg=', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-QVhBWEFYQVg=").size());
    assertEquals(1, extensionElements.get("group-info-name-QVhBWEFYQVg=").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateGroups(JsonNode, JsonNode, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("group-info-name-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then {@link UserTask} (default constructor) CandidateGroups Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayList() add 'null'; then UserTask (default constructor) CandidateGroups Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayListAddNull_thenUserTaskCandidateGroupsEmpty() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(null);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayList() add valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayListAddValueOfTen() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>When {@link ArrayNode}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayNode asText() return 'null'; when ArrayNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayNodeAsTextReturnNull_whenArrayNode() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn(null);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    userTaskJsonConverter.fillCandidateGroups(
        idmDefNode, mock(ArrayNode.class), mock(UserTask.class));

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode).asText();
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayNodeIsNullReturnTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayNodeIsNullReturnTrue2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return zero.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayNode size() return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayNodeSizeReturnZero() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(0);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");

    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateGroups(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateGroups(isA(List.class));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode idmDefNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given 'false'; when ArrayNode isNull() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenFalse_whenArrayNodeIsNullReturnFalse() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("group-info-name-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given 'true'; when ArrayNode isNull() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenTrue_whenArrayNodeIsNullReturnTrue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("group-info-name-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>Then calls {@link UserTask#addExtensionElement(ExtensionElement)}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); then calls addExtensionElement(ExtensionElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_thenCallsAddExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(false);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");

    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateGroups(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(canCompleteTaskNode).asText();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateGroups(isA(List.class));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); when BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_whenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BinaryNode canCompleteTaskNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("group-info-name-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName("Test fillCandidateGroups(JsonNode, JsonNode, UserTask); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_whenNull() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, null, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals(
        "${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}",
        candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    assertEquals(1, extensionElements.get("group-info-externalid-As Text").size());
    assertEquals(1, extensionElements.get("group-info-name-As Text").size());
    assertTrue(extensionElements.containsKey("activiti-idm-candidate-group"));
    assertTrue(extensionElements.containsKey("initiator-can-complete"));
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link UserTask} (default constructor) CandidateGroups Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); when valueOf ten; then UserTask (default constructor) CandidateGroups Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_whenValueOfTen_thenUserTaskCandidateGroupsEmpty() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode idmDefNode = DoubleNode.valueOf(10.0d);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link UserTask} (default constructor) CandidateGroups Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test fillCandidateGroups(JsonNode, JsonNode, UserTask); when valueOf ten; then UserTask (default constructor) CandidateGroups Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.fillCandidateGroups(JsonNode, JsonNode, UserTask)"
  })
  void testFillCandidateGroups_whenValueOfTen_thenUserTaskCandidateGroupsEmpty2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode canCompleteTaskNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#addInitiatorCanCompleteExtensionElement(boolean, UserTask)}.
   *
   * <p>Method under test: {@link
   * UserTaskJsonConverter#addInitiatorCanCompleteExtensionElement(boolean, UserTask)}
   */
  @Test
  @DisplayName("Test addInitiatorCanCompleteExtensionElement(boolean, UserTask)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskJsonConverter.addInitiatorCanCompleteExtensionElement(boolean, UserTask)"
  })
  void testAddInitiatorCanCompleteExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addInitiatorCanCompleteExtensionElement(true, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(Boolean.TRUE.toString(), getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("10.0", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", (JsonNode) null, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask3() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    BinaryNode elementNode = new BinaryNode(new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Name", getResult2.getName());
    assertEquals("QQFBAUEBQQE=", getResult2.getElementText());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>When Instance.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'; when Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask_whenInstance() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>When Instance.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'; when Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask_whenInstance2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    NullNode elementNode = NullNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)} with {@code
   * name}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, JsonNode, UserTask) with 'name', 'elementNode', 'task'; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, JsonNode, UserTask)"})
  void testAddExtensionElementWithNameElementNodeTask_whenValueOfTen() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement(null, elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, String, UserTask)} with {@code
   * name}, {@code elementText}, {@code task}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, String,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, String, UserTask) with 'name', 'elementText', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, String, UserTask)"})
  void testAddExtensionElementWithNameElementTextTask() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", "Element Text", task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Element Text", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test {@link UserTaskJsonConverter#addExtensionElement(String, String, UserTask)} with {@code
   * name}, {@code elementText}, {@code task}.
   *
   * <p>Method under test: {@link UserTaskJsonConverter#addExtensionElement(String, String,
   * UserTask)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, String, UserTask) with 'name', 'elementText', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.addExtensionElement(String, String, UserTask)"})
  void testAddExtensionElementWithNameElementTextTask2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("", "Element Text", task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link UserTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link UserTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new UserTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskJsonConverter.<init>()"})
  void testNewUserTaskJsonConverter() {
    // Arrange and Act
    UserTaskJsonConverter actualUserTaskJsonConverter = new UserTaskJsonConverter();

    // Assert
    assertNull(actualUserTaskJsonConverter.shapesArrayNode);
    assertNull(actualUserTaskJsonConverter.flowElementNode);
    assertNull(actualUserTaskJsonConverter.formMap);
    assertNull(actualUserTaskJsonConverter.formKeyMap);
    assertNull(actualUserTaskJsonConverter.model);
    assertNull(actualUserTaskJsonConverter.processor);
    assertEquals(0.0d, actualUserTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualUserTaskJsonConverter.subProcessY);
  }
}
