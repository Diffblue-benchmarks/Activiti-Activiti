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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Process.<init>()",
    "Collection Process.getArtifacts()",
    "List Process.getCandidateStarterGroups()",
    "List Process.getCandidateStarterUsers()",
    "List Process.getDataObjects()",
    "String Process.getDocumentation()",
    "List Process.getEventListeners()",
    "List Process.getExecutionListeners()",
    "Map Process.getFlowElementMap()",
    "Collection Process.getFlowElements()",
    "FlowElement Process.getInitialFlowElement()",
    "IOSpecification Process.getIoSpecification()",
    "List Process.getLanes()",
    "String Process.getName()",
    "boolean Process.isCandidateStarterGroupsDefined()",
    "boolean Process.isCandidateStarterUsersDefined()",
    "boolean Process.isExecutable()",
    "void Process.setCandidateStarterGroups(List)",
    "void Process.setCandidateStarterGroupsDefined(boolean)",
    "void Process.setCandidateStarterUsers(List)",
    "void Process.setCandidateStarterUsersDefined(boolean)",
    "void Process.setDataObjects(List)",
    "void Process.setDocumentation(String)",
    "void Process.setEventListeners(List)",
    "void Process.setExecutable(boolean)",
    "void Process.setExecutionListeners(List)",
    "void Process.setFlowElementMap(Map)",
    "void Process.setInitialFlowElement(FlowElement)",
    "void Process.setIoSpecification(IOSpecification)",
    "void Process.setLanes(List)",
    "void Process.setName(String)"
  })
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
    boolean actualIsCandidateStarterGroupsDefinedResult =
        actualProcess.isCandidateStarterGroupsDefined();
    boolean actualIsCandidateStarterUsersDefinedResult =
        actualProcess.isCandidateStarterUsersDefined();
    boolean actualIsExecutableResult = actualProcess.isExecutable();

    // Assert
    assertTrue(actualArtifacts instanceof List);
    assertTrue(actualFlowElements instanceof List);
    assertEquals("Documentation", actualDocumentation);
    assertEquals("Name", actualName);
    assertNull(actualProcess.getId());
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
   *
   * <p>Method under test: {@link Process#containsFlowElementId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Process.containsFlowElementId(String)"})
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse(new Process().containsFlowElementId("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with {@code flowElementId}, {@code
   * searchRecurive}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String, boolean)"})
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenAdhocSubProcessIdIsFoo() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("foo");

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with {@code flowElementId}, {@code
   * searchRecurive}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String, boolean)"})
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenAdhocSubProcessIdIsNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with {@code flowElementId}, {@code
   * searchRecurive}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String, boolean)"})
  public void testGetFlowElementWithFlowElementIdSearchRecurive_givenProcess_whenTrue() {
    // Arrange, Act and Assert
    assertNull(new Process().getFlowElement("42", true));
  }

  /**
   * Test {@link Process#getFlowElement(String, boolean)} with {@code flowElementId}, {@code
   * searchRecurive}.
   *
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String, boolean)"})
  public void testGetFlowElementWithFlowElementIdSearchRecurive_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42", false));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code foo}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String)"})
  public void testGetFlowElementWithFlowElementId_givenAdhocSubProcessIdIsFoo_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("foo");

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String)"})
  public void testGetFlowElementWithFlowElementId_givenAdhocSubProcessIdIsNull_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String)"})
  public void testGetFlowElementWithFlowElementId_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Process().getFlowElement("42"));
  }

  /**
   * Test {@link Process#getFlowElement(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.getFlowElement(String)"})
  public void testGetFlowElementWithFlowElementId_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42"));
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithSourceRefRecursive(new AdhocSubProcess(), "Source Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef2() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Flow Elements Container");
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setSourceRef("Flow Elements Container");
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setSourceRef("Flow Elements Container");
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef3() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Flow Elements Container");
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setSourceRef("Flow Elements Container");
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setSourceRef("Flow Elements Container");
    artifact3.setTargetRef(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef4() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Flow Elements Container");
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setSourceRef("Flow Elements Container");
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setSourceRef(null);
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef5() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(
        process.findAssociationsWithSourceRefRecursive(new Process(), "Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef6() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Source Ref");
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setSourceRef("Flow Elements Container");
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setSourceRef("Flow Elements Container");
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act
    List<Association> actualFindAssociationsWithSourceRefRecursiveResult =
        process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithSourceRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithSourceRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code sourceRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithSourceRefRecursiveWithFlowElementsContainerSourceRef7() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setSourceRef("Flow Elements Container");
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setSourceRef("Flow Elements Container");
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setSourceRef("Flow Elements Container");
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new BooleanDataObject());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef4() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
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
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
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
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef_givenProcess() {
    // Arrange, Act and Assert
    assertTrue(new Process().findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithSourceRefRecursive(String)} with {@code sourceRef}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithSourceRefRecursive(String)"})
  public void testFindAssociationsWithSourceRefRecursiveWithSourceRef_thenReturnSizeIsOne() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");
    artifact.setSourceRef("Source Ref");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithSourceRefRecursiveResult =
        process.findAssociationsWithSourceRefRecursive("Source Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithSourceRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithSourceRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithTargetRefRecursive(new AdhocSubProcess(), "Target Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef2() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef3() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setTargetRef(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef4() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(
        process.findAssociationsWithTargetRefRecursive(new Process(), "Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef5() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");

    Association artifact2 = new Association();
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act
    List<Association> actualFindAssociationsWithTargetRefRecursiveResult =
        process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithTargetRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithTargetRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)} with
   * {@code flowElementsContainer}, {@code targetRef}.
   *
   * <p>Method under test: {@link
   * Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List Process.findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)"
  })
  public void testFindAssociationsWithTargetRefRecursiveWithFlowElementsContainerTargetRef6() {
    // Arrange
    Process process = new Process();

    Association artifact = new Association();
    artifact.setTargetRef("Flow Elements Container");

    Association artifact2 = new Association();
    artifact2.setTargetRef("Flow Elements Container");

    Association artifact3 = new Association();
    artifact3.setTargetRef("Flow Elements Container");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(artifact3);
    element.addFlowElement(new BooleanDataObject());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addArtifact(artifact2);
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(artifact);
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertTrue(
        process
            .findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref")
            .isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef4() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
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
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef_givenProcess() {
    // Arrange, Act and Assert
    assertTrue(new Process().findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Test {@link Process#findAssociationsWithTargetRefRecursive(String)} with {@code targetRef}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findAssociationsWithTargetRefRecursive(String)"})
  public void testFindAssociationsWithTargetRefRecursiveWithTargetRef_thenReturnSizeIsOne() {
    // Arrange
    Association artifact = new Association();
    artifact.setTargetRef("Target Ref");

    Process process = new Process();
    process.addArtifact(artifact);
    process.addFlowElement(new AdhocSubProcess());

    // Act
    List<Association> actualFindAssociationsWithTargetRefRecursiveResult =
        process.findAssociationsWithTargetRefRecursive("Target Ref");

    // Assert
    assertEquals(1, actualFindAssociationsWithTargetRefRecursiveResult.size());
    assertSame(artifact, actualFindAssociationsWithTargetRefRecursiveResult.get(0));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.getFlowElementsContainer(String)"})
  public void testGetFlowElementsContainerWithFlowElementId() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.getFlowElementsContainer(String)"})
  public void testGetFlowElementsContainerWithFlowElementId2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code Id}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.getFlowElementsContainer(String)"})
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
   * Test {@link Process#getFlowElementsContainer(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.getFlowElementsContainer(String)"})
  public void testGetFlowElementsContainerWithFlowElementId_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Process().getFlowElementsContainer("42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(String)} with {@code flowElementId}.
   *
   * <ul>
   *   <li>Then return {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.getFlowElementsContainer(String)"})
  public void testGetFlowElementsContainerWithFlowElementId_thenReturnProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act
    FlowElementsContainer actualFlowElementsContainer = process.getFlowElementsContainer("42");

    // Assert
    assertSame(process, actualFlowElementsContainer);
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new AdhocSubProcess(), "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId2() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Flow Elements Container");

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.setId("Flow Elements Container");
    element2.addFlowElement(element);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.setId("Flow Elements Container");
    element3.addFlowElement(element2);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId3() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.setId("Flow Elements Container");
    element2.addFlowElement(element);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.setId("Flow Elements Container");
    element3.addFlowElement(element2);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId4() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Flow Elements Container");

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.setId("Flow Elements Container");
    element2.addFlowElement(element);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.setId("42");
    element3.addFlowElement(element2);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act
    FlowElementsContainer actualFlowElementsContainer =
        process.getFlowElementsContainer(flowElementsContainer, "42");

    // Assert
    Map<String, FlowElement> flowElementMap = actualFlowElementsContainer.getFlowElementMap();
    assertEquals(2, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("Flow Elements Container");
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    Map<String, FlowElement> flowElementMap2 = ((AdhocSubProcess) getResult).getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    FlowElement getResult2 = flowElementMap2.get("Flow Elements Container");
    assertTrue(getResult2 instanceof AdhocSubProcess);
    assertTrue(actualFlowElementsContainer instanceof AdhocSubProcess);
    assertNull(((AdhocSubProcess) actualFlowElementsContainer).getId());
    assertNull(((AdhocSubProcess) actualFlowElementsContainer).getParentContainer());
    assertNull(((AdhocSubProcess) actualFlowElementsContainer).getSubProcess());
    assertTrue(flowElementMap.containsKey("42"));
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, getResult2);
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId5() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Flow Elements Container");

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.setId("42");
    element2.addFlowElement(element);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.setId("Flow Elements Container");
    element3.addFlowElement(element2);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act
    FlowElementsContainer actualFlowElementsContainer =
        process.getFlowElementsContainer(flowElementsContainer, "42");

    // Assert
    FlowElementsContainer parentContainer =
        ((AdhocSubProcess) actualFlowElementsContainer).getParentContainer();
    assertTrue(parentContainer instanceof AdhocSubProcess);
    assertTrue(actualFlowElementsContainer instanceof AdhocSubProcess);
    assertEquals(
        "Flow Elements Container", ((AdhocSubProcess) actualFlowElementsContainer).getId());
    assertSame(flowElementsContainer, parentContainer);
    assertSame(
        flowElementsContainer, ((AdhocSubProcess) actualFlowElementsContainer).getSubProcess());
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId6() {
    // Arrange
    Process process = new Process();

    BooleanDataObject element = new BooleanDataObject();
    element.setId("Flow Elements Container");

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.setId("Flow Elements Container");
    element2.addFlowElement(element);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.setId("Flow Elements Container");
    element3.addFlowElement(element2);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Test {@link Process#getFlowElementsContainer(FlowElementsContainer, String)} with {@code
   * flowElementsContainer}, {@code flowElementId}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.getFlowElementsContainer(FlowElementsContainer, String)"
  })
  public void testGetFlowElementsContainerWithFlowElementsContainerFlowElementId_whenProcess() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new Process(), "42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   *   <li>Then return {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.findFlowElementInList(String)"})
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
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code foo}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.findFlowElementInList(String)"})
  public void testFindFlowElementInList_givenAdhocSubProcessIdIsFoo_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("foo");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.findFlowElementInList(String)"})
  public void testFindFlowElementInList_givenAdhocSubProcessIdIsNull_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId(null);

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#findFlowElementInList(String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Process.findFlowElementInList(String)"})
  public void testFindFlowElementInList_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Process().findFlowElementInList("42"));
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty string.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElement(FlowElement)"})
  public void testAddFlowElement_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ParentContainer FlowElements size is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElement(FlowElement)"})
  public void testAddFlowElement_thenAdhocSubProcessParentContainerFlowElementsSizeIsOne() {
    // Arrange
    Process process = new Process();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    process.setFlowElementMap(flowElementMap);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    process.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof Process);
    Map<String, FlowElement> flowElementMap2 = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) ParentContainer Artifacts {@link
   *       List}.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElement(FlowElement)"})
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
  }

  /**
   * Test {@link Process#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElement(FlowElement)"})
  public void testAddFlowElement_whenAdhocSubProcess() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty string.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    process.addFlowElementToMap(element);

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given {@code not empty}.
   *   <li>Then {@link Process} (default constructor) FlowElementMap size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenNotEmpty_thenProcessFlowElementMapSizeIsOne() {
    // Arrange
    Process process = new Process();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    process.setFlowElementMap(flowElementMap);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    process.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap2 = process.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenProcess_whenAdhocSubProcess() {
    // Arrange
    Process process = new Process();

    // Act
    process.addFlowElementToMap(new AdhocSubProcess());

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link Process} (default constructor) FlowElementMap Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_whenNull_thenProcessFlowElementMapEmpty() {
    // Arrange
    Process process = new Process();
    process.setFlowElementMap(new HashMap<>());

    // Act
    process.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link Process#getArtifact(String)}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.
   *   <li>Then return {@link Association} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact Process.getArtifact(String)"})
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
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact {@link Association} (default
   *       constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact Process.getArtifact(String)"})
  public void testGetArtifact_givenProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());

    // Act and Assert
    assertNull(process.getArtifact("42"));
  }

  /**
   * Test {@link Process#getArtifact(String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact Process.getArtifact(String)"})
  public void testGetArtifact_givenProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Process().getArtifact("42"));
  }

  /**
   * Test {@link Process#addArtifact(Artifact)}.
   *
   * <p>Method under test: {@link Process#addArtifact(Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.addArtifact(Artifact)"})
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
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.
   *   <li>When {@code 42}.
   *   <li>Then {@link Process} (default constructor) Artifacts Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.removeArtifact(String)"})
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
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@code 42}.
   *   <li>Then {@link Process} (default constructor) Artifacts Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.removeArtifact(String)"})
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
   *
   * <ul>
   *   <li>Then {@link Process} (default constructor) Artifacts size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.removeArtifact(String)"})
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
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult =
        process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses2() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult =
        process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses3() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult =
        process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(2, actualFindFlowElementsOfTypeResult.size());
    assertTrue(actualFindFlowElementsOfTypeResult.get(0) instanceof AdhocSubProcess);
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(1);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, getResult);
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses4() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult =
        process.findFlowElementsOfType(type, true);

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
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses_givenProcess() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type, true).isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class, boolean)} with {@code type}, {@code
   * goIntoSubprocesses}.
   *
   * <ul>
   *   <li>Then return first is {@link SubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class, boolean)"})
  public void testFindFlowElementsOfTypeWithTypeGoIntoSubprocesses_thenReturnFirstIsSubProcess() {
    // Arrange
    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    SubProcess element2 = new SubProcess();
    element2.addFlowElement(element);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element3);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult =
        process.findFlowElementsOfType(type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element3, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class)"})
  public void testFindFlowElementsOfTypeWithType_givenProcess_thenReturnEmpty() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type).isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   *
   * <ul>
   *   <li>Then fourth return {@link AdhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class)"})
  public void testFindFlowElementsOfTypeWithType_thenFourthReturnAdhocSubProcess() {
    // Arrange
    SubProcess element = new SubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element);

    SubProcess element4 = new SubProcess();
    element4.addFlowElement(element3);

    Process process = new Process();
    process.addFlowElement(element4);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(4, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(3);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(actualFindFlowElementsOfTypeResult.get(2) instanceof SubProcess);
    assertSame(element2, getResult);
  }

  /**
   * Test {@link Process#findFlowElementsOfType(Class)} with {@code type}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class)"})
  public void testFindFlowElementsOfTypeWithType_thenReturnSizeIsOne() {
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
   *
   * <ul>
   *   <li>Then return third FlowElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsOfType(Class)"})
  public void testFindFlowElementsOfTypeWithType_thenReturnThirdFlowElementsSizeIsOne() {
    // Arrange
    SubProcess element = new SubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element);

    SubProcess element4 = new SubProcess();
    element4.addFlowElement(element3);

    Process process = new Process();
    process.addFlowElement(element4);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(4, actualFindFlowElementsOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsOfTypeResult.get(2);
    Collection<FlowElement> flowElements = ((SubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof SubProcess);
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(3));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)} with {@code
   * subProcess}, {@code type}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType() {
    // Arrange
    Process process = new Process();

    SubProcess element = new SubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element);

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element3);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult =
        process.findFlowElementsInSubProcessOfType(subProcess, type);

    // Assert
    assertEquals(3, actualFindFlowElementsInSubProcessOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsInSubProcessOfTypeResult.get(2);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(actualFindFlowElementsInSubProcessOfTypeResult.get(1) instanceof SubProcess);
    assertSame(element2, getResult);
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)} with {@code
   * subProcess}, {@code type}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType2() {
    // Arrange
    Process process = new Process();

    SubProcess element = new SubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element);

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element3);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult =
        process.findFlowElementsInSubProcessOfType(subProcess, type);

    // Assert
    assertEquals(3, actualFindFlowElementsInSubProcessOfTypeResult.size());
    FlowElement getResult = actualFindFlowElementsInSubProcessOfTypeResult.get(1);
    Collection<FlowElement> flowElements = ((SubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof SubProcess);
    assertSame(element2, actualFindFlowElementsInSubProcessOfTypeResult.get(2));
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)} with {@code
   * subProcess}, {@code type}, {@code goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type, true).isEmpty());
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)} with {@code
   * subProcess}, {@code type}, {@code goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses2() {
    // Arrange
    Process process = new Process();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    SubProcess element2 = new SubProcess();
    element2.addFlowElement(element);

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(element2);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult =
        process.findFlowElementsInSubProcessOfType(subProcess, type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element2, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)} with {@code
   * subProcess}, {@code type}, {@code goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses3() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult =
        process.findFlowElementsInSubProcessOfType(subProcess, type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)} with {@code
   * subProcess}, {@code type}, {@code goIntoSubprocesses}.
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessTypeGoIntoSubprocesses4() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    BooleanDataObject element = new BooleanDataObject();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult =
        process.findFlowElementsInSubProcessOfType(subProcess, type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Test {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)} with {@code
   * subProcess}, {@code type}.
   *
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Process.findFlowElementsInSubProcessOfType(SubProcess, Class)"})
  public void testFindFlowElementsInSubProcessOfTypeWithSubProcessType_whenSubProcess() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type).isEmpty());
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with {@code childElement},
   * {@code flowElementsContainer}.
   *
   * <p>Method under test: {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.findParent(FlowElement, FlowElementsContainer)"
  })
  public void testFindParentWithChildElementFlowElementsContainer() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("Child Element");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BooleanDataObject());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with {@code childElement},
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>Given {@code Child Element}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.findParent(FlowElement, FlowElementsContainer)"
  })
  public void testFindParentWithChildElementFlowElementsContainer_givenChildElement() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("Child Element");

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with {@code childElement},
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.findParent(FlowElement, FlowElementsContainer)"
  })
  public void testFindParentWithChildElementFlowElementsContainer_givenNull() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess element2 = new AdhocSubProcess();
    element2.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element2);

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with {@code childElement},
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.findParent(FlowElement, FlowElementsContainer)"
  })
  public void testFindParentWithChildElementFlowElementsContainer_whenAdhocSubProcess() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement, FlowElementsContainer)} with {@code childElement},
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElementsContainer Process.findParent(FlowElement, FlowElementsContainer)"
  })
  public void testFindParentWithChildElementFlowElementsContainer_whenProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new Process()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.findParent(FlowElement)"})
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
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   *   <li>Then return {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.findParent(FlowElement)"})
  public void testFindParentWithChildElement_givenAdhocSubProcessIdIs42_thenReturnProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    // Act
    FlowElementsContainer actualFindParentResult = process.findParent(childElement);

    // Assert
    assertSame(process, actualFindParentResult);
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement {@link BooleanDataObject}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.findParent(FlowElement)"})
  public void testFindParentWithChildElement_givenProcessAddFlowElementBooleanDataObject() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.findParent(FlowElement)"})
  public void testFindParentWithChildElement_givenProcess_whenAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#findParent(FlowElement)} with {@code childElement}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer Process.findParent(FlowElement)"})
  public void testFindParentWithChildElement_whenAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default constructor).
   *   <li>Then return Artifacts is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenArrayListAddEventListener_thenReturnArtifactsIsArrayList() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ActivitiListener activitiListener = new ActivitiListener();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(new ArrayList<>());
    process.setCandidateStarterGroups(new ArrayList<>());
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    List<ValuedDataObject> dataObjects2 = actualCloneResult.getDataObjects();
    assertEquals(1, dataObjects2.size());
    assertTrue(dataObjects2.get(0) instanceof BooleanDataObject);
    assertEquals(1, actualCloneResult.getEventListeners().size());
    assertEquals(fieldExtensions, actualCloneResult.getArtifacts());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   *   <li>Then return FlowElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenArrayListAddFieldExtension_thenReturnFlowElementsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(new ArrayList<>());
    process.setCandidateStarterGroups(new ArrayList<>());
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    List<ValuedDataObject> dataObjects2 = actualCloneResult.getDataObjects();
    assertEquals(1, dataObjects2.size());
    assertTrue(dataObjects2.get(0) instanceof BooleanDataObject);
    assertEquals(1, actualCloneResult.getEventListeners().size());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return CandidateStarterGroups size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenArrayListAddFoo_thenReturnCandidateStarterGroupsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<String> candidateStarterGroups = new ArrayList<>();
    candidateStarterGroups.add("foo");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    ArrayList<String> candidateStarterUsers = new ArrayList<>();
    process.setCandidateStarterUsers(candidateStarterUsers);
    process.setCandidateStarterGroups(candidateStarterGroups);
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    List<String> candidateStarterGroups2 = actualCloneResult.getCandidateStarterGroups();
    assertEquals(1, candidateStarterGroups2.size());
    assertEquals("foo", candidateStarterGroups2.get(0));
    assertEquals(candidateStarterUsers, actualCloneResult.getArtifacts());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return CandidateStarterUsers size is one.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenArrayListAddFoo_thenReturnCandidateStarterUsersSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<String> candidateStarterUsers = new ArrayList<>();
    candidateStarterUsers.add("foo");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(candidateStarterUsers);
    ArrayList<String> candidateStarterGroups = new ArrayList<>();
    process.setCandidateStarterGroups(candidateStarterGroups);
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    List<String> candidateStarterUsers2 = actualCloneResult.getCandidateStarterUsers();
    assertEquals(1, candidateStarterUsers2.size());
    assertEquals("foo", candidateStarterUsers2.get(0));
    assertEquals(candidateStarterGroups, actualCloneResult.getArtifacts());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) CandidateStarterGroups is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenProcessCandidateStarterGroupsIsNull() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(new ArrayList<>());
    process.setCandidateStarterGroups(null);
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    List<ValuedDataObject> dataObjects2 = actualCloneResult.getDataObjects();
    assertEquals(1, dataObjects2.size());
    assertTrue(dataObjects2.get(0) instanceof BooleanDataObject);
    assertEquals(1, actualCloneResult.getEventListeners().size());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) CandidateStarterUsers is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenProcessCandidateStarterUsersIsNull() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(null);
    process.setCandidateStarterGroups(new ArrayList<>());
    process.setEventListeners(eventListeners);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    List<ValuedDataObject> dataObjects2 = actualCloneResult.getDataObjects();
    assertEquals(1, dataObjects2.size());
    assertTrue(dataObjects2.get(0) instanceof BooleanDataObject);
    assertEquals(1, actualCloneResult.getEventListeners().size());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) DataObjects is {@code null}.
   *   <li>Then return EventListeners first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenProcessDataObjectsIsNull_thenReturnEventListenersFirstIdIsNull() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    ArrayList<String> candidateStarterUsers = new ArrayList<>();
    process.setCandidateStarterUsers(candidateStarterUsers);
    process.setCandidateStarterGroups(new ArrayList<>());
    process.setEventListeners(eventListeners);
    process.setDataObjects(null);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    List<EventListener> eventListeners2 = actualCloneResult.getEventListeners();
    assertEquals(1, eventListeners2.size());
    EventListener getResult = eventListeners2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getEntityType());
    assertNull(getResult.getEvents());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    List<ActivitiListener> executionListeners2 = actualCloneResult.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    assertEquals(1, executionListeners2.get(0).getFieldExtensions().size());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertEquals(candidateStarterUsers, actualCloneResult.getFlowElements());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) EventListeners is {@code null}.
   *   <li>Then return EventListeners Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenProcessEventListenersIsNull_thenReturnEventListenersEmpty() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process process = new Process();
    process.setIoSpecification(ioSpecification);
    process.setExecutionListeners(executionListeners);
    process.setCandidateStarterUsers(new ArrayList<>());
    process.setCandidateStarterGroups(new ArrayList<>());
    process.setEventListeners(null);
    process.setDataObjects(dataObjects);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    List<ValuedDataObject> dataObjects2 = actualCloneResult.getDataObjects();
    assertEquals(1, dataObjects2.size());
    assertTrue(dataObjects2.get(0) instanceof BooleanDataObject);
    assertTrue(actualCloneResult.getEventListeners().isEmpty());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return FlowElements Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_givenProcess_thenReturnFlowElementsEmpty() {
    // Arrange and Act
    Process actualCloneResult = new Process().clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
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
   *
   * <ul>
   *   <li>Then return IoSpecification DataInputs Empty.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_thenReturnIoSpecificationDataInputsEmpty() {
    // Arrange
    Process process = new Process();
    process.setIoSpecification(new IOSpecification());
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    process.addAttribute(attribute);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    IOSpecification ioSpecification = actualCloneResult.getIoSpecification();
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Process#clone()}.
   *
   * <ul>
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Process#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Process Process.clone()"})
  public void testClone_thenReturnIoSpecificationIsNull() {
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
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataSpec} (default constructor).
   *   <li>Then calls {@link DataSpec#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenArrayListAddDataSpec_thenCallsClone() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(dataSpec).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then calls {@link ActivitiListener#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenArrayListAddFoo_thenCallsClone() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<String> candidateStarterUsers = new ArrayList<>();
    candidateStarterUsers.add("foo");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(candidateStarterUsers);
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then calls {@link ActivitiListener#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenArrayListAddFoo_thenCallsClone2() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<String> candidateStarterGroups = new ArrayList<>();
    candidateStarterGroups.add("foo");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(candidateStarterGroups);
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} {@link BooleanDataObject#clone()} return {@link
   *       BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenBooleanDataObjectCloneReturnBooleanDataObject() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.clone()).thenReturn(new EventListener());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    BooleanDataObject booleanDataObject = mock(BooleanDataObject.class);
    when(booleanDataObject.clone()).thenReturn(new BooleanDataObject());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(booleanDataObject).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
    verify(eventListener).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} {@link BooleanDataObject#getId()} return {@code 42}.
   *   <li>Then calls {@link BooleanDataObject#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenBooleanDataObjectGetIdReturn42_thenCallsGetId() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.clone()).thenReturn(new EventListener());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    BooleanDataObject booleanDataObject = mock(BooleanDataObject.class);
    when(booleanDataObject.getId()).thenReturn("42");
    doNothing().when(booleanDataObject).setParentContainer(Mockito.<FlowElementsContainer>any());

    BooleanDataObject booleanDataObject2 = mock(BooleanDataObject.class);
    when(booleanDataObject2.clone()).thenReturn(booleanDataObject);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject2);

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(booleanDataObject, atLeast(1)).getId();
    verify(booleanDataObject2).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
    verify(eventListener).clone();
    verify(booleanDataObject).setParentContainer(isA(FlowElementsContainer.class));
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} {@link BooleanDataObject#getId()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_givenBooleanDataObjectGetIdReturnEmptyString() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.clone()).thenReturn(new EventListener());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    BooleanDataObject booleanDataObject = mock(BooleanDataObject.class);
    when(booleanDataObject.getId()).thenReturn("");
    doNothing().when(booleanDataObject).setParentContainer(Mockito.<FlowElementsContainer>any());

    BooleanDataObject booleanDataObject2 = mock(BooleanDataObject.class);
    when(booleanDataObject2.clone()).thenReturn(booleanDataObject);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject2);

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(booleanDataObject, atLeast(1)).getId();
    verify(booleanDataObject2).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
    verify(eventListener).clone();
    verify(booleanDataObject).setParentContainer(isA(FlowElementsContainer.class));
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_thenCallsClone() {
    // Arrange
    Process process = new Process();

    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.addAttribute(new ExtensionAttribute("Name"));

    // Act
    process.setValues(otherElement);

    // Assert
    verify(ioSpecification).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Then calls {@link DataSpec#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_thenCallsClone2() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiListener#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_thenCallsClone3() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link Process#setValues(Process)} with {@code Process}.
   *
   * <ul>
   *   <li>Then calls {@link EventListener#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link Process#setValues(Process)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Process.setValues(Process)"})
  public void testSetValuesWithProcess_thenCallsClone4() {
    // Arrange
    Process process = new Process();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.clone()).thenReturn(new EventListener());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());

    Process otherElement = new Process();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setExecutionListeners(executionListeners);
    otherElement.setCandidateStarterUsers(new ArrayList<>());
    otherElement.setCandidateStarterGroups(new ArrayList<>());
    otherElement.setEventListeners(eventListeners);
    otherElement.setDataObjects(dataObjects);

    // Act
    process.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
    verify(eventListener).clone();
  }
}
