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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.junit.jupiter.api.Test;

class ProcessSuspendedEventImplDiffblueTest {
  /**
   * Method under test: {@link ProcessSuspendedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED,
        (new ProcessSuspendedEventImpl(new ProcessInstanceImpl())).getEventType());
  }

  /**
   * Method under test: {@link ProcessSuspendedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType2() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED,
        (new ProcessSuspendedEventImpl(entity)).getEventType());
  }

  /**
   * Method under test:
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}
   */
  @Test
  void testNewProcessSuspendedEventImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessSuspendedEventImpl actualProcessSuspendedEventImpl = new ProcessSuspendedEventImpl(entity);

    // Assert
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessSuspendedEventImpl.getBusinessKey());
    assertNull(actualProcessSuspendedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessSuspendedEventImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED, actualProcessSuspendedEventImpl.getEventType());
    assertSame(entity, actualProcessSuspendedEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}
   */
  @Test
  void testNewProcessSuspendedEventImpl2() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act
    ProcessSuspendedEventImpl actualProcessSuspendedEventImpl = new ProcessSuspendedEventImpl(entity);

    // Assert
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessSuspendedEventImpl.getBusinessKey());
    assertNull(actualProcessSuspendedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessSuspendedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessSuspendedEventImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED, actualProcessSuspendedEventImpl.getEventType());
    assertSame(entity, actualProcessSuspendedEventImpl.getEntity());
  }
}
