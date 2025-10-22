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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.model.shared.event.VariableEvent;
import org.activiti.api.model.shared.event.VariableEvent.VariableEvents;
import org.activiti.api.runtime.event.impl.VariableCreatedEventImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToVariableCreatedConverterDiffblueTest {
  /**
   * Test {@link ToVariableCreatedConverter#from(ActivitiVariableEvent)} with {@code ActivitiVariableEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link VariableCreatedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToVariableCreatedConverter#from(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiVariableEvent) with 'ActivitiVariableEvent'; then get() return VariableCreatedEventImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToVariableCreatedConverter.from(ActivitiVariableEvent)"})
  void testFromWithActivitiVariableEvent_thenGetReturnVariableCreatedEventImpl() {
    // Arrange
    ToVariableCreatedConverter toVariableCreatedConverter = new ToVariableCreatedConverter();

    ActivitiVariableUpdatedEventImpl internalEvent = new ActivitiVariableUpdatedEventImpl();
    internalEvent.setVariableType(new BigDecimalType());

    // Act
    Optional<VariableCreatedEvent> actualFromResult = toVariableCreatedConverter.from(internalEvent);

    // Assert
    VariableCreatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof VariableCreatedEventImpl);
    assertTrue(getResult.getEntity() instanceof VariableInstanceImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(VariableEvents.VARIABLE_CREATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
