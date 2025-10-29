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
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.ProcessCreatedEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCreatedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCreatedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCreatedListenerDelegate#ProcessCreatedListenerDelegate(List, ToAPIProcessCreatedEventConverter)}
   *   <li>{@link ProcessCreatedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCreatedListenerDelegate(listeners,
        new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link ProcessCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToAPIProcessCreatedEventConverter entityCreatedEventConverter = mock(ToAPIProcessCreatedEventConverter.class);
    Optional<ProcessCreatedEvent> ofResult = Optional.of(new ProcessCreatedEventImpl(new ProcessInstanceImpl()));
    when(entityCreatedEventConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);

    // Act
    (new ProcessCreatedListenerDelegate(new ArrayList<>(), entityCreatedEventConverter))
        .onEvent(mock(ActivitiEntityEventImpl.class));

    // Assert that nothing has changed
    verify(entityCreatedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Method under test:
   * {@link ProcessCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCreatedEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCreatedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ToAPIProcessCreatedEventConverter entityCreatedEventConverter = mock(ToAPIProcessCreatedEventConverter.class);
    Optional<ProcessCreatedEvent> ofResult = Optional.of(new ProcessCreatedEventImpl(new ProcessInstanceImpl()));
    when(entityCreatedEventConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);

    // Act
    (new ProcessCreatedListenerDelegate(listeners, entityCreatedEventConverter))
        .onEvent(mock(ActivitiEntityEventImpl.class));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCreatedEvent.class));
    verify(entityCreatedEventConverter).from(isA(ActivitiEntityEvent.class));
  }
}
