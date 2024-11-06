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

public class ActivitiMessageEventImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiMessageEventImpl#ActivitiMessageEventImpl(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return MessageData is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiMessageEventImpl#ActivitiMessageEventImpl(ActivitiEventType)}
   */
  @Test
  public void testNewActivitiMessageEventImpl_whenEntityCreated_thenReturnMessageDataIsNull() {
    // Arrange and Act
    ActivitiMessageEventImpl actualActivitiMessageEventImpl = new ActivitiMessageEventImpl(
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiMessageEventImpl.getMessageData());
    assertNull(actualActivitiMessageEventImpl.getActivityId());
    assertNull(actualActivitiMessageEventImpl.getActivityName());
    assertNull(actualActivitiMessageEventImpl.getActivityType());
    assertNull(actualActivitiMessageEventImpl.getBehaviorClass());
    assertNull(actualActivitiMessageEventImpl.getExecutionId());
    assertNull(actualActivitiMessageEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiMessageEventImpl.getProcessInstanceId());
    assertNull(actualActivitiMessageEventImpl.getReason());
    assertNull(actualActivitiMessageEventImpl.getMessageBusinessKey());
    assertNull(actualActivitiMessageEventImpl.getMessageCorrelationKey());
    assertNull(actualActivitiMessageEventImpl.getMessageName());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiMessageEventImpl.getType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiMessageEventImpl#setMessageBusinessKey(String)}
   *   <li>{@link ActivitiMessageEventImpl#setMessageCorrelationKey(String)}
   *   <li>{@link ActivitiMessageEventImpl#setMessageData(Object)}
   *   <li>{@link ActivitiMessageEventImpl#setMessageName(String)}
   *   <li>{@link ActivitiMessageEventImpl#getMessageBusinessKey()}
   *   <li>{@link ActivitiMessageEventImpl#getMessageCorrelationKey()}
   *   <li>{@link ActivitiMessageEventImpl#getMessageData()}
   *   <li>{@link ActivitiMessageEventImpl#getMessageName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiMessageEventImpl activitiMessageEventImpl = new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiMessageEventImpl.setMessageBusinessKey("Business Key");
    activitiMessageEventImpl.setMessageCorrelationKey("Correlation Key");
    Object object = JSONObject.NULL;
    activitiMessageEventImpl.setMessageData(object);
    activitiMessageEventImpl.setMessageName("Message Name");
    String actualMessageBusinessKey = activitiMessageEventImpl.getMessageBusinessKey();
    String actualMessageCorrelationKey = activitiMessageEventImpl.getMessageCorrelationKey();
    Object actualMessageData = activitiMessageEventImpl.getMessageData();

    // Assert that nothing has changed
    assertEquals("Business Key", actualMessageBusinessKey);
    assertEquals("Correlation Key", actualMessageCorrelationKey);
    assertEquals("Message Name", activitiMessageEventImpl.getMessageName());
    assertSame(object, actualMessageData);
  }
}
