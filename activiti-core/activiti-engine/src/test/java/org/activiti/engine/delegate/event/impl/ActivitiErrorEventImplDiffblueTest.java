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
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.junit.Test;

public class ActivitiErrorEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiErrorEventImpl_whenEntityCreated_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ActivitiErrorEventImpl actualActivitiErrorEventImpl = new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiErrorEventImpl.getActivityId());
    assertNull(actualActivitiErrorEventImpl.getActivityName());
    assertNull(actualActivitiErrorEventImpl.getActivityType());
    assertNull(actualActivitiErrorEventImpl.getBehaviorClass());
    assertNull(actualActivitiErrorEventImpl.getErrorCode());
    assertNull(actualActivitiErrorEventImpl.getErrorId());
    assertNull(actualActivitiErrorEventImpl.getExecutionId());
    assertNull(actualActivitiErrorEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiErrorEventImpl.getProcessInstanceId());
    assertNull(actualActivitiErrorEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiErrorEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiErrorEventImpl#setErrorCode(String)}
   *   <li>{@link ActivitiErrorEventImpl#setErrorId(String)}
   *   <li>{@link ActivitiErrorEventImpl#getErrorCode()}
   *   <li>{@link ActivitiErrorEventImpl#getErrorId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiErrorEventImpl activitiErrorEventImpl = new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiErrorEventImpl.setErrorCode("An error occurred");
    activitiErrorEventImpl.setErrorId("An error occurred");
    String actualErrorCode = activitiErrorEventImpl.getErrorCode();

    // Assert that nothing has changed
    assertEquals("An error occurred", actualErrorCode);
    assertEquals("An error occurred", activitiErrorEventImpl.getErrorId());
  }
}
