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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessCreatedEventImplDiffblueTest {
  /**
   * Test {@link ProcessCreatedEventImpl#ProcessCreatedEventImpl(ProcessInstance)}.
   * <p>
   * Method under test: {@link ProcessCreatedEventImpl#ProcessCreatedEventImpl(ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessCreatedEventImpl(ProcessInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessCreatedEventImpl.<init>(ProcessInstance)"})
  void testNewProcessCreatedEventImpl() {
    // Arrange
    ProcessInstanceImpl entity = new ProcessInstanceImpl();

    // Act
    ProcessCreatedEventImpl actualProcessCreatedEventImpl = new ProcessCreatedEventImpl(entity);

    // Assert
    ProcessInstance entity2 = actualProcessCreatedEventImpl.getEntity();
    assertTrue(entity2 instanceof ProcessInstanceImpl);
    assertNull(actualProcessCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCreatedEventImpl.getBusinessKey());
    assertNull(actualProcessCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCreatedEventImpl.getProcessInstanceId());
    assertEquals(ProcessEvents.PROCESS_CREATED, actualProcessCreatedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link ProcessCreatedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ProcessCreatedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProcessRuntimeEvent.ProcessEvents ProcessCreatedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ProcessEvents.PROCESS_CREATED,
        (new ProcessCreatedEventImpl(new ProcessInstanceImpl())).getEventType());
  }
}
