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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

class ProcessVariablesMapDeserializerDiffblueTest {
  /**
   * Test
   * {@link ProcessVariablesMapDeserializer#ProcessVariablesMapDeserializer(ConversionService)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapDeserializer#ProcessVariablesMapDeserializer(ConversionService)}
   */
  @Test
  @DisplayName("Test new ProcessVariablesMapDeserializer(ConversionService)")
  void testNewProcessVariablesMapDeserializer() {
    // Arrange and Act
    ProcessVariablesMapDeserializer actualProcessVariablesMapDeserializer = new ProcessVariablesMapDeserializer(
        new ApplicationConversionService());

    // Assert
    assertNull(actualProcessVariablesMapDeserializer.getDelegatee());
    assertNull(actualProcessVariablesMapDeserializer.getObjectIdReader());
    assertNull(actualProcessVariablesMapDeserializer.getEmptyValue());
    assertNull(actualProcessVariablesMapDeserializer.getKnownPropertyNames());
    assertNull(actualProcessVariablesMapDeserializer.getNullValue());
    assertEquals(AccessPattern.CONSTANT, actualProcessVariablesMapDeserializer.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualProcessVariablesMapDeserializer.getEmptyAccessPattern());
    assertFalse(actualProcessVariablesMapDeserializer.isCachable());
  }

  /**
   * Test
   * {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code jp}, {@code ctxt}.
   * <ul>
   *   <li>Given {@code END_OBJECT}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapDeserializer#deserialize(JsonParser, DeserializationContext)}
   */
  @Test
  @DisplayName("Test deserialize(JsonParser, DeserializationContext) with 'jp', 'ctxt'; given 'END_OBJECT'; then return Empty")
  void testDeserializeWithJpCtxt_givenEndObject_thenReturnEmpty() throws IOException {
    // Arrange
    ProcessVariablesMapDeserializer processVariablesMapDeserializer = new ProcessVariablesMapDeserializer(
        new ApplicationConversionService());
    JsonParserSequence jp = mock(JsonParserSequence.class);
    doNothing().when(jp).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(jp.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(jp.currentToken()).thenReturn(JsonToken.END_OBJECT);
    when(jp.getCodec()).thenReturn(new ObjectMapper());

    // Act
    ProcessVariablesMap<String, Object> actualDeserializeResult = processVariablesMapDeserializer.deserialize(jp,
        new DefaultDeserializationContext.Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(jp).clearCurrentToken();
    verify(jp, atLeast(1)).currentToken();
    verify(jp).getCodec();
    verify(jp).getReadCapabilities();
    assertTrue(actualDeserializeResult.isEmpty());
  }
}
