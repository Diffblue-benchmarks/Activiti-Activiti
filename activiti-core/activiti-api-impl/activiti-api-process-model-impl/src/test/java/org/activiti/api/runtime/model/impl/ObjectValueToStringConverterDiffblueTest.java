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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Given builder addMixIn {@link Map} and {@link Object}.</li>
   *   <li>Then return {@code {"object":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; given builder addMixIn Map and Object; then return '{\"object\":null}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenBuilderAddMixInMapAndObject_thenReturnObjectNull() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Map> target = Map.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ObjectValueToStringConverter objectValueToStringConverter = new ObjectValueToStringConverter(
        builderResult.findAndAddModules().build());

    // Act and Assert
    assertEquals("{\"object\":null}", objectValueToStringConverter.convert(new ObjectValue()));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Map}.</li>
   *   <li>Then return {@code {"object":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; given builder addMixIn Object and Map; then return '{\"object\":null}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenBuilderAddMixInObjectAndMap_thenReturnObjectNull() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Map> mixinSource = Map.class;
    builderResult.addMixIn(target, mixinSource);
    ObjectValueToStringConverter objectValueToStringConverter = new ObjectValueToStringConverter(
        builderResult.findAndAddModules().build());

    // Act and Assert
    assertEquals("{\"object\":null}", objectValueToStringConverter.convert(new ObjectValue()));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; given builder addMixIn Object and Object")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenBuilderAddMixInObjectAndObject() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ObjectValueToStringConverter objectValueToStringConverter = new ObjectValueToStringConverter(
        builderResult.findAndAddModules().build());

    // Act and Assert
    assertEquals("{\"object\":null}", objectValueToStringConverter.convert(new ObjectValue()));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Then calls {@link CacheProvider#forDeserializerCache(DeserializationConfig)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then calls forDeserializerCache(DeserializationConfig)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_thenCallsForDeserializerCache() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ObjectValueToStringConverter objectValueToStringConverter = new ObjectValueToStringConverter(
        builderResult.findAndAddModules().build());

    // Act
    String actualConvertResult = objectValueToStringConverter.convert(new ObjectValue());

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("{\"object\":null}", actualConvertResult);
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
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
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Then return {@code {"object":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then return '{\"object\":null}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_thenReturnObjectNull() {
    // Arrange
    ObjectValueToStringConverter objectValueToStringConverter = new ObjectValueToStringConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act and Assert
    assertEquals("{\"object\":null}", objectValueToStringConverter.convert(new ObjectValue()));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
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
