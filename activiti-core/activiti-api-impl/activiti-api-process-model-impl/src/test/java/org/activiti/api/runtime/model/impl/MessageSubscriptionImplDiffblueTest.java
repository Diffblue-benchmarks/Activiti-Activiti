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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.runtime.model.impl.MessageSubscriptionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Builder.class})
@ExtendWith(SpringExtension.class)
class MessageSubscriptionImplDiffblueTest {
  @Autowired private Builder builder;

  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#withActivityId(String)}
   *   <li>{@link Builder#withBusinessKey(String)}
   *   <li>{@link Builder#withConfiguration(String)}
   *   <li>{@link Builder#withCreated(Date)}
   *   <li>{@link Builder#withEventName(String)}
   *   <li>{@link Builder#withExecutionId(String)}
   *   <li>{@link Builder#withId(String)}
   *   <li>{@link Builder#withProcessDefinitionId(String)}
   *   <li>{@link Builder#withProcessInstanceId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Builder.<init>()",
    "MessageSubscriptionImpl Builder.build()",
    "Builder Builder.withActivityId(String)",
    "Builder Builder.withBusinessKey(String)",
    "Builder Builder.withConfiguration(String)",
    "Builder Builder.withCreated(Date)",
    "Builder Builder.withEventName(String)",
    "Builder Builder.withExecutionId(String)",
    "Builder Builder.withId(String)",
    "Builder Builder.withProcessDefinitionId(String)",
    "Builder Builder.withProcessInstanceId(String)"
  })
  void testBuilderBuild() {
    // Arrange and Act
    Builder actualWithConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    Date created =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    MessageSubscriptionImpl actualMessageSubscriptionImpl =
        actualWithConfigurationResult
            .withCreated(created)
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    // Assert
    assertEquals("0123456789ABCDEF", actualMessageSubscriptionImpl.getExecutionId());
    assertEquals("42", actualMessageSubscriptionImpl.getActivityId());
    assertEquals("42", actualMessageSubscriptionImpl.getId());
    assertEquals("42", actualMessageSubscriptionImpl.getProcessDefinitionId());
    assertEquals("42", actualMessageSubscriptionImpl.getProcessInstanceId());
    assertEquals("Business Key", actualMessageSubscriptionImpl.getBusinessKey());
    assertEquals("Configuration", actualMessageSubscriptionImpl.getConfiguration());
    assertEquals("Event Name", actualMessageSubscriptionImpl.getEventName());
    assertSame(created, actualMessageSubscriptionImpl.getCreated());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MessageSubscriptionImpl.<init>()",
    "Builder MessageSubscriptionImpl.builderFrom(MessageSubscriptionImpl)",
    "String MessageSubscriptionImpl.getActivityId()",
    "String MessageSubscriptionImpl.getBusinessKey()",
    "String MessageSubscriptionImpl.getConfiguration()",
    "Date MessageSubscriptionImpl.getCreated()",
    "String MessageSubscriptionImpl.getEventName()",
    "String MessageSubscriptionImpl.getExecutionId()",
    "String MessageSubscriptionImpl.getId()",
    "String MessageSubscriptionImpl.getProcessDefinitionId()",
    "String MessageSubscriptionImpl.getProcessInstanceId()",
    "String MessageSubscriptionImpl.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    MessageSubscriptionImpl actualMessageSubscriptionImpl = new MessageSubscriptionImpl();
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    actualMessageSubscriptionImpl.builderFrom(
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
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
    assertEquals(
        "MessageEventSubscriptionImpl [id=null, eventName=null, executionId=null, processInstanceId=null,"
            + " processDefinitionId=null, configuration=null, activityId=null, created=null]",
        actualToStringResult);
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
   * Test {@link MessageSubscriptionImpl#equals(Object)}, and {@link
   * MessageSubscriptionImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
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

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl2 =
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    // Act and Assert
    assertEquals(messageSubscriptionImpl, messageSubscriptionImpl2);
    assertEquals(messageSubscriptionImpl.hashCode(), messageSubscriptionImpl2.hashCode());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}, and {@link
   * MessageSubscriptionImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MessageSubscriptionImpl#equals(Object)}
   *   <li>{@link MessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
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

    // Act and Assert
    assertEquals(messageSubscriptionImpl, messageSubscriptionImpl);
    int expectedHashCodeResult = messageSubscriptionImpl.hashCode();
    assertEquals(expectedHashCodeResult, messageSubscriptionImpl.hashCode());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("Activity Id")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
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

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration(null);
    MessageSubscriptionImpl messageSubscriptionImpl =
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

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName(null)
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("42")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("Id")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("Process Definition Id")
            .withProcessInstanceId("42")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");
    MessageSubscriptionImpl messageSubscriptionImpl =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("Process Instance Id")
            .build();

    Builder withConfigurationResult2 =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        messageSubscriptionImpl,
        withConfigurationResult2
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build());
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build(),
        null);
  }

  /**
   * Test {@link MessageSubscriptionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageSubscriptionImpl.equals(Object)",
    "int MessageSubscriptionImpl.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult =
        MessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withBusinessKey("Business Key")
            .withConfiguration("Configuration");

    // Act and Assert
    assertNotEquals(
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withExecutionId("0123456789ABCDEF")
            .withId("42")
            .withProcessDefinitionId("42")
            .withProcessInstanceId("42")
            .build(),
        "Different type to MessageSubscriptionImpl");
  }
}
