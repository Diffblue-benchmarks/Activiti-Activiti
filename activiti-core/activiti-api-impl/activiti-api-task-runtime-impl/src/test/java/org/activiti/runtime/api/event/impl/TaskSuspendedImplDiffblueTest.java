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
import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.model.events.TaskRuntimeEvent.TaskEvents;
import org.activiti.api.task.model.impl.TaskImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskSuspendedImplDiffblueTest {
  /**
   * Test {@link TaskSuspendedImpl#TaskSuspendedImpl()}.
   *
   * <p>Method under test: {@link TaskSuspendedImpl#TaskSuspendedImpl()}
   */
  @Test
  @DisplayName("Test new TaskSuspendedImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskSuspendedImpl.<init>()"})
  void testNewTaskSuspendedImpl() {
    // Arrange and Act
    TaskSuspendedImpl actualTaskSuspendedImpl = new TaskSuspendedImpl();

    // Assert
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskSuspendedImpl.getBusinessKey());
    assertNull(actualTaskSuspendedImpl.getParentProcessInstanceId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionKey());
    assertNull(actualTaskSuspendedImpl.getProcessInstanceId());
    assertNull(actualTaskSuspendedImpl.getEntity());
    assertEquals(TaskEvents.TASK_SUSPENDED, actualTaskSuspendedImpl.getEventType());
  }

  /**
   * Test {@link TaskSuspendedImpl#TaskSuspendedImpl(Task)}.
   *
   * <p>Method under test: {@link TaskSuspendedImpl#TaskSuspendedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskSuspendedImpl(Task)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskSuspendedImpl.<init>(Task)"})
  void testNewTaskSuspendedImpl2() {
    // Arrange
    TaskImpl entity = new TaskImpl();

    // Act
    TaskSuspendedImpl actualTaskSuspendedImpl = new TaskSuspendedImpl(entity);

    // Assert
    Task entity2 = actualTaskSuspendedImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskSuspendedImpl.getBusinessKey());
    assertNull(actualTaskSuspendedImpl.getParentProcessInstanceId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionKey());
    assertNull(actualTaskSuspendedImpl.getProcessInstanceId());
    assertEquals(TaskEvents.TASK_SUSPENDED, actualTaskSuspendedImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link TaskSuspendedImpl#getEventType()}.
   *
   * <p>Method under test: {@link TaskSuspendedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskRuntimeEvent.TaskEvents TaskSuspendedImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskEvents.TASK_SUSPENDED, new TaskSuspendedImpl().getEventType());
  }
}
