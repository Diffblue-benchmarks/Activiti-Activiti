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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonObjectVariableTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JsonObjectVariableType#JsonObjectVariableType(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#setObjectMapper(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#getObjectMapper()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    JsonObjectVariableType actualJsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ObjectMapper objectMapper = new ObjectMapper();
    actualJsonObjectVariableType.setObjectMapper(objectMapper);

    // Assert that nothing has changed
    assertSame(objectMapper, actualJsonObjectVariableType.getObjectMapper());
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List)")
  void testValidate() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any())).thenReturn(null);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(objectMapper);
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    assertEquals(1, errors.size());
    ActivitiException getResult = errors.get(0);
    assertEquals("class java.lang.String is not serializable as json", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not serializable as json", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls
   * {@link SerializerFactory#createSerializer(SerializerProvider, JavaType)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); given 'java.lang.Object'; when ArrayList(); then calls createSerializer(SerializerProvider, JavaType)")
  void testValidate_givenJavaLangObject_whenArrayList_thenCallsCreateSerializer() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(objectMapper);
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); then ArrayList() size is one")
  void testValidate_thenArrayListSizeIsOne() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());

    ArrayList<ActivitiException> errors = new ArrayList<>();
    errors.add(new ActivitiException("An error occurred"));

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(1, errors.size());
    ActivitiException getResult = errors.get(0);
    assertEquals("An error occurred", getResult.getLocalizedMessage());
    assertEquals("An error occurred", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); then ArrayList() size is two")
  void testValidate_thenArrayListSizeIsTwo() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());

    ArrayList<ActivitiException> errors = new ArrayList<>();
    errors.add(new ActivitiException("An error occurred"));
    errors.add(new ActivitiException("An error occurred"));

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    ActivitiException getResult = errors.get(0);
    assertEquals("An error occurred", getResult.getLocalizedMessage());
    assertEquals("An error occurred", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); when ArrayList(); then ArrayList() Empty")
  void testValidate_whenArrayList_thenArrayListEmpty() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link JsonObjectVariableType#validate(Object, List)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  @DisplayName("Test validate(Object, List); when one; then ArrayList() Empty")
  void testValidate_whenOne_thenArrayListEmpty() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate(1, errors);

    // Assert
    assertTrue(errors.isEmpty());
  }
}
