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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.Test;

class BigDecimalVariableTypeDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link BigDecimalVariableType}
   */
  @Test
  void testNewBigDecimalVariableType() {
    // Arrange, Act and Assert
    assertNull((new BigDecimalVariableType()).getName());
  }

  /**
   * Method under test: {@link BigDecimalVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue() throws ActivitiException {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new BigDecimalVariableType()).parseFromValue("Value"));
    assertThrows(ActivitiException.class, () -> (new BigDecimalVariableType()).parseFromValue(Double.NaN));
  }

  /**
   * Method under test: {@link BigDecimalVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue2() throws ActivitiException {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();
    BigDecimal bigDecimal = new BigDecimal("2.3");

    // Act and Assert
    assertSame(bigDecimal, bigDecimalVariableType.parseFromValue(bigDecimal));
  }

  /**
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    bigDecimalVariableType.validate("Var", errors);

    // Assert
    assertEquals(1, errors.size());
    ActivitiException getResult = errors.get(0);
    assertEquals("class java.lang.String is not a numeric type", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not a numeric type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    bigDecimalVariableType.validate(1, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();

    ArrayList<ActivitiException> errors = new ArrayList<>();
    ActivitiException activitiException = new ActivitiException("An error occurred");
    errors.add(activitiException);

    // Act
    bigDecimalVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not a numeric type", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not a numeric type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertSame(activitiException, errors.get(0));
  }
}
