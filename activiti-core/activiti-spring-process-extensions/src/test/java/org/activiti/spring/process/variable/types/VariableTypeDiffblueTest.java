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
package org.activiti.spring.process.variable.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.Test;

class VariableTypeDiffblueTest {
  /**
   * Method under test: {@link VariableType#getName()}
   */
  @Test
  void testGetName() {
    // Arrange, Act and Assert
    assertNull((new BigDecimalVariableType()).getName());
  }

  /**
   * Method under test: {@link VariableType#setName(String)}
   */
  @Test
  void testSetName() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();

    // Act
    bigDecimalVariableType.setName("Name");

    // Assert
    assertEquals("Name", bigDecimalVariableType.getName());
  }

  /**
   * Method under test: {@link VariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("Value", (new JavaObjectVariableType(clazz)).parseFromValue("Value"));
  }

  /**
   * Method under test: {@link VariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue2() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("${UU}",
        (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("${UU}"));
  }

  /**
   * Method under test: {@link VariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue3() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;
    Date date = mock(Date.class);

    // Act and Assert
    assertSame(date, (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue(date));
  }

  /**
   * Method under test: {@link VariableType#isExpression(Object)}
   */
  @Test
  void testIsExpression() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalVariableType()).isExpression("Var"));
    assertTrue((new BigDecimalVariableType()).isExpression("${UU}"));
    assertFalse((new BigDecimalVariableType()).isExpression(1));
    assertFalse((new BigDecimalVariableType()).isExpression(null));
  }
}
