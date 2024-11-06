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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessSuspendedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessSuspendedEventImpl(ProcessInstance); given Date")
  void testNewProcessSuspendedEventImpl_givenDate() {
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

  /**
   * Test
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>When {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSuspendedEventImpl#ProcessSuspendedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessSuspendedEventImpl(ProcessInstance); when ProcessInstanceImpl (default constructor)")
  void testNewProcessSuspendedEventImpl_whenProcessInstanceImpl() {
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
   * Test {@link ProcessSuspendedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessSuspendedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED,
        (new ProcessSuspendedEventImpl(new ProcessInstanceImpl())).getEventType());
  }

  /**
   * Test {@link ProcessSuspendedEventImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessSuspendedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given ProcessInstanceImpl (default constructor) StartDate is Date")
  void testGetEventType_givenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED,
        (new ProcessSuspendedEventImpl(entity)).getEventType());
  }
}
