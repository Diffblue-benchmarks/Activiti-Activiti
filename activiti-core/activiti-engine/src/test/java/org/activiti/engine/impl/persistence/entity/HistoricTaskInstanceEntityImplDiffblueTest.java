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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricTaskInstanceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricTaskInstanceEntityImpl.<init>()",
    "String HistoricTaskInstanceEntityImpl.getAssignee()",
    "String HistoricTaskInstanceEntityImpl.getBusinessKey()",
    "String HistoricTaskInstanceEntityImpl.getCategory()",
    "Date HistoricTaskInstanceEntityImpl.getClaimTime()",
    "Date HistoricTaskInstanceEntityImpl.getDueDate()",
    "String HistoricTaskInstanceEntityImpl.getExecutionId()",
    "String HistoricTaskInstanceEntityImpl.getFormKey()",
    "String HistoricTaskInstanceEntityImpl.getOwner()",
    "String HistoricTaskInstanceEntityImpl.getParentTaskId()",
    "int HistoricTaskInstanceEntityImpl.getPriority()",
    "String HistoricTaskInstanceEntityImpl.getTaskDefinitionKey()",
    "String HistoricTaskInstanceEntityImpl.getTenantId()",
    "void HistoricTaskInstanceEntityImpl.setAssignee(String)",
    "void HistoricTaskInstanceEntityImpl.setBusinessKey(String)",
    "void HistoricTaskInstanceEntityImpl.setCategory(String)",
    "void HistoricTaskInstanceEntityImpl.setClaimTime(Date)",
    "void HistoricTaskInstanceEntityImpl.setDescription(String)",
    "void HistoricTaskInstanceEntityImpl.setDueDate(Date)",
    "void HistoricTaskInstanceEntityImpl.setExecutionId(String)",
    "void HistoricTaskInstanceEntityImpl.setFormKey(String)",
    "void HistoricTaskInstanceEntityImpl.setLocalizedDescription(String)",
    "void HistoricTaskInstanceEntityImpl.setLocalizedName(String)",
    "void HistoricTaskInstanceEntityImpl.setName(String)",
    "void HistoricTaskInstanceEntityImpl.setOwner(String)",
    "void HistoricTaskInstanceEntityImpl.setParentTaskId(String)",
    "void HistoricTaskInstanceEntityImpl.setPriority(int)",
    "void HistoricTaskInstanceEntityImpl.setQueryVariables(List)",
    "void HistoricTaskInstanceEntityImpl.setTaskDefinitionKey(String)",
    "void HistoricTaskInstanceEntityImpl.setTenantId(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricTaskInstanceEntityImpl actualHistoricTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    actualHistoricTaskInstanceEntityImpl.setAssignee("Assignee");
    actualHistoricTaskInstanceEntityImpl.setBusinessKey("Business Key");
    actualHistoricTaskInstanceEntityImpl.setCategory("Category");
    Date claimTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricTaskInstanceEntityImpl.setClaimTime(claimTime);
    actualHistoricTaskInstanceEntityImpl.setDescription(
        "The characteristics of someone or something");
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualHistoricTaskInstanceEntityImpl.setDueDate(dueDate);
    actualHistoricTaskInstanceEntityImpl.setExecutionId("42");
    actualHistoricTaskInstanceEntityImpl.setFormKey("Form Key");
    actualHistoricTaskInstanceEntityImpl.setLocalizedDescription(
        "The characteristics of someone or something");
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

    // Assert
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", actualHistoricTaskInstanceEntityImpl.getTenantId());
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Category", actualCategory);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Owner", actualOwner);
    assertEquals("Task Definition Key", actualTaskDefinitionKey);
    assertNull(actualHistoricTaskInstanceEntityImpl.getDurationInMillis());
    assertNull(actualHistoricTaskInstanceEntityImpl.getId());
    assertNull(actualHistoricTaskInstanceEntityImpl.getDeleteReason());
    assertNull(actualHistoricTaskInstanceEntityImpl.getProcessDefinitionId());
    assertNull(actualHistoricTaskInstanceEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricTaskInstanceEntityImpl.getEndTime());
    assertNull(actualHistoricTaskInstanceEntityImpl.getStartTime());
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
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricTaskInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicTaskInstanceEntityImpl.setDueDate(dueDate);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
    assertSame(dueDate, ((Map<String, Object>) actualPersistentState).get("dueDate"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricTaskInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState2() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    Date claimTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    historicTaskInstanceEntityImpl.setClaimTime(claimTime);

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
    assertSame(claimTime, ((Map<String, Object>) actualPersistentState).get("claimTime"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return {@code parentTaskId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricTaskInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnParentTaskIdIs42() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setParentTaskId("42");

    // Act
    Object actualPersistentState = historicTaskInstanceEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(13, ((Map<String, Object>) actualPersistentState).size());
    assertEquals("42", ((Map<String, Object>) actualPersistentState).get("parentTaskId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("taskDefinitionKey"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return size is twelve.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricTaskInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnSizeIsTwelve() {
    // Arrange and Act
    Object actualPersistentState = new HistoricTaskInstanceEntityImpl().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(12, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("endTime"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("assignee"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("description"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("formKey"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("name"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("owner"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("priority"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<String, Integer>) actualPersistentState).containsKey("taskDefinitionKey"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}
   *       LocalizedName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getName()"})
  public void testGetName_givenHistoricTaskInstanceEntityImplLocalizedNameIsEmptyString() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getName()"})
  public void testGetName_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getName()"})
  public void testGetName_thenReturnName() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedName("Name");

    // Act and Assert
    assertEquals("Name", historicTaskInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getDescription()"})
  public void testGetDescription() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription("");

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getDescription()"})
  public void testGetDescription_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Then return {@code The characteristics of someone or something}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceEntityImpl.getDescription()"})
  public void testGetDescription_thenReturnTheCharacteristicsOfSomeoneOrSomething() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setLocalizedDescription(
        "The characteristics of someone or something");

    // Act and Assert
    assertEquals(
        "The characteristics of someone or something",
        historicTaskInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getCreateTime()}.
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getCreateTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date HistoricTaskInstanceEntityImpl.getCreateTime()"})
  public void testGetCreateTime() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getCreateTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTime()}.
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date HistoricTaskInstanceEntityImpl.getTime()"})
  public void testGetTime() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getTime());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Long HistoricTaskInstanceEntityImpl.getWorkTimeInMillis()"})
  public void testGetWorkTimeInMillis_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getWorkTimeInMillis());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Long HistoricTaskInstanceEntityImpl.getWorkTimeInMillis()"})
  public void testGetWorkTimeInMillis_thenReturnLongValueIsZero() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setClaimTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicTaskInstanceEntityImpl.setEndTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertEquals(0L, historicTaskInstanceEntityImpl.getWorkTimeInMillis().longValue());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getWorkTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Long HistoricTaskInstanceEntityImpl.getWorkTimeInMillis()"})
  public void testGetWorkTimeInMillis_thenReturnNull() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setEndTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertNull(historicTaskInstanceEntityImpl.getWorkTimeInMillis());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link HistoricVariableInstanceEntityImpl}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getTaskLocalVariables()"})
  public void testGetTaskLocalVariables_givenArrayListAddHistoricVariableInstanceEntityImpl() {
    // Arrange
    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(new HistoricVariableInstanceEntityImpl());

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getTaskLocalVariables()"})
  public void testGetTaskLocalVariables_givenHistoricTaskInstanceEntityImpl_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new HistoricTaskInstanceEntityImpl().getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntity} {@link
   *       HistoricVariableInstanceEntity#getTaskId()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getTaskLocalVariables()"})
  public void testGetTaskLocalVariables_givenHistoricVariableInstanceEntityGetTaskIdReturnNull() {
    // Arrange
    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getTaskId()).thenReturn(null);
    when(historicVariableInstanceEntity.getId()).thenReturn("42");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntity);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualTaskLocalVariables =
        historicTaskInstanceEntityImpl.getTaskLocalVariables();

    // Assert
    verify(historicVariableInstanceEntity).getId();
    verify(historicVariableInstanceEntity).getTaskId();
    assertTrue(actualTaskLocalVariables.isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getTaskLocalVariables()"})
  public void testGetTaskLocalVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getTaskLocalVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getTaskLocalVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getTaskLocalVariables()"})
  public void testGetTaskLocalVariables_thenReturnSizeIsOne() {
    // Arrange
    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getValue()).thenReturn(JSONObject.NULL);
    when(historicVariableInstanceEntity.getName()).thenReturn("Name");
    when(historicVariableInstanceEntity.getTaskId()).thenReturn("42");
    when(historicVariableInstanceEntity.getId()).thenReturn("42");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntity);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualTaskLocalVariables =
        historicTaskInstanceEntityImpl.getTaskLocalVariables();

    // Assert
    verify(historicVariableInstanceEntity).getId();
    verify(historicVariableInstanceEntity).getValue();
    verify(historicVariableInstanceEntity).getName();
    verify(historicVariableInstanceEntity).getTaskId();
    assertEquals(1, actualTaskLocalVariables.size());
    assertTrue(actualTaskLocalVariables.containsKey("Name"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link HistoricVariableInstanceEntityImpl}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenArrayListAddHistoricVariableInstanceEntityImpl() {
    // Arrange
    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(new HistoricVariableInstanceEntityImpl());

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenHistoricTaskInstanceEntityImpl_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new HistoricTaskInstanceEntityImpl().getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntity} {@link
   *       HistoricVariableInstanceEntity#getTaskId()} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenHistoricVariableInstanceEntityGetTaskIdReturn42() {
    // Arrange
    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getTaskId()).thenReturn("42");
    when(historicVariableInstanceEntity.getId()).thenReturn("42");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntity);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables =
        historicTaskInstanceEntityImpl.getProcessVariables();

    // Assert
    verify(historicVariableInstanceEntity).getId();
    verify(historicVariableInstanceEntity).getTaskId();
    assertTrue(actualProcessVariables.isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricTaskInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnSizeIsOne() {
    // Arrange
    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getValue()).thenReturn(JSONObject.NULL);
    when(historicVariableInstanceEntity.getName()).thenReturn("Name");
    when(historicVariableInstanceEntity.getTaskId()).thenReturn(null);
    when(historicVariableInstanceEntity.getId()).thenReturn("42");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntity);

    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables =
        historicTaskInstanceEntityImpl.getProcessVariables();

    // Assert
    verify(historicVariableInstanceEntity).getId();
    verify(historicVariableInstanceEntity).getValue();
    verify(historicVariableInstanceEntity).getName();
    verify(historicVariableInstanceEntity).getTaskId();
    assertEquals(1, actualProcessVariables.size());
    assertTrue(actualProcessVariables.containsKey("Name"));
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceEntityImpl#HistoricTaskInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HistoricTaskInstanceEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_givenHistoricTaskInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricTaskInstanceEntityImpl().getQueryVariables());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HistoricTaskInstanceEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        new HistoricTaskInstanceEntityImpl();
    historicTaskInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicTaskInstanceEntityImpl.getQueryVariables().isEmpty());
  }
}
