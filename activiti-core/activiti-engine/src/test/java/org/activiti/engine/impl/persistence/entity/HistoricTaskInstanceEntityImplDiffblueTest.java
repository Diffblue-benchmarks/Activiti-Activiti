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
   * Test getters and setters.
   * <p>
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

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
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
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
    assertSame(claimTime, ((Map<String, Object>) actualPersistentState).get("claimTime"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
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
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
    assertSame(dueDate, ((Map<String, Object>) actualPersistentState).get("dueDate"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code parentTaskId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnParentTaskIdIsFoo() {
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
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is fourteen.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnSizeIsFourteen() {
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
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("dueDate"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("priority"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
    assertSame(claimTime, ((Map<String, Object>) actualPersistentState).get("claimTime"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is twelve.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnSizeIsTwelve() {
    // Arrange and Act
    Object actualPersistentState = (new HistoricTaskInstanceEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(12, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("endTime"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("priority").intValue());
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("taskDefinitionKey"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * DueDate is {@link Date}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName_givenHistoricTaskInstanceEntityImplDueDateIsDate_thenReturnNull() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * LocalizedName is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName_givenHistoricTaskInstanceEntityImplLocalizedNameIsEmptyString() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * LocalizedName is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName_givenHistoricTaskInstanceEntityImplLocalizedNameIsFoo_thenReturnFoo() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("foo");

    // Act and Assert
    assertEquals("foo", historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  public void testGetName_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * DueDate is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription_givenHistoricTaskInstanceEntityImplDueDateIsDate() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription_thenReturnFoo() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription("foo");

    // Act and Assert
    assertEquals("foo", historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getCreateTime()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getCreateTime()}
   */
  @Test
  public void testGetCreateTime_givenHistoricTaskInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getCreateTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getCreateTime()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * DueDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getCreateTime()}
   */
  @Test
  public void testGetCreateTime_givenHistoricTaskInstanceEntityImplDueDateIsDate() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getCreateTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTime()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime_givenHistoricTaskInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTime()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * DueDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getTime()}
   */
  @Test
  public void testGetTime_givenHistoricTaskInstanceEntityImplDueDateIsDate() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl
        .setEndTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getWorkTimeInMillis());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * EndTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis_givenHistoricTaskInstanceEntityImplEndTimeIsDate() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setEndTime(mock(java.sql.Date.class));
    historicTaskInstanceEntityImpl.setClaimTime(null);

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getWorkTimeInMillis());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getWorkTimeInMillis());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   * <ul>
   *   <li>Then return longValue is zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  public void testGetWorkTimeInMillis_thenReturnLongValueIsZero() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables_givenHistoricTaskInstanceEntityImpl_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new HistoricTaskInstanceEntityImpl()).getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor) Id
   * is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables_givenHistoricVariableInstanceEntityImplIdIsFoo() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor) Id
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables_givenHistoricVariableInstanceEntityImplIdIsNull() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  public void testGetTaskLocalVariables_thenReturnSizeIsOne() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenHistoricTaskInstanceEntityImpl_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new HistoricTaskInstanceEntityImpl()).getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor) Id
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenHistoricVariableInstanceEntityImplIdIsNull() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * TaskId is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenHistoricVariableInstanceEntityImplTaskIdIsFoo() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_thenReturnSizeIsOne() {
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
   * Test {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   * DueDate is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_givenHistoricTaskInstanceEntityImplDueDateIsDate() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getQueryVariables());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceEntityImpl()).getQueryVariables());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getQueryVariables().isEmpty());
  }
}
