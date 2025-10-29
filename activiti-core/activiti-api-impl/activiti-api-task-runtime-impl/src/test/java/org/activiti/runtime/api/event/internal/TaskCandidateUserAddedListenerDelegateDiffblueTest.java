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
import org.activiti.api.task.runtime.events.TaskCandidateUserAddedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.TaskCandidateUserAddedEventImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateUserAddedEventConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskCandidateUserAddedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TaskCandidateUserAddedListenerDelegate#TaskCandidateUserAddedListenerDelegate(List, ToAPITaskCandidateUserAddedEventConverter)}
   *   <li>{@link TaskCandidateUserAddedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TaskCandidateUserAddedListenerDelegate(listeners,
        new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link TaskCandidateUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToAPITaskCandidateUserAddedEventConverter converter = mock(ToAPITaskCandidateUserAddedEventConverter.class);
    Optional<TaskCandidateUserAddedEvent> ofResult = Optional.of(new TaskCandidateUserAddedEventImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateUserAddedListenerDelegate taskCandidateUserAddedListenerDelegate = new TaskCandidateUserAddedListenerDelegate(
        new ArrayList<>(), converter);

    // Act
    taskCandidateUserAddedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link TaskCandidateUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    TaskRuntimeEventListener<TaskCandidateUserAddedEvent> taskRuntimeEventListener = mock(
        TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCandidateUserAddedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(taskRuntimeEventListener);
    ToAPITaskCandidateUserAddedEventConverter converter = mock(ToAPITaskCandidateUserAddedEventConverter.class);
    Optional<TaskCandidateUserAddedEvent> ofResult = Optional.of(new TaskCandidateUserAddedEventImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateUserAddedListenerDelegate taskCandidateUserAddedListenerDelegate = new TaskCandidateUserAddedListenerDelegate(
        listeners, converter);

    // Act
    taskCandidateUserAddedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener).onEvent(isA(TaskCandidateUserAddedEvent.class));
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }
}
