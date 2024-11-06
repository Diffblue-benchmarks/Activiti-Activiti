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

class TaskCancelledImplDiffblueTest {
  /**
   * Test {@link TaskCancelledImpl#TaskCancelledImpl(Task, String)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCancelledImpl#TaskCancelledImpl(Task, String)}
   */
  @Test
  @DisplayName("Test new TaskCancelledImpl(Task, String); given Date")
  void testNewTaskCancelledImpl_givenDate() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act
    TaskCancelledImpl actualTaskCancelledImpl = new TaskCancelledImpl(entity, "Just cause");

    // Assert
    assertEquals("Just cause", actualTaskCancelledImpl.getReason());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCancelledImpl.getBusinessKey());
    assertNull(actualTaskCancelledImpl.getParentProcessInstanceId());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionId());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionKey());
    assertNull(actualTaskCancelledImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CANCELLED, actualTaskCancelledImpl.getEventType());
    assertSame(entity, actualTaskCancelledImpl.getEntity());
  }

  /**
   * Test {@link TaskCancelledImpl#TaskCancelledImpl(Task, String)}.
   * <ul>
   *   <li>When {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCancelledImpl#TaskCancelledImpl(Task, String)}
   */
  @Test
  @DisplayName("Test new TaskCancelledImpl(Task, String); when TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testNewTaskCancelledImpl_whenTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act
    TaskCancelledImpl actualTaskCancelledImpl = new TaskCancelledImpl(entity, "Just cause");

    // Assert
    assertEquals("Just cause", actualTaskCancelledImpl.getReason());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionVersion());
    assertNull(actualTaskCancelledImpl.getBusinessKey());
    assertNull(actualTaskCancelledImpl.getParentProcessInstanceId());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionId());
    assertNull(actualTaskCancelledImpl.getProcessDefinitionKey());
    assertNull(actualTaskCancelledImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CANCELLED, actualTaskCancelledImpl.getEventType());
    assertSame(entity, actualTaskCancelledImpl.getEntity());
  }

  /**
   * Test {@link TaskCancelledImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskCancelledImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CANCELLED,
        (new TaskCancelledImpl(entity, "Just cause")).getEventType());
  }

  /**
   * Test {@link TaskCancelledImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCancelledImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testGetEventType_givenTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_CANCELLED,
        (new TaskCancelledImpl(new TaskImpl("42", "Name", Task.TaskStatus.CREATED), "Just cause")).getEventType());
  }

  /**
   * Test {@link TaskCancelledImpl#getReason()}.
   * <p>
   * Method under test: {@link TaskCancelledImpl#getReason()}
   */
  @Test
  @DisplayName("Test getReason()")
  void testGetReason() {
    // Arrange, Act and Assert
    assertEquals("Just cause",
        (new TaskCancelledImpl(new TaskImpl("42", "Name", Task.TaskStatus.CREATED), "Just cause")).getReason());
  }
}
