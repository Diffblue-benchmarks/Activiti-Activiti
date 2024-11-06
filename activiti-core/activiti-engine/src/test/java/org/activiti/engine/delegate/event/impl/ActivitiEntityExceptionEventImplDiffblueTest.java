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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ActivitiEntityExceptionEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiEntityExceptionEventImpl#ActivitiEntityExceptionEventImpl(Object, ActivitiEventType, Throwable)}.
   * <p>
   * Method under test:
   * {@link ActivitiEntityExceptionEventImpl#ActivitiEntityExceptionEventImpl(Object, ActivitiEventType, Throwable)}
   */
  @Test
  public void testNewActivitiEntityExceptionEventImpl() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new ActivitiEntityExceptionEventImpl(null, ActivitiEventType.ENTITY_CREATED, new Throwable()));

  }

  /**
   * Test
   * {@link ActivitiEntityExceptionEventImpl#ActivitiEntityExceptionEventImpl(Object, ActivitiEventType, Throwable)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEntityExceptionEventImpl#ActivitiEntityExceptionEventImpl(Object, ActivitiEventType, Throwable)}
   */
  @Test
  public void testNewActivitiEntityExceptionEventImpl_whenNull_thenReturnExecutionIdIsNull() {
    // Arrange
    Object object = JSONObject.NULL;
    Throwable cause = new Throwable();

    // Act
    ActivitiEntityExceptionEventImpl actualActivitiEntityExceptionEventImpl = new ActivitiEntityExceptionEventImpl(
        object, ActivitiEventType.ENTITY_CREATED, cause);

    // Assert
    assertNull(actualActivitiEntityExceptionEventImpl.getExecutionId());
    assertNull(actualActivitiEntityExceptionEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEntityExceptionEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEntityExceptionEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEntityExceptionEventImpl.getType());
    assertSame(cause, actualActivitiEntityExceptionEventImpl.getCause());
    assertSame(object, actualActivitiEntityExceptionEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiEntityExceptionEventImpl#getCause()}
   *   <li>{@link ActivitiEntityExceptionEventImpl#getEntity()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiEntityExceptionEventImpl activitiEntityExceptionEventImpl = new ActivitiEntityExceptionEventImpl(
        JSONObject.NULL, ActivitiEventType.ENTITY_CREATED, new Throwable());

    // Act
    Throwable actualCause = activitiEntityExceptionEventImpl.getCause();

    // Assert
    assertSame(activitiEntityExceptionEventImpl.cause, actualCause);
    assertSame(activitiEntityExceptionEventImpl.entity, activitiEntityExceptionEventImpl.getEntity());
  }
}
