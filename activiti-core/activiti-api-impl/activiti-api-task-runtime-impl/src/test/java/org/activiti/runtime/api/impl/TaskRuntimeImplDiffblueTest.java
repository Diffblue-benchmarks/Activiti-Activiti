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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
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
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskRuntimeImpl.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TaskRuntimeImplDiffblueTest {
  @MockBean private APITaskConverter aPITaskConverter;

  @MockBean private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean private SecurityManager securityManager;

  @MockBean private TaskRuntimeConfiguration taskRuntimeConfiguration;

  @MockBean private TaskRuntimeHelper taskRuntimeHelper;

  @Autowired private TaskRuntimeImpl taskRuntimeImpl;

  @MockBean private TaskService taskService;

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable2() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload2() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload3() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload4() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), mock(GetTasksPayload.class)));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload5() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), mock(GetTasksPayload.class)));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link GetTasksPayload#getAssigneeId()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given '42'; then calls getAssigneeId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_given42_thenCallsGetAssigneeId()
      throws SecurityException {
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
    verify(getTasksPayload).setAssigneeId("42");
    verify(getTasksPayload).setGroups(isA(List.class));
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl2).or();
    verify(taskQueryImpl).taskCandidateOrAssigned(eq("42"), isA(List.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_givenIllegalStateException() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetTasksPayload getTasksPayload = mock(GetTasksPayload.class);
    doThrow(new IllegalStateException()).when(getTasksPayload).setAssigneeId(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(getTasksPayload).setAssigneeId("42");
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given TaskQueryImpl or() return TaskQueryImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_givenTaskQueryImplOrReturnTaskQueryImpl()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; when 'null'; then calls taskCandidateOrAssigned(String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_whenNull_thenCallsTaskCandidateOrAssigned()
      throws SecurityException {
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
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'; then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
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
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is {@code 42}
   *       and {@code Name} and status is {@code CREATED}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test update(UpdateTaskPayload); then return TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenReturnTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", TaskStatus.CREATED);
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenReturn(taskImpl);

    // Act
    Task actualUpdateResult = taskRuntimeImpl.update(new UpdateTaskPayload());

    // Assert
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
    assertSame(taskImpl, actualUpdateResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserRoles()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then calls getAuthenticatedUserRoles()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenCallsGetAuthenticatedUserRoles() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserRoles()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then calls getAuthenticatedUserRoles()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenCallsGetAuthenticatedUserRoles() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter} {@link
   *       APIVariableInstanceConverter#from(Collection)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter from(Collection) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverterFromThrowIllegalStateException() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenThrow(new IllegalStateException());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverter_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult =
        taskRuntimeImpl.variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#createVariable(boolean,
   *       CreateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#updateVariable(boolean,
   *       UpdateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#setVariablesLocal(String, Map)} does
   *       nothing.
   *   <li>Then calls {@link TaskService#setVariablesLocal(String, Map)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService setVariablesLocal(String, Map) does nothing; then calls setVariablesLocal(String, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskServiceSetVariablesLocalDoesNothing_thenCallsSetVariablesLocal() {
    // Arrange
    doNothing()
        .when(taskService)
        .setVariablesLocal(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());
    doNothing().when(taskRuntimeHelper).handleSaveTaskPayload(Mockito.<SaveTaskPayload>any());

    // Act
    taskRuntimeImpl.save(new SaveTaskPayload());

    // Assert
    verify(taskService).setVariablesLocal(isNull(), isNull());
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).handleSaveTaskPayload(isA(SaveTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#setVariablesLocal(String, Map)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService setVariablesLocal(String, Map) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskServiceSetVariablesLocalThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .setVariablesLocal(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());
    doNothing().when(taskRuntimeHelper).handleSaveTaskPayload(Mockito.<SaveTaskPayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskService).setVariablesLocal(isNull(), isNull());
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).handleSaveTaskPayload(isA(SaveTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>When {@link SaveTaskPayload#SaveTaskPayload()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService; when SaveTaskPayload(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskService_whenSaveTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload); then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskService).createTaskQuery();
  }
}
