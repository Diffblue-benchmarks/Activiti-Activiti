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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.math.BigInteger;
import java.util.Iterator;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DynamicBpmnServiceImplDiffblueTest {
  @InjectMocks
  private DynamicBpmnServiceImpl dynamicBpmnServiceImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewDynamicBpmnServiceImpl() {
    // Arrange and Act
    DynamicBpmnServiceImpl actualDynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(
        new JtaProcessEngineConfiguration());

    // Assert
    assertNull(actualDynamicBpmnServiceImpl.getCommandExecutor());
    assertNull(
        ((DynamicBpmnServiceImpl) actualDynamicBpmnServiceImpl.processEngineConfiguration.getDynamicBpmnService())
            .getCommandExecutor());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id}, {@code className}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"})
  public void testChangeServiceTaskClassNameWithIdClassName() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        actualChangeServiceTaskClassNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id}, {@code className}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"})
  public void testChangeServiceTaskClassNameWithIdClassName2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"\"\n    }\n  }\n}",
        actualChangeServiceTaskClassNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id}, {@code className}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"})
  public void testChangeServiceTaskClassNameWithIdClassName3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : null\n    }\n  }\n}",
        actualChangeServiceTaskClassNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with {@code id}, {@code className}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"})
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with {@code id}, {@code className}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"})
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with {@code id}, {@code className}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"})
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskClassName"), eq("Class Name"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"})
  public void testChangeServiceTaskExpressionWithIdExpression() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"})
  public void testChangeServiceTaskExpressionWithIdExpression2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"\"\n    }\n  }\n}",
        actualChangeServiceTaskExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"})
  public void testChangeServiceTaskExpressionWithIdExpression3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : null\n    }\n  }\n}",
        actualChangeServiceTaskExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskExpression"), eq("Expression"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpression() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskDelegateExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpression2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"\"\n    }\n  }\n}",
        actualChangeServiceTaskDelegateExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with {@code id}, {@code expression}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpression3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : null\n    }\n  }\n}",
        actualChangeServiceTaskDelegateExpressionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"})
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskDelegateExpression"), eq("Expression"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id}, {@code script}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        actualChangeScriptTaskScriptResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id}, {@code script}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"\"\n    }\n  }\n}",
        actualChangeScriptTaskScriptResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id}, {@code script}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : null\n    }\n  }\n}",
        actualChangeScriptTaskScriptResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with {@code id}, {@code script}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"})
  public void testChangeScriptTaskScriptWithIdScriptInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with {@code id}, {@code script}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"})
  public void testChangeScriptTaskScriptWithIdScriptInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with {@code id}, {@code script}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"})
  public void testChangeScriptTaskScriptWithIdScriptInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("scriptTaskScript"), eq("Script"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code name}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        actualChangeUserTaskNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code name}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code name}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : null\n    }\n  }\n}",
        actualChangeUserTaskNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code id}, {@code name}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code id}, {@code name}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code id}, {@code name}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskName"), eq("Name"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id}, {@code description}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", actualChangeUserTaskDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id}, {@code description}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDescription\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDescription\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id}, {@code description}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDescription\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDescription\" : null\n    }\n  }\n}",
        actualChangeUserTaskDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with {@code id}, {@code description}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"})
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "The characteristics of someone or something", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with {@code id}, {@code description}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"})
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDescription\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDescription\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with {@code id}, {@code description}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"})
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "The characteristics of someone or something", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDescription"), eq("The characteristics of someone or something"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id}, {@code dueDate}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        actualChangeUserTaskDueDateResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id}, {@code dueDate}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskDueDateResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id}, {@code dueDate}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : null\n    }\n  }\n}",
        actualChangeUserTaskDueDateResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with {@code id}, {@code dueDate}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"})
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with {@code id}, {@code dueDate}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"})
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with {@code id}, {@code dueDate}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"})
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDueDate"), eq("2020-03-01"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id}, {@code priority}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        actualChangeUserTaskPriorityResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id}, {@code priority}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskPriorityResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id}, {@code priority}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : null\n    }\n  }\n}",
        actualChangeUserTaskPriorityResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with {@code id}, {@code priority}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"})
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with {@code id}, {@code priority}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"})
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with {@code id}, {@code priority}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"})
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskPriority"), eq("Priority"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id}, {@code category}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        actualChangeUserTaskCategoryResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id}, {@code category}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskCategoryResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id}, {@code category}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : null\n    }\n  }\n}",
        actualChangeUserTaskCategoryResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with {@code id}, {@code category}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"})
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with {@code id}, {@code category}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"})
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with {@code id}, {@code category}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"})
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskCategory"), eq("Category"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id}, {@code formKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        actualChangeUserTaskFormKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id}, {@code formKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskFormKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id}, {@code formKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : null\n    }\n  }\n}",
        actualChangeUserTaskFormKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with {@code id}, {@code formKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"})
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with {@code id}, {@code formKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"})
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with {@code id}, {@code formKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"})
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskFormKey"), eq("Form Key"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id}, {@code assignee}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        actualChangeUserTaskAssigneeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id}, {@code assignee}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskAssigneeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id}, {@code assignee}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : null\n    }\n  }\n}",
        actualChangeUserTaskAssigneeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with {@code id}, {@code assignee}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"})
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with {@code id}, {@code assignee}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"})
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with {@code id}, {@code assignee}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"})
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskAssignee"), eq("Assignee"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code owner}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        actualChangeUserTaskOwnerResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code owner}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"\"\n    }\n  }\n}",
        actualChangeUserTaskOwnerResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code owner}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : null\n    }\n  }\n}",
        actualChangeUserTaskOwnerResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code id}, {@code owner}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code id}, {@code owner}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code id}, {@code owner}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskOwner"), eq("Owner"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "",
        true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"\" ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"\" ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateUserResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries4() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", null,
        false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ null ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ null ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateUserResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "", true, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    assertTrue(elementsResult.next() instanceof TextNode);
    assertEquals("[ \"\" ]", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"\" ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateUsers\" : [ \"\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", false, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode4() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateUsers"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "",
        true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"\" ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"\" ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateGroupResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries4() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        null, false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ null ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ null ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateGroupResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "", true, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    assertTrue(elementsResult.next() instanceof TextNode);
    assertEquals("[ \"\" ]", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"\" ]\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateGroups\" : [ \"\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", false, infoNode);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"})
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode4() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateGroups"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        actualChangeDmnTaskDecisionTableKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"\"\n    }\n  }\n}",
        actualChangeDmnTaskDecisionTableKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : null\n    }\n  }\n}",
        actualChangeDmnTaskDecisionTableKeyResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)} with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)} with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)} with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"})
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("dmnTaskDecisionTableKey"), eq("Decision Table Key"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code id}, {@code condition}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"})
  public void testChangeSequenceFlowConditionWithIdCondition() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        actualChangeSequenceFlowConditionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code id}, {@code condition}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"})
  public void testChangeSequenceFlowConditionWithIdCondition2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"\"\n    }\n  }\n}",
        actualChangeSequenceFlowConditionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code id}, {@code condition}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"})
  public void testChangeSequenceFlowConditionWithIdCondition3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : null\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : null\n    }\n  }\n}",
        actualChangeSequenceFlowConditionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)} with {@code id}, {@code condition}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"})
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)} with {@code id}, {@code condition}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"})
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)} with {@code id}, {@code condition}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"})
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("sequenceFlowCondition"), eq("Condition"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return {@code null}.</li>
   *   <li>Then calls {@link ObjectNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getBpmnElementProperties(String, ObjectNode)"})
  public void testGetBpmnElementProperties_givenNull_whenObjectNodeGetReturnNull_thenCallsGet() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualBpmnElementProperties = dynamicBpmnServiceImpl.getBpmnElementProperties("42", infoNode);

    // Assert
    verify(infoNode).get(eq("bpmn"));
    assertNull(actualBpmnElementProperties);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getBpmnElementProperties(String, ObjectNode)"})
  public void testGetBpmnElementProperties_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"})
  public void testChangeLocalizationNameWithLanguageIdValue() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"})
  public void testChangeLocalizationNameWithLanguageIdValue2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"\"\n    }\n  }\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"})
  public void testChangeLocalizationNameWithLanguageIdValue3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : null\n    }\n  }\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : null\n      }\n    }\n  }\n}",
        actualChangeLocalizationNameResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"})
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"})
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"\"\n    }\n  }\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"name\" : \"\"\n}", nextResult3.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"})
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc3);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("name"), eq("42"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValue() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValue2() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValue3() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", null);

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : null\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : null\n      }\n    }\n  }\n}",
        actualChangeLocalizationDescriptionResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"})
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc3);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("description"), eq("42"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getLocalizationElementProperties(String, String, ObjectNode)"})
  public void testGetLocalizationElementProperties() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link ObjectNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getLocalizationElementProperties(String, String, ObjectNode)"})
  public void testGetLocalizationElementProperties_givenNull_thenCallsGet() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualLocalizationElementProperties = dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        infoNode);

    // Assert
    verify(infoNode).get(eq("localization"));
    assertNull(actualLocalizationElementProperties);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("42"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnInstance_thenReturnFalse() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("42"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnInstance_thenReturnFalse2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnInstance_thenReturnTrue() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).get(eq("42"));
    verify(arrayNode2, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_givenInstance_whenObjectNodeGetReturnInstance() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"})
  public void testDoesElementPropertyExist_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"})
  public void testSetElementPropertyWithStringStringStringObjectNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"42\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"})
  public void testSetElementPropertyWithStringStringStringObjectNode2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"\"\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"Property Name\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"})
  public void testSetElementPropertyWithStringStringStringObjectNode3() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    ObjectNode objectNode2 = new ObjectNode(nc);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc2);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("Property Name"), eq("42"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetBpmnNode(ObjectNode)"})
  public void testCreateOrGetBpmnNode_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualCreateOrGetBpmnNodeResult = dynamicBpmnServiceImpl.createOrGetBpmnNode(infoNode);

    // Assert
    verify(infoNode).has(eq("bpmn"));
    verify(infoNode).get(eq("bpmn"));
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertNull(actualCreateOrGetBpmnNodeResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getBpmnNode(ObjectNode)"})
  public void testGetBpmnNode_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"})
  public void testSetLocalizationProperty() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"localization\" : {\n" + "    \"en\" : {\n" + "      \"42\" : {\n"
            + "        \"Property Name\" : \"42\"\n" + "      }\n" + "    }\n" + "  }\n" + "}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"})
  public void testSetLocalizationProperty2() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"Property Name\" : \"\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"Property Name\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}.
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is {@link JsonNodeFactory} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"})
  public void testSetLocalizationProperty_thenObjectNodeWithNcIsJsonNodeFactorySizeIsOne() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(objectNode2);
    ObjectNode infoNode = new ObjectNode(nc3);

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("Property Name"), eq("42"));
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertSame(objectNode2, nextResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetLocalizationNode(ObjectNode)"})
  public void testCreateOrGetLocalizationNode_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualCreateOrGetLocalizationNodeResult = dynamicBpmnServiceImpl.createOrGetLocalizationNode(infoNode);

    // Assert
    verify(infoNode).has(eq("localization"));
    verify(infoNode).get(eq("localization"));
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertNull(actualCreateOrGetLocalizationNodeResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getLocalizationNode(ObjectNode)"})
  public void testGetLocalizationNode_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }
}
