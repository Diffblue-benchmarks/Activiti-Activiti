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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class StringToObjectValueConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private StringToObjectValueConverter stringToObjectValueConverter;

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.annotationIntrospector(new JacksonAnnotationIntrospector());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString2() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(
        DefaultCacheProvider.builder()
            .maxDeserializerCacheSize(3)
            .maxSerializerCacheSize(3)
            .maxTypeFactoryCacheSize(3)
            .build());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString3() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link ObjectValue}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn Object and ObjectValue")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObjectValue()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<ObjectValue> mixinSource = ObjectValue.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder addMixIn Object and Object; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObject_thenReturnObjectIsNull()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link ObjectValue} and {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn ObjectValue and Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectValueAndObject()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<ObjectValue> target = ObjectValue.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder defaultLeniency 'true'; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultLeniencyTrue_thenReturnObjectIsNull()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultTypingIsStdTypeResolverBuilder()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ObjectMapper} {@link ObjectMapper#readValue(String, Class)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given 'null'; when ObjectMapper readValue(String, Class) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenNull_whenObjectMapperReadValueThrowRuntimeException()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), eq(ObjectValue.class)))
        .thenReturn(new ObjectValue());
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
    when(objectMapper.readValue(Mockito.<String>any(), eq(ObjectValue.class)))
        .thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(
                objectMapper.writeValueAsString(new ObjectValue())));
    verify(objectMapper, atLeast(1)).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenReturnObjectIsNull() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link ObjectValue#ObjectValue()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return ObjectValue()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenReturnObjectValue() throws JsonProcessingException {
    // Arrange
    ObjectValue objectValue = new ObjectValue();
    when(objectMapper.readValue(Mockito.<String>any(), eq(ObjectValue.class)))
        .thenReturn(objectValue);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    ObjectValue actualConvertResult =
        stringToObjectValueConverter.convert(objectMapper.writeValueAsString(new ObjectValue()));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertNull(actualConvertResult.getObject());
    assertSame(objectValue, actualConvertResult);
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'null'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenNull_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToObjectValueConverter(objectMapper).convert(null));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code Source}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenSource_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new StringToObjectValueConverter(objectMapper).convert("Source"));
  }
}
