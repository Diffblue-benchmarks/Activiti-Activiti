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
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  void testGetResourceNameSelector() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse(
        (new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector().test("foo"));
  }

  /**
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  void testGetResourceNameSelector2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act and Assert
    assertFalse(
        (new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector().test("foo"));
  }

  /**
   * Method under test:
   * {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  void testGetResourceNameSelector3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertTrue((new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector()
        .test("-extensions.json"));
  }

  /**
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  void testRead() throws IOException {
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

  /**
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  void testRead2() throws IOException {
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
    assertSame(processExtensionModel, actualReadResult);
  }

  /**
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  void testRead3() throws IOException {
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
}
