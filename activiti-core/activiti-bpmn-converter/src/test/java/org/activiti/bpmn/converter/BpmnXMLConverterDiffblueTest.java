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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnXMLConverterDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter)}
   */
  @Test
  void testAddConverter() {
    // Arrange
    AssociationXMLConverter converter = mock(AssociationXMLConverter.class);
    when(converter.getXMLElementName()).thenReturn("Xml Element Name");
    Class<BaseElement> forNameResult = BaseElement.class;
    Mockito.<Class<? extends BaseElement>>when(converter.getBpmnElementType()).thenReturn(forNameResult);

    // Act
    BpmnXMLConverter.addConverter(converter);

    // Assert
    verify(converter).getBpmnElementType();
    verify(converter).getXMLElementName();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter, Class)}
   */
  @Test
  void testAddConverter2() {
    // Arrange
    AssociationXMLConverter converter = mock(AssociationXMLConverter.class);
    when(converter.getXMLElementName()).thenReturn("Xml Element Name");
    Class<BaseElement> elementType = BaseElement.class;

    // Act
    BpmnXMLConverter.addConverter(converter, elementType);

    // Assert
    verify(converter).getXMLElementName();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#validateModel(InputStreamProvider)}
   */
  @Test
  void testValidateModel() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenThrow(new XMLException("An error occurred"));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.validateModel(inputStreamProvider));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  void testConvertToBpmnModel() throws UnsupportedEncodingException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  void testConvertToBpmnModel2() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(0);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  void testConvertToBpmnModel3() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true));
    verify(dataInputStream, atLeast(1)).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  void testConvertToBpmnModel4() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read()).thenReturn(1);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, false));
    verify(dataInputStream, atLeast(1)).read(Mockito.<byte[]>any(), eq(0), anyInt());
    verify(dataInputStream, atLeast(1)).close();
    verify(dataInputStream, atLeast(1)).read();
    verify(inputStreamProvider, atLeast(1)).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  void testConvertToBpmnModel5() throws UnsupportedEncodingException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true, "UTF-8"));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  void testConvertToBpmnModel6() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(0);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true, "UTF-8"));
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  void testConvertToBpmnModel7() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(mock(DataInputStream.class));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true,
        "javax.xml.stream.isReplacingEntityReferences"));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  void testConvertToBpmnModel8() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, false, true, "UTF-8"));
    verify(dataInputStream, atLeast(1)).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  void testConvertToBpmnModel9() throws IOException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read()).thenReturn(1);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    doNothing().when(dataInputStream).close();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, false, "UTF-8"));
    verify(dataInputStream, atLeast(1)).read(Mockito.<byte[]>any(), eq(0), anyInt());
    verify(dataInputStream, atLeast(1)).close();
    verify(dataInputStream, atLeast(1)).read();
    verify(inputStreamProvider, atLeast(1)).getInputStream();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  void testProcessFlowElements() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    // Act
    bpmnXMLConverter.processFlowElements(flowElementList, new ActivitiListener());

    // Assert that nothing has changed
    verify(adhocSubProcess).getFlowElements();
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  void testGetFlowNodeFromScope() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("42", new ActivitiListener()));
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  void testGetFlowNodeFromScope2() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("", new ActivitiListener()));
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  void testGetFlowNodeFromScope3() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("42", new AdhocSubProcess()));
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML2() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addSignal(new Signal("42", "UTF-8"));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(678, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[659]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[664]);
    assertEquals(':', actualConvertToXMLResult[665]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[658]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[656]);
    assertEquals('>', actualConvertToXMLResult[677]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[657]);
    assertEquals('a', actualConvertToXMLResult[654]);
    assertEquals('b', actualConvertToXMLResult[660]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[666]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[667]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[669]);
    assertEquals('i', actualConvertToXMLResult[671]);
    assertEquals('i', actualConvertToXMLResult[673]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('l', actualConvertToXMLResult[655]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[662]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[653]);
    assertEquals('n', actualConvertToXMLResult[663]);
    assertEquals('n', actualConvertToXMLResult[670]);
    assertEquals('n', actualConvertToXMLResult[675]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[674]);
    assertEquals('p', actualConvertToXMLResult[661]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[676]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[672]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML3() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "", "UTF-8"));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(695, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[676]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[681]);
    assertEquals(':', actualConvertToXMLResult[682]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[675]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[673]);
    assertEquals('>', actualConvertToXMLResult[694]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[674]);
    assertEquals('a', actualConvertToXMLResult[670]);
    assertEquals('b', actualConvertToXMLResult[677]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[683]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[672]);
    assertEquals('e', actualConvertToXMLResult[684]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[685]);
    assertEquals('g', actualConvertToXMLResult[671]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[686]);
    assertEquals('i', actualConvertToXMLResult[688]);
    assertEquals('i', actualConvertToXMLResult[690]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[679]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[680]);
    assertEquals('n', actualConvertToXMLResult[687]);
    assertEquals('n', actualConvertToXMLResult[692]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[691]);
    assertEquals('p', actualConvertToXMLResult[678]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[693]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[689]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML4() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "UTF-8", ""));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(692, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[673]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[678]);
    assertEquals(':', actualConvertToXMLResult[679]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[672]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[670]);
    assertEquals('>', actualConvertToXMLResult[691]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[671]);
    assertEquals('a', actualConvertToXMLResult[667]);
    assertEquals('b', actualConvertToXMLResult[674]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[680]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[669]);
    assertEquals('e', actualConvertToXMLResult[681]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[682]);
    assertEquals('g', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[683]);
    assertEquals('i', actualConvertToXMLResult[685]);
    assertEquals('i', actualConvertToXMLResult[687]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[676]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[677]);
    assertEquals('n', actualConvertToXMLResult[684]);
    assertEquals('n', actualConvertToXMLResult[689]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[688]);
    assertEquals('p', actualConvertToXMLResult[675]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[690]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[686]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML5() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "  ");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML6() {
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
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  void testConvertToXML7() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("UTF-8");
    attribute.setNamespacePrefix("UTF-8");
    attribute.setValue("UTF-8");

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "UTF-8", "UTF-8"));
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model));
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML8() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML9() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addSignal(new Signal("42", "  "));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(675, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[656]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[661]);
    assertEquals(':', actualConvertToXMLResult[662]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[655]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[653]);
    assertEquals('>', actualConvertToXMLResult[674]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[654]);
    assertEquals('a', actualConvertToXMLResult[651]);
    assertEquals('b', actualConvertToXMLResult[657]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[663]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[664]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[665]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[666]);
    assertEquals('i', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[670]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('l', actualConvertToXMLResult[652]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[659]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[650]);
    assertEquals('n', actualConvertToXMLResult[660]);
    assertEquals('n', actualConvertToXMLResult[667]);
    assertEquals('n', actualConvertToXMLResult[672]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[671]);
    assertEquals('p', actualConvertToXMLResult[658]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[673]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[669]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML10() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "", "  "));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(692, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[673]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[678]);
    assertEquals(':', actualConvertToXMLResult[679]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[672]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[670]);
    assertEquals('>', actualConvertToXMLResult[691]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[671]);
    assertEquals('a', actualConvertToXMLResult[667]);
    assertEquals('b', actualConvertToXMLResult[674]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[680]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[669]);
    assertEquals('e', actualConvertToXMLResult[681]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[682]);
    assertEquals('g', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[683]);
    assertEquals('i', actualConvertToXMLResult[685]);
    assertEquals('i', actualConvertToXMLResult[687]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[676]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[677]);
    assertEquals('n', actualConvertToXMLResult[684]);
    assertEquals('n', actualConvertToXMLResult[689]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[688]);
    assertEquals('p', actualConvertToXMLResult[675]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[690]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[686]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML11() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "  ", ""));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(689, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[670]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[675]);
    assertEquals(':', actualConvertToXMLResult[676]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[669]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[667]);
    assertEquals('>', actualConvertToXMLResult[688]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('\n', actualConvertToXMLResult[668]);
    assertEquals('a', actualConvertToXMLResult[664]);
    assertEquals('b', actualConvertToXMLResult[671]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[677]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[666]);
    assertEquals('e', actualConvertToXMLResult[678]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[679]);
    assertEquals('g', actualConvertToXMLResult[665]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[680]);
    assertEquals('i', actualConvertToXMLResult[682]);
    assertEquals('i', actualConvertToXMLResult[684]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[673]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[674]);
    assertEquals('n', actualConvertToXMLResult[681]);
    assertEquals('n', actualConvertToXMLResult[686]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[685]);
    assertEquals('p', actualConvertToXMLResult[672]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[687]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[683]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML12() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "1.0");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[19]);
    assertEquals(' ', actualConvertToXMLResult[5]);
    assertEquals('.', actualConvertToXMLResult[Short.SIZE]);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('0', actualConvertToXMLResult[17]);
    assertEquals('1', actualConvertToXMLResult[15]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[0]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('=', actualConvertToXMLResult[13]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('?', actualConvertToXMLResult[1]);
    assertEquals('"', actualConvertToXMLResult[14]);
    assertEquals('"', actualConvertToXMLResult[18]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('c', actualConvertToXMLResult[22]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('d', actualConvertToXMLResult[Float.PRECISION]);
    assertEquals('e', actualConvertToXMLResult[20]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('e', actualConvertToXMLResult[7]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[10]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('l', actualConvertToXMLResult[4]);
    assertEquals('m', actualConvertToXMLResult[3]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[12]);
    assertEquals('n', actualConvertToXMLResult[21]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[11]);
    assertEquals('o', actualConvertToXMLResult[23]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('r', actualConvertToXMLResult[8]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('s', actualConvertToXMLResult[9]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
    assertEquals('v', actualConvertToXMLResult[6]);
    assertEquals('x', actualConvertToXMLResult[2]);
  }

  /**
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML13() {
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
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML14() {
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
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML15() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "  ", "  "));
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#createXML(Artifact, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testCreateXML() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    Artifact artifact = mock(Artifact.class);
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.createXML(artifact, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testCreateXML2() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataObject flowElement = new DataObject();
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link BpmnXMLConverter}
   */
  @Test
  void testNewBpmnXMLConverter() {
    // Arrange and Act
    BpmnXMLConverter actualBpmnXMLConverter = new BpmnXMLConverter();

    // Assert
    assertEquals("documentation", actualBpmnXMLConverter.documentationParser.getElementName());
    assertEquals("ioSpecification", actualBpmnXMLConverter.ioSpecificationParser.getElementName());
    assertEquals("multiInstanceLoopCharacteristics", actualBpmnXMLConverter.multiInstanceParser.getElementName());
    assertNull(actualBpmnXMLConverter.classloader);
    assertNull(actualBpmnXMLConverter.startEventFormTypes);
    assertNull(actualBpmnXMLConverter.userTaskFormTypes);
  }
}
