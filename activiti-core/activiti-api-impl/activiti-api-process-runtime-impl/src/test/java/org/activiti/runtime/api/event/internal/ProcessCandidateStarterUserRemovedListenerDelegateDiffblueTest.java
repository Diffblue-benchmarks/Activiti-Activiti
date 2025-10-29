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
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserRemovedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterUserRemovedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterUserRemovedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCandidateStarterUserRemovedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterUserRemovedListenerDelegate#ProcessCandidateStarterUserRemovedListenerDelegate(List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   *   <li>
   * {@link ProcessCandidateStarterUserRemovedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCandidateStarterUserRemovedListenerDelegate(listeners,
        new ToAPIProcessCandidateStarterUserRemovedEventConverter(new APIProcessCandidateStarterUserConverter())))
        .isFailOnException());
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterUserRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToAPIProcessCandidateStarterUserRemovedEventConverter processCandidateStarterUserRemovedEventConverter = mock(
        ToAPIProcessCandidateStarterUserRemovedEventConverter.class);
    Optional<ProcessCandidateStarterUserRemovedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterUserRemovedEventImpl());
    when(processCandidateStarterUserRemovedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);
    ProcessCandidateStarterUserRemovedListenerDelegate processCandidateStarterUserRemovedListenerDelegate = new ProcessCandidateStarterUserRemovedListenerDelegate(
        new ArrayList<>(), processCandidateStarterUserRemovedEventConverter);

    // Act
    processCandidateStarterUserRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(processCandidateStarterUserRemovedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterUserRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCandidateStarterUserRemovedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ToAPIProcessCandidateStarterUserRemovedEventConverter processCandidateStarterUserRemovedEventConverter = mock(
        ToAPIProcessCandidateStarterUserRemovedEventConverter.class);
    Optional<ProcessCandidateStarterUserRemovedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterUserRemovedEventImpl());
    when(processCandidateStarterUserRemovedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);
    ProcessCandidateStarterUserRemovedListenerDelegate processCandidateStarterUserRemovedListenerDelegate = new ProcessCandidateStarterUserRemovedListenerDelegate(
        listeners, processCandidateStarterUserRemovedEventConverter);

    // Act
    processCandidateStarterUserRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCandidateStarterUserRemovedEvent.class));
    verify(processCandidateStarterUserRemovedEventConverter).from(isA(ActivitiEntityEvent.class));
  }
}
