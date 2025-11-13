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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ObjectValueToStringConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private ObjectValueToStringConverter objectValueToStringConverter;

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue() throws JsonProcessingException, IllegalArgumentException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException());
    when(objectMapper.convertValue(Mockito.<Object>any(), eq(Map.class)))
        .thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> objectValueToStringConverter.convert(new ObjectValue()));
    verify(objectMapper).convertValue(isA(Object.class), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.annotationIntrospector(new JacksonAnnotationIntrospector());
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    ObjectValue source = mock(ObjectValue.class);
    when(source.getObject()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(source));
    verify(source).getObject();
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue3() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(
        DefaultCacheProvider.builder()
            .maxDeserializerCacheSize(3)
            .maxSerializerCacheSize(3)
            .maxTypeFactoryCacheSize(3)
            .build());
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    ObjectValue source = mock(ObjectValue.class);
    when(source.getObject()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(source));
    verify(source).getObject();
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue4() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new ObjectValueToStringConverter(objectMapper).convert(mock(ObjectValue.class)));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue5() {
    // Arrange
    AnnotationIntrospectorPair intr = mock(AnnotationIntrospectorPair.class);
    when(intr.findSerializer(Mockito.<Annotated>any())).thenReturn("Serializer");

    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(new PlaceholderForType(1));

    Builder builderResult = JsonMapper.builder();
    builderResult.typeFactory(f);
    builderResult.annotationIntrospector(intr);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new ObjectValueToStringConverter(objectMapper).convert(mock(ObjectValue.class)));
    verify(intr).findSerializer(isA(Annotated.class));
    verify(f, atLeast(1)).constructType(Mockito.<Type>any());
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; given builder addMixIn Object and Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenBuilderAddMixInObjectAndObject() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    ObjectValue source = mock(ObjectValue.class);
    when(source.getObject()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(source));
    verify(source).getObject();
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   *   <li>Then calls {@link ObjectValue#getObject()}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; given builder defaultLeniency 'true'; then calls getObject()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenBuilderDefaultLeniencyTrue_thenCallsGetObject() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    ObjectValue source = mock(ObjectValue.class);
    when(source.getObject()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(source));
    verify(source).getObject();
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#convertValue(Object, Class)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; given ObjectMapper convertValue(Object, Class) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenObjectMapperConvertValueThrowRuntimeException()
      throws IllegalArgumentException {
    // Arrange
    when(objectMapper.convertValue(Mockito.<Object>any(), eq(Map.class)))
        .thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> objectValueToStringConverter.convert(new ObjectValue()));
    verify(objectMapper).convertValue(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>Then calls {@link ObjectValue#getObject()}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; given RuntimeException(); then calls getObject()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_givenRuntimeException_thenCallsGetObject() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    ObjectValue source = mock(ObjectValue.class);
    when(source.getObject()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> objectValueToStringConverter.convert(source));
    verify(source).getObject();
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Then calls {@link AnnotationIntrospectorPair#findAutoDetectVisibility(AnnotatedClass,
   *       VisibilityChecker)}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; then calls findAutoDetectVisibility(AnnotatedClass, VisibilityChecker)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_thenCallsFindAutoDetectVisibility() {
    // Arrange
    AnnotationIntrospectorPair intr = mock(AnnotationIntrospectorPair.class);
    when(intr.findObjectReferenceInfo(Mockito.<Annotated>any(), Mockito.<ObjectIdInfo>any()))
        .thenReturn(ObjectIdInfo.empty());
    when(intr.isAnnotationBundle(Mockito.<Annotation>any())).thenReturn(true);
    when(intr.findObjectIdInfo(Mockito.<Annotated>any())).thenReturn(ObjectIdInfo.empty());
    Mockito.<VisibilityChecker<?>>when(
            intr.findAutoDetectVisibility(
                Mockito.<AnnotatedClass>any(), Mockito.<VisibilityChecker<?>>any()))
        .thenReturn(Std.allPublicInstance());
    when(intr.findSerializer(Mockito.<Annotated>any())).thenReturn("Serializer");

    Builder builderResult = JsonMapper.builder();
    builderResult.annotationIntrospector(intr);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new ObjectValueToStringConverter(objectMapper).convert(mock(ObjectValue.class)));
    verify(intr).findAutoDetectVisibility(isA(AnnotatedClass.class), isA(VisibilityChecker.class));
    verify(intr).findObjectIdInfo(isA(Annotated.class));
    verify(intr).findObjectReferenceInfo(isA(Annotated.class), isA(ObjectIdInfo.class));
    verify(intr).findSerializer(isA(Annotated.class));
    verify(intr, atLeast(1)).isAnnotationBundle(Mockito.<Annotation>any());
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayType#getRawClass()}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then calls getRawClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_thenCallsGetRawClass() {
    // Arrange
    AnnotationIntrospectorPair intr = mock(AnnotationIntrospectorPair.class);
    when(intr.findSerializer(Mockito.<Annotated>any())).thenReturn("Serializer");

    ArrayType arrayType = mock(ArrayType.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(arrayType.getRawClass()).thenReturn(forNameResult);

    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(arrayType);

    Builder builderResult = JsonMapper.builder();
    builderResult.typeFactory(f);
    builderResult.annotationIntrospector(intr);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new ObjectValueToStringConverter(objectMapper).convert(mock(ObjectValue.class)));
    verify(arrayType).getRawClass();
    verify(intr).findSerializer(isA(Annotated.class));
    verify(f, atLeast(1)).constructType(Mockito.<Type>any());
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName("Test convert(ObjectValue) with 'ObjectValue'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_thenReturn42()
      throws JsonProcessingException, IllegalArgumentException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
    when(objectMapper.convertValue(Mockito.<Object>any(), eq(Map.class)))
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
   *
   * <ul>
   *   <li>When {@link ObjectValue#ObjectValue(Object)} with {@code Object}.
   *   <li>Then return {@code {"object":"Object"}}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; when ObjectValue(Object) with 'Object'; then return '{\"object\":\"Object\"}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_whenObjectValueWithObject_thenReturnObjectObject() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    // Act
    String actualConvertResult = objectValueToStringConverter.convert(new ObjectValue("Object"));

    // Assert
    assertEquals("{\"object\":\"Object\"}", actualConvertResult);
  }

  /**
   * Test {@link ObjectValueToStringConverter#convert(ObjectValue)} with {@code ObjectValue}.
   *
   * <ul>
   *   <li>When {@link ObjectValue#ObjectValue()}.
   *   <li>Then return {@code {"object":null}}.
   * </ul>
   *
   * <p>Method under test: {@link ObjectValueToStringConverter#convert(ObjectValue)}
   */
  @Test
  @DisplayName(
      "Test convert(ObjectValue) with 'ObjectValue'; when ObjectValue(); then return '{\"object\":null}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ObjectValueToStringConverter.convert(ObjectValue)"})
  void testConvertWithObjectValue_whenObjectValue_thenReturnObjectNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ObjectValueToStringConverter objectValueToStringConverter =
        new ObjectValueToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("{\"object\":null}", objectValueToStringConverter.convert(new ObjectValue()));
  }
}
