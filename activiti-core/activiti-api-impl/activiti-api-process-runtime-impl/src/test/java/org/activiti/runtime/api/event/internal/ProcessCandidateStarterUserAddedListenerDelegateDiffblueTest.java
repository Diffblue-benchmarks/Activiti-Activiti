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
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterUserAddedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterUserAddedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCandidateStarterUserAddedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterUserAddedListenerDelegate#ProcessCandidateStarterUserAddedListenerDelegate(List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   *   <li>
   * {@link ProcessCandidateStarterUserAddedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCandidateStarterUserAddedListenerDelegate(listeners,
        new ToAPIProcessCandidateStarterUserAddedEventConverter(new APIProcessCandidateStarterUserConverter())))
        .isFailOnException());
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToAPIProcessCandidateStarterUserAddedEventConverter processCandidateStarterUserAddedEventConverter = mock(
        ToAPIProcessCandidateStarterUserAddedEventConverter.class);
    Optional<ProcessCandidateStarterUserAddedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterUserAddedEventImpl());
    when(processCandidateStarterUserAddedEventConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    ProcessCandidateStarterUserAddedListenerDelegate processCandidateStarterUserAddedListenerDelegate = new ProcessCandidateStarterUserAddedListenerDelegate(
        new ArrayList<>(), processCandidateStarterUserAddedEventConverter);

    // Act
    processCandidateStarterUserAddedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(processCandidateStarterUserAddedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCandidateStarterUserAddedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ToAPIProcessCandidateStarterUserAddedEventConverter processCandidateStarterUserAddedEventConverter = mock(
        ToAPIProcessCandidateStarterUserAddedEventConverter.class);
    Optional<ProcessCandidateStarterUserAddedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterUserAddedEventImpl());
    when(processCandidateStarterUserAddedEventConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    ProcessCandidateStarterUserAddedListenerDelegate processCandidateStarterUserAddedListenerDelegate = new ProcessCandidateStarterUserAddedListenerDelegate(
        listeners, processCandidateStarterUserAddedEventConverter);

    // Act
    processCandidateStarterUserAddedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCandidateStarterUserAddedEvent.class));
    verify(processCandidateStarterUserAddedEventConverter).from(isA(ActivitiEntityEvent.class));
  }
}
