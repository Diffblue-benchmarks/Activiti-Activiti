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

class TaskCreatedEventImplDiffblueTest {
  /**
   * Method under test: {@link TaskCreatedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CREATED,
        (new TaskCreatedEventImpl(new TaskImpl("42", "Name", Task.TaskStatus.CREATED))).getEventType());
  }

  /**
   * Method under test: {@link TaskCreatedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType2() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CREATED, (new TaskCreatedEventImpl(entity)).getEventType());
  }

  /**
   * Method under test: {@link TaskCreatedEventImpl#TaskCreatedEventImpl(Task)}
   */
  @Test
  void testNewTaskCreatedEventImpl() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act
    TaskCreatedEventImpl actualTaskCreatedEventImpl = new TaskCreatedEventImpl(entity);

    // Assert
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCreatedEventImpl.getBusinessKey());
    assertNull(actualTaskCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCreatedEventImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CREATED, actualTaskCreatedEventImpl.getEventType());
    assertSame(entity, actualTaskCreatedEventImpl.getEntity());
  }

  /**
   * Method under test: {@link TaskCreatedEventImpl#TaskCreatedEventImpl(Task)}
   */
  @Test
  void testNewTaskCreatedEventImpl2() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act
    TaskCreatedEventImpl actualTaskCreatedEventImpl = new TaskCreatedEventImpl(entity);

    // Assert
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCreatedEventImpl.getBusinessKey());
    assertNull(actualTaskCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualTaskCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualTaskCreatedEventImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CREATED, actualTaskCreatedEventImpl.getEventType());
    assertSame(entity, actualTaskCreatedEventImpl.getEntity());
  }
}
