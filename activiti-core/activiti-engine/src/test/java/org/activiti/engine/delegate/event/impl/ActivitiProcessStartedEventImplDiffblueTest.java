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
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiProcessStartedEventImplDiffblueTest {
  /**
   * Test {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map,
   * boolean)}.
   *
   * <ul>
   *   <li>Then Entity return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiProcessStartedEventImpl.<init>(Object, Map, boolean)"})
  public void testNewActivitiProcessStartedEventImpl_thenEntityReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiProcessStartedEventImpl actualActivitiProcessStartedEventImpl =
        new ActivitiProcessStartedEventImpl(
            createWithEmptyRelationshipCollectionsResult, new HashMap<>(), true);

    // Assert
    Object entity = actualActivitiProcessStartedEventImpl.getEntity();
    assertTrue(entity instanceof ExecutionEntityImpl);
    assertSame(createWithEmptyRelationshipCollectionsResult, entity);
  }

  /**
   * Test {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map,
   * boolean)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiProcessStartedEventImpl.<init>(Object, Map, boolean)"})
  public void testNewActivitiProcessStartedEventImpl_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ActivitiProcessStartedEventImpl(null, new HashMap<>(), true));
  }

  /**
   * Test {@link ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map,
   * boolean)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return ExecutionId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiProcessStartedEventImpl#ActivitiProcessStartedEventImpl(Object, Map, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiProcessStartedEventImpl.<init>(Object, Map, boolean)"})
  public void testNewActivitiProcessStartedEventImpl_whenNull_thenReturnExecutionIdIsNull() {
    // Arrange
    Object object = JSONObject.NULL;
    HashMap<Object, Object> variables = new HashMap<>();

    // Act
    ActivitiProcessStartedEventImpl actualActivitiProcessStartedEventImpl =
        new ActivitiProcessStartedEventImpl(object, variables, true);

    // Assert
    assertNull(actualActivitiProcessStartedEventImpl.getExecutionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getProcessInstanceId());
    assertNull(actualActivitiProcessStartedEventImpl.getReason());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessDefinitionId());
    assertNull(actualActivitiProcessStartedEventImpl.getNestedProcessInstanceId());
    assertEquals(
        ActivitiEventType.PROCESS_STARTED, actualActivitiProcessStartedEventImpl.getType());
    assertTrue(actualActivitiProcessStartedEventImpl.isLocalScope());
    assertSame(variables, actualActivitiProcessStartedEventImpl.getVariables());
    assertSame(object, actualActivitiProcessStartedEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivitiProcessStartedEventImpl#getNestedProcessDefinitionId()}
   *   <li>{@link ActivitiProcessStartedEventImpl#getNestedProcessInstanceId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ActivitiProcessStartedEventImpl.getNestedProcessDefinitionId()",
    "String ActivitiProcessStartedEventImpl.getNestedProcessInstanceId()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ActivitiProcessStartedEventImpl activitiProcessStartedEventImpl =
        new ActivitiProcessStartedEventImpl(JSONObject.NULL, new HashMap<>(), true);

    // Act
    String actualNestedProcessDefinitionId =
        activitiProcessStartedEventImpl.getNestedProcessDefinitionId();

    // Assert
    assertNull(actualNestedProcessDefinitionId);
    assertNull(activitiProcessStartedEventImpl.getNestedProcessInstanceId());
  }
}
