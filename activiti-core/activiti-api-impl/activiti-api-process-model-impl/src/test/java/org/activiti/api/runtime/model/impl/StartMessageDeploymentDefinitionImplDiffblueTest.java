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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.StartMessageSubscription;
import org.activiti.api.runtime.model.impl.StartMessageDeploymentDefinitionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartMessageDeploymentDefinitionImplDiffblueTest {
  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl.Builder#build()}
   *   <li>
   * {@link StartMessageDeploymentDefinitionImpl.Builder#withMessageSubscription(StartMessageSubscription)}
   *   <li>
   * {@link StartMessageDeploymentDefinitionImpl.Builder#withProcessDefinition(ProcessDefinition)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  void testBuilderBuild() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageSubscriptionImpl messageEventSubscription = new StartMessageSubscriptionImpl();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(messageEventSubscription);
    ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl();

    // Act
    StartMessageDeploymentDefinitionImpl actualBuildResult = withMessageSubscriptionResult
        .withProcessDefinition(processDefinition)
        .build();

    // Assert
    assertSame(processDefinition, actualBuildResult.getProcessDefinition());
    assertSame(messageEventSubscription, actualBuildResult.getMessageSubscription());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StartMessageDeploymentDefinitionImpl#StartMessageDeploymentDefinitionImpl()}
   *   <li>
   * {@link StartMessageDeploymentDefinitionImpl#builderFrom(StartMessageDeploymentDefinitionImpl)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#toString()}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#getMessageSubscription()}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#getProcessDefinition()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    StartMessageDeploymentDefinitionImpl actualStartMessageDeploymentDefinitionImpl = new StartMessageDeploymentDefinitionImpl();
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl startMessageEventSubscriptionImpl = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();
    actualStartMessageDeploymentDefinitionImpl.builderFrom(startMessageEventSubscriptionImpl);
    String actualToStringResult = actualStartMessageDeploymentDefinitionImpl.toString();
    StartMessageSubscription actualMessageSubscription = actualStartMessageDeploymentDefinitionImpl
        .getMessageSubscription();

    // Assert
    assertEquals("StartMessageDeploymentDefinitionImpl [messageSubscription=null, processDefinition=null]",
        actualToStringResult);
    assertNull(actualStartMessageDeploymentDefinitionImpl.getProcessDefinition());
    assertNull(actualMessageSubscription);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}, and
   * {@link StartMessageDeploymentDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();
    StartMessageDeploymentDefinitionImpl.Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult2 = builderResult2
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult2 = withMessageSubscriptionResult2
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}, and
   * {@link StartMessageDeploymentDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageSubscriptionImpl.Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl messageEventSubscription = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(messageEventSubscription);
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();
    StartMessageDeploymentDefinitionImpl.Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult2 = builderResult2
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult2 = withMessageSubscriptionResult2
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = StartMessageDeploymentDefinitionImpl
        .builder()
        .withMessageSubscription(mock(StartMessageSubscriptionImpl.class));
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult2 = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult2 = withMessageSubscriptionResult2
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl buildResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl())
        .withProcessDefinition(null)
        .build();
    StartMessageDeploymentDefinitionImpl.Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult2
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult2 = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link StartMessageDeploymentDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl.Builder builderResult = StartMessageDeploymentDefinitionImpl.builder();
    StartMessageDeploymentDefinitionImpl.Builder withMessageSubscriptionResult = builderResult
        .withMessageSubscription(new StartMessageSubscriptionImpl());
    StartMessageDeploymentDefinitionImpl buildResult = withMessageSubscriptionResult
        .withProcessDefinition(new ProcessDefinitionImpl())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to StartMessageDeploymentDefinitionImpl");
  }
}
