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

public class SubProcessDiffblueTest {
  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) FlowElementMap is {@link
   *       HashMap#HashMap()}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement SubProcess.getFlowElement(String)"})
  public void testGetFlowElement_givenSubProcessFlowElementMapIsHashMap_whenEmptyString() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(subProcess.getFlowElement(""));
  }

  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) FlowElementMap is {@link
   *       HashMap#HashMap()}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement SubProcess.getFlowElement(String)"})
  public void testGetFlowElement_givenSubProcessFlowElementMapIsHashMap_whenNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());

    // Act and Assert
    assertNull(subProcess.getFlowElement(null));
  }

  /**
   * Test {@link SubProcess#getFlowElement(String)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getFlowElement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement SubProcess.getFlowElement(String)"})
  public void testGetFlowElement_givenSubProcess_when42() {
    // Arrange, Act and Assert
    assertNull(new SubProcess().getFlowElement("42"));
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer2 = element.getParentContainer();
    FlowElementsContainer parentContainer3 = ((SubProcess) parentContainer2).getParentContainer();
    assertTrue(parentContainer3 instanceof AdhocSubProcess);
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, parentContainer3);
    assertSame(parentContainer, ((SubProcess) parentContainer2).getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement2() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    AdhocSubProcess parentContainer2 = new AdhocSubProcess();
    parentContainer.setParentContainer(parentContainer2);

    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer3 = element.getParentContainer();
    FlowElementsContainer parentContainer4 = ((SubProcess) parentContainer3).getParentContainer();
    FlowElementsContainer parentContainer5 =
        ((AdhocSubProcess) parentContainer4).getParentContainer();
    assertTrue(parentContainer5 instanceof AdhocSubProcess);
    assertTrue(parentContainer4 instanceof AdhocSubProcess);
    assertTrue(parentContainer3 instanceof SubProcess);
    assertSame(parentContainer2, parentContainer5);
    assertSame(parentContainer2, ((AdhocSubProcess) parentContainer4).getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement3() {
    // Arrange
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    Process parentContainer2 = new Process();
    parentContainer.setParentContainer(parentContainer2);

    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

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
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty string.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof SubProcess);
    assertTrue(parentContainer.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(parentContainer, element.getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ParentContainer ParentContainer is
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement_thenAdhocSubProcessParentContainerParentContainerIsNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    subProcess.setFlowElementMap(flowElementMap);
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    assertNull(((SubProcess) parentContainer).getParentContainer());
    assertNull(((SubProcess) parentContainer).getSubProcess());
    Map<String, FlowElement> flowElementMap2 = parentContainer.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ParentContainer ParentContainer is
   *       {@link Process} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement_thenAdhocSubProcessParentContainerParentContainerIsProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    Process parentContainer = new Process();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer2 = element.getParentContainer();
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, ((SubProcess) parentContainer2).getParentContainer());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) ParentContainer Artifacts {@link
   *       List}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
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
    assertSame(parentContainer, element.getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElement(FlowElement)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElement(FlowElement)"})
  public void testAddFlowElement_whenAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    subProcess.addFlowElement(element);

    // Assert
    FlowElementsContainer parentContainer = element.getParentContainer();
    Collection<FlowElement> flowElements = parentContainer.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(parentContainer instanceof SubProcess);
    assertTrue(parentContainer.getFlowElementMap().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
    assertSame(parentContainer, element.getSubProcess());
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link AdhocSubProcess} (default constructor) Id is empty string.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenEmptyString_whenAdhocSubProcessIdIsEmptyString() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
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
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) ParentContainer is {@link AdhocSubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenSubProcessParentContainerIsAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    subProcess.setFlowElementMap(flowElementMap);
    subProcess.setParentContainer(new AdhocSubProcess());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap2 = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) ParentContainer is {@link Process}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_givenSubProcessParentContainerIsProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    subProcess.setFlowElementMap(flowElementMap);
    subProcess.setParentContainer(new Process());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap2 = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
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
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) FlowElementMap size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_thenSubProcessFlowElementMapSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    HashMap<String, FlowElement> flowElementMap = new HashMap<>();
    subProcess.setFlowElementMap(flowElementMap);
    subProcess.setParentContainer(null);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    // Act
    subProcess.addFlowElementToMap(element);

    // Assert
    Map<String, FlowElement> flowElementMap2 = subProcess.getFlowElementMap();
    assertEquals(1, flowElementMap2.size());
    assertSame(flowElementMap, flowElementMap2);
    assertSame(element, flowElementMap2.get("not empty"));
  }

  /**
   * Test {@link SubProcess#addFlowElementToMap(FlowElement)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link SubProcess} (default constructor) FlowElementMap Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#addFlowElementToMap(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addFlowElementToMap(FlowElement)"})
  public void testAddFlowElementToMap_whenNull_thenSubProcessFlowElementMapEmpty() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.setFlowElementMap(new HashMap<>());
    subProcess.setParentContainer(null);

    // Act
    subProcess.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcess#containsFlowElementId(String)}.
   *
   * <p>Method under test: {@link SubProcess#containsFlowElementId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SubProcess.containsFlowElementId(String)"})
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse(new SubProcess().containsFlowElementId("42"));
  }

  /**
   * Test {@link SubProcess#getArtifact(String)}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.
   *   <li>Then return {@link Association} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact SubProcess.getArtifact(String)"})
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
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addArtifact {@link Association} (default
   *       constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact SubProcess.getArtifact(String)"})
  public void testGetArtifact_givenSubProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act and Assert
    assertNull(subProcess.getArtifact("42"));
  }

  /**
   * Test {@link SubProcess#getArtifact(String)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#getArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Artifact SubProcess.getArtifact(String)"})
  public void testGetArtifact_givenSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new SubProcess().getArtifact("42"));
  }

  /**
   * Test {@link SubProcess#addArtifact(Artifact)}.
   *
   * <p>Method under test: {@link SubProcess#addArtifact(Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.addArtifact(Artifact)"})
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
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor) Id is {@code 42}.
   *   <li>When {@code 42}.
   *   <li>Then {@link SubProcess} (default constructor) Artifacts Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.removeArtifact(String)"})
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
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>When {@code 42}.
   *   <li>Then {@link SubProcess} (default constructor) Artifacts Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.removeArtifact(String)"})
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
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) Artifacts size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#removeArtifact(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.removeArtifact(String)"})
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
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is {@code not empty}.
   *   <li>Then return FlowElementMap size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_givenAdhocSubProcessIdIsNotEmpty_thenReturnFlowElementMapSizeIsOne() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    SubProcess subProcess = new SubProcess();
    subProcess.setValues(new SubProcess());
    subProcess.addFlowElement(element);
    subProcess.addArtifact(new Association());

    // Act
    SubProcess actualCloneResult = subProcess.clone();

    // Assert
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Map<String, FlowElement> flowElementMap = actualCloneResult.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("not empty");
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element, getResult);
    assertSame(getResult, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
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
    assertTrue(((List<FlowElement>) flowElements).get(0) instanceof AdhocSubProcess);
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>Then Artifacts return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_givenSubProcess_thenArtifactsReturnList() {
    // Arrange and Act
    SubProcess actualCloneResult = new SubProcess().clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then FlowElements first Artifacts return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenFlowElementsFirstArtifactsReturnList() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    SubProcess subProcess = new SubProcess();
    subProcess.setValues(new SubProcess());
    subProcess.addFlowElement(element);
    subProcess.addArtifact(new Association());

    // Act and Assert
    Collection<FlowElement> flowElements = subProcess.clone().getFlowElements();
    assertEquals(1, flowElements.size());
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertEquals("", getResult.getId());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then FlowElements first return {@link AdhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenFlowElementsFirstReturnAdhocSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    Collection<FlowElement> flowElements = subProcess.clone().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(((List<FlowElement>) flowElements).get(0) instanceof AdhocSubProcess);
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then return BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenReturnBoundaryEventsSizeIsOne() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvents.add(boundaryEvent);

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(null);
    adhocSubProcess.setIoSpecification(null);
    adhocSubProcess.setDataInputAssociations(null);
    adhocSubProcess.setDataOutputAssociations(null);
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<BoundaryEvent> boundaryEvents2 = adhocSubProcess.clone().getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    assertSame(boundaryEvent, boundaryEvents2.get(0));
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(null);
    adhocSubProcess.setIoSpecification(null);
    adhocSubProcess.setDataInputAssociations(dataInputAssociations);
    adhocSubProcess.setDataOutputAssociations(null);
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 =
        adhocSubProcess.clone().getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(null);
    adhocSubProcess.setIoSpecification(null);
    adhocSubProcess.setDataInputAssociations(null);
    adhocSubProcess.setDataOutputAssociations(dataOutputAssociations);
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 =
        adhocSubProcess.clone().getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#clone()}.
   *
   * <ul>
   *   <li>Then return FlowElements first is {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
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
   *
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(null);
    adhocSubProcess.setIoSpecification(new IOSpecification());
    adhocSubProcess.setDataInputAssociations(null);
    adhocSubProcess.setDataOutputAssociations(null);
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = adhocSubProcess.clone().getIoSpecification();
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
   *
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess SubProcess.clone()"})
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    adhocSubProcess.setIoSpecification(null);
    adhocSubProcess.setDataInputAssociations(null);
    adhocSubProcess.setDataOutputAssociations(null);
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Act
    SubProcess actualCloneResult = adhocSubProcess.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics =
        actualCloneResult.getLoopCharacteristics();
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
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    SubProcess otherElement = new SubProcess();
    otherElement.setValues(new SubProcess());
    otherElement.addFlowElement(element);
    otherElement.addArtifact(new Association());

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("not empty");
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer = getResult.getParentContainer();
    assertTrue(parentContainer instanceof SubProcess);
    assertSame(subProcess, parentContainer);
    assertSame(subProcess, getResult.getSubProcess());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess2() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    SubProcess otherElement = new SubProcess();
    otherElement.setValues(new SubProcess());
    otherElement.addFlowElement(element);
    Association artifact = new Association();
    otherElement.addArtifact(artifact);

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
    Collection<FlowElement> flowElements2 = parentContainer.getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(parentContainer instanceof SubProcess);
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess3() {
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
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess4() {
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
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertTrue(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess5() {
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
    MultiInstanceLoopCharacteristics loopCharacteristics =
        ((SubProcess) parentContainer).getLoopCharacteristics();
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
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess6() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();
    subProcess.setParentContainer(parentContainer);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("not empty");

    SubProcess otherElement = new SubProcess();
    otherElement.setValues(new SubProcess());
    otherElement.addFlowElement(element);
    otherElement.addArtifact(new Association());

    // Act
    subProcess.setValues(otherElement);

    // Assert
    Map<String, FlowElement> flowElementMap = otherElement.getFlowElementMap();
    assertEquals(1, flowElementMap.size());
    FlowElement getResult = flowElementMap.get("not empty");
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElementsContainer parentContainer2 = getResult.getParentContainer();
    FlowElementsContainer parentContainer3 = ((SubProcess) parentContainer2).getParentContainer();
    assertTrue(parentContainer3 instanceof AdhocSubProcess);
    assertTrue(parentContainer2 instanceof SubProcess);
    assertSame(parentContainer, parentContainer3);
    assertSame(parentContainer, ((SubProcess) parentContainer2).getSubProcess());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess_thenSubProcessBoundaryEventsSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvents.add(boundaryEvent);

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);
    otherElement.setExecutionListeners(null);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    List<BoundaryEvent> boundaryEvents2 = subProcess.getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    assertSame(boundaryEvent, boundaryEvents2.get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess_thenSubProcessDataInputAssociationsSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);
    otherElement.setExecutionListeners(null);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    List<DataAssociation> dataInputAssociations2 = subProcess.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) DataOutputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess_thenSubProcessDataOutputAssociationsSizeIsOne() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);
    otherElement.setExecutionListeners(null);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    List<DataAssociation> dataOutputAssociations2 = subProcess.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) FlowElements first {@link
   *       BooleanDataObject}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
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
    assertNull(((SubProcess) parentContainer).getLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SubProcess) parentContainer).isForCompensation());
    assertSame(element, ((List<FlowElement>) flowElements2).get(0));
  }

  /**
   * Test {@link SubProcess#setValues(SubProcess)} with {@code SubProcess}.
   *
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess_thenSubProcessIoSpecificationIdIsNull() {
    // Arrange
    SubProcess subProcess = new SubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(new IOSpecification());
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);
    otherElement.setExecutionListeners(null);

    // Act
    subProcess.setValues(otherElement);

    // Assert
    IOSpecification ioSpecification = subProcess.getIoSpecification();
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
   *
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).
   *   <li>Then {@link SubProcess} (default constructor) BoundaryEvents Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcess#setValues(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcess.setValues(SubProcess)"})
  public void testSetValuesWithSubProcess_whenSubProcess_thenSubProcessBoundaryEventsEmpty() {
    // Arrange
    SubProcess subProcess = new SubProcess();
    SubProcess otherElement = new SubProcess();

    // Act
    subProcess.setValues(otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getBoundaryEvents().isEmpty());
    assertTrue(otherElement.getDataInputAssociations().isEmpty());
    assertTrue(otherElement.getDataOutputAssociations().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SubProcess.<init>()",
    "Collection SubProcess.getArtifacts()",
    "List SubProcess.getDataObjects()",
    "Map SubProcess.getFlowElementMap()",
    "Collection SubProcess.getFlowElements()",
    "void SubProcess.setDataObjects(List)",
    "void SubProcess.setFlowElementMap(Map)"
  })
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

    // Assert
    assertTrue(actualArtifacts instanceof List);
    assertTrue(actualSubProcess.getFlowElements() instanceof List);
    assertNull(actualSubProcess.getBehavior());
    assertNull(actualSubProcess.getDefaultFlow());
    assertNull(actualSubProcess.getFailedJobRetryTimeCycleValue());
    assertNull(actualSubProcess.getId());
    assertNull(actualSubProcess.getDocumentation());
    assertNull(actualSubProcess.getName());
    assertNull(actualSubProcess.getParentContainer());
    assertNull(actualSubProcess.getIoSpecification());
    assertNull(actualSubProcess.getLoopCharacteristics());
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
