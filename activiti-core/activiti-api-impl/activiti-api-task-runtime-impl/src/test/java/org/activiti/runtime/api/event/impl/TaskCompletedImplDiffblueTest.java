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

class TaskCompletedImplDiffblueTest {
  /**
   * Method under test: {@link TaskCompletedImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED,
        (new TaskCompletedImpl(new TaskImpl("42", "Name", Task.TaskStatus.CREATED))).getEventType());
  }

  /**
   * Method under test: {@link TaskCompletedImpl#getEventType()}
   */
  @Test
  void testGetEventType2() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED, (new TaskCompletedImpl(entity)).getEventType());
  }

  /**
   * Method under test: {@link TaskCompletedImpl#TaskCompletedImpl(Task)}
   */
  @Test
  void testNewTaskCompletedImpl() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act
    TaskCompletedImpl actualTaskCompletedImpl = new TaskCompletedImpl(entity);

    // Assert
    assertNull(actualTaskCompletedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCompletedImpl.getBusinessKey());
    assertNull(actualTaskCompletedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCompletedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED, actualTaskCompletedImpl.getEventType());
    assertSame(entity, actualTaskCompletedImpl.getEntity());
  }

  /**
   * Method under test: {@link TaskCompletedImpl#TaskCompletedImpl(Task)}
   */
  @Test
  void testNewTaskCompletedImpl2() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act
    TaskCompletedImpl actualTaskCompletedImpl = new TaskCompletedImpl(entity);

    // Assert
    assertNull(actualTaskCompletedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCompletedImpl.getBusinessKey());
    assertNull(actualTaskCompletedImpl.getParentProcessInstanceId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionId());
    assertNull(actualTaskCompletedImpl.getProcessDefinitionKey());
    assertNull(actualTaskCompletedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED, actualTaskCompletedImpl.getEventType());
    assertSame(entity, actualTaskCompletedImpl.getEntity());
  }
}
