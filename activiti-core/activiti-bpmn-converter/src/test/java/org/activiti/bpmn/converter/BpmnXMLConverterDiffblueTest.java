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
import java.util.HashMap;
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
import org.activiti.bpmn.model.SubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnXMLConverterDiffblueTest {
  /**
   * Test {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter, Class)} with
   * {@code converter}, {@code elementType}.
   * <ul>
   *   <li>Then calls {@link AssociationXMLConverter#getXMLElementName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter, Class)}
   */
  @Test
  @DisplayName("Test addConverter(BaseBpmnXMLConverter, Class) with 'converter', 'elementType'; then calls getXMLElementName()")
  void testAddConverterWithConverterElementType_thenCallsGetXMLElementName() {
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
   * Test {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter)} with
   * {@code converter}.
   * <ul>
   *   <li>Given {@code Xml Element Name}.</li>
   *   <li>Then calls {@link AssociationXMLConverter#getBpmnElementType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#addConverter(BaseBpmnXMLConverter)}
   */
  @Test
  @DisplayName("Test addConverter(BaseBpmnXMLConverter) with 'converter'; given 'Xml Element Name'; then calls getBpmnElementType()")
  void testAddConverterWithConverter_givenXmlElementName_thenCallsGetBpmnElementType() {
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
   * Test {@link BpmnXMLConverter#validateModel(InputStreamProvider)} with
   * {@code inputStreamProvider}.
   * <ul>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#validateModel(InputStreamProvider)}
   */
  @Test
  @DisplayName("Test validateModel(InputStreamProvider) with 'inputStreamProvider'; then throw XMLException")
  void testValidateModelWithInputStreamProvider_thenThrowXMLException() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenThrow(new XMLException("An error occurred"));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.validateModel(inputStreamProvider));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    InputStreamProvider inputStreamProvider = mock(InputStreamProvider.class);
    when(inputStreamProvider.getInputStream()).thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(XMLException.class, () -> bpmnXMLConverter.convertToBpmnModel(inputStreamProvider, true, true));
    verify(inputStreamProvider).getInputStream();
  }

  /**
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml2() throws IOException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml3() throws IOException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXml4() throws IOException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}, {@code encoding}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding()
      throws UnsupportedEncodingException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}, {@code encoding}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding2() throws IOException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}, {@code encoding}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding3() {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}, {@code encoding}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding4() throws IOException {
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
   * Test
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   * with {@code inputStreamProvider}, {@code validateSchema},
   * {@code enableSafeBpmnXml}, {@code encoding}.
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#convertToBpmnModel(InputStreamProvider, boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test convertToBpmnModel(InputStreamProvider, boolean, boolean, String) with 'inputStreamProvider', 'validateSchema', 'enableSafeBpmnXml', 'encoding'")
  void testConvertToBpmnModelWithInputStreamProviderValidateSchemaEnableSafeBpmnXmlEncoding5() throws IOException {
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
   * Test {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}.
   * <ul>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#processFlowElements(Collection, BaseElement)}
   */
  @Test
  @DisplayName("Test processFlowElements(Collection, BaseElement); then calls getFlowElements()")
  void testProcessFlowElements_thenCallsGetFlowElements() {
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
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName("Test getFlowNodeFromScope(String, BaseElement); when '42'")
  void testGetFlowNodeFromScope_when42() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("42", new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName("Test getFlowNodeFromScope(String, BaseElement); when AdhocSubProcess (default constructor)")
  void testGetFlowNodeFromScope_whenAdhocSubProcess() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("42", new AdhocSubProcess()));
  }

  /**
   * Test {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#getFlowNodeFromScope(String, BaseElement)}
   */
  @Test
  @DisplayName("Test getFlowNodeFromScope(String, BaseElement); when empty string")
  void testGetFlowNodeFromScope_whenEmptyString() {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    // Act and Assert
    assertNull(bpmnXMLConverter.getFlowNodeFromScope("", new ActivitiListener()));
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
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
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
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
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'")
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
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given empty string")
  void testConvertToXMLWithModelEncoding_givenEmptyString() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "1.0");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred ninety-eight")
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredNinetyEight() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    model.addMessage(message);
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(698, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[679]);
    assertEquals('2', actualConvertToXMLResult[684]);
    assertEquals(':', actualConvertToXMLResult[685]);
    assertEquals('<', actualConvertToXMLResult[678]);
    assertEquals('>', actualConvertToXMLResult[676]);
    assertEquals('>', actualConvertToXMLResult[697]);
    assertEquals('\n', actualConvertToXMLResult[677]);
    assertEquals('a', actualConvertToXMLResult[673]);
    assertEquals('b', actualConvertToXMLResult[680]);
    assertEquals('d', actualConvertToXMLResult[686]);
    assertEquals('e', actualConvertToXMLResult[675]);
    assertEquals('e', actualConvertToXMLResult[687]);
    assertEquals('f', actualConvertToXMLResult[688]);
    assertEquals('g', actualConvertToXMLResult[674]);
    assertEquals('i', actualConvertToXMLResult[689]);
    assertEquals('i', actualConvertToXMLResult[691]);
    assertEquals('i', actualConvertToXMLResult[693]);
    assertEquals('m', actualConvertToXMLResult[682]);
    assertEquals('n', actualConvertToXMLResult[690]);
    assertEquals('n', actualConvertToXMLResult[695]);
    assertEquals('o', actualConvertToXMLResult[694]);
    assertEquals('p', actualConvertToXMLResult[681]);
    assertEquals('s', actualConvertToXMLResult[696]);
    assertEquals('t', actualConvertToXMLResult[692]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred ninety-one")
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredNinetyOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    model.addMessage(message);
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(691, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[672]);
    assertEquals('2', actualConvertToXMLResult[677]);
    assertEquals(':', actualConvertToXMLResult[678]);
    assertEquals('<', actualConvertToXMLResult[671]);
    assertEquals('>', actualConvertToXMLResult[669]);
    assertEquals('>', actualConvertToXMLResult[690]);
    assertEquals('\n', actualConvertToXMLResult[670]);
    assertEquals('a', actualConvertToXMLResult[666]);
    assertEquals('b', actualConvertToXMLResult[673]);
    assertEquals('d', actualConvertToXMLResult[679]);
    assertEquals('e', actualConvertToXMLResult[668]);
    assertEquals('e', actualConvertToXMLResult[680]);
    assertEquals('f', actualConvertToXMLResult[681]);
    assertEquals('g', actualConvertToXMLResult[667]);
    assertEquals('i', actualConvertToXMLResult[682]);
    assertEquals('i', actualConvertToXMLResult[684]);
    assertEquals('i', actualConvertToXMLResult[686]);
    assertEquals('m', actualConvertToXMLResult[675]);
    assertEquals('n', actualConvertToXMLResult[676]);
    assertEquals('n', actualConvertToXMLResult[688]);
    assertEquals('o', actualConvertToXMLResult[687]);
    assertEquals('p', actualConvertToXMLResult[674]);
    assertEquals('s', actualConvertToXMLResult[689]);
    assertEquals('t', actualConvertToXMLResult[685]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred seventy-five.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred seventy-five")
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredSeventyFive() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addSignal(new Signal("42", "  "));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(675, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[656]);
    assertEquals('2', actualConvertToXMLResult[661]);
    assertEquals(':', actualConvertToXMLResult[662]);
    assertEquals('<', actualConvertToXMLResult[655]);
    assertEquals('>', actualConvertToXMLResult[653]);
    assertEquals('>', actualConvertToXMLResult[674]);
    assertEquals('\n', actualConvertToXMLResult[654]);
    assertEquals('a', actualConvertToXMLResult[651]);
    assertEquals('b', actualConvertToXMLResult[657]);
    assertEquals('d', actualConvertToXMLResult[663]);
    assertEquals('e', actualConvertToXMLResult[664]);
    assertEquals('f', actualConvertToXMLResult[665]);
    assertEquals('i', actualConvertToXMLResult[666]);
    assertEquals('i', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[670]);
    assertEquals('l', actualConvertToXMLResult[652]);
    assertEquals('m', actualConvertToXMLResult[659]);
    assertEquals('n', actualConvertToXMLResult[650]);
    assertEquals('n', actualConvertToXMLResult[660]);
    assertEquals('n', actualConvertToXMLResult[667]);
    assertEquals('n', actualConvertToXMLResult[672]);
    assertEquals('o', actualConvertToXMLResult[671]);
    assertEquals('p', actualConvertToXMLResult[658]);
    assertEquals('s', actualConvertToXMLResult[673]);
    assertEquals('t', actualConvertToXMLResult[669]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred thirty-six.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred thirty-six")
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredThirtySix() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then throw XMLException")
  void testConvertToXMLWithModelEncoding_thenThrowXMLException() {
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
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>When space space.</li>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; when space space; then throw XMLException")
  void testConvertToXMLWithModelEncoding_whenSpaceSpace_thenThrowXMLException() {
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
   * <ul>
   *   <li>Given {@code bpmn2}.</li>
   *   <li>When {@link BpmnModel} (default constructor) addNamespace {@code bpmn2}
   * and {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; given 'bpmn2'; when BpmnModel (default constructor) addNamespace 'bpmn2' and 'UTF-8'")
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
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; given empty string")
  void testConvertToXMLWithModel_givenEmptyString() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "  ");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is six hundred ninety-eight")
  void testConvertToXMLWithModel_thenReturnArrayLengthIsSixHundredNinetyEight() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    model.addMessage(message);
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(698, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[679]);
    assertEquals('2', actualConvertToXMLResult[684]);
    assertEquals(':', actualConvertToXMLResult[685]);
    assertEquals('<', actualConvertToXMLResult[678]);
    assertEquals('>', actualConvertToXMLResult[676]);
    assertEquals('>', actualConvertToXMLResult[697]);
    assertEquals('\n', actualConvertToXMLResult[677]);
    assertEquals('a', actualConvertToXMLResult[673]);
    assertEquals('b', actualConvertToXMLResult[680]);
    assertEquals('d', actualConvertToXMLResult[686]);
    assertEquals('e', actualConvertToXMLResult[675]);
    assertEquals('e', actualConvertToXMLResult[687]);
    assertEquals('f', actualConvertToXMLResult[688]);
    assertEquals('g', actualConvertToXMLResult[674]);
    assertEquals('i', actualConvertToXMLResult[689]);
    assertEquals('i', actualConvertToXMLResult[691]);
    assertEquals('i', actualConvertToXMLResult[693]);
    assertEquals('m', actualConvertToXMLResult[682]);
    assertEquals('n', actualConvertToXMLResult[690]);
    assertEquals('n', actualConvertToXMLResult[695]);
    assertEquals('o', actualConvertToXMLResult[694]);
    assertEquals('p', actualConvertToXMLResult[681]);
    assertEquals('s', actualConvertToXMLResult[696]);
    assertEquals('t', actualConvertToXMLResult[692]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is six hundred ninety-one")
  void testConvertToXMLWithModel_thenReturnArrayLengthIsSixHundredNinetyOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    model.addMessage(message);
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(691, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[672]);
    assertEquals('2', actualConvertToXMLResult[677]);
    assertEquals(':', actualConvertToXMLResult[678]);
    assertEquals('<', actualConvertToXMLResult[671]);
    assertEquals('>', actualConvertToXMLResult[669]);
    assertEquals('>', actualConvertToXMLResult[690]);
    assertEquals('\n', actualConvertToXMLResult[670]);
    assertEquals('a', actualConvertToXMLResult[666]);
    assertEquals('b', actualConvertToXMLResult[673]);
    assertEquals('d', actualConvertToXMLResult[679]);
    assertEquals('e', actualConvertToXMLResult[668]);
    assertEquals('e', actualConvertToXMLResult[680]);
    assertEquals('f', actualConvertToXMLResult[681]);
    assertEquals('g', actualConvertToXMLResult[667]);
    assertEquals('i', actualConvertToXMLResult[682]);
    assertEquals('i', actualConvertToXMLResult[684]);
    assertEquals('i', actualConvertToXMLResult[686]);
    assertEquals('m', actualConvertToXMLResult[675]);
    assertEquals('n', actualConvertToXMLResult[676]);
    assertEquals('n', actualConvertToXMLResult[688]);
    assertEquals('o', actualConvertToXMLResult[687]);
    assertEquals('p', actualConvertToXMLResult[674]);
    assertEquals('s', actualConvertToXMLResult[689]);
    assertEquals('t', actualConvertToXMLResult[685]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   * <ul>
   *   <li>Then return array length is six hundred seventy-eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is six hundred seventy-eight")
  void testConvertToXMLWithModel_thenReturnArrayLengthIsSixHundredSeventyEight() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addSignal(new Signal("42", "UTF-8"));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(678, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[659]);
    assertEquals('2', actualConvertToXMLResult[664]);
    assertEquals(':', actualConvertToXMLResult[665]);
    assertEquals('<', actualConvertToXMLResult[658]);
    assertEquals('>', actualConvertToXMLResult[656]);
    assertEquals('>', actualConvertToXMLResult[677]);
    assertEquals('\n', actualConvertToXMLResult[657]);
    assertEquals('a', actualConvertToXMLResult[654]);
    assertEquals('b', actualConvertToXMLResult[660]);
    assertEquals('d', actualConvertToXMLResult[666]);
    assertEquals('e', actualConvertToXMLResult[667]);
    assertEquals('f', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[669]);
    assertEquals('i', actualConvertToXMLResult[671]);
    assertEquals('i', actualConvertToXMLResult[673]);
    assertEquals('l', actualConvertToXMLResult[655]);
    assertEquals('m', actualConvertToXMLResult[662]);
    assertEquals('n', actualConvertToXMLResult[653]);
    assertEquals('n', actualConvertToXMLResult[663]);
    assertEquals('n', actualConvertToXMLResult[670]);
    assertEquals('n', actualConvertToXMLResult[675]);
    assertEquals('o', actualConvertToXMLResult[674]);
    assertEquals('p', actualConvertToXMLResult[661]);
    assertEquals('s', actualConvertToXMLResult[676]);
    assertEquals('t', actualConvertToXMLResult[672]);
  }

  /**
   * Test {@link BpmnXMLConverter#convertToXML(BpmnModel)} with {@code model}.
   * <ul>
   *   <li>Then return array length is six hundred thirty-six.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLConverter#convertToXML(BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel) with 'model'; then return array length is six hundred thirty-six")
  void testConvertToXMLWithModel_thenReturnArrayLengthIsSixHundredThirtySix() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model);

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('e', actualConvertToXMLResult[625]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
  }

  /**
   * Test {@link BpmnXMLConverter#createXML(Artifact, BpmnModel, XMLStreamWriter)}
   * with {@code artifact}, {@code model}, {@code xtw}.
   * <ul>
   *   <li>When {@link Artifact}.</li>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#createXML(Artifact, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test createXML(Artifact, BpmnModel, XMLStreamWriter) with 'artifact', 'model', 'xtw'; when Artifact; then throw XMLException")
  void testCreateXMLWithArtifactModelXtw_whenArtifact_thenThrowXMLException() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    Artifact artifact = mock(Artifact.class);
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.createXML(artifact, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)}
   * with {@code flowElement}, {@code model}, {@code xtw}.
   * <ul>
   *   <li>When {@link DataObject} (default constructor).</li>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLConverter#createXML(FlowElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test createXML(FlowElement, BpmnModel, XMLStreamWriter) with 'flowElement', 'model', 'xtw'; when DataObject (default constructor); then throw XMLException")
  void testCreateXMLWithFlowElementModelXtw_whenDataObject_thenThrowXMLException() throws Exception {
    // Arrange
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    DataObject flowElement = new DataObject();
    BpmnModel model = new BpmnModel();

    // Act and Assert
    assertThrows(XMLException.class,
        () -> bpmnXMLConverter.createXML(flowElement, model, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test new {@link BpmnXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BpmnXMLConverter}
   */
  @Test
  @DisplayName("Test new BpmnXMLConverter (default constructor)")
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
