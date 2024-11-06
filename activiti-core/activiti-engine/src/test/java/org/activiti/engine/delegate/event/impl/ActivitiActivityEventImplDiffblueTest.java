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

public class ActivitiActivityEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiActivityEventImpl#ActivitiActivityEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiActivityEventImpl#ActivitiActivityEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiActivityEventImpl_whenEntityCreated_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ActivitiActivityEventImpl actualActivitiActivityEventImpl = new ActivitiActivityEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiActivityEventImpl.getActivityId());
    assertNull(actualActivitiActivityEventImpl.getActivityName());
    assertNull(actualActivitiActivityEventImpl.getActivityType());
    assertNull(actualActivitiActivityEventImpl.getBehaviorClass());
    assertNull(actualActivitiActivityEventImpl.getExecutionId());
    assertNull(actualActivitiActivityEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiActivityEventImpl.getProcessInstanceId());
    assertNull(actualActivitiActivityEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiActivityEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiActivityEventImpl#setActivityId(String)}
   *   <li>{@link ActivitiActivityEventImpl#setActivityName(String)}
   *   <li>{@link ActivitiActivityEventImpl#setActivityType(String)}
   *   <li>{@link ActivitiActivityEventImpl#setBehaviorClass(String)}
   *   <li>{@link ActivitiActivityEventImpl#getActivityId()}
   *   <li>{@link ActivitiActivityEventImpl#getActivityName()}
   *   <li>{@link ActivitiActivityEventImpl#getActivityType()}
   *   <li>{@link ActivitiActivityEventImpl#getBehaviorClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiActivityEventImpl activitiActivityEventImpl = new ActivitiActivityEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiActivityEventImpl.setActivityId("42");
    activitiActivityEventImpl.setActivityName("Activity Name");
    activitiActivityEventImpl.setActivityType("Activity Type");
    activitiActivityEventImpl.setBehaviorClass("Behavior Class");
    String actualActivityId = activitiActivityEventImpl.getActivityId();
    String actualActivityName = activitiActivityEventImpl.getActivityName();
    String actualActivityType = activitiActivityEventImpl.getActivityType();

    // Assert that nothing has changed
    assertEquals("42", actualActivityId);
    assertEquals("Activity Name", actualActivityName);
    assertEquals("Activity Type", actualActivityType);
    assertEquals("Behavior Class", activitiActivityEventImpl.getBehaviorClass());
  }
}
