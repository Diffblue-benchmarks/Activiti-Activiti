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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ObjectValueToStringConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ObjectValueToStringConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private ObjectValueToStringConverter objectValueToStringConverter;

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with
   * {@code ObjectValue}.
   * <ul>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then return '42'")
  void testConvertWithObjectValue_thenReturn42() throws JsonProcessingException, IllegalArgumentException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
    when(objectMapper.convertValue(Mockito.<Object>any(), Mockito.<Class<Map<Object, Object>>>any()))
        .thenReturn(new HashMap<>());

    // Act
    String actualConvertResult = objectValueToStringConverter.convert(new ObjectValue());

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with
   * {@code ObjectValue}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then throw RuntimeException")
  void testConvertWithObjectValue_thenThrowRuntimeException() throws JsonProcessingException, IllegalArgumentException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any()))
        .thenThrow(new RuntimeException(ProcessVariablesMapTypeRegistry.OBJECT_TYPE_KEY));
    when(objectMapper.convertValue(Mockito.<Object>any(), Mockito.<Class<Map<Object, Object>>>any()))
        .thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(new ObjectValue()));
    verify(objectMapper).convertValue(isA(Object.class), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }
}
