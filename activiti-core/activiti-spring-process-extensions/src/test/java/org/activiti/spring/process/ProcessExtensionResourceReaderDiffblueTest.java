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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessExtensionResourceReader.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessExtensionResourceReaderDiffblueTest {
  @Autowired
  private Map<String, VariableType> map;

  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private ProcessExtensionResourceReader processExtensionResourceReader;

  @MockBean
  private VariableType variableType;

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector()")
  void testGetResourceNameSelector() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act and Assert
    assertFalse(
        (new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector().test("foo"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   * <ul>
   *   <li>Then return not test {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return not test 'foo'")
  void testGetResourceNameSelector_thenReturnNotTestFoo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse(
        (new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector().test("foo"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   * <ul>
   *   <li>Then return test {@code -extensions.json}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return test '-extensions.json'")
  void testGetResourceNameSelector_thenReturnTestExtensionsJson() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertTrue((new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector()
        .test("-extensions.json"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link Extension} (default
   * constructor).</li>
   *   <li>Then return AllExtensions is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); given HashMap() 'foo' is Extension (default constructor); then return AllExtensions is HashMap()")
  void testRead_givenHashMapFooIsExtension_thenReturnAllExtensionsIsHashMap() throws IOException {
    // Arrange
    HashMap<String, Extension> extensions = new HashMap<>();
    extensions.put("foo", new Extension());

    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(extensions);
    processExtensionModel.setId("42");
    when(objectMapper.enable(isA(MapperFeature[].class))).thenReturn(objectMapper);
    when(objectMapper.readValue(Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenReturn(processExtensionModel);

    // Act
    ProcessExtensionModel actualReadResult = processExtensionResourceReader
        .read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    assertSame(extensions, actualReadResult.getAllExtensions());
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   * <ul>
   *   <li>Given {@link ObjectMapper}
   * {@link ObjectMapper#readValue(InputStream, Class)} throw
   * {@link IOException#IOException(String)} with {@code foo}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); given ObjectMapper readValue(InputStream, Class) throw IOException(String) with 'foo'; then throw IOException")
  void testRead_givenObjectMapperReadValueThrowIOExceptionWithFoo_thenThrowIOException() throws IOException {
    // Arrange
    when(objectMapper.enable(isA(MapperFeature[].class))).thenReturn(objectMapper);
    when(objectMapper.readValue(Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenThrow(new IOException("foo"));

    // Act and Assert
    assertThrows(IOException.class,
        () -> processExtensionResourceReader.read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   * <ul>
   *   <li>Then return {@link ProcessExtensionModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); then return ProcessExtensionModel (default constructor)")
  void testRead_thenReturnProcessExtensionModel() throws IOException {
    // Arrange
    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(new HashMap<>());
    processExtensionModel.setId("42");
    when(objectMapper.enable(isA(MapperFeature[].class))).thenReturn(objectMapper);
    when(objectMapper.readValue(Mockito.<InputStream>any(), Mockito.<Class<ProcessExtensionModel>>any()))
        .thenReturn(processExtensionModel);

    // Act
    ProcessExtensionModel actualReadResult = processExtensionResourceReader
        .read(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    verify(objectMapper).enable(isA(MapperFeature[].class));
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    assertSame(processExtensionModel, actualReadResult);
  }
}
