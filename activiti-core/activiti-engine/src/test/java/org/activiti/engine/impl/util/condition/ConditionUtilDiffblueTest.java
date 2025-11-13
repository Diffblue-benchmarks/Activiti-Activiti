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
package org.activiti.engine.impl.util.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ConditionUtilDiffblueTest {
  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   *   <li>Then return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenArrayNodeIsNullReturnFalse_thenReturnAsText() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).asText();
    verify(elementProperties).get("Property Name");
    assertEquals("As Text", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenArrayNodeIsNullReturnTrue_thenReturnNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(elementProperties).get("Property Name");
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get("Property Name");
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given Instance.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenInstance_thenReturnEmptyString() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get("Property Name");
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given Instance.
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return Instance.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenInstance_whenObjectNodeGetReturnInstance_thenReturnNull() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get("Property Name");
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_givenValueOfTen_thenReturn100() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get("Property Name");
    assertEquals("10.0", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_whenNull_thenReturn42() {
    // Arrange and Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", null);

    // Assert
    assertEquals("42", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ConditionUtil.getActiveValue(String, String, ObjectNode)"})
  public void testGetActiveValue_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturn42() {
    // Arrange
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    String actualActiveValue =
        ConditionUtil.getActiveValue("42", "Property Name", new ObjectNode(nc));

    // Assert
    assertEquals("42", actualActiveValue);
  }
}
