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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskCompletedImplDiffblueTest {
  /**
   * Test {@link TaskCompletedImpl#TaskCompletedImpl(Task)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCompletedImpl#TaskCompletedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskCompletedImpl(Task); given Date")
  void testNewTaskCompletedImpl_givenDate() {
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

  /**
   * Test {@link TaskCompletedImpl#TaskCompletedImpl(Task)}.
   * <ul>
   *   <li>When {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCompletedImpl#TaskCompletedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskCompletedImpl(Task); when TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testNewTaskCompletedImpl_whenTaskImplWithIdIs42AndNameAndStatusIsCreated() {
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
   * Test {@link TaskCompletedImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskCompletedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED, (new TaskCompletedImpl(entity)).getEventType());
  }

  /**
   * Test {@link TaskCompletedImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCompletedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testGetEventType_givenTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_COMPLETED,
        (new TaskCompletedImpl(new TaskImpl("42", "Name", Task.TaskStatus.CREATED))).getEventType());
  }
}
