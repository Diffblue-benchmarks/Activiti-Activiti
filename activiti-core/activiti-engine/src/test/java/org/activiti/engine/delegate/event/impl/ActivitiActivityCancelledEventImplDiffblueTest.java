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

public class ActivitiActivityCancelledEventImplDiffblueTest {
  /**
   * Test new {@link ActivitiActivityCancelledEventImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ActivitiActivityCancelledEventImpl}
   */
  @Test
  public void testNewActivitiActivityCancelledEventImpl() {
    // Arrange and Act
    ActivitiActivityCancelledEventImpl actualActivitiActivityCancelledEventImpl = new ActivitiActivityCancelledEventImpl();

    // Assert
    assertNull(actualActivitiActivityCancelledEventImpl.getCause());
    assertNull(actualActivitiActivityCancelledEventImpl.getActivityId());
    assertNull(actualActivitiActivityCancelledEventImpl.getActivityName());
    assertNull(actualActivitiActivityCancelledEventImpl.getActivityType());
    assertNull(actualActivitiActivityCancelledEventImpl.getBehaviorClass());
    assertNull(actualActivitiActivityCancelledEventImpl.getExecutionId());
    assertNull(actualActivitiActivityCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiActivityCancelledEventImpl.getProcessInstanceId());
    assertNull(actualActivitiActivityCancelledEventImpl.getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, actualActivitiActivityCancelledEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiActivityCancelledEventImpl#setCause(Object)}
   *   <li>{@link ActivitiActivityCancelledEventImpl#getCause()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiActivityCancelledEventImpl activitiActivityCancelledEventImpl = new ActivitiActivityCancelledEventImpl();
    Object object = JSONObject.NULL;

    // Act
    activitiActivityCancelledEventImpl.setCause(object);

    // Assert that nothing has changed
    assertSame(object, activitiActivityCancelledEventImpl.getCause());
  }
}
