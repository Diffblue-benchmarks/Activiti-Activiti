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
package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.filter.FilteringParserDelegate;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.types.VariableType;
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

@ContextConfiguration(classes = {ProcessExtensionResourceReader.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessExtensionResourceReaderDiffblueTest {
  @Autowired private Map<String, VariableType> map;

  @MockBean private ObjectMapper objectMapper;

  @Autowired private ProcessExtensionResourceReader processExtensionResourceReader;

  @MockBean private VariableType variableType;

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   *
   * <ul>
   *   <li>Then return not test {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return not test 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"
  })
  void testGetResourceNameSelector_thenReturnNotTestFoo() {
    // Arrange, Act and Assert
    assertFalse(processExtensionResourceReader.getResourceNameSelector().test("foo"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   *
   * <ul>
   *   <li>Then return test {@code -extensions.json}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return test '-extensions.json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"
  })
  void testGetResourceNameSelector_thenReturnTestExtensionsJson() {
    // Arrange, Act and Assert
    assertTrue(processExtensionResourceReader.getResourceNameSelector().test("-extensions.json"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link FilteringParserDelegate} {@link FilteringParserDelegate#currentName()} throw
   *       {@link IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given FilteringParserDelegate currentName() throw IOException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenFilteringParserDelegateCurrentNameThrowIOException() throws IOException {
    // Arrange
    FilteringParserDelegate filteringParserDelegate = mock(FilteringParserDelegate.class);
    when(filteringParserDelegate.currentName()).thenThrow(new IOException());
    doNothing().when(filteringParserDelegate).assignCurrentValue(Mockito.<Object>any());
    when(filteringParserDelegate.hasTokenId(anyInt())).thenReturn(true);
    when(filteringParserDelegate.isExpectedStartObjectToken()).thenReturn(true);
    when(filteringParserDelegate.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    when(filteringParserDelegate.nextToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(filteringParserDelegate.getReadCapabilities()).thenReturn(fromBitmaskResult);
    doNothing().when(filteringParserDelegate).close();

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any()))
        .thenReturn(filteringParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    Builder builderResult = JsonMapper.builder(streamFactory);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            processExtensionResourceReader.read(
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(filteringParserDelegate).currentName();
    verify(filteringParserDelegate).currentToken();
    verify(filteringParserDelegate).hasTokenId(5);
    verify(filteringParserDelegate).isExpectedStartObjectToken();
    verify(filteringParserDelegate).nextToken();
    verify(filteringParserDelegate).assignCurrentValue(isA(Object.class));
    verify(filteringParserDelegate).close();
    verify(filteringParserDelegate).getReadCapabilities();
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link FilteringParserDelegate} {@link FilteringParserDelegate#currentToken()}
   *       return {@code END_OBJECT}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given FilteringParserDelegate currentToken() return 'END_OBJECT'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenFilteringParserDelegateCurrentTokenReturnEndObject_thenReturnNull()
      throws IOException {
    // Arrange
    FilteringParserDelegate filteringParserDelegate = mock(FilteringParserDelegate.class);
    when(filteringParserDelegate.currentToken()).thenReturn(JsonToken.END_OBJECT);
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(filteringParserDelegate.getReadCapabilities()).thenReturn(fromBitmaskResult);
    doNothing().when(filteringParserDelegate).close();

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any()))
        .thenReturn(filteringParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(filteringParserDelegate).currentToken();
    verify(filteringParserDelegate).close();
    verify(filteringParserDelegate).getReadCapabilities();
    assertNull(actualReadResult);
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link FilteringParserDelegate} {@link FilteringParserDelegate#hasTokenId(int)}
   *       return {@code false}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given FilteringParserDelegate hasTokenId(int) return 'false'; then return Id is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenFilteringParserDelegateHasTokenIdReturnFalse_thenReturnIdIsNull()
      throws IOException {
    // Arrange
    FilteringParserDelegate filteringParserDelegate = mock(FilteringParserDelegate.class);
    when(filteringParserDelegate.hasTokenId(anyInt())).thenReturn(false);
    when(filteringParserDelegate.isExpectedStartObjectToken()).thenReturn(true);
    when(filteringParserDelegate.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    when(filteringParserDelegate.nextToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(filteringParserDelegate.getReadCapabilities()).thenReturn(fromBitmaskResult);
    doNothing().when(filteringParserDelegate).close();

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any()))
        .thenReturn(filteringParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(filteringParserDelegate).currentToken();
    verify(filteringParserDelegate).hasTokenId(5);
    verify(filteringParserDelegate).isExpectedStartObjectToken();
    verify(filteringParserDelegate).nextToken();
    verify(filteringParserDelegate).close();
    verify(filteringParserDelegate).getReadCapabilities();
    assertNull(actualReadResult.getId());
    assertTrue(actualReadResult.getAllExtensions().isEmpty());
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link Extension} (default constructor).
   *   <li>Then return AllExtensions is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given HashMap() 'foo' is Extension (default constructor); then return AllExtensions is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenHashMapFooIsExtension_thenReturnAllExtensionsIsHashMap() throws IOException {
    // Arrange
    HashMap<String, Extension> extensions = new HashMap<>();
    extensions.put("foo", new Extension());

    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(extensions);
    processExtensionModel.setId("42");
    when(objectMapper.enable(isA(MapperFeature[].class)))
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    when(objectMapper.readValue(
            Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenReturn(processExtensionModel);

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    assertSame(extensions, actualReadResult.getAllExtensions());
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link JsonFactory} {@link JsonFactory#createParser(InputStream)} throw {@link
   *       IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given JsonFactory createParser(InputStream) throw IOException(); then throw IOException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenJsonFactoryCreateParserThrowIOException_thenThrowIOException()
      throws IOException {
    // Arrange
    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any())).thenThrow(new IOException());
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            processExtensionResourceReader.read(
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#fields()} return {@link ArrayList#ArrayList()}
   *       iterator.
   *   <li>Then calls {@link JsonNode#asToken()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given JsonNode fields() return ArrayList() iterator; then calls asToken()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenJsonNodeFieldsReturnArrayListIterator_thenCallsAsToken() throws IOException {
    // Arrange
    JsonNode n = mock(JsonNode.class);

    ArrayList<Entry<String, JsonNode>> entryList = new ArrayList<>();
    when(n.fields()).thenReturn(entryList.iterator());
    when(n.asToken()).thenReturn(JsonToken.START_OBJECT);
    TreeTraversingParser d = new TreeTraversingParser(n);
    JsonParserDelegate jsonParserDelegate = new JsonParserDelegate(d);

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any())).thenReturn(jsonParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(n).asToken();
    verify(n).fields();
    assertNull(actualReadResult.getId());
    assertTrue(actualReadResult.getAllExtensions().isEmpty());
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link JsonParser} {@link JsonParser#currentName()} throw {@link
   *       IOException#IOException()}.
   *   <li>Then calls {@link JsonParser#assignCurrentValue(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given JsonParser currentName() throw IOException(); then calls assignCurrentValue(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenJsonParserCurrentNameThrowIOException_thenCallsAssignCurrentValue()
      throws IOException {
    // Arrange
    JsonParser d = mock(JsonParser.class);
    when(d.currentName()).thenThrow(new IOException());
    doNothing().when(d).assignCurrentValue(Mockito.<Object>any());
    when(d.hasTokenId(anyInt())).thenReturn(true);
    when(d.isExpectedStartObjectToken()).thenReturn(true);
    when(d.nextToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    doNothing().when(d).close();
    when(d.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(d.getReadCapabilities()).thenReturn(fromBitmaskResult);
    JsonParserDelegate jsonParserDelegate = new JsonParserDelegate(d);

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any())).thenReturn(jsonParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            processExtensionResourceReader.read(
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(d).assignCurrentValue(isA(Object.class));
    verify(d).close();
    verify(d).currentName();
    verify(d).currentToken();
    verify(d).getReadCapabilities();
    verify(d).hasTokenId(5);
    verify(d).isExpectedStartObjectToken();
    verify(d).nextToken();
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#readValue(InputStream, Class)} throw
   *       {@link IOException#IOException()}.
   *   <li>Then calls {@link ObjectMapper#enable(MapperFeature[])}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given ObjectMapper readValue(InputStream, Class) throw IOException(); then calls enable(MapperFeature[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenObjectMapperReadValueThrowIOException_thenCallsEnable() throws IOException {
    // Arrange
    when(objectMapper.enable(isA(MapperFeature[].class)))
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    when(objectMapper.readValue(
            Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenThrow(new IOException());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            processExtensionResourceReader.read(
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link TreeTraversingParser#TreeTraversingParser(JsonNode)} with n is Instance.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); given TreeTraversingParser(JsonNode) with n is Instance; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenTreeTraversingParserWithNIsInstance_thenReturnNull() throws IOException {
    // Arrange
    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any()))
        .thenReturn(new JsonParserDelegate(new TreeTraversingParser(NullNode.getInstance())));
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());
    JsonMapper objectMapper = JsonMapper.builder(streamFactory).findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    assertNull(actualReadResult);
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       DeserializationProblemHandler#handleUnknownProperty(DeserializationContext, JsonParser,
   *       JsonDeserializer, Object, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName(
      "Test read(InputStream); then calls handleUnknownProperty(DeserializationContext, JsonParser, JsonDeserializer, Object, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_thenCallsHandleUnknownProperty() throws IOException {
    // Arrange
    FilteringParserDelegate filteringParserDelegate = mock(FilteringParserDelegate.class);
    when(filteringParserDelegate.currentName()).thenReturn("Current Name");
    doNothing().when(filteringParserDelegate).assignCurrentValue(Mockito.<Object>any());
    when(filteringParserDelegate.hasTokenId(anyInt())).thenReturn(true);
    when(filteringParserDelegate.isExpectedStartObjectToken()).thenReturn(true);
    when(filteringParserDelegate.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    when(filteringParserDelegate.nextToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(filteringParserDelegate.getReadCapabilities()).thenReturn(fromBitmaskResult);
    doNothing().when(filteringParserDelegate).close();

    JsonFactory streamFactory = mock(JsonFactory.class);
    when(streamFactory.requiresPropertyOrdering()).thenReturn(true);
    when(streamFactory.createParser(Mockito.<InputStream>any()))
        .thenReturn(filteringParserDelegate);
    when(streamFactory.getCodec()).thenReturn(JsonMapper.builder().findAndAddModules().build());

    DeserializationProblemHandler h = mock(DeserializationProblemHandler.class);
    when(h.handleUnknownProperty(
            Mockito.<DeserializationContext>any(),
            Mockito.<JsonParser>any(),
            Mockito.<JsonDeserializer<?>>any(),
            Mockito.<Object>any(),
            Mockito.<String>any()))
        .thenThrow(new IOException());

    Builder builderResult = JsonMapper.builder(streamFactory);
    builderResult.addHandler(h);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            processExtensionResourceReader.read(
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(streamFactory).createParser(isA(InputStream.class));
    verify(streamFactory).getCodec();
    verify(streamFactory).requiresPropertyOrdering();
    verify(filteringParserDelegate).currentName();
    verify(filteringParserDelegate).currentToken();
    verify(filteringParserDelegate).hasTokenId(5);
    verify(filteringParserDelegate).isExpectedStartObjectToken();
    verify(filteringParserDelegate, atLeast(1)).nextToken();
    verify(filteringParserDelegate).assignCurrentValue(isA(Object.class));
    verify(filteringParserDelegate).close();
    verify(filteringParserDelegate).getReadCapabilities();
    verify(h)
        .handleUnknownProperty(
            isA(DeserializationContext.class),
            isA(JsonParser.class),
            isA(JsonDeserializer.class),
            isA(Object.class),
            eq("Current Name"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessExtensionModel} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); then return ProcessExtensionModel (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_thenReturnProcessExtensionModel() throws IOException {
    // Arrange
    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(new HashMap<>());
    processExtensionModel.setId("42");
    when(objectMapper.enable(isA(MapperFeature[].class)))
        .thenReturn(JsonMapper.builder().findAndAddModules().build());
    when(objectMapper.readValue(
            Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenReturn(processExtensionModel);

    // Act
    ProcessExtensionModel actualReadResult =
        processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    assertSame(processExtensionModel, actualReadResult);
  }
}
