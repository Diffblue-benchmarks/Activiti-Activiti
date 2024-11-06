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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageSubscriptionCancelledListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MessageSubscriptionCancelledListenerDelegate#MessageSubscriptionCancelledListenerDelegate(List, ToMessageSubscriptionCancelledConverter)}
   *   <li>{@link MessageSubscriptionCancelledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new MessageSubscriptionCancelledListenerDelegate(processRuntimeEventListeners,
        new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()))).isFailOnException());
  }

  /**
   * Test
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isValidEvent(ActivitiEvent); given ArrayList() add ProcessRuntimeEventListener")
  void testIsValidEvent_givenArrayListAddProcessRuntimeEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    MessageSubscriptionCancelledListenerDelegate messageSubscriptionCancelledListenerDelegate = new MessageSubscriptionCancelledListenerDelegate(
        processRuntimeEventListeners, new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()));

    // Act and Assert
    assertFalse(messageSubscriptionCancelledListenerDelegate.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isValidEvent(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor)")
  void testIsValidEvent_whenActivitiActivityCancelledEventImpl() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> processRuntimeEventListeners = new ArrayList<>();
    MessageSubscriptionCancelledListenerDelegate messageSubscriptionCancelledListenerDelegate = new MessageSubscriptionCancelledListenerDelegate(
        processRuntimeEventListeners, new ToMessageSubscriptionCancelledConverter(new MessageSubscriptionConverter()));

    // Act and Assert
    assertFalse(messageSubscriptionCancelledListenerDelegate.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   * with {@code Entity} and type is {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageSubscriptionCancelledListenerDelegate#isValidEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isValidEvent(ActivitiEvent); when ActivitiEntityEventImpl(Object, ActivitiEventType) with 'Entity' and type is 'ENTITY_CREATED'")
  void testIsValidEvent_whenActivitiEntityEventImplWithEntityAndTypeIsEntityCreated() {
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
