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
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.events.ProcessDefinitionEvent;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessDeployedEventImplDiffblueTest {
  /**
   * Test {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessDeployedEventImpl()")
  void testNewProcessDeployedEventImpl() {
    // Arrange and Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl = new ProcessDeployedEventImpl();

    // Assert
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getEntity());
    assertEquals(ProcessDefinitionEvent.ProcessDefinitionEvents.PROCESS_DEPLOYED,
        actualProcessDeployedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition, String)}
   */
  @Test
  @DisplayName("Test new ProcessDeployedEventImpl(ProcessDefinition, String)")
  void testNewProcessDeployedEventImpl2() {
    // Arrange
    ProcessDefinitionImpl entity = new ProcessDefinitionImpl();

    // Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl = new ProcessDeployedEventImpl(entity,
        "Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertEquals(ProcessDefinitionEvent.ProcessDefinitionEvents.PROCESS_DEPLOYED,
        actualProcessDeployedEventImpl.getEventType());
    assertSame(entity, actualProcessDeployedEventImpl.getEntity());
  }

  /**
   * Test
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition)}.
   * <ul>
   *   <li>Then return ProcessDefinitionVersion is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test new ProcessDeployedEventImpl(ProcessDefinition); then return ProcessDefinitionVersion is 'null'")
  void testNewProcessDeployedEventImpl_thenReturnProcessDefinitionVersionIsNull() {
    // Arrange
    ProcessDefinitionImpl entity = new ProcessDefinitionImpl();

    // Act
    ProcessDeployedEventImpl actualProcessDeployedEventImpl = new ProcessDeployedEventImpl(entity);

    // Assert
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessDeployedEventImpl.getProcessModelContent());
    assertNull(actualProcessDeployedEventImpl.getBusinessKey());
    assertNull(actualProcessDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessDeployedEventImpl.getProcessInstanceId());
    assertEquals(ProcessDefinitionEvent.ProcessDefinitionEvents.PROCESS_DEPLOYED,
        actualProcessDeployedEventImpl.getEventType());
    assertSame(entity, actualProcessDeployedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessDeployedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessDeployedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessDefinitionEvent.ProcessDefinitionEvents.PROCESS_DEPLOYED,
        (new ProcessDeployedEventImpl()).getEventType());
  }

  /**
   * Test {@link ProcessDeployedEventImpl#getProcessModelContent()}.
   * <p>
   * Method under test: {@link ProcessDeployedEventImpl#getProcessModelContent()}
   */
  @Test
  @DisplayName("Test getProcessModelContent()")
  void testGetProcessModelContent() {
    // Arrange, Act and Assert
    assertNull((new ProcessDeployedEventImpl()).getProcessModelContent());
  }
}
