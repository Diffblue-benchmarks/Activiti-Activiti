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
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.DelegationState;
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
   * Test {@link TaskQueryImpl#taskId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} TaskId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskId(String)"})
  public void testTaskId_when42_thenTaskQueryImplTaskIdIs42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskIdResult = taskQueryImpl.taskId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTaskId());
    assertSame(taskQueryImpl, actualTaskIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskId(String)"})
  public void testTaskId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskId(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskName(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} Name is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskName(String)"})
  public void testTaskName_whenName_thenTaskQueryImplNameIsName() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskNameResult = taskQueryImpl.taskName("Name");

    // Assert
    assertEquals("Name", taskQueryImpl.getName());
    assertSame(taskQueryImpl, actualTaskNameResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskName(String)"})
  public void testTaskName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskName(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} NameList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_given42_whenArrayListAdd42_thenTaskQueryImplNameListIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("42");
    nameList.add("Task name list is empty");

    // Act
    TaskQuery actualTaskNameInResult = taskQueryImpl.taskNameIn(nameList);

    // Assert
    assertSame(nameList, taskQueryImpl.getNameList());
    assertSame(taskQueryImpl, actualTaskNameInResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskNameLikeIgnoreCase {@code Name Like Ignore Case}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenTaskQueryImplTaskNameLikeIgnoreCaseNameLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskNameLikeIgnoreCase("Name Like Ignore Case");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskNameLike {@code Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenTaskQueryImplTaskNameLikeNameLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskNameLike("Name Like");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskName {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenTaskQueryImplTaskNameName() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskName("Name");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} NameList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenTaskQueryImpl_thenTaskQueryImplNameListIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act
    TaskQuery actualTaskNameInResult = taskQueryImpl.taskNameIn(nameList);

    // Assert
    assertSame(nameList, taskQueryImpl.getNameList());
    assertSame(taskQueryImpl, actualTaskNameInResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenTaskQueryImpl_whenArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskNameLikeIgnoreCase("Name Like Ignore Case");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_given42_whenArrayListAdd42_thenReturnTaskQueryImpl() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("42");
    nameList.add("Task name list is empty");

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskNameLike {@code Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenTaskQueryImplTaskNameLikeNameLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskNameLike("Name Like");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskName {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenTaskQueryImplTaskNameName() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskName("Name");

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>Then return {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenTaskQueryImpl_thenReturnTaskQueryImpl() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> nameList = new ArrayList<>();
    nameList.add("Task name list is empty");

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskNameInIgnoreCase(nameList));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenTaskQueryImpl_whenArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameLike(String)}.
   * <ul>
   *   <li>When {@code Name Like}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} NameLike is {@code Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskNameLike(String)"})
  public void testTaskNameLike_whenNameLike_thenTaskQueryImplNameLikeIsNameLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskNameLikeResult = taskQueryImpl.taskNameLike("Name Like");

    // Assert
    assertEquals("Name Like", taskQueryImpl.getNameLike());
    assertSame(taskQueryImpl, actualTaskNameLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskNameLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskNameLike(String)"})
  public void testTaskNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskNameLike(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameLikeIgnoreCase(String)"})
  public void testTaskNameLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskNameLikeIgnoreCaseResult = taskQueryImpl.taskNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", taskQueryImpl.getNameLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskNameLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskNameLikeIgnoreCase(String)"})
  public void testTaskNameLikeIgnoreCase_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskNameLikeIgnoreCase(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskDescription(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskDescription(String)"})
  public void testTaskDescription() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskDescriptionResult = taskQueryImpl
        .taskDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", taskQueryImpl.getDescription());
    assertSame(taskQueryImpl, actualTaskDescriptionResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDescription(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskDescription(String)"})
  public void testTaskDescription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskDescription(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskDescriptionLike(String)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} DescriptionLike is {@code Description Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDescriptionLike(String)"})
  public void testTaskDescriptionLike_thenTaskQueryImplDescriptionLikeIsDescriptionLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDescriptionLikeResult = taskQueryImpl.taskDescriptionLike("Description Like");

    // Assert
    assertEquals("Description Like", taskQueryImpl.getDescriptionLike());
    assertSame(taskQueryImpl, actualTaskDescriptionLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDescriptionLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDescriptionLike(String)"})
  public void testTaskDescriptionLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskDescriptionLike(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDescriptionLikeIgnoreCase(String)"})
  public void testTaskDescriptionLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDescriptionLikeIgnoreCaseResult = taskQueryImpl
        .taskDescriptionLikeIgnoreCase("Description Like Ignore Case");

    // Assert
    assertEquals("description like ignore case", taskQueryImpl.getDescriptionLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskDescriptionLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDescriptionLikeIgnoreCase(String)"})
  public void testTaskDescriptionLikeIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new TaskQueryImpl()).taskDescriptionLikeIgnoreCase(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskPriority(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskPriority(Integer)"})
  public void testTaskPriority_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskPriority(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskPriority(Integer)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} Priority intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskPriority(Integer)"})
  public void testTaskPriority_whenOne_thenTaskQueryImplPriorityIntValueIsOne() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskPriorityResult = taskQueryImpl.taskPriority(1);

    // Assert
    assertEquals(1, taskQueryImpl.getPriority().intValue());
    assertSame(taskQueryImpl, actualTaskPriorityResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskMinPriority(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskMinPriority(Integer)"})
  public void testTaskMinPriority_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskMinPriority(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskMinPriority(Integer)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} MinPriority intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskMinPriority(Integer)"})
  public void testTaskMinPriority_whenOne_thenTaskQueryImplMinPriorityIntValueIsOne() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskMinPriorityResult = taskQueryImpl.taskMinPriority(1);

    // Assert
    assertEquals(1, taskQueryImpl.getMinPriority().intValue());
    assertSame(taskQueryImpl, actualTaskMinPriorityResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskMaxPriority(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskMaxPriority(Integer)"})
  public void testTaskMaxPriority_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskMaxPriority(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskMaxPriority(Integer)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} MaxPriority intValue is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskMaxPriority(Integer)"})
  public void testTaskMaxPriority_whenThree_thenTaskQueryImplMaxPriorityIntValueIsThree() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskMaxPriorityResult = taskQueryImpl.taskMaxPriority(3);

    // Assert
    assertEquals(3, taskQueryImpl.getMaxPriority().intValue());
    assertSame(taskQueryImpl, actualTaskMaxPriorityResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssignee(String)}.
   * <ul>
   *   <li>When {@code Assignee}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} Assignee is {@code Assignee}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssignee(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskAssignee(String)"})
  public void testTaskAssignee_whenAssignee_thenTaskQueryImplAssigneeIsAssignee() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskAssigneeResult = taskQueryImpl.taskAssignee("Assignee");

    // Assert
    assertEquals("Assignee", taskQueryImpl.getAssignee());
    assertSame(taskQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssignee(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssignee(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskAssignee(String)"})
  public void testTaskAssignee_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskAssignee(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeLike(String)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} AssigneeLike is {@code Assignee Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskAssigneeLike(String)"})
  public void testTaskAssigneeLike_thenTaskQueryImplAssigneeLikeIsAssigneeLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskAssigneeLikeResult = taskQueryImpl.taskAssigneeLike("Assignee Like");

    // Assert
    assertEquals("Assignee Like", taskQueryImpl.getAssigneeLike());
    assertSame(taskQueryImpl, actualTaskAssigneeLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskAssigneeLike(String)"})
  public void testTaskAssigneeLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskAssigneeLike(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeLikeIgnoreCase(String)"})
  public void testTaskAssigneeLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskAssigneeLikeIgnoreCaseResult = taskQueryImpl
        .taskAssigneeLikeIgnoreCase("Assignee Like Ignore Case");

    // Assert
    assertEquals("assignee like ignore case", taskQueryImpl.getAssigneeLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskAssigneeLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeLikeIgnoreCase(String)"})
  public void testTaskAssigneeLikeIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskAssigneeLikeIgnoreCase(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskAssigneeLikeIgnoreCase("Assignee Like Ignore Case");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("42");
    assigneeIds.add("Task assignee list is empty");

    // Act
    TaskQuery actualTaskAssigneeIdsResult = taskQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, taskQueryImpl.getAssigneeIds());
    assertSame(taskQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskAssignee {@code Assignee}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenTaskQueryImplTaskAssigneeAssignee() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskAssignee("Assignee");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskAssigneeLike {@code Assignee Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenTaskQueryImplTaskAssigneeLikeAssigneeLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskAssigneeLike("Assignee Like");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} AssigneeIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenTaskQueryImpl_thenTaskQueryImplAssigneeIdsIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act
    TaskQuery actualTaskAssigneeIdsResult = taskQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, taskQueryImpl.getAssigneeIds());
    assertSame(taskQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenTaskQueryImpl_whenArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskOwner(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwner(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskOwner(String)"})
  public void testTaskOwner_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskOwner(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskOwner(String)}.
   * <ul>
   *   <li>When {@code Owner}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} Owner is {@code Owner}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwner(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskOwner(String)"})
  public void testTaskOwner_whenOwner_thenTaskQueryImplOwnerIsOwner() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskOwnerResult = taskQueryImpl.taskOwner("Owner");

    // Assert
    assertEquals("Owner", taskQueryImpl.getOwner());
    assertSame(taskQueryImpl, actualTaskOwnerResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskOwnerLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwnerLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskOwnerLike(String)"})
  public void testTaskOwnerLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskOwnerLike(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskOwnerLike(String)}.
   * <ul>
   *   <li>When {@code Owner Like}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} OwnerLike is {@code Owner Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwnerLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskOwnerLike(String)"})
  public void testTaskOwnerLike_whenOwnerLike_thenTaskQueryImplOwnerLikeIsOwnerLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskOwnerLikeResult = taskQueryImpl.taskOwnerLike("Owner Like");

    // Assert
    assertEquals("Owner Like", taskQueryImpl.getOwnerLike());
    assertSame(taskQueryImpl, actualTaskOwnerLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskOwnerLikeIgnoreCase(String)"})
  public void testTaskOwnerLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskOwnerLikeIgnoreCaseResult = taskQueryImpl.taskOwnerLikeIgnoreCase("Owner Like Ignore Case");

    // Assert
    assertEquals("owner like ignore case", taskQueryImpl.getOwnerLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskOwnerLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskOwnerLikeIgnoreCase(String)"})
  public void testTaskOwnerLikeIgnoreCase_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskOwnerLikeIgnoreCase(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskUnassigned()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskUnassigned()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskUnassigned()"})
  public void testTaskUnassigned() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskUnassignedResult = taskQueryImpl.taskUnassigned();

    // Assert
    assertTrue(taskQueryImpl.getUnassigned());
    assertSame(taskQueryImpl, actualTaskUnassignedResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDelegationState(DelegationState)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} DelegationStateString is {@code PENDING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDelegationState(DelegationState)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDelegationState(DelegationState)"})
  public void testTaskDelegationState_thenTaskQueryImplDelegationStateStringIsPending() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDelegationStateResult = taskQueryImpl.taskDelegationState(DelegationState.PENDING);

    // Assert
    assertTrue(actualTaskDelegationStateResult instanceof TaskQueryImpl);
    assertEquals("PENDING", taskQueryImpl.getDelegationStateString());
    assertEquals("PENDING", ((TaskQueryImpl) actualTaskDelegationStateResult).getDelegationStateString());
    assertEquals(DelegationState.PENDING, taskQueryImpl.getDelegationState());
    assertEquals(DelegationState.PENDING, ((TaskQueryImpl) actualTaskDelegationStateResult).getDelegationState());
    assertFalse(taskQueryImpl.getNoDelegationState());
    assertFalse(((TaskQueryImpl) actualTaskDelegationStateResult).getNoDelegationState());
    assertSame(actualTaskDelegationStateResult, ((TaskQueryImpl) actualTaskDelegationStateResult).getParameter());
  }

  /**
   * Test {@link TaskQueryImpl#taskDelegationState(DelegationState)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} DelegationStateString is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDelegationState(DelegationState)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDelegationState(DelegationState)"})
  public void testTaskDelegationState_whenNull_thenTaskQueryImplDelegationStateStringIsNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDelegationStateResult = taskQueryImpl.taskDelegationState(null);

    // Assert
    assertTrue(actualTaskDelegationStateResult instanceof TaskQueryImpl);
    assertNull(taskQueryImpl.getDelegationStateString());
    assertNull(((TaskQueryImpl) actualTaskDelegationStateResult).getDelegationStateString());
    assertNull(taskQueryImpl.getDelegationState());
    assertNull(((TaskQueryImpl) actualTaskDelegationStateResult).getDelegationState());
    assertTrue(taskQueryImpl.getNoDelegationState());
    assertTrue(((TaskQueryImpl) actualTaskDelegationStateResult).getNoDelegationState());
    assertSame(actualTaskDelegationStateResult, ((TaskQueryImpl) actualTaskDelegationStateResult).getParameter());
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01", new ArrayList<>());

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    assertTrue(taskQueryImpl.candidateGroups.isEmpty());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
    List<String> expectedCandidateGroups = actualTaskCandidateUserResult.candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups2() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskQueryImpl.taskCandidateUser(null, new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateUser(String)} with {@code candidateUser}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} CandidateUser is {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateUser(String)"})
  public void testTaskCandidateUserWithCandidateUser_thenTaskQueryImplCandidateUserIs20200301() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01");

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateUser(String)} with {@code candidateUser}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateUser(String)"})
  public void testTaskCandidateUserWithCandidateUser_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskCandidateUser(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskInvolvedUser(String)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} InvolvedUser is {@code Involved User}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskInvolvedUser(String)"})
  public void testTaskInvolvedUser_thenTaskQueryImplInvolvedUserIsInvolvedUser() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskInvolvedUserResult = taskQueryImpl.taskInvolvedUser("Involved User");

    // Assert
    assertEquals("Involved User", taskQueryImpl.getInvolvedUser());
    assertSame(taskQueryImpl, actualTaskInvolvedUserResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskInvolvedUser(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskInvolvedUser(String)"})
  public void testTaskInvolvedUser_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskInvolvedUser(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    TaskQueryImpl actualTaskInvolvedGroupsInResult = taskQueryImpl.taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, taskQueryImpl.getInvolvedGroups());
    assertSame(taskQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} InvolvedGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn_thenTaskQueryImplInvolvedGroupsIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    TaskQueryImpl actualTaskInvolvedGroupsInResult = taskQueryImpl.taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, taskQueryImpl.getInvolvedGroups());
    assertSame(taskQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskInvolvedGroupsIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>When {@code 2020-03-01}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} CandidateGroup is {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateGroup(String)"})
  public void testTaskCandidateGroup_when20200301_thenTaskQueryImplCandidateGroupIs20200301() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualTaskCandidateGroupResult = taskQueryImpl.taskCandidateGroup("2020-03-01");

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateGroup());
    assertEquals(1, taskQueryImpl.getCandidateGroups().size());
    assertSame(taskQueryImpl, actualTaskCandidateGroupResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCandidateGroup(String)"})
  public void testTaskCandidateGroup_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskCandidateGroup(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String)} with {@code userIdForCandidateAndAssignee}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssignee() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01");

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String)} with {@code userIdForCandidateAndAssignee}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssignee2() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateUser("2020-03-01");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateOrAssigned("2020-03-01"));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String)} with {@code userIdForCandidateAndAssignee}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssignee3() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateGroup("2020-03-01");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateOrAssigned("2020-03-01"));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)} with {@code userIdForCandidateAndAssignee}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String, List)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssigneeUsersGroups() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01",
        new ArrayList<>());

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    assertTrue(taskQueryImpl.candidateGroups.isEmpty());
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
    List<String> expectedCandidateGroups = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)} with {@code userIdForCandidateAndAssignee}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String, List)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssigneeUsersGroups2() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("foo");

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01", usersGroups);

    // Assert
    assertTrue(actualTaskCandidateOrAssignedResult instanceof TaskQueryImpl);
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    List<String> stringList2 = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertEquals(1, stringList2.size());
    assertEquals("foo", stringList2.get(0));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)} with {@code userIdForCandidateAndAssignee}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String, List)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssigneeUsersGroups3() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("42");
    usersGroups.add("foo");

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01", usersGroups);

    // Assert
    assertTrue(actualTaskCandidateOrAssignedResult instanceof TaskQueryImpl);
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(2, stringList.size());
    assertEquals("42", stringList.get(0));
    List<String> stringList2 = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertEquals(2, stringList2.size());
    assertEquals("42", stringList2.get(0));
    assertEquals("foo", stringList.get(1));
    assertEquals("foo", stringList2.get(1));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)} with {@code userIdForCandidateAndAssignee}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String, List)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssigneeUsersGroups4() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateUser("2020-03-01");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskQueryImpl.taskCandidateOrAssigned("2020-03-01", new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)} with {@code userIdForCandidateAndAssignee}, {@code usersGroups}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateOrAssigned(String, List)"})
  public void testTaskCandidateOrAssignedWithUserIdForCandidateAndAssigneeUsersGroups5() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateGroup("2020-03-01");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskQueryImpl.taskCandidateOrAssigned("2020-03-01", new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} {@link TaskQueryImpl#candidateGroups} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_given42_thenTaskQueryImplCandidateGroupsSizeIsTwo() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("Candidate group list is empty");

    // Act
    TaskQuery actualTaskCandidateGroupInResult = taskQueryImpl.taskCandidateGroupIn(candidateGroups);

    // Assert
    assertTrue(actualTaskCandidateGroupInResult instanceof TaskQueryImpl);
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(2, stringList.size());
    assertEquals("42", stringList.get(0));
    List<String> stringList2 = ((TaskQueryImpl) actualTaskCandidateGroupInResult).candidateGroups;
    assertEquals(2, stringList2.size());
    assertEquals("42", stringList2.get(0));
    assertEquals("Candidate group list is empty", stringList.get(1));
    assertEquals("Candidate group list is empty", stringList2.get(1));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl#TaskQueryImpl()} taskCandidateGroup {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_givenTaskQueryImplTaskCandidateGroup20200301() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    taskQueryImpl.taskCandidateGroup("2020-03-01");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateGroupIn(candidateGroups));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} {@link TaskQueryImpl#candidateGroups} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_thenTaskQueryImplCandidateGroupsSizeIsOne() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act
    TaskQuery actualTaskCandidateGroupInResult = taskQueryImpl.taskCandidateGroupIn(candidateGroups);

    // Assert
    assertTrue(actualTaskCandidateGroupInResult instanceof TaskQueryImpl);
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(1, stringList.size());
    assertEquals("Candidate group list is empty", stringList.get(0));
    List<String> stringList2 = ((TaskQueryImpl) actualTaskCandidateGroupInResult).candidateGroups;
    assertEquals(1, stringList2.size());
    assertEquals("Candidate group list is empty", stringList2.get(0));
  }

  /**
   * Test {@link TaskQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateGroupIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#taskTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskTenantId(String)"})
  public void testTaskTenantId_when42_thenTaskQueryImplTenantIdIs42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskTenantIdResult = taskQueryImpl.taskTenantId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTenantId());
    assertSame(taskQueryImpl, actualTaskTenantIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskTenantId(String)"})
  public void testTaskTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskTenantId(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} TenantIdLike is {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskTenantIdLike(String)"})
  public void testTaskTenantIdLike_thenTaskQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskTenantIdLikeResult = taskQueryImpl.taskTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", taskQueryImpl.getTenantIdLike());
    assertSame(taskQueryImpl, actualTaskTenantIdLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskTenantIdLike(String)"})
  public void testTaskTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TaskQueryImpl()).taskTenantIdLike(null));
  }

  /**
   * Test {@link TaskQueryImpl#taskWithoutTenantId()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskWithoutTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskWithoutTenantId()"})
  public void testTaskWithoutTenantId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskWithoutTenantIdResult = taskQueryImpl.taskWithoutTenantId();

    // Assert
    assertTrue(taskQueryImpl.isWithoutTenantId());
    assertSame(taskQueryImpl, actualTaskWithoutTenantIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskParentTaskId(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskParentTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskParentTaskId(String)"})
  public void testTaskParentTaskId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskParentTaskIdResult = taskQueryImpl.taskParentTaskId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTaskParentTaskId());
    assertSame(taskQueryImpl, actualTaskParentTaskIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceId(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualProcessInstanceIdResult = taskQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getProcessInstanceId());
    assertSame(taskQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Process instance id list is empty");

    // Act
    TaskQuery actualProcessInstanceIdInResult = taskQueryImpl.processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, taskQueryImpl.getProcessInstanceIds());
    assertSame(taskQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_givenNull_whenArrayListAddNull() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processInstanceIdIn(processInstanceIds));
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Then {@link TaskQueryImpl#TaskQueryImpl()} ProcessInstanceIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_thenTaskQueryImplProcessInstanceIdsIsArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("Process instance id list is empty");

    // Act
    TaskQuery actualProcessInstanceIdInResult = taskQueryImpl.processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, taskQueryImpl.getProcessInstanceIds());
    assertSame(taskQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processInstanceIdIn(new ArrayList<>()));
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceBusinessKey(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKey() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualProcessInstanceBusinessKeyResult = taskQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key");

    // Assert
    assertEquals("Process Instance Business Key", taskQueryImpl.getProcessInstanceBusinessKey());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceBusinessKeyLike(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceBusinessKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.processInstanceBusinessKeyLike(String)"})
  public void testProcessInstanceBusinessKeyLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualProcessInstanceBusinessKeyLikeResult = taskQueryImpl
        .processInstanceBusinessKeyLike("Process Instance Business Key Like");

    // Assert
    assertEquals("Process Instance Business Key Like", taskQueryImpl.getProcessInstanceBusinessKeyLike());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processInstanceBusinessKeyLikeIgnoreCase(String)"})
  public void testProcessInstanceBusinessKeyLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessInstanceBusinessKeyLikeIgnoreCaseResult = taskQueryImpl
        .processInstanceBusinessKeyLikeIgnoreCase("Process Instance Business Key Like Ignore Case");

    // Assert
    assertEquals("process instance business key like ignore case",
        taskQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#executionId(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#executionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.executionId(String)"})
  public void testExecutionId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQueryImpl actualExecutionIdResult = taskQueryImpl.executionId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getExecutionId());
    assertSame(taskQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskCreatedOn(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCreatedOn(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQueryImpl TaskQueryImpl.taskCreatedOn(Date)"})
  public void testTaskCreatedOn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedOn(createTime));
    assertSame(createTime, taskQueryImpl.getCreateTime());
  }

  /**
   * Test {@link TaskQueryImpl#taskCreatedBefore(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCreatedBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCreatedBefore(Date)"})
  public void testTaskCreatedBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date before = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedBefore(before));
    assertSame(before, taskQueryImpl.getCreateTimeBefore());
  }

  /**
   * Test {@link TaskQueryImpl#taskCreatedAfter(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCreatedAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCreatedAfter(Date)"})
  public void testTaskCreatedAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date after = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedAfter(after));
    assertSame(after, taskQueryImpl.getCreateTimeAfter());
  }

  /**
   * Test {@link TaskQueryImpl#taskCategory(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskCategory(String)"})
  public void testTaskCategory() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskCategoryResult = taskQueryImpl.taskCategory("Category");

    // Assert
    assertEquals("Category", taskQueryImpl.getCategory());
    assertSame(taskQueryImpl, actualTaskCategoryResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDefinitionKey(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDefinitionKey(String)"})
  public void testTaskDefinitionKey() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDefinitionKeyResult = taskQueryImpl.taskDefinitionKey("Key");

    // Assert
    assertEquals("Key", taskQueryImpl.getKey());
    assertSame(taskQueryImpl, actualTaskDefinitionKeyResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskDefinitionKeyLike(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDefinitionKeyLike(String)"})
  public void testTaskDefinitionKeyLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDefinitionKeyLikeResult = taskQueryImpl.taskDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", taskQueryImpl.getKeyLike());
    assertSame(taskQueryImpl, actualTaskDefinitionKeyLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueEquals(String, Object)"})
  public void testTaskVariableValueEqualsWithVariableNameVariableValue() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueEqualsResult = taskQueryImpl.taskVariableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueEquals(Object)} with {@code variableValue}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueEquals(Object)"})
  public void testTaskVariableValueEqualsWithVariableValue() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueEqualsResult = taskQueryImpl.taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueEqualsIgnoreCase(String, String)"})
  public void testTaskVariableValueEqualsIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueEqualsIgnoreCaseResult = taskQueryImpl.taskVariableValueEqualsIgnoreCase("Name",
        "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueNotEqualsIgnoreCase(String, String)"})
  public void testTaskVariableValueNotEqualsIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueNotEqualsIgnoreCaseResult = taskQueryImpl
        .taskVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueNotEquals(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueNotEquals(String, Object)"})
  public void testTaskVariableValueNotEquals() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueNotEqualsResult = taskQueryImpl.taskVariableValueNotEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueGreaterThan(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueGreaterThan(String, Object)"})
  public void testTaskVariableValueGreaterThan() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueGreaterThanResult = taskQueryImpl.taskVariableValueGreaterThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueGreaterThanResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueGreaterThanOrEqual(String, Object)"})
  public void testTaskVariableValueGreaterThanOrEqual() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueGreaterThanOrEqualResult = taskQueryImpl
        .taskVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueLessThan(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueLessThan(String, Object)"})
  public void testTaskVariableValueLessThan() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueLessThanResult = taskQueryImpl.taskVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLessThanResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueLessThanOrEqual(String, Object)"})
  public void testTaskVariableValueLessThanOrEqual() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueLessThanOrEqualResult = taskQueryImpl.taskVariableValueLessThanOrEqual("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueLike(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueLike(String, String)"})
  public void testTaskVariableValueLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueLikeResult = taskQueryImpl.taskVariableValueLike("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskVariableValueLikeIgnoreCase(String, String)"})
  public void testTaskVariableValueLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueLikeIgnoreCaseResult = taskQueryImpl.taskVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueEquals(String, Object)"})
  public void testProcessVariableValueEqualsWithVariableNameVariableValue() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueEqualsResult = taskQueryImpl.processVariableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueEquals(Object)} with {@code variableValue}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueEquals(Object)"})
  public void testProcessVariableValueEqualsWithVariableValue() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueEqualsResult = taskQueryImpl.processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueNotEquals(String, Object)"})
  public void testProcessVariableValueNotEquals() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueNotEqualsResult = taskQueryImpl.processVariableValueNotEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueEqualsIgnoreCase(String, String)"})
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueEqualsIgnoreCaseResult = taskQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueNotEqualsIgnoreCase(String, String)"})
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = taskQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueGreaterThan(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueGreaterThan(String, Object)"})
  public void testProcessVariableValueGreaterThan() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueGreaterThanResult = taskQueryImpl.processVariableValueGreaterThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueGreaterThanOrEqual(String, Object)"})
  public void testProcessVariableValueGreaterThanOrEqual() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueGreaterThanOrEqualResult = taskQueryImpl
        .processVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueLessThan(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueLessThan(String, Object)"})
  public void testProcessVariableValueLessThan() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueLessThanResult = taskQueryImpl.processVariableValueLessThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueLessThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueLessThanOrEqual(String, Object)"})
  public void testProcessVariableValueLessThanOrEqual() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueLessThanOrEqualResult = taskQueryImpl
        .processVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueLike(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueLike(String, String)"})
  public void testProcessVariableValueLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueLikeResult = taskQueryImpl.processVariableValueLike("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#processVariableValueLikeIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processVariableValueLikeIgnoreCase(String, String)"})
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueLikeIgnoreCaseResult = taskQueryImpl.processVariableValueLikeIgnoreCase("Name",
        "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKey(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionKeyResult = taskQueryImpl.processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", taskQueryImpl.getProcessDefinitionKey());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKeyLike(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionKeyLikeResult = taskQueryImpl
        .processDefinitionKeyLike("Process Definition Key Like");

    // Assert
    assertEquals("Process Definition Key Like", taskQueryImpl.getProcessDefinitionKeyLike());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKeyLikeIgnoreCase(String)"})
  public void testProcessDefinitionKeyLikeIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionKeyLikeIgnoreCaseResult = taskQueryImpl
        .processDefinitionKeyLikeIgnoreCase("Process Definition Key Like Ignore Case");

    // Assert
    assertEquals("process definition key like ignore case", taskQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyLikeIgnoreCaseResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_given42_whenArrayListAdd42() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    TaskQuery actualProcessDefinitionKeyInResult = taskQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, taskQueryImpl.getProcessDefinitionKeys());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    TaskQuery actualProcessDefinitionKeyInResult = taskQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, taskQueryImpl.getProcessDefinitionKeys());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_whenArrayList() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    TaskQuery actualProcessDefinitionKeyInResult = taskQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, taskQueryImpl.getProcessDefinitionKeys());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionId(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionIdResult = taskQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getProcessDefinitionId());
    assertSame(taskQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionName(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionNameResult = taskQueryImpl.processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", taskQueryImpl.getProcessDefinitionName());
    assertSame(taskQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link TaskQueryImpl#processDefinitionNameLike(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.processDefinitionNameLike(String)"})
  public void testProcessDefinitionNameLike() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessDefinitionNameLikeResult = taskQueryImpl
        .processDefinitionNameLike("Process Definition Name Like");

    // Assert
    assertEquals("Process Definition Name Like", taskQueryImpl.getProcessDefinitionNameLike());
    assertSame(taskQueryImpl, actualProcessDefinitionNameLikeResult);
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
   * Test {@link TaskQueryImpl#deploymentId(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.deploymentId(String)"})
  public void testDeploymentId() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualDeploymentIdResult = taskQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getDeploymentId());
    assertSame(taskQueryImpl, actualDeploymentIdResult);
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
   * Test {@link TaskQueryImpl#taskDueDate(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDueDate(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDueDate(Date)"})
  public void testTaskDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueDate(dueDate));
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
   * Test {@link TaskQueryImpl#taskDueBefore(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDueBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDueBefore(Date)"})
  public void testTaskDueBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueBefore(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueBefore());
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
   * Test {@link TaskQueryImpl#taskDueAfter(Date)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#taskDueAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.taskDueAfter(Date)"})
  public void testTaskDueAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueAfter(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueAfter());
  }

  /**
   * Test {@link TaskQueryImpl#withoutDueDate()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#withoutDueDate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.withoutDueDate()"})
  public void testWithoutDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualWithoutDueDateResult = taskQueryImpl.withoutDueDate();

    // Assert
    assertTrue(taskQueryImpl.isWithoutDueDate());
    assertSame(taskQueryImpl, actualWithoutDueDateResult);
  }

  /**
   * Test {@link TaskQueryImpl#withoutTaskDueDate()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#withoutTaskDueDate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.withoutTaskDueDate()"})
  public void testWithoutTaskDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualWithoutTaskDueDateResult = taskQueryImpl.withoutTaskDueDate();

    // Assert
    assertTrue(taskQueryImpl.isWithoutDueDate());
    assertSame(taskQueryImpl, actualWithoutTaskDueDateResult);
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
   * Test {@link TaskQueryImpl#suspended()}.
   * <p>
   * Method under test: {@link TaskQueryImpl#suspended()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.suspended()"})
  public void testSuspended() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.suspended());
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
   * Test {@link TaskQueryImpl#locale(String)}.
   * <p>
   * Method under test: {@link TaskQueryImpl#locale(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TaskQuery TaskQueryImpl.locale(String)"})
  public void testLocale() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualLocaleResult = taskQueryImpl.locale("en");

    // Assert
    assertEquals("en", taskQueryImpl.getLocale());
    assertSame(taskQueryImpl, actualLocaleResult);
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
