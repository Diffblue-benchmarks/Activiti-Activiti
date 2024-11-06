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
import org.junit.Test;

public class ActivitiSignalEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return SignalData is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiSignalEventImpl_whenEntityCreated_thenReturnSignalDataIsNull() {
    // Arrange and Act
    ActivitiSignalEventImpl actualActivitiSignalEventImpl = new ActivitiSignalEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiSignalEventImpl.getSignalData());
    assertNull(actualActivitiSignalEventImpl.getActivityId());
    assertNull(actualActivitiSignalEventImpl.getActivityName());
    assertNull(actualActivitiSignalEventImpl.getActivityType());
    assertNull(actualActivitiSignalEventImpl.getBehaviorClass());
    assertNull(actualActivitiSignalEventImpl.getExecutionId());
    assertNull(actualActivitiSignalEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiSignalEventImpl.getProcessInstanceId());
    assertNull(actualActivitiSignalEventImpl.getReason());
    assertNull(actualActivitiSignalEventImpl.getSignalName());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiSignalEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiSignalEventImpl#setSignalData(Object)}
   *   <li>{@link ActivitiSignalEventImpl#setSignalName(String)}
   *   <li>{@link ActivitiSignalEventImpl#getSignalData()}
   *   <li>{@link ActivitiSignalEventImpl#getSignalName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiSignalEventImpl activitiSignalEventImpl = new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    Object object = JSONObject.NULL;

    // Act
    activitiSignalEventImpl.setSignalData(object);
    activitiSignalEventImpl.setSignalName("Signal Name");
    Object actualSignalData = activitiSignalEventImpl.getSignalData();

    // Assert that nothing has changed
    assertEquals("Signal Name", activitiSignalEventImpl.getSignalName());
    assertSame(object, actualSignalData);
  }
}
