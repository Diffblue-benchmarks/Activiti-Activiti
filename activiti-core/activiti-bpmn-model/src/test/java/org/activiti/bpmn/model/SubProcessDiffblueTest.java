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
   * Test {@link SubProcess#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenHashMapComputeIfPresentFooAndBiFunction_whenNull() {
    // Arrange
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    flowElementMap.computeIfPresent("foo", mock(BiFunction.class));

    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(flowElementMap);

    // Act and Assert
    assertNull(subProcess.getFlowElement(null));
  }

  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) FlowElementMap is
   * {@link HashMap#HashMap()}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenSubProcessFlowElementMapIsHashMap_whenNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(subProcess.getFlowElement(null));
  }

  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenSubProcess_when42() {
    // Arrange, Act and Assert
    assertNull((new SubProcess()).getFlowElement("42"));
  }

  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenSubProcess_whenEmptyString() {
    // Arrange, Act and Assert
    assertNull((new SubProcess()).getFlowElement(""));
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    AdhocSubProcess parentContainer2 = new AdhocSubProcess();
    parentContainer.setParentContainer(parentContainer2);

    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer3 = element.getParentContainer();
    FlowElementsContainer parentContainer4 = ((SubProcess) parentContainer3).getParentContainer();
    assertTrue(parentContainer4 instanceof AdhocSubProcess);
    assertTrue(parentContainer3 instanceof SubProcess);
    assertSame(parentContainer2, ((AdhocSubProcess) parentContainer4).getParentContainer());
    assertSame(parentContainer2, ((AdhocSubProcess) parentContainer4).getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement2() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    Process parentContainer2 = new Process();
    parentContainer.setParentContainer(parentContainer2);

    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer3 = element.getParentContainer();
    FlowElementsContainer parentContainer4 = ((SubProcess) parentContainer3).getParentContainer();
    assertTrue(parentContainer4 instanceof AdhocSubProcess);
    assertTrue(parentContainer3 instanceof SubProcess);
    assertSame(parentContainer2, ((AdhocSubProcess) parentContainer4).getParentContainer());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(subProcess.getParentContainer());
    assertNull(((SubProcess) parentContainer).getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertNull(((SubProcess) parentContainer).getSubProcess());
    assertTrue(parentContainer.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ParentContainer
   * FlowElementMap size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenAdhocSubProcessParentContainerFlowElementMapSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(subProcess.getParentContainer());
    assertNull(((SubProcess) parentContainer).getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertNull(((SubProcess) parentContainer).getSubProcess());
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) ParentContainer
   * Artifacts {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenBooleanDataObjectParentContainerArtifactsList() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<Artifact> artifacts = parentContainer.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof SubProcess);
    assertTrue(artifacts.isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(artifacts, subProcess.getArtifacts());
    assertSame(flowElements, subProcess.getFlowElements());
    assertSame(parentContainer, element.getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) ParentContainer is
   * {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenSubProcessParentContainerIsAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer2 = element.getParentContainer();
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(parentContainer, ((SubProcess) parentContainer2).getParentContainer());
    assertSame(parentContainer, subProcess.getSubProcess());
    assertSame(parentContainer, ((SubProcess) parentContainer2).getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) ParentContainer is
   * {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenSubProcessParentContainerIsProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Process parentContainer = new Process();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer2 = element.getParentContainer();
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, subProcess.getParentContainer());
    assertSame(parentContainer, ((SubProcess) parentContainer2).getParentContainer());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_whenAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(subProcess.getParentContainer());
    assertNull(((SubProcess) parentContainer).getParentContainer());
    assertNull(subProcess.getSubProcess());
    assertNull(((SubProcess) parentContainer).getSubProcess());
    assertTrue(parentContainer.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
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
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_givenSubProcess_whenAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    // Act
    subProcess.addFlowElementToMap(new AdhocSubProcess());

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) FlowElementMap size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_thenSubProcessFlowElementMapSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) ParentContainer
   * {@link AdhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_thenSubProcessParentContainerAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(new AdhocSubProcess());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    FlowElementsContainer parentContainer = subProcess.getParentContainer();
    assertTrue(parentContainer instanceof AdhocSubProcess);
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    Map<String, FlowElement> flowElementMap2 = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(element, flowElementMap.get("Element"));
    assertSame(element, flowElementMap2.get("Element"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) ParentContainer
   * {@link Process}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_thenSubProcessParentContainerProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(new Process());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    FlowElementsContainer parentContainer = subProcess.getParentContainer();
    assertTrue(parentContainer instanceof Process);
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    Map<String, FlowElement> flowElementMap2 = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(element, flowElementMap.get("Element"));
    assertSame(element, flowElementMap2.get("Element"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link SubProcess} (default constructor) FlowElementMap Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_whenNull_thenSubProcessFlowElementMapEmpty() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setParentContainer(null);

    // Act
    subProcess.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#containsFlowElementId(String)}.
   * <p>
   * Method under test: {@link SubProcess#containsFlowElementId(String)}
   */
  @Test
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse((new SubProcess()).containsFlowElementId("42"));
  }

  /**
   * Test {@link SubProcess#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenAssociationIdIs42_thenReturnAssociation() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(artifact);

    // Act and Assert
    assertSame(artifact, subProcess.getArtifact("42"));
  }

  /**
   * Test {@link SubProcess#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenSubProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act and Assert
    assertNull(subProcess.getArtifact("42"));
  }

  /**
   * Test {@link SubProcess#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new SubProcess()).getArtifact("42"));
  }

  /**
   * Test {@link SubProcess#addArtifact(Artifact)}.
   * <p>
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
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link SubProcess#removeArtifact(String)}.
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SubProcess} (default constructor) Artifacts Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_givenAssociationIdIs42_when42_thenSubProcessArtifactsEmpty() {
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
  }

  /**
   * Test {@link SubProcess#removeArtifact(String)}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SubProcess} (default constructor) Artifacts Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_givenSubProcess_when42_thenSubProcessArtifactsEmpty() {
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
  }

  /**
   * Test {@link SubProcess#removeArtifact(String)}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) Artifacts size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_thenSubProcessArtifactsSizeIsOne() {
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
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code 42}.</li>
   *   <li>Then return FlowElementMap size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_givenAdhocSubProcessIdIs42_thenReturnFlowElementMapSizeIsOne() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element);

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> flowElementMap = actualCloneResult.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("42");
    assertSame(element, getResult);
    assertSame(getResult, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   *   <li>Then return Artifacts size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_givenSubProcessAddArtifactAssociation_thenReturnArtifactsSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    Association artifact = new Association();
    subProcess.addArtifact(artifact);
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts2 = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(artifacts2.isEmpty());
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) ForCompensation is
   * {@code true}.</li>
   *   <li>Then return ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_givenSubProcessForCompensationIsTrue_thenReturnForCompensation() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setForCompensation(true);
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertNull(((AdhocSubProcess) getResult).getBehavior());
    assertNull(((AdhocSubProcess) getResult).getDefaultFlow());
    assertNull(((AdhocSubProcess) getResult).getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) getResult).getCompletionCondition());
    assertNull(getResult.getId());
    assertNull(((AdhocSubProcess) getResult).getIoSpecification());
    assertNull(((AdhocSubProcess) getResult).getLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).isForCompensation());
    assertFalse(((AdhocSubProcess) getResult).hasSequentialOrdering());
    assertFalse(((AdhocSubProcess) getResult).isAsynchronous());
    assertFalse(((AdhocSubProcess) getResult).isNotExclusive());
    assertTrue(flowElements2.isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getBoundaryEvents().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataInputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataOutputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getMapExceptions().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getIncomingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getOutgoingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataObjects().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
    assertTrue(((AdhocSubProcess) getResult).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) getResult).isCancelRemainingInstances());
    assertTrue(((AdhocSubProcess) getResult).isExclusive());
    assertEquals(AdhocSubProcess.ORDERING_PARALLEL, ((AdhocSubProcess) getResult).getOrdering());
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).</li>
   *   <li>Then return IoSpecification is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_givenSubProcess_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    SubProcess actualCloneResult = (new SubProcess()).clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Then return FlowElements first Id is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_thenReturnFlowElementsFirstIdIsEmptyString() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element);

    // Act and Assert
    Collection<FlowElement> flowElements = subProcess.clone().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertEquals("", getResult.getId());
    assertNull(((AdhocSubProcess) getResult).getBehavior());
    assertNull(((AdhocSubProcess) getResult).getDefaultFlow());
    assertNull(((AdhocSubProcess) getResult).getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) getResult).getCompletionCondition());
    assertNull(((AdhocSubProcess) getResult).getIoSpecification());
    assertNull(((AdhocSubProcess) getResult).getLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).isForCompensation());
    assertFalse(((AdhocSubProcess) getResult).hasSequentialOrdering());
    assertFalse(((AdhocSubProcess) getResult).isAsynchronous());
    assertFalse(((AdhocSubProcess) getResult).isNotExclusive());
    assertTrue(flowElements2.isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getBoundaryEvents().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataInputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataOutputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getMapExceptions().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getIncomingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getOutgoingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataObjects().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getFlowElementMap().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) getResult).isCancelRemainingInstances());
    assertTrue(((AdhocSubProcess) getResult).isExclusive());
    assertEquals(AdhocSubProcess.ORDERING_PARALLEL, ((AdhocSubProcess) getResult).getOrdering());
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Then return FlowElements first Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_thenReturnFlowElementsFirstIdIsNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    Collection<FlowElement> flowElements = subProcess.clone().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertNull(((AdhocSubProcess) getResult).getBehavior());
    assertNull(((AdhocSubProcess) getResult).getDefaultFlow());
    assertNull(((AdhocSubProcess) getResult).getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) getResult).getCompletionCondition());
    assertNull(getResult.getId());
    assertNull(((AdhocSubProcess) getResult).getIoSpecification());
    assertNull(((AdhocSubProcess) getResult).getLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((AdhocSubProcess) getResult).isForCompensation());
    assertFalse(((AdhocSubProcess) getResult).hasSequentialOrdering());
    assertFalse(((AdhocSubProcess) getResult).isAsynchronous());
    assertFalse(((AdhocSubProcess) getResult).isNotExclusive());
    assertTrue(flowElements2.isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getBoundaryEvents().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataInputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataOutputAssociations().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getMapExceptions().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getIncomingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getOutgoingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getDataObjects().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).getFlowElementMap().isEmpty());
    assertTrue(((AdhocSubProcess) getResult).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) getResult).isCancelRemainingInstances());
    assertTrue(((AdhocSubProcess) getResult).isExclusive());
    assertEquals(AdhocSubProcess.ORDERING_PARALLEL, ((AdhocSubProcess) getResult).getOrdering());
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Then return FlowElements first is {@link BooleanDataObject} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_thenReturnFlowElementsFirstIsBooleanDataObject() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    subProcess.addFlowElement(element);

    // Act and Assert
    Collection<FlowElement> flowElements = subProcess.clone().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setIoSpecification(new IOSpecification());
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    IOSpecification ioSpecification = subProcess.clone().getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess() {
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
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements2 = parentContainer.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(((SubProcess) parentContainer).getIoSpecification());
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess2() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    Association artifact = new Association();
    otherElement.addArtifact(artifact);
    otherElement.addFlowElement(new AdhocSubProcess());

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<Artifact> artifacts = parentContainer.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess3() {
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
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements2 = parentContainer.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(((SubProcess) parentContainer).getIoSpecification());
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertTrue(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess4() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.addFlowElement(new AdhocSubProcess());

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    MultiInstanceLoopCharacteristics loopCharacteristics = ((SubProcess) parentContainer).getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess5() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    SubProcess otherElement = new SubProcess();
    otherElement.setIoSpecification(new IOSpecification());
    otherElement.addFlowElement(new AdhocSubProcess());

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Collection<FlowElement> flowElements = otherElement.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    IOSpecification ioSpecification = ((SubProcess) parentContainer).getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess6() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess otherElement = new SubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("42");
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(subProcess, getResult.getParentContainer());
    assertSame(subProcess, getResult.getSubProcess());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess7() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    SubProcess otherElement = new SubProcess();
    otherElement.addFlowElement(element);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("42");
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer2 = getResult.getParentContainer();
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, ((SubProcess) parentContainer2).getParentContainer());
    assertSame(parentContainer, ((SubProcess) parentContainer2).getSubProcess());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess_givenAdhocSubProcessIdIsEmptyString() {
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
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements2 = parentContainer.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(((SubProcess) parentContainer).getIoSpecification());
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) FlowElements first
   * {@link BooleanDataObject}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  public void testSetValuesWithSubProcess_thenSubProcessFlowElementsFirstBooleanDataObject() {
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
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements2 = parentContainer.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof BooleanDataObject);
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(((SubProcess) parentContainer).getIoSpecification());
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test getters and setters.
   * <p>
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
