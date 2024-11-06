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

class ProcessStartedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessStartedEventImpl#ProcessStartedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   *   <li>When {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessStartedEventImpl#ProcessStartedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessStartedEventImpl(ProcessInstance); given Date; when ProcessInstanceImpl (default constructor) StartDate is Date")
  void testNewProcessStartedEventImpl_givenDate_whenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act
    ProcessStartedEventImpl actualProcessStartedEventImpl = new ProcessStartedEventImpl(entity);

    // Assert
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessStartedEventImpl.getBusinessKey());
    assertNull(actualProcessStartedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessStartedEventImpl.getProcessInstanceId());
    assertNull(actualProcessStartedEventImpl.getNestedProcessDefinitionId());
    assertNull(actualProcessStartedEventImpl.getNestedProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED, actualProcessStartedEventImpl.getEventType());
    assertSame(entity, actualProcessStartedEventImpl.getEntity());
  }

  /**
   * Test
   * {@link ProcessStartedEventImpl#ProcessStartedEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>When {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessStartedEventImpl#ProcessStartedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessStartedEventImpl(ProcessInstance); when ProcessInstanceImpl (default constructor)")
  void testNewProcessStartedEventImpl_whenProcessInstanceImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessStartedEventImpl actualProcessStartedEventImpl = new ProcessStartedEventImpl(entity);

    // Assert
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessStartedEventImpl.getBusinessKey());
    assertNull(actualProcessStartedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessStartedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessStartedEventImpl.getProcessInstanceId());
    assertNull(actualProcessStartedEventImpl.getNestedProcessDefinitionId());
    assertNull(actualProcessStartedEventImpl.getNestedProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED, actualProcessStartedEventImpl.getEventType());
    assertSame(entity, actualProcessStartedEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessStartedEventImpl#setNestedProcessDefinitionId(String)}
   *   <li>{@link ProcessStartedEventImpl#setNestedProcessInstanceId(String)}
   *   <li>{@link ProcessStartedEventImpl#toString()}
   *   <li>{@link ProcessStartedEventImpl#getNestedProcessDefinitionId()}
   *   <li>{@link ProcessStartedEventImpl#getNestedProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ProcessStartedEventImpl processStartedEventImpl = new ProcessStartedEventImpl(new ProcessInstanceImpl());

    // Act
    processStartedEventImpl.setNestedProcessDefinitionId("42");
    processStartedEventImpl.setNestedProcessInstanceId("42");
    processStartedEventImpl.toString();
    String actualNestedProcessDefinitionId = processStartedEventImpl.getNestedProcessDefinitionId();

    // Assert that nothing has changed
    assertEquals("42", actualNestedProcessDefinitionId);
    assertEquals("42", processStartedEventImpl.getNestedProcessInstanceId());
  }

  /**
   * Test {@link ProcessStartedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessStartedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED,
        (new ProcessStartedEventImpl(new ProcessInstanceImpl())).getEventType());
  }

  /**
   * Test {@link ProcessStartedEventImpl#getEventType()}.
   * <ul>
   *   <li>Given {@link ProcessInstanceImpl} (default constructor) StartDate is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessStartedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType(); given ProcessInstanceImpl (default constructor) StartDate is Date")
  void testGetEventType_givenProcessInstanceImplStartDateIsDate() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    entity.setStartDate(mock(Date.class));

    // Act and Assert
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED,
        (new ProcessStartedEventImpl(entity)).getEventType());
  }
}
