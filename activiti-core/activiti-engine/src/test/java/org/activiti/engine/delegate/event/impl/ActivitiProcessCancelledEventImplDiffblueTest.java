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
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ActivitiProcessCancelledEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}.
   * <p>
   * Method under test:
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   */
  @Test
  public void testNewActivitiProcessCancelledEventImpl() {
    // Arrange
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertSame(processInstance, (new ActivitiProcessCancelledEventImpl(processInstance)).getEntity());
  }

  /**
   * Test
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}.
   * <ul>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   */
  @Test
  public void testNewActivitiProcessCancelledEventImpl_thenReturnCauseIsNull() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);

    // Act
    ActivitiProcessCancelledEventImpl actualActivitiProcessCancelledEventImpl = new ActivitiProcessCancelledEventImpl(
        processInstance);

    // Assert
    assertNull(actualActivitiProcessCancelledEventImpl.getCause());
    assertNull(actualActivitiProcessCancelledEventImpl.getExecutionId());
    assertNull(actualActivitiProcessCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiProcessCancelledEventImpl.getProcessInstanceId());
    assertNull(actualActivitiProcessCancelledEventImpl.getReason());
    assertEquals(ActivitiEventType.PROCESS_CANCELLED, actualActivitiProcessCancelledEventImpl.getType());
    assertSame(processInstance, actualActivitiProcessCancelledEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiProcessCancelledEventImpl#setCause(Object)}
   *   <li>{@link ActivitiProcessCancelledEventImpl#getCause()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiProcessCancelledEventImpl activitiProcessCancelledEventImpl = new ActivitiProcessCancelledEventImpl(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    Object object = JSONObject.NULL;

    // Act
    activitiProcessCancelledEventImpl.setCause(object);

    // Assert that nothing has changed
    assertSame(object, activitiProcessCancelledEventImpl.getCause());
  }
}
