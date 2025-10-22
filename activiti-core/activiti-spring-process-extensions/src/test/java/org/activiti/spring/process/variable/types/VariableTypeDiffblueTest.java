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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VariableTypeDiffblueTest {
  /**
   * Test {@link VariableType#getName()}.
   * <p>
   * Method under test: {@link VariableType#getName()}
   */
  @Test
  @DisplayName("Test getName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String VariableType.getName()"})
  void testGetName() {
    // Arrange, Act and Assert
    assertNull((new BigDecimalVariableType()).getName());
  }

  /**
   * Test {@link VariableType#setName(String)}.
   * <p>
   * Method under test: {@link VariableType#setName(String)}
   */
  @Test
  @DisplayName("Test setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VariableType.setName(String)"})
  void testSetName() {
    // Arrange
    BigDecimalVariableType bigDecimalVariableType = new BigDecimalVariableType();

    // Act
    bigDecimalVariableType.setName("Name");

    // Assert
    assertEquals("Name", bigDecimalVariableType.getName());
  }

  /**
   * Test {@link VariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>Given {@link JavaObjectVariableType#JavaObjectVariableType(Class)} with clazz is {@link Object}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); given JavaObjectVariableType(Class) with clazz is Object; then return 'Value'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object VariableType.parseFromValue(Object)"})
  void testParseFromValue_givenJavaObjectVariableTypeWithClazzIsObject_thenReturnValue() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("Value", (new JavaObjectVariableType(clazz)).parseFromValue("Value"));
  }

  /**
   * Test {@link VariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then return {@code ${UU}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when '${UU}'; then return '${UU}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object VariableType.parseFromValue(Object)"})
  void testParseFromValue_whenUu_thenReturnUu() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("${UU}",
        (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("${UU}"));
  }

  /**
   * Test {@link VariableType#isExpression(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#isExpression(Object)}
   */
  @Test
  @DisplayName("Test isExpression(Object); when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VariableType.isExpression(Object)"})
  void testIsExpression_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalVariableType()).isExpression(null));
  }

  /**
   * Test {@link VariableType#isExpression(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#isExpression(Object)}
   */
  @Test
  @DisplayName("Test isExpression(Object); when one; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VariableType.isExpression(Object)"})
  void testIsExpression_whenOne_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalVariableType()).isExpression(1));
  }

  /**
   * Test {@link VariableType#isExpression(Object)}.
   * <ul>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#isExpression(Object)}
   */
  @Test
  @DisplayName("Test isExpression(Object); when '${UU}'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VariableType.isExpression(Object)"})
  void testIsExpression_whenUu_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new BigDecimalVariableType()).isExpression("${UU}"));
  }

  /**
   * Test {@link VariableType#isExpression(Object)}.
   * <ul>
   *   <li>When {@code Var}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableType#isExpression(Object)}
   */
  @Test
  @DisplayName("Test isExpression(Object); when 'Var'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VariableType.isExpression(Object)"})
  void testIsExpression_whenVar_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalVariableType()).isExpression("Var"));
  }
}
