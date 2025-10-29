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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  void testTasks() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException("foo"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  void testTasks2() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  void testTasks3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks4() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks5() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks6() {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks7() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks8() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks9() {
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
   * Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks10() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  void testUpdate() {
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
   * Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  void testUpdate2() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates2() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates2() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  void testVariables() {
    // Arrange
    ArrayList<org.activiti.api.model.shared.model.VariableInstance> variableInstanceList = new ArrayList<>();
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(variableInstanceList);
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
    assertSame(variableInstanceList, actualVariablesResult);
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  void testVariables2() {
    // Arrange
    doThrow(new NotFoundException("An error occurred")).when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  void testCreateVariable() {
    // Arrange
    doNothing().when(taskRuntimeHelper).createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  void testCreateVariable2() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  void testUpdateVariable() {
    // Arrange
    doNothing().when(taskRuntimeHelper).updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  void testUpdateVariable2() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  void testSave() {
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
   * Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  void testSave2() {
    // Arrange
    doThrow(new NotFoundException("An error occurred")).when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(isNull());
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  void testAssign() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  void testAssign2() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  void testAssign3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }
}
