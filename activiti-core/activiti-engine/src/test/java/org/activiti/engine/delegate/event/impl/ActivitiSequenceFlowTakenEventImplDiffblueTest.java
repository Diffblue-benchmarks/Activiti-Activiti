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
package org.activiti.engine.delegate.event.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.junit.Test;

public class ActivitiSequenceFlowTakenEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiSequenceFlowTakenEventImpl#ActivitiSequenceFlowTakenEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiSequenceFlowTakenEventImpl#ActivitiSequenceFlowTakenEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiSequenceFlowTakenEventImpl_thenReturnExecutionIdIsNull() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEventImpl actualActivitiSequenceFlowTakenEventImpl = new ActivitiSequenceFlowTakenEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getExecutionId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getProcessInstanceId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getReason());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getSourceActivityBehaviorClass());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getSourceActivityId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getSourceActivityName());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getSourceActivityType());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getTargetActivityBehaviorClass());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getTargetActivityId());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getTargetActivityName());
    assertNull(actualActivitiSequenceFlowTakenEventImpl.getTargetActivityType());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiSequenceFlowTakenEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setId(String)}
   *   <li>
   * {@link ActivitiSequenceFlowTakenEventImpl#setSourceActivityBehaviorClass(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setSourceActivityId(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setSourceActivityName(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setSourceActivityType(String)}
   *   <li>
   * {@link ActivitiSequenceFlowTakenEventImpl#setTargetActivityBehaviorClass(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setTargetActivityId(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setTargetActivityName(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#setTargetActivityType(String)}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getId()}
   *   <li>
   * {@link ActivitiSequenceFlowTakenEventImpl#getSourceActivityBehaviorClass()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getSourceActivityId()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getSourceActivityName()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getSourceActivityType()}
   *   <li>
   * {@link ActivitiSequenceFlowTakenEventImpl#getTargetActivityBehaviorClass()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getTargetActivityId()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getTargetActivityName()}
   *   <li>{@link ActivitiSequenceFlowTakenEventImpl#getTargetActivityType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiSequenceFlowTakenEventImpl activitiSequenceFlowTakenEventImpl = new ActivitiSequenceFlowTakenEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiSequenceFlowTakenEventImpl.setId("42");
    activitiSequenceFlowTakenEventImpl.setSourceActivityBehaviorClass("Source Activity Behavior Class");
    activitiSequenceFlowTakenEventImpl.setSourceActivityId("42");
    activitiSequenceFlowTakenEventImpl.setSourceActivityName("Source Activity Name");
    activitiSequenceFlowTakenEventImpl.setSourceActivityType("Source Activity Type");
    activitiSequenceFlowTakenEventImpl.setTargetActivityBehaviorClass("Target Activity Behavior Class");
    activitiSequenceFlowTakenEventImpl.setTargetActivityId("42");
    activitiSequenceFlowTakenEventImpl.setTargetActivityName("Target Activity Name");
    activitiSequenceFlowTakenEventImpl.setTargetActivityType("Target Activity Type");
    String actualId = activitiSequenceFlowTakenEventImpl.getId();
    String actualSourceActivityBehaviorClass = activitiSequenceFlowTakenEventImpl.getSourceActivityBehaviorClass();
    String actualSourceActivityId = activitiSequenceFlowTakenEventImpl.getSourceActivityId();
    String actualSourceActivityName = activitiSequenceFlowTakenEventImpl.getSourceActivityName();
    String actualSourceActivityType = activitiSequenceFlowTakenEventImpl.getSourceActivityType();
    String actualTargetActivityBehaviorClass = activitiSequenceFlowTakenEventImpl.getTargetActivityBehaviorClass();
    String actualTargetActivityId = activitiSequenceFlowTakenEventImpl.getTargetActivityId();
    String actualTargetActivityName = activitiSequenceFlowTakenEventImpl.getTargetActivityName();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualSourceActivityId);
    assertEquals("42", actualTargetActivityId);
    assertEquals("Source Activity Behavior Class", actualSourceActivityBehaviorClass);
    assertEquals("Source Activity Name", actualSourceActivityName);
    assertEquals("Source Activity Type", actualSourceActivityType);
    assertEquals("Target Activity Behavior Class", actualTargetActivityBehaviorClass);
    assertEquals("Target Activity Name", actualTargetActivityName);
    assertEquals("Target Activity Type", activitiSequenceFlowTakenEventImpl.getTargetActivityType());
  }
}
