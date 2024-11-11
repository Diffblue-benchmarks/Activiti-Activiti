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
import org.activiti.api.process.model.events.BPMNMessageSentEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiMessageEventImpl;
import org.activiti.runtime.api.event.impl.BPMNMessageConverter;
import org.activiti.runtime.api.event.impl.ToMessageSentConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageSentListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MessageSentListenerDelegate#MessageSentListenerDelegate(List, ToMessageSentConverter)}
   *   <li>{@link MessageSentListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new MessageSentListenerDelegate(processRuntimeEventListeners,
        new ToMessageSentConverter(new BPMNMessageConverter()))).isFailOnException());
  }

  /**
   * Test {@link MessageSentListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls
   * {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSentListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNMessageSentEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNMessageSentEvent>any());

    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    MessageSentListenerDelegate messageSentListenerDelegate = new MessageSentListenerDelegate(
        processRuntimeEventListeners, new ToMessageSentConverter(new BPMNMessageConverter()));

    // Act
    messageSentListenerDelegate.onEvent(new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNMessageSentEvent.class));
  }
}