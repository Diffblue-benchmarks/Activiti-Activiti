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

public class ActivitiEntityEventImplDiffblueTest {
  /**
   * Method under test: {@link ActivitiEntityEventImpl#getEntity()}
   */
  @Test
  public void testGetEntity() {
    // Arrange
    ActivitiEntityEventImpl activitiEntityEventImpl = new ActivitiEntityEventImpl(JSONObject.NULL,
        ActivitiEventType.ENTITY_CREATED);

    // Act and Assert
    assertSame(activitiEntityEventImpl.entity, activitiEntityEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEntityEventImpl() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiEntityEventImpl actualActivitiEntityEventImpl = new ActivitiEntityEventImpl(object,
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiEntityEventImpl.getExecutionId());
    assertNull(actualActivitiEntityEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEntityEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEntityEventImpl.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualActivitiEntityEventImpl.getType());
    assertSame(object, actualActivitiEntityEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   */
  @Test
  public void testNewActivitiEntityEventImpl2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new ActivitiEntityEventImpl(null, ActivitiEventType.ENTITY_CREATED));

  }
}
