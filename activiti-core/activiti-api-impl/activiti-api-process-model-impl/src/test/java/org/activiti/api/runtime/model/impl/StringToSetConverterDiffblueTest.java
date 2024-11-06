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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StringToSetConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToSetConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToSetConverter stringToSetConverter;

  /**
   * Test {@link StringToSetConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link ObjectMapper}
   * {@link ObjectMapper#readValue(String, JavaType)} return
   * {@link HashSet#HashSet()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToSetConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given ObjectMapper readValue(String, JavaType) return HashSet(); then return Empty")
  void testConvertWithString_givenObjectMapperReadValueReturnHashSet_thenReturnEmpty() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashSet<>());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Set<Object> actualConvertResult = stringToSetConverter.convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test {@link StringToSetConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then calls
   * {@link TypeFactory#constructParametricType(Class, Class[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToSetConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls constructParametricType(Class, Class[])")
  void testConvertWithString_thenCallsConstructParametricType() throws JsonProcessingException {
    // Arrange
    TypeFactory typeFactory = mock(TypeFactory.class);
    when(typeFactory.constructParametricType(Mockito.<Class<Object>>any(), isA(Class[].class)))
        .thenReturn(new PlaceholderForType(1));
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashSet<>());
    when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

    // Act
    Set<Object> actualConvertResult = stringToSetConverter.convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    verify(typeFactory).constructParametricType(isA(Class.class), isA(Class[].class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test {@link StringToSetConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToSetConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then throw RuntimeException")
  void testConvertWithString_thenThrowRuntimeException() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenThrow(new RuntimeException("foo"));
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToSetConverter.convert("Source"));
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
  }
}
