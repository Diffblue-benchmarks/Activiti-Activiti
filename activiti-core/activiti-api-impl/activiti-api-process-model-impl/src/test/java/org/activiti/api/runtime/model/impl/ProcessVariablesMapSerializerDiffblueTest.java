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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.filter.FilteringGeneratorDelegate;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.filter.TokenFilter.Inclusion;
import com.fasterxml.jackson.core.filter.TokenFilterContext;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

class ProcessVariablesMapSerializerDiffblueTest {
  /**
   * Test {@link ProcessVariablesMapSerializer#ProcessVariablesMapSerializer(ConversionService)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesMapSerializer#ProcessVariablesMapSerializer(ConversionService)}
   */
  @Test
  @DisplayName("Test new ProcessVariablesMapSerializer(ConversionService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessVariablesMapSerializer.<init>(ConversionService)"})
  void testNewProcessVariablesMapSerializer() {
    // Arrange and Act
    ProcessVariablesMapSerializer actualProcessVariablesMapSerializer =
        new ProcessVariablesMapSerializer(new ApplicationConversionService());

    // Assert
    assertNull(actualProcessVariablesMapSerializer.getDelegatee());
    assertFalse(actualProcessVariablesMapSerializer.isUnwrappingSerializer());
  }

  /**
   * Test {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator,
   * SerializerProvider)} with {@code ProcessVariablesMap}, {@code JsonGenerator}, {@code
   * SerializerProvider}.
   *
   * <p>Method under test: {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap,
   * JsonGenerator, SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider) with 'ProcessVariablesMap', 'JsonGenerator', 'SerializerProvider'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesMapSerializer.serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithProcessVariablesMapJsonGeneratorSerializerProvider() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer =
        new ProcessVariablesMapSerializer(new ApplicationConversionService());
    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeObject(Mockito.<Object>any());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new Impl());

    // Assert
    verify(d).writeObject(isA(Object.class));
  }

  /**
   * Test {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator,
   * SerializerProvider)} with {@code ProcessVariablesMap}, {@code JsonGenerator}, {@code
   * SerializerProvider}.
   *
   * <p>Method under test: {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap,
   * JsonGenerator, SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider) with 'ProcessVariablesMap', 'JsonGenerator', 'SerializerProvider'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesMapSerializer.serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithProcessVariablesMapJsonGeneratorSerializerProvider2() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer =
        new ProcessVariablesMapSerializer(new ApplicationConversionService());
    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    doNothing().when(d).flush();
    when(d.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate d3 = new JsonGeneratorDelegate(d2, true);

    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter).filterFinishObject();

    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.filterStartObject()).thenReturn(tokenFilter);

    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter2);

    FilteringGeneratorDelegate d4 =
        new FilteringGeneratorDelegate(d3, f, Inclusion.ONLY_INCLUDE_ALL, true);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d4, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new Impl());

    // Assert
    verify(d).flush();
    verify(d).getCodec();
    verify(d).writeEndObject();
    verify(d).writeStartObject();
    verify(tokenFilter).filterFinishObject();
    verify(tokenFilter2).filterStartObject();
    verify(tokenFilter).includeEmptyObject(false);
    verify(f).includeRootValue(0);
    JsonStreamContext outputContext = gen.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    assertEquals(1, outputContext.getEntryCount());
    assertTrue(outputContext.hasCurrentIndex());
  }

  /**
   * Test {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator,
   * SerializerProvider)} with {@code ProcessVariablesMap}, {@code JsonGenerator}, {@code
   * SerializerProvider}.
   *
   * <p>Method under test: {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap,
   * JsonGenerator, SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider) with 'ProcessVariablesMap', 'JsonGenerator', 'SerializerProvider'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesMapSerializer.serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithProcessVariablesMapJsonGeneratorSerializerProvider3() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer =
        new ProcessVariablesMapSerializer(new ApplicationConversionService());
    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    doNothing().when(d).flush();
    when(d.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate d3 = new JsonGeneratorDelegate(d2, true);

    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter).filterFinishObject();

    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.filterStartObject()).thenReturn(tokenFilter);

    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter2);

    FilteringGeneratorDelegate d4 =
        new FilteringGeneratorDelegate(d3, f, Inclusion.ONLY_INCLUDE_ALL, true);
    JsonGeneratorDelegate d5 = new JsonGeneratorDelegate(d4, true);

    TokenFilter tokenFilter3 = mock(TokenFilter.class);
    when(tokenFilter3.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter3).filterFinishObject();

    TokenFilter tokenFilter4 = mock(TokenFilter.class);
    when(tokenFilter4.filterStartObject()).thenReturn(tokenFilter3);

    TokenFilter f2 = mock(TokenFilter.class);
    when(f2.includeRootValue(anyInt())).thenReturn(tokenFilter4);

    FilteringGeneratorDelegate d6 =
        new FilteringGeneratorDelegate(d5, f2, Inclusion.ONLY_INCLUDE_ALL, true);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d6, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new Impl());

    // Assert
    verify(d).flush();
    verify(d).getCodec();
    verify(d).writeEndObject();
    verify(d).writeStartObject();
    verify(tokenFilter).filterFinishObject();
    verify(tokenFilter3).filterFinishObject();
    verify(tokenFilter2).filterStartObject();
    verify(tokenFilter4).filterStartObject();
    verify(tokenFilter).includeEmptyObject(false);
    verify(tokenFilter3).includeEmptyObject(false);
    verify(f).includeRootValue(0);
    verify(f2).includeRootValue(0);
    JsonGenerator delegateResult = gen.delegate();
    assertTrue(delegateResult instanceof FilteringGeneratorDelegate);
    JsonGenerator delegateResult2 = ((FilteringGeneratorDelegate) delegateResult).delegate();
    JsonStreamContext outputContext = delegateResult2.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    JsonStreamContext outputContext2 = gen.getOutputContext();
    assertTrue(outputContext2 instanceof TokenFilterContext);
    assertTrue(delegateResult2 instanceof JsonGeneratorDelegate);
    assertEquals(1, outputContext.getEntryCount());
    assertEquals(1, outputContext2.getEntryCount());
    assertTrue(outputContext.hasCurrentIndex());
    assertTrue(outputContext2.hasCurrentIndex());
  }
}
