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
import org.activiti.api.process.model.events.BPMNTimerFailedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.BPMNTimerFailedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerFailedConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerFailedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TimerFailedListenerDelegate#TimerFailedListenerDelegate(List, ToTimerFailedConverter)}
   *   <li>{@link TimerFailedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TimerFailedListenerDelegate(processRuntimeEventListeners,
        new ToTimerFailedConverter(new BPMNTimerConverter()))).isFailOnException());
  }

  /**
   * Test {@link TimerFailedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFailedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNTimerFailedEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNTimerFailedEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    ToTimerFailedConverter converter = mock(ToTimerFailedConverter.class);
    Optional<BPMNTimerFailedEvent> ofResult = Optional.of(new BPMNTimerFailedEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerFailedListenerDelegate timerFailedListenerDelegate = new TimerFailedListenerDelegate(
        processRuntimeEventListeners, converter);

    // Act
    timerFailedListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerFailedEvent.class));
    verify(converter).from(isA(ActivitiEvent.class));
  }

  /**
   * Test {@link TimerFailedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then calls {@link ToTimerFailedConverter#from(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFailedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEvent)")
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ToTimerFailedConverter converter = mock(ToTimerFailedConverter.class);
    Optional<BPMNTimerFailedEvent> ofResult = Optional.of(new BPMNTimerFailedEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerFailedListenerDelegate timerFailedListenerDelegate = new TimerFailedListenerDelegate(new ArrayList<>(),
        converter);

    // Act
    timerFailedListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEvent.class));
  }
}
