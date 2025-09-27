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
import javax.management.loading.MLet;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.util.StreamReaderDelegate;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
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
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getFlowElement(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given AdhocSubProcess getFlowElement(String) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenAdhocSubProcessGetFlowElementReturnNull() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setAttachedToRefId("not empty");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElement(Mockito.<String>any())).thenReturn(null);
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
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link BoundaryEvent}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test processFlowElements(Collection, BaseElement); given SubProcess (default constructor) addFlowElement BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.processFlowElements(Collection, BaseElement)"})
  void testProcessFlowElements_givenSubProcessAddFlowElementBoundaryEvent() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    BoundaryEvent element = new BoundaryEvent();
    element.setAttachedToRefId("not empty");

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

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
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'null'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsNull_whenEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

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
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getFlowNodeFromScope(String, BaseElement); given AdhocSubProcess (default constructor) Id is 'null'; when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowNode BpmnXMLConverter.getFlowNodeFromScope(String, BaseElement)"
  })
  void testGetFlowNodeFromScope_givenAdhocSubProcessIdIsNull_whenNull_thenReturnNull() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

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

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8", "UTF-8");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
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

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8");
    attribute.setNamespacePrefix("UTF-8");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addNamespace("UTF-8", "UTF-8");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
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

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8");
    attribute.setNamespacePrefix("bpmn2");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addNamespace("UTF-8", "UTF-8");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
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

    ExtensionAttribute attribute = new ExtensionAttribute("  ", "  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
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

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addNamespace("  ", "  ");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
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

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("bpmn2");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addNamespace("  ", "  ");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given {@code bpmn2}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given 'bpmn2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenBpmn2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addNamespace("bpmn2", "  ");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_givenEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "  ");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>When space space.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; when space space")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_whenSpaceSpace() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ", "  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "  "));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@code bpmn2}.
   *   <li>When {@link BpmnModel} (default constructor) addNamespace {@code bpmn2} and {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; given 'bpmn2'; when BpmnModel (default constructor) addNamespace 'bpmn2' and 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_givenBpmn2_whenBpmnModelAddNamespaceBpmn2AndUtf8() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8");
    attribute.setNamespacePrefix("UTF-8");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addNamespace("bpmn2", "UTF-8");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor) addNamespace empty string and {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel) with 'model'; when BpmnModel (default constructor) addNamespace empty string and 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] BpmnXMLConverter.convertToXML(BpmnModel)"})
  void testConvertToXMLWithModel_whenBpmnModelAddNamespaceEmptyStringAndUtf8() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8");
    attribute.setNamespacePrefix("UTF-8");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "UTF-8");
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
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
    bpmnXMLConverter.setClassloader(new MLet());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer));

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
    bpmnXMLConverter.setClassloader(new MLet());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLException("An error occurred"))
        .when(writer)
        .writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeCharacters("\n");
    verify(writer)
        .writeStartElement("bpmn2", "userTask", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(activitiListener).getEvent();
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
    bpmnXMLConverter.setClassloader(new MLet());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementationType()).thenThrow(new XMLException("An error occurred"));
    when(activitiListener.getEvent()).thenReturn("Event");

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

    // Act and Assert
    assertThrows(
        XMLException.class,
        () -> bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("event", "Event");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getImplementationType();
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

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given ArrayList() add ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenArrayListAddActivitiListener() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    bpmnXMLConverter.setClassloader(new MLet());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "userTask", "http://www.omg.org/spec/BPMN/20100524/MODEL");
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
    bpmnXMLConverter.setClassloader(new MLet());

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(taskListeners);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer));

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
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link UserTask} (default constructor) TaskListeners is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given 'null'; when UserTask (default constructor) TaskListeners is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenNull_whenUserTaskTaskListenersIsNull()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    bpmnXMLConverter.setClassloader(new MLet());

    UserTask flowElement = new UserTask();
    flowElement.setTaskListeners(null);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "userTask", "http://www.omg.org/spec/BPMN/20100524/MODEL");
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
