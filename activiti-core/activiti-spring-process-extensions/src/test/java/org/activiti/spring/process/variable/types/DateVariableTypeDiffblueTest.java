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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateVariableTypeDiffblueTest {
  /**
   * Test {@link DateVariableType#DateVariableType(Class, DateFormatterProvider)}.
   * <p>
   * Method under test:
   * {@link DateVariableType#DateVariableType(Class, DateFormatterProvider)}
   */
  @Test
  @DisplayName("Test new DateVariableType(Class, DateFormatterProvider)")
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
   * Test {@link DateVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when 'null'; then ArrayList() Empty")
  void testValidate_givenJavaLangObject_whenNull_thenArrayListEmpty() {
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
   * Test {@link DateVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when '${UU}'; then ArrayList() Empty")
  void testValidate_givenJavaLangObject_whenUu_thenArrayListEmpty() {
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
   * Test {@link DateVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code Var}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when 'Var'; then ArrayList() size is one")
  void testValidate_givenJavaLangObject_whenVar_thenArrayListSizeIsOne() {
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
   * Test {@link DateVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); then ArrayList() size is two")
  void testValidate_thenArrayListSizeIsTwo() {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));

    ArrayList<ActivitiException> errors = new ArrayList<>();
    errors.add(new ActivitiException("An error occurred"));

    // Act
    dateVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(1);
    assertEquals("class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not assignable from class java.lang.Object", getResult.getMessage());
    assertNull(getResult.getCause());
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object)")
  void testParseFromValue() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType = new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(fromResult, dateVariableType.parseFromValue(fromResult));
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>Given {@link DateFormatterProvider#DateFormatterProvider(String)} with
   * dateFormatPattern is {@code ${UU}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); given DateFormatterProvider(String) with dateFormatPattern is '${UU}'")
  void testParseFromValue_givenDateFormatterProviderWithDateFormatPatternIsUu() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("${UU}"))).parseFromValue("Value"));
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@link Date}.</li>
   *   <li>Then return {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when Date; then return Date")
  void testParseFromValue_whenDate_thenReturnDate() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;
    java.sql.Date date = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(date, (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue(date));
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when forty-two; then throw ActivitiException")
  void testParseFromValue_whenFortyTwo_thenThrowActivitiException() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue(42));
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then return {@code ${UU}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when '${UU}'; then return '${UU}'")
  void testParseFromValue_whenUu_thenReturnUu() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("${UU}",
        (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("${UU}"));
  }

  /**
   * Test {@link DateVariableType#parseFromValue(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateVariableType#parseFromValue(Object)}
   */
  @Test
  @DisplayName("Test parseFromValue(Object); when 'Value'; then throw ActivitiException")
  void testParseFromValue_whenValue_thenThrowActivitiException() throws ActivitiException {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"))).parseFromValue("Value"));
  }
}
