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
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiEntityWithVariablesEventImplDiffblueTest {
  /**
   * Test {@link ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object,
   * Map, boolean, ActivitiEventType)}.
   *
   * <p>Method under test: {@link
   * ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object, Map, boolean,
   * ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEntityWithVariablesEventImpl.<init>(Object, Map, boolean, ActivitiEventType)"
  })
  public void testNewActivitiEntityWithVariablesEventImpl() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new ActivitiEntityWithVariablesEventImpl(JSONObject.NULL, new HashMap<>(), true, null));
  }

  /**
   * Test {@link ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object,
   * Map, boolean, ActivitiEventType)}.
   *
   * <p>Method under test: {@link
   * ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object, Map, boolean,
   * ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEntityWithVariablesEventImpl.<init>(Object, Map, boolean, ActivitiEventType)"
  })
  public void testNewActivitiEntityWithVariablesEventImpl2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new ActivitiEntityWithVariablesEventImpl(
                null, new HashMap<>(), true, ActivitiEventType.ENTITY_CREATED));
  }

  /**
   * Test {@link ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object,
   * Map, boolean, ActivitiEventType)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return ExecutionId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEntityWithVariablesEventImpl#ActivitiEntityWithVariablesEventImpl(Object, Map, boolean,
   * ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEntityWithVariablesEventImpl.<init>(Object, Map, boolean, ActivitiEventType)"
  })
  public void testNewActivitiEntityWithVariablesEventImpl_whenNull_thenReturnExecutionIdIsNull() {
    // Arrange
    Object object = JSONObject.NULL;
    HashMap<Object, Object> variables = new HashMap<>();

    // Act
    ActivitiEntityWithVariablesEventImpl actualActivitiEntityWithVariablesEventImpl =
        new ActivitiEntityWithVariablesEventImpl(
            object, variables, true, ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertNull(actualActivitiEntityWithVariablesEventImpl.getExecutionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessDefinitionId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getProcessInstanceId());
    assertNull(actualActivitiEntityWithVariablesEventImpl.getReason());
    assertEquals(
        ActivitiEventType.ENTITY_CREATED, actualActivitiEntityWithVariablesEventImpl.getType());
    assertTrue(actualActivitiEntityWithVariablesEventImpl.isLocalScope());
    assertSame(variables, actualActivitiEntityWithVariablesEventImpl.getVariables());
    assertSame(object, actualActivitiEntityWithVariablesEventImpl.getEntity());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ActivitiEntityWithVariablesEventImpl#getVariables()}
   *   <li>{@link ActivitiEntityWithVariablesEventImpl#isLocalScope()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ActivitiEntityWithVariablesEventImpl.getVariables()",
    "boolean ActivitiEntityWithVariablesEventImpl.isLocalScope()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ActivitiEntityWithVariablesEventImpl activitiEntityWithVariablesEventImpl =
        new ActivitiEntityWithVariablesEventImpl(
            JSONObject.NULL, new HashMap<>(), true, ActivitiEventType.ENTITY_CREATED);

    // Act
    Map actualVariables = activitiEntityWithVariablesEventImpl.getVariables();

    // Assert
    assertTrue(activitiEntityWithVariablesEventImpl.isLocalScope());
    assertSame(activitiEntityWithVariablesEventImpl.variables, actualVariables);
  }
}
