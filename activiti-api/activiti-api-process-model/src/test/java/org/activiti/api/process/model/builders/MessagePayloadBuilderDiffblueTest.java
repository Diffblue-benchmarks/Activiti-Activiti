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
package org.activiti.api.process.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessagePayloadBuilderDiffblueTest {
  /**
   * Test {@link MessagePayloadBuilder#start(String)}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#start(String)}
   */
  @Test
  @DisplayName("Test start(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.StartMessagePayloadBuilder MessagePayloadBuilder.start(String)"
  })
  void testStart() {
    // Arrange, Act and Assert
    StartMessagePayload startMessagePayload = MessagePayloadBuilder.start("Name").build();
    assertEquals("Name", startMessagePayload.getName());
    assertNull(startMessagePayload.getBusinessKey());
    assertNull(startMessagePayload.getVariables());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(MessageEventPayload)} with {@code messageEventPayload}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  @DisplayName("Test from(MessageEventPayload) with 'messageEventPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.MessageEventPayloadBuilder MessagePayloadBuilder.from(MessageEventPayload)"
  })
  void testFromWithMessageEventPayload() {
    // Arrange
    MessageEventPayload messageEventPayload =
        new MessageEventPayload(
            "messagePayload must not be null",
            "messagePayload must not be null",
            "messagePayload must not be null",
            new HashMap<>());

    // Act and Assert
    MessageEventPayload messageEventPayload2 =
        MessagePayloadBuilder.from(messageEventPayload).build();
    assertEquals("messagePayload must not be null", messageEventPayload2.getBusinessKey());
    assertEquals("messagePayload must not be null", messageEventPayload2.getCorrelationKey());
    assertEquals("messagePayload must not be null", messageEventPayload2.getName());
    assertTrue(messageEventPayload2.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(ReceiveMessagePayload)} with {@code
   * receiveMessagePayload}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test from(ReceiveMessagePayload) with 'receiveMessagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.ReceiveMessagePayloadBuilder MessagePayloadBuilder.from(ReceiveMessagePayload)"
  })
  void testFromWithReceiveMessagePayload() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload(
            "messagePayload must not be null", "messagePayload must not be null", new HashMap<>());

    // Act and Assert
    ReceiveMessagePayload receiveMessagePayload2 =
        MessagePayloadBuilder.from(receiveMessagePayload).build();
    assertEquals("messagePayload must not be null", receiveMessagePayload2.getCorrelationKey());
    assertEquals("messagePayload must not be null", receiveMessagePayload2.getName());
    assertTrue(receiveMessagePayload2.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(StartMessagePayload)} with {@code startMessagePayload}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test from(StartMessagePayload) with 'startMessagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.StartMessagePayloadBuilder MessagePayloadBuilder.from(StartMessagePayload)"
  })
  void testFromWithStartMessagePayload() {
    // Arrange
    StartMessagePayload startMessagePayload =
        new StartMessagePayload(
            "messagePayload must not be null", "messagePayload must not be null", new HashMap<>());

    // Act and Assert
    StartMessagePayload startMessagePayload2 =
        MessagePayloadBuilder.from(startMessagePayload).build();
    assertEquals("messagePayload must not be null", startMessagePayload2.getBusinessKey());
    assertEquals("messagePayload must not be null", startMessagePayload2.getName());
    assertTrue(startMessagePayload2.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#receive(String)}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#receive(String)}
   */
  @Test
  @DisplayName("Test receive(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.ReceiveMessagePayloadBuilder MessagePayloadBuilder.receive(String)"
  })
  void testReceive() {
    // Arrange, Act and Assert
    ReceiveMessagePayload receiveMessagePayload = MessagePayloadBuilder.receive("Name").build();
    assertEquals("Name", receiveMessagePayload.getName());
    assertNull(receiveMessagePayload.getCorrelationKey());
    assertNull(receiveMessagePayload.getVariables());
  }

  /**
   * Test {@link MessagePayloadBuilder#event(String)}.
   *
   * <p>Method under test: {@link MessagePayloadBuilder#event(String)}
   */
  @Test
  @DisplayName("Test event(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.MessageEventPayloadBuilder MessagePayloadBuilder.event(String)"
  })
  void testEvent() {
    // Arrange, Act and Assert
    MessageEventPayload messageEventPayload = MessagePayloadBuilder.event("Name").build();
    assertEquals("Name", messageEventPayload.getName());
    assertNull(messageEventPayload.getBusinessKey());
    assertNull(messageEventPayload.getCorrelationKey());
    assertNull(messageEventPayload.getVariables());
  }
}
