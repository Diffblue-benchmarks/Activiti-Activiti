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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
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
import java.util.Map;
import org.activiti.api.runtime.shared.NotFoundException;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.AssignTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {TaskRuntimeImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskRuntimeImplDiffblueTest {
  @MockBean
  private APITaskConverter aPITaskConverter;

  @MockBean
  private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean
  private SecurityManager securityManager;

  @MockBean
  private TaskRuntimeConfiguration taskRuntimeConfiguration;

  @MockBean
  private TaskRuntimeHelper taskRuntimeHelper;

  @Autowired
  private TaskRuntimeImpl taskRuntimeImpl;

  @MockBean
  private TaskService taskService;

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  void testTasksWithPageable() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException("foo"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  void testTasksWithPageable2() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  void testTasksWithPageableGetTasksPayload() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException("foo"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  void testTasksWithPageableGetTasksPayload2() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn("");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  void testTasksWithPageableGetTasksPayload3() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), mock(GetTasksPayload.class)));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link GetTasksPayload#getAssigneeId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given '42'; then calls getAssigneeId()")
  void testTasksWithPageableGetTasksPayload_given42_thenCallsGetAssigneeId() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.taskCandidateOrAssigned(Mockito.<String>any(), Mockito.<List<String>>any()))
        .thenReturn(new TaskQueryImpl());
    TaskQueryImpl taskQueryImpl2 = mock(TaskQueryImpl.class);
    when(taskQueryImpl2.or()).thenReturn(taskQueryImpl);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl2);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetTasksPayload getTasksPayload = mock(GetTasksPayload.class);
    when(getTasksPayload.getAssigneeId()).thenReturn("42");
    when(getTasksPayload.getGroups()).thenReturn(new ArrayList<>());
    doNothing().when(getTasksPayload).setAssigneeId(Mockito.<String>any());
    doNothing().when(getTasksPayload).setGroups(Mockito.<List<String>>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(getTasksPayload).getAssigneeId();
    verify(getTasksPayload).getGroups();
    verify(getTasksPayload).setAssigneeId(eq("42"));
    verify(getTasksPayload).setGroups(isA(List.class));
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl2).or();
    verify(taskQueryImpl).taskCandidateOrAssigned(eq("42"), isA(List.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return
   * {@link TaskQueryImpl#TaskQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given TaskQueryImpl or() return TaskQueryImpl()")
  void testTasksWithPageableGetTasksPayload_givenTaskQueryImplOrReturnTaskQueryImpl() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; then throw NotFoundException")
  void testTasksWithPageableGetTasksPayload_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetTasksPayload getTasksPayload = mock(GetTasksPayload.class);
    doThrow(new NotFoundException("An error occurred")).when(getTasksPayload).setAssigneeId(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(getTasksPayload).setAssigneeId(eq("42"));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with
   * {@code pageable}, {@code getTasksPayload}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then calls
   * {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; when 'null'; then calls taskCandidateOrAssigned(String, List)")
  void testTasksWithPageableGetTasksPayload_whenNull_thenCallsTaskCandidateOrAssigned() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.taskCandidateOrAssigned(Mockito.<String>any(), Mockito.<List<String>>any()))
        .thenReturn(new TaskQueryImpl());
    TaskQueryImpl taskQueryImpl2 = mock(TaskQueryImpl.class);
    when(taskQueryImpl2.or()).thenReturn(taskQueryImpl);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl2);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), null));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl2).or();
    verify(taskQueryImpl).taskCandidateOrAssigned(eq("42"), isA(List.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'; then throw ActivitiException")
  void testTasksWithPageable_thenThrowActivitiException() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager, atLeast(1)).getAuthenticatedUserGroups();
    verify(securityManager, atLeast(1)).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id
   * is {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then return TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  void testUpdate_thenReturnTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any())).thenReturn(taskImpl);

    // Act
    Task actualUpdateResult = taskRuntimeImpl.update(new UpdateTaskPayload());

    // Assert
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
    assertSame(taskImpl, actualUpdateResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then throw IllegalStateException")
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then throw NotFoundException")
  void testUserCandidates_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then throw NotFoundException")
  void testGroupCandidates_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any())).thenReturn(new HashMap<>());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult = taskRuntimeImpl
        .variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
    verify(taskRuntimeHelper).getInternalTaskVariables(isNull());
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then throw NotFoundException")
  void testVariables_thenThrowNotFoundException() {
    // Arrange
    doThrow(new NotFoundException("An error occurred")).when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper}
   * {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}
   * does nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
  void testCreateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper}
   * {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}
   * does nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing().when(taskRuntimeHelper).updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
  void testUpdateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   * <ul>
   *   <li>Given {@link TaskService}
   * {@link TaskService#setVariablesLocal(String, Map)} does nothing.</li>
   *   <li>Then calls {@link TaskService#setVariablesLocal(String, Map)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName("Test save(SaveTaskPayload); given TaskService setVariablesLocal(String, Map) does nothing; then calls setVariablesLocal(String, Map)")
  void testSave_givenTaskServiceSetVariablesLocalDoesNothing_thenCallsSetVariablesLocal() {
    // Arrange
    doNothing().when(taskService).setVariablesLocal(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());
    doNothing().when(taskRuntimeHelper).handleSaveTaskPayload(Mockito.<SaveTaskPayload>any());

    // Act
    taskRuntimeImpl.save(new SaveTaskPayload());

    // Assert that nothing has changed
    verify(taskService).setVariablesLocal(isNull(), isNull());
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
    verify(taskRuntimeHelper).handleSaveTaskPayload(isA(SaveTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName("Test save(SaveTaskPayload); then throw NotFoundException")
  void testSave_thenThrowNotFoundException() {
    // Arrange
    doThrow(new NotFoundException("An error occurred")).when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return empty string")
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return 'null'")
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload); then throw NotFoundException")
  void testAssign_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }
}
