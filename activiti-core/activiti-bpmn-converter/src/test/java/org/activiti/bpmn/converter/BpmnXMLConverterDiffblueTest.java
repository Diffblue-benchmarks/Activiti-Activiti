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
import static org.mockito.Mockito.anyBoolean;
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
import javax.xml.stream.XMLStreamException;
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
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.bpmn.model.Transaction;
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

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.getId()).thenThrow(new XMLException("An error occurred"));
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer).writeStartElement("adHocSubProcess");
    verify(flowElement).getId();
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

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLException("An error occurred"))
        .when(writer)
        .writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCharacters("\n");
    verify(writer).writeStartElement("adHocSubProcess");
    verify(flowElement).getId();
    verify(flowElement).getDocumentation();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
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

    Association association = mock(Association.class);
    when(association.getId()).thenThrow(new XMLException("An error occurred"));

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
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

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(null);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw5() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCData(Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw6() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getName()).thenReturn("Name");
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLException("An error occurred"))
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer)
        .writeStartElement("bpmn2", "documentation", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw7() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setInputDataItem("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw8() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setLoopCardinality("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw9() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setCompletionCondition("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw10() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setElementVariable("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw11() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setLoopDataOutputRef("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw12() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setOutputDataItem("adHocSubProcess");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw13() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.isSequential())
        .thenThrow(new XMLException("An error occurred"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isNotExclusive()).thenReturn(false);
    when(flowElement.isAsynchronous()).thenReturn(true);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.createXML(flowElement, model, xtw));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeCData("not empty");
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement("bpmn2", "documentation", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(flowElement).isNotExclusive();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(multiInstanceLoopCharacteristics).isSequential();
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getFlowElements();
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
  void testCreateXMLWithFlowElementModelXtw14() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isNotExclusive()).thenThrow(new XMLException("An error occurred"));
    when(flowElement.isAsynchronous()).thenReturn(true);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);
    BpmnModel model = mock(BpmnModel.class);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.createXML(flowElement, model, xtw));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeStartElement("adHocSubProcess");
    verify(flowElement).getId();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(flowElement).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
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

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(activitiListenerList);
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCData(Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link EventSubProcess} (default constructor) Name is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given EventSubProcess (default constructor) Name is 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenEventSubProcessNameIsNotEmpty() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    EventSubProcess eventSubProcess = new EventSubProcess();
    eventSubProcess.setName("not empty");
    eventSubProcess.setAsynchronous(false);
    eventSubProcess.setExclusive(false);
    eventSubProcess.setDocumentation("not empty");
    eventSubProcess.addFlowElement(new AdhocSubProcess());
    eventSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(eventSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code adHocSubProcess} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given HashMap() 'adHocSubProcess' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenHashMapAdHocSubProcessIsArrayList()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("adHocSubProcess", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(stringListMap);
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association, atLeast(1)).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code adHocSubProcess} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given HashMap() 'adHocSubProcess' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenHashMapAdHocSubProcessIsArrayList2()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("adHocSubProcess", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(stringListMap);
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCData(Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(adhocSubProcess, atLeast(1)).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code adHocSubProcess} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given HashMap() 'adHocSubProcess' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenHashMapAdHocSubProcessIsArrayList3()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("adHocSubProcess", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(stringListMap);
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(adhocSubProcess, atLeast(1)).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code id} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given HashMap() 'id' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenHashMapIdIsArrayList() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("id", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(stringListMap);
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association, atLeast(1)).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link Association} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given LinkedHashSet() add Association (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenLinkedHashSetAddAssociation() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(flowElement).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link SubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given LinkedHashSet() add SubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenLinkedHashSetAddSubProcess() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link TextAnnotation} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given LinkedHashSet() add TextAnnotation (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenLinkedHashSetAddTextAnnotation() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new TextAnnotation());

    SubProcess subProcess = new SubProcess();
    subProcess.setName("not empty");
    subProcess.setAsynchronous(false);
    subProcess.setExclusive(false);
    subProcess.setDocumentation("not empty");
    subProcess.addFlowElement(new AdhocSubProcess());
    subProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(subProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(flowElement).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link Transaction} (default constructor) Name is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; given Transaction (default constructor) Name is 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_givenTransactionNameIsNotEmpty() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Transaction transaction = new Transaction();
    transaction.setName("not empty");
    transaction.setAsynchronous(false);
    transaction.setExclusive(false);
    transaction.setDocumentation("not empty");
    transaction.addFlowElement(new AdhocSubProcess());
    transaction.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(transaction);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(association).getId();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(flowElement).isAsynchronous();
    verify(flowElement).getArtifacts();
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#isSequential()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; then calls isSequential()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_thenCallsIsSequential() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.isSequential())
        .thenThrow(new XMLException("An error occurred"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.createXML(flowElement, model, xtw));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeCData("not empty");
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement("bpmn2", "documentation", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(multiInstanceLoopCharacteristics).isSequential();
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_thenThrowXMLStreamException() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getName()).thenReturn("Name");
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(null);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer)
        .writeStartElement("bpmn2", "documentation", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(model).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_thenThrowXMLStreamException2() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getName()).thenReturn("Name");
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("adHocSubProcess", new ArrayList<>());

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(stringListMap);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(null);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3)));
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(writer).writeCData("not empty");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer)
        .writeStartElement("bpmn2", "documentation", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(flowElement).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(flowElement, atLeast(1)).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(model).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when AdhocSubProcess (default constructor); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenAdhocSubProcess_thenCallsWriteAttribute()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    AdhocSubProcess flowElement = new AdhocSubProcess();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("adHocSubProcess");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenBooleanDataObject() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    BooleanDataObject flowElement = new BooleanDataObject();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "dataObject", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenBoundaryEvent() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    BoundaryEvent flowElement = new BoundaryEvent();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "boundaryEvent", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getNamespaces()} return {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when BpmnModel getNamespaces() return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenBpmnModelGetNamespacesReturnHashMap()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCData(Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getNamespaces()} return {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when BpmnModel getNamespaces() return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenBpmnModelGetNamespacesReturnHashMap2()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(adhocSubProcess).setDocumentation(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setName(Mockito.<String>any());
    doNothing().when(adhocSubProcess).setAsynchronous(anyBoolean());
    doNothing().when(adhocSubProcess).setExclusive(anyBoolean());
    doNothing().when(adhocSubProcess).addArtifact(Mockito.<Artifact>any());
    doNothing().when(adhocSubProcess).addFlowElement(Mockito.<FlowElement>any());
    adhocSubProcess.setName("not empty");
    adhocSubProcess.setAsynchronous(false);
    adhocSubProcess.setExclusive(false);
    adhocSubProcess.setDocumentation("not empty");
    adhocSubProcess.addFlowElement(new AdhocSubProcess());
    adhocSubProcess.addArtifact(new Association());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.isAsynchronous()).thenReturn(false);
    when(flowElement.getCompletionCondition()).thenReturn("not empty");
    when(flowElement.getDocumentation()).thenReturn("not empty");
    when(flowElement.getArtifacts()).thenReturn(artifactSet);
    when(flowElement.getFlowElements()).thenReturn(flowElementSet);
    when(flowElement.getLoopCharacteristics()).thenReturn(null);
    when(flowElement.getId()).thenReturn("42");
    when(flowElement.getName()).thenReturn("Name");
    when(flowElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(flowElement.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    bpmnXMLConverter.createXML(flowElement, model, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCData(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowElement).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(flowElement, atLeast(1)).getCompletionCondition();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(flowElement).getExtensionElements();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(flowElement).getId();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(model, atLeast(1)).getNamespaces();
    verify(flowElement, atLeast(1)).getDocumentation();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(flowElement).getExecutionListeners();
    verify(adhocSubProcess).getExecutionListeners();
    verify(flowElement, atLeast(1)).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).setDocumentation("not empty");
    verify(adhocSubProcess).setName("not empty");
    verify(flowElement).isAsynchronous();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(adhocSubProcess).setAsynchronous(false);
    verify(adhocSubProcess).setExclusive(false);
    verify(adhocSubProcess).addArtifact(isA(Artifact.class));
    verify(adhocSubProcess).addFlowElement(isA(FlowElement.class));
    verify(flowElement).getArtifacts();
    verify(adhocSubProcess).getArtifacts();
    verify(flowElement).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link BusinessRuleTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when BusinessRuleTask (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenBusinessRuleTask() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    BusinessRuleTask flowElement = new BusinessRuleTask();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement(
            "bpmn2", "businessRuleTask", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)} with {@code
   * flowElement}, {@code model}, {@code xtw}.
   *
   * <ul>
   *   <li>When {@link CallActivity} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when CallActivity (default constructor); then calls writeStartElement(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLConverter.createXML(FlowElement, BpmnModel, XMLStreamWriter)"})
  void testCreateXMLWithFlowElementModelXtw_whenCallActivity_thenCallsWriteStartElement()
      throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    CallActivity flowElement = new CallActivity();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter writer3 = new IndentingXMLStreamWriter(writer2);

    // Act
    bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(writer3));

    // Assert
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "callActivity", "http://www.omg.org/spec/BPMN/20100524/MODEL");
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
