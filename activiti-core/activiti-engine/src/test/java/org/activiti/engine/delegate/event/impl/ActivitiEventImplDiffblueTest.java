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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.junit.Test;

public class ActivitiEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType, String, String, String)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType, String, String, String)}
   */
  @Test
  public void testNewActivitiEventImpl_whenEntityCreated_thenReturnExecutionIdIs42() {
    // Arrange and Act
    ActivitiEventImpl actualActivitiEventImpl = new ActivitiEventImpl(ActivitiEventType.ENTITY_CREATED, "42", "42",
        "42");

    // Assert
    assertEquals("42", actualActivitiEventImpl.getExecutionId());
    assertEquals("42", actualActivitiEventImpl.getProcessDefinitionId());
    assertEquals("42", actualActivitiEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEventImpl.getType());
  }

  /**
   * Test {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEventImpl_whenEntityCreated_thenReturnExecutionIdIsNull() {
    // Arrange and Act
    ActivitiEventImpl actualActivitiEventImpl = new ActivitiEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiEventImpl.getExecutionId());
    assertNull(actualActivitiEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEventImpl.getType());
  }

  /**
   * Test {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventImpl#ActivitiEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEventImpl_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new ActivitiEventImpl(null));
    assertThrows(ActivitiIllegalArgumentException.class, () -> new ActivitiEventImpl(null, "42", "42", "42"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiEventImpl#setExecutionId(String)}
   *   <li>{@link ActivitiEventImpl#setProcessDefinitionId(String)}
   *   <li>{@link ActivitiEventImpl#setProcessInstanceId(String)}
   *   <li>{@link ActivitiEventImpl#setReason(String)}
   *   <li>{@link ActivitiEventImpl#setType(ActivitiEventType)}
   *   <li>{@link ActivitiEventImpl#toString()}
   *   <li>{@link ActivitiEventImpl#getExecutionId()}
   *   <li>{@link ActivitiEventImpl#getProcessDefinitionId()}
   *   <li>{@link ActivitiEventImpl#getProcessInstanceId()}
   *   <li>{@link ActivitiEventImpl#getReason()}
   *   <li>{@link ActivitiEventImpl#getType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiEventImpl activitiEventImpl = new ActivitiEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiEventImpl.setExecutionId("42");
    activitiEventImpl.setProcessDefinitionId("42");
    activitiEventImpl.setProcessInstanceId("42");
    activitiEventImpl.setReason("Just cause");
    activitiEventImpl.setType(ActivitiEventType.ENTITY_CREATED);
    String actualToStringResult = activitiEventImpl.toString();
    String actualExecutionId = activitiEventImpl.getExecutionId();
    String actualProcessDefinitionId = activitiEventImpl.getProcessDefinitionId();
    String actualProcessInstanceId = activitiEventImpl.getProcessInstanceId();
    String actualReason = activitiEventImpl.getReason();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("Just cause", actualReason);
    assertEquals("class org.activiti.engine.delegate.event.impl.ActivitiEventImpl - ENTITY_CREATED",
        actualToStringResult);
    assertEquals(ActivitiEventType.ENTITY_CREATED, activitiEventImpl.getType());
  }
}
