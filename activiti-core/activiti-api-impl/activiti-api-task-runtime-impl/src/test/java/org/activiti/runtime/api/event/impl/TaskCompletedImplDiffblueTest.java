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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.model.events.TaskRuntimeEvent.TaskEvents;
import org.activiti.api.task.model.impl.TaskImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskCompletedImplDiffblueTest {
  /**
   * Test {@link TaskCompletedImpl#TaskCompletedImpl(Task)}.
   * <p>
   * Method under test: {@link TaskCompletedImpl#TaskCompletedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskCompletedImpl(Task)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskCompletedImpl.<init>(Task)"})
  void testNewTaskCompletedImpl() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", TaskStatus.CREATED);

    // Act
    TaskCompletedImpl actualTaskCompletedImpl = new TaskCompletedImpl(entity);

    // Assert
    Task entity2 = actualTaskCompletedImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertNull(actualTaskCompletedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCompletedImpl.getBusinessKey());
    assertNull(actualTaskCompletedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCompletedImpl.getProcessInstanceId());
    assertEquals(TaskEvents.TASK_COMPLETED, actualTaskCompletedImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link TaskCompletedImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskCompletedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TaskRuntimeEvent.TaskEvents TaskCompletedImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskEvents.TASK_COMPLETED,
        (new TaskCompletedImpl(new TaskImpl("42", "Name", TaskStatus.CREATED))).getEventType());
  }
}
