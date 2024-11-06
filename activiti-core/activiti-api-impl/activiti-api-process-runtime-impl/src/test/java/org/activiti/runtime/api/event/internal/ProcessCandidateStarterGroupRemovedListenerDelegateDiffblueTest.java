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
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupRemovedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterGroupRemovedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterGroupRemovedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCandidateStarterGroupRemovedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#ProcessCandidateStarterGroupRemovedListenerDelegate(List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   *   <li>
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCandidateStarterGroupRemovedListenerDelegate(listeners,
        new ToAPIProcessCandidateStarterGroupRemovedEventConverter(new APIProcessCandidateStarterGroupConverter())))
        .isFailOnException());
  }

  /**
   * Test
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCandidateStarterGroupRemovedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ToAPIProcessCandidateStarterGroupRemovedEventConverter processCandidateStarterGroupRemovedEventConverter = mock(
        ToAPIProcessCandidateStarterGroupRemovedEventConverter.class);
    Optional<ProcessCandidateStarterGroupRemovedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterGroupRemovedEventImpl());
    when(processCandidateStarterGroupRemovedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);
    ProcessCandidateStarterGroupRemovedListenerDelegate processCandidateStarterGroupRemovedListenerDelegate = new ProcessCandidateStarterGroupRemovedListenerDelegate(
        listeners, processCandidateStarterGroupRemovedEventConverter);

    // Act
    processCandidateStarterGroupRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCandidateStarterGroupRemovedEvent.class));
    verify(processCandidateStarterGroupRemovedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then calls
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#from(ActivitiEntityEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupRemovedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEntityEvent)")
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ToAPIProcessCandidateStarterGroupRemovedEventConverter processCandidateStarterGroupRemovedEventConverter = mock(
        ToAPIProcessCandidateStarterGroupRemovedEventConverter.class);
    Optional<ProcessCandidateStarterGroupRemovedEvent> ofResult = Optional
        .of(new ProcessCandidateStarterGroupRemovedEventImpl());
    when(processCandidateStarterGroupRemovedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);
    ProcessCandidateStarterGroupRemovedListenerDelegate processCandidateStarterGroupRemovedListenerDelegate = new ProcessCandidateStarterGroupRemovedListenerDelegate(
        new ArrayList<>(), processCandidateStarterGroupRemovedEventConverter);

    // Act
    processCandidateStarterGroupRemovedListenerDelegate
        .onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(processCandidateStarterGroupRemovedEventConverter).from(isA(ActivitiEntityEvent.class));
  }
}
