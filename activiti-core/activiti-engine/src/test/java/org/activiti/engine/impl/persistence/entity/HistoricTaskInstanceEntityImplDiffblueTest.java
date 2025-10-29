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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.Test;

public class HistoricTaskInstanceEntityImplDiffblueTest {
  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new HistoricTaskInstanceEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(12, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("assignee"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("description"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("endTime"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("formKey"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("name"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("owner"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("taskDefinitionKey"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("priority").intValue());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setParentTaskId(null);
    historicTaskInstanceEntityImpl.setDueDate(null);
    Date claimTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicTaskInstanceEntityImpl.setClaimTime(claimTime);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("assignee"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("formKey"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertNull(((Map<String, Object>) actualPersistentState).get("owner"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("taskDefinitionKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertSame(claimTime, ((Map<String, Object>) actualPersistentState).get("claimTime"));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setParentTaskId(null);
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicTaskInstanceEntityImpl.setDueDate(dueDate);
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("assignee"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("formKey"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertNull(((Map<String, Object>) actualPersistentState).get("owner"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("taskDefinitionKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertSame(dueDate, ((Map<String, Object>) actualPersistentState).get("dueDate"));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState4() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setParentTaskId("foo");
    historicTaskInstanceEntityImpl.setDueDate(null);
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertEquals("foo", ((Map<String, Object>) actualPersistentState).get("parentTaskId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("assignee"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("formKey"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertNull(((Map<String, Object>) actualPersistentState).get("owner"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("taskDefinitionKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState5() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setParentTaskId(null);
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));
    java.util.Date claimTime = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicTaskInstanceEntityImpl.setClaimTime(claimTime);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(14, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("description"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("formKey"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertNull(((Map<String, Object>) actualPersistentState).get("owner"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("taskDefinitionKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("dueDate"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertSame(claimTime, ((Map<String, Object>) actualPersistentState).get("claimTime"));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getName());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("foo");

    // Act and Assert
    assertEquals("foo", historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName3() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName4() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getDescription());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription("foo");

    // Act and Assert
    assertEquals("foo", historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription3() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription4() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getCreateTime()}
   */
  @Test
  public void testGetCreateTime() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getCreateTime());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getCreateTime()}
   */
  @Test
  public void testGetCreateTime2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getCreateTime());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getTime());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getTime());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getWorkTimeInMillis());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl
        .setEndTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getWorkTimeInMillis());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis3() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl
        .setEndTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicTaskInstanceEntityImpl
        .setClaimTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertEquals(0L, historicTaskInstanceEntityImpl.getWorkTimeInMillis().longValue());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis4() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setEndTime(mock(java.sql.Date.class));
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getWorkTimeInMillis());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables() {
    // Arrange, Act and Assert
    assertTrue((new HistoricTaskInstanceEntityImpl()).getTaskLocalVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables3() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId(null);
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables4() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables5() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId("foo");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualTaskLocalVariables = historicTaskInstanceEntityImpl.getTaskLocalVariables();

    // Assert
    assertEquals(1, actualTaskLocalVariables.size());
    assertTrue(actualTaskLocalVariables.containsKey("Name"));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables() {
    // Arrange, Act and Assert
    assertTrue((new HistoricTaskInstanceEntityImpl()).getProcessVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables3() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId(null);
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables4() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables = historicTaskInstanceEntityImpl.getProcessVariables();

    // Assert
    assertEquals(1, actualProcessVariables.size());
    assertTrue(actualProcessVariables.containsKey("Name"));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables5() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl
        .setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId("foo");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getQueryVariables());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    List<HistoricVariableInstanceEntity> actualQueryVariables = historicTaskInstanceEntityImpl.getQueryVariables();

    // Assert
    assertTrue(actualQueryVariables.isEmpty());
    assertSame(queryVariables, actualQueryVariables);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables3() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getQueryVariables());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setAssignee(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setBusinessKey(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setCategory(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setClaimTime(Date)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setDescription(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setDueDate(Date)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setExecutionId(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setFormKey(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setLocalizedDescription(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setLocalizedName(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setName(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setOwner(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setParentTaskId(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setPriority(int)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setQueryVariables(List)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setTaskDefinitionKey(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#setTenantId(String)}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getAssignee()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getBusinessKey()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getCategory()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getClaimTime()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getDueDate()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getExecutionId()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getFormKey()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getOwner()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getParentTaskId()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getPriority()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getTaskDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceEntityImpl#getTenantId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricTaskInstanceEntityImpl actualHistoricTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    actualHistoricTaskInstanceEntityImpl.setAssignee("Assignee");
    actualHistoricTaskInstanceEntityImpl.setBusinessKey("Business Key");
    actualHistoricTaskInstanceEntityImpl.setCategory("Category");
    Date claimTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricTaskInstanceEntityImpl.setClaimTime(claimTime);
    actualHistoricTaskInstanceEntityImpl.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricTaskInstanceEntityImpl.setDueDate(dueDate);
    actualHistoricTaskInstanceEntityImpl.setExecutionId("42");
    actualHistoricTaskInstanceEntityImpl.setFormKey("Form Key");
    actualHistoricTaskInstanceEntityImpl.setLocalizedDescription("The characteristics of someone or something");
    actualHistoricTaskInstanceEntityImpl.setLocalizedName("Name");
    actualHistoricTaskInstanceEntityImpl.setName("Name");
    actualHistoricTaskInstanceEntityImpl.setOwner("Owner");
    actualHistoricTaskInstanceEntityImpl.setParentTaskId("42");
    actualHistoricTaskInstanceEntityImpl.setPriority(1);
    actualHistoricTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());
    actualHistoricTaskInstanceEntityImpl.setTaskDefinitionKey("Task Definition Key");
    actualHistoricTaskInstanceEntityImpl.setTenantId("42");
    String actualAssignee = actualHistoricTaskInstanceEntityImpl.getAssignee();
    String actualBusinessKey = actualHistoricTaskInstanceEntityImpl.getBusinessKey();
    String actualCategory = actualHistoricTaskInstanceEntityImpl.getCategory();
    Date actualClaimTime = actualHistoricTaskInstanceEntityImpl.getClaimTime();
    Date actualDueDate = actualHistoricTaskInstanceEntityImpl.getDueDate();
    String actualExecutionId = actualHistoricTaskInstanceEntityImpl.getExecutionId();
    String actualFormKey = actualHistoricTaskInstanceEntityImpl.getFormKey();
    String actualOwner = actualHistoricTaskInstanceEntityImpl.getOwner();
    String actualParentTaskId = actualHistoricTaskInstanceEntityImpl.getParentTaskId();
    int actualPriority = actualHistoricTaskInstanceEntityImpl.getPriority();
    String actualTaskDefinitionKey = actualHistoricTaskInstanceEntityImpl.getTaskDefinitionKey();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", actualHistoricTaskInstanceEntityImpl.getTenantId());
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Category", actualCategory);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Owner", actualOwner);
    assertEquals("Task Definition Key", actualTaskDefinitionKey);
    assertEquals(1, actualPriority);
    assertFalse(actualHistoricTaskInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricTaskInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricTaskInstanceEntityImpl.isUpdated());
    assertTrue(actualHistoricTaskInstanceEntityImpl.queryVariables.isEmpty());
    assertSame(claimTime, actualClaimTime);
    assertSame(dueDate, actualDueDate);
  }
}
