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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.model.shared.event.VariableEvent;
import org.activiti.api.model.shared.event.VariableEvent.VariableEvents;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VariableUpdatedEventImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link VariableUpdatedEventImpl#VariableUpdatedEventImpl(VariableInstance, Object)}
   *   <li>{@link VariableUpdatedEventImpl#getPreviousValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableUpdatedEventImpl.<init>(VariableInstance, Object)",
    "Object VariableUpdatedEventImpl.getPreviousValue()"
  })
  void testGettersAndSetters() {
    // Arrange
    VariableInstanceImpl<Object> entity = new VariableInstanceImpl<>();

    // Act
    VariableUpdatedEventImpl<Object> actualVariableUpdatedEventImpl =
        new VariableUpdatedEventImpl<>(entity, "Previous Value");

    // Assert
    assertEquals("Previous Value", actualVariableUpdatedEventImpl.getPreviousValue());
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableUpdatedEventImpl.getBusinessKey());
    assertNull(actualVariableUpdatedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableUpdatedEventImpl.getProcessInstanceId());
    assertSame(entity, actualVariableUpdatedEventImpl.getEntity());
  }

  /**
   * Test {@link VariableUpdatedEventImpl#VariableUpdatedEventImpl()}.
   *
   * <p>Method under test: {@link VariableUpdatedEventImpl#VariableUpdatedEventImpl()}
   */
  @Test
  @DisplayName("Test new VariableUpdatedEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableUpdatedEventImpl.<init>()"})
  void testNewVariableUpdatedEventImpl() {
    // Arrange and Act
    VariableUpdatedEventImpl<Object> actualVariableUpdatedEventImpl =
        new VariableUpdatedEventImpl<>();

    // Assert
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableUpdatedEventImpl.getPreviousValue());
    assertNull(actualVariableUpdatedEventImpl.getBusinessKey());
    assertNull(actualVariableUpdatedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableUpdatedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableUpdatedEventImpl.getProcessInstanceId());
    assertNull(actualVariableUpdatedEventImpl.getEntity());
    assertEquals(VariableEvents.VARIABLE_UPDATED, actualVariableUpdatedEventImpl.getEventType());
  }

  /**
   * Test {@link VariableUpdatedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link VariableUpdatedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableEvents VariableUpdatedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange
    VariableUpdatedEventImpl<Object> variableUpdatedEventImpl = new VariableUpdatedEventImpl<>();

    // Act and Assert
    assertEquals(VariableEvents.VARIABLE_UPDATED, variableUpdatedEventImpl.getEventType());
  }
}
