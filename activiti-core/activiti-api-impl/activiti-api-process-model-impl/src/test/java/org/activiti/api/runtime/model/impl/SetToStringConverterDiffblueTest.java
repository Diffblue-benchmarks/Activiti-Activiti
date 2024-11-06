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

@ContextConfiguration(classes = {SetToStringConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SetToStringConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private SetToStringConverter setToStringConverter;

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; given '42'; when HashSet() add '42'; then return '42'")
  void testConvertWithSet_given42_whenHashSetAdd42_thenReturn42() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    HashSet<Object> source = new HashSet<>();
    source.add("42");

    // Act
    String actualConvertResult = setToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link HashSet#HashSet()} add two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; given two; when HashSet() add two; then return '42'")
  void testConvertWithSet_givenTwo_whenHashSetAddTwo_thenReturn42() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    HashSet<Object> source = new HashSet<>();
    source.add("42");
    source.add(2);

    // Act
    String actualConvertResult = setToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; then throw RuntimeException")
  void testConvertWithSet_thenThrowRuntimeException() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> setToStringConverter.convert(new HashSet<>()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; when HashSet(); then return '42'")
  void testConvertWithSet_whenHashSet_thenReturn42() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = setToStringConverter.convert(new HashSet<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }
}
