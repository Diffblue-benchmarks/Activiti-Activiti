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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DiagramInterchangeInfoValidatorDiffblueTest {
  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link BpmnModel} (default constructor) addFlowGraphicInfoList {@code Key} and
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given ArrayList(); when BpmnModel (default constructor) addFlowGraphicInfoList 'Key' and ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_givenArrayList_whenBpmnModelAddFlowGraphicInfoListKeyAndArrayList() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("Key", new ArrayList<>());

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is empty string.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given empty string; then ArrayList() first Params 'bpmnReference' is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_givenEmptyString_thenArrayListFirstParamsBpmnReferenceIsEmptyString() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code false}.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given GraphicInfo (default constructor) Expanded is 'false'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_givenGraphicInfoExpandedIsFalse_thenArrayListSizeIsTwo() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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
    ValidationError getResult = errors.get(1);
    assertEquals("DI_INVALID_REFERENCE", getResult.getDefaultDescription());
    assertEquals("DI_INVALID_REFERENCE", getResult.getKey());
    assertEquals("DI_INVALID_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    Map<String, String> params = getResult.getParams();
    assertEquals(1, params.size());
    assertTrue(params.containsKey("bpmnReference"));
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact {@link Association} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor) addArtifact Association (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcessAddArtifactAssociation() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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

    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess FlowElements Empty.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor); then BpmnModel (default constructor) MainProcess FlowElements Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcess_thenBpmnModelMainProcessFlowElementsEmpty() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); then ArrayList() first Params 'bpmnReference' is 'Key'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListFirstParamsBpmnReferenceIsKey() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first Params {@code bpmnReference} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); then ArrayList() first Params 'bpmnReference' is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListFirstParamsBpmnReferenceIsNull() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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
   *
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); then BpmnModel (default constructor) MainProcess Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_thenBpmnModelMainProcessArtifactsList() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();

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

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    diagramInterchangeInfoValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Process mainProcess = bpmnModel.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts2 = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts2 instanceof List);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(artifacts2.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link DiagramInterchangeInfoValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiagramInterchangeInfoValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    DiagramInterchangeInfoValidator diagramInterchangeInfoValidator =
        new DiagramInterchangeInfoValidator();
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
