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
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskQuery;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
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
   * Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable)}
   */
  @Test
  void testTasks() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    ArrayList<org.activiti.api.task.model.Task> taskList = new ArrayList<>();
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(taskList);

    // Act
    Page<org.activiti.api.task.model.Task> actualTasksResult = taskAdminRuntimeImpl.tasks(Pageable.of(1, 3));

    // Assert
    verify(taskService).createTaskQuery();
    verify(taskQuery).count();
    verify(taskQuery).listPage(eq(1), eq(3));
    verify(aPITaskConverter).from(isA(Collection.class));
    assertTrue(actualTasksResult instanceof PageImpl);
    assertEquals(3, actualTasksResult.getTotalItems());
    List<org.activiti.api.task.model.Task> content = actualTasksResult.getContent();
    assertTrue(content.isEmpty());
    assertSame(taskList, content);
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  void testTasks2() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    ArrayList<org.activiti.api.task.model.Task> taskList = new ArrayList<>();
    when(aPITaskConverter.from(Mockito.<Collection<org.activiti.engine.task.Task>>any())).thenReturn(taskList);
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
    List<org.activiti.api.task.model.Task> content = actualTasksResult.getContent();
    assertTrue(content.isEmpty());
    assertSame(taskList, content);
  }

  /**
   * Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  void testUpdate() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  void testUpdate2() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(true), isA(UpdateTaskPayload.class));
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  void testVariables() {
    // Arrange
    ArrayList<org.activiti.api.model.shared.model.VariableInstance> variableInstanceList = new ArrayList<>();
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any())).thenReturn(new HashMap<>());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult = taskAdminRuntimeImpl
        .variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).getInternalTaskVariables(isNull());
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
    assertSame(variableInstanceList, actualVariablesResult);
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  void testVariables2() {
    // Arrange
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).getInternalTaskVariables(isNull());
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  void testCreateVariable() {
    // Arrange
    doNothing().when(taskRuntimeHelper).createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  void testCreateVariable2() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  void testUpdateVariable() {
    // Arrange
    doNothing().when(taskRuntimeHelper).updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert that nothing has changed
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  void testUpdateVariable2() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testAddCandidateUsers() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testAddCandidateUsers2() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testAddCandidateUsers3() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testDeleteCandidateUsers() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = mock(CandidateUsersPayload.class);
    when(candidateUsersPayload.getCandidateUsers()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload);

    // Assert that nothing has changed
    verify(candidateUsersPayload, atLeast(1)).getCandidateUsers();
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testDeleteCandidateUsers2() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  void testDeleteCandidateUsers3() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testAddCandidateGroups() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testAddCandidateGroups2() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testAddCandidateGroups3() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testDeleteCandidateGroups() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = mock(CandidateGroupsPayload.class);
    when(candidateGroupsPayload.getCandidateGroups()).thenReturn(new ArrayList<>());

    // Act
    taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload);

    // Assert that nothing has changed
    verify(candidateGroupsPayload, atLeast(1)).getCandidateGroups();
  }

  /**
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testDeleteCandidateGroups2() {
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
   * Method under test:
   * {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  void testDeleteCandidateGroups3() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates2() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates3() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.userCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
  }

  /**
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates4() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates5() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  void testUserCandidates6() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates2() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates3() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.groupCandidates("42"));
    verify(taskService).getIdentityLinksForTask(eq("42"));
  }

  /**
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates4() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates5() {
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
   * Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  void testGroupCandidates6() {
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
