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
package org.activiti.validation.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class DiagramInterchangeInfoValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

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
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertEquals("Key", params.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("Key", new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertEquals("Key", params.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

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
    Process process = new Process();
    bpmnModel.addProcess(process);
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertEquals("Key", params.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(1, bpmnModel.getProcesses().size());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(graphicInfo, locationMap.get("Key"));
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate5() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

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

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("DI_INVALID_REFERENCE", graphicInfo2);
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertEquals("DI_INVALID_REFERENCE", params.get("bpmnReference"));
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("DI_INVALID_REFERENCE", getResult2.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult2.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    assertEquals("DI_INVALID_REFERENCE", getResult2.getProblem());
    Map<String, String> params2 = getResult2.getParams();
    assertEquals(1, params2.size());
    assertEquals("Key", params2.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(2, locationMap.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.isWarning());
    assertTrue(getResult2.isWarning());
    assertSame(graphicInfo2, locationMap.get("DI_INVALID_REFERENCE"));
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate6() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

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
    bpmnModel.addGraphicInfo(null, graphicInfo);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertNull(params.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(graphicInfo, locationMap.get(null));
  }

  /**
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate7() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

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
    bpmnModel.addGraphicInfo("", graphicInfo);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertEquals("", params.get("bpmnReference"));
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(bpmnModel.getMainProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(graphicInfo, locationMap.get(""));
  }
}
