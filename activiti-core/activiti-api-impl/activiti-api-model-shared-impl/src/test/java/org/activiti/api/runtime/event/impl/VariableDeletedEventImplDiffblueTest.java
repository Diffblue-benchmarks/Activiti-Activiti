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

class VariableDeletedEventImplDiffblueTest {
  /**
   * Test {@link VariableDeletedEventImpl#VariableDeletedEventImpl()}.
   * <p>
   * Method under test:
   * {@link VariableDeletedEventImpl#VariableDeletedEventImpl()}
   */
  @Test
  @DisplayName("Test new VariableDeletedEventImpl()")
  void testNewVariableDeletedEventImpl() {
    // Arrange and Act
    VariableDeletedEventImpl actualVariableDeletedEventImpl = new VariableDeletedEventImpl();

    // Assert
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableDeletedEventImpl.getBusinessKey());
    assertNull(actualVariableDeletedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableDeletedEventImpl.getProcessInstanceId());
    assertNull(actualVariableDeletedEventImpl.getEntity());
    assertEquals(VariableEvent.VariableEvents.VARIABLE_DELETED, actualVariableDeletedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link VariableDeletedEventImpl#VariableDeletedEventImpl(VariableInstance)}.
   * <p>
   * Method under test:
   * {@link VariableDeletedEventImpl#VariableDeletedEventImpl(VariableInstance)}
   */
  @Test
  @DisplayName("Test new VariableDeletedEventImpl(VariableInstance)")
  void testNewVariableDeletedEventImpl2() {
    // Arrange
    VariableInstanceImpl<Object> entity = new VariableInstanceImpl<>();

    // Act
    VariableDeletedEventImpl actualVariableDeletedEventImpl = new VariableDeletedEventImpl(entity);

    // Assert
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableDeletedEventImpl.getBusinessKey());
    assertNull(actualVariableDeletedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableDeletedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableDeletedEventImpl.getProcessInstanceId());
    assertEquals(VariableEvent.VariableEvents.VARIABLE_DELETED, actualVariableDeletedEventImpl.getEventType());
    assertSame(entity, actualVariableDeletedEventImpl.getEntity());
  }

  /**
   * Test {@link VariableDeletedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link VariableDeletedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(VariableEvent.VariableEvents.VARIABLE_DELETED, (new VariableDeletedEventImpl()).getEventType());
  }
}
