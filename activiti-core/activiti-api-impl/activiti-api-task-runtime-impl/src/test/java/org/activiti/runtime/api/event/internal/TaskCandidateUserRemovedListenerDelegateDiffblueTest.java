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
import org.activiti.api.task.runtime.events.TaskCandidateUserRemovedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.TaskCandidateUserRemovedImpl;
import org.activiti.runtime.api.event.impl.ToTaskCandidateUserRemovedConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskCandidateUserRemovedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TaskCandidateUserRemovedListenerDelegate#TaskCandidateUserRemovedListenerDelegate(List, ToTaskCandidateUserRemovedConverter)}
   *   <li>{@link TaskCandidateUserRemovedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TaskCandidateUserRemovedListenerDelegate(listeners,
        new ToTaskCandidateUserRemovedConverter(new APITaskCandidateUserConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link TaskCandidateUserRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToTaskCandidateUserRemovedConverter converter = mock(ToTaskCandidateUserRemovedConverter.class);
    Optional<TaskCandidateUserRemovedEvent> ofResult = Optional.of(new TaskCandidateUserRemovedImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateUserRemovedListenerDelegate taskCandidateUserRemovedListenerDelegate = new TaskCandidateUserRemovedListenerDelegate(
        new ArrayList<>(), converter);

    // Act
    taskCandidateUserRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link TaskCandidateUserRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    TaskRuntimeEventListener<TaskCandidateUserRemovedEvent> taskRuntimeEventListener = mock(
        TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCandidateUserRemovedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(taskRuntimeEventListener);
    ToTaskCandidateUserRemovedConverter converter = mock(ToTaskCandidateUserRemovedConverter.class);
    Optional<TaskCandidateUserRemovedEvent> ofResult = Optional.of(new TaskCandidateUserRemovedImpl());
    when(converter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    TaskCandidateUserRemovedListenerDelegate taskCandidateUserRemovedListenerDelegate = new TaskCandidateUserRemovedListenerDelegate(
        listeners, converter);

    // Act
    taskCandidateUserRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener).onEvent(isA(TaskCandidateUserRemovedEvent.class));
    verify(converter).from(isA(ActivitiEntityEvent.class));
  }
}
