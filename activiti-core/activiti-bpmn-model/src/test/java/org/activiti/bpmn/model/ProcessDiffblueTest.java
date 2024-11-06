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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Process}
   *   <li>{@link Process#setCandidateStarterGroups(List)}
   *   <li>{@link Process#setCandidateStarterGroupsDefined(boolean)}
   *   <li>{@link Process#setCandidateStarterUsers(List)}
   *   <li>{@link Process#setCandidateStarterUsersDefined(boolean)}
   *   <li>{@link Process#setDataObjects(List)}
   *   <li>{@link Process#setDocumentation(String)}
   *   <li>{@link Process#setEventListeners(List)}
   *   <li>{@link Process#setExecutable(boolean)}
   *   <li>{@link Process#setExecutionListeners(List)}
   *   <li>{@link Process#setFlowElementMap(Map)}
   *   <li>{@link Process#setInitialFlowElement(FlowElement)}
   *   <li>{@link Process#setIoSpecification(IOSpecification)}
   *   <li>{@link Process#setLanes(List)}
   *   <li>{@link Process#setName(String)}
   *   <li>{@link Process#getArtifacts()}
   *   <li>{@link Process#getCandidateStarterGroups()}
   *   <li>{@link Process#getCandidateStarterUsers()}
   *   <li>{@link Process#getDataObjects()}
   *   <li>{@link Process#getDocumentation()}
   *   <li>{@link Process#getEventListeners()}
   *   <li>{@link Process#getExecutionListeners()}
   *   <li>{@link Process#getFlowElementMap()}
   *   <li>{@link Process#getFlowElements()}
   *   <li>{@link Process#getInitialFlowElement()}
   *   <li>{@link Process#getIoSpecification()}
   *   <li>{@link Process#getLanes()}
   *   <li>{@link Process#getName()}
   *   <li>{@link Process#isCandidateStarterGroupsDefined()}
   *   <li>{@link Process#isCandidateStarterUsersDefined()}
   *   <li>{@link Process#isExecutable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Process actualProcess = new Process();
    ArrayList<String> candidateStarterGroups = new ArrayList<>();
    actualProcess.setCandidateStarterGroups(candidateStarterGroups);
    actualProcess.setCandidateStarterGroupsDefined(true);
    ArrayList<String> candidateStarterUsers = new ArrayList<>();
    actualProcess.setCandidateStarterUsers(candidateStarterUsers);
    actualProcess.setCandidateStarterUsersDefined(true);
    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    actualProcess.setDataObjects(dataObjects);
    actualProcess.setDocumentation("Documentation");
    ArrayList<EventListener> eventListeners = new ArrayList<>();
    actualProcess.setEventListeners(eventListeners);
    actualProcess.setExecutable(true);
    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    actualProcess.setExecutionListeners(executionListeners);
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    actualProcess.setFlowElementMap(flowElementMap);
    AdhocSubProcess initialFlowElement = new AdhocSubProcess();
    actualProcess.setInitialFlowElement(initialFlowElement);
    IOSpecification ioSpecification = new IOSpecification();
    actualProcess.setIoSpecification(ioSpecification);
    ArrayList<Lane> lanes = new ArrayList<>();
    actualProcess.setLanes(lanes);
    actualProcess.setName("Name");
    Collection<Artifact> actualArtifacts = actualProcess.getArtifacts();
    List<String> actualCandidateStarterGroups = actualProcess.getCandidateStarterGroups();
    List<String> actualCandidateStarterUsers = actualProcess.getCandidateStarterUsers();
    List<ValuedDataObject> actualDataObjects = actualProcess.getDataObjects();
    String actualDocumentation = actualProcess.getDocumentation();
    List<EventListener> actualEventListeners = actualProcess.getEventListeners();
    List<ActivitiListener> actualExecutionListeners = actualProcess.getExecutionListeners();
    Map<String, FlowElement> actualFlowElementMap = actualProcess.getFlowElementMap();
    Collection<FlowElement> actualFlowElements = actualProcess.getFlowElements();
    FlowElement actualInitialFlowElement = actualProcess.getInitialFlowElement();
    IOSpecification actualIoSpecification = actualProcess.getIoSpecification();
    List<Lane> actualLanes = actualProcess.getLanes();
    String actualName = actualProcess.getName();
    boolean actualIsCandidateStarterGroupsDefinedResult = actualProcess.isCandidateStarterGroupsDefined();
    boolean actualIsCandidateStarterUsersDefinedResult = actualProcess.isCandidateStarterUsersDefined();
    boolean actualIsExecutableResult = actualProcess.isExecutable();

    // Assert that nothing has changed
    assertTrue(actualArtifacts instanceof List);
    assertTrue(actualFlowElements instanceof List);
    assertEquals("Documentation", actualDocumentation);
    assertEquals("Name", actualName);
    assertEquals(0, actualProcess.getXmlColumnNumber());
    assertEquals(0, actualProcess.getXmlRowNumber());
    assertTrue(actualCandidateStarterGroups.isEmpty());
    assertTrue(actualCandidateStarterUsers.isEmpty());
    assertTrue(actualDataObjects.isEmpty());
    assertTrue(actualEventListeners.isEmpty());
    assertTrue(actualExecutionListeners.isEmpty());
    assertTrue(actualLanes.isEmpty());
    assertTrue(actualProcess.getAttributes().isEmpty());
    assertTrue(actualProcess.getExtensionElements().isEmpty());
    assertTrue(actualFlowElementMap.isEmpty());
    assertTrue(actualIsCandidateStarterGroupsDefinedResult);
    assertTrue(actualIsCandidateStarterUsersDefinedResult);
    assertTrue(actualIsExecutableResult);
    assertSame(candidateStarterGroups, actualCandidateStarterGroups);
    assertSame(candidateStarterUsers, actualCandidateStarterUsers);
    assertSame(dataObjects, actualDataObjects);
    assertSame(eventListeners, actualEventListeners);
    assertSame(executionListeners, actualExecutionListeners);
    assertSame(lanes, actualLanes);
    assertSame(flowElementMap, actualFlowElementMap);
    assertSame(initialFlowElement, actualInitialFlowElement);
    assertSame(ioSpecification, actualIoSpecification);
  }

  /**
   * Test {@link Process#containsFlowElementId(String)}.
   * <p>
   * Method under test: {@link Process#containsFlowElementId(String)}
   */
  @Test
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse((new Process()).containsFlowElementId("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with
   * {@code flowElementId}, {@code searchRecurive}.
   * <p>
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElementWithFlowElementIdSearchRecurive() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with
   * {@code flowElementId}, {@code searchRecurive}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenAdhocSubProcessIdIsId() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with
   * {@code flowElementId}, {@code searchRecurive}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with
   * {@code flowElementId}, {@code searchRecurive}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenProcess_whenTrue() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElement("42", true));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with
   * {@code flowElementId}, {@code searchRecurive}.
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElementWithFlowElementIdSearchRecurive_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code Id}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElementWithFlowElementId_givenAdhocSubProcessIdIsId_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElementWithFlowElementId_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElementWithFlowElementId_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElementWithFlowElementId_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(new AdhocSubProcess(), "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef2() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(new Process(), "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef3() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef4() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef5() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef6() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(null);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef7() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Source Ref");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef8() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");
    artifact.setSourceRef("Source Ref");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithSourceRefRecursiveResult = process
        .findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithSourceRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithSourceRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef4() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef5() {
    // Arrange
    Association artifact = new Association();
    artifact.setSourceRef("Source Ref");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef6() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");
    artifact.setSourceRef("42");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef_givenProcess() {
    // Arrange, Act and Assert
    assertTrue((new Process()).findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with
   * {@code sourceRef}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef_thenReturnSizeIsOne() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");
    artifact.setSourceRef("Source Ref");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithSourceRefRecursiveResult = process
        .findAssociationsWithSourceRefRecursive("Source Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithSourceRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithSourceRefRecursiveResult.get(0));
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(new AdhocSubProcess(), "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef2() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(new Process(), "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef3() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef4() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef5() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef6() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(null);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef7() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithTargetRefRecursiveResult = process
        .findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithTargetRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithTargetRefRecursiveResult.get(0));
  }

  /**
   * Test
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef8() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("42");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef4() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef5() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("42");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef_givenProcess() {
    // Arrange, Act and Assert
    assertTrue((new Process()).findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with
   * {@code targetRef}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef_thenReturnSizeIsOne() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithTargetRefRecursiveResult = process
        .findAssociationsWithTargetRefRecursive("Target Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithTargetRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithTargetRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with
   * {@code flowElementId}.
   * <p>
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementId() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with
   * {@code flowElementId}.
   * <p>
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementId2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with
   * {@code flowElementId}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementId_givenAdhocSubProcessIdIsId() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with
   * {@code flowElementId}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementId_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with
   * {@code flowElementId}.
   * <ul>
   *   <li>Then return {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementId_thenReturnProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(process, process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new AdhocSubProcess(), "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId2() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId3() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId4() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);

    // Act and Assert
    assertSame(flowElementsContainer, process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId5() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   * with {@code flowElementsContainer}, {@code flowElementId}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId_whenProcess() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new Process(), "42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code 42}.</li>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList_givenAdhocSubProcessIdIs42_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code Id}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList_givenAdhocSubProcessIdIsId_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Process()).findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    process.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof Process);
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertTrue(flowElementMap.isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    Collection<Artifact> expectedArtifacts = parentContainer.getArtifacts();
    assertSame(expectedArtifacts, process.getArtifacts());
    assertSame(flowElementMap, process.getFlowElementMap());
    assertSame(flowElements, process.getFlowElements());
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ParentContainer
   * FlowElementMap size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenAdhocSubProcessParentContainerFlowElementMapSizeIsOne() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    process.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof Process);
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, flowElementMap.get("Element"));
    Collection<Artifact> expectedArtifacts = parentContainer.getArtifacts();
    assertSame(expectedArtifacts, process.getArtifacts());
    assertSame(flowElementMap, process.getFlowElementMap());
    assertSame(flowElements, process.getFlowElements());
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) ParentContainer
   * Artifacts {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_thenBooleanDataObjectParentContainerArtifactsList() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();

    // Act
    process.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<Artifact> artifacts = parentContainer.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof Process);
    assertTrue(artifacts.isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(artifacts, process.getArtifacts());
    assertSame(flowElements, process.getFlowElements());
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement_whenAdhocSubProcess() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    process.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof Process);
    Map<String, FlowElement> flowElementMap = parentContainer.getFlowElementMap();
    assertTrue(flowElementMap.isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    Collection<Artifact> expectedArtifacts = parentContainer.getArtifacts();
    assertSame(expectedArtifacts, process.getArtifacts());
    assertSame(flowElementMap, process.getFlowElementMap());
    assertSame(flowElements, process.getFlowElements());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Given {@code Element}.</li>
   *   <li>Then {@link Process} (default constructor) FlowElementMap size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_givenElement_thenProcessFlowElementMapSizeIsOne() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    process.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap = process.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    assertSame(element, flowElementMap.get("Element"));
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    process.addFlowElementToMap(element);

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link Process} (default constructor) FlowElementMap Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_whenAdhocSubProcess_thenProcessFlowElementMapEmpty() {
    // Arrange
    Process process = new Process();

    // Act
    process.addFlowElementToMap(new AdhocSubProcess());

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link Process} (default constructor) FlowElementMap Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap_whenNull_thenProcessFlowElementMapEmpty() {
    // Arrange
    Process process = new Process();

    // Act
    process.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#removeFlowElement(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link AdhocSubProcess}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement_givenHashMap42IsAdhocSubProcess() {
    // Arrange
    HashMap<String, FlowElement> stringFlowElementMap = new HashMap<>();
    stringFlowElementMap.put("42", new AdhocSubProcess());
    stringFlowElementMap.put("foo", new AdhocSubProcess());
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElementMap()).thenReturn(stringFlowElementMap);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);

    // Act
    process.removeFlowElement("42");

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getFlowElementMap();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertEquals(stringFlowElementMap, process.getFlowElementMap());
  }

  /**
   * Test {@link Process#removeFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then {@link Process} (default constructor) FlowElements Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement_givenProcess_thenProcessFlowElementsEmpty() {
    // Arrange
    Process process = new Process();

    // Act
    process.removeFlowElement("42");

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#removeFlowElement(String)}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement_thenProcessFlowElementsEmpty() {
    // Arrange
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);

    // Act
    process.removeFlowElement("42");

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getFlowElementMap();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#removeFlowElement(String)}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement_thenProcessFlowElementsSizeIsOne() {
    // Arrange
    HashMap<String, FlowElement> stringFlowElementMap = new HashMap<>();
    stringFlowElementMap.put("42", new BooleanDataObject());
    stringFlowElementMap.put("foo", new AdhocSubProcess());
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElementMap()).thenReturn(stringFlowElementMap);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);

    // Act
    process.removeFlowElement("42");

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getFlowElementMap();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertEquals(stringFlowElementMap, process.getFlowElementMap());
  }

  /**
   * Test {@link Process#removeFlowElement(String)}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement_thenProcessFlowElementsSizeIsTwo() {
    // Arrange
    HashMap<String, FlowElement> stringFlowElementMap = new HashMap<>();
    stringFlowElementMap.put("42", new BooleanDataObject());
    stringFlowElementMap.put("foo", new AdhocSubProcess());
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElementMap()).thenReturn(stringFlowElementMap);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    process.addFlowElement(element);

    // Act
    process.removeFlowElement("42");

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getFlowElementMap();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(2, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertEquals(stringFlowElementMap, process.getFlowElementMap());
  }

  /**
   * Test {@link Process#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenAssociationIdIs42_thenReturnAssociation() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    Process process = new Process();
    process.addArtifact(artifact);

    // Act and Assert
    assertSame(artifact, process.getArtifact("42"));
  }

  /**
   * Test {@link Process#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());

    // Act and Assert
    assertNull(process.getArtifact("42"));
  }

  /**
   * Test {@link Process#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Process()).getArtifact("42"));
  }

  /**
   * Test {@link Process#addArtifact(Artifact)}.
   * <p>
   * Method under test: {@link Process#addArtifact(Artifact)}
   */
  @Test
  public void testAddArtifact() {
    // Arrange
    Process process = new Process();
    Association artifact = new Association();

    // Act
    process.addArtifact(artifact);

    // Assert
    Collection<Artifact> artifacts = process.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link Process#removeArtifact(String)}.
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link Process} (default constructor) Artifacts Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_givenAssociationIdIs42_when42_thenProcessArtifactsEmpty() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    Process process = new Process();
    process.addArtifact(artifact);

    // Act
    process.removeArtifact("42");

    // Assert
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link Process#removeArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link Process} (default constructor) Artifacts Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_givenProcess_when42_thenProcessArtifactsEmpty() {
    // Arrange
    Process process = new Process();

    // Act
    process.removeArtifact("42");

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link Process#removeArtifact(String)}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) Artifacts size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact_thenProcessArtifactsSizeIsOne() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());

    // Act
    process.removeArtifact("42");

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    assertTrue(parentContainer instanceof Process);
    assertTrue(flowElements.isEmpty());
    assertEquals(actualFindFlowElementsOfTypeResult, parentContainer.getFlowElements());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses2() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses3() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(2, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof Process);
    assertSame(getResult, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses5() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(2, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with
   * {@code type}, {@code goIntoSubprocesses}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses_givenProcess() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type, true).isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithType_givenProcess_thenReturnEmpty() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type).isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   * <ul>
   *   <li>Then first ParentContainer return {@link Process}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithType_thenFirstParentContainerReturnProcess() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    assertTrue(parentContainer instanceof Process);
    assertTrue(flowElements.isEmpty());
    assertEquals(actualFindFlowElementsOfTypeResult, parentContainer.getFlowElements());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   * <ul>
   *   <li>Then return first is {@link BooleanDataObject} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithType_thenReturnFirstIsBooleanDataObject() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   * <ul>
   *   <li>Then return second is {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithType_thenReturnSecondIsAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(2, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   * <ul>
   *   <li>Then return second is {@link BooleanDataObject} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfTypeWithType_thenReturnSecondIsBooleanDataObject() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(2, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(0);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   * with {@code subProcess}, {@code type}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   * with {@code subProcess}, {@code type}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType2() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   * with {@code subProcess}, {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type, true).isEmpty());
  }

  /**
   * Test
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   * with {@code subProcess}, {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses2() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   * with {@code subProcess}, {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses3() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   * with {@code subProcess}, {@code type}, {@code goIntoSubprocesses}.
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses4() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsInSubProcessOfTypeResult.get(0);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertSame(getResult, ((List<FlowElement>) flowElements).get(0));
    assertSame(parentContainer, getResult.getSubProcess());
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   * with {@code subProcess}, {@code type}.
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType_whenSubProcess() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type).isEmpty());
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_given42() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_givenAdhocSubProcess() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_givenBooleanDataObject() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_thenReturnAdhocSubProcess() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);

    // Act and Assert
    assertSame(flowElementsContainer, process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_thenReturnNull() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with
   * {@code childElement}, {@code flowElementsContainer}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParentWithChildElementFlowElementsContainer_whenProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new Process()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParentWithChildElement_given42_whenAdhocSubProcessIdIs42_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    // Act and Assert
    assertNull(process.findParent(childElement));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code 42}.</li>
   *   <li>Then return {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParentWithChildElement_givenAdhocSubProcessIdIs42_thenReturnProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    // Act and Assert
    assertSame(process, process.findParent(childElement));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParentWithChildElement_givenProcessAddFlowElementBooleanDataObject() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParentWithChildElement_givenProcess_whenAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParentWithChildElement_whenAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#clone()}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then Artifacts return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone_givenProcess_thenArtifactsReturnList() {
    // Arrange and Act
    Process actualCloneResult = (new Process()).clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getInitialFlowElement());
    assertNull(actualCloneResult.getIoSpecification());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isCandidateStarterGroupsDefined());
    assertFalse(actualCloneResult.isCandidateStarterUsersDefined());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(actualCloneResult.getCandidateStarterGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateStarterUsers().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getEventListeners().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getLanes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Process#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Process process = new Process();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    process.addAttribute(attribute);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Process#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Process process = new Process();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    process.addAttribute(attribute);
    process.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = process.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Process#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    Process process = new Process();
    process.setIoSpecification(new IOSpecification());
    process.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    IOSpecification ioSpecification = process.clone().getIoSpecification();
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
   * Test {@link Process#setValues(Process)} with {@code Process}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Process#setValues(Process)}
   */
  @Test
  public void testSetValuesWithProcess_thenCallsGetName() {
    // Arrange
    Process process = new Process();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Process otherElement = new Process();
    otherElement.addAttribute(attribute);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }
}
