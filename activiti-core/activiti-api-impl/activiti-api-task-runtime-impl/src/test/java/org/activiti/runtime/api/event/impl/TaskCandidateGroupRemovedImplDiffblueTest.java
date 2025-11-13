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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.TaskCandidateGroup;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent.TaskCandidateGroupEvents;
import org.activiti.api.task.model.impl.TaskCandidateGroupImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskCandidateGroupRemovedImplDiffblueTest {
  /**
   * Test {@link TaskCandidateGroupRemovedImpl#TaskCandidateGroupRemovedImpl()}.
   *
   * <p>Method under test: {@link TaskCandidateGroupRemovedImpl#TaskCandidateGroupRemovedImpl()}
   */
  @Test
  @DisplayName("Test new TaskCandidateGroupRemovedImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCandidateGroupRemovedImpl.<init>()"})
  void testNewTaskCandidateGroupRemovedImpl() {
    // Arrange and Act
    TaskCandidateGroupRemovedImpl actualTaskCandidateGroupRemovedImpl =
        new TaskCandidateGroupRemovedImpl();

    // Assert
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateGroupRemovedImpl.getBusinessKey());
    assertNull(actualTaskCandidateGroupRemovedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessInstanceId());
    assertNull(actualTaskCandidateGroupRemovedImpl.getEntity());
    assertEquals(
        TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_REMOVED,
        actualTaskCandidateGroupRemovedImpl.getEventType());
  }

  /**
   * Test {@link TaskCandidateGroupRemovedImpl#TaskCandidateGroupRemovedImpl(TaskCandidateGroup)}.
   *
   * <p>Method under test: {@link
   * TaskCandidateGroupRemovedImpl#TaskCandidateGroupRemovedImpl(TaskCandidateGroup)}
   */
  @Test
  @DisplayName("Test new TaskCandidateGroupRemovedImpl(TaskCandidateGroup)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCandidateGroupRemovedImpl.<init>(TaskCandidateGroup)"})
  void testNewTaskCandidateGroupRemovedImpl2() {
    // Arrange
    TaskCandidateGroupImpl entity = new TaskCandidateGroupImpl();

    // Act
    TaskCandidateGroupRemovedImpl actualTaskCandidateGroupRemovedImpl =
        new TaskCandidateGroupRemovedImpl(entity);

    // Assert
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCandidateGroupRemovedImpl.getBusinessKey());
    assertNull(actualTaskCandidateGroupRemovedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionId());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCandidateGroupRemovedImpl.getProcessInstanceId());
    assertEquals(
        TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_REMOVED,
        actualTaskCandidateGroupRemovedImpl.getEventType());
    assertSame(entity, actualTaskCandidateGroupRemovedImpl.getEntity());
  }

  /**
   * Test {@link TaskCandidateGroupRemovedImpl#getEventType()}.
   *
   * <p>Method under test: {@link TaskCandidateGroupRemovedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskCandidateGroupEvent.TaskCandidateGroupEvents TaskCandidateGroupRemovedImpl.getEventType()"
  })
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_REMOVED,
        new TaskCandidateGroupRemovedImpl().getEventType());
  }
}
