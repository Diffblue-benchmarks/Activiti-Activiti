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

class TaskActivatedImplDiffblueTest {
  /**
   * Test {@link TaskActivatedImpl#TaskActivatedImpl()}.
   * <p>
   * Method under test: {@link TaskActivatedImpl#TaskActivatedImpl()}
   */
  @Test
  @DisplayName("Test new TaskActivatedImpl()")
  void testNewTaskActivatedImpl() {
    // Arrange and Act
    TaskActivatedImpl actualTaskActivatedImpl = new TaskActivatedImpl();

    // Assert
    assertNull(actualTaskActivatedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskActivatedImpl.getBusinessKey());
    assertNull(actualTaskActivatedImpl.getParentProcessInstanceId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionKey());
    assertNull(actualTaskActivatedImpl.getProcessInstanceId());
    assertNull(actualTaskActivatedImpl.getEntity());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_ACTIVATED, actualTaskActivatedImpl.getEventType());
  }

  /**
   * Test {@link TaskActivatedImpl#TaskActivatedImpl(Task)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskActivatedImpl#TaskActivatedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskActivatedImpl(Task); given Date")
  void testNewTaskActivatedImpl_givenDate() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    entity.setCreatedDate(mock(Date.class));

    // Act
    TaskActivatedImpl actualTaskActivatedImpl = new TaskActivatedImpl(entity);

    // Assert
    assertNull(actualTaskActivatedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskActivatedImpl.getBusinessKey());
    assertNull(actualTaskActivatedImpl.getParentProcessInstanceId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionKey());
    assertNull(actualTaskActivatedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_ACTIVATED, actualTaskActivatedImpl.getEventType());
    assertSame(entity, actualTaskActivatedImpl.getEntity());
  }

  /**
   * Test {@link TaskActivatedImpl#TaskActivatedImpl(Task)}.
   * <ul>
   *   <li>When {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskActivatedImpl#TaskActivatedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskActivatedImpl(Task); when TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testNewTaskActivatedImpl_whenTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl entity = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act
    TaskActivatedImpl actualTaskActivatedImpl = new TaskActivatedImpl(entity);

    // Assert
    assertNull(actualTaskActivatedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskActivatedImpl.getBusinessKey());
    assertNull(actualTaskActivatedImpl.getParentProcessInstanceId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionKey());
    assertNull(actualTaskActivatedImpl.getProcessInstanceId());
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_ACTIVATED, actualTaskActivatedImpl.getEventType());
    assertSame(entity, actualTaskActivatedImpl.getEntity());
  }

  /**
   * Test {@link TaskActivatedImpl#getEventType()}.
   * <p>
   * Method under test: {@link TaskActivatedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskRuntimeEvent.TaskEvents.TASK_ACTIVATED, (new TaskActivatedImpl()).getEventType());
  }
}
