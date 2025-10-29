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
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue() {
    // Arrange, Act and Assert
    assertEquals("42", ConditionUtil.getActiveValue("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertEquals("42",
        ConditionUtil.getActiveValue("42", "Property Name", new ObjectNode(mock(JsonNodeFactory.class))));
    assertEquals("42", ConditionUtil.getActiveValue("42", "Property Name", null));
  }

  /**
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue2() {
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
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue3() {
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
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue4() {
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
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue5() {
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
   * Method under test:
   * {@link ConditionUtil#getActiveValue(String, String, ObjectNode)}
   */
  @Test
  public void testGetActiveValue6() {
    // Arrange
    ObjectNode elementProperties = mock(ObjectNode.class);
    when(elementProperties.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    String actualActiveValue = ConditionUtil.getActiveValue("42", "Property Name", elementProperties);

    // Assert
    verify(elementProperties).get(eq("Property Name"));
    assertNull(actualActiveValue);
  }
}
