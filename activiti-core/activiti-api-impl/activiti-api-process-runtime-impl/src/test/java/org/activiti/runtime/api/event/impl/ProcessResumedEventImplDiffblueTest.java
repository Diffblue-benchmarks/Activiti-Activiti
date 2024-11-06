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

class ProcessResumedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   *   <li>When {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessResumedEventImpl(ProcessInstance); given Date; when ProcessInstanceImpl (default constructor) StartDate is Date")
  void testNewProcessResumedEventImpl_givenDate_whenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act
    ProcessResumedEventImpl actualProcessResumedEventImpl = new ProcessResumedEventImpl(entity);

    // Assert
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessResumedEventImpl.getBusinessKey());
    assertNull(actualProcessResumedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessResumedEventImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED, actualProcessResumedEventImpl.getEventType());
    assertSame(entity, actualProcessResumedEventImpl.getEntity());
  }

  /**
   * Test
   * {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>When {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessResumedEventImpl(ProcessInstance); when ProcessInstanceImpl (default constructor)")
  void testNewProcessResumedEventImpl_whenProcessInstanceImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessResumedEventImpl actualProcessResumedEventImpl = new ProcessResumedEventImpl(entity);

    // Assert
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessResumedEventImpl.getBusinessKey());
    assertNull(actualProcessResumedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessResumedEventImpl.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED, actualProcessResumedEventImpl.getEventType());
    assertSame(entity, actualProcessResumedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessResumedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessResumedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED,
        (new ProcessResumedEventImpl(new ProcessInstanceImpl())).getEventType());
  }

  /**
   * Test {@link ProcessResumedEventImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessResumedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given ProcessInstanceImpl (default constructor) StartDate is Date")
  void testGetEventType_givenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED,
        (new ProcessResumedEventImpl(entity)).getEventType());
  }
}
