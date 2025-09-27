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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessResumedEventImplDiffblueTest {
  /**
   * Test {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}.
   *
   * <p>Method under test: {@link ProcessResumedEventImpl#ProcessResumedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessResumedEventImpl(ProcessInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessResumedEventImpl.<init>(ProcessInstance)"})
  void testNewProcessResumedEventImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessResumedEventImpl actualProcessResumedEventImpl = new ProcessResumedEventImpl(entity);

    // Assert
    ProcessInstance entity2 = actualProcessResumedEventImpl.getEntity();
    assertTrue(entity2 instanceof ProcessInstanceImpl);
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessResumedEventImpl.getBusinessKey());
    assertNull(actualProcessResumedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessResumedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessResumedEventImpl.getProcessInstanceId());
    assertEquals(ProcessEvents.PROCESS_RESUMED, actualProcessResumedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link ProcessResumedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link ProcessResumedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessRuntimeEvent.ProcessEvents ProcessResumedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessEvents.PROCESS_RESUMED,
        new ProcessResumedEventImpl(new ProcessInstanceImpl()).getEventType());
  }
}
