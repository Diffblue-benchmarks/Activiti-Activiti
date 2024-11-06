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
package org.activiti.engine.delegate.event.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;

public class ActivitiVariableEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiVariableEventImpl#ActivitiVariableEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return VariableValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiVariableEventImpl#ActivitiVariableEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiVariableEventImpl_whenEntityCreated_thenReturnVariableValueIsNull() {
    // Arrange and Act
    ActivitiVariableEventImpl actualActivitiVariableEventImpl = new ActivitiVariableEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiVariableEventImpl.getVariableValue());
    assertNull(actualActivitiVariableEventImpl.getExecutionId());
    assertNull(actualActivitiVariableEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiVariableEventImpl.getProcessInstanceId());
    assertNull(actualActivitiVariableEventImpl.getReason());
    assertNull(actualActivitiVariableEventImpl.getTaskId());
    assertNull(actualActivitiVariableEventImpl.getVariableName());
    assertNull(actualActivitiVariableEventImpl.getVariableType());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiVariableEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiVariableEventImpl#setTaskId(String)}
   *   <li>{@link ActivitiVariableEventImpl#setVariableName(String)}
   *   <li>{@link ActivitiVariableEventImpl#setVariableType(VariableType)}
   *   <li>{@link ActivitiVariableEventImpl#setVariableValue(Object)}
   *   <li>{@link ActivitiVariableEventImpl#getTaskId()}
   *   <li>{@link ActivitiVariableEventImpl#getVariableName()}
   *   <li>{@link ActivitiVariableEventImpl#getVariableType()}
   *   <li>{@link ActivitiVariableEventImpl#getVariableValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiVariableEventImpl activitiVariableEventImpl = new ActivitiVariableEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiVariableEventImpl.setTaskId("42");
    activitiVariableEventImpl.setVariableName("Variable Name");
    BigDecimalType variableType = new BigDecimalType();
    activitiVariableEventImpl.setVariableType(variableType);
    Object object = JSONObject.NULL;
    activitiVariableEventImpl.setVariableValue(object);
    String actualTaskId = activitiVariableEventImpl.getTaskId();
    String actualVariableName = activitiVariableEventImpl.getVariableName();
    VariableType actualVariableType = activitiVariableEventImpl.getVariableType();

    // Assert that nothing has changed
    assertEquals("42", actualTaskId);
    assertEquals("Variable Name", actualVariableName);
    assertSame(variableType, actualVariableType);
    assertSame(object, activitiVariableEventImpl.getVariableValue());
  }
}
