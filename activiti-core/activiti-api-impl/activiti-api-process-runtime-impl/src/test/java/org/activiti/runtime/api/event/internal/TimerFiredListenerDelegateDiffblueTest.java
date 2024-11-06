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
import org.activiti.api.process.model.events.BPMNTimerFiredEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.BPMNTimerFiredEventImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerFiredConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerFiredListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TimerFiredListenerDelegate#TimerFiredListenerDelegate(List, ToTimerFiredConverter)}
   *   <li>{@link TimerFiredListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TimerFiredListenerDelegate(processRuntimeEventListeners,
        new ToTimerFiredConverter(new BPMNTimerConverter()))).isFailOnException());
  }

  /**
   * Test {@link TimerFiredListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNTimerFiredEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNTimerFiredEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    ToTimerFiredConverter converter = mock(ToTimerFiredConverter.class);
    Optional<BPMNTimerFiredEvent> ofResult = Optional.of(new BPMNTimerFiredEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerFiredListenerDelegate timerFiredListenerDelegate = new TimerFiredListenerDelegate(processRuntimeEventListeners,
        converter);

    // Act
    timerFiredListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerFiredEvent.class));
    verify(converter).from(isA(ActivitiEvent.class));
  }

  /**
   * Test {@link TimerFiredListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then calls {@link ToTimerFiredConverter#from(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEvent)")
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ToTimerFiredConverter converter = mock(ToTimerFiredConverter.class);
    Optional<BPMNTimerFiredEvent> ofResult = Optional.of(new BPMNTimerFiredEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerFiredListenerDelegate timerFiredListenerDelegate = new TimerFiredListenerDelegate(new ArrayList<>(),
        converter);

    // Act
    timerFiredListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEvent.class));
  }
}
