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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskActivityBehaviorDiffblueTest {
  @InjectMocks
  private TaskActivityBehavior taskActivityBehavior;

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue = taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    String actualActiveValue = taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenBigIntegerNodeWithVIsValueOfOne_thenReturn1() {
    // Arrange
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Act
    String actualActiveValue = taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertEquals("1", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenInstance_thenReturnEmptyString() {
    // Arrange
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    String actualActiveValue = taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return
   * Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenInstance_whenObjectNodeGetReturnInstance_thenReturnNull() {
    // Arrange
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    String actualActiveValue = taskActivityBehavior.getActiveValue("42", "Property Name", taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenNull_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", taskActivityBehavior.getActiveValue("42", "Property Name", null));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenObjectNodeWithNcIsJsonNodeFactory_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42",
        taskActivityBehavior.getActiveValue("42", "Property Name", new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", taskActivityBehavior.getActiveValue("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.isNull()).thenReturn(false);
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get(eq("Property Name"));
    assertEquals(1, actualActiveValueList.size());
    assertEquals("", actualActiveValueList.get(0));
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_given42_whenArrayListAdd42_thenReturnArrayList() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("42");
    originalValues.add("foo");

    // Act and Assert
    assertSame(originalValues, taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenArrayListAddInstance_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.isNull()).thenReturn(false);
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get(eq("Property Name"));
    assertEquals(1, actualActiveValueList.size());
    assertEquals("", actualActiveValueList.get(0));
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenArrayNodeIsNullReturnTrue_thenReturnNull() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValueList);
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValueList);
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValueList);
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenFoo_whenArrayListAddFoo_thenReturnArrayList() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("foo");

    // Act and Assert
    assertSame(originalValues, taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_givenInstance_whenObjectNodeGetReturnInstance() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(taskElementProperties).get(eq("Property Name"));
    assertNull(actualActiveValueList);
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_thenCallsIterator() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.isNull()).thenReturn(false);
    ObjectNode taskElementProperties = mock(ObjectNode.class);
    when(taskElementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        taskElementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(taskElementProperties).get(eq("Property Name"));
    assertTrue(actualActiveValueList.isEmpty());
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(taskActivityBehavior.getActiveValueList(new ArrayList<>(), "Property Name", null).isEmpty());
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_whenObjectNodeWithNcIsJsonNodeFactory_thenReturnEmpty() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    // Act and Assert
    assertTrue(taskActivityBehavior
        .getActiveValueList(originalValues, "Property Name", new ObjectNode(mock(JsonNodeFactory.class)))
        .isEmpty());
  }

  /**
   * Test
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    // Act and Assert
    assertTrue(taskActivityBehavior
        .getActiveValueList(originalValues, "Property Name", new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)))
        .isEmpty());
  }

  /**
   * Test new {@link TaskActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link TaskActivityBehavior}
   */
  @Test
  public void testNewTaskActivityBehavior() {
    // Arrange and Act
    TaskActivityBehavior actualTaskActivityBehavior = new TaskActivityBehavior();

    // Assert
    assertNull(actualTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualTaskActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
