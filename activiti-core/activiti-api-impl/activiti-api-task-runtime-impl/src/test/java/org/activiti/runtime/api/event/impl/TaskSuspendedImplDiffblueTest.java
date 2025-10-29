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
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.model.impl.TaskImpl;
import org.junit.jupiter.api.Test;

class TaskSuspendedImplDiffblueTest {
  /**
   * Method under test: {@link TaskSuspendedImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_SUSPENDED, (new TaskSuspendedImpl()).getEventType());
  }

  /**
   * Method under test: {@link TaskSuspendedImpl#TaskSuspendedImpl()}
   */
  @Test
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
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_SUSPENDED, actualTaskSuspendedImpl.getEventType());
  }

  /**
   * Method under test: {@link TaskSuspendedImpl#TaskSuspendedImpl(Task)}
   */
  @Test
  void testNewTaskSuspendedImpl2() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act
    TaskSuspendedImpl actualTaskSuspendedImpl = new TaskSuspendedImpl(entity);

    // Assert
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskSuspendedImpl.getBusinessKey());
    assertNull(actualTaskSuspendedImpl.getParentProcessInstanceId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionKey());
    assertNull(actualTaskSuspendedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_SUSPENDED, actualTaskSuspendedImpl.getEventType());
    assertSame(entity, actualTaskSuspendedImpl.getEntity());
  }

  /**
   * Method under test: {@link TaskSuspendedImpl#TaskSuspendedImpl(Task)}
   */
  @Test
  void testNewTaskSuspendedImpl3() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act
    TaskSuspendedImpl actualTaskSuspendedImpl = new TaskSuspendedImpl(entity);

    // Assert
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskSuspendedImpl.getBusinessKey());
    assertNull(actualTaskSuspendedImpl.getParentProcessInstanceId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionId());
    assertNull(actualTaskSuspendedImpl.getProcessDefinitionKey());
    assertNull(actualTaskSuspendedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_SUSPENDED, actualTaskSuspendedImpl.getEventType());
    assertSame(entity, actualTaskSuspendedImpl.getEntity());
  }
}
