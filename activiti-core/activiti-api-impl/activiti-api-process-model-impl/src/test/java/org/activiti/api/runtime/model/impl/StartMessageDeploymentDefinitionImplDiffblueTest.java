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
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.StartMessageSubscription;
import org.junit.jupiter.api.Test;

class StartMessageDeploymentDefinitionImplDiffblueTest {
  /**
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
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl = new StartMessageDeploymentDefinitionImpl();
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl2 = new StartMessageDeploymentDefinitionImpl();

    // Act and Assert
    assertEquals(startMessageDeploymentDefinitionImpl, startMessageDeploymentDefinitionImpl2);
    int expectedHashCodeResult = startMessageDeploymentDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, startMessageDeploymentDefinitionImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   *   <li>{@link StartMessageDeploymentDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StartMessageDeploymentDefinitionImpl startMessageDeploymentDefinitionImpl = new StartMessageDeploymentDefinitionImpl();

    // Act and Assert
    assertEquals(startMessageDeploymentDefinitionImpl, startMessageDeploymentDefinitionImpl);
    int expectedHashCodeResult = startMessageDeploymentDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, startMessageDeploymentDefinitionImpl.hashCode());
  }

  /**
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageDeploymentDefinitionImpl(), 1);
  }

  /**
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageDeploymentDefinitionImpl(), null);
  }

  /**
   * Method under test:
   * {@link StartMessageDeploymentDefinitionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageDeploymentDefinitionImpl(),
        "Different type to StartMessageDeploymentDefinitionImpl");
  }

  /**
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
  void testGettersAndSetters() {
    // Arrange and Act
    StartMessageDeploymentDefinitionImpl actualStartMessageDeploymentDefinitionImpl = new StartMessageDeploymentDefinitionImpl();
    actualStartMessageDeploymentDefinitionImpl.builderFrom(new StartMessageDeploymentDefinitionImpl());
    String actualToStringResult = actualStartMessageDeploymentDefinitionImpl.toString();
    StartMessageSubscription actualMessageSubscription = actualStartMessageDeploymentDefinitionImpl
        .getMessageSubscription();

    // Assert
    assertEquals("StartMessageDeploymentDefinitionImpl [messageSubscription=null, processDefinition=null]",
        actualToStringResult);
    assertNull(actualStartMessageDeploymentDefinitionImpl.getProcessDefinition());
    assertNull(actualMessageSubscription);
  }
}
