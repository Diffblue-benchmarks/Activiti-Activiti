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
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class AdhocSubProcessDiffblueTest {
  /**
   * Test {@link AdhocSubProcess#hasParallelOrdering()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdhocSubProcess#hasParallelOrdering()}
   */
  @Test
  public void testHasParallelOrdering_givenAdhocSubProcess_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).hasParallelOrdering());
  }

  /**
   * Test {@link AdhocSubProcess#hasParallelOrdering()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdhocSubProcess#hasParallelOrdering()}
   */
  @Test
  public void testHasParallelOrdering_thenReturnFalse() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setOrdering(AdhocSubProcess.ORDERING_SEQUENTIALL);

    // Act and Assert
    assertFalse(adhocSubProcess.hasParallelOrdering());
  }

  /**
   * Test {@link AdhocSubProcess#hasSequentialOrdering()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdhocSubProcess#hasSequentialOrdering()}
   */
  @Test
  public void testHasSequentialOrdering_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).hasSequentialOrdering());
  }

  /**
   * Test {@link AdhocSubProcess#hasSequentialOrdering()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdhocSubProcess#hasSequentialOrdering()}
   */
  @Test
  public void testHasSequentialOrdering_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setOrdering(AdhocSubProcess.ORDERING_SEQUENTIALL);

    // Act and Assert
    assertTrue(adhocSubProcess.hasSequentialOrdering());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AdhocSubProcess}
   *   <li>{@link AdhocSubProcess#setCancelRemainingInstances(boolean)}
   *   <li>{@link AdhocSubProcess#setCompletionCondition(String)}
   *   <li>{@link AdhocSubProcess#setOrdering(String)}
   *   <li>{@link AdhocSubProcess#getCompletionCondition()}
   *   <li>{@link AdhocSubProcess#getOrdering()}
   *   <li>{@link AdhocSubProcess#isCancelRemainingInstances()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    AdhocSubProcess actualAdhocSubProcess = new AdhocSubProcess();
    actualAdhocSubProcess.setCancelRemainingInstances(true);
    actualAdhocSubProcess.setCompletionCondition("Completion Condition");
    actualAdhocSubProcess.setOrdering("Ordering");
    String actualCompletionCondition = actualAdhocSubProcess.getCompletionCondition();
    String actualOrdering = actualAdhocSubProcess.getOrdering();
    boolean actualIsCancelRemainingInstancesResult = actualAdhocSubProcess.isCancelRemainingInstances();

    // Assert that nothing has changed
    assertTrue(actualAdhocSubProcess.getArtifacts() instanceof List);
    assertTrue(actualAdhocSubProcess.getFlowElements() instanceof List);
    assertEquals("Completion Condition", actualCompletionCondition);
    assertEquals("Ordering", actualOrdering);
    assertEquals(0, actualAdhocSubProcess.getXmlColumnNumber());
    assertEquals(0, actualAdhocSubProcess.getXmlRowNumber());
    assertFalse(actualAdhocSubProcess.isForCompensation());
    assertFalse(actualAdhocSubProcess.isAsynchronous());
    assertFalse(actualAdhocSubProcess.isNotExclusive());
    assertTrue(actualAdhocSubProcess.getBoundaryEvents().isEmpty());
    assertTrue(actualAdhocSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(actualAdhocSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(actualAdhocSubProcess.getMapExceptions().isEmpty());
    assertTrue(actualAdhocSubProcess.getExecutionListeners().isEmpty());
    assertTrue(actualAdhocSubProcess.getIncomingFlows().isEmpty());
    assertTrue(actualAdhocSubProcess.getOutgoingFlows().isEmpty());
    assertTrue(actualAdhocSubProcess.getDataObjects().isEmpty());
    assertTrue(actualAdhocSubProcess.getAttributes().isEmpty());
    assertTrue(actualAdhocSubProcess.getExtensionElements().isEmpty());
    assertTrue(actualAdhocSubProcess.getFlowElementMap().isEmpty());
    assertTrue(actualIsCancelRemainingInstancesResult);
  }
}
