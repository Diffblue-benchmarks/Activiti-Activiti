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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.runtime.api.event.impl.ProcessStartedEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessStartedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessStartedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessStartedListenerDelegate#ProcessStartedListenerDelegate(List,
   *       ToAPIProcessStartedEventConverter)}
   *   <li>{@link ProcessStartedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessStartedListenerDelegate.<init>(List, ToAPIProcessStartedEventConverter)",
    "boolean ProcessStartedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();

    // Act
    ProcessStartedListenerDelegate actualProcessStartedListenerDelegate =
        new ProcessStartedListenerDelegate(
            listeners, new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()));

    // Assert
    assertFalse(actualProcessStartedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessStartedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener} {@link
   *       ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessStartedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessStartedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessStartedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessStartedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);

    ToAPIProcessStartedEventConverter processInstanceStartedEventConverter =
        mock(ToAPIProcessStartedEventConverter.class);
    Optional<ProcessStartedEvent> ofResult =
        Optional.of(new ProcessStartedEventImpl(new ProcessInstanceImpl()));
    when(processInstanceStartedEventConverter.from(Mockito.<ActivitiProcessStartedEvent>any()))
        .thenReturn(ofResult);

    ProcessStartedListenerDelegate processStartedListenerDelegate =
        new ProcessStartedListenerDelegate(listeners, processInstanceStartedEventConverter);
    ActivitiProcessStartedEventImpl event =
        new ActivitiProcessStartedEventImpl(1, new HashMap<>(), true);

    // Act
    processStartedListenerDelegate.onEvent(event);

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessStartedEvent.class));
    verify(processInstanceStartedEventConverter).from(isA(ActivitiProcessStartedEvent.class));
  }

  /**
   * Test {@link ProcessStartedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessStartedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiProcessStartedEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessStartedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ToAPIProcessStartedEventConverter processInstanceStartedEventConverter =
        mock(ToAPIProcessStartedEventConverter.class);
    Optional<ProcessStartedEvent> ofResult =
        Optional.of(new ProcessStartedEventImpl(new ProcessInstanceImpl()));
    when(processInstanceStartedEventConverter.from(Mockito.<ActivitiProcessStartedEvent>any()))
        .thenReturn(ofResult);
    ProcessStartedListenerDelegate processStartedListenerDelegate =
        new ProcessStartedListenerDelegate(new ArrayList<>(), processInstanceStartedEventConverter);
    ActivitiProcessStartedEventImpl event =
        new ActivitiProcessStartedEventImpl(1, new HashMap<>(), true);

    // Act
    processStartedListenerDelegate.onEvent(event);

    // Assert
    verify(processInstanceStartedEventConverter).from(isA(ActivitiProcessStartedEvent.class));
  }
}
