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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.Test;

class DateVariableTypeDiffblueTest {
  /**
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    dateVariableType.validate("Var", errors);

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
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    dateVariableType.validate(null, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    dateVariableType.validate("${UU}", errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));

    ArrayList<ActivitiException> errors = new ArrayList<>();
    ActivitiException activitiException = new ActivitiException("An error occurred");
    errors.add(activitiException);

    // Act
    dateVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not assignable from class java.lang.Object", getResult.getMessage());
    assertNull(getResult.getCause());
    assertSame(activitiException, errors.get(0));
  }

  /**
   * Method under test:
   * {@link DateVariableType#DateVariableType(Class, DateFormatterProvider)}
   */
  @Test
  void testNewDateVariableType() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    DateVariableType actualDateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertNull(actualDateVariableType.getName());
    Class<Object> expectedClazz = Object.class;
    Class clazz2 = actualDateVariableType.getClazz();
    assertEquals(expectedClazz, clazz2);
    assertSame(clazz, clazz2);
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("Value"));
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue2() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(fromResult, dateVariableType.parseFromValue(fromResult));
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue3() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("${UU}"))).parseFromValue("Value"));
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue4() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("${UU}",
        (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("${UU}"));
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue5() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue(42));
  }

  /**
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  void testParseFromValue6() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;
    java.sql.Date date = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(date, (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue(date));
  }
}
