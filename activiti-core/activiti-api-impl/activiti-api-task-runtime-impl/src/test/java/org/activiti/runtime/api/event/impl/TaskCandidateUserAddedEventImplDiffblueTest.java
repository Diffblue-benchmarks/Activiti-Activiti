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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.activiti.api.task.model.TaskCandidateUser;
import org.activiti.api.task.model.events.TaskCandidateUserEvent;
import org.activiti.api.task.model.impl.TaskCandidateUserImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskCandidateUserAddedEventImplDiffblueTest {
  /**
   * Test
   * {@link TaskCandidateUserAddedEventImpl#TaskCandidateUserAddedEventImpl()}.
   * <p>
   * Method under test:
   * {@link TaskCandidateUserAddedEventImpl#TaskCandidateUserAddedEventImpl()}
   */
  @Test
  @DisplayName("Test new TaskCandidateUserAddedEventImpl()")
  void testNewTaskCandidateUserAddedEventImpl() {
    // Arrange and Act
    TaskCandidateUserAddedEventImpl actualTaskCandidateUserAddedEventImpl = new TaskCandidateUserAddedEventImpl();

    // Assert
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateUserAddedEventImpl.getBusinessKey());
    assertNull(actualTaskCandidateUserAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessInstanceId());
    assertNull(actualTaskCandidateUserAddedEventImpl.getEntity());
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_ADDED,
        actualTaskCandidateUserAddedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link TaskCandidateUserAddedEventImpl#TaskCandidateUserAddedEventImpl(TaskCandidateUser)}.
   * <p>
   * Method under test:
   * {@link TaskCandidateUserAddedEventImpl#TaskCandidateUserAddedEventImpl(TaskCandidateUser)}
   */
  @Test
  @DisplayName("Test new TaskCandidateUserAddedEventImpl(TaskCandidateUser)")
  void testNewTaskCandidateUserAddedEventImpl2() {
    // Arrange
    TaskCandidateUserImpl entity = new TaskCandidateUserImpl("42", "42");

    // Act
    TaskCandidateUserAddedEventImpl actualTaskCandidateUserAddedEventImpl = new TaskCandidateUserAddedEventImpl(entity);

    // Assert
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateUserAddedEventImpl.getBusinessKey());
    assertNull(actualTaskCandidateUserAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateUserAddedEventImpl.getProcessInstanceId());
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_ADDED,
        actualTaskCandidateUserAddedEventImpl.getEventType());
    assertSame(entity, actualTaskCandidateUserAddedEventImpl.getEntity());
  }

  /**
   * Test {@link TaskCandidateUserAddedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskCandidateUserAddedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_ADDED,
        (new TaskCandidateUserAddedEventImpl()).getEventType());
  }
}
