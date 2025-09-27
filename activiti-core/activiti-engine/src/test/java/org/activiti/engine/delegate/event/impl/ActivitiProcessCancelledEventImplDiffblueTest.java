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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiProcessCancelledEventImplDiffblueTest {
  /**
   * Test {@link
   * ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}.
   *
   * <p>Method under test: {@link
   * ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiProcessCancelledEventImpl.<init>(ProcessInstance)"})
  public void testNewActivitiProcessCancelledEventImpl() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ActivitiProcessCancelledEventImpl(null));
  }

  /**
   * Test {@link
   * ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}.
   *
   * <ul>
   *   <li>Then Entity return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiProcessCancelledEventImpl.<init>(ProcessInstance)"})
  public void testNewActivitiProcessCancelledEventImpl_thenEntityReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiProcessCancelledEventImpl actualActivitiProcessCancelledEventImpl =
        new ActivitiProcessCancelledEventImpl(processInstance);

    // Assert
    Object entity = actualActivitiProcessCancelledEventImpl.getEntity();
    assertTrue(entity instanceof ExecutionEntityImpl);
    assertNull(actualActivitiProcessCancelledEventImpl.getCause());
    assertNull(actualActivitiProcessCancelledEventImpl.getExecutionId());
    assertNull(actualActivitiProcessCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiProcessCancelledEventImpl.getProcessInstanceId());
    assertNull(actualActivitiProcessCancelledEventImpl.getReason());
    assertEquals(
        ActivitiEventType.PROCESS_CANCELLED, actualActivitiProcessCancelledEventImpl.getType());
    assertSame(processInstance, entity);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivitiProcessCancelledEventImpl#setCause(Object)}
   *   <li>{@link ActivitiProcessCancelledEventImpl#getCause()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object ActivitiProcessCancelledEventImpl.getCause()",
    "void ActivitiProcessCancelledEventImpl.setCause(Object)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ActivitiProcessCancelledEventImpl activitiProcessCancelledEventImpl =
        new ActivitiProcessCancelledEventImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    Object object = JSONObject.NULL;

    // Act
    activitiProcessCancelledEventImpl.setCause(object);

    // Assert
    assertSame(object, activitiProcessCancelledEventImpl.getCause());
  }
}
