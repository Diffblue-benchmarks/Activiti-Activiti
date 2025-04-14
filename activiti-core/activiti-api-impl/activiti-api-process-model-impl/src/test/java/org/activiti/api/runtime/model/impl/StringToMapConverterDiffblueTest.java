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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

@ContextConfiguration(classes = {StringToMapConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToMapConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToMapConverter stringToMapConverter;

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(JsonMapper.builder().findAndAddModules().build())).convert("Source"));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString2() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(builderResult.findAndAddModules().build())).convert("Source"));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link LRUMap} {@link LRUMap#get(Object)} return {@code null}.</li>
   *   <li>When {@code Source}.</li>
   *   <li>Then calls {@link LRUMap#putIfAbsent(Object, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given LRUMap get(Object) return 'null'; when 'Source'; then calls putIfAbsent(Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_givenLRUMapGetReturnNull_whenSource_thenCallsPutIfAbsent() {
    // Arrange
    LRUMap<Object, JavaType> lruMap = mock(LRUMap.class);
    when(lruMap.get(Mockito.<Object>any())).thenReturn(null);
    when(lruMap.putIfAbsent(Mockito.<Object>any(), Mockito.<JavaType>any())).thenReturn(new PlaceholderForType(1));
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(lruMap);
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(builderResult.findAndAddModules().build())).convert("Source"));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(lruMap).get(isA(Object.class));
    verify(lruMap).putIfAbsent(isA(Object.class), isA(JavaType.class));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link LRUMap} {@link LRUMap#get(Object)} return {@link PlaceholderForType#PlaceholderForType(int)} with ordinal is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given LRUMap get(Object) return PlaceholderForType(int) with ordinal is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_givenLRUMapGetReturnPlaceholderForTypeWithOrdinalIsOne() {
    // Arrange
    LRUMap<Object, JavaType> lruMap = mock(LRUMap.class);
    when(lruMap.get(Mockito.<Object>any())).thenReturn(new PlaceholderForType(1));
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(lruMap);
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(builderResult.findAndAddModules().build())).convert("Source"));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(lruMap).get(isA(Object.class));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link LRUMap} {@link LRUMap#get(Object)} throw {@link RuntimeException#RuntimeException(String)} with {@code foo}.</li>
   *   <li>Then calls {@link LRUMap#get(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given LRUMap get(Object) throw RuntimeException(String) with 'foo'; then calls get(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_givenLRUMapGetThrowRuntimeExceptionWithFoo_thenCallsGet() {
    // Arrange
    LRUMap<Object, JavaType> lruMap = mock(LRUMap.class);
    when(lruMap.get(Mockito.<Object>any())).thenThrow(new RuntimeException("foo"));
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(lruMap);
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(builderResult.findAndAddModules().build())).convert("Source"));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(lruMap).get(isA(Object.class));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#readValue(String, JavaType)} return {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given ObjectMapper readValue(String, JavaType) return HashMap(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_givenObjectMapperReadValueReturnHashMap_thenReturnEmpty() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashMap<>());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Map<String, Object> actualConvertResult = stringToMapConverter.convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#readValue(String, JavaType)} throw {@link RuntimeException#RuntimeException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given ObjectMapper readValue(String, JavaType) throw RuntimeException(String) with 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_givenObjectMapperReadValueThrowRuntimeExceptionWithFoo() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenThrow(new RuntimeException("foo"));
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToMapConverter.convert("Source"));
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then calls {@link TypeFactory#constructParametricType(Class, Class[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls constructParametricType(Class, Class[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_thenCallsConstructParametricType() throws JsonProcessingException {
    // Arrange
    TypeFactory typeFactory = mock(TypeFactory.class);
    when(typeFactory.constructParametricType(Mockito.<Class<Object>>any(), isA(Class[].class)))
        .thenReturn(new PlaceholderForType(1));
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashMap<>());
    when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

    // Act
    Map<String, Object> actualConvertResult = stringToMapConverter.convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    verify(typeFactory).constructParametricType(isA(Class.class), isA(Class[].class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_when42() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(JsonMapper.builder().findAndAddModules().build())).convert("42"));
  }

  /**
   * Test {@link StringToMapConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToMapConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map StringToMapConverter.convert(String)"})
  void testConvertWithString_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToMapConverter(JsonMapper.builder().findAndAddModules().build())).convert(""));
  }
}
