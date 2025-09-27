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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.model.shared.event.VariableEvent;
import org.activiti.api.model.shared.event.VariableEvent.VariableEvents;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VariableCreatedEventImplDiffblueTest {
  /**
   * Test {@link VariableCreatedEventImpl#VariableCreatedEventImpl()}.
   *
   * <p>Method under test: {@link VariableCreatedEventImpl#VariableCreatedEventImpl()}
   */
  @Test
  @DisplayName("Test new VariableCreatedEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableCreatedEventImpl.<init>()"})
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
    assertEquals(VariableEvents.VARIABLE_CREATED, actualVariableCreatedEventImpl.getEventType());
  }

  /**
   * Test {@link VariableCreatedEventImpl#VariableCreatedEventImpl(VariableInstance, String)}.
   *
   * <ul>
   *   <li>Then Entity return {@link VariableInstanceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableCreatedEventImpl#VariableCreatedEventImpl(VariableInstance, String)}
   */
  @Test
  @DisplayName(
      "Test new VariableCreatedEventImpl(VariableInstance, String); then Entity return VariableInstanceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableCreatedEventImpl.<init>(VariableInstance, String)"})
  void testNewVariableCreatedEventImpl_thenEntityReturnVariableInstanceImpl() {
    // Arrange
    VariableInstanceImpl<Object> entity = new VariableInstanceImpl<>();

    // Act
    VariableCreatedEventImpl actualVariableCreatedEventImpl =
        new VariableCreatedEventImpl(entity, "42");

    // Assert
    VariableInstance entity2 = actualVariableCreatedEventImpl.getEntity();
    assertTrue(entity2 instanceof VariableInstanceImpl);
    assertEquals("42", actualVariableCreatedEventImpl.getProcessDefinitionId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionVersion());
    assertNull(actualVariableCreatedEventImpl.getBusinessKey());
    assertNull(actualVariableCreatedEventImpl.getParentProcessInstanceId());
    assertNull(actualVariableCreatedEventImpl.getProcessDefinitionKey());
    assertNull(actualVariableCreatedEventImpl.getProcessInstanceId());
    assertEquals(VariableEvents.VARIABLE_CREATED, actualVariableCreatedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link VariableCreatedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link VariableCreatedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableEvents VariableCreatedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(VariableEvents.VARIABLE_CREATED, new VariableCreatedEventImpl().getEventType());
  }
}
