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

class ProcessCancelledImplDiffblueTest {
  /**
   * Test
   * {@link ProcessCancelledImpl#ProcessCancelledImpl(ProcessInstance, String)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   *   <li>When {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledImpl#ProcessCancelledImpl(ProcessInstance, String)}
   */
  @Test
  @DisplayName("Test new ProcessCancelledImpl(ProcessInstance, String); given Date; when ProcessInstanceImpl (default constructor) StartDate is Date")
  void testNewProcessCancelledImpl_givenDate_whenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act
    ProcessCancelledImpl actualProcessCancelledImpl = new ProcessCancelledImpl(entity, "Cause");

    // Assert
    assertEquals("Cause", actualProcessCancelledImpl.getCause());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCancelledImpl.getBusinessKey());
    assertNull(actualProcessCancelledImpl.getParentProcessInstanceId());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionId());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionKey());
    assertNull(actualProcessCancelledImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, actualProcessCancelledImpl.getEventType());
    assertSame(entity, actualProcessCancelledImpl.getEntity());
  }

  /**
   * Test
   * {@link ProcessCancelledImpl#ProcessCancelledImpl(ProcessInstance, String)}.
   * <ul>
   *   <li>When {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledImpl#ProcessCancelledImpl(ProcessInstance, String)}
   */
  @Test
  @DisplayName("Test new ProcessCancelledImpl(ProcessInstance, String); when ProcessInstanceImpl (default constructor)")
  void testNewProcessCancelledImpl_whenProcessInstanceImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessCancelledImpl actualProcessCancelledImpl = new ProcessCancelledImpl(entity, "Cause");

    // Assert
    assertEquals("Cause", actualProcessCancelledImpl.getCause());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCancelledImpl.getBusinessKey());
    assertNull(actualProcessCancelledImpl.getParentProcessInstanceId());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionId());
    assertNull(actualProcessCancelledImpl.getProcessDefinitionKey());
    assertNull(actualProcessCancelledImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, actualProcessCancelledImpl.getEventType());
    assertSame(entity, actualProcessCancelledImpl.getEntity());
  }

  /**
   * Test {@link ProcessCancelledImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessCancelledImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED,
        (new ProcessCancelledImpl(new ProcessInstanceImpl(), "Cause")).getEventType());
  }

  /**
   * Test {@link ProcessCancelledImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCancelledImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given ProcessInstanceImpl (default constructor) StartDate is Date")
  void testGetEventType_givenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED,
        (new ProcessCancelledImpl(entity, "Cause")).getEventType());
  }

  /**
   * Test {@link ProcessCancelledImpl#getCause()}.
   * <p>
   * Method under test: {@link ProcessCancelledImpl#getCause()}
   */
  @Test
  @DisplayName("Test getCause()")
  void testGetCause() {
    // Arrange, Act and Assert
    assertEquals("Cause", (new ProcessCancelledImpl(new ProcessInstanceImpl(), "Cause")).getCause());
  }
}
