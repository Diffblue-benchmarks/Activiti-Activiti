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
import org.activiti.api.process.model.ProcessCandidateStarterUser;
import org.activiti.api.process.model.events.ProcessCandidateStarterUserEvent;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterUserImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterUserAddedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessCandidateStarterUserAddedEventImpl#ProcessCandidateStarterUserAddedEventImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserAddedEventImpl#ProcessCandidateStarterUserAddedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterUserAddedEventImpl()")
  void testNewProcessCandidateStarterUserAddedEventImpl() {
    // Arrange and Act
    ProcessCandidateStarterUserAddedEventImpl actualProcessCandidateStarterUserAddedEventImpl = new ProcessCandidateStarterUserAddedEventImpl();

    // Assert
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getEntity());
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_ADDED,
        actualProcessCandidateStarterUserAddedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link ProcessCandidateStarterUserAddedEventImpl#ProcessCandidateStarterUserAddedEventImpl(ProcessCandidateStarterUser)}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserAddedEventImpl#ProcessCandidateStarterUserAddedEventImpl(ProcessCandidateStarterUser)}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterUserAddedEventImpl(ProcessCandidateStarterUser)")
  void testNewProcessCandidateStarterUserAddedEventImpl2() {
    // Arrange
    ProcessCandidateStarterUserImpl entity = new ProcessCandidateStarterUserImpl("42", "42");

    // Act
    ProcessCandidateStarterUserAddedEventImpl actualProcessCandidateStarterUserAddedEventImpl = new ProcessCandidateStarterUserAddedEventImpl(
        entity);

    // Assert
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterUserAddedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_ADDED,
        actualProcessCandidateStarterUserAddedEventImpl.getEventType());
    assertSame(entity, actualProcessCandidateStarterUserAddedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessCandidateStarterUserAddedEventImpl#getEventType()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserAddedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_ADDED,
        (new ProcessCandidateStarterUserAddedEventImpl()).getEventType());
  }
}
