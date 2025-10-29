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
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class SubProcessDiffblueTest {
  /**
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement() {
    // Arrange, Act and Assert
    assertNull((new SubProcess()).getFlowElement("42"));
    assertNull((new SubProcess()).getFlowElement(""));
  }

  /**
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(subProcess.getFlowElement(null));
  }

  /**
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement3() {
    // Arrange
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    flowElementMap.computeIfPresent("foo", mock(BiFunction.class));

    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(flowElementMap);

    // Act and Assert
    assertNull(subProcess.getFlowElement(null));
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertNull(subProcess.getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertNull(subProcess.getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement3() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(parentContainer, subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement4() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertNull(subProcess.getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement5() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Process parentContainer = new Process();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertNull(subProcess.getSubProcess());
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement6() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertNull(subProcess.getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement7() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    parentContainer.setParentContainer(new AdhocSubProcess());

    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(parentContainer, subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement8() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    parentContainer.setParentContainer(new Process());

    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(parentContainer, subProcess.getSubProcess());
    assertSame(subProcess, element.getParentContainer());
    assertSame(subProcess, element.getSubProcess());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    // Act
    subProcess.addFlowElementToMap(new AdhocSubProcess());

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    // Act
    subProcess.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap3() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    assertNull(subProcess.getParentContainer());
    Map<String, FlowElement> flowElementMap = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap4() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
    assertSame(parentContainer, subProcess.getParentContainer());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap5() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Process parentContainer = new Process();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
    assertSame(parentContainer, subProcess.getParentContainer());
  }

  /**
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap6() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#containsFlowElementId(String)}
   */
  @Test
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse((new SubProcess()).containsFlowElementId("42"));
  }

  /**
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact() {
    // Arrange, Act and Assert
    assertNull((new SubProcess()).getArtifact("42"));
  }

  /**
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act and Assert
    assertNull(subProcess.getArtifact("42"));
  }

  /**
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact3() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(artifact);

    // Act and Assert
    assertSame(artifact, subProcess.getArtifact("42"));
  }

  /**
   * Method under test: {@link SubProcess#addArtifact(Artifact)}
   */
  @Test
  public void testAddArtifact() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Association artifact = new Association();

    // Act
    subProcess.addArtifact(artifact);

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    // Act
    subProcess.removeArtifact("42");

    // Assert that nothing has changed
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act
    subProcess.removeArtifact("42");

    // Assert that nothing has changed
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact3() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(artifact);

    // Act
    subProcess.removeArtifact("42");

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    SubProcess actualCloneResult = (new SubProcess()).clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Association artifact = new Association();
    subProcess.addArtifact(artifact);
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setForCompensation(true);
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone6() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setIoSpecification(new IOSpecification());
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification = actualCloneResult.getIoSpecification();
    assertNull(ioSpecification.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone8() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, FlowElement> flowElementMap = actualCloneResult.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, flowElementMap.get("42"));
  }

  /**
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone9() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(artifacts.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    SubProcess otherElement = new SubProcess();

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(otherElement.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.addArtifact(new Association());
    AdhocSubProcess element = new AdhocSubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.setForCompensation(true);
    AdhocSubProcess element = new AdhocSubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    AdhocSubProcess element = new AdhocSubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.setIoSpecification(new IOSpecification());
    AdhocSubProcess element = new AdhocSubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess otherElement = new SubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, flowElementMap.get("42"));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues9() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    SubProcess otherElement = new SubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(otherElement.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValues10() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(new AdhocSubProcess());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess otherElement = new SubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, flowElementMap.get("42"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SubProcess}
   *   <li>{@link SubProcess#setDataObjects(List)}
   *   <li>{@link SubProcess#setFlowElementMap(Map)}
   *   <li>{@link SubProcess#getArtifacts()}
   *   <li>{@link SubProcess#getDataObjects()}
   *   <li>{@link SubProcess#getFlowElementMap()}
   *   <li>{@link SubProcess#getFlowElements()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SubProcess actualSubProcess = new SubProcess();
    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    actualSubProcess.setDataObjects(dataObjects);
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    actualSubProcess.setFlowElementMap(flowElementMap);
    Collection<Artifact> actualArtifacts = actualSubProcess.getArtifacts();
    List<ValuedDataObject> actualDataObjects = actualSubProcess.getDataObjects();
    Map<String, FlowElement> actualFlowElementMap = actualSubProcess.getFlowElementMap();

    // Assert that nothing has changed
    assertTrue(actualArtifacts instanceof List);
    assertTrue(actualSubProcess.getFlowElements() instanceof List);
    assertEquals(0, actualSubProcess.getXmlColumnNumber());
    assertEquals(0, actualSubProcess.getXmlRowNumber());
    assertFalse(actualSubProcess.isForCompensation());
    assertFalse(actualSubProcess.isAsynchronous());
    assertFalse(actualSubProcess.isNotExclusive());
    assertTrue(actualSubProcess.getBoundaryEvents().isEmpty());
    assertTrue(actualSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(actualSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(actualSubProcess.getMapExceptions().isEmpty());
    assertTrue(actualSubProcess.getExecutionListeners().isEmpty());
    assertTrue(actualSubProcess.getIncomingFlows().isEmpty());
    assertTrue(actualSubProcess.getOutgoingFlows().isEmpty());
    assertTrue(actualDataObjects.isEmpty());
    assertTrue(actualSubProcess.getAttributes().isEmpty());
    assertTrue(actualSubProcess.getExtensionElements().isEmpty());
    assertTrue(actualFlowElementMap.isEmpty());
    assertSame(dataObjects, actualDataObjects);
    assertSame(flowElementMap, actualFlowElementMap);
  }
}
