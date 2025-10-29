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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskQueryImplDiffblueTest {
  @InjectMocks
  private TaskQueryImpl taskQueryImpl;

  /**
   * Method under test: {@link TaskQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId() {
    // Arrange and Act
    TaskQueryImpl actualTaskIdResult = taskQueryImpl.taskId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTaskId());
    assertSame(taskQueryImpl, actualTaskIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskId(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskName(String)}
   */
  @Test
  public void testTaskName() {
    // Arrange and Act
    TaskQueryImpl actualTaskNameResult = taskQueryImpl.taskName("Name");

    // Assert
    assertEquals("Name", taskQueryImpl.getName());
    assertSame(taskQueryImpl, actualTaskNameResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskName(String)}
   */
  @Test
  public void testTaskName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskName(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameInIgnoreCase(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameLike(String)}
   */
  @Test
  public void testTaskNameLike() {
    // Arrange and Act
    TaskQueryImpl actualTaskNameLikeResult = taskQueryImpl.taskNameLike("Name Like");

    // Assert
    assertEquals("Name Like", taskQueryImpl.getNameLike());
    assertSame(taskQueryImpl, actualTaskNameLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameLike(String)}
   */
  @Test
  public void testTaskNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameLike(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskNameLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskNameLikeIgnoreCaseResult = taskQueryImpl.taskNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", taskQueryImpl.getNameLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskNameLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskNameLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskNameLikeIgnoreCase(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDescription(String)}
   */
  @Test
  public void testTaskDescription() {
    // Arrange and Act
    TaskQueryImpl actualTaskDescriptionResult = taskQueryImpl
        .taskDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", taskQueryImpl.getDescription());
    assertSame(taskQueryImpl, actualTaskDescriptionResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDescription(String)}
   */
  @Test
  public void testTaskDescription2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskDescription(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  public void testTaskDescriptionLike() {
    // Arrange and Act
    TaskQuery actualTaskDescriptionLikeResult = taskQueryImpl.taskDescriptionLike("Description Like");

    // Assert
    assertEquals("Description Like", taskQueryImpl.getDescriptionLike());
    assertSame(taskQueryImpl, actualTaskDescriptionLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  public void testTaskDescriptionLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskDescriptionLike(null));
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskDescriptionLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskDescriptionLikeIgnoreCaseResult = taskQueryImpl
        .taskDescriptionLikeIgnoreCase("Description Like Ignore Case");

    // Assert
    assertEquals("description like ignore case", taskQueryImpl.getDescriptionLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskDescriptionLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskDescriptionLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskDescriptionLikeIgnoreCase(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskPriority(Integer)}
   */
  @Test
  public void testTaskPriority() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskPriorityResult = taskQueryImpl.taskPriority(1);

    // Assert
    assertEquals(1, taskQueryImpl.getPriority().intValue());
    assertSame(taskQueryImpl, actualTaskPriorityResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  public void testTaskMinPriority() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskMinPriorityResult = taskQueryImpl.taskMinPriority(1);

    // Assert
    assertEquals(1, taskQueryImpl.getMinPriority().intValue());
    assertSame(taskQueryImpl, actualTaskMinPriorityResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  public void testTaskMaxPriority() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskMaxPriorityResult = taskQueryImpl.taskMaxPriority(3);

    // Assert
    assertEquals(3, taskQueryImpl.getMaxPriority().intValue());
    assertSame(taskQueryImpl, actualTaskMaxPriorityResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssignee(String)}
   */
  @Test
  public void testTaskAssignee() {
    // Arrange and Act
    TaskQueryImpl actualTaskAssigneeResult = taskQueryImpl.taskAssignee("Assignee");

    // Assert
    assertEquals("Assignee", taskQueryImpl.getAssignee());
    assertSame(taskQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssignee(String)}
   */
  @Test
  public void testTaskAssignee2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssignee(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  public void testTaskAssigneeLike() {
    // Arrange and Act
    TaskQueryImpl actualTaskAssigneeLikeResult = taskQueryImpl.taskAssigneeLike("Assignee Like");

    // Assert
    assertEquals("Assignee Like", taskQueryImpl.getAssigneeLike());
    assertSame(taskQueryImpl, actualTaskAssigneeLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  public void testTaskAssigneeLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeLike(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskAssigneeLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskAssigneeLikeIgnoreCaseResult = taskQueryImpl
        .taskAssigneeLikeIgnoreCase("Assignee Like Ignore Case");

    // Assert
    assertEquals("assignee like ignore case", taskQueryImpl.getAssigneeLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskAssigneeLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskAssigneeLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeLikeIgnoreCase(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskAssigneeIds(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwner(String)}
   */
  @Test
  public void testTaskOwner() {
    // Arrange and Act
    TaskQueryImpl actualTaskOwnerResult = taskQueryImpl.taskOwner("Owner");

    // Assert
    assertEquals("Owner", taskQueryImpl.getOwner());
    assertSame(taskQueryImpl, actualTaskOwnerResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwner(String)}
   */
  @Test
  public void testTaskOwner2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskOwner(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwnerLike(String)}
   */
  @Test
  public void testTaskOwnerLike() {
    // Arrange and Act
    TaskQueryImpl actualTaskOwnerLikeResult = taskQueryImpl.taskOwnerLike("Owner Like");

    // Assert
    assertEquals("Owner Like", taskQueryImpl.getOwnerLike());
    assertSame(taskQueryImpl, actualTaskOwnerLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwnerLike(String)}
   */
  @Test
  public void testTaskOwnerLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskOwnerLike(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskOwnerLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskOwnerLikeIgnoreCaseResult = taskQueryImpl.taskOwnerLikeIgnoreCase("Owner Like Ignore Case");

    // Assert
    assertEquals("owner like ignore case", taskQueryImpl.getOwnerLikeIgnoreCase());
    assertSame(taskQueryImpl, actualTaskOwnerLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskOwnerLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskOwnerLikeIgnoreCase(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskUnassigned()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#taskDelegationState(DelegationState)}
   */
  @Test
  public void testTaskDelegationState() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskDelegationStateResult = taskQueryImpl.taskDelegationState(DelegationState.PENDING);

    // Assert
    assertEquals("PENDING", taskQueryImpl.getDelegationStateString());
    assertEquals(DelegationState.PENDING, taskQueryImpl.getDelegationState());
    assertSame(taskQueryImpl, actualTaskDelegationStateResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUser() {
    // Arrange and Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01");

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUser2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateUser(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser3() {
    // Arrange and Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01", new ArrayList<>());

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    assertTrue(taskQueryImpl.candidateGroups.isEmpty());
    List<String> expectedCandidateGroups = actualTaskCandidateUserResult.candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskQueryImpl.taskCandidateUser(null, new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser5() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("foo");

    // Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    List<String> expectedCandidateGroups = actualTaskCandidateUserResult.candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser6() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("42");
    usersGroups.add("foo");

    // Act
    TaskQueryImpl actualTaskCandidateUserResult = taskQueryImpl.taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getCandidateUser());
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(2, stringList.size());
    assertEquals("42", stringList.get(0));
    assertEquals("foo", stringList.get(1));
    List<String> expectedCandidateGroups = actualTaskCandidateUserResult.candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  public void testTaskInvolvedUser() {
    // Arrange and Act
    TaskQueryImpl actualTaskInvolvedUserResult = taskQueryImpl.taskInvolvedUser("Involved User");

    // Assert
    assertEquals("Involved User", taskQueryImpl.getInvolvedUser());
    assertSame(taskQueryImpl, actualTaskInvolvedUserResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  public void testTaskInvolvedUser2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskInvolvedUser(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskInvolvedGroupsIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup() {
    // Arrange and Act
    TaskQueryImpl actualTaskCandidateGroupResult = taskQueryImpl.taskCandidateGroup("2020-03-01");

    // Assert
    List<String> candidateGroups = taskQueryImpl.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("2020-03-01", candidateGroups.get(0));
    assertEquals("2020-03-01", taskQueryImpl.getCandidateGroup());
    assertSame(taskQueryImpl, actualTaskCandidateGroupResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateGroup(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateOrAssigned(String)}
   */
  @Test
  public void testTaskCandidateOrAssigned() {
    // Arrange and Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01");

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  public void testTaskCandidateOrAssigned2() {
    // Arrange and Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01",
        new ArrayList<>());

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    assertTrue(taskQueryImpl.candidateGroups.isEmpty());
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    List<String> expectedCandidateGroups = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  public void testTaskCandidateOrAssigned3() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("foo");

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    List<String> expectedCandidateGroups = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}
   */
  @Test
  public void testTaskCandidateOrAssigned4() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("42");
    usersGroups.add("foo");

    // Act
    TaskQuery actualTaskCandidateOrAssignedResult = taskQueryImpl.taskCandidateOrAssigned("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", taskQueryImpl.getUserIdForCandidateAndAssignee());
    List<String> stringList = taskQueryImpl.candidateGroups;
    assertEquals(2, stringList.size());
    assertEquals("42", stringList.get(0));
    assertEquals("foo", stringList.get(1));
    assertTrue(taskQueryImpl.isBothCandidateAndAssigned());
    List<String> expectedCandidateGroups = ((TaskQueryImpl) actualTaskCandidateOrAssignedResult).candidateGroups;
    assertSame(expectedCandidateGroups, taskQueryImpl.getCandidateGroups());
    assertSame(taskQueryImpl, actualTaskCandidateOrAssignedResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskCandidateGroupIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskTenantId(String)}
   */
  @Test
  public void testTaskTenantId() {
    // Arrange and Act
    TaskQuery actualTaskTenantIdResult = taskQueryImpl.taskTenantId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTenantId());
    assertSame(taskQueryImpl, actualTaskTenantIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskTenantId(String)}
   */
  @Test
  public void testTaskTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskTenantId(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike() {
    // Arrange and Act
    TaskQuery actualTaskTenantIdLikeResult = taskQueryImpl.taskTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", taskQueryImpl.getTenantIdLike());
    assertSame(taskQueryImpl, actualTaskTenantIdLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.taskTenantIdLike(null));
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskWithoutTenantId()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#taskParentTaskId(String)}
   */
  @Test
  public void testTaskParentTaskId() {
    // Arrange and Act
    TaskQuery actualTaskParentTaskIdResult = taskQueryImpl.taskParentTaskId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getTaskParentTaskId());
    assertSame(taskQueryImpl, actualTaskParentTaskIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    TaskQueryImpl actualProcessInstanceIdResult = taskQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getProcessInstanceId());
    assertSame(taskQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processInstanceIdIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    TaskQueryImpl actualProcessInstanceBusinessKeyResult = taskQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key");

    // Assert
    assertEquals("Process Instance Business Key", taskQueryImpl.getProcessInstanceBusinessKey());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processInstanceBusinessKeyLike(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLike() {
    // Arrange and Act
    TaskQueryImpl actualProcessInstanceBusinessKeyLikeResult = taskQueryImpl
        .processInstanceBusinessKeyLike("Process Instance Business Key Like");

    // Assert
    assertEquals("Process Instance Business Key Like", taskQueryImpl.getProcessInstanceBusinessKeyLike());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualProcessInstanceBusinessKeyLikeIgnoreCaseResult = taskQueryImpl
        .processInstanceBusinessKeyLikeIgnoreCase("Process Instance Business Key Like Ignore Case");

    // Assert
    assertEquals("process instance business key like ignore case",
        taskQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertSame(taskQueryImpl, actualProcessInstanceBusinessKeyLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    TaskQueryImpl actualExecutionIdResult = taskQueryImpl.executionId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getExecutionId());
    assertSame(taskQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCreatedOn(Date)}
   */
  @Test
  public void testTaskCreatedOn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedOn(createTime));
    assertSame(createTime, taskQueryImpl.getCreateTime());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCreatedBefore(Date)}
   */
  @Test
  public void testTaskCreatedBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date before = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedBefore(before));
    assertSame(before, taskQueryImpl.getCreateTimeBefore());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCreatedAfter(Date)}
   */
  @Test
  public void testTaskCreatedAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date after = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskCreatedAfter(after));
    assertSame(after, taskQueryImpl.getCreateTimeAfter());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskCategory(String)}
   */
  @Test
  public void testTaskCategory() {
    // Arrange and Act
    TaskQuery actualTaskCategoryResult = taskQueryImpl.taskCategory("Category");

    // Assert
    assertEquals("Category", taskQueryImpl.getCategory());
    assertSame(taskQueryImpl, actualTaskCategoryResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDefinitionKey(String)}
   */
  @Test
  public void testTaskDefinitionKey() {
    // Arrange and Act
    TaskQuery actualTaskDefinitionKeyResult = taskQueryImpl.taskDefinitionKey("Key");

    // Assert
    assertEquals("Key", taskQueryImpl.getKey());
    assertSame(taskQueryImpl, actualTaskDefinitionKeyResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDefinitionKeyLike(String)}
   */
  @Test
  public void testTaskDefinitionKeyLike() {
    // Arrange and Act
    TaskQuery actualTaskDefinitionKeyLikeResult = taskQueryImpl.taskDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", taskQueryImpl.getKeyLike());
    assertSame(taskQueryImpl, actualTaskDefinitionKeyLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  public void testTaskVariableValueEquals() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualTaskVariableValueEqualsResult = taskQueryImpl.taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEquals2() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueEqualsResult = taskQueryImpl.taskVariableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueEqualsIgnoreCaseResult = taskQueryImpl.taskVariableValueEqualsIgnoreCase("Name",
        "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueNotEqualsIgnoreCaseResult = taskQueryImpl
        .taskVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueNotEquals() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueNotEqualsResult = taskQueryImpl.taskVariableValueNotEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThan() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueGreaterThanResult = taskQueryImpl.taskVariableValueGreaterThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueGreaterThanOrEqualResult = taskQueryImpl
        .taskVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueLessThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThan() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueLessThanResult = taskQueryImpl.taskVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThanOrEqual() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueLessThanOrEqualResult = taskQueryImpl.taskVariableValueLessThanOrEqual("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueLike(String, String)}
   */
  @Test
  public void testTaskVariableValueLike() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueLikeResult = taskQueryImpl.taskVariableValueLike("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualTaskVariableValueLikeIgnoreCaseResult = taskQueryImpl.taskVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualTaskVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEquals() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualProcessVariableValueEqualsResult = taskQueryImpl.processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals2() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueEqualsResult = taskQueryImpl.processVariableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueNotEqualsResult = taskQueryImpl.processVariableValueNotEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueEqualsIgnoreCaseResult = taskQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = taskQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThan() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueGreaterThanResult = taskQueryImpl.processVariableValueGreaterThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueGreaterThanOrEqualResult = taskQueryImpl
        .processVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThan() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueLessThanResult = taskQueryImpl.processVariableValueLessThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThanOrEqual() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueLessThanOrEqualResult = taskQueryImpl
        .processVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  public void testProcessVariableValueLike() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueLikeResult = taskQueryImpl.processVariableValueLike("Name", "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualProcessVariableValueLikeIgnoreCaseResult = taskQueryImpl.processVariableValueLikeIgnoreCase("Name",
        "42");

    // Assert
    assertTrue(taskQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(taskQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionKeyResult = taskQueryImpl.processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", taskQueryImpl.getProcessDefinitionKey());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionKeyLikeResult = taskQueryImpl
        .processDefinitionKeyLike("Process Definition Key Like");

    // Assert
    assertEquals("Process Definition Key Like", taskQueryImpl.getProcessDefinitionKeyLike());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link TaskQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessDefinitionKeyLikeIgnoreCase() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionKeyLikeIgnoreCaseResult = taskQueryImpl
        .processDefinitionKeyLikeIgnoreCase("Process Definition Key Like Ignore Case");

    // Assert
    assertEquals("process definition key like ignore case", taskQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertSame(taskQueryImpl, actualProcessDefinitionKeyLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn() {
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
   * Method under test: {@link TaskQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionIdResult = taskQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getProcessDefinitionId());
    assertSame(taskQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionNameResult = taskQueryImpl.processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", taskQueryImpl.getProcessDefinitionName());
    assertSame(taskQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  public void testProcessDefinitionNameLike() {
    // Arrange and Act
    TaskQuery actualProcessDefinitionNameLikeResult = taskQueryImpl
        .processDefinitionNameLike("Process Definition Name Like");

    // Assert
    assertEquals("Process Definition Name Like", taskQueryImpl.getProcessDefinitionNameLike());
    assertSame(taskQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processCategoryIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> taskQueryImpl.processCategoryNotIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link TaskQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    TaskQuery actualDeploymentIdResult = taskQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", taskQueryImpl.getDeploymentId());
    assertSame(taskQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn() {
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
   * Method under test: {@link TaskQueryImpl#dueDate(Date)}
   */
  @Test
  public void testDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueDate(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueDate());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDueDate(Date)}
   */
  @Test
  public void testTaskDueDate() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueDate(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueDate());
  }

  /**
   * Method under test: {@link TaskQueryImpl#dueBefore(Date)}
   */
  @Test
  public void testDueBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueBefore(dueBefore));
    assertSame(dueBefore, taskQueryImpl.getDueBefore());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDueBefore(Date)}
   */
  @Test
  public void testTaskDueBefore() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueBefore(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueBefore());
  }

  /**
   * Method under test: {@link TaskQueryImpl#dueAfter(Date)}
   */
  @Test
  public void testDueAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.dueAfter(dueAfter));
    assertSame(dueAfter, taskQueryImpl.getDueAfter());
  }

  /**
   * Method under test: {@link TaskQueryImpl#taskDueAfter(Date)}
   */
  @Test
  public void testTaskDueAfter() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.taskDueAfter(dueDate));
    assertSame(dueDate, taskQueryImpl.getDueAfter());
  }

  /**
   * Method under test: {@link TaskQueryImpl#withoutDueDate()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#withoutTaskDueDate()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#excludeSubtasks()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#suspended()}
   */
  @Test
  public void testSuspended() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.suspended());
  }

  /**
   * Method under test: {@link TaskQueryImpl#active()}
   */
  @Test
  public void testActive() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act and Assert
    assertSame(taskQueryImpl, taskQueryImpl.active());
  }

  /**
   * Method under test: {@link TaskQueryImpl#locale(String)}
   */
  @Test
  public void testLocale() {
    // Arrange and Act
    TaskQuery actualLocaleResult = taskQueryImpl.locale("en");

    // Assert
    assertEquals("en", taskQueryImpl.getLocale());
    assertSame(taskQueryImpl, actualLocaleResult);
  }

  /**
   * Method under test: {@link TaskQueryImpl#includeTaskLocalVariables()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#includeProcessVariables()}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
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
   * Method under test: {@link TaskQueryImpl#getCandidateGroups()}
   */
  @Test
  public void testGetCandidateGroups() {
    // Arrange, Act and Assert
    assertNull((new TaskQueryImpl()).getCandidateGroups());
  }

  /**
   * Method under test: {@link TaskQueryImpl#or()}
   */
  @Test
  public void testOr() {
    // Arrange
    TaskQueryImpl taskQueryImpl = new TaskQueryImpl();

    // Act
    TaskQuery actualOrResult = taskQueryImpl.or();

    // Assert
    TaskQueryImpl taskQueryImpl2 = taskQueryImpl.currentOrQueryObject;
    assertEquals("RES.ID_ asc", taskQueryImpl2.getOrderBy());
    assertEquals("RES.ID_ asc", taskQueryImpl2.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", taskQueryImpl2.getMssqlOrDB2OrderBy());
    assertNull(taskQueryImpl2.getMaxPriority());
    assertNull(taskQueryImpl2.getMinPriority());
    assertNull(taskQueryImpl2.getPriority());
    assertNull(taskQueryImpl2.getTaskVariablesLimit());
    assertNull(taskQueryImpl2.getDatabaseType());
    assertNull(taskQueryImpl2.getAssignee());
    assertNull(taskQueryImpl2.getAssigneeLike());
    assertNull(taskQueryImpl2.getAssigneeLikeIgnoreCase());
    assertNull(taskQueryImpl2.getCandidateGroup());
    assertNull(taskQueryImpl2.getCandidateUser());
    assertNull(taskQueryImpl2.getCategory());
    assertNull(taskQueryImpl2.getDelegationStateString());
    assertNull(taskQueryImpl2.getDeploymentId());
    assertNull(taskQueryImpl2.getDescription());
    assertNull(taskQueryImpl2.getDescriptionLike());
    assertNull(taskQueryImpl2.getDescriptionLikeIgnoreCase());
    assertNull(taskQueryImpl2.getExecutionId());
    assertNull(taskQueryImpl2.getInvolvedUser());
    assertNull(taskQueryImpl2.getKey());
    assertNull(taskQueryImpl2.getKeyLike());
    assertNull(taskQueryImpl2.getLocale());
    assertNull(taskQueryImpl2.getName());
    assertNull(taskQueryImpl2.getNameLike());
    assertNull(taskQueryImpl2.getNameLikeIgnoreCase());
    assertNull(taskQueryImpl2.getOwner());
    assertNull(taskQueryImpl2.getOwnerLike());
    assertNull(taskQueryImpl2.getOwnerLikeIgnoreCase());
    assertNull(taskQueryImpl2.getProcessDefinitionId());
    assertNull(taskQueryImpl2.getProcessDefinitionKey());
    assertNull(taskQueryImpl2.getProcessDefinitionKeyLike());
    assertNull(taskQueryImpl2.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(taskQueryImpl2.getProcessDefinitionName());
    assertNull(taskQueryImpl2.getProcessDefinitionNameLike());
    assertNull(taskQueryImpl2.getProcessInstanceBusinessKey());
    assertNull(taskQueryImpl2.getProcessInstanceBusinessKeyLike());
    assertNull(taskQueryImpl2.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(taskQueryImpl2.getProcessInstanceId());
    assertNull(taskQueryImpl2.getTaskId());
    assertNull(taskQueryImpl2.getTaskParentTaskId());
    assertNull(taskQueryImpl2.getTenantId());
    assertNull(taskQueryImpl2.getTenantIdLike());
    assertNull(taskQueryImpl2.getUserIdForCandidateAndAssignee());
    assertNull(taskQueryImpl2.orderBy);
    assertNull(taskQueryImpl2.getCreateTime());
    assertNull(taskQueryImpl2.getCreateTimeAfter());
    assertNull(taskQueryImpl2.getCreateTimeBefore());
    assertNull(taskQueryImpl2.getDueAfter());
    assertNull(taskQueryImpl2.getDueBefore());
    assertNull(taskQueryImpl2.getDueDate());
    assertNull(taskQueryImpl2.getAssigneeIds());
    assertNull(taskQueryImpl2.getCandidateGroups());
    assertNull(taskQueryImpl2.getDeploymentIds());
    assertNull(taskQueryImpl2.getInvolvedGroups());
    assertNull(taskQueryImpl2.getNameList());
    assertNull(taskQueryImpl2.getNameListIgnoreCase());
    assertNull(taskQueryImpl2.getProcessCategoryInList());
    assertNull(taskQueryImpl2.getProcessCategoryNotInList());
    assertNull(taskQueryImpl2.getProcessDefinitionKeys());
    assertNull(taskQueryImpl2.getProcessInstanceIds());
    assertNull(taskQueryImpl2.candidateGroups);
    assertNull(taskQueryImpl2.nullHandlingOnOrder);
    assertNull(taskQueryImpl2.resultType);
    assertNull(taskQueryImpl2.currentOrQueryObject);
    assertNull(taskQueryImpl2.commandContext);
    assertNull(taskQueryImpl2.commandExecutor);
    assertNull(taskQueryImpl2.getSuspensionState());
    assertNull(taskQueryImpl2.orderProperty);
    assertNull(taskQueryImpl2.getDelegationState());
    assertEquals(0, taskQueryImpl2.getFirstResult());
    assertEquals(1, taskQueryImpl2.getFirstRow());
    assertFalse(taskQueryImpl2.hasLocalQueryVariableValue());
    assertFalse(taskQueryImpl2.hasNonLocalQueryVariableValue());
    assertFalse(taskQueryImpl2.getExcludeSubtasks());
    assertFalse(taskQueryImpl2.getNoDelegationState());
    assertFalse(taskQueryImpl2.getUnassigned());
    assertFalse(taskQueryImpl2.isBothCandidateAndAssigned());
    assertFalse(taskQueryImpl2.isIncludeProcessVariables());
    assertFalse(taskQueryImpl2.isIncludeTaskLocalVariables());
    assertFalse(taskQueryImpl2.isOrActive());
    assertFalse(taskQueryImpl2.isWithoutDueDate());
    assertFalse(taskQueryImpl2.isWithoutTenantId());
    assertFalse(taskQueryImpl2.withLocalizationFallback);
    assertTrue(taskQueryImpl.isOrActive());
    assertEquals(Integer.MAX_VALUE, taskQueryImpl2.getLastRow());
    assertEquals(Integer.MAX_VALUE, taskQueryImpl2.getMaxResults());
    assertSame(taskQueryImpl, actualOrResult);
    TaskQueryImpl expectedParameter = ((TaskQueryImpl) actualOrResult).currentOrQueryObject;
    assertSame(expectedParameter, taskQueryImpl2.getParameter());
  }

  /**
   * Method under test: {@link TaskQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new TaskQueryImpl()).endOr());
  }

  /**
   * Method under test: {@link TaskQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new TaskQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test: {@link TaskQueryImpl#getDelegationStateString()}
   */
  @Test
  public void testGetDelegationStateString() {
    // Arrange, Act and Assert
    assertNull((new TaskQueryImpl()).getDelegationStateString());
  }

  /**
   * Method under test: {@link TaskQueryImpl#TaskQueryImpl()}
   */
  @Test
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
}
