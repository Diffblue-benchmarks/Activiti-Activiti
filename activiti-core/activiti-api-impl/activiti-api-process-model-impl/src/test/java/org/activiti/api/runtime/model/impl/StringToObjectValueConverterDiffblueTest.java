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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StringToObjectValueConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToObjectValueConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToObjectValueConverter stringToObjectValueConverter;

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with
   * {@code String}.
   * <ul>
   *   <li>Then return Object is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return Object is 'null'")
  void testConvertWithString_thenReturnObjectIsNull() throws JsonProcessingException {
    // Arrange
    ObjectValue objectValue = new ObjectValue();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<ObjectValue>>any())).thenReturn(objectValue);

    ObjectMapper objectMapper2 = new ObjectMapper();

    // Act
    ObjectValue actualConvertResult = stringToObjectValueConverter
        .convert(objectMapper2.writeValueAsString(new ObjectValue()));

    // Assert
    verify(objectMapper).readValue(eq("{\"object\":null}"), isA(Class.class));
    assertNull(actualConvertResult.getObject());
    assertSame(objectValue, actualConvertResult);
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with
   * {@code String}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then throw RuntimeException")
  void testConvertWithString_thenThrowRuntimeException() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<ObjectValue>>any()))
        .thenThrow(new RuntimeException("foo"));

    ObjectMapper objectMapper2 = new ObjectMapper();

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> stringToObjectValueConverter.convert(objectMapper2.writeValueAsString(new ObjectValue())));
    verify(objectMapper).readValue(eq("{\"object\":null}"), isA(Class.class));
  }
}
