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
import org.junit.jupiter.api.Test;

class ProcessDeployedEventImplDiffblueTest {
  /**
   * Method under test: {@link ProcessDeployedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessDefinitionEvent.ProcessDefinitionEvents.PROCESS_DEPLOYED,
        (new ProcessDeployedEventImpl()).getEventType());
  }

  /**
   * Method under test: {@link ProcessDeployedEventImpl#getProcessModelContent()}
   */
  @Test
  void testGetProcessModelContent() {
    // Arrange, Act and Assert
    assertNull((new ProcessDeployedEventImpl()).getProcessModelContent());
  }

  /**
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl()}
   */
  @Test
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
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition)}
   */
  @Test
  void testNewProcessDeployedEventImpl2() {
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
   * Method under test:
   * {@link ProcessDeployedEventImpl#ProcessDeployedEventImpl(ProcessDefinition, String)}
   */
  @Test
  void testNewProcessDeployedEventImpl3() {
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
}
