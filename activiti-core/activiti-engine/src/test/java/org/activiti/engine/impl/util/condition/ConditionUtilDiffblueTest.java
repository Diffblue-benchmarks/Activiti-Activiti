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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConditionUtilDiffblueTest {
  @InjectMocks
  private ConditionUtil conditionUtil;

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(arrayNode).isNull();
    verify(elementProperties).get(eq("Property Name"));
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get(eq("Property Name"));
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenBigIntegerNodeWithVIsValueOfOne_thenReturn1() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get(eq("Property Name"));
    assertEquals("1", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenInstance_thenReturnEmptyString() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get(eq("Property Name"));
    assertEquals("", actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ObjectNode} {@link ObjectNode#get(String)} return
   * Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_givenInstance_whenObjectNodeGetReturnInstance_thenReturnNull() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get(eq("Property Name"));
    assertNull(actualActiveValue);
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenNull_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", ConditionUtil.getActiveValue("42", "Property Name", null));
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * {@link JsonNodeFactory}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenObjectNodeWithNcIsJsonNodeFactory_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42",
        ConditionUtil.getActiveValue("42", "Property Name", new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Test {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue_whenObjectNodeWithNcIsWithExactBigDecimalsTrue_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", ConditionUtil.getActiveValue("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }
}
