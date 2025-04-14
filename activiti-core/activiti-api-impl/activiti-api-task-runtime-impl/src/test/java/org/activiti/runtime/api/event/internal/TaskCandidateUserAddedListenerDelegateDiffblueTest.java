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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.runtime.events.TaskCandidateUserAddedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateUserAddedEventConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskCandidateUserAddedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateUserAddedListenerDelegate#TaskCandidateUserAddedListenerDelegate(List, ToAPITaskCandidateUserAddedEventConverter)}
   *   <li>{@link TaskCandidateUserAddedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void TaskCandidateUserAddedListenerDelegate.<init>(List, ToAPITaskCandidateUserAddedEventConverter)",
      "boolean TaskCandidateUserAddedListenerDelegate.isFailOnException()"})
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TaskCandidateUserAddedListenerDelegate(listeners,
        new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))).isFailOnException());
  }

  /**
   * Test {@link TaskCandidateUserAddedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener} {@link TaskRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls {@link TaskRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCandidateUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given TaskRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskCandidateUserAddedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenTaskRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    TaskRuntimeEventListener<TaskCandidateUserAddedEvent> taskRuntimeEventListener = mock(
        TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCandidateUserAddedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(taskRuntimeEventListener);
    TaskCandidateUserAddedListenerDelegate taskCandidateUserAddedListenerDelegate = new TaskCandidateUserAddedListenerDelegate(
        listeners, new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()));

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setUserId("42");

    // Act
    taskCandidateUserAddedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener).onEvent(isA(TaskCandidateUserAddedEvent.class));
  }
}
