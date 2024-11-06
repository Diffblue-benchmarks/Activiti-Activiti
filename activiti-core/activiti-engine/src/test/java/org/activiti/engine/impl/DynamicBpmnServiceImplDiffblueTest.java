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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.Test;
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
   * Test
   * {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   * with {@code id}, {@code className}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassNameWithIdClassName() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Class Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        actualChangeServiceTaskClassNameResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"Class Name\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   * with {@code id}, {@code className}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassNameWithIdClassName2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   * with {@code id}, {@code className}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassNameWithIdClassName3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   * with {@code id}, {@code className}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Class Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"Class Name\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   * with {@code id}, {@code className}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   * with {@code id}, {@code className}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassNameWithIdClassName_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskClassName"), eq("Class Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpressionWithIdExpression() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskExpressionResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpressionWithIdExpression2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpressionWithIdExpression3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpressionWithIdExpression_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskExpression"), eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpressionWithIdExpression() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskDelegateExpressionResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpressionWithIdExpression2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpressionWithIdExpression3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   * with {@code id}, {@code expression}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpressionWithIdExpression_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskDelegateExpression"), eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   * with {@code id}, {@code script}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScriptWithIdScript() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Script\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        actualChangeScriptTaskScriptResult.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"Script\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   * with {@code id}, {@code script}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScriptWithIdScript2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeScriptTaskScriptResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   * with {@code id}, {@code script}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScriptWithIdScript3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeScriptTaskScriptResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   * with {@code id}, {@code script}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  public void testChangeScriptTaskScriptWithIdScriptInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Script\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"Script\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   * with {@code id}, {@code script}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   * with {@code id}, {@code script}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScriptWithIdScript_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("scriptTaskScript"), eq("Script"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeScriptTaskScriptResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with
   * {@code id}, {@code name}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskNameWithIdName() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskNameResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with
   * {@code id}, {@code name}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskNameWithIdName2() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   * with {@code id}, {@code name}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskNameWithIdNameInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"Name\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   * with {@code id}, {@code name}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with
   * {@code id}, {@code name}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskNameWithIdName_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskName"), eq("Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskNameResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with
   * {@code id}, {@code name}.
   * <ul>
   *   <li>Then iterator next iterator next return {@link ObjectNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskNameWithIdName_thenIteratorNextIteratorNextReturnObjectNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        actualChangeUserTaskNameResult.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"Name\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   * with {@code id}, {@code description}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescriptionWithIdDescription() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"The characteristics of someone or something\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", actualChangeUserTaskDescriptionResult.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"The characteristics of someone or something\"\n}",
        nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   * with {@code id}, {@code description}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescriptionWithIdDescription2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   * with {@code id}, {@code description}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescriptionWithIdDescription3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   * with {@code id}, {@code description}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "The characteristics of someone or something", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"The characteristics of someone or something\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"The characteristics of someone or something\"\n}",
        nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   * with {@code id}, {@code description}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   * with {@code id}, {@code description}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescriptionWithIdDescription_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDescription"), eq("The characteristics of someone or something"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   * with {@code id}, {@code dueDate}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDateWithIdDueDate() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"2020-03-01\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        actualChangeUserTaskDueDateResult.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"2020-03-01\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   * with {@code id}, {@code dueDate}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDateWithIdDueDate2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDueDateResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   * with {@code id}, {@code dueDate}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDateWithIdDueDate3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDueDateResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   * with {@code id}, {@code dueDate}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"2020-03-01\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"2020-03-01\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   * with {@code id}, {@code dueDate}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   * with {@code id}, {@code dueDate}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDateWithIdDueDate_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDueDate"), eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskDueDateResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   * with {@code id}, {@code priority}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriorityWithIdPriority() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Priority\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        actualChangeUserTaskPriorityResult.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"Priority\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   * with {@code id}, {@code priority}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriorityWithIdPriority2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskPriorityResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   * with {@code id}, {@code priority}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriorityWithIdPriority3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskPriorityResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   * with {@code id}, {@code priority}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Priority\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"Priority\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   * with {@code id}, {@code priority}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   * with {@code id}, {@code priority}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriorityWithIdPriority_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskPriority"), eq("Priority"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskPriorityResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   * with {@code id}, {@code category}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategoryWithIdCategory() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Category\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        actualChangeUserTaskCategoryResult.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"Category\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   * with {@code id}, {@code category}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategoryWithIdCategory2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCategoryResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   * with {@code id}, {@code category}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategoryWithIdCategory3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCategoryResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   * with {@code id}, {@code category}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Category\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"Category\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   * with {@code id}, {@code category}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   * with {@code id}, {@code category}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategoryWithIdCategory_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskCategory"), eq("Category"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCategoryResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   * with {@code id}, {@code formKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKeyWithIdFormKey() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Form Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        actualChangeUserTaskFormKeyResult.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"Form Key\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   * with {@code id}, {@code formKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKeyWithIdFormKey2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   * with {@code id}, {@code formKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKeyWithIdFormKey3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   * with {@code id}, {@code formKey}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Form Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"Form Key\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   * with {@code id}, {@code formKey}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   * with {@code id}, {@code formKey}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKeyWithIdFormKey_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskFormKey"), eq("Form Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   * with {@code id}, {@code assignee}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssigneeWithIdAssignee() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Assignee\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        actualChangeUserTaskAssigneeResult.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"Assignee\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   * with {@code id}, {@code assignee}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssigneeWithIdAssignee2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   * with {@code id}, {@code assignee}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssigneeWithIdAssignee3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   * with {@code id}, {@code assignee}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Assignee\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"Assignee\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   * with {@code id}, {@code assignee}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   * with {@code id}, {@code assignee}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssigneeWithIdAssignee_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskAssignee"), eq("Assignee"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with
   * {@code id}, {@code owner}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwnerWithIdOwner() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskOwnerResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with
   * {@code id}, {@code owner}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwnerWithIdOwner2() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskOwnerResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   * with {@code id}, {@code owner}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Owner\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"Owner\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   * with {@code id}, {@code owner}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with
   * {@code id}, {@code owner}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwnerWithIdOwner_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskOwner"), eq("Owner"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskOwnerResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with
   * {@code id}, {@code owner}.
   * <ul>
   *   <li>Then iterator next iterator next return {@link ObjectNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwnerWithIdOwner_thenIteratorNextIteratorNextReturnObjectNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Owner\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        actualChangeUserTaskOwnerResult.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"Owner\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("[ \"2020-03-01\" ]", nextResult3.toPrettyString());
    assertEquals("\"2020-03-01\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateUserResult.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateUsers"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

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
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("[ \"2020-03-01\" ]", nextResult3.toPrettyString());
    assertEquals("\"2020-03-01\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateUsers\" : [ \"2020-03-01\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", false, infoNode);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("[ \"2020-03-01\" ]", nextResult3.toPrettyString());
    assertEquals("\"2020-03-01\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        actualChangeUserTaskCandidateGroupResult.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateGroups"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

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
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("[ \"2020-03-01\" ]", nextResult3.toPrettyString());
    assertEquals("\"2020-03-01\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCandidateGroups\" : [ \"2020-03-01\" ]\n}", nextResult2.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   * with {@code id}, {@code candidateGroup},
   * {@code overwriteOtherChangedEntries}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", false, infoNode);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   * with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Decision Table Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        actualChangeDmnTaskDecisionTableKeyResult.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   * with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   * with {@code id}, {@code decisionTableKey}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Decision Table Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   * with {@code id}, {@code decisionTableKey}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("dmnTaskDecisionTableKey"), eq("Decision Table Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   * with {@code id}, {@code condition}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowConditionWithIdCondition() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("\"Condition\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        actualChangeSequenceFlowConditionResult.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"Condition\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   * with {@code id}, {@code condition}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowConditionWithIdCondition2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   * with {@code id}, {@code condition}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowConditionWithIdCondition3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"Condition\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"Condition\"\n}", nextResult2.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   * with {@code id}, {@code condition}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowConditionWithIdCondition_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("sequenceFlowCondition"), eq("Condition"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return
   * {@code null}.</li>
   *   <li>Then calls {@link ObjectNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  public void testGetBpmnElementProperties_whenObjectNodeWithNcIsJsonNodeFactory() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42", new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  public void testGetBpmnElementProperties_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationNameWithLanguageIdValue() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationNameResult.toPrettyString());
    assertEquals("{\n  \"name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationNameWithLanguageIdValue2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationNameWithLanguageIdValue3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult4.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("\"\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"\"\n    }\n  }\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"name\" : \"\"\n}", nextResult3.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationNameWithLanguageIdValue_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode2 = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("name"), eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeLocalizationNameResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescriptionWithLanguageIdValue() {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationDescriptionResult.toPrettyString());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescriptionWithLanguageIdValue2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescriptionWithLanguageIdValue3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult4.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("\"\"", nextResult4.toPrettyString());
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
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   * with {@code language}, {@code id}, {@code value}.
   * <ul>
   *   <li>Then calls {@link ObjectNode#put(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescriptionWithLanguageIdValue_thenCallsPut() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode2 = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("description"), eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  public void testGetLocalizationElementProperties() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link ObjectNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  public void testGetLocalizationElementProperties_whenObjectNodeWithNcIsJsonNodeFactory() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnInstance_thenCallsGet() {
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnInstance_thenCallsGet2() {
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
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
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist_whenObjectNodeWithNcIsJsonNodeFactory() {
    // Arrange, Act and Assert
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name",
        new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   * with {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  public void testSetElementPropertyWithStringStringStringObjectNode() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

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
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"42\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"42\"\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   * with {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
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
   * Test {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}
   */
  @Test
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
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  public void testGetBpmnNode() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  public void testGetBpmnNode_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testSetLocalizationProperty() {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult4.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"localization\" : {\n" + "    \"en\" : {\n" + "      \"42\" : {\n"
            + "        \"Property Name\" : \"42\"\n" + "      }\n" + "    }\n" + "  }\n" + "}",
        infoNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
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
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("\"\"", nextResult4.toPrettyString());
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
    assertFalse(iteratorResult4.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  public void testGetLocalizationNode() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  public void testGetLocalizationNode_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }
}
