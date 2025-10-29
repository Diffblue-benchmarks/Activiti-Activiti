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
   * Method under test: {@link QueryVariableValue#initialize(VariableTypes)}
   */
  @Test
  public void testInitialize() {
    // Arrange
    QueryVariableValue queryVariableValue = new QueryVariableValue("Name", null, QueryOperator.EQUALS, true);

    DefaultVariableTypes types = new DefaultVariableTypes();
    types.addType(new ByteArrayType());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> queryVariableValue.initialize(types));
  }

  /**
   * Method under test: {@link QueryVariableValue#getOperator()}
   */
  @Test
  public void testGetOperator() {
    // Arrange, Act and Assert
    assertEquals("EQUALS", (new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getOperator());
    assertEquals("EQUALS", (new QueryVariableValue("Name", JSONObject.NULL, null, true)).getOperator());
  }

  /**
   * Method under test: {@link QueryVariableValue#getTextValue()}
   */
  @Test
  public void testGetTextValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getTextValue());
  }

  /**
   * Method under test: {@link QueryVariableValue#getLongValue()}
   */
  @Test
  public void testGetLongValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getLongValue());
  }

  /**
   * Method under test: {@link QueryVariableValue#getDoubleValue()}
   */
  @Test
  public void testGetDoubleValue() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getDoubleValue());
  }

  /**
   * Method under test: {@link QueryVariableValue#getTextValue2()}
   */
  @Test
  public void testGetTextValue2() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getTextValue2());
  }

  /**
   * Method under test: {@link QueryVariableValue#getType()}
   */
  @Test
  public void testGetType() {
    // Arrange, Act and Assert
    assertNull((new QueryVariableValue("Name", JSONObject.NULL, QueryOperator.EQUALS, true)).getType());
  }

  /**
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
}
