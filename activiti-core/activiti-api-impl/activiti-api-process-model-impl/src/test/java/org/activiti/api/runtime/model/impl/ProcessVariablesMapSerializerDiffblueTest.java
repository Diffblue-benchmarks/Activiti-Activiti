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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.filter.FilteringGeneratorDelegate;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.filter.TokenFilterContext;
import com.fasterxml.jackson.core.io.ContentReference;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

class ProcessVariablesMapSerializerDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#ProcessVariablesMapSerializer(ConversionService)}
   */
  @Test
  void testNewProcessVariablesMapSerializer() {
    // Arrange and Act
    ProcessVariablesMapSerializer actualProcessVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    // Assert
    assertNull(actualProcessVariablesMapSerializer.getDelegatee());
    assertFalse(actualProcessVariablesMapSerializer.isUnwrappingSerializer());
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());
    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeObject(Mockito.<Object>any());
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(d).writeObject(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize2() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    processVariablesMap.put("foo", "42");
    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeObject(Mockito.<Object>any());
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(d).writeObject(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize3() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());
    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    doNothing().when(d).flush();
    when(d.getCodec()).thenReturn(new ObjectMapper());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true);

    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter).filterFinishObject();
    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.filterStartObject()).thenReturn(tokenFilter);
    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter2);
    FilteringGeneratorDelegate d3 = new FilteringGeneratorDelegate(d2, f, TokenFilter.Inclusion.ONLY_INCLUDE_ALL, true);

    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d3, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(d).flush();
    verify(d).getCodec();
    verify(d).writeEndObject();
    verify(d).writeStartObject();
    verify(tokenFilter).filterFinishObject();
    verify(tokenFilter2).filterStartObject();
    verify(tokenFilter).includeEmptyObject(eq(false));
    verify(f).includeRootValue(eq(0));
    JsonGenerator delegateResult = gen.delegate();
    assertTrue(delegateResult instanceof FilteringGeneratorDelegate);
    JsonStreamContext outputContext = gen.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    assertEquals(0, ((FilteringGeneratorDelegate) delegateResult).getMatchCount());
    assertEquals(1, outputContext.getEntryCount());
    assertTrue(outputContext.hasCurrentIndex());
    assertSame(d3, delegateResult);
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize4() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    processVariablesMap.put("", "42");
    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeString(Mockito.<String>any());
    doNothing().when(d).writeFieldName(Mockito.<String>any());
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    doNothing().when(d).flush();
    when(d.getCodec()).thenReturn(new ObjectMapper());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true);

    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeString(Mockito.<String>any())).thenReturn(true);
    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.includeEmptyObject(anyBoolean())).thenReturn(true);
    when(tokenFilter2.includeProperty(Mockito.<String>any())).thenReturn(tokenFilter);
    doNothing().when(tokenFilter2).filterFinishObject();
    TokenFilter tokenFilter3 = mock(TokenFilter.class);
    when(tokenFilter3.filterStartObject()).thenReturn(tokenFilter2);
    TokenFilter tokenFilter4 = mock(TokenFilter.class);
    when(tokenFilter4.includeProperty(Mockito.<String>any())).thenReturn(tokenFilter3);
    when(tokenFilter4.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter4).filterFinishObject();
    TokenFilter tokenFilter5 = mock(TokenFilter.class);
    when(tokenFilter5.filterStartObject()).thenReturn(tokenFilter4);
    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter5);
    FilteringGeneratorDelegate d3 = new FilteringGeneratorDelegate(d2, f, TokenFilter.Inclusion.ONLY_INCLUDE_ALL, true);

    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d3, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(d).flush();
    verify(d).getCodec();
    verify(d, atLeast(1)).writeEndObject();
    verify(d).writeFieldName(eq(""));
    verify(d, atLeast(1)).writeStartObject();
    verify(d, atLeast(1)).writeString(Mockito.<String>any());
    verify(tokenFilter4).filterFinishObject();
    verify(tokenFilter2).filterFinishObject();
    verify(tokenFilter5).filterStartObject();
    verify(tokenFilter3).filterStartObject();
    verify(tokenFilter2).includeEmptyObject(eq(true));
    verify(tokenFilter2, atLeast(1)).includeProperty(Mockito.<String>any());
    verify(tokenFilter4).includeProperty(eq(""));
    verify(f).includeRootValue(eq(0));
    verify(tokenFilter, atLeast(1)).includeString(Mockito.<String>any());
    JsonGenerator delegateResult = gen.delegate();
    assertTrue(delegateResult instanceof FilteringGeneratorDelegate);
    JsonStreamContext outputContext = gen.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    assertEquals(1, outputContext.getEntryCount());
    assertEquals(2, ((FilteringGeneratorDelegate) delegateResult).getMatchCount());
    assertTrue(outputContext.hasCurrentIndex());
    assertSame(d3, delegateResult);
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize5() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    processVariablesMap.put("", null);
    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeNull();
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    doNothing().when(d).flush();
    when(d.getCodec()).thenReturn(new ObjectMapper());
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true);

    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeEmptyObject(anyBoolean())).thenReturn(true);
    when(tokenFilter.includeProperty(Mockito.<String>any())).thenReturn(mock(TokenFilter.class));
    doNothing().when(tokenFilter).filterFinishObject();
    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.includeNull()).thenReturn(true);
    when(tokenFilter2.filterStartObject()).thenReturn(tokenFilter);
    TokenFilter tokenFilter3 = mock(TokenFilter.class);
    when(tokenFilter3.includeProperty(Mockito.<String>any())).thenReturn(tokenFilter2);
    when(tokenFilter3.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter3).filterFinishObject();
    TokenFilter tokenFilter4 = mock(TokenFilter.class);
    when(tokenFilter4.filterStartObject()).thenReturn(tokenFilter3);
    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter4);
    FilteringGeneratorDelegate d3 = new FilteringGeneratorDelegate(d2, f, TokenFilter.Inclusion.ONLY_INCLUDE_ALL, true);

    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d3, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(d).flush();
    verify(d).getCodec();
    verify(d).writeEndObject();
    verify(d).writeNull();
    verify(d).writeStartObject();
    verify(tokenFilter3).filterFinishObject();
    verify(tokenFilter4).filterStartObject();
    verify(tokenFilter3).includeEmptyObject(eq(true));
    verify(tokenFilter2).includeNull();
    verify(tokenFilter3).includeProperty(eq(""));
    verify(f).includeRootValue(eq(0));
    JsonGenerator delegateResult = gen.delegate();
    assertTrue(delegateResult instanceof FilteringGeneratorDelegate);
    JsonStreamContext outputContext = gen.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    assertEquals(1, outputContext.getEntryCount());
    assertEquals(1, ((FilteringGeneratorDelegate) delegateResult).getMatchCount());
    assertTrue(outputContext.hasCurrentIndex());
    assertSame(d3, delegateResult);
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize6() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    processVariablesMap.put("", null);
    JsonGenerator d = mock(JsonGenerator.class);
    when(d.getCodec()).thenReturn(null);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(
        new FilteringGeneratorDelegate(new JsonGeneratorDelegate(new JsonGeneratorDelegate(d), true),
            mock(TokenFilter.class), TokenFilter.Inclusion.ONLY_INCLUDE_ALL, true),
        true);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl()));
    verify(d).getCodec();
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapSerializer#serialize(ProcessVariablesMap, JsonGenerator, SerializerProvider)}
   */
  @Test
  void testSerialize7() throws IOException {
    // Arrange
    ProcessVariablesMapSerializer processVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    ProcessVariablesMap<String, Object> processVariablesMap = new ProcessVariablesMap<>();
    processVariablesMap.put("", null);
    new IOException("foo");
    TokenFilter tokenFilter = mock(TokenFilter.class);
    when(tokenFilter.includeEmptyObject(anyBoolean())).thenReturn(true);
    when(tokenFilter.includeProperty(Mockito.<String>any())).thenReturn(mock(TokenFilter.class));
    doNothing().when(tokenFilter).filterFinishObject();
    TokenFilter tokenFilter2 = mock(TokenFilter.class);
    when(tokenFilter2.includeNull()).thenReturn(true);
    when(tokenFilter2.filterStartObject()).thenReturn(tokenFilter);
    TokenFilter tokenFilter3 = mock(TokenFilter.class);
    when(tokenFilter3.includeProperty(Mockito.<String>any())).thenReturn(tokenFilter2);
    when(tokenFilter3.includeEmptyObject(anyBoolean())).thenReturn(true);
    doNothing().when(tokenFilter3).filterFinishObject();
    TokenFilter tokenFilter4 = mock(TokenFilter.class);
    when(tokenFilter4.filterStartObject()).thenReturn(tokenFilter3);
    TokenFilter f = mock(TokenFilter.class);
    when(f.includeRootValue(anyInt())).thenReturn(tokenFilter4);
    BufferRecycler br = new BufferRecycler();
    IOContext ctxt = new IOContext(br, ContentReference.redacted(), true);

    ObjectMapper codec = new ObjectMapper();
    ByteArrayOutputStream out = new ByteArrayOutputStream(1);
    FilteringGeneratorDelegate d = new FilteringGeneratorDelegate(
        new JsonGeneratorDelegate(new UTF8JsonGenerator(ctxt, 1, codec, out), true), f,
        TokenFilter.Inclusion.ONLY_INCLUDE_ALL, true);

    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d, true);

    // Act
    processVariablesMapSerializer.serialize(processVariablesMap, gen, new DefaultSerializerProvider.Impl());

    // Assert
    verify(tokenFilter3).filterFinishObject();
    verify(tokenFilter4).filterStartObject();
    verify(tokenFilter3).includeEmptyObject(eq(true));
    verify(tokenFilter2).includeNull();
    verify(tokenFilter3).includeProperty(eq(""));
    verify(f).includeRootValue(eq(0));
    JsonStreamContext outputContext = gen.getOutputContext();
    assertTrue(outputContext instanceof TokenFilterContext);
    assertEquals(1, outputContext.getEntryCount());
    assertTrue(outputContext.hasCurrentIndex());
    assertSame(d, gen.delegate());
    assertSame(out, gen.getOutputTarget());
  }
}
