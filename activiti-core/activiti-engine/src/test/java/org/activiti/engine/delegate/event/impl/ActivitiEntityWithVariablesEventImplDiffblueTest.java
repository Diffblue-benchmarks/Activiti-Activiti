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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ActivitiEntityWithVariablesEventImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiEntityWithVariablesEventImpl#getVariables()}
   *   <li>{@link ActivitiEntityWithVariablesEventImpl#isLocalScope()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiEntityWithVariablesEventImpl activitiEntityWithVariablesEventImpl = new ActivitiEntityWithVariablesEventImpl(
        JSONObject.NULL, new HashMap<>(), true, ActivitiEventType.ENTITY_CREATED);

    // Act
    Map actualVariables = activitiEntityWithVariablesEventImpl.getVariables();

    // Assert
    assertTrue(activitiEntityWithVariablesEventImpl.isLocalScope());
    assertSame(activitiEntityWithVariablesEventImpl.variables, actualVariables);
  }

  /**
   * Method under test:
   * {@link ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object, Map, boolean, ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEntityWithVariablesEventImpl() {
    // Arrange
    Object object = JSONObject.NULL;
    HashMap<Object, Object> variables = new HashMap<>();

    // Act
    ActivitiEntityWithVariablesEventImpl actualActivitiEntityWithVariablesEventImpl = new ActivitiEntityWithVariablesEventImpl(
        object, variables, true, ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiEntityWithVariablesEventImpl.getExecutionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEntityWithVariablesEventImpl.getType());
    Map variables2 = actualActivitiEntityWithVariablesEventImpl.getVariables();
    assertTrue(variables2.isEmpty());
    assertTrue(actualActivitiEntityWithVariablesEventImpl.isLocalScope());
    assertSame(variables, variables2);
    assertSame(object, actualActivitiEntityWithVariablesEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object, Map, boolean, ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEntityWithVariablesEventImpl2() {
    // Arrange
    Object object = JSONObject.NULL;

    HashMap<Object, Object> variables = new HashMap<>();
    variables.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    // Act
    ActivitiEntityWithVariablesEventImpl actualActivitiEntityWithVariablesEventImpl = new ActivitiEntityWithVariablesEventImpl(
        object, variables, true, ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiEntityWithVariablesEventImpl.getExecutionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEntityWithVariablesEventImpl.getType());
    Map variables2 = actualActivitiEntityWithVariablesEventImpl.getVariables();
    assertTrue(variables2.isEmpty());
    assertTrue(actualActivitiEntityWithVariablesEventImpl.isLocalScope());
    assertSame(variables, variables2);
    assertSame(object, actualActivitiEntityWithVariablesEventImpl.getEntity());
  }
}
