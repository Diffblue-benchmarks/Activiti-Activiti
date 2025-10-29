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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue() {
    // Arrange, Act and Assert
    assertEquals("42", taskActivityBehavior.getActiveValue("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertEquals("42",
        taskActivityBehavior.getActiveValue("42", "Property Name", new ObjectNode(mock(JsonNodeFactory.class))));
    assertEquals("42", taskActivityBehavior.getActiveValue("42", "Property Name", null));
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue2() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue3() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue4() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue5() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue6() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualActiveValueList.isEmpty());
    assertSame(originalValues, actualActiveValueList);
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList2() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("foo");

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertEquals(1, actualActiveValueList.size());
    assertEquals("foo", actualActiveValueList.get(0));
    assertSame(originalValues, actualActiveValueList);
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList3() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();
    originalValues.add("42");
    originalValues.add("foo");

    // Act and Assert
    assertSame(originalValues, taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList4() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name",
        new ObjectNode(mock(JsonNodeFactory.class)));

    // Assert
    assertTrue(actualActiveValueList.isEmpty());
    assertSame(originalValues, actualActiveValueList);
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList5() {
    // Arrange
    ArrayList<String> originalValues = new ArrayList<>();

    // Act
    List<String> actualActiveValueList = taskActivityBehavior.getActiveValueList(originalValues, "Property Name", null);

    // Assert
    assertTrue(actualActiveValueList.isEmpty());
    assertSame(originalValues, actualActiveValueList);
  }

  /**
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList6() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList7() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList8() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList9() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList10() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList11() {
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
   * Method under test:
   * {@link TaskActivityBehavior#getActiveValueList(List, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValueList12() {
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
