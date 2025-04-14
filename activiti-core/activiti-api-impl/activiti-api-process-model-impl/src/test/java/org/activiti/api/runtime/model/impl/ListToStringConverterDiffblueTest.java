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
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.util.ArrayList;
import java.util.List;
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

@ContextConfiguration(classes = {ListToStringConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ListToStringConverterDiffblueTest {
  @Autowired
  private ListToStringConverter listToStringConverter;

  @MockBean
  private ObjectMapper objectMapper;

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList2() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ListToStringConverter listToStringConverter = new ListToStringConverter(builderResult.findAndAddModules().build());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; given builder defaultLeniency 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenBuilderDefaultLeniencyTrue() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ListToStringConverter listToStringConverter = new ListToStringConverter(builderResult.findAndAddModules().build());

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("[]", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn42() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn422() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn423() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Then return {@code ["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[\"42\"]'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturn42() {
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
    ListToStringConverter listToStringConverter = new ListToStringConverter(builderResult.findAndAddModules().build());

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("[\"42\"]", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Then return {@code ["42","42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[\"42\",\"42\"]'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturn4242() {
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
    ListToStringConverter listToStringConverter = new ListToStringConverter(builderResult.findAndAddModules().build());

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("[\"42\",\"42\"]", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[]'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnLeftSquareBracketRightSquareBracket() {
    // Arrange
    ListToStringConverter listToStringConverter = new ListToStringConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act and Assert
    assertEquals("[]", listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[]'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnLeftSquareBracketRightSquareBracket2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    ListToStringConverter listToStringConverter = new ListToStringConverter(builderResult.findAndAddModules().build());

    // Act and Assert
    assertEquals("[]", listToStringConverter.convert(new ArrayList<>()));
  }
}
