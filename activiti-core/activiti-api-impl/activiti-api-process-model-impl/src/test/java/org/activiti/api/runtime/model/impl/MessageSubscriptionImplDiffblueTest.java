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
import org.activiti.api.runtime.model.impl.MessageSubscriptionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageSubscriptionImplDiffblueTest {
  /**
   * Test Builder {@link Builder#build()}.
   * <p>
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
  @DisplayName("Test Builder build()")
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
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    MessageSubscriptionImpl actualMessageSubscriptionImpl = new MessageSubscriptionImpl();
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl messageEventSubscriptionImpl = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    actualMessageSubscriptionImpl.builderFrom(messageEventSubscriptionImpl);
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

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}, and
   * {@link MessageSubscriptionImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}, and
   * {@link MessageSubscriptionImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("Activity Id")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration(null);
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName(null)
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("42")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("Id")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("Process Definition Id")
        .withProcessInstanceId("42")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("Process Instance Id")
        .build();
    MessageSubscriptionImpl.Builder withConfigurationResult2 = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MessageSubscriptionImpl.Builder withConfigurationResult = MessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withBusinessKey("Business Key")
        .withConfiguration("Configuration");
    MessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withExecutionId("0123456789ABCDEF")
        .withId("42")
        .withProcessDefinitionId("42")
        .withProcessInstanceId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to MessageSubscriptionImpl");
  }
}
