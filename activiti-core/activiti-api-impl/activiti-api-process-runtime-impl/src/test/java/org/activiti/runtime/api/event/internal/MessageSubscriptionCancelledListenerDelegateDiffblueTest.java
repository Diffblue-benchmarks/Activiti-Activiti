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
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.MessageSubscriptionCancelledEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.runtime.api.event.impl.MessageSubscriptionConverter;
import org.activiti.runtime.api.event.impl.ToMessageSubscriptionCancelledConverter;
import org.junit.jupiter.api.Test;

class MessageSubscriptionCancelledListenerDelegateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MessageSubscriptionCancelledListenerDelegate#MessageSubscriptionCancelledListenerDelegate(List, ToMessageSubscriptionCancelledConverter)}
   *   <li>{@link MessageSubscriptionCancelledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new MessageSubscriptionCancelledListenerDelegate(processRuntimeEventListeners,
        new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()))).isFailOnException());
  }

  /**
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  void testIsValidEvent() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    MessageSubscriptionCancelledListenerDelegate messageSubscriptionCancelledListenerDelegate = new MessageSubscriptionCancelledListenerDelegate(
        processRuntimeEventListeners, new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()));

    // Act and Assert
    assertFalse(messageSubscriptionCancelledListenerDelegate.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  void testIsValidEvent2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    MessageSubscriptionCancelledListenerDelegate messageSubscriptionCancelledListenerDelegate = new MessageSubscriptionCancelledListenerDelegate(
        processRuntimeEventListeners, new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()));

    // Act and Assert
    assertFalse(messageSubscriptionCancelledListenerDelegate.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  void testIsValidEvent3() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    MessageSubscriptionCancelledListenerDelegate messageSubscriptionCancelledListenerDelegate = new MessageSubscriptionCancelledListenerDelegate(
        processRuntimeEventListeners, new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()));

    // Act and Assert
    assertFalse(messageSubscriptionCancelledListenerDelegate
        .isValidEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED)));
  }
}
