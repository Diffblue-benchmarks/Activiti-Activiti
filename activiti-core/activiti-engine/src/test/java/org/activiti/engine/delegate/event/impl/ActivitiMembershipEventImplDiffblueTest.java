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

public class ActivitiMembershipEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiMembershipEventImpl#ActivitiMembershipEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMembershipEventImpl#ActivitiMembershipEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiMembershipEventImpl_whenEntityCreated_thenReturnExecutionIdIsNull() {
    // Arrange and Act
    ActivitiMembershipEventImpl actualActivitiMembershipEventImpl = new ActivitiMembershipEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiMembershipEventImpl.getExecutionId());
    assertNull(actualActivitiMembershipEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiMembershipEventImpl.getProcessInstanceId());
    assertNull(actualActivitiMembershipEventImpl.getReason());
    assertNull(actualActivitiMembershipEventImpl.getGroupId());
    assertNull(actualActivitiMembershipEventImpl.getUserId());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiMembershipEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiMembershipEventImpl#setGroupId(String)}
   *   <li>{@link ActivitiMembershipEventImpl#setUserId(String)}
   *   <li>{@link ActivitiMembershipEventImpl#getGroupId()}
   *   <li>{@link ActivitiMembershipEventImpl#getUserId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiMembershipEventImpl activitiMembershipEventImpl = new ActivitiMembershipEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiMembershipEventImpl.setGroupId("42");
    activitiMembershipEventImpl.setUserId("42");
    String actualGroupId = activitiMembershipEventImpl.getGroupId();

    // Assert that nothing has changed
    assertEquals("42", actualGroupId);
    assertEquals("42", activitiMembershipEventImpl.getUserId());
  }
}
