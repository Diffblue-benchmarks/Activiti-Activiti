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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.model.events.TaskRuntimeEvent.TaskEvents;
import org.activiti.api.task.model.impl.TaskImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskAssignedEventImplDiffblueTest {
  /**
   * Test {@link TaskAssignedEventImpl#TaskAssignedEventImpl(Task)}.
   *
   * <p>Method under test: {@link TaskAssignedEventImpl#TaskAssignedEventImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskAssignedEventImpl(Task)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAssignedEventImpl.<init>(Task)"})
  void testNewTaskAssignedEventImpl() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", TaskStatus.CREATED);

    // Act
    TaskAssignedEventImpl actualTaskAssignedEventImpl = new TaskAssignedEventImpl(entity);

    // Assert
    Task entity2 = actualTaskAssignedEventImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertNull(actualTaskAssignedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskAssignedEventImpl.getBusinessKey());
    assertNull(actualTaskAssignedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskAssignedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskAssignedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskAssignedEventImpl.getProcessInstanceId());
    assertEquals(TaskEvents.TASK_ASSIGNED, actualTaskAssignedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link TaskAssignedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link TaskAssignedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskRuntimeEvent.TaskEvents TaskAssignedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        TaskEvents.TASK_ASSIGNED,
        new TaskAssignedEventImpl(new TaskImpl("42", "Name", TaskStatus.CREATED)).getEventType());
  }
}
