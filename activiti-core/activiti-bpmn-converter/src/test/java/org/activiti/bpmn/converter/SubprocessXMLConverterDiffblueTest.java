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
import java.util.List;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.Test;

class SubprocessXMLConverterDiffblueTest {
  /**
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML2() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML3() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML4() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML5() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "  ");
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML6() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML7() {
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
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML8() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addMessage(new Message("42", "  ", "  "));
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Method under test:
   * {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  void testConvertToXML9() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addNamespace("  ", "  ");
    model.addMessage(new Message("42", "  ", "  "));
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  void testParseSubModels() {
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
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
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
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  void testParseSubModels2() {
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
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    BpmnModel getResult2 = actualParseSubModelsResult.get(1);
    Collection<Resource> resources2 = getResult2.getResources();
    assertTrue(resources2 instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
    Collection<Signal> signals2 = getResult2.getSignals();
    assertTrue(signals2 instanceof List);
    Process mainProcess = getResult.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertNull(getResult.getEventSupport());
    assertNull(getResult2.getEventSupport());
    assertNull(mainProcess.getId());
    assertNull(getResult.getSourceSystemId());
    assertNull(getResult2.getSourceSystemId());
    assertNull(getResult.getTargetNamespace());
    assertNull(getResult2.getTargetNamespace());
    assertNull(mainProcess.getDocumentation());
    assertNull(mainProcess.getName());
    assertNull(getResult.getStartEventFormTypes());
    assertNull(getResult2.getStartEventFormTypes());
    assertNull(getResult.getUserTaskFormTypes());
    assertNull(getResult2.getUserTaskFormTypes());
    assertNull(mainProcess.getInitialFlowElement());
    assertNull(mainProcess.getIoSpecification());
    assertEquals(0, mainProcess.getXmlColumnNumber());
    assertEquals(0, mainProcess.getXmlRowNumber());
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    List<Process> processes2 = getResult2.getProcesses();
    assertEquals(1, processes2.size());
    assertFalse(getResult.hasDiagramInterchangeInfo());
    assertFalse(getResult2.hasDiagramInterchangeInfo());
    assertFalse(mainProcess.isCandidateStarterGroupsDefined());
    assertFalse(mainProcess.isCandidateStarterUsersDefined());
    assertTrue(getResult.getMessages().isEmpty());
    assertTrue(getResult2.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(resources2.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(signals2.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getGlobalArtifacts().isEmpty());
    assertTrue(getResult2.getGlobalArtifacts().isEmpty());
    assertTrue(getResult.getImports().isEmpty());
    assertTrue(getResult2.getImports().isEmpty());
    assertTrue(getResult.getInterfaces().isEmpty());
    assertTrue(getResult2.getInterfaces().isEmpty());
    assertTrue(getResult.getPools().isEmpty());
    assertTrue(getResult2.getPools().isEmpty());
    assertTrue(mainProcess.getCandidateStarterGroups().isEmpty());
    assertTrue(mainProcess.getCandidateStarterUsers().isEmpty());
    assertTrue(mainProcess.getDataObjects().isEmpty());
    assertTrue(mainProcess.getEventListeners().isEmpty());
    assertTrue(mainProcess.getExecutionListeners().isEmpty());
    assertTrue(mainProcess.getLanes().isEmpty());
    assertTrue(mainProcess.getAttributes().isEmpty());
    assertTrue(mainProcess.getExtensionElements().isEmpty());
    assertTrue(getResult.getDataStores().isEmpty());
    assertTrue(getResult2.getDataStores().isEmpty());
    assertTrue(getResult.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult2.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult.getErrors().isEmpty());
    assertTrue(getResult2.getErrors().isEmpty());
    assertTrue(getResult.getFlowLocationMap().isEmpty());
    assertTrue(getResult2.getFlowLocationMap().isEmpty());
    assertTrue(getResult.getItemDefinitions().isEmpty());
    assertTrue(getResult2.getItemDefinitions().isEmpty());
    assertTrue(getResult.getLabelLocationMap().isEmpty());
    assertTrue(getResult2.getLabelLocationMap().isEmpty());
    assertTrue(getResult.getLocationMap().isEmpty());
    assertTrue(getResult2.getLocationMap().isEmpty());
    assertTrue(getResult.getMessageFlows().isEmpty());
    assertTrue(getResult2.getMessageFlows().isEmpty());
    assertTrue(getResult.getNamespaces().isEmpty());
    assertTrue(getResult2.getNamespaces().isEmpty());
    assertTrue(mainProcess.getFlowElementMap().isEmpty());
    assertTrue(mainProcess.isExecutable());
    assertSame(process, processes2.get(0));
    assertSame(process, getResult2.getMainProcess());
    assertSame(mainProcess, processes.get(0));
  }

  /**
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  void testParseSubModels3() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(1, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
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
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  void testParseSubModels4() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(3, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    BpmnModel getResult2 = actualParseSubModelsResult.get(1);
    Collection<Resource> resources2 = getResult2.getResources();
    assertTrue(resources2 instanceof List);
    BpmnModel getResult3 = actualParseSubModelsResult.get(2);
    Collection<Resource> resources3 = getResult3.getResources();
    assertTrue(resources3 instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
    Collection<Signal> signals2 = getResult2.getSignals();
    assertTrue(signals2 instanceof List);
    Collection<Signal> signals3 = getResult3.getSignals();
    assertTrue(signals3 instanceof List);
    Process mainProcess = getResult.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Process mainProcess2 = getResult2.getMainProcess();
    Collection<Artifact> artifacts2 = mainProcess2.getArtifacts();
    assertTrue(artifacts2 instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = mainProcess2.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertNull(getResult.getEventSupport());
    assertNull(getResult2.getEventSupport());
    assertNull(getResult3.getEventSupport());
    assertNull(mainProcess.getId());
    assertNull(mainProcess2.getId());
    assertNull(getResult.getSourceSystemId());
    assertNull(getResult2.getSourceSystemId());
    assertNull(getResult3.getSourceSystemId());
    assertNull(getResult.getTargetNamespace());
    assertNull(getResult2.getTargetNamespace());
    assertNull(getResult3.getTargetNamespace());
    assertNull(mainProcess.getDocumentation());
    assertNull(mainProcess2.getDocumentation());
    assertNull(mainProcess.getName());
    assertNull(mainProcess2.getName());
    assertNull(getResult.getStartEventFormTypes());
    assertNull(getResult2.getStartEventFormTypes());
    assertNull(getResult3.getStartEventFormTypes());
    assertNull(getResult.getUserTaskFormTypes());
    assertNull(getResult2.getUserTaskFormTypes());
    assertNull(getResult3.getUserTaskFormTypes());
    assertNull(mainProcess.getInitialFlowElement());
    assertNull(mainProcess2.getInitialFlowElement());
    assertNull(mainProcess.getIoSpecification());
    assertNull(mainProcess2.getIoSpecification());
    assertEquals(0, mainProcess.getXmlColumnNumber());
    assertEquals(0, mainProcess2.getXmlColumnNumber());
    assertEquals(0, mainProcess.getXmlRowNumber());
    assertEquals(0, mainProcess2.getXmlRowNumber());
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    List<Process> processes2 = getResult2.getProcesses();
    assertEquals(1, processes2.size());
    List<Process> processes3 = getResult3.getProcesses();
    assertEquals(1, processes3.size());
    assertFalse(getResult.hasDiagramInterchangeInfo());
    assertFalse(getResult2.hasDiagramInterchangeInfo());
    assertFalse(getResult3.hasDiagramInterchangeInfo());
    assertFalse(mainProcess.isCandidateStarterGroupsDefined());
    assertFalse(mainProcess2.isCandidateStarterGroupsDefined());
    assertFalse(mainProcess.isCandidateStarterUsersDefined());
    assertFalse(mainProcess2.isCandidateStarterUsersDefined());
    assertTrue(getResult.getMessages().isEmpty());
    assertTrue(getResult2.getMessages().isEmpty());
    assertTrue(getResult3.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(resources2.isEmpty());
    assertTrue(resources3.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(signals2.isEmpty());
    assertTrue(signals3.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(artifacts2.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getGlobalArtifacts().isEmpty());
    assertTrue(getResult2.getGlobalArtifacts().isEmpty());
    assertTrue(getResult3.getGlobalArtifacts().isEmpty());
    assertTrue(getResult.getImports().isEmpty());
    assertTrue(getResult2.getImports().isEmpty());
    assertTrue(getResult3.getImports().isEmpty());
    assertTrue(getResult.getInterfaces().isEmpty());
    assertTrue(getResult2.getInterfaces().isEmpty());
    assertTrue(getResult3.getInterfaces().isEmpty());
    assertTrue(getResult.getPools().isEmpty());
    assertTrue(getResult2.getPools().isEmpty());
    assertTrue(getResult3.getPools().isEmpty());
    assertTrue(mainProcess.getCandidateStarterGroups().isEmpty());
    assertTrue(mainProcess2.getCandidateStarterGroups().isEmpty());
    assertTrue(mainProcess.getCandidateStarterUsers().isEmpty());
    assertTrue(mainProcess2.getCandidateStarterUsers().isEmpty());
    assertTrue(mainProcess.getDataObjects().isEmpty());
    assertTrue(mainProcess2.getDataObjects().isEmpty());
    assertTrue(mainProcess.getEventListeners().isEmpty());
    assertTrue(mainProcess2.getEventListeners().isEmpty());
    assertTrue(mainProcess.getExecutionListeners().isEmpty());
    assertTrue(mainProcess2.getExecutionListeners().isEmpty());
    assertTrue(mainProcess.getLanes().isEmpty());
    assertTrue(mainProcess2.getLanes().isEmpty());
    assertTrue(mainProcess.getAttributes().isEmpty());
    assertTrue(mainProcess2.getAttributes().isEmpty());
    assertTrue(mainProcess.getExtensionElements().isEmpty());
    assertTrue(mainProcess2.getExtensionElements().isEmpty());
    assertTrue(getResult.getDataStores().isEmpty());
    assertTrue(getResult2.getDataStores().isEmpty());
    assertTrue(getResult3.getDataStores().isEmpty());
    assertTrue(getResult.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult2.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult3.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult.getErrors().isEmpty());
    assertTrue(getResult2.getErrors().isEmpty());
    assertTrue(getResult3.getErrors().isEmpty());
    assertTrue(getResult.getFlowLocationMap().isEmpty());
    assertTrue(getResult2.getFlowLocationMap().isEmpty());
    assertTrue(getResult3.getFlowLocationMap().isEmpty());
    assertTrue(getResult.getItemDefinitions().isEmpty());
    assertTrue(getResult2.getItemDefinitions().isEmpty());
    assertTrue(getResult3.getItemDefinitions().isEmpty());
    assertTrue(getResult.getLabelLocationMap().isEmpty());
    assertTrue(getResult2.getLabelLocationMap().isEmpty());
    assertTrue(getResult3.getLabelLocationMap().isEmpty());
    assertTrue(getResult.getLocationMap().isEmpty());
    assertTrue(getResult2.getLocationMap().isEmpty());
    assertTrue(getResult3.getLocationMap().isEmpty());
    assertTrue(getResult.getMessageFlows().isEmpty());
    assertTrue(getResult2.getMessageFlows().isEmpty());
    assertTrue(getResult3.getMessageFlows().isEmpty());
    assertTrue(getResult.getNamespaces().isEmpty());
    assertTrue(getResult2.getNamespaces().isEmpty());
    assertTrue(getResult3.getNamespaces().isEmpty());
    assertTrue(mainProcess.getFlowElementMap().isEmpty());
    assertTrue(mainProcess2.getFlowElementMap().isEmpty());
    assertTrue(mainProcess.isExecutable());
    assertTrue(mainProcess2.isExecutable());
    assertSame(element2, ((List<FlowElement>) flowElements2).get(0));
    assertSame(process, processes3.get(0));
    assertSame(process, getResult3.getMainProcess());
    assertSame(mainProcess, processes.get(0));
    assertSame(mainProcess2, processes2.get(0));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link SubprocessXMLConverter}
   */
  @Test
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
