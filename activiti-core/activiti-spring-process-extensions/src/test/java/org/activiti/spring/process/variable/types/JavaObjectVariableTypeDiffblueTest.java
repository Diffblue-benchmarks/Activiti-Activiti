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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JavaObjectVariableTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JavaObjectVariableType#JavaObjectVariableType(Class)}
   *   <li>{@link JavaObjectVariableType#setClazz(Class)}
   *   <li>{@link JavaObjectVariableType#getClazz()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    JavaObjectVariableType actualJavaObjectVariableType = new JavaObjectVariableType(clazz);
    Class<Object> clazz2 = Object.class;
    actualJavaObjectVariableType.setClazz(clazz2);
    Class actualClazz = actualJavaObjectVariableType.getClazz();

    // Assert that nothing has changed
    Class<Object> expectedClazz = Object.class;
    assertEquals(expectedClazz, actualClazz);
    assertSame(clazz2, actualClazz);
  }

  /**
   * Test {@link JavaObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when 'null'; then ArrayList() Empty")
  void testValidate_givenJavaLangObject_whenNull_thenArrayListEmpty() {
    // Arrange
    Class<Object> clazz = Object.class;
    JavaObjectVariableType javaObjectVariableType = new JavaObjectVariableType(clazz);
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    javaObjectVariableType.validate(null, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link JavaObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when '${UU}'; then ArrayList() Empty")
  void testValidate_givenJavaLangObject_whenUu_thenArrayListEmpty() {
    // Arrange
    Class<Object> clazz = Object.class;
    JavaObjectVariableType javaObjectVariableType = new JavaObjectVariableType(clazz);
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    javaObjectVariableType.validate("${UU}", errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link JavaObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code Var}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when 'Var'; then ArrayList() size is one")
  void testValidate_givenJavaLangObject_whenVar_thenArrayListSizeIsOne() {
    // Arrange
    Class<Object> clazz = Object.class;
    JavaObjectVariableType javaObjectVariableType = new JavaObjectVariableType(clazz);
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    javaObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(1, errors.size());
    ActivitiException getResult = errors.get(0);
    assertEquals("class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not assignable from class java.lang.Object", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link JavaObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); then ArrayList() size is two")
  void testValidate_thenArrayListSizeIsTwo() {
    // Arrange
    Class<Object> clazz = Object.class;
    JavaObjectVariableType javaObjectVariableType = new JavaObjectVariableType(clazz);

    ArrayList<ActivitiException> errors = new ArrayList<>();
    errors.add(new ActivitiException("An error occurred"));

    // Act
    javaObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not assignable from class java.lang.Object", getResult.getMessage());
    assertNull(getResult.getCause());
  }
}
