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
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ActivitiProcessStartedEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}.
   * <p>
   * Method under test:
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  public void testNewActivitiProcessStartedEventImpl() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertSame(createWithEmptyRelationshipCollectionsResult,
        (new ActivitiProcessStartedEventImpl(createWithEmptyRelationshipCollectionsResult, new HashMap<>(), true))
            .getEntity());
  }

  /**
   * Test
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  public void testNewActivitiProcessStartedEventImpl_givenNull() {
    // Arrange
    Object object = JSONObject.NULL;

    HashMap<Object, Object> variables = new HashMap<>();
    variables.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    // Act
    ActivitiProcessStartedEventImpl actualActivitiProcessStartedEventImpl = new ActivitiProcessStartedEventImpl(object,
        variables, true);

    // Assert
    assertNull(actualActivitiProcessStartedEventImpl.getExecutionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessInstanceId());
    assertNull(actualActivitiProcessStartedEventImpl.getReason());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessInstanceId());
    assertEquals(ActivitiEventType.PROCESS_STARTED, actualActivitiProcessStartedEventImpl.getType());
    assertTrue(actualActivitiProcessStartedEventImpl.isLocalScope());
    assertSame(variables, actualActivitiProcessStartedEventImpl.getVariables());
    assertSame(object, actualActivitiProcessStartedEventImpl.getEntity());
  }

  /**
   * Test
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  public void testNewActivitiProcessStartedEventImpl_whenNull_thenReturnExecutionIdIsNull() {
    // Arrange
    Object object = JSONObject.NULL;
    HashMap<Object, Object> variables = new HashMap<>();

    // Act
    ActivitiProcessStartedEventImpl actualActivitiProcessStartedEventImpl = new ActivitiProcessStartedEventImpl(object,
        variables, true);

    // Assert
    assertNull(actualActivitiProcessStartedEventImpl.getExecutionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessInstanceId());
    assertNull(actualActivitiProcessStartedEventImpl.getReason());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessInstanceId());
    assertEquals(ActivitiEventType.PROCESS_STARTED, actualActivitiProcessStartedEventImpl.getType());
    assertTrue(actualActivitiProcessStartedEventImpl.isLocalScope());
    assertSame(variables, actualActivitiProcessStartedEventImpl.getVariables());
    assertSame(object, actualActivitiProcessStartedEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiProcessStartedEventImpl#getNestedProcessDefinitionId()}
   *   <li>{@link ActivitiProcessStartedEventImpl#getNestedProcessInstanceId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiProcessStartedEventImpl activitiProcessStartedEventImpl = new ActivitiProcessStartedEventImpl(
        JSONObject.NULL, new HashMap<>(), true);

    // Act
    String actualNestedProcessDefinitionId = activitiProcessStartedEventImpl.getNestedProcessDefinitionId();

    // Assert
    assertNull(actualNestedProcessDefinitionId);
    assertNull(activitiProcessStartedEventImpl.getNestedProcessInstanceId());
  }
}
