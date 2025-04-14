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

@ContextConfiguration(classes = {StringToObjectValueConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToObjectValueConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToObjectValueConverter stringToObjectValueConverter;

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString() throws JsonProcessingException {
    // Arrange
    StringToObjectValueConverter stringToObjectValueConverter = new StringToObjectValueConverter(
        JsonMapper.builder().findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(stringToObjectValueConverter.convert(buildResult.writeValueAsString(new ObjectValue())).getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn Object and Object")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObject() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    StringToObjectValueConverter stringToObjectValueConverter = new StringToObjectValueConverter(
        builderResult.findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(stringToObjectValueConverter.convert(buildResult.writeValueAsString(new ObjectValue())).getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link ObjectValue}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn Object and ObjectValue")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObjectValue() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<ObjectValue> mixinSource = ObjectValue.class;
    builderResult.addMixIn(target, mixinSource);
    StringToObjectValueConverter stringToObjectValueConverter = new StringToObjectValueConverter(
        builderResult.findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(stringToObjectValueConverter.convert(buildResult.writeValueAsString(new ObjectValue())).getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Given builder addMixIn {@link ObjectValue} and {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn ObjectValue and Object")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectValueAndObject() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<ObjectValue> target = ObjectValue.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    StringToObjectValueConverter stringToObjectValueConverter = new StringToObjectValueConverter(
        builderResult.findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(stringToObjectValueConverter.convert(buildResult.writeValueAsString(new ObjectValue())).getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then calls {@link CacheProvider#forDeserializerCache(DeserializationConfig)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls forDeserializerCache(DeserializationConfig)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenCallsForDeserializerCache() throws JsonProcessingException {
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
    StringToObjectValueConverter stringToObjectValueConverter = new StringToObjectValueConverter(
        builderResult.findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act
    ObjectValue actualConvertResult = stringToObjectValueConverter
        .convert(buildResult.writeValueAsString(new ObjectValue()));

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertNull(actualConvertResult.getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return {@link ObjectValue#ObjectValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return ObjectValue()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenReturnObjectValue() throws JsonProcessingException {
    // Arrange
    ObjectValue objectValue = new ObjectValue();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<ObjectValue>>any())).thenReturn(objectValue);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    ObjectValue actualConvertResult = stringToObjectValueConverter
        .convert(objectMapper.writeValueAsString(new ObjectValue()));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertNull(actualConvertResult.getObject());
    assertSame(objectValue, actualConvertResult);
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'null'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenNull_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToObjectValueConverter(JsonMapper.builder().findAndAddModules().build())).convert(null));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@code Source}.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenSource_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToObjectValueConverter(JsonMapper.builder().findAndAddModules().build())).convert("Source"));
  }
}
