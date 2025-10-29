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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.ToActivityCancelledConverter;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActivityCancelledListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivityCancelledListenerDelegate#ActivityCancelledListenerDelegate(List, ToActivityCancelledConverter)}
   *   <li>{@link ActivityCancelledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ActivityCancelledListenerDelegate(processRuntimeEventListeners,
        new ToActivityCancelledConverter(new ToActivityConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link ActivityCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  void testOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNActivityCancelledEvent> bpmnElementEventListener = mock(
        BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNActivityCancelledEvent>any());

    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    ActivityCancelledListenerDelegate activityCancelledListenerDelegate = new ActivityCancelledListenerDelegate(
        processRuntimeEventListeners, new ToActivityCancelledConverter(new ToActivityConverter()));

    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    event.setActivityId("42");

    // Act
    activityCancelledListenerDelegate.onEvent(event);

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNActivityCancelledEvent.class));
  }
}
