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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.util.Iterator;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DynamicBpmnServiceImplDiffblueTest {
  /**
   * Test {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewDynamicBpmnServiceImpl() {
    // Arrange and Act
    DynamicBpmnServiceImpl actualDynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Assert
    assertNull(actualDynamicBpmnServiceImpl.getCommandExecutor());
    assertNull(
        ((DynamicBpmnServiceImpl)
                actualDynamicBpmnServiceImpl.processEngineConfiguration.getDynamicBpmnService())
            .getCommandExecutor());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)} with
   * {@code id}, {@code className}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskClassNameWithIdClassNameInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id},
   * {@code className}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"
  })
  public void testChangeServiceTaskClassNameWithIdClassName_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeServiceTaskClassName("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id},
   * {@code className}.
   *
   * <ul>
   *   <li>When {@code Class Name}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"
  })
  public void testChangeServiceTaskClassNameWithIdClassName_whenClassName() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskClassNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskClassName("42", "Class Name");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)} with {@code id},
   * {@code className}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskClassName(String, String)"
  })
  public void testChangeServiceTaskClassNameWithIdClassName_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskClassNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskClassName("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskClassNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   * with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskExpressionWithIdExpressionInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code
   * id}, {@code expression}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"
  })
  public void testChangeServiceTaskExpressionWithIdExpression_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeServiceTaskExpression("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code
   * id}, {@code expression}.
   *
   * <ul>
   *   <li>When {@code Expression}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"
  })
  public void testChangeServiceTaskExpressionWithIdExpression_whenExpression() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskExpressionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskExpression("42", "Expression");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)} with {@code
   * id}, {@code expression}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskExpression(String, String)"
  })
  public void testChangeServiceTaskExpressionWithIdExpression_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskExpressionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskExpression("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeServiceTaskExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode4() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String,
   * ObjectNode)} with {@code id}, {@code expression}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String, ObjectNode)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpressionInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with
   * {@code id}, {@code expression}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpression_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeServiceTaskDelegateExpression("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with
   * {@code id}, {@code expression}.
   *
   * <ul>
   *   <li>When {@code Expression}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpression_whenExpression() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)} with
   * {@code id}, {@code expression}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeServiceTaskDelegateExpression(String, String)"
  })
  public void testChangeServiceTaskDelegateExpressionWithIdExpression_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeServiceTaskDelegateExpression("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeServiceTaskDelegateExpressionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)} with
   * {@code id}, {@code script}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeScriptTaskScript(String, String, ObjectNode)"
  })
  public void testChangeScriptTaskScriptWithIdScriptInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id},
   * {@code script}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeScriptTaskScript("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id},
   * {@code script}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeScriptTaskScriptResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeScriptTaskScript("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)} with {@code id},
   * {@code script}.
   *
   * <ul>
   *   <li>When {@code Script}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeScriptTaskScript(String, String)"})
  public void testChangeScriptTaskScriptWithIdScript_whenScript() {
    // Arrange and Act
    ObjectNode actualChangeScriptTaskScriptResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeScriptTaskScript("42", "Script");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeScriptTaskScriptResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode_givenTrue_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)} with {@code
   * id}, {@code name}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskName(String, String, ObjectNode)"})
  public void testChangeUserTaskNameWithIdNameInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code
   * name}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskNameResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskName("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code
   * name}.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName_whenName() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskName("42", "Name");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)} with {@code id}, {@code
   * name}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskName(String, String)"})
  public void testChangeUserTaskNameWithIdName_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskName("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id},
   * {@code description}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskDescriptionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskDescription("42", "The characteristics of someone or something");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription(
        "42", "The characteristics of someone or something", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription(
        "42", "The characteristics of someone or something", infoNode);

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription(
        "42", "The characteristics of someone or something", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription(
        "42", "The characteristics of someone or something", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)} with
   * {@code id}, {@code description}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDescription(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDescriptionWithIdDescriptionInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id},
   * {@code description}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskDescription("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)} with {@code id},
   * {@code description}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDescription(String, String)"})
  public void testChangeUserTaskDescriptionWithIdDescription_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskDescriptionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskDescription("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)} with
   * {@code id}, {@code dueDate}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String, ObjectNode)"
  })
  public void testChangeUserTaskDueDateWithIdDueDateInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id},
   * {@code dueDate}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskDueDate("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id},
   * {@code dueDate}.
   *
   * <ul>
   *   <li>When {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate_when20200301() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskDueDateResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)} with {@code id},
   * {@code dueDate}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskDueDate(String, String)"})
  public void testChangeUserTaskDueDateWithIdDueDate_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskDueDateResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskDueDate("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskDueDateResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)} with
   * {@code id}, {@code priority}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskPriority(String, String, ObjectNode)"
  })
  public void testChangeUserTaskPriorityWithIdPriorityInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id},
   * {@code priority}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskPriority("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id},
   * {@code priority}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskPriorityResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskPriority("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)} with {@code id},
   * {@code priority}.
   *
   * <ul>
   *   <li>When {@code Priority}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskPriority(String, String)"})
  public void testChangeUserTaskPriorityWithIdPriority_whenPriority() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskPriorityResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskPriority("42", "Priority");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskPriorityResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)} with
   * {@code id}, {@code category}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCategory(String, String, ObjectNode)"
  })
  public void testChangeUserTaskCategoryWithIdCategoryInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id},
   * {@code category}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskCategory("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id},
   * {@code category}.
   *
   * <ul>
   *   <li>When {@code Category}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory_whenCategory() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCategoryResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCategory("42", "Category");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)} with {@code id},
   * {@code category}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskCategory(String, String)"})
  public void testChangeUserTaskCategoryWithIdCategory_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCategoryResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCategory("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCategoryResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)} with
   * {@code id}, {@code formKey}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String, ObjectNode)"
  })
  public void testChangeUserTaskFormKeyWithIdFormKeyInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id},
   * {@code formKey}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskFormKey("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id},
   * {@code formKey}.
   *
   * <ul>
   *   <li>When {@code Form Key}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey_whenFormKey() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskFormKeyResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskFormKey("42", "Form Key");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)} with {@code id},
   * {@code formKey}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskFormKey(String, String)"})
  public void testChangeUserTaskFormKeyWithIdFormKey_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskFormKeyResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskFormKey("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskFormKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)} with
   * {@code id}, {@code assignee}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String, ObjectNode)"
  })
  public void testChangeUserTaskAssigneeWithIdAssigneeInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id},
   * {@code assignee}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskAssignee("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id},
   * {@code assignee}.
   *
   * <ul>
   *   <li>When {@code Assignee}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee_whenAssignee() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskAssigneeResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskAssignee("42", "Assignee");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)} with {@code id},
   * {@code assignee}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskAssignee(String, String)"})
  public void testChangeUserTaskAssigneeWithIdAssignee_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskAssigneeResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskAssignee("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskAssigneeResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)} with {@code
   * id}, {@code owner}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicBpmnServiceImpl.changeUserTaskOwner(String, String, ObjectNode)"})
  public void testChangeUserTaskOwnerWithIdOwnerInfoNode_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code
   * owner}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration).changeUserTaskOwner("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code
   * owner}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskOwnerResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskOwner("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)} with {@code id}, {@code
   * owner}.
   *
   * <ul>
   *   <li>When {@code Owner}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.changeUserTaskOwner(String, String)"})
  public void testChangeUserTaskOwnerWithIdOwner_whenOwner() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskOwnerResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskOwner("42", "Owner");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskOwnerResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with
   * {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateUserResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateUser("42", "2020-03-01", true);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with
   * {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries2() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateUserResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateUser("42", "2020-03-01", false);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with
   * {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries3() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateUserResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateUser("42", null, false);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)} with
   * {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntries4() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeUserTaskCandidateUser("42", "", true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateUserResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateUserResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", false, infoNode);

    // Assert
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
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", null, false, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult4.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode4() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode5() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode = new ObjectNode(nc);
    objectNode.putObject("42");

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode6() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode7() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode8() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", false, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode, atLeast(1)).get("42");
    verify(infoNode, atLeast(1)).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode9() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(false);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(objectNode).putObject("42");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateUser}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String,
   * boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateUser(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateUserWithIdCandidateUserOverwriteOtherChangedEntriesInfoNode10() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(infoNode).putObject("bpmn");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with
   * {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateGroupResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateGroup("42", "2020-03-01", true);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with
   * {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries2() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateGroupResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateGroup("42", "2020-03-01", false);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with
   * {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries3() {
    // Arrange and Act
    ObjectNode actualChangeUserTaskCandidateGroupResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeUserTaskCandidateGroup("42", null, false);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)} with
   * {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean)"
  })
  public void testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntries4() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeUserTaskCandidateGroup("42", "", true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCandidateGroupResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeUserTaskCandidateGroupResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", false, infoNode);

    // Assert
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
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", null, false, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult3.elements();
    JsonNode nextResult4 = elementsResult.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult4.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode4() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode5() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode = new ObjectNode(nc);
    objectNode.putObject("42");

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode6() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode7() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode8() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", false, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode, atLeast(1)).get("42");
    verify(infoNode, atLeast(1)).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode9() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(false);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(objectNode).putObject("42");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean,
   * ObjectNode)} with {@code id}, {@code candidateGroup}, {@code overwriteOtherChangedEntries},
   * {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String,
   * String, boolean, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeUserTaskCandidateGroup(String, String, boolean, ObjectNode)"
  })
  public void
      testChangeUserTaskCandidateGroupWithIdCandidateGroupOverwriteOtherChangedEntriesInfoNode10() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(processEngineConfiguration);

    ObjectNode objectNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(objectNode.has(Mockito.<String>any())).thenReturn(true);
    when(objectNode.put(Mockito.<String>any(), Mockito.<JsonNode>any()))
        .thenReturn(DoubleNode.valueOf(10.0d));
    objectNode.put("42", DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(objectNode);
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42", "2020-03-01", true, infoNode);

    // Assert
    verify(objectNode).has("42");
    verify(infoNode).has("bpmn");
    verify(objectNode).get("42");
    verify(infoNode).get("bpmn");
    verify(objectNode).put(eq("42"), isA(JsonNode.class));
    verify(infoNode).putObject("bpmn");
    verify(processEngineConfiguration).getObjectMapper();
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode4() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode_givenFalse() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   * with {@code id}, {@code decisionTableKey}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String, ObjectNode)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKeyInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code
   * id}, {@code decisionTableKey}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeDmnTaskDecisionTableKey("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code
   * id}, {@code decisionTableKey}.
   *
   * <ul>
   *   <li>When {@code Decision Table Key}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey_whenDecisionTableKey() {
    // Arrange and Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeDmnTaskDecisionTableKey("42", "Decision Table Key");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)} with {@code
   * id}, {@code decisionTableKey}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey(String, String)"
  })
  public void testChangeDmnTaskDecisionTableKeyWithIdDecisionTableKey_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeDmnTaskDecisionTableKey("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeDmnTaskDecisionTableKeyResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   * with {@code id}, {@code condition}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String, ObjectNode)"
  })
  public void testChangeSequenceFlowConditionWithIdConditionInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code
   * id}, {@code condition}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"
  })
  public void testChangeSequenceFlowConditionWithIdCondition_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeSequenceFlowCondition("42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code
   * id}, {@code condition}.
   *
   * <ul>
   *   <li>When {@code Condition}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"
  })
  public void testChangeSequenceFlowConditionWithIdCondition_whenCondition() {
    // Arrange and Act
    ObjectNode actualChangeSequenceFlowConditionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeSequenceFlowCondition("42", "Condition");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)} with {@code
   * id}, {@code condition}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeSequenceFlowCondition(String, String)"
  })
  public void testChangeSequenceFlowConditionWithIdCondition_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeSequenceFlowConditionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeSequenceFlowCondition("42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeSequenceFlowConditionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code bpmn}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getBpmnElementProperties(String, ObjectNode)"
  })
  public void testGetBpmnElementProperties_givenBpmn() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42", infoNode));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getBpmnElementProperties(String, ObjectNode)"
  })
  public void testGetBpmnElementProperties_thenCallsGet() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));

    // Act
    ObjectNode actualBpmnElementProperties =
        dynamicBpmnServiceImpl.getBpmnElementProperties("42", infoNode);

    // Assert
    verify(infoNode).get("bpmn");
    assertNull(actualBpmnElementProperties);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getBpmnElementProperties(String, ObjectNode)"
  })
  public void testGetBpmnElementProperties_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42", new ObjectNode(nc)));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code
   * language}, {@code id}, {@code value}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"
  })
  public void testChangeLocalizationNameWithLanguageIdValue() {
    // Arrange and Act
    ObjectNode actualChangeLocalizationNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeLocalizationName("en", "42", "42");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode_givenTrue_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
    verify(infoNode).putObject("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   * with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationName(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationNameWithLanguageIdValueInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code
   * language}, {@code id}, {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"
  })
  public void testChangeLocalizationNameWithLanguageIdValue_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationNameResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeLocalizationName("en", "42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)} with {@code
   * language}, {@code id}, {@code value}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationName(String, String, String)"
  })
  public void testChangeLocalizationNameWithLanguageIdValue_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeLocalizationNameResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeLocalizationName("en", "42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualChangeLocalizationNameResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with
   * {@code language}, {@code id}, {@code value}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValue() {
    // Arrange and Act
    ObjectNode actualChangeLocalizationDescriptionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeLocalizationDescription("en", "42", "42");

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
    verify(infoNode).putObject("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String,
   * ObjectNode)} with {@code language}, {@code id}, {@code value}, {@code infoNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String, ObjectNode)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValueInfoNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with
   * {@code language}, {@code id}, {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getObjectMapper()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValue_thenCallsGetObjectMapper() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getObjectMapper())
        .thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult =
        new DynamicBpmnServiceImpl(processEngineConfiguration)
            .changeLocalizationDescription("en", "42", "");

    // Assert
    verify(processEngineConfiguration).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)} with
   * {@code language}, {@code id}, {@code value}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.changeLocalizationDescription(String, String, String)"
  })
  public void testChangeLocalizationDescriptionWithLanguageIdValue_whenNull() {
    // Arrange and Act
    ObjectNode actualChangeLocalizationDescriptionResult =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration())
            .changeLocalizationDescription("en", "42", null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(
        actualChangeLocalizationDescriptionResult.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String,
   * ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getLocalizationElementProperties(String, String, ObjectNode)"
  })
  public void testGetLocalizationElementProperties() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertNull(
        dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42", new ObjectNode(nc)));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code localization}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getLocalizationElementProperties(String, String, ObjectNode)"
  })
  public void testGetLocalizationElementProperties_givenLocalization() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42", infoNode));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String,
   * String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode DynamicBpmnServiceImpl.getLocalizationElementProperties(String, String, ObjectNode)"
  })
  public void testGetLocalizationElementProperties_thenCallsGet() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));

    // Act
    ObjectNode actualLocalizationElementProperties =
        dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42", infoNode);

    // Assert
    verify(infoNode).get("localization");
    assertNull(actualLocalizationElementProperties);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get("42");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get("42");
    verify(arrayNode, atLeast(1)).get("Property Name");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnValueOfTen_thenCallsGet() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get("42");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenArrayNodeGetReturnValueOfTen_thenReturnTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get("42");
    verify(arrayNode, atLeast(1)).get("Property Name");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenArrayNodeIsNullReturnFalse_thenCallsIsNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).get("42");
    verify(arrayNode2, atLeast(1)).get("Property Name");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).get("42");
    verify(arrayNode2, atLeast(1)).get("Property Name");
    verify(infoNode, atLeast(1)).get("bpmn");
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get("bpmn");
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code bpmn}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenBpmn() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    infoNode.put("bpmn", DoubleNode.valueOf(10.0d));

    // Act and Assert
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_givenValueOfTen_whenObjectNodeGetReturnValueOfTen() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    boolean actualDoesElementPropertyExistResult =
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get("bpmn");
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean DynamicBpmnServiceImpl.doesElementPropertyExist(String, String, ObjectNode)"
  })
  public void testDoesElementPropertyExist_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertFalse(
        dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name", new ObjectNode(nc)));
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, JsonNode, ObjectNode)}
   * with {@code String}, {@code String}, {@code JsonNode}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String,
   * JsonNode, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, JsonNode, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringJsonNodeObjectNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    DoubleNode propertyValue = DoubleNode.valueOf(10.0d);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", propertyValue, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, JsonNode, ObjectNode)}
   * with {@code String}, {@code String}, {@code JsonNode}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String,
   * JsonNode, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, JsonNode, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringJsonNodeObjectNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    DoubleNode propertyValue = DoubleNode.valueOf(10.0d);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", propertyValue, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode actualNextResult = iteratorResult3.next();
    assertFalse(iteratorResult3.hasNext());
    assertSame(propertyValue, actualNextResult);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, JsonNode, ObjectNode)}
   * with {@code String}, {@code String}, {@code JsonNode}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String,
   * JsonNode, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, JsonNode, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringJsonNodeObjectNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", (JsonNode) null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    assertTrue(iteratorResult3.next() instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, JsonNode, ObjectNode)}
   * with {@code String}, {@code String}, {@code JsonNode}, {@code ObjectNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String,
   * JsonNode, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, JsonNode, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringJsonNodeObjectNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    DoubleNode propertyValue = DoubleNode.valueOf(10.0d);

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", propertyValue, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, JsonNode, ObjectNode)}
   * with {@code String}, {@code String}, {@code JsonNode}, {@code ObjectNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String,
   * JsonNode, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, JsonNode, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringJsonNodeObjectNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    DoubleNode propertyValue = DoubleNode.valueOf(10.0d);

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", propertyValue, infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("bpmn", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", (String) null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult3.getNodeType());
    assertFalse(nextResult3.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertTrue(nextResult3.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)} with
   * {@code String}, {@code String}, {@code String}, {@code ObjectNode}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String,
   * ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setElementProperty(String, String, String, ObjectNode)"
  })
  public void testSetElementPropertyWithStringStringStringObjectNode_whenEmptyString() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "", infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetBpmnNode(ObjectNode)"})
  public void testCreateOrGetBpmnNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    ObjectNode actualCreateOrGetBpmnNodeResult =
        dynamicBpmnServiceImpl.createOrGetBpmnNode(infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    verify(infoNode).putObject("bpmn");
    assertEquals(0, actualCreateOrGetBpmnNodeResult.size());
    assertEquals(JsonNodeType.OBJECT, actualCreateOrGetBpmnNodeResult.getNodeType());
    assertFalse(actualCreateOrGetBpmnNodeResult.isArray());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBigDecimal());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBigInteger());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBinary());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBoolean());
    assertFalse(actualCreateOrGetBpmnNodeResult.isDouble());
    assertFalse(actualCreateOrGetBpmnNodeResult.isFloat());
    assertFalse(actualCreateOrGetBpmnNodeResult.isFloatingPointNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isInt());
    assertFalse(actualCreateOrGetBpmnNodeResult.isIntegralNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isLong());
    assertFalse(actualCreateOrGetBpmnNodeResult.isMissingNode());
    assertFalse(actualCreateOrGetBpmnNodeResult.isNull());
    assertFalse(actualCreateOrGetBpmnNodeResult.isNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isPojo());
    assertFalse(actualCreateOrGetBpmnNodeResult.isShort());
    assertFalse(actualCreateOrGetBpmnNodeResult.isTextual());
    assertFalse(actualCreateOrGetBpmnNodeResult.isValueNode());
    assertFalse(actualCreateOrGetBpmnNodeResult.iterator().hasNext());
    assertTrue(actualCreateOrGetBpmnNodeResult.isContainerNode());
    assertTrue(actualCreateOrGetBpmnNodeResult.isEmpty());
    assertTrue(actualCreateOrGetBpmnNodeResult.isObject());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ObjectNode} {@link ObjectNode#has(String)} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetBpmnNode(ObjectNode)"})
  public void testCreateOrGetBpmnNode_givenTrue_whenObjectNodeHasReturnTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    ObjectNode actualCreateOrGetBpmnNodeResult =
        dynamicBpmnServiceImpl.createOrGetBpmnNode(infoNode);

    // Assert
    verify(infoNode).has("bpmn");
    verify(infoNode).get("bpmn");
    assertEquals(0, actualCreateOrGetBpmnNodeResult.size());
    assertEquals(JsonNodeType.OBJECT, actualCreateOrGetBpmnNodeResult.getNodeType());
    assertFalse(actualCreateOrGetBpmnNodeResult.isArray());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBigDecimal());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBigInteger());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBinary());
    assertFalse(actualCreateOrGetBpmnNodeResult.isBoolean());
    assertFalse(actualCreateOrGetBpmnNodeResult.isDouble());
    assertFalse(actualCreateOrGetBpmnNodeResult.isFloat());
    assertFalse(actualCreateOrGetBpmnNodeResult.isFloatingPointNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isInt());
    assertFalse(actualCreateOrGetBpmnNodeResult.isIntegralNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isLong());
    assertFalse(actualCreateOrGetBpmnNodeResult.isMissingNode());
    assertFalse(actualCreateOrGetBpmnNodeResult.isNull());
    assertFalse(actualCreateOrGetBpmnNodeResult.isNumber());
    assertFalse(actualCreateOrGetBpmnNodeResult.isPojo());
    assertFalse(actualCreateOrGetBpmnNodeResult.isShort());
    assertFalse(actualCreateOrGetBpmnNodeResult.isTextual());
    assertFalse(actualCreateOrGetBpmnNodeResult.isValueNode());
    assertFalse(actualCreateOrGetBpmnNodeResult.iterator().hasNext());
    assertTrue(actualCreateOrGetBpmnNodeResult.isContainerNode());
    assertTrue(actualCreateOrGetBpmnNodeResult.isEmpty());
    assertTrue(actualCreateOrGetBpmnNodeResult.isObject());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getBpmnNode(ObjectNode)"})
  public void testGetBpmnNode_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    ObjectNode actualBpmnNode = dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(nc));

    // Assert
    assertNull(actualBpmnNode);
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode infoNode = new ObjectNode(nc);

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, infoNode.size());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty2() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

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
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty3() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode infoNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    infoNode.put("localization", new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", null, infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult3 instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertTrue(nextResult4.isNull());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
    verify(infoNode).putObject("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When empty string.
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty_givenTrue_whenEmptyString_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String,
   * ObjectNode)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectNode#has(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String,
   * String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBpmnServiceImpl.setLocalizationProperty(String, String, String, String, ObjectNode)"
  })
  public void testSetLocalizationProperty_thenCallsHas() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then calls {@link ObjectNode#putObject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetLocalizationNode(ObjectNode)"})
  public void testCreateOrGetLocalizationNode_givenFalse_thenCallsPutObject() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(false);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.putObject(Mockito.<String>any())).thenReturn(new ObjectNode(nc2));

    // Act
    ObjectNode actualCreateOrGetLocalizationNodeResult =
        dynamicBpmnServiceImpl.createOrGetLocalizationNode(infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
    verify(infoNode).putObject("localization");
    assertEquals(0, actualCreateOrGetLocalizationNodeResult.size());
    assertEquals(JsonNodeType.OBJECT, actualCreateOrGetLocalizationNodeResult.getNodeType());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isArray());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBigDecimal());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBigInteger());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBinary());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBoolean());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isDouble());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isFloat());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isFloatingPointNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isInt());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isIntegralNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isLong());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isMissingNode());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isNull());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isPojo());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isShort());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isTextual());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isValueNode());
    assertFalse(actualCreateOrGetLocalizationNodeResult.iterator().hasNext());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isContainerNode());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isEmpty());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isObject());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ObjectNode} {@link ObjectNode#has(String)} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.createOrGetLocalizationNode(ObjectNode)"})
  public void testCreateOrGetLocalizationNode_givenTrue_whenObjectNodeHasReturnTrue() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    ObjectNode infoNode = mock(ObjectNode.class);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ObjectNode(nc));
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);

    // Act
    ObjectNode actualCreateOrGetLocalizationNodeResult =
        dynamicBpmnServiceImpl.createOrGetLocalizationNode(infoNode);

    // Assert
    verify(infoNode).has("localization");
    verify(infoNode).get("localization");
    assertEquals(0, actualCreateOrGetLocalizationNodeResult.size());
    assertEquals(JsonNodeType.OBJECT, actualCreateOrGetLocalizationNodeResult.getNodeType());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isArray());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBigDecimal());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBigInteger());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBinary());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isBoolean());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isDouble());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isFloat());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isFloatingPointNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isInt());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isIntegralNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isLong());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isMissingNode());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isNull());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isNumber());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isPojo());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isShort());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isTextual());
    assertFalse(actualCreateOrGetLocalizationNodeResult.isValueNode());
    assertFalse(actualCreateOrGetLocalizationNodeResult.iterator().hasNext());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isContainerNode());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isEmpty());
    assertTrue(actualCreateOrGetLocalizationNodeResult.isObject());
  }

  /**
   * Test {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode DynamicBpmnServiceImpl.getLocalizationNode(ObjectNode)"})
  public void testGetLocalizationNode_thenReturnNull() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl =
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    ObjectNode actualLocalizationNode =
        dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(nc));

    // Assert
    assertNull(actualLocalizationNode);
  }
}
