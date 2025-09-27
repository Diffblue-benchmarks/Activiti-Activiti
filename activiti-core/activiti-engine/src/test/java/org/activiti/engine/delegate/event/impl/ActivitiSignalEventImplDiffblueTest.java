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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiSignalEventImplDiffblueTest {
  /**
   * Test {@link ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiSignalEventImpl.<init>(ActivitiEventType)"})
  public void testNewActivitiSignalEventImpl_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new ActivitiSignalEventImpl(null));
  }

  /**
   * Test {@link ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return SignalData is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiSignalEventImpl#ActivitiSignalEventImpl(ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiSignalEventImpl.<init>(ActivitiEventType)"})
  public void testNewActivitiSignalEventImpl_whenEntityCreated_thenReturnSignalDataIsNull() {
    // Arrange and Act
    ActivitiSignalEventImpl actualActivitiSignalEventImpl =
        new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);

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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivitiSignalEventImpl#setSignalData(Object)}
   *   <li>{@link ActivitiSignalEventImpl#setSignalName(String)}
   *   <li>{@link ActivitiSignalEventImpl#getSignalData()}
   *   <li>{@link ActivitiSignalEventImpl#getSignalName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object ActivitiSignalEventImpl.getSignalData()",
    "String ActivitiSignalEventImpl.getSignalName()",
    "void ActivitiSignalEventImpl.setSignalData(Object)",
    "void ActivitiSignalEventImpl.setSignalName(String)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ActivitiSignalEventImpl activitiSignalEventImpl =
        new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    Object object = JSONObject.NULL;

    // Act
    activitiSignalEventImpl.setSignalData(object);
    activitiSignalEventImpl.setSignalName("Signal Name");
    Object actualSignalData = activitiSignalEventImpl.getSignalData();

    // Assert
    assertEquals("Signal Name", activitiSignalEventImpl.getSignalName());
    assertSame(object, actualSignalData);
  }
}
