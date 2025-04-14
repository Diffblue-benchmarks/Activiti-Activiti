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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TaskQueryImplDiffblueTest {
  /**
   * Test {@link TaskQueryImpl#TaskQueryImpl()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#TaskQueryImpl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TaskQueryImpl.<init>()"})
  public void testNewTaskQueryImpl() {
    // Arrange and Act
    TaskQueryImpl actualTaskQueryImpl = new TaskQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualTaskQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualTaskQueryImpl.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualTaskQueryImpl.getMssqlOrDB2OrderBy());
    assertNull(actualTaskQueryImpl.getMaxPriority());
    assertNull(actualTaskQueryImpl.getMinPriority());
    assertNull(actualTaskQueryImpl.getPriority());
    assertNull(actualTaskQueryImpl.getTaskVariablesLimit());
    assertNull(actualTaskQueryImpl.getDatabaseType());
    assertNull(actualTaskQueryImpl.getAssignee());
    assertNull(actualTaskQueryImpl.getAssigneeLike());
    assertNull(actualTaskQueryImpl.getAssigneeLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getCandidateGroup());
    assertNull(actualTaskQueryImpl.getCandidateUser());
    assertNull(actualTaskQueryImpl.getCategory());
    assertNull(actualTaskQueryImpl.getDelegationStateString());
    assertNull(actualTaskQueryImpl.getDeploymentId());
    assertNull(actualTaskQueryImpl.getDescription());
    assertNull(actualTaskQueryImpl.getDescriptionLike());
    assertNull(actualTaskQueryImpl.getDescriptionLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getExecutionId());
    assertNull(actualTaskQueryImpl.getInvolvedUser());
    assertNull(actualTaskQueryImpl.getKey());
    assertNull(actualTaskQueryImpl.getKeyLike());
    assertNull(actualTaskQueryImpl.getLocale());
    assertNull(actualTaskQueryImpl.getName());
    assertNull(actualTaskQueryImpl.getNameLike());
    assertNull(actualTaskQueryImpl.getNameLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getOwner());
    assertNull(actualTaskQueryImpl.getOwnerLike());
    assertNull(actualTaskQueryImpl.getOwnerLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getProcessDefinitionId());
    assertNull(actualTaskQueryImpl.getProcessDefinitionKey());
    assertNull(actualTaskQueryImpl.getProcessDefinitionKeyLike());
    assertNull(actualTaskQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getProcessDefinitionName());
    assertNull(actualTaskQueryImpl.getProcessDefinitionNameLike());
    assertNull(actualTaskQueryImpl.getProcessInstanceBusinessKey());
    assertNull(actualTaskQueryImpl.getProcessInstanceBusinessKeyLike());
    assertNull(actualTaskQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualTaskQueryImpl.getProcessInstanceId());
    assertNull(actualTaskQueryImpl.getTaskId());
    assertNull(actualTaskQueryImpl.getTaskParentTaskId());
    assertNull(actualTaskQueryImpl.getTenantId());
    assertNull(actualTaskQueryImpl.getTenantIdLike());
    assertNull(actualTaskQueryImpl.getUserIdForCandidateAndAssignee());
    assertNull(actualTaskQueryImpl.orderBy);
    assertNull(actualTaskQueryImpl.getCreateTime());
    assertNull(actualTaskQueryImpl.getCreateTimeAfter());
    assertNull(actualTaskQueryImpl.getCreateTimeBefore());
    assertNull(actualTaskQueryImpl.getDueAfter());
    assertNull(actualTaskQueryImpl.getDueBefore());
    assertNull(actualTaskQueryImpl.getDueDate());
    assertNull(actualTaskQueryImpl.getAssigneeIds());
    assertNull(actualTaskQueryImpl.getCandidateGroups());
    assertNull(actualTaskQueryImpl.getDeploymentIds());
    assertNull(actualTaskQueryImpl.getInvolvedGroups());
    assertNull(actualTaskQueryImpl.getNameList());
    assertNull(actualTaskQueryImpl.getNameListIgnoreCase());
    assertNull(actualTaskQueryImpl.getProcessCategoryInList());
    assertNull(actualTaskQueryImpl.getProcessCategoryNotInList());
    assertNull(actualTaskQueryImpl.getProcessDefinitionKeys());
    assertNull(actualTaskQueryImpl.getProcessInstanceIds());
    assertNull(actualTaskQueryImpl.candidateGroups);
    assertNull(actualTaskQueryImpl.nullHandlingOnOrder);
    assertNull(actualTaskQueryImpl.resultType);
    assertNull(actualTaskQueryImpl.currentOrQueryObject);
    assertNull(actualTaskQueryImpl.commandContext);
    assertNull(actualTaskQueryImpl.commandExecutor);
    assertNull(actualTaskQueryImpl.getSuspensionState());
    assertNull(actualTaskQueryImpl.orderProperty);
    assertNull(actualTaskQueryImpl.getDelegationState());
    assertEquals(0, actualTaskQueryImpl.getFirstResult());
    assertEquals(1, actualTaskQueryImpl.getFirstRow());
    assertFalse(actualTaskQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualTaskQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualTaskQueryImpl.getExcludeSubtasks());
    assertFalse(actualTaskQueryImpl.getNoDelegationState());
    assertFalse(actualTaskQueryImpl.getUnassigned());
    assertFalse(actualTaskQueryImpl.isBothCandidateAndAssigned());
    assertFalse(actualTaskQueryImpl.isIncludeProcessVariables());
    assertFalse(actualTaskQueryImpl.isIncludeTaskLocalVariables());
    assertFalse(actualTaskQueryImpl.isOrActive());
    assertFalse(actualTaskQueryImpl.isWithoutDueDate());
    assertFalse(actualTaskQueryImpl.isWithoutTenantId());
    assertFalse(actualTaskQueryImpl.withLocalizationFallback);
    assertTrue(actualTaskQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualTaskQueryImpl.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualTaskQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualTaskQueryImpl.getMaxResults());
    assertSame(actualTaskQueryImpl, actualTaskQueryImpl.getParameter());
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("42");
    processCategoryInList.add("Process category list is empty");

    // Act
    TaskQuery actualProcessCategoryInResult = taskQueryImpl.processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, taskQueryImpl.getProcessCategoryInList());
    assertSame(taskQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processCategoryIn(processCategoryInList));
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} ProcessCategoryInList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_thenTaskQueryImplProcessCategoryInListIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("Process category list is empty");

    // Act
    TaskQuery actualProcessCategoryInResult = taskQueryImpl.processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, taskQueryImpl.getProcessCategoryInList());
    assertSame(taskQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processCategoryIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("42");
    processCategoryNotInList.add("Process category list is empty");

    // Act
    TaskQuery actualProcessCategoryNotInResult = taskQueryImpl.processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, taskQueryImpl.getProcessCategoryNotInList());
    assertSame(taskQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskQueryImpl.processCategoryNotIn(processCategoryNotInList));
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} ProcessCategoryNotInList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_thenTaskQueryImplProcessCategoryNotInListIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("Process category list is empty");

    // Act
    TaskQuery actualProcessCategoryNotInResult = taskQueryImpl.processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, taskQueryImpl.getProcessCategoryNotInList());
    assertSame(taskQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processCategoryNotIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    TaskQuery actualDeploymentIdInResult = taskQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, taskQueryImpl.getDeploymentIds());
    assertSame(taskQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link TaskQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    TaskQuery actualDeploymentIdInResult = taskQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, taskQueryImpl.getDeploymentIds());
    assertSame(taskQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link TaskQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_whenArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    TaskQuery actualDeploymentIdInResult = taskQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, taskQueryImpl.getDeploymentIds());
    assertSame(taskQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link TaskQueryImpl#dueDate(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#dueDate(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.dueDate(Date)"})
  public void testDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueDate(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueDate());
  }

  /**
   * Test {@link TaskQueryImpl#dueBefore(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#dueBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.dueBefore(Date)"})
  public void testDueBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueBefore(dueBefore));
    assertSame(dueBefore, taskQueryImpl.getDueBefore());
  }

  /**
   * Test {@link TaskQueryImpl#dueAfter(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#dueAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.dueAfter(Date)"})
  public void testDueAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueAfter(dueAfter));
    assertSame(dueAfter, taskQueryImpl.getDueAfter());
  }

  /**
   * Test {@link TaskQueryImpl#excludeSubtasks()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#excludeSubtasks()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.excludeSubtasks()"})
  public void testExcludeSubtasks() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualExcludeSubtasksResult = taskQueryImpl.excludeSubtasks();

    // Assert
    assertTrue(taskQueryImpl.getExcludeSubtasks());
    assertSame(taskQueryImpl, actualExcludeSubtasksResult);
  }

  /**
   * Test {@link TaskQueryImpl#active()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#active()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.active()"})
  public void testActive() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.active());
  }

  /**
   * Test {@link TaskQueryImpl#includeTaskLocalVariables()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#includeTaskLocalVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.includeTaskLocalVariables()"})
  public void testIncludeTaskLocalVariables() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualIncludeTaskLocalVariablesResult = taskQueryImpl.includeTaskLocalVariables();

    // Assert
    assertTrue(taskQueryImpl.isIncludeTaskLocalVariables());
    assertSame(taskQueryImpl, actualIncludeTaskLocalVariablesResult);
  }

  /**
   * Test {@link TaskQueryImpl#includeProcessVariables()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#includeProcessVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.includeProcessVariables()"})
  public void testIncludeProcessVariables() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualIncludeProcessVariablesResult = taskQueryImpl.includeProcessVariables();

    // Assert
    assertTrue(taskQueryImpl.isIncludeProcessVariables());
    assertSame(taskQueryImpl, actualIncludeProcessVariablesResult);
  }

  /**
   * Test {@link TaskQueryImpl#limitTaskVariables(Integer)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.limitTaskVariables(Integer)"})
  public void testLimitTaskVariables() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualLimitTaskVariablesResult = taskQueryImpl.limitTaskVariables(1);

    // Assert
    assertEquals(1, taskQueryImpl.getTaskVariablesLimit().intValue());
    assertSame(taskQueryImpl, actualLimitTaskVariablesResult);
  }

  /**
   * Test {@link TaskQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#getCandidateGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List TaskQueryImpl.getCandidateGroups()"})
  public void testGetCandidateGroups_givenTaskQueryImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TaskQueryImpl()).getCandidateGroups());
  }

  /**
   * Test {@link TaskQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#getCandidateGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List TaskQueryImpl.getCandidateGroups()"})
  public void testGetCandidateGroups_thenReturnSizeIsOne() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateGroup("2020-03-01");

    // Act
    List<String> actualCandidateGroups = taskQueryImpl.getCandidateGroups();

    // Assert
    assertEquals(1, actualCandidateGroups.size());
    assertEquals("2020-03-01", actualCandidateGroups.get(0));
  }

  /**
   * Test {@link TaskQueryImpl#or()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#or()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.or()"})
  public void testOr() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualOrResult = taskQueryImpl.or();

    // Assert
    assertTrue(taskQueryImpl.isOrActive());
    assertSame(taskQueryImpl, actualOrResult);
  }

  /**
   * Test {@link TaskQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#endOr()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.endOr()"})
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new TaskQueryImpl()).endOr());
  }

  /**
   * Test {@link TaskQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TaskQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAsc() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new TaskQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link TaskQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TaskQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", taskQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link TaskQueryImpl#getDelegationStateString()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#getDelegationStateString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TaskQueryImpl.getDelegationStateString()"})
  public void testGetDelegationStateString() {
    // Arrange, Act and Assert
    assertNull((new TaskQueryImpl()).getDelegationStateString());
  }
}
