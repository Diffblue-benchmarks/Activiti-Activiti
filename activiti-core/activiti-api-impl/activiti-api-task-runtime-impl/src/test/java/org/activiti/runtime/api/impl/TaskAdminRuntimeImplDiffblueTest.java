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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.CandidateGroupsPayload;
import org.activiti.api.task.model.payloads.CandidateUsersPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.query.Query;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskQuery;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskAdminRuntimeImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskAdminRuntimeImplDiffblueTest {
  @MockBean
  private APITaskConverter aPITaskConverter;

  @MockBean
  private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean
  private SecurityManager securityManager;

  @Autowired
  private TaskAdminRuntimeImpl taskAdminRuntimeImpl;

  @MockBean
  private TaskRuntimeHelper taskRuntimeHelper;

  @MockBean
  private TaskService taskService;

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>When {@link GetTasksPayload#GetTasksPayload()}.</li>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; when GetTasksPayload(); then return PageImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable, GetTasksPayload)"})
  void testTasksWithPageableGetTasksPayload_whenGetTasksPayload_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<Task> actualTasksResult = taskAdminRuntimeImpl.tasks(pageable, new GetTasksPayload());

    // Assert
    verify(taskService).createTaskQuery();
    verify(taskQuery).count();
    verify(taskQuery).listPage(eq(1), eq(3));
    verify(aPITaskConverter).from(isA(Collection.class));
    assertTrue(actualTasksResult instanceof PageImpl);
    assertEquals(3, actualTasksResult.getTotalItems());
    assertTrue(actualTasksResult.getContent().isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   * <ul>
   *   <li>Given {@link TaskQuery} {@link Query#count()} return three.</li>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'; given TaskQuery count() return three; then return PageImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable_givenTaskQueryCountReturnThree_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(new ArrayList<>());

    // Act
    Page<Task> actualTasksResult = taskAdminRuntimeImpl.tasks(Pageable.of(1, 3));

    // Assert
    verify(taskService).createTaskQuery();
    verify(taskQuery).count();
    verify(taskQuery).listPage(eq(1), eq(3));
    verify(aPITaskConverter).from(isA(Collection.class));
    assertTrue(actualTasksResult instanceof PageImpl);
    assertEquals(3, actualTasksResult.getTotalItems());
    assertTrue(actualTasksResult.getContent().isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}.
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then return TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Task TaskAdminRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenReturnTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", TaskStatus.CREATED);

    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any())).thenReturn(taskImpl);

    // Act
    Task actualUpdateResult = taskAdminRuntimeImpl.update(new UpdateTaskPayload());

    // Assert
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(true), isA(UpdateTaskPayload.class));
    assertSame(taskImpl, actualUpdateResult);
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Task TaskAdminRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(true), isA(UpdateTaskPayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any())).thenReturn(new ArrayList<>());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any())).thenReturn(new HashMap<>());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult = taskAdminRuntimeImpl
        .variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).getInternalTaskVariables(isNull());
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).getInternalTaskVariables(isNull());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskService)
        .addCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload));
    verify(taskService).addCandidateUser(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#addCandidateUser(String, String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); given TaskService addCandidateUser(String, String) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_givenTaskServiceAddCandidateUserDoesNothing() {
    // Arrange
    doNothing().when(taskService).addCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act
    taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload);

    // Assert
    verify(taskService).addCandidateUser(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#isEmpty()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); then calls isEmpty()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_thenCallsIsEmpty() {
    // Arrange
    COWArrayList<String> candidateUsers = mock(COWArrayList.class);
    when(candidateUsers.isEmpty()).thenThrow(new IllegalStateException("foo"));

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload));
    verify(candidateUsers).isEmpty();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskService)
        .deleteCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload));
    verify(taskService).deleteCandidateUser(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#deleteCandidateUser(String, String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); given TaskService deleteCandidateUser(String, String) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_givenTaskServiceDeleteCandidateUserDoesNothing() {
    // Arrange
    doNothing().when(taskService).deleteCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act
    taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload);

    // Assert
    verify(taskService).deleteCandidateUser(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#isEmpty()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); then calls isEmpty()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_thenCallsIsEmpty() {
    // Arrange
    COWArrayList<String> candidateUsers = mock(COWArrayList.class);
    when(candidateUsers.isEmpty()).thenThrow(new IllegalStateException("foo"));

    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    candidateUsersPayload.setCandidateUsers(candidateUsers);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload));
    verify(candidateUsers).isEmpty();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskService)
        .addCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload));
    verify(taskService).addCandidateGroup(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#addCandidateGroup(String, String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); given TaskService addCandidateGroup(String, String) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_givenTaskServiceAddCandidateGroupDoesNothing() {
    // Arrange
    doNothing().when(taskService).addCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act
    taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload);

    // Assert
    verify(taskService).addCandidateGroup(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#isEmpty()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); then calls isEmpty()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_thenCallsIsEmpty() {
    // Arrange
    COWArrayList<String> candidateGroups = mock(COWArrayList.class);
    when(candidateGroups.isEmpty()).thenThrow(new IllegalStateException("foo"));

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload));
    verify(candidateGroups).isEmpty();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskService)
        .deleteCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload));
    verify(taskService).deleteCandidateGroup(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#deleteCandidateGroup(String, String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload); given TaskService deleteCandidateGroup(String, String) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_givenTaskServiceDeleteCandidateGroupDoesNothing() {
    // Arrange
    doNothing().when(taskService).deleteCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act
    taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload);

    // Assert
    verify(taskService).deleteCandidateGroup(isNull(), eq("2020-03-01"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#isEmpty()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload); then calls isEmpty()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_thenCallsIsEmpty() {
    // Arrange
    COWArrayList<String> candidateGroups = mock(COWArrayList.class);
    when(candidateGroups.isEmpty()).thenThrow(new IllegalStateException("foo"));

    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    candidateGroupsPayload.setCandidateGroups(candidateGroups);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload));
    verify(candidateGroups).isEmpty();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.userCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenThrow(new IllegalStateException("candidate"));
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.userCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl).getUserId();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenArrayListAddIdentityLinkEntityImpl_thenReturnEmpty() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} return {@code Type}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenIdentityLinkEntityImplGetTypeReturnType_thenReturnEmpty() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("Type");
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl).getUserId();
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenReturnEmpty() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenReturnSizeIsOne() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("candidate");
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl, atLeast(1)).getUserId();
    assertEquals(1, actualUserCandidatesResult.size());
    assertEquals("42", actualUserCandidatesResult.get(0));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.groupCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenThrow(new IllegalStateException("candidate"));
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.groupCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl).getGroupId();
    verify(identityLinkEntityImpl).getType();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenArrayListAddIdentityLinkEntityImpl_thenReturnEmpty() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} return {@code Type}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenIdentityLinkEntityImplGetTypeReturnType_thenReturnEmpty() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("Type");
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl).getGroupId();
    verify(identityLinkEntityImpl).getType();
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenReturnEmpty() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenReturnSizeIsOne() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("candidate");
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(identityLinkEntityImpl, atLeast(1)).getGroupId();
    verify(identityLinkEntityImpl).getType();
    assertEquals(1, actualGroupCandidatesResult.size());
    assertEquals("42", actualGroupCandidatesResult.get(0));
  }
}
