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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiErrorEventImplDiffblueTest {
  /**
   * Test {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return ActivityId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiErrorEventImpl.<init>(ActivitiEventType)"})
  public void testNewActivitiErrorEventImpl_whenEntityCreated_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ActivitiErrorEventImpl actualActivitiErrorEventImpl =
        new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED);

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
   * Test {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiErrorEventImpl#ActivitiErrorEventImpl(ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiErrorEventImpl.<init>(ActivitiEventType)"})
  public void testNewActivitiErrorEventImpl_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new ActivitiErrorEventImpl(null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivitiErrorEventImpl#setErrorCode(String)}
   *   <li>{@link ActivitiErrorEventImpl#setErrorId(String)}
   *   <li>{@link ActivitiErrorEventImpl#getErrorCode()}
   *   <li>{@link ActivitiErrorEventImpl#getErrorId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ActivitiErrorEventImpl.getErrorCode()",
    "String ActivitiErrorEventImpl.getErrorId()",
    "void ActivitiErrorEventImpl.setErrorCode(String)",
    "void ActivitiErrorEventImpl.setErrorId(String)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ActivitiErrorEventImpl activitiErrorEventImpl =
        new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiErrorEventImpl.setErrorCode("An error occurred");
    activitiErrorEventImpl.setErrorId("An error occurred");
    String actualErrorCode = activitiErrorEventImpl.getErrorCode();

    // Assert
    assertEquals("An error occurred", actualErrorCode);
    assertEquals("An error occurred", activitiErrorEventImpl.getErrorId());
  }
}
