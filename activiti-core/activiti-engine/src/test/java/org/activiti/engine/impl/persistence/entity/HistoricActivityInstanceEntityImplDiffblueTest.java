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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.junit.Test;

public class HistoricActivityInstanceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link HistoricActivityInstanceEntityImpl}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setActivityId(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setActivityName(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setActivityType(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setAssignee(String)}
   *   <li>
   * {@link HistoricActivityInstanceEntityImpl#setCalledProcessInstanceId(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setExecutionId(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setTaskId(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#setTenantId(String)}
   *   <li>{@link HistoricActivityInstanceEntityImpl#toString()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getActivityId()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getActivityName()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getActivityType()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getAssignee()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getCalledProcessInstanceId()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getExecutionId()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getTaskId()}
   *   <li>{@link HistoricActivityInstanceEntityImpl#getTenantId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricActivityInstanceEntityImpl actualHistoricActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    actualHistoricActivityInstanceEntityImpl.setActivityId("42");
    actualHistoricActivityInstanceEntityImpl.setActivityName("Activity Name");
    actualHistoricActivityInstanceEntityImpl.setActivityType("Activity Type");
    actualHistoricActivityInstanceEntityImpl.setAssignee("Assignee");
    actualHistoricActivityInstanceEntityImpl.setCalledProcessInstanceId("42");
    actualHistoricActivityInstanceEntityImpl.setExecutionId("42");
    actualHistoricActivityInstanceEntityImpl.setTaskId("42");
    actualHistoricActivityInstanceEntityImpl.setTenantId("42");
    String actualToStringResult = actualHistoricActivityInstanceEntityImpl.toString();
    String actualActivityId = actualHistoricActivityInstanceEntityImpl.getActivityId();
    String actualActivityName = actualHistoricActivityInstanceEntityImpl.getActivityName();
    String actualActivityType = actualHistoricActivityInstanceEntityImpl.getActivityType();
    String actualAssignee = actualHistoricActivityInstanceEntityImpl.getAssignee();
    String actualCalledProcessInstanceId = actualHistoricActivityInstanceEntityImpl.getCalledProcessInstanceId();
    String actualExecutionId = actualHistoricActivityInstanceEntityImpl.getExecutionId();
    String actualTaskId = actualHistoricActivityInstanceEntityImpl.getTaskId();

    // Assert that nothing has changed
    assertEquals("42", actualActivityId);
    assertEquals("42", actualCalledProcessInstanceId);
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualHistoricActivityInstanceEntityImpl.getTenantId());
    assertEquals("Activity Name", actualActivityName);
    assertEquals("Activity Type", actualActivityType);
    assertEquals("Assignee", actualAssignee);
    assertEquals("HistoricActivityInstanceEntity[id=null, activityId=42, activityName=Activity Name]",
        actualToStringResult);
    assertFalse(actualHistoricActivityInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricActivityInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricActivityInstanceEntityImpl.isUpdated());
  }

  /**
   * Test {@link HistoricActivityInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code endTime} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnEndTimeIsNull() {
    // Arrange and Act
    Object actualPersistentState = (new HistoricActivityInstanceEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(5, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("assignee"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("endTime"));
    assertNull(((Map<String, Object>) actualPersistentState).get("executionId"));
  }

  /**
   * Test {@link HistoricActivityInstanceEntityImpl#getTime()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricActivityInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime_givenHistoricActivityInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getTime());
  }
}
