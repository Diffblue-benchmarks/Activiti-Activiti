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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.Task;
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
import org.activiti.engine.query.Query;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskQuery;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>When {@link GetTasksPayload#GetTasksPayload()}.</li>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; when GetTasksPayload(); then return PageImpl")
  void testTasksWithPageableGetTasksPayload_whenGetTasksPayload_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<org.activiti.api.task.model.Task> actualTasksResult = taskAdminRuntimeImpl.tasks(pageable,
        new GetTasksPayload());

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
  void testTasksWithPageable_givenTaskQueryCountReturnThree_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.task.model.Task> actualTasksResult = taskAdminRuntimeImpl.tasks(Pageable.of(1, 3));

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
   *   <li>Then return {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id
   * is {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then return TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testUpdate_thenReturnTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then throw IllegalStateException")
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
   *   <li>Given {@link TaskRuntimeHelper}
   * {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}
   * does nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
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
   *   <li>Given {@link TaskRuntimeHelper}
   * {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}
   * does nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link CandidateUsersPayload#getCandidateUsers()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); given ArrayList(); then calls getCandidateUsers()")
  void testAddCandidateUsers_givenArrayList_thenCallsGetCandidateUsers() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then calls {@link TaskService#addCandidateUser(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); then calls addCandidateUser(String, String)")
  void testAddCandidateUsers_thenCallsAddCandidateUser() {
    // Arrange
    doNothing().when(taskService).addCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getTaskId()).thenReturn("42");
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(stringList);

    // Act
    taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
    verify(candidateUsersPayload).getTaskId();
    verify(taskService).addCandidateUser(eq("42"), eq("foo"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); then throw IllegalStateException")
  void testAddCandidateUsers_thenThrowIllegalStateException() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getTaskId()).thenThrow(new IllegalStateException("foo"));
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(stringList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload));
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
    verify(candidateUsersPayload).getTaskId();
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link CandidateUsersPayload#getCandidateUsers()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); given ArrayList(); then calls getCandidateUsers()")
  void testDeleteCandidateUsers_givenArrayList_thenCallsGetCandidateUsers() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then calls {@link TaskService#deleteCandidateUser(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); then calls deleteCandidateUser(String, String)")
  void testDeleteCandidateUsers_thenCallsDeleteCandidateUser() {
    // Arrange
    doNothing().when(taskService).deleteCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getTaskId()).thenReturn("42");
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(stringList);

    // Act
    taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
    verify(candidateUsersPayload).getTaskId();
    verify(taskService).deleteCandidateUser(eq("42"), eq("foo"));
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); then throw IllegalStateException")
  void testDeleteCandidateUsers_thenThrowIllegalStateException() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getTaskId()).thenThrow(new IllegalStateException("foo"));
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(stringList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload));
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
    verify(candidateUsersPayload).getTaskId();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link CandidateGroupsPayload#getCandidateGroups()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); given ArrayList(); then calls getCandidateGroups()")
  void testAddCandidateGroups_givenArrayList_thenCallsGetCandidateGroups() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then calls {@link TaskService#addCandidateGroup(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); then calls addCandidateGroup(String, String)")
  void testAddCandidateGroups_thenCallsAddCandidateGroup() {
    // Arrange
    doNothing().when(taskService).addCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getTaskId()).thenReturn("42");
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(stringList);

    // Act
    taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
    verify(candidateGroupsPayload).getTaskId();
    verify(taskService).addCandidateGroup(eq("42"), eq("foo"));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); then throw IllegalStateException")
  void testAddCandidateGroups_thenThrowIllegalStateException() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getTaskId()).thenThrow(new IllegalStateException("foo"));
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(stringList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload));
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
    verify(candidateGroupsPayload).getTaskId();
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link CandidateGroupsPayload#getCandidateGroups()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload); given ArrayList(); then calls getCandidateGroups()")
  void testDeleteCandidateGroups_givenArrayList_thenCallsGetCandidateGroups() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then calls {@link TaskService#deleteCandidateGroup(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload); then calls deleteCandidateGroup(String, String)")
  void testDeleteCandidateGroups_thenCallsDeleteCandidateGroup() {
    // Arrange
    doNothing().when(taskService).deleteCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getTaskId()).thenReturn("42");
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(stringList);

    // Act
    taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
    verify(candidateGroupsPayload).getTaskId();
    verify(taskService).deleteCandidateGroup(eq("42"), eq("foo"));
  }

  /**
   * Test
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups(CandidateGroupsPayload); then throw IllegalStateException")
  void testDeleteCandidateGroups_thenThrowIllegalStateException() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getTaskId()).thenThrow(new IllegalStateException("foo"));
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(stringList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload));
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
    verify(candidateGroupsPayload).getTaskId();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl}
   * (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
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
   *   <li>Given {@link IdentityLinkEntityImpl}
   * {@link IdentityLinkEntityImpl#getType()} return {@code Type}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl}
   * (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
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
   *   <li>Given {@link IdentityLinkEntityImpl}
   * {@link IdentityLinkEntityImpl#getType()} return {@code Type}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
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
