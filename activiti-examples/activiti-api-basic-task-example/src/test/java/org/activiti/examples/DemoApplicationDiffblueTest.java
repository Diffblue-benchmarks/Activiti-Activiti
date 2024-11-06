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
package org.activiti.examples;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.runtime.api.event.impl.TaskAssignedEventImpl;
import org.activiti.runtime.api.event.impl.TaskCompletedImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#taskAssignedListener()}.
   * <ul>
   *   <li>Then calls {@link TaskImpl#getAssignee()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#taskAssignedListener()}
   */
  @Test
  @DisplayName("Test taskAssignedListener(); then calls getAssignee()")
  void testTaskAssignedListener_thenCallsGetAssignee() {
    // Arrange and Act
    TaskRuntimeEventListener<TaskAssignedEvent> actualTaskAssignedListenerResult = (new DemoApplication())
        .taskAssignedListener();
    TaskImpl entity = mock(TaskImpl.class);
    when(entity.getAssignee()).thenReturn("Assignee");
    when(entity.getName()).thenReturn("Name");
    actualTaskAssignedListenerResult.onEvent(new TaskAssignedEventImpl(entity));

    // Assert that nothing has changed
    verify(entity).getAssignee();
    verify(entity).getName();
  }

  /**
   * Test {@link DemoApplication#taskCompletedListener()}.
   * <ul>
   *   <li>Then calls {@link TaskImpl#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#taskCompletedListener()}
   */
  @Test
  @DisplayName("Test taskCompletedListener(); then calls getName()")
  void testTaskCompletedListener_thenCallsGetName() {
    // Arrange and Act
    TaskRuntimeEventListener<TaskCompletedEvent> actualTaskCompletedListenerResult = (new DemoApplication())
        .taskCompletedListener();
    TaskImpl entity = mock(TaskImpl.class);
    when(entity.getName()).thenReturn("Name");
    when(entity.getOwner()).thenReturn("Owner");
    actualTaskCompletedListenerResult.onEvent(new TaskCompletedImpl(entity));

    // Assert that nothing has changed
    verify(entity).getName();
    verify(entity).getOwner();
  }
}
