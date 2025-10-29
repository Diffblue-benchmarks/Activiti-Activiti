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
import org.activiti.api.process.model.events.BPMNTimerScheduledEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.runtime.event.impl.BPMNTimerScheduledEventImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerScheduledConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerScheduledListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TimerScheduledListenerDelegate#TimerScheduledListenerDelegate(List, ToTimerScheduledConverter)}
   *   <li>{@link TimerScheduledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TimerScheduledListenerDelegate(processRuntimeEventListeners,
        new ToTimerScheduledConverter(new BPMNTimerConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link TimerScheduledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    ToTimerScheduledConverter converter = mock(ToTimerScheduledConverter.class);
    Optional<BPMNTimerScheduledEvent> ofResult = Optional.of(new BPMNTimerScheduledEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerScheduledListenerDelegate timerScheduledListenerDelegate = new TimerScheduledListenerDelegate(
        new ArrayList<>(), converter);

    // Act
    timerScheduledListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    verify(converter).from(isA(ActivitiEvent.class));
  }

  /**
   * Method under test:
   * {@link TimerScheduledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent2() {
    // Arrange
    BPMNElementEventListener<BPMNTimerScheduledEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNTimerScheduledEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    ToTimerScheduledConverter converter = mock(ToTimerScheduledConverter.class);
    Optional<BPMNTimerScheduledEvent> ofResult = Optional.of(new BPMNTimerScheduledEventImpl());
    when(converter.from(Mockito.<ActivitiEvent>any())).thenReturn(ofResult);
    TimerScheduledListenerDelegate timerScheduledListenerDelegate = new TimerScheduledListenerDelegate(
        processRuntimeEventListeners, converter);

    // Act
    timerScheduledListenerDelegate.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerScheduledEvent.class));
    verify(converter).from(isA(ActivitiEvent.class));
  }
}
