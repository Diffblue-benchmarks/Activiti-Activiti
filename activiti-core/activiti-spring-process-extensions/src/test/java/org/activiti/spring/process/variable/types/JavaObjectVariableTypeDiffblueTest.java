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
import org.junit.jupiter.api.Test;

class JavaObjectVariableTypeDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link JavaObjectVariableType#JavaObjectVariableType(Class)}
   *   <li>{@link JavaObjectVariableType#setClazz(Class)}
   *   <li>{@link JavaObjectVariableType#getClazz()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate() {
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
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate2() {
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
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate3() {
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
   * Method under test: {@link JavaObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    Class<Object> clazz = Object.class;
    JavaObjectVariableType javaObjectVariableType = new JavaObjectVariableType(clazz);

    ArrayList<ActivitiException> errors = new ArrayList<>();
    ActivitiException activitiException = new ActivitiException("An error occurred");
    errors.add(activitiException);

    // Act
    javaObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not assignable from class java.lang.Object", getResult.getMessage());
    assertNull(getResult.getCause());
    assertSame(activitiException, errors.get(0));
  }
}
