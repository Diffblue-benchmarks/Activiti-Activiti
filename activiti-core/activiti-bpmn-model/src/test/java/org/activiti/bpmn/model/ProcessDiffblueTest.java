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
   * Method under test: {@link Process#containsFlowElementId(String)}
   */
  @Test
  public void testContainsFlowElementId() {
    // Arrange, Act and Assert
    assertFalse((new Process()).containsFlowElementId("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElement("42"));
    assertNull((new Process()).getFlowElement("42", true));
    assertNull((new Process()).getFlowElement("42", false));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement3() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement4() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElement5() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElement6() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.getFlowElement("42", false));
  }

  /**
   * Method under test: {@link Process#getFlowElement(String, boolean)}
   */
  @Test
  public void testGetFlowElement7() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElement("42", false));
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive() {
    // Arrange, Act and Assert
    assertTrue((new Process()).findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive3() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive5() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive("Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive6() {
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
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive7() {
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
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive8() {
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
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive9() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(new AdhocSubProcess(), "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive10() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(new Process(), "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive11() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive12() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive13() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive14() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(null);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithSourceRefRecursive(flowElementsContainer, "Source Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive15() {
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
   * Method under test:
   * {@link Process#findAssociationsWithSourceRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithSourceRefRecursive16() {
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
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive() {
    // Arrange, Act and Assert
    assertTrue((new Process()).findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive3() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive5() {
    // Arrange
    Process process = new Process();
    process.addArtifact(null);
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive("Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive6() {
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
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive7() {
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
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive8() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(new AdhocSubProcess(), "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive9() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(new Process(), "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive10() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive11() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(new Association());
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive12() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive13() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addArtifact(null);
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertTrue(process.findAssociationsWithTargetRefRecursive(flowElementsContainer, "Target Ref").isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive14() {
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
   * Method under test:
   * {@link Process#findAssociationsWithTargetRefRecursive(FlowElementsContainer, String)}
   */
  @Test
  public void testFindAssociationsWithTargetRefRecursive15() {
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
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainer() {
    // Arrange, Act and Assert
    assertNull((new Process()).getFlowElementsContainer("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainer2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainer3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainer4() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(process, process.getFlowElementsContainer("42"));
  }

  /**
   * Method under test: {@link Process#getFlowElementsContainer(String)}
   */
  @Test
  public void testGetFlowElementsContainer5() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.getFlowElementsContainer("42"));
  }

  /**
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer6() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new AdhocSubProcess(), "42"));
  }

  /**
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer7() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.getFlowElementsContainer(new Process(), "42"));
  }

  /**
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer8() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer9() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.getFlowElementsContainer(flowElementsContainer, "42"));
  }

  /**
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer10() {
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
   * Method under test:
   * {@link Process#getFlowElementsContainer(FlowElementsContainer, String)}
   */
  @Test
  public void testGetFlowElementsContainer11() {
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
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList() {
    // Arrange, Act and Assert
    assertNull((new Process()).findFlowElementInList("42"));
  }

  /**
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList3() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertSame(element, process.findFlowElementInList("42"));
  }

  /**
   * Method under test: {@link Process#findFlowElementInList(String)}
   */
  @Test
  public void testFindFlowElementInList4() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Id");

    Process process = new Process();
    process.addFlowElement(element);

    // Act and Assert
    assertNull(process.findFlowElementInList("42"));
  }

  /**
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement2() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("Element");

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement3() {
    // Arrange
    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Method under test: {@link Process#addFlowElement(FlowElement)}
   */
  @Test
  public void testAddFlowElement4() {
    // Arrange
    Process process = new Process();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("");

    // Act
    process.addFlowElement(element);

    // Assert
    assertSame(process, element.getParentContainer());
  }

  /**
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap() {
    // Arrange
    Process process = new Process();

    // Act
    process.addFlowElementToMap(new AdhocSubProcess());

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap2() {
    // Arrange
    Process process = new Process();

    // Act
    process.addFlowElementToMap(null);

    // Assert that nothing has changed
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap3() {
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
   * Method under test: {@link Process#addFlowElementToMap(FlowElement)}
   */
  @Test
  public void testAddFlowElementToMap4() {
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
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement() {
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
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement2() {
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
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement3() {
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
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement4() {
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
   * Method under test: {@link Process#removeFlowElement(String)}
   */
  @Test
  public void testRemoveFlowElement5() {
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
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact() {
    // Arrange, Act and Assert
    assertNull((new Process()).getArtifact("42"));
  }

  /**
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact2() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());

    // Act and Assert
    assertNull(process.getArtifact("42"));
  }

  /**
   * Method under test: {@link Process#getArtifact(String)}
   */
  @Test
  public void testGetArtifact3() {
    // Arrange
    Association artifact = new Association();
    artifact.setId("42");

    Process process = new Process();
    process.addArtifact(artifact);

    // Act and Assert
    assertSame(artifact, process.getArtifact("42"));
  }

  /**
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
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact() {
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact2() {
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test: {@link Process#removeArtifact(String)}
   */
  @Test
  public void testRemoveArtifact3() {
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfType() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type).isEmpty());
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfType2() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfType3() {
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
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfType4() {
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
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class)}
   */
  @Test
  public void testFindFlowElementsOfType5() {
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
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType6() {
    // Arrange
    Process process = new Process();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsOfType(type, true).isEmpty());
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType7() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, true);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType8() {
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
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType9() {
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
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType10() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsOfTypeResult = process.findFlowElementsOfType(type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsOfTypeResult.size());
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
  }

  /**
   * Method under test: {@link Process#findFlowElementsOfType(Class, boolean)}
   */
  @Test
  public void testFindFlowElementsOfType11() {
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
    assertSame(element, actualFindFlowElementsOfTypeResult.get(0));
    assertSame(element2, actualFindFlowElementsOfTypeResult.get(1));
  }

  /**
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type).isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType2() {
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
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType3() {
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
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType4() {
    // Arrange
    Process process = new Process();
    SubProcess subProcess = new SubProcess();
    Class<FlowElement> type = FlowElement.class;

    // Act and Assert
    assertTrue(process.findFlowElementsInSubProcessOfType(subProcess, type, true).isEmpty());
  }

  /**
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType5() {
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
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType6() {
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
   * Method under test:
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}
   */
  @Test
  public void testFindFlowElementsInSubProcessOfType7() {
    // Arrange
    Process process = new Process();

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);
    Class<FlowElement> type = FlowElement.class;

    // Act
    List<FlowElement> actualFindFlowElementsInSubProcessOfTypeResult = process
        .findFlowElementsInSubProcessOfType(subProcess, type, false);

    // Assert
    assertEquals(1, actualFindFlowElementsInSubProcessOfTypeResult.size());
    assertSame(element, actualFindFlowElementsInSubProcessOfTypeResult.get(0));
  }

  /**
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParent() {
    // Arrange
    Process process = new Process();

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParent2() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParent3() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.findParent(new AdhocSubProcess()));
  }

  /**
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParent4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess childElement = new AdhocSubProcess();
    childElement.setId("42");

    // Act and Assert
    assertNull(process.findParent(childElement));
  }

  /**
   * Method under test: {@link Process#findParent(FlowElement)}
   */
  @Test
  public void testFindParent5() {
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
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent6() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new AdhocSubProcess()));
  }

  /**
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent7() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    // Act and Assert
    assertNull(process.findParent(childElement, new Process()));
  }

  /**
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent8() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent9() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess childElement = new AdhocSubProcess();

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(process.findParent(childElement, flowElementsContainer));
  }

  /**
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent10() {
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
   * Method under test:
   * {@link Process#findParent(FlowElement, FlowElementsContainer)}
   */
  @Test
  public void testFindParent11() {
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
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone() {
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
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    Process process = new Process();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    process.addAttribute(attribute);

    // Act
    Process actualCloneResult = process.clone();

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
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
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
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    Process process = new Process();
    process.setIoSpecification(new IOSpecification());
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    process.addAttribute(attribute);

    // Act
    Process actualCloneResult = process.clone();

    // Assert
    Collection<Artifact> artifacts = actualCloneResult.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = actualCloneResult.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertNull(actualCloneResult.getId());
    IOSpecification ioSpecification = actualCloneResult.getIoSpecification();
    assertNull(ioSpecification.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getInitialFlowElement());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertFalse(actualCloneResult.isCandidateStarterGroupsDefined());
    assertFalse(actualCloneResult.isCandidateStarterUsersDefined());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(actualCloneResult.getCandidateStarterGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateStarterUsers().isEmpty());
    assertTrue(actualCloneResult.getDataObjects().isEmpty());
    assertTrue(actualCloneResult.getEventListeners().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getLanes().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link Process#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    Process process = new Process();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    process.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");
    process.addAttribute(attribute2);

    // Act
    Process actualCloneResult = process.clone();

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
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    List<ExtensionAttribute> getResult2 = attributes.get("Name");
    assertEquals(1, getResult2.size());
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
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getFlowElementMap().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult2.get(0));
  }

  /**
   * Method under test: {@link Process#setValues(Process)}
   */
  @Test
  public void testSetValues() {
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

  /**
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
}
