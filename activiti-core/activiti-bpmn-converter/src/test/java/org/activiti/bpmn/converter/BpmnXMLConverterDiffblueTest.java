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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.util.StreamReaderDelegate;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.DataStore;
import org.activiti.bpmn.model.Error;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

class BpmnXMLConverterDiffblueTest {
  /**
   * Test {@link BpmnXMLConverter#validateModel(InputStreamProvider)} with {@code
   * inputStreamProvider}.
   *
   * <ul>
   *   <li>Then calls {@link InputStreamProvider#getInputStream()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#validateModel(InputStreamProvider)}
   */
  @Test
  @DisplayName(
      "Test validateModel(InputStreamProvider) with 'inputStreamProvider'; then calls getInputStream()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.validateModel(InputStreamProvider)"})
  void testValidateModelWithInputStreamProvider_thenCallsGetInputStream() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenThrow(new XMLException("An error occurred"));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.validateModel(inputStreamProvider));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#validateModel(InputStreamProvider)} with {@code
   * inputStreamProvider}.
   *
   * <ul>
   *   <li>Then calls {@link ByteArrayClassLoader#getResource(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#validateModel(InputStreamProvider)}
   */
  @Test
  @DisplayName(
      "Test validateModel(InputStreamProvider) with 'inputStreamProvider'; then calls getResource(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.validateModel(InputStreamProvider)"})
  void testValidateModelWithInputStreamProvider_thenCallsGetResource() throws Exception {
    // Arrange
    ByteArrayClassLoader classloader = mock(ByteArrayClassLoader.class);
    when(classloader.getResource(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));

    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    bpmnXMLConverter.setClassloader(classloader);

    // Act and Assert
    assertThrows(
        XMLException.class, () -> bpmnXMLConverter.validateModel(mock(InputStreamProvider.class)));
    verify(classloader).getResource("org/activiti/impl/bpmn/parser/BPMN20.xsd");
  }

  /**
   * Test {@link BpmnXMLConverter#validateModel(XMLStreamReader)} with {@code xmlStreamReader}.
   *
   * <ul>
   *   <li>Then throw {@link XMLException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#validateModel(XMLStreamReader)}
   */
  @Test
  @DisplayName(
      "Test validateModel(XMLStreamReader) with 'xmlStreamReader'; then throw XMLException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.validateModel(XMLStreamReader)"})
  void testValidateModelWithXmlStreamReader_thenThrowXMLException() throws Exception {
    // Arrange
    ByteArrayClassLoader classloader = mock(ByteArrayClassLoader.class);
    when(classloader.getResource(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));

    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    bpmnXMLConverter.setClassloader(classloader);

    // Act and Assert
    assertThrows(
        XMLException.class, () -> bpmnXMLConverter.validateModel(new StreamReaderDelegate()));
    verify(classloader).getResource("org/activiti/impl/bpmn/parser/BPMN20.xsd");
  }

  /**
   * Test {@link BpmnXMLConverter#createSchema()}.
   *
   * <ul>
   *   <li>Given {@link BpmnXMLConverter} (default constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createSchema()}
   */
  @Test
  @DisplayName(
      "Test createSchema(); given BpmnXMLConverter (default constructor); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"javax.xml.validation.Schema BpmnXMLConverter.createSchema()"})
  void testCreateSchema_givenBpmnXMLConverter_thenDoesNotThrow() throws SAXException {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> new BpmnXMLConverter().createSchema());
  }

  /**
   * Test {@link BpmnXMLConverter#createSchema()}.
   *
   * <ul>
   *   <li>Then throw {@link XMLException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createSchema()}
   */
  @Test
  @DisplayName("Test createSchema(); then throw XMLException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"javax.xml.validation.Schema BpmnXMLConverter.createSchema()"})
  void testCreateSchema_thenThrowXMLException() throws SAXException {
    // Arrange
    ByteArrayClassLoader classloader = mock(ByteArrayClassLoader.class);
    when(classloader.getResource(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));

    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    bpmnXMLConverter.setClassloader(classloader);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.createSchema());
    verify(classloader).getResource("org/activiti/impl/bpmn/parser/BPMN20.xsd");
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenThrow(new XMLException("An error occurred"));

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml3()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt()))
        .thenThrow(new XMLException("An error occurred"));
    doThrow(new XMLException("An error occurred")).when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml4()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.available()).thenReturn(1);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(1);
    doNothing().when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true));
    verify(dataInputStream, atLeast(1)).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream, atLeast(1)).available();
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml5()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(0);
    doNothing().when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)} with
   * {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml6()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read()).thenReturn(1);
    when(dataInputStream.available()).thenReturn(1);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(1);
    doNothing().when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, false));
    verify(dataInputStream, atLeast(1)).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream, atLeast(1)).available();
    verify(dataInputStream, atLeast(1)).close();
    verify(dataInputStream, atLeast(1)).read();
    verify(inputStreamProvider, atLeast(1)).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true, "UTF-8"));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenThrow(new XMLException("An error occurred"));

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true, "UTF-8"));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding3()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt()))
        .thenThrow(new XMLException("An error occurred"));
    doThrow(new XMLException("An error occurred")).when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true, "UTF-8"));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding4()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.available()).thenReturn(1);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(1);
    doNothing().when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true, "UTF-8"));
    verify(dataInputStream, atLeast(1)).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream, atLeast(1)).available();
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding5()
      throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(0);
    doNothing().when(dataInputStream).close();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true, "UTF-8"));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema}, {@code enableSafeBpmnXml}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean,
   * boolean, String)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnModel BpmnXMLConverter.convertToBpmnModel(InputStreamProvider, boolean, boolean, String)"
  })
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding6() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(mock(DataInputStream.class));

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.convertToBpmnModel(
                inputStreamProvider, false, true, "javax.xml.stream.isReplacingEntityReferences"));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName("Test processFlowElements(Collection, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new SequenceFlow("", ""));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act
    bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener());

    // Assert
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName("Test processFlowElements(Collection, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElements()).thenThrow(new XMLException("An error occurred"));

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName("Test processFlowElements(Collection, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements3() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("not empty");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElement(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(adhocSubProcess.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
    verify(adhocSubProcess).getFlowElement("not empty");
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName("Test processFlowElements(Collection, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements4() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("not empty");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBoundaryEvents()).thenThrow(new XMLException("An error occurred"));

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(adhocSubProcess2.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess2);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
    verify(adhocSubProcess).getBoundaryEvents();
    verify(adhocSubProcess2).getFlowElement("not empty");
    verify(adhocSubProcess2).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getBoundaryEvents()} return {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given AdhocSubProcess getBoundaryEvents() return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenAdhocSubProcessGetBoundaryEventsReturnArrayList() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("not empty");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBoundaryEvents()).thenReturn(new ArrayList<>());

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(adhocSubProcess2.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess2);

    // Act
    bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener());

    // Assert
    verify(adhocSubProcess).getBoundaryEvents();
    verify(adhocSubProcess2).getFlowElement("not empty");
    verify(adhocSubProcess2).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getFlowElement(String)} return
   *       {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given AdhocSubProcess getFlowElement(String) return AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenAdhocSubProcessGetFlowElementReturnAdhocSubProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("not empty");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(adhocSubProcess.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act
    bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener());

    // Assert
    verify(adhocSubProcess).getFlowElement("not empty");
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given AdhocSubProcess (default constructor); when ArrayList() add AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenAdhocSubProcess_whenArrayListAddAdhocSubProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());

    // Act and Assert
    assertDoesNotThrow(
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given AdhocSubProcess (default constructor); when ArrayList() add AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenAdhocSubProcess_whenArrayListAddAdhocSubProcess2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    flowElementList.add(new AdhocSubProcess());

    // Act and Assert
    assertDoesNotThrow(
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) AttachedToRefId is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given BoundaryEvent (default constructor) AttachedToRefId is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenBoundaryEventAttachedToRefIdIsEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElements()).thenReturn(flowElementSet);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act
    bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener());

    // Assert
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given BoundaryEvent (default constructor); when ArrayList() add BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenBoundaryEvent_whenArrayListAddBoundaryEvent() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new BoundaryEvent());

    // Act and Assert
    assertDoesNotThrow(
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given 'null'; when ArrayList() add 'null'; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenNull_whenArrayListAddNull_thenDoesNotThrow() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(null);

    // Act and Assert
    assertDoesNotThrow(
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); when ArrayList(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_whenArrayList_thenDoesNotThrow() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    ArrayList<FlowElement> flowElementList = new ArrayList<>();

    // Act and Assert
    assertDoesNotThrow(
        () -> bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   *   <li>When {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'null'; when 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsNull_whenNotEmpty() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

    Process scope = new Process();
    scope.addFlowElement(element);
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("not empty", scope));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code Scope}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'Scope'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsScope_whenEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Scope");

    Process scope = new Process();
    scope.addFlowElement(element);
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("", scope));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code Scope}.
   *   <li>When {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'Scope'; when 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsScope_whenNotEmpty() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Scope");

    Process scope = new Process();
    scope.addFlowElement(element);
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("not empty", scope));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code Scope}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'Scope'; when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsScope_whenNull_thenReturnNull() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Scope");

    Process scope = new Process();
    scope.addFlowElement(element);
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope(null, scope));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); then return AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_thenReturnAdhocSubProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    Process scope = new Process();
    scope.addFlowElement(element);
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertSame(element, bpmnXMLConverter.getFlowNodeFromScope("not empty", scope));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName("Test getFlowNodeFromScope(String, BaseElement); when '42'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_when42_thenReturnNull() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("42", new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link SubProcess} (default constructor) FlowElementMap is {@link
   *       HashMap#HashMap()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); when SubProcess (default constructor) FlowElementMap is HashMap(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_whenSubProcessFlowElementMapIsHashMap_thenReturnNull() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    SubProcess scope = new SubProcess();
    scope.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("not empty", scope));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = new Pool();
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenThrow(new XMLException("An error occurred"));
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenThrow(new XMLException("An error occurred"));
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(pool).getId();
    verify(pool).setId("42");
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool).getName();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel3() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new AdhocSubProcess());

    Process process = mock(Process.class);
    when(process.getId()).thenThrow(new XMLException("An error occurred"));
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel4() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new AdhocSubProcess());

    Process process = mock(Process.class);
    when(process.getName()).thenThrow(new XMLException("An error occurred"));
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElements();
    verify(process).getName();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel5() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenThrow(new XMLException("An error occurred"));
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel6() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowElement(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel7() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel8() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenThrow(new XMLException("An error occurred"));

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel9() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getId()).thenThrow(new XMLException("An error occurred"));

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel10() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenThrow(new XMLException("An error occurred"));

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel11() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenThrow(new XMLException("An error occurred"));
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel12() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenThrow(new XMLException("An error occurred"));
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getLocationMap()).thenThrow(new XMLException("An error occurred"));
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowElement(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("  ");
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding3() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding4() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("  ");
    verify(model).getGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding5() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenThrow(new XMLException("An error occurred"));

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getName()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given AdhocSubProcess getName() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenAdhocSubProcessGetNameReturnEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1035, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1016]);
    assertEquals('2', actualConvertToXMLResult[1021]);
    assertEquals(':', actualConvertToXMLResult[1022]);
    assertEquals('<', actualConvertToXMLResult[1015]);
    assertEquals('>', actualConvertToXMLResult[1013]);
    assertEquals('>', actualConvertToXMLResult[1034]);
    assertEquals('\n', actualConvertToXMLResult[1014]);
    assertEquals('a', actualConvertToXMLResult[1011]);
    assertEquals('b', actualConvertToXMLResult[1017]);
    assertEquals('d', actualConvertToXMLResult[Double.MAX_EXPONENT]);
    assertEquals('e', actualConvertToXMLResult[1024]);
    assertEquals('f', actualConvertToXMLResult[1025]);
    assertEquals('i', actualConvertToXMLResult[1026]);
    assertEquals('i', actualConvertToXMLResult[1028]);
    assertEquals('i', actualConvertToXMLResult[1030]);
    assertEquals('m', actualConvertToXMLResult[1012]);
    assertEquals('m', actualConvertToXMLResult[1019]);
    assertEquals('n', actualConvertToXMLResult[1020]);
    assertEquals('n', actualConvertToXMLResult[1027]);
    assertEquals('n', actualConvertToXMLResult[1032]);
    assertEquals('o', actualConvertToXMLResult[1031]);
    assertEquals('p', actualConvertToXMLResult[1018]);
    assertEquals('r', actualConvertToXMLResult[1010]);
    assertEquals('s', actualConvertToXMLResult[1033]);
    assertEquals('t', actualConvertToXMLResult[1029]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given ArrayList() add Process (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenArrayListAddProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(new Process());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1180, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1161]);
    assertEquals('2', actualConvertToXMLResult[1166]);
    assertEquals(':', actualConvertToXMLResult[1167]);
    assertEquals('<', actualConvertToXMLResult[1160]);
    assertEquals('>', actualConvertToXMLResult[1158]);
    assertEquals('>', actualConvertToXMLResult[1179]);
    assertEquals('\n', actualConvertToXMLResult[1159]);
    assertEquals('a', actualConvertToXMLResult[1156]);
    assertEquals('b', actualConvertToXMLResult[1162]);
    assertEquals('d', actualConvertToXMLResult[1168]);
    assertEquals('e', actualConvertToXMLResult[1169]);
    assertEquals('f', actualConvertToXMLResult[1170]);
    assertEquals('i', actualConvertToXMLResult[1171]);
    assertEquals('i', actualConvertToXMLResult[1173]);
    assertEquals('i', actualConvertToXMLResult[1175]);
    assertEquals('m', actualConvertToXMLResult[1157]);
    assertEquals('m', actualConvertToXMLResult[1164]);
    assertEquals('n', actualConvertToXMLResult[1165]);
    assertEquals('n', actualConvertToXMLResult[1172]);
    assertEquals('o', actualConvertToXMLResult[1176]);
    assertEquals('p', actualConvertToXMLResult[1163]);
    assertEquals('r', actualConvertToXMLResult[1155]);
    assertEquals('s', actualConvertToXMLResult[1178]);
    assertEquals('t', actualConvertToXMLResult[1174]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor).
   *   <li>Then return array length is {@code 1017}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given Association (default constructor); then return array length is '1017'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenAssociation_thenReturnArrayLengthIs1017() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(new MessageFlow());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model, atLeast(1)).getArtifact("  ");
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessageFlow("  ");
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    assertEquals(1017, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[998]);
    assertEquals('2', actualConvertToXMLResult[1003]);
    assertEquals(':', actualConvertToXMLResult[1004]);
    assertEquals('<', actualConvertToXMLResult[997]);
    assertEquals('>', actualConvertToXMLResult[1016]);
    assertEquals('>', actualConvertToXMLResult[995]);
    assertEquals('\n', actualConvertToXMLResult[996]);
    assertEquals('a', actualConvertToXMLResult[993]);
    assertEquals('b', actualConvertToXMLResult[999]);
    assertEquals('d', actualConvertToXMLResult[1005]);
    assertEquals('e', actualConvertToXMLResult[1006]);
    assertEquals('f', actualConvertToXMLResult[1007]);
    assertEquals('i', actualConvertToXMLResult[1008]);
    assertEquals('i', actualConvertToXMLResult[1010]);
    assertEquals('i', actualConvertToXMLResult[1012]);
    assertEquals('m', actualConvertToXMLResult[1001]);
    assertEquals('m', actualConvertToXMLResult[994]);
    assertEquals('n', actualConvertToXMLResult[1002]);
    assertEquals('n', actualConvertToXMLResult[1009]);
    assertEquals('n', actualConvertToXMLResult[1014]);
    assertEquals('o', actualConvertToXMLResult[1013]);
    assertEquals('p', actualConvertToXMLResult[1000]);
    assertEquals('r', actualConvertToXMLResult[992]);
    assertEquals('s', actualConvertToXMLResult[1015]);
    assertEquals('t', actualConvertToXMLResult[1011]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>Then return array length is {@code 1192}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given empty string; then return array length is '1192'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenEmptyString_thenReturnArrayLengthIs1192() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1192, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1173]);
    assertEquals('2', actualConvertToXMLResult[1178]);
    assertEquals(':', actualConvertToXMLResult[1179]);
    assertEquals('<', actualConvertToXMLResult[1172]);
    assertEquals('>', actualConvertToXMLResult[1170]);
    assertEquals('>', actualConvertToXMLResult[1191]);
    assertEquals('\n', actualConvertToXMLResult[1171]);
    assertEquals('a', actualConvertToXMLResult[1168]);
    assertEquals('b', actualConvertToXMLResult[1174]);
    assertEquals('d', actualConvertToXMLResult[1180]);
    assertEquals('e', actualConvertToXMLResult[1181]);
    assertEquals('f', actualConvertToXMLResult[1182]);
    assertEquals('i', actualConvertToXMLResult[1183]);
    assertEquals('m', actualConvertToXMLResult[1169]);
    assertEquals('m', actualConvertToXMLResult[1176]);
    assertEquals('n', actualConvertToXMLResult[1184]);
    assertEquals('n', actualConvertToXMLResult[1189]);
    assertEquals('o', actualConvertToXMLResult[1188]);
    assertEquals('p', actualConvertToXMLResult[1175]);
    assertEquals('r', actualConvertToXMLResult[1167]);
    assertEquals('s', actualConvertToXMLResult[1190]);
    assertEquals('t', actualConvertToXMLResult[1186]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} lf is {@code 1.0}.
   *   <li>Then return array length is {@code 1194}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given HashMap() lf is '1.0'; then return array length is '1194'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenHashMapLfIs10_thenReturnArrayLengthIs1194() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("\n", "1.0");

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1194, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1175]);
    assertEquals('2', actualConvertToXMLResult[1180]);
    assertEquals(':', actualConvertToXMLResult[1181]);
    assertEquals('<', actualConvertToXMLResult[1174]);
    assertEquals('>', actualConvertToXMLResult[1172]);
    assertEquals('>', actualConvertToXMLResult[1193]);
    assertEquals('\n', actualConvertToXMLResult[1173]);
    assertEquals('a', actualConvertToXMLResult[1170]);
    assertEquals('b', actualConvertToXMLResult[1176]);
    assertEquals('d', actualConvertToXMLResult[1182]);
    assertEquals('e', actualConvertToXMLResult[1183]);
    assertEquals('f', actualConvertToXMLResult[1184]);
    assertEquals('i', actualConvertToXMLResult[1189]);
    assertEquals('m', actualConvertToXMLResult[1171]);
    assertEquals('m', actualConvertToXMLResult[1178]);
    assertEquals('n', actualConvertToXMLResult[1179]);
    assertEquals('n', actualConvertToXMLResult[1186]);
    assertEquals('n', actualConvertToXMLResult[1191]);
    assertEquals('o', actualConvertToXMLResult[1190]);
    assertEquals('p', actualConvertToXMLResult[1177]);
    assertEquals('r', actualConvertToXMLResult[1169]);
    assertEquals('s', actualConvertToXMLResult[1192]);
    assertEquals('t', actualConvertToXMLResult[1188]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} lf is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given HashMap() lf is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenHashMapLfIsArrayList() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("\n", new ArrayList<>());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap2);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(stringListMap);
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1180, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1161]);
    assertEquals('2', actualConvertToXMLResult[1166]);
    assertEquals(':', actualConvertToXMLResult[1167]);
    assertEquals('<', actualConvertToXMLResult[1160]);
    assertEquals('>', actualConvertToXMLResult[1158]);
    assertEquals('>', actualConvertToXMLResult[1179]);
    assertEquals('\n', actualConvertToXMLResult[1159]);
    assertEquals('a', actualConvertToXMLResult[1156]);
    assertEquals('b', actualConvertToXMLResult[1162]);
    assertEquals('d', actualConvertToXMLResult[1168]);
    assertEquals('e', actualConvertToXMLResult[1169]);
    assertEquals('f', actualConvertToXMLResult[1170]);
    assertEquals('i', actualConvertToXMLResult[1171]);
    assertEquals('i', actualConvertToXMLResult[1173]);
    assertEquals('i', actualConvertToXMLResult[1175]);
    assertEquals('m', actualConvertToXMLResult[1157]);
    assertEquals('m', actualConvertToXMLResult[1164]);
    assertEquals('n', actualConvertToXMLResult[1165]);
    assertEquals('n', actualConvertToXMLResult[1172]);
    assertEquals('o', actualConvertToXMLResult[1176]);
    assertEquals('p', actualConvertToXMLResult[1163]);
    assertEquals('r', actualConvertToXMLResult[1155]);
    assertEquals('s', actualConvertToXMLResult[1178]);
    assertEquals('t', actualConvertToXMLResult[1174]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1035}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is '1035'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIs1035() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    assertEquals(1035, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1016]);
    assertEquals('2', actualConvertToXMLResult[1021]);
    assertEquals(':', actualConvertToXMLResult[1022]);
    assertEquals('<', actualConvertToXMLResult[1015]);
    assertEquals('>', actualConvertToXMLResult[1013]);
    assertEquals('>', actualConvertToXMLResult[1034]);
    assertEquals('\n', actualConvertToXMLResult[1014]);
    assertEquals('a', actualConvertToXMLResult[1011]);
    assertEquals('b', actualConvertToXMLResult[1017]);
    assertEquals('d', actualConvertToXMLResult[Double.MAX_EXPONENT]);
    assertEquals('e', actualConvertToXMLResult[1024]);
    assertEquals('f', actualConvertToXMLResult[1025]);
    assertEquals('i', actualConvertToXMLResult[1026]);
    assertEquals('i', actualConvertToXMLResult[1028]);
    assertEquals('i', actualConvertToXMLResult[1030]);
    assertEquals('m', actualConvertToXMLResult[1012]);
    assertEquals('m', actualConvertToXMLResult[1019]);
    assertEquals('n', actualConvertToXMLResult[1020]);
    assertEquals('n', actualConvertToXMLResult[1027]);
    assertEquals('n', actualConvertToXMLResult[1032]);
    assertEquals('o', actualConvertToXMLResult[1031]);
    assertEquals('p', actualConvertToXMLResult[1018]);
    assertEquals('r', actualConvertToXMLResult[1010]);
    assertEquals('s', actualConvertToXMLResult[1033]);
    assertEquals('t', actualConvertToXMLResult[1029]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1180}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is '1180'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIs1180() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1180, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1161]);
    assertEquals('2', actualConvertToXMLResult[1166]);
    assertEquals(':', actualConvertToXMLResult[1167]);
    assertEquals('<', actualConvertToXMLResult[1160]);
    assertEquals('>', actualConvertToXMLResult[1158]);
    assertEquals('>', actualConvertToXMLResult[1179]);
    assertEquals('\n', actualConvertToXMLResult[1159]);
    assertEquals('a', actualConvertToXMLResult[1156]);
    assertEquals('b', actualConvertToXMLResult[1162]);
    assertEquals('d', actualConvertToXMLResult[1168]);
    assertEquals('e', actualConvertToXMLResult[1169]);
    assertEquals('f', actualConvertToXMLResult[1170]);
    assertEquals('i', actualConvertToXMLResult[1171]);
    assertEquals('i', actualConvertToXMLResult[1173]);
    assertEquals('i', actualConvertToXMLResult[1175]);
    assertEquals('m', actualConvertToXMLResult[1157]);
    assertEquals('m', actualConvertToXMLResult[1164]);
    assertEquals('n', actualConvertToXMLResult[1165]);
    assertEquals('n', actualConvertToXMLResult[1172]);
    assertEquals('o', actualConvertToXMLResult[1176]);
    assertEquals('p', actualConvertToXMLResult[1163]);
    assertEquals('r', actualConvertToXMLResult[1155]);
    assertEquals('s', actualConvertToXMLResult[1178]);
    assertEquals('t', actualConvertToXMLResult[1174]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1246}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is '1246'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIs1246() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    HashMap<String, Error> stringErrorMap = new HashMap<>();
    Error error = new Error("42", "  ", "An error occurred");
    stringErrorMap.put("\n", error);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(stringErrorMap);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1246, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1227]);
    assertEquals('2', actualConvertToXMLResult[1232]);
    assertEquals(':', actualConvertToXMLResult[1233]);
    assertEquals('<', actualConvertToXMLResult[1226]);
    assertEquals('>', actualConvertToXMLResult[1224]);
    assertEquals('>', actualConvertToXMLResult[1245]);
    assertEquals('\n', actualConvertToXMLResult[1225]);
    assertEquals('a', actualConvertToXMLResult[1222]);
    assertEquals('b', actualConvertToXMLResult[1228]);
    assertEquals('d', actualConvertToXMLResult[1234]);
    assertEquals('e', actualConvertToXMLResult[1235]);
    assertEquals('f', actualConvertToXMLResult[1236]);
    assertEquals('i', actualConvertToXMLResult[1237]);
    assertEquals('i', actualConvertToXMLResult[1239]);
    assertEquals('i', actualConvertToXMLResult[1241]);
    assertEquals('m', actualConvertToXMLResult[1223]);
    assertEquals('n', actualConvertToXMLResult[1231]);
    assertEquals('n', actualConvertToXMLResult[1243]);
    assertEquals('o', actualConvertToXMLResult[1242]);
    assertEquals('p', actualConvertToXMLResult[1229]);
    assertEquals('r', actualConvertToXMLResult[1221]);
    assertEquals('s', actualConvertToXMLResult[1244]);
    assertEquals('t', actualConvertToXMLResult[1240]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1253}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is '1253'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIs1253() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("  ", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("  ");
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    assertEquals(1253, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1234]);
    assertEquals('2', actualConvertToXMLResult[1239]);
    assertEquals(':', actualConvertToXMLResult[1240]);
    assertEquals('<', actualConvertToXMLResult[1233]);
    assertEquals('>', actualConvertToXMLResult[1231]);
    assertEquals('>', actualConvertToXMLResult[1252]);
    assertEquals('\n', actualConvertToXMLResult[1232]);
    assertEquals('a', actualConvertToXMLResult[1229]);
    assertEquals('b', actualConvertToXMLResult[1235]);
    assertEquals('d', actualConvertToXMLResult[1241]);
    assertEquals('e', actualConvertToXMLResult[1242]);
    assertEquals('f', actualConvertToXMLResult[1243]);
    assertEquals('i', actualConvertToXMLResult[1244]);
    assertEquals('i', actualConvertToXMLResult[1246]);
    assertEquals('i', actualConvertToXMLResult[1248]);
    assertEquals('m', actualConvertToXMLResult[1237]);
    assertEquals('n', actualConvertToXMLResult[1245]);
    assertEquals('n', actualConvertToXMLResult[1250]);
    assertEquals('o', actualConvertToXMLResult[1249]);
    assertEquals('p', actualConvertToXMLResult[1236]);
    assertEquals('r', actualConvertToXMLResult[1228]);
    assertEquals('s', actualConvertToXMLResult[1251]);
    assertEquals('t', actualConvertToXMLResult[1247]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is eight hundred fifty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is eight hundred fifty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsEightHundredFifty() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    assertEquals(850, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[831]);
    assertEquals('2', actualConvertToXMLResult[836]);
    assertEquals(':', actualConvertToXMLResult[837]);
    assertEquals('<', actualConvertToXMLResult[830]);
    assertEquals('>', actualConvertToXMLResult[828]);
    assertEquals('>', actualConvertToXMLResult[849]);
    assertEquals('\n', actualConvertToXMLResult[829]);
    assertEquals('a', actualConvertToXMLResult[826]);
    assertEquals('b', actualConvertToXMLResult[832]);
    assertEquals('d', actualConvertToXMLResult[838]);
    assertEquals('e', actualConvertToXMLResult[839]);
    assertEquals('f', actualConvertToXMLResult[840]);
    assertEquals('i', actualConvertToXMLResult[841]);
    assertEquals('i', actualConvertToXMLResult[843]);
    assertEquals('i', actualConvertToXMLResult[845]);
    assertEquals('m', actualConvertToXMLResult[827]);
    assertEquals('m', actualConvertToXMLResult[834]);
    assertEquals('n', actualConvertToXMLResult[835]);
    assertEquals('n', actualConvertToXMLResult[842]);
    assertEquals('n', actualConvertToXMLResult[847]);
    assertEquals('o', actualConvertToXMLResult[846]);
    assertEquals('p', actualConvertToXMLResult[833]);
    assertEquals('r', actualConvertToXMLResult[825]);
    assertEquals('s', actualConvertToXMLResult[848]);
    assertEquals('t', actualConvertToXMLResult[844]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is nine hundred fifteen.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is nine hundred fifteen")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsNineHundredFifteen() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("  ", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("  ");
    verify(model).getFlowLocationGraphicInfo("  ");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("  ");
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    assertEquals(915, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[896]);
    assertEquals('2', actualConvertToXMLResult[901]);
    assertEquals(':', actualConvertToXMLResult[902]);
    assertEquals('<', actualConvertToXMLResult[895]);
    assertEquals('>', actualConvertToXMLResult[893]);
    assertEquals('>', actualConvertToXMLResult[914]);
    assertEquals('\n', actualConvertToXMLResult[894]);
    assertEquals('a', actualConvertToXMLResult[891]);
    assertEquals('b', actualConvertToXMLResult[897]);
    assertEquals('d', actualConvertToXMLResult[903]);
    assertEquals('e', actualConvertToXMLResult[904]);
    assertEquals('f', actualConvertToXMLResult[905]);
    assertEquals('i', actualConvertToXMLResult[906]);
    assertEquals('i', actualConvertToXMLResult[908]);
    assertEquals('i', actualConvertToXMLResult[910]);
    assertEquals('m', actualConvertToXMLResult[892]);
    assertEquals('m', actualConvertToXMLResult[899]);
    assertEquals('n', actualConvertToXMLResult[900]);
    assertEquals('n', actualConvertToXMLResult[907]);
    assertEquals('n', actualConvertToXMLResult[912]);
    assertEquals('o', actualConvertToXMLResult[911]);
    assertEquals('p', actualConvertToXMLResult[898]);
    assertEquals('r', actualConvertToXMLResult[890]);
    assertEquals('s', actualConvertToXMLResult[913]);
    assertEquals('t', actualConvertToXMLResult[909]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is seven hundred sixty-nine.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is seven hundred sixty-nine")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSevenHundredSixtyNine() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMainProcess()).thenReturn(process);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    verify(process).getId();
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    assertEquals(769, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[750]);
    assertEquals('2', actualConvertToXMLResult[755]);
    assertEquals(':', actualConvertToXMLResult[756]);
    assertEquals('<', actualConvertToXMLResult[749]);
    assertEquals('>', actualConvertToXMLResult[747]);
    assertEquals('>', actualConvertToXMLResult[768]);
    assertEquals('\n', actualConvertToXMLResult[748]);
    assertEquals('a', actualConvertToXMLResult[745]);
    assertEquals('b', actualConvertToXMLResult[751]);
    assertEquals('d', actualConvertToXMLResult[757]);
    assertEquals('e', actualConvertToXMLResult[758]);
    assertEquals('f', actualConvertToXMLResult[759]);
    assertEquals('i', actualConvertToXMLResult[760]);
    assertEquals('i', actualConvertToXMLResult[762]);
    assertEquals('i', actualConvertToXMLResult[764]);
    assertEquals('m', actualConvertToXMLResult[746]);
    assertEquals('m', actualConvertToXMLResult[753]);
    assertEquals('n', actualConvertToXMLResult[754]);
    assertEquals('n', actualConvertToXMLResult[761]);
    assertEquals('n', actualConvertToXMLResult[766]);
    assertEquals('o', actualConvertToXMLResult[765]);
    assertEquals('p', actualConvertToXMLResult[752]);
    assertEquals('r', actualConvertToXMLResult[744]);
    assertEquals('s', actualConvertToXMLResult[767]);
    assertEquals('t', actualConvertToXMLResult[763]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>When {@link BpmnModel}.
   *   <li>Then throw {@link XMLException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; when BpmnModel; then throw XMLException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_whenBpmnModel_thenThrowXMLException() {
    // Arrange, Act and Assert
    assertThrows(
        XMLException.class, () -> new BpmnXMLConverter().convertToXML(mock(BpmnModel.class), "  "));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; given AdhocSubProcess")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenAdhocSubProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any()))
        .thenThrow(new XMLException("An error occurred"));
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(mock(AdhocSubProcess.class));
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowElement("UTF-8");
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return array length is {@code 1294}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given AdhocSubProcess (default constructor); then return array length is '1294'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenAdhocSubProcess_thenReturnArrayLengthIs1294() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1294, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1275]);
    assertEquals('2', actualConvertToXMLResult[1280]);
    assertEquals(':', actualConvertToXMLResult[1281]);
    assertEquals('<', actualConvertToXMLResult[1274]);
    assertEquals('>', actualConvertToXMLResult[1272]);
    assertEquals('>', actualConvertToXMLResult[1293]);
    assertEquals('\n', actualConvertToXMLResult[1273]);
    assertEquals('a', actualConvertToXMLResult[1270]);
    assertEquals('b', actualConvertToXMLResult[1276]);
    assertEquals('d', actualConvertToXMLResult[1282]);
    assertEquals('e', actualConvertToXMLResult[1283]);
    assertEquals('f', actualConvertToXMLResult[1284]);
    assertEquals('i', actualConvertToXMLResult[1285]);
    assertEquals('i', actualConvertToXMLResult[1287]);
    assertEquals('i', actualConvertToXMLResult[1289]);
    assertEquals('m', actualConvertToXMLResult[1271]);
    assertEquals('m', actualConvertToXMLResult[1278]);
    assertEquals('n', actualConvertToXMLResult[1279]);
    assertEquals('n', actualConvertToXMLResult[1286]);
    assertEquals('n', actualConvertToXMLResult[1291]);
    assertEquals('o', actualConvertToXMLResult[1290]);
    assertEquals('p', actualConvertToXMLResult[1277]);
    assertEquals('r', actualConvertToXMLResult[1269]);
    assertEquals('s', actualConvertToXMLResult[1292]);
    assertEquals('t', actualConvertToXMLResult[1288]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 1.0}.
   *   <li>Then return array length is {@code 1721}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given ArrayList() add '1.0'; then return array length is '1721'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenArrayListAdd10_thenReturnArrayLengthIs1721() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("1.0");

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(stringList);
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1721, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1702]);
    assertEquals('2', actualConvertToXMLResult[1707]);
    assertEquals(':', actualConvertToXMLResult[1708]);
    assertEquals('<', actualConvertToXMLResult[1701]);
    assertEquals('>', actualConvertToXMLResult[1699]);
    assertEquals('>', actualConvertToXMLResult[1720]);
    assertEquals('\n', actualConvertToXMLResult[1700]);
    assertEquals('a', actualConvertToXMLResult[1697]);
    assertEquals('b', actualConvertToXMLResult[1703]);
    assertEquals('d', actualConvertToXMLResult[1709]);
    assertEquals('e', actualConvertToXMLResult[1710]);
    assertEquals('f', actualConvertToXMLResult[1711]);
    assertEquals('i', actualConvertToXMLResult[1712]);
    assertEquals('i', actualConvertToXMLResult[1714]);
    assertEquals('i', actualConvertToXMLResult[1716]);
    assertEquals('m', actualConvertToXMLResult[1698]);
    assertEquals('n', actualConvertToXMLResult[1706]);
    assertEquals('n', actualConvertToXMLResult[1718]);
    assertEquals('o', actualConvertToXMLResult[1717]);
    assertEquals('p', actualConvertToXMLResult[1704]);
    assertEquals('r', actualConvertToXMLResult[1696]);
    assertEquals('s', actualConvertToXMLResult[1719]);
    assertEquals('t', actualConvertToXMLResult[1715]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 1.0}.
   *   <li>Then return array length is {@code 1722}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given ArrayList() add '1.0'; then return array length is '1722'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenArrayListAdd10_thenReturnArrayLengthIs1722() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("1.0");

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1722, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1703]);
    assertEquals('2', actualConvertToXMLResult[1708]);
    assertEquals(':', actualConvertToXMLResult[1709]);
    assertEquals('<', actualConvertToXMLResult[1702]);
    assertEquals('>', actualConvertToXMLResult[1700]);
    assertEquals('>', actualConvertToXMLResult[1721]);
    assertEquals('\n', actualConvertToXMLResult[1701]);
    assertEquals('a', actualConvertToXMLResult[1698]);
    assertEquals('b', actualConvertToXMLResult[1704]);
    assertEquals('d', actualConvertToXMLResult[1710]);
    assertEquals('e', actualConvertToXMLResult[1711]);
    assertEquals('f', actualConvertToXMLResult[1712]);
    assertEquals('i', actualConvertToXMLResult[1713]);
    assertEquals('i', actualConvertToXMLResult[1715]);
    assertEquals('i', actualConvertToXMLResult[1717]);
    assertEquals('m', actualConvertToXMLResult[1699]);
    assertEquals('m', actualConvertToXMLResult[1706]);
    assertEquals('n', actualConvertToXMLResult[1707]);
    assertEquals('n', actualConvertToXMLResult[1714]);
    assertEquals('n', actualConvertToXMLResult[1719]);
    assertEquals('o', actualConvertToXMLResult[1718]);
    assertEquals('p', actualConvertToXMLResult[1705]);
    assertEquals('r', actualConvertToXMLResult[1697]);
    assertEquals('s', actualConvertToXMLResult[1720]);
    assertEquals('t', actualConvertToXMLResult[1716]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given ArrayList() add ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenArrayListAddActivitiListener() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1684, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1665]);
    assertEquals('2', actualConvertToXMLResult[1670]);
    assertEquals(':', actualConvertToXMLResult[1671]);
    assertEquals('<', actualConvertToXMLResult[1664]);
    assertEquals('>', actualConvertToXMLResult[1662]);
    assertEquals('>', actualConvertToXMLResult[1683]);
    assertEquals('\n', actualConvertToXMLResult[1663]);
    assertEquals('a', actualConvertToXMLResult[1660]);
    assertEquals('b', actualConvertToXMLResult[1666]);
    assertEquals('d', actualConvertToXMLResult[1672]);
    assertEquals('e', actualConvertToXMLResult[1673]);
    assertEquals('f', actualConvertToXMLResult[1674]);
    assertEquals('i', actualConvertToXMLResult[1675]);
    assertEquals('i', actualConvertToXMLResult[1677]);
    assertEquals('m', actualConvertToXMLResult[1661]);
    assertEquals('m', actualConvertToXMLResult[1668]);
    assertEquals('n', actualConvertToXMLResult[1669]);
    assertEquals('n', actualConvertToXMLResult[1676]);
    assertEquals('o', actualConvertToXMLResult[1680]);
    assertEquals('p', actualConvertToXMLResult[1667]);
    assertEquals('r', actualConvertToXMLResult[1659]);
    assertEquals('s', actualConvertToXMLResult[1682]);
    assertEquals('t', actualConvertToXMLResult[1678]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given ArrayList() add BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenArrayListAddBoundaryEvent() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(new BoundaryEvent());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(1998, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1979]);
    assertEquals('2', actualConvertToXMLResult[1984]);
    assertEquals(':', actualConvertToXMLResult[1985]);
    assertEquals('<', actualConvertToXMLResult[1978]);
    assertEquals('>', actualConvertToXMLResult[1976]);
    assertEquals('>', actualConvertToXMLResult[1997]);
    assertEquals('\n', actualConvertToXMLResult[1977]);
    assertEquals('a', actualConvertToXMLResult[1974]);
    assertEquals('b', actualConvertToXMLResult[1980]);
    assertEquals('d', actualConvertToXMLResult[1986]);
    assertEquals('e', actualConvertToXMLResult[1987]);
    assertEquals('f', actualConvertToXMLResult[1988]);
    assertEquals('i', actualConvertToXMLResult[1989]);
    assertEquals('i', actualConvertToXMLResult[1991]);
    assertEquals('i', actualConvertToXMLResult[1993]);
    assertEquals('m', actualConvertToXMLResult[1975]);
    assertEquals('m', actualConvertToXMLResult[1982]);
    assertEquals('n', actualConvertToXMLResult[1983]);
    assertEquals('n', actualConvertToXMLResult[1990]);
    assertEquals('n', actualConvertToXMLResult[1995]);
    assertEquals('o', actualConvertToXMLResult[1994]);
    assertEquals('p', actualConvertToXMLResult[1981]);
    assertEquals('r', actualConvertToXMLResult[1973]);
    assertEquals('s', actualConvertToXMLResult[1996]);
    assertEquals('t', actualConvertToXMLResult[1992]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Process} (default constructor).
   *   <li>Then return array length is {@code 1630}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given ArrayList() add Process (default constructor); then return array length is '1630'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenArrayListAddProcess_thenReturnArrayLengthIs1630() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(new Process());
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1630, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1611]);
    assertEquals('2', actualConvertToXMLResult[1616]);
    assertEquals(':', actualConvertToXMLResult[1617]);
    assertEquals('<', actualConvertToXMLResult[1610]);
    assertEquals('>', actualConvertToXMLResult[1608]);
    assertEquals('>', actualConvertToXMLResult[1629]);
    assertEquals('\n', actualConvertToXMLResult[1609]);
    assertEquals('a', actualConvertToXMLResult[1606]);
    assertEquals('b', actualConvertToXMLResult[1612]);
    assertEquals('d', actualConvertToXMLResult[1618]);
    assertEquals('e', actualConvertToXMLResult[1619]);
    assertEquals('f', actualConvertToXMLResult[1620]);
    assertEquals('i', actualConvertToXMLResult[1621]);
    assertEquals('i', actualConvertToXMLResult[1623]);
    assertEquals('i', actualConvertToXMLResult[1625]);
    assertEquals('m', actualConvertToXMLResult[1607]);
    assertEquals('m', actualConvertToXMLResult[1614]);
    assertEquals('n', actualConvertToXMLResult[1615]);
    assertEquals('n', actualConvertToXMLResult[1622]);
    assertEquals('n', actualConvertToXMLResult[1627]);
    assertEquals('o', actualConvertToXMLResult[1626]);
    assertEquals('p', actualConvertToXMLResult[1613]);
    assertEquals('r', actualConvertToXMLResult[1605]);
    assertEquals('s', actualConvertToXMLResult[1628]);
    assertEquals('t', actualConvertToXMLResult[1624]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 1.0} is {@link ArrayList#ArrayList()}.
   *   <li>Then return array length is {@code 1684}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given HashMap() '1.0' is ArrayList(); then return array length is '1684'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenHashMap10IsArrayList_thenReturnArrayLengthIs1684() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(stringListMap);
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap2);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1684, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1665]);
    assertEquals('2', actualConvertToXMLResult[1670]);
    assertEquals(':', actualConvertToXMLResult[1671]);
    assertEquals('<', actualConvertToXMLResult[1664]);
    assertEquals('>', actualConvertToXMLResult[1662]);
    assertEquals('>', actualConvertToXMLResult[1683]);
    assertEquals('\n', actualConvertToXMLResult[1663]);
    assertEquals('a', actualConvertToXMLResult[1660]);
    assertEquals('b', actualConvertToXMLResult[1666]);
    assertEquals('d', actualConvertToXMLResult[1672]);
    assertEquals('e', actualConvertToXMLResult[1673]);
    assertEquals('f', actualConvertToXMLResult[1674]);
    assertEquals('i', actualConvertToXMLResult[1675]);
    assertEquals('i', actualConvertToXMLResult[1677]);
    assertEquals('m', actualConvertToXMLResult[1661]);
    assertEquals('m', actualConvertToXMLResult[1668]);
    assertEquals('n', actualConvertToXMLResult[1669]);
    assertEquals('n', actualConvertToXMLResult[1676]);
    assertEquals('o', actualConvertToXMLResult[1680]);
    assertEquals('p', actualConvertToXMLResult[1667]);
    assertEquals('r', actualConvertToXMLResult[1659]);
    assertEquals('s', actualConvertToXMLResult[1682]);
    assertEquals('t', actualConvertToXMLResult[1678]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 1.0} is {@link ArrayList#ArrayList()}.
   *   <li>Then return array length is {@code 1728}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given HashMap() '1.0' is ArrayList(); then return array length is '1728'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenHashMap10IsArrayList_thenReturnArrayLengthIs1728() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(stringListMap);
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap2);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process, atLeast(1)).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1728, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1709]);
    assertEquals('2', actualConvertToXMLResult[1714]);
    assertEquals(':', actualConvertToXMLResult[1715]);
    assertEquals('<', actualConvertToXMLResult[1708]);
    assertEquals('>', actualConvertToXMLResult[1706]);
    assertEquals('>', actualConvertToXMLResult[1727]);
    assertEquals('\n', actualConvertToXMLResult[1707]);
    assertEquals('a', actualConvertToXMLResult[1704]);
    assertEquals('b', actualConvertToXMLResult[1710]);
    assertEquals('d', actualConvertToXMLResult[1716]);
    assertEquals('e', actualConvertToXMLResult[1717]);
    assertEquals('f', actualConvertToXMLResult[1718]);
    assertEquals('i', actualConvertToXMLResult[1719]);
    assertEquals('i', actualConvertToXMLResult[1721]);
    assertEquals('i', actualConvertToXMLResult[1723]);
    assertEquals('n', actualConvertToXMLResult[1725]);
    assertEquals('o', actualConvertToXMLResult[1724]);
    assertEquals('p', actualConvertToXMLResult[1711]);
    assertEquals('r', actualConvertToXMLResult[1703]);
    assertEquals('s', actualConvertToXMLResult[1726]);
    assertEquals('t', actualConvertToXMLResult[1722]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code UTF-8} is {@code 42}.
   *   <li>Then return array length is {@code 2015}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given HashMap() 'UTF-8' is '42'; then return array length is '2015'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenHashMapUtf8Is42_thenReturnArrayLengthIs2015() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("UTF-8", "42");

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2015, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1996]);
    assertEquals('2', actualConvertToXMLResult[2001]);
    assertEquals(':', actualConvertToXMLResult[2002]);
    assertEquals('<', actualConvertToXMLResult[1995]);
    assertEquals('>', actualConvertToXMLResult[1993]);
    assertEquals('>', actualConvertToXMLResult[2014]);
    assertEquals('\n', actualConvertToXMLResult[1994]);
    assertEquals('a', actualConvertToXMLResult[1991]);
    assertEquals('b', actualConvertToXMLResult[1997]);
    assertEquals('d', actualConvertToXMLResult[2003]);
    assertEquals('e', actualConvertToXMLResult[2004]);
    assertEquals('f', actualConvertToXMLResult[2005]);
    assertEquals('i', actualConvertToXMLResult[2006]);
    assertEquals('i', actualConvertToXMLResult[2008]);
    assertEquals('i', actualConvertToXMLResult[2010]);
    assertEquals('m', actualConvertToXMLResult[1992]);
    assertEquals('m', actualConvertToXMLResult[1999]);
    assertEquals('n', actualConvertToXMLResult[2000]);
    assertEquals('n', actualConvertToXMLResult[2007]);
    assertEquals('n', actualConvertToXMLResult[2012]);
    assertEquals('o', actualConvertToXMLResult[2011]);
    assertEquals('p', actualConvertToXMLResult[1998]);
    assertEquals('r', actualConvertToXMLResult[1990]);
    assertEquals('s', actualConvertToXMLResult[2013]);
    assertEquals('t', actualConvertToXMLResult[2009]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given 'null'; then calls getArtifact(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenNull_thenCallsGetArtifact() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(new MessageFlow());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getArtifact("UTF-8");
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("UTF-8");
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1294, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1275]);
    assertEquals('2', actualConvertToXMLResult[1280]);
    assertEquals(':', actualConvertToXMLResult[1281]);
    assertEquals('<', actualConvertToXMLResult[1274]);
    assertEquals('>', actualConvertToXMLResult[1272]);
    assertEquals('>', actualConvertToXMLResult[1293]);
    assertEquals('\n', actualConvertToXMLResult[1273]);
    assertEquals('a', actualConvertToXMLResult[1270]);
    assertEquals('b', actualConvertToXMLResult[1276]);
    assertEquals('d', actualConvertToXMLResult[1282]);
    assertEquals('e', actualConvertToXMLResult[1283]);
    assertEquals('f', actualConvertToXMLResult[1284]);
    assertEquals('i', actualConvertToXMLResult[1285]);
    assertEquals('i', actualConvertToXMLResult[1287]);
    assertEquals('i', actualConvertToXMLResult[1289]);
    assertEquals('m', actualConvertToXMLResult[1271]);
    assertEquals('m', actualConvertToXMLResult[1278]);
    assertEquals('n', actualConvertToXMLResult[1279]);
    assertEquals('n', actualConvertToXMLResult[1286]);
    assertEquals('n', actualConvertToXMLResult[1291]);
    assertEquals('o', actualConvertToXMLResult[1290]);
    assertEquals('p', actualConvertToXMLResult[1277]);
    assertEquals('r', actualConvertToXMLResult[1269]);
    assertEquals('s', actualConvertToXMLResult[1292]);
    assertEquals('t', actualConvertToXMLResult[1288]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#isSequential()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then calls isSequential()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenCallsIsSequential() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.isSequential())
        .thenThrow(new XMLException("An error occurred"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(multiInstanceLoopCharacteristics).isSequential();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1207}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1207'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1207() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1207, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1188]);
    assertEquals('2', actualConvertToXMLResult[1193]);
    assertEquals(':', actualConvertToXMLResult[1194]);
    assertEquals('<', actualConvertToXMLResult[1187]);
    assertEquals('>', actualConvertToXMLResult[1185]);
    assertEquals('>', actualConvertToXMLResult[1206]);
    assertEquals('\n', actualConvertToXMLResult[1186]);
    assertEquals('a', actualConvertToXMLResult[1183]);
    assertEquals('b', actualConvertToXMLResult[1189]);
    assertEquals('d', actualConvertToXMLResult[1195]);
    assertEquals('e', actualConvertToXMLResult[1196]);
    assertEquals('f', actualConvertToXMLResult[1197]);
    assertEquals('i', actualConvertToXMLResult[1198]);
    assertEquals('i', actualConvertToXMLResult[1200]);
    assertEquals('i', actualConvertToXMLResult[1202]);
    assertEquals('m', actualConvertToXMLResult[1184]);
    assertEquals('m', actualConvertToXMLResult[1191]);
    assertEquals('n', actualConvertToXMLResult[1192]);
    assertEquals('n', actualConvertToXMLResult[1199]);
    assertEquals('n', actualConvertToXMLResult[1204]);
    assertEquals('o', actualConvertToXMLResult[1203]);
    assertEquals('p', actualConvertToXMLResult[1190]);
    assertEquals('r', actualConvertToXMLResult[1182]);
    assertEquals('s', actualConvertToXMLResult[1205]);
    assertEquals('t', actualConvertToXMLResult[1201]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1359}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1359'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1359() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1359, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1340]);
    assertEquals('2', actualConvertToXMLResult[1345]);
    assertEquals(':', actualConvertToXMLResult[1346]);
    assertEquals('<', actualConvertToXMLResult[1339]);
    assertEquals('>', actualConvertToXMLResult[1337]);
    assertEquals('>', actualConvertToXMLResult[1358]);
    assertEquals('\n', actualConvertToXMLResult[1338]);
    assertEquals('a', actualConvertToXMLResult[1335]);
    assertEquals('b', actualConvertToXMLResult[1341]);
    assertEquals('d', actualConvertToXMLResult[1347]);
    assertEquals('e', actualConvertToXMLResult[1348]);
    assertEquals('f', actualConvertToXMLResult[1349]);
    assertEquals('i', actualConvertToXMLResult[1350]);
    assertEquals('i', actualConvertToXMLResult[1352]);
    assertEquals('i', actualConvertToXMLResult[1354]);
    assertEquals('m', actualConvertToXMLResult[1336]);
    assertEquals('m', actualConvertToXMLResult[1343]);
    assertEquals('n', actualConvertToXMLResult[1344]);
    assertEquals('n', actualConvertToXMLResult[1351]);
    assertEquals('n', actualConvertToXMLResult[1356]);
    assertEquals('o', actualConvertToXMLResult[1355]);
    assertEquals('p', actualConvertToXMLResult[1342]);
    assertEquals('r', actualConvertToXMLResult[1334]);
    assertEquals('s', actualConvertToXMLResult[1357]);
    assertEquals('t', actualConvertToXMLResult[1353]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1439}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1439'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1439() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1439, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1420]);
    assertEquals('2', actualConvertToXMLResult[1425]);
    assertEquals(':', actualConvertToXMLResult[1426]);
    assertEquals('<', actualConvertToXMLResult[1419]);
    assertEquals('>', actualConvertToXMLResult[1417]);
    assertEquals('>', actualConvertToXMLResult[1438]);
    assertEquals('\n', actualConvertToXMLResult[1418]);
    assertEquals('a', actualConvertToXMLResult[1415]);
    assertEquals('b', actualConvertToXMLResult[1421]);
    assertEquals('d', actualConvertToXMLResult[1427]);
    assertEquals('e', actualConvertToXMLResult[1428]);
    assertEquals('f', actualConvertToXMLResult[1429]);
    assertEquals('i', actualConvertToXMLResult[1430]);
    assertEquals('i', actualConvertToXMLResult[1432]);
    assertEquals('i', actualConvertToXMLResult[1434]);
    assertEquals('m', actualConvertToXMLResult[1416]);
    assertEquals('m', actualConvertToXMLResult[1423]);
    assertEquals('n', actualConvertToXMLResult[1424]);
    assertEquals('n', actualConvertToXMLResult[1431]);
    assertEquals('n', actualConvertToXMLResult[1436]);
    assertEquals('o', actualConvertToXMLResult[1435]);
    assertEquals('p', actualConvertToXMLResult[1422]);
    assertEquals('r', actualConvertToXMLResult[1414]);
    assertEquals('s', actualConvertToXMLResult[1437]);
    assertEquals('t', actualConvertToXMLResult[1433]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1608}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1608'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1608() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new TextAnnotation());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1608, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1589]);
    assertEquals('2', actualConvertToXMLResult[1594]);
    assertEquals(':', actualConvertToXMLResult[1595]);
    assertEquals('<', actualConvertToXMLResult[1588]);
    assertEquals('>', actualConvertToXMLResult[1586]);
    assertEquals('>', actualConvertToXMLResult[1607]);
    assertEquals('\n', actualConvertToXMLResult[1587]);
    assertEquals('a', actualConvertToXMLResult[1584]);
    assertEquals('b', actualConvertToXMLResult[1590]);
    assertEquals('d', actualConvertToXMLResult[1596]);
    assertEquals('e', actualConvertToXMLResult[1597]);
    assertEquals('f', actualConvertToXMLResult[1598]);
    assertEquals('i', actualConvertToXMLResult[1599]);
    assertEquals('i', actualConvertToXMLResult[1601]);
    assertEquals('m', actualConvertToXMLResult[1585]);
    assertEquals('m', actualConvertToXMLResult[1592]);
    assertEquals('n', actualConvertToXMLResult[1593]);
    assertEquals('n', actualConvertToXMLResult[1600]);
    assertEquals('n', actualConvertToXMLResult[1605]);
    assertEquals('o', actualConvertToXMLResult[1604]);
    assertEquals('p', actualConvertToXMLResult[1591]);
    assertEquals('r', actualConvertToXMLResult[1583]);
    assertEquals('s', actualConvertToXMLResult[1606]);
    assertEquals('t', actualConvertToXMLResult[1602]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1612}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1612'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1612() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(null);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1612, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1593]);
    assertEquals('2', actualConvertToXMLResult[1598]);
    assertEquals(':', actualConvertToXMLResult[1599]);
    assertEquals('<', actualConvertToXMLResult[1592]);
    assertEquals('>', actualConvertToXMLResult[1590]);
    assertEquals('>', actualConvertToXMLResult[1611]);
    assertEquals('\n', actualConvertToXMLResult[1591]);
    assertEquals('a', actualConvertToXMLResult[1588]);
    assertEquals('b', actualConvertToXMLResult[1594]);
    assertEquals('d', actualConvertToXMLResult[1600]);
    assertEquals('e', actualConvertToXMLResult[1601]);
    assertEquals('f', actualConvertToXMLResult[1602]);
    assertEquals('i', actualConvertToXMLResult[1605]);
    assertEquals('i', actualConvertToXMLResult[1607]);
    assertEquals('m', actualConvertToXMLResult[1589]);
    assertEquals('m', actualConvertToXMLResult[1596]);
    assertEquals('n', actualConvertToXMLResult[1597]);
    assertEquals('n', actualConvertToXMLResult[1604]);
    assertEquals('n', actualConvertToXMLResult[1609]);
    assertEquals('o', actualConvertToXMLResult[1608]);
    assertEquals('p', actualConvertToXMLResult[1595]);
    assertEquals('r', actualConvertToXMLResult[1587]);
    assertEquals('s', actualConvertToXMLResult[1610]);
    assertEquals('t', actualConvertToXMLResult[1606]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1630}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1630'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1630() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1630, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1611]);
    assertEquals('2', actualConvertToXMLResult[1616]);
    assertEquals(':', actualConvertToXMLResult[1617]);
    assertEquals('<', actualConvertToXMLResult[1610]);
    assertEquals('>', actualConvertToXMLResult[1608]);
    assertEquals('>', actualConvertToXMLResult[1629]);
    assertEquals('\n', actualConvertToXMLResult[1609]);
    assertEquals('a', actualConvertToXMLResult[1606]);
    assertEquals('b', actualConvertToXMLResult[1612]);
    assertEquals('d', actualConvertToXMLResult[1618]);
    assertEquals('e', actualConvertToXMLResult[1619]);
    assertEquals('f', actualConvertToXMLResult[1620]);
    assertEquals('i', actualConvertToXMLResult[1621]);
    assertEquals('i', actualConvertToXMLResult[1623]);
    assertEquals('i', actualConvertToXMLResult[1625]);
    assertEquals('m', actualConvertToXMLResult[1607]);
    assertEquals('m', actualConvertToXMLResult[1614]);
    assertEquals('n', actualConvertToXMLResult[1615]);
    assertEquals('n', actualConvertToXMLResult[1622]);
    assertEquals('n', actualConvertToXMLResult[1627]);
    assertEquals('o', actualConvertToXMLResult[1626]);
    assertEquals('p', actualConvertToXMLResult[1613]);
    assertEquals('r', actualConvertToXMLResult[1605]);
    assertEquals('s', actualConvertToXMLResult[1628]);
    assertEquals('t', actualConvertToXMLResult[1624]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1656}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1656'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1656() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(null);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1656, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1637]);
    assertEquals('2', actualConvertToXMLResult[1642]);
    assertEquals(':', actualConvertToXMLResult[1643]);
    assertEquals('<', actualConvertToXMLResult[1636]);
    assertEquals('>', actualConvertToXMLResult[1634]);
    assertEquals('>', actualConvertToXMLResult[1655]);
    assertEquals('\n', actualConvertToXMLResult[1635]);
    assertEquals('a', actualConvertToXMLResult[1632]);
    assertEquals('b', actualConvertToXMLResult[1638]);
    assertEquals('d', actualConvertToXMLResult[1644]);
    assertEquals('e', actualConvertToXMLResult[1645]);
    assertEquals('f', actualConvertToXMLResult[1646]);
    assertEquals('i', actualConvertToXMLResult[1647]);
    assertEquals('i', actualConvertToXMLResult[1649]);
    assertEquals('i', actualConvertToXMLResult[1651]);
    assertEquals('m', actualConvertToXMLResult[1633]);
    assertEquals('m', actualConvertToXMLResult[1640]);
    assertEquals('n', actualConvertToXMLResult[1641]);
    assertEquals('n', actualConvertToXMLResult[1648]);
    assertEquals('n', actualConvertToXMLResult[1653]);
    assertEquals('o', actualConvertToXMLResult[1652]);
    assertEquals('p', actualConvertToXMLResult[1639]);
    assertEquals('r', actualConvertToXMLResult[1631]);
    assertEquals('s', actualConvertToXMLResult[1654]);
    assertEquals('t', actualConvertToXMLResult[1650]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1684}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1684'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1684() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1684, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1665]);
    assertEquals('2', actualConvertToXMLResult[1670]);
    assertEquals(':', actualConvertToXMLResult[1671]);
    assertEquals('<', actualConvertToXMLResult[1664]);
    assertEquals('>', actualConvertToXMLResult[1662]);
    assertEquals('>', actualConvertToXMLResult[1683]);
    assertEquals('\n', actualConvertToXMLResult[1663]);
    assertEquals('a', actualConvertToXMLResult[1660]);
    assertEquals('b', actualConvertToXMLResult[1666]);
    assertEquals('d', actualConvertToXMLResult[1672]);
    assertEquals('e', actualConvertToXMLResult[1673]);
    assertEquals('f', actualConvertToXMLResult[1674]);
    assertEquals('i', actualConvertToXMLResult[1675]);
    assertEquals('i', actualConvertToXMLResult[1677]);
    assertEquals('m', actualConvertToXMLResult[1661]);
    assertEquals('m', actualConvertToXMLResult[1668]);
    assertEquals('n', actualConvertToXMLResult[1669]);
    assertEquals('n', actualConvertToXMLResult[1676]);
    assertEquals('o', actualConvertToXMLResult[1680]);
    assertEquals('p', actualConvertToXMLResult[1667]);
    assertEquals('r', actualConvertToXMLResult[1659]);
    assertEquals('s', actualConvertToXMLResult[1682]);
    assertEquals('t', actualConvertToXMLResult[1678]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1688}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1688'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1688() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new CallActivity());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1688, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1669]);
    assertEquals('2', actualConvertToXMLResult[1674]);
    assertEquals(':', actualConvertToXMLResult[1675]);
    assertEquals('<', actualConvertToXMLResult[1668]);
    assertEquals('>', actualConvertToXMLResult[1666]);
    assertEquals('>', actualConvertToXMLResult[1687]);
    assertEquals('\n', actualConvertToXMLResult[1667]);
    assertEquals('a', actualConvertToXMLResult[1664]);
    assertEquals('b', actualConvertToXMLResult[1670]);
    assertEquals('d', actualConvertToXMLResult[1676]);
    assertEquals('e', actualConvertToXMLResult[1677]);
    assertEquals('f', actualConvertToXMLResult[1678]);
    assertEquals('m', actualConvertToXMLResult[1665]);
    assertEquals('m', actualConvertToXMLResult[1672]);
    assertEquals('n', actualConvertToXMLResult[1673]);
    assertEquals('n', actualConvertToXMLResult[1680]);
    assertEquals('n', actualConvertToXMLResult[1685]);
    assertEquals('o', actualConvertToXMLResult[1684]);
    assertEquals('p', actualConvertToXMLResult[1671]);
    assertEquals('r', actualConvertToXMLResult[1663]);
    assertEquals('s', actualConvertToXMLResult[1686]);
    assertEquals('t', actualConvertToXMLResult[1682]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1690}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1690'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1690() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BoundaryEvent());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1690, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1671]);
    assertEquals('2', actualConvertToXMLResult[1676]);
    assertEquals(':', actualConvertToXMLResult[1677]);
    assertEquals('<', actualConvertToXMLResult[1670]);
    assertEquals('>', actualConvertToXMLResult[1668]);
    assertEquals('>', actualConvertToXMLResult[1689]);
    assertEquals('\n', actualConvertToXMLResult[1669]);
    assertEquals('a', actualConvertToXMLResult[1666]);
    assertEquals('b', actualConvertToXMLResult[1672]);
    assertEquals('d', actualConvertToXMLResult[1678]);
    assertEquals('e', actualConvertToXMLResult[1679]);
    assertEquals('f', actualConvertToXMLResult[1680]);
    assertEquals('i', actualConvertToXMLResult[1685]);
    assertEquals('m', actualConvertToXMLResult[1667]);
    assertEquals('m', actualConvertToXMLResult[1674]);
    assertEquals('n', actualConvertToXMLResult[1675]);
    assertEquals('n', actualConvertToXMLResult[1682]);
    assertEquals('n', actualConvertToXMLResult[1687]);
    assertEquals('o', actualConvertToXMLResult[1686]);
    assertEquals('p', actualConvertToXMLResult[1673]);
    assertEquals('r', actualConvertToXMLResult[1665]);
    assertEquals('s', actualConvertToXMLResult[1688]);
    assertEquals('t', actualConvertToXMLResult[1684]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1696}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1696'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1696() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BusinessRuleTask());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1696, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1677]);
    assertEquals('2', actualConvertToXMLResult[1682]);
    assertEquals(':', actualConvertToXMLResult[1683]);
    assertEquals('<', actualConvertToXMLResult[1676]);
    assertEquals('>', actualConvertToXMLResult[1674]);
    assertEquals('>', actualConvertToXMLResult[1695]);
    assertEquals('\n', actualConvertToXMLResult[1675]);
    assertEquals('a', actualConvertToXMLResult[1672]);
    assertEquals('b', actualConvertToXMLResult[1678]);
    assertEquals('d', actualConvertToXMLResult[1684]);
    assertEquals('e', actualConvertToXMLResult[1685]);
    assertEquals('f', actualConvertToXMLResult[1686]);
    assertEquals('i', actualConvertToXMLResult[1687]);
    assertEquals('i', actualConvertToXMLResult[1689]);
    assertEquals('i', actualConvertToXMLResult[1691]);
    assertEquals('m', actualConvertToXMLResult[1673]);
    assertEquals('m', actualConvertToXMLResult[1680]);
    assertEquals('n', actualConvertToXMLResult[1688]);
    assertEquals('n', actualConvertToXMLResult[1693]);
    assertEquals('o', actualConvertToXMLResult[1692]);
    assertEquals('p', actualConvertToXMLResult[1679]);
    assertEquals('r', actualConvertToXMLResult[1671]);
    assertEquals('s', actualConvertToXMLResult[1694]);
    assertEquals('t', actualConvertToXMLResult[1690]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1735}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1735'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1735() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(stringListMap);
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap2);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(association, atLeast(1)).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1735, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1716]);
    assertEquals('2', actualConvertToXMLResult[1721]);
    assertEquals(':', actualConvertToXMLResult[1722]);
    assertEquals('<', actualConvertToXMLResult[1715]);
    assertEquals('>', actualConvertToXMLResult[1713]);
    assertEquals('>', actualConvertToXMLResult[1734]);
    assertEquals('\n', actualConvertToXMLResult[1714]);
    assertEquals('a', actualConvertToXMLResult[1711]);
    assertEquals('b', actualConvertToXMLResult[1717]);
    assertEquals('d', actualConvertToXMLResult[1723]);
    assertEquals('e', actualConvertToXMLResult[1724]);
    assertEquals('f', actualConvertToXMLResult[1725]);
    assertEquals('i', actualConvertToXMLResult[1726]);
    assertEquals('i', actualConvertToXMLResult[1728]);
    assertEquals('i', actualConvertToXMLResult[1730]);
    assertEquals('m', actualConvertToXMLResult[1719]);
    assertEquals('n', actualConvertToXMLResult[1727]);
    assertEquals('n', actualConvertToXMLResult[1732]);
    assertEquals('o', actualConvertToXMLResult[1731]);
    assertEquals('p', actualConvertToXMLResult[1718]);
    assertEquals('r', actualConvertToXMLResult[1710]);
    assertEquals('s', actualConvertToXMLResult[1733]);
    assertEquals('t', actualConvertToXMLResult[1729]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1789}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1789'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1789() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    assertEquals(1789, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1770]);
    assertEquals('2', actualConvertToXMLResult[1775]);
    assertEquals(':', actualConvertToXMLResult[1776]);
    assertEquals('<', actualConvertToXMLResult[1769]);
    assertEquals('>', actualConvertToXMLResult[1767]);
    assertEquals('>', actualConvertToXMLResult[1788]);
    assertEquals('\n', actualConvertToXMLResult[1768]);
    assertEquals('a', actualConvertToXMLResult[1765]);
    assertEquals('b', actualConvertToXMLResult[1771]);
    assertEquals('d', actualConvertToXMLResult[1777]);
    assertEquals('e', actualConvertToXMLResult[1778]);
    assertEquals('f', actualConvertToXMLResult[1779]);
    assertEquals('i', actualConvertToXMLResult[1780]);
    assertEquals('i', actualConvertToXMLResult[1782]);
    assertEquals('i', actualConvertToXMLResult[1784]);
    assertEquals('m', actualConvertToXMLResult[1766]);
    assertEquals('m', actualConvertToXMLResult[1773]);
    assertEquals('n', actualConvertToXMLResult[1774]);
    assertEquals('n', actualConvertToXMLResult[1781]);
    assertEquals('n', actualConvertToXMLResult[1786]);
    assertEquals('o', actualConvertToXMLResult[1785]);
    assertEquals('p', actualConvertToXMLResult[1772]);
    assertEquals('r', actualConvertToXMLResult[1764]);
    assertEquals('s', actualConvertToXMLResult[1787]);
    assertEquals('t', actualConvertToXMLResult[1783]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1998}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '1998'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs1998() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(1998, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[1979]);
    assertEquals('2', actualConvertToXMLResult[1984]);
    assertEquals(':', actualConvertToXMLResult[1985]);
    assertEquals('<', actualConvertToXMLResult[1978]);
    assertEquals('>', actualConvertToXMLResult[1976]);
    assertEquals('>', actualConvertToXMLResult[1997]);
    assertEquals('\n', actualConvertToXMLResult[1977]);
    assertEquals('a', actualConvertToXMLResult[1974]);
    assertEquals('b', actualConvertToXMLResult[1980]);
    assertEquals('d', actualConvertToXMLResult[1986]);
    assertEquals('e', actualConvertToXMLResult[1987]);
    assertEquals('f', actualConvertToXMLResult[1988]);
    assertEquals('i', actualConvertToXMLResult[1989]);
    assertEquals('i', actualConvertToXMLResult[1991]);
    assertEquals('i', actualConvertToXMLResult[1993]);
    assertEquals('m', actualConvertToXMLResult[1975]);
    assertEquals('m', actualConvertToXMLResult[1982]);
    assertEquals('n', actualConvertToXMLResult[1983]);
    assertEquals('n', actualConvertToXMLResult[1990]);
    assertEquals('n', actualConvertToXMLResult[1995]);
    assertEquals('o', actualConvertToXMLResult[1994]);
    assertEquals('p', actualConvertToXMLResult[1981]);
    assertEquals('r', actualConvertToXMLResult[1973]);
    assertEquals('s', actualConvertToXMLResult[1996]);
    assertEquals('t', actualConvertToXMLResult[1992]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2044}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2044'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2044() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(stringListMap);
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap2);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess, atLeast(1)).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2044, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2025]);
    assertEquals('2', actualConvertToXMLResult[2030]);
    assertEquals(':', actualConvertToXMLResult[2031]);
    assertEquals('<', actualConvertToXMLResult[2024]);
    assertEquals('>', actualConvertToXMLResult[2022]);
    assertEquals('>', actualConvertToXMLResult[2043]);
    assertEquals('\n', actualConvertToXMLResult[2023]);
    assertEquals('a', actualConvertToXMLResult[2020]);
    assertEquals('b', actualConvertToXMLResult[2026]);
    assertEquals('d', actualConvertToXMLResult[2032]);
    assertEquals('e', actualConvertToXMLResult[2033]);
    assertEquals('f', actualConvertToXMLResult[2034]);
    assertEquals('i', actualConvertToXMLResult[2035]);
    assertEquals('i', actualConvertToXMLResult[2037]);
    assertEquals('i', actualConvertToXMLResult[2039]);
    assertEquals('m', actualConvertToXMLResult[2021]);
    assertEquals('m', actualConvertToXMLResult[2028]);
    assertEquals('n', actualConvertToXMLResult[2029]);
    assertEquals('n', actualConvertToXMLResult[2036]);
    assertEquals('n', actualConvertToXMLResult[2041]);
    assertEquals('o', actualConvertToXMLResult[2040]);
    assertEquals('p', actualConvertToXMLResult[2027]);
    assertEquals('r', actualConvertToXMLResult[2019]);
    assertEquals('s', actualConvertToXMLResult[2042]);
    assertEquals('t', actualConvertToXMLResult[2038]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2071}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2071'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2071() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2071, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2052]);
    assertEquals('2', actualConvertToXMLResult[2057]);
    assertEquals(':', actualConvertToXMLResult[2058]);
    assertEquals('<', actualConvertToXMLResult[2051]);
    assertEquals('>', actualConvertToXMLResult[2049]);
    assertEquals('>', actualConvertToXMLResult[2070]);
    assertEquals('\n', actualConvertToXMLResult[2050]);
    assertEquals('a', actualConvertToXMLResult[2047]);
    assertEquals('b', actualConvertToXMLResult[2053]);
    assertEquals('d', actualConvertToXMLResult[2059]);
    assertEquals('e', actualConvertToXMLResult[2060]);
    assertEquals('f', actualConvertToXMLResult[2061]);
    assertEquals('i', actualConvertToXMLResult[2062]);
    assertEquals('i', actualConvertToXMLResult[2064]);
    assertEquals('i', actualConvertToXMLResult[2066]);
    assertEquals('m', actualConvertToXMLResult[2048]);
    assertEquals('m', actualConvertToXMLResult[2055]);
    assertEquals('n', actualConvertToXMLResult[2056]);
    assertEquals('n', actualConvertToXMLResult[2063]);
    assertEquals('n', actualConvertToXMLResult[2068]);
    assertEquals('o', actualConvertToXMLResult[2067]);
    assertEquals('p', actualConvertToXMLResult[2054]);
    assertEquals('r', actualConvertToXMLResult[2046]);
    assertEquals('s', actualConvertToXMLResult[2069]);
    assertEquals('t', actualConvertToXMLResult[2065]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2072}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2072'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2072() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    ArrayList<Artifact> artifactList = new ArrayList<>();
    artifactList.add(new Association());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(artifactList);
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2072, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2053]);
    assertEquals('2', actualConvertToXMLResult[2058]);
    assertEquals(':', actualConvertToXMLResult[2059]);
    assertEquals('<', actualConvertToXMLResult[2052]);
    assertEquals('>', actualConvertToXMLResult[2050]);
    assertEquals('>', actualConvertToXMLResult[2071]);
    assertEquals('\n', actualConvertToXMLResult[2051]);
    assertEquals('a', actualConvertToXMLResult[2048]);
    assertEquals('b', actualConvertToXMLResult[2054]);
    assertEquals('d', actualConvertToXMLResult[2060]);
    assertEquals('e', actualConvertToXMLResult[2061]);
    assertEquals('f', actualConvertToXMLResult[2062]);
    assertEquals('i', actualConvertToXMLResult[2063]);
    assertEquals('i', actualConvertToXMLResult[2065]);
    assertEquals('i', actualConvertToXMLResult[2067]);
    assertEquals('m', actualConvertToXMLResult[2049]);
    assertEquals('m', actualConvertToXMLResult[2056]);
    assertEquals('n', actualConvertToXMLResult[2057]);
    assertEquals('n', actualConvertToXMLResult[2064]);
    assertEquals('n', actualConvertToXMLResult[2069]);
    assertEquals('o', actualConvertToXMLResult[2068]);
    assertEquals('p', actualConvertToXMLResult[2055]);
    assertEquals('r', actualConvertToXMLResult[2047]);
    assertEquals('s', actualConvertToXMLResult[2070]);
    assertEquals('t', actualConvertToXMLResult[2066]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2133}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2133'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2133() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2133, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2114]);
    assertEquals('2', actualConvertToXMLResult[2119]);
    assertEquals(':', actualConvertToXMLResult[2120]);
    assertEquals('<', actualConvertToXMLResult[2113]);
    assertEquals('>', actualConvertToXMLResult[2111]);
    assertEquals('>', actualConvertToXMLResult[2132]);
    assertEquals('\n', actualConvertToXMLResult[2112]);
    assertEquals('a', actualConvertToXMLResult[2109]);
    assertEquals('b', actualConvertToXMLResult[2115]);
    assertEquals('d', actualConvertToXMLResult[2121]);
    assertEquals('e', actualConvertToXMLResult[2122]);
    assertEquals('f', actualConvertToXMLResult[2123]);
    assertEquals('i', actualConvertToXMLResult[2124]);
    assertEquals('i', actualConvertToXMLResult[2126]);
    assertEquals('i', actualConvertToXMLResult[2128]);
    assertEquals('m', actualConvertToXMLResult[2110]);
    assertEquals('m', actualConvertToXMLResult[2117]);
    assertEquals('n', actualConvertToXMLResult[2118]);
    assertEquals('n', actualConvertToXMLResult[2125]);
    assertEquals('n', actualConvertToXMLResult[2130]);
    assertEquals('o', actualConvertToXMLResult[2129]);
    assertEquals('p', actualConvertToXMLResult[2116]);
    assertEquals('r', actualConvertToXMLResult[2108]);
    assertEquals('s', actualConvertToXMLResult[2131]);
    assertEquals('t', actualConvertToXMLResult[2127]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2161}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2161'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2161() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setInputDataItem("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2161, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2142]);
    assertEquals('2', actualConvertToXMLResult[2147]);
    assertEquals(':', actualConvertToXMLResult[2148]);
    assertEquals('<', actualConvertToXMLResult[2141]);
    assertEquals('>', actualConvertToXMLResult[2139]);
    assertEquals('>', actualConvertToXMLResult[2160]);
    assertEquals('\n', actualConvertToXMLResult[2140]);
    assertEquals('a', actualConvertToXMLResult[2137]);
    assertEquals('b', actualConvertToXMLResult[2143]);
    assertEquals('d', actualConvertToXMLResult[2149]);
    assertEquals('e', actualConvertToXMLResult[2150]);
    assertEquals('f', actualConvertToXMLResult[2151]);
    assertEquals('i', actualConvertToXMLResult[2152]);
    assertEquals('i', actualConvertToXMLResult[2154]);
    assertEquals('i', actualConvertToXMLResult[2156]);
    assertEquals('m', actualConvertToXMLResult[2138]);
    assertEquals('m', actualConvertToXMLResult[2145]);
    assertEquals('n', actualConvertToXMLResult[2146]);
    assertEquals('n', actualConvertToXMLResult[2153]);
    assertEquals('o', actualConvertToXMLResult[2157]);
    assertEquals('p', actualConvertToXMLResult[2144]);
    assertEquals('r', actualConvertToXMLResult[2136]);
    assertEquals('s', actualConvertToXMLResult[2159]);
    assertEquals('t', actualConvertToXMLResult[2155]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2166}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2166'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2166() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setElementVariable("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2166, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2147]);
    assertEquals('2', actualConvertToXMLResult[2152]);
    assertEquals(':', actualConvertToXMLResult[2153]);
    assertEquals('<', actualConvertToXMLResult[2146]);
    assertEquals('>', actualConvertToXMLResult[2144]);
    assertEquals('>', actualConvertToXMLResult[2165]);
    assertEquals('\n', actualConvertToXMLResult[2145]);
    assertEquals('a', actualConvertToXMLResult[2142]);
    assertEquals('b', actualConvertToXMLResult[2148]);
    assertEquals('d', actualConvertToXMLResult[2154]);
    assertEquals('e', actualConvertToXMLResult[2155]);
    assertEquals('f', actualConvertToXMLResult[2156]);
    assertEquals('i', actualConvertToXMLResult[2157]);
    assertEquals('i', actualConvertToXMLResult[2159]);
    assertEquals('i', actualConvertToXMLResult[2161]);
    assertEquals('m', actualConvertToXMLResult[2143]);
    assertEquals('m', actualConvertToXMLResult[2150]);
    assertEquals('n', actualConvertToXMLResult[2151]);
    assertEquals('n', actualConvertToXMLResult[2163]);
    assertEquals('o', actualConvertToXMLResult[2162]);
    assertEquals('p', actualConvertToXMLResult[2149]);
    assertEquals('r', actualConvertToXMLResult[2141]);
    assertEquals('s', actualConvertToXMLResult[2164]);
    assertEquals('t', actualConvertToXMLResult[2160]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2189}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2189'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2189() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setLoopCardinality("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2189, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2170]);
    assertEquals('2', actualConvertToXMLResult[2175]);
    assertEquals(':', actualConvertToXMLResult[2176]);
    assertEquals('<', actualConvertToXMLResult[2169]);
    assertEquals('>', actualConvertToXMLResult[2167]);
    assertEquals('>', actualConvertToXMLResult[2188]);
    assertEquals('\n', actualConvertToXMLResult[2168]);
    assertEquals('a', actualConvertToXMLResult[2165]);
    assertEquals('b', actualConvertToXMLResult[2171]);
    assertEquals('d', actualConvertToXMLResult[2177]);
    assertEquals('e', actualConvertToXMLResult[2178]);
    assertEquals('f', actualConvertToXMLResult[2179]);
    assertEquals('i', actualConvertToXMLResult[2180]);
    assertEquals('i', actualConvertToXMLResult[2182]);
    assertEquals('m', actualConvertToXMLResult[2166]);
    assertEquals('m', actualConvertToXMLResult[2173]);
    assertEquals('n', actualConvertToXMLResult[2174]);
    assertEquals('n', actualConvertToXMLResult[2181]);
    assertEquals('n', actualConvertToXMLResult[2186]);
    assertEquals('o', actualConvertToXMLResult[2185]);
    assertEquals('p', actualConvertToXMLResult[2172]);
    assertEquals('r', actualConvertToXMLResult[2164]);
    assertEquals('s', actualConvertToXMLResult[2187]);
    assertEquals('t', actualConvertToXMLResult[2183]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2193}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2193'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2193() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setLoopDataOutputRef("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2193, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2174]);
    assertEquals('2', actualConvertToXMLResult[2179]);
    assertEquals(':', actualConvertToXMLResult[2180]);
    assertEquals('<', actualConvertToXMLResult[2173]);
    assertEquals('>', actualConvertToXMLResult[2171]);
    assertEquals('>', actualConvertToXMLResult[2192]);
    assertEquals('\n', actualConvertToXMLResult[2172]);
    assertEquals('a', actualConvertToXMLResult[2169]);
    assertEquals('b', actualConvertToXMLResult[2175]);
    assertEquals('d', actualConvertToXMLResult[2181]);
    assertEquals('e', actualConvertToXMLResult[2182]);
    assertEquals('f', actualConvertToXMLResult[2183]);
    assertEquals('m', actualConvertToXMLResult[2170]);
    assertEquals('m', actualConvertToXMLResult[2177]);
    assertEquals('n', actualConvertToXMLResult[2178]);
    assertEquals('n', actualConvertToXMLResult[2185]);
    assertEquals('n', actualConvertToXMLResult[2190]);
    assertEquals('o', actualConvertToXMLResult[2189]);
    assertEquals('p', actualConvertToXMLResult[2176]);
    assertEquals('r', actualConvertToXMLResult[2168]);
    assertEquals('s', actualConvertToXMLResult[2191]);
    assertEquals('t', actualConvertToXMLResult[2187]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2195}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2195'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2195() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setOutputDataItem("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2195, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2176]);
    assertEquals('2', actualConvertToXMLResult[2181]);
    assertEquals(':', actualConvertToXMLResult[2182]);
    assertEquals('<', actualConvertToXMLResult[2175]);
    assertEquals('>', actualConvertToXMLResult[2173]);
    assertEquals('>', actualConvertToXMLResult[2194]);
    assertEquals('\n', actualConvertToXMLResult[2174]);
    assertEquals('a', actualConvertToXMLResult[2171]);
    assertEquals('b', actualConvertToXMLResult[2177]);
    assertEquals('d', actualConvertToXMLResult[2183]);
    assertEquals('e', actualConvertToXMLResult[2184]);
    assertEquals('f', actualConvertToXMLResult[2185]);
    assertEquals('m', actualConvertToXMLResult[2172]);
    assertEquals('n', actualConvertToXMLResult[2180]);
    assertEquals('n', actualConvertToXMLResult[2192]);
    assertEquals('o', actualConvertToXMLResult[2191]);
    assertEquals('p', actualConvertToXMLResult[2178]);
    assertEquals('r', actualConvertToXMLResult[2170]);
    assertEquals('s', actualConvertToXMLResult[2193]);
    assertEquals('t', actualConvertToXMLResult[2189]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2197}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2197'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2197() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setCompletionCondition("UTF-8");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2197, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2178]);
    assertEquals('2', actualConvertToXMLResult[2183]);
    assertEquals(':', actualConvertToXMLResult[2184]);
    assertEquals('<', actualConvertToXMLResult[2177]);
    assertEquals('>', actualConvertToXMLResult[2175]);
    assertEquals('>', actualConvertToXMLResult[2196]);
    assertEquals('\n', actualConvertToXMLResult[2176]);
    assertEquals('a', actualConvertToXMLResult[2173]);
    assertEquals('b', actualConvertToXMLResult[2179]);
    assertEquals('d', actualConvertToXMLResult[2185]);
    assertEquals('e', actualConvertToXMLResult[2186]);
    assertEquals('f', actualConvertToXMLResult[2187]);
    assertEquals('i', actualConvertToXMLResult[2192]);
    assertEquals('m', actualConvertToXMLResult[2174]);
    assertEquals('m', actualConvertToXMLResult[2181]);
    assertEquals('n', actualConvertToXMLResult[2182]);
    assertEquals('n', actualConvertToXMLResult[2189]);
    assertEquals('o', actualConvertToXMLResult[2193]);
    assertEquals('p', actualConvertToXMLResult[2180]);
    assertEquals('r', actualConvertToXMLResult[2172]);
    assertEquals('s', actualConvertToXMLResult[2195]);
    assertEquals('t', actualConvertToXMLResult[2191]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is {@code 2202}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is '2202'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIs2202() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");
    when(dataStore.getId()).thenReturn("42");
    doNothing().when(dataStore).setId(Mockito.<String>any());
    dataStore.setId(null);

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.put("UTF-8", dataStore);

    HashMap<String, Error> stringErrorMap = new HashMap<>();
    Error error = new Error("42", "UTF-8", "An error occurred");
    stringErrorMap.put("UTF-8", error);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(stringErrorMap);

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(dataStore).getId();
    verify(pool).getId();
    verify(process).getId();
    verify(pool).setId("42");
    verify(dataStore).setId(null);
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model, atLeast(1)).getFlowElement("UTF-8");
    verify(model).getFlowLocationGraphicInfo("UTF-8");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("UTF-8");
    verify(model).getLabelGraphicInfo("UTF-8");
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(2202, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[2183]);
    assertEquals('2', actualConvertToXMLResult[2188]);
    assertEquals(':', actualConvertToXMLResult[2189]);
    assertEquals('<', actualConvertToXMLResult[2182]);
    assertEquals('>', actualConvertToXMLResult[2180]);
    assertEquals('>', actualConvertToXMLResult[2201]);
    assertEquals('\n', actualConvertToXMLResult[2181]);
    assertEquals('a', actualConvertToXMLResult[2178]);
    assertEquals('b', actualConvertToXMLResult[2184]);
    assertEquals('d', actualConvertToXMLResult[2190]);
    assertEquals('e', actualConvertToXMLResult[2191]);
    assertEquals('f', actualConvertToXMLResult[2192]);
    assertEquals('i', actualConvertToXMLResult[2193]);
    assertEquals('i', actualConvertToXMLResult[2195]);
    assertEquals('i', actualConvertToXMLResult[2197]);
    assertEquals('m', actualConvertToXMLResult[2186]);
    assertEquals('n', actualConvertToXMLResult[2199]);
    assertEquals('o', actualConvertToXMLResult[2198]);
    assertEquals('p', actualConvertToXMLResult[2185]);
    assertEquals('r', actualConvertToXMLResult[2177]);
    assertEquals('s', actualConvertToXMLResult[2200]);
    assertEquals('t', actualConvertToXMLResult[2196]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return array length is nine hundred fifty-five.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; then return array length is nine hundred fifty-five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_thenReturnArrayLengthIsNineHundredFiftyFive() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("not empty");
    when(pool.getProcessRef()).thenReturn("not empty");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setId(Mockito.<String>any());
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setId("42");
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(new ArrayList<>());
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).setId(Mockito.<String>any());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("not empty");
    when(model.getPools()).thenReturn(poolList);
    when(model.getProcesses()).thenReturn(processList);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getDataStores()).thenReturn(new HashMap<>());
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    byte[] actualConvertToXMLResult = bpmnXMLConverter.convertToXML(model);

    // Assert
    verify(pool).getId();
    verify(pool).setId("42");
    verify(process).setId(null);
    verify(model).getDataStores();
    verify(model).getDefinitionsAttributes();
    verify(model).getErrors();
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMessageFlows();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getPools();
    verify(model, atLeast(1)).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElements();
    verify(process).getLanes();
    assertEquals(955, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[936]);
    assertEquals('2', actualConvertToXMLResult[941]);
    assertEquals(':', actualConvertToXMLResult[942]);
    assertEquals('<', actualConvertToXMLResult[935]);
    assertEquals('>', actualConvertToXMLResult[933]);
    assertEquals('>', actualConvertToXMLResult[954]);
    assertEquals('\n', actualConvertToXMLResult[934]);
    assertEquals('a', actualConvertToXMLResult[931]);
    assertEquals('b', actualConvertToXMLResult[937]);
    assertEquals('d', actualConvertToXMLResult[943]);
    assertEquals('e', actualConvertToXMLResult[944]);
    assertEquals('f', actualConvertToXMLResult[945]);
    assertEquals('i', actualConvertToXMLResult[946]);
    assertEquals('i', actualConvertToXMLResult[948]);
    assertEquals('i', actualConvertToXMLResult[950]);
    assertEquals('m', actualConvertToXMLResult[932]);
    assertEquals('m', actualConvertToXMLResult[939]);
    assertEquals('n', actualConvertToXMLResult[940]);
    assertEquals('n', actualConvertToXMLResult[947]);
    assertEquals('n', actualConvertToXMLResult[952]);
    assertEquals('o', actualConvertToXMLResult[951]);
    assertEquals('p', actualConvertToXMLResult[938]);
    assertEquals('r', actualConvertToXMLResult[930]);
    assertEquals('s', actualConvertToXMLResult[953]);
    assertEquals('t', actualConvertToXMLResult[949]);
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(Artifact, BpmnModel, XMLStreamWriter)} with {@code
   * artifact}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link Artifact}.
   *   <li>Then throw {@link XMLException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(Artifact, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(Artifact, BpmnModel, XMLStreamWriter) with 'artifact', 'model', 'xtw'; when Artifact; then throw XMLException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(Artifact, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithArtifactModelXtw_whenArtifact_thenThrowXMLException() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    Artifact artifact = mock(Artifact.class);
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.createXML(artifact, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw2() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementationType()).thenThrow(new XMLException("An error occurred"));
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2)));
    verify(writer).writeAttribute("event", "Event");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getImplementationType();
    verify(activitiListener).setEvent("not empty");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw3() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getStringValue()).thenThrow(new XMLException("An error occurred"));
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
    verify(fieldExtension).getFieldName();
    verify(fieldExtension).getStringValue();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw4() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLException("An error occurred")).when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("42");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension, atLeast(1)).getStringValue();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Event is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given ActivitiListener (default constructor) Event is 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenActivitiListenerEventIsNotEmpty()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("event", "not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Event is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given ActivitiListener (default constructor) Event is 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenActivitiListenerEventIsNotEmpty2()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("event", "not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} {@link ActivitiListener#getEvent()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given ActivitiListener getEvent() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenActivitiListenerGetEventReturnNull()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getEvent()).thenReturn(null);
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "userTask", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(activitiListener).getEvent();
    verify(activitiListener).setEvent("not empty");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given ArrayList() add FieldExtension (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenArrayListAddFieldExtension() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getStringValue()} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given FieldExtension getStringValue() return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenFieldExtensionGetStringValueReturn42()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("42");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension, atLeast(1)).getStringValue();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; then calls getExpression()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_thenCallsGetExpression() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getStringValue()).thenReturn(null);
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");
    doNothing().when(activitiListener).setEvent(Mockito.<String>any());
    activitiListener.setEvent("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    writer2.setIndentStep("bpmn2");

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("Expression");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(activitiListener).setEvent("not empty");
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension, atLeast(1)).getStringValue();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link FlowElement}.
   *   <li>Then throw {@link XMLException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when FlowElement; then throw XMLException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenFlowElement_thenThrowXMLException()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    FlowElement flowElement = mock(FlowElement.class);
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test new {@link BpmnXMLConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BpmnXMLConverter}
   */
  @Test
  @DisplayName("Test new BpmnXMLConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.<init>()"})
  void testNewBpmnXMLConverter() {
    // Arrange and Act
    BpmnXMLConverter actualBpmnXMLConverter = new BpmnXMLConverter();

    // Assert
    assertEquals("documentation", actualBpmnXMLConverter.documentationParser.getElementName());
    assertEquals("ioSpecification", actualBpmnXMLConverter.ioSpecificationParser.getElementName());
    assertEquals(
        "multiInstanceLoopCharacteristics",
        actualBpmnXMLConverter.multiInstanceParser.getElementName());
    assertNull(actualBpmnXMLConverter.classloader);
    assertNull(actualBpmnXMLConverter.startEventFormTypes);
    assertNull(actualBpmnXMLConverter.userTaskFormTypes);
  }
}
