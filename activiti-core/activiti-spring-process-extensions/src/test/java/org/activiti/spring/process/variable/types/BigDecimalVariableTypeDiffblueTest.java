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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BigDecimalVariableTypeDiffblueTest {
  /**
   * Test {@link BigDecimalVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when BigDecimal(String) with '2.3'; then return BigDecimal(String) with '2.3'")
  void testParseFromValue_whenBigDecimalWith23_thenReturnBigDecimalWith23() throws ActivitiException {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();
    BigDecimal bigDecimal = new BigDecimal("2.3");

    // Act and Assert
    assertSame(bigDecimal, bigDecimalVariableType.parseFromValue(bigDecimal));
  }

  /**
   * Test {@link BigDecimalVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when NaN; then throw ActivitiException")
  void testParseFromValue_whenNaN_thenThrowActivitiException() throws ActivitiException {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new BigDecimalVariableType()).parseFromValue(Double.NaN));
  }

  /**
   * Test {@link BigDecimalVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when 'Value'; then throw ActivitiException")
  void testParseFromValue_whenValue_thenThrowActivitiException() throws ActivitiException {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new BigDecimalVariableType()).parseFromValue("Value"));
  }

  /**
   * Test {@link BigDecimalVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); then ArrayList() size is two")
  void testValidate_thenArrayListSizeIsTwo() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();

    ArrayList<ActivitiException> errors = new ArrayList<>();
    errors.add(new ActivitiException("An error occurred"));

    // Act
    bigDecimalVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not a numeric type", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not a numeric type", getResult.getMessage());
    assertNull(getResult.getCause());
  }

  /**
   * Test {@link BigDecimalVariableType#validate(Object, List)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); when one; then ArrayList() Empty")
  void testValidate_whenOne_thenArrayListEmpty() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    bigDecimalVariableType.validate(1, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link BigDecimalVariableType#validate(Object, List)}.
   * <ul>
   *   <li>When {@code Var}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); when 'Var'; then ArrayList() size is one")
  void testValidate_whenVar_thenArrayListSizeIsOne() {
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
   * Test new {@link BigDecimalVariableType} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BigDecimalVariableType}
   */
  @Test
  @DisplayName("Test new BigDecimalVariableType (default constructor)")
  void testNewBigDecimalVariableType() {
    // Arrange, Act and Assert
    assertNull((new BigDecimalVariableType()).getName());
  }
}
