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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.MessageSubscription;
import org.activiti.api.process.model.events.MessageSubscriptionEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageSubscriptionCancelledEventImplDiffblueTest {
  /**
   * Test
   * {@link MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl()}.
   * <p>
   * Method under test:
   * {@link MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl()}
   */
  @Test
  @DisplayName("Test new MessageSubscriptionCancelledEventImpl()")
  void testNewMessageSubscriptionCancelledEventImpl() {
    // Arrange and Act
    MessageSubscriptionCancelledEventImpl actualMessageSubscriptionCancelledEventImpl = new MessageSubscriptionCancelledEventImpl();

    // Assert
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getBusinessKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getEntity());
    assertEquals(MessageSubscriptionEvent.MessageSubscriptionEvents.MESSAGE_SUBSCRIPTION_CANCELLED,
        actualMessageSubscriptionCancelledEventImpl.getEventType());
  }

  /**
   * Test
   * {@link MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl(MessageSubscription)}.
   * <p>
   * Method under test:
   * {@link MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl(MessageSubscription)}
   */
  @Test
  @DisplayName("Test new MessageSubscriptionCancelledEventImpl(MessageSubscription)")
  void testNewMessageSubscriptionCancelledEventImpl2() {
    // Arrange
    MessageSubscription entity = mock(MessageSubscription.class);
    when(entity.getProcessDefinitionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    MessageSubscriptionCancelledEventImpl actualMessageSubscriptionCancelledEventImpl = new MessageSubscriptionCancelledEventImpl(
        entity);

    // Assert
    verify(entity).getProcessDefinitionId();
    verify(entity).getProcessInstanceId();
    assertEquals("42", actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionId());
    assertEquals("42", actualMessageSubscriptionCancelledEventImpl.getProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getBusinessKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionKey());
    assertEquals(MessageSubscriptionEvent.MessageSubscriptionEvents.MESSAGE_SUBSCRIPTION_CANCELLED,
        actualMessageSubscriptionCancelledEventImpl.getEventType());
    assertSame(entity, actualMessageSubscriptionCancelledEventImpl.getEntity());
  }
}
