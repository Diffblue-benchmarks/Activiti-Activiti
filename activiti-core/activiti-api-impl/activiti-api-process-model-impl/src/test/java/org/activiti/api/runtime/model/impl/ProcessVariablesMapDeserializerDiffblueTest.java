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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonParser.NumberTypeFP;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

class ProcessVariablesMapDeserializerDiffblueTest {
  /**
   * Test {@link
   * ProcessVariablesMapDeserializer#ProcessVariablesMapDeserializer(ConversionService)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesMapDeserializer#ProcessVariablesMapDeserializer(ConversionService)}
   */
  @Test
  @DisplayName("Test new ProcessVariablesMapDeserializer(ConversionService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessVariablesMapDeserializer.<init>(ConversionService)"})
  void testNewProcessVariablesMapDeserializer() {
    // Arrange and Act
    ProcessVariablesMapDeserializer actualProcessVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());

    // Assert
    assertNull(actualProcessVariablesMapDeserializer.getDelegatee());
    assertNull(actualProcessVariablesMapDeserializer.getObjectIdReader());
    assertNull(actualProcessVariablesMapDeserializer.getEmptyValue());
    assertNull(actualProcessVariablesMapDeserializer.getKnownPropertyNames());
    assertNull(actualProcessVariablesMapDeserializer.getNullValue());
    assertEquals(
        AccessPattern.CONSTANT, actualProcessVariablesMapDeserializer.getNullAccessPattern());
    assertEquals(
        AccessPattern.DYNAMIC, actualProcessVariablesMapDeserializer.getEmptyAccessPattern());
    assertFalse(actualProcessVariablesMapDeserializer.isCachable());
  }

  /**
   * Test {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   *
   * <p>Method under test: {@link ProcessVariablesMapDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName("Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessVariablesMap ProcessVariablesMapDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithJpCtxt() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());
    DoubleNode n = DoubleNode.valueOf(10.0d);
    JsonMapper codec = JsonMapper.builder().findAndAddModules().build();

    TreeTraversingParser jp = new TreeTraversingParser(n, codec);

    // Act
    processVariablesMapDeserializer.deserialize(
        jp, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    assertEquals(10, jp.getIntValue());
    assertEquals(10.0d, jp.getDoubleValue());
    assertEquals(10.0d, ((Double) jp.getNumberValueDeferred()).doubleValue());
    assertEquals(10.0d, jp.getNumberValueExact().doubleValue());
    assertEquals(10.0d, jp.getNumberValue().doubleValue());
    assertEquals(10.0f, jp.getFloatValue());
    assertEquals(10L, jp.getLongValue());
    assertEquals((short) 10, jp.getShortValue());
    assertEquals(NumberType.DOUBLE, jp.getNumberType());
    assertEquals(NumberTypeFP.DOUBLE64, jp.getNumberTypeFP());
    assertEquals(JsonToken.VALUE_NUMBER_FLOAT, jp.getLastClearedToken());
    assertEquals(new BigDecimal("10.0"), jp.getDecimalValue());
    assertEquals('\n', jp.getByteValue());
  }

  /**
   * Test {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   *
   * <ul>
   *   <li>Given {@link JsonToken#END_ARRAY}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesMapDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName(
      "Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'; given END_ARRAY")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessVariablesMap ProcessVariablesMapDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithJpCtxt_givenEnd_array() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());

    JsonParser jp = mock(JsonParser.class);
    doNothing().when(jp).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(jp.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(jp.currentToken()).thenReturn(JsonToken.END_ARRAY);
    when(jp.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ProcessVariablesMap<String, Object> actualDeserializeResult =
        processVariablesMapDeserializer.deserialize(
            jp, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(jp).clearCurrentToken();
    verify(jp, atLeast(1)).currentToken();
    verify(jp).getCodec();
    verify(jp).getReadCapabilities();
    assertTrue(actualDeserializeResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   *
   * <ul>
   *   <li>Given {@link JsonToken#END_OBJECT}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesMapDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName(
      "Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'; given END_OBJECT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessVariablesMap ProcessVariablesMapDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithJpCtxt_givenEnd_object() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());

    JsonParser jp = mock(JsonParser.class);
    doNothing().when(jp).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(jp.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(jp.currentToken()).thenReturn(JsonToken.END_OBJECT);
    when(jp.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ProcessVariablesMap<String, Object> actualDeserializeResult =
        processVariablesMapDeserializer.deserialize(
            jp, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(jp).clearCurrentToken();
    verify(jp, atLeast(1)).currentToken();
    verify(jp).getCodec();
    verify(jp).getReadCapabilities();
    assertTrue(actualDeserializeResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   *
   * <ul>
   *   <li>Then calls {@link JsonParser#nextToken()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesMapDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName(
      "Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'; then calls nextToken()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessVariablesMap ProcessVariablesMapDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithJpCtxt_thenCallsNextToken() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());

    JsonParser jp = mock(JsonParser.class);
    doNothing().when(jp).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(jp.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(jp.nextToken()).thenReturn(JsonToken.VALUE_NULL);
    when(jp.currentToken()).thenReturn(null);
    when(jp.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ProcessVariablesMap<String, Object> actualDeserializeResult =
        processVariablesMapDeserializer.deserialize(
            jp, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(jp).clearCurrentToken();
    verify(jp, atLeast(1)).currentToken();
    verify(jp).getCodec();
    verify(jp).getReadCapabilities();
    verify(jp, atLeast(1)).nextToken();
    assertTrue(actualDeserializeResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   *
   * <ul>
   *   <li>When {@link JsonParser} {@link JsonParser#currentToken()} return {@link
   *       JsonToken#VALUE_NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesMapDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName(
      "Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'; when JsonParser currentToken() return VALUE_NULL")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessVariablesMap ProcessVariablesMapDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithJpCtxt_whenJsonParserCurrentTokenReturnValue_null() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer =
        new ProcessVariablesMapDeserializer(new ApplicationConversionService());

    JsonParser jp = mock(JsonParser.class);
    doNothing().when(jp).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(jp.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(jp.currentToken()).thenReturn(JsonToken.VALUE_NULL);
    when(jp.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    // Act
    ProcessVariablesMap<String, Object> actualDeserializeResult =
        processVariablesMapDeserializer.deserialize(
            jp, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(jp).clearCurrentToken();
    verify(jp, atLeast(1)).currentToken();
    verify(jp).getCodec();
    verify(jp).getReadCapabilities();
    assertTrue(actualDeserializeResult.isEmpty());
  }
}
