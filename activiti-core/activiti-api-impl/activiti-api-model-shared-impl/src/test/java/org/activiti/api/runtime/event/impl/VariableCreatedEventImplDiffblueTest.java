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
import org.activiti.api.model.shared.event.VariableEvent;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableCreatedEventImplDiffblueTest {
  /**
   * Test {@link VariableCreatedEventImpl#VariableCreatedEventImpl()}.
   * <p>
   * Method under test:
   * {@link VariableCreatedEventImpl#VariableCreatedEventImpl()}
   */
  @Test
  @DisplayName("Test new VariableCreatedEventImpl()")
  void testNewVariableCreatedEventImpl() {
    // Arrange and Act
    VariableCreatedEventImpl actualVariableCreatedEventImpl = new VariableCreatedEventImpl();

    // Assert
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableCreatedEventImpl.getBusinessKey());
    assertNull(actualVariableCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableCreatedEventImpl.getProcessInstanceId());
    assertNull(actualVariableCreatedEventImpl.getEntity());
    assertEquals(VariableEvent.VariableEvents.VARIABLE_CREATED, actualVariableCreatedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link VariableCreatedEventImpl#VariableCreatedEventImpl(VariableInstance, String)}.
   * <ul>
   *   <li>Then return ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableCreatedEventImpl#VariableCreatedEventImpl(VariableInstance, String)}
   */
  @Test
  @DisplayName("Test new VariableCreatedEventImpl(VariableInstance, String); then return ProcessDefinitionId is '42'")
  void testNewVariableCreatedEventImpl_thenReturnProcessDefinitionIdIs42() {
    // Arrange
    VariableInstanceImpl<Object> entity = new VariableInstanceImpl<>();

    // Act
    VariableCreatedEventImpl actualVariableCreatedEventImpl = new VariableCreatedEventImpl(entity, "42");

    // Assert
    assertEquals("42", actualVariableCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableCreatedEventImpl.getBusinessKey());
    assertNull(actualVariableCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableCreatedEventImpl.getProcessInstanceId());
    assertEquals(VariableEvent.VariableEvents.VARIABLE_CREATED, actualVariableCreatedEventImpl.getEventType());
    assertSame(entity, actualVariableCreatedEventImpl.getEntity());
  }

  /**
   * Test {@link VariableCreatedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link VariableCreatedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(VariableEvent.VariableEvents.VARIABLE_CREATED, (new VariableCreatedEventImpl()).getEventType());
  }
}
