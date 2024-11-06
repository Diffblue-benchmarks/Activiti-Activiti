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

class TaskCandidateUserRemovedImplDiffblueTest {
  /**
   * Test {@link TaskCandidateUserRemovedImpl#TaskCandidateUserRemovedImpl()}.
   * <p>
   * Method under test:
   * {@link TaskCandidateUserRemovedImpl#TaskCandidateUserRemovedImpl()}
   */
  @Test
  @DisplayName("Test new TaskCandidateUserRemovedImpl()")
  void testNewTaskCandidateUserRemovedImpl() {
    // Arrange and Act
    TaskCandidateUserRemovedImpl actualTaskCandidateUserRemovedImpl = new TaskCandidateUserRemovedImpl();

    // Assert
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateUserRemovedImpl.getBusinessKey());
    assertNull(actualTaskCandidateUserRemovedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessInstanceId());
    assertNull(actualTaskCandidateUserRemovedImpl.getEntity());
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_REMOVED,
        actualTaskCandidateUserRemovedImpl.getEventType());
  }

  /**
   * Test
   * {@link TaskCandidateUserRemovedImpl#TaskCandidateUserRemovedImpl(TaskCandidateUser)}.
   * <p>
   * Method under test:
   * {@link TaskCandidateUserRemovedImpl#TaskCandidateUserRemovedImpl(TaskCandidateUser)}
   */
  @Test
  @DisplayName("Test new TaskCandidateUserRemovedImpl(TaskCandidateUser)")
  void testNewTaskCandidateUserRemovedImpl2() {
    // Arrange
    TaskCandidateUserImpl entity = new TaskCandidateUserImpl("42", "42");

    // Act
    TaskCandidateUserRemovedImpl actualTaskCandidateUserRemovedImpl = new TaskCandidateUserRemovedImpl(entity);

    // Assert
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateUserRemovedImpl.getBusinessKey());
    assertNull(actualTaskCandidateUserRemovedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateUserRemovedImpl.getProcessInstanceId());
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_REMOVED,
        actualTaskCandidateUserRemovedImpl.getEventType());
    assertSame(entity, actualTaskCandidateUserRemovedImpl.getEntity());
  }

  /**
   * Test {@link TaskCandidateUserRemovedImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskCandidateUserRemovedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskCandidateUserEvent.TaskCandidateUserEvents.TASK_CANDIDATE_USER_REMOVED,
        (new TaskCandidateUserRemovedImpl()).getEventType());
  }
}
