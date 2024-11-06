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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubprocessXMLConverterDiffblueTest {
  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; given empty string")
  void testConvertToXMLWithModelEncoding_givenEmptyString() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "  ");
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
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-eight.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
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
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred ninety-one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
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
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred seventy-five.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
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
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then return array length is six hundred thirty-six.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
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
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then throw XMLException")
  void testConvertToXMLWithModelEncoding_thenThrowXMLException() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ", "  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with
   * {@code model}, {@code encoding}.
   * <ul>
   *   <li>When space space.</li>
   *   <li>Then throw {@link XMLException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'; when space space; then throw XMLException")
  void testConvertToXMLWithModelEncoding_whenSpaceSpace_thenThrowXMLException() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ", "  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "  "));
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with
   * {@code model}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return first Processes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName("Test parseSubModels(BpmnModel) with 'model'; given Process (default constructor); then return first Processes size is one")
  void testParseSubModelsWithModel_givenProcess_thenReturnFirstProcessesSizeIsOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Process process = new Process();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(1, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with
   * {@code model}.
   * <ul>
   *   <li>Then return first MainProcess FlowElements size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName("Test parseSubModels(BpmnModel) with 'model'; then return first MainProcess FlowElements size is one")
  void testParseSubModelsWithModel_thenReturnFirstMainProcessFlowElementsSizeIsOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
    process.addFlowElement(element);

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(1, actualParseSubModelsResult.size());
    Collection<FlowElement> flowElements = actualParseSubModelsResult.get(0).getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with
   * {@code model}.
   * <ul>
   *   <li>Then return size is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName("Test parseSubModels(BpmnModel) with 'model'; then return size is three")
  void testParseSubModelsWithModel_thenReturnSizeIsThree() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(3, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(2);
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
    Collection<FlowElement> flowElements = actualParseSubModelsResult.get(1).getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult2 = ((List<FlowElement>) flowElements).get(0);
    assertTrue(getResult2 instanceof AdhocSubProcess);
    assertNull(getResult.getEventSupport());
    assertNull(getResult.getSourceSystemId());
    assertNull(getResult.getTargetNamespace());
    assertNull(getResult.getStartEventFormTypes());
    assertNull(getResult.getUserTaskFormTypes());
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertFalse(getResult.hasDiagramInterchangeInfo());
    assertTrue(getResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getGlobalArtifacts().isEmpty());
    assertTrue(getResult.getImports().isEmpty());
    assertTrue(getResult.getInterfaces().isEmpty());
    assertTrue(getResult.getPools().isEmpty());
    assertTrue(getResult.getDataStores().isEmpty());
    assertTrue(getResult.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult.getErrors().isEmpty());
    assertTrue(getResult.getFlowLocationMap().isEmpty());
    assertTrue(getResult.getItemDefinitions().isEmpty());
    assertTrue(getResult.getLabelLocationMap().isEmpty());
    assertTrue(getResult.getLocationMap().isEmpty());
    assertTrue(getResult.getMessageFlows().isEmpty());
    assertTrue(getResult.getNamespaces().isEmpty());
    assertSame(element, getResult2.getParentContainer());
    assertSame(element, getResult2.getSubProcess());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with
   * {@code model}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName("Test parseSubModels(BpmnModel) with 'model'; then return size is two")
  void testParseSubModelsWithModel_thenReturnSizeIsTwo() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(2, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(1);
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test new {@link SubprocessXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link SubprocessXMLConverter}
   */
  @Test
  @DisplayName("Test new SubprocessXMLConverter (default constructor)")
  void testNewSubprocessXMLConverter() {
    // Arrange and Act
    SubprocessXMLConverter actualSubprocessXMLConverter = new SubprocessXMLConverter();

    // Assert
    assertEquals("documentation", actualSubprocessXMLConverter.documentationParser.getElementName());
    assertEquals("ioSpecification", actualSubprocessXMLConverter.ioSpecificationParser.getElementName());
    assertEquals("multiInstanceLoopCharacteristics", actualSubprocessXMLConverter.multiInstanceParser.getElementName());
    assertNull(actualSubprocessXMLConverter.classloader);
    assertNull(actualSubprocessXMLConverter.startEventFormTypes);
    assertNull(actualSubprocessXMLConverter.userTaskFormTypes);
  }
}
