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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.model.shared.event.VariableEvent;
import org.activiti.api.model.shared.event.VariableEvent.VariableEvents;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.runtime.event.impl.VariableUpdatedEventImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiVariableUpdatedEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToVariableUpdatedConverterDiffblueTest {
  /**
   * Test {@link ToVariableUpdatedConverter#from(ActivitiVariableUpdatedEvent)} with {@code
   * ActivitiVariableUpdatedEvent}.
   *
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link VariableUpdatedEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ToVariableUpdatedConverter#from(ActivitiVariableUpdatedEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiVariableUpdatedEvent) with 'ActivitiVariableUpdatedEvent'; then get() return VariableUpdatedEventImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToVariableUpdatedConverter.from(ActivitiVariableUpdatedEvent)"})
  void testFromWithActivitiVariableUpdatedEvent_thenGetReturnVariableUpdatedEventImpl() {
    // Arrange
    ToVariableUpdatedConverter toVariableUpdatedConverter = new ToVariableUpdatedConverter();

    ActivitiVariableUpdatedEventImpl internalEvent = new ActivitiVariableUpdatedEventImpl();
    internalEvent.setVariableType(new BigDecimalType());

    // Act
    Optional<VariableUpdatedEvent> actualFromResult =
        toVariableUpdatedConverter.from(internalEvent);

    // Assert
    VariableUpdatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof VariableUpdatedEventImpl);
    assertTrue(getResult.getEntity() instanceof VariableInstanceImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getPreviousValue());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(VariableEvents.VARIABLE_UPDATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
