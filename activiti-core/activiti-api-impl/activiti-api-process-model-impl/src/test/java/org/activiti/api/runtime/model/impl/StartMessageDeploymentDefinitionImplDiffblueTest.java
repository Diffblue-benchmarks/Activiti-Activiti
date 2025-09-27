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
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.StartMessageSubscription;
import org.activiti.api.runtime.model.impl.StartMessageDeploymentDefinitionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Builder.class})
@ExtendWith(SpringExtension.class)
class StartMessageDeploymentDefinitionImplDiffblueTest {
  @Autowired private Builder builder;

  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#withMessageSubscription(StartMessageSubscription)}
   *   <li>{@link Builder#withProcessDefinition(ProcessDefinition)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Builder.<init>()",
    "StartMessageDeploymentDefinitionImpl Builder.build()",
    "Builder Builder.withMessageSubscription(StartMessageSubscription)",
    "Builder Builder.withProcessDefinition(ProcessDefinition)"
  })
  void testBuilderBuild() {
    // Arrange and Act
    Builder actualBuilderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");
    StartMessageSubscriptionImpl messageEventSubscription =
        withConfigurationResult
            .withCreated(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
            .withEventName("Event Name")
            .withId("42")
            .withProcessDefinitionId("42")
            .build();
    Builder actualWithMessageSubscriptionResult =
        actualBuilderResult.withMessageSubscription(messageEventSubscription);
    ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl();
    StartMessageDeploymentDefinitionImpl actualStartMessageDeploymentDefinitionImpl =
        actualWithMessageSubscriptionResult.withProcessDefinition(processDefinition).build();

    // Assert
    assertSame(
        processDefinition, actualStartMessageDeploymentDefinitionImpl.getProcessDefinition());
    assertSame(
        messageEventSubscription,
        actualStartMessageDeploymentDefinitionImpl.getMessageSubscription());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#StartMessageDeploymentDefinitionImpl()}
   *   <li>{@link
   *       StartMessageDeploymentDefinitionImpl#builderFrom(StartMessageDeploymentDefinitionImpl)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#toString()}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#getMessageSubscription()}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#getProcessDefinition()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartMessageDeploymentDefinitionImpl.<init>()",
    "Builder StartMessageDeploymentDefinitionImpl.builderFrom(StartMessageDeploymentDefinitionImpl)",
    "StartMessageSubscription StartMessageDeploymentDefinitionImpl.getMessageSubscription()",
    "ProcessDefinition StartMessageDeploymentDefinitionImpl.getProcessDefinition()",
    "String StartMessageDeploymentDefinitionImpl.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    StartMessageDeploymentDefinitionImpl actualStartMessageDeploymentDefinitionImpl =
        new StartMessageDeploymentDefinitionImpl();
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");
    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    actualStartMessageDeploymentDefinitionImpl.builderFrom(
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build());
    String actualToStringResult = actualStartMessageDeploymentDefinitionImpl.toString();
    StartMessageSubscription actualMessageSubscription =
        actualStartMessageDeploymentDefinitionImpl.getMessageSubscription();

    // Assert
    assertEquals(
        "StartMessageDeploymentDefinitionImpl [messageSubscription=null, processDefinition=null]",
        actualToStringResult);
    assertNull(actualStartMessageDeploymentDefinitionImpl.getProcessDefinition());
    assertNull(actualMessageSubscription);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}, and {@link
   * StartMessageDeploymentDefinitionImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl =
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build();

    Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult2 =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult2 =
        builderResult2.withMessageSubscription(
            withConfigurationResult2
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl2 =
        withMessageSubscriptionResult2.withProcessDefinition(new ProcessDefinitionImpl()).build();

    // Act and Assert
    assertEquals(startMessageDeploymentDefinitionImpl, startMessageDeploymentDefinitionImpl2);
    assertEquals(
        startMessageDeploymentDefinitionImpl.hashCode(),
        startMessageDeploymentDefinitionImpl2.hashCode());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}, and {@link
   * StartMessageDeploymentDefinitionImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl =
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build();

    // Act and Assert
    assertEquals(startMessageDeploymentDefinitionImpl, startMessageDeploymentDefinitionImpl);
    int expectedHashCodeResult = startMessageDeploymentDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, startMessageDeploymentDefinitionImpl.hashCode());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl =
        builderResult
            .withMessageSubscription(
                withConfigurationResult
                    .withCreated(
                        Date.from(
                            LocalDate.of(1970, 1, 1)
                                .atStartOfDay()
                                .atZone(ZoneOffset.UTC)
                                .toInstant()))
                    .withEventName("Event Name")
                    .withId("42")
                    .withProcessDefinitionId("42")
                    .build())
            .withProcessDefinition(null)
            .build();

    Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult2 =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult2.withMessageSubscription(
            withConfigurationResult2
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());

    // Act and Assert
    assertNotEquals(
        startMessageDeploymentDefinitionImpl,
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("Activity Id")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl =
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build();

    Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult2 =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult2 =
        builderResult2.withMessageSubscription(
            withConfigurationResult2
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());

    // Act and Assert
    assertNotEquals(
        startMessageDeploymentDefinitionImpl,
        withMessageSubscriptionResult2.withProcessDefinition(new ProcessDefinitionImpl()).build());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());

    // Act and Assert
    assertNotEquals(
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build(),
        null);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessageDeploymentDefinitionImpl.equals(Object)",
    "int StartMessageDeploymentDefinitionImpl.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());

    // Act and Assert
    assertNotEquals(
        withMessageSubscriptionResult.withProcessDefinition(new ProcessDefinitionImpl()).build(),
        "Different type to StartMessageDeploymentDefinitionImpl");
  }
}
