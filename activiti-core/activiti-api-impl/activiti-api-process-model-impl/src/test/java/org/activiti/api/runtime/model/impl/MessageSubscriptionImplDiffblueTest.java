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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class MessageSubscriptionImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl.Builder#build()}
   *   <li>{@link MessageSubscriptionImpl.Builder#withActivityId(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withBusinessKey(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withConfiguration(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withCreated(Date)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withEventName(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withExecutionId(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withId(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withProcessDefinitionId(String)}
   *   <li>{@link MessageSubscriptionImpl.Builder#withProcessInstanceId(String)}
   * </ul>
   */
  @Test
  void testBuilderBuild() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    MessageSubscriptionImpl actualBuildResult = withConfigurationResult.withCreated(created)
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Assert
    assertEquals("0123456789ABCDEF", actualBuildResult.getExecutionId());
    assertEquals("42", actualBuildResult.getActivityId());
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getProcessDefinitionId());
    assertEquals("42", actualBuildResult.getProcessInstanceId());
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertEquals("Configuration", actualBuildResult.getConfiguration());
    assertEquals("Event Name", actualBuildResult.getEventName());
    assertSame(created, actualBuildResult.getCreated());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageSubscriptionImpl messageSubscriptionImpl = new MessageSubscriptionImpl();
    MessageSubscriptionImpl messageSubscriptionImpl2 = new MessageSubscriptionImpl();

    // Act and Assert
    assertEquals(messageSubscriptionImpl, messageSubscriptionImpl2);
    int expectedHashCodeResult = messageSubscriptionImpl.hashCode();
    assertEquals(expectedHashCodeResult, messageSubscriptionImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageSubscriptionImpl messageSubscriptionImpl = new MessageSubscriptionImpl();

    // Act and Assert
    assertEquals(messageSubscriptionImpl, messageSubscriptionImpl);
    int expectedHashCodeResult = messageSubscriptionImpl.hashCode();
    assertEquals(expectedHashCodeResult, messageSubscriptionImpl.hashCode());
  }

  /**
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageSubscriptionImpl(), 1);
  }

  /**
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageSubscriptionImpl(), null);
  }

  /**
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageSubscriptionImpl(), "Different type to MessageSubscriptionImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#MessageSubscriptionImpl()}
   *   <li>{@link MessageSubscriptionImpl#builderFrom(MessageSubscriptionImpl)}
   *   <li>{@link MessageSubscriptionImpl#toString()}
   *   <li>{@link MessageSubscriptionImpl#getActivityId()}
   *   <li>{@link MessageSubscriptionImpl#getBusinessKey()}
   *   <li>{@link MessageSubscriptionImpl#getConfiguration()}
   *   <li>{@link MessageSubscriptionImpl#getCreated()}
   *   <li>{@link MessageSubscriptionImpl#getEventName()}
   *   <li>{@link MessageSubscriptionImpl#getExecutionId()}
   *   <li>{@link MessageSubscriptionImpl#getId()}
   *   <li>{@link MessageSubscriptionImpl#getProcessDefinitionId()}
   *   <li>{@link MessageSubscriptionImpl#getProcessInstanceId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    MessageSubscriptionImpl actualMessageSubscriptionImpl = new MessageSubscriptionImpl();
    actualMessageSubscriptionImpl.builderFrom(new MessageSubscriptionImpl());
    String actualToStringResult = actualMessageSubscriptionImpl.toString();
    String actualActivityId = actualMessageSubscriptionImpl.getActivityId();
    String actualBusinessKey = actualMessageSubscriptionImpl.getBusinessKey();
    String actualConfiguration = actualMessageSubscriptionImpl.getConfiguration();
    Date actualCreated = actualMessageSubscriptionImpl.getCreated();
    String actualEventName = actualMessageSubscriptionImpl.getEventName();
    String actualExecutionId = actualMessageSubscriptionImpl.getExecutionId();
    String actualId = actualMessageSubscriptionImpl.getId();
    String actualProcessDefinitionId = actualMessageSubscriptionImpl.getProcessDefinitionId();

    // Assert
    assertEquals("MessageEventSubscriptionImpl [id=null, eventName=null, executionId=null, processInstanceId=null,"
        + " processDefinitionId=null, configuration=null, activityId=null, created=null]", actualToStringResult);
    assertNull(actualActivityId);
    assertNull(actualBusinessKey);
    assertNull(actualConfiguration);
    assertNull(actualEventName);
    assertNull(actualExecutionId);
    assertNull(actualId);
    assertNull(actualProcessDefinitionId);
    assertNull(actualMessageSubscriptionImpl.getProcessInstanceId());
    assertNull(actualCreated);
  }
}
