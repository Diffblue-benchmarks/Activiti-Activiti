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

class TaskActivatedImplDiffblueTest {
  /**
   * Test {@link TaskActivatedImpl#TaskActivatedImpl()}.
   *
   * <p>Method under test: {@link TaskActivatedImpl#TaskActivatedImpl()}
   */
  @Test
  @DisplayName("Test new TaskActivatedImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskActivatedImpl.<init>()"})
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
    assertEquals(TaskEvents.TASK_ACTIVATED, actualTaskActivatedImpl.getEventType());
  }

  /**
   * Test {@link TaskActivatedImpl#TaskActivatedImpl(Task)}.
   *
   * <p>Method under test: {@link TaskActivatedImpl#TaskActivatedImpl(Task)}
   */
  @Test
  @DisplayName("Test new TaskActivatedImpl(Task)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskActivatedImpl.<init>(Task)"})
  void testNewTaskActivatedImpl2() {
    // Arrange
    TaskImpl entity = new TaskImpl();

    // Act
    TaskActivatedImpl actualTaskActivatedImpl = new TaskActivatedImpl(entity);

    // Assert
    Task entity2 = actualTaskActivatedImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertNull(actualTaskActivatedImpl.getProcessDefinitionVersion());
    assertNull(actualTaskActivatedImpl.getBusinessKey());
    assertNull(actualTaskActivatedImpl.getParentProcessInstanceId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionId());
    assertNull(actualTaskActivatedImpl.getProcessDefinitionKey());
    assertNull(actualTaskActivatedImpl.getProcessInstanceId());
    assertEquals(TaskEvents.TASK_ACTIVATED, actualTaskActivatedImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link TaskActivatedImpl#getEventType()}.
   *
   * <p>Method under test: {@link TaskActivatedImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskRuntimeEvent.TaskEvents TaskActivatedImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TaskEvents.TASK_ACTIVATED, new TaskActivatedImpl().getEventType());
  }
}
