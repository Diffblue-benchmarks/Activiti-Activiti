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
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.ProcessCompletedImpl;
import org.activiti.runtime.api.event.impl.ToProcessCompletedConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCompletedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCompletedListenerDelegate#ProcessCompletedListenerDelegate(List, ToProcessCompletedConverter)}
   *   <li>{@link ProcessCompletedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCompletedListenerDelegate(processRuntimeEventListeners,
        new ToProcessCompletedConverter(new APIProcessInstanceConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToProcessCompletedConverter processCompletedConverter = mock(ToProcessCompletedConverter.class);
    Optional<ProcessCompletedEvent> ofResult = Optional.of(new ProcessCompletedImpl(new ProcessInstanceImpl()));
    when(processCompletedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate = new ProcessCompletedListenerDelegate(
        new ArrayList<>(), processCompletedConverter);

    // Act
    processCompletedListenerDelegate.onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(processCompletedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);
    ToProcessCompletedConverter processCompletedConverter = mock(ToProcessCompletedConverter.class);
    Optional<ProcessCompletedEvent> ofResult = Optional.of(new ProcessCompletedImpl(new ProcessInstanceImpl()));
    when(processCompletedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate = new ProcessCompletedListenerDelegate(
        processRuntimeEventListeners, processCompletedConverter);

    // Act
    processCompletedListenerDelegate.onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(processCompletedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent3() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    ToProcessCompletedConverter processCompletedConverter = mock(ToProcessCompletedConverter.class);
    Optional<ProcessCompletedEvent> emptyResult = Optional.empty();
    when(processCompletedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(emptyResult);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate = new ProcessCompletedListenerDelegate(
        processRuntimeEventListeners, processCompletedConverter);

    // Act
    processCompletedListenerDelegate.onEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(processCompletedConverter).from(isA(ActivitiEntityEvent.class));
  }
}
