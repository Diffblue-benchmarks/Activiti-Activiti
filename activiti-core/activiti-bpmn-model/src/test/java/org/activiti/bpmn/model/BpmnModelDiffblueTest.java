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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class BpmnModelDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(bpmnModel.getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue3() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertNull(bpmnModel.getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act
    bpmnModel.addDefinitionsAttribute(attribute);

    // Assert
    Map<String, List<ExtensionAttribute>> definitionsAttributes = bpmnModel.getDefinitionsAttributes();
    assertEquals(1, definitionsAttributes.size());
    List<ExtensionAttribute> getResult = definitionsAttributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    bpmnModel.addDefinitionsAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");

    // Act
    bpmnModel.addDefinitionsAttribute(attribute2);

    // Assert
    Map<String, List<ExtensionAttribute>> definitionsAttributes = bpmnModel.getDefinitionsAttributes();
    assertEquals(1, definitionsAttributes.size());
    List<ExtensionAttribute> getResult = definitionsAttributes.get("Name");
    assertEquals(2, getResult.size());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult.get(1));
  }

  /**
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute(null));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute(""));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute5() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#getMainProcess()}
   */
  @Test
  public void testGetMainProcess() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMainProcess());
  }

  /**
   * Method under test: {@link BpmnModel#getMainProcess()}
   */
  @Test
  public void testGetMainProcess2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getProcess("Pool Ref"));
  }

  /**
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getProcess("Pool Ref"));
  }

  /**
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getProcess(null));
  }

  /**
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getProcessById("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById2() {
    // Arrange
    Process process = new Process();
    process.setId("42");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getProcessById("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById3() {
    // Arrange
    Process process = new Process();
    process.setId("Id");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getProcessById("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addProcess(Process)}
   */
  @Test
  public void testAddProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();

    // Act
    bpmnModel.addProcess(process);

    // Assert
    List<Process> processes = bpmnModel.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Method under test: {@link BpmnModel#getPool(String)}
   */
  @Test
  public void testGetPool() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getPool("42"));
    assertNull((new BpmnModel()).getPool(""));
  }

  /**
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getLane("42"));
    assertNull((new BpmnModel()).getLane(""));
  }

  /**
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getLane("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getLane(null));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement5() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement6() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(element, bpmnModel.getFlowElement("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement7() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement(null));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement8() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement(""));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("42", new SubProcess()));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess(null, new SubProcess()));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("", new SubProcess()));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("42", subProcess));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact4() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact5() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact6() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact7() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(new Association());
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", new SubProcess()));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Method under test: {@link BpmnModel#addGraphicInfo(String, GraphicInfo)}
   */
  @Test
  public void testAddGraphicInfo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#getGraphicInfo(String)}
   */
  @Test
  public void testGetGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getGraphicInfo("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#getFlowLocationGraphicInfo(String)}
   */
  @Test
  public void testGetFlowLocationGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getFlowLocationGraphicInfo("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#hasDiagramInterchangeInfo()}
   */
  @Test
  public void testHasDiagramInterchangeInfo() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).hasDiagramInterchangeInfo());
  }

  /**
   * Method under test: {@link BpmnModel#hasDiagramInterchangeInfo()}
   */
  @Test
  public void testHasDiagramInterchangeInfo2() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act and Assert
    assertTrue(bpmnModel.hasDiagramInterchangeInfo());
  }

  /**
   * Method under test: {@link BpmnModel#getLabelGraphicInfo(String)}
   */
  @Test
  public void testGetLabelGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getLabelGraphicInfo("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#addLabelGraphicInfo(String, GraphicInfo)}
   */
  @Test
  public void testAddLabelGraphicInfo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    bpmnModel.addLabelGraphicInfo("Key", graphicInfo);

    // Assert
    Map<String, GraphicInfo> labelLocationMap = bpmnModel.getLabelLocationMap();
    assertEquals(1, labelLocationMap.size());
    assertSame(graphicInfo, labelLocationMap.get("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(0.5d);
    graphicInfo2.setWidth(0.5d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(10.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setResources(new ArrayList<>());

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(resources.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setResources(null);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(resources.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(new Resource("42", "Resource Name"));

    // Act
    bpmnModel.setResources(resourceList);

    // Assert
    assertEquals(resourceList, bpmnModel.getResources());
  }

  /**
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(new Resource("42", "Resource Name"));
    resourceList.add(new Resource("42", "Resource Name"));

    // Act
    bpmnModel.setResources(resourceList);

    // Assert
    assertEquals(resourceList, bpmnModel.getResources());
  }

  /**
   * Method under test: {@link BpmnModel#addResource(Resource)}
   */
  @Test
  public void testAddResource() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Resource resource = new Resource("42", "Resource Name");

    // Act
    bpmnModel.addResource(resource);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertEquals(1, resources.size());
    assertTrue(resources instanceof List);
    assertSame(resource, ((List<Resource>) resources).get(0));
  }

  /**
   * Method under test: {@link BpmnModel#addResource(Resource)}
   */
  @Test
  public void testAddResource2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addResource(null);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(resources.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsResourceId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("42", "Resource Name"));

    // Act and Assert
    assertTrue(bpmnModel.containsResourceId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("Resource Id", "Resource Name"));

    // Act and Assert
    assertFalse(bpmnModel.containsResourceId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getResource("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Resource resource = new Resource("42", "Resource Name");

    bpmnModel.addResource(resource);

    // Act and Assert
    assertSame(resource, bpmnModel.getResource("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("Resource Id", "Resource Name"));

    // Act and Assert
    assertNull(bpmnModel.getResource("42"));
  }

  /**
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setSignals(new ArrayList<>());

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(signals.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setSignals(null);

    // Assert that nothing has changed
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(signals.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(new Signal("42", "Name"));

    // Act
    bpmnModel.setSignals(signalList);

    // Assert
    assertEquals(signalList, bpmnModel.getSignals());
  }

  /**
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(new Signal("42", "Name"));
    signalList.add(new Signal("42", "Name"));

    // Act
    bpmnModel.setSignals(signalList);

    // Assert
    assertEquals(signalList, bpmnModel.getSignals());
  }

  /**
   * Method under test: {@link BpmnModel#addSignal(Signal)}
   */
  @Test
  public void testAddSignal() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Signal signal = new Signal("42", "Name");

    // Act
    bpmnModel.addSignal(signal);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertEquals(1, signals.size());
    assertTrue(signals instanceof List);
    assertSame(signal, ((List<Signal>) signals).get(0));
  }

  /**
   * Method under test: {@link BpmnModel#addSignal(Signal)}
   */
  @Test
  public void testAddSignal2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addSignal(null);

    // Assert that nothing has changed
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(signals.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsSignalId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "Name"));

    // Act and Assert
    assertTrue(bpmnModel.containsSignalId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));

    // Act and Assert
    assertFalse(bpmnModel.containsSignalId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getSignal("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Signal signal = new Signal("42", "Name");

    bpmnModel.addSignal(signal);

    // Act and Assert
    assertSame(signal, bpmnModel.getSignal("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));

    // Act and Assert
    assertNull(bpmnModel.getSignal("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessageFlow(new MessageFlow("Source Ref", "Target Ref"));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessageFlow(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    messageFlow.setId("Message Flow");

    // Act
    bpmnModel.addMessageFlow(messageFlow);

    // Assert
    Map<String, MessageFlow> messageFlows = bpmnModel.getMessageFlows();
    assertEquals(1, messageFlows.size());
    assertSame(messageFlow, messageFlows.get("Message Flow"));
  }

  /**
   * Method under test: {@link BpmnModel#getMessageFlow(String)}
   */
  @Test
  public void testGetMessageFlow() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMessageFlow("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsMessageFlowId(String)}
   */
  @Test
  public void testContainsMessageFlowId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsMessageFlowId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getMessages()}
   */
  @Test
  public void testGetMessages() {
    // Arrange, Act and Assert
    assertTrue((new BpmnModel()).getMessages().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setMessages(new ArrayList<>());

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setMessages(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(message);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId("Message List");

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(message);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertEquals(1, bpmnModel.getMessages().size());
    Map<String, Message> stringMessageMap = bpmnModel.messageMap;
    assertEquals(1, stringMessageMap.size());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertSame(message, stringMessageMap.get("Message List"));
  }

  /**
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages5() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(null);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Message message = new Message("42", "Name", "Item Ref");

    // Act
    bpmnModel.addMessage(message);

    // Assert
    assertEquals(1, bpmnModel.getMessages().size());
    Map<String, Message> stringMessageMap = bpmnModel.messageMap;
    assertEquals(1, stringMessageMap.size());
    assertTrue(message.getExtensionElements().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertSame(message, stringMessageMap.get("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessage(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);

    // Act
    bpmnModel.addMessage(message);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(message.getExtensionElements().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessage(new Message("", "Name", "Item Ref"));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#getMessage(String)}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMessage("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getMessage(String)}
   */
  @Test
  public void testGetMessage2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Message message = new Message("42", ":", ":");

    bpmnModel.addMessage(message);

    // Act and Assert
    assertSame(message, bpmnModel.getMessage("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsMessageId(String)}
   */
  @Test
  public void testContainsMessageId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsMessageId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsMessageId(String)}
   */
  @Test
  public void testContainsMessageId2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(new Message("42", "Name", "Item Ref"));

    // Act and Assert
    assertTrue(bpmnModel.containsMessageId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Assert
    Map<String, Error> errors = bpmnModel.getErrors();
    assertEquals(1, errors.size());
    Error getResult = errors.get("An error occurred");
    assertEquals("An error occurred", getResult.getErrorCode());
    assertEquals("An error occurred", getResult.getId());
    assertEquals("An error occurred", getResult.getName());
  }

  /**
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError(null, "An error occurred", "An error occurred");

    // Assert that nothing has changed
    assertTrue(bpmnModel.getErrors().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError("", "An error occurred", "An error occurred");

    // Assert that nothing has changed
    assertTrue(bpmnModel.getErrors().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#containsErrorRef(String)}
   */
  @Test
  public void testContainsErrorRef() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsErrorRef("An error occurred"));
  }

  /**
   * Method under test: {@link BpmnModel#containsErrorRef(String)}
   */
  @Test
  public void testContainsErrorRef2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act and Assert
    assertTrue(bpmnModel.containsErrorRef("An error occurred"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ItemDefinition item = new ItemDefinition();

    // Act
    bpmnModel.addItemDefinition("42", item);

    // Assert
    Map<String, ItemDefinition> itemDefinitions = bpmnModel.getItemDefinitions();
    assertEquals(1, itemDefinitions.size());
    assertSame(item, itemDefinitions.get("42"));
  }

  /**
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addItemDefinition(null, new ItemDefinition());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addItemDefinition("", new ItemDefinition());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#containsItemDefinitionId(String)}
   */
  @Test
  public void testContainsItemDefinitionId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsItemDefinitionId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsItemDefinitionId(String)}
   */
  @Test
  public void testContainsItemDefinitionId2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", new ItemDefinition());

    // Act and Assert
    assertTrue(bpmnModel.containsItemDefinitionId("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getDataStore(String)}
   */
  @Test
  public void testGetDataStore() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getDataStore("42"));
  }

  /**
   * Method under test: {@link BpmnModel#getDataStore(String)}
   */
  @Test
  public void testGetDataStore2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    DataStore dataStore = new DataStore();
    bpmnModel.addDataStore("42", dataStore);

    // Act and Assert
    assertSame(dataStore, bpmnModel.getDataStore("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    DataStore dataStore = new DataStore();

    // Act
    bpmnModel.addDataStore("42", dataStore);

    // Assert
    Map<String, DataStore> dataStores = bpmnModel.getDataStores();
    assertEquals(1, dataStores.size());
    assertSame(dataStore, dataStores.get("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDataStore(null, new DataStore());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDataStores().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDataStore("", new DataStore());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDataStores().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModel#containsDataStore(String)}
   */
  @Test
  public void testContainsDataStore() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsDataStore("42"));
  }

  /**
   * Method under test: {@link BpmnModel#containsDataStore(String)}
   */
  @Test
  public void testContainsDataStore2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDataStore("42", new DataStore());

    // Act and Assert
    assertTrue(bpmnModel.containsDataStore("42"));
  }

  /**
   * Method under test: {@link BpmnModel#addNamespace(String, String)}
   */
  @Test
  public void testAddNamespace() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addNamespace("Prefix", "Uri");

    // Assert
    Map<String, String> namespaces = bpmnModel.getNamespaces();
    assertEquals(1, namespaces.size());
    assertEquals("Uri", namespaces.get("Prefix"));
  }

  /**
   * Method under test: {@link BpmnModel#containsNamespacePrefix(String)}
   */
  @Test
  public void testContainsNamespacePrefix() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsNamespacePrefix("Prefix"));
  }

  /**
   * Method under test: {@link BpmnModel#containsNamespacePrefix(String)}
   */
  @Test
  public void testContainsNamespacePrefix2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addNamespace("Prefix", "Uri");

    // Act and Assert
    assertTrue(bpmnModel.containsNamespacePrefix("Prefix"));
  }

  /**
   * Method under test: {@link BpmnModel#getNamespace(String)}
   */
  @Test
  public void testGetNamespace() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getNamespace("Prefix"));
  }

  /**
   * Method under test: {@link BpmnModel#getStartFormKey(String)}
   */
  @Test
  public void testGetStartFormKey() {
    // Arrange
    Process process = new Process();
    process.setId("42");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getStartFormKey("42"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnModel}
   *   <li>{@link BpmnModel#setDataStores(Map)}
   *   <li>{@link BpmnModel#setDefinitionsAttributes(Map)}
   *   <li>{@link BpmnModel#setErrors(Map)}
   *   <li>{@link BpmnModel#setEventSupport(Object)}
   *   <li>{@link BpmnModel#setGlobalArtifacts(List)}
   *   <li>{@link BpmnModel#setImports(List)}
   *   <li>{@link BpmnModel#setInterfaces(List)}
   *   <li>{@link BpmnModel#setItemDefinitions(Map)}
   *   <li>{@link BpmnModel#setMessageFlows(Map)}
   *   <li>{@link BpmnModel#setPools(List)}
   *   <li>{@link BpmnModel#setSourceSystemId(String)}
   *   <li>{@link BpmnModel#setStartEventFormTypes(List)}
   *   <li>{@link BpmnModel#setTargetNamespace(String)}
   *   <li>{@link BpmnModel#setUserTaskFormTypes(List)}
   *   <li>{@link BpmnModel#getDataStores()}
   *   <li>{@link BpmnModel#getDefinitionsAttributes()}
   *   <li>{@link BpmnModel#getErrors()}
   *   <li>{@link BpmnModel#getEventSupport()}
   *   <li>{@link BpmnModel#getFlowLocationMap()}
   *   <li>{@link BpmnModel#getGlobalArtifacts()}
   *   <li>{@link BpmnModel#getImports()}
   *   <li>{@link BpmnModel#getInterfaces()}
   *   <li>{@link BpmnModel#getItemDefinitions()}
   *   <li>{@link BpmnModel#getLabelLocationMap()}
   *   <li>{@link BpmnModel#getLocationMap()}
   *   <li>{@link BpmnModel#getMessageFlows()}
   *   <li>{@link BpmnModel#getNamespaces()}
   *   <li>{@link BpmnModel#getPools()}
   *   <li>{@link BpmnModel#getProcesses()}
   *   <li>{@link BpmnModel#getResources()}
   *   <li>{@link BpmnModel#getSignals()}
   *   <li>{@link BpmnModel#getSourceSystemId()}
   *   <li>{@link BpmnModel#getStartEventFormTypes()}
   *   <li>{@link BpmnModel#getTargetNamespace()}
   *   <li>{@link BpmnModel#getUserTaskFormTypes()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnModel actualBpmnModel = new BpmnModel();
    HashMap<String, DataStore> dataStoreMap = new HashMap<>();
    actualBpmnModel.setDataStores(dataStoreMap);
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    actualBpmnModel.setDefinitionsAttributes(attributes);
    HashMap<String, Error> errorMap = new HashMap<>();
    actualBpmnModel.setErrors(errorMap);
    actualBpmnModel.setEventSupport("Event Support");
    ArrayList<Artifact> globalArtifacts = new ArrayList<>();
    actualBpmnModel.setGlobalArtifacts(globalArtifacts);
    ArrayList<Import> imports = new ArrayList<>();
    actualBpmnModel.setImports(imports);
    ArrayList<Interface> interfaces = new ArrayList<>();
    actualBpmnModel.setInterfaces(interfaces);
    HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<>();
    actualBpmnModel.setItemDefinitions(itemDefinitionMap);
    HashMap<String, MessageFlow> messageFlows = new HashMap<>();
    actualBpmnModel.setMessageFlows(messageFlows);
    ArrayList<Pool> pools = new ArrayList<>();
    actualBpmnModel.setPools(pools);
    actualBpmnModel.setSourceSystemId("42");
    ArrayList<String> startEventFormTypes = new ArrayList<>();
    actualBpmnModel.setStartEventFormTypes(startEventFormTypes);
    actualBpmnModel.setTargetNamespace("Target Namespace");
    ArrayList<String> userTaskFormTypes = new ArrayList<>();
    actualBpmnModel.setUserTaskFormTypes(userTaskFormTypes);
    Map<String, DataStore> actualDataStores = actualBpmnModel.getDataStores();
    Map<String, List<ExtensionAttribute>> actualDefinitionsAttributes = actualBpmnModel.getDefinitionsAttributes();
    Map<String, Error> actualErrors = actualBpmnModel.getErrors();
    Object actualEventSupport = actualBpmnModel.getEventSupport();
    Map<String, List<GraphicInfo>> actualFlowLocationMap = actualBpmnModel.getFlowLocationMap();
    List<Artifact> actualGlobalArtifacts = actualBpmnModel.getGlobalArtifacts();
    List<Import> actualImports = actualBpmnModel.getImports();
    List<Interface> actualInterfaces = actualBpmnModel.getInterfaces();
    Map<String, ItemDefinition> actualItemDefinitions = actualBpmnModel.getItemDefinitions();
    Map<String, GraphicInfo> actualLabelLocationMap = actualBpmnModel.getLabelLocationMap();
    Map<String, GraphicInfo> actualLocationMap = actualBpmnModel.getLocationMap();
    Map<String, MessageFlow> actualMessageFlows = actualBpmnModel.getMessageFlows();
    Map<String, String> actualNamespaces = actualBpmnModel.getNamespaces();
    List<Pool> actualPools = actualBpmnModel.getPools();
    List<Process> actualProcesses = actualBpmnModel.getProcesses();
    Collection<Resource> actualResources = actualBpmnModel.getResources();
    Collection<Signal> actualSignals = actualBpmnModel.getSignals();
    String actualSourceSystemId = actualBpmnModel.getSourceSystemId();
    List<String> actualStartEventFormTypes = actualBpmnModel.getStartEventFormTypes();
    String actualTargetNamespace = actualBpmnModel.getTargetNamespace();
    List<String> actualUserTaskFormTypes = actualBpmnModel.getUserTaskFormTypes();

    // Assert that nothing has changed
    assertTrue(actualResources instanceof List);
    assertTrue(actualSignals instanceof List);
    assertEquals("42", actualSourceSystemId);
    assertEquals("Event Support", actualEventSupport);
    assertEquals("Target Namespace", actualTargetNamespace);
    assertTrue(actualGlobalArtifacts.isEmpty());
    assertTrue(actualImports.isEmpty());
    assertTrue(actualInterfaces.isEmpty());
    assertTrue(actualPools.isEmpty());
    assertTrue(actualProcesses.isEmpty());
    assertTrue(actualStartEventFormTypes.isEmpty());
    assertTrue(actualUserTaskFormTypes.isEmpty());
    assertTrue(actualDataStores.isEmpty());
    assertTrue(actualDefinitionsAttributes.isEmpty());
    assertTrue(actualErrors.isEmpty());
    assertTrue(actualFlowLocationMap.isEmpty());
    assertTrue(actualItemDefinitions.isEmpty());
    assertTrue(actualLabelLocationMap.isEmpty());
    assertTrue(actualLocationMap.isEmpty());
    assertTrue(actualMessageFlows.isEmpty());
    assertTrue(actualNamespaces.isEmpty());
    assertTrue(actualBpmnModel.messageMap.isEmpty());
    assertSame(globalArtifacts, actualGlobalArtifacts);
    assertSame(imports, actualImports);
    assertSame(interfaces, actualInterfaces);
    assertSame(pools, actualPools);
    assertSame(startEventFormTypes, actualStartEventFormTypes);
    assertSame(userTaskFormTypes, actualUserTaskFormTypes);
    assertSame(dataStoreMap, actualDataStores);
    assertSame(attributes, actualDefinitionsAttributes);
    assertSame(errorMap, actualErrors);
    assertSame(itemDefinitionMap, actualItemDefinitions);
    assertSame(messageFlows, actualMessageFlows);
  }
}
