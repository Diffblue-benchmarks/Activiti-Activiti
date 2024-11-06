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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.ByteArrayType;
import org.activiti.engine.impl.variable.DefaultVariableTypes;
import org.activiti.engine.impl.variable.VariableTypes;
import org.junit.Test;

public class QueryVariableValueDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link QueryVariableValue#QueryVariableValue(String, Object, QueryOperator, boolean)}
   *   <li>{@link QueryVariableValue#getName()}
   *   <li>{@link QueryVariableValue#isLocal()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    QueryVariableValue actualQueryVariableValue = new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS,
        true);
    String actualName = actualQueryVariableValue.getName();

    // Assert
    assertEquals("Name", actualName);
    assertTrue(actualQueryVariableValue.isLocal());
  }

  /**
   * Test {@link QueryVariableValue#initialize(VariableTypes)}.
   * <ul>
   *   <li>Given {@link ByteArrayType} (default constructor).</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryVariableValue#initialize(VariableTypes)}
   */
  @Test
  public void testInitialize_givenByteArrayType_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    QueryVariableValue queryVariableValue = new QueryVariableValue("Name", null, QueryOperator.EQUALS, true);

    DefaultVariableTypes types = new DefaultVariableTypes();
    types.addType(new ByteArrayType());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> queryVariableValue.initialize(types));
  }

  /**
   * Test {@link QueryVariableValue#getOperator()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getOperator()}
   */
  @Test
  public void testGetOperator() {
    // Arrange, Act and Assert
    assertEquals("EQUALS", (new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getOperator());
    assertEquals("EQUALS", (new QueryVariableValue("Name", JSONObject.NULL, null, true)).getOperator());
  }

  /**
   * Test {@link QueryVariableValue#getTextValue()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getTextValue()}
   */
  @Test
  public void testGetTextValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getTextValue());
  }

  /**
   * Test {@link QueryVariableValue#getLongValue()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getLongValue()}
   */
  @Test
  public void testGetLongValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getLongValue());
  }

  /**
   * Test {@link QueryVariableValue#getDoubleValue()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getDoubleValue()}
   */
  @Test
  public void testGetDoubleValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getDoubleValue());
  }

  /**
   * Test {@link QueryVariableValue#getTextValue2()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getTextValue2()}
   */
  @Test
  public void testGetTextValue2() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getTextValue2());
  }

  /**
   * Test {@link QueryVariableValue#getType()}.
   * <p>
   * Method under test: {@link QueryVariableValue#getType()}
   */
  @Test
  public void testGetType() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getType());
  }
}
