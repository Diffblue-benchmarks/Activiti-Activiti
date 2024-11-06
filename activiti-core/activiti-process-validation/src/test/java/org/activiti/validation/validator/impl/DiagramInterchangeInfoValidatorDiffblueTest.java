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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiagramInterchangeInfoValidatorDiffblueTest {
  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When {@link BpmnModel} (default constructor) addFlowGraphicInfoList
   * {@code Key} and {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ArrayList(); when BpmnModel (default constructor) addFlowGraphicInfoList 'Key' and ArrayList()")
  void testValidate_givenArrayList_whenBpmnModelAddFlowGraphicInfoListKeyAndArrayList() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator = new DiagramInterchangeInfoValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("Key", new ArrayList<>());

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given empty string; then ArrayList() first Params 'bpmnReference' is empty string")
  void testValidate_givenEmptyString_thenArrayListFirstParamsBpmnReferenceIsEmptyString() {
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
    Map<String, String> params = errors.get(0).getParams();
    assertEquals(1, params.size());
    assertEquals("", params.get("bpmnReference"));
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is
   * {@code false}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given GraphicInfo (default constructor) Expanded is 'false'; then ArrayList() size is two")
  void testValidate_givenGraphicInfoExpandedIsFalse_thenArrayListSizeIsTwo() {
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
    assertEquals(2, errors.size());
    Map<String, String> params = errors.get(0).getParams();
    assertEquals(1, params.size());
    assertEquals("DI_INVALID_REFERENCE", params.get("bpmnReference"));
    ValidationError getResult = errors.get(1);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    Map<String, String> params2 = getResult.getParams();
    assertEquals(1, params2.size());
    assertEquals("Key", params2.get("bpmnReference"));
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given 'null'; then ArrayList() first Params 'bpmnReference' is 'null'")
  void testValidate_givenNull_thenArrayListFirstParamsBpmnReferenceIsNull() {
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
    Map<String, String> params = errors.get(0).getParams();
    assertEquals(1, params.size());
    assertNull(params.get("bpmnReference"));
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts
   * {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Process (default constructor); then BpmnModel (default constructor) MainProcess Artifacts List")
  void testValidate_givenProcess_thenBpmnModelMainProcessArtifactsList() {
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
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Process mainProcess = bpmnModel.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is
   * {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); then ArrayList() first Params 'bpmnReference' is 'Key'")
  void testValidate_thenArrayListFirstParamsBpmnReferenceIsKey() {
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
    Map<String, String> params = errors.get(0).getParams();
    assertEquals(1, params.size());
    assertEquals("Key", params.get("bpmnReference"));
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
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
  }
}
