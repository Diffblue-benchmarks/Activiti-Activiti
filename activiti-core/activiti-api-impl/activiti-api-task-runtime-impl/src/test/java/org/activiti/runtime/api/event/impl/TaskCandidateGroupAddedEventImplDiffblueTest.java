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
import org.activiti.api.task.model.TaskCandidateGroup;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent;
import org.activiti.api.task.model.impl.TaskCandidateGroupImpl;
import org.junit.jupiter.api.Test;

class TaskCandidateGroupAddedEventImplDiffblueTest {
  /**
   * Method under test: {@link TaskCandidateGroupAddedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskCandidateGroupEvent.TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_ADDED,
        (new TaskCandidateGroupAddedEventImpl()).getEventType());
  }

  /**
   * Method under test:
   * {@link TaskCandidateGroupAddedEventImpl#TaskCandidateGroupAddedEventImpl()}
   */
  @Test
  void testNewTaskCandidateGroupAddedEventImpl() {
    // Arrange and Act
    TaskCandidateGroupAddedEventImpl actualTaskCandidateGroupAddedEventImpl = new TaskCandidateGroupAddedEventImpl();

    // Assert
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getBusinessKey());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessInstanceId());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getEntity());
    assertEquals(TaskCandidateGroupEvent.TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_ADDED,
        actualTaskCandidateGroupAddedEventImpl.getEventType());
  }

  /**
   * Method under test:
   * {@link TaskCandidateGroupAddedEventImpl#TaskCandidateGroupAddedEventImpl(TaskCandidateGroup)}
   */
  @Test
  void testNewTaskCandidateGroupAddedEventImpl2() {
    // Arrange
    TaskCandidateGroupImpl entity = new TaskCandidateGroupImpl("42", "42");

    // Act
    TaskCandidateGroupAddedEventImpl actualTaskCandidateGroupAddedEventImpl = new TaskCandidateGroupAddedEventImpl(
        entity);

    // Assert
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getBusinessKey());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateGroupAddedEventImpl.getProcessInstanceId());
    assertEquals(TaskCandidateGroupEvent.TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_ADDED,
        actualTaskCandidateGroupAddedEventImpl.getEventType());
    assertSame(entity, actualTaskCandidateGroupAddedEventImpl.getEntity());
  }
}
