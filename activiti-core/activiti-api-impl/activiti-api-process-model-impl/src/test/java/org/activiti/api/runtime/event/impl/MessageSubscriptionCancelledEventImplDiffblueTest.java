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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.MessageSubscription;
import org.activiti.api.process.model.events.MessageSubscriptionEvent;
import org.activiti.api.process.model.events.MessageSubscriptionEvent.MessageSubscriptionEvents;
import org.activiti.api.runtime.model.impl.MessageSubscriptionImpl;
import org.activiti.api.runtime.model.impl.MessageSubscriptionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessageSubscriptionCancelledEventImplDiffblueTest {
  /**
   * Test {@link MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl()}.
   *
   * <p>Method under test: {@link
   * MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl()}
   */
  @Test
  @DisplayName("Test new MessageSubscriptionCancelledEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageSubscriptionCancelledEventImpl.<init>()"})
  void testNewMessageSubscriptionCancelledEventImpl() {
    // Arrange and Act
    MessageSubscriptionCancelledEventImpl actualMessageSubscriptionCancelledEventImpl =
        new MessageSubscriptionCancelledEventImpl();

    // Assert
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getBusinessKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getEntity());
    assertEquals(
        MessageSubscriptionEvents.MESSAGE_SUBSCRIPTION_CANCELLED,
        actualMessageSubscriptionCancelledEventImpl.getEventType());
  }

  /**
   * Test {@link
   * MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl(MessageSubscription)}.
   *
   * <p>Method under test: {@link
   * MessageSubscriptionCancelledEventImpl#MessageSubscriptionCancelledEventImpl(MessageSubscription)}
   */
  @Test
  @DisplayName("Test new MessageSubscriptionCancelledEventImpl(MessageSubscription)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageSubscriptionCancelledEventImpl.<init>(MessageSubscription)"})
  void testNewMessageSubscriptionCancelledEventImpl2() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl entity =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    // Act
    MessageSubscriptionCancelledEventImpl actualMessageSubscriptionCancelledEventImpl =
        new MessageSubscriptionCancelledEventImpl(entity);

    // Assert
    MessageSubscription entity2 = actualMessageSubscriptionCancelledEventImpl.getEntity();
    assertTrue(entity2 instanceof MessageSubscriptionImpl);
    assertEquals("42", actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionId());
    assertEquals("42", actualMessageSubscriptionCancelledEventImpl.getProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getBusinessKey());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualMessageSubscriptionCancelledEventImpl.getProcessDefinitionKey());
    assertEquals(
        MessageSubscriptionEvents.MESSAGE_SUBSCRIPTION_CANCELLED,
        actualMessageSubscriptionCancelledEventImpl.getEventType());
    assertSame(entity, entity2);
  }
}
