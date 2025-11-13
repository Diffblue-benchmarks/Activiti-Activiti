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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TaskActivityBehaviorDiffblueTest {
  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   *   <li>Then return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenArrayNodeIsNullReturnFalse_thenReturnAsText() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue =
        taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).asText();
    verify(taskElementProperties).get("Property Name");
    assertEquals("As Text", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenArrayNodeIsNullReturnTrue_thenReturnNull() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue =
        taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenValueOfTen_thenReturn100() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    String actualActiveValue =
        taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertEquals("10.0", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_thenReturnEmptyString() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    String actualActiveValue =
        taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_whenNull_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", new TaskActivityBehavior().getActiveValue("42", "Property Name", null));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TaskActivityBehavior.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturn42() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertEquals(
        "42", taskActivityBehavior.getActiveValue("42", "Property Name", new ObjectNode(nc)));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_given42_whenArrayListAdd42_thenReturnNull() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("42");
    originalValues.add("foo");

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValueList);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.
   *   <li>Then return first is empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenArrayListAddInstance_thenReturnFirstIsEmptyString() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isArray()).thenReturn(true);

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get("Property Name");
    assertEquals(1, actualActiveValueList.size());
    assertEquals("", actualActiveValueList.get(0));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then return first is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenArrayListAddValueOfTen_thenReturnFirstIs100() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isArray()).thenReturn(true);

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get("Property Name");
    assertEquals(1, actualActiveValueList.size());
    assertEquals("10.0", actualActiveValueList.get(0));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValueList);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValueList);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenFoo_whenArrayListAddFoo_thenReturnNull() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("foo");

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValueList);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_givenValueOfTen_whenObjectNodeGetReturnValueOfTen() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get("Property Name");
    assertNull(actualActiveValueList);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_thenReturnEmpty() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isArray()).thenReturn(true);

    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList =
        taskActivityBehavior.getActiveValueList(
            originalValues, "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get("Property Name");
    assertTrue(actualActiveValueList.isEmpty());
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_whenNull_thenReturnEmpty() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();

    // Act and Assert
    assertTrue(
        taskActivityBehavior
            .getActiveValueList(new ArrayList<>(), "Property Name", null)
            .isEmpty());
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskActivityBehavior.getActiveValueList(List, String, ObjectNode)"})
  public void testGetActiveValueList_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange
    TaskActivityBehavior taskActivityBehavior = new TaskActivityBehavior();
    ArrayList<String> originalValues = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertTrue(
        taskActivityBehavior
            .getActiveValueList(originalValues, "Property Name", new ObjectNode(nc))
            .isEmpty());
  }

  /**
   * Test new {@link TaskActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link TaskActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskActivityBehavior.<init>()"})
  public void testNewTaskActivityBehavior() {
    // Arrange and Act
    TaskActivityBehavior actualTaskActivityBehavior = new TaskActivityBehavior();

    // Assert
    assertNull(actualTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualTaskActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
