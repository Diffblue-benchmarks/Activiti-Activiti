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
import org.activiti.api.process.model.StartMessageDeploymentDefinition;
import org.activiti.api.process.model.events.MessageDefinitionEvent;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartMessageDeployedEventImplDiffblueTest {
  /**
   * Test {@link StartMessageDeployedEventImpl#builder()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageDeployedEventImpl#builder()}
   *   <li>
   * {@link StartMessageDeployedEventImpl#withEntity(StartMessageDeploymentDefinition)}
   * </ul>
   */
  @Test
  @DisplayName("Test builder()")
  void testBuilder() {
    // Arrange and Act
    StartMessageDeployedEventImpl.Builder actualBuilderResult = StartMessageDeployedEventImpl.builder();

    // Assert
    assertSame(actualBuilderResult, actualBuilderResult.withEntity(mock(StartMessageDeploymentDefinition.class)));
  }

  /**
   * Test {@link StartMessageDeployedEventImpl#StartMessageDeployedEventImpl()}.
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventImpl#StartMessageDeployedEventImpl()}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventImpl()")
  void testNewStartMessageDeployedEventImpl() {
    // Arrange and Act
    StartMessageDeployedEventImpl actualStartMessageDeployedEventImpl = new StartMessageDeployedEventImpl();

    // Assert
    assertNull(actualStartMessageDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualStartMessageDeployedEventImpl.getBusinessKey());
    assertNull(actualStartMessageDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualStartMessageDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualStartMessageDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualStartMessageDeployedEventImpl.getProcessInstanceId());
    assertNull(actualStartMessageDeployedEventImpl.getEntity());
    assertEquals(MessageDefinitionEvent.MessageDefinitionEvents.START_MESSAGE_DEPLOYED,
        actualStartMessageDeployedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link StartMessageDeployedEventImpl#StartMessageDeployedEventImpl(StartMessageDeploymentDefinition)}.
   * <ul>
   *   <li>Then return BusinessKey is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventImpl#StartMessageDeployedEventImpl(StartMessageDeploymentDefinition)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventImpl(StartMessageDeploymentDefinition); then return BusinessKey is 'null'")
  void testNewStartMessageDeployedEventImpl_thenReturnBusinessKeyIsNull() {
    // Arrange
    StartMessageDeploymentDefinition startMessageEventSubscription = mock(StartMessageDeploymentDefinition.class);
    when(startMessageEventSubscription.getProcessDefinition()).thenReturn(new ProcessDefinitionImpl());

    // Act
    StartMessageDeployedEventImpl actualStartMessageDeployedEventImpl = new StartMessageDeployedEventImpl(
        startMessageEventSubscription);

    // Assert
    verify(startMessageEventSubscription).getProcessDefinition();
    assertNull(actualStartMessageDeployedEventImpl.getBusinessKey());
    assertNull(actualStartMessageDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualStartMessageDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualStartMessageDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualStartMessageDeployedEventImpl.getProcessInstanceId());
    assertEquals(0, actualStartMessageDeployedEventImpl.getProcessDefinitionVersion().intValue());
    assertEquals(MessageDefinitionEvent.MessageDefinitionEvents.START_MESSAGE_DEPLOYED,
        actualStartMessageDeployedEventImpl.getEventType());
    assertSame(startMessageEventSubscription, actualStartMessageDeployedEventImpl.getEntity());
  }
}
