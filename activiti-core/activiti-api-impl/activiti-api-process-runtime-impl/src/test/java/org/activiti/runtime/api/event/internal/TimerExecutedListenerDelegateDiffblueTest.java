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
import org.activiti.api.process.model.events.BPMNTimerExecutedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.runtime.event.impl.BPMNTimerExecutedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerExecutedConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerExecutedListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TimerExecutedListenerDelegate#TimerExecutedListenerDelegate(List, ToTimerExecutedConverter)}
   *   <li>{@link TimerExecutedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TimerExecutedListenerDelegate(processRuntimeEventListeners,
        new ToTimerExecutedConverter(new BPMNTimerConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link TimerExecutedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToTimerExecutedConverter converter = mock(ToTimerExecutedConverter.class);
    Optional<BPMNTimerExecutedEvent> ofResult = Optional.of(new BPMNTimerExecutedEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerExecutedListenerDelegate timerExecutedListenerDelegate = new TimerExecutedListenerDelegate(new ArrayList<>(),
        converter);

    // Act
    timerExecutedListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEvent.class));
  }

  /**
   * Method under test:
   * {@link TimerExecutedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    BPMNElementEventListener<BPMNTimerExecutedEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNTimerExecutedEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    ToTimerExecutedConverter converter = mock(ToTimerExecutedConverter.class);
    Optional<BPMNTimerExecutedEvent> ofResult = Optional.of(new BPMNTimerExecutedEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerExecutedListenerDelegate timerExecutedListenerDelegate = new TimerExecutedListenerDelegate(
        processRuntimeEventListeners, converter);

    // Act
    timerExecutedListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerExecutedEvent.class));
    verify(converter).from(isA(ActivitiEvent.class));
  }
}
