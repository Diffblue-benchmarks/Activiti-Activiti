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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.function.BiFunction;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessagePayloadBuilderDiffblueTest {
  /**
   * Test {@link MessagePayloadBuilder#start(String)}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#start(String)}
   */
  @Test
  @DisplayName("Test start(String)")
  void testStart() {
    // Arrange, Act and Assert
    StartMessagePayload buildResult = MessagePayloadBuilder.start("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(MessageEventPayload)} with
   * {@code messageEventPayload}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  @DisplayName("Test from(MessageEventPayload) with 'messageEventPayload'")
  void testFromWithMessageEventPayload() {
    // Arrange, Act and Assert
    MessageEventPayload buildResult = MessagePayloadBuilder
        .from(new MessageEventPayload("messagePayload must not be null", "messagePayload must not be null",
            "messagePayload must not be null", new HashMap<>()))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(MessageEventPayload)} with
   * {@code messageEventPayload}.
   * <ul>
   *   <li>Given {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  @DisplayName("Test from(MessageEventPayload) with 'messageEventPayload'; given 'messagePayload must not be null'")
  void testFromWithMessageEventPayload_givenMessagePayloadMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    MessageEventPayload buildResult = MessagePayloadBuilder
        .from(new MessageEventPayload("messagePayload must not be null", "messagePayload must not be null",
            "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(ReceiveMessagePayload)} with
   * {@code receiveMessagePayload}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test from(ReceiveMessagePayload) with 'receiveMessagePayload'")
  void testFromWithReceiveMessagePayload() {
    // Arrange, Act and Assert
    ReceiveMessagePayload buildResult = MessagePayloadBuilder
        .from(new ReceiveMessagePayload("messagePayload must not be null", "messagePayload must not be null",
            new HashMap<>()))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(ReceiveMessagePayload)} with
   * {@code receiveMessagePayload}.
   * <ul>
   *   <li>Given {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test from(ReceiveMessagePayload) with 'receiveMessagePayload'; given 'messagePayload must not be null'")
  void testFromWithReceiveMessagePayload_givenMessagePayloadMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    ReceiveMessagePayload buildResult = MessagePayloadBuilder
        .from(
            new ReceiveMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(StartMessagePayload)} with
   * {@code startMessagePayload}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test from(StartMessagePayload) with 'startMessagePayload'")
  void testFromWithStartMessagePayload() {
    // Arrange, Act and Assert
    StartMessagePayload buildResult = MessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null",
            new HashMap<>()))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#from(StartMessagePayload)} with
   * {@code startMessagePayload}.
   * <ul>
   *   <li>Given {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test from(StartMessagePayload) with 'startMessagePayload'; given 'messagePayload must not be null'")
  void testFromWithStartMessagePayload_givenMessagePayloadMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    StartMessagePayload buildResult = MessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link MessagePayloadBuilder#receive(String)}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#receive(String)}
   */
  @Test
  @DisplayName("Test receive(String)")
  void testReceive() {
    // Arrange, Act and Assert
    ReceiveMessagePayload buildResult = MessagePayloadBuilder.receive("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getCorrelationKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link MessagePayloadBuilder#event(String)}.
   * <p>
   * Method under test: {@link MessagePayloadBuilder#event(String)}
   */
  @Test
  @DisplayName("Test event(String)")
  void testEvent() {
    // Arrange, Act and Assert
    MessageEventPayload buildResult = MessagePayloadBuilder.event("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getCorrelationKey());
    assertNull(buildResult.getVariables());
  }
}
