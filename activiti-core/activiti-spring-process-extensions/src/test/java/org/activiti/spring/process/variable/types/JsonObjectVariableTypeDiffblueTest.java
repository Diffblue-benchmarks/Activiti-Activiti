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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonObjectVariableTypeDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link JsonObjectVariableType#JsonObjectVariableType(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#setObjectMapper(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#getObjectMapper()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    JsonObjectVariableType actualJsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ObjectMapper objectMapper = new ObjectMapper();
    actualJsonObjectVariableType.setObjectMapper(objectMapper);

    // Assert that nothing has changed
    assertSame(objectMapper, actualJsonObjectVariableType.getObjectMapper());
  }

  /**
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());
    ArrayList<ActivitiException> errors = new ArrayList<>();

    // Act
    jsonObjectVariableType.validate(1, errors);

    // Assert
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());

    ArrayList<ActivitiException> errors = new ArrayList<>();
    ActivitiException activitiException = new ActivitiException("An error occurred");
    errors.add(activitiException);

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(1, errors.size());
    assertSame(activitiException, errors.get(0));
  }

  /**
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    JsonObjectVariableType jsonObjectVariableType = new JsonObjectVariableType(new ObjectMapper());

    ArrayList<ActivitiException> errors = new ArrayList<>();
    ActivitiException activitiException = new ActivitiException("An error occurred");
    errors.add(activitiException);
    errors.add(new ActivitiException("An error occurred"));

    // Act
    jsonObjectVariableType.validate("Var", errors);

    // Assert
    assertEquals(2, errors.size());
    assertSame(activitiException, errors.get(0));
  }

  /**
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate5() throws JsonMappingException {
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
   * Method under test: {@link JsonObjectVariableType#validate(Object, List)}
   */
  @Test
  void testValidate6() throws JsonMappingException {
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
}
