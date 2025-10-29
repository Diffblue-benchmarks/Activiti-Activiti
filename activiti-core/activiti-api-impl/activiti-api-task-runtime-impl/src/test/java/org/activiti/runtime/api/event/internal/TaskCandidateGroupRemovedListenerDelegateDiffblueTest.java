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
package org.activiti.runtime.api.event.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.activiti.api.task.runtime.events.TaskCandidateGroupRemovedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.TaskCandidateGroupRemovedImpl;
import org.activiti.runtime.api.event.impl.ToTaskCandidateGroupRemovedConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskCandidateGroupRemovedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TaskCandidateGroupRemovedListenerDelegate#TaskCandidateGroupRemovedListenerDelegate(List, ToTaskCandidateGroupRemovedConverter)}
   *   <li>{@link TaskCandidateGroupRemovedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TaskCandidateGroupRemovedListenerDelegate(listeners,
        new ToTaskCandidateGroupRemovedConverter(new APITaskCandidateGroupConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link TaskCandidateGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter converter = mock(ToTaskCandidateGroupRemovedConverter.class);
    Optional<TaskCandidateGroupRemovedEvent> ofResult = Optional.of(new TaskCandidateGroupRemovedImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateGroupRemovedListenerDelegate taskCandidateGroupRemovedListenerDelegate = new TaskCandidateGroupRemovedListenerDelegate(
        new ArrayList<>(), converter);

    // Act
    taskCandidateGroupRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link TaskCandidateGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent> taskRuntimeEventListener = mock(
        TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCandidateGroupRemovedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(taskRuntimeEventListener);
    ToTaskCandidateGroupRemovedConverter converter = mock(ToTaskCandidateGroupRemovedConverter.class);
    Optional<TaskCandidateGroupRemovedEvent> ofResult = Optional.of(new TaskCandidateGroupRemovedImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateGroupRemovedListenerDelegate taskCandidateGroupRemovedListenerDelegate = new TaskCandidateGroupRemovedListenerDelegate(
        listeners, converter);

    // Act
    taskCandidateGroupRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener).onEvent(isA(TaskCandidateGroupRemovedEvent.class));
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }
}
